/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.linsol.chol;

import org.ejml.UtilEjml;
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.linsol.LinearSolverSparse;
import org.ejml.sparse.ComputePermutation;
import org.ejml.sparse.csc.CommonOps_FSCC;
import org.ejml.sparse.csc.decomposition.chol.CholeskyUpLooking_FSCC;
import org.ejml.sparse.csc.misc.ApplyFillReductionPermutation_FSCC;
import org.ejml.sparse.csc.misc.TriangularSolver_FSCC;
import org.jetbrains.annotations.Nullable;

public class LinearSolverCholesky_FSCC
implements LinearSolverSparse<FMatrixSparseCSC, FMatrixRMaj> {
    CholeskyUpLooking_FSCC cholesky;
    ApplyFillReductionPermutation_FSCC reduce;
    FGrowArray gb = new FGrowArray();
    FGrowArray gx = new FGrowArray();
    IGrowArray gw = new IGrowArray();
    FMatrixSparseCSC tmp = new FMatrixSparseCSC(1, 1, 1);
    int AnumRows;
    int AnumCols;

    public LinearSolverCholesky_FSCC(CholeskyUpLooking_FSCC cholesky, @Nullable ComputePermutation<FMatrixSparseCSC> fillReduce) {
        this.cholesky = cholesky;
        this.reduce = new ApplyFillReductionPermutation_FSCC(fillReduce, true);
    }

    @Override
    public boolean setA(FMatrixSparseCSC A) {
        this.AnumRows = A.numRows;
        this.AnumCols = A.numCols;
        FMatrixSparseCSC C = this.reduce.apply(A);
        return this.cholesky.decompose(C);
    }

    @Override
    public double quality() {
        return TriangularSolver_FSCC.qualityTriangular(this.cholesky.getL());
    }

    @Override
    public void solveSparse(FMatrixSparseCSC B, FMatrixSparseCSC X) {
        X.reshape(this.AnumCols, B.numCols, X.numRows);
        IGrowArray gw1 = this.cholesky.getGw();
        FMatrixSparseCSC L = this.cholesky.getL();
        this.tmp.reshape(L.numRows, B.numCols, 1);
        int[] Pinv = this.reduce.getArrayPinv();
        TriangularSolver_FSCC.solve(L, true, B, this.tmp, Pinv, this.gx, this.gw, gw1);
        TriangularSolver_FSCC.solveTran(L, true, this.tmp, X, null, this.gx, this.gw, gw1);
    }

    @Override
    public void setStructureLocked(boolean locked) {
        this.cholesky.setStructureLocked(locked);
    }

    @Override
    public boolean isStructureLocked() {
        return this.cholesky.isStructureLocked();
    }

    @Override
    public void solve(FMatrixRMaj B, FMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.AnumRows, this.AnumCols, B, X);
        FMatrixSparseCSC L = this.cholesky.getL();
        int N = L.numRows;
        float[] b = UtilEjml.adjust(this.gb, N);
        float[] x = UtilEjml.adjust(this.gx, N);
        int[] Pinv = this.reduce.getArrayPinv();
        for (int col = 0; col < B.numCols; ++col) {
            int index = col;
            int i = 0;
            while (i < N) {
                b[i] = B.data[index];
                ++i;
                index += B.numCols;
            }
            if (Pinv != null) {
                CommonOps_FSCC.permuteInv(Pinv, b, x, N);
                TriangularSolver_FSCC.solveL(L, x);
                TriangularSolver_FSCC.solveTranL(L, x);
                CommonOps_FSCC.permute(Pinv, x, b, N);
            } else {
                TriangularSolver_FSCC.solveL(L, b);
                TriangularSolver_FSCC.solveTranL(L, b);
            }
            index = col;
            i = 0;
            while (i < N) {
                X.data[index] = b[i];
                ++i;
                index += X.numCols;
            }
        }
    }

    @Override
    public boolean modifiesA() {
        return this.cholesky.inputModified();
    }

    @Override
    public boolean modifiesB() {
        return false;
    }

    @Override
    public <D extends DecompositionInterface> D getDecomposition() {
        return (D)this.cholesky;
    }
}

