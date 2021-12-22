/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.qr;

import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.FMatrixRMaj;

public class QrHelperFunctions_MT_FDRM {
    public static void rank1UpdateMultR(FMatrixRMaj A, float[] u, float gamma, int colA0, int w0, int w1, float[] _temp) {
        int i2;
        for (i2 = colA0; i2 < A.numCols; ++i2) {
            _temp[i2] = u[w0] * A.data[w0 * A.numCols + i2];
        }
        for (int k = w0 + 1; k < w1; ++k) {
            int indexA = k * A.numCols + colA0;
            float valU = u[k];
            int i3 = colA0;
            while (i3 < A.numCols) {
                int n = i3++;
                _temp[n] = _temp[n] + valU * A.data[indexA++];
            }
        }
        i2 = colA0;
        while (i2 < A.numCols) {
            int n = i2++;
            _temp[n] = _temp[n] * gamma;
        }
        EjmlConcurrency.loopFor(w0, w1, i -> {
            float valU = u[i];
            int indexA = i * A.numCols + colA0;
            for (int j = colA0; j < A.numCols; ++j) {
                int n = indexA++;
                A.data[n] = A.data[n] - valU * _temp[j];
            }
        });
    }

    public static void rank1UpdateMultR_u0(FMatrixRMaj A, float[] u, float u_0, float gamma, int colA0, int w0, int w1, float[] _temp) {
        int i2;
        for (i2 = colA0; i2 < A.numCols; ++i2) {
            _temp[i2] = u_0 * A.data[w0 * A.numCols + i2];
        }
        for (int k = w0 + 1; k < w1; ++k) {
            int indexA = k * A.numCols + colA0;
            float valU = u[k];
            int i3 = colA0;
            while (i3 < A.numCols) {
                int n = i3++;
                _temp[n] = _temp[n] + valU * A.data[indexA++];
            }
        }
        i2 = colA0;
        while (i2 < A.numCols) {
            int n = i2++;
            _temp[n] = _temp[n] * gamma;
        }
        int indexA = w0 * A.numCols + colA0;
        for (int j = colA0; j < A.numCols; ++j) {
            int n = indexA++;
            A.data[n] = A.data[n] - u_0 * _temp[j];
        }
        EjmlConcurrency.loopFor(w0 + 1, w1, i -> {
            float valU = u[i];
            int indexA = i * A.numCols + colA0;
            for (int j = colA0; j < A.numCols; ++j) {
                int n = indexA++;
                A.data[n] = A.data[n] - valU * _temp[j];
            }
        });
    }

    public static void rank1UpdateMultR(FMatrixRMaj A, float[] u, int offsetU, float gamma, int colA0, int w0, int w1, float[] _temp) {
        int i2;
        for (i2 = colA0; i2 < A.numCols; ++i2) {
            _temp[i2] = u[w0 + offsetU] * A.data[w0 * A.numCols + i2];
        }
        for (int k = w0 + 1; k < w1; ++k) {
            int indexA = k * A.numCols + colA0;
            float valU = u[k + offsetU];
            int i3 = colA0;
            while (i3 < A.numCols) {
                int n = i3++;
                _temp[n] = _temp[n] + valU * A.data[indexA++];
            }
        }
        i2 = colA0;
        while (i2 < A.numCols) {
            int n = i2++;
            _temp[n] = _temp[n] * gamma;
        }
        EjmlConcurrency.loopFor(w0, w1, i -> {
            float valU = u[i + offsetU];
            int indexA = i * A.numCols + colA0;
            for (int j = colA0; j < A.numCols; ++j) {
                int n = indexA++;
                A.data[n] = A.data[n] - valU * _temp[j];
            }
        });
    }

    public static void rank1UpdateMultL(FMatrixRMaj A, float[] u, float gamma, int colA0, int w0, int w1) {
        EjmlConcurrency.loopFor(colA0, A.numRows, i -> {
            int j;
            int startIndex = i * A.numCols + w0;
            float sum = 0.0f;
            int rowIndex = startIndex;
            for (j = w0; j < w1; ++j) {
                sum += A.data[rowIndex++] * u[j];
            }
            sum = -gamma * sum;
            rowIndex = startIndex;
            for (j = w0; j < w1; ++j) {
                int n = rowIndex++;
                A.data[n] = A.data[n] + sum * u[j];
            }
        });
    }
}

