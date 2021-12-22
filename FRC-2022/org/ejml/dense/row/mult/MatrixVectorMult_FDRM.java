/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.mult;

import org.ejml.MatrixDimensionException;
import org.ejml.data.FMatrix1Row;
import org.ejml.data.FMatrixD1;
import org.ejml.dense.row.CommonOps_FDRM;

public class MatrixVectorMult_FDRM {
    public static void mult(FMatrix1Row A, FMatrixD1 B, FMatrixD1 C) {
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
            CommonOps_FDRM.fill(C, 0.0f);
            return;
        }
        int indexA = 0;
        int cIndex = 0;
        float b0 = B.get(0);
        for (int i = 0; i < A.numRows; ++i) {
            float total = A.get(indexA++) * b0;
            for (int j = 1; j < A.numCols; ++j) {
                total += A.get(indexA++) * B.get(j);
            }
            C.set(cIndex++, total);
        }
    }

    public static void multAdd(FMatrix1Row A, FMatrixD1 B, FMatrixD1 C) {
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
            float total = A.get(indexA++) * B.get(0);
            for (int j = 1; j < A.numCols; ++j) {
                total += A.get(indexA++) * B.get(j);
            }
            C.plus(cIndex++, total);
        }
    }

    public static void multTransA_small(FMatrix1Row A, FMatrixD1 B, FMatrixD1 C) {
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
            float total = 0.0f;
            int indexA = i;
            for (int j = 0; j < A.numRows; ++j) {
                total += A.get(indexA) * B.get(j);
                indexA += A.numCols;
            }
            C.set(cIndex++, total);
        }
    }

    public static void multTransA_reorder(FMatrix1Row A, FMatrixD1 B, FMatrixD1 C) {
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
            CommonOps_FDRM.fill(C, 0.0f);
            return;
        }
        float B_val = B.get(0);
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

    public static void multAddTransA_small(FMatrix1Row A, FMatrixD1 B, FMatrixD1 C) {
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
            float total = 0.0f;
            int indexA = i;
            for (int j = 0; j < A.numRows; ++j) {
                total += A.get(indexA) * B.get(j);
                indexA += A.numCols;
            }
            C.plus(cIndex++, total);
        }
    }

    public static void multAddTransA_reorder(FMatrix1Row A, FMatrixD1 B, FMatrixD1 C) {
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
            float B_val = B.get(j);
            for (int i = 0; i < A.numCols; ++i) {
                C.plus(i, A.get(indexA++) * B_val);
            }
        }
    }

    public static float innerProduct(float[] a, int offsetA, FMatrix1Row B, float[] c, int offsetC) {
        if (a.length - offsetA < B.numRows) {
            throw new IllegalArgumentException("Length of 'a' isn't long enough");
        }
        if (c.length - offsetC < B.numCols) {
            throw new IllegalArgumentException("Length of 'c' isn't long enough");
        }
        int cols = B.numCols;
        float output = 0.0f;
        for (int k = 0; k < B.numCols; ++k) {
            float sum = 0.0f;
            for (int i = 0; i < B.numRows; ++i) {
                sum += a[offsetA + i] * B.data[k + i * cols];
            }
            output += sum * c[offsetC + k];
        }
        return output;
    }
}

