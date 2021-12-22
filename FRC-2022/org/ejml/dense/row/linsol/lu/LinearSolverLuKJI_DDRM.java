/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.lu;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_DDRM;
import org.ejml.dense.row.decomposition.lu.LUDecompositionBase_DDRM;
import org.ejml.dense.row.linsol.lu.LinearSolverLuBase_DDRM;

public class LinearSolverLuKJI_DDRM
extends LinearSolverLuBase_DDRM {
    private double[] dataLU;
    private int[] pivot;

    public LinearSolverLuKJI_DDRM(LUDecompositionBase_DDRM decomp) {
        super(decomp);
    }

    @Override
    public boolean setA(DMatrixRMaj A) {
        boolean ret = super.setA(A);
        this.pivot = this.decomp.getPivot();
        this.dataLU = this.decomp.getLU().data;
        return ret;
    }

    @Override
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        int j;
        int i;
        int k;
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        if (B == X) {
            throw new IllegalArgumentException("Current doesn't support using the same matrix instance");
        }
        SpecializedOps_DDRM.copyChangeRow(this.pivot, B, X);
        int nx = B.numCols;
        double[] dataX = X.data;
        for (k = 0; k < this.numCols; ++k) {
            for (i = k + 1; i < this.numCols; ++i) {
                for (j = 0; j < nx; ++j) {
                    int n = i * nx + j;
                    dataX[n] = dataX[n] - dataX[k * nx + j] * this.dataLU[i * this.numCols + k];
                }
            }
        }
        for (k = this.numCols - 1; k >= 0; --k) {
            for (int j2 = 0; j2 < nx; ++j2) {
                int n = k * nx + j2;
                dataX[n] = dataX[n] / this.dataLU[k * this.numCols + k];
            }
            for (i = 0; i < k; ++i) {
                for (j = 0; j < nx; ++j) {
                    int n = i * nx + j;
                    dataX[n] = dataX[n] - dataX[k * nx + j] * this.dataLU[i * this.numCols + k];
                }
            }
        }
    }
}

