/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.block.decomposition.qr;

import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixD1;
import org.ejml.data.FMatrixRBlock;
import org.ejml.data.FSubmatrixD1;
import org.ejml.dense.block.MatrixMult_FDRB;
import org.ejml.dense.block.MatrixOps_FDRB;
import org.ejml.dense.block.decomposition.qr.BlockHouseHolder_FDRB;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.jetbrains.annotations.Nullable;
import pabeles.concurrency.GrowArray;

public class QRDecompositionHouseholder_FDRB
implements QRDecomposition<FMatrixRBlock> {
    private FMatrixRBlock dataA;
    private final FMatrixRBlock dataW = new FMatrixRBlock(1, 1);
    private final FMatrixRBlock dataWTA = new FMatrixRBlock(1, 1);
    private int blockLength;
    private final FSubmatrixD1 A = new FSubmatrixD1();
    private final FSubmatrixD1 Y = new FSubmatrixD1();
    private final FSubmatrixD1 W = new FSubmatrixD1(this.dataW);
    private final FSubmatrixD1 WTA = new FSubmatrixD1(this.dataWTA);
    private final GrowArray<FGrowArray> workspace = new GrowArray<FGrowArray>(FGrowArray::new);
    private float[] gammas = new float[1];
    private boolean saveW = false;

    public FMatrixRBlock getQR() {
        return this.dataA;
    }

    public void setSaveW(boolean saveW) {
        this.saveW = saveW;
    }

    @Override
    public FMatrixRBlock getQ(@Nullable FMatrixRBlock Q, boolean compact) {
        Q = QRDecompositionHouseholder_FDRB.initializeQ(Q, this.dataA.numRows, this.dataA.numCols, this.blockLength, compact);
        this.applyQ(Q, true);
        return Q;
    }

    public static FMatrixRBlock initializeQ(@Nullable FMatrixRBlock Q, int numRows, int numCols, int blockLength, boolean compact) {
        int minLength = Math.min(numRows, numCols);
        if (compact) {
            if (Q == null) {
                Q = new FMatrixRBlock(numRows, minLength, blockLength);
                MatrixOps_FDRB.setIdentity(Q);
            } else {
                if (Q.numRows != numRows || Q.numCols != minLength) {
                    throw new IllegalArgumentException("Unexpected matrix dimension. Found " + Q.numRows + " " + Q.numCols);
                }
                MatrixOps_FDRB.setIdentity(Q);
            }
        } else if (Q == null) {
            Q = new FMatrixRBlock(numRows, numRows, blockLength);
            MatrixOps_FDRB.setIdentity(Q);
        } else {
            if (Q.numRows != numRows || Q.numCols != numRows) {
                throw new IllegalArgumentException("Unexpected matrix dimension. Found " + Q.numRows + " " + Q.numCols);
            }
            MatrixOps_FDRB.setIdentity(Q);
        }
        return Q;
    }

    public void applyQ(FMatrixRBlock B) {
        this.applyQ(B, false);
    }

    public void applyQ(FMatrixRBlock B, boolean isIdentity) {
        int minDimen = Math.min(this.dataA.numCols, this.dataA.numRows);
        FSubmatrixD1 subB = new FSubmatrixD1(B);
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
            ((FMatrixD1)this.WTA.original).reshape(this.WTA.row1, this.WTA.col1, false);
            if (!this.saveW) {
                BlockHouseHolder_FDRB.computeW_Column(this.blockLength, this.Y, this.W, this.workspace, this.gammas, this.Y.col0);
            }
            BlockHouseHolder_FDRB.multTransA_vecCol(this.blockLength, this.Y, subB, this.WTA);
            MatrixMult_FDRB.multPlus(this.blockLength, this.W, this.WTA, subB);
        }
    }

    public void applyQTran(FMatrixRBlock B) {
        int minDimen = Math.min(this.dataA.numCols, this.dataA.numRows);
        FSubmatrixD1 subB = new FSubmatrixD1(B);
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
            ((FMatrixD1)this.WTA.original).reshape(this.WTA.row1, this.WTA.col1, false);
            if (!this.saveW) {
                BlockHouseHolder_FDRB.computeW_Column(this.blockLength, this.Y, this.W, this.workspace, this.gammas, this.Y.col0);
            }
            MatrixMult_FDRB.multTransA(this.blockLength, this.W, subB, this.WTA);
            BlockHouseHolder_FDRB.multAdd_zeros(this.blockLength, this.Y, this.WTA, subB);
        }
    }

    @Override
    public FMatrixRBlock getR(@Nullable FMatrixRBlock R, boolean compact) {
        int min = Math.min(this.dataA.numRows, this.dataA.numCols);
        if (R == null) {
            R = compact ? new FMatrixRBlock(min, this.dataA.numCols, this.blockLength) : new FMatrixRBlock(this.dataA.numRows, this.dataA.numCols, this.blockLength);
        } else if (compact ? R.numCols != this.dataA.numCols || R.numRows != min : R.numCols != this.dataA.numCols || R.numRows != this.dataA.numRows) {
            throw new IllegalArgumentException("Unexpected dimension.");
        }
        MatrixOps_FDRB.zeroTriangle(false, R);
        MatrixOps_FDRB.copyTriangle(true, this.dataA, R);
        return R;
    }

    @Override
    public boolean decompose(FMatrixRBlock orig) {
        this.setup(orig);
        int m = Math.min(orig.numCols, orig.numRows);
        for (int j = 0; j < m; j += this.blockLength) {
            this.Y.col0 = j;
            this.Y.col1 = Math.min(orig.numCols, this.Y.col0 + this.blockLength);
            this.Y.row0 = j;
            if (!BlockHouseHolder_FDRB.decomposeQR_block_col(this.blockLength, this.Y, this.gammas)) {
                return false;
            }
            this.updateA(this.A);
        }
        return true;
    }

    private void setup(FMatrixRBlock orig) {
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
            this.gammas = new float[orig.numCols];
        }
        if (this.saveW) {
            this.dataW.reshape(orig.numRows, orig.numCols, false);
        }
    }

    protected void updateA(FSubmatrixD1 A) {
        this.setW();
        A.row0 = this.Y.row0;
        A.row1 = this.Y.row1;
        A.col0 = this.Y.col1;
        A.col1 = ((FMatrixD1)this.Y.original).numCols;
        this.WTA.row0 = 0;
        this.WTA.col0 = 0;
        this.WTA.row1 = this.W.col1 - this.W.col0;
        this.WTA.col1 = A.col1 - A.col0;
        ((FMatrixD1)this.WTA.original).reshape(this.WTA.row1, this.WTA.col1, false);
        if (A.col1 > A.col0) {
            BlockHouseHolder_FDRB.computeW_Column(this.blockLength, this.Y, this.W, this.workspace, this.gammas, this.Y.col0);
            MatrixMult_FDRB.multTransA(this.blockLength, this.W, A, this.WTA);
            BlockHouseHolder_FDRB.multAdd_zeros(this.blockLength, this.Y, this.WTA, A);
        } else if (this.saveW) {
            BlockHouseHolder_FDRB.computeW_Column(this.blockLength, this.Y, this.W, this.workspace, this.gammas, this.Y.col0);
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

