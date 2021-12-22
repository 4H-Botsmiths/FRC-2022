/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decomposition.lu;

import org.ejml.UtilEjml;
import org.ejml.data.Complex_F32;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.IGrowArray;
import org.ejml.dense.row.SpecializedOps_FDRM;
import org.ejml.dense.row.decomposition.TriangularSolver_FDRM;
import org.ejml.dense.row.decomposition.UtilDecompositons_FDRM;
import org.ejml.interfaces.decomposition.LUDecomposition_F32;
import org.jetbrains.annotations.Nullable;

public abstract class LUDecompositionBase_FDRM
implements LUDecomposition_F32<FMatrixRMaj> {
    protected FMatrixRMaj LU;
    protected int maxWidth = -1;
    protected int m;
    protected int n;
    protected float[] dataLU;
    protected float[] vv;
    protected int[] indx;
    protected int[] pivot;
    protected float pivsign;
    Complex_F32 det = new Complex_F32();

    public void setExpectedMaxSize(int numRows, int numCols) {
        this.LU = new FMatrixRMaj(numRows, numCols);
        this.dataLU = this.LU.data;
        this.maxWidth = Math.max(numRows, numCols);
        this.vv = new float[this.maxWidth];
        this.indx = new int[this.maxWidth];
        this.pivot = new int[this.maxWidth];
    }

    public FMatrixRMaj getLU() {
        return this.LU;
    }

    public int[] getIndx() {
        return this.indx;
    }

    public int[] getPivot() {
        return this.pivot;
    }

    @Override
    public boolean inputModified() {
        return false;
    }

    @Override
    public FMatrixRMaj getLower(@Nullable FMatrixRMaj lower) {
        int j;
        int i;
        int numRows = this.LU.numRows;
        int numCols = this.LU.numRows < this.LU.numCols ? this.LU.numRows : this.LU.numCols;
        lower = UtilDecompositons_FDRM.checkZerosUT(lower, numRows, numCols);
        for (i = 0; i < numCols; ++i) {
            lower.unsafe_set(i, i, 1.0f);
            for (j = 0; j < i; ++j) {
                lower.unsafe_set(i, j, this.LU.unsafe_get(i, j));
            }
        }
        if (numRows > numCols) {
            for (i = numCols; i < numRows; ++i) {
                for (j = 0; j < numCols; ++j) {
                    lower.unsafe_set(i, j, this.LU.unsafe_get(i, j));
                }
            }
        }
        return lower;
    }

    @Override
    public FMatrixRMaj getUpper(@Nullable FMatrixRMaj upper) {
        int numRows = this.LU.numRows < this.LU.numCols ? this.LU.numRows : this.LU.numCols;
        int numCols = this.LU.numCols;
        upper = UtilDecompositons_FDRM.checkZerosLT(upper, numRows, numCols);
        for (int i = 0; i < numRows; ++i) {
            for (int j = i; j < numCols; ++j) {
                upper.unsafe_set(i, j, this.LU.unsafe_get(i, j));
            }
        }
        return upper;
    }

    @Override
    public FMatrixRMaj getRowPivot(@Nullable FMatrixRMaj pivot) {
        return SpecializedOps_FDRM.pivotMatrix(pivot, this.pivot, this.LU.numRows, false);
    }

    @Override
    public int[] getRowPivotV(@Nullable IGrowArray pivot) {
        return UtilEjml.pivotVector(this.pivot, this.LU.numRows, pivot);
    }

    protected void decomposeCommonInit(FMatrixRMaj a) {
        if (a.numRows > this.maxWidth || a.numCols > this.maxWidth) {
            this.setExpectedMaxSize(a.numRows, a.numCols);
        }
        this.m = a.numRows;
        this.n = a.numCols;
        this.LU.setTo(a);
        for (int i = 0; i < this.m; ++i) {
            this.pivot[i] = i;
        }
        this.pivsign = 1.0f;
    }

    @Override
    public boolean isSingular() {
        for (int i = 0; i < this.m; ++i) {
            if (!(Math.abs(this.dataLU[i * this.n + i]) < UtilEjml.F_EPS)) continue;
            return true;
        }
        return false;
    }

    @Override
    public Complex_F32 computeDeterminant() {
        if (this.m != this.n) {
            throw new IllegalArgumentException("Must be a square matrix.");
        }
        float ret = this.pivsign;
        int total = this.m * this.n;
        for (int i = 0; i < total; i += this.n + 1) {
            ret *= this.dataLU[i];
        }
        this.det.real = ret;
        this.det.imaginary = 0.0f;
        return this.det;
    }

    public double quality() {
        return SpecializedOps_FDRM.qualityTriangular(this.LU);
    }

    public void _solveVectorInternal(float[] vv) {
        int ii = 0;
        for (int i = 0; i < this.n; ++i) {
            int ip = this.indx[i];
            float sum = vv[ip];
            vv[ip] = vv[i];
            if (ii != 0) {
                int index = i * this.n + ii - 1;
                for (int j = ii - 1; j < i; ++j) {
                    sum -= this.dataLU[index++] * vv[j];
                }
            } else if (sum != 0.0f) {
                ii = i + 1;
            }
            vv[i] = sum;
        }
        TriangularSolver_FDRM.solveU(this.dataLU, vv, this.n);
    }

    public float[] _getVV() {
        return this.vv;
    }
}

