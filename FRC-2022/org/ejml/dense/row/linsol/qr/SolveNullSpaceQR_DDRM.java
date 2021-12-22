/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderTran_DDRM;
import org.ejml.interfaces.SolveNullSpace;

public class SolveNullSpaceQR_DDRM
implements SolveNullSpace<DMatrixRMaj> {
    CustomizedQR decomposition = new CustomizedQR();
    DMatrixRMaj Q = new DMatrixRMaj(1, 1);

    @Override
    public boolean process(DMatrixRMaj A, int numSingularValues, DMatrixRMaj nullspace) {
        this.decomposition.decompose(A);
        if (A.numRows > A.numCols) {
            this.Q.reshape(A.numCols, Math.min(A.numRows, A.numCols));
            this.decomposition.getQ(this.Q, true);
        } else {
            this.Q.reshape(A.numCols, A.numCols);
            this.decomposition.getQ(this.Q, false);
        }
        nullspace.reshape(this.Q.numRows, numSingularValues);
        CommonOps_DDRM.extract(this.Q, 0, this.Q.numRows, this.Q.numCols - numSingularValues, this.Q.numCols, nullspace, 0, 0);
        return true;
    }

    @Override
    public boolean inputModified() {
        return true;
    }

    public DMatrixRMaj getQ() {
        return this.Q;
    }

    private static class CustomizedQR
    extends QRDecompositionHouseholderTran_DDRM {
        private CustomizedQR() {
        }

        @Override
        public void setExpectedMaxSize(int numRows, int numCols) {
            this.numCols = numCols;
            this.numRows = numRows;
            this.minLength = Math.min(numCols, numRows);
            int maxLength = Math.max(numCols, numRows);
            if (this.v == null) {
                this.v = new double[maxLength];
                this.gammas = new double[this.minLength];
            }
            if (this.v.length < maxLength) {
                this.v = new double[maxLength];
            }
            if (this.gammas.length < this.minLength) {
                this.gammas = new double[this.minLength];
            }
        }

        @Override
        public boolean decompose(DMatrixRMaj A_tran) {
            this.setExpectedMaxSize(A_tran.numCols, Math.min(A_tran.numRows, A_tran.numCols));
            this.QR = A_tran;
            this.error = false;
            for (int j = 0; j < this.minLength; ++j) {
                this.householder(j);
                this.updateA(j);
            }
            return !this.error;
        }
    }
}

