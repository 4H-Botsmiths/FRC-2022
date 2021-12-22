/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decompose.qr;

import org.ejml.data.CMatrixRMaj;
import org.ejml.data.Complex_F32;
import org.ejml.dense.row.CommonOps_CDRM;
import org.ejml.dense.row.decompose.UtilDecompositons_CDRM;
import org.ejml.dense.row.decompose.qr.QrHelperFunctions_CDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.jetbrains.annotations.Nullable;

public class QRDecompositionHouseholderTran_CDRM
implements QRDecomposition<CMatrixRMaj> {
    protected CMatrixRMaj QR;
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
        if (this.QR == null) {
            this.QR = new CMatrixRMaj(numCols, numRows);
            this.v = new float[maxLength * 2];
            this.gammas = new float[this.minLength];
        } else {
            this.QR.reshape(numCols, numRows);
        }
        if (this.v.length < maxLength * 2) {
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
            int diagIndex = (j * this.numRows + j) * 2;
            float realBefore = this.QR.data[diagIndex];
            float imagBefore = this.QR.data[diagIndex + 1];
            this.QR.data[diagIndex] = 1.0f;
            this.QR.data[diagIndex + 1] = 0.0f;
            QrHelperFunctions_CDRM.rank1UpdateMultR(Q, this.QR.data, j * this.numRows, this.gammas[j], j, j, this.numRows, this.v);
            this.QR.data[diagIndex] = realBefore;
            this.QR.data[diagIndex + 1] = imagBefore;
        }
        return Q;
    }

    public void applyQ(CMatrixRMaj A) {
        if (A.numRows != this.numRows) {
            throw new IllegalArgumentException("A must have at least " + this.numRows + " rows.");
        }
        for (int j = this.minLength - 1; j >= 0; --j) {
            int diagIndex = (j * this.numRows + j) * 2;
            float realBefore = this.QR.data[diagIndex];
            float imagBefore = this.QR.data[diagIndex + 1];
            this.QR.data[diagIndex] = 1.0f;
            this.QR.data[diagIndex + 1] = 0.0f;
            QrHelperFunctions_CDRM.rank1UpdateMultR(A, this.QR.data, j * this.numRows, this.gammas[j], 0, j, this.numRows, this.v);
            this.QR.data[diagIndex] = realBefore;
            this.QR.data[diagIndex + 1] = imagBefore;
        }
    }

    public void applyTranQ(CMatrixRMaj A) {
        for (int j = 0; j < this.minLength; ++j) {
            int diagIndex = (j * this.numRows + j) * 2;
            float realBefore = this.QR.data[diagIndex];
            float imagBefore = this.QR.data[diagIndex + 1];
            this.QR.data[diagIndex] = 1.0f;
            this.QR.data[diagIndex + 1] = 0.0f;
            QrHelperFunctions_CDRM.rank1UpdateMultR(A, this.QR.data, j * this.numRows, this.gammas[j], 0, j, this.numRows, this.v);
            this.QR.data[diagIndex] = realBefore;
            this.QR.data[diagIndex + 1] = imagBefore;
        }
    }

    @Override
    public CMatrixRMaj getR(@Nullable CMatrixRMaj R, boolean compact) {
        R = compact ? UtilDecompositons_CDRM.checkZerosLT(R, this.minLength, this.numCols) : UtilDecompositons_CDRM.checkZerosLT(R, this.numRows, this.numCols);
        for (int i = 0; i < R.numRows; ++i) {
            for (int j = i; j < R.numCols; ++j) {
                int index = this.QR.getIndex(j, i);
                R.set(i, j, this.QR.data[index], this.QR.data[index + 1]);
            }
        }
        return R;
    }

    @Override
    public boolean decompose(CMatrixRMaj A) {
        this.setExpectedMaxSize(A.numRows, A.numCols);
        CommonOps_CDRM.transpose(A, this.QR);
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

    protected void householder(int j) {
        int startQR = j * this.numRows;
        int endQR = startQR + this.numRows;
        float max = QrHelperFunctions_CDRM.findMax(this.QR.data, startQR += j, this.numRows - j);
        if (max == 0.0f) {
            this.gamma = 0.0f;
            this.error = true;
        } else {
            this.gamma = QrHelperFunctions_CDRM.computeTauGammaAndDivide(startQR, endQR, this.QR.data, max, this.tau);
            float realU0 = this.QR.data[startQR * 2] + this.tau.real;
            float imagU0 = this.QR.data[startQR * 2 + 1] + this.tau.imaginary;
            QrHelperFunctions_CDRM.divideElements(startQR + 1, endQR, this.QR.data, 0, realU0, imagU0);
            this.tau.real *= max;
            this.tau.imaginary *= max;
            this.QR.data[startQR * 2] = -this.tau.real;
            this.QR.data[startQR * 2 + 1] = -this.tau.imaginary;
        }
        this.gammas[j] = this.gamma;
    }

    protected void updateA(int w) {
        float[] data = this.QR.data;
        int rowW = w * this.numRows + w + 1;
        int rowJ = rowW + this.numRows;
        int rowJEnd = 2 * (rowJ + (this.numCols - w - 1) * this.numRows);
        int indexWEnd = 2 * (rowW + this.numRows - w - 1);
        rowW = 2 * rowW;
        for (rowJ = 2 * rowJ; rowJEnd != rowJ; rowJ += this.numRows * 2) {
            float imagW;
            float realW;
            float realVal = data[rowJ - 2];
            float imagVal = data[rowJ - 1];
            int indexW = rowW;
            int indexJ = rowJ;
            while (indexW != indexWEnd) {
                realW = data[indexW++];
                imagW = -data[indexW++];
                float realJ = data[indexJ++];
                float imagJ = data[indexJ++];
                realVal += realW * realJ - imagW * imagJ;
                imagVal += realW * imagJ + imagW * realJ;
            }
            int n = rowJ - 2;
            data[n] = data[n] - (realVal *= this.gamma);
            int n2 = rowJ - 1;
            data[n2] = data[n2] - (imagVal *= this.gamma);
            indexW = rowW;
            indexJ = rowJ;
            while (indexW != indexWEnd) {
                realW = data[indexW++];
                imagW = data[indexW++];
                int n3 = indexJ++;
                data[n3] = data[n3] - (realW * realVal - imagW * imagVal);
                int n4 = indexJ++;
                data[n4] = data[n4] - (realW * imagVal + imagW * realVal);
            }
        }
    }

    public float[] getGammas() {
        return this.gammas;
    }
}

