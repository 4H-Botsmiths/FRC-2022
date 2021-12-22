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
import org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition_F32;
import org.jetbrains.annotations.Nullable;

public class TridiagonalDecompositionHouseholder_CDRM
implements TridiagonalSimilarDecomposition_F32<CMatrixRMaj> {
    private CMatrixRMaj QT;
    private int N = 1;
    private float[] w;
    private float[] gammas;
    private float[] b;
    private final Complex_F32 tau = new Complex_F32();

    public TridiagonalDecompositionHouseholder_CDRM() {
        this.w = new float[this.N * 2];
        this.b = new float[this.N * 2];
        this.gammas = new float[this.N];
    }

    public CMatrixRMaj getQT() {
        return this.QT;
    }

    @Override
    public void getDiagonal(float[] diag, float[] off) {
        for (int i = 0; i < this.N; ++i) {
            diag[i * 2] = this.QT.data[(i * this.N + i) * 2];
            diag[i * 2 + 1] = this.QT.data[(i * this.N + i) * 2 + 1];
            if (i + 1 >= this.N) continue;
            off[i * 2] = this.QT.data[(i * this.N + i + 1) * 2];
            off[i * 2 + 1] = this.QT.data[(i * this.N + i + 1) * 2 + 1];
        }
    }

    @Override
    public CMatrixRMaj getT(@Nullable CMatrixRMaj T) {
        T = UtilDecompositons_CDRM.checkZeros(T, this.N, this.N);
        T.data[0] = this.QT.data[0];
        T.data[1] = this.QT.data[1];
        for (int i = 1; i < this.N; ++i) {
            T.set(i, i, this.QT.getReal(i, i), this.QT.getImag(i, i));
            float real = this.QT.getReal(i - 1, i);
            float imag = this.QT.getImag(i - 1, i);
            T.set(i - 1, i, real, imag);
            T.set(i, i - 1, real, -imag);
        }
        return T;
    }

    @Override
    public CMatrixRMaj getQ(@Nullable CMatrixRMaj Q, boolean transposed) {
        Q = UtilDecompositons_CDRM.checkIdentity(Q, this.N, this.N);
        Arrays.fill(this.w, 0, this.N * 2, 0.0f);
        if (transposed) {
            for (int j = this.N - 2; j >= 0; --j) {
                QrHelperFunctions_CDRM.extractHouseholderRow(this.QT, j, j + 1, this.N, this.w, 0);
                QrHelperFunctions_CDRM.rank1UpdateMultL(Q, this.w, 0, this.gammas[j], j + 1, j + 1, this.N);
            }
        } else {
            for (int j = this.N - 2; j >= 0; --j) {
                QrHelperFunctions_CDRM.extractHouseholderRow(this.QT, j, j + 1, this.N, this.w, 0);
                QrHelperFunctions_CDRM.rank1UpdateMultR(Q, this.w, 0, this.gammas[j], j + 1, j + 1, this.N, this.b);
            }
        }
        return Q;
    }

    @Override
    public boolean decompose(CMatrixRMaj A) {
        this.init(A);
        for (int k = 0; k < this.N - 1; ++k) {
            this.similarTransform(k);
        }
        return true;
    }

    private void similarTransform(int k) {
        float[] t = this.QT.data;
        float max = QrHelperFunctions_CDRM.computeRowMax(this.QT, k, k + 1, this.N);
        if (max > 0.0f) {
            float gamma;
            this.gammas[k] = gamma = QrHelperFunctions_CDRM.computeTauGammaAndDivide(k * this.N + k + 1, k * this.N + this.N, t, max, this.tau);
            float real_u_0 = t[(k * this.N + k + 1) * 2] + this.tau.real;
            float imag_u_0 = t[(k * this.N + k + 1) * 2 + 1] + this.tau.imaginary;
            QrHelperFunctions_CDRM.divideElements(k + 2, this.N, t, k * this.N, real_u_0, imag_u_0);
            for (int i = k + 2; i < this.N; ++i) {
                t[(k * this.N + i) * 2 + 1] = -t[(k * this.N + i) * 2 + 1];
            }
            t[(k * this.N + k + 1) * 2] = 1.0f;
            t[(k * this.N + k + 1) * 2 + 1] = 0.0f;
            this.householderSymmetric(k, gamma);
            t[(k * this.N + k + 1) * 2] = -this.tau.real * max;
            t[(k * this.N + k + 1) * 2 + 1] = -this.tau.imaginary * max;
        } else {
            this.gammas[k] = 0.0f;
        }
    }

    public void householderSymmetric(int row, float gamma) {
        float imagU;
        int i;
        int startU = row * this.N;
        for (int i2 = row + 1; i2 < this.N; ++i2) {
            float imagU2;
            float realU;
            float imagA;
            float realA;
            int j;
            float totalReal = 0.0f;
            float totalImag = 0.0f;
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
        float realAplha = 0.0f;
        float imageAlpha = 0.0f;
        for (i = row + 1; i < this.N; ++i) {
            float realU = this.QT.data[(startU + i) * 2];
            imagU = -this.QT.data[(startU + i) * 2 + 1];
            float realV = this.w[i * 2];
            float imagV = this.w[i * 2 + 1];
            realAplha += realU * realV - imagU * imagV;
            imageAlpha += realU * imagV + imagU * realV;
        }
        realAplha *= -0.5f * gamma;
        imageAlpha *= -0.5f * gamma;
        for (i = row + 1; i < this.N; ++i) {
            float realU = this.QT.data[(startU + i) * 2];
            imagU = this.QT.data[(startU + i) * 2 + 1];
            int n = i * 2;
            this.w[n] = this.w[n] + (realAplha * realU - imageAlpha * imagU);
            int n2 = i * 2 + 1;
            this.w[n2] = this.w[n2] + (realAplha * imagU + imageAlpha * realU);
        }
        for (i = row + 1; i < this.N; ++i) {
            float realWW = this.w[i * 2];
            float imagWW = this.w[i * 2 + 1];
            float realUU = this.QT.data[(startU + i) * 2];
            float imagUU = this.QT.data[(startU + i) * 2 + 1];
            int indA = (i * this.N + i) * 2;
            for (int j = i; j < this.N; ++j) {
                float realU = this.QT.data[(startU + j) * 2];
                float imagU3 = -this.QT.data[(startU + j) * 2 + 1];
                float realW = this.w[j * 2];
                float imagW = -this.w[j * 2 + 1];
                int n = indA++;
                this.QT.data[n] = this.QT.data[n] + (realWW * realU - imagWW * imagU3 + realW * realUU - imagW * imagUU);
                int n3 = indA++;
                this.QT.data[n3] = this.QT.data[n3] + (realWW * imagU3 + imagWW * realU + realW * imagUU + imagW * realUU);
            }
        }
    }

    public void init(CMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            throw new IllegalArgumentException("Must be square");
        }
        if (A.numCols != this.N) {
            this.N = A.numCols;
            if (this.w.length < this.N) {
                this.w = new float[this.N * 2];
                this.gammas = new float[this.N * 2];
                this.b = new float[this.N * 2];
            }
        }
        this.QT = A;
    }

    @Override
    public boolean inputModified() {
        return true;
    }

    public float[] getGammas() {
        return this.gammas;
    }
}

