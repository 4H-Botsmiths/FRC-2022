/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.mult;

import java.util.Arrays;
import org.ejml.UtilEjml;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.ops.DOperatorBinary;
import org.jetbrains.annotations.Nullable;

public class ImplMultiplication_DSCC {
    public static void mult(DMatrixSparseCSC A, DMatrixSparseCSC B, DMatrixSparseCSC C, @Nullable IGrowArray gw, @Nullable DGrowArray gx) {
        double[] x = UtilEjml.adjust(gx, A.numRows);
        int[] w = UtilEjml.adjust(gw, A.numRows, A.numRows);
        C.growMaxLength(A.nz_length + B.nz_length, false);
        C.indicesSorted = false;
        C.nz_length = 0;
        int idx0 = B.col_idx[0];
        for (int bj = 1; bj <= B.numCols; ++bj) {
            int colB = bj - 1;
            int idx1 = B.col_idx[bj];
            C.col_idx[bj] = C.nz_length;
            if (idx0 == idx1) continue;
            for (int bi = idx0; bi < idx1; ++bi) {
                int rowB = B.nz_rows[bi];
                double valB = B.nz_values[bi];
                ImplMultiplication_DSCC.multAddColA(A, rowB, valB, C, colB + 1, x, w);
            }
            int idxC0 = C.col_idx[colB];
            int idxC1 = C.col_idx[colB + 1];
            for (int i = idxC0; i < idxC1; ++i) {
                C.nz_values[i] = x[C.nz_rows[i]];
            }
            idx0 = idx1;
        }
    }

    public static void multAddColA(DMatrixSparseCSC A, int colA, double alpha, DMatrixSparseCSC C, int mark, double[] x, int[] w) {
        int idxA0 = A.col_idx[colA];
        int idxA1 = A.col_idx[colA + 1];
        for (int j = idxA0; j < idxA1; ++j) {
            int row = A.nz_rows[j];
            if (w[row] < mark) {
                if (C.nz_length >= C.nz_rows.length) {
                    C.growMaxLength(C.nz_length * 2 + 1, true);
                }
                w[row] = mark;
                C.nz_rows[C.nz_length] = row;
                C.col_idx[mark] = ++C.nz_length;
                x[row] = A.nz_values[j] * alpha;
                continue;
            }
            int n = row;
            x[n] = x[n] + A.nz_values[j] * alpha;
        }
    }

    public static void addRowsInAInToC(DMatrixSparseCSC A, int colA, DMatrixSparseCSC C, int colC, int[] w) {
        int idxA0 = A.col_idx[colA];
        int idxA1 = A.col_idx[colA + 1];
        for (int j = idxA0; j < idxA1; ++j) {
            int row = A.nz_rows[j];
            if (w[row] >= colC) continue;
            if (C.nz_length >= C.nz_rows.length) {
                C.growMaxLength(C.nz_length * 2 + 1, true);
            }
            w[row] = colC;
            C.nz_rows[C.nz_length++] = row;
        }
        C.col_idx[colC + 1] = C.nz_length;
    }

    public static void mult(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj C) {
        C.zero();
        ImplMultiplication_DSCC.multAdd(A, B, C);
    }

    public static void multAdd(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj C) {
        for (int k = 0; k < A.numCols; ++k) {
            int idx0 = A.col_idx[k];
            int idx1 = A.col_idx[k + 1];
            for (int indexA = idx0; indexA < idx1; ++indexA) {
                int i = A.nz_rows[indexA];
                double valueA = A.nz_values[indexA];
                int indexB = k * B.numCols;
                int indexC = i * C.numCols;
                int end = indexB + B.numCols;
                while (indexB < end) {
                    int n = indexC++;
                    C.data[n] = C.data[n] + valueA * B.data[indexB++];
                }
            }
        }
    }

    public static void multTransA(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj C, DGrowArray workArray) {
        ImplMultiplication_DSCC.multTransA(A, B, C, workArray, (a, b) -> b);
    }

    public static void multAddTransA(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj C, DGrowArray workArray) {
        ImplMultiplication_DSCC.multTransA(A, B, C, workArray, Double::sum);
    }

