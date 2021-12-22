/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decompose.chol;

import org.ejml.data.Complex_F64;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.decompose.UtilDecompositons_ZDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F64;
import org.jetbrains.annotations.Nullable;

public abstract class CholeskyDecompositionCommon_ZDRM
implements CholeskyDecomposition_F64<ZMatrixRMaj> {
    protected int n;
    protected ZMatrixRMaj T;
    protected double[] t;
    protected boolean lower;
    protected Complex_F64 det = new Complex_F64();

    protected CholeskyDecompositionCommon_ZDRM(boolean lower) {
        this.lower = lower;
    }

    @Override
    public boolean isLower() {
        return this.lower;
    }

    @Override
    public boolean decompose(ZMatrixRMaj mat) {
        if (mat.numRows != mat.numCols) {
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
    public ZMatrixRMaj getT(@Nullable ZMatrixRMaj T) {
        if (this.lower) {
            T = UtilDecompositons_ZDRM.checkZerosUT(T, this.n, this.n);
            for (int i = 0; i < this.n; ++i) {
                int index = i * this.n * 2;
                for (int j = 0; j <= i; ++j) {
                    T.data[index] = this.T.data[index];
                    T.data[++index] = this.T.data[index];
                    ++index;
                }
            }
        } else {
            T = UtilDecompositons_ZDRM.checkZerosLT(T, this.n, this.n);
            for (int i = 0; i < this.n; ++i) {
                int index = (i * this.n + i) * 2;
                for (int j = i; j < this.n; ++j) {
                    T.data[index] = this.T.data[index];
                    T.data[++index] = this.T.data[index];
                    ++index;
                }
            }
        }
        return T;
    }

    public ZMatrixRMaj _getT() {
        return this.T;
    }

    @Override
    public Complex_F64 computeDeterminant() {
        double prod = 1.0;
        int total = this.n * this.n * 2;
        for (int i = 0; i < total; i += 2 * (this.n + 1)) {
            prod *= this.t[i];
        }
        this.det.real = prod * prod;
        this.det.imaginary = 0.0;
        return this.det;
    }
}

