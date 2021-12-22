/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decompose.qr;

import org.ejml.data.Complex_F64;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.decompose.UtilDecompositons_ZDRM;
import org.ejml.dense.row.decompose.qr.QrHelperFunctions_ZDRM;
import org.ejml.interfaces.decomposition.QRDecomposition;
import org.jetbrains.annotations.Nullable;

public class QRDecompositionHouseholderColumn_ZDRM
implements QRDecomposition<ZMatrixRMaj> {
    protected double[][] dataQR;
    protected double[] v;
    protected int numCols;
    protected int numRows;
    protected int minLength;
    protected double[] gammas;
    protected double gamma;
    protected Complex_F64 tau = new Complex_F64();
    protected boolean error;

    public void setExpectedMaxSize(int numRows, int numCols) {
        this.numCols = numCols;
        this.numRows = numRows;
        this.minLength = Math.min(numCols, numRows);
        int maxLength = Math.max(numCols, numRows);
        if (this.dataQR == null || this.dataQR.length < numCols || this.dataQR[0].length < numRows * 2) {
            this.dataQR = new double[numCols][numRows * 2];
            this.v = new double[maxLength * 2];
            this.gammas = new double[this.minLength];
        }
        if (this.v.length < maxLength * 2) {
            this.v = new double[maxLength * 2];
        }
        if (this.gammas.length < this.minLength) {
            this.gammas = new double[this.minLength];
        }
    }

    public double[][] getQR() {
        return this.dataQR;
    }

    @Override
    public ZMatrixRMaj getQ(@Nullable ZMatrixRMaj Q, boolean compact) {
        Q = compact ? UtilDecompositons_ZDRM.checkIdentity(Q, this.numRows, this.minLength) : UtilDecompositons_ZDRM.checkIdentity(Q, this.numRows, this.numRows);
        for (int j = this.minLength - 1; j >= 0; --j) {
            double[] u = this.dataQR[j];
            double vvReal = u[j * 2];
            double vvImag = u[j * 2 + 1];
            u[j * 2] = 1.0;
            u[j * 2 + 1] = 0.0;
            double gammaReal = this.gammas[j];
            QrHelperFunctions_ZDRM.rank1UpdateMultR(Q, u, 0, gammaReal, j, j, this.numRows, this.v);
            u[j * 2] = vvReal;
            u[j * 2 + 1] = vvImag;
        }
        return Q;
    }

    @Override
    public ZMatrixRMaj getR(@Nullable ZMatrixRMaj R, boolean compact) {
        R = compact ? UtilDecompositons_ZDRM.checkZerosLT(R, this.minLength, this.numCols) : UtilDecompositons_ZDRM.checkZerosLT(R, this.numRows, this.numCols);
        for (int j = 0; j < this.numCols; ++j) {
            double[] colR = this.dataQR[j];
            int l = Math.min(j, this.numRows - 1);
            for (int i = 0; i <= l; ++i) {
                R.set(i, j, colR[i * 2], colR[i * 2 + 1]);
            }
        }
        return R;
    }

    @Override
    public boolean decompose(ZMatrixRMaj A) {
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

    protected void convertToColumnMajor(ZMatrixRMaj A) {
        for (int x = 0; x < this.numCols; ++x) {
            double[] colQ = this.dataQR[x];
            int indexCol = 0;
            for (int y = 0; y < this.numRows; ++y) {
                int index = (y * this.numCols + x) * 2;
                colQ[indexCol++] = A.data[index];
                colQ[indexCol++] = A.data[index + 1];
            }
        }
    }

    protected void householder(int j) {
        double[] u = this.dataQR[j];
        double max = QrHelperFunctions_ZDRM.findMax(u, j, this.numRows - j);
        if (max == 0.0) {
            this.gamma = 0.0;
            this.error = true;
        } else {
            this.gamma = QrHelperFunctions_ZDRM.computeTauGammaAndDivide(j, this.numRows, u, max, this.tau);
            double real_u_0 = u[j * 2] + this.tau.real;
            double imag_u_0 = u[j * 2 + 1] + this.tau.imaginary;
            QrHelperFunctions_ZDRM.divideElements(j + 1, this.numRows, u, 0, real_u_0, imag_u_0);
            this.tau.real *= max;
            this.tau.imaginary *= max;
            u[j * 2] = -this.tau.real;
            u[j * 2 + 1] = -this.tau.imaginary;
        }
        this.gammas[j] = this.gamma;
    }

    protected void updateA(int w) {
        double[] u = this.dataQR[w];
        for (int j = w + 1; j < this.numCols; ++j) {
            double imagU;
            double realU;
            double[] colQ = this.dataQR[j];
            double realSum = colQ[w * 2];
            double imagSum = colQ[w * 2 + 1];
            for (int k = w + 1; k < this.numRows; ++k) {
                realU = u[k * 2];
                imagU = -u[k * 2 + 1];
                double realQ = colQ[k * 2];
                double imagQ = colQ[k * 2 + 1];
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

    public double[] getGammas() {
        return this.gammas;
    }
}

