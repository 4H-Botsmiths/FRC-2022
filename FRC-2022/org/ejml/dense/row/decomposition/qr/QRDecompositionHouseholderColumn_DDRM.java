/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.qr;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.UtilDecompositons_DDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_DDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.jetbrains.annotations.Nullable;

public class QRDecompositionHouseholderColumn_DDRM
implements QRDecomposition<DMatrixRMaj> {
    protected double[][] dataQR;
    protected double[] v;
    protected int numCols;
    protected int numRows;
    protected int minLength;
    protected double[] gammas;
    protected double gamma;
    protected double tau;
    protected boolean error;

    public void setExpectedMaxSize(int numRows, int numCols) {
        this.numCols = numCols;
        this.numRows = numRows;
        this.minLength = Math.min(numCols, numRows);
        int maxLength = Math.max(numCols, numRows);
        if (this.dataQR == null || this.dataQR.length < numCols || this.dataQR[0].length < numRows) {
            this.dataQR = new double[numCols][numRows];
            this.v = new double[maxLength];
            this.gammas = new double[this.minLength];
        }
        if (this.v.length < maxLength) {
            this.v = new double[maxLength];
        }
        if (this.gammas.length < this.minLength) {
            this.gammas = new double[this.minLength];
        }
    }

    public double[][] getQR() {
        return this.dataQR;
    }

    @Override
    public DMatrixRMaj getQ(@Nullable DMatrixRMaj Q, boolean compact) {
        Q = compact ? UtilDecompositons_DDRM.ensureIdentity(Q, this.numRows, this.minLength) : UtilDecompositons_DDRM.ensureIdentity(Q, this.numRows, this.numRows);
        for (int j = this.minLength - 1; j >= 0; --j) {
            double[] u = this.dataQR[j];
            QrHelperFunctions_DDRM.rank1UpdateMultR_u0(Q, u, 1.0, this.gammas[j], j, j, this.numRows, this.v);
        }
        return Q;
    }

    @Override
    public DMatrixRMaj getR(@Nullable DMatrixRMaj R, boolean compact) {
        R = compact ? UtilDecompositons_DDRM.checkZerosLT(R, this.minLength, this.numCols) : UtilDecompositons_DDRM.checkZerosLT(R, this.numRows, this.numCols);
        for (int j = 0; j < this.numCols; ++j) {
            double[] colR = this.dataQR[j];
            int l = Math.min(j, this.numRows - 1);
            for (int i = 0; i <= l; ++i) {
                double val = colR[i];
                R.set(i, j, val);
            }
        }
        return R;
    }

    @Override
    public boolean decompose(DMatrixRMaj A) {
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

    protected void convertToColumnMajor(DMatrixRMaj A) {
        for (int x = 0; x < this.numCols; ++x) {
            double[] colQ = this.dataQR[x];
            for (int y = 0; y < this.numRows; ++y) {
                colQ[y] = A.data[y * A.numCols + x];
            }
        }
    }

    protected void householder(int j) {
        double[] u = this.dataQR[j];
        double max = QrHelperFunctions_DDRM.findMax(u, j, this.numRows - j);
        if (max == 0.0) {
            this.gamma = 0.0;
            this.error = true;
        } else {
            this.tau = QrHelperFunctions_DDRM.computeTauAndDivide(j, this.numRows, u, max);
            double u_0 = u[j] + this.tau;
            QrHelperFunctions_DDRM.divideElements(j + 1, this.numRows, u, u_0);
            this.gamma = u_0 / this.tau;
            this.tau *= max;
            u[j] = -this.tau;
        }
        this.gammas[j] = this.gamma;
    }

    protected void updateA(int w) {
        double[] u = this.dataQR[w];
        for (int j = w + 1; j < this.numCols; ++j) {
            double[] colQ = this.dataQR[j];
            double val = colQ[w];
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

    public double[] getGammas() {
        return this.gammas;
    }
}

