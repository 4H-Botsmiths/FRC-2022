/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import java.util.Iterator;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixSparse;
import org.ejml.data.IGrowArray;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;

public class DMatrixSparseTriplet
implements DMatrixSparse {
    public IGrowArray nz_rowcol = new IGrowArray();
    public DGrowArray nz_value = new DGrowArray();
    public int nz_length;
    public int numRows;
    public int numCols;

    public DMatrixSparseTriplet() {
    }

    public DMatrixSparseTriplet(int numRows, int numCols, int initLength) {
        this.nz_rowcol.reshape(initLength * 2);
        this.nz_value.reshape(initLength);
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public DMatrixSparseTriplet(DMatrixSparseTriplet orig) {
        this.setTo(orig);
    }

    public void reset() {
        this.nz_length = 0;
        this.numRows = 0;
        this.numCols = 0;
    }

    @Override
    public void reshape(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.nz_length = 0;
    }

    @Override
    public void reshape(int numRows, int numCols, int arrayLength) {
        this.reshape(numRows, numCols);
        this.nz_rowcol.reshape(arrayLength * 2);
        this.nz_value.reshape(arrayLength);
    }

    public void addItem(int row, int col, double value) {
        if (this.nz_length == this.nz_value.data.length) {
            int amount = this.nz_length + 10;
            this.nz_value.growInternal(amount);
            this.nz_rowcol.growInternal(amount * 2);
        }
        this.nz_value.data[this.nz_length] = value;
        this.nz_rowcol.data[this.nz_length * 2] = row;
        this.nz_rowcol.data[this.nz_length * 2 + 1] = col;
        ++this.nz_length;
    }

    public void addItemCheck(int row, int col, double value) {
        if (row < 0 || col < 0 || row >= this.numRows || col >= this.numCols) {
            throw new IllegalArgumentException("Out of bounds. (" + row + "," + col + ") " + this.numRows + " " + this.numCols);
        }
        if (this.nz_length == this.nz_value.data.length) {
            int amount = this.nz_length + 10;
            this.nz_value.growInternal(amount);
            this.nz_rowcol.growInternal(amount * 2);
        }
        this.nz_value.data[this.nz_length] = value;
        this.nz_rowcol.data[this.nz_length * 2] = row;
        this.nz_rowcol.data[this.nz_length * 2 + 1] = col;
        ++this.nz_length;
    }

    @Override
    public void set(int row, int col, double value) {
        if (row < 0 || row >= this.numRows || col < 0 || col >= this.numCols) {
            throw new IllegalArgumentException("Outside of matrix bounds");
        }
        this.unsafe_set(row, col, value);
    }

    @Override
    public void unsafe_set(int row, int col, double value) {
        int index = this.nz_index(row, col);
        if (index < 0) {
            this.addItem(row, col, value);
        } else {
            this.nz_value.data[index] = value;
        }
    }

    @Override
    public double get(int row, int col) {
        if (row < 0 || row >= this.numRows || col < 0 || col >= this.numCols) {
            throw new IllegalArgumentException("Outside of matrix bounds");
        }
        return this.unsafe_get(row, col);
    }

    @Override
    public double get(int row, int col, double fallBackValue) {
        if (row < 0 || row >= this.numRows || col < 0 || col >= this.numCols) {
            throw new IllegalArgumentException("Outside of matrix bounds");
        }
        return this.unsafe_get(row, col, fallBackValue);
    }

    @Override
    public double unsafe_get(int row, int col) {
        int index = this.nz_index(row, col);
        if (index < 0) {
            return 0.0;
        }
        return this.nz_value.data[index];
    }

    @Override
    public double unsafe_get(int row, int col, double fallBackValue) {
        int index = this.nz_index(row, col);
        if (index < 0) {
            return fallBackValue;
        }
        return this.nz_value.data[index];
    }

    public int nz_index(int row, int col) {
        int end = this.nz_length * 2;
        for (int i = 0; i < end; i += 2) {
            int r = this.nz_rowcol.data[i];
            int c = this.nz_rowcol.data[i + 1];
            if (r != row || c != col) continue;
            return i / 2;
        }
        return -1;
    }

    public int getLength() {
        return this.nz_length;
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
    public <T extends Matrix> T copy() {
        return (T)new DMatrixSparseTriplet(this);
    }

    @Override
    public <T extends Matrix> T createLike() {
        return (T)new DMatrixSparseTriplet(this.numRows, this.numCols, this.nz_length);
    }

    @Override
    public <T extends Matrix> T create(int numRows, int numCols) {
        return (T)new DMatrixSparseTriplet(numRows, numCols, 1);
    }

    @Override
    public void setTo(Matrix original) {
        DMatrixSparseTriplet orig = (DMatrixSparseTriplet)original;
        this.reshape(orig.numRows, orig.numCols);
        this.nz_rowcol.setTo(orig.nz_rowcol);
        this.nz_value.setTo(orig.nz_value);
        this.nz_length = orig.nz_length;
    }

    @Override
    public void shrinkArrays() {
        if (this.nz_length < this.nz_value.length) {
            double[] vtmp = new double[this.nz_length];
            int[] rctmp = new int[this.nz_length * 2];
            System.arraycopy(this.nz_value.data, 0, vtmp, 0, vtmp.length);
            System.arraycopy(this.nz_rowcol.data, 0, rctmp, 0, rctmp.length);
            this.nz_value.data = vtmp;
            this.nz_rowcol.data = rctmp;
        }
    }

    @Override
    public void remove(int row, int col) {
        int where = this.nz_index(row, col);
        if (where >= 0) {
            --this.nz_length;
            for (int i = where; i < this.nz_length; ++i) {
                this.nz_value.data[i] = this.nz_value.data[i + 1];
            }
            int end = this.nz_length * 2;
            for (int i = where * 2; i < end; i += 2) {
                this.nz_rowcol.data[i] = this.nz_rowcol.data[i + 2];
                this.nz_rowcol.data[i + 1] = this.nz_rowcol.data[i + 3];
            }
        }
    }

    @Override
    public boolean isAssigned(int row, int col) {
        return this.nz_index(row, col) >= 0;
    }

    @Override
    public void zero() {
        this.nz_length = 0;
    }

    @Override
    public int getNonZeroLength() {
        return this.nz_length;
    }

    @Override
    public void print() {
        this.print("%11.4E");
    }

    @Override
    public void print(String format) {
        System.out.println("Type = " + this.getClass().getSimpleName() + " , rows = " + this.numRows + " , cols = " + this.numCols + " , nz_length = " + this.nz_length);
        for (int row = 0; row < this.numRows; ++row) {
            for (int col = 0; col < this.numCols; ++col) {
                int index = this.nz_index(row, col);
                if (index >= 0) {
                    System.out.printf(format, this.nz_value.data[index]);
                } else {
                    System.out.print("   *  ");
                }
                if (col == this.numCols - 1) continue;
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    @Override
    public void printNonZero() {
        System.out.println("Type = " + this.getClass().getSimpleName() + " , rows = " + this.numRows + " , cols = " + this.numCols + " , nz_length = " + this.nz_length);
        for (int i = 0; i < this.nz_length; ++i) {
            int row = this.nz_rowcol.data[i * 2];
            int col = this.nz_rowcol.data[i * 2 + 1];
            double value = this.nz_value.data[i];
            System.out.printf("%d %d %f\n", row, col, value);
        }
    }

    @Override
    public MatrixType getType() {
        return MatrixType.DTRIPLET;
    }

    @Override
    public Iterator<DMatrixSparse.CoordinateRealValue> createCoordinateIterator() {
        return new Iterator<DMatrixSparse.CoordinateRealValue>(){
            final DMatrixSparse.CoordinateRealValue coordinate = new DMatrixSparse.CoordinateRealValue();
            int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < DMatrixSparseTriplet.this.nz_length;
            }

            @Override
            public DMatrixSparse.CoordinateRealValue next() {
                this.coordinate.row = DMatrixSparseTriplet.this.nz_rowcol.data[this.index * 2];
                this.coordinate.col = DMatrixSparseTriplet.this.nz_rowcol.data[this.index * 2 + 1];
                this.coordinate.value = DMatrixSparseTriplet.this.nz_value.data[this.index];
                ++this.index;
                return this.coordinate;
            }
        };
    }
}

