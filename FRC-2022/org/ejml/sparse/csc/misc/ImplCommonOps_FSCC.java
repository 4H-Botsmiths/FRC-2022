/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.misc;

import java.util.Arrays;
import org.ejml.UtilEjml;
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.ops.IPredicateBinary;
import org.ejml.sparse.csc.mult.ImplMultiplication_FSCC;
import org.jetbrains.annotations.Nullable;

public class ImplCommonOps_FSCC {
    public static void select(FMatrixSparseCSC A, FMatrixSparseCSC output, IPredicateBinary selector) {
        int selectCount = 0;
        if (output != A) {
            output.growMaxLength(A.nz_length / 2, false);
        }
        output.indicesSorted = A.indicesSorted;
        for (int col = 0; col < A.numCols; ++col) {
            int start = A.col_idx[col];
            int end = A.col_idx[col + 1];
            output.col_idx[col] = selectCount;
            if (output.nz_rows.length < selectCount + (end - start)) {
                int maxLength = Integer.max(output.nz_length * 2 + 1, A.nz_length);
                output.growMaxLength(maxLength, true);
            }
            for (int i = start; i < end; ++i) {
                int row = A.nz_rows[i];
                if (!selector.apply(row, col)) continue;
                output.nz_rows[selectCount] = row;
                output.nz_values[selectCount] = A.nz_values[i];
                ++selectCount;
            }
        }
        output.col_idx[output.numCols] = selectCount;
        output.nz_length = selectCount;
    }

    public static void transpose(FMatrixSparseCSC A, FMatrixSparseCSC C, @Nullable IGrowArray gw) {
        int[] work = UtilEjml.adjust(gw, A.numRows, A.numRows);
        C.reshape(A.numCols, A.numRows, A.nz_length);
        for (int j = 0; j < A.nz_length; ++j) {
            int n = A.nz_rows[j];
            work[n] = work[n] + 1;
        }
        C.histogramToStructure(work);
        System.arraycopy(C.col_idx, 0, work, 0, C.numCols);
        int idx0 = A.col_idx[0];
        for (int j = 1; j <= A.numCols; ++j) {
            int col = j - 1;
            int idx1 = A.col_idx[j];
            for (int i = idx0; i < idx1; ++i) {
                int row;
                int n = row = A.nz_rows[i];
                work[n] = work[n] + 1;
                C.nz_rows[index] = col;
                C.nz_values[index] = A.nz_values[i];
            }
            idx0 = idx1;
        }
    }

    public static void add(float alpha, FMatrixSparseCSC A, float beta, FMatrixSparseCSC B, FMatrixSparseCSC C, @Nullable IGrowArray gw, @Nullable FGrowArray gx) {
        float[] x = UtilEjml.adjust(gx, A.numRows);
        int[] w = UtilEjml.adjust(gw, A.numRows, A.numRows);
        C.indicesSorted = false;
        C.nz_length = 0;
        for (int col = 0; col < A.numCols; ++col) {
            C.col_idx[col] = C.nz_length;
            ImplMultiplication_FSCC.multAddColA(A, col, alpha, C, col + 1, x, w);
            ImplMultiplication_FSCC.multAddColA(B, col, beta, C, col + 1, x, w);
            int idxC0 = C.col_idx[col];
            int idxC1 = C.col_idx[col + 1];
            for (int i = idxC0; i < idxC1; ++i) {
                C.nz_values[i] = x[C.nz_rows[i]];
            }
        }
        C.col_idx[A.numCols] = C.nz_length;
    }

    public static void addColAppend(float alpha, FMatrixSparseCSC A, int colA, float beta, FMatrixSparseCSC B, int colB, FMatrixSparseCSC C, @Nullable IGrowArray gw) {
        int row;
        int i;
        if (A.numRows != B.numRows || A.numRows != C.numRows) {
            throw new IllegalArgumentException("Number of rows in A, B, and C do not match");
        }
        int idxA0 = A.col_idx[colA];
        int idxA1 = A.col_idx[colA + 1];
        int idxB0 = B.col_idx[colB];
        int idxB1 = B.col_idx[colB + 1];
        C.growMaxColumns(++C.numCols, true);
        C.growMaxLength(C.nz_length + idxA1 - idxA0 + idxB1 - idxB0, true);
        int[] w = UtilEjml.adjust(gw, A.numRows);
        Arrays.fill(w, 0, A.numRows, -1);
        for (i = idxA0; i < idxA1; ++i) {
            C.nz_rows[C.nz_length] = row = A.nz_rows[i];
            C.nz_values[C.nz_length] = alpha * A.nz_values[i];
            ++C.nz_length;
        }
        for (i = idxB0; i < idxB1; ++i) {
            row = B.nz_rows[i];
            if (w[row] != -1) {
                int n = w[row];
                C.nz_values[n] = C.nz_values[n] + beta * B.nz_values[i];
                continue;
            }
            C.nz_values[C.nz_length] = beta * B.nz_values[i];
            C.nz_rows[C.nz_length++] = row;
        }
        C.col_idx[C.numCols] = C.nz_length;
    }

