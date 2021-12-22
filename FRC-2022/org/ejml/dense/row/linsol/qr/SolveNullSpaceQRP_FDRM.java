/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.decomposition.qr.QRColPivDecompositionHouseholderColumn_FDRM;
import org.ejml.interfaces.SolveNullSpace;

public class SolveNullSpaceQRP_FDRM
implements SolveNullSpace<FMatrixRMaj> {
    CustomizedQRP decomposition = new CustomizedQRP();
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
        return this.decomposition.inputModified();
    }

    public FMatrixRMaj getQ() {
        return this.Q;
    }

    private static class CustomizedQRP
    extends QRColPivDecompositionHouseholderColumn_FDRM {
        private CustomizedQRP() {
        }

        @Override
        protected void convertToColumnMajor(FMatrixRMaj A) {
            for (int x = 0; x < this.numCols; ++x) {
                System.arraycopy(A.data, x * A.numCols, this.dataQR[x], 0, this.numRows);
            }
        }

        @Override
        public boolean decompose(FMatrixRMaj A) {
            this.setExpectedMaxSize(A.numCols, A.numRows);
            this.convertToColumnMajor(A);
            this.setupPivotInfo();
            for (int j = 0; j < this.minLength; ++j) {
                if (j > 0) {
                    this.updateNorms(j);
                }
                this.swapColumns(j);
                if (!this.householderPivot(j)) break;
                this.updateA(j);
                this.rank = j + 1;
            }
            return true;
        }
    }
}

