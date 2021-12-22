/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol;

import org.ejml.data.DMatrix1Row;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class InvertUsingSolve_DDRM {
    public static void invert(LinearSolverDense<DMatrixRMaj> solver, DMatrix1Row A, DMatrixRMaj A_inv, DMatrixRMaj storage) {
        if (A.numRows != A_inv.numRows || A.numCols != A_inv.numCols) {
            throw new IllegalArgumentException("A and A_inv must have the same dimensions");
        }
        CommonOps_DDRM.setIdentity(storage);
        solver.solve(storage, A_inv);
    }

    public static void invert(LinearSolverDense<DMatrixRMaj> solver, DMatrix1Row A, DMatrixRMaj A_inv) {
        if (A.numRows != A_inv.numRows || A.numCols != A_inv.numCols) {
            throw new IllegalArgumentException("A and A_inv must have the same dimensions");
        }
        CommonOps_DDRM.setIdentity(A_inv);
        solver.solve(A_inv, A_inv);
    }
}

