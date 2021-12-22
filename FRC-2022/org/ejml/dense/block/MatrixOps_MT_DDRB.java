/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block;

import org.ejml.data.DMatrixRBlock;
import org.ejml.data.DSubmatrixD1;
import org.ejml.dense.block.MatrixMult_MT_DDRB;

public class MatrixOps_MT_DDRB {
    public static void mult(DMatrixRBlock A, DMatrixRBlock B, DMatrixRBlock C) {
        if (A.numCols != B.numRows) {
            throw new IllegalArgumentException("Columns in A are incompatible with rows in B");
        }
        if (A.numRows != C.numRows) {
            throw new IllegalArgumentException("Rows in A are incompatible with rows in C");
        }
        if (B.numCols != C.numCols) {
            throw new IllegalArgumentException("Columns in B are incompatible with columns in C");
        }
        if (A.blockLength != B.blockLength || A.blockLength != C.blockLength) {
            throw new IllegalArgumentException("Block lengths are not all the same.");
        }
        int blockLength = A.blockLength;
        DSubmatrixD1 Asub = new DSubmatrixD1(A, 0, A.numRows, 0, A.numCols);
        DSubmatrixD1 Bsub = new DSubmatrixD1(B, 0, B.numRows, 0, B.numCols);
        DSubmatrixD1 Csub = new DSubmatrixD1(C, 0, C.numRows, 0, C.numCols);
        MatrixMult_MT_DDRB.mult(blockLength, Asub, Bsub, Csub);
    }

    public static void multTransA(DMatrixRBlock A, DMatrixRBlock B, DMatrixRBlock C) {
        if (A.numRows != B.numRows) {
            throw new IllegalArgumentException("Rows in A are incompatible with rows in B");
        }
        if (A.numCols != C.numRows) {
            throw new IllegalArgumentException("Columns in A are incompatible with rows in C");
        }
        if (B.numCols != C.numCols) {
            throw new IllegalArgumentException("Columns in B are incompatible with columns in C");
        }
        if (A.blockLength != B.blockLength || A.blockLength != C.blockLength) {
            throw new IllegalArgumentException("Block lengths are not all the same.");
        }
        int blockLength = A.blockLength;
        DSubmatrixD1 Asub = new DSubmatrixD1(A, 0, A.numRows, 0, A.numCols);
        DSubmatrixD1 Bsub = new DSubmatrixD1(B, 0, B.numRows, 0, B.numCols);
        DSubmatrixD1 Csub = new DSubmatrixD1(C, 0, C.numRows, 0, C.numCols);
        MatrixMult_MT_DDRB.multTransA(blockLength, Asub, Bsub, Csub);
    }

    public static void multTransB(DMatrixRBlock A, DMatrixRBlock B, DMatrixRBlock C) {
        if (A.numCols != B.numCols) {
            throw new IllegalArgumentException("Columns in A are incompatible with columns in B");
        }
        if (A.numRows != C.numRows) {
            throw new IllegalArgumentException("Rows in A are incompatible with rows in C");
        }
        if (B.numRows != C.numCols) {
            throw new IllegalArgumentException("Rows in B are incompatible with columns in C");
        }
        if (A.blockLength != B.blockLength || A.blockLength != C.blockLength) {
            throw new IllegalArgumentException("Block lengths are not all the same.");
        }
        int blockLength = A.blockLength;
        DSubmatrixD1 Asub = new DSubmatrixD1(A, 0, A.numRows, 0, A.numCols);
        DSubmatrixD1 Bsub = new DSubmatrixD1(B, 0, B.numRows, 0, B.numCols);
        DSubmatrixD1 Csub = new DSubmatrixD1(C, 0, C.numRows, 0, C.numCols);
        MatrixMult_MT_DDRB.multTransB(blockLength, Asub, Bsub, Csub);
    }
}

