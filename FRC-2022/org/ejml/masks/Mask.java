/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.masks;

import org.ejml.MatrixDimensionException;
import org.ejml.data.Matrix;

public abstract class Mask {
    public final boolean negated;

    protected Mask(boolean negated) {
        this.negated = negated;
    }

    public abstract boolean isSet(int var1, int var2);

    public abstract boolean isSet(int var1);

    protected abstract int getNumCols();

    protected abstract int getNumRows();

    public void print() {
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < this.getNumRows(); ++row) {
            for (int col = 0; col < this.getNumCols(); ++col) {
                result.append(this.isSet(row, col) ? "+ " : "- ");
            }
            result.append(System.lineSeparator());
        }
        System.out.println(result);
    }

    public abstract void setIndexColumn(int var1);

    public void compatible(Matrix matrix) {
        if (matrix.getNumCols() != this.getNumCols() || matrix.getNumRows() != this.getNumRows()) {
            throw new MatrixDimensionException(String.format("Mask of (%d, %d) cannot be applied for matrix (%d, %d)", this.getNumRows(), this.getNumCols(), matrix.getNumCols(), matrix.getNumCols()));
        }
    }

    public abstract int maxMaskedEntries();

    public boolean isNegated() {
        return this.negated;
    }
}

