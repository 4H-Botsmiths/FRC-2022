/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.qr;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.jetbrains.annotations.Nullable;

public class QrUpdate_DDRM {
    @Nullable
    private DMatrixRMaj Q;
    @Nullable
    private DMatrixRMaj R;
    private DMatrixRMaj U_tran;
    private DMatrixRMaj Qm;
    private double[] r_row;
    private int maxCols;
    private int maxRows;
    private int m;
    private int n;
    private int m_m;
    private boolean autoGrow;

    public QrUpdate_DDRM(int maxRows, int maxCols) {
        this.autoGrow = false;
        this.declareInternalData(maxRows, maxCols);
    }

    public QrUpdate_DDRM(int maxRows, int maxCols, boolean autoGrow) {
        this.autoGrow = autoGrow;
        this.declareInternalData(maxRows, maxCols);
    }

    public QrUpdate_DDRM() {
        this.autoGrow = true;
    }

    public void declareInternalData(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.U_tran = new DMatrixRMaj(maxRows, maxRows);
        this.Qm = new DMatrixRMaj(maxRows, maxRows);
        this.r_row = new double[maxCols];
    }

    public void addRow(DMatrixRMaj Q, DMatrixRMaj R, double[] row, int rowIndex, boolean resizeR) {
        this.setQR(Q, R, 1);
        this.m_m = this.m + 1;
        if (Q.data.length < this.m_m * this.m_m) {
            throw new IllegalArgumentException("Q matrix does not have enough data to grow");
        }
        if (resizeR && R.data.length < this.m_m * this.n) {
            throw new IllegalArgumentException("R matrix does not have enough data to grow");
        }
        if (resizeR) {
            R.reshape(this.m_m, this.n, false);
        }
        this.U_tran.reshape(this.m_m, this.m_m, false);
        this.applyFirstGivens(row);
        this.applyLaterGivens();
        this.updateInsertQ(rowIndex);
        this.R = null;
        this.Q = null;
    }

    public void deleteRow(DMatrixRMaj Q, DMatrixRMaj R, int rowIndex, boolean resizeR) {
        this.setQR(Q, R, 0);
        if (this.m - 1 < this.n) {
            throw new IllegalArgumentException("Removing any row would make the system under determined.");
        }
        this.m_m = this.m - 1;
        this.U_tran.reshape(this.m, this.m, false);
        if (resizeR) {
            R.reshape(this.m_m, this.n, false);
        }
        this.computeRemoveGivens(rowIndex);
        this.updateRemoveQ(rowIndex);
        this.updateRemoveR();
        this.R = null;
        this.Q = null;
    }

    private void setQR(DMatrixRMaj Q, DMatrixRMaj R, int growRows) {
        if (Q.numRows != Q.numCols) {
            throw new IllegalArgumentException("Q should be square.");
        }
        this.Q = Q;
        this.R = R;
        this.m = Q.numRows;
        this.n = R.numCols;
        if (this.m + growRows > this.maxRows || this.n > this.maxCols) {
            if (this.autoGrow) {
                this.declareInternalData(this.m + growRows, this.n);
            } else {
                throw new IllegalArgumentException("Autogrow has been set to false and the maximum number of rows or columns has been exceeded.");
            }
        }
    }

    private void updateInsertQ(int rowIndex) {
        int k;
        double sum;
        int j;
        int i;
        this.Qm.setTo(this.Q);
        this.Q.reshape(this.m_m, this.m_m, false);
        for (i = 0; i < rowIndex; ++i) {
            for (j = 0; j < this.m_m; ++j) {
                sum = 0.0;
                for (k = 0; k < this.m; ++k) {
                    sum += this.Qm.data[i * this.m + k] * this.U_tran.data[j * this.m_m + k + 1];
                }
                this.Q.data[i * this.m_m + j] = sum;
            }
        }
        for (int j2 = 0; j2 < this.m_m; ++j2) {
            this.Q.data[rowIndex * this.m_m + j2] = this.U_tran.data[j2 * this.m_m];
        }
        for (i = rowIndex + 1; i < this.m_m; ++i) {
            for (j = 0; j < this.m_m; ++j) {
                sum = 0.0;
                for (k = 0; k < this.m; ++k) {
                    sum += this.Qm.data[(i - 1) * this.m + k] * this.U_tran.data[j * this.m_m + k + 1];
                }
                this.Q.data[i * this.m_m + j] = sum;
            }
        }
    }

