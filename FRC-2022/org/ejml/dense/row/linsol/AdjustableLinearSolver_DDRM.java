/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol;

import org.ejml.data.DMatrixRMaj;
import org.ejml.interfaces.linsol.LinearSolverDense;

public interface AdjustableLinearSolver_DDRM
extends LinearSolverDense<DMatrixRMaj> {
    public boolean addRowToA(double[] var1, int var2);

    public boolean removeRowFromA(int var1);
}

