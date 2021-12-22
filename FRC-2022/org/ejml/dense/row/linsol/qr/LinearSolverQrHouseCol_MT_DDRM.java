/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.UtilEjml;
import org.ejml.concurrency.EjmlConcurrency;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.TriangularSolver_DDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderColumn_MT_DDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_DDRM;
import org.ejml.dense.row.linsol.qr.LinearSolverQrHouseCol_DDRM;
import pabeles.concurrency.GrowArray;

public class LinearSolverQrHouseCol_MT_DDRM
extends LinearSolverQrHouseCol_DDRM {
    GrowArray<Work> workArrays = new GrowArray<Work>(() -> new Work());

    public LinearSolverQrHouseCol_MT_DDRM() {
        super(new QRDecompositionHouseholderColumn_MT_DDRM());
    }

    @Override
    public void solve(DMatrixRMaj B, DMatrixRMaj X) {
        UtilEjml.checkReshapeSolve(this.numRows, this.numCols, B, X);
        int BnumCols = B.numCols;
        EjmlConcurrency.loopBlocks(0, BnumCols, this.workArrays, (work, idx0, idx1) -> {
            work.a.reshape(this.numRows, 1);
            work.tmp.reshape(this.numRows);
            DMatrixRMaj a = work.a;
            double[] temp = work.tmp.data;
            for (int colB = idx0; colB < idx1; ++colB) {
                int i;
                for (i = 0; i < this.numRows; ++i) {
                    a.data[i] = B.data[i * BnumCols + colB];
                }
                for (int n = 0; n < this.numCols; ++n) {
                    double[] u = this.QR[n];
                    QrHelperFunctions_DDRM.rank1UpdateMultR_u0(a, u, 1.0, this.gammas[n], 0, n, this.numRows, temp);
                }
                TriangularSolver_DDRM.solveU(this.R.data, a.data, this.numCols);
                for (i = 0; i < this.numCols; ++i) {
                    X.data[i * X.numCols + colB] = a.data[i];
                }
            }
        });
    }

    private static class Work {
        public final DMatrixRMaj a = new DMatrixRMaj(1, 1);
        public final DMatrixRMaj u = new DMatrixRMaj(1, 1);
        public final DGrowArray tmp = new DGrowArray();

        private Work() {
        }
    }
}

