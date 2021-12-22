/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.decompose;

public class TriangularSolver_CDRM {
    public static void solveU(float[] U, float[] b, int n) {
        int stride = n * 2;
        for (int i = n - 1; i >= 0; --i) {
            float sumReal = b[i * 2];
            float sumImg = b[i * 2 + 1];
            int indexU = i * stride + i * 2 + 2;
            for (int j = i + 1; j < n; ++j) {
                float realB = b[j * 2];
                float imgB = b[j * 2 + 1];
                float realU = U[indexU++];
                float imgU = U[indexU++];
                sumReal -= realB * realU - imgB * imgU;
                sumImg -= realB * imgU + imgB * realU;
            }
            float realU = U[i * stride + i * 2];
            float imgU = U[i * stride + i * 2 + 1];
            float normU = realU * realU + imgU * imgU;
            b[i * 2] = (sumReal * realU + sumImg * imgU) / normU;
            b[i * 2 + 1] = (sumImg * realU - sumReal * imgU) / normU;
        }
    }

    public static void solveL_diagReal(float[] L, float[] b, int n) {
        int stride = n * 2;
        for (int i = 0; i < n; ++i) {
            float realSum = b[i * 2];
            float imagSum = b[i * 2 + 1];
            int indexL = i * stride;
            int indexB = 0;
            for (int k = 0; k < i; ++k) {
                float realL = L[indexL++];
                float imagL = L[indexL++];
                float realB = b[indexB++];
                float imagB = b[indexB++];
                realSum -= realL * realB - imagL * imagB;
                imagSum -= realL * imagB + imagL * realB;
            }
            float realL = L[indexL];
            b[i * 2] = realSum / realL;
            b[i * 2 + 1] = imagSum / realL;
        }
    }

    public static void solveConjTranL_diagReal(float[] L, float[] b, int n) {
        for (int i = n - 1; i >= 0; --i) {
            float realSum = b[i * 2];
            float imagSum = b[i * 2 + 1];
            int indexB = (i + 1) * 2;
            for (int k = i + 1; k < n; ++k) {
                int indexL = (k * n + i) * 2;
                float realL = L[indexL];
                float imagL = L[indexL + 1];
                float realB = b[indexB++];
                float imagB = b[indexB++];
                realSum -= realL * realB + imagL * imagB;
                imagSum -= realL * imagB - imagL * realB;
            }
            float realL = L[(i * n + i) * 2];
            b[i * 2] = realSum / realL;
            b[i * 2 + 1] = imagSum / realL;
        }
    }
}

