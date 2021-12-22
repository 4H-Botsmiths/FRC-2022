/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.row.linsol.qr;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderColumn_DDRM;
import org.ejml.dense.row.decomposition.qr.QrUpdate_DDRM;
import org.ejml.dense.row.linsol.AdjustableLinearSolver_DDRM;
import org.ejml.dense.row.linsol.qr.LinearSolverQr_DDRM;

public class AdjLinearSolverQr_DDRM
extends LinearSolverQr_DDRM
implements AdjustableLinearSolver_DDRM {
    private QrUpdate_DDRM update;
    private DMatrixRMaj A;

    public AdjLinearSolverQr_DDRM() {
        super(new QRDecompositionHouseholderColumn_DDRM());
    }

    @Override
    public void setMaxSize(int maxRows, int maxCols) {
        super.setMaxSize(maxRows += 5, maxCols);
        this.update = new QrUpdate_DDRM(maxRows, maxCols, true);
        this.A = new DMatrixRMaj(maxRows, maxCols);
    }

    @Override
    public DMatrixRMaj getA() {
        CommonOps_DDRM.mult(this.Q, this.R, this.A);
        return this.A;
    }

    @Override
    public boolean addRowToA(double[] A_row, int rowIndex) {
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

