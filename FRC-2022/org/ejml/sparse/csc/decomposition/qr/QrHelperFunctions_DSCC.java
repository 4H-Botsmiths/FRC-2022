/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.decomposition.qr;

import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.DScalar;
import org.ejml.data.IGrowArray;
import org.ejml.sparse.csc.CommonOps_DSCC;
import org.ejml.sparse.csc.misc.ImplCommonOps_DSCC;
import org.jetbrains.annotations.Nullable;

public class QrHelperFunctions_DSCC {
    public static void applyHouseholder(DMatrixSparseCSC V, int colV, double beta, double[] x) {
        int p;
        int idx0 = V.col_idx[colV];
        int idx1 = V.col_idx[colV + 1];
        double tau = 0.0;
        for (p = idx0; p < idx1; ++p) {
            tau += V.nz_values[p] * x[V.nz_rows[p]];
        }
        tau *= beta;
        for (p = idx0; p < idx1; ++p) {
            int n = V.nz_rows[p];
            x[n] = x[n] - V.nz_values[p] * tau;
        }
    }

    public static void rank1UpdateMultR(DMatrixSparseCSC V, int colV, double gamma, DMatrixSparseCSC A, DMatrixSparseCSC C, @Nullable IGrowArray gw, @Nullable DGrowArray gx) {
        if (V.numRows != A.numRows) {
            throw new IllegalArgumentException("Number of rows in V and A must match");
        }
        C.nz_length = 0;
        C.numRows = V.numRows;
        C.numCols = 0;
        for (int i = 0; i < A.numCols; ++i) {
            double tau = CommonOps_DSCC.dotInnerColumns(V, colV, A, i, gw, gx);
            ImplCommonOps_DSCC.addColAppend(1.0, A, i, -gamma * tau, V, colV, C, gw);
        }
    }

    public static double computeHouseholder(double[] x, int xStart, int xEnd, double max, DScalar gamma) {
        double tau = 0.0;
        int i = xStart;
        while (i < xEnd) {
            int n = i++;
            double d = x[n] / max;
            x[n] = d;
            double val = d;
            tau += val * val;
        }
        tau = Math.sqrt(tau);
        if (x[xStart] < 0.0) {
            tau = -tau;
        }
        double u_0 = x[xStart] + tau;
        gamma.value = u_0 / tau;
        x[xStart] = 1.0;
        int i2 = xStart + 1;
        while (i2 < xEnd) {
            int n = i2++;
            x[n] = x[n] / u_0;
        }
        return -tau * max;
    }
}

