/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import java.util.Iterator;
import org.ejml.data.DMatrixD1;

public class DMatrixIterator
implements Iterator<Double> {
    private DMatrixD1 a;
    private boolean rowMajor;
    private int minCol;
    private int minRow;
    private int index = 0;
    private int size;
    private int submatrixStride;
    int subRow;
    int subCol;

    public DMatrixIterator(DMatrixD1 a, boolean rowMajor, int minRow, int minCol, int maxRow, int maxCol) {
        if (maxCol < minCol) {
            throw new IllegalArgumentException("maxCol has to be more than or equal to minCol");
        }
        if (maxRow < minRow) {
            throw new IllegalArgumentException("maxRow has to be more than or equal to minCol");
        }
        if (maxCol >= a.numCols) {
            throw new IllegalArgumentException("maxCol must be < numCols");
        }
        if (maxRow >= a.numRows) {
            throw new IllegalArgumentException("maxRow must be < numCRows");
        }
        this.a = a;
        this.rowMajor = rowMajor;
        this.minCol = minCol;
        this.minRow = minRow;
        this.size = (maxCol - minCol + 1) * (maxRow - minRow + 1);
        this.submatrixStride = rowMajor ? maxCol - minCol + 1 : maxRow - minRow + 1;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.size;
    }

    @Override
    public Double next() {
        if (this.rowMajor) {
            this.subRow = this.index / this.submatrixStride;
            this.subCol = this.index % this.submatrixStride;
        } else {
            this.subRow = this.index % this.submatrixStride;
            this.subCol = this.index / this.submatrixStride;
        }
        ++this.index;
        return this.a.get(this.subRow + this.minRow, this.subCol + this.minCol);
    }

    @Override
    public void remove() {
        throw new RuntimeException("Operation not supported");
    }

    public int getIndex() {
        return this.index - 1;
    }

    public boolean isRowMajor() {
        return this.rowMajor;
    }

    public void set(double value) {
        this.a.set(this.subRow + this.minRow, this.subCol + this.minCol, value);
    }
}

