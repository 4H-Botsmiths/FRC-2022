/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.TriangularSolver_DDRM;
import org.ejml.dense.row.decomposition.qr.QRColPivDecompositionHouseholderColumn_DDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_DDRM;
import org.ejml.dense.row.linsol.qr.BaseLinearSolverQrp_DDRM;

public class LinearSolverQrpHouseCol_DDRM
extends BaseLinearSolverQrp_DDRM {
    private QRColPivDecompositionHouseholderColumn_DDRM decomposition;
    private DMatrixRMaj x_basic = new DMatrixRMaj(1, 1);

    public LinearSolverQrpHouseCol_DDRM(QRColPivDecompositionHouseholderColumn_DDRM decomposition, boolean norm2Solution) {
        super(decomposition, norm2Solution);
        this.decomposition = decomposition;
    }

    @Override
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int BnumCols = B.numCols;
        int[] pivots = this.decomposition.getColPivots();
        double[][] qr = this.decomposition.getQR();
        double[] gammas = this.decomposition.getGammas();
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            this.x_basic.reshape(this.numRows, 1);
            this.Y.reshape(this.numRows, 1);
            for (i = 0; i < this.numRows; ++i) {
                this.x_basic.data[i] = B.get(i, colB);
            }
            for (i = 0; i < this.rank; ++i) {
                double[] u = qr[i];
                double vv = u[i];
                u[i] = 1.0;
                QrHelperFunctions_DDRM.rank1UpdateMultR(this.x_basic, u, gammas[i], 0, i, this.numRows, this.Y.data);
                u[i] = vv;
            }
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

