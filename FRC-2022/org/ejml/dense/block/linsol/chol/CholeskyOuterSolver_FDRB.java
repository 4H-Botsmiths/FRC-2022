/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.block.linsol.chol;

import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixRBlock;
import org.ejml.data.FSubmatrixD1;
import org.ejml.dense.block.MatrixOps_FDRB;
import org.ejml.dense.block.TriangularSolver_FDRB;
import org.ejml.dense.block.decomposition.chol.CholeskyOuterForm_FDRB;
import org.ejml.dense.row.SpecializedOps_FDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F32;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.jetbrains.annotations.Nullable;
import pabeles.concurrency.GrowArray;

public class CholeskyOuterSolver_FDRB
implements LinearSolverDense<FMatrixRBlock> {
    private final CholeskyOuterForm_FDRB decomposer = new CholeskyOuterForm_FDRB(true);
    private int blockLength;
    private final GrowArray<FGrowArray> workspace = new GrowArray<FGrowArray>(FGrowArray::new);

    @Override
    public boolean setA(FMatrixRBlock A) {
        if (!this.decomposer.decompose(A)) {
            return false;
        }
        this.blockLength = A.blockLength;
        return true;
    }

    @Override
    public double quality() {
        return SpecializedOps_FDRM.qualityTriangular(this.decomposer.getT((FMatrixRBlock)null));
    }

    @Override
    public void solve(FMatrixRBlock B, @Nullable FMatrixRBlock X) {
        if (B.blockLength != this.blockLength) {
            throw new IllegalArgumentException("Unexpected blocklength in B.");
        }
        FSubmatrixD1 L = new FSubmatrixD1(this.decomposer.getT((FMatrixRBlock)null));
        if (X == null) {
            X = (FMatrixRBlock)B.create(L.col1, B.numCols);
        } else {
            X.reshape(L.col1, B.numCols, this.blockLength, false);
        }
        TriangularSolver_FDRB.solve(this.blockLength, false, L, new FSubmatrixD1(B), false);
        TriangularSolver_FDRB.solve(this.blockLength, false, L, new FSubmatrixD1(B), true);
        if (X != null) {
            MatrixOps_FDRB.extractAligned(B, X);
        }
    }

    @Override
    public void invert(FMatrixRBlock A_inv) {
        FMatrixRBlock T = this.decomposer.getT((FMatrixRBlock)null);
        if (A_inv.numRows != T.numRows || A_inv.numCols != T.numCols) {
            throw new IllegalArgumentException("Unexpected number or rows and/or columns");
        }
        MatrixOps_FDRB.zeroTriangle(true, A_inv);
        FSubmatrixD1 L = new FSubmatrixD1(T);
        FSubmatrixD1 B = new FSubmatrixD1(A_inv);
        TriangularSolver_FDRB.invert(this.blockLength, false, L, B, this.workspace);
        TriangularSolver_FDRB.solveL(this.blockLength, L, B, true);
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
    public CholeskyDecomposition_F32<FMatrixRBlock> getDecomposition() {
        return this.decomposer;
    }
}

