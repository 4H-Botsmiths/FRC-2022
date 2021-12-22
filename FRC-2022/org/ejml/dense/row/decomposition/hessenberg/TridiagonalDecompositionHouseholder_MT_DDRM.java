/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.hessenberg;

import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.hessenberg.TridiagonalDecompositionHouseholder_DDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_MT_DDRM;

public class TridiagonalDecompositionHouseholder_MT_DDRM
extends TridiagonalDecompositionHouseholder_DDRM {
    @Override
    public void householderSymmetric(int row, double gamma) {
        int i2;
        int startU = (row - 1) * this.N;
        EjmlConcurrency.loopFor(row, this.N, i -> {
            int j;
            double total = 0.0;
            for (j = row; j < i; ++j) {
                total += this.QT.data[j * this.N + i] * this.QT.data[startU + j];
            }
            for (j = i; j < this.N; ++j) {
                total += this.QT.data[i * this.N + j] * this.QT.data[startU + j];
            }
            this.w[i] = -gamma * total;
        });
        double alpha = 0.0;
        for (i2 = row; i2 < this.N; ++i2) {
            alpha += this.QT.data[startU + i2] * this.w[i2];
        }
        alpha *= -0.5 * gamma;
        for (i2 = row; i2 < this.N; ++i2) {
            int n = i2;
            this.w[n] = this.w[n] + alpha * this.QT.data[startU + i2];
        }
        EjmlConcurrency.loopFor(row, this.N, i -> {
            double ww = this.w[i];
            double uu = this.QT.data[startU + i];
            int rowA = i * this.N;
            for (int j = i; j < this.N; ++j) {
                int n = rowA + j;
                this.QT.data[n] = this.QT.data[n] + (ww * this.QT.data[startU + j] + this.w[j] * uu);
            }
        });
    }

    @Override
    protected void rank1UpdateMultL(DMatrixRMaj A, double gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_MT_DDRM.rank1UpdateMultL(A, this.w, gamma, colA0, w0, w1);
    }

    @Override
    protected void rank1UpdateMultR(DMatrixRMaj A, double gamma, int colA0, int w0, int w1) {
        QrHelperFunctions_MT_DDRM.rank1UpdateMultR(A, this.w, gamma, colA0, w0, w1, this.b);
    }
}

