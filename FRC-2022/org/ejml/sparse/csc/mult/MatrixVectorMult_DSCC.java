/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.mult;

import java.util.Arrays;
import org.ejml.data.DMatrixSparseCSC;

public class MatrixVectorMult_DSCC {
    public static void mult(DMatrixSparseCSC A, double[] b, int offsetB, double[] c, int offsetC) {
        Arrays.fill(c, offsetC, offsetC + A.numRows, 0.0);
        MatrixVectorMult_DSCC.multAdd(A, b, offsetB, c, offsetC);
    }

    public static void multAdd(DMatrixSparseCSC A, double[] b, int offsetB, double[] c, int offsetC) {
        if (b.length - offsetB < A.numCols) {
            throw new IllegalArgumentException("Length of 'b' isn't long enough");
        }
        if (c.length - offsetC < A.numRows) {
            throw new IllegalArgumentException("Length of 'c' isn't long enough");
        }
        for (int k = 0; k < A.numCols; ++k) {
            int idx0 = A.col_idx[k];
            int idx1 = A.col_idx[k + 1];
            for (int indexA = idx0; indexA < idx1; ++indexA) {
                int n = offsetC + A.nz_rows[indexA];
                c[n] = c[n] + A.nz_values[indexA] * b[offsetB + k];
            }
        }
    }

    public static void mult(double[] a, int offsetA, DMatrixSparseCSC B, double[] c, int offsetC) {
        if (a.length - offsetA < B.numRows) {
            throw new IllegalArgumentException("Length of 'a' isn't long enough");
        }
        if (c.length - offsetC < B.numCols) {
            throw new IllegalArgumentException("Length of 'c' isn't long enough");
        }
        for (int k = 0; k < B.numCols; ++k) {
            int idx0 = B.col_idx[k];
            int idx1 = B.col_idx[k + 1];
            double sum = 0.0;
            for (int indexB = idx0; indexB < idx1; ++indexB) {
                sum += a[offsetA + B.nz_rows[indexB]] * B.nz_values[indexB];
            }
            c[offsetC + k] = sum;
        }
    }

    public static double innerProduct(double[] a, int offsetA, DMatrixSparseCSC B, double[] c, int offsetC) {
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
                sum += a[offsetA + B.nz_rows[indexB]] * B.nz_values[indexB];
            }
            output += sum * c[offsetC + k];
        }
        return output;
    }
}

