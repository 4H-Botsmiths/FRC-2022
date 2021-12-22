/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.decomposition.lu;

import org.ejml.UtilEjml;
import org.ejml.data.Complex_F32;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.interfaces.decomposition.LUSparseDecomposition_F32;
import org.ejml.sparse.ComputePermutation;
import org.ejml.sparse.csc.CommonOps_FSCC;
import org.ejml.sparse.csc.misc.ApplyFillReductionPermutation_FSCC;
import org.ejml.sparse.csc.misc.TriangularSolver_FSCC;
import org.jetbrains.annotations.Nullable;

public class LuUpLooking_FSCC
implements LUSparseDecomposition_F32<FMatrixSparseCSC> {
    private final ApplyFillReductionPermutation_FSCC applyReduce;
    private final FMatrixSparseCSC L = new FMatrixSparseCSC(0, 0, 0);
    private final FMatrixSparseCSC U = new FMatrixSparseCSC(0, 0, 0);
    private int[] pinv = new int[0];
    private float[] x = new float[0];
    private final IGrowArray gxi = new IGrowArray();
    private final IGrowArray gw = new IGrowArray();
    private boolean singular;

    public LuUpLooking_FSCC(@Nullable ComputePermutation<FMatrixSparseCSC> reduceFill) {
        this.applyReduce = new ApplyFillReductionPermutation_FSCC(reduceFill, false);
    }

    @Override
    public boolean decompose(FMatrixSparseCSC A) {
        this.initialize(A);
        return this.performLU(this.applyReduce.apply(A));
    }

    private void initialize(FMatrixSparseCSC A) {
        int m = A.numRows;
        int n = A.numCols;
        int o = Math.min(m, n);
        this.L.reshape(m, m, 4 * A.nz_length + o);
        this.L.nz_length = 0;
        this.U.reshape(m, n, 4 * A.nz_length + o);
        this.U.nz_length = 0;
        this.singular = false;
        if (this.pinv.length != m) {
            this.pinv = new int[m];
            this.x = new float[m];
        }
        for (int i = 0; i < m; ++i) {
            this.pinv[i] = -1;
            this.L.col_idx[i] = 0;
        }
    }

    private boolean performLU(FMatrixSparseCSC A) {
        int m = A.numRows;
        int n = A.numCols;
        int[] q = this.applyReduce.getArrayP();
        int[] w = UtilEjml.adjust(this.gw, m * 2, m);
        for (int k = 0; k < n; ++k) {
            this.L.col_idx[k] = this.L.nz_length;
            this.U.col_idx[k] = this.U.nz_length;
            if (this.L.nz_length + n > this.L.nz_values.length) {
                this.L.growMaxLength(2 * this.L.nz_values.length + n, true);
            }
            if (this.U.nz_length + n > this.U.nz_values.length) {
                this.U.growMaxLength(2 * this.U.nz_values.length + n, true);
            }
            int col = q != null ? q[k] : k;
            int top = TriangularSolver_FSCC.solveColB(this.L, true, A, col, this.x, this.pinv, this.gxi, w);
            int[] xi = this.gxi.data;
            int ipiv = -1;
            float a = -3.4028235E38f;
            for (int p = top; p < n; ++p) {
                int i = xi[p];
                if (this.pinv[i] < 0) {
                    float f;
                    float t = Math.abs(this.x[i]);
                    if (!(f > a)) continue;
                    a = t;
                    ipiv = i;
                    continue;
                }
                this.U.nz_rows[this.U.nz_length] = this.pinv[i];
                this.U.nz_values[this.U.nz_length++] = this.x[i];
            }
            if (ipiv == -1 || a <= 0.0f) {
                this.singular = true;
                return false;
            }
            float pivot = this.x[ipiv];
            this.U.nz_rows[this.U.nz_length] = k;
            this.U.nz_values[this.U.nz_length++] = pivot;
            this.pinv[ipiv] = k;
            this.L.nz_rows[this.L.nz_length] = ipiv;
            this.L.nz_values[this.L.nz_length++] = 1.0f;
            for (int p = top; p < n; ++p) {
                int i = xi[p];
                if (this.pinv[i] < 0) {
                    this.L.nz_rows[this.L.nz_length] = i;
                    this.L.nz_values[this.L.nz_length++] = this.x[i] / pivot;
                }
                this.x[i] = 0.0f;
            }
        }
        this.L.col_idx[n] = this.L.nz_length;
        this.U.col_idx[n] = this.U.nz_length;
        for (int p = 0; p < this.L.nz_length; ++p) {
            this.L.nz_rows[p] = this.pinv[this.L.nz_rows[p]];
        }
        return true;
    }

    @Override
    public Complex_F32 computeDeterminant() {
        float value = UtilEjml.permutationSign(this.pinv, this.U.numCols, this.gw.data);
        for (int i = 0; i < this.U.numCols; ++i) {
            value *= this.U.nz_values[this.U.col_idx[i + 1] - 1];
        }
        return new Complex_F32(value, 0.0f);
    }

    @Override
    public FMatrixSparseCSC getLower(@Nullable FMatrixSparseCSC lower) {
        if (lower == null) {
            lower = new FMatrixSparseCSC(1, 1, 0);
        }
        lower.setTo(this.L);
        return lower;
    }

    @Override
    public FMatrixSparseCSC getUpper(@Nullable FMatrixSparseCSC upper) {
        if (upper == null) {
            upper = new FMatrixSparseCSC(1, 1, 0);
        }
        upper.setTo(this.U);
        return upper;
    }

    @Override
    public FMatrixSparseCSC getRowPivot(@Nullable FMatrixSparseCSC pivot) {
        if (pivot == null) {
            pivot = new FMatrixSparseCSC(this.L.numRows, this.L.numRows, 0);
        }
        pivot.reshape(this.L.numRows, this.L.numRows, this.L.numRows);
        CommonOps_FSCC.permutationMatrix(this.pinv, true, this.L.numRows, pivot);
        return pivot;
    }

    @Override
    public int[] getRowPivotV(@Nullable IGrowArray pivot) {
        return UtilEjml.pivotVector(this.pinv, this.L.numRows, pivot);
    }

    @Override
    public boolean isSingular() {
        return this.singular;
    }

    @Override
    public boolean inputModified() {
        return false;
    }

    public IGrowArray getGxi() {
        return this.gxi;
    }

    public IGrowArray getGw() {
        return this.gw;
    }

    public int[] getPinv() {
        return this.pinv;
    }

    public FMatrixSparseCSC getL() {
        return this.L;
    }

    public FMatrixSparseCSC getU() {
        return this.U;
    }

    public boolean isReduceFill() {
        return this.applyReduce.isApplied();
    }

    public ComputePermutation<FMatrixSparseCSC> getReduceFill() {
        ComputePermutation<FMatrixSparseCSC> ret = this.applyReduce.getFillReduce();
        if (ret == null) {
            throw new RuntimeException("Check to see if there is any fill reduce ordering to apply first");
        }
        return ret;
    }

    public int[] getReducePermutation() {
        int[] ret = this.applyReduce.getArrayP();
        if (ret == null) {
            throw new RuntimeException("Check to see if there is any fill reduce ordering to apply first");
        }
        return ret;
    }

    @Override
    public void setStructureLocked(boolean locked) {
        if (locked) {
            throw new RuntimeException("Can't lock a LU decomposition. Pivots change depending on numerical values and not justthe matrix's structure");
        }
    }

    @Override
    public boolean isStructureLocked() {
        return false;
    }
}

