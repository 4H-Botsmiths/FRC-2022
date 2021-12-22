/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.mult;

import org.ejml.MatrixDimensionException;
import org.ejml.data.DMatrix1Row;
import org.ejml.data.DMatrixD1;
import org.ejml.dense.row.CommonOps_DDRM;

public class MatrixVectorMult_DDRM {
    public static void mult(DMatrix1Row A, DMatrixD1 B, DMatrixD1 C) {
        if (B.numRows == 1) {
            if (A.numCols != B.numCols) {
                throw new MatrixDimensionException("A and B are not compatible");
            }
        } else if (B.numCols == 1) {
            if (A.numCols != B.numRows) {
                throw new MatrixDimensionException("A and B are not compatible");
            }
        } else {
            throw new MatrixDimensionException("B is not a vector");
        }
        C.reshape(A.numRows, 1);
        if (A.numCols == 0) {
            CommonOps_DDRM.fill(C, 0.0);
            return;
        }
        int indexA = 0;
        int cIndex = 0;
        double b0 = B.get(0);
        for (int i = 0; i < A.numRows; ++i) {
            double total = A.get(indexA++) * b0;
            for (int j = 1; j < A.numCols; ++j) {
                total += A.get(indexA++) * B.get(j);
            }
            C.set(cIndex++, total);
        }
    }

    public static void multAdd(DMatrix1Row A, DMatrixD1 B, DMatrixD1 C) {
        if (B.numRows == 1) {
            if (A.numCols != B.numCols) {
                throw new MatrixDimensionException("A and B are not compatible");
            }
        } else if (B.numCols == 1) {
            if (A.numCols != B.numRows) {
                throw new MatrixDimensionException("A and B are not compatible");
            }
        } else {
            throw new MatrixDimensionException("B is not a vector");
        }
        if (A.numRows != C.getNumElements()) {
            throw new MatrixDimensionException("C is not compatible with A");
        }
        if (A.numCols == 0) {
            return;
        }
        int indexA = 0;
        int cIndex = 0;
        for (int i = 0; i < A.numRows; ++i) {
            double total = A.get(indexA++) * B.get(0);
            for (int j = 1; j < A.numCols; ++j) {
                total += A.get(indexA++) * B.get(j);
            }
            C.plus(cIndex++, total);
        }
    }

    public static void multTransA_small(DMatrix1Row A, DMatrixD1 B, DMatrixD1 C) {
        if (B.numRows == 1) {
            if (A.numRows != B.numCols) {
                throw new MatrixDimensionException("A and B are not compatible");
            }
        } else if (B.numCols == 1) {
            if (A.numRows != B.numRows) {
                throw new MatrixDimensionException("A and B are not compatible");
            }
        } else {
            throw new MatrixDimensionException("B is not a vector");
        }
        C.reshape(A.numCols, 1);
        int cIndex = 0;
        for (int i = 0; i < A.numCols; ++i) {
            double total = 0.0;
            int indexA = i;
            for (int j = 0; j < A.numRows; ++j) {
                total += A.get(indexA) * B.get(j);
                indexA += A.numCols;
            }
            C.set(cIndex++, total);
        }
    }

    public static void multTransA_reorder(DMatrix1Row A, DMatrixD1 B, DMatrixD1 C) {
        if (B.numRows == 1) {
            if (A.numRows != B.numCols) {
                throw new MatrixDimensionException("A and B are not compatible");
            }
        } else if (B.numCols == 1) {
            if (A.numRows != B.numRows) {
                throw new MatrixDimensionException("A and B are not compatible");
            }
        } else {
            throw new MatrixDimensionException("B is not a vector");
        }
        C.reshape(A.numCols, 1);
        if (A.numRows == 0) {
            CommonOps_DDRM.fill(C, 0.0);
            return;
        }
        double B_val = B.get(0);
        for (int i = 0; i < A.numCols; ++i) {
            C.set(i, A.get(i) * B_val);
        }
        int indexA = A.numCols;
        for (int i = 1; i < A.numRows; ++i) {
            B_val = B.get(i);
            for (int j = 0; j < A.numCols; ++j) {
                C.plus(j, A.get(indexA++) * B_val);
            }
        }
    }

    public static void multAddTransA_small(DMatrix1Row A, DMatrixD1 B, DMatrixD1 C) {
        if (B.numRows == 1) {
            if (A.numRows != B.numCols) {
                throw new MatrixDimensionException("A and B are not compatible");
            }
        } else if (B.numCols == 1) {
            if (A.numRows != B.numRows) {
                throw new MatrixDimensionException("A and B are not compatible");
            }
        } else {
            throw new MatrixDimensionException("B is not a vector");
        }
        if (A.numCols != C.getNumElements()) {
            throw new MatrixDimensionException("C is not compatible with A");
        }
        int cIndex = 0;
        for (int i = 0; i < A.numCols; ++i) {
            double total = 0.0;
            int indexA = i;
            for (int j = 0; j < A.numRows; ++j) {
                total += A.get(indexA) * B.get(j);
                indexA += A.numCols;
            }
            C.plus(cIndex++, total);
        }
    }

    public static void multAddTransA_reorder(DMatrix1Row A, DMatrixD1 B, DMatrixD1 C) {
        if (B.numRows == 1) {
            if (A.numRows != B.numCols) {
                throw new MatrixDimensionException("A and B are not compatible");
            }
        } else if (B.numCols == 1) {
            if (A.numRows != B.numRows) {
                throw new MatrixDimensionException("A and B are not compatible");
            }
        } else {
            throw new MatrixDimensionException("B is not a vector");
        }
        if (A.numCols != C.getNumElements()) {
            throw new MatrixDimensionException("C is not compatible with A");
        }
        int indexA = 0;
        for (int j = 0; j < A.numRows; ++j) {
            double B_val = B.get(j);
            for (int i = 0; i < A.numCols; ++i) {
                C.plus(i, A.get(indexA++) * B_val);
            }
        }
    }

    public static double innerProduct(double[] a, int offsetA, DMatrix1Row B, double[] c, int offsetC) {
        if (a.length - offsetA < B.numRows) {
            throw new IllegalArgumentException("Length of 'a' isn't long enough");
        }
        if (c.length - offsetC < B.numCols) {
            throw new IllegalArgumentException("Length of 'c' isn't long enough");
        }
        int cols = B.numCols;
        double output = 0.0;
        for (int k = 0; k < B.numCols; ++k) {
            double sum = 0.0;
            for (int i = 0; i < B.numRows; ++i) {
                sum += a[offsetA + i] * B.data[k + i * cols];
            }
            output += sum * c[offsetC + k];
        }
        return output;
    }
}

