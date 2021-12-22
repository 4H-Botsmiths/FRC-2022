/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixFixed;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.ops.MatrixIO;

public class DMatrix4x4
implements DMatrixFixed {
    public double a11;
    public double a12;
    public double a13;
    public double a14;
    public double a21;
    public double a22;
    public double a23;
    public double a24;
    public double a31;
    public double a32;
    public double a33;
    public double a34;
    public double a41;
    public double a42;
    public double a43;
    public double a44;

    public DMatrix4x4() {
    }

    public DMatrix4x4(double a11, double a12, double a13, double a14, double a21, double a22, double a23, double a24, double a31, double a32, double a33, double a34, double a41, double a42, double a43, double a44) {
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.a14 = a14;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;
        this.a24 = a24;
        this.a31 = a31;
        this.a32 = a32;
        this.a33 = a33;
        this.a34 = a34;
        this.a41 = a41;
        this.a42 = a42;
        this.a43 = a43;
        this.a44 = a44;
    }

    public DMatrix4x4(DMatrix4x4 o) {
        this.a11 = o.a11;
        this.a12 = o.a12;
        this.a13 = o.a13;
        this.a14 = o.a14;
        this.a21 = o.a21;
        this.a22 = o.a22;
        this.a23 = o.a23;
        this.a24 = o.a24;
        this.a31 = o.a31;
        this.a32 = o.a32;
        this.a33 = o.a33;
        this.a34 = o.a34;
        this.a41 = o.a41;
        this.a42 = o.a42;
        this.a43 = o.a43;
        this.a44 = o.a44;
    }

    @Override
    public void zero() {
        this.a11 = 0.0;
        this.a12 = 0.0;
        this.a13 = 0.0;
        this.a14 = 0.0;
        this.a21 = 0.0;
        this.a22 = 0.0;
        this.a23 = 0.0;
        this.a24 = 0.0;
        this.a31 = 0.0;
        this.a32 = 0.0;
        this.a33 = 0.0;
        this.a34 = 0.0;
        this.a41 = 0.0;
        this.a42 = 0.0;
        this.a43 = 0.0;
        this.a44 = 0.0;
    }

    public void setTo(double a11, double a12, double a13, double a14, double a21, double a22, double a23, double a24, double a31, double a32, double a33, double a34, double a41, double a42, double a43, double a44) {
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.a14 = a14;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;
        this.a24 = a24;
        this.a31 = a31;
        this.a32 = a32;
        this.a33 = a33;
        this.a34 = a34;
        this.a41 = a41;
        this.a42 = a42;
        this.a43 = a43;
        this.a44 = a44;
    }

    public void setTo(int offset, double[] a) {
        this.a11 = a[offset + 0];
        this.a12 = a[offset + 1];
        this.a13 = a[offset + 2];
        this.a14 = a[offset + 3];
        this.a21 = a[offset + 4];
        this.a22 = a[offset + 5];
        this.a23 = a[offset + 6];
        this.a24 = a[offset + 7];
        this.a31 = a[offset + 8];
        this.a32 = a[offset + 9];
        this.a33 = a[offset + 10];
        this.a34 = a[offset + 11];
        this.a41 = a[offset + 12];
        this.a42 = a[offset + 13];
        this.a43 = a[offset + 14];
        this.a44 = a[offset + 15];
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
            if (col == 3) {
                return this.a14;
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
            if (col == 3) {
                return this.a24;
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
            if (col == 3) {
                return this.a34;
            }
        } else if (row == 3) {
            if (col == 0) {
                return this.a41;
            }
            if (col == 1) {
                return this.a42;
            }
            if (col == 2) {
                return this.a43;
            }
            if (col == 3) {
                return this.a44;
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
            if (col == 3) {
                this.a14 = val;
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
            if (col == 3) {
                this.a24 = val;
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
            if (col == 3) {
                this.a34 = val;
                return;
            }
        } else if (row == 3) {
            if (col == 0) {
                this.a41 = val;
                return;
            }
            if (col == 1) {
                this.a42 = val;
                return;
            }
            if (col == 2) {
                this.a43 = val;
                return;
            }
            if (col == 3) {
                this.a44 = val;
                return;
            }
        }
        throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
    }

    @Override
    public void setTo(Matrix original) {
        if (original.getNumCols() != 4 || original.getNumRows() != 4) {
            throw new IllegalArgumentException("Rows and/or columns do not match");
        }
        DMatrix m = (DMatrix)original;
        this.a11 = m.get(0, 0);
        this.a12 = m.get(0, 1);
        this.a13 = m.get(0, 2);
        this.a14 = m.get(0, 3);
        this.a21 = m.get(1, 0);
        this.a22 = m.get(1, 1);
        this.a23 = m.get(1, 2);
        this.a24 = m.get(1, 3);
        this.a31 = m.get(2, 0);
        this.a32 = m.get(2, 1);
        this.a33 = m.get(2, 2);
        this.a34 = m.get(2, 3);
        this.a41 = m.get(3, 0);
        this.a42 = m.get(3, 1);
        this.a43 = m.get(3, 2);
        this.a44 = m.get(3, 3);
    }

    @Override
    public int getNumRows() {
        return 4;
    }

    @Override
    public int getNumCols() {
        return 4;
    }

    @Override
    public int getNumElements() {
        return 16;
    }

    @Override
    public <T extends Matrix> T copy() {
        return (T)new DMatrix4x4(this);
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
        return (T)new DMatrix4x4();
    }

    @Override
    public MatrixType getType() {
        return MatrixType.UNSPECIFIED;
    }
}

