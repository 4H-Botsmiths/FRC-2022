/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decompose.lu;

import org.ejml.UtilEjml;
import org.ejml.data.CMatrixRMaj;
import org.ejml.data.Complex_F32;
import org.ejml.data.IGrowArray;
import org.ejml.dense.row.SpecializedOps_CDRM;
import org.ejml.dense.row.decompose.TriangularSolver_CDRM;
import org.ejml.dense.row.decompose.UtilDecompositons_CDRM;
import org.ejml.interfaces.decomposition.LUDecomposition_F32;
import org.jetbrains.annotations.Nullable;

public abstract class LUDecompositionBase_CDRM
implements LUDecomposition_F32<CMatrixRMaj> {
    protected CMatrixRMaj LU;
    protected int maxWidth = -1;
    protected int m;
    protected int n;
    protected int stride;
    protected float[] dataLU;
    protected float[] vv;
    protected int[] indx;
    protected int[] pivot;
    protected float pivsign;
    protected Complex_F32 det = new Complex_F32();

    public void setExpectedMaxSize(int numRows, int numCols) {
        this.LU = new CMatrixRMaj(numRows, numCols);
        this.dataLU = this.LU.data;
        this.maxWidth = Math.max(numRows, numCols);
        this.vv = new float[this.maxWidth * 2];
        this.indx = new int[this.maxWidth];
        this.pivot = new int[this.maxWidth];
    }

    public CMatrixRMaj getLU() {
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
    public CMatrixRMaj getLower(@Nullable CMatrixRMaj lower) {
        float imaginary;
        float real;
        int indexL;
        int indexLU;
        int j;
        int i;
        int numRows = this.LU.numRows;
        int numCols = this.LU.numRows < this.LU.numCols ? this.LU.numRows : this.LU.numCols;
        lower = UtilDecompositons_CDRM.checkZerosUT(lower, numRows, numCols);
        for (i = 0; i < numCols; ++i) {
            lower.set(i, i, 1.0f, 0.0f);
            for (j = 0; j < i; ++j) {
                indexLU = this.LU.getIndex(i, j);
                indexL = lower.getIndex(i, j);
                real = this.LU.data[indexLU];
                imaginary = this.LU.data[indexLU + 1];
                lower.data[indexL] = real;
                lower.data[indexL + 1] = imaginary;
            }
        }
        if (numRows > numCols) {
            for (i = numCols; i < numRows; ++i) {
                for (j = 0; j < numCols; ++j) {
                    indexLU = this.LU.getIndex(i, j);
                    indexL = lower.getIndex(i, j);
                    real = this.LU.data[indexLU];
                    imaginary = this.LU.data[indexLU + 1];
                    lower.data[indexL] = real;
                    lower.data[indexL + 1] = imaginary;
                }
            }
        }
        return lower;
    }

    @Override
    public CMatrixRMaj getUpper(@Nullable CMatrixRMaj upper) {
        int numRows = this.LU.numRows < this.LU.numCols ? this.LU.numRows : this.LU.numCols;
        int numCols = this.LU.numCols;
        upper = UtilDecompositons_CDRM.checkZerosLT(upper, numRows, numCols);
        for (int i = 0; i < numRows; ++i) {
            for (int j = i; j < numCols; ++j) {
                int indexLU = this.LU.getIndex(i, j);
                int indexU = upper.getIndex(i, j);
                float real = this.LU.data[indexLU];
                float imaginary = this.LU.data[indexLU + 1];
                upper.data[indexU] = real;
                upper.data[indexU + 1] = imaginary;
            }
        }
        return upper;
    }

    @Override
    public CMatrixRMaj getRowPivot(@Nullable CMatrixRMaj pivot) {
        return SpecializedOps_CDRM.pivotMatrix(pivot, this.pivot, this.LU.numRows, false);
    }

    @Override
    public int[] getRowPivotV(@Nullable IGrowArray pivot) {
        return UtilEjml.pivotVector(this.pivot, this.LU.numRows, pivot);
    }

    protected void decomposeCommonInit(CMatrixRMaj a) {
        if (a.numRows > this.maxWidth || a.numCols > this.maxWidth) {
            this.setExpectedMaxSize(a.numRows, a.numCols);
        }
        this.m = a.numRows;
        this.n = a.numCols;
        this.stride = this.n * 2;
        this.LU.setTo(a);
        for (int i = 0; i < this.m; ++i) {
            this.pivot[i] = i;
        }
        this.pivsign = 1.0f;
    }

    @Override
    public boolean isSingular() {
        for (int i = 0; i < this.m; ++i) {
            float real = this.dataLU[i * this.stride + i * 2];
            float imaginary = this.dataLU[i * this.stride + i * 2 + 1];
            float mag2 = real * real + imaginary * imaginary;
            if (!(mag2 < UtilEjml.F_EPS * UtilEjml.F_EPS)) continue;
            return true;
        }
        return false;
    }

    @Override
    public Complex_F32 computeDeterminant() {
        if (this.m != this.n) {
            throw new IllegalArgumentException("Must be a square matrix.");
        }
        float realRet = this.pivsign;
        float realImg = 0.0f;
        int total = this.m * this.stride;
        for (int i = 0; i < total; i += this.stride + 2) {
            float real = this.dataLU[i];
            float imaginary = this.dataLU[i + 1];
            float r = realRet * real - realImg * imaginary;
            float t = realRet * imaginary + realImg * real;
            realRet = r;
            realImg = t;
        }
        this.det.setTo(realRet, realImg);
        return this.det;
    }

    public double quality() {
        return SpecializedOps_CDRM.qualityTriangular(this.LU);
    }

    public void _solveVectorInternal(float[] vv) {
        this.solveL(vv);
        TriangularSolver_CDRM.solveU(this.dataLU, vv, this.n);
    }

    protected void solveL(float[] vv) {
        int ii = 0;
        for (int i = 0; i < this.n; ++i) {
            int ip = this.indx[i];
            float sumReal = vv[ip * 2];
            float sumImg = vv[ip * 2 + 1];
            vv[ip * 2] = vv[i * 2];
            vv[ip * 2 + 1] = vv[i * 2 + 1];
            if (ii != 0) {
                int index = i * this.stride + (ii - 1) * 2;
                for (int j = ii - 1; j < i; ++j) {
                    float luReal = this.dataLU[index++];
                    float luImg = this.dataLU[index++];
                    float vvReal = vv[j * 2];
                    float vvImg = vv[j * 2 + 1];
                    sumReal -= luReal * vvReal - luImg * vvImg;
                    sumImg -= luReal * vvImg + luImg * vvReal;
                }
            } else if (sumReal * sumReal + sumImg * sumImg != 0.0f) {
                ii = i + 1;
            }
            vv[i * 2] = sumReal;
            vv[i * 2 + 1] = sumImg;
        }
    }

    public float[] _getVV() {
        return this.vv;
    }
}

