/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixIterator;
import org.ejml.data.ReshapeMatrix;
import org.ejml.ops.MatrixIO;

public abstract class DMatrixD1
implements ReshapeMatrix,
DMatrix {
    public double[] data = UtilEjml.ZERO_LENGTH_F64;
    public int numRows;
    public int numCols;

    public double[] getData() {
        return this.data;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    public abstract int getIndex(int var1, int var2);

    public void setTo(DMatrixD1 b) {
        this.reshape(b.numRows, b.numCols);
        int dataLength = b.getNumElements();
        System.arraycopy(b.data, 0, this.data, 0, dataLength);
    }

    public double get(int index) {
        return this.data[index];
    }

    public double set(int index, double val) {
        this.data[index] = val;
        return this.data[index];
    }

    public double plus(int index, double val) {
        int n = index;
        double d = this.data[n] + val;
        this.data[n] = d;
        return d;
    }

    public double minus(int index, double val) {
        int n = index;
        double d = this.data[n] - val;
        this.data[n] = d;
        return d;
    }

    public double times(int index, double val) {
        int n = index;
        double d = this.data[n] * val;
        this.data[n] = d;
        return d;
    }

    public double div(int index, double val) {
        int n = index;
        double d = this.data[n] / val;
        this.data[n] = d;
        return d;
    }

    public abstract void reshape(int var1, int var2, boolean var3);

    @Override
    public void reshape(int numRows, int numCols) {
        this.reshape(numRows, numCols, false);
    }

    public DMatrixIterator iterator(boolean rowMajor, int minRow, int minCol, int maxRow, int maxCol) {
        return new DMatrixIterator(this, rowMajor, minRow, minCol, maxRow, maxCol);
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

