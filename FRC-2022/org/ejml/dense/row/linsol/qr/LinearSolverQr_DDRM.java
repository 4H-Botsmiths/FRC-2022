/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.SpecializedOps_DDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_DDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_DDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;

public class LinearSolverQr_DDRM
extends LinearSolverAbstract_DDRM {
    private QRDecomposition<DMatrixRMaj> decomposer;
    protected int maxRows = -1;
    protected int maxCols = -1;
    protected DMatrixRMaj Q;
    protected DMatrixRMaj R;
    private DMatrixRMaj Y;
    private DMatrixRMaj Z;

    public LinearSolverQr_DDRM(QRDecomposition<DMatrixRMaj> decomposer) {
        this.decomposer = decomposer;
    }

    public void setMaxSize(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.Q = new DMatrixRMaj(maxRows, maxRows);
        this.R = new DMatrixRMaj(maxRows, maxCols);
        this.Y = new DMatrixRMaj(maxRows, 1);
        this.Z = new DMatrixRMaj(maxRows, 1);
    }

    @Override
    public boolean setA(DMatrixRMaj A) {
        if (A.numRows > this.maxRows || A.numCols > this.maxCols) {
            this.setMaxSize(A.numRows, A.numCols);
        }
        this._setA(A);
        if (!this.decomposer.decompose(A)) {
            return false;
        }
        this.Q.reshape(this.numRows, this.numRows, false);
        this.R.reshape(this.numRows, this.numCols, false);
        this.decomposer.getQ(this.Q, false);
        this.decomposer.getR(this.R, false);
        return true;
    }

    @Override
    public double quality() {
        return SpecializedOps_DDRM.qualityTriangular(this.R);
    }

    @Override
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int BnumCols = B.numCols;
        this.Y.reshape(this.numRows, 1, false);
        this.Z.reshape(this.numRows, 1, false);
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            for (i = 0; i < this.numRows; ++i) {
                this.Y.data[i] = B.get(i, colB);
            }
            CommonOps_DDRM.multTransA(this.Q, this.Y, this.Z);
            TriangularSolver_DDRM.solveU(this.R.data, this.Z.data, this.numCols);
            for (i = 0; i < this.numCols; ++i) {
                X.set(i, colB, this.Z.data[i]);
            }
        }
    }

    @Override
    public boolean modifiesA() {
        return this.decomposer.inputModified();
    }

    @Override
    public boolean modifiesB() {
        return false;
    }

    @Override
    public QRDecomposition<DMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }

    public QRDecomposition<DMatrixRMaj> getDecomposer() {
        return this.decomposer;
    }

    public DMatrixRMaj getQ() {
        return this.Q;
    }

    public DMatrixRMaj getR() {
        return this.R;
    }
}

