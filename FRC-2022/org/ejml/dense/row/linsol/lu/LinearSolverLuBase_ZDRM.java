/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.lu;

import java.util.Arrays;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.decompose.lu.LUDecompositionBase_ZDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_ZDRM;

public abstract class LinearSolverLuBase_ZDRM
extends LinearSolverAbstract_ZDRM {
    protected LUDecompositionBase_ZDRM decomp;

    protected LinearSolverLuBase_ZDRM(LUDecompositionBase_ZDRM decomp) {
        this.decomp = decomp;
    }

    @Override
    public boolean setA(ZMatrixRMaj A) {
        this._setA(A);
        return this.decomp.decompose(A);
    }

    @Override
    public double quality() {
        return this.decomp.quality();
    }

    @Override
    public void invert(ZMatrixRMaj A_inv) {
        double[] vv = this.decomp._getVV();
        ZMatrixRMaj LU = this.decomp.getLU();
        A_inv.reshape(LU.numRows, LU.numCols);
        int n = this.A.numCols;
        double[] dataInv = A_inv.data;
        int strideAinv = A_inv.getRowStride();
        for (int j = 0; j < n; ++j) {
            Arrays.fill(vv, 0, n * 2, 0.0);
            vv[j * 2] = 1.0;
            vv[j * 2 + 1] = 0.0;
            this.decomp._solveVectorInternal(vv);
            int index = j * 2;
            int i = 0;
            while (i < n) {
                dataInv[index] = vv[i * 2];
                dataInv[index + 1] = vv[i * 2 + 1];
                ++i;
                index += strideAinv;
            }
        }
    }

    @Override
    public boolean modifiesA() {
        return false;
    }

    @Override
    public boolean modifiesB() {
        return false;
    }

    @Override
    public LUDecompositionBase_ZDRM getDecomposition() {
        return this.decomp;
    }
}

