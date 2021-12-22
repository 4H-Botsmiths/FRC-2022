/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol;

import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.linsol.InvertUsingSolve_ZDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public abstract class LinearSolverAbstract_ZDRM
implements LinearSolverDense<ZMatrixRMaj> {
    protected ZMatrixRMaj A;
    protected int numRows;
    protected int numCols;
    protected int stride;

    public ZMatrixRMaj getA() {
        return this.A;
    }

    protected void _setA(ZMatrixRMaj A) {
        this.A = A;
        this.numRows = A.numRows;
        this.numCols = A.numCols;
        this.stride = this.numCols * 2;
    }

    @Override
    public void invert(ZMatrixRMaj A_inv) {
        InvertUsingSolve_ZDRM.invert(this, this.A, A_inv);
    }
}

