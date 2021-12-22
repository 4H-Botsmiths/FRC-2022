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

public class QRDecompositionHouseholderColumn_CDRM
implements QRDecomposition<CMatrixRMaj> {
    protected float[][] dataQR;
    protected float[] v;
    protected int numCols;
    protected int numRows;
    protected int minLength;
    protected float[] gammas;
    protected float gamma;
    protected Complex_F32 tau = new Complex_F32();
    protected boolean error;

    public void setExpectedMaxSize(int numRows, int numCols) {
        this.numCols = numCols;
        this.numRows = numRows;
        this.minLength = Math.min(numCols, numRows);
        int maxLength = Math.max(numCols, numRows);
        if (this.dataQR == null || this.dataQR.length < numCols || this.dataQR[0].length < numRows * 2) {
            this.dataQR = new float[numCols][numRows * 2];
            this.v = new float[maxLength * 2];
            this.gammas = new float[this.minLength];
        }
        if (this.v.length < maxLength * 2) {
            this.v = new float[maxLength * 2];
        }
        if (this.gammas.length < this.minLength) {
            this.gammas = new float[this.minLength];
        }
    }

    public float[][] getQR() {
        return this.dataQR;
    }

    @Override
    public CMatrixRMaj getQ(@Nullable CMatrixRMaj Q, boolean compact) {
        Q = compact ? UtilDecompositons_CDRM.checkIdentity(Q, this.numRows, this.minLength) : UtilDecompositons_CDRM.checkIdentity(Q, this.numRows, this.numRows);
        for (int j = this.minLength - 1; j >= 0; --j) {
            float[] u = this.dataQR[j];
            float vvReal = u[j * 2];
            float vvImag = u[j * 2 + 1];
            u[j * 2] = 1.0f;
            u[j * 2 + 1] = 0.0f;
            float gammaReal = this.gammas[j];
            QrHelperFunctions_CDRM.rank1UpdateMultR(Q, u, 0, gammaReal, j, j, this.numRows, this.v);
            u[j * 2] = vvReal;
            u[j * 2 + 1] = vvImag;
        }
        return Q;
    }

    @Override
    public CMatrixRMaj getR(@Nullable CMatrixRMaj R, boolean compact) {
        R = compact ? UtilDecompositons_CDRM.checkZerosLT(R, this.minLength, this.numCols) : UtilDecompositons_CDRM.checkZerosLT(R, this.numRows, this.numCols);
        for (int j = 0; j < this.numCols; ++j) {
            float[] colR = this.dataQR[j];
            int l = Math.min(j, this.numRows - 1);
            for (int i = 0; i <= l; ++i) {
                R.set(i, j, colR[i * 2], colR[i * 2 + 1]);
            }
        }
        return R;
    }

    @Override
    public boolean decompose(CMatrixRMaj A) {
        this.setExpectedMaxSize(A.numRows, A.numCols);
        this.convertToColumnMajor(A);
        this.error = false;
        for (int j = 0; j < this.minLength; ++j) {
            this.householder(j);
            this.updateA(j);
        }
        return !this.error;
    }

    @Override
    public boolean inputModified() {
        return false;
    }

    protected void convertToColumnMajor(CMatrixRMaj A) {
        for (int x = 0; x < this.numCols; ++x) {
            float[] colQ = this.dataQR[x];
            int indexCol = 0;
            for (int y = 0; y < this.numRows; ++y) {
                int index = (y * this.numCols + x) * 2;
                colQ[indexCol++] = A.data[index];
                colQ[indexCol++] = A.data[index + 1];
            }
        }
    }

    protected void householder(int j) {
        float[] u = this.dataQR[j];
        float max = QrHelperFunctions_CDRM.findMax(u, j, this.numRows - j);
        if (max == 0.0f) {
            this.gamma = 0.0f;
            this.error = true;
        } else {
            this.gamma = QrHelperFunctions_CDRM.computeTauGammaAndDivide(j, this.numRows, u, max, this.tau);
            float real_u_0 = u[j * 2] + this.tau.real;
            float imag_u_0 = u[j * 2 + 1] + this.tau.imaginary;
            QrHelperFunctions_CDRM.divideElements(j + 1, this.numRows, u, 0, real_u_0, imag_u_0);
            this.tau.real *= max;
            this.tau.imaginary *= max;
            u[j * 2] = -this.tau.real;
            u[j * 2 + 1] = -this.tau.imaginary;
        }
        this.gammas[j] = this.gamma;
    }

    protected void updateA(int w) {
        float[] u = this.dataQR[w];
        for (int j = w + 1; j < this.numCols; ++j) {
            float imagU;
            float realU;
            float[] colQ = this.dataQR[j];
            float realSum = colQ[w * 2];
            float imagSum = colQ[w * 2 + 1];
            for (int k = w + 1; k < this.numRows; ++k) {
                realU = u[k * 2];
                imagU = -u[k * 2 + 1];
                float realQ = colQ[k * 2];
                float imagQ = colQ[k * 2 + 1];
                realSum += realU * realQ - imagU * imagQ;
                imagSum += imagU * realQ + realU * imagQ;
            }
            int n = w * 2;
            colQ[n] = colQ[n] - (realSum *= this.gamma);
            int n2 = w * 2 + 1;
            colQ[n2] = colQ[n2] - (imagSum *= this.gamma);
            for (int i = w + 1; i < this.numRows; ++i) {
                realU = u[i * 2];
                imagU = u[i * 2 + 1];
                int n3 = i * 2;
                colQ[n3] = colQ[n3] - (realU * realSum - imagU * imagSum);
                int n4 = i * 2 + 1;
                colQ[n4] = colQ[n4] - (imagU * realSum + realU * imagSum);
            }
        }
    }

    public float[] getGammas() {
        return this.gammas;
    }
}

