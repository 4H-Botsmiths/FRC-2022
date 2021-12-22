/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.TriangularSolver_FDRM;
import org.ejml.dense.row.decomposition.qr.QRColPivDecompositionHouseholderColumn_FDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_FDRM;
import org.ejml.dense.row.linsol.qr.BaseLinearSolverQrp_FDRM;

public class LinearSolverQrpHouseCol_FDRM
extends BaseLinearSolverQrp_FDRM {
    private QRColPivDecompositionHouseholderColumn_FDRM decomposition;
    private FMatrixRMaj x_basic = new FMatrixRMaj(1, 1);

    public LinearSolverQrpHouseCol_FDRM(QRColPivDecompositionHouseholderColumn_FDRM decomposition, boolean norm2Solution) {
        super(decomposition, norm2Solution);
        this.decomposition = decomposition;
    }

    @Override
    public void solve(FMatrixRMaj B, FMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int BnumCols = B.numCols;
        int[] pivots = this.decomposition.getColPivots();
        float[][] qr = this.decomposition.getQR();
        float[] gammas = this.decomposition.getGammas();
        for (int colB = 0; colB < BnumCols; ++colB) {
            int i;
            this.x_basic.reshape(this.numRows, 1);
            this.Y.reshape(this.numRows, 1);
            for (i = 0; i < this.numRows; ++i) {
                this.x_basic.data[i] = B.get(i, colB);
            }
            for (i = 0; i < this.rank; ++i) {
                float[] u = qr[i];
                float vv = u[i];
                u[i] = 1.0f;
                QrHelperFunctions_FDRM.rank1UpdateMultR(this.x_basic, u, gammas[i], 0, i, this.numRows, this.Y.data);
                u[i] = vv;
            }
            TriangularSolver_FDRM.solveU(this.R11.data, this.x_basic.data, this.rank);
            this.x_basic.reshape(this.numCols, 1, true);
            for (i = this.rank; i < this.numCols; ++i) {
                this.x_basic.data[i] = 0.0f;
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

