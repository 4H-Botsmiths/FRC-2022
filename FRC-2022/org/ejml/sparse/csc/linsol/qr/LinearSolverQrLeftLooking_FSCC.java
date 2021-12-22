/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.interfaces.decomposition.DecompositionInterface;
import org.ejml.interfaces.linsol.LinearSolverSparse;
import org.ejml.sparse.csc.CommonOps_FSCC;
import org.ejml.sparse.csc.decomposition.qr.QrHelperFunctions_FSCC;
import org.ejml.sparse.csc.decomposition.qr.QrLeftLookingDecomposition_FSCC;
import org.ejml.sparse.csc.misc.TriangularSolver_FSCC;

public class LinearSolverQrLeftLooking_FSCC
implements LinearSolverSparse<FMatrixSparseCSC, FMatrixRMaj> {
    private final QrLeftLookingDecomposition_FSCC qr;
    private int AnumRows;
    private int AnumCols;
    private final FGrowArray gb = new FGrowArray();
    private final FGrowArray gbp = new FGrowArray();
    private final FGrowArray gx = new FGrowArray();
    private final IGrowArray gw = new IGrowArray();
    private final FMatrixSparseCSC tmp = new FMatrixSparseCSC(1, 1, 1);

    public LinearSolverQrLeftLooking_FSCC(QrLeftLookingDecomposition_FSCC qr) {
        this.qr = qr;
    }

    @Override
    public boolean setA(FMatrixSparseCSC A) {
        if (A.numCols > A.numRows) {
            throw new IllegalArgumentException("Can't handle wide matrices");
        }
        this.AnumRows = A.numRows;
        this.AnumCols = A.numCols;
        return this.qr.decompose(A) && !this.qr.isSingular();
    }

    @Override
    public double quality() {
        return TriangularSolver_FSCC.qualityTriangular(this.qr.getR());
    }

    @Override
    public void solveSparse(FMatrixSparseCSC B, FMatrixSparseCSC X) {
        X.reshape(this.AnumCols, B.numCols, X.numRows);
        IGrowArray gw1 = this.qr.getGwork();
        this.tmp.setTo(B);
        B = this.tmp;
        FMatrixSparseCSC B_tmp = B.createLike();
        int[] pinv = this.qr.getStructure().getPinv();
        CommonOps_FSCC.permuteRowInv(pinv, B, B_tmp);
        FMatrixSparseCSC swap = B_tmp;
        B_tmp = B;
        B = swap;
        FMatrixSparseCSC V = this.qr.getV();
        for (int i = 0; i < this.AnumCols; ++i) {
            QrHelperFunctions_FSCC.rank1UpdateMultR(V, i, this.qr.getBeta(i), B, B_tmp, this.gw, this.gx);
            swap = B_tmp;
            B_tmp = B;
            B = swap;
        }
        FMatrixSparseCSC R = this.qr.getR();
        TriangularSolver_FSCC.solve(R, false, B, X, null, this.gx, this.gw, gw1);
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
    public void solve(FMatrixRMaj B, FMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.AnumRows, this.AnumCols, B, X);
        float[] b = UtilEjml.adjust(this.gb, B.numRows);
        float[] bp = UtilEjml.adjust(this.gbp, B.numRows);
        float[] x = UtilEjml.adjust(this.gx, this.AnumCols);
        int[] pinv = this.qr.getStructure().getPinv();
        for (int colX = 0; colX < B.numCols; ++colX) {
            float[] out;
            int index = colX;
            int i = 0;
            while (i < B.numRows) {
                b[i] = B.data[index];
                ++i;
                index += X.numCols;
            }
            CommonOps_FSCC.permuteInv(pinv, b, bp, this.AnumRows);
            for (int j = 0; j < this.AnumCols; ++j) {
                QrHelperFunctions_FSCC.applyHouseholder(this.qr.getV(), j, this.qr.getBeta(j), bp);
            }
            TriangularSolver_FSCC.solveU(this.qr.getR(), bp);
            if (this.qr.isFillPermutated()) {
                CommonOps_FSCC.permute(this.qr.getFillPermutation(), bp, x, X.numRows);
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

