/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block.linsol.qr;

import org.ejml.data.FMatrixRBlock;
import org.ejml.data.FSubmatrixD1;
import org.ejml.dense.block.MatrixOps_FDRB;
import org.ejml.dense.block.TriangularSolver_FDRB;
import org.ejml.dense.block.decomposition.qr.QRDecompositionHouseholder_FDRB;
import org.ejml.dense.row.SpecializedOps_FDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class QrHouseHolderSolver_FDRB
implements LinearSolverDense<FMatrixRBlock> {
    protected QRDecompositionHouseholder_FDRB decomposer = new QRDecompositionHouseholder_FDRB();
    protected FMatrixRBlock QR;

    public QrHouseHolderSolver_FDRB() {
        this.decomposer.setSaveW(false);
    }

    @Override
    public boolean setA(FMatrixRBlock A) {
        if (A.numRows < A.numCols) {
            throw new IllegalArgumentException("Number of rows must be more than or equal to the number of columns. Can't solve an underdetermined system.");
        }
        if (!this.decomposer.decompose(A)) {
            return false;
        }
        this.QR = this.decomposer.getQR();
        return true;
    }

    @Override
    public double quality() {
        return SpecializedOps_FDRM.qualityTriangular(this.decomposer.getQR());
    }

    @Override
    public void solve(FMatrixRBlock B, FMatrixRBlock X) {
        if (B.numRows != this.QR.numRows) {
            throw new IllegalArgumentException("Row of B and A do not match");
        }
        X.reshape(this.QR.numCols, B.numCols);
        this.decomposer.applyQTran(B);
        MatrixOps_FDRB.extractAligned(B, X);
        int M = Math.min(this.QR.numRows, this.QR.numCols);
        TriangularSolver_FDRB.solve(this.QR.blockLength, true, new FSubmatrixD1(this.QR, 0, M, 0, M), new FSubmatrixD1(X), false);
    }

    @Override
    public void invert(FMatrixRBlock A_inv) {
        int M = Math.min(this.QR.numRows, this.QR.numCols);
        if (A_inv.numRows != M || A_inv.numCols != M) {
            throw new IllegalArgumentException("A_inv must be square an have dimension " + M);
        }
        MatrixOps_FDRB.setIdentity(A_inv);
        this.decomposer.applyQTran(A_inv);
        TriangularSolver_FDRB.solve(this.QR.blockLength, true, new FSubmatrixD1(this.QR, 0, M, 0, M), new FSubmatrixD1(A_inv), false);
    }

    @Override
    public boolean modifiesA() {
        return this.decomposer.inputModified();
    }

    @Override
    public boolean modifiesB() {
        return true;
    }

    @Override
    public QRDecomposition<FMatrixRBlock> getDecomposition() {
        return this.decomposer;
    }
}

