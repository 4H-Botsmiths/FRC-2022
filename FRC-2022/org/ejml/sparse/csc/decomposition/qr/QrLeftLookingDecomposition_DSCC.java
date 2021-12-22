/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.decomposition.qr;

import java.util.Arrays;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.DScalar;
import org.ejml.data.IGrowArray;
import org.ejml.dense.row.decomposition.qr.QrHelperFunctions_DDRM;
import org.ejml.interfaces.decomposition.QRSparseDecomposition;
import org.ejml.sparse.ComputePermutation;
import org.ejml.sparse.csc.CommonOps_DSCC;
import org.ejml.sparse.csc.decomposition.qr.QrHelperFunctions_DSCC;
import org.ejml.sparse.csc.decomposition.qr.QrStructuralCounts_DSCC;
import org.ejml.sparse.csc.misc.ApplyFillReductionPermutation_DSCC;
import org.ejml.sparse.csc.mult.ImplMultiplication_DSCC;
import org.jetbrains.annotations.Nullable;

public class QrLeftLookingDecomposition_DSCC
implements QRSparseDecomposition<DMatrixSparseCSC> {
    int m;
    int n;
    int m2;
    ApplyFillReductionPermutation_DSCC applyReduce;
    DMatrixSparseCSC V = new DMatrixSparseCSC(1, 1, 0);
    DMatrixSparseCSC R = new DMatrixSparseCSC(1, 1, 0);
    double[] beta = new double[0];
    DScalar Beta = new DScalar();
    double[] x = new double[0];
    QrStructuralCounts_DSCC structure = new QrStructuralCounts_DSCC();
    int[] structureP = new int[0];
    IGrowArray gwork = new IGrowArray();
    DGrowArray gx = new DGrowArray();
    boolean singular;
    private boolean decomposed = false;
    private boolean locked = false;

    public QrLeftLookingDecomposition_DSCC(@Nullable ComputePermutation<DMatrixSparseCSC> permutation) {
        this.applyReduce = new ApplyFillReductionPermutation_DSCC(permutation, false);
        this.structure.setGwork(this.gwork);
    }

    @Override
    public boolean decompose(DMatrixSparseCSC A) {
        DMatrixSparseCSC C = this.applyReduce.apply(A);
        if (!this.decomposed || !this.locked) {
            if (!this.structure.process(C)) {
                return false;
            }
            this.initializeDecomposition(C);
        }
        this.performDecomposition(C);
        this.decomposed = true;
        return true;
    }

    private void performDecomposition(DMatrixSparseCSC A) {
        int[] w = this.gwork.data;
        int[] permCol = this.applyReduce.getArrayQ();
        int[] parent = this.structure.getParent();
        int[] leftmost = this.structure.getLeftMost();
        int[] pinv_structure = this.structure.getPinv();
        int s = this.m2;
        Arrays.fill(w, 0, this.m2, -1);
        Arrays.fill(this.x, 0, this.m2, 0.0);
        this.R.nz_length = 0;
        this.V.nz_length = 0;
        for (int k = 0; k < this.n; ++k) {
            int i;
            int p;
            this.R.col_idx[k] = this.R.nz_length;
            int p1 = this.V.col_idx[k] = this.V.nz_length;
            w[k] = k;
            this.V.nz_rows[this.V.nz_length++] = k;
            int top = this.n;
            int col = permCol != null ? permCol[k] : k;
            int idx0 = A.col_idx[col];
            int idx1 = A.col_idx[col + 1];
            for (p = idx0; p < idx1; ++p) {
                i = leftmost[A.nz_rows[p]];
                int len = 0;
                while (w[i] != k) {
                    w[s + len++] = i;
                    w[i] = k;
                    i = parent[i];
                }
                while (len > 0) {
                    w[s + --top] = w[s + --len];
                }
                i = pinv_structure[A.nz_rows[p]];
                this.x[i] = A.nz_values[p];
                if (i <= k || w[i] >= k) continue;
                this.V.nz_rows[this.V.nz_length++] = i;
                w[i] = k;
            }
            for (p = top; p < this.n; ++p) {
                i = w[s + p];
                QrHelperFunctions_DSCC.applyHouseholder(this.V, i, this.beta[i], this.x);
                this.R.nz_rows[this.R.nz_length] = i;
                this.R.nz_values[this.R.nz_length++] = this.x[i];
                this.x[i] = 0.0;
                if (parent[i] != k) continue;
                ImplMultiplication_DSCC.addRowsInAInToC(this.V, i, this.V, k, w);
            }
            for (p = p1; p < this.V.nz_length; ++p) {
                this.V.nz_values[p] = this.x[this.V.nz_rows[p]];
                this.x[this.V.nz_rows[p]] = 0.0;
            }
            this.R.nz_rows[this.R.nz_length] = k;
            double max = QrHelperFunctions_DDRM.findMax(this.V.nz_values, p1, this.V.nz_length - p1);
            if (max == 0.0) {
                this.singular = true;
                this.R.nz_values[this.R.nz_length] = 0.0;
                this.beta[k] = 0.0;
            } else {
                this.R.nz_values[this.R.nz_length] = QrHelperFunctions_DSCC.computeHouseholder(this.V.nz_values, p1, this.V.nz_length, max, this.Beta);
                this.beta[k] = this.Beta.value;
            }
            ++this.R.nz_length;
        }
        this.R.col_idx[this.n] = this.R.nz_length;
        this.V.col_idx[this.n] = this.V.nz_length;
    }

    private void initializeDecomposition(DMatrixSparseCSC A) {
        this.singular = false;
        this.m2 = this.structure.getFicticousRowCount();
        this.m = A.numRows;
        this.n = A.numCols;
        if (this.beta.length < this.n) {
            this.beta = new double[this.n];
        }
        if (this.x.length < this.m2) {
            this.x = new double[this.m2];
            this.structureP = new int[this.m2];
        }
        this.V.reshape(this.m2, this.n, this.structure.nz_in_V);
        this.R.reshape(this.m2, this.n, this.structure.nz_in_R);
    }

    @Override
    public DMatrixSparseCSC getQ(@Nullable DMatrixSparseCSC Q, boolean compact) {
        if (Q == null) {
            Q = new DMatrixSparseCSC(1, 1, 0);
        }
        if (compact) {
            Q.reshape(this.V.numRows, this.n, 0);
        } else {
            Q.reshape(this.V.numRows, this.m, 0);
        }
        DMatrixSparseCSC I = CommonOps_DSCC.identity(this.V.numRows, Q.numCols);
        for (int i = this.V.numCols - 1; i >= 0; --i) {
            QrHelperFunctions_DSCC.rank1UpdateMultR(this.V, i, this.beta[i], I, Q, this.gwork, this.gx);
            I.setTo(Q);
        }
        CommonOps_DSCC.permutationInverse(this.structure.pinv, this.structureP, this.V.numRows);
        CommonOps_DSCC.permuteRowInv(this.structureP, Q, I);
        if (this.V.numRows > this.m) {
            CommonOps_DSCC.extractRows(I, 0, this.m, Q);
        } else {
            Q.setTo(I);
        }
        return Q;
    }

    @Override
    public DMatrixSparseCSC getR(@Nullable DMatrixSparseCSC R, boolean compact) {
        if (R == null) {
            R = new DMatrixSparseCSC(0, 0, 0);
        }
        R.setTo(this.R);
        if (this.m > this.n) {
            R.numRows = compact ? this.n : this.m;
        } else if (this.n > this.m && this.V.numRows != this.m) {
            DMatrixSparseCSC tmp = new DMatrixSparseCSC(this.m, this.n, 0);
            CommonOps_DSCC.extractRows(R, 0, this.m, tmp);
            R.setTo(tmp);
        }
        return R;
    }

    @Override
    public boolean inputModified() {
        return false;
    }

    public IGrowArray getGwork() {
        return this.gwork;
    }

    public DGrowArray getGx() {
        return this.gx;
    }

    public QrStructuralCounts_DSCC getStructure() {
        return this.structure;
    }

    public DMatrixSparseCSC getV() {
        return this.V;
    }

    public DMatrixSparseCSC getR() {
        return this.R;
    }

    public double[] getBeta() {
        return this.beta;
    }

    public double getBeta(int index) {
        if (index >= this.n) {
            throw new IllegalArgumentException("index is out of bounds");
        }
        return this.beta[index];
    }

    public int[] getFillPermutation() {
        int[] ret = this.applyReduce.getArrayP();
        if (ret == null) {
            throw new RuntimeException("No permutation. Should have called isFillPermuted()");
        }
        return ret;
    }

    public boolean isFillPermutated() {
        return this.applyReduce.isApplied();
    }

    public boolean isSingular() {
        return this.singular;
    }

    @Override
    public void setStructureLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean isStructureLocked() {
        return this.locked;
    }
}

