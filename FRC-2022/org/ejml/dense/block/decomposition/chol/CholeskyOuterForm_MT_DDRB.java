/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.block.decomposition.chol;

import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrixRBlock;
import org.ejml.data.DSubmatrixD1;
import org.ejml.dense.block.InnerRankUpdate_MT_DDRB;
import org.ejml.dense.block.MatrixOps_DDRB;
import org.ejml.dense.block.TriangularSolver_MT_DDRB;
import org.ejml.dense.block.decomposition.chol.InnerCholesky_DDRB;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F64;
import org.jetbrains.annotations.Nullable;

public class CholeskyOuterForm_MT_DDRB
implements CholeskyDecomposition_F64<DMatrixRBlock> {
    private final boolean lower;
    private DMatrixRBlock T;
    private final DSubmatrixD1 subA = new DSubmatrixD1();
    private final DSubmatrixD1 subB = new DSubmatrixD1();
    private final DSubmatrixD1 subC = new DSubmatrixD1();
    private final Complex_F64 det = new Complex_F64();

    public CholeskyOuterForm_MT_DDRB(boolean lower) {
        this.lower = lower;
    }

    @Override
    public boolean decompose(DMatrixRBlock A) {
        if (A.numCols != A.numRows) {
            throw new IllegalArgumentException("A must be square");
        }
        this.T = A;
        if (this.lower) {
            return this.decomposeLower();
        }
        return this.decomposeUpper();
    }

    private boolean decomposeLower() {
        int blockLength = this.T.blockLength;
        this.subA.set(this.T);
        this.subB.set(this.T);
        this.subC.set(this.T);
        for (int i = 0; i < this.T.numCols; i += blockLength) {
            int widthA = Math.min(blockLength, this.T.numCols - i);
            this.subA.col0 = i;
            this.subA.col1 = i + widthA;
            this.subA.row0 = this.subA.col0;
            this.subA.row1 = this.subA.col1;
            this.subB.col0 = i;
            this.subB.col1 = i + widthA;
            this.subB.row0 = i + widthA;
            this.subB.row1 = this.T.numRows;
            this.subC.col0 = i + widthA;
            this.subC.col1 = this.T.numRows;
            this.subC.row0 = i + widthA;
            this.subC.row1 = this.T.numRows;
            if (!InnerCholesky_DDRB.lower(this.subA)) {
                return false;
            }
            if (widthA != blockLength) continue;
            TriangularSolver_MT_DDRB.solveBlock(blockLength, false, this.subA, this.subB, false, true);
            InnerRankUpdate_MT_DDRB.symmRankNMinus_L(blockLength, this.subC, this.subB);
        }
        MatrixOps_DDRB.zeroTriangle(true, this.T);
        return true;
    }

    private boolean decomposeUpper() {
        int blockLength = this.T.blockLength;
        this.subA.set(this.T);
        this.subB.set(this.T);
        this.subC.set(this.T);
        for (int i = 0; i < this.T.numCols; i += blockLength) {
            int widthA = Math.min(blockLength, this.T.numCols - i);
            this.subA.col0 = i;
            this.subA.col1 = i + widthA;
            this.subA.row0 = this.subA.col0;
            this.subA.row1 = this.subA.col1;
            this.subB.col0 = i + widthA;
            this.subB.col1 = this.T.numCols;
            this.subB.row0 = i;
            this.subB.row1 = i + widthA;
            this.subC.col0 = i + widthA;
            this.subC.col1 = this.T.numCols;
            this.subC.row0 = i + widthA;
            this.subC.row1 = this.T.numCols;
            if (!InnerCholesky_DDRB.upper(this.subA)) {
                return false;
            }
            if (widthA != blockLength) continue;
            TriangularSolver_MT_DDRB.solveBlock(blockLength, true, this.subA, this.subB, true, false);
            InnerRankUpdate_MT_DDRB.symmRankNMinus_U(blockLength, this.subC, this.subB);
        }
        MatrixOps_DDRB.zeroTriangle(false, this.T);
        return true;
    }

    @Override
    public boolean isLower() {
        return this.lower;
    }

    @Override
    public DMatrixRBlock getT(@Nullable DMatrixRBlock T) {
        if (T == null) {
            return this.T;
        }
        T.set(this.T);
        return T;
    }

    @Override
    public Complex_F64 computeDeterminant() {
        double prod = 1.0;
        int blockLength = this.T.blockLength;
        for (int i = 0; i < this.T.numCols; i += blockLength) {
            int widthA = Math.min(blockLength, this.T.numCols - i);
            int indexT = i * this.T.numCols + i * widthA;
            for (int j = 0; j < widthA; ++j) {
                prod *= this.T.data[indexT];
                indexT += widthA + 1;
            }
        }
        this.det.real = prod * prod;
        this.det.imaginary = 0.0;
        return this.det;
    }

    @Override
    public boolean inputModified() {
        return true;
    }
}

