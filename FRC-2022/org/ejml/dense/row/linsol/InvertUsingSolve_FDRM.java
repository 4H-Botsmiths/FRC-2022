/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol;

import org.ejml.data.FMatrix1Row;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class InvertUsingSolve_FDRM {
    public static void invert(LinearSolverDense<FMatrixRMaj> solver, FMatrix1Row A, FMatrixRMaj A_inv, FMatrixRMaj storage) {
        if (A.numRows != A_inv.numRows || A.numCols != A_inv.numCols) {
            throw new IllegalArgumentException("A and A_inv must have the same dimensions");
        }
        CommonOps_FDRM.setIdentity(storage);
        solver.solve(storage, A_inv);
    }

    public static void invert(LinearSolverDense<FMatrixRMaj> solver, FMatrix1Row A, FMatrixRMaj A_inv) {
        if (A.numRows != A_inv.numRows || A.numCols != A_inv.numCols) {
            throw new IllegalArgumentException("A and A_inv must have the same dimensions");
        }
        CommonOps_FDRM.setIdentity(A_inv);
        solver.solve(A_inv, A_inv);
    }
}

