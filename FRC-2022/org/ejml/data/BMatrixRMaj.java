/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import java.util.Arrays;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.data.ReshapeMatrix;

public class BMatrixRMaj
implements ReshapeMatrix {
    public boolean[] data;
    public int numRows;
    public int numCols;

    public BMatrixRMaj(int numRows, int numCols) {
        this.data = new boolean[numRows * numCols];
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public int getNumElements() {
        return this.numRows * this.numCols;
    }

    public int getIndex(int row, int col) {
        return row * this.numCols + col;
    }

    public void fill(boolean value) {
        Arrays.fill(this.data, 0, this.getNumElements(), value);
    }

    public boolean get(int index) {
        return this.data[index];
    }

    public boolean get(int row, int col) {
        if (!this.isInBounds(row, col)) {
            throw new IllegalArgumentException("Out of matrix bounds. " + row + " " + col);
        }
        return this.data[row * this.numCols + col];
    }

    public void set(int row, int col, boolean value) {
        if (!this.isInBounds(row, col)) {
            throw new IllegalArgumentException("Out of matrix bounds. " + row + " " + col);
        }
        this.data[row * this.numCols + col] = value;
    }

    public boolean unsafe_get(int row, int col) {
        return this.data[row * this.numCols + col];
    }

    public void unsafe_set(int row, int col, boolean value) {
        this.data[row * this.numCols + col] = value;
    }

    public boolean isInBounds(int row, int col) {
        return col >= 0 && col < this.numCols && row >= 0 && row < this.numRows;
    }

    public int sum() {
        int total = 0;
        int N = this.getNumElements();
        for (int i = 0; i < N; ++i) {
            if (!this.data[i]) continue;
            ++total;
        }
        return total;
    }

    @Override
    public void reshape(int numRows, int numCols) {
        int N = numRows * numCols;
        if (this.data.length < N) {
            this.data = new boolean[N];
        }
        this.numRows = numRows;
        this.numCols = numCols;
    }

    @Override
    public int getNumRows() {
        return this.numRows;
    }

    @Override
    public int getNumCols() {
        return this.numCols;
    }

    @Override
    public void zero() {
        Arrays.fill(this.data, 0, this.getNumElements(), false);
    }

    @Override
    public <T extends Matrix> T copy() {
        BMatrixRMaj ret = new BMatrixRMaj(this.numRows, this.numCols);
        ret.setTo(this);
        return (T)ret;
    }

    @Override
    public void setTo(Matrix original) {
        BMatrixRMaj orig = (BMatrixRMaj)original;
        this.reshape(original.getNumRows(), original.getNumCols());
        System.arraycopy(orig.data, 0, this.data, 0, orig.getNumElements());
    }

    @Override
    public void print() {
        System.out.println("Type = binary , numRows = " + this.numRows + " , numCols = " + this.numCols);
        for (int row = 0; row < this.numRows; ++row) {
            for (int col = 0; col < this.numCols; ++col) {
                if (this.get(row, col)) {
                    System.out.print("+");
                    continue;
                }
                System.out.print("-");
            }
            System.out.println();
        }
    }

    @Override
    public void print(String format) {
        this.print();
    }

    public BMatrixRMaj createLike() {
        return new BMatrixRMaj(this.numRows, this.numCols);
    }

    public BMatrixRMaj create(int numRows, int numCols) {
        return new BMatrixRMaj(numRows, numCols);
    }

    @Override
    public MatrixType getType() {
        return MatrixType.UNSPECIFIED;
    }
}

