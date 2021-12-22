/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_DDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_DDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholder_DDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_DDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;

public class LinearSolverQrHouse_DDRM
extends LinearSolverAbstract_DDRM {
    private final QRDecompositionHouseholder_DDRM decomposer = new QRDecompositionHouseholder_DDRM();
    private double[] a;
    private double[] u;
    private int maxRows = -1;
    private DMatrixRMaj QR;
    private double[] gammas;

    public void setMaxSize(int maxRows) {
        this.maxRows = maxRows;
        this.a = new double[maxRows];
        this.u = new double[maxRows];
    }

    @Override
    public boolean setA(DMatrixRMaj A) {
        if (A.numRows > this.maxRows) {
            this.setMaxSize(A.numRows);
        }
        this._setA(A);
        if (!this.decomposer.decompose(A)) {
            return false;
        }
        this.gammas = this.decomposer.getGammas();
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
        int BnumCols = B.numCols;
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            for (i = 0; i < this.numRows; ++i) {
                this.a[i] = B.data[i * BnumCols + colB];
            }
            for (int n = 0; n < this.numCols; ++n) {
                int i2;
                this.u[n] = 1.0;
                double ub = this.a[n];
                for (i2 = n + 1; i2 < this.numRows; ++i2) {
                    this.u[i2] = this.QR.unsafe_get(i2, n);
                    ub += this.u[i2] * this.a[i2];
                }
                ub *= this.gammas[n];
                for (i2 = n; i2 < this.numRows; ++i2) {
                    int n2 = i2;
                    this.a[n2] = this.a[n2] - this.u[i2] * ub;
                }
            }
            TriangularSolver_DDRM.solveU(this.QR.data, this.a, this.numCols);
            for (i = 0; i < this.numCols; ++i) {
                X.data[i * X.numCols + colB] = this.a[i];
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
    public QRDecomposition<DMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }
}

