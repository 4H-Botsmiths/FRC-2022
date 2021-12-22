/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.block.linsol.chol;

import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixRBlock;
import org.ejml.data.DSubmatrixD1;
import org.ejml.dense.block.MatrixOps_DDRB;
import org.ejml.dense.block.TriangularSolver_MT_DDRB;
import org.ejml.dense.block.decomposition.chol.CholeskyOuterForm_MT_DDRB;
import org.ejml.dense.row.SpecializedOps_DDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F64;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.jetbrains.annotations.Nullable;
import pabeles.concurrency.GrowArray;

public class CholeskyOuterSolver_MT_DDRB
implements LinearSolverDense<DMatrixRBlock> {
    private final CholeskyOuterForm_MT_DDRB decomposer = new CholeskyOuterForm_MT_DDRB(true);
    private int blockLength;
    private final GrowArray<DGrowArray> workspace = new GrowArray<DGrowArray>(DGrowArray::new);

    @Override
    public boolean setA(DMatrixRBlock A) {
        if (!this.decomposer.decompose(A)) {
            return false;
        }
        this.blockLength = A.blockLength;
        return true;
    }

    @Override
    public double quality() {
        return SpecializedOps_DDRM.qualityTriangular(this.decomposer.getT((DMatrixRBlock)null));
    }

    @Override
    public void solve(DMatrixRBlock B, @Nullable DMatrixRBlock X) {
        if (B.blockLength != this.blockLength) {
            throw new IllegalArgumentException("Unexpected blocklength in B.");
        }
        DSubmatrixD1 L = new DSubmatrixD1(this.decomposer.getT((DMatrixRBlock)null));
        if (X == null) {
            X = (DMatrixRBlock)B.create(L.col1, B.numCols);
        } else {
            X.reshape(L.col1, B.numCols, this.blockLength, false);
        }
        TriangularSolver_MT_DDRB.solve(this.blockLength, false, L, new DSubmatrixD1(B), false);
        TriangularSolver_MT_DDRB.solve(this.blockLength, false, L, new DSubmatrixD1(B), true);
        if (X != null) {
            MatrixOps_DDRB.extractAligned(B, X);
        }
    }

    @Override
    public void invert(DMatrixRBlock A_inv) {
        DMatrixRBlock T = this.decomposer.getT((DMatrixRBlock)null);
        if (A_inv.numRows != T.numRows || A_inv.numCols != T.numCols) {
            throw new IllegalArgumentException("Unexpected number or rows and/or columns");
        }
        MatrixOps_DDRB.zeroTriangle(true, A_inv);
        DSubmatrixD1 L = new DSubmatrixD1(T);
        DSubmatrixD1 B = new DSubmatrixD1(A_inv);
        TriangularSolver_MT_DDRB.invert(this.blockLength, false, L, B, this.workspace);
        TriangularSolver_MT_DDRB.solveL(this.blockLength, L, B, true);
    }

    @Override
    public boolean modifiesA() {
        return this.decomposer.inputModified();
    }

    @Override
    public boolean modifiesB() {
        return true;
    }

    @Override
    public CholeskyDecomposition_F64<DMatrixRBlock> getDecomposition() {
        return this.decomposer;
    }
}

