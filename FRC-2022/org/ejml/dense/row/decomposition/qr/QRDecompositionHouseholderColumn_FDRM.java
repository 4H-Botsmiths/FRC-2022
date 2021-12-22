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

public class QRDecompositionHouseholderColumn_FDRM
implements QRDecomposition<FMatrixRMaj> {
    protected float[][] dataQR;
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
        if (this.dataQR == null || this.dataQR.length < numCols || this.dataQR[0].length < numRows) {
            this.dataQR = new float[numCols][numRows];
            this.v = new float[maxLength];
            this.gammas = new float[this.minLength];
        }
        if (this.v.length < maxLength) {
            this.v = new float[maxLength];
        }
        if (this.gammas.length < this.minLength) {
            this.gammas = new float[this.minLength];
        }
    }

    public float[][] getQR() {
        return this.dataQR;
    }

    @Override
    public FMatrixRMaj getQ(@Nullable FMatrixRMaj Q, boolean compact) {
        Q = compact ? UtilDecompositons_FDRM.ensureIdentity(Q, this.numRows, this.minLength) : UtilDecompositons_FDRM.ensureIdentity(Q, this.numRows, this.numRows);
        for (int j = this.minLength - 1; j >= 0; --j) {
            float[] u = this.dataQR[j];
            QrHelperFunctions_FDRM.rank1UpdateMultR_u0(Q, u, 1.0f, this.gammas[j], j, j, this.numRows, this.v);
        }
        return Q;
    }

    @Override
    public FMatrixRMaj getR(@Nullable FMatrixRMaj R, boolean compact) {
        R = compact ? UtilDecompositons_FDRM.checkZerosLT(R, this.minLength, this.numCols) : UtilDecompositons_FDRM.checkZerosLT(R, this.numRows, this.numCols);
        for (int j = 0; j < this.numCols; ++j) {
            float[] colR = this.dataQR[j];
            int l = Math.min(j, this.numRows - 1);
            for (int i = 0; i <= l; ++i) {
                float val = colR[i];
                R.set(i, j, val);
            }
        }
        return R;
    }

    @Override
    public boolean decompose(FMatrixRMaj A) {
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

    protected void convertToColumnMajor(FMatrixRMaj A) {
        for (int x = 0; x < this.numCols; ++x) {
            float[] colQ = this.dataQR[x];
            for (int y = 0; y < this.numRows; ++y) {
                colQ[y] = A.data[y * A.numCols + x];
            }
        }
    }

    protected void householder(int j) {
        float[] u = this.dataQR[j];
        float max = QrHelperFunctions_FDRM.findMax(u, j, this.numRows - j);
        if (max == 0.0f) {
            this.gamma = 0.0f;
            this.error = true;
        } else {
            this.tau = QrHelperFunctions_FDRM.computeTauAndDivide(j, this.numRows, u, max);
            float u_0 = u[j] + this.tau;
            QrHelperFunctions_FDRM.divideElements(j + 1, this.numRows, u, u_0);
            this.gamma = u_0 / this.tau;
            this.tau *= max;
            u[j] = -this.tau;
        }
        this.gammas[j] = this.gamma;
    }

    protected void updateA(int w) {
        float[] u = this.dataQR[w];
        for (int j = w + 1; j < this.numCols; ++j) {
            float[] colQ = this.dataQR[j];
            float val = colQ[w];
            for (int k = w + 1; k < this.numRows; ++k) {
                val += u[k] * colQ[k];
            }
            int n = w;
            colQ[n] = colQ[n] - (val *= this.gamma);
            for (int i = w + 1; i < this.numRows; ++i) {
                int n2 = i;
                colQ[n2] = colQ[n2] - u[i] * val;
            }
        }
    }

    public float[] getGammas() {
        return this.gammas;
    }
}

