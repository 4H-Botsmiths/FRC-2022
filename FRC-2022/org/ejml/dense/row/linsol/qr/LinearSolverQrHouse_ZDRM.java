/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_ZDRM;
import org.ejml.dense.row.decompose.TriangularSolver_ZDRM;
import org.ejml.dense.row.decompose.qr.QRDecompositionHouseholder_ZDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_ZDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;

public class LinearSolverQrHouse_ZDRM
extends LinearSolverAbstract_ZDRM {
    private final QRDecompositionHouseholder_ZDRM decomposer = new QRDecompositionHouseholder_ZDRM();
    private double[] a;
    private double[] u;
    private int maxRows = -1;
    private ZMatrixRMaj QR;
    private double[] gammas;

    public void setMaxSize(int maxRows) {
        this.maxRows = maxRows;
        this.a = new double[maxRows * 2];
        this.u = new double[maxRows * 2];
    }

    @Override
    public boolean setA(ZMatrixRMaj A) {
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
        return SpecializedOps_ZDRM.qualityTriangular(this.QR);
    }

    @Override
    public void solve(ZMatrixRMaj B, ZMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int BnumCols = B.numCols;
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            for (i = 0; i < this.numRows; ++i) {
                int indexB = (i * BnumCols + colB) * 2;
                this.a[i * 2] = B.data[indexB];
                this.a[i * 2 + 1] = B.data[indexB + 1];
            }
            for (int n = 0; n < this.numCols; ++n) {
                int i2;
                this.u[n * 2] = 1.0;
                this.u[n * 2 + 1] = 0.0;
                double realUb = this.a[2 * n];
                double imagUb = this.a[2 * n + 1];
                for (i2 = n + 1; i2 < this.numRows; ++i2) {
                    int indexQR = (i2 * this.QR.numCols + n) * 2;
                    double d = this.QR.data[indexQR];
                    this.u[i2 * 2] = d;
                    double realU = d;
                    double d2 = this.QR.data[indexQR + 1];
                    this.u[i2 * 2 + 1] = d2;
                    double imagU = d2;
                    double realB = this.a[i2 * 2];
                    double imagB = this.a[i2 * 2 + 1];
                    realUb += realU * realB + imagU * imagB;
                    imagUb += realU * imagB - imagU * realB;
                }
                realUb *= this.gammas[n];
                imagUb *= this.gammas[n];
                for (i2 = n; i2 < this.numRows; ++i2) {
                    double realU = this.u[i2 * 2];
                    double imagU = this.u[i2 * 2 + 1];
                    int n2 = i2 * 2;
                    this.a[n2] = this.a[n2] - (realU * realUb - imagU * imagUb);
                    int n3 = i2 * 2 + 1;
                    this.a[n3] = this.a[n3] - (realU * imagUb + imagU * realUb);
                }
            }
            TriangularSolver_ZDRM.solveU(this.QR.data, this.a, this.numCols);
            for (i = 0; i < this.numCols; ++i) {
                int indexX = (i * X.numCols + colB) * 2;
                X.data[indexX] = this.a[i * 2];
                X.data[indexX + 1] = this.a[i * 2 + 1];
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
    public QRDecomposition<ZMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }
}

