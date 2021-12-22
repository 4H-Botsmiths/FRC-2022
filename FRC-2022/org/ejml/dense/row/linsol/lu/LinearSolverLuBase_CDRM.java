/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.lu;

import java.util.Arrays;
import org.ejml.data.CMatrixRMaj;
import org.ejml.dense.row.decompose.lu.LUDecompositionBase_CDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_CDRM;

public abstract class LinearSolverLuBase_CDRM
extends LinearSolverAbstract_CDRM {
    protected LUDecompositionBase_CDRM decomp;

    protected LinearSolverLuBase_CDRM(LUDecompositionBase_CDRM decomp) {
        this.decomp = decomp;
    }

    @Override
    public boolean setA(CMatrixRMaj A) {
        this._setA(A);
        return this.decomp.decompose(A);
    }

    @Override
    public double quality() {
        return this.decomp.quality();
    }

    @Override
    public void invert(CMatrixRMaj A_inv) {
        float[] vv = this.decomp._getVV();
        CMatrixRMaj LU = this.decomp.getLU();
        A_inv.reshape(LU.numRows, LU.numCols);
        int n = this.A.numCols;
        float[] dataInv = A_inv.data;
        int strideAinv = A_inv.getRowStride();
        for (int j = 0; j < n; ++j) {
            Arrays.fill(vv, 0, n * 2, 0.0f);
            vv[j * 2] = 1.0f;
            vv[j * 2 + 1] = 0.0f;
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
    public LUDecompositionBase_CDRM getDecomposition() {
        return this.decomp;
    }
}

