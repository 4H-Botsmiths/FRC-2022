/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.lu;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.lu.LUDecompositionBase_FDRM;
import org.ejml.dense.row.linsol.lu.LinearSolverLuBase_FDRM;

public class LinearSolverLu_FDRM
extends LinearSolverLuBase_FDRM {
    boolean doImprove = false;

    public LinearSolverLu_FDRM(LUDecompositionBase_FDRM decomp) {
        super(decomp);
    }

    public LinearSolverLu_FDRM(LUDecompositionBase_FDRM decomp, boolean doImprove) {
        super(decomp);
        this.doImprove = doImprove;
    }

    @Override
    public void solve(FMatrixRMaj B, FMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int numCols = B.numCols;
        float[] dataB = B.data;
        float[] dataX = X.data;
        float[] vv = this.decomp._getVV();
        for (int j = 0; j < numCols; ++j) {
            int index = j;
            int i = 0;
            while (i < this.numCols) {
                vv[i] = dataB[index];
                ++i;
                index += numCols;
            }
            this.decomp._solveVectorInternal(vv);
            index = j;
            i = 0;
            while (i < this.numCols) {
                dataX[index] = vv[i];
                ++i;
                index += numCols;
            }
        }
        if (this.doImprove) {
            this.improveSol(B, X);
        }
    }
}

