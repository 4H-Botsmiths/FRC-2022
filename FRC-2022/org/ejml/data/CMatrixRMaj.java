/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import java.util.Arrays;
import org.ejml.UtilEjml;
import org.ejml.data.CMatrix;
import org.ejml.data.CMatrixD1;
import org.ejml.data.Complex_F32;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.ops.MatrixIO;

public class CMatrixRMaj
extends CMatrixD1 {
    public CMatrixRMaj(float[][] data) {
        this.numRows = data.length;
        this.numCols = data[0].length / 2;
        UtilEjml.checkTooLargeComplex(this.numRows, this.numCols);
        this.data = new float[this.numRows * this.numCols * 2];
        for (int i = 0; i < this.numRows; ++i) {
            float[] row = data[i];
            if (row.length != this.numCols * 2) {
                throw new IllegalArgumentException("Unexpected row size in input data at row " + i);
            }
            System.arraycopy(row, 0, this.data, i * this.numCols * 2, row.length);
        }
    }

    public CMatrixRMaj(int numRows, int numCols, boolean rowMajor, float ... data) {
        if (data.length != numRows * numCols * 2) {
            throw new RuntimeException("Unexpected length for data");
        }
        this.data = new float[numRows * numCols * 2];
        this.numRows = numRows;
        this.numCols = numCols;
        this.setTo(numRows, numCols, rowMajor, data);
    }

    public CMatrixRMaj(CMatrixRMaj original) {
        this(original.numRows, original.numCols);
        this.setTo(original);
    }

    public CMatrixRMaj(int numRows, int numCols) {
        UtilEjml.checkTooLargeComplex(numRows, numCols);
        this.numRows = numRows;
        this.numCols = numCols;
        this.data = new float[numRows * numCols * 2];
    }

    @Override
    public int getIndex(int row, int col) {
        return row * this.numCols * 2 + col * 2;
    }

    @Override
    public void reshape(int numRows, int numCols) {
        UtilEjml.checkTooLargeComplex(numRows, numCols);
        int newLength = numRows * numCols * 2;
        if (newLength > this.data.length) {
            this.data = new float[newLength];
        }
        this.numRows = numRows;
        this.numCols = numCols;
    }

    @Override
    public void get(int row, int col, Complex_F32 output) {
        int index = row * this.numCols * 2 + col * 2;
        output.real = this.data[index];
        output.imaginary = this.data[index + 1];
    }

    @Override
    public void set(int row, int col, float real, float imaginary) {
        int index = row * this.numCols * 2 + col * 2;
        this.data[index] = real;
        this.data[index + 1] = imaginary;
    }

    public float getReal(int element) {
        return this.data[element * 2];
    }

    public float getImag(int element) {
        return this.data[element * 2 + 1];
    }

    @Override
    public float getReal(int row, int col) {
        return this.data[(row * this.numCols + col) * 2];
    }

    @Override
    public void setReal(int row, int col, float val) {
        this.data[(row * this.numCols + col) * 2] = val;
    }

    @Override
    public float getImag(int row, int col) {
        return this.data[(row * this.numCols + col) * 2 + 1];
    }

    @Override
    public void setImag(int row, int col, float val) {
        this.data[(row * this.numCols + col) * 2 + 1] = val;
    }

    @Override
    public int getDataLength() {
        return this.numRows * this.numCols * 2;
    }

    public void setTo(CMatrixRMaj original) {
        this.reshape(original.numRows, original.numCols);
        int columnSize = this.numCols * 2;
        for (int y = 0; y < this.numRows; ++y) {
            int index = y * this.numCols * 2;
            System.arraycopy(original.data, index, this.data, index, columnSize);
        }
    }

    public CMatrixRMaj copy() {
        return new CMatrixRMaj(this);
    }

    @Override
    public void setTo(Matrix original) {
        this.reshape(original.getNumRows(), original.getNumCols());
        CMatrix n = (CMatrix)original;
        Complex_F32 c = new Complex_F32();
        for (int i = 0; i < this.numRows; ++i) {
            for (int j = 0; j < this.numCols; ++j) {
                n.get(i, j, c);
                this.set(i, j, c.real, c.imaginary);
            }
        }
    }

    @Override
    public void print() {
        MatrixIO.printFancy(System.out, this, 11);
    }

    @Override
    public void print(String format) {
        MatrixIO.print(System.out, this, format);
    }

    public int getRowStride() {
        return this.numCols * 2;
    }

    public void setTo(int numRows, int numCols, boolean rowMajor, float ... data) {
        this.reshape(numRows, numCols);
        int length = numRows * numCols * 2;
        if (length > data.length) {
            throw new RuntimeException("Passed in array not long enough");
        }
        if (rowMajor) {
            System.arraycopy(data, 0, this.data, 0, length);
        } else {
            int index = 0;
            int stride = numRows * 2;
            for (int i = 0; i < numRows; ++i) {
                for (int j = 0; j < numCols; ++j) {
                    this.data[index++] = data[j * stride + i * 2];
                    this.data[index++] = data[j * stride + i * 2 + 1];
                }
            }
        }
    }

    @Override
    public void zero() {
        Arrays.fill(this.data, 0, this.numCols * this.numRows * 2, 0.0f);
    }

    public CMatrixRMaj createLike() {
        return new CMatrixRMaj(this.numRows, this.numCols);
    }

    public CMatrixRMaj create(int numRows, int numCols) {
        return new CMatrixRMaj(numRows, numCols);
    }

    @Override
    public MatrixType getType() {
        return MatrixType.CDRM;
    }
}

