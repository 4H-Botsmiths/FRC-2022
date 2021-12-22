/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixFixed;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.ops.MatrixIO;

public class FMatrix2x2
implements FMatrixFixed {
    public float a11;
    public float a12;
    public float a21;
    public float a22;

    public FMatrix2x2() {
    }

    public FMatrix2x2(float a11, float a12, float a21, float a22) {
        this.a11 = a11;
        this.a12 = a12;
        this.a21 = a21;
        this.a22 = a22;
    }

    public FMatrix2x2(FMatrix2x2 o) {
        this.a11 = o.a11;
        this.a12 = o.a12;
        this.a21 = o.a21;
        this.a22 = o.a22;
    }

    @Override
    public void zero() {
        this.a11 = 0.0f;
        this.a12 = 0.0f;
        this.a21 = 0.0f;
        this.a22 = 0.0f;
    }

    public void setTo(float a11, float a12, float a21, float a22) {
        this.a11 = a11;
        this.a12 = a12;
        this.a21 = a21;
        this.a22 = a22;
    }

    public void setTo(int offset, float[] a) {
        this.a11 = a[offset + 0];
        this.a12 = a[offset + 1];
        this.a21 = a[offset + 2];
        this.a22 = a[offset + 3];
    }

    @Override
    public float get(int row, int col) {
        return this.unsafe_get(row, col);
    }

    @Override
    public float unsafe_get(int row, int col) {
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
    public void set(int row, int col, float val) {
        this.unsafe_set(row, col, val);
    }

    @Override
    public void unsafe_set(int row, int col, float val) {
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
        FMatrix m = (FMatrix)original;
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
        return (T)new FMatrix2x2(this);
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
        return (T)new FMatrix2x2();
    }

    @Override
    public MatrixType getType() {
        return MatrixType.UNSPECIFIED;
    }
}

