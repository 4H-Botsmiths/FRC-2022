/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.qr;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.UtilDecompositons_FDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_FDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.jetbrains.annotations.Nullable;

public class QRDecompositionHouseholder_FDRM
implements QRDecomposition<FMatrixRMaj> {
    protected FMatrixRMaj QR;
    protected float[] u;
    protected float[] v;
    protected int numCols;
    protected int numRows;
    protected int minLength;
    protected float[] dataQR;
    protected float[] gammas;
    protected float gamma;
    protected float tau;
    protected boolean error;

    public void setExpectedMaxSize(int numRows, int numCols) {
        this.error = false;
        this.numCols = numCols;
        this.numRows = numRows;
        this.minLength = Math.min(numRows, numCols);
        int maxLength = Math.max(numRows, numCols);
        if (this.QR == null) {
            this.QR = new FMatrixRMaj(numRows, numCols);
            this.u = new float[maxLength];
            this.v = new float[maxLength];
            this.gammas = new float[this.minLength];
        } else {
            this.QR.reshape(numRows, numCols, false);
        }
        this.dataQR = this.QR.data;
        if (this.u.length < maxLength) {
            this.u = new float[maxLength];
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
            this.u[j] = 1.0f;
            for (int i = j + 1; i < this.numRows; ++i) {
                this.u[i] = this.QR.get(i, j);
            }
            QrHelperFunctions_FDRM.rank1UpdateMultR(Q, this.u, this.gammas[j], j, j, this.numRows, this.v);
        }
        return Q;
    }

    @Override
    public FMatrixRMaj getR(@Nullable FMatrixRMaj R, boolean compact) {
        R = compact ? UtilDecompositons_FDRM.checkZerosLT(R, this.minLength, this.numCols) : UtilDecompositons_FDRM.checkZerosLT(R, this.numRows, this.numCols);
        for (int i = 0; i < this.minLength; ++i) {
            for (int j = i; j < this.numCols; ++j) {
                float val = this.QR.get(i, j);
                R.set(i, j, val);
            }
        }
        return R;
    }

    @Override
    public boolean decompose(FMatrixRMaj A) {
        this.commonSetup(A);
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
        float d;
        int i;
        int index = j + j * this.numCols;
        float max = 0.0f;
        for (i = j; i < this.numRows; ++i) {
            this.u[i] = this.dataQR[index];
            d = this.u[i];
            if (d < 0.0f) {
                d = -d;
            }
            if (max < d) {
                max = d;
            }
            index += this.numCols;
        }
        if (max == 0.0f) {
            this.gamma = 0.0f;
            this.error = true;
        } else {
            this.tau = 0.0f;
            for (i = j; i < this.numRows; ++i) {
                int n = i;
                this.u[n] = this.u[n] / max;
                d = this.u[i];
                this.tau += d * d;
            }
            this.tau = (float)Math.sqrt(this.tau);
            if (this.u[j] < 0.0f) {
                this.tau = -this.tau;
            }
            float u_0 = this.u[j] + this.tau;
            this.gamma = u_0 / this.tau;
            int i2 = j + 1;
            while (i2 < this.numRows) {
                int n = i2++;
                this.u[n] = this.u[n] / u_0;
            }
            this.u[j] = 1.0f;
            this.tau *= max;
        }
        this.gammas[j] = this.gamma;
    }

    protected void updateA(int w) {
        int i;
        for (i = w + 1; i < this.numCols; ++i) {
            this.v[i] = this.u[w] * this.dataQR[w * this.numCols + i];
        }
        for (int k = w + 1; k < this.numRows; ++k) {
            int indexQR = k * this.numCols + w + 1;
            int i2 = w + 1;
            while (i2 < this.numCols) {
                int n = i2++;
                this.v[n] = this.v[n] + this.u[k] * this.dataQR[indexQR++];
            }
        }
        i = w + 1;
        while (i < this.numCols) {
            int n = i++;
            this.v[n] = this.v[n] * this.gamma;
        }
        for (i = w; i < this.numRows; ++i) {
            float valU = this.u[i];
            int indexQR = i * this.numCols + w + 1;
            for (int j = w + 1; j < this.numCols; ++j) {
                int n = indexQR++;
                this.dataQR[n] = this.dataQR[n] - valU * this.v[j];
            }
        }
        if (w < this.numCols) {
            this.dataQR[w + w * this.numCols] = -this.tau;
        }
        for (i = w + 1; i < this.numRows; ++i) {
            this.dataQR[w + i * this.numCols] = this.u[i];
        }
    }

    protected void commonSetup(FMatrixRMaj A) {
        this.setExpectedMaxSize(A.numRows, A.numCols);
        this.QR.setTo(A);
    }

    public float[] getGammas() {
        return this.gammas;
    }
}

