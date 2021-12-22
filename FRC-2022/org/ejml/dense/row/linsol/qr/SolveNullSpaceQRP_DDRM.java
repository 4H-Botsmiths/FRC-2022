/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.decomposition.qr.QRColPivDecompositionHouseholderColumn_DDRM;
import org.ejml.interfaces.SolveNullSpace;

public class SolveNullSpaceQRP_DDRM
implements SolveNullSpace<DMatrixRMaj> {
    CustomizedQRP decomposition = new CustomizedQRP();
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
        return this.decomposition.inputModified();
    }

    public DMatrixRMaj getQ() {
        return this.Q;
    }

    private static class CustomizedQRP
    extends QRColPivDecompositionHouseholderColumn_DDRM {
        private CustomizedQRP() {
        }

        @Override
        protected void convertToColumnMajor(DMatrixRMaj A) {
            for (int x = 0; x < this.numCols; ++x) {
                System.arraycopy(A.data, x * A.numCols, this.dataQR[x], 0, this.numRows);
            }
        }

        @Override
        public boolean decompose(DMatrixRMaj A) {
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

