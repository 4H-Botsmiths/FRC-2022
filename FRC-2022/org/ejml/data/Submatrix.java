/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.Matrix;

public abstract class Submatrix<M extends Matrix> {
    public M original;
    public int row0;
    public int col0;
    public int row1;
    public int col1;

    public void set(M original, int row0, int row1, int col0, int col1) {
        this.original = original;
        this.row0 = row0;
        this.col0 = col0;
        this.row1 = row1;
        this.col1 = col1;
    }

    public void set(M original) {
        this.original = original;
        this.row1 = original.getNumRows();
        this.col1 = original.getNumCols();
    }

    public int getRows() {
        return this.row1 - this.row0;
    }

    public int getCols() {
        return this.col1 - this.col0;
    }

    public abstract void print();
}

