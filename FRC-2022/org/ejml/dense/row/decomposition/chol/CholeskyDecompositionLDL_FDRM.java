/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.chol;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.ejml.interfaces.decomposition.CholeskyLDLDecomposition_F32;
import org.jetbrains.annotations.Nullable;

public class CholeskyDecompositionLDL_FDRM
implements CholeskyLDLDecomposition_F32<FMatrixRMaj> {
    private int maxWidth;
    private int n;
    private FMatrixRMaj L;
    private float[] d;
    float[] vv;

    public void setExpectedMaxSize(int numRows, int numCols) {
        if (numRows != numCols) {
            throw new IllegalArgumentException("Can only decompose square matrices");
        }
        this.maxWidth = numRows;
        this.L = new FMatrixRMaj(this.maxWidth, this.maxWidth);
        this.vv = new float[this.maxWidth];
        this.d = new float[this.maxWidth];
    }

    @Override
    public boolean decompose(FMatrixRMaj mat) {
        int j;
        int i;
        if (mat.numRows > this.maxWidth) {
            this.setExpectedMaxSize(mat.numRows, mat.numCols);
        } else if (mat.numRows != mat.numCols) {
            throw new RuntimeException("Can only decompose square matrices");
        }
        this.n = mat.numRows;
        this.L.setTo(mat);
        float[] el = this.L.data;
        float d_inv = 0.0f;
        for (i = 0; i < this.n; ++i) {
            for (j = i; j < this.n; ++j) {
                float sum = el[i * this.n + j];
                for (int k = 0; k < i; ++k) {
                    sum -= el[i * this.n + k] * el[j * this.n + k] * this.d[k];
                }
                if (i == j) {
                    if (sum <= 0.0f) {
                        return false;
                    }
                    this.d[i] = sum;
                    d_inv = 1.0f / sum;
                    el[i * this.n + i] = 1.0f;
                    continue;
                }
                el[j * this.n + i] = sum * d_inv;
            }
        }
        for (i = 0; i < this.n; ++i) {
            for (j = i + 1; j < this.n; ++j) {
                el[i * this.n + j] = 0.0f;
            }
        }
        return true;
    }

    @Override
    public boolean inputModified() {
        return false;
    }

    @Override
    public float[] getDiagonal() {
        return this.d;
    }

    public FMatrixRMaj getL() {
        return this.L;
    }

    public float[] _getVV() {
        return this.vv;
    }

    @Override
    public FMatrixRMaj getL(@Nullable FMatrixRMaj L) {
        if (L == null) {
            L = this.L.copy();
        } else {
            L.setTo(this.L);
        }
        return L;
    }

    @Override
    public FMatrixRMaj getD(@Nullable FMatrixRMaj D) {
        return CommonOps_FDRM.diag(D, this.L.numCols, this.d);
    }
}

