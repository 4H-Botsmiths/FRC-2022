/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row;

import org.ejml.data.DMatrix1Row;
import org.ejml.data.DMatrixD1;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.MatrixFeatures_DDRM;
import org.ejml.dense.row.NormOps_DDRM;
import org.jetbrains.annotations.Nullable;

public class SpecializedOps_DDRM {
    public static DMatrixRMaj createReflector(DMatrix1Row u) {
        if (!MatrixFeatures_DDRM.isVector(u)) {
            throw new IllegalArgumentException("u must be a vector");
        }
        double norm = NormOps_DDRM.fastNormF(u);
        double gamma = -2.0 / (norm * norm);
        DMatrixRMaj Q = CommonOps_DDRM.identity(u.getNumElements());
        CommonOps_DDRM.multAddTransB(gamma, u, u, Q);
        return Q;
    }

    public static DMatrixRMaj createReflector(DMatrixRMaj u, double gamma) {
        if (!MatrixFeatures_DDRM.isVector(u)) {
            throw new IllegalArgumentException("u must be a vector");
        }
        DMatrixRMaj Q = CommonOps_DDRM.identity(u.getNumElements());
        CommonOps_DDRM.multAddTransB(-gamma, u, u, Q);
        return Q;
    }

    public static DMatrixRMaj copyChangeRow(int[] order, DMatrixRMaj src, @Nullable DMatrixRMaj dst) {
        if (dst == null) {
            dst = new DMatrixRMaj(src.numRows, src.numCols);
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

    public static DMatrixRMaj copyTriangle(DMatrixRMaj src, @Nullable DMatrixRMaj dst, boolean upper) {
        if (dst == null) {
            dst = new DMatrixRMaj(src.numRows, src.numCols);
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

    public static void multLowerTranB(DMatrixRMaj mat) {
        int j;
        int i;
        int m = mat.numCols;
        double[] L = mat.data;
        for (i = 0; i < m; ++i) {
            for (j = m - 1; j >= i; --j) {
                double val = 0.0;
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

    public static void multLowerTranA(DMatrixRMaj mat) {
        int j;
        int i;
        int m = mat.numCols;
        double[] L = mat.data;
        for (i = 0; i < m; ++i) {
            for (j = m - 1; j >= i; --j) {
                double val = 0.0;
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

    public static double diffNormF(DMatrixD1 a, DMatrixD1 b) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            throw new IllegalArgumentException("Both matrices must have the same shape.");
        }
        int size = a.getNumElements();
        DMatrixRMaj diff = new DMatrixRMaj(size, 1);
        for (int i = 0; i < size; ++i) {
            diff.set(i, b.get(i) - a.get(i));
        }
        return NormOps_DDRM.normF(diff);
    }

    public static double diffNormF_fast(DMatrixD1 a, DMatrixD1 b) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            throw new IllegalArgumentException("Both matrices must have the same shape.");
        }
        int size = a.getNumElements();
        double total = 0.0;
        for (int i = 0; i < size; ++i) {
            double diff = b.get(i) - a.get(i);
            total += diff * diff;
        }
        return Math.sqrt(total);
    }

    public static double diffNormP1(DMatrixD1 a, DMatrixD1 b) {
        if (a.numRows != b.numRows || a.numCols != b.numCols) {
            throw new IllegalArgumentException("Both matrices must have the same shape.");
        }
        int size = a.getNumElements();
        double total = 0.0;
        for (int i = 0; i < size; ++i) {
            total += Math.abs(b.get(i) - a.get(i));
        }
        return total;
    }

    public static void addIdentity(DMatrix1Row A, DMatrix1Row B, double alpha) {
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

    public static void subvector(DMatrix1Row A, int rowA, int colA, int length, boolean row, int offsetV, DMatrix1Row v) {
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

    public static DMatrixRMaj[] splitIntoVectors(DMatrix1Row A, boolean column) {
        int w = column ? A.numCols : A.numRows;
        int M = column ? A.numRows : 1;
        int N = column ? 1 : A.numCols;
        int o = Math.max(M, N);
        DMatrixRMaj[] ret = new DMatrixRMaj[w];
        for (int i = 0; i < w; ++i) {
            DMatrixRMaj a = new DMatrixRMaj(M, N);
            if (column) {
                SpecializedOps_DDRM.subvector(A, 0, i, o, false, 0, a);
            } else {
                SpecializedOps_DDRM.subvector(A, i, 0, o, true, 0, a);
            }
            ret[i] = a;
        }
        return ret;
    }

    public static DMatrixRMaj pivotMatrix(@Nullable DMatrixRMaj ret, int[] pivots, int numPivots, boolean transposed) {
        if (ret == null) {
            ret = new DMatrixRMaj(numPivots, numPivots);
        } else {
            if (ret.numCols != numPivots || ret.numRows != numPivots) {
                throw new IllegalArgumentException("Unexpected matrix dimension");
            }
            CommonOps_DDRM.fill(ret, 0.0);
        }
        if (transposed) {
            for (int i = 0; i < numPivots; ++i) {
                ret.set(pivots[i], i, 1.0);
            }
        } else {
            for (int i = 0; i < numPivots; ++i) {
                ret.set(i, pivots[i], 1.0);
            }
        }
        return ret;
    }

    public static double diagProd(DMatrix1Row T) {
        double prod = 1.0;
        int N = Math.min(T.numRows, T.numCols);
        for (int i = 0; i < N; ++i) {
            prod *= T.unsafe_get(i, i);
        }
        return prod;
    }

    public static double elementDiagonalMaxAbs(DMatrixD1 a) {
        int size = Math.min(a.numRows, a.numCols);
        double max = 0.0;
        for (int i = 0; i < size; ++i) {
            double val = Math.abs(a.get(i, i));
            if (!(val > max)) continue;
            max = val;
        }
        return max;
    }

    public static double qualityTriangular(DMatrixD1 T) {
        int N = Math.min(T.numRows, T.numCols);
        double max = SpecializedOps_DDRM.elementDiagonalMaxAbs(T);
        if (max == 0.0) {
            return 0.0;
        }
        double quality = 1.0;
        for (int i = 0; i < N; ++i) {
            quality *= T.unsafe_get(i, i) / max;
        }
        return Math.abs(quality);
    }

    public static double elementSumSq(DMatrixD1 m) {
        double maxAbs = CommonOps_DDRM.elementMaxAbs(m);
        if (maxAbs == 0.0) {
            return 0.0;
        }
        double total = 0.0;
        int N = m.getNumElements();
        for (int i = 0; i < N; ++i) {
            double d = m.data[i] / maxAbs;
            total += d * d;
        }
        return maxAbs * total * maxAbs;
    }
}