    public static void elementMult(FMatrixSparseCSC A, FMatrixSparseCSC B, FMatrixSparseCSC C, @Nullable IGrowArray gw, @Nullable FGrowArray gx) {
        float[] x = UtilEjml.adjust(gx, A.numRows);
        int[] w = UtilEjml.adjust(gw, A.numRows);
        Arrays.fill(w, 0, A.numRows, -1);
        C.growMaxLength(Math.min(A.nz_length, B.nz_length), false);
        C.indicesSorted = false;
        C.nz_length = 0;
        for (int col = 0; col < A.numCols; ++col) {
            int row;
            int i;
            int idxA1 = A.col_idx[col + 1];
            int idxA0 = A.col_idx[col];
            int idxB1 = B.col_idx[col + 1];
            int idxB0 = B.col_idx[col];
            int maxInRow = Math.min(idxA1 - idxA0, idxB1 - idxB0);
            if (C.nz_length + maxInRow > C.nz_values.length) {
                C.growMaxLength(C.nz_values.length + maxInRow, true);
            }
            C.col_idx[col] = C.nz_length;
            for (i = idxA0; i < idxA1; ++i) {
                row = A.nz_rows[i];
                w[row] = col;
                x[row] = A.nz_values[i];
            }
            for (i = idxB0; i < idxB1; ++i) {
                row = B.nz_rows[i];
                if (w[row] != col) continue;
                C.nz_values[C.nz_length] = x[row] * B.nz_values[i];
                C.nz_rows[C.nz_length++] = row;
            }
        }
        C.col_idx[C.numCols] = C.nz_length;
    }

    public static void removeZeros(FMatrixSparseCSC input, FMatrixSparseCSC output, float tol) {
        output.reshape(input.numRows, input.numCols, input.nz_length);
        output.nz_length = 0;
        for (int i = 0; i < input.numCols; ++i) {
            output.col_idx[i] = output.nz_length;
            int idx0 = input.col_idx[i];
            int idx1 = input.col_idx[i + 1];
            for (int j = idx0; j < idx1; ++j) {
                float val = input.nz_values[j];
                if (!(Math.abs(val) > tol)) continue;
                output.nz_rows[output.nz_length] = input.nz_rows[j];
                output.nz_values[output.nz_length++] = val;
            }
        }
        output.col_idx[output.numCols] = output.nz_length;
    }

    public static void removeZeros(FMatrixSparseCSC A, float tol) {
        int offset = 0;
        for (int i = 0; i < A.numCols; ++i) {
            int idx0 = A.col_idx[i] + offset;
            int idx1 = A.col_idx[i + 1];
            for (int j = idx0; j < idx1; ++j) {
                float val = A.nz_values[j];
                if (Math.abs(val) > tol) {
                    A.nz_rows[j - offset] = A.nz_rows[j];
                    A.nz_values[j - offset] = val;
                    continue;
                }
                ++offset;
            }
            int n = i + 1;
            A.col_idx[n] = A.col_idx[n] - offset;
        }
        A.nz_length -= offset;
    }

    public static void duplicatesAdd(FMatrixSparseCSC A, @Nullable IGrowArray work) {
        int[] table = UtilEjml.adjustFill(work, A.numRows, -1);
        int offset = 0;
        for (int i = 0; i < A.numCols; ++i) {
            int row;
            int j;
            int idx0 = A.col_idx[i] + offset;
            int idx1 = A.col_idx[i + 1];
            for (j = idx0; j < idx1; ++j) {
                row = A.nz_rows[j];
                if (table[row] != -1) continue;
                table[row] = j;
            }
            for (j = idx0; j < idx1; ++j) {
                row = A.nz_rows[j];
                if (table[row] == j) {
                    A.nz_rows[j - offset] = row;
                    A.nz_values[j - offset] = A.nz_values[j];
                    table[row] = j - offset;
                    continue;
                }
                int n = table[row];
                A.nz_values[n] = A.nz_values[n] + A.nz_values[j];
                ++offset;
            }
            int n = i + 1;
            A.col_idx[n] = A.col_idx[n] - offset;
            for (j = A.col_idx[i]; j < (idx1 -= offset); ++j) {
                table[A.nz_rows[j]] = -1;
            }
        }
        A.nz_length -= offset;
    }

    public static void symmLowerToFull(FMatrixSparseCSC A, FMatrixSparseCSC B, @Nullable IGrowArray gw) {
        int idx1;
        int idx0;
        int col;
        if (A.numCols != A.numRows) {
            throw new IllegalArgumentException("Must be a lower triangular square matrix");
        }
        int N = A.numCols;
        int[] w = UtilEjml.adjust(gw, N, N);
        B.reshape(N, N, A.nz_length * 2);
        B.indicesSorted = false;
        for (col = 0; col < N; ++col) {
            idx0 = A.col_idx[col];
            idx1 = A.col_idx[col + 1];
            int n = col;
            w[n] = w[n] + (idx1 - idx0);
            for (int i = idx0; i < idx1; ++i) {
                int row = A.nz_rows[i];
                if (row <= col) continue;
                int n2 = row;
                w[n2] = w[n2] + 1;
            }
        }
        B.histogramToStructure(w);
        Arrays.fill(w, 0, N, 0);
        for (col = 0; col < N; ++col) {
            idx0 = A.col_idx[col];
            idx1 = A.col_idx[col + 1];
            int lengthA = idx1 - idx0;
            int lengthB = B.col_idx[col + 1] - B.col_idx[col];
            System.arraycopy(A.nz_values, idx0, B.nz_values, B.col_idx[col] + lengthB - lengthA, lengthA);
            System.arraycopy(A.nz_rows, idx0, B.nz_rows, B.col_idx[col] + lengthB - lengthA, lengthA);
            for (int i = idx0; i < idx1; ++i) {
                int row = A.nz_rows[i];
                if (row <= col) continue;
                int n = row;
                int n3 = w[n];
                w[n] = n3 + 1;
                int indexB = B.col_idx[row] + n3;
                B.nz_rows[indexB] = col;
                B.nz_values[indexB] = A.nz_values[i];
            }
        }
    }
}

