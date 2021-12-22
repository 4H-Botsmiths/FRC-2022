/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.SpecializedOps_FDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_FDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_FDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;

public class LinearSolverQr_FDRM
extends LinearSolverAbstract_FDRM {
    private QRDecomposition<FMatrixRMaj> decomposer;
    protected int maxRows = -1;
    protected int maxCols = -1;
    protected FMatrixRMaj Q;
    protected FMatrixRMaj R;
    private FMatrixRMaj Y;
    private FMatrixRMaj Z;

    public LinearSolverQr_FDRM(QRDecomposition<FMatrixRMaj> decomposer) {
        this.decomposer = decomposer;
    }

    public void setMaxSize(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.Q = new FMatrixRMaj(maxRows, maxRows);
        this.R = new FMatrixRMaj(maxRows, maxCols);
        this.Y = new FMatrixRMaj(maxRows, 1);
        this.Z = new FMatrixRMaj(maxRows, 1);
    }

    @Override
    public boolean setA(FMatrixRMaj A) {
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
        return SpecializedOps_FDRM.qualityTriangular(this.R);
    }

    @Override
    public void solve(FMatrixRMaj B, FMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int BnumCols = B.numCols;
        this.Y.reshape(this.numRows, 1, false);
        this.Z.reshape(this.numRows, 1, false);
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            for (i = 0; i < this.numRows; ++i) {
                this.Y.data[i] = B.get(i, colB);
            }
            CommonOps_FDRM.multTransA(this.Q, this.Y, this.Z);
            TriangularSolver_FDRM.solveU(this.R.data, this.Z.data, this.numCols);
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
    public QRDecomposition<FMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }

    public QRDecomposition<FMatrixRMaj> getDecomposer() {
        return this.decomposer;
    }

    public FMatrixRMaj getQ() {
        return this.Q;
    }

    public FMatrixRMaj getR() {
        return this.R;
    }
}

