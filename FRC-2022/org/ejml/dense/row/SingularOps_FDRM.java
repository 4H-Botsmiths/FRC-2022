/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row;

import java.util.Arrays;
import org.ejml.UtilEjml;
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.SpecializedOps_FDRM;
import org.ejml.dense.row.factory.DecompositionFactory_FDRM;
import org.ejml.dense.row.linsol.qr.SolveNullSpaceQRP_FDRM;
import org.ejml.dense.row.linsol.qr.SolveNullSpaceQR_FDRM;
import org.ejml.dense.row.linsol.svd.SolveNullSpaceSvd_FDRM;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F32;
import org.jetbrains.annotations.Nullable;

public class SingularOps_FDRM {
    public static float[] singularValues(FMatrixRMaj A) {
        SingularValueDecomposition_F32<FMatrixRMaj> svd = DecompositionFactory_FDRM.svd(A.numRows, A.numCols, false, true, true);
        if (svd.inputModified()) {
            A = A.copy();
        }
        if (!svd.decompose(A)) {
            throw new RuntimeException("SVD Failed!");
        }
        float[] sv = svd.getSingularValues();
        Arrays.sort(sv, 0, svd.numberOfSingularValues());
        for (int i = 0; i < sv.length / 2; ++i) {
            float tmp = sv[i];
            sv[i] = sv[sv.length - i - 1];
            sv[sv.length - i - 1] = tmp;
        }
        return sv;
    }

    public static float ratioSmallestOverLargest(float[] sv) {
        float min;
        if (sv.length == 0) {
            return Float.NaN;
        }
        float max = min = sv[0];
        for (int i = 1; i < sv.length; ++i) {
            float v = sv[i];
            if (v > max) {
                max = v;
                continue;
            }
            if (!(v < min)) continue;
            min = v;
        }
        return min / max;
    }

    public static int rank(FMatrixRMaj A, float threshold) {
        SingularValueDecomposition_F32<FMatrixRMaj> svd = DecompositionFactory_FDRM.svd(A.numRows, A.numCols, false, true, true);
        if (svd.inputModified()) {
            A = A.copy();
        }
        if (!svd.decompose(A)) {
            throw new RuntimeException("SVD Failed!");
        }
        float[] sv = svd.getSingularValues();
        int count = 0;
        for (int i = 0; i < sv.length; ++i) {
            if (!(sv[i] >= threshold)) continue;
            ++count;
        }
        return count;
    }

    public static int rank(FMatrixRMaj A) {
        SingularValueDecomposition_F32<FMatrixRMaj> svd = DecompositionFactory_FDRM.svd(A.numRows, A.numCols, false, true, true);
        if (svd.inputModified()) {
            A = A.copy();
        }
        if (!svd.decompose(A)) {
            throw new RuntimeException("SVD Failed!");
        }
        int N = svd.numberOfSingularValues();
        float[] sv = svd.getSingularValues();
        float threshold = SingularOps_FDRM.singularThreshold(sv, N);
        int count = 0;
        for (int i = 0; i < sv.length; ++i) {
            if (!(sv[i] >= threshold)) continue;
            ++count;
        }
        return count;
    }

    public static boolean svd(FMatrixRMaj A, @Nullable FMatrixRMaj U, FGrowArray sv, @Nullable FMatrixRMaj Vt) {
        boolean needV;
        boolean needU = U != null;
        SingularValueDecomposition_F32<FMatrixRMaj> svd = DecompositionFactory_FDRM.svd(A.numRows, A.numCols, needU, needV = Vt != null, true);
        if (svd.inputModified()) {
            A = A.copy();
        }
        if (!svd.decompose(A)) {
            return false;
        }
        int N = Math.min(A.numCols, A.numRows);
        if (needU) {
            svd.getU(U, false);
        }
        if (needV) {
            svd.getV(Vt, true);
        }
        sv.reshape(N);
        System.arraycopy(svd.getSingularValues(), 0, sv.data, 0, N);
        SingularOps_FDRM.descendingOrder(U, false, sv.data, N, Vt, true);
        return true;
    }

