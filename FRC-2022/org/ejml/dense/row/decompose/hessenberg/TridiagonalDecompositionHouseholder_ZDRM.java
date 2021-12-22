/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decompose.hessenberg;

import java.util.Arrays;
import org.ejml.data.Complex_F64;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.decompose.UtilDecompositons_ZDRM;
import org.ejml.dense.row.decompose.qr.QrHelperFunctions_ZDRM;
import org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition_F64;
import org.jetbrains.annotations.Nullable;

public class TridiagonalDecompositionHouseholder_ZDRM
implements TridiagonalSimilarDecomposition_F64<ZMatrixRMaj> {
    private ZMatrixRMaj QT;
    private int N = 1;
    private double[] w;
    private double[] gammas;
    private double[] b;
    private final Complex_F64 tau = new Complex_F64();

    public TridiagonalDecompositionHouseholder_ZDRM() {
        this.w = new double[this.N * 2];
        this.b = new double[this.N * 2];
        this.gammas = new double[this.N];
    }

    public ZMatrixRMaj getQT() {
        return this.QT;
    }

    @Override
    public void getDiagonal(double[] diag, double[] off) {
        for (int i = 0; i < this.N; ++i) {
            diag[i * 2] = this.QT.data[(i * this.N + i) * 2];
            diag[i * 2 + 1] = this.QT.data[(i * this.N + i) * 2 + 1];
            if (i + 1 >= this.N) continue;
            off[i * 2] = this.QT.data[(i * this.N + i + 1) * 2];
            off[i * 2 + 1] = this.QT.data[(i * this.N + i + 1) * 2 + 1];
        }
    }

    @Override
    public ZMatrixRMaj getT(@Nullable ZMatrixRMaj T) {
        T = UtilDecompositons_ZDRM.checkZeros(T, this.N, this.N);
        T.data[0] = this.QT.data[0];
        T.data[1] = this.QT.data[1];
        for (int i = 1; i < this.N; ++i) {
            T.set(i, i, this.QT.getReal(i, i), this.QT.getImag(i, i));
            double real = this.QT.getReal(i - 1, i);
            double imag = this.QT.getImag(i - 1, i);
            T.set(i - 1, i, real, imag);
            T.set(i, i - 1, real, -imag);
        }
        return T;
    }

    @Override
    public ZMatrixRMaj getQ(@Nullable ZMatrixRMaj Q, boolean transposed) {
        Q = UtilDecompositons_ZDRM.checkIdentity(Q, this.N, this.N);
        Arrays.fill(this.w, 0, this.N * 2, 0.0);
        if (transposed) {
            for (int j = this.N - 2; j >= 0; --j) {
                QrHelperFunctions_ZDRM.extractHouseholderRow(this.QT, j, j + 1, this.N, this.w, 0);
                QrHelperFunctions_ZDRM.rank1UpdateMultL(Q, this.w, 0, this.gammas[j], j + 1, j + 1, this.N);
            }
        } else {
            for (int j = this.N - 2; j >= 0; --j) {
                QrHelperFunctions_ZDRM.extractHouseholderRow(this.QT, j, j + 1, this.N, this.w, 0);
                QrHelperFunctions_ZDRM.rank1UpdateMultR(Q, this.w, 0, this.gammas[j], j + 1, j + 1, this.N, this.b);
            }
        }
        return Q;
    }

    @Override
    public boolean decompose(ZMatrixRMaj A) {
        this.init(A);
        for (int k = 0; k < this.N - 1; ++k) {
            this.similarTransform(k);
        }
        return true;
    }

    private void similarTransform(int k) {
        double[] t = this.QT.data;
        double max = QrHelperFunctions_ZDRM.computeRowMax(this.QT, k, k + 1, this.N);
        if (max > 0.0) {
            double gamma;
            this.gammas[k] = gamma = QrHelperFunctions_ZDRM.computeTauGammaAndDivide(k * this.N + k + 1, k * this.N + this.N, t, max, this.tau);
            double real_u_0 = t[(k * this.N + k + 1) * 2] + this.tau.real;
            double imag_u_0 = t[(k * this.N + k + 1) * 2 + 1] + this.tau.imaginary;
            QrHelperFunctions_ZDRM.divideElements(k + 2, this.N, t, k * this.N, real_u_0, imag_u_0);
            for (int i = k + 2; i < this.N; ++i) {
                t[(k * this.N + i) * 2 + 1] = -t[(k * this.N + i) * 2 + 1];
            }
            t[(k * this.N + k + 1) * 2] = 1.0;
            t[(k * this.N + k + 1) * 2 + 1] = 0.0;
            this.householderSymmetric(k, gamma);
            t[(k * this.N + k + 1) * 2] = -this.tau.real * max;
            t[(k * this.N + k + 1) * 2 + 1] = -this.tau.imaginary * max;
        } else {
            this.gammas[k] = 0.0;
        }
    }

    public void householderSymmetric(int row, double gamma) {
        double imagU;
        int i;
        int startU = row * this.N;
        for (int i2 = row + 1; i2 < this.N; ++i2) {
            double imagU2;
            double realU;
            double imagA;
            double realA;
            int j;
            double totalReal = 0.0;
            double totalImag = 0.0;
            for (j = row + 1; j < i2; ++j) {
                realA = this.QT.data[(j * this.N + i2) * 2];
                imagA = -this.QT.data[(j * this.N + i2) * 2 + 1];
                realU = this.QT.data[(startU + j) * 2];
                imagU2 = this.QT.data[(startU + j) * 2 + 1];
                totalReal += realA * realU - imagA * imagU2;
                totalImag += realA * imagU2 + imagA * realU;
            }
            for (j = i2; j < this.N; ++j) {
                realA = this.QT.data[(i2 * this.N + j) * 2];
                imagA = this.QT.data[(i2 * this.N + j) * 2 + 1];
                realU = this.QT.data[(startU + j) * 2];
                imagU2 = this.QT.data[(startU + j) * 2 + 1];
                totalReal += realA * realU - imagA * imagU2;
                totalImag += realA * imagU2 + imagA * realU;
            }
            this.w[i2 * 2] = -gamma * totalReal;
            this.w[i2 * 2 + 1] = -gamma * totalImag;
        }
        double realAplha = 0.0;
        double imageAlpha = 0.0;
        for (i = row + 1; i < this.N; ++i) {
            double realU = this.QT.data[(startU + i) * 2];
            imagU = -this.QT.data[(startU + i) * 2 + 1];
            double realV = this.w[i * 2];
            double imagV = this.w[i * 2 + 1];
            realAplha += realU * realV - imagU * imagV;
            imageAlpha += realU * imagV + imagU * realV;
        }
        realAplha *= -0.5 * gamma;
        imageAlpha *= -0.5 * gamma;
        for (i = row + 1; i < this.N; ++i) {
            double realU = this.QT.data[(startU + i) * 2];
            imagU = this.QT.data[(startU + i) * 2 + 1];
            int n = i * 2;
            this.w[n] = this.w[n] + (realAplha * realU - imageAlpha * imagU);
            int n2 = i * 2 + 1;
            this.w[n2] = this.w[n2] + (realAplha * imagU + imageAlpha * realU);
        }
        for (i = row + 1; i < this.N; ++i) {
            double realWW = this.w[i * 2];
            double imagWW = this.w[i * 2 + 1];
            double realUU = this.QT.data[(startU + i) * 2];
            double imagUU = this.QT.data[(startU + i) * 2 + 1];
            int indA = (i * this.N + i) * 2;
            for (int j = i; j < this.N; ++j) {
                double realU = this.QT.data[(startU + j) * 2];
                double imagU3 = -this.QT.data[(startU + j) * 2 + 1];
                double realW = this.w[j * 2];
                double imagW = -this.w[j * 2 + 1];
                int n = indA++;
                this.QT.data[n] = this.QT.data[n] + (realWW * realU - imagWW * imagU3 + realW * realUU - imagW * imagUU);
                int n3 = indA++;
                this.QT.data[n3] = this.QT.data[n3] + (realWW * imagU3 + imagWW * realU + realW * imagUU + imagW * realUU);
            }
        }
    }

    public void init(ZMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("Must be square");
        }
        if (A.numCols != this.N) {
            this.N = A.numCols;
            if (this.w.length < this.N) {
                this.w = new double[this.N * 2];
                this.gammas = new double[this.N * 2];
                this.b = new double[this.N * 2];
            }
        }
        this.QT = A;
    }

    @Override
    public boolean inputModified() {
        return true;
    }

    public double[] getGammas() {
        return this.gammas;
    }
}

