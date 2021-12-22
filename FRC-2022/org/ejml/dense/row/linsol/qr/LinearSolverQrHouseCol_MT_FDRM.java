/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.TriangularSolver_FDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderColumn_MT_FDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_FDRM;
import org.ejml.dense.row.linsol.qr.LinearSolverQrHouseCol_FDRM;
import pabeles.concurrency.GrowArray;

public class LinearSolverQrHouseCol_MT_FDRM
extends LinearSolverQrHouseCol_FDRM {
    GrowArray<Work> workArrays = new GrowArray<Work>(() -> new Work());

    public LinearSolverQrHouseCol_MT_FDRM() {
        super(new QRDecompositionHouseholderColumn_MT_FDRM());
    }

    @Override
    public void solve(FMatrixRMaj B, FMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int BnumCols = B.numCols;
        EjmlConcurrency.loopBlocks(0, BnumCols, this.workArrays, (work, idx0, idx1) -> {
            work.a.reshape(this.numRows, 1);
            work.tmp.reshape(this.numRows);
            FMatrixRMaj a = work.a;
            float[] temp = work.tmp.data;
            for (int colB = idx0; colB < idx1; ++colB) {
                int i;
                for (i = 0; i < this.numRows; ++i) {
                    a.data[i] = B.data[i * BnumCols + colB];
                }
                for (int n = 0; n < this.numCols; ++n) {
                    float[] u = this.QR[n];
                    QrHelperFunctions_FDRM.rank1UpdateMultR_u0(a, u, 1.0f, this.gammas[n], 0, n, this.numRows, temp);
                }
                TriangularSolver_FDRM.solveU(this.R.data, a.data, this.numCols);
                for (i = 0; i < this.numCols; ++i) {
                    X.data[i * X.numCols + colB] = a.data[i];
                }
            }
        });
    }

    private static class Work {
        public final FMatrixRMaj a = new FMatrixRMaj(1, 1);
        public final FMatrixRMaj u = new FMatrixRMaj(1, 1);
        public final FGrowArray tmp = new FGrowArray();

        private Work() {
        }
    }
}

