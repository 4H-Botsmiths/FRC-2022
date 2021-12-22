/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.data;

import java.util.Arrays;
import java.util.Iterator;
import org.ejml.UtilEjml;
import org.ejml.data.FMatrixSparse;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.ops.MatrixIO;
import org.ejml.ops.SortCoupledArray_F32;
import org.jetbrains.annotations.Nullable;

public class FMatrixSparseCSC
implements FMatrixSparse {
    public float[] nz_values = UtilEjml.ZERO_LENGTH_F32;
    public int nz_length;
    public int[] nz_rows = UtilEjml.ZERO_LENGTH_I32;
    public int[] col_idx;
    public int numRows;
    public int numCols;
    public boolean indicesSorted = false;

    public FMatrixSparseCSC(int numRows, int numCols) {
        this(numRows, numCols, 0);
    }

    public FMatrixSparseCSC(int numRows, int numCols, int arrayLength) {
        if (numRows < 0 || numCols < 0 || arrayLength < 0) {
            throw new IllegalArgumentException("Rows, columns, and arrayLength must be not be negative");
        }
        this.numRows = numRows;
        this.numCols = numCols;
        this.nz_length = 0;
        this.col_idx = new int[numCols + 1];
        this.growMaxLength(arrayLength, false);
    }

    public FMatrixSparseCSC(FMatrixSparseCSC original) {
        this(original.numRows, original.numCols, original.nz_length);
        this.setTo(original);
    }

    @Override
    public int getNumRows() {
        return this.numRows;
    }

    @Override
    public int getNumCols() {
        return this.numCols;
    }

    public FMatrixSparseCSC copy() {
        return new FMatrixSparseCSC(this);
    }

    public FMatrixSparseCSC createLike() {
        return new FMatrixSparseCSC(this.numRows, this.numCols);
    }

    @Override
    public void setTo(Matrix original) {
        FMatrixSparseCSC o = (FMatrixSparseCSC)original;
        this.reshape(o.numRows, o.numCols, o.nz_length);
        this.nz_length = o.nz_length;
        System.arraycopy(o.nz_values, 0, this.nz_values, 0, this.nz_length);
        System.arraycopy(o.nz_rows, 0, this.nz_rows, 0, this.nz_length);
        System.arraycopy(o.col_idx, 0, this.col_idx, 0, this.numCols + 1);
        this.indicesSorted = o.indicesSorted;
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
    public void printNonZero() {
        String format = "%d %d %11.4E\n";
        System.out.println("Type = " + this.getType().name() + " , rows = " + this.numRows + " , cols = " + this.numCols + " , nz_length = " + this.nz_length);
        for (int col = 0; col < this.numCols; ++col) {
            int idx0 = this.col_idx[col];
            int idx1 = this.col_idx[col + 1];
            for (int i = idx0; i < idx1; ++i) {
                int row = this.nz_rows[i];
                float value = this.nz_values[i];
                System.out.printf(format, row, col, Float.valueOf(value));
            }
        }
    }

    @Override
    public boolean isAssigned(int row, int col) {
        return this.nz_index(row, col) >= 0;
    }

    @Override
    public float get(int row, int col) {
        if (row < 0 || row >= this.numRows || col < 0 || col >= this.numCols) {
            throw new IllegalArgumentException("Outside of matrix bounds");
        }
        return this.unsafe_get(row, col);
    }

    @Override
    public float get(int row, int col, float fallBackValue) {
        if (row < 0 || row >= this.numRows || col < 0 || col >= this.numCols) {
            throw new IllegalArgumentException("Outside of matrix bounds");
        }
        return this.unsafe_get(row, col, fallBackValue);
    }

    @Override
    public float unsafe_get(int row, int col) {
        int index = this.nz_index(row, col);
        if (index >= 0) {
            return this.nz_values[index];
        }
        return 0.0f;
    }

    @Override
    public float unsafe_get(int row, int col, float fallBackValue) {
        int index = this.nz_index(row, col);
        if (index >= 0) {
            return this.nz_values[index];
        }
        return fallBackValue;
    }

    public int nz_index(int row, int col) {
        int col0 = this.col_idx[col];
        int col1 = this.col_idx[col + 1];
        if (this.indicesSorted) {
            return Arrays.binarySearch(this.nz_rows, col0, col1, row);
        }
        for (int i = col0; i < col1; ++i) {
            if (this.nz_rows[i] != row) continue;
            return i;
        }
        return -1;
    }

    @Override
    public void set(int row, int col, float val) {
        if (row < 0 || row >= this.numRows || col < 0 || col >= this.numCols) {
            throw new IllegalArgumentException("Outside of matrix bounds");
        }
        this.unsafe_set(row, col, val);
    }

    @Override
    public void unsafe_set(int row, int col, float val) {
        int index = this.nz_index(row, col);
        if (index >= 0) {
            this.nz_values[index] = val;
        } else {
            int idx0 = this.col_idx[col];
            int idx1 = this.col_idx[col + 1];
            for (index = idx0; index < idx1 && row >= this.nz_rows[index]; ++index) {
            }
            int i = col + 1;
            while (i <= this.numCols) {
                int n = i++;
                this.col_idx[n] = this.col_idx[n] + 1;
            }
            if (this.nz_length >= this.nz_values.length) {
                this.growMaxLength(this.nz_length * 2 + 1, true);
            }
            for (i = this.nz_length; i > index; --i) {
                this.nz_rows[i] = this.nz_rows[i - 1];
                this.nz_values[i] = this.nz_values[i - 1];
            }
            this.nz_rows[index] = row;
            this.nz_values[index] = val;
            ++this.nz_length;
        }
    }

    @Override
    public void remove(int row, int col) {
        int index = this.nz_index(row, col);
        if (index < 0) {
            return;
        }
        int i = col + 1;
        while (i <= this.numCols) {
            int n = i++;
            this.col_idx[n] = this.col_idx[n] - 1;
        }
        --this.nz_length;
        for (i = index; i < this.nz_length; ++i) {
            this.nz_rows[i] = this.nz_rows[i + 1];
            this.nz_values[i] = this.nz_values[i + 1];
        }
    }

    @Override
    public void zero() {
        Arrays.fill(this.col_idx, 0, this.numCols + 1, 0);
        this.nz_length = 0;
        this.indicesSorted = false;
    }

    public FMatrixSparseCSC create(int numRows, int numCols) {
        return new FMatrixSparseCSC(numRows, numCols);
    }

    @Override
    public int getNonZeroLength() {
        return this.nz_length;
    }

    @Override
    public void reshape(int numRows, int numCols, int arrayLength) {
        this.indicesSorted = false;
        this.numRows = numRows;
        this.numCols = numCols;
        this.growMaxLength(arrayLength, false);
        this.nz_length = 0;
        if (numCols + 1 > this.col_idx.length) {
            this.col_idx = new int[numCols + 1];
        } else {
            Arrays.fill(this.col_idx, 0, numCols + 1, 0);
        }
    }

    @Override
    public void reshape(int numRows, int numCols) {
        this.reshape(numRows, numCols, 0);
    }

    @Override
    public void shrinkArrays() {
        if (this.nz_length < this.nz_values.length) {
            float[] tmp_values = new float[this.nz_length];
            int[] tmp_rows = new int[this.nz_length];
            System.arraycopy(this.nz_values, 0, tmp_values, 0, this.nz_length);
            System.arraycopy(this.nz_rows, 0, tmp_rows, 0, this.nz_length);
            this.nz_values = tmp_values;
            this.nz_rows = tmp_rows;
        }
    }

    public void growMaxLength(int arrayLength, boolean preserveValue) {
        if (arrayLength < 0) {
            throw new IllegalArgumentException("Negative array length. Overflow?");
        }
        if (arrayLength > this.nz_values.length) {
            float[] data = new float[arrayLength];
            int[] row_idx = new int[arrayLength];
            if (preserveValue) {
                System.arraycopy(this.nz_values, 0, data, 0, this.nz_length);
                System.arraycopy(this.nz_rows, 0, row_idx, 0, this.nz_length);
            }
            this.nz_values = data;
            this.nz_rows = row_idx;
        }
    }

    public void growMaxColumns(int desiredColumns, boolean preserveValue) {
        if (this.col_idx.length < desiredColumns + 1) {
            int[] c = new int[desiredColumns + 1];
            if (preserveValue) {
                System.arraycopy(this.col_idx, 0, c, 0, this.col_idx.length);
            }
            this.col_idx = c;
        }
    }

    public void histogramToStructure(int[] histogram) {
        this.col_idx[0] = 0;
        int index = 0;
        for (int i = 1; i <= this.numCols; ++i) {
            this.col_idx[i] = index += histogram[i - 1];
        }
        this.nz_length = index;
        this.growMaxLength(this.nz_length, false);
        if (this.col_idx[this.numCols] != this.nz_length) {
            throw new RuntimeException("Egads");
        }
    }

    public void sortIndices(@Nullable SortCoupledArray_F32 sorter) {
        if (sorter == null) {
            sorter = new SortCoupledArray_F32();
        }
        sorter.quick(this.col_idx, this.numCols + 1, this.nz_rows, this.nz_values);
        this.indicesSorted = true;
    }

    public void copyStructure(FMatrixSparseCSC orig) {
        this.reshape(orig.numRows, orig.numCols, orig.nz_length);
        this.nz_length = orig.nz_length;
        System.arraycopy(orig.col_idx, 0, this.col_idx, 0, orig.numCols + 1);
        System.arraycopy(orig.nz_rows, 0, this.nz_rows, 0, orig.nz_length);
    }

    public boolean isIndicesSorted() {
        return this.indicesSorted;
    }

    public boolean isFull() {
        return this.nz_length == this.numRows * this.numCols;
    }

    @Override
    public MatrixType getType() {
        return MatrixType.FSCC;
    }

    @Override
    public Iterator<FMatrixSparse.CoordinateRealValue> createCoordinateIterator() {
        return new Iterator<FMatrixSparse.CoordinateRealValue>(){
            final FMatrixSparse.CoordinateRealValue coordinate = new FMatrixSparse.CoordinateRealValue();
            int nz_index = 0;
            int column = 0;
            {
                this.incrementColumn();
            }

            @Override
            public boolean hasNext() {
                return this.nz_index < FMatrixSparseCSC.this.nz_length;
            }

            @Override
            public FMatrixSparse.CoordinateRealValue next() {
                this.coordinate.row = FMatrixSparseCSC.this.nz_rows[this.nz_index];
                this.coordinate.col = this.column;
                this.coordinate.value = FMatrixSparseCSC.this.nz_values[this.nz_index];
                ++this.nz_index;
                this.incrementColumn();
                return this.coordinate;
            }

            private void incrementColumn() {
                while (this.column + 1 <= FMatrixSparseCSC.this.numCols && this.nz_index >= FMatrixSparseCSC.this.col_idx[this.column + 1]) {
                    ++this.column;
                }
            }
        };
    }
}

