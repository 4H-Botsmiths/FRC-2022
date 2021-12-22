/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.block.decomposition.qr;

import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixD1;
import org.ejml.data.DMatrixRBlock;
import org.ejml.data.DSubmatrixD1;
import org.ejml.dense.block.MatrixMult_MT_DDRB;
import org.ejml.dense.block.MatrixOps_DDRB;
import org.ejml.dense.block.decomposition.qr.BlockHouseHolder_MT_DDRB;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.jetbrains.annotations.Nullable;
import pabeles.concurrency.GrowArray;

public class QRDecompositionHouseholder_MT_DDRB
implements QRDecomposition<DMatrixRBlock> {
    private DMatrixRBlock dataA;
    private final DMatrixRBlock dataW = new DMatrixRBlock(1, 1);
    private final DMatrixRBlock dataWTA = new DMatrixRBlock(1, 1);
    private int blockLength;
    private final DSubmatrixD1 A = new DSubmatrixD1();
    private final DSubmatrixD1 Y = new DSubmatrixD1();
    private final DSubmatrixD1 W = new DSubmatrixD1(this.dataW);
    private final DSubmatrixD1 WTA = new DSubmatrixD1(this.dataWTA);
    private final GrowArray<DGrowArray> workspace = new GrowArray<DGrowArray>(DGrowArray::new);
    private double[] gammas = new double[1];
    private boolean saveW = false;

    public DMatrixRBlock getQR() {
        return this.dataA;
    }

    public void setSaveW(boolean saveW) {
        this.saveW = saveW;
    }

    @Override
    public DMatrixRBlock getQ(@Nullable DMatrixRBlock Q, boolean compact) {
        Q = QRDecompositionHouseholder_MT_DDRB.initializeQ(Q, this.dataA.numRows, this.dataA.numCols, this.blockLength, compact);
        this.applyQ(Q, true);
        return Q;
    }

    public static DMatrixRBlock initializeQ(@Nullable DMatrixRBlock Q, int numRows, int numCols, int blockLength, boolean compact) {
        int minLength = Math.min(numRows, numCols);
        if (compact) {
            if (Q == null) {
                Q = new DMatrixRBlock(numRows, minLength, blockLength);
                MatrixOps_DDRB.setIdentity(Q);
            } else {
                if (Q.numRows != numRows || Q.numCols != minLength) {
                    throw new IllegalArgumentException("Unexpected matrix dimension. Found " + Q.numRows + " " + Q.numCols);
                }
                MatrixOps_DDRB.setIdentity(Q);
            }
        } else if (Q == null) {
            Q = new DMatrixRBlock(numRows, numRows, blockLength);
            MatrixOps_DDRB.setIdentity(Q);
        } else {
            if (Q.numRows != numRows || Q.numCols != numRows) {
                throw new IllegalArgumentException("Unexpected matrix dimension. Found " + Q.numRows + " " + Q.numCols);
            }
            MatrixOps_DDRB.setIdentity(Q);
        }
        return Q;
    }

    public void applyQ(DMatrixRBlock B) {
        this.applyQ(B, false);
    }

    public void applyQ(DMatrixRBlock B, boolean isIdentity) {
        int minDimen = Math.min(this.dataA.numCols, this.dataA.numRows);
        DSubmatrixD1 subB = new DSubmatrixD1(B);
        this.W.row0 = 0;
        this.W.col0 = 0;
        this.Y.row1 = this.W.row1 = this.dataA.numRows;
        this.WTA.col0 = 0;
        this.WTA.row0 = 0;
        int start = minDimen - minDimen % this.blockLength;
        if (start == minDimen) {
            start -= this.blockLength;
        }
        if (start < 0) {
            start = 0;
        }
        for (int i = start; i >= 0; i -= this.blockLength) {
            this.Y.col0 = i;
            this.Y.col1 = Math.min(this.Y.col0 + this.blockLength, this.dataA.numCols);
            this.Y.row0 = i;
            if (isIdentity) {
                subB.col0 = i;
            }
            subB.row0 = i;
            this.setW();
            this.WTA.row1 = this.Y.col1 - this.Y.col0;
            this.WTA.col1 = subB.col1 - subB.col0;
            ((DMatrixD1)this.WTA.original).reshape(this.WTA.row1, this.WTA.col1, false);
            if (!this.saveW) {
                BlockHouseHolder_MT_DDRB.computeW_Column(this.blockLength, this.Y, this.W, this.workspace, this.gammas, this.Y.col0);
            }
            BlockHouseHolder_MT_DDRB.multTransA_vecCol(this.blockLength, this.Y, subB, this.WTA);
            MatrixMult_MT_DDRB.multPlus(this.blockLength, this.W, this.WTA, subB);
        }
    }

    public void applyQTran(DMatrixRBlock B) {
        int minDimen = Math.min(this.dataA.numCols, this.dataA.numRows);
        DSubmatrixD1 subB = new DSubmatrixD1(B);
        this.W.row0 = 0;
        this.W.col0 = 0;
        this.Y.row1 = this.W.row1 = this.dataA.numRows;
        this.WTA.col0 = 0;
        this.WTA.row0 = 0;
        for (int i = 0; i < minDimen; i += this.blockLength) {
            this.Y.col0 = i;
            this.Y.col1 = Math.min(this.Y.col0 + this.blockLength, this.dataA.numCols);
            this.Y.row0 = i;
            subB.row0 = i;
            this.setW();
            this.WTA.row0 = 0;
            this.WTA.col0 = 0;
            this.WTA.row1 = this.W.col1 - this.W.col0;
            this.WTA.col1 = subB.col1 - subB.col0;
            ((DMatrixD1)this.WTA.original).reshape(this.WTA.row1, this.WTA.col1, false);
            if (!this.saveW) {
                BlockHouseHolder_MT_DDRB.computeW_Column(this.blockLength, this.Y, this.W, this.workspace, this.gammas, this.Y.col0);
            }
            MatrixMult_MT_DDRB.multTransA(this.blockLength, this.W, subB, this.WTA);
            BlockHouseHolder_MT_DDRB.multAdd_zeros(this.blockLength, this.Y, this.WTA, subB);
        }
    }

    @Override
    public DMatrixRBlock getR(@Nullable DMatrixRBlock R, boolean compact) {
        int min = Math.min(this.dataA.numRows, this.dataA.numCols);
        if (R == null) {
            R = compact ? new DMatrixRBlock(min, this.dataA.numCols, this.blockLength) : new DMatrixRBlock(this.dataA.numRows, this.dataA.numCols, this.blockLength);
        } else if (compact ? R.numCols != this.dataA.numCols || R.numRows != min : R.numCols != this.dataA.numCols || R.numRows != this.dataA.numRows) {
            throw new IllegalArgumentException("Unexpected dimension.");
        }
        MatrixOps_DDRB.zeroTriangle(false, R);
        MatrixOps_DDRB.copyTriangle(true, this.dataA, R);
        return R;
    }

    @Override
    public boolean decompose(DMatrixRBlock orig) {
        this.setup(orig);
        int m = Math.min(orig.numCols, orig.numRows);
        for (int j = 0; j < m; j += this.blockLength) {
            this.Y.col0 = j;
            this.Y.col1 = Math.min(orig.numCols, this.Y.col0 + this.blockLength);
            this.Y.row0 = j;
            if (!BlockHouseHolder_MT_DDRB.decomposeQR_block_col(this.blockLength, this.Y, this.gammas)) {
                return false;
            }
            this.updateA(this.A);
        }
        return true;
    }

    private void setup(DMatrixRBlock orig) {
        this.dataW.blockLength = this.blockLength = orig.blockLength;
        this.dataWTA.blockLength = this.blockLength;
        this.dataA = orig;
        this.A.original = this.dataA;
        int l = Math.min(this.blockLength, orig.numCols);
        this.dataW.reshape(orig.numRows, l, false);
        this.dataWTA.reshape(l, orig.numRows, false);
        this.Y.original = orig;
        this.Y.row1 = this.W.row1 = orig.numRows;
        if (this.gammas.length < orig.numCols) {
            this.gammas = new double[orig.numCols];
        }
        if (this.saveW) {
            this.dataW.reshape(orig.numRows, orig.numCols, false);
        }
    }

    protected void updateA(DSubmatrixD1 A) {
        this.setW();
        A.row0 = this.Y.row0;
        A.row1 = this.Y.row1;
        A.col0 = this.Y.col1;
        A.col1 = ((DMatrixD1)this.Y.original).numCols;
        this.WTA.row0 = 0;
        this.WTA.col0 = 0;
        this.WTA.row1 = this.W.col1 - this.W.col0;
        this.WTA.col1 = A.col1 - A.col0;
        ((DMatrixD1)this.WTA.original).reshape(this.WTA.row1, this.WTA.col1, false);
        if (A.col1 > A.col0) {
            BlockHouseHolder_MT_DDRB.computeW_Column(this.blockLength, this.Y, this.W, this.workspace, this.gammas, this.Y.col0);
            MatrixMult_MT_DDRB.multTransA(this.blockLength, this.W, A, this.WTA);
            BlockHouseHolder_MT_DDRB.multAdd_zeros(this.blockLength, this.Y, this.WTA, A);
        } else if (this.saveW) {
            BlockHouseHolder_MT_DDRB.computeW_Column(this.blockLength, this.Y, this.W, this.workspace, this.gammas, this.Y.col0);
        }
    }

    private void setW() {
        if (this.saveW) {
            this.W.col0 = this.Y.col0;
            this.W.col1 = this.Y.col1;
            this.W.row0 = this.Y.row0;
            this.W.row1 = this.Y.row1;
        } else {
            this.W.col1 = this.Y.col1 - this.Y.col0;
            this.W.row0 = this.Y.row0;
        }
    }

    @Override
    public boolean inputModified() {
        return true;
    }
}

