/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.chol;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.interfaces.decomposition.CholeskyLDLDecomposition_F64;
import org.jetbrains.annotations.Nullable;

public class CholeskyDecompositionLDL_DDRM
implements CholeskyLDLDecomposition_F64<DMatrixRMaj> {
    private int maxWidth;
    private int n;
    private DMatrixRMaj L;
    private double[] d;
    double[] vv;

    public void setExpectedMaxSize(int numRows, int numCols) {
        if (numRows != numCols) {
            throw new IllegalArgumentException("Can only decompose square matrices");
        }
        this.maxWidth = numRows;
        this.L = new DMatrixRMaj(this.maxWidth, this.maxWidth);
        this.vv = new double[this.maxWidth];
        this.d = new double[this.maxWidth];
    }

    @Override
    public boolean decompose(DMatrixRMaj mat) {
        int j;
        int i;
        if (mat.numRows > this.maxWidth) {
            this.setExpectedMaxSize(mat.numRows, mat.numCols);
        } else if (mat.numRows != mat.numCols) {
            throw new RuntimeException("Can only decompose square matrices");
        }
        this.n = mat.numRows;
        this.L.setTo(mat);
        double[] el = this.L.data;
        double d_inv = 0.0;
        for (i = 0; i < this.n; ++i) {
            for (j = i; j < this.n; ++j) {
                double sum = el[i * this.n + j];
                for (int k = 0; k < i; ++k) {
                    sum -= el[i * this.n + k] * el[j * this.n + k] * this.d[k];
                }
                if (i == j) {
                    if (sum <= 0.0) {
                        return false;
                    }
                    this.d[i] = sum;
                    d_inv = 1.0 / sum;
                    el[i * this.n + i] = 1.0;
                    continue;
                }
                el[j * this.n + i] = sum * d_inv;
            }
        }
        for (i = 0; i < this.n; ++i) {
            for (j = i + 1; j < this.n; ++j) {
                el[i * this.n + j] = 0.0;
            }
        }
        return true;
    }

    @Override
    public boolean inputModified() {
        return false;
    }

    @Override
    public double[] getDiagonal() {
        return this.d;
    }

    public DMatrixRMaj getL() {
        return this.L;
    }

    public double[] _getVV() {
        return this.vv;
    }

    @Override
    public DMatrixRMaj getL(@Nullable DMatrixRMaj L) {
        if (L == null) {
            L = this.L.copy();
        } else {
            L.setTo(this.L);
        }
        return L;
    }

    @Override
    public DMatrixRMaj getD(@Nullable DMatrixRMaj D) {
        return CommonOps_DDRM.diag(D, this.L.numCols, this.d);
    }
}

