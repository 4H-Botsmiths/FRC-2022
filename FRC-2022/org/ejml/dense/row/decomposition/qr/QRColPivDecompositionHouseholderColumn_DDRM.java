/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.qr;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.decomposition.UtilDecompositons_DDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderColumn_DDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_DDRM;
import org.ejml.interfaces.decomposition.QRPDecomposition_F64;
import org.jetbrains.annotations.Nullable;

public class QRColPivDecompositionHouseholderColumn_DDRM
extends QRDecompositionHouseholderColumn_DDRM
implements QRPDecomposition_F64<DMatrixRMaj> {
    protected int[] pivots;
    protected double[] normsCol;
    protected double singularThreshold = UtilEjml.EPS;
    protected int rank;
    double maxValueAbs;

    public QRColPivDecompositionHouseholderColumn_DDRM(double singularThreshold) {
        this.singularThreshold = singularThreshold;
    }

    public QRColPivDecompositionHouseholderColumn_DDRM() {
    }

    @Override
    public void setSingularThreshold(double threshold) {
        this.singularThreshold = threshold;
    }

    @Override
    public void setExpectedMaxSize(int numRows, int numCols) {
        super.setExpectedMaxSize(numRows, numCols);
        if (this.pivots == null || this.pivots.length < numCols) {
            this.pivots = new int[numCols];
            this.normsCol = new double[numCols];
        }
    }

    @Override
    public DMatrixRMaj getQ(@Nullable DMatrixRMaj Q, boolean compact) {
        Q = compact ? UtilDecompositons_DDRM.ensureIdentity(Q, this.numRows, this.minLength) : UtilDecompositons_DDRM.ensureIdentity(Q, this.numRows, this.numRows);
        for (int j = this.rank - 1; j >= 0; --j) {
            double[] u = this.dataQR[j];
            double vv = u[j];
            u[j] = 1.0;
            QrHelperFunctions_DDRM.rank1UpdateMultR(Q, u, this.gammas[j], j, j, this.numRows, this.v);
            u[j] = vv;
        }
        return Q;
    }

    @Override
    public boolean decompose(DMatrixRMaj A) {
        this.setExpectedMaxSize(A.numRows, A.numCols);
        this.maxValueAbs = CommonOps_DDRM.elementMaxAbs(A);
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

    protected void setupPivotInfo() {
        for (int col = 0; col < this.numCols; ++col) {
            this.pivots[col] = col;
            double[] c = this.dataQR[col];
            double norm = 0.0;
            for (int row = 0; row < this.numRows; ++row) {
                double element = c[row];
                norm += element * element;
            }
            this.normsCol[col] = norm;
        }
    }

    protected void updateNorms(int j) {
        boolean foundNegative = false;
        int col = j;
        while (col < this.numCols) {
            double e = this.dataQR[col][j - 1];
            int n = col++;
            double d = this.normsCol[n] = this.normsCol[n] - e * e;
            double v = d;
            if (!(v < 0.0)) continue;
            foundNegative = true;
            break;
        }
        if (foundNegative) {
            for (col = j; col < this.numCols; ++col) {
                double[] u = this.dataQR[col];
                double actual = 0.0;
                for (int i = j; i < this.numRows; ++i) {
                    double v = u[i];
                    actual += v * v;
                }
                this.normsCol[col] = actual;
            }
        }
    }

    protected void swapColumns(int j) {
        int largestIndex = j;
        double largestNorm = this.normsCol[j];
        for (int col = j + 1; col < this.numCols; ++col) {
            double n = this.normsCol[col];
            if (!(n > largestNorm)) continue;
            largestNorm = n;
            largestIndex = col;
        }
        double[] tempC = this.dataQR[j];
        this.dataQR[j] = this.dataQR[largestIndex];
        this.dataQR[largestIndex] = tempC;
        double tempN = this.normsCol[j];
        this.normsCol[j] = this.normsCol[largestIndex];
        this.normsCol[largestIndex] = tempN;
        int tempP = this.pivots[j];
        this.pivots[j] = this.pivots[largestIndex];
        this.pivots[largestIndex] = tempP;
    }

    protected boolean householderPivot(int j) {
        double[] u = this.dataQR[j];
        double max = QrHelperFunctions_DDRM.findMax(u, j, this.numRows - j);
        if (max <= this.singularThreshold * this.maxValueAbs) {
            return false;
        }
        this.tau = QrHelperFunctions_DDRM.computeTauAndDivide(j, this.numRows, u, max);
        double u_0 = u[j] + this.tau;
        QrHelperFunctions_DDRM.divideElements(j + 1, this.numRows, u, u_0);
        this.gamma = u_0 / this.tau;
        this.tau *= max;
        u[j] = -this.tau;
        this.gammas[j] = this.gamma;
        return true;
    }

    @Override
    public int getRank() {
        return this.rank;
    }

    @Override
    public int[] getColPivots() {
        return this.pivots;
    }

    @Override
    public DMatrixRMaj getColPivotMatrix(@Nullable DMatrixRMaj P) {
        if (P == null) {
            P = new DMatrixRMaj(this.numCols, this.numCols);
        } else {
            if (P.numRows != this.numCols) {
                throw new IllegalArgumentException("Number of rows must be " + this.numCols);
            }
            if (P.numCols != this.numCols) {
                throw new IllegalArgumentException("Number of columns must be " + this.numCols);
            }
            P.zero();
        }
        for (int i = 0; i < this.numCols; ++i) {
            P.set(this.pivots[i], i, 1.0);
        }
        return P;
    }
}

