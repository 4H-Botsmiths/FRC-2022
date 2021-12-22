/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.qr;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.dense.row.decomposition.UtilDecompositons_FDRM;
import org.ejml.dense.row.decomposition.qr.QRDecompositionHouseholderColumn_FDRM;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_FDRM;
import org.ejml.interfaces.decomposition.QRPDecomposition_F32;
import org.jetbrains.annotations.Nullable;

public class QRColPivDecompositionHouseholderColumn_FDRM
extends QRDecompositionHouseholderColumn_FDRM
implements QRPDecomposition_F32<FMatrixRMaj> {
    protected int[] pivots;
    protected float[] normsCol;
    protected float singularThreshold = UtilEjml.F_EPS;
    protected int rank;
    float maxValueAbs;

    public QRColPivDecompositionHouseholderColumn_FDRM(float singularThreshold) {
        this.singularThreshold = singularThreshold;
    }

    public QRColPivDecompositionHouseholderColumn_FDRM() {
    }

    @Override
    public void setSingularThreshold(float threshold) {
        this.singularThreshold = threshold;
    }

    @Override
    public void setExpectedMaxSize(int numRows, int numCols) {
        super.setExpectedMaxSize(numRows, numCols);
        if (this.pivots == null || this.pivots.length < numCols) {
            this.pivots = new int[numCols];
            this.normsCol = new float[numCols];
        }
    }

    @Override
    public FMatrixRMaj getQ(@Nullable FMatrixRMaj Q, boolean compact) {
        Q = compact ? UtilDecompositons_FDRM.ensureIdentity(Q, this.numRows, this.minLength) : UtilDecompositons_FDRM.ensureIdentity(Q, this.numRows, this.numRows);
        for (int j = this.rank - 1; j >= 0; --j) {
            float[] u = this.dataQR[j];
            float vv = u[j];
            u[j] = 1.0f;
            QrHelperFunctions_FDRM.rank1UpdateMultR(Q, u, this.gammas[j], j, j, this.numRows, this.v);
            u[j] = vv;
        }
        return Q;
    }

    @Override
    public boolean decompose(FMatrixRMaj A) {
        this.setExpectedMaxSize(A.numRows, A.numCols);
        this.maxValueAbs = CommonOps_FDRM.elementMaxAbs(A);
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
            float[] c = this.dataQR[col];
            float norm = 0.0f;
            for (int row = 0; row < this.numRows; ++row) {
                float element = c[row];
                norm += element * element;
            }
            this.normsCol[col] = norm;
        }
    }

    protected void updateNorms(int j) {
        boolean foundNegative = false;
        int col = j;
        while (col < this.numCols) {
            float e = this.dataQR[col][j - 1];
            int n = col++;
            float f = this.normsCol[n] = this.normsCol[n] - e * e;
            float v = f;
            if (!(v < 0.0f)) continue;
            foundNegative = true;
            break;
        }
        if (foundNegative) {
            for (col = j; col < this.numCols; ++col) {
                float[] u = this.dataQR[col];
                float actual = 0.0f;
                for (int i = j; i < this.numRows; ++i) {
                    float v = u[i];
                    actual += v * v;
                }
                this.normsCol[col] = actual;
            }
        }
    }

    protected void swapColumns(int j) {
        int largestIndex = j;
        float largestNorm = this.normsCol[j];
        for (int col = j + 1; col < this.numCols; ++col) {
            float n = this.normsCol[col];
            if (!(n > largestNorm)) continue;
            largestNorm = n;
            largestIndex = col;
        }
        float[] tempC = this.dataQR[j];
        this.dataQR[j] = this.dataQR[largestIndex];
        this.dataQR[largestIndex] = tempC;
        float tempN = this.normsCol[j];
        this.normsCol[j] = this.normsCol[largestIndex];
        this.normsCol[largestIndex] = tempN;
        int tempP = this.pivots[j];
        this.pivots[j] = this.pivots[largestIndex];
        this.pivots[largestIndex] = tempP;
    }

    protected boolean householderPivot(int j) {
        float[] u = this.dataQR[j];
        float max = QrHelperFunctions_FDRM.findMax(u, j, this.numRows - j);
        if (max <= this.singularThreshold * this.maxValueAbs) {
            return false;
        }
        this.tau = QrHelperFunctions_FDRM.computeTauAndDivide(j, this.numRows, u, max);
        float u_0 = u[j] + this.tau;
        QrHelperFunctions_FDRM.divideElements(j + 1, this.numRows, u, u_0);
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
    public FMatrixRMaj getColPivotMatrix(@Nullable FMatrixRMaj P) {
        if (P == null) {
            P = new FMatrixRMaj(this.numCols, this.numCols);
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
            P.set(this.pivots[i], i, 1.0f);
        }
        return P;
    }
}

