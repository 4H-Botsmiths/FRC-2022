/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixFixed;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.ops.MatrixIO;

public class FMatrix6
implements FMatrixFixed {
    public float a1;
    public float a2;
    public float a3;
    public float a4;
    public float a5;
    public float a6;

    public FMatrix6() {
    }

    public FMatrix6(float a1, float a2, float a3, float a4, float a5, float a6) {
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
        this.a6 = a6;
    }

    public FMatrix6(FMatrix6 o) {
        this.a1 = o.a1;
        this.a2 = o.a2;
        this.a3 = o.a3;
        this.a4 = o.a4;
        this.a5 = o.a5;
        this.a6 = o.a6;
    }

    @Override
    public void zero() {
        this.a1 = 0.0f;
        this.a2 = 0.0f;
        this.a3 = 0.0f;
        this.a4 = 0.0f;
        this.a5 = 0.0f;
        this.a6 = 0.0f;
    }

    public void setTo(float a1, float a2, float a3, float a4, float a5, float a6) {
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
        this.a6 = a6;
    }

    public void setTo(int offset, float[] array) {
        this.a1 = array[offset + 0];
        this.a2 = array[offset + 1];
        this.a3 = array[offset + 2];
        this.a4 = array[offset + 3];
        this.a5 = array[offset + 4];
        this.a6 = array[offset + 5];
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
        if (w == 2) {
            return this.a3;
        }
        if (w == 3) {
            return this.a4;
        }
        if (w == 4) {
            return this.a5;
        }
        if (w == 5) {
            return this.a6;
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
        } else if (w == 2) {
            this.a3 = val;
        } else if (w == 3) {
            this.a4 = val;
        } else if (w == 4) {
            this.a5 = val;
        } else if (w == 5) {
            this.a6 = val;
        } else {
            throw new IllegalArgumentException("Out of range.  " + w);
        }
    }

    @Override
    public void setTo(Matrix original) {
        FMatrix m = (FMatrix)original;
        if (m.getNumCols() == 1 && m.getNumRows() == 6) {
            this.a1 = m.get(0, 0);
            this.a2 = m.get(1, 0);
            this.a3 = m.get(2, 0);
            this.a4 = m.get(3, 0);
            this.a5 = m.get(4, 0);
            this.a6 = m.get(5, 0);
        } else if (m.getNumRows() == 1 && m.getNumCols() == 6) {
            this.a1 = m.get(0, 0);
            this.a2 = m.get(0, 1);
            this.a3 = m.get(0, 2);
            this.a4 = m.get(0, 3);
            this.a5 = m.get(0, 4);
            this.a6 = m.get(0, 5);
        } else {
            throw new IllegalArgumentException("Incompatible shape");
        }
    }

    @Override
    public int getNumRows() {
        return 6;
    }

    @Override
    public int getNumCols() {
        return 1;
    }

    @Override
    public int getNumElements() {
        return 6;
    }

    @Override
    public <T extends Matrix> T copy() {
        return (T)new FMatrix6(this);
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
        return (T)new FMatrix6();
    }

    @Override
    public MatrixType getType() {
        return MatrixType.UNSPECIFIED;
    }
}

