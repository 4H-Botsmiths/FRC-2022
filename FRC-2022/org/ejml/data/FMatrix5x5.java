/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixFixed;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.ops.MatrixIO;

public class FMatrix5x5
implements FMatrixFixed {
    public float a11;
    public float a12;
    public float a13;
    public float a14;
    public float a15;
    public float a21;
    public float a22;
    public float a23;
    public float a24;
    public float a25;
    public float a31;
    public float a32;
    public float a33;
    public float a34;
    public float a35;
    public float a41;
    public float a42;
    public float a43;
    public float a44;
    public float a45;
    public float a51;
    public float a52;
    public float a53;
    public float a54;
    public float a55;

    public FMatrix5x5() {
    }

    public FMatrix5x5(float a11, float a12, float a13, float a14, float a15, float a21, float a22, float a23, float a24, float a25, float a31, float a32, float a33, float a34, float a35, float a41, float a42, float a43, float a44, float a45, float a51, float a52, float a53, float a54, float a55) {
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.a14 = a14;
        this.a15 = a15;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;
        this.a24 = a24;
        this.a25 = a25;
        this.a31 = a31;
        this.a32 = a32;
        this.a33 = a33;
        this.a34 = a34;
        this.a35 = a35;
        this.a41 = a41;
        this.a42 = a42;
        this.a43 = a43;
        this.a44 = a44;
        this.a45 = a45;
        this.a51 = a51;
        this.a52 = a52;
        this.a53 = a53;
        this.a54 = a54;
        this.a55 = a55;
    }

    public FMatrix5x5(FMatrix5x5 o) {
        this.a11 = o.a11;
        this.a12 = o.a12;
        this.a13 = o.a13;
        this.a14 = o.a14;
        this.a15 = o.a15;
        this.a21 = o.a21;
        this.a22 = o.a22;
        this.a23 = o.a23;
        this.a24 = o.a24;
        this.a25 = o.a25;
        this.a31 = o.a31;
        this.a32 = o.a32;
        this.a33 = o.a33;
        this.a34 = o.a34;
        this.a35 = o.a35;
        this.a41 = o.a41;
        this.a42 = o.a42;
        this.a43 = o.a43;
        this.a44 = o.a44;
        this.a45 = o.a45;
        this.a51 = o.a51;
        this.a52 = o.a52;
        this.a53 = o.a53;
        this.a54 = o.a54;
        this.a55 = o.a55;
    }

    @Override
    public void zero() {
        this.a11 = 0.0f;
        this.a12 = 0.0f;
        this.a13 = 0.0f;
        this.a14 = 0.0f;
        this.a15 = 0.0f;
        this.a21 = 0.0f;
        this.a22 = 0.0f;
        this.a23 = 0.0f;
        this.a24 = 0.0f;
        this.a25 = 0.0f;
        this.a31 = 0.0f;
        this.a32 = 0.0f;
        this.a33 = 0.0f;
        this.a34 = 0.0f;
        this.a35 = 0.0f;
        this.a41 = 0.0f;
        this.a42 = 0.0f;
        this.a43 = 0.0f;
        this.a44 = 0.0f;
        this.a45 = 0.0f;
        this.a51 = 0.0f;
        this.a52 = 0.0f;
        this.a53 = 0.0f;
        this.a54 = 0.0f;
        this.a55 = 0.0f;
    }

    public void setTo(float a11, float a12, float a13, float a14, float a15, float a21, float a22, float a23, float a24, float a25, float a31, float a32, float a33, float a34, float a35, float a41, float a42, float a43, float a44, float a45, float a51, float a52, float a53, float a54, float a55) {
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.a14 = a14;
        this.a15 = a15;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;
        this.a24 = a24;
        this.a25 = a25;
        this.a31 = a31;
        this.a32 = a32;
        this.a33 = a33;
        this.a34 = a34;
        this.a35 = a35;
        this.a41 = a41;
        this.a42 = a42;
        this.a43 = a43;
        this.a44 = a44;
        this.a45 = a45;
        this.a51 = a51;
        this.a52 = a52;
        this.a53 = a53;
        this.a54 = a54;
        this.a55 = a55;
    }

    public void setTo(int offset, float[] a) {
        this.a11 = a[offset + 0];
        this.a12 = a[offset + 1];
        this.a13 = a[offset + 2];
        this.a14 = a[offset + 3];
        this.a15 = a[offset + 4];
        this.a21 = a[offset + 5];
        this.a22 = a[offset + 6];
        this.a23 = a[offset + 7];
        this.a24 = a[offset + 8];
        this.a25 = a[offset + 9];
        this.a31 = a[offset + 10];
        this.a32 = a[offset + 11];
        this.a33 = a[offset + 12];
        this.a34 = a[offset + 13];
        this.a35 = a[offset + 14];
        this.a41 = a[offset + 15];
        this.a42 = a[offset + 16];
        this.a43 = a[offset + 17];
        this.a44 = a[offset + 18];
        this.a45 = a[offset + 19];
        this.a51 = a[offset + 20];
        this.a52 = a[offset + 21];
        this.a53 = a[offset + 22];
        this.a54 = a[offset + 23];
        this.a55 = a[offset + 24];
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
            if (col == 2) {
                return this.a13;
            }
            if (col == 3) {
                return this.a14;
            }
            if (col == 4) {
                return this.a15;
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
            if (col == 4) {
                return this.a25;
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
            if (col == 4) {
                return this.a35;
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
            if (col == 4) {
                return this.a45;
            }
        } else if (row == 4) {
            if (col == 0) {
                return this.a51;
            }
            if (col == 1) {
                return this.a52;
            }
            if (col == 2) {
                return this.a53;
            }
            if (col == 3) {
                return this.a54;
            }
            if (col == 4) {
                return this.a55;
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
            if (col == 2) {
                this.a13 = val;
                return;
            }
            if (col == 3) {
                this.a14 = val;
                return;
            }
            if (col == 4) {
                this.a15 = val;
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
            if (col == 4) {
                this.a25 = val;
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
            if (col == 4) {
                this.a35 = val;
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
            if (col == 4) {
                this.a45 = val;
                return;
            }
        } else if (row == 4) {
            if (col == 0) {
                this.a51 = val;
                return;
            }
            if (col == 1) {
                this.a52 = val;
                return;
            }
            if (col == 2) {
                this.a53 = val;
                return;
            }
            if (col == 3) {
                this.a54 = val;
                return;
            }
            if (col == 4) {
                this.a55 = val;
                return;
            }
        }
        throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
    }

    @Override
    public void setTo(Matrix original) {
        if (original.getNumCols() != 5 || original.getNumRows() != 5) {
            throw new IllegalArgumentException("Rows and/or columns do not match");
        }
        FMatrix m = (FMatrix)original;
        this.a11 = m.get(0, 0);
        this.a12 = m.get(0, 1);
        this.a13 = m.get(0, 2);
        this.a14 = m.get(0, 3);
        this.a15 = m.get(0, 4);
        this.a21 = m.get(1, 0);
        this.a22 = m.get(1, 1);
        this.a23 = m.get(1, 2);
        this.a24 = m.get(1, 3);
        this.a25 = m.get(1, 4);
        this.a31 = m.get(2, 0);
        this.a32 = m.get(2, 1);
        this.a33 = m.get(2, 2);
        this.a34 = m.get(2, 3);
        this.a35 = m.get(2, 4);
        this.a41 = m.get(3, 0);
        this.a42 = m.get(3, 1);
        this.a43 = m.get(3, 2);
        this.a44 = m.get(3, 3);
        this.a45 = m.get(3, 4);
        this.a51 = m.get(4, 0);
        this.a52 = m.get(4, 1);
        this.a53 = m.get(4, 2);
        this.a54 = m.get(4, 3);
        this.a55 = m.get(4, 4);
    }

    @Override
    public int getNumRows() {
        return 5;
    }

    @Override
    public int getNumCols() {
        return 5;
    }

    @Override
    public int getNumElements() {
        return 25;
    }

    @Override
    public <T extends Matrix> T copy() {
        return (T)new FMatrix5x5(this);
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
        return (T)new FMatrix5x5();
    }

    @Override
    public MatrixType getType() {
        return MatrixType.UNSPECIFIED;
    }
}

