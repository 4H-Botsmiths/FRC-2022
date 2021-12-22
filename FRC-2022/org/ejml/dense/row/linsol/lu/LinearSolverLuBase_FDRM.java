/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.lu;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.lu.LUDecompositionBase_FDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_FDRM;

public abstract class LinearSolverLuBase_FDRM
extends LinearSolverAbstract_FDRM {
    protected LUDecompositionBase_FDRM decomp;

    protected LinearSolverLuBase_FDRM(LUDecompositionBase_FDRM decomp) {
        this.decomp = decomp;
    }

    @Override
    public boolean setA(FMatrixRMaj A) {
        this._setA(A);
        return this.decomp.decompose(A);
    }

    @Override
    public double quality() {
        return this.decomp.quality();
    }

    @Override
    public void invert(FMatrixRMaj A_inv) {
        if (this.A == null) {
            throw new RuntimeException("Must call setA() first");
        }
        float[] vv = this.decomp._getVV();
        FMatrixRMaj LU = this.decomp.getLU();
        if (A_inv.numCols != LU.numCols || A_inv.numRows != LU.numRows) {
            throw new IllegalArgumentException("Unexpected matrix dimension");
        }
        int n = this.A.numCols;
        float[] dataInv = A_inv.data;
        for (int j = 0; j < n; ++j) {
            for (int i = 0; i < n; ++i) {
                vv[i] = i == j ? 1.0f : 0.0f;
            }
            this.decomp._solveVectorInternal(vv);
            int index = j;
            int i = 0;
            while (i < n) {
                dataInv[index] = vv[i];
                ++i;
                index += n;
            }
        }
    }

    public void improveSol(FMatrixRMaj b, FMatrixRMaj x) {
        if (b.numCols != x.numCols) {
            throw new IllegalArgumentException("bad shapes");
        }
        if (this.A == null) {
            throw new IllegalArgumentException("Must setA() first");
        }
        float[] dataA = this.A.data;
        float[] dataB = b.data;
        float[] dataX = x.data;
        int nc = b.numCols;
        int n = b.numCols;
        float[] vv = this.decomp._getVV();
        for (int k = 0; k < nc; ++k) {
            int i;
            for (i = 0; i < n; ++i) {
                float sdp = -dataB[i * nc + k];
                for (int j = 0; j < n; ++j) {
                    sdp += dataA[i * n + j] * dataX[j * nc + k];
                }
                vv[i] = sdp;
            }
            this.decomp._solveVectorInternal(vv);
            for (i = 0; i < n; ++i) {
                int n2 = i * nc + k;
                dataX[n2] = dataX[n2] - vv[i];
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
    public LUDecompositionBase_FDRM getDecomposition() {
        return this.decomp;
    }
}

