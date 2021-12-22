/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.linsol.chol;

import org.ejml.UtilEjml;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.linsol.LinearSolverSparse;
import org.ejml.sparse.ComputePermutation;
import org.ejml.sparse.csc.CommonOps_DSCC;
import org.ejml.sparse.csc.decomposition.chol.CholeskyUpLooking_DSCC;
import org.ejml.sparse.csc.misc.ApplyFillReductionPermutation_DSCC;
import org.ejml.sparse.csc.misc.TriangularSolver_DSCC;
import org.jetbrains.annotations.Nullable;

public class LinearSolverCholesky_DSCC
implements LinearSolverSparse<DMatrixSparseCSC, DMatrixRMaj> {
    CholeskyUpLooking_DSCC cholesky;
    ApplyFillReductionPermutation_DSCC reduce;
    DGrowArray gb = new DGrowArray();
    DGrowArray gx = new DGrowArray();
    IGrowArray gw = new IGrowArray();
    DMatrixSparseCSC tmp = new DMatrixSparseCSC(1, 1, 1);
    int AnumRows;
    int AnumCols;

    public LinearSolverCholesky_DSCC(CholeskyUpLooking_DSCC cholesky, @Nullable ComputePermutation<DMatrixSparseCSC> fillReduce) {
        this.cholesky = cholesky;
        this.reduce = new ApplyFillReductionPermutation_DSCC(fillReduce, true);
    }

    @Override
    public boolean setA(DMatrixSparseCSC A) {
        this.AnumRows = A.numRows;
        this.AnumCols = A.numCols;
        DMatrixSparseCSC C = this.reduce.apply(A);
        return this.cholesky.decompose(C);
    }

    @Override
    public double quality() {
        return TriangularSolver_DSCC.qualityTriangular(this.cholesky.getL());
    }

    @Override
    public void solveSparse(DMatrixSparseCSC B, DMatrixSparseCSC X) {
        X.reshape(this.AnumCols, B.numCols, X.numRows);
        IGrowArray gw1 = this.cholesky.getGw();
        DMatrixSparseCSC L = this.cholesky.getL();
        this.tmp.reshape(L.numRows, B.numCols, 1);
        int[] Pinv = this.reduce.getArrayPinv();
        TriangularSolver_DSCC.solve(L, true, B, this.tmp, Pinv, this.gx, this.gw, gw1);
        TriangularSolver_DSCC.solveTran(L, true, this.tmp, X, null, this.gx, this.gw, gw1);
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
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.AnumRows, this.AnumCols, B, X);
        DMatrixSparseCSC L = this.cholesky.getL();
        int N = L.numRows;
        double[] b = UtilEjml.adjust(this.gb, N);
        double[] x = UtilEjml.adjust(this.gx, N);
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
                CommonOps_DSCC.permuteInv(Pinv, b, x, N);
                TriangularSolver_DSCC.solveL(L, x);
                TriangularSolver_DSCC.solveTranL(L, x);
                CommonOps_DSCC.permute(Pinv, x, b, N);
            } else {
                TriangularSolver_DSCC.solveL(L, b);
                TriangularSolver_DSCC.solveTranL(L, b);
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

