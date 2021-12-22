/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.dense.row.decompose.lu;

import org.ejml.UtilEjml;
import org.ejml.data.Complex_F64;
import org.ejml.data.IGrowArray;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.dense.row.SpecializedOps_ZDRM;
import org.ejml.dense.row.decompose.TriangularSolver_ZDRM;
import org.ejml.dense.row.decompose.UtilDecompositons_ZDRM;
import org.ejml.interfaces.decomposition.LUDecomposition_F64;
import org.jetbrains.annotations.Nullable;

public abstract class LUDecompositionBase_ZDRM
implements LUDecomposition_F64<ZMatrixRMaj> {
    protected ZMatrixRMaj LU;
    protected int maxWidth = -1;
    protected int m;
    protected int n;
    protected int stride;
    protected double[] dataLU;
    protected double[] vv;
    protected int[] indx;
    protected int[] pivot;
    protected double pivsign;
    protected Complex_F64 det = new Complex_F64();

    public void setExpectedMaxSize(int numRows, int numCols) {
        this.LU = new ZMatrixRMaj(numRows, numCols);
        this.dataLU = this.LU.data;
        this.maxWidth = Math.max(numRows, numCols);
        this.vv = new double[this.maxWidth * 2];
        this.indx = new int[this.maxWidth];
        this.pivot = new int[this.maxWidth];
    }

    public ZMatrixRMaj getLU() {
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
    public ZMatrixRMaj getLower(@Nullable ZMatrixRMaj lower) {
        double imaginary;
        double real;
        int indexL;
        int indexLU;
        int j;
        int i;
        int numRows = this.LU.numRows;
        int numCols = this.LU.numRows < this.LU.numCols ? this.LU.numRows : this.LU.numCols;
        lower = UtilDecompositons_ZDRM.checkZerosUT(lower, numRows, numCols);
        for (i = 0; i < numCols; ++i) {
            lower.set(i, i, 1.0, 0.0);
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
    public ZMatrixRMaj getUpper(@Nullable ZMatrixRMaj upper) {
        int numRows = this.LU.numRows < this.LU.numCols ? this.LU.numRows : this.LU.numCols;
        int numCols = this.LU.numCols;
        upper = UtilDecompositons_ZDRM.checkZerosLT(upper, numRows, numCols);
        for (int i = 0; i < numRows; ++i) {
            for (int j = i; j < numCols; ++j) {
                int indexLU = this.LU.getIndex(i, j);
                int indexU = upper.getIndex(i, j);
                double real = this.LU.data[indexLU];
                double imaginary = this.LU.data[indexLU + 1];
                upper.data[indexU] = real;
                upper.data[indexU + 1] = imaginary;
            }
        }
        return upper;
    }

    @Override
    public ZMatrixRMaj getRowPivot(@Nullable ZMatrixRMaj pivot) {
        return SpecializedOps_ZDRM.pivotMatrix(pivot, this.pivot, this.LU.numRows, false);
    }

    @Override
    public int[] getRowPivotV(@Nullable IGrowArray pivot) {
        return UtilEjml.pivotVector(this.pivot, this.LU.numRows, pivot);
    }

    protected void decomposeCommonInit(ZMatrixRMaj a) {
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
        this.pivsign = 1.0;
    }

    @Override
    public boolean isSingular() {
        for (int i = 0; i < this.m; ++i) {
            double real = this.dataLU[i * this.stride + i * 2];
            double imaginary = this.dataLU[i * this.stride + i * 2 + 1];
            double mag2 = real * real + imaginary * imaginary;
            if (!(mag2 < UtilEjml.EPS * UtilEjml.EPS)) continue;
            return true;
        }
        return false;
    }

    @Override
    public Complex_F64 computeDeterminant() {
        if (this.m != this.n) {
            throw new IllegalArgumentException("Must be a square matrix.");
        }
        double realRet = this.pivsign;
        double realImg = 0.0;
        int total = this.m * this.stride;
        for (int i = 0; i < total; i += this.stride + 2) {
            double real = this.dataLU[i];
            double imaginary = this.dataLU[i + 1];
            double r = realRet * real - realImg * imaginary;
            double t = realRet * imaginary + realImg * real;
            realRet = r;
            realImg = t;
        }
        this.det.setTo(realRet, realImg);
        return this.det;
    }

    public double quality() {
        return SpecializedOps_ZDRM.qualityTriangular(this.LU);
    }

    public void _solveVectorInternal(double[] vv) {
        this.solveL(vv);
        TriangularSolver_ZDRM.solveU(this.dataLU, vv, this.n);
    }

    protected void solveL(double[] vv) {
        int ii = 0;
        for (int i = 0; i < this.n; ++i) {
            int ip = this.indx[i];
            double sumReal = vv[ip * 2];
            double sumImg = vv[ip * 2 + 1];
            vv[ip * 2] = vv[i * 2];
            vv[ip * 2 + 1] = vv[i * 2 + 1];
            if (ii != 0) {
                int index = i * this.stride + (ii - 1) * 2;
                for (int j = ii - 1; j < i; ++j) {
                    double luReal = this.dataLU[index++];
                    double luImg = this.dataLU[index++];
                    double vvReal = vv[j * 2];
                    double vvImg = vv[j * 2 + 1];
                    sumReal -= luReal * vvReal - luImg * vvImg;
                    sumImg -= luReal * vvImg + luImg * vvReal;
                }
            } else if (sumReal * sumReal + sumImg * sumImg != 0.0) {
                ii = i + 1;
            }
            vv[i * 2] = sumReal;
            vv[i * 2 + 1] = sumImg;
        }
    }

    public double[] _getVV() {
        return this.vv;
    }
}

