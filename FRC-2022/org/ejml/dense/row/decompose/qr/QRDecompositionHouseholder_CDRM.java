/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decompose.qr;

import org.ejml.data.CMatrixRMaj;
import org.ejml.data.Complex_F32;
import org.ejml.dense.row.decompose.UtilDecompositons_CDRM;
import org.ejml.dense.row.decompose.qr.QrHelperFunctions_CDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.jetbrains.annotations.Nullable;

public class QRDecompositionHouseholder_CDRM
implements QRDecomposition<CMatrixRMaj> {
    protected CMatrixRMaj QR;
    protected float[] u;
    protected float[] v;
    protected int numCols;
    protected int numRows;
    protected int minLength;
    protected float[] dataQR;
    protected float[] gammas;
    protected Complex_F32 tau = new Complex_F32();
    protected boolean error;

    public void setExpectedMaxSize(int numRows, int numCols) {
        this.error = false;
        this.numCols = numCols;
        this.numRows = numRows;
        this.minLength = Math.min(numRows, numCols);
        int maxLength = Math.max(numRows, numCols);
        if (this.QR == null) {
            this.QR = new CMatrixRMaj(numRows, numCols);
            this.u = new float[maxLength * 2];
            this.v = new float[maxLength * 2];
            this.gammas = new float[this.minLength];
        } else {
            this.QR.reshape(numRows, numCols);
        }
        this.dataQR = this.QR.data;
        if (this.u.length < maxLength * 2) {
            this.u = new float[maxLength * 2];
            this.v = new float[maxLength * 2];
        }
        if (this.gammas.length < this.minLength) {
            this.gammas = new float[this.minLength];
        }
    }

    public CMatrixRMaj getQR() {
        return this.QR;
    }

    @Override
    public CMatrixRMaj getQ(@Nullable CMatrixRMaj Q, boolean compact) {
        Q = compact ? UtilDecompositons_CDRM.checkIdentity(Q, this.numRows, this.minLength) : UtilDecompositons_CDRM.checkIdentity(Q, this.numRows, this.numRows);
        for (int j = this.minLength - 1; j >= 0; --j) {
            QrHelperFunctions_CDRM.extractHouseholderColumn(this.QR, j, this.numRows, j, this.u, 0);
            QrHelperFunctions_CDRM.rank1UpdateMultR(Q, this.u, 0, this.gammas[j], j, j, this.numRows, this.v);
        }
        return Q;
    }

    @Override
    public CMatrixRMaj getR(@Nullable CMatrixRMaj R, boolean compact) {
        R = compact ? UtilDecompositons_CDRM.checkZerosLT(R, this.minLength, this.numCols) : UtilDecompositons_CDRM.checkZerosLT(R, this.numRows, this.numCols);
        for (int i = 0; i < this.minLength; ++i) {
            for (int j = i; j < this.numCols; ++j) {
                int indexQR = this.QR.getIndex(i, j);
                float realQR = this.QR.data[indexQR];
                float imagQR = this.QR.data[indexQR + 1];
                R.set(i, j, realQR, imagQR);
            }
        }
        return R;
    }

    @Override
    public boolean decompose(CMatrixRMaj A) {
        this.commonSetup(A);
        for (int j = 0; j < this.minLength; ++j) {
            this.householder(j);
        }
        return !this.error;
    }

    @Override
    public boolean inputModified() {
        return false;
    }

    protected void householder(int j) {
        float max = QrHelperFunctions_CDRM.extractColumnAndMax(this.QR, j, this.numRows, j, this.u, 0);
        if (max <= 0.0f) {
            this.gammas[j] = 0.0f;
            this.error = true;
        } else {
            float gamma;
            this.gammas[j] = gamma = QrHelperFunctions_CDRM.computeTauGammaAndDivide(j, this.numRows, this.u, max, this.tau);
            float real_u_0 = this.u[j * 2] + this.tau.real;
            float imag_u_0 = this.u[j * 2 + 1] + this.tau.imaginary;
            QrHelperFunctions_CDRM.divideElements(j + 1, this.numRows, this.u, 0, real_u_0, imag_u_0);
            for (int i = j + 1; i < this.numRows; ++i) {
                this.dataQR[(i * this.numCols + j) * 2] = this.u[i * 2];
                this.dataQR[(i * this.numCols + j) * 2 + 1] = this.u[i * 2 + 1];
            }
            this.u[j * 2] = 1.0f;
            this.u[j * 2 + 1] = 0.0f;
            QrHelperFunctions_CDRM.rank1UpdateMultR(this.QR, this.u, 0, gamma, j + 1, j, this.numRows, this.v);
            if (j < this.numCols) {
                this.dataQR[(j * this.numCols + j) * 2] = -this.tau.real * max;
                this.dataQR[(j * this.numCols + j) * 2 + 1] = -this.tau.imaginary * max;
            }
        }
    }

    protected void commonSetup(CMatrixRMaj A) {
        this.setExpectedMaxSize(A.numRows, A.numCols);
        this.QR.setTo(A);
    }

    public float[] getGammas() {
        return this.gammas;
    }
}

