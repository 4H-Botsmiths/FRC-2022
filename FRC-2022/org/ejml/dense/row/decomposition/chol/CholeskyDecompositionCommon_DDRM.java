/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.chol;

import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.decomposition.UtilDecompositons_DDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F64;
import org.jetbrains.annotations.Nullable;

public abstract class CholeskyDecompositionCommon_DDRM
implements CholeskyDecomposition_F64<DMatrixRMaj> {
    protected int maxWidth = -1;
    protected int n;
    protected DMatrixRMaj T;
    protected double[] t;
    protected double[] vv;
    protected boolean lower;
    protected Complex_F64 det = new Complex_F64();

    protected CholeskyDecompositionCommon_DDRM(boolean lower) {
        this.lower = lower;
    }

    public void setExpectedMaxSize(int numRows, int numCols) {
        if (numRows != numCols) {
            throw new IllegalArgumentException("Can only decompose square matrices");
        }
        this.maxWidth = numCols;
        this.vv = new double[this.maxWidth];
    }

    @Override
    public boolean isLower() {
        return this.lower;
    }

    @Override
    public boolean decompose(DMatrixRMaj mat) {
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
    public DMatrixRMaj getT(@Nullable DMatrixRMaj T) {
        if (this.lower) {
            T = UtilDecompositons_DDRM.checkZerosUT(T, this.n, this.n);
            for (int i = 0; i < this.n; ++i) {
                for (int j = 0; j <= i; ++j) {
                    T.unsafe_set(i, j, this.T.unsafe_get(i, j));
                }
            }
        } else {
            T = UtilDecompositons_DDRM.checkZerosLT(T, this.n, this.n);
            for (int i = 0; i < this.n; ++i) {
                for (int j = i; j < this.n; ++j) {
                    T.unsafe_set(i, j, this.T.unsafe_get(i, j));
                }
            }
        }
        return T;
    }

    public DMatrixRMaj getT() {
        return this.T;
    }

    public double[] _getVV() {
        return this.vv;
    }

    @Override
    public Complex_F64 computeDeterminant() {
        double prod = 1.0;
        int total = this.n * this.n;
        for (int i = 0; i < total; i += this.n + 1) {
            prod *= this.t[i];
        }
        this.det.real = prod * prod;
        this.det.imaginary = 0.0;
        return this.det;
    }
}

