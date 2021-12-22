/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.chol;

import org.ejml.data.DMatrixRBlock;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.block.MatrixOps_DDRB;
import org.ejml.dense.block.linsol.chol.CholeskyOuterSolver_DDRB;
import org.ejml.dense.row.linsol.LinearSolver_DDRB_to_DDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class LinearSolverChol_DDRB
extends LinearSolver_DDRB_to_DDRM {
    public LinearSolverChol_DDRB() {
        super(new CholeskyOuterSolver_DDRB());
    }

    public LinearSolverChol_DDRB(LinearSolverDense<DMatrixRBlock> alg) {
        super(alg);
    }

    @Override
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        this.blockB.reshape(B.numRows, B.numCols, false);
        MatrixOps_DDRB.convert(B, this.blockB);
        this.alg.solve(this.blockB, null);
        MatrixOps_DDRB.convert(this.blockB, X);
    }
}

