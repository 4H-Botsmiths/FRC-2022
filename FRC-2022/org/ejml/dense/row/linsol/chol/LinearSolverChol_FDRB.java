/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.chol;

import org.ejml.data.FMatrixRBlock;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.block.MatrixOps_FDRB;
import org.ejml.dense.block.linsol.chol.CholeskyOuterSolver_FDRB;
import org.ejml.dense.row.linsol.LinearSolver_FDRB_to_FDRM;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class LinearSolverChol_FDRB
extends LinearSolver_FDRB_to_FDRM {
    public LinearSolverChol_FDRB() {
        super(new CholeskyOuterSolver_FDRB());
    }

    public LinearSolverChol_FDRB(LinearSolverDense<FMatrixRBlock> alg) {
        super(alg);
    }

    @Override
    public void solve(FMatrixRMaj B, FMatrixRMaj X) {
        this.blockB.reshape(B.numRows, B.numCols, false);
        MatrixOps_FDRB.convert(B, this.blockB);
        this.alg.solve(this.blockB, null);
        MatrixOps_FDRB.convert(this.blockB, X);
    }
}

