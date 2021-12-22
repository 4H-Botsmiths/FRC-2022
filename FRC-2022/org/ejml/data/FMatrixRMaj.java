/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import org.ejml.UtilEjml;
import org.ejml.data.FMatrix;
import org.ejml.data.FMatrix1Row;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.ops.FConvertArrays;
import org.ejml.ops.MatrixIO;

public class FMatrixRMaj
extends FMatrix1Row {
    public FMatrixRMaj(int numRows, int numCols, boolean rowMajor, float ... data) {
        UtilEjml.checkTooLarge(numRows, numCols);
        int length = numRows * numCols;
        this.data = new float[length];
        this.numRows = numRows;
        this.numCols = numCols;
        this.set(numRows, numCols, rowMajor, data);
    }

    public FMatrixRMaj(float[][] data) {
        this(1, 1);
        this.set(data);
    }

    public FMatrixRMaj(float[] data) {
        this.data = (float[])data.clone();
        this.numRows = this.data.length;
        this.numCols = 1;
    }

    public FMatrixRMaj(int numRows, int numCols) {
        UtilEjml.checkTooLarge(numRows, numCols);
        this.data = new float[numRows * numCols];
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public FMatrixRMaj(FMatrixRMaj orig) {
        this(orig.numRows, orig.numCols);
        System.arraycopy(orig.data, 0, this.data, 0, orig.getNumElements());
    }

    public FMatrixRMaj(int length) {
        this.data = new float[length];
    }

    public FMatrixRMaj() {
    }

    public FMatrixRMaj(FMatrix mat) {
        this(mat.getNumRows(), mat.getNumCols());
        for (int i = 0; i < this.numRows; ++i) {
            for (int j = 0; j < this.numCols; ++j) {
                this.set(i, j, mat.get(i, j));
            }
        }
    }

    public static FMatrixRMaj wrap(int numRows, int numCols, float[] data) {
        UtilEjml.checkTooLarge(numRows, numCols);
        FMatrixRMaj s = new FMatrixRMaj();
        s.data = data;
        s.numRows = numRows;
        s.numCols = numCols;
        return s;
    }

    @Override
    public void reshape(int numRows, int numCols, boolean saveValues) {
        UtilEjml.checkTooLarge(numRows, numCols);
        if (this.data.length < numRows * numCols) {
            float[] d = new float[numRows * numCols];
            if (saveValues) {
                System.arraycopy(this.data, 0, d, 0, this.getNumElements());
            }
            this.data = d;
        }
        this.numRows = numRows;
        this.numCols = numCols;
    }

    @Override
    public void set(int row, int col, float value) {
        if (col < 0 || col >= this.numCols || row < 0 || row >= this.numRows) {
            throw new IllegalArgumentException("Specified element is out of bounds: (" + row + " , " + col + ")");
        }
        this.data[row * this.numCols + col] = value;
    }

    @Override
    public void unsafe_set(int row, int col, float value) {
        this.data[row * this.numCols + col] = value;
    }

    public void add(int row, int col, float value) {
        if (col < 0 || col >= this.numCols || row < 0 || row >= this.numRows) {
            throw new IllegalArgumentException("Specified element is out of bounds");
        }
        int n = row * this.numCols + col;
        this.data[n] = this.data[n] + value;
    }

    @Override
    public float get(int row, int col) {
        if (col < 0 || col >= this.numCols || row < 0 || row >= this.numRows) {
            throw new IllegalArgumentException("Specified element is out of bounds: " + row + " " + col);
        }
        return this.data[row * this.numCols + col];
    }

    @Override
    public float unsafe_get(int row, int col) {
        return this.data[row * this.numCols + col];
    }

    @Override
    public int getIndex(int row, int col) {
        return row * this.numCols + col;
    }

    public boolean isInBounds(int row, int col) {
        return col >= 0 && col < this.numCols && row >= 0 && row < this.numRows;
    }

    public void set(int numRows, int numCols, boolean rowMajor, float ... data) {
        this.reshape(numRows, numCols);
        int length = numRows * numCols;
        if (length > this.data.length) {
            throw new IllegalArgumentException("The length of this matrix's data array is too small.");
        }
        if (rowMajor) {
            System.arraycopy(data, 0, this.data, 0, length);
        } else {
            int index = 0;
            for (int i = 0; i < numRows; ++i) {
                for (int j = 0; j < numCols; ++j) {
                    this.data[index++] = data[j * numRows + i];
                }
            }
        }
    }

    @Override
    public void zero() {
        Arrays.fill(this.data, 0, this.getNumElements(), 0.0f);
    }

    public void fill(float value) {
        Arrays.fill(this.data, 0, this.getNumElements(), value);
    }

    public FMatrixRMaj copy() {
        return new FMatrixRMaj(this);
    }

    @Override
    public void setTo(Matrix original) {
        FMatrix m = (FMatrix)original;
        this.reshape(original.getNumRows(), original.getNumCols());
        if (original instanceof FMatrixRMaj) {
            System.arraycopy(((FMatrixRMaj)m).data, 0, this.data, 0, this.numRows * this.numCols);
        } else {
            int index = 0;
            for (int i = 0; i < this.numRows; ++i) {
                for (int j = 0; j < this.numCols; ++j) {
                    this.data[index++] = m.get(i, j);
                }
            }
        }
    }

    public String toString() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MatrixIO.print(new PrintStream(stream), this);
        return stream.toString();
    }

    public FMatrixRMaj createLike() {
        return new FMatrixRMaj(this.numRows, this.numCols);
    }

    public FMatrixRMaj create(int numRows, int numCols) {
        return new FMatrixRMaj(numRows, numCols);
    }

    @Override
    public MatrixType getType() {
        return MatrixType.FDRM;
    }

    public void set(float[][] input) {
        FConvertArrays.convert(input, this);
    }
}

