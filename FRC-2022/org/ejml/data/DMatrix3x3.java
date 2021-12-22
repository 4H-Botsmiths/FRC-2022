/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixFixed;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.ops.MatrixIO;

public class DMatrix3x3
implements DMatrixFixed {
    public double a11;
    public double a12;
    public double a13;
    public double a21;
    public double a22;
    public double a23;
    public double a31;
    public double a32;
    public double a33;

    public DMatrix3x3() {
    }

    public DMatrix3x3(double a11, double a12, double a13, double a21, double a22, double a23, double a31, double a32, double a33) {
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;
        this.a31 = a31;
        this.a32 = a32;
        this.a33 = a33;
    }

    public DMatrix3x3(DMatrix3x3 o) {
        this.a11 = o.a11;
        this.a12 = o.a12;
        this.a13 = o.a13;
        this.a21 = o.a21;
        this.a22 = o.a22;
        this.a23 = o.a23;
        this.a31 = o.a31;
        this.a32 = o.a32;
        this.a33 = o.a33;
    }

    @Override
    public void zero() {
        this.a11 = 0.0;
        this.a12 = 0.0;
        this.a13 = 0.0;
        this.a21 = 0.0;
        this.a22 = 0.0;
        this.a23 = 0.0;
        this.a31 = 0.0;
        this.a32 = 0.0;
        this.a33 = 0.0;
    }

    public void setTo(double a11, double a12, double a13, double a21, double a22, double a23, double a31, double a32, double a33) {
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;
        this.a31 = a31;
        this.a32 = a32;
        this.a33 = a33;
    }

    public void setTo(int offset, double[] a) {
        this.a11 = a[offset + 0];
        this.a12 = a[offset + 1];
        this.a13 = a[offset + 2];
        this.a21 = a[offset + 3];
        this.a22 = a[offset + 4];
        this.a23 = a[offset + 5];
        this.a31 = a[offset + 6];
        this.a32 = a[offset + 7];
        this.a33 = a[offset + 8];
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
            if (col == 2) {
                return this.a13;
            }
        } else if (row == 1) {
            if (col == 0) {
                return this.a21;
            }
            if (col == 1) {
                return this.a22;
            }
            if (col == 2) {
                return this.a23;
            }
        } else if (row == 2) {
            if (col == 0) {
                return this.a31;
            }
            if (col == 1) {
                return this.a32;
            }
            if (col == 2) {
                return this.a33;
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
            if (col == 2) {
                this.a13 = val;
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
            if (col == 2) {
                this.a23 = val;
                return;
            }
        } else if (row == 2) {
            if (col == 0) {
                this.a31 = val;
                return;
            }
            if (col == 1) {
                this.a32 = val;
                return;
            }
            if (col == 2) {
                this.a33 = val;
                return;
            }
        }
        throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
    }

    @Override
    public void setTo(Matrix original) {
        if (original.getNumCols() != 3 || original.getNumRows() != 3) {
            throw new IllegalArgumentException("Rows and/or columns do not match");
        }
        DMatrix m = (DMatrix)original;
        this.a11 = m.get(0, 0);
        this.a12 = m.get(0, 1);
        this.a13 = m.get(0, 2);
        this.a21 = m.get(1, 0);
        this.a22 = m.get(1, 1);
        this.a23 = m.get(1, 2);
        this.a31 = m.get(2, 0);
        this.a32 = m.get(2, 1);
        this.a33 = m.get(2, 2);
    }

    @Override
    public int getNumRows() {
        return 3;
    }

    @Override
    public int getNumCols() {
        return 3;
    }

    @Override
    public int getNumElements() {
        return 9;
    }

    @Override
    public <T extends Matrix> T copy() {
        return (T)new DMatrix3x3(this);
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
        return (T)new DMatrix3x3();
    }

    @Override
    public MatrixType getType() {
        return MatrixType.UNSPECIFIED;
    }
}

