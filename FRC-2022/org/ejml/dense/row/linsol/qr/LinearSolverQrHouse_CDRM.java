/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.CMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_CDRM;
import org.ejml.dense.row.decompose.TriangularSolver_CDRM;
import org.ejml.dense.row.decompose.qr.QRDecompositionHouseholder_CDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_CDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;

public class LinearSolverQrHouse_CDRM
extends LinearSolverAbstract_CDRM {
    private final QRDecompositionHouseholder_CDRM decomposer = new QRDecompositionHouseholder_CDRM();
    private float[] a;
    private float[] u;
    private int maxRows = -1;
    private CMatrixRMaj QR;
    private float[] gammas;

    public void setMaxSize(int maxRows) {
        this.maxRows = maxRows;
        this.a = new float[maxRows * 2];
        this.u = new float[maxRows * 2];
    }

    @Override
    public boolean setA(CMatrixRMaj A) {
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
        return SpecializedOps_CDRM.qualityTriangular(this.QR);
    }

    @Override
    public void solve(CMatrixRMaj B, CMatrixRMaj X) {
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
                this.u[n * 2] = 1.0f;
                this.u[n * 2 + 1] = 0.0f;
                float realUb = this.a[2 * n];
                float imagUb = this.a[2 * n + 1];
                for (i2 = n + 1; i2 < this.numRows; ++i2) {
                    int indexQR = (i2 * this.QR.numCols + n) * 2;
                    float f = this.QR.data[indexQR];
                    this.u[i2 * 2] = f;
                    float realU = f;
                    float f2 = this.QR.data[indexQR + 1];
                    this.u[i2 * 2 + 1] = f2;
                    float imagU = f2;
                    float realB = this.a[i2 * 2];
                    float imagB = this.a[i2 * 2 + 1];
                    realUb += realU * realB + imagU * imagB;
                    imagUb += realU * imagB - imagU * realB;
                }
                realUb *= this.gammas[n];
                imagUb *= this.gammas[n];
                for (i2 = n; i2 < this.numRows; ++i2) {
                    float realU = this.u[i2 * 2];
                    float imagU = this.u[i2 * 2 + 1];
                    int n2 = i2 * 2;
                    this.a[n2] = this.a[n2] - (realU * realUb - imagU * imagUb);
                    int n3 = i2 * 2 + 1;
                    this.a[n3] = this.a[n3] - (realU * imagUb + imagU * realUb);
                }
            }
            TriangularSolver_CDRM.solveU(this.QR.data, this.a, this.numCols);
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
    public QRDecomposition<CMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }
}

