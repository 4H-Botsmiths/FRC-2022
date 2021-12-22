/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.LinearSolverSafe;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.SpecializedOps_FDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_FDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_FDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_FDRM;
import org.ejml.interfaces.decomposition.QRPDecomposition_F32;
import org.ejml.interfaces.linsol.LinearSolverDense;

public abstract class BaseLinearSolverQrp_FDRM
extends LinearSolverAbstract_FDRM {
    QRPDecomposition_F32<FMatrixRMaj> decomposition;
    protected boolean norm2Solution;
    protected FMatrixRMaj Y = new FMatrixRMaj(1, 1);
    protected FMatrixRMaj R = new FMatrixRMaj(1, 1);
    protected FMatrixRMaj R11 = new FMatrixRMaj(1, 1);
    protected FMatrixRMaj I = new FMatrixRMaj(1, 1);
    protected int rank;
    protected LinearSolverDense<FMatrixRMaj> internalSolver = LinearSolverFactory_FDRM.leastSquares(1, 1);
    private FMatrixRMaj W = new FMatrixRMaj(1, 1);

    protected BaseLinearSolverQrp_FDRM(QRPDecomposition_F32<FMatrixRMaj> decomposition, boolean norm2Solution) {
        this.decomposition = decomposition;
        this.norm2Solution = norm2Solution;
        if (this.internalSolver.modifiesA()) {
            this.internalSolver = new LinearSolverSafe<FMatrixRMaj>(this.internalSolver);
        }
    }

    @Override
    public boolean setA(FMatrixRMaj A) {
        this._setA(A);
        if (!this.decomposition.decompose(A)) {
            return false;
        }
        this.rank = this.decomposition.getRank();
        this.R.reshape(this.numRows, this.numCols);
        this.decomposition.getR(this.R, false);
        this.R11.reshape(this.rank, this.rank);
        CommonOps_FDRM.extract(this.R, 0, this.rank, 0, this.rank, this.R11, 0, 0);
        if (this.norm2Solution && this.rank < this.numCols) {
            this.W.reshape(this.rank, this.numCols - this.rank);
            CommonOps_FDRM.extract(this.R, 0, this.rank, this.rank, this.numCols, this.W, 0, 0);
            TriangularSolver_FDRM.solveU(this.R11.data, 0, this.R11.numCols, this.R11.numCols, this.W.data, 0, this.W.numCols, this.W.numCols);
            this.W.reshape(this.numCols, this.W.numCols, true);
            for (int i = 0; i < this.numCols - this.rank; ++i) {
                for (int j = 0; j < this.numCols - this.rank; ++j) {
                    if (i == j) {
                        this.W.set(i + this.rank, j, -1.0f);
                        continue;
                    }
                    this.W.set(i + this.rank, j, 0.0f);
                }
            }
        }
        return true;
    }

    @Override
    public double quality() {
        return SpecializedOps_FDRM.qualityTriangular(this.R);
    }

    protected void upgradeSolution(FMatrixRMaj X) {
        FMatrixRMaj z = this.Y;
        if (!this.internalSolver.setA(this.W)) {
            throw new RuntimeException("This should never happen.  Is input NaN?");
        }
        z.reshape(this.numCols - this.rank, 1);
        this.internalSolver.solve(X, z);
        CommonOps_FDRM.multAdd(-1.0f, this.W, z, X);
    }

    @Override
    public void invert(FMatrixRMaj A_inv) {
        if (A_inv.numCols != this.numRows || A_inv.numRows != this.numCols) {
            throw new IllegalArgumentException("Unexpected dimensions for A_inv");
        }
        this.I.reshape(this.numRows, this.numRows);
        CommonOps_FDRM.setIdentity(this.I);
        this.solve(this.I, A_inv);
    }

    @Override
    public QRPDecomposition_F32<FMatrixRMaj> getDecomposition() {
        return this.decomposition;
    }
}