    public static void multTransA(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj C, DGrowArray workArray, DOperatorBinary op) {
        double[] work = workArray.reshape((int)B.numRows).data;
        for (int j = 0; j < B.numCols; ++j) {
            for (int k = 0; k < B.numRows; ++k) {
                work[k] = B.data[k * B.numCols + j];
            }
            for (int i = 0; i < A.numCols; ++i) {
                int idx0 = A.col_idx[i];
                int idx1 = A.col_idx[i + 1];
                double sum = 0.0;
                for (int indexA = idx0; indexA < idx1; ++indexA) {
                    int k = A.nz_rows[indexA];
                    sum += A.nz_values[indexA] * work[k];
                }
                C.data[i * C.numCols + j] = op.apply(C.data[i * C.numCols + j], sum);
            }
        }
    }

    public static void multTransB(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj C, DGrowArray workArray) {
        C.zero();
        ImplMultiplication_DSCC.multAddTransB(A, B, C, workArray);
    }

    public static void multAddTransB(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj C, DGrowArray workArray) {
        double[] work = workArray.reshape((int)B.numRows).data;
        for (int k = 0; k < A.numCols; ++k) {
            for (int j = 0; j < B.numRows; ++j) {
                work[j] = B.data[j * B.numCols + k];
            }
            int idx0 = A.col_idx[k];
            int idx1 = A.col_idx[k + 1];
            for (int indexA = idx0; indexA < idx1; ++indexA) {
                for (int j = 0; j < B.numRows; ++j) {
                    int i = A.nz_rows[indexA];
                    int n = i * C.numCols + j;
                    C.data[n] = C.data[n] + A.nz_values[indexA] * work[j];
                }
            }
        }
    }

    public static void multTransAB(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj C) {
        for (int j = 0; j < B.numRows; ++j) {
            for (int i = 0; i < A.numCols; ++i) {
                int idx0 = A.col_idx[i];
                int idx1 = A.col_idx[i + 1];
                int indexRowB = j * B.numCols;
                double sum = 0.0;
                for (int indexA = idx0; indexA < idx1; ++indexA) {
                    int k = A.nz_rows[indexA];
                    sum += A.nz_values[indexA] * B.data[indexRowB + k];
                }
                C.data[i * C.numCols + j] = sum;
            }
        }
    }

    public static void multAddTransAB(DMatrixSparseCSC A, DMatrixRMaj B, DMatrixRMaj C) {
        for (int j = 0; j < B.numRows; ++j) {
            for (int i = 0; i < A.numCols; ++i) {
                int idx0 = A.col_idx[i];
                int idx1 = A.col_idx[i + 1];
                int indexRowB = j * B.numCols;
                double sum = 0.0;
                for (int indexA = idx0; indexA < idx1; ++indexA) {
                    int k = A.nz_rows[indexA];
                    sum += A.nz_values[indexA] * B.data[indexRowB + k];
                }
                int n = i * C.numCols + j;
                C.data[n] = C.data[n] + sum;
            }
        }
    }

    public static double dotInnerColumns(DMatrixSparseCSC A, int colA, DMatrixSparseCSC B, int colB, @Nullable IGrowArray gw, @Nullable DGrowArray gx) {
        if (A.numRows != B.numRows) {
            throw new IllegalArgumentException("Number of rows must match.");
        }
        int[] w = UtilEjml.adjust(gw, A.numRows);
        Arrays.fill(w, 0, A.numRows, -1);
        double[] x = UtilEjml.adjust(gx, A.numRows);
        int length = 0;
        int idx0 = A.col_idx[colA];
        int idx1 = A.col_idx[colA + 1];
        for (int i = idx0; i < idx1; ++i) {
            int row = A.nz_rows[i];
            x[length] = A.nz_values[i];
            w[row] = length++;
        }
        double dot = 0.0;
        idx0 = B.col_idx[colB];
        idx1 = B.col_idx[colB + 1];
        for (int i = idx0; i < idx1; ++i) {
            int row = B.nz_rows[i];
            if (w[row] == -1) continue;
            dot += x[w[row]] * B.nz_values[i];
        }
        return dot;
    }
}

