/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.hessenberg;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.UtilDecompositons_DDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_DDRM;
import org.jetbrains.annotations.Nullable;

public class TridiagonalDecompositionHouseholderOrig_DDRM {
    DMatrixRMaj QT = new DMatrixRMaj(this.N, this.N);
    int N = 1;
    double[] w = new double[this.N];
    double[] gammas;
    double[] b = new double[this.N];

    public TridiagonalDecompositionHouseholderOrig_DDRM() {
        this.gammas = new double[this.N];
    }

    public DMatrixRMaj getQT() {
        return this.QT;
    }

    public DMatrixRMaj getT(@Nullable DMatrixRMaj T) {
        T = UtilDecompositons_DDRM.ensureZeros(T, this.N, this.N);
        T.data[0] = this.QT.data[0];
        T.data[1] = this.QT.data[1];
        for (int i = 1; i < this.N - 1; ++i) {
            T.set(i, i, this.QT.get(i, i));
            T.set(i, i + 1, this.QT.get(i, i + 1));
            T.set(i, i - 1, this.QT.get(i - 1, i));
        }
        T.data[(this.N - 1) * this.N + this.N - 1] = this.QT.data[(this.N - 1) * this.N + this.N - 1];
        T.data[(this.N - 1) * this.N + this.N - 2] = this.QT.data[(this.N - 2) * this.N + this.N - 1];
        return T;
    }

    public DMatrixRMaj getQ(@Nullable DMatrixRMaj Q) {
        Q = UtilDecompositons_DDRM.ensureIdentity(Q, this.N, this.N);
        for (int i = 0; i < this.N; ++i) {
            this.w[i] = 0.0;
        }
        for (int j = this.N - 2; j >= 0; --j) {
            this.w[j + 1] = 1.0;
            for (int i = j + 2; i < this.N; ++i) {
                this.w[i] = this.QT.get(j, i);
            }
            QrHelperFunctions_DDRM.rank1UpdateMultR(Q, this.w, this.gammas[j + 1], j + 1, j + 1, this.N, this.b);
        }
        return Q;
    }

    public void decompose(DMatrixRMaj A) {
        this.init(A);
        for (int k = 1; k < this.N; ++k) {
            this.similarTransform(k);
        }
    }

    private void similarTransform(int k) {
        double[] t = this.QT.data;
        double max = 0.0;
        int rowU = (k - 1) * this.N;
        for (int i = k; i < this.N; ++i) {
            double val = Math.abs(t[rowU + i]);
            if (!(val > max)) continue;
            max = val;
        }
        if (max > 0.0) {
            double gamma;
            double tau = 0.0;
            for (int i = k; i < this.N; ++i) {
                int n = rowU + i;
                double d = t[n] / max;
                t[n] = d;
                double val = d;
                tau += val * val;
            }
            tau = Math.sqrt(tau);
            if (t[rowU + k] < 0.0) {
                tau = -tau;
            }
            double nu = t[rowU + k] + tau;
            t[rowU + k] = 1.0;
            for (int i = k + 1; i < this.N; ++i) {
                int n = rowU + i;
                t[n] = t[n] / nu;
            }
            this.gammas[k] = gamma = nu / tau;
            this.householderSymmetric(k, gamma);
            t[rowU + k] = -tau * max;
        } else {
            this.gammas[k] = 0.0;
        }
    }

    public void householderSymmetric(int row, double gamma) {
        int i;
        int startU = (row - 1) * this.N;
        for (int i2 = row; i2 < this.N; ++i2) {
            double total = 0.0;
            for (int j = row; j < this.N; ++j) {
                total += this.QT.data[i2 * this.N + j] * this.QT.data[startU + j];
            }
            this.w[i2] = -gamma * total;
        }
        double alpha = 0.0;
        for (i = row; i < this.N; ++i) {
            alpha += this.QT.data[startU + i] * this.w[i];
        }
        alpha *= -0.5 * gamma;
        for (i = row; i < this.N; ++i) {
            int n = i;
            this.w[n] = this.w[n] + alpha * this.QT.data[startU + i];
        }
        for (i = row; i < this.N; ++i) {
            double ww = this.w[i];
            double uu = this.QT.data[startU + i];
            for (int j = i; j < this.N; ++j) {
                int n = i * this.N + j;
                double d = this.QT.data[n] + (ww * this.QT.data[startU + j] + this.w[j] * uu);
                this.QT.data[n] = d;
                this.QT.data[j * this.N + i] = d;
            }
        }
    }

    public void init(DMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("Must be square");
        }
        if (A.numCols != this.N) {
            this.N = A.numCols;
            this.QT.reshape(this.N, this.N, false);
            if (this.w.length < this.N) {
                this.w = new double[this.N];
                this.gammas = new double[this.N];
                this.b = new double[this.N];
            }
        }
        this.QT.setTo(A);
    }

    public double getGamma(int index) {
        return this.gammas[index];
    }
}

