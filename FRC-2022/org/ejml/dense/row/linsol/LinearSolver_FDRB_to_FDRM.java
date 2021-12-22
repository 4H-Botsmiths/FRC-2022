/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol;

import org.ejml.data.FMatrixRBlock;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.block.MatrixOps_FDRB;
import org.ejml.dense.block.linsol.chol.CholeskyOuterSolver_FDRB;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class LinearSolver_FDRB_to_FDRM
implements LinearSolverDense<FMatrixRMaj> {
    protected LinearSolverDense<FMatrixRBlock> alg = new CholeskyOuterSolver_FDRB();
    protected FMatrixRBlock blockA = new FMatrixRBlock(1, 1);
    protected FMatrixRBlock blockB = new FMatrixRBlock(1, 1);
    protected FMatrixRBlock blockX = new FMatrixRBlock(1, 1);

    public LinearSolver_FDRB_to_FDRM(LinearSolverDense<FMatrixRBlock> alg) {
        this.alg = alg;
    }

    @Override
    public boolean setA(FMatrixRMaj A) {
        this.blockA.reshape(A.numRows, A.numCols, false);
        MatrixOps_FDRB.convert(A, this.blockA);
        return this.alg.setA(this.blockA);
    }

    @Override
    public double quality() {
        return this.alg.quality();
    }

    @Override
    public void solve(FMatrixRMaj B, FMatrixRMaj X) {
        X.reshape(this.blockA.numCols, B.numCols);
        this.blockB.reshape(B.numRows, B.numCols, false);
        this.blockX.reshape(X.numRows, X.numCols, false);
        MatrixOps_FDRB.convert(B, this.blockB);
        this.alg.solve(this.blockB, this.blockX);
        MatrixOps_FDRB.convert(this.blockX, X);
    }

    @Override
    public void invert(FMatrixRMaj A_inv) {
        this.blockB.reshape(A_inv.numRows, A_inv.numCols, false);
        this.alg.invert(this.blockB);
        MatrixOps_FDRB.convert(this.blockB, A_inv);
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
        return (D)this.alg.getDecomposition();
    }
}