    private void updateRemoveQ(int rowIndex) {
        int k;
        double sum;
        int j;
        int i;
        this.Qm.setTo(this.Q);
        this.Q.reshape(this.m_m, this.m_m, false);
        for (i = 0; i < rowIndex; ++i) {
            for (j = 1; j < this.m; ++j) {
                sum = 0.0;
                for (k = 0; k < this.m; ++k) {
                    sum += this.Qm.data[i * this.m + k] * this.U_tran.data[j * this.m + k];
                }
                this.Q.data[i * this.m_m + j - 1] = sum;
            }
        }
        for (i = rowIndex + 1; i < this.m; ++i) {
            for (j = 1; j < this.m; ++j) {
                sum = 0.0;
                for (k = 0; k < this.m; ++k) {
                    sum += this.Qm.data[i * this.m + k] * this.U_tran.data[j * this.m + k];
                }
                this.Q.data[(i - 1) * this.m_m + j - 1] = sum;
            }
        }
    }

    private void updateRemoveR() {
        for (int i = 1; i < this.n + 1; ++i) {
            for (int j = 0; j < this.n; ++j) {
                double sum = 0.0;
                for (int k = i - 1; k <= j; ++k) {
                    sum += this.U_tran.data[i * this.m + k] * this.R.data[k * this.n + j];
                }
                this.R.data[(i - 1) * this.n + j] = sum;
            }
        }
    }

    private void applyFirstGivens(double[] row) {
        double s;
        double c;
        double xi = row[0];
        double xj = this.R.data[0];
        double r = xi * xi + xj * xj;
        if (r != 0.0) {
            r = Math.sqrt(r);
            c = xi / r;
            s = xj / r;
        } else {
            c = 1.0;
            s = 0.0;
        }
        this.R.data[0] = r;
        for (int col = 1; col < this.n; ++col) {
            double vali = row[col];
            double valj = this.R.data[col];
            this.R.data[col] = c * vali + s * valj;
            this.r_row[col] = c * valj - s * vali;
        }
        CommonOps_DDRM.setIdentity(this.U_tran);
        this.U_tran.data[0] = c;
        this.U_tran.data[1] = s;
        this.U_tran.data[this.m_m] = -s;
        this.U_tran.data[this.m_m + 1] = c;
    }

    private void applyLaterGivens() {
        for (int row = 1; row < this.n; ++row) {
            int col;
            double s;
            double c;
            double xi = this.r_row[row];
            double xj = this.R.data[this.n * row + row];
            double r = xi * xi + xj * xj;
            if (r != 0.0) {
                r = Math.sqrt(r);
                c = xi / r;
                s = xj / r;
            } else {
                c = 1.0;
                s = 0.0;
            }
            this.R.data[this.n * row + row] = r;
            for (col = row + 1; col < this.n; ++col) {
                double vali = this.r_row[col];
                double valj = this.R.data[this.n * row + col];
                this.R.data[this.n * row + col] = c * vali + s * valj;
                this.r_row[col] = c * valj - s * vali;
            }
            for (col = 0; col <= row + 1; ++col) {
                double q1 = this.U_tran.data[row * this.m_m + col];
                double q2 = this.U_tran.data[(row + 1) * this.m_m + col];
                this.U_tran.data[row * this.m_m + col] = c * q1 + s * q2;
                this.U_tran.data[(row + 1) * this.m_m + col] = c * q2 - s * q1;
            }
        }
    }

    private void computeRemoveGivens(int selectedRow) {
        CommonOps_DDRM.setIdentity(this.U_tran);
        double xj = this.Q.data[selectedRow * this.m + this.m - 1];
        for (int j = this.m - 2; j >= 0; --j) {
            double s;
            double c;
            double xi = this.Q.data[selectedRow * this.m + j];
            double r = xi * xi + xj * xj;
            if (r != 0.0) {
                r = Math.sqrt(r);
                c = xi / r;
                s = xj / r;
            } else {
                c = 1.0;
                s = 0.0;
            }
            xj = r;
            for (int col = j; col < this.m; ++col) {
                double q1 = this.U_tran.data[j * this.m + col];
                double q2 = this.U_tran.data[(j + 1) * this.m + col];
                this.U_tran.data[j * this.m + col] = c * q1 + s * q2;
                this.U_tran.data[(j + 1) * this.m + col] = c * q2 - s * q1;
            }
        }
    }

    public DMatrixRMaj getU_tran() {
        return this.U_tran;
    }
}

