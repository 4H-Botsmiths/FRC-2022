/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decompose.hessenberg;

import java.util.Arrays;
import org.ejml.data.CMatrixRMaj;
import org.ejml.data.Complex_F32;
import org.ejml.dense.row.decompose.UtilDecompositons_CDRM;
import org.ejml.dense.row.decompose.qr.QrHelperFunctions_CDRM;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.jetbrains.annotations.Nullable;

public class HessenbergSimilarDecomposition_CDRM
implements DecompositionInterface<CMatrixRMaj> {
    private CMatrixRMaj QH;
    private int N;
    private float[] gammas;
    private float[] b;
    private float[] u;
    private Complex_F32 tau = new Complex_F32();

    public HessenbergSimilarDecomposition_CDRM(int initialSize) {
        this.gammas = new float[initialSize];
        this.b = new float[initialSize * 2];
        this.u = new float[initialSize * 2];
    }

    public HessenbergSimilarDecomposition_CDRM() {
        this(5);
    }

    @Override
    public boolean decompose(CMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("A must be square.");
        }
        if (A.numRows <= 0) {
            return false;
        }
        this.QH = A;
        this.N = A.numCols;
        if (this.b.length < this.N * 2) {
            this.b = new float[this.N * 2];
            this.gammas = new float[this.N];
            this.u = new float[this.N * 2];
        }
        return this._decompose();
    }

    @Override
    public boolean inputModified() {
        return true;
    }

    public CMatrixRMaj getQH() {
        return this.QH;
    }

    public CMatrixRMaj getH(@Nullable CMatrixRMaj H) {
        H = UtilDecompositons_CDRM.checkZeros(H, this.N, this.N);
        System.arraycopy(this.QH.data, 0, H.data, 0, this.N * 2);
        for (int i = 1; i < this.N; ++i) {
            System.arraycopy(this.QH.data, (i * this.N + i - 1) * 2, H.data, (i * this.N + i - 1) * 2, (this.N - i + 1) * 2);
        }
        return H;
    }

    public CMatrixRMaj getQ(@Nullable CMatrixRMaj Q) {
        Q = UtilDecompositons_CDRM.checkIdentity(Q, this.N, this.N);
        Arrays.fill(this.u, 0, this.N * 2, 0.0f);
        for (int j = this.N - 2; j >= 0; --j) {
            QrHelperFunctions_CDRM.extractHouseholderColumn(this.QH, j + 1, this.N, j, this.u, 0);
            QrHelperFunctions_CDRM.rank1UpdateMultR(Q, this.u, 0, this.gammas[j], j + 1, j + 1, this.N, this.b);
        }
        return Q;
    }

    private boolean _decompose() {
        float[] h = this.QH.data;
        for (int k = 0; k < this.N - 2; ++k) {
            this.u[k * 2] = 0.0f;
            this.u[k * 2 + 1] = 0.0f;
            float max = QrHelperFunctions_CDRM.extractColumnAndMax(this.QH, k + 1, this.N, k, this.u, 0);
            if (max > 0.0f) {
                float gamma;
                this.gammas[k] = gamma = QrHelperFunctions_CDRM.computeTauGammaAndDivide(k + 1, this.N, this.u, max, this.tau);
                float real_u_0 = this.u[(k + 1) * 2] + this.tau.real;
                float imag_u_0 = this.u[(k + 1) * 2 + 1] + this.tau.imaginary;
                QrHelperFunctions_CDRM.divideElements(k + 2, this.N, this.u, 0, real_u_0, imag_u_0);
                for (int i = k + 2; i < this.N; ++i) {
                    h[(i * this.N + k) * 2] = this.u[i * 2];
                    h[(i * this.N + k) * 2 + 1] = this.u[i * 2 + 1];
                }
                this.u[(k + 1) * 2] = 1.0f;
                this.u[(k + 1) * 2 + 1] = 0.0f;
                QrHelperFunctions_CDRM.rank1UpdateMultR(this.QH, this.u, 0, gamma, k + 1, k + 1, this.N, this.b);
                QrHelperFunctions_CDRM.rank1UpdateMultL(this.QH, this.u, 0, gamma, 0, k + 1, this.N);
                h[((k + 1) * this.N + k) * 2] = -this.tau.real * max;
                h[((k + 1) * this.N + k) * 2 + 1] = -this.tau.imaginary * max;
                continue;
            }
            this.gammas[k] = 0.0f;
        }
        return true;
    }

    public float[] getGammas() {
        return this.gammas;
    }
}

