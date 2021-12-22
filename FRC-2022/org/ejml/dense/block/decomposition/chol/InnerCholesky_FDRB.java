/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block.decomposition.chol;

import org.ejml.data.FMatrixD1;
import org.ejml.data.FSubmatrixD1;

public class InnerCholesky_FDRB {
    public static boolean upper(FSubmatrixD1 T) {
        int n = T.row1 - T.row0;
        int indexT = T.row0 * ((FMatrixD1)T.original).numCols + T.col0 * n;
        return InnerCholesky_FDRB.upper(((FMatrixD1)T.original).data, indexT, n);
    }

    public static boolean lower(FSubmatrixD1 T) {
        int n = T.row1 - T.row0;
        int indexT = T.row0 * ((FMatrixD1)T.original).numCols + T.col0 * n;
        return InnerCholesky_FDRB.lower(((FMatrixD1)T.original).data, indexT, n);
    }

    public static boolean upper(float[] T, int indexT, int n) {
        float div_el_ii = 0.0f;
        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; ++j) {
                float sum = T[indexT + i * n + j];
                for (int k = 0; k < i; ++k) {
                    sum -= T[indexT + k * n + i] * T[indexT + k * n + j];
                }
                if (i == j) {
                    float el_ii;
                    if (sum <= 0.0f) {
                        return false;
                    }
                    T[indexT + i * n + i] = el_ii = (float)Math.sqrt(sum);
                    div_el_ii = 1.0f / el_ii;
                    continue;
                }
                T[indexT + i * n + j] = sum * div_el_ii;
            }
        }
        return true;
    }

    public static boolean lower(float[] T, int indexT, int n) {
        float div_el_ii = 0.0f;
        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; ++j) {
                float sum = T[indexT + j * n + i];
                for (int k = 0; k < i; ++k) {
                    sum -= T[indexT + i * n + k] * T[indexT + j * n + k];
                }
                if (i == j) {
                    float el_ii;
                    if (sum <= 0.0f) {
                        return false;
                    }
                    T[indexT + i * n + i] = el_ii = (float)Math.sqrt(sum);
                    div_el_ii = 1.0f / el_ii;
                    continue;
                }
                T[indexT + j * n + i] = sum * div_el_ii;
            }
        }
        return true;
    }
}

