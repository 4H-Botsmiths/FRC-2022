/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decomposition.qr;

import org.ejml.data.FMatrixRMaj;

public class QrHelperFunctions_FDRM {
    public static float findMax(float[] u, int startU, int length) {
        float max = -1.0f;
        int stopIndex = startU + length;
        for (int index = startU; index < stopIndex; ++index) {
            float val = u[index];
            float f = val = val < 0.0f ? -val : val;
            if (!(val > max)) continue;
            max = val;
        }
        return max;
    }

    public static void divideElements(int j, int numRows, float[] u, float u_0) {
        int i = j;
        while (i < numRows) {
            int n = i++;
            u[n] = u[n] / u_0;
        }
    }

    public static void divideElements(int j, int numRows, float[] u, int startU, float u_0) {
        for (int i = j; i < numRows; ++i) {
            int n = i + startU;
            u[n] = u[n] / u_0;
        }
    }

    public static void divideElements_Brow(int j, int numRows, float[] u, float[] b, int startB, float u_0) {
        for (int i = j; i < numRows; ++i) {
            int n = i + startB;
            float f = b[n] / u_0;
            b[n] = f;
            u[i] = f;
        }
    }

    public static void divideElements_Bcol(int j, int numRows, int numCols, float[] u, float[] b, int startB, float u_0) {
        int indexB = j * numCols + startB;
        int i = j;
        while (i < numRows) {
            int n = i++;
            float f = u[n] / u_0;
            u[n] = f;
            b[indexB] = f;
            indexB += numCols;
        }
    }

    public static float computeTauAndDivide(int j, int numRows, float[] u, int startU, float max) {
        float tau = 0.0f;
        for (int i = j; i < numRows; ++i) {
            int n = startU + i;
            float f = u[n] / max;
            u[n] = f;
            float d = f;
            tau += d * d;
        }
        tau = (float)Math.sqrt(tau);
        if (u[startU + j] < 0.0f) {
            tau = -tau;
        }
        return tau;
    }

    public static float computeTauAndDivide(int j, int numRows, float[] u, float max) {
        float tau = 0.0f;
        int i = j;
        while (i < numRows) {
            int n = i++;
            float f = u[n] / max;
            u[n] = f;
            float d = f;
            tau += d * d;
        }
        tau = (float)Math.sqrt(tau);
        if (u[j] < 0.0f) {
            tau = -tau;
        }
        return tau;
    }

    public static void rank1UpdateMultR(FMatrixRMaj A, float[] u, float gamma, int colA0, int w0, int w1, float[] _temp) {
        int i;
        for (i = colA0; i < A.numCols; ++i) {
            _temp[i] = u[w0] * A.data[w0 * A.numCols + i];
        }
        for (int k = w0 + 1; k < w1; ++k) {
            int indexA = k * A.numCols + colA0;
            float valU = u[k];
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
            float valU = u[i];
            int indexA = i * A.numCols + colA0;
            for (int j = colA0; j < A.numCols; ++j) {
                int n = indexA++;
                A.data[n] = A.data[n] - valU * _temp[j];
            }
        }
    }

    public static void rank1UpdateMultR_u0(FMatrixRMaj A, float[] u, float u_0, float gamma, int colA0, int w0, int w1, float[] _temp) {
        int i;
        for (i = colA0; i < A.numCols; ++i) {
            _temp[i] = u_0 * A.data[w0 * A.numCols + i];
        }
        for (int k = w0 + 1; k < w1; ++k) {
            int indexA = k * A.numCols + colA0;
            float valU = u[k];
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
            float valU = u[i];
            int indexA2 = i * A.numCols + colA0;
            for (int j = colA0; j < A.numCols; ++j) {
                int n = indexA2++;
                A.data[n] = A.data[n] - valU * _temp[j];
            }
        }
    }

    public static void rank1UpdateMultR(FMatrixRMaj A, float[] u, int offsetU, float gamma, int colA0, int w0, int w1, float[] _temp) {
        int i;
        for (i = colA0; i < A.numCols; ++i) {
            _temp[i] = u[w0 + offsetU] * A.data[w0 * A.numCols + i];
        }
        for (int k = w0 + 1; k < w1; ++k) {
            int indexA = k * A.numCols + colA0;
            float valU = u[k + offsetU];
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
            float valU = u[i + offsetU];
            int indexA = i * A.numCols + colA0;
            for (int j = colA0; j < A.numCols; ++j) {
                int n = indexA++;
                A.data[n] = A.data[n] - valU * _temp[j];
            }
        }
    }

    public static void rank1UpdateMultL(FMatrixRMaj A, float[] u, float gamma, int colA0, int w0, int w1) {
        for (int i = colA0; i < A.numRows; ++i) {
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
        }
    }
}

