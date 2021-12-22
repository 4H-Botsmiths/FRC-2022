/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.linsol;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.linsol.InvertUsingSolve_DDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.jetbrains.annotations.Nullable;

public abstract class LinearSolverAbstract_DDRM
implements LinearSolverDense<DMatrixRMaj> {
    @Nullable
    protected DMatrixRMaj A;
    protected int numRows;
    protected int numCols;

    @Nullable
    public DMatrixRMaj getA() {
        return this.A;
    }

    protected void _setA(DMatrixRMaj A) {
        this.A = A;
        this.numRows = A.numRows;
        this.numCols = A.numCols;
    }

    @Override
    public void invert(DMatrixRMaj A_inv) {
        if (this.A == null) {
            throw new RuntimeException("Must call setA() first");
        }
        InvertUsingSolve_DDRM.invert(this, this.A, A_inv);
    }
}

