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
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.jetbrains.annotations.Nullable;

public class HessenbergSimilarDecomposition_ZDRM
implements DecompositionInterface<ZMatrixRMaj> {
    private ZMatrixRMaj QH;
    private int N;
    private double[] gammas;
    private double[] b;
    private double[] u;
    private Complex_F64 tau = new Complex_F64();

    public HessenbergSimilarDecomposition_ZDRM(int initialSize) {
        this.gammas = new double[initialSize];
        this.b = new double[initialSize * 2];
        this.u = new double[initialSize * 2];
    }

    public HessenbergSimilarDecomposition_ZDRM() {
        this(5);
    }

    @Override
    public boolean decompose(ZMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("A must be square.");
        }
        if (A.numRows <= 0) {
            return false;
        }
        this.QH = A;
        this.N = A.numCols;
        if (this.b.length < this.N * 2) {
            this.b = new double[this.N * 2];
            this.gammas = new double[this.N];
            this.u = new double[this.N * 2];
        }
        return this._decompose();
    }

    @Override
    public boolean inputModified() {
        return true;
    }

    public ZMatrixRMaj getQH() {
        return this.QH;
    }

    public ZMatrixRMaj getH(@Nullable ZMatrixRMaj H) {
        H = UtilDecompositons_ZDRM.checkZeros(H, this.N, this.N);
        System.arraycopy(this.QH.data, 0, H.data, 0, this.N * 2);
        for (int i = 1; i < this.N; ++i) {
            System.arraycopy(this.QH.data, (i * this.N + i - 1) * 2, H.data, (i * this.N + i - 1) * 2, (this.N - i + 1) * 2);
        }
        return H;
    }

    public ZMatrixRMaj getQ(@Nullable ZMatrixRMaj Q) {
        Q = UtilDecompositons_ZDRM.checkIdentity(Q, this.N, this.N);
        Arrays.fill(this.u, 0, this.N * 2, 0.0);
        for (int j = this.N - 2; j >= 0; --j) {
            QrHelperFunctions_ZDRM.extractHouseholderColumn(this.QH, j + 1, this.N, j, this.u, 0);
            QrHelperFunctions_ZDRM.rank1UpdateMultR(Q, this.u, 0, this.gammas[j], j + 1, j + 1, this.N, this.b);
        }
        return Q;
    }

    private boolean _decompose() {
        double[] h = this.QH.data;
        for (int k = 0; k < this.N - 2; ++k) {
            this.u[k * 2] = 0.0;
            this.u[k * 2 + 1] = 0.0;
            double max = QrHelperFunctions_ZDRM.extractColumnAndMax(this.QH, k + 1, this.N, k, this.u, 0);
            if (max > 0.0) {
                double gamma;
                this.gammas[k] = gamma = QrHelperFunctions_ZDRM.computeTauGammaAndDivide(k + 1, this.N, this.u, max, this.tau);
                double real_u_0 = this.u[(k + 1) * 2] + this.tau.real;
                double imag_u_0 = this.u[(k + 1) * 2 + 1] + this.tau.imaginary;
                QrHelperFunctions_ZDRM.divideElements(k + 2, this.N, this.u, 0, real_u_0, imag_u_0);
                for (int i = k + 2; i < this.N; ++i) {
                    h[(i * this.N + k) * 2] = this.u[i * 2];
                    h[(i * this.N + k) * 2 + 1] = this.u[i * 2 + 1];
                }
                this.u[(k + 1) * 2] = 1.0;
                this.u[(k + 1) * 2 + 1] = 0.0;
                QrHelperFunctions_ZDRM.rank1UpdateMultR(this.QH, this.u, 0, gamma, k + 1, k + 1, this.N, this.b);
                QrHelperFunctions_ZDRM.rank1UpdateMultL(this.QH, this.u, 0, gamma, 0, k + 1, this.N);
                h[((k + 1) * this.N + k) * 2] = -this.tau.real * max;
                h[((k + 1) * this.N + k) * 2 + 1] = -this.tau.imaginary * max;
                continue;
            }
            this.gammas[k] = 0.0;
        }
        return true;
    }

    public double[] getGammas() {
        return this.gammas;
    }
}

