/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.decomposition.qr;

import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.FScalar;
import org.ejml.data.IGrowArray;
import org.ejml.sparse.csc.CommonOps_FSCC;
import org.ejml.sparse.csc.misc.ImplCommonOps_FSCC;
import org.jetbrains.annotations.Nullable;

public class QrHelperFunctions_FSCC {
    public static void applyHouseholder(FMatrixSparseCSC V, int colV, float beta, float[] x) {
        int p;
        int idx0 = V.col_idx[colV];
        int idx1 = V.col_idx[colV + 1];
        float tau = 0.0f;
        for (p = idx0; p < idx1; ++p) {
            tau += V.nz_values[p] * x[V.nz_rows[p]];
        }
        tau *= beta;
        for (p = idx0; p < idx1; ++p) {
            int n = V.nz_rows[p];
            x[n] = x[n] - V.nz_values[p] * tau;
        }
    }

    public static void rank1UpdateMultR(FMatrixSparseCSC V, int colV, float gamma, FMatrixSparseCSC A, FMatrixSparseCSC C, @Nullable IGrowArray gw, @Nullable FGrowArray gx) {
        if (V.numRows != A.numRows) {
            throw new IllegalArgumentException("Number of rows in V and A must match");
        }
        C.nz_length = 0;
        C.numRows = V.numRows;
        C.numCols = 0;
        for (int i = 0; i < A.numCols; ++i) {
            float tau = CommonOps_FSCC.dotInnerColumns(V, colV, A, i, gw, gx);
            ImplCommonOps_FSCC.addColAppend(1.0f, A, i, -gamma * tau, V, colV, C, gw);
        }
    }

    public static float computeHouseholder(float[] x, int xStart, int xEnd, float max, FScalar gamma) {
        float tau = 0.0f;
        int i = xStart;
        while (i < xEnd) {
            int n = i++;
            float f = x[n] / max;
            x[n] = f;
            float val = f;
            tau += val * val;
        }
        tau = (float)Math.sqrt(tau);
        if (x[xStart] < 0.0f) {
            tau = -tau;
        }
        float u_0 = x[xStart] + tau;
        gamma.value = u_0 / tau;
        x[xStart] = 1.0f;
        int i2 = xStart + 1;
        while (i2 < xEnd) {
            int n = i2++;
            x[n] = x[n] / u_0;
        }
        return -tau * max;
    }
}

