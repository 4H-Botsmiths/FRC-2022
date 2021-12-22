/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixFixed;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.ops.MatrixIO;

public class DMatrix5
implements DMatrixFixed {
    public double a1;
    public double a2;
    public double a3;
    public double a4;
    public double a5;

    public DMatrix5() {
    }

    public DMatrix5(double a1, double a2, double a3, double a4, double a5) {
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
    }

    public DMatrix5(DMatrix5 o) {
        this.a1 = o.a1;
        this.a2 = o.a2;
        this.a3 = o.a3;
        this.a4 = o.a4;
        this.a5 = o.a5;
    }

    @Override
    public void zero() {
        this.a1 = 0.0;
        this.a2 = 0.0;
        this.a3 = 0.0;
        this.a4 = 0.0;
        this.a5 = 0.0;
    }

    public void setTo(double a1, double a2, double a3, double a4, double a5) {
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
    }

    public void setTo(int offset, double[] array) {
        this.a1 = array[offset + 0];
        this.a2 = array[offset + 1];
        this.a3 = array[offset + 2];
        this.a4 = array[offset + 3];
        this.a5 = array[offset + 4];
    }

    @Override
    public double get(int row, int col) {
        return this.unsafe_get(row, col);
    }

    @Override
    public double unsafe_get(int row, int col) {
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
        throw new IllegalArgumentException("Out of range.  " + w);
    }

    @Override
    public void set(int row, int col, double val) {
        this.unsafe_set(row, col, val);
    }

    @Override
    public void unsafe_set(int row, int col, double val) {
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
        } else {
            throw new IllegalArgumentException("Out of range.  " + w);
        }
    }

    @Override
    public void setTo(Matrix original) {
        DMatrix m = (DMatrix)original;
        if (m.getNumCols() == 1 && m.getNumRows() == 5) {
            this.a1 = m.get(0, 0);
            this.a2 = m.get(1, 0);
            this.a3 = m.get(2, 0);
            this.a4 = m.get(3, 0);
            this.a5 = m.get(4, 0);
        } else if (m.getNumRows() == 1 && m.getNumCols() == 5) {
            this.a1 = m.get(0, 0);
            this.a2 = m.get(0, 1);
            this.a3 = m.get(0, 2);
            this.a4 = m.get(0, 3);
            this.a5 = m.get(0, 4);
        } else {
            throw new IllegalArgumentException("Incompatible shape");
        }
    }

    @Override
    public int getNumRows() {
        return 5;
    }

    @Override
    public int getNumCols() {
        return 1;
    }

    @Override
    public int getNumElements() {
        return 5;
    }

    @Override
    public <T extends Matrix> T copy() {
        return (T)new DMatrix5(this);
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
        return (T)new DMatrix5();
    }

    @Override
    public MatrixType getType() {
        return MatrixType.UNSPECIFIED;
    }
}

