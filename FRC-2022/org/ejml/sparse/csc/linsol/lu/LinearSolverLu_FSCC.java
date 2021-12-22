/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.linsol.lu;

import org.ejml.UtilEjml;
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.linsol.LinearSolverSparse;
import org.ejml.sparse.csc.CommonOps_FSCC;
import org.ejml.sparse.csc.decomposition.lu.LuUpLooking_FSCC;
import org.ejml.sparse.csc.misc.TriangularSolver_FSCC;

public class LinearSolverLu_FSCC
implements LinearSolverSparse<FMatrixSparseCSC, FMatrixRMaj> {
    LuUpLooking_FSCC decomposition;
    private final FGrowArray gx = new FGrowArray();
    private final FGrowArray gb = new FGrowArray();
    FMatrixSparseCSC Bp = new FMatrixSparseCSC(1, 1, 1);
    FMatrixSparseCSC tmp = new FMatrixSparseCSC(1, 1, 1);
    int AnumRows;
    int AnumCols;

    public LinearSolverLu_FSCC(LuUpLooking_FSCC decomposition) {
        this.decomposition = decomposition;
    }

    @Override
    public boolean setA(FMatrixSparseCSC A) {
        this.AnumRows = A.numRows;
        this.AnumCols = A.numCols;
        return this.decomposition.decompose(A);
    }

    @Override
    public double quality() {
        return TriangularSolver_FSCC.qualityTriangular(this.decomposition.getU());
    }

    @Override
    public void solveSparse(FMatrixSparseCSC B, FMatrixSparseCSC X) {
        X.reshape(this.AnumCols, B.numCols, X.numRows);
        FMatrixSparseCSC L = this.decomposition.getL();
        FMatrixSparseCSC U = this.decomposition.getU();
        this.Bp.reshape(B.numRows, B.numCols, B.nz_length);
        int[] Pinv = this.decomposition.getPinv();
        CommonOps_FSCC.permute(Pinv, B, null, this.Bp);
        IGrowArray gw = this.decomposition.getGw();
        IGrowArray gw1 = this.decomposition.getGxi();
        this.tmp.reshape(L.numRows, B.numCols, 1);
        TriangularSolver_FSCC.solve(L, true, this.Bp, this.tmp, null, this.gx, gw, gw1);
        TriangularSolver_FSCC.solve(U, false, this.tmp, X, null, this.gx, gw, gw1);
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
    public void solve(FMatrixRMaj B, FMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.AnumRows, this.AnumCols, B, X);
        int[] pinv = this.decomposition.getPinv();
        float[] x = UtilEjml.adjust(this.gx, X.numRows);
        float[] b = UtilEjml.adjust(this.gb, B.numRows);
        FMatrixSparseCSC L = this.decomposition.getL();
        FMatrixSparseCSC U = this.decomposition.getU();
        boolean reduceFill = this.decomposition.isReduceFill();
        int[] q = reduceFill ? this.decomposition.getReducePermutation() : null;
        for (int colX = 0; colX < X.numCols; ++colX) {
            float[] d;
            int index = colX;
            int i = 0;
            while (i < B.numRows) {
                b[i] = B.data[index];
                ++i;
                index += X.numCols;
            }
            CommonOps_FSCC.permuteInv(pinv, b, x, X.numRows);
            TriangularSolver_FSCC.solveL(L, x);
            TriangularSolver_FSCC.solveU(U, x);
            if (reduceFill) {
                CommonOps_FSCC.permute(q, x, b, X.numRows);
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

