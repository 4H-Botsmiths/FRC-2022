/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row;

import org.ejml.data.FMatrix1Row;
import org.ejml.data.FMatrixD1;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.MatrixFeatures_FDRM;
import org.ejml.dense.row.NormOps_FDRM;
import org.jetbrains.annotations.Nullable;

public class SpecializedOps_FDRM {
    public static FMatrixRMaj createReflector(FMatrix1Row u) {
        if (!MatrixFeatures_FDRM.isVector(u)) {
            throw new IllegalArgumentException("u must be a vector");
        }
        float norm = NormOps_FDRM.fastNormF(u);
        float gamma = -2.0f / (norm * norm);
        FMatrixRMaj Q = CommonOps_FDRM.identity(u.getNumElements());
        CommonOps_FDRM.multAddTransB(gamma, u, u, Q);
        return Q;
    }

    public static FMatrixRMaj createReflector(FMatrixRMaj u, float gamma) {
        if (!MatrixFeatures_FDRM.isVector(u)) {
            throw new IllegalArgumentException("u must be a vector");
        }
        FMatrixRMaj Q = CommonOps_FDRM.identity(u.getNumElements());
        CommonOps_FDRM.multAddTransB(-gamma, u, u, Q);
        return Q;
    }

    public static FMatrixRMaj copyChangeRow(int[] order, FMatrixRMaj src, @Nullable FMatrixRMaj dst) {
        if (dst == null) {
            dst = new FMatrixRMaj(src.numRows, src.numCols);
        } else if (src.numRows != dst.numRows || src.numCols != dst.numCols) {
            throw new IllegalArgumentException("src and dst must have the same dimensions.");
        }
        for (int i = 0; i < src.numRows; ++i) {
            int indexDst = i * src.numCols;
            int indexSrc = order[i] * src.numCols;
            System.arraycopy(src.data, indexSrc, dst.data, indexDst, src.numCols);
        }
        return dst;
    }

    public static FMatrixRMaj copyTriangle(FMatrixRMaj src, @Nullable FMatrixRMaj dst, boolean upper) {
        if (dst == null) {
            dst = new FMatrixRMaj(src.numRows, src.numCols);
        } else if (src.numRows != dst.numRows || src.numCols != dst.numCols) {
            throw new IllegalArgumentException("src and dst must have the same dimensions.");
        }
        if (upper) {
            int N = Math.min(src.numRows, src.numCols);
            for (int i = 0; i < N; ++i) {
                int index = i * src.numCols + i;
                System.arraycopy(src.data, index, dst.data, index, src.numCols - i);
            }
        } else {
            for (int i = 0; i < src.numRows; ++i) {
                int length = Math.min(i + 1, src.numCols);
                int index = i * src.numCols;
                System.arraycopy(src.data, index, dst.data, index, length);
            }
        }
        return dst;
    }

    public static void multLowerTranB(FMatrixRMaj mat) {
        int j;
        int i;
        int m = mat.numCols;
        float[] L = mat.data;
        for (i = 0; i < m; ++i) {
            for (j = m - 1; j >= i; --j) {
                float val = 0.0f;
                for (int k = 0; k <= i; ++k) {
                    val += L[i * m + k] * L[j * m + k];
                }
                L[i * m + j] = val;
            }
        }
        for (i = 0; i < m; ++i) {
            for (j = 0; j < i; ++j) {
                L[i * m + j] = L[j * m + i];
            }
        }
    }

    public static void multLowerTranA(FMatrixRMaj mat) {
        int j;
        int i;
        int m = mat.numCols;
        float[] L = mat.data;
        for (i = 0; i < m; ++i) {
            for (j = m - 1; j >= i; --j) {
                float val = 0.0f;
                for (int k = j; k < m; ++k) {
                    val += L[k * m + i] * L[k * m + j];
                }
                L[i * m + j] = val;
            }
        }
        for (i = 0; i < m; ++i) {
            for (j = 0; j < i; ++j) {
                L[i * m + j] = L[j * m + i];
            }
        }
    }

