/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.LinearSolverSafe;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.SpecializedOps_DDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_DDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_DDRM;
import org.ejml.dense.row.linsol.LinearSolverAbstract_DDRM;
import org.ejml.interfaces.decomposition.QRPDecomposition_F64;
import org.ejml.interfaces.linsol.LinearSolverDense;

public abstract class BaseLinearSolverQrp_DDRM
extends LinearSolverAbstract_DDRM {
    QRPDecomposition_F64<DMatrixRMaj> decomposition;
    protected boolean norm2Solution;
    protected DMatrixRMaj Y = new DMatrixRMaj(1, 1);
    protected DMatrixRMaj R = new DMatrixRMaj(1, 1);
    protected DMatrixRMaj R11 = new DMatrixRMaj(1, 1);
    protected DMatrixRMaj I = new DMatrixRMaj(1, 1);
    protected int rank;
    protected LinearSolverDense<DMatrixRMaj> internalSolver = LinearSolverFactory_DDRM.leastSquares(1, 1);
    private DMatrixRMaj W = new DMatrixRMaj(1, 1);

    protected BaseLinearSolverQrp_DDRM(QRPDecomposition_F64<DMatrixRMaj> decomposition, boolean norm2Solution) {
        this.decomposition = decomposition;
        this.norm2Solution = norm2Solution;
        if (this.internalSolver.modifiesA()) {
            this.internalSolver = new LinearSolverSafe<DMatrixRMaj>(this.internalSolver);
        }
    }

    @Override
    public boolean setA(DMatrixRMaj A) {
        this._setA(A);
        if (!this.decomposition.decompose(A)) {
            return false;
        }
        this.rank = this.decomposition.getRank();
        this.R.reshape(this.numRows, this.numCols);
        this.decomposition.getR(this.R, false);
        this.R11.reshape(this.rank, this.rank);
        CommonOps_DDRM.extract(this.R, 0, this.rank, 0, this.rank, this.R11, 0, 0);
        if (this.norm2Solution && this.rank < this.numCols) {
            this.W.reshape(this.rank, this.numCols - this.rank);
            CommonOps_DDRM.extract(this.R, 0, this.rank, this.rank, this.numCols, this.W, 0, 0);
            TriangularSolver_DDRM.solveU(this.R11.data, 0, this.R11.numCols, this.R11.numCols, this.W.data, 0, this.W.numCols, this.W.numCols);
            this.W.reshape(this.numCols, this.W.numCols, true);
            for (int i = 0; i < this.numCols - this.rank; ++i) {
                for (int j = 0; j < this.numCols - this.rank; ++j) {
                    if (i == j) {
                        this.W.set(i + this.rank, j, -1.0);
                        continue;
                    }
                    this.W.set(i + this.rank, j, 0.0);
                }
            }
        }
        return true;
    }

    @Override
    public double quality() {
        return SpecializedOps_DDRM.qualityTriangular(this.R);
    }

    protected void upgradeSolution(DMatrixRMaj X) {
        DMatrixRMaj z = this.Y;
        if (!this.internalSolver.setA(this.W)) {
            throw new RuntimeException("This should never happen.  Is input NaN?");
        }
        z.reshape(this.numCols - this.rank, 1);
        this.internalSolver.solve(X, z);
        CommonOps_DDRM.multAdd(-1.0, this.W, z, X);
    }

    @Override
    public void invert(DMatrixRMaj A_inv) {
        if (A_inv.numCols != this.numRows || A_inv.numRows != this.numCols) {
            throw new IllegalArgumentException("Unexpected dimensions for A_inv");
        }
        this.I.reshape(this.numRows, this.numRows);
        CommonOps_DDRM.setIdentity(this.I);
        this.solve(this.I, A_inv);
    }

    @Override
    public QRPDecomposition_F64<DMatrixRMaj> getDecomposition() {
        return this.decomposition;
    }
}

