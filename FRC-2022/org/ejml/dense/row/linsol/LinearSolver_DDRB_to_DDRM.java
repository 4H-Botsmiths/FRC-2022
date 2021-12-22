/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol;

import org.ejml.data.DMatrixRBlock;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.block.MatrixOps_DDRB;
import org.ejml.dense.block.linsol.chol.CholeskyOuterSolver_DDRB;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.linsol.LinearSolverDense;

public class LinearSolver_DDRB_to_DDRM
implements LinearSolverDense<DMatrixRMaj> {
    protected LinearSolverDense<DMatrixRBlock> alg = new CholeskyOuterSolver_DDRB();
    protected DMatrixRBlock blockA = new DMatrixRBlock(1, 1);
    protected DMatrixRBlock blockB = new DMatrixRBlock(1, 1);
    protected DMatrixRBlock blockX = new DMatrixRBlock(1, 1);

    public LinearSolver_DDRB_to_DDRM(LinearSolverDense<DMatrixRBlock> alg) {
        this.alg = alg;
    }

    @Override
    public boolean setA(DMatrixRMaj A) {
        this.blockA.reshape(A.numRows, A.numCols, false);
        MatrixOps_DDRB.convert(A, this.blockA);
        return this.alg.setA(this.blockA);
    }

    @Override
    public double quality() {
        return this.alg.quality();
    }

    @Override
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        X.reshape(this.blockA.numCols, B.numCols);
        this.blockB.reshape(B.numRows, B.numCols, false);
        this.blockX.reshape(X.numRows, X.numCols, false);
        MatrixOps_DDRB.convert(B, this.blockB);
        this.alg.solve(this.blockB, this.blockX);
        MatrixOps_DDRB.convert(this.blockX, X);
    }

    @Override
    public void invert(DMatrixRMaj A_inv) {
        this.blockB.reshape(A_inv.numRows, A_inv.numCols, false);
        this.alg.invert(this.blockB);
        MatrixOps_DDRB.convert(this.blockB, A_inv);
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

