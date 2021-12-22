/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderColumn_FDRM;
import org.ejml.dense.row.decomposition.qr.QrUpdate_FDRM;
import org.ejml.dense.row.linsol.AdjustableLinearSolver_FDRM;
import org.ejml.dense.row.linsol.qr.LinearSolverQr_FDRM;

public class AdjLinearSolverQr_FDRM
extends LinearSolverQr_FDRM
implements AdjustableLinearSolver_FDRM {
    private QrUpdate_FDRM update;
    private FMatrixRMaj A;

    public AdjLinearSolverQr_FDRM() {
        super(new QRDecompositionHouseholderColumn_FDRM());
    }

    @Override
    public void setMaxSize(int maxRows, int maxCols) {
        super.setMaxSize(maxRows += 5, maxCols);
        this.update = new QrUpdate_FDRM(maxRows, maxCols, true);
        this.A = new FMatrixRMaj(maxRows, maxCols);
    }

    @Override
    public FMatrixRMaj getA() {
        CommonOps_FDRM.mult(this.Q, this.R, this.A);
        return this.A;
    }

    @Override
    public boolean addRowToA(float[] A_row, int rowIndex) {
        if (this.numRows + 1 > this.maxRows) {
            int grow = this.maxRows / 10;
            if (grow < 1) {
                grow = 1;
            }
            this.maxRows = this.numRows + grow;
            this.Q.reshape(this.maxRows, this.maxRows, true);
            this.R.reshape(this.maxRows, this.maxCols, true);
        }
        this.update.addRow(this.Q, this.R, A_row, rowIndex, true);
        ++this.numRows;
        return true;
    }

    @Override
    public boolean removeRowFromA(int index) {
        this.update.deleteRow(this.Q, this.R, index, true);
        --this.numRows;
        return true;
    }
}

