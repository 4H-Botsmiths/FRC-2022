/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_DDRM;
import org.ejml.dense.row.linsol.qr.BaseLinearSolverQrp_DDRM;
import org.ejml.interfaces.decomposition.QRPDecomposition_F64;

public class SolvePseudoInverseQrp_DDRM
extends BaseLinearSolverQrp_DDRM {
    private DMatrixRMaj Q = new DMatrixRMaj(1, 1);
    private DMatrixRMaj x_basic = new DMatrixRMaj(1, 1);

    public SolvePseudoInverseQrp_DDRM(QRPDecomposition_F64<DMatrixRMaj> decomposition, boolean norm2Solution) {
        super(decomposition, norm2Solution);
    }

    @Override
    public boolean setA(DMatrixRMaj A) {
        if (!super.setA(A)) {
            return false;
        }
        this.Q.reshape(A.numRows, A.numRows);
        this.decomposition.getQ(this.Q, false);
        return true;
    }

    @Override
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        if (B.numRows != this.numRows) {
            throw new IllegalArgumentException("Unexpected dimensions for X: X rows = " + X.numRows + " expected = " + this.numCols);
        }
        X.reshape(this.numCols, B.numCols);
        int BnumCols = B.numCols;
        int[] pivots = this.decomposition.getColPivots();
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            this.x_basic.reshape(this.numRows, 1);
            this.Y.reshape(this.numRows, 1);
            for (i = 0; i < this.numRows; ++i) {
                this.Y.data[i] = B.get(i, colB);
            }
            CommonOps_DDRM.multTransA(this.Q, this.Y, this.x_basic);
            TriangularSolver_DDRM.solveU(this.R11.data, this.x_basic.data, this.rank);
            this.x_basic.reshape(this.numCols, 1, true);
            for (i = this.rank; i < this.numCols; ++i) {
                this.x_basic.data[i] = 0.0;
            }
            if (this.norm2Solution && this.rank < this.numCols) {
                this.upgradeSolution(this.x_basic);
            }
            for (i = 0; i < this.numCols; ++i) {
                X.set(pivots[i], colB, this.x_basic.data[i]);
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
}