    public static float diffNormF(FMatrixD1 a, FMatrixD1 b) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            throw new IllegalArgumentException("Both matrices must have the same shape.");
        }
        int size = a.getNumElements();
        FMatrixRMaj diff = new FMatrixRMaj(size, 1);
        for (int i = 0; i < size; ++i) {
            diff.set(i, b.get(i) - a.get(i));
        }
        return NormOps_FDRM.normF(diff);
    }

    public static float diffNormF_fast(FMatrixD1 a, FMatrixD1 b) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            throw new IllegalArgumentException("Both matrices must have the same shape.");
        }
        int size = a.getNumElements();
        float total = 0.0f;
        for (int i = 0; i < size; ++i) {
            float diff = b.get(i) - a.get(i);
            total += diff * diff;
        }
        return (float)Math.sqrt(total);
    }

    public static float diffNormP1(FMatrixD1 a, FMatrixD1 b) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            throw new IllegalArgumentException("Both matrices must have the same shape.");
        }
        int size = a.getNumElements();
        float total = 0.0f;
        for (int i = 0; i < size; ++i) {
            total += Math.abs(b.get(i) - a.get(i));
        }
        return total;
    }

    public static void addIdentity(FMatrix1Row A, FMatrix1Row B, float alpha) {
        if (A.numCols != A.numRows) {
            throw new IllegalArgumentException("A must be square");
        }
        if (B.numCols != A.numCols || B.numRows != A.numRows) {
            throw new IllegalArgumentException("B must be the same shape as A");
        }
        int n = A.numCols;
        int index = 0;
        for (int i = 0; i < n; ++i) {
            int j = 0;
            while (j < n) {
                if (i == j) {
                    B.set(index, A.get(index) + alpha);
                } else {
                    B.set(index, A.get(index));
                }
                ++j;
                ++index;
            }
        }
    }

    public static void subvector(FMatrix1Row A, int rowA, int colA, int length, boolean row, int offsetV, FMatrix1Row v) {
        if (row) {
            for (int i = 0; i < length; ++i) {
                v.set(offsetV + i, A.get(rowA, colA + i));
            }
        } else {
            for (int i = 0; i < length; ++i) {
                v.set(offsetV + i, A.get(rowA + i, colA));
            }
        }
    }

    public static FMatrixRMaj[] splitIntoVectors(FMatrix1Row A, boolean column) {
        int w = column ? A.numCols : A.numRows;
        int M = column ? A.numRows : 1;
        int N = column ? 1 : A.numCols;
        int o = Math.max(M, N);
        FMatrixRMaj[] ret = new FMatrixRMaj[w];
        for (int i = 0; i < w; ++i) {
            FMatrixRMaj a = new FMatrixRMaj(M, N);
            if (column) {
                SpecializedOps_FDRM.subvector(A, 0, i, o, false, 0, a);
            } else {
                SpecializedOps_FDRM.subvector(A, i, 0, o, true, 0, a);
            }
            ret[i] = a;
        }
        return ret;
    }

    public static FMatrixRMaj pivotMatrix(@Nullable FMatrixRMaj ret, int[] pivots, int numPivots, boolean transposed) {
        if (ret == null) {
            ret = new FMatrixRMaj(numPivots, numPivots);
        } else {
            if (ret.numCols != numPivots || ret.numRows != numPivots) {
                throw new IllegalArgumentException("Unexpected matrix dimension");
            }
            CommonOps_FDRM.fill(ret, 0.0f);
        }
        if (transposed) {
            for (int i = 0; i < numPivots; ++i) {
                ret.set(pivots[i], i, 1.0f);
            }
        } else {
            for (int i = 0; i < numPivots; ++i) {
                ret.set(i, pivots[i], 1.0f);
            }
        }
        return ret;
    }

    public static float diagProd(FMatrix1Row T) {
        float prod = 1.0f;
        int N = Math.min(T.numRows, T.numCols);
        for (int i = 0; i < N; ++i) {
            prod *= T.unsafe_get(i, i);
        }
        return prod;
    }

    public static float elementDiagonalMaxAbs(FMatrixD1 a) {
        int size = Math.min(a.numRows, a.numCols);
        float max = 0.0f;
        for (int i = 0; i < size; ++i) {
            float val = Math.abs(a.get(i, i));
            if (!(val > max)) continue;
            max = val;
        }
        return max;
    }

    public static float qualityTriangular(FMatrixD1 T) {
        int N = Math.min(T.numRows, T.numCols);
        float max = SpecializedOps_FDRM.elementDiagonalMaxAbs(T);
        if (max == 0.0f) {
            return 0.0f;
        }
        float quality = 1.0f;
        for (int i = 0; i < N; ++i) {
            quality *= T.unsafe_get(i, i) / max;
        }
        return Math.abs(quality);
    }

    public static float elementSumSq(FMatrixD1 m) {
        float maxAbs = CommonOps_FDRM.elementMaxAbs(m);
        if (maxAbs == 0.0f) {
            return 0.0f;
        }
        float total = 0.0f;
        int N = m.getNumElements();
        for (int i = 0; i < N; ++i) {
            float d = m.data[i] / maxAbs;
            total += d * d;
        }
        return maxAbs * total * maxAbs;
    }
}

