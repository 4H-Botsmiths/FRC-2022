/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decompose;

public class TriangularSolver_ZDRM {
    public static void solveU(double[] U, double[] b, int n) {
        int stride = n * 2;
        for (int i = n - 1; i >= 0; --i) {
            double sumReal = b[i * 2];
            double sumImg = b[i * 2 + 1];
            int indexU = i * stride + i * 2 + 2;
            for (int j = i + 1; j < n; ++j) {
                double realB = b[j * 2];
                double imgB = b[j * 2 + 1];
                double realU = U[indexU++];
                double imgU = U[indexU++];
                sumReal -= realB * realU - imgB * imgU;
                sumImg -= realB * imgU + imgB * realU;
            }
            double realU = U[i * stride + i * 2];
            double imgU = U[i * stride + i * 2 + 1];
            double normU = realU * realU + imgU * imgU;
            b[i * 2] = (sumReal * realU + sumImg * imgU) / normU;
            b[i * 2 + 1] = (sumImg * realU - sumReal * imgU) / normU;
        }
    }

    public static void solveL_diagReal(double[] L, double[] b, int n) {
        int stride = n * 2;
        for (int i = 0; i < n; ++i) {
            double realSum = b[i * 2];
            double imagSum = b[i * 2 + 1];
            int indexL = i * stride;
            int indexB = 0;
            for (int k = 0; k < i; ++k) {
                double realL = L[indexL++];
                double imagL = L[indexL++];
                double realB = b[indexB++];
                double imagB = b[indexB++];
                realSum -= realL * realB - imagL * imagB;
                imagSum -= realL * imagB + imagL * realB;
            }
            double realL = L[indexL];
            b[i * 2] = realSum / realL;
            b[i * 2 + 1] = imagSum / realL;
        }
    }

    public static void solveConjTranL_diagReal(double[] L, double[] b, int n) {
        for (int i = n - 1; i >= 0; --i) {
            double realSum = b[i * 2];
            double imagSum = b[i * 2 + 1];
            int indexB = (i + 1) * 2;
            for (int k = i + 1; k < n; ++k) {
                int indexL = (k * n + i) * 2;
                double realL = L[indexL];
                double imagL = L[indexL + 1];
                double realB = b[indexB++];
                double imagB = b[indexB++];
                realSum -= realL * realB + imagL * imagB;
                imagSum -= realL * imagB - imagL * realB;
            }
            double realL = L[(i * n + i) * 2];
            b[i * 2] = realSum / realL;
            b[i * 2 + 1] = imagSum / realL;
        }
    }
}

