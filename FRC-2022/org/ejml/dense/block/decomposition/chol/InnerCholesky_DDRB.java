/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.block.decomposition.chol;

import org.ejml.data.DMatrixD1;
import org.ejml.data.DSubmatrixD1;

public class InnerCholesky_DDRB {
    public static boolean upper(DSubmatrixD1 T) {
        int n = T.row1 - T.row0;
        int indexT = T.row0 * ((DMatrixD1)T.original).numCols + T.col0 * n;
        return InnerCholesky_DDRB.upper(((DMatrixD1)T.original).data, indexT, n);
    }

    public static boolean lower(DSubmatrixD1 T) {
        int n = T.row1 - T.row0;
        int indexT = T.row0 * ((DMatrixD1)T.original).numCols + T.col0 * n;
        return InnerCholesky_DDRB.lower(((DMatrixD1)T.original).data, indexT, n);
    }

    public static boolean upper(double[] T, int indexT, int n) {
        double div_el_ii = 0.0;
        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; ++j) {
                double sum = T[indexT + i * n + j];
                for (int k = 0; k < i; ++k) {
                    sum -= T[indexT + k * n + i] * T[indexT + k * n + j];
                }
                if (i == j) {
                    double el_ii;
                    if (sum <= 0.0) {
                        return false;
                    }
                    T[indexT + i * n + i] = el_ii = Math.sqrt(sum);
                    div_el_ii = 1.0 / el_ii;
                    continue;
                }
                T[indexT + i * n + j] = sum * div_el_ii;
            }
        }
        return true;
    }

    public static boolean lower(double[] T, int indexT, int n) {
        double div_el_ii = 0.0;
        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; ++j) {
                double sum = T[indexT + j * n + i];
                for (int k = 0; k < i; ++k) {
                    sum -= T[indexT + i * n + k] * T[indexT + j * n + k];
                }
                if (i == j) {
                    double el_ii;
                    if (sum <= 0.0) {
                        return false;
                    }
                    T[indexT + i * n + i] = el_ii = Math.sqrt(sum);
                    div_el_ii = 1.0 / el_ii;
                    continue;
                }
                T[indexT + j * n + i] = sum * div_el_ii;
            }
        }
        return true;
    }
}

