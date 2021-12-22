/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.decomposition.lu;

import org.ejml.UtilEjml;
import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.interfaces.decomposition.LUSparseDecomposition_F64;
import org.ejml.sparse.ComputePermutation;
import org.ejml.sparse.csc.CommonOps_DSCC;
import org.ejml.sparse.csc.misc.ApplyFillReductionPermutation_DSCC;
import org.ejml.sparse.csc.misc.TriangularSolver_DSCC;
import org.jetbrains.annotations.Nullable;

public class LuUpLooking_DSCC
implements LUSparseDecomposition_F64<DMatrixSparseCSC> {
    private final ApplyFillReductionPermutation_DSCC applyReduce;
    private final DMatrixSparseCSC L = new DMatrixSparseCSC(0, 0, 0);
    private final DMatrixSparseCSC U = new DMatrixSparseCSC(0, 0, 0);
    private int[] pinv = new int[0];
    private double[] x = new double[0];
    private final IGrowArray gxi = new IGrowArray();
    private final IGrowArray gw = new IGrowArray();
    private boolean singular;

    public LuUpLooking_DSCC(@Nullable ComputePermutation<DMatrixSparseCSC> reduceFill) {
        this.applyReduce = new ApplyFillReductionPermutation_DSCC(reduceFill, false);
    }

    @Override
    public boolean decompose(DMatrixSparseCSC A) {
        this.initialize(A);
        return this.performLU(this.applyReduce.apply(A));
    }

    private void initialize(DMatrixSparseCSC A) {
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
            this.x = new double[m];
        }
        for (int i = 0; i < m; ++i) {
            this.pinv[i] = -1;
            this.L.col_idx[i] = 0;
        }
    }

    private boolean performLU(DMatrixSparseCSC A) {
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
            int top = TriangularSolver_DSCC.solveColB(this.L, true, A, col, this.x, this.pinv, this.gxi, w);
            int[] xi = this.gxi.data;
            int ipiv = -1;
            double a = -1.7976931348623157E308;
            for (int p = top; p < n; ++p) {
                int i = xi[p];
                if (this.pinv[i] < 0) {
                    double d;
                    double t = Math.abs(this.x[i]);
                    if (!(d > a)) continue;
                    a = t;
                    ipiv = i;
                    continue;
                }
                this.U.nz_rows[this.U.nz_length] = this.pinv[i];
                this.U.nz_values[this.U.nz_length++] = this.x[i];
            }
            if (ipiv == -1 || a <= 0.0) {
                this.singular = true;
                return false;
            }
            double pivot = this.x[ipiv];
            this.U.nz_rows[this.U.nz_length] = k;
            this.U.nz_values[this.U.nz_length++] = pivot;
            this.pinv[ipiv] = k;
            this.L.nz_rows[this.L.nz_length] = ipiv;
            this.L.nz_values[this.L.nz_length++] = 1.0;
            for (int p = top; p < n; ++p) {
                int i = xi[p];
                if (this.pinv[i] < 0) {
                    this.L.nz_rows[this.L.nz_length] = i;
                    this.L.nz_values[this.L.nz_length++] = this.x[i] / pivot;
                }
                this.x[i] = 0.0;
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
    public Complex_F64 computeDeterminant() {
        double value = UtilEjml.permutationSign(this.pinv, this.U.numCols, this.gw.data);
        for (int i = 0; i < this.U.numCols; ++i) {
            value *= this.U.nz_values[this.U.col_idx[i + 1] - 1];
        }
        return new Complex_F64(value, 0.0);
    }

    @Override
    public DMatrixSparseCSC getLower(@Nullable DMatrixSparseCSC lower) {
        if (lower == null) {
            lower = new DMatrixSparseCSC(1, 1, 0);
        }
        lower.setTo(this.L);
        return lower;
    }

    @Override
    public DMatrixSparseCSC getUpper(@Nullable DMatrixSparseCSC upper) {
        if (upper == null) {
            upper = new DMatrixSparseCSC(1, 1, 0);
        }
        upper.setTo(this.U);
        return upper;
    }

    @Override
    public DMatrixSparseCSC getRowPivot(@Nullable DMatrixSparseCSC pivot) {
        if (pivot == null) {
            pivot = new DMatrixSparseCSC(this.L.numRows, this.L.numRows, 0);
        }
        pivot.reshape(this.L.numRows, this.L.numRows, this.L.numRows);
        CommonOps_DSCC.permutationMatrix(this.pinv, true, this.L.numRows, pivot);
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

    public DMatrixSparseCSC getL() {
        return this.L;
    }

    public DMatrixSparseCSC getU() {
        return this.U;
    }

    public boolean isReduceFill() {
        return this.applyReduce.isApplied();
    }

    public ComputePermutation<DMatrixSparseCSC> getReduceFill() {
        ComputePermutation<DMatrixSparseCSC> ret = this.applyReduce.getFillReduce();
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

