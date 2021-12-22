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
import org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition_F64;
import org.jetbrains.annotations.Nullable;

public class TridiagonalDecompositionHouseholder_DDRM
implements TridiagonalSimilarDecomposition_F64<DMatrixRMaj> {
    protected DMatrixRMaj QT;
    protected int N = 1;
    protected double[] w = new double[this.N];
    protected double[] gammas;
    protected double[] b = new double[this.N];

    public TridiagonalDecompositionHouseholder_DDRM() {
        this.gammas = new double[this.N];
    }

    public DMatrixRMaj getQT() {
        return this.QT;
    }

    @Override
    public void getDiagonal(double[] diag, double[] off) {
        for (int i = 0; i < this.N; ++i) {
            diag[i] = this.QT.data[i * this.N + i];
            if (i + 1 >= this.N) continue;
            off[i] = this.QT.data[i * this.N + i + 1];
        }
    }

    @Override
    public DMatrixRMaj getT(@Nullable DMatrixRMaj T) {
        T = UtilDecompositons_DDRM.ensureZeros(T, this.N, this.N);
        T.data[0] = this.QT.data[0];
        for (int i = 1; i < this.N; ++i) {
            T.set(i, i, this.QT.get(i, i));
            double a = this.QT.get(i - 1, i);
            T.set(i - 1, i, a);
            T.set(i, i - 1, a);
        }
        if (this.N > 1) {
            T.data[(this.N - 1) * this.N + this.N - 1] = this.QT.data[(this.N - 1) * this.N + this.N - 1];
            T.data[(this.N - 1) * this.N + this.N - 2] = this.QT.data[(this.N - 2) * this.N + this.N - 1];
        }
        return T;
    }

    @Override
    public DMatrixRMaj getQ(@Nullable DMatrixRMaj Q, boolean transposed) {
        int j;
        Q = UtilDecompositons_DDRM.ensureIdentity(Q, this.N, this.N);
        for (int i = 0; i < this.N; ++i) {
            this.w[i] = 0.0;
        }
        if (transposed) {
            for (j = this.N - 2; j >= 0; --j) {
                this.w[j + 1] = 1.0;
                for (int i = j + 2; i < this.N; ++i) {
                    this.w[i] = this.QT.data[j * this.N + i];
                }
                this.rank1UpdateMultL(Q, this.gammas[j + 1], j + 1, j + 1, this.N);
            }
        } else {
            for (j = this.N - 2; j >= 0; --j) {
                this.w[j + 1] = 1.0;
                for (int i = j + 2; i < this.N; ++i) {
                    this.w[i] = this.QT.get(j, i);
                }
                this.rank1UpdateMultR(Q, this.gammas[j + 1], j + 1, j + 1, this.N);
            }
        }
        return Q;
    }

    @Override
    public boolean decompose(DMatrixRMaj A) {
        this.init(A);
        for (int k = 1; k < this.N; ++k) {
            this.similarTransform(k);
        }
        return true;
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
            double tau = QrHelperFunctions_DDRM.computeTauAndDivide(k, this.N, t, rowU, max);
            double nu = t[rowU + k] + tau;
            QrHelperFunctions_DDRM.divideElements(k + 1, this.N, t, rowU, nu);
            t[rowU + k] = 1.0;
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
            int j;
            double total = 0.0;
            for (j = row; j < i2; ++j) {
                total += this.QT.data[j * this.N + i2] * this.QT.data[startU + j];
            }
            for (j = i2; j < this.N; ++j) {
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
            int rowA = i * this.N;
            for (int j = i; j < this.N; ++j) {
                int n = rowA + j;
                this.QT.data[n] = this.QT.data[n] + (ww * this.QT.data[startU + j] + this.w[j] * uu);
            }
        }
    }

    public void init(DMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("Must be square");
        }
        if (A.numCols != this.N) {
            this.N = A.numCols;
            if (this.w.length < this.N) {
                this.w = new double[this.N];
                this.gammas = new double[this.N];
                this.b = new double[this.N];
            }
        }
        this.QT = A;
    }

    protected void rank1UpdateMultL(DMatrixRMaj A, double gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_DDRM.rank1UpdateMultL(A, this.w, gamma, colA0, w0, w1);
    }

    protected void rank1UpdateMultR(DMatrixRMaj A, double gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_DDRM.rank1UpdateMultR(A, this.w, gamma, colA0, w0, w1, this.b);
    }

    @Override
    public boolean inputModified() {
        return true;
    }
}

