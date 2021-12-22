/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.linsol;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.linsol.InvertUsingSolve_FDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.jetbrains.annotations.Nullable;

public abstract class LinearSolverAbstract_FDRM
implements LinearSolverDense<FMatrixRMaj> {
    @Nullable
    protected FMatrixRMaj A;
    protected int numRows;
    protected int numCols;

    @Nullable
    public FMatrixRMaj getA() {
        return this.A;
    }

    protected void _setA(FMatrixRMaj A) {
        this.A = A;
        this.numRows = A.numRows;
        this.numCols = A.numCols;
    }

    @Override
    public void invert(FMatrixRMaj A_inv) {
        if (this.A == null) {
            throw new RuntimeException("Must call setA() first");
        }
        InvertUsingSolve_FDRM.invert(this, this.A, A_inv);
    }
}

