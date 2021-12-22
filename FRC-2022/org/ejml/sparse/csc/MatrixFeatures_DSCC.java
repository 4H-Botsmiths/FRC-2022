/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc;

import org.ejml.UtilEjml;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.sparse.csc.CommonOps_DSCC;
import org.ejml.sparse.csc.decomposition.chol.CholeskyUpLooking_DSCC;

public class MatrixFeatures_DSCC {
    public static boolean isEquals(DMatrixSparseCSC a, DMatrixSparseCSC b) {
        if (!a.indicesSorted || !b.indicesSorted) {
            throw new IllegalArgumentException("Inputs must have sorted indices");
        }
        if (!MatrixFeatures_DSCC.isSameStructure(a, b)) {
            return false;
        }
        for (int i = 0; i < a.nz_length; ++i) {
            if (a.nz_values[i] == b.nz_values[i]) continue;
            return false;
        }
        return true;
    }

    public static boolean isEquals(DMatrixSparseCSC a, DMatrixSparseCSC b, double tol) {
        if (!a.indicesSorted || !b.indicesSorted) {
            throw new IllegalArgumentException("Inputs must have sorted indices");
        }
        if (!MatrixFeatures_DSCC.isSameStructure(a, b)) {
            return false;
        }
        for (int i = 0; i < a.nz_length; ++i) {
            if (!(Math.abs(a.nz_values[i] - b.nz_values[i]) > tol)) continue;
            return false;
        }
        return true;
    }

    public static boolean isEqualsSort(DMatrixSparseCSC a, DMatrixSparseCSC b, double tol) {
        if (!a.indicesSorted) {
            a.sortIndices(null);
        }
        if (!b.indicesSorted) {
            b.sortIndices(null);
        }
        if (!MatrixFeatures_DSCC.isSameStructure(a, b)) {
            return false;
        }
        for (int i = 0; i < a.nz_length; ++i) {
            if (!(Math.abs(a.nz_values[i] - b.nz_values[i]) > tol)) continue;
            return false;
        }
        return true;
    }

    public static boolean isIdenticalSort(DMatrixSparseCSC a, DMatrixSparseCSC b, double tol) {
        if (!a.indicesSorted) {
            a.sortIndices(null);
        }
        if (!b.indicesSorted) {
            b.sortIndices(null);
        }
        if (!MatrixFeatures_DSCC.isSameStructure(a, b)) {
            return false;
        }
        for (int i = 0; i < a.nz_length; ++i) {
            if (UtilEjml.isIdentical(a.nz_values[i], b.nz_values[i], tol)) continue;
            return false;
        }
        return true;
    }

    public static boolean isSameStructure(DMatrixSparseCSC a, DMatrixSparseCSC b) {
        if (a.numRows == b.numRows && a.numCols == b.numCols && a.nz_length == b.nz_length) {
            int i;
            for (i = 0; i <= a.numCols; ++i) {
                if (a.col_idx[i] == b.col_idx[i]) continue;
                return false;
            }
            for (i = 0; i < a.nz_length; ++i) {
                if (a.nz_rows[i] == b.nz_rows[i]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean hasUncountable(DMatrixSparseCSC A) {
        for (int i = 0; i < A.nz_length; ++i) {
            if (!UtilEjml.isUncountable(A.nz_values[i])) continue;
            return true;
        }
        return false;
    }

    public static boolean isZeros(DMatrixSparseCSC A, double tol) {
        for (int i = 0; i < A.nz_length; ++i) {
            if (!(Math.abs(A.nz_values[i]) > tol)) continue;
            return false;
        }
        return true;
    }

    public static boolean isIdentity(DMatrixSparseCSC A, double tol) {
        if (A.numCols != A.numRows) {
            return false;
        }
        if (A.nz_length != A.numCols) {
            return false;
        }
        for (int i = 1; i <= A.numCols; ++i) {
            if (A.col_idx[i] != i) {
                return false;
            }
            if (!(Math.abs(A.nz_values[i - 1] - 1.0) > tol)) continue;
            return false;
        }
        return true;
    }

    public static boolean isLowerTriangle(DMatrixSparseCSC A, int hessenberg, double tol) {
        if (A.numCols != A.numRows) {
            return false;
        }
        if (A.nz_length < A.numCols - hessenberg) {
            return false;
        }
        for (int col = 0; col < A.numCols; ++col) {
            int idx0 = A.col_idx[col];
            int idx1 = A.col_idx[col + 1];
            if (col >= hessenberg) {
                if (idx0 == idx1) {
                    return false;
                }
                if (A.nz_rows[idx0] != Math.max(0, col - hessenberg)) {
                    return false;
                }
            }
            if (col - hessenberg < 0 || !(Math.abs(A.nz_values[idx0]) <= tol)) continue;
            return false;
        }
        return true;
    }

    public static boolean isTranspose(DMatrixSparseCSC A, DMatrixSparseCSC B, double tol) {
        if (A.numCols != B.numRows || A.numRows != B.numCols) {
            return false;
        }
        if (A.nz_length != B.nz_length) {
            return false;
        }
        if (!A.indicesSorted) {
            throw new IllegalArgumentException("A must have sorted indicies");
        }
        DMatrixSparseCSC Btran = new DMatrixSparseCSC(B.numCols, B.numRows, B.nz_length);
        CommonOps_DSCC.transpose(B, Btran, null);
        Btran.sortIndices(null);
        for (int i = 0; i < B.nz_length; ++i) {
            if (A.nz_rows[i] != Btran.nz_rows[i]) {
                return false;
            }
            if (!(Math.abs(A.nz_values[i] - Btran.nz_values[i]) > tol)) continue;
            return false;
        }
        return true;
    }

    public static boolean isVector(DMatrixSparseCSC a) {
        return a.numCols == 1 && a.numRows > 1 || a.numRows == 1 && a.numCols > 1;
    }

    public static boolean isSymmetric(DMatrixSparseCSC A, double tol) {
        if (A.numRows != A.numCols) {
            return false;
        }
        int N = A.numCols;
        for (int i = 0; i < N; ++i) {
            int idx0 = A.col_idx[i];
            int idx1 = A.col_idx[i + 1];
            for (int index = idx0; index < idx1; ++index) {
                int j = A.nz_rows[index];
                double value_ji = A.nz_values[index];
                double value_ij = A.get(i, j);
                if (!(Math.abs(value_ij - value_ji) > tol)) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isPositiveDefinite(DMatrixSparseCSC A) {
        if (A.numRows != A.numCols) {
            return false;
        }
        CholeskyUpLooking_DSCC chol = new CholeskyUpLooking_DSCC();
        return chol.decompose(A);
    }

    public static boolean isOrthogonal(DMatrixSparseCSC Q, double tol) {
        if (Q.numRows < Q.numCols) {
            throw new IllegalArgumentException("The number of rows must be more than or equal to the number of columns");
        }
        IGrowArray gw = new IGrowArray();
        DGrowArray gx = new DGrowArray();
        for (int i = 0; i < Q.numRows; ++i) {
            for (int j = i + 1; j < Q.numCols; ++j) {
                double val = CommonOps_DSCC.dotInnerColumns(Q, i, Q, j, gw, gx);
                if (Math.abs(val) <= tol) continue;
                return false;
            }
        }
        return true;
    }
}

