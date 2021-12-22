/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixFixed;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.ops.MatrixIO;

public class FMatrix2
implements FMatrixFixed {
    public float a1;
    public float a2;

    public FMatrix2() {
    }

    public FMatrix2(float a1, float a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    public FMatrix2(FMatrix2 o) {
        this.a1 = o.a1;
        this.a2 = o.a2;
    }

    @Override
    public void zero() {
        this.a1 = 0.0f;
        this.a2 = 0.0f;
    }

    public void setTo(float a1, float a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    public void setTo(int offset, float[] array) {
        this.a1 = array[offset + 0];
        this.a2 = array[offset + 1];
    }

    @Override
    public float get(int row, int col) {
        return this.unsafe_get(row, col);
    }

    @Override
    public float unsafe_get(int row, int col) {
        if (row != 0 && col != 0) {
            throw new IllegalArgumentException("Row or column must be zero since this is a vector");
        }
        int w = Math.max(row, col);
        if (w == 0) {
            return this.a1;
        }
        if (w == 1) {
            return this.a2;
        }
        throw new IllegalArgumentException("Out of range.  " + w);
    }

    @Override
    public void set(int row, int col, float val) {
        this.unsafe_set(row, col, val);
    }

    @Override
    public void unsafe_set(int row, int col, float val) {
        if (row != 0 && col != 0) {
            throw new IllegalArgumentException("Row or column must be zero since this is a vector");
        }
        int w = Math.max(row, col);
        if (w == 0) {
            this.a1 = val;
        } else if (w == 1) {
            this.a2 = val;
        } else {
            throw new IllegalArgumentException("Out of range.  " + w);
        }
    }

    @Override
    public void setTo(Matrix original) {
        FMatrix m = (FMatrix)original;
        if (m.getNumCols() == 1 && m.getNumRows() == 2) {
            this.a1 = m.get(0, 0);
            this.a2 = m.get(1, 0);
        } else if (m.getNumRows() == 1 && m.getNumCols() == 2) {
            this.a1 = m.get(0, 0);
            this.a2 = m.get(0, 1);
        } else {
            throw new IllegalArgumentException("Incompatible shape");
        }
    }

    @Override
    public int getNumRows() {
        return 2;
    }

    @Override
    public int getNumCols() {
        return 1;
    }

    @Override
    public int getNumElements() {
        return 2;
    }

    @Override
    public <T extends Matrix> T copy() {
        return (T)new FMatrix2(this);
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
        return (T)new FMatrix2();
    }

    @Override
    public MatrixType getType() {
        return MatrixType.UNSPECIFIED;
    }
}