    public static void descendingOrder(FMatrixRMaj U, boolean tranU, FMatrixRMaj W, FMatrixRMaj V, boolean tranV) {
        int numSingular = Math.min(W.numRows, W.numCols);
        SingularOps_FDRM.checkSvdMatrixSize(U, tranU, W, V, tranV);
        for (int i = 0; i < numSingular; ++i) {
            float bigValue = -1.0f;
            int bigIndex = -1;
            for (int j = i; j < numSingular; ++j) {
                float v = W.get(j, j);
                if (!(v > bigValue)) continue;
                bigValue = v;
                bigIndex = j;
            }
            if (bigIndex == i) continue;
            if (bigIndex == -1) break;
            float tmp = W.get(i, i);
            W.set(i, i, bigValue);
            W.set(bigIndex, bigIndex, tmp);
            if (V != null) {
                SingularOps_FDRM.swapRowOrCol(V, tranV, i, bigIndex);
            }
            if (U == null) continue;
            SingularOps_FDRM.swapRowOrCol(U, tranU, i, bigIndex);
        }
    }

    public static void descendingOrder(@Nullable FMatrixRMaj U, boolean tranU, float[] singularValues, int singularLength, @Nullable FMatrixRMaj V, boolean tranV) {
        for (int i = 0; i < singularLength; ++i) {
            float bigValue = -1.0f;
            int bigIndex = -1;
            for (int j = i; j < singularLength; ++j) {
                float v = singularValues[j];
                if (!(v > bigValue)) continue;
                bigValue = v;
                bigIndex = j;
            }
            if (bigIndex == i) continue;
            if (bigIndex == -1) break;
            float tmp = singularValues[i];
            singularValues[i] = bigValue;
            singularValues[bigIndex] = tmp;
            if (V != null) {
                SingularOps_FDRM.swapRowOrCol(V, tranV, i, bigIndex);
            }
            if (U == null) continue;
            SingularOps_FDRM.swapRowOrCol(U, tranU, i, bigIndex);
        }
    }

    public static void checkSvdMatrixSize(@Nullable FMatrixRMaj U, boolean tranU, FMatrixRMaj W, @Nullable FMatrixRMaj V, boolean tranV) {
        boolean compact;
        int numSingular = Math.min(W.numRows, W.numCols);
        boolean bl = compact = W.numRows == W.numCols;
        if (compact) {
            if (U != null) {
                if (tranU && U.numRows != numSingular) {
                    throw new IllegalArgumentException("Unexpected size of matrix U");
                }
                if (!tranU && U.numCols != numSingular) {
                    throw new IllegalArgumentException("Unexpected size of matrix U");
                }
            }
            if (V != null) {
                if (tranV && V.numRows != numSingular) {
                    throw new IllegalArgumentException("Unexpected size of matrix V");
                }
                if (!tranV && V.numCols != numSingular) {
                    throw new IllegalArgumentException("Unexpected size of matrix V");
                }
            }
        } else {
            if (U != null && U.numRows != U.numCols) {
                throw new IllegalArgumentException("Unexpected size of matrix U");
            }
            if (V != null && V.numRows != V.numCols) {
                throw new IllegalArgumentException("Unexpected size of matrix V");
            }
            if (U != null && U.numRows != W.numRows) {
                throw new IllegalArgumentException("Unexpected size of W");
            }
            if (V != null && V.numRows != W.numCols) {
                throw new IllegalArgumentException("Unexpected size of W");
            }
        }
    }

