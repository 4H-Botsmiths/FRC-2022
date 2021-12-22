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
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.jetbrains.annotations.Nullable;

public class HessenbergSimilarDecomposition_DDRM
implements DecompositionInterface<DMatrixRMaj> {
    private DMatrixRMaj QH;
    private int N;
    private double[] gammas;
    protected double[] b;
    protected double[] u;

    public HessenbergSimilarDecomposition_DDRM(int initialSize) {
        this.gammas = new double[initialSize];
        this.b = new double[initialSize];
        this.u = new double[initialSize];
    }

    public HessenbergSimilarDecomposition_DDRM() {
        this(5);
    }

    @Override
    public boolean decompose(DMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("A must be square.");
        }
        if (A.numRows <= 0) {
            return false;
        }
        this.QH = A;
        this.N = A.numCols;
        if (this.b.length < this.N) {
            this.b = new double[this.N];
            this.gammas = new double[this.N];
            this.u = new double[this.N];
        }
        return this._decompose();
    }

    @Override
    public boolean inputModified() {
        return true;
    }

    public DMatrixRMaj getQH() {
        return this.QH;
    }

    public DMatrixRMaj getH(@Nullable DMatrixRMaj H) {
        H = UtilDecompositons_DDRM.ensureZeros(H, this.N, this.N);
        System.arraycopy(this.QH.data, 0, H.data, 0, this.N);
        for (int i = 1; i < this.N; ++i) {
            for (int j = i - 1; j < this.N; ++j) {
                H.set(i, j, this.QH.get(i, j));
            }
        }
        return H;
    }

    public DMatrixRMaj getQ(@Nullable DMatrixRMaj Q) {
        Q = UtilDecompositons_DDRM.ensureIdentity(Q, this.N, this.N);
        for (int j = this.N - 2; j >= 0; --j) {
            this.u[j + 1] = 1.0;
            for (int i = j + 2; i < this.N; ++i) {
                this.u[i] = this.QH.get(i, j);
            }
            this.rank1UpdateMultR(Q, this.gammas[j], j + 1, j + 1, this.N);
        }
        return Q;
    }

    private boolean _decompose() {
        double[] h = this.QH.data;
        for (int k = 0; k < this.N - 2; ++k) {
            double max = 0.0;
            for (int i = k + 1; i < this.N; ++i) {
                double val = this.u[i] = h[i * this.N + k];
                if (!((val = Math.abs(val)) > max)) continue;
                max = val;
            }
            if (max > 0.0) {
                double gamma;
                double tau = 0.0;
                int i = k + 1;
                while (i < this.N) {
                    int n = i++;
                    double d = this.u[n] / max;
                    this.u[n] = d;
                    double val = d;
                    tau += val * val;
                }
                tau = Math.sqrt(tau);
                if (this.u[k + 1] < 0.0) {
                    tau = -tau;
                }
                double nu = this.u[k + 1] + tau;
                this.u[k + 1] = 1.0;
                int i2 = k + 2;
                while (i2 < this.N) {
                    int n = i2++;
                    double d = this.u[n] / nu;
                    this.u[n] = d;
                    h[i2 * this.N + k] = d;
                }
                this.gammas[k] = gamma = nu / tau;
                this.rank1UpdateMultR(this.QH, gamma, k + 1, k + 1, this.N);
                this.rank1UpdateMultL(this.QH, gamma, 0, k + 1, this.N);
                h[(k + 1) * this.N + k] = -tau * max;
                continue;
            }
            this.gammas[k] = 0.0;
        }
        return true;
    }

    protected void rank1UpdateMultL(DMatrixRMaj A, double gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_DDRM.rank1UpdateMultL(A, this.u, gamma, colA0, w0, w1);
    }

    protected void rank1UpdateMultR(DMatrixRMaj A, double gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_DDRM.rank1UpdateMultR(A, this.u, gamma, colA0, w0, w1, this.b);
    }

    public double[] getGammas() {
        return this.gammas;
    }
}

