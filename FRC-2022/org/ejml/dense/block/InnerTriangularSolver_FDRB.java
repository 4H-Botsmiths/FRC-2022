/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block;

public class InnerTriangularSolver_FDRB {
    public static void invertLower(float[] L, float[] L_inv, int m, int offsetL, int offsetL_inv) {
        for (int i = 0; i < m; ++i) {
            float L_ii = L[offsetL + i * m + i];
            for (int j = 0; j < i; ++j) {
                float val = 0.0f;
                for (int k = j; k < i; ++k) {
                    val += L[offsetL + i * m + k] * L_inv[offsetL_inv + k * m + j];
                }
                L_inv[offsetL_inv + i * m + j] = -val / L_ii;
            }
            L_inv[offsetL_inv + i * m + i] = 1.0f / L_ii;
        }
    }

    public static void invertLower(float[] L, int m, int offsetL) {
        for (int i = 0; i < m; ++i) {
            float L_ii = L[offsetL + i * m + i];
            for (int j = 0; j < i; ++j) {
                float val = 0.0f;
                for (int k = j; k < i; ++k) {
                    val += L[offsetL + i * m + k] * L[offsetL + k * m + j];
                }
                L[offsetL + i * m + j] = -val / L_ii;
            }
            L[offsetL + i * m + i] = 1.0f / L_ii;
        }
    }

    public static void solveL(float[] L, float[] b, int m, int n, int strideL, int offsetL, int offsetB) {
        for (int j = 0; j < n; ++j) {
            for (int i = 0; i < m; ++i) {
                float sum = b[offsetB + i * n + j];
                for (int k = 0; k < i; ++k) {
                    sum -= L[offsetL + i * strideL + k] * b[offsetB + k * n + j];
                }
                b[offsetB + i * n + j] = sum / L[offsetL + i * strideL + i];
            }
        }
    }

    public static void solveTransL(float[] L, float[] b, int m, int n, int strideL, int offsetL, int offsetB) {
        for (int j = 0; j < n; ++j) {
            for (int i = m - 1; i >= 0; --i) {
                float sum = b[offsetB + i * n + j];
                for (int k = i + 1; k < m; ++k) {
                    sum -= L[offsetL + k * strideL + i] * b[offsetB + k * n + j];
                }
                b[offsetB + i * n + j] = sum / L[offsetL + i * strideL + i];
            }
        }
    }

    public static void solveLTransB(float[] L, float[] b, int m, int n, int strideL, int offsetL, int offsetB) {
        for (int j = 0; j < n; ++j) {
            for (int i = 0; i < m; ++i) {
                float sum = b[offsetB + j * m + i];
                int l = offsetL + i * strideL;
                int bb = offsetB + j * m;
                int endL = l + i;
                while (l != endL) {
                    sum -= L[l++] * b[bb++];
                }
                b[offsetB + j * m + i] = sum / L[offsetL + i * strideL + i];
            }
        }
    }

    public static void solveU(float[] U, float[] b, int m, int n, int strideU, int offsetU, int offsetB) {
        for (int j = 0; j < n; ++j) {
            for (int i = m - 1; i >= 0; --i) {
                float sum = b[offsetB + i * n + j];
                for (int k = i + 1; k < m; ++k) {
                    sum -= U[offsetU + i * strideU + k] * b[offsetB + k * n + j];
                }
                b[offsetB + i * n + j] = sum / U[offsetU + i * strideU + i];
            }
        }
    }

    public static void solveTransU(float[] U, float[] b, int m, int n, int strideU, int offsetU, int offsetB) {
        for (int j = 0; j < n; ++j) {
            for (int i = 0; i < m; ++i) {
                float sum = b[offsetB + i * n + j];
                for (int k = 0; k < i; ++k) {
                    sum -= U[offsetU + k * strideU + i] * b[offsetB + k * n + j];
                }
                b[offsetB + i * n + j] = sum / U[offsetU + i * strideU + i];
            }
        }
    }
}

