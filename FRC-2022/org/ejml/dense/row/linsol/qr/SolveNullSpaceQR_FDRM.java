/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderTran_FDRM;
import org.ejml.interfaces.SolveNullSpace;

public class SolveNullSpaceQR_FDRM
implements SolveNullSpace<FMatrixRMaj> {
    CustomizedQR decomposition = new CustomizedQR();
    FMatrixRMaj Q = new FMatrixRMaj(1, 1);

    @Override
    public boolean process(FMatrixRMaj A, int numSingularValues, FMatrixRMaj nullspace) {
        this.decomposition.decompose(A);
        if (A.numRows > A.numCols) {
            this.Q.reshape(A.numCols, Math.min(A.numRows, A.numCols));
            this.decomposition.getQ(this.Q, true);
        } else {
            this.Q.reshape(A.numCols, A.numCols);
            this.decomposition.getQ(this.Q, false);
        }
        nullspace.reshape(this.Q.numRows, numSingularValues);
        CommonOps_FDRM.extract(this.Q, 0, this.Q.numRows, this.Q.numCols - numSingularValues, this.Q.numCols, nullspace, 0, 0);
        return true;
    }

    @Override
    public boolean inputModified() {
        return true;
    }

    public FMatrixRMaj getQ() {
        return this.Q;
    }

    private static class CustomizedQR
    extends QRDecompositionHouseholderTran_FDRM {
        private CustomizedQR() {
        }

        @Override
        public void setExpectedMaxSize(int numRows, int numCols) {
            this.numCols = numCols;
            this.numRows = numRows;
            this.minLength = Math.min(numCols, numRows);
            int maxLength = Math.max(numCols, numRows);
            if (this.v == null) {
                this.v = new float[maxLength];
                this.gammas = new float[this.minLength];
            }
            if (this.v.length < maxLength) {
                this.v = new float[maxLength];
            }
            if (this.gammas.length < this.minLength) {
                this.gammas = new float[this.minLength];
            }
        }

        @Override
        public boolean decompose(FMatrixRMaj A_tran) {
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

