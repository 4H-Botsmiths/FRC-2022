/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decompose.chol;

import org.ejml.data.CMatrixRMaj;
import org.ejml.data.Complex_F32;
import org.ejml.dense.row.decompose.UtilDecompositons_CDRM;
import org.ejml.interfaces.decomposition.CholeskyDecomposition_F32;
import org.jetbrains.annotations.Nullable;

public abstract class CholeskyDecompositionCommon_CDRM
implements CholeskyDecomposition_F32<CMatrixRMaj> {
    protected int n;
    protected CMatrixRMaj T;
    protected float[] t;
    protected boolean lower;
    protected Complex_F32 det = new Complex_F32();

    protected CholeskyDecompositionCommon_CDRM(boolean lower) {
        this.lower = lower;
    }

    @Override
    public boolean isLower() {
        return this.lower;
    }

    @Override
    public boolean decompose(CMatrixRMaj mat) {
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
    public CMatrixRMaj getT(@Nullable CMatrixRMaj T) {
        if (this.lower) {
            T = UtilDecompositons_CDRM.checkZerosUT(T, this.n, this.n);
            for (int i = 0; i < this.n; ++i) {
                int index = i * this.n * 2;
                for (int j = 0; j <= i; ++j) {
                    T.data[index] = this.T.data[index];
                    T.data[++index] = this.T.data[index];
                    ++index;
                }
            }
        } else {
            T = UtilDecompositons_CDRM.checkZerosLT(T, this.n, this.n);
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

    public CMatrixRMaj _getT() {
        return this.T;
    }

    @Override
    public Complex_F32 computeDeterminant() {
        float prod = 1.0f;
        int total = this.n * this.n * 2;
        for (int i = 0; i < total; i += 2 * (this.n + 1)) {
            prod *= this.t[i];
        }
        this.det.real = prod * prod;
        this.det.imaginary = 0.0f;
        return this.det;
    }
}

