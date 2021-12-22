/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_ZDRM;
import org.ejml.dense.row.decompose.TriangularSolver_ZDRM;
import org.ejml.dense.row.decompose.qr.QRDecompositionHouseholderColumn_ZDRM;
import org.ejml.dense.row.decompose.qr.QrHelperFunctions_ZDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_ZDRM;

public class LinearSolverQrHouseCol_ZDRM
extends LinearSolverAbstract_ZDRM {
    private final QRDecompositionHouseholderColumn_ZDRM decomposer;
    private final ZMatrixRMaj a = new ZMatrixRMaj(1, 1);
    private final ZMatrixRMaj temp = new ZMatrixRMaj(1, 1);
    protected int maxRows = -1;
    protected int maxCols = -1;
    private double[][] QR;
    private final ZMatrixRMaj R = new ZMatrixRMaj(1, 1);
    private double[] gammas;

    public LinearSolverQrHouseCol_ZDRM() {
        this.decomposer = new QRDecompositionHouseholderColumn_ZDRM();
    }

    public void setMaxSize(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
    }

    @Override
    public boolean setA(ZMatrixRMaj A) {
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
        return SpecializedOps_ZDRM.qualityTriangular(this.R);
    }

    @Override
    public void solve(ZMatrixRMaj B, ZMatrixRMaj X) {
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
                double[] u = this.QR[n];
                double realVV = u[n * 2];
                double imagVV = u[n * 2 + 1];
                u[n * 2] = 1.0;
                u[n * 2 + 1] = 0.0;
                QrHelperFunctions_ZDRM.rank1UpdateMultR(this.a, u, 0, this.gammas[n], 0, n, this.numRows, this.temp.data);
                u[n * 2] = realVV;
                u[n * 2 + 1] = imagVV;
            }
            TriangularSolver_ZDRM.solveU(this.R.data, this.a.data, this.numCols);
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
    public QRDecompositionHouseholderColumn_ZDRM getDecomposition() {
        return this.decomposer;
    }
}

