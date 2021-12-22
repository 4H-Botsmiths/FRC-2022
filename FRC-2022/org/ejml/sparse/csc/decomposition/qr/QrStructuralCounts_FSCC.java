/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.sparse.csc.decomposition.qr;

import java.util.Arrays;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.sparse.csc.misc.ColumnCounts_FSCC;
import org.ejml.sparse.csc.misc.TriangularSolver_FSCC;

public class QrStructuralCounts_FSCC {
    FMatrixSparseCSC A;
    int m;
    int n;
    int[] leftmost = new int[0];
    int m2;
    int[] pinv = new int[0];
    int[] parent = new int[0];
    int[] post = new int[0];
    IGrowArray gwork = new IGrowArray();
    int nz_in_V;
    int nz_in_R;
    int[] countsR = new int[0];
    int next;
    int head;
    int tail;
    int nque;
    ColumnCounts_FSCC columnCounts = new ColumnCounts_FSCC(true);

    public boolean process(FMatrixSparseCSC A) {
        this.init(A);
        TriangularSolver_FSCC.eliminationTree(A, true, this.parent, this.gwork);
        this.countNonZeroInR(this.parent);
        this.countNonZeroInV(this.parent);
        if (this.m < this.n) {
            for (int row = 0; row < this.m; ++row) {
                if (this.gwork.data[this.head + row] >= 0) continue;
                return false;
            }
        }
        return true;
    }

    void init(FMatrixSparseCSC A) {
        this.A = A;
        this.m = A.numRows;
        this.n = A.numCols;
        this.next = 0;
        this.head = this.m;
        this.tail = this.m + this.n;
        this.nque = this.m + 2 * this.n;
        if (this.parent.length < this.n || this.leftmost.length < this.m) {
            this.parent = new int[this.n];
            this.post = new int[this.n];
            this.pinv = new int[this.m + this.n];
            this.countsR = new int[this.n];
            this.leftmost = new int[this.m];
        }
        this.gwork.reshape(this.m + 3 * this.n);
    }

    void countNonZeroInR(int[] parent) {
        TriangularSolver_FSCC.postorder(parent, this.n, this.post, this.gwork);
        this.columnCounts.process(this.A, parent, this.post, this.countsR);
        this.nz_in_R = 0;
        for (int k = 0; k < this.n; ++k) {
            this.nz_in_R += this.countsR[k];
        }
        if (this.nz_in_R < 0) {
            throw new RuntimeException("Too many elements. Numerical overflow in R counts");
        }
    }

    void countNonZeroInV(int[] parent) {
        int[] w = this.gwork.data;
        this.findMinElementIndexInRows(this.leftmost);
        this.createRowElementLinkedLists(this.leftmost, w);
        this.countNonZeroUsingLinkedList(parent, w);
    }

    void countNonZeroUsingLinkedList(int[] parent, int[] ll) {
        Arrays.fill(this.pinv, 0, this.m, -1);
        this.nz_in_V = 0;
        this.m2 = this.m;
        for (int k = 0; k < this.n; ++k) {
            int i = ll[this.head + k];
            ++this.nz_in_V;
            if (i < 0) {
                i = this.m2++;
            }
            this.pinv[i] = k;
            int n = this.nque + k;
            ll[n] = ll[n] - 1;
            if (ll[n] <= 0) continue;
            this.nz_in_V += ll[this.nque + k];
            int pa = parent[k];
            if (pa == -1) continue;
            if (ll[this.nque + pa] == 0) {
                ll[this.tail + pa] = ll[this.tail + k];
            }
            ll[this.next + ll[this.tail + k]] = ll[this.head + pa];
            ll[this.head + pa] = ll[this.next + i];
            int n2 = this.nque + pa;
            ll[n2] = ll[n2] + ll[this.nque + k];
        }
        int k = this.n;
        for (int i = 0; i < this.m; ++i) {
            if (this.pinv[i] >= 0) continue;
            this.pinv[i] = k++;
        }
        if (this.nz_in_V < 0) {
            throw new RuntimeException("Too many elements. Numerical overflow in V counts");
        }
    }

    void createRowElementLinkedLists(int[] leftmost, int[] w) {
        for (int k = 0; k < this.n; ++k) {
            w[this.head + k] = -1;
            w[this.tail + k] = -1;
            w[this.nque + k] = 0;
        }
        for (int i = this.m - 1; i >= 0; --i) {
            int k = leftmost[i];
            if (k == -1) continue;
            int n = this.nque + k;
            int n2 = w[n];
            w[n] = n2 + 1;
            if (n2 == 0) {
                w[this.tail + k] = i;
            }
            w[this.next + i] = w[this.head + k];
            w[this.head + k] = i;
        }
    }

    void findMinElementIndexInRows(int[] leftmost) {
        Arrays.fill(leftmost, 0, this.m, -1);
        for (int k = this.n - 1; k >= 0; --k) {
            int idx0 = this.A.col_idx[k];
            int idx1 = this.A.col_idx[k + 1];
            for (int p = idx0; p < idx1; ++p) {
                leftmost[this.A.nz_rows[p]] = k;
            }
        }
    }

    public void setGwork(IGrowArray gwork) {
        this.gwork = gwork;
    }

    public int getFicticousRowCount() {
        return this.m2;
    }

    public int[] getLeftMost() {
        return this.leftmost;
    }

    public int[] getParent() {
        return this.parent;
    }

    public int[] getPinv() {
        return this.pinv;
    }

    public int getM2() {
        return this.m2;
    }
}

