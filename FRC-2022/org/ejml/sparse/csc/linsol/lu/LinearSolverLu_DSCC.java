/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.linsol.lu;

import org.ejml.UtilEjml;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.linsol.LinearSolverSparse;
import org.ejml.sparse.csc.CommonOps_DSCC;
import org.ejml.sparse.csc.decomposition.lu.LuUpLooking_DSCC;
import org.ejml.sparse.csc.misc.TriangularSolver_DSCC;

public class LinearSolverLu_DSCC
implements LinearSolverSparse<DMatrixSparseCSC, DMatrixRMaj> {
    LuUpLooking_DSCC decomposition;
    private final DGrowArray gx = new DGrowArray();
    private final DGrowArray gb = new DGrowArray();
    DMatrixSparseCSC Bp = new DMatrixSparseCSC(1, 1, 1);
    DMatrixSparseCSC tmp = new DMatrixSparseCSC(1, 1, 1);
    int AnumRows;
    int AnumCols;

    public LinearSolverLu_DSCC(LuUpLooking_DSCC decomposition) {
        this.decomposition = decomposition;
    }

    @Override
    public boolean setA(DMatrixSparseCSC A) {
        this.AnumRows = A.numRows;
        this.AnumCols = A.numCols;
        return this.decomposition.decompose(A);
    }

    @Override
    public double quality() {
        return TriangularSolver_DSCC.qualityTriangular(this.decomposition.getU());
    }

    @Override
    public void solveSparse(DMatrixSparseCSC B, DMatrixSparseCSC X) {
        X.reshape(this.AnumCols, B.numCols, X.numRows);
        DMatrixSparseCSC L = this.decomposition.getL();
        DMatrixSparseCSC U = this.decomposition.getU();
        this.Bp.reshape(B.numRows, B.numCols, B.nz_length);
        int[] Pinv = this.decomposition.getPinv();
        CommonOps_DSCC.permute(Pinv, B, null, this.Bp);
        IGrowArray gw = this.decomposition.getGw();
        IGrowArray gw1 = this.decomposition.getGxi();
        this.tmp.reshape(L.numRows, B.numCols, 1);
        TriangularSolver_DSCC.solve(L, true, this.Bp, this.tmp, null, this.gx, gw, gw1);
        TriangularSolver_DSCC.solve(U, false, this.tmp, X, null, this.gx, gw, gw1);
    }

    @Override
    public void setStructureLocked(boolean locked) {
        this.decomposition.setStructureLocked(locked);
    }

    @Override
    public boolean isStructureLocked() {
        return this.decomposition.isStructureLocked();
    }

    @Override
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.AnumRows, this.AnumCols, B, X);
        int[] pinv = this.decomposition.getPinv();
        double[] x = UtilEjml.adjust(this.gx, X.numRows);
        double[] b = UtilEjml.adjust(this.gb, B.numRows);
        DMatrixSparseCSC L = this.decomposition.getL();
        DMatrixSparseCSC U = this.decomposition.getU();
        boolean reduceFill = this.decomposition.isReduceFill();
        int[] q = reduceFill ? this.decomposition.getReducePermutation() : null;
        for (int colX = 0; colX < X.numCols; ++colX) {
            double[] d;
            int index = colX;
            int i = 0;
            while (i < B.numRows) {
                b[i] = B.data[index];
                ++i;
                index += X.numCols;
            }
            CommonOps_DSCC.permuteInv(pinv, b, x, X.numRows);
            TriangularSolver_DSCC.solveL(L, x);
            TriangularSolver_DSCC.solveU(U, x);
            if (reduceFill) {
                CommonOps_DSCC.permute(q, x, b, X.numRows);
                d = b;
            } else {
                d = x;
            }
            index = colX;
            int i2 = 0;
            while (i2 < X.numRows) {
                X.data[index] = d[i2];
                ++i2;
                index += X.numCols;
            }
        }
    }

    @Override
    public boolean modifiesA() {
        return this.decomposition.inputModified();
    }

    @Override
    public boolean modifiesB() {
        return false;
    }

    @Override
    public <D extends DecompositionInterface> D getDecomposition() {
        return (D)this.decomposition;
    }
}

