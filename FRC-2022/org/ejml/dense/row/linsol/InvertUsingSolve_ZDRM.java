/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol;

import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.CommonOps_ZDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class InvertUsingSolve_ZDRM {
    public static void invert(LinearSolverDense<ZMatrixRMaj> solver, ZMatrixRMaj A, ZMatrixRMaj A_inv, ZMatrixRMaj storage) {
        A_inv.reshape(A.numRows, A.numCols);
        CommonOps_ZDRM.setIdentity(storage);
        solver.solve(storage, A_inv);
    }

    public static void invert(LinearSolverDense<ZMatrixRMaj> solver, ZMatrixRMaj A, ZMatrixRMaj A_inv) {
        A_inv.reshape(A.numRows, A.numCols);
        CommonOps_ZDRM.setIdentity(A_inv);
        solver.solve(A_inv, A_inv);
    }
}

