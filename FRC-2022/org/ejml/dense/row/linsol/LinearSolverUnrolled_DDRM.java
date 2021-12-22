/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.linsol;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.misc.UnrolledInverseFromMinor_DDRM;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.jetbrains.annotations.Nullable;

public class LinearSolverUnrolled_DDRM
implements LinearSolverDense<DMatrixRMaj> {
    @Nullable
    DMatrixRMaj A;

    @Override
    public boolean setA(DMatrixRMaj A) {
        if (A.numRows != A.numCols) {
            return false;
        }
        this.A = A;
        return A.numRows <= 5;
    }

    @Override
    public double quality() {
        throw new IllegalArgumentException("Not supported by this solver.");
    }

    @Override
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void invert(DMatrixRMaj A_inv) {
        if (this.A == null) {
            throw new RuntimeException("Must call setA() first");
        }
        if (this.A.numRows == 1) {
            A_inv.set(0, 1.0 / this.A.get(0));
        }
        UnrolledInverseFromMinor_DDRM.inv(this.A, A_inv);
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
    public <D extends DecompositionInterface> D getDecomposition() {
        throw new RuntimeException("Not supported");
    }
}

