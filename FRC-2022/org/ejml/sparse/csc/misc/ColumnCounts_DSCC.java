/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.misc;

import java.util.Arrays;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.sparse.csc.CommonOps_DSCC;

public class ColumnCounts_DSCC {
    private final boolean ata;
    private final DMatrixSparseCSC At = new DMatrixSparseCSC(1, 1, 1);
    IGrowArray gw = new IGrowArray();
    int[] w;
    int m;
    int n;
    int ancestor;
    int maxfirst;
    int prevleaf;
    int first;
    int head;
    int next;
    int jleaf;

    public ColumnCounts_DSCC(boolean ata) {
        this.ata = ata;
    }

    void initialize(DMatrixSparseCSC A) {
        this.m = A.numRows;
        this.n = A.numCols;
        int s = 4 * this.n + (this.ata ? this.n + this.m + 1 : 0);
        this.gw.reshape(s);
        this.w = this.gw.data;
        this.At.reshape(A.numCols, A.numRows, A.nz_length);
        CommonOps_DSCC.transpose(A, this.At, this.gw);
        Arrays.fill(this.w, 0, s, -1);
        this.ancestor = 0;
        this.maxfirst = this.n;
        this.prevleaf = 2 * this.n;
        this.first = 3 * this.n;
    }

    public void process(DMatrixSparseCSC A, int[] parent, int[] post, int[] counts) {
        if (counts.length < A.numCols) {
            throw new IllegalArgumentException("counts must be at least of length A.numCols");
        }
        this.initialize(A);
        int[] delta = counts;
        this.findFirstDescendant(parent, post, delta);
        if (this.ata) {
            this.init_ata(post);
        }
        for (int i = 0; i < this.n; ++i) {
            this.w[this.ancestor + i] = i;
        }
        int[] ATp = this.At.col_idx;
        int[] ATi = this.At.nz_rows;
        for (int k = 0; k < this.n; ++k) {
            int j = post[k];
            if (parent[j] != -1) {
                int n = parent[j];
                delta[n] = delta[n] - 1;
            }
            int J = this.HEAD(k, j);
            while (J != -1) {
                for (int p = ATp[J]; p < ATp[J + 1]; ++p) {
                    int i = ATi[p];
                    int q = this.isLeaf(i, j);
                    if (this.jleaf >= 1) {
                        int n = j;
                        delta[n] = delta[n] + 1;
                    }
                    if (this.jleaf != 2) continue;
                    int n = q;
                    delta[n] = delta[n] - 1;
                }
                J = this.NEXT(J);
            }
            if (parent[j] == -1) continue;
            this.w[this.ancestor + j] = parent[j];
        }
        for (int j = 0; j < this.n; ++j) {
            if (parent[j] == -1) continue;
            int n = parent[j];
            counts[n] = counts[n] + counts[j];
        }
    }

    void findFirstDescendant(int[] parent, int[] post, int[] delta) {
        for (int k = 0; k < this.n; ++k) {
            int j = post[k];
            int n = delta[j] = this.w[this.first + j] == -1 ? 1 : 0;
            while (j != -1 && this.w[this.first + j] == -1) {
                this.w[this.first + j] = k;
                j = parent[j];
            }
        }
    }

    private int HEAD(int k, int j) {
        return this.ata ? this.w[this.head + k] : j;
    }

    private int NEXT(int J) {
        return this.ata ? this.w[this.next + J] : -1;
    }

    private void init_ata(int[] post) {
        int[] ATp = this.At.col_idx;
        int[] ATi = this.At.nz_rows;
        this.head = 4 * this.n;
        this.next = 5 * this.n + 1;
        for (int k = 0; k < this.n; ++k) {
            this.w[post[k]] = k;
        }
        int i = 0;
        while (i < this.m) {
            int k = this.n;
            for (int p = ATp[i]; p < ATp[i + 1]; ++p) {
                k = Math.min(k, this.w[ATi[p]]);
            }
            this.w[this.next + i] = this.w[this.head + k];
            this.w[this.head + k] = i++;
        }
    }

    int isLeaf(int i, int j) {
        this.jleaf = 0;
        if (i <= j || this.w[this.first + j] <= this.w[this.maxfirst + i]) {
            return -1;
        }
        this.w[this.maxfirst + i] = this.w[this.first + j];
        int jprev = this.w[this.prevleaf + i];
        this.w[this.prevleaf + i] = j;
        if (jprev == -1) {
            this.jleaf = 1;
            return i;
        }
        this.jleaf = 2;
        int q = jprev;
        while (q != this.w[this.ancestor + q]) {
            q = this.w[this.ancestor + q];
        }
        int s = jprev;
        while (s != q) {
            int sparent = this.w[this.ancestor + s];
            this.w[this.ancestor + s] = q;
            s = sparent;
        }
        return q;
    }

    int[] getW() {
        return this.w;
    }
}

