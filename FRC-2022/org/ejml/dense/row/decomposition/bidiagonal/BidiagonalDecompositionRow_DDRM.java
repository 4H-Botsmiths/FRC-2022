/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.bidiagonal;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_DDRM;
import org.ejml.interfaces.decomposition.BidiagonalDecomposition_F64;
import org.jetbrains.annotations.Nullable;

public class BidiagonalDecompositionRow_DDRM
implements BidiagonalDecomposition_F64<DMatrixRMaj> {
    private DMatrixRMaj UBV;
    private int m;
    private int n;
    private int min;
    private double[] gammasU;
    private double[] gammasV;
    protected double[] b;
    protected double[] u;

    public BidiagonalDecompositionRow_DDRM(int numElements) {
        this.UBV = new DMatrixRMaj(numElements);
        this.gammasU = new double[numElements];
        this.gammasV = new double[numElements];
        this.b = new double[numElements];
        this.u = new double[numElements];
    }

    public BidiagonalDecompositionRow_DDRM() {
        this(1);
    }

    @Override
    public boolean decompose(DMatrixRMaj A) {
        this.init(A);
        return this._decompose();
    }

    protected void init(DMatrixRMaj A) {
        this.UBV = A;
        this.m = this.UBV.numRows;
        this.n = this.UBV.numCols;
        this.min = Math.min(this.m, this.n);
        int max = Math.max(this.m, this.n);
        if (this.b.length < max + 1) {
            this.b = new double[max + 1];
            this.u = new double[max + 1];
        }
        if (this.gammasU.length < this.m) {
            this.gammasU = new double[this.m];
        }
        if (this.gammasV.length < this.n) {
            this.gammasV = new double[this.n];
        }
    }

    public DMatrixRMaj getUBV() {
        return this.UBV;
    }

    @Override
    public void getDiagonal(double[] diag, double[] off) {
        diag[0] = this.UBV.get(0);
        for (int i = 1; i < this.n; ++i) {
            diag[i] = this.UBV.unsafe_get(i, i);
            off[i - 1] = this.UBV.unsafe_get(i - 1, i);
        }
    }

    @Override
    public DMatrixRMaj getB(@Nullable DMatrixRMaj B, boolean compact) {
        B = BidiagonalDecompositionRow_DDRM.handleB(B, compact, this.m, this.n, this.min);
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

    public static DMatrixRMaj handleB(@Nullable DMatrixRMaj B, boolean compact, int m, int n, int min) {
        int w;
        int n2 = w = n > m ? min + 1 : min;
        if (compact) {
            if (B == null) {
                B = new DMatrixRMaj(min, w);
            } else {
                B.reshape(min, w, false);
                B.zero();
            }
        } else if (B == null) {
            B = new DMatrixRMaj(m, n);
        } else {
            B.reshape(m, n, false);
            B.zero();
        }
        return B;
    }

    @Override
    public DMatrixRMaj getU(@Nullable DMatrixRMaj U, boolean transpose, boolean compact) {
        U = BidiagonalDecompositionRow_DDRM.handleU(U, transpose, compact, this.m, this.n, this.min);
        CommonOps_DDRM.setIdentity(U);
        for (int i = 0; i < this.m; ++i) {
            this.u[i] = 0.0;
        }
        for (int j = this.min - 1; j >= 0; --j) {
            this.u[j] = 1.0;
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

    public static DMatrixRMaj handleU(@Nullable DMatrixRMaj U, boolean transpose, boolean compact, int m, int n, int min) {
        if (compact) {
            if (transpose) {
                if (U == null) {
                    U = new DMatrixRMaj(min, m);
                } else {
                    U.reshape(min, m, false);
                }
            } else if (U == null) {
                U = new DMatrixRMaj(m, min);
            } else {
                U.reshape(m, min, false);
            }
        } else if (U == null) {
            U = new DMatrixRMaj(m, m);
        } else {
            U.reshape(m, m, false);
        }
        return U;
    }

    @Override
    public DMatrixRMaj getV(@Nullable DMatrixRMaj V, boolean transpose, boolean compact) {
        V = BidiagonalDecompositionRow_DDRM.handleV(V, transpose, compact, this.m, this.n, this.min);
        CommonOps_DDRM.setIdentity(V);
        for (int j = this.min - 1; j >= 0; --j) {
            this.u[j + 1] = 1.0;
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

    public static DMatrixRMaj handleV(@Nullable DMatrixRMaj V, boolean transpose, boolean compact, int m, int n, int min) {
        int w;
        int n2 = w = n > m ? min + 1 : min;
        if (compact) {
            if (transpose) {
                if (V == null) {
                    V = new DMatrixRMaj(w, n);
                } else {
                    V.reshape(w, n, false);
                }
            } else if (V == null) {
                V = new DMatrixRMaj(n, w);
            } else {
                V.reshape(n, w, false);
            }
        } else if (V == null) {
            V = new DMatrixRMaj(n, n);
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
        double[] b = this.UBV.data;
        double max = 0.0;
        for (int i = k; i < this.m; ++i) {
            double val = this.u[i] = b[i * this.n + k];
            if (!((val = Math.abs(val)) > max)) continue;
            max = val;
        }
        if (max > 0.0) {
            double gamma;
            double tau = QrHelperFunctions_DDRM.computeTauAndDivide(k, this.m, this.u, max);
            double nu = this.u[k] + tau;
            QrHelperFunctions_DDRM.divideElements_Bcol(k + 1, this.m, this.n, this.u, b, k, nu);
            this.u[k] = 1.0;
            this.gammasU[k] = gamma = nu / tau;
            this.rank1UpdateMultR(this.UBV, gamma, k + 1, k, this.m);
            b[k * this.n + k] = -tau * max;
        } else {
            this.gammasU[k] = 0.0;
        }
    }

    protected void rank1UpdateMultL(DMatrixRMaj A, double gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_DDRM.rank1UpdateMultL(A, this.u, gamma, colA0, w0, w1);
    }

    protected void rank1UpdateMultR(DMatrixRMaj A, double gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_DDRM.rank1UpdateMultR(A, this.u, gamma, colA0, w0, w1, this.b);
    }

    protected void computeV(int k) {
        double[] b = this.UBV.data;
        int row = k * this.n;
        double max = QrHelperFunctions_DDRM.findMax(b, row + k + 1, this.n - k - 1);
        if (max > 0.0) {
            double gamma;
            double tau = QrHelperFunctions_DDRM.computeTauAndDivide(k + 1, this.n, b, row, max);
            double nu = b[row + k + 1] + tau;
            QrHelperFunctions_DDRM.divideElements_Brow(k + 2, this.n, this.u, b, row, nu);
            this.u[k + 1] = 1.0;
            this.gammasV[k] = gamma = nu / tau;
            this.rank1UpdateMultL(this.UBV, gamma, k + 1, k + 1, this.n);
            b[row + k + 1] = -tau * max;
        } else {
            this.gammasV[k] = 0.0;
        }
    }

    public double[] getGammasU() {
        return this.gammasU;
    }

    public double[] getGammasV() {
        return this.gammasV;
    }

    @Override
    public boolean inputModified() {
        return true;
    }
}

