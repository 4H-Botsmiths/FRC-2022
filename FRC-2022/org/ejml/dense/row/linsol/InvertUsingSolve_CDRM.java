/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol;

import org.ejml.data.CMatrixRMaj;
import org.ejml.dense.row.CommonOps_CDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class InvertUsingSolve_CDRM {
    public static void invert(LinearSolverDense<CMatrixRMaj> solver, CMatrixRMaj A, CMatrixRMaj A_inv, CMatrixRMaj storage) {
        A_inv.reshape(A.numRows, A.numCols);
        CommonOps_CDRM.setIdentity(storage);
        solver.solve(storage, A_inv);
    }

    public static void invert(LinearSolverDense<CMatrixRMaj> solver, CMatrixRMaj A, CMatrixRMaj A_inv) {
        A_inv.reshape(A.numRows, A.numCols);
        CommonOps_CDRM.setIdentity(A_inv);
        solver.solve(A_inv, A_inv);
    }
}

