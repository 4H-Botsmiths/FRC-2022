/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.qr;

import org.ejml.data.DMatrixRMaj;

public class QrHelperFunctions_DDRM {
    public static double findMax(double[] u, int startU, int length) {
        double max = -1.0;
        int stopIndex = startU + length;
        for (int index = startU; index < stopIndex; ++index) {
            double val = u[index];
            double d = val = val < 0.0 ? -val : val;
            if (!(val > max)) continue;
            max = val;
        }
        return max;
    }

    public static void divideElements(int j, int numRows, double[] u, double u_0) {
        int i = j;
        while (i < numRows) {
            int n = i++;
            u[n] = u[n] / u_0;
        }
    }

    public static void divideElements(int j, int numRows, double[] u, int startU, double u_0) {
        for (int i = j; i < numRows; ++i) {
            int n = i + startU;
            u[n] = u[n] / u_0;
        }
    }

    public static void divideElements_Brow(int j, int numRows, double[] u, double[] b, int startB, double u_0) {
        for (int i = j; i < numRows; ++i) {
            int n = i + startB;
            double d = b[n] / u_0;
            b[n] = d;
            u[i] = d;
        }
    }

    public static void divideElements_Bcol(int j, int numRows, int numCols, double[] u, double[] b, int startB, double u_0) {
        int indexB = j * numCols + startB;
        int i = j;
        while (i < numRows) {
            int n = i++;
            double d = u[n] / u_0;
            u[n] = d;
            b[indexB] = d;
            indexB += numCols;
        }
    }

    public static double computeTauAndDivide(int j, int numRows, double[] u, int startU, double max) {
        double tau = 0.0;
        for (int i = j; i < numRows; ++i) {
            int n = startU + i;
            double d = u[n] / max;
            u[n] = d;
            double d2 = d;
            tau += d2 * d2;
        }
        tau = Math.sqrt(tau);
        if (u[startU + j] < 0.0) {
            tau = -tau;
        }
        return tau;
    }

    public static double computeTauAndDivide(int j, int numRows, double[] u, double max) {
        double tau = 0.0;
        int i = j;
        while (i < numRows) {
            int n = i++;
            double d = u[n] / max;
            u[n] = d;
            double d2 = d;
            tau += d2 * d2;
        }
        tau = Math.sqrt(tau);
        if (u[j] < 0.0) {
            tau = -tau;
        }
        return tau;
    }

    public static void rank1UpdateMultR(DMatrixRMaj A, double[] u, double gamma, int colA0, int w0, int w1, double[] _temp) {
        int i;
        for (i = colA0; i < A.numCols; ++i) {
            _temp[i] = u[w0] * A.data[w0 * A.numCols + i];
        }
        for (int k = w0 + 1; k < w1; ++k) {
            int indexA = k * A.numCols + colA0;
            double valU = u[k];
            int i2 = colA0;
            while (i2 < A.numCols) {
                int n = i2++;
                _temp[n] = _temp[n] + valU * A.data[indexA++];
            }
        }
        i = colA0;
        while (i < A.numCols) {
            int n = i++;
            _temp[n] = _temp[n] * gamma;
        }
        for (i = w0; i < w1; ++i) {
            double valU = u[i];
            int indexA = i * A.numCols + colA0;
            for (int j = colA0; j < A.numCols; ++j) {
                int n = indexA++;
                A.data[n] = A.data[n] - valU * _temp[j];
            }
        }
    }

    public static void rank1UpdateMultR_u0(DMatrixRMaj A, double[] u, double u_0, double gamma, int colA0, int w0, int w1, double[] _temp) {
        int i;
        for (i = colA0; i < A.numCols; ++i) {
            _temp[i] = u_0 * A.data[w0 * A.numCols + i];
        }
        for (int k = w0 + 1; k < w1; ++k) {
            int indexA = k * A.numCols + colA0;
            double valU = u[k];
            int i2 = colA0;
            while (i2 < A.numCols) {
                int n = i2++;
                _temp[n] = _temp[n] + valU * A.data[indexA++];
            }
        }
        i = colA0;
        while (i < A.numCols) {
            int n = i++;
            _temp[n] = _temp[n] * gamma;
        }
        int indexA = w0 * A.numCols + colA0;
        for (int j = colA0; j < A.numCols; ++j) {
            int n = indexA++;
            A.data[n] = A.data[n] - u_0 * _temp[j];
        }
        for (i = w0 + 1; i < w1; ++i) {
            double valU = u[i];
            int indexA2 = i * A.numCols + colA0;
            for (int j = colA0; j < A.numCols; ++j) {
                int n = indexA2++;
                A.data[n] = A.data[n] - valU * _temp[j];
            }
        }
    }

    public static void rank1UpdateMultR(DMatrixRMaj A, double[] u, int offsetU, double gamma, int colA0, int w0, int w1, double[] _temp) {
        int i;
        for (i = colA0; i < A.numCols; ++i) {
            _temp[i] = u[w0 + offsetU] * A.data[w0 * A.numCols + i];
        }
        for (int k = w0 + 1; k < w1; ++k) {
            int indexA = k * A.numCols + colA0;
            double valU = u[k + offsetU];
            int i2 = colA0;
            while (i2 < A.numCols) {
                int n = i2++;
                _temp[n] = _temp[n] + valU * A.data[indexA++];
            }
        }
        i = colA0;
        while (i < A.numCols) {
            int n = i++;
            _temp[n] = _temp[n] * gamma;
        }
        for (i = w0; i < w1; ++i) {
            double valU = u[i + offsetU];
            int indexA = i * A.numCols + colA0;
            for (int j = colA0; j < A.numCols; ++j) {
                int n = indexA++;
                A.data[n] = A.data[n] - valU * _temp[j];
            }
        }
    }

    public static void rank1UpdateMultL(DMatrixRMaj A, double[] u, double gamma, int colA0, int w0, int w1) {
        for (int i = colA0; i < A.numRows; ++i) {
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
        }
    }
}

