/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixFixed;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.ops.MatrixIO;

public class DMatrix2x2
implements DMatrixFixed {
    public double a11;
    public double a12;
    public double a21;
    public double a22;

    public DMatrix2x2() {
    }

    public DMatrix2x2(double a11, double a12, double a21, double a22) {
        this.a11 = a11;
        this.a12 = a12;
        this.a21 = a21;
        this.a22 = a22;
    }

    public DMatrix2x2(DMatrix2x2 o) {
        this.a11 = o.a11;
        this.a12 = o.a12;
        this.a21 = o.a21;
        this.a22 = o.a22;
    }

    @Override
    public void zero() {
        this.a11 = 0.0;
        this.a12 = 0.0;
        this.a21 = 0.0;
        this.a22 = 0.0;
    }

    public void setTo(double a11, double a12, double a21, double a22) {
        this.a11 = a11;
        this.a12 = a12;
        this.a21 = a21;
        this.a22 = a22;
    }

    public void setTo(int offset, double[] a) {
        this.a11 = a[offset + 0];
        this.a12 = a[offset + 1];
        this.a21 = a[offset + 2];
        this.a22 = a[offset + 3];
    }

    @Override
    public double get(int row, int col) {
        return this.unsafe_get(row, col);
    }

    @Override
    public double unsafe_get(int row, int col) {
        if (row == 0) {
            if (col == 0) {
                return this.a11;
            }
            if (col == 1) {
                return this.a12;
            }
        } else if (row == 1) {
            if (col == 0) {
                return this.a21;
            }
            if (col == 1) {
                return this.a22;
            }
        }
        throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
    }

    @Override
    public void set(int row, int col, double val) {
        this.unsafe_set(row, col, val);
    }

    @Override
    public void unsafe_set(int row, int col, double val) {
        if (row == 0) {
            if (col == 0) {
                this.a11 = val;
                return;
            }
            if (col == 1) {
                this.a12 = val;
                return;
            }
        } else if (row == 1) {
            if (col == 0) {
                this.a21 = val;
                return;
            }
            if (col == 1) {
                this.a22 = val;
                return;
            }
        }
        throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
    }

    @Override
    public void setTo(Matrix original) {
        if (original.getNumCols() != 2 || original.getNumRows() != 2) {
            throw new IllegalArgumentException("Rows and/or columns do not match");
        }
        DMatrix m = (DMatrix)original;
        this.a11 = m.get(0, 0);
        this.a12 = m.get(0, 1);
        this.a21 = m.get(1, 0);
        this.a22 = m.get(1, 1);
    }

    @Override
    public int getNumRows() {
        return 2;
    }

    @Override
    public int getNumCols() {
        return 2;
    }

    @Override
    public int getNumElements() {
        return 4;
    }

    @Override
    public <T extends Matrix> T copy() {
        return (T)new DMatrix2x2(this);
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
    public <T extends Matrix> T createLike() {
        return (T)new DMatrix2x2();
    }

    @Override
    public MatrixType getType() {
        return MatrixType.UNSPECIFIED;
    }
}

