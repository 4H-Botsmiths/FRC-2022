/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.linsol.LinearSolverSparse;
import org.ejml.sparse.csc.CommonOps_DSCC;
import org.ejml.sparse.csc.decomposition.qr.QrHelperFunctions_DSCC;
import org.ejml.sparse.csc.decomposition.qr.QrLeftLookingDecomposition_DSCC;
import org.ejml.sparse.csc.misc.TriangularSolver_DSCC;

public class LinearSolverQrLeftLooking_DSCC
implements LinearSolverSparse<DMatrixSparseCSC, DMatrixRMaj> {
    private final QrLeftLookingDecomposition_DSCC qr;
    private int AnumRows;
    private int AnumCols;
    private final DGrowArray gb = new DGrowArray();
    private final DGrowArray gbp = new DGrowArray();
    private final DGrowArray gx = new DGrowArray();
    private final IGrowArray gw = new IGrowArray();
    private final DMatrixSparseCSC tmp = new DMatrixSparseCSC(1, 1, 1);

    public LinearSolverQrLeftLooking_DSCC(QrLeftLookingDecomposition_DSCC qr) {
        this.qr = qr;
    }

    @Override
    public boolean setA(DMatrixSparseCSC A) {
        if (A.numCols > A.numRows) {
            throw new IllegalArgumentException("Can't handle wide matrices");
        }
        this.AnumRows = A.numRows;
        this.AnumCols = A.numCols;
        return this.qr.decompose(A) && !this.qr.isSingular();
    }

    @Override
    public double quality() {
        return TriangularSolver_DSCC.qualityTriangular(this.qr.getR());
    }

    @Override
    public void solveSparse(DMatrixSparseCSC B, DMatrixSparseCSC X) {
        X.reshape(this.AnumCols, B.numCols, X.numRows);
        IGrowArray gw1 = this.qr.getGwork();
        this.tmp.setTo(B);
        B = this.tmp;
        DMatrixSparseCSC B_tmp = B.createLike();
        int[] pinv = this.qr.getStructure().getPinv();
        CommonOps_DSCC.permuteRowInv(pinv, B, B_tmp);
        DMatrixSparseCSC swap = B_tmp;
        B_tmp = B;
        B = swap;
        DMatrixSparseCSC V = this.qr.getV();
        for (int i = 0; i < this.AnumCols; ++i) {
            QrHelperFunctions_DSCC.rank1UpdateMultR(V, i, this.qr.getBeta(i), B, B_tmp, this.gw, this.gx);
            swap = B_tmp;
            B_tmp = B;
            B = swap;
        }
        DMatrixSparseCSC R = this.qr.getR();
        TriangularSolver_DSCC.solve(R, false, B, X, null, this.gx, this.gw, gw1);
    }

    @Override
    public void setStructureLocked(boolean locked) {
        this.qr.setStructureLocked(locked);
    }

    @Override
    public boolean isStructureLocked() {
        return this.qr.isStructureLocked();
    }

    @Override
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.AnumRows, this.AnumCols, B, X);
        double[] b = UtilEjml.adjust(this.gb, B.numRows);
        double[] bp = UtilEjml.adjust(this.gbp, B.numRows);
        double[] x = UtilEjml.adjust(this.gx, this.AnumCols);
        int[] pinv = this.qr.getStructure().getPinv();
        for (int colX = 0; colX < B.numCols; ++colX) {
            double[] out;
            int index = colX;
            int i = 0;
            while (i < B.numRows) {
                b[i] = B.data[index];
                ++i;
                index += X.numCols;
            }
            CommonOps_DSCC.permuteInv(pinv, b, bp, this.AnumRows);
            for (int j = 0; j < this.AnumCols; ++j) {
                QrHelperFunctions_DSCC.applyHouseholder(this.qr.getV(), j, this.qr.getBeta(j), bp);
            }
            TriangularSolver_DSCC.solveU(this.qr.getR(), bp);
            if (this.qr.isFillPermutated()) {
                CommonOps_DSCC.permute(this.qr.getFillPermutation(), bp, x, X.numRows);
                out = x;
            } else {
                out = bp;
            }
            index = colX;
            int i2 = 0;
            while (i2 < X.numRows) {
                X.data[index] = out[i2];
                ++i2;
                index += X.numCols;
            }
        }
    }

    @Override
    public boolean modifiesA() {
        return this.qr.inputModified();
    }

    @Override
    public boolean modifiesB() {
        return false;
    }

    @Override
    public <D extends DecompositionInterface> D getDecomposition() {
        return (D)this.qr;
    }
}

