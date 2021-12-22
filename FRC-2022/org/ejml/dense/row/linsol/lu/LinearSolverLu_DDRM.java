/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.lu;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.lu.LUDecompositionBase_DDRM;
import org.ejml.dense.row.linsol.lu.LinearSolverLuBase_DDRM;

public class LinearSolverLu_DDRM
extends LinearSolverLuBase_DDRM {
    boolean doImprove = false;

    public LinearSolverLu_DDRM(LUDecompositionBase_DDRM decomp) {
        super(decomp);
    }

    public LinearSolverLu_DDRM(LUDecompositionBase_DDRM decomp, boolean doImprove) {
        super(decomp);
        this.doImprove = doImprove;
    }

    @Override
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int numCols = B.numCols;
        double[] dataB = B.data;
        double[] dataX = X.data;
        double[] vv = this.decomp._getVV();
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

