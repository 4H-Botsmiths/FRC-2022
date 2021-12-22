/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decompose.qr;

import org.ejml.data.Complex_F64;
import org.ejml.data.ZMatrixRMaj;

public class QrHelperFunctions_ZDRM {
    public static double findMax(double[] u, int startU, int length) {
        double max = -1.0;
        int index = startU * 2;
        int stopIndex = (startU + length) * 2;
        while (index < stopIndex) {
            double real = u[index++];
            int n = index++;
            double img = u[n];
            double val = real * real + img * img;
            if (!(val > max)) continue;
            max = val;
        }
        return Math.sqrt(max);
    }

    public static void divideElements(int j, int numRows, double[] u, int startU, double realA, double imagA) {
        double mag2 = realA * realA + imagA * imagA;
        int index = (startU + j) * 2;
        for (int i = j; i < numRows; ++i) {
            double realU = u[index];
            double imagU = u[index + 1];
            u[index++] = (realU * realA + imagU * imagA) / mag2;
            u[index++] = (imagU * realA - realU * imagA) / mag2;
        }
    }

    public static double computeTauGammaAndDivide(int start, int stop, double[] x, double max, Complex_F64 tau) {
        double bottom;
        double top;
        int index = start * 2;
        double nx = 0.0;
        for (int i = start; i < stop; ++i) {
            int n = index++;
            double d = x[n] / max;
            x[n] = d;
            double realX = d;
            int n2 = index++;
            double d2 = x[n2] / max;
            x[n2] = d2;
            double imagX = d2;
            nx += realX * realX + imagX * imagX;
        }
        nx = Math.sqrt(nx);
        double real_x0 = x[2 * start];
        double imag_x0 = x[2 * start + 1];
        double mag_x0 = Math.sqrt(real_x0 * real_x0 + imag_x0 * imag_x0);
        if (mag_x0 == 0.0) {
            tau.real = nx;
            tau.imaginary = 0.0;
        } else {
            tau.real = real_x0 / mag_x0 * nx;
            tau.imaginary = imag_x0 / mag_x0 * nx;
        }
        double m0 = QrHelperFunctions_ZDRM.mag(real_x0 + tau.real, imag_x0 + tau.imaginary);
        double m1 = QrHelperFunctions_ZDRM.mag(real_x0 - tau.real, imag_x0 - tau.imaginary);
        if (m1 > m0) {
            tau.real = -tau.real;
            tau.imaginary = -tau.imaginary;
            top = nx * nx - nx * mag_x0;
            bottom = mag_x0 * mag_x0 - 2.0 * nx * mag_x0 + nx * nx;
        } else {
            top = nx * nx + nx * mag_x0;
            bottom = mag_x0 * mag_x0 + 2.0 * nx * mag_x0 + nx * nx;
        }
        return bottom / top;
    }

    private static double mag(double r, double i) {
        return r * r + i * i;
    }

    public static void rank1UpdateMultR(ZMatrixRMaj A, double[] u, int offsetU, double gamma, int colA0, int w0, int w1, double[] _temp) {
        int i;
        int indexU = (w0 + offsetU) * 2;
        double realU = u[indexU];
        double imagU = -u[indexU + 1];
        int indexA = (w0 * A.numCols + colA0) * 2;
        int indexTmp = colA0 * 2;
        for (i = colA0; i < A.numCols; ++i) {
            double realA = A.data[indexA++];
            double imagA = A.data[indexA++];
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
                double realA = A.data[indexA++];
                double imagA = A.data[indexA++];
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
                double realTmp = _temp[indexTmp++];
                double imagTmp = _temp[indexTmp++];
                int n = indexA++;
                A.data[n] = A.data[n] - (realU * realTmp - imagU * imagTmp);
                int n4 = indexA++;
                A.data[n4] = A.data[n4] - (realU * imagTmp + imagU * realTmp);
            }
        }
    }

    public static void rank1UpdateMultL(ZMatrixRMaj A, double[] u, int offsetU, double gammaR, int colA0, int w0, int w1) {
        for (int i = colA0; i < A.numRows; ++i) {
            double realU;
            int startIndex = (i * A.numCols + w0) * 2;
            double realSum = 0.0;
            double imagSum = 0.0;
            int rowIndex = startIndex;
            int indexU = (offsetU + w0) * 2;
            for (int j = w0; j < w1; ++j) {
                double realA = A.data[rowIndex++];
                double imajA = A.data[rowIndex++];
                realU = u[indexU++];
                double imajU = u[indexU++];
                realSum += realA * realU - imajA * imajU;
                imagSum += realA * imajU + imajA * realU;
            }
            double realTmp = -gammaR * realSum;
            double imagTmp = -gammaR * imagSum;
            rowIndex = startIndex;
            indexU = (offsetU + w0) * 2;
            for (int j = w0; j < w1; ++j) {
                realU = u[indexU++];
                double imagU = -u[indexU++];
                int n = rowIndex++;
                A.data[n] = A.data[n] + (realTmp * realU - imagTmp * imagU);
                int n2 = rowIndex++;
                A.data[n2] = A.data[n2] + (realTmp * imagU + imagTmp * realU);
            }
        }
    }

    public static void extractHouseholderColumn(ZMatrixRMaj A, int row0, int row1, int col, double[] u, int offsetU) {
        int indexU = (row0 + offsetU) * 2;
        u[indexU++] = 1.0;
        u[indexU++] = 0.0;
        for (int row = row0 + 1; row < row1; ++row) {
            int indexA = A.getIndex(row, col);
            u[indexU++] = A.data[indexA];
            u[indexU++] = A.data[indexA + 1];
        }
    }

    public static void extractHouseholderRow(ZMatrixRMaj A, int row, int col0, int col1, double[] u, int offsetU) {
        int indexU = (offsetU + col0) * 2;
        u[indexU] = 1.0;
        u[indexU + 1] = 0.0;
        int indexA = (row * A.numCols + (col0 + 1)) * 2;
        System.arraycopy(A.data, indexA, u, indexU + 2, (col1 - col0 - 1) * 2);
    }

    public static double extractColumnAndMax(ZMatrixRMaj A, int row0, int row1, int col, double[] u, int offsetU) {
        int indexU = (offsetU + row0) * 2;
        double max = 0.0;
        int indexA = A.getIndex(row0, col);
        double[] h = A.data;
        int i = row0;
        while (i < row1) {
            int n = indexU++;
            double d = h[indexA];
            u[n] = d;
            double realVal = d;
            double d2 = u[indexU++] = h[indexA + 1];
            double imagVal = d2;
            double magVal = realVal * realVal + imagVal * imagVal;
            if (max < magVal) {
                max = magVal;
            }
            ++i;
            indexA += A.numCols * 2;
        }
        return Math.sqrt(max);
    }

    public static double computeRowMax(ZMatrixRMaj A, int row, int col0, int col1) {
        double max = 0.0;
        int indexA = A.getIndex(row, col0);
        double[] h = A.data;
        for (int i = col0; i < col1; ++i) {
            double realVal = h[indexA++];
            int n = indexA++;
            double imagVal = h[n];
            double magVal = realVal * realVal + imagVal * imagVal;
            if (!(max < magVal)) continue;
            max = magVal;
        }
        return Math.sqrt(max);
    }
}

