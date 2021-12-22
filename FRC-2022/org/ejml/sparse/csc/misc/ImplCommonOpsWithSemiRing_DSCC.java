/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.misc;

import java.util.Arrays;
import org.ejml.UtilEjml;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.masks.Mask;
import org.ejml.ops.DSemiRing;
import org.ejml.sparse.csc.mult.ImplMultiplicationWithSemiRing_DSCC;
import org.jetbrains.annotations.Nullable;

public class ImplCommonOpsWithSemiRing_DSCC {
    public static void add(double alpha, DMatrixSparseCSC A, double beta, DMatrixSparseCSC B, DMatrixSparseCSC C, DSemiRing semiRing, @Nullable Mask mask, @Nullable IGrowArray gw, @Nullable DGrowArray gx) {
        double[] x = UtilEjml.adjust(gx, A.numRows);
        int[] w = UtilEjml.adjust(gw, A.numRows, A.numRows);
        C.indicesSorted = false;
        C.nz_length = 0;
        for (int col = 0; col < A.numCols; ++col) {
            C.col_idx[col] = C.nz_length;
            if (mask != null) {
                mask.setIndexColumn(col);
            }
            ImplMultiplicationWithSemiRing_DSCC.multAddColA(A, col, alpha, C, col + 1, semiRing, mask, x, w);
            ImplMultiplicationWithSemiRing_DSCC.multAddColA(B, col, beta, C, col + 1, semiRing, mask, x, w);
            int idxC0 = C.col_idx[col];
            int idxC1 = C.col_idx[col + 1];
            for (int i = idxC0; i < idxC1; ++i) {
                C.nz_values[i] = x[C.nz_rows[i]];
            }
        }
        C.col_idx[A.numCols] = C.nz_length;
    }

    public static void addColAppend(DMatrixSparseCSC A, int colA, DMatrixSparseCSC B, int colB, DMatrixSparseCSC C, DSemiRing semiRing, @Nullable IGrowArray gw) {
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
            C.nz_values[C.nz_length] = A.nz_values[i];
            ++C.nz_length;
        }
        for (i = idxB0; i < idxB1; ++i) {
            row = B.nz_rows[i];
            if (w[row] != -1) {
                C.nz_values[w[row]] = semiRing.add.func.apply(C.nz_values[w[row]], B.nz_values[i]);
                continue;
            }
            C.nz_values[C.nz_length] = B.nz_values[i];
            C.nz_rows[C.nz_length++] = row;
        }
        C.col_idx[C.numCols] = C.nz_length;
    }

    public static void elementMult(DMatrixSparseCSC A, DMatrixSparseCSC B, DMatrixSparseCSC C, DSemiRing semiRing, @Nullable Mask mask, @Nullable IGrowArray gw, @Nullable DGrowArray gx) {
        double[] x = UtilEjml.adjust(gx, A.numRows);
        int[] w = UtilEjml.adjust(gw, A.numRows);
        Arrays.fill(w, 0, A.numRows, -1);
        int maxMaskEntries = Integer.MAX_VALUE;
        if (mask != null) {
            maxMaskEntries = mask.maxMaskedEntries();
        }
        C.growMaxLength(Math.min(maxMaskEntries, Math.min(A.nz_length, B.nz_length)), false);
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
            int expectedResultSize = C.nz_length + maxInRow;
            if (expectedResultSize > C.nz_values.length) {
                C.growMaxLength(Math.min(maxMaskEntries, expectedResultSize), true);
            }
            C.col_idx[col] = C.nz_length;
            for (i = idxA0; i < idxA1; ++i) {
                row = A.nz_rows[i];
                w[row] = col;
                x[row] = A.nz_values[i];
            }
            for (i = idxB0; i < idxB1; ++i) {
                row = B.nz_rows[i];
                if (mask != null && !mask.isSet(row, col) || w[row] != col) continue;
                C.nz_values[C.nz_length] = semiRing.mult.func.apply(x[row], B.nz_values[i]);
                C.nz_rows[C.nz_length++] = row;
            }
        }
        C.col_idx[C.numCols] = C.nz_length;
    }
}

