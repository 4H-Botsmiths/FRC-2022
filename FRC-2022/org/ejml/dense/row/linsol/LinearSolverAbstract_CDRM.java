/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol;

import org.ejml.data.CMatrixRMaj;
import org.ejml.dense.row.linsol.InvertUsingSolve_CDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public abstract class LinearSolverAbstract_CDRM
implements LinearSolverDense<CMatrixRMaj> {
    protected CMatrixRMaj A;
    protected int numRows;
    protected int numCols;
    protected int stride;

    public CMatrixRMaj getA() {
        return this.A;
    }

    protected void _setA(CMatrixRMaj A) {
        this.A = A;
        this.numRows = A.numRows;
        this.numCols = A.numCols;
        this.stride = this.numCols * 2;
    }

    @Override
    public void invert(CMatrixRMaj A_inv) {
        InvertUsingSolve_CDRM.invert(this, this.A, A_inv);
    }
}

