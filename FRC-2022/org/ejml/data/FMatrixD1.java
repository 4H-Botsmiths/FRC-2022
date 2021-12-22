/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixIterator;
import org.ejml.data.ReshapeMatrix;
import org.ejml.ops.MatrixIO;

public abstract class FMatrixD1
implements ReshapeMatrix,
FMatrix {
    public float[] data = UtilEjml.ZERO_LENGTH_F32;
    public int numRows;
    public int numCols;

    public float[] getData() {
        return this.data;
    }

    public void setData(float[] data) {
        this.data = data;
    }

    public abstract int getIndex(int var1, int var2);

    public void setTo(FMatrixD1 b) {
        this.reshape(b.numRows, b.numCols);
        int dataLength = b.getNumElements();
        System.arraycopy(b.data, 0, this.data, 0, dataLength);
    }

    public float get(int index) {
        return this.data[index];
    }

    public float set(int index, float val) {
        this.data[index] = val;
        return this.data[index];
    }

    public float plus(int index, float val) {
        int n = index;
        float f = this.data[n] + val;
        this.data[n] = f;
        return f;
    }

    public float minus(int index, float val) {
        int n = index;
        float f = this.data[n] - val;
        this.data[n] = f;
        return f;
    }

    public float times(int index, float val) {
        int n = index;
        float f = this.data[n] * val;
        this.data[n] = f;
        return f;
    }

    public float div(int index, float val) {
        int n = index;
        float f = this.data[n] / val;
        this.data[n] = f;
        return f;
    }

    public abstract void reshape(int var1, int var2, boolean var3);

    @Override
    public void reshape(int numRows, int numCols) {
        this.reshape(numRows, numCols, false);
    }

    public FMatrixIterator iterator(boolean rowMajor, int minRow, int minCol, int maxRow, int maxCol) {
        return new FMatrixIterator(this, rowMajor, minRow, minCol, maxRow, maxCol);
    }

    @Override
    public void print() {
        MatrixIO.printFancy(System.out, this, 11);
    }

    @Override
    public void print(String format) {
        MatrixIO.print(System.out, this, format);
    }

    @Override
    public int getNumRows() {
        return this.numRows;
    }

    @Override
    public int getNumCols() {
        return this.numCols;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }
}

