/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row;

import java.util.Arrays;
import org.ejml.UtilEjml;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.SpecializedOps_DDRM;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.dense.row.linsol.qr.SolveNullSpaceQRP_DDRM;
import org.ejml.dense.row.linsol.qr.SolveNullSpaceQR_DDRM;
import org.ejml.dense.row.linsol.svd.SolveNullSpaceSvd_DDRM;
import org.ejml.interfaces.decomposition.SingularValueDecomposition_F64;
import org.jetbrains.annotations.Nullable;

public class SingularOps_DDRM {
    public static double[] singularValues(DMatrixRMaj A) {
        SingularValueDecomposition_F64<DMatrixRMaj> svd = DecompositionFactory_DDRM.svd(A.numRows, A.numCols, false, true, true);
        if (svd.inputModified()) {
            A = A.copy();
        }
        if (!svd.decompose(A)) {
            throw new RuntimeException("SVD Failed!");
        }
        double[] sv = svd.getSingularValues();
        Arrays.sort(sv, 0, svd.numberOfSingularValues());
        for (int i = 0; i < sv.length / 2; ++i) {
            double tmp = sv[i];
            sv[i] = sv[sv.length - i - 1];
            sv[sv.length - i - 1] = tmp;
        }
        return sv;
    }

    public static double ratioSmallestOverLargest(double[] sv) {
        double min;
        if (sv.length == 0) {
            return Double.NaN;
        }
        double max = min = sv[0];
        for (int i = 1; i < sv.length; ++i) {
            double v = sv[i];
            if (v > max) {
                max = v;
                continue;
            }
            if (!(v < min)) continue;
            min = v;
        }
        return min / max;
    }

    public static int rank(DMatrixRMaj A, double threshold) {
        SingularValueDecomposition_F64<DMatrixRMaj> svd = DecompositionFactory_DDRM.svd(A.numRows, A.numCols, false, true, true);
        if (svd.inputModified()) {
            A = A.copy();
        }
        if (!svd.decompose(A)) {
            throw new RuntimeException("SVD Failed!");
        }
        double[] sv = svd.getSingularValues();
        int count = 0;
        for (int i = 0; i < sv.length; ++i) {
            if (!(sv[i] >= threshold)) continue;
            ++count;
        }
        return count;
    }

    public static int rank(DMatrixRMaj A) {
        SingularValueDecomposition_F64<DMatrixRMaj> svd = DecompositionFactory_DDRM.svd(A.numRows, A.numCols, false, true, true);
        if (svd.inputModified()) {
            A = A.copy();
        }
        if (!svd.decompose(A)) {
            throw new RuntimeException("SVD Failed!");
        }
        int N = svd.numberOfSingularValues();
        double[] sv = svd.getSingularValues();
        double threshold = SingularOps_DDRM.singularThreshold(sv, N);
        int count = 0;
        for (int i = 0; i < sv.length; ++i) {
            if (!(sv[i] >= threshold)) continue;
            ++count;
        }
        return count;
    }

    public static boolean svd(DMatrixRMaj A, @Nullable DMatrixRMaj U, DGrowArray sv, @Nullable DMatrixRMaj Vt) {
        boolean needV;
        boolean needU = U != null;
        SingularValueDecomposition_F64<DMatrixRMaj> svd = DecompositionFactory_DDRM.svd(A.numRows, A.numCols, needU, needV = Vt != null, true);
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
        SingularOps_DDRM.descendingOrder(U, false, sv.data, N, Vt, true);
        return true;
    }

    public static void descendingOrder(DMatrixRMaj U, boolean tranU, DMatrixRMaj W, DMatrixRMaj V, boolean tranV) {
        int numSingular = Math.min(W.numRows, W.numCols);
        SingularOps_DDRM.checkSvdMatrixSize(U, tranU, W, V, tranV);
        for (int i = 0; i < numSingular; ++i) {
            double bigValue = -1.0;
            int bigIndex = -1;
            for (int j = i; j < numSingular; ++j) {
                double v = W.get(j, j);
                if (!(v > bigValue)) continue;
                bigValue = v;
                bigIndex = j;
            }
            if (bigIndex == i) continue;
            if (bigIndex == -1) break;
            double tmp = W.get(i, i);
            W.set(i, i, bigValue);
            W.set(bigIndex, bigIndex, tmp);
            if (V != null) {
                SingularOps_DDRM.swapRowOrCol(V, tranV, i, bigIndex);
            }
            if (U == null) continue;
            SingularOps_DDRM.swapRowOrCol(U, tranU, i, bigIndex);
        }
    }

