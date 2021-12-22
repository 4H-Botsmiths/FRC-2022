/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decompose.qr;

import org.ejml.data.CMatrixRMaj;
import org.ejml.data.Complex_F32;

public class QrHelperFunctions_CDRM {
    public static float findMax(float[] u, int startU, int length) {
        float max = -1.0f;
        int index = startU * 2;
        int stopIndex = (startU + length) * 2;
        while (index < stopIndex) {
            float real = u[index++];
            int n = index++;
            float img = u[n];
            float val = real * real + img * img;
            if (!(val > max)) continue;
            max = val;
        }
        return (float)Math.sqrt(max);
    }

    public static void divideElements(int j, int numRows, float[] u, int startU, float realA, float imagA) {
        float mag2 = realA * realA + imagA * imagA;
        int index = (startU + j) * 2;
        for (int i = j; i < numRows; ++i) {
            float realU = u[index];
            float imagU = u[index + 1];
            u[index++] = (realU * realA + imagU * imagA) / mag2;
            u[index++] = (imagU * realA - realU * imagA) / mag2;
        }
    }

    public static float computeTauGammaAndDivide(int start, int stop, float[] x, float max, Complex_F32 tau) {
        float bottom;
        float top;
        int index = start * 2;
        float nx = 0.0f;
        for (int i = start; i < stop; ++i) {
            int n = index++;
            float f = x[n] / max;
            x[n] = f;
            float realX = f;
            int n2 = index++;
            float f2 = x[n2] / max;
            x[n2] = f2;
            float imagX = f2;
            nx += realX * realX + imagX * imagX;
        }
        nx = (float)Math.sqrt(nx);
        float real_x0 = x[2 * start];
        float imag_x0 = x[2 * start + 1];
        float mag_x0 = (float)Math.sqrt(real_x0 * real_x0 + imag_x0 * imag_x0);
        if (mag_x0 == 0.0f) {
            tau.real = nx;
            tau.imaginary = 0.0f;
        } else {
            tau.real = real_x0 / mag_x0 * nx;
            tau.imaginary = imag_x0 / mag_x0 * nx;
        }
        float m0 = QrHelperFunctions_CDRM.mag(real_x0 + tau.real, imag_x0 + tau.imaginary);
        float m1 = QrHelperFunctions_CDRM.mag(real_x0 - tau.real, imag_x0 - tau.imaginary);
        if (m1 > m0) {
            tau.real = -tau.real;
            tau.imaginary = -tau.imaginary;
            top = nx * nx - nx * mag_x0;
            bottom = mag_x0 * mag_x0 - 2.0f * nx * mag_x0 + nx * nx;
        } else {
            top = nx * nx + nx * mag_x0;
            bottom = mag_x0 * mag_x0 + 2.0f * nx * mag_x0 + nx * nx;
        }
        return bottom / top;
    }

    private static float mag(float r, float i) {
        return r * r + i * i;
    }

    public static void rank1UpdateMultR(CMatrixRMaj A, float[] u, int offsetU, float gamma, int colA0, int w0, int w1, float[] _temp) {
        int i;
        int indexU = (w0 + offsetU) * 2;
        float realU = u[indexU];
        float imagU = -u[indexU + 1];
        int indexA = (w0 * A.numCols + colA0) * 2;
        int indexTmp = colA0 * 2;
        for (i = colA0; i < A.numCols; ++i) {
            float realA = A.data[indexA++];
            float imagA = A.data[indexA++];
            _temp[indexTmp++] = realU * realA - imagU * imagA;
            _temp[indexTmp++] = realU * imagA + imagU * realA;
        }
        for (int k = w0 + 1; k < w1; ++k) {
            indexA = (k * A.numCols + colA0) * 2;
            indexU = (k + offsetU) * 2;
            indexTmp = colA0 * 2;
            realU = u[indexU];
            imagU = -u[indexU + 1];
            for (int i2 = colA0; i2 < A.numCols; ++i2) {
                float realA = A.data[indexA++];
                float imagA = A.data[indexA++];
                int n = indexTmp++;
                _temp[n] = _temp[n] + (realU * realA - imagU * imagA);
                int n2 = indexTmp++;
                _temp[n2] = _temp[n2] + (realU * imagA + imagU * realA);
            }
        }
        indexTmp = colA0 * 2;
        for (i = colA0; i < A.numCols; ++i) {
            int n = indexTmp++;
            _temp[n] = _temp[n] * gamma;
            int n3 = indexTmp++;
            _temp[n3] = _temp[n3] * gamma;
        }
        for (i = w0; i < w1; ++i) {
            indexA = (i * A.numCols + colA0) * 2;
            indexU = (i + offsetU) * 2;
            indexTmp = colA0 * 2;
            realU = u[indexU];
            imagU = u[indexU + 1];
            for (int j = colA0; j < A.numCols; ++j) {
                float realTmp = _temp[indexTmp++];
                float imagTmp = _temp[indexTmp++];
                int n = indexA++;
                A.data[n] = A.data[n] - (realU * realTmp - imagU * imagTmp);
                int n4 = indexA++;
                A.data[n4] = A.data[n4] - (realU * imagTmp + imagU * realTmp);
            }
        }
    }

