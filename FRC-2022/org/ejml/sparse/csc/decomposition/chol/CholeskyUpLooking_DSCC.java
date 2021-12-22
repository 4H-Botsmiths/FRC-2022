/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.sparse.csc.decomposition.chol;

import org.ejml.UtilEjml;
import org.ejml.data.Complex_F64;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.interfaces.decomposition.CholeskySparseDecomposition_F64;
import org.ejml.sparse.csc.misc.ColumnCounts_DSCC;
import org.ejml.sparse.csc.misc.TriangularSolver_DSCC;
import org.jetbrains.annotations.Nullable;

public class CholeskyUpLooking_DSCC
implements CholeskySparseDecomposition_F64<DMatrixSparseCSC> {
    private int N;
    DMatrixSparseCSC L = new DMatrixSparseCSC(1, 1, 0);
    IGrowArray gw = new IGrowArray(1);
    IGrowArray gs = new IGrowArray(1);
    DGrowArray gx = new DGrowArray(1);
    int[] parent = new int[1];
    int[] post = new int[1];
    int[] counts = new int[1];
    ColumnCounts_DSCC columnCounter = new ColumnCounts_DSCC(false);
    private boolean decomposed = false;
    private boolean locked = false;

    @Override
    public boolean decompose(DMatrixSparseCSC orig) {
        if (orig.numCols != orig.numRows) {
            throw new IllegalArgumentException("Must be a square matrix");
        }
        if (!this.locked || !this.decomposed) {
            this.performSymbolic(orig);
        }
        if (this.performDecomposition(orig)) {
            this.decomposed = true;
            return true;
        }
        return false;
    }

    public void performSymbolic(DMatrixSparseCSC A) {
        this.init(A.numCols);
        TriangularSolver_DSCC.eliminationTree(A, false, this.parent, this.gw);
        TriangularSolver_DSCC.postorder(this.parent, this.N, this.post, this.gw);
        this.columnCounter.process(A, this.parent, this.post, this.counts);
        this.L.reshape(A.numRows, A.numCols, 0);
        this.L.histogramToStructure(this.counts);
    }

    private void init(int N) {
        this.N = N;
        if (this.parent.length < N) {
            this.parent = new int[N];
            this.post = new int[N];
            this.counts = new int[N];
            this.gw.reshape(3 * N);
        }
    }

    private boolean performDecomposition(DMatrixSparseCSC A) {
        int[] c = UtilEjml.adjust(this.gw, this.N);
        int[] s = UtilEjml.adjust(this.gs, this.N);
        double[] x = UtilEjml.adjust(this.gx, this.N);
        System.arraycopy(this.L.col_idx, 0, c, 0, this.N);
        int k = 0;
        while (k < this.N) {
            int top = TriangularSolver_DSCC.searchNzRowsElim(A, k, this.parent, s, c);
            x[k] = 0.0;
            int idx0 = A.col_idx[k];
            int idx1 = A.col_idx[k + 1];
            for (int p = idx0; p < idx1; ++p) {
                if (A.nz_rows[p] > k) continue;
                x[A.nz_rows[p]] = A.nz_values[p];
            }
            double d = x[k];
            x[k] = 0.0;
            while (top < this.N) {
                int p;
                int i = s[top];
                double lki = x[i] / this.L.nz_values[this.L.col_idx[i]];
                x[i] = 0.0;
                for (p = this.L.col_idx[i] + 1; p < c[i]; ++p) {
                    int n = this.L.nz_rows[p];
                    x[n] = x[n] - this.L.nz_values[p] * lki;
                }
                d -= lki * lki;
                int n = i;
                int n2 = c[n];
                c[n] = n2 + 1;
                p = n2;
                this.L.nz_rows[p] = k;
                this.L.nz_values[p] = lki;
                ++top;
            }
            if (d <= 0.0) {
                return false;
            }
            int n = k;
            c[n] = c[n] + 1;
            this.L.nz_rows[p] = k++;
            this.L.nz_values[p] = Math.sqrt(d);
        }
        return true;
    }

    @Override
    public boolean inputModified() {
        return false;
    }

    @Override
    public boolean isLower() {
        return true;
    }

    @Override
    public DMatrixSparseCSC getT(@Nullable DMatrixSparseCSC T) {
        if (T == null) {
            T = new DMatrixSparseCSC(this.L.numRows, this.L.numCols, this.L.nz_length);
        }
        T.setTo(this.L);
        return T;
    }

    @Override
    public Complex_F64 computeDeterminant() {
        double value = 1.0;
        for (int i = 0; i < this.N; ++i) {
            value *= this.L.nz_values[this.L.col_idx[i]];
        }
        return new Complex_F64(value * value, 0.0);
    }

    public DGrowArray getGx() {
        return this.gx;
    }

    public DMatrixSparseCSC getL() {
        return this.L;
    }

    public IGrowArray getGw() {
        return this.gw;
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