    private static void swapRowOrCol(FMatrixRMaj M, boolean tran, int i, int bigIndex) {
        if (tran) {
            for (int col = 0; col < M.numCols; ++col) {
                float tmp = M.get(i, col);
                M.set(i, col, M.get(bigIndex, col));
                M.set(bigIndex, col, tmp);
            }
        } else {
            for (int row = 0; row < M.numRows; ++row) {
                float tmp = M.get(row, i);
                M.set(row, i, M.get(row, bigIndex));
                M.set(row, bigIndex, tmp);
            }
        }
    }

    public static FMatrixRMaj nullSpace(SingularValueDecomposition_F32<FMatrixRMaj> svd, @Nullable FMatrixRMaj nullSpace, float tol) {
        int i;
        int N = svd.numberOfSingularValues();
        float[] s = svd.getSingularValues();
        FMatrixRMaj V = svd.getV(null, true);
        if (V.numRows != svd.numCols()) {
            throw new IllegalArgumentException("Can't compute the null space using a compact SVD for a matrix of this size.");
        }
        int numVectors = svd.numCols() - N;
        for (int i2 = 0; i2 < N; ++i2) {
            if (!(s[i2] <= tol)) continue;
            ++numVectors;
        }
        if (nullSpace == null) {
            nullSpace = new FMatrixRMaj(numVectors, svd.numCols());
        } else {
            nullSpace.reshape(numVectors, svd.numCols());
        }
        int count = 0;
        for (i = 0; i < N; ++i) {
            if (!(s[i] <= tol)) continue;
            CommonOps_FDRM.extract(V, i, i + 1, 0, V.numCols, nullSpace, count++, 0);
        }
        for (i = N; i < svd.numCols(); ++i) {
            CommonOps_FDRM.extract(V, i, i + 1, 0, V.numCols, nullSpace, count++, 0);
        }
        CommonOps_FDRM.transpose(nullSpace);
        return nullSpace;
    }

    public static FMatrixRMaj nullspaceQR(FMatrixRMaj A, int totalSingular) {
        SolveNullSpaceQR_FDRM solver = new SolveNullSpaceQR_FDRM();
        FMatrixRMaj nullspace = new FMatrixRMaj(1, 1);
        if (!solver.process(A, totalSingular, nullspace)) {
            throw new RuntimeException("Solver failed. try SVD based method instead?");
        }
        return nullspace;
    }

    public static FMatrixRMaj nullspaceQRP(FMatrixRMaj A, int totalSingular) {
        SolveNullSpaceQRP_FDRM solver = new SolveNullSpaceQRP_FDRM();
        FMatrixRMaj nullspace = new FMatrixRMaj(1, 1);
        if (!solver.process(A, totalSingular, nullspace)) {
            throw new RuntimeException("Solver failed. try SVD based method instead?");
        }
        return nullspace;
    }

    public static FMatrixRMaj nullspaceSVD(FMatrixRMaj A, int totalSingular) {
        SolveNullSpaceSvd_FDRM solver = new SolveNullSpaceSvd_FDRM();
        FMatrixRMaj nullspace = new FMatrixRMaj(1, 1);
        if (!solver.process(A, totalSingular, nullspace)) {
            throw new RuntimeException("Solver failed. try SVD based method instead?");
        }
        return nullspace;
    }

