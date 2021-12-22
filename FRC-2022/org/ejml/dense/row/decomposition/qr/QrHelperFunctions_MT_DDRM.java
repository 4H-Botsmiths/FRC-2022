/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.qr;

import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.DMatrixRMaj;

public class QrHelperFunctions_MT_DDRM {
    public static void rank1UpdateMultR(DMatrixRMaj A, double[] u, double gamma, int colA0, int w0, int w1, double[] _temp) {
        int i2;
        for (i2 = colA0; i2 < A.numCols; ++i2) {
            _temp[i2] = u[w0] * A.data[w0 * A.numCols + i2];
        }
        for (int k = w0 + 1; k < w1; ++k) {
            int indexA = k * A.numCols + colA0;
            double valU = u[k];
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
            double valU = u[i];
            int indexA = i * A.numCols + colA0;
            for (int j = colA0; j < A.numCols; ++j) {
                int n = indexA++;
                A.data[n] = A.data[n] - valU * _temp[j];
            }
        });
    }

    public static void rank1UpdateMultR_u0(DMatrixRMaj A, double[] u, double u_0, double gamma, int colA0, int w0, int w1, double[] _temp) {
        int i2;
        for (i2 = colA0; i2 < A.numCols; ++i2) {
            _temp[i2] = u_0 * A.data[w0 * A.numCols + i2];
        }
        for (int k = w0 + 1; k < w1; ++k) {
            int indexA = k * A.numCols + colA0;
            double valU = u[k];
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
            double valU = u[i];
            int indexA = i * A.numCols + colA0;
            for (int j = colA0; j < A.numCols; ++j) {
                int n = indexA++;
                A.data[n] = A.data[n] - valU * _temp[j];
            }
        });
    }

    public static void rank1UpdateMultR(DMatrixRMaj A, double[] u, int offsetU, double gamma, int colA0, int w0, int w1, double[] _temp) {
        int i2;
        for (i2 = colA0; i2 < A.numCols; ++i2) {
            _temp[i2] = u[w0 + offsetU] * A.data[w0 * A.numCols + i2];
        }
        for (int k = w0 + 1; k < w1; ++k) {
            int indexA = k * A.numCols + colA0;
            double valU = u[k + offsetU];
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
            double valU = u[i + offsetU];
            int indexA = i * A.numCols + colA0;
            for (int j = colA0; j < A.numCols; ++j) {
                int n = indexA++;
                A.data[n] = A.data[n] - valU * _temp[j];
            }
        });
    }

    public static void rank1UpdateMultL(DMatrixRMaj A, double[] u, double gamma, int colA0, int w0, int w1) {
        EjmlConcurrency.loopFor(colA0, A.numRows, i -> {
            int j;
            int startIndex = i * A.numCols + w0;
            double sum = 0.0;
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

