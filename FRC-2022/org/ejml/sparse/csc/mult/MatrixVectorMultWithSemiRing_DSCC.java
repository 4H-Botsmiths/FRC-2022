/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.mult;

import java.util.Arrays;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.masks.DMaskPrimitive;
import org.ejml.masks.DMaskSparse;
import org.ejml.masks.Mask;
import org.ejml.ops.DSemiRing;
import org.jetbrains.annotations.Nullable;

public class MatrixVectorMultWithSemiRing_DSCC {
    public static void mult(DMatrixSparseCSC A, double[] b, int offsetB, double[] c, int offsetC, DSemiRing semiRing, @Nullable Mask mask) {
        MatrixVectorMultWithSemiRing_DSCC.multAdd(A, b, offsetB, c, offsetC, semiRing, mask);
    }

    public static void mult(DMatrixSparseCSC A, double[] b, double[] c, DSemiRing semiRing, @Nullable Mask mask) {
        MatrixVectorMultWithSemiRing_DSCC.mult(A, b, 0, c, 0, semiRing, mask);
    }

    public static void multAdd(DMatrixSparseCSC A, double[] b, int offsetB, double[] c, int offsetC, DSemiRing semiRing, @Nullable Mask mask) {
        if (b.length - offsetB < A.numCols) {
            throw new IllegalArgumentException("Length of 'b' isn't long enough");
        }
        if (c.length - offsetC < A.numRows) {
            throw new IllegalArgumentException("Length of 'c' isn't long enough");
        }
        Arrays.fill(c, semiRing.add.id);
        for (int k = 0; k < A.numCols; ++k) {
            int idx0 = A.col_idx[k];
            int idx1 = A.col_idx[k + 1];
            for (int indexA = idx0; indexA < idx1; ++indexA) {
                c[offsetC + A.nz_rows[indexA]] = semiRing.add.func.apply(c[offsetC + A.nz_rows[indexA]], semiRing.mult.func.apply(A.nz_values[indexA], b[offsetB + k]));
            }
        }
        if (mask != null) {
            double zeroElement = 0.0;
            if (mask instanceof DMaskPrimitive) {
                zeroElement = ((DMaskPrimitive)mask).zeroElement;
            } else if (mask instanceof DMaskSparse) {
                zeroElement = ((DMaskSparse)mask).zeroElement;
            }
            for (int i = offsetC; i < c.length - offsetC; ++i) {
                if (mask.isSet(i)) continue;
                c[i] = zeroElement;
            }
        }
    }

    public static void mult(double[] a, int offsetA, DMatrixSparseCSC B, double[] c, int offsetC, DSemiRing semiRing, @Nullable Mask mask) {
        if (a.length - offsetA < B.numRows) {
            throw new IllegalArgumentException("Length of 'a' isn't long enough");
        }
        if (c.length - offsetC < B.numCols) {
            throw new IllegalArgumentException("Length of 'c' isn't long enough");
        }
        for (int k = 0; k < B.numCols; ++k) {
            if (mask != null && !mask.isSet(k)) continue;
            int idx0 = B.col_idx[k];
            int idx1 = B.col_idx[k + 1];
            double sum = semiRing.add.id;
            for (int indexB = idx0; indexB < idx1; ++indexB) {
                sum = semiRing.add.func.apply(sum, semiRing.mult.func.apply(a[offsetA + B.nz_rows[indexB]], B.nz_values[indexB]));
            }
            c[offsetC + k] = sum;
        }
    }

    public static void mult(double[] a, DMatrixSparseCSC B, double[] c, DSemiRing semiRing, @Nullable Mask mask) {
        MatrixVectorMultWithSemiRing_DSCC.mult(a, 0, B, c, 0, semiRing, mask);
    }

    public static double innerProduct(double[] a, int offsetA, DMatrixSparseCSC B, double[] c, int offsetC, DSemiRing semiRing) {
        if (a.length - offsetA < B.numRows) {
            throw new IllegalArgumentException("Length of 'a' isn't long enough");
        }
        if (c.length - offsetC < B.numCols) {
            throw new IllegalArgumentException("Length of 'c' isn't long enough");
        }
        double output = 0.0;
        for (int k = 0; k < B.numCols; ++k) {
            int idx0 = B.col_idx[k];
            int idx1 = B.col_idx[k + 1];
            double sum = 0.0;
            for (int indexB = idx0; indexB < idx1; ++indexB) {
                sum = semiRing.add.func.apply(sum, semiRing.mult.func.apply(a[offsetA + B.nz_rows[indexB]], B.nz_values[indexB]));
            }
            output = semiRing.add.func.apply(output, semiRing.mult.func.apply(sum, c[offsetC + k]));
        }
        return output;
    }
}