    public static FMatrixRMaj nullVector(SingularValueDecomposition_F32<FMatrixRMaj> svd, boolean isRight, @Nullable FMatrixRMaj nullVector) {
        FMatrixRMaj A;
        int N = svd.numberOfSingularValues();
        float[] s = svd.getSingularValues();
        FMatrixRMaj fMatrixRMaj = A = isRight ? (FMatrixRMaj)svd.getV(null, true) : (FMatrixRMaj)svd.getU(null, false);
        if (isRight) {
            if (A.numRows != svd.numCols()) {
                throw new IllegalArgumentException("Can't compute the null space using a compact SVD for a matrix of this size.");
            }
            if (nullVector == null) {
                nullVector = new FMatrixRMaj(svd.numCols(), 1);
            } else {
                nullVector.reshape(svd.numCols(), 1);
            }
        } else {
            if (A.numCols != svd.numRows()) {
                throw new IllegalArgumentException("Can't compute the null space using a compact SVD for a matrix of this size.");
            }
            if (nullVector == null) {
                nullVector = new FMatrixRMaj(svd.numRows(), 1);
            } else {
                nullVector.reshape(svd.numRows(), 1);
            }
        }
        int smallestIndex = -1;
        if (isRight && svd.numCols() > svd.numRows()) {
            smallestIndex = svd.numCols() - 1;
        } else if (!isRight && svd.numCols() < svd.numRows()) {
            smallestIndex = svd.numRows() - 1;
        } else {
            float smallestValue = Float.MAX_VALUE;
            for (int i = 0; i < N; ++i) {
                if (!(s[i] < smallestValue)) continue;
                smallestValue = s[i];
                smallestIndex = i;
            }
        }
        if (isRight) {
            SpecializedOps_FDRM.subvector(A, smallestIndex, 0, A.numRows, true, 0, nullVector);
        } else {
            SpecializedOps_FDRM.subvector(A, 0, smallestIndex, A.numRows, false, 0, nullVector);
        }
        return nullVector;
    }

    public static float singularThreshold(SingularValueDecomposition_F32<?> svd) {
        return SingularOps_FDRM.singularThreshold(svd, UtilEjml.F_EPS);
    }

    public static float singularThreshold(SingularValueDecomposition_F32<?> svd, float tolerance) {
        float[] w = svd.getSingularValues();
        int N = svd.numberOfSingularValues();
        return SingularOps_FDRM.singularThreshold(w, N, tolerance);
    }

    private static float singularThreshold(float[] w, int N) {
        return SingularOps_FDRM.singularThreshold(w, N, UtilEjml.F_EPS);
    }

    private static float singularThreshold(float[] w, int N, float tolerance) {
        float largest = 0.0f;
        for (int j = 0; j < N; ++j) {
            if (!(w[j] > largest)) continue;
            largest = w[j];
        }
        return (float)N * largest * tolerance;
    }

    public static int rank(SingularValueDecomposition_F32<?> svd) {
        float threshold = SingularOps_FDRM.singularThreshold(svd);
        return SingularOps_FDRM.rank(svd, threshold);
    }

    public static int rank(SingularValueDecomposition_F32<?> svd, float threshold) {
        int numRank = 0;
        float[] w = svd.getSingularValues();
        int N = svd.numberOfSingularValues();
        for (int j = 0; j < N; ++j) {
            if (!(w[j] > threshold)) continue;
            ++numRank;
        }
        return numRank;
    }

    public static int nullity(SingularValueDecomposition_F32<?> svd) {
        float threshold = SingularOps_FDRM.singularThreshold(svd);
        return SingularOps_FDRM.nullity(svd, threshold);
    }

    public static int nullity(SingularValueDecomposition_F32<?> svd, float threshold) {
        int ret = 0;
        float[] w = svd.getSingularValues();
        int N = svd.numberOfSingularValues();
        int numCol = svd.numCols();
        for (int j = 0; j < N; ++j) {
            if (!(w[j] <= threshold)) continue;
            ++ret;
        }
        return ret + numCol - N;
    }

    public static int nullity(FMatrixRMaj A, float threshold) {
        SingularValueDecomposition_F32<FMatrixRMaj> svd = DecompositionFactory_FDRM.svd(A.numRows, A.numCols, false, true, true);
        if (svd.inputModified()) {
            A = A.copy();
        }
        if (!svd.decompose(A)) {
            throw new RuntimeException("SVD Failed!");
        }
        float[] sv = svd.getSingularValues();
        int count = 0;
        for (int i = 0; i < sv.length; ++i) {
            if (!(sv[i] <= threshold)) continue;
            ++count;
        }
        return count;
    }
}

