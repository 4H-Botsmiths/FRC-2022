/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.CMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_CDRM;
import org.ejml.dense.row.decompose.TriangularSolver_CDRM;
import org.ejml.dense.row.decompose.qr.QRDecompositionHouseholderTran_CDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_CDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;

public class LinearSolverQrHouseTran_CDRM
extends LinearSolverAbstract_CDRM {
    private final QRDecompositionHouseholderTran_CDRM decomposer = new QRDecompositionHouseholderTran_CDRM();
    private float[] a;
    protected int maxRows = -1;
    protected int maxCols = -1;
    private CMatrixRMaj QR;
    private CMatrixRMaj U;

    public void setMaxSize(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.a = new float[maxRows * 2];
    }

    @Override
    public boolean setA(CMatrixRMaj A) {
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
        return SpecializedOps_CDRM.qualityTriangular(this.QR);
    }

    @Override
    public void solve(CMatrixRMaj B, CMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        this.U = this.decomposer.getR(this.U, true);
        float[] gammas = this.decomposer.getGammas();
        float[] dataQR = this.QR.data;
        int BnumCols = B.numCols;
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            for (i = 0; i < this.numRows; ++i) {
                int indexB = (i * BnumCols + colB) * 2;
                this.a[i * 2] = B.data[indexB];
                this.a[i * 2 + 1] = B.data[indexB + 1];
            }
            for (int n = 0; n < this.numCols; ++n) {
                float imagU;
                float realU;
                int i2;
                int indexU = (n * this.numRows + n + 1) * 2;
                float realUb = this.a[n * 2];
                float imagUb = this.a[n * 2 + 1];
                for (i2 = n + 1; i2 < this.numRows; ++i2) {
                    realU = dataQR[indexU++];
                    imagU = -dataQR[indexU++];
                    float realB = this.a[i2 * 2];
                    float imagB = this.a[i2 * 2 + 1];
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
            TriangularSolver_CDRM.solveU(this.U.data, this.a, this.numCols);
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
    public QRDecomposition<CMatrixRMaj> getDecomposition() {
        return this.decomposer;
    }
}

