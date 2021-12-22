/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.bidiagonal;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_FDRM;
import org.ejml.interfaces.decomposition.BidiagonalDecomposition_F32;
import org.jetbrains.annotations.Nullable;

public class BidiagonalDecompositionRow_FDRM
implements BidiagonalDecomposition_F32<FMatrixRMaj> {
    private FMatrixRMaj UBV;
    private int m;
    private int n;
    private int min;
    private float[] gammasU;
    private float[] gammasV;
    protected float[] b;
    protected float[] u;

    public BidiagonalDecompositionRow_FDRM(int numElements) {
        this.UBV = new FMatrixRMaj(numElements);
        this.gammasU = new float[numElements];
        this.gammasV = new float[numElements];
        this.b = new float[numElements];
        this.u = new float[numElements];
    }

    public BidiagonalDecompositionRow_FDRM() {
        this(1);
    }

    @Override
    public boolean decompose(FMatrixRMaj A) {
        this.init(A);
        return this._decompose();
    }

    protected void init(FMatrixRMaj A) {
        this.UBV = A;
        this.m = this.UBV.numRows;
        this.n = this.UBV.numCols;
        this.min = Math.min(this.m, this.n);
        int max = Math.max(this.m, this.n);
        if (this.b.length < max + 1) {
            this.b = new float[max + 1];
            this.u = new float[max + 1];
        }
        if (this.gammasU.length < this.m) {
            this.gammasU = new float[this.m];
        }
        if (this.gammasV.length < this.n) {
            this.gammasV = new float[this.n];
        }
    }

    public FMatrixRMaj getUBV() {
        return this.UBV;
    }

    @Override
    public void getDiagonal(float[] diag, float[] off) {
        diag[0] = this.UBV.get(0);
        for (int i = 1; i < this.n; ++i) {
            diag[i] = this.UBV.unsafe_get(i, i);
            off[i - 1] = this.UBV.unsafe_get(i - 1, i);
        }
    }

    @Override
    public FMatrixRMaj getB(@Nullable FMatrixRMaj B, boolean compact) {
        B = BidiagonalDecompositionRow_FDRM.handleB(B, compact, this.m, this.n, this.min);
        B.set(0, 0, this.UBV.get(0, 0));
        for (int i = 1; i < this.min; ++i) {
            B.set(i, i, this.UBV.get(i, i));
            B.set(i - 1, i, this.UBV.get(i - 1, i));
        }
        if (this.n > this.m) {
            B.set(this.min - 1, this.min, this.UBV.get(this.min - 1, this.min));
        }
        return B;
    }

    public static FMatrixRMaj handleB(@Nullable FMatrixRMaj B, boolean compact, int m, int n, int min) {
        int w;
        int n2 = w = n > m ? min + 1 : min;
        if (compact) {
            if (B == null) {
                B = new FMatrixRMaj(min, w);
            } else {
                B.reshape(min, w, false);
                B.zero();
            }
        } else if (B == null) {
            B = new FMatrixRMaj(m, n);
        } else {
            B.reshape(m, n, false);
            B.zero();
        }
        return B;
    }

    @Override
    public FMatrixRMaj getU(@Nullable FMatrixRMaj U, boolean transpose, boolean compact) {
        U = BidiagonalDecompositionRow_FDRM.handleU(U, transpose, compact, this.m, this.n, this.min);
        CommonOps_FDRM.setIdentity(U);
        for (int i = 0; i < this.m; ++i) {
            this.u[i] = 0.0f;
        }
        for (int j = this.min - 1; j >= 0; --j) {
            this.u[j] = 1.0f;
            for (int i = j + 1; i < this.m; ++i) {
                this.u[i] = this.UBV.get(i, j);
            }
            if (transpose) {
                this.rank1UpdateMultL(U, this.gammasU[j], j, j, this.m);
                continue;
            }
            this.rank1UpdateMultR(U, this.gammasU[j], j, j, this.m);
        }
        return U;
    }

    public static FMatrixRMaj handleU(@Nullable FMatrixRMaj U, boolean transpose, boolean compact, int m, int n, int min) {
        if (compact) {
            if (transpose) {
                if (U == null) {
                    U = new FMatrixRMaj(min, m);
                } else {
                    U.reshape(min, m, false);
                }
            } else if (U == null) {
                U = new FMatrixRMaj(m, min);
            } else {
                U.reshape(m, min, false);
            }
        } else if (U == null) {
            U = new FMatrixRMaj(m, m);
        } else {
            U.reshape(m, m, false);
        }
        return U;
    }

    @Override
    public FMatrixRMaj getV(@Nullable FMatrixRMaj V, boolean transpose, boolean compact) {
        V = BidiagonalDecompositionRow_FDRM.handleV(V, transpose, compact, this.m, this.n, this.min);
        CommonOps_FDRM.setIdentity(V);
        for (int j = this.min - 1; j >= 0; --j) {
            this.u[j + 1] = 1.0f;
            for (int i = j + 2; i < this.n; ++i) {
                this.u[i] = this.UBV.get(j, i);
            }
            if (transpose) {
                this.rank1UpdateMultL(V, this.gammasV[j], j + 1, j + 1, this.n);
                continue;
            }
            this.rank1UpdateMultR(V, this.gammasV[j], j + 1, j + 1, this.n);
        }
        return V;
    }

    public static FMatrixRMaj handleV(@Nullable FMatrixRMaj V, boolean transpose, boolean compact, int m, int n, int min) {
        int w;
        int n2 = w = n > m ? min + 1 : min;
        if (compact) {
            if (transpose) {
                if (V == null) {
                    V = new FMatrixRMaj(w, n);
                } else {
                    V.reshape(w, n, false);
                }
            } else if (V == null) {
                V = new FMatrixRMaj(n, w);
            } else {
                V.reshape(n, w, false);
            }
        } else if (V == null) {
            V = new FMatrixRMaj(n, n);
        } else {
            V.reshape(n, n, false);
        }
        return V;
    }

    private boolean _decompose() {
        for (int k = 0; k < this.min; ++k) {
            this.computeU(k);
            this.computeV(k);
        }
        return true;
    }

    protected void computeU(int k) {
        float[] b = this.UBV.data;
        float max = 0.0f;
        for (int i = k; i < this.m; ++i) {
            float val = this.u[i] = b[i * this.n + k];
            if (!((val = Math.abs(val)) > max)) continue;
            max = val;
        }
        if (max > 0.0f) {
            float gamma;
            float tau = QrHelperFunctions_FDRM.computeTauAndDivide(k, this.m, this.u, max);
            float nu = this.u[k] + tau;
            QrHelperFunctions_FDRM.divideElements_Bcol(k + 1, this.m, this.n, this.u, b, k, nu);
            this.u[k] = 1.0f;
            this.gammasU[k] = gamma = nu / tau;
            this.rank1UpdateMultR(this.UBV, gamma, k + 1, k, this.m);
            b[k * this.n + k] = -tau * max;
        } else {
            this.gammasU[k] = 0.0f;
        }
    }

    protected void rank1UpdateMultL(FMatrixRMaj A, float gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_FDRM.rank1UpdateMultL(A, this.u, gamma, colA0, w0, w1);
    }

    protected void rank1UpdateMultR(FMatrixRMaj A, float gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_FDRM.rank1UpdateMultR(A, this.u, gamma, colA0, w0, w1, this.b);
    }

    protected void computeV(int k) {
        float[] b = this.UBV.data;
        int row = k * this.n;
        float max = QrHelperFunctions_FDRM.findMax(b, row + k + 1, this.n - k - 1);
        if (max > 0.0f) {
            float gamma;
            float tau = QrHelperFunctions_FDRM.computeTauAndDivide(k + 1, this.n, b, row, max);
            float nu = b[row + k + 1] + tau;
            QrHelperFunctions_FDRM.divideElements_Brow(k + 2, this.n, this.u, b, row, nu);
            this.u[k + 1] = 1.0f;
            this.gammasV[k] = gamma = nu / tau;
            this.rank1UpdateMultL(this.UBV, gamma, k + 1, k + 1, this.n);
            b[row + k + 1] = -tau * max;
        } else {
            this.gammasV[k] = 0.0f;
        }
    }

    public float[] getGammasU() {
        return this.gammasU;
    }

    public float[] getGammasV() {
        return this.gammasV;
    }

    @Override
    public boolean inputModified() {
        return true;
    }
}

