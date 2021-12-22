/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.qr;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.decomposition.UtilDecompositons_FDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_FDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.jetbrains.annotations.Nullable;

public class QRDecompositionHouseholderTran_FDRM
implements QRDecomposition<FMatrixRMaj> {
    protected FMatrixRMaj QR;
    protected float[] v;
    protected int numCols;
    protected int numRows;
    protected int minLength;
    protected float[] gammas;
    protected float gamma;
    protected float tau;
    protected boolean error;

    public void setExpectedMaxSize(int numRows, int numCols) {
        this.numCols = numCols;
        this.numRows = numRows;
        this.minLength = Math.min(numCols, numRows);
        int maxLength = Math.max(numCols, numRows);
        if (this.QR == null) {
            this.QR = new FMatrixRMaj(numCols, numRows);
            this.v = new float[maxLength];
            this.gammas = new float[this.minLength];
        } else {
            this.QR.reshape(numCols, numRows, false);
        }
        if (this.v.length < maxLength) {
            this.v = new float[maxLength];
        }
        if (this.gammas.length < this.minLength) {
            this.gammas = new float[this.minLength];
        }
    }

    public FMatrixRMaj getQR() {
        return this.QR;
    }

    @Override
    public FMatrixRMaj getQ(@Nullable FMatrixRMaj Q, boolean compact) {
        Q = compact ? UtilDecompositons_FDRM.ensureIdentity(Q, this.numRows, this.minLength) : UtilDecompositons_FDRM.ensureIdentity(Q, this.numRows, this.numRows);
        for (int j = this.minLength - 1; j >= 0; --j) {
            int diagIndex = j * this.numRows + j;
            float before = this.QR.data[diagIndex];
            this.QR.data[diagIndex] = 1.0f;
            QrHelperFunctions_FDRM.rank1UpdateMultR(Q, this.QR.data, j * this.numRows, this.gammas[j], j, j, this.numRows, this.v);
            this.QR.data[diagIndex] = before;
        }
        return Q;
    }

    public void applyQ(FMatrixRMaj A) {
        if (A.numRows != this.numRows) {
            throw new IllegalArgumentException("A must have at least " + this.numRows + " rows.");
        }
        for (int j = this.minLength - 1; j >= 0; --j) {
            int diagIndex = j * this.numRows + j;
            float before = this.QR.data[diagIndex];
            this.QR.data[diagIndex] = 1.0f;
            QrHelperFunctions_FDRM.rank1UpdateMultR(A, this.QR.data, j * this.numRows, this.gammas[j], 0, j, this.numRows, this.v);
            this.QR.data[diagIndex] = before;
        }
    }

    public void applyTranQ(FMatrixRMaj A) {
        for (int j = 0; j < this.minLength; ++j) {
            int diagIndex = j * this.numRows + j;
            float before = this.QR.data[diagIndex];
            this.QR.data[diagIndex] = 1.0f;
            QrHelperFunctions_FDRM.rank1UpdateMultR(A, this.QR.data, j * this.numRows, this.gammas[j], 0, j, this.numRows, this.v);
            this.QR.data[diagIndex] = before;
        }
    }

    @Override
    public FMatrixRMaj getR(@Nullable FMatrixRMaj R, boolean compact) {
        R = compact ? UtilDecompositons_FDRM.checkZerosLT(R, this.minLength, this.numCols) : UtilDecompositons_FDRM.checkZerosLT(R, this.numRows, this.numCols);
        for (int i = 0; i < R.numRows; ++i) {
            for (int j = i; j < R.numCols; ++j) {
                R.unsafe_set(i, j, this.QR.unsafe_get(j, i));
            }
        }
        return R;
    }

    @Override
    public boolean decompose(FMatrixRMaj A) {
        this.setExpectedMaxSize(A.numRows, A.numCols);
        CommonOps_FDRM.transpose(A, this.QR);
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
        float max = QrHelperFunctions_FDRM.findMax(this.QR.data, startQR += j, this.numRows - j);
        if (max == 0.0f) {
            this.gamma = 0.0f;
            this.error = true;
        } else {
            this.tau = QrHelperFunctions_FDRM.computeTauAndDivide(startQR, endQR, this.QR.data, max);
            float u_0 = this.QR.data[startQR] + this.tau;
            QrHelperFunctions_FDRM.divideElements(startQR + 1, endQR, this.QR.data, u_0);
            this.gamma = u_0 / this.tau;
            this.tau *= max;
            this.QR.data[startQR] = -this.tau;
        }
        this.gammas[j] = this.gamma;
    }

    protected void updateA(int w) {
        int rowJ;
        float[] data = this.QR.data;
        int rowW = w * this.numRows + w + 1;
        int rowJEnd = rowJ + (this.numCols - w - 1) * this.numRows;
        int indexWEnd = rowW + this.numRows - w - 1;
        for (rowJ = rowW + this.numRows; rowJEnd != rowJ; rowJ += this.numRows) {
            float val = data[rowJ - 1];
            int indexW = rowW;
            int indexJ = rowJ;
            while (indexW != indexWEnd) {
                val += data[indexW++] * data[indexJ++];
            }
            int n = rowJ - 1;
            data[n] = data[n] - (val *= this.gamma);
            indexW = rowW;
            indexJ = rowJ;
            while (indexW != indexWEnd) {
                int n2 = indexJ++;
                data[n2] = data[n2] - data[indexW++] * val;
            }
        }
    }

    public float[] getGammas() {
        return this.gammas;
    }
}