    public static void rank1UpdateMultL(CMatrixRMaj A, float[] u, int offsetU, float gammaR, int colA0, int w0, int w1) {
        for (int i = colA0; i < A.numRows; ++i) {
            float realU;
            int startIndex = (i * A.numCols + w0) * 2;
            float realSum = 0.0f;
            float imagSum = 0.0f;
            int rowIndex = startIndex;
            int indexU = (offsetU + w0) * 2;
            for (int j = w0; j < w1; ++j) {
                float realA = A.data[rowIndex++];
                float imajA = A.data[rowIndex++];
                realU = u[indexU++];
                float imajU = u[indexU++];
                realSum += realA * realU - imajA * imajU;
                imagSum += realA * imajU + imajA * realU;
            }
            float realTmp = -gammaR * realSum;
            float imagTmp = -gammaR * imagSum;
            rowIndex = startIndex;
            indexU = (offsetU + w0) * 2;
            for (int j = w0; j < w1; ++j) {
                realU = u[indexU++];
                float imagU = -u[indexU++];
                int n = rowIndex++;
                A.data[n] = A.data[n] + (realTmp * realU - imagTmp * imagU);
                int n2 = rowIndex++;
                A.data[n2] = A.data[n2] + (realTmp * imagU + imagTmp * realU);
            }
        }
    }

    public static void extractHouseholderColumn(CMatrixRMaj A, int row0, int row1, int col, float[] u, int offsetU) {
        int indexU = (row0 + offsetU) * 2;
        u[indexU++] = 1.0f;
        u[indexU++] = 0.0f;
        for (int row = row0 + 1; row < row1; ++row) {
            int indexA = A.getIndex(row, col);
            u[indexU++] = A.data[indexA];
            u[indexU++] = A.data[indexA + 1];
        }
    }

    public static void extractHouseholderRow(CMatrixRMaj A, int row, int col0, int col1, float[] u, int offsetU) {
        int indexU = (offsetU + col0) * 2;
        u[indexU] = 1.0f;
        u[indexU + 1] = 0.0f;
        int indexA = (row * A.numCols + (col0 + 1)) * 2;
        System.arraycopy(A.data, indexA, u, indexU + 2, (col1 - col0 - 1) * 2);
    }

    public static float extractColumnAndMax(CMatrixRMaj A, int row0, int row1, int col, float[] u, int offsetU) {
        int indexU = (offsetU + row0) * 2;
        float max = 0.0f;
        int indexA = A.getIndex(row0, col);
        float[] h = A.data;
        int i = row0;
        while (i < row1) {
            int n = indexU++;
            float f = h[indexA];
            u[n] = f;
            float realVal = f;
            float f2 = u[indexU++] = h[indexA + 1];
            float imagVal = f2;
            float magVal = realVal * realVal + imagVal * imagVal;
            if (max < magVal) {
                max = magVal;
            }
            ++i;
            indexA += A.numCols * 2;
        }
        return (float)Math.sqrt(max);
    }

    public static float computeRowMax(CMatrixRMaj A, int row, int col0, int col1) {
        float max = 0.0f;
        int indexA = A.getIndex(row, col0);
        float[] h = A.data;
        for (int i = col0; i < col1; ++i) {
            float realVal = h[indexA++];
            int n = indexA++;
            float imagVal = h[n];
            float magVal = realVal * realVal + imagVal * imagVal;
            if (!(max < magVal)) continue;
            max = magVal;
        }
        return (float)Math.sqrt(max);
    }
}

