/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.CMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_CDRM;
import org.ejml.dense.row.decompose.TriangularSolver_CDRM;
import org.ejml.dense.row.decompose.qr.QRDecompositionHouseholderColumn_CDRM;
import org.ejml.dense.row.decompose.qr.QrHelperFunctions_CDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_CDRM;

public class LinearSolverQrHouseCol_CDRM
extends LinearSolverAbstract_CDRM {
    private final QRDecompositionHouseholderColumn_CDRM decomposer;
    private final CMatrixRMaj a = new CMatrixRMaj(1, 1);
    private final CMatrixRMaj temp = new CMatrixRMaj(1, 1);
    protected int maxRows = -1;
    protected int maxCols = -1;
    private float[][] QR;
    private final CMatrixRMaj R = new CMatrixRMaj(1, 1);
    private float[] gammas;

    public LinearSolverQrHouseCol_CDRM() {
        this.decomposer = new QRDecompositionHouseholderColumn_CDRM();
    }

    public void setMaxSize(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
    }

    @Override
    public boolean setA(CMatrixRMaj A) {
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
        return SpecializedOps_CDRM.qualityTriangular(this.R);
    }

    @Override
    public void solve(CMatrixRMaj B, CMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int BnumCols = B.numCols;
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            for (i = 0; i < this.numRows; ++i) {
                int indexB = (i * BnumCols + colB) * 2;
                this.a.data[i * 2] = B.data[indexB];
                this.a.data[i * 2 + 1] = B.data[indexB + 1];
            }
            for (int n = 0; n < this.numCols; ++n) {
                float[] u = this.QR[n];
                float realVV = u[n * 2];
                float imagVV = u[n * 2 + 1];
                u[n * 2] = 1.0f;
                u[n * 2 + 1] = 0.0f;
                QrHelperFunctions_CDRM.rank1UpdateMultR(this.a, u, 0, this.gammas[n], 0, n, this.numRows, this.temp.data);
                u[n * 2] = realVV;
                u[n * 2 + 1] = imagVV;
            }
            TriangularSolver_CDRM.solveU(this.R.data, this.a.data, this.numCols);
            for (i = 0; i < this.numCols; ++i) {
                int indexB = (i * BnumCols + colB) * 2;
                X.data[indexB] = this.a.data[i * 2];
                X.data[indexB + 1] = this.a.data[i * 2 + 1];
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
    public QRDecompositionHouseholderColumn_CDRM getDecomposition() {
        return this.decomposer;
    }
}

