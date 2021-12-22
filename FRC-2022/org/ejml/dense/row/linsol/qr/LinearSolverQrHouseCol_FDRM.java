/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_FDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_FDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderColumn_FDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_FDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_FDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;

public class LinearSolverQrHouseCol_FDRM
extends LinearSolverAbstract_FDRM {
    protected final QRDecompositionHouseholderColumn_FDRM decomposer;
    protected final FMatrixRMaj a = new FMatrixRMaj(1, 1);
    protected final FMatrixRMaj temp = new FMatrixRMaj(1, 1);
    protected int maxRows = -1;
    protected int maxCols = -1;
    protected float[][] QR;
    protected final FMatrixRMaj R = new FMatrixRMaj(1, 1);
    protected float[] gammas;

    public LinearSolverQrHouseCol_FDRM() {
        this(new QRDecompositionHouseholderColumn_FDRM());
    }

    protected LinearSolverQrHouseCol_FDRM(QRDecompositionHouseholderColumn_FDRM decomposer) {
        this.decomposer = decomposer;
    }

    public void setMaxSize(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
    }

    @Override
    public boolean setA(FMatrixRMaj A) {
        if (A.numRows < A.numCols) {
            throw new IllegalArgumentException("Can't solve for wide systems.  More variables than equations.");
        }
        if (A.numRows > this.maxRows || A.numCols > this.maxCols) {
            this.setMaxSize(A.numRows, A.numCols);
        }
        this.R.reshape(A.numCols, A.numCols);
        this.a.reshape(A.numRows, 1);
        this.temp.reshape(A.numRows, 1);
        this._setA(A);
        if (!this.decomposer.decompose(A)) {
            return false;
        }
        this.gammas = this.decomposer.getGammas();
        this.QR = this.decomposer.getQR();
        this.decomposer.getR(this.R, true);
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
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            for (i = 0; i < this.numRows; ++i) {
                this.a.data[i] = B.data[i * BnumCols + colB];
            }
            for (int n = 0; n < this.numCols; ++n) {
                float[] u = this.QR[n];
                QrHelperFunctions_FDRM.rank1UpdateMultR_u0(this.a, u, 1.0f, this.gammas[n], 0, n, this.numRows, this.temp.data);
            }
            TriangularSolver_FDRM.solveU(this.R.data, this.a.data, this.numCols);
            for (i = 0; i < this.numCols; ++i) {
                X.data[i * X.numCols + colB] = this.a.data[i];
            }
        }
    }

    @Override
    public boolean modifiesA() {
        return false;
    }

    @Override
    public boolean modifiesB() {
        return false;
    }

    @Override
    public QRDecomposition<FMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }
}

