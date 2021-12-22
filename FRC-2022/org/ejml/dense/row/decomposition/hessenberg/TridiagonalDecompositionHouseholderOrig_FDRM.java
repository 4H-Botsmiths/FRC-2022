/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.hessenberg;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.UtilDecompositons_FDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_FDRM;
import org.jetbrains.annotations.Nullable;

public class TridiagonalDecompositionHouseholderOrig_FDRM {
    FMatrixRMaj QT = new FMatrixRMaj(this.N, this.N);
    int N = 1;
    float[] w = new float[this.N];
    float[] gammas;
    float[] b = new float[this.N];

    public TridiagonalDecompositionHouseholderOrig_FDRM() {
        this.gammas = new float[this.N];
    }

    public FMatrixRMaj getQT() {
        return this.QT;
    }

    public FMatrixRMaj getT(@Nullable FMatrixRMaj T) {
        T = UtilDecompositons_FDRM.ensureZeros(T, this.N, this.N);
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

    public FMatrixRMaj getQ(@Nullable FMatrixRMaj Q) {
        Q = UtilDecompositons_FDRM.ensureIdentity(Q, this.N, this.N);
        for (int i = 0; i < this.N; ++i) {
            this.w[i] = 0.0f;
        }
        for (int j = this.N - 2; j >= 0; --j) {
            this.w[j + 1] = 1.0f;
            for (int i = j + 2; i < this.N; ++i) {
                this.w[i] = this.QT.get(j, i);
            }
            QrHelperFunctions_FDRM.rank1UpdateMultR(Q, this.w, this.gammas[j + 1], j + 1, j + 1, this.N, this.b);
        }
        return Q;
    }

    public void decompose(FMatrixRMaj A) {
        this.init(A);
        for (int k = 1; k < this.N; ++k) {
            this.similarTransform(k);
        }
    }

    private void similarTransform(int k) {
        float[] t = this.QT.data;
        float max = 0.0f;
        int rowU = (k - 1) * this.N;
        for (int i = k; i < this.N; ++i) {
            float val = Math.abs(t[rowU + i]);
            if (!(val > max)) continue;
            max = val;
        }
        if (max > 0.0f) {
            float gamma;
            float tau = 0.0f;
            for (int i = k; i < this.N; ++i) {
                int n = rowU + i;
                float f = t[n] / max;
                t[n] = f;
                float val = f;
                tau += val * val;
            }
            tau = (float)Math.sqrt(tau);
            if (t[rowU + k] < 0.0f) {
                tau = -tau;
            }
            float nu = t[rowU + k] + tau;
            t[rowU + k] = 1.0f;
            for (int i = k + 1; i < this.N; ++i) {
                int n = rowU + i;
                t[n] = t[n] / nu;
            }
            this.gammas[k] = gamma = nu / tau;
            this.householderSymmetric(k, gamma);
            t[rowU + k] = -tau * max;
        } else {
            this.gammas[k] = 0.0f;
        }
    }

    public void householderSymmetric(int row, float gamma) {
        int i;
        int startU = (row - 1) * this.N;
        for (int i2 = row; i2 < this.N; ++i2) {
            float total = 0.0f;
            for (int j = row; j < this.N; ++j) {
                total += this.QT.data[i2 * this.N + j] * this.QT.data[startU + j];
            }
            this.w[i2] = -gamma * total;
        }
        float alpha = 0.0f;
        for (i = row; i < this.N; ++i) {
            alpha += this.QT.data[startU + i] * this.w[i];
        }
        alpha *= -0.5f * gamma;
        for (i = row; i < this.N; ++i) {
            int n = i;
            this.w[n] = this.w[n] + alpha * this.QT.data[startU + i];
        }
        for (i = row; i < this.N; ++i) {
            float ww = this.w[i];
            float uu = this.QT.data[startU + i];
            for (int j = i; j < this.N; ++j) {
                int n = i * this.N + j;
                float f = this.QT.data[n] + (ww * this.QT.data[startU + j] + this.w[j] * uu);
                this.QT.data[n] = f;
                this.QT.data[j * this.N + i] = f;
            }
        }
    }

    public void init(FMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("Must be square");
        }
        if (A.numCols != this.N) {
            this.N = A.numCols;
            this.QT.reshape(this.N, this.N, false);
            if (this.w.length < this.N) {
                this.w = new float[this.N];
                this.gammas = new float[this.N];
                this.b = new float[this.N];
            }
        }
        this.QT.setTo(A);
    }

    public float getGamma(int index) {
        return this.gammas[index];
    }
}

