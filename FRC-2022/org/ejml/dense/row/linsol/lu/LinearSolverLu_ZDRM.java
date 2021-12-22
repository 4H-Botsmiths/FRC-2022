/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.lu;

import org.ejml.UtilEjml;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.decompose.lu.LUDecompositionBase_ZDRM;
import org.ejml.dense.row.linsol.lu.LinearSolverLuBase_ZDRM;

public class LinearSolverLu_ZDRM
extends LinearSolverLuBase_ZDRM {
    public LinearSolverLu_ZDRM(LUDecompositionBase_ZDRM decomp) {
        super(decomp);
    }

    @Override
    public void solve(ZMatrixRMaj B, ZMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int bnumCols = B.numCols;
        int bstride = B.getRowStride();
        double[] dataB = B.data;
        double[] dataX = X.data;
        double[] vv = this.decomp._getVV();
        for (int j = 0; j < bnumCols; ++j) {
            int index = j * 2;
            int i = 0;
            while (i < this.numRows) {
                vv[i * 2] = dataB[index];
                vv[i * 2 + 1] = dataB[index + 1];
                ++i;
                index += bstride;
            }
            this.decomp._solveVectorInternal(vv);
            index = j * 2;
            i = 0;
            while (i < this.numRows) {
                dataX[index] = vv[i * 2];
                dataX[index + 1] = vv[i * 2 + 1];
                ++i;
                index += bstride;
            }
        }
    }
}

