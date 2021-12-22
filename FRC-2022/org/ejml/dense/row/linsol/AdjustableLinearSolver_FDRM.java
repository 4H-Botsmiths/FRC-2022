/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol;

import org.ejml.data.FMatrixRMaj;
import org.ejml.interfaces.linsol.LinearSolverDense;

public interface AdjustableLinearSolver_FDRM
extends LinearSolverDense<FMatrixRMaj> {
    public boolean addRowToA(float[] var1, int var2);

    public boolean removeRowFromA(int var1);
}

