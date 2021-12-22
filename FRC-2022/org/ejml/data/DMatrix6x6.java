/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixFixed;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.ops.MatrixIO;

public class DMatrix6x6
implements DMatrixFixed {
    public double a11;
    public double a12;
    public double a13;
    public double a14;
    public double a15;
    public double a16;
    public double a21;
    public double a22;
    public double a23;
    public double a24;
    public double a25;
    public double a26;
    public double a31;
    public double a32;
    public double a33;
    public double a34;
    public double a35;
    public double a36;
    public double a41;
    public double a42;
    public double a43;
    public double a44;
    public double a45;
    public double a46;
    public double a51;
    public double a52;
    public double a53;
    public double a54;
    public double a55;
    public double a56;
    public double a61;
    public double a62;
    public double a63;
    public double a64;
    public double a65;
    public double a66;

    public DMatrix6x6() {
    }

    public DMatrix6x6(double a11, double a12, double a13, double a14, double a15, double a16, double a21, double a22, double a23, double a24, double a25, double a26, double a31, double a32, double a33, double a34, double a35, double a36, double a41, double a42, double a43, double a44, double a45, double a46, double a51, double a52, double a53, double a54, double a55, double a56, double a61, double a62, double a63, double a64, double a65, double a66) {
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.a14 = a14;
        this.a15 = a15;
        this.a16 = a16;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;
        this.a24 = a24;
        this.a25 = a25;
        this.a26 = a26;
        this.a31 = a31;
        this.a32 = a32;
        this.a33 = a33;
        this.a34 = a34;
        this.a35 = a35;
        this.a36 = a36;
        this.a41 = a41;
        this.a42 = a42;
        this.a43 = a43;
        this.a44 = a44;
        this.a45 = a45;
        this.a46 = a46;
        this.a51 = a51;
        this.a52 = a52;
        this.a53 = a53;
        this.a54 = a54;
        this.a55 = a55;
        this.a56 = a56;
        this.a61 = a61;
        this.a62 = a62;
        this.a63 = a63;
        this.a64 = a64;
        this.a65 = a65;
        this.a66 = a66;
    }

    public DMatrix6x6(DMatrix6x6 o) {
        this.a11 = o.a11;
        this.a12 = o.a12;
        this.a13 = o.a13;
        this.a14 = o.a14;
        this.a15 = o.a15;
        this.a16 = o.a16;
        this.a21 = o.a21;
        this.a22 = o.a22;
        this.a23 = o.a23;
        this.a24 = o.a24;
        this.a25 = o.a25;
        this.a26 = o.a26;
        this.a31 = o.a31;
        this.a32 = o.a32;
        this.a33 = o.a33;
        this.a34 = o.a34;
        this.a35 = o.a35;
        this.a36 = o.a36;
        this.a41 = o.a41;
        this.a42 = o.a42;
        this.a43 = o.a43;
        this.a44 = o.a44;
        this.a45 = o.a45;
        this.a46 = o.a46;
        this.a51 = o.a51;
        this.a52 = o.a52;
        this.a53 = o.a53;
        this.a54 = o.a54;
        this.a55 = o.a55;
        this.a56 = o.a56;
        this.a61 = o.a61;
        this.a62 = o.a62;
        this.a63 = o.a63;
        this.a64 = o.a64;
        this.a65 = o.a65;
        this.a66 = o.a66;
    }

    @Override
    public void zero() {
        this.a11 = 0.0;
        this.a12 = 0.0;
        this.a13 = 0.0;
        this.a14 = 0.0;
        this.a15 = 0.0;
        this.a16 = 0.0;
        this.a21 = 0.0;
        this.a22 = 0.0;
        this.a23 = 0.0;
        this.a24 = 0.0;
        this.a25 = 0.0;
        this.a26 = 0.0;
        this.a31 = 0.0;
        this.a32 = 0.0;
        this.a33 = 0.0;
        this.a34 = 0.0;
        this.a35 = 0.0;
        this.a36 = 0.0;
        this.a41 = 0.0;
        this.a42 = 0.0;
        this.a43 = 0.0;
        this.a44 = 0.0;
        this.a45 = 0.0;
        this.a46 = 0.0;
        this.a51 = 0.0;
        this.a52 = 0.0;
        this.a53 = 0.0;
        this.a54 = 0.0;
        this.a55 = 0.0;
        this.a56 = 0.0;
        this.a61 = 0.0;
        this.a62 = 0.0;
        this.a63 = 0.0;
        this.a64 = 0.0;
        this.a65 = 0.0;
        this.a66 = 0.0;
    }

    public void setTo(double a11, double a12, double a13, double a14, double a15, double a16, double a21, double a22, double a23, double a24, double a25, double a26, double a31, double a32, double a33, double a34, double a35, double a36, double a41, double a42, double a43, double a44, double a45, double a46, double a51, double a52, double a53, double a54, double a55, double a56, double a61, double a62, double a63, double a64, double a65, double a66) {
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.a14 = a14;
        this.a15 = a15;
        this.a16 = a16;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;
        this.a24 = a24;
        this.a25 = a25;
        this.a26 = a26;
        this.a31 = a31;
        this.a32 = a32;
        this.a33 = a33;
        this.a34 = a34;
        this.a35 = a35;
        this.a36 = a36;
        this.a41 = a41;
        this.a42 = a42;
        this.a43 = a43;
        this.a44 = a44;
        this.a45 = a45;
        this.a46 = a46;
        this.a51 = a51;
        this.a52 = a52;
        this.a53 = a53;
        this.a54 = a54;
        this.a55 = a55;
        this.a56 = a56;
        this.a61 = a61;
        this.a62 = a62;
        this.a63 = a63;
        this.a64 = a64;
        this.a65 = a65;
        this.a66 = a66;
    }

    public void setTo(int offset, double[] a) {
        this.a11 = a[offset + 0];
        this.a12 = a[offset + 1];
        this.a13 = a[offset + 2];
        this.a14 = a[offset + 3];
        this.a15 = a[offset + 4];
        this.a16 = a[offset + 5];
        this.a21 = a[offset + 6];
        this.a22 = a[offset + 7];
        this.a23 = a[offset + 8];
        this.a24 = a[offset + 9];
        this.a25 = a[offset + 10];
        this.a26 = a[offset + 11];
        this.a31 = a[offset + 12];
        this.a32 = a[offset + 13];
        this.a33 = a[offset + 14];
        this.a34 = a[offset + 15];
        this.a35 = a[offset + 16];
        this.a36 = a[offset + 17];
        this.a41 = a[offset + 18];
        this.a42 = a[offset + 19];
        this.a43 = a[offset + 20];
        this.a44 = a[offset + 21];
        this.a45 = a[offset + 22];
        this.a46 = a[offset + 23];
        this.a51 = a[offset + 24];
        this.a52 = a[offset + 25];
        this.a53 = a[offset + 26];
        this.a54 = a[offset + 27];
        this.a55 = a[offset + 28];
        this.a56 = a[offset + 29];
        this.a61 = a[offset + 30];
        this.a62 = a[offset + 31];
        this.a63 = a[offset + 32];
        this.a64 = a[offset + 33];
        this.a65 = a[offset + 34];
        this.a66 = a[offset + 35];
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
            if (col == 4) {
                return this.a15;
            }
            if (col == 5) {
                return this.a16;
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
            if (col == 5) {
                return this.a26;
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
            if (col == 5) {
                return this.a36;
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
            if (col == 5) {
                return this.a46;
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
            if (col == 5) {
                return this.a56;
            }
        } else if (row == 5) {
            if (col == 0) {
                return this.a61;
            }
            if (col == 1) {
                return this.a62;
            }
            if (col == 2) {
                return this.a63;
            }
            if (col == 3) {
                return this.a64;
            }
            if (col == 4) {
                return this.a65;
            }
            if (col == 5) {
                return this.a66;
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
            if (col == 4) {
                this.a15 = val;
                return;
            }
            if (col == 5) {
                this.a16 = val;
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
            if (col == 5) {
                this.a26 = val;
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
            if (col == 5) {
                this.a36 = val;
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
            if (col == 5) {
                this.a46 = val;
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
            if (col == 5) {
                this.a56 = val;
                return;
            }
        } else if (row == 5) {
            if (col == 0) {
                this.a61 = val;
                return;
            }
            if (col == 1) {
                this.a62 = val;
                return;
            }
            if (col == 2) {
                this.a63 = val;
                return;
            }
            if (col == 3) {
                this.a64 = val;
                return;
            }
            if (col == 4) {
                this.a65 = val;
                return;
            }
            if (col == 5) {
                this.a66 = val;
                return;
            }
        }
        throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
    }

    @Override
    public void setTo(Matrix original) {
        if (original.getNumCols() != 6 || original.getNumRows() != 6) {
            throw new IllegalArgumentException("Rows and/or columns do not match");
        }
        DMatrix m = (DMatrix)original;
        this.a11 = m.get(0, 0);
        this.a12 = m.get(0, 1);
        this.a13 = m.get(0, 2);
        this.a14 = m.get(0, 3);
        this.a15 = m.get(0, 4);
        this.a16 = m.get(0, 5);
        this.a21 = m.get(1, 0);
        this.a22 = m.get(1, 1);
        this.a23 = m.get(1, 2);
        this.a24 = m.get(1, 3);
        this.a25 = m.get(1, 4);
        this.a26 = m.get(1, 5);
        this.a31 = m.get(2, 0);
        this.a32 = m.get(2, 1);
        this.a33 = m.get(2, 2);
        this.a34 = m.get(2, 3);
        this.a35 = m.get(2, 4);
        this.a36 = m.get(2, 5);
        this.a41 = m.get(3, 0);
        this.a42 = m.get(3, 1);
        this.a43 = m.get(3, 2);
        this.a44 = m.get(3, 3);
        this.a45 = m.get(3, 4);
        this.a46 = m.get(3, 5);
        this.a51 = m.get(4, 0);
        this.a52 = m.get(4, 1);
        this.a53 = m.get(4, 2);
        this.a54 = m.get(4, 3);
        this.a55 = m.get(4, 4);
        this.a56 = m.get(4, 5);
        this.a61 = m.get(5, 0);
        this.a62 = m.get(5, 1);
        this.a63 = m.get(5, 2);
        this.a64 = m.get(5, 3);
        this.a65 = m.get(5, 4);
        this.a66 = m.get(5, 5);
    }

    @Override
    public int getNumRows() {
        return 6;
    }

    @Override
    public int getNumCols() {
        return 6;
    }

    @Override
    public int getNumElements() {
        return 36;
    }

    @Override
    public <T extends Matrix> T copy() {
        return (T)new DMatrix6x6(this);
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
        return (T)new DMatrix6x6();
    }

    @Override
    public MatrixType getType() {
        return MatrixType.UNSPECIFIED;
    }
}
