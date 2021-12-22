/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block;

import org.ejml.data.FMatrixRBlock;
import org.ejml.data.FSubmatrixD1;
import org.ejml.dense.block.MatrixMult_MT_FDRB;

public class MatrixOps_MT_FDRB {
    public static void mult(FMatrixRBlock A, FMatrixRBlock B, FMatrixRBlock C) {
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
        FSubmatrixD1 Asub = new FSubmatrixD1(A, 0, A.numRows, 0, A.numCols);
        FSubmatrixD1 Bsub = new FSubmatrixD1(B, 0, B.numRows, 0, B.numCols);
        FSubmatrixD1 Csub = new FSubmatrixD1(C, 0, C.numRows, 0, C.numCols);
        MatrixMult_MT_FDRB.mult(blockLength, Asub, Bsub, Csub);
    }

    public static void multTransA(FMatrixRBlock A, FMatrixRBlock B, FMatrixRBlock C) {
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
        FSubmatrixD1 Asub = new FSubmatrixD1(A, 0, A.numRows, 0, A.numCols);
        FSubmatrixD1 Bsub = new FSubmatrixD1(B, 0, B.numRows, 0, B.numCols);
        FSubmatrixD1 Csub = new FSubmatrixD1(C, 0, C.numRows, 0, C.numCols);
        MatrixMult_MT_FDRB.multTransA(blockLength, Asub, Bsub, Csub);
    }

    public static void multTransB(FMatrixRBlock A, FMatrixRBlock B, FMatrixRBlock C) {
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
        FSubmatrixD1 Asub = new FSubmatrixD1(A, 0, A.numRows, 0, A.numCols);
        FSubmatrixD1 Bsub = new FSubmatrixD1(B, 0, B.numRows, 0, B.numCols);
        FSubmatrixD1 Csub = new FSubmatrixD1(C, 0, C.numRows, 0, C.numCols);
        MatrixMult_MT_FDRB.multTransB(blockLength, Asub, Bsub, Csub);
    }
}