    public static void descendingOrder(@Nullable DMatrixRMaj U, boolean tranU, double[] singularValues, int singularLength, @Nullable DMatrixRMaj V, boolean tranV) {
        for (int i = 0; i < singularLength; ++i) {
            double bigValue = -1.0;
            int bigIndex = -1;
            for (int j = i; j < singularLength; ++j) {
                double v = singularValues[j];
                if (!(v > bigValue)) continue;
                bigValue = v;
                bigIndex = j;
            }
            if (bigIndex == i) continue;
            if (bigIndex == -1) break;
            double tmp = singularValues[i];
            singularValues[i] = bigValue;
            singularValues[bigIndex] = tmp;
            if (V != null) {
                SingularOps_DDRM.swapRowOrCol(V, tranV, i, bigIndex);
            }
            if (U == null) continue;
            SingularOps_DDRM.swapRowOrCol(U, tranU, i, bigIndex);
        }
    }

    public static void checkSvdMatrixSize(@Nullable DMatrixRMaj U, boolean tranU, DMatrixRMaj W, @Nullable DMatrixRMaj V, boolean tranV) {
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

    private static void swapRowOrCol(DMatrixRMaj M, boolean tran, int i, int bigIndex) {
        if (tran) {
            for (int col = 0; col < M.numCols; ++col) {
                double tmp = M.get(i, col);
                M.set(i, col, M.get(bigIndex, col));
                M.set(bigIndex, col, tmp);
            }
        } else {
            for (int row = 0; row < M.numRows; ++row) {
                double tmp = M.get(row, i);
                M.set(row, i, M.get(row, bigIndex));
                M.set(row, bigIndex, tmp);
            }
        }
    }

    public static DMatrixRMaj nullSpace(SingularValueDecomposition_F64<DMatrixRMaj> svd, @Nullable DMatrixRMaj nullSpace, double tol) {
        int i;
        int N = svd.numberOfSingularValues();
        double[] s = svd.getSingularValues();
        DMatrixRMaj V = svd.getV(null, true);
        if (V.numRows != svd.numCols()) {
            throw new IllegalArgumentException("Can't compute the null space using a compact SVD for a matrix of this size.");
        }
        int numVectors = svd.numCols() - N;
        for (int i2 = 0; i2 < N; ++i2) {
            if (!(s[i2] <= tol)) continue;
            ++numVectors;
        }
        if (nullSpace == null) {
            nullSpace = new DMatrixRMaj(numVectors, svd.numCols());
        } else {
            nullSpace.reshape(numVectors, svd.numCols());
        }
        int count = 0;
        for (i = 0; i < N; ++i) {
            if (!(s[i] <= tol)) continue;
            CommonOps_DDRM.extract(V, i, i + 1, 0, V.numCols, nullSpace, count++, 0);
        }
        for (i = N; i < svd.numCols(); ++i) {
            CommonOps_DDRM.extract(V, i, i + 1, 0, V.numCols, nullSpace, count++, 0);
        }
        CommonOps_DDRM.transpose(nullSpace);
        return nullSpace;
    }

    public static DMatrixRMaj nullspaceQR(DMatrixRMaj A, int totalSingular) {
        SolveNullSpaceQR_DDRM solver = new SolveNullSpaceQR_DDRM();
        DMatrixRMaj nullspace = new DMatrixRMaj(1, 1);
        if (!solver.process(A, totalSingular, nullspace)) {
            throw new RuntimeException("Solver failed. try SVD based method instead?");
        }
        return nullspace;
    }

    public static DMatrixRMaj nullspaceQRP(DMatrixRMaj A, int totalSingular) {
        SolveNullSpaceQRP_DDRM solver = new SolveNullSpaceQRP_DDRM();
        DMatrixRMaj nullspace = new DMatrixRMaj(1, 1);
        if (!solver.process(A, totalSingular, nullspace)) {
            throw new RuntimeException("Solver failed. try SVD based method instead?");
        }
        return nullspace;
    }

    public static DMatrixRMaj nullspaceSVD(DMatrixRMaj A, int totalSingular) {
        SolveNullSpaceSvd_DDRM solver = new SolveNullSpaceSvd_DDRM();
        DMatrixRMaj nullspace = new DMatrixRMaj(1, 1);
        if (!solver.process(A, totalSingular, nullspace)) {
            throw new RuntimeException("Solver failed. try SVD based method instead?");
        }
        return nullspace;
    }

    public static DMatrixRMaj nullVector(SingularValueDecomposition_F64<DMatrixRMaj> svd, boolean isRight, @Nullable DMatrixRMaj nullVector) {
        DMatrixRMaj A;
        int N = svd.numberOfSingularValues();
        double[] s = svd.getSingularValues();
        DMatrixRMaj dMatrixRMaj = A = isRight ? (DMatrixRMaj)svd.getV(null, true) : (DMatrixRMaj)svd.getU(null, false);
        if (isRight) {
            if (A.numRows != svd.numCols()) {
                throw new IllegalArgumentException("Can't compute the null space using a compact SVD for a matrix of this size.");
            }
            if (nullVector == null) {
                nullVector = new DMatrixRMaj(svd.numCols(), 1);
            } else {
                nullVector.reshape(svd.numCols(), 1);
            }
        } else {
            if (A.numCols != svd.numRows()) {
                throw new IllegalArgumentException("Can't compute the null space using a compact SVD for a matrix of this size.");
            }
            if (nullVector == null) {
                nullVector = new DMatrixRMaj(svd.numRows(), 1);
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
            double smallestValue = Double.MAX_VALUE;
            for (int i = 0; i < N; ++i) {
                if (!(s[i] < smallestValue)) continue;
                smallestValue = s[i];
                smallestIndex = i;
            }
        }
        if (isRight) {
            SpecializedOps_DDRM.subvector(A, smallestIndex, 0, A.numRows, true, 0, nullVector);
        } else {
            SpecializedOps_DDRM.subvector(A, 0, smallestIndex, A.numRows, false, 0, nullVector);
        }
        return nullVector;
    }

    public static double singularThreshold(SingularValueDecomposition_F64<?> svd) {
        return SingularOps_DDRM.singularThreshold(svd, UtilEjml.EPS);
    }

    public static double singularThreshold(SingularValueDecomposition_F64<?> svd, double tolerance) {
        double[] w = svd.getSingularValues();
        int N = svd.numberOfSingularValues();
        return SingularOps_DDRM.singularThreshold(w, N, tolerance);
    }

    private static double singularThreshold(double[] w, int N) {
        return SingularOps_DDRM.singularThreshold(w, N, UtilEjml.EPS);
    }

    private static double singularThreshold(double[] w, int N, double tolerance) {
        double largest = 0.0;
        for (int j = 0; j < N; ++j) {
            if (!(w[j] > largest)) continue;
            largest = w[j];
        }
        return (double)N * largest * tolerance;
    }

    public static int rank(SingularValueDecomposition_F64<?> svd) {
        double threshold = SingularOps_DDRM.singularThreshold(svd);
        return SingularOps_DDRM.rank(svd, threshold);
    }

    public static int rank(SingularValueDecomposition_F64<?> svd, double threshold) {
        int numRank = 0;
        double[] w = svd.getSingularValues();
        int N = svd.numberOfSingularValues();
        for (int j = 0; j < N; ++j) {
            if (!(w[j] > threshold)) continue;
            ++numRank;
        }
        return numRank;
    }

    public static int nullity(SingularValueDecomposition_F64<?> svd) {
        double threshold = SingularOps_DDRM.singularThreshold(svd);
        return SingularOps_DDRM.nullity(svd, threshold);
    }

    public static int nullity(SingularValueDecomposition_F64<?> svd, double threshold) {
        int ret = 0;
        double[] w = svd.getSingularValues();
        int N = svd.numberOfSingularValues();
        int numCol = svd.numCols();
        for (int j = 0; j < N; ++j) {
            if (!(w[j] <= threshold)) continue;
            ++ret;
        }
        return ret + numCol - N;
    }

    public static int nullity(DMatrixRMaj A, double threshold) {
        SingularValueDecomposition_F64<DMatrixRMaj> svd = DecompositionFactory_DDRM.svd(A.numRows, A.numCols, false, true, true);
        if (svd.inputModified()) {
            A = A.copy();
        }
        if (!svd.decompose(A)) {
            throw new RuntimeException("SVD Failed!");
        }
        double[] sv = svd.getSingularValues();
        int count = 0;
        for (int i = 0; i < sv.length; ++i) {
            if (!(sv[i] <= threshold)) continue;
            ++count;
        }
        return count;
    }
}

