/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_DDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_DDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderTran_DDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_DDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;

public class LinearSolverQrHouseTran_DDRM
extends LinearSolverAbstract_DDRM {
    private QRDecompositionHouseholderTran_DDRM decomposer = new QRDecompositionHouseholderTran_DDRM();
    private double[] a;
    protected int maxRows = -1;
    protected int maxCols = -1;
    private DMatrixRMaj QR;
    private DMatrixRMaj U;

    public void setMaxSize(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.a = new double[maxRows];
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
        this.QR = this.decomposer.getQR();
        return true;
    }

    @Override
    public double quality() {
        return SpecializedOps_DDRM.qualityTriangular(this.QR);
    }

    @Override
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        this.U = this.decomposer.getR(this.U, true);
        double[] gammas = this.decomposer.getGammas();
        double[] dataQR = this.QR.data;
        int BnumCols = B.numCols;
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            for (i = 0; i < this.numRows; ++i) {
                this.a[i] = B.data[i * BnumCols + colB];
            }
            for (int n = 0; n < this.numCols; ++n) {
                int indexU = n * this.numRows + n + 1;
                double ub = this.a[n];
                int i2 = n + 1;
                while (i2 < this.numRows) {
                    ub += dataQR[indexU] * this.a[i2];
                    ++i2;
                    ++indexU;
                }
                int n2 = n;
                this.a[n2] = this.a[n2] - (ub *= gammas[n]);
                indexU = n * this.numRows + n + 1;
                i2 = n + 1;
                while (i2 < this.numRows) {
                    int n3 = i2++;
                    this.a[n3] = this.a[n3] - dataQR[indexU] * ub;
                    ++indexU;
                }
            }
            TriangularSolver_DDRM.solveU(this.U.data, this.a, this.numCols);
            for (i = 0; i < this.numCols; ++i) {
                X.data[i * X.numCols + colB] = this.a[i];
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
}

