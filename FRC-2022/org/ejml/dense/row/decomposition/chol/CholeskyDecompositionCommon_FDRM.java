/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.chol;

import org.ejml.data.Complex_F32;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.decomposition.UtilDecompositons_FDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F32;
import org.jetbrains.annotations.Nullable;

public abstract class CholeskyDecompositionCommon_FDRM
implements CholeskyDecomposition_F32<FMatrixRMaj> {
    protected int maxWidth = -1;
    protected int n;
    protected FMatrixRMaj T;
    protected float[] t;
    protected float[] vv;
    protected boolean lower;
    protected Complex_F32 det = new Complex_F32();

    protected CholeskyDecompositionCommon_FDRM(boolean lower) {
        this.lower = lower;
    }

    public void setExpectedMaxSize(int numRows, int numCols) {
        if (numRows != numCols) {
            throw new IllegalArgumentException("Can only decompose square matrices");
        }
        this.maxWidth = numCols;
        this.vv = new float[this.maxWidth];
    }

    @Override
    public boolean isLower() {
        return this.lower;
    }

    @Override
    public boolean decompose(FMatrixRMaj mat) {
        if (mat.numRows > this.maxWidth) {
            this.setExpectedMaxSize(mat.numRows, mat.numCols);
        } else if (mat.numRows != mat.numCols) {
            throw new IllegalArgumentException("Must be a square matrix.");
        }
        this.n = mat.numRows;
        this.T = mat;
        this.t = this.T.data;
        if (this.lower) {
            return this.decomposeLower();
        }
        return this.decomposeUpper();
    }

    @Override
    public boolean inputModified() {
        return true;
    }

    protected abstract boolean decomposeLower();

    protected abstract boolean decomposeUpper();

    @Override
    public FMatrixRMaj getT(@Nullable FMatrixRMaj T) {
        if (this.lower) {
            T = UtilDecompositons_FDRM.checkZerosUT(T, this.n, this.n);
            for (int i = 0; i < this.n; ++i) {
                for (int j = 0; j <= i; ++j) {
                    T.unsafe_set(i, j, this.T.unsafe_get(i, j));
                }
            }
        } else {
            T = UtilDecompositons_FDRM.checkZerosLT(T, this.n, this.n);
            for (int i = 0; i < this.n; ++i) {
                for (int j = i; j < this.n; ++j) {
                    T.unsafe_set(i, j, this.T.unsafe_get(i, j));
                }
            }
        }
        return T;
    }

    public FMatrixRMaj getT() {
        return this.T;
    }

    public float[] _getVV() {
        return this.vv;
    }

    @Override
    public Complex_F32 computeDeterminant() {
        float prod = 1.0f;
        int total = this.n * this.n;
        for (int i = 0; i < total; i += this.n + 1) {
            prod *= this.t[i];
        }
        this.det.real = prod * prod;
        this.det.imaginary = 0.0f;
        return this.det;
    }
}

