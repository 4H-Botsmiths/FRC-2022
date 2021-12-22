/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block.linsol.qr;

import org.ejml.data.DMatrixRBlock;
import org.ejml.data.DSubmatrixD1;
import org.ejml.dense.block.MatrixOps_DDRB;
import org.ejml.dense.block.TriangularSolver_DDRB;
import org.ejml.dense.block.decomposition.qr.QRDecompositionHouseholder_DDRB;
import org.ejml.dense.row.SpecializedOps_DDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class QrHouseHolderSolver_DDRB
implements LinearSolverDense<DMatrixRBlock> {
    protected QRDecompositionHouseholder_DDRB decomposer = new QRDecompositionHouseholder_DDRB();
    protected DMatrixRBlock QR;

    public QrHouseHolderSolver_DDRB() {
        this.decomposer.setSaveW(false);
    }

    @Override
    public boolean setA(DMatrixRBlock A) {
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
        return SpecializedOps_DDRM.qualityTriangular(this.decomposer.getQR());
    }

    @Override
    public void solve(DMatrixRBlock B, DMatrixRBlock X) {
        if (B.numRows != this.QR.numRows) {
            throw new IllegalArgumentException("Row of B and A do not match");
        }
        X.reshape(this.QR.numCols, B.numCols);
        this.decomposer.applyQTran(B);
        MatrixOps_DDRB.extractAligned(B, X);
        int M = Math.min(this.QR.numRows, this.QR.numCols);
        TriangularSolver_DDRB.solve(this.QR.blockLength, true, new DSubmatrixD1(this.QR, 0, M, 0, M), new DSubmatrixD1(X), false);
    }

    @Override
    public void invert(DMatrixRBlock A_inv) {
        int M = Math.min(this.QR.numRows, this.QR.numCols);
        if (A_inv.numRows != M || A_inv.numCols != M) {
            throw new IllegalArgumentException("A_inv must be square an have dimension " + M);
        }
        MatrixOps_DDRB.setIdentity(A_inv);
        this.decomposer.applyQTran(A_inv);
        TriangularSolver_DDRB.solve(this.QR.blockLength, true, new DSubmatrixD1(this.QR, 0, M, 0, M), new DSubmatrixD1(A_inv), false);
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
    public QRDecomposition<DMatrixRBlock> getDecomposition() {
        return this.decomposer;
    }
}

