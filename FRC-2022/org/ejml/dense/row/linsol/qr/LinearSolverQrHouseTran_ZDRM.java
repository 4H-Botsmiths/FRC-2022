/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_ZDRM;
import org.ejml.dense.row.decompose.TriangularSolver_ZDRM;
import org.ejml.dense.row.decompose.qr.QRDecompositionHouseholderTran_ZDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_ZDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;

public class LinearSolverQrHouseTran_ZDRM
extends LinearSolverAbstract_ZDRM {
    private final QRDecompositionHouseholderTran_ZDRM decomposer = new QRDecompositionHouseholderTran_ZDRM();
    private double[] a;
    protected int maxRows = -1;
    protected int maxCols = -1;
    private ZMatrixRMaj QR;
    private ZMatrixRMaj U;

    public void setMaxSize(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.a = new double[maxRows * 2];
    }

    @Override
    public boolean setA(ZMatrixRMaj A) {
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
        return SpecializedOps_ZDRM.qualityTriangular(this.QR);
    }

    @Override
    public void solve(ZMatrixRMaj B, ZMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        this.U = this.decomposer.getR(this.U, true);
        double[] gammas = this.decomposer.getGammas();
        double[] dataQR = this.QR.data;
        int BnumCols = B.numCols;
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            for (i = 0; i < this.numRows; ++i) {
                int indexB = (i * BnumCols + colB) * 2;
                this.a[i * 2] = B.data[indexB];
                this.a[i * 2 + 1] = B.data[indexB + 1];
            }
            for (int n = 0; n < this.numCols; ++n) {
                double imagU;
                double realU;
                int i2;
                int indexU = (n * this.numRows + n + 1) * 2;
                double realUb = this.a[n * 2];
                double imagUb = this.a[n * 2 + 1];
                for (i2 = n + 1; i2 < this.numRows; ++i2) {
                    realU = dataQR[indexU++];
                    imagU = -dataQR[indexU++];
                    double realB = this.a[i2 * 2];
                    double imagB = this.a[i2 * 2 + 1];
                    realUb += realU * realB - imagU * imagB;
                    imagUb += realU * imagB + imagU * realB;
                }
                int n2 = n * 2;
                this.a[n2] = this.a[n2] - (realUb *= gammas[n]);
                int n3 = n * 2 + 1;
                this.a[n3] = this.a[n3] - (imagUb *= gammas[n]);
                indexU = (n * this.numRows + n + 1) * 2;
                for (i2 = n + 1; i2 < this.numRows; ++i2) {
                    realU = dataQR[indexU++];
                    imagU = dataQR[indexU++];
                    int n4 = i2 * 2;
                    this.a[n4] = this.a[n4] - (realU * realUb - imagU * imagUb);
                    int n5 = i2 * 2 + 1;
                    this.a[n5] = this.a[n5] - (realU * imagUb + imagU * realUb);
                }
            }
            TriangularSolver_ZDRM.solveU(this.U.data, this.a, this.numCols);
            for (i = 0; i < this.numCols; ++i) {
                int indexX = (i * X.numCols + colB) * 2;
                X.data[indexX] = this.a[i * 2];
                X.data[indexX + 1] = this.a[i * 2 + 1];
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
    public QRDecomposition<ZMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }
}

