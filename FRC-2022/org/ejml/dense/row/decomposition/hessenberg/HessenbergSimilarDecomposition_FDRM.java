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
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.jetbrains.annotations.Nullable;

public class HessenbergSimilarDecomposition_FDRM
implements DecompositionInterface<FMatrixRMaj> {
    private FMatrixRMaj QH;
    private int N;
    private float[] gammas;
    protected float[] b;
    protected float[] u;

    public HessenbergSimilarDecomposition_FDRM(int initialSize) {
        this.gammas = new float[initialSize];
        this.b = new float[initialSize];
        this.u = new float[initialSize];
    }

    public HessenbergSimilarDecomposition_FDRM() {
        this(5);
    }

    @Override
    public boolean decompose(FMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("A must be square.");
        }
        if (A.numRows <= 0) {
            return false;
        }
        this.QH = A;
        this.N = A.numCols;
        if (this.b.length < this.N) {
            this.b = new float[this.N];
            this.gammas = new float[this.N];
            this.u = new float[this.N];
        }
        return this._decompose();
    }

    @Override
    public boolean inputModified() {
        return true;
    }

    public FMatrixRMaj getQH() {
        return this.QH;
    }

    public FMatrixRMaj getH(@Nullable FMatrixRMaj H) {
        H = UtilDecompositons_FDRM.ensureZeros(H, this.N, this.N);
        System.arraycopy(this.QH.data, 0, H.data, 0, this.N);
        for (int i = 1; i < this.N; ++i) {
            for (int j = i - 1; j < this.N; ++j) {
                H.set(i, j, this.QH.get(i, j));
            }
        }
        return H;
    }

    public FMatrixRMaj getQ(@Nullable FMatrixRMaj Q) {
        Q = UtilDecompositons_FDRM.ensureIdentity(Q, this.N, this.N);
        for (int j = this.N - 2; j >= 0; --j) {
            this.u[j + 1] = 1.0f;
            for (int i = j + 2; i < this.N; ++i) {
                this.u[i] = this.QH.get(i, j);
            }
            this.rank1UpdateMultR(Q, this.gammas[j], j + 1, j + 1, this.N);
        }
        return Q;
    }

    private boolean _decompose() {
        float[] h = this.QH.data;
        for (int k = 0; k < this.N - 2; ++k) {
            float max = 0.0f;
            for (int i = k + 1; i < this.N; ++i) {
                float val = this.u[i] = h[i * this.N + k];
                if (!((val = Math.abs(val)) > max)) continue;
                max = val;
            }
            if (max > 0.0f) {
                float gamma;
                float tau = 0.0f;
                int i = k + 1;
                while (i < this.N) {
                    int n = i++;
                    float f = this.u[n] / max;
                    this.u[n] = f;
                    float val = f;
                    tau += val * val;
                }
                tau = (float)Math.sqrt(tau);
                if (this.u[k + 1] < 0.0f) {
                    tau = -tau;
                }
                float nu = this.u[k + 1] + tau;
                this.u[k + 1] = 1.0f;
                int i2 = k + 2;
                while (i2 < this.N) {
                    int n = i2++;
                    float f = this.u[n] / nu;
                    this.u[n] = f;
                    h[i2 * this.N + k] = f;
                }
                this.gammas[k] = gamma = nu / tau;
                this.rank1UpdateMultR(this.QH, gamma, k + 1, k + 1, this.N);
                this.rank1UpdateMultL(this.QH, gamma, 0, k + 1, this.N);
                h[(k + 1) * this.N + k] = -tau * max;
                continue;
            }
            this.gammas[k] = 0.0f;
        }
        return true;
    }

    protected void rank1UpdateMultL(FMatrixRMaj A, float gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_FDRM.rank1UpdateMultL(A, this.u, gamma, colA0, w0, w1);
    }

    protected void rank1UpdateMultR(FMatrixRMaj A, float gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_FDRM.rank1UpdateMultR(A, this.u, gamma, colA0, w0, w1, this.b);
    }

    public float[] getGammas() {
        return this.gammas;
    }
}

