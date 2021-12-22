/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.fixed;

import org.ejml.UtilEjml;
import org.ejml.data.DMatrix2;
import org.ejml.data.DMatrix2x2;

public class CommonOps_DDF2 {
    public static void add(DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        c.a11 = a.a11 + b.a11;
        c.a12 = a.a12 + b.a12;
        c.a21 = a.a21 + b.a21;
        c.a22 = a.a22 + b.a22;
    }

    public static void add(DMatrix2 a, DMatrix2 b, DMatrix2 c) {
        c.a1 = a.a1 + b.a1;
        c.a2 = a.a2 + b.a2;
    }

    public static void addEquals(DMatrix2x2 a, DMatrix2x2 b) {
        a.a11 += b.a11;
        a.a12 += b.a12;
        a.a21 += b.a21;
        a.a22 += b.a22;
    }

    public static void addEquals(DMatrix2 a, DMatrix2 b) {
        a.a1 += b.a1;
        a.a2 += b.a2;
    }

    public static void subtract(DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        c.a11 = a.a11 - b.a11;
        c.a12 = a.a12 - b.a12;
        c.a21 = a.a21 - b.a21;
        c.a22 = a.a22 - b.a22;
    }

    public static void subtract(DMatrix2 a, DMatrix2 b, DMatrix2 c) {
        c.a1 = a.a1 - b.a1;
        c.a2 = a.a2 - b.a2;
    }

    public static void subtractEquals(DMatrix2x2 a, DMatrix2x2 b) {
        a.a11 -= b.a11;
        a.a12 -= b.a12;
        a.a21 -= b.a21;
        a.a22 -= b.a22;
    }

    public static void subtractEquals(DMatrix2 a, DMatrix2 b) {
        a.a1 -= b.a1;
        a.a2 -= b.a2;
    }

    public static void transpose(DMatrix2x2 m) {
        double tmp = m.a12;
        m.a12 = m.a21;
        m.a21 = tmp;
    }

    public static DMatrix2x2 transpose(DMatrix2x2 input, DMatrix2x2 output) {
        if (input == null) {
            input = new DMatrix2x2();
        }
        UtilEjml.checkSameInstance(input, output);
        output.a11 = input.a11;
        output.a12 = input.a21;
        output.a21 = input.a12;
        output.a22 = input.a22;
        return output;
    }

    public static void mult(DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = a.a11 * b.a11 + a.a12 * b.a21;
        c.a12 = a.a11 * b.a12 + a.a12 * b.a22;
        c.a21 = a.a21 * b.a11 + a.a22 * b.a21;
        c.a22 = a.a21 * b.a12 + a.a22 * b.a22;
    }

    public static void mult(double alpha, DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = alpha * (a.a11 * b.a11 + a.a12 * b.a21);
        c.a12 = alpha * (a.a11 * b.a12 + a.a12 * b.a22);
        c.a21 = alpha * (a.a21 * b.a11 + a.a22 * b.a21);
        c.a22 = alpha * (a.a21 * b.a12 + a.a22 * b.a22);
    }

    public static void multTransA(DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = a.a11 * b.a11 + a.a21 * b.a21;
        c.a12 = a.a11 * b.a12 + a.a21 * b.a22;
        c.a21 = a.a12 * b.a11 + a.a22 * b.a21;
        c.a22 = a.a12 * b.a12 + a.a22 * b.a22;
    }

    public static void multTransA(double alpha, DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = alpha * (a.a11 * b.a11 + a.a21 * b.a21);
        c.a12 = alpha * (a.a11 * b.a12 + a.a21 * b.a22);
        c.a21 = alpha * (a.a12 * b.a11 + a.a22 * b.a21);
        c.a22 = alpha * (a.a12 * b.a12 + a.a22 * b.a22);
    }

    public static void multTransAB(DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = a.a11 * b.a11 + a.a21 * b.a12;
        c.a12 = a.a11 * b.a21 + a.a21 * b.a22;
        c.a21 = a.a12 * b.a11 + a.a22 * b.a12;
        c.a22 = a.a12 * b.a21 + a.a22 * b.a22;
    }

    public static void multTransAB(double alpha, DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = alpha * (a.a11 * b.a11 + a.a21 * b.a12);
        c.a12 = alpha * (a.a11 * b.a21 + a.a21 * b.a22);
        c.a21 = alpha * (a.a12 * b.a11 + a.a22 * b.a12);
        c.a22 = alpha * (a.a12 * b.a21 + a.a22 * b.a22);
    }

    public static void multTransB(DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = a.a11 * b.a11 + a.a12 * b.a12;
        c.a12 = a.a11 * b.a21 + a.a12 * b.a22;
        c.a21 = a.a21 * b.a11 + a.a22 * b.a12;
        c.a22 = a.a21 * b.a21 + a.a22 * b.a22;
    }

    public static void multTransB(double alpha, DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = alpha * (a.a11 * b.a11 + a.a12 * b.a12);
        c.a12 = alpha * (a.a11 * b.a21 + a.a12 * b.a22);
        c.a21 = alpha * (a.a21 * b.a11 + a.a22 * b.a12);
        c.a22 = alpha * (a.a21 * b.a21 + a.a22 * b.a22);
    }

    public static void multAdd(DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += a.a11 * b.a11 + a.a12 * b.a21;
        c.a12 += a.a11 * b.a12 + a.a12 * b.a22;
        c.a21 += a.a21 * b.a11 + a.a22 * b.a21;
        c.a22 += a.a21 * b.a12 + a.a22 * b.a22;
    }

    public static void multAdd(double alpha, DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += alpha * (a.a11 * b.a11 + a.a12 * b.a21);
        c.a12 += alpha * (a.a11 * b.a12 + a.a12 * b.a22);
        c.a21 += alpha * (a.a21 * b.a11 + a.a22 * b.a21);
        c.a22 += alpha * (a.a21 * b.a12 + a.a22 * b.a22);
    }

    public static void multAddTransA(DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += a.a11 * b.a11 + a.a21 * b.a21;
        c.a12 += a.a11 * b.a12 + a.a21 * b.a22;
        c.a21 += a.a12 * b.a11 + a.a22 * b.a21;
        c.a22 += a.a12 * b.a12 + a.a22 * b.a22;
    }

    public static void multAddTransA(double alpha, DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += alpha * (a.a11 * b.a11 + a.a21 * b.a21);
        c.a12 += alpha * (a.a11 * b.a12 + a.a21 * b.a22);
        c.a21 += alpha * (a.a12 * b.a11 + a.a22 * b.a21);
        c.a22 += alpha * (a.a12 * b.a12 + a.a22 * b.a22);
    }

    public static void multAddTransAB(DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += a.a11 * b.a11 + a.a21 * b.a12;
        c.a12 += a.a11 * b.a21 + a.a21 * b.a22;
        c.a21 += a.a12 * b.a11 + a.a22 * b.a12;
        c.a22 += a.a12 * b.a21 + a.a22 * b.a22;
    }

    public static void multAddTransAB(double alpha, DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += alpha * (a.a11 * b.a11 + a.a21 * b.a12);
        c.a12 += alpha * (a.a11 * b.a21 + a.a21 * b.a22);
        c.a21 += alpha * (a.a12 * b.a11 + a.a22 * b.a12);
        c.a22 += alpha * (a.a12 * b.a21 + a.a22 * b.a22);
    }

    public static void multAddTransB(DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += a.a11 * b.a11 + a.a12 * b.a12;
        c.a12 += a.a11 * b.a21 + a.a12 * b.a22;
        c.a21 += a.a21 * b.a11 + a.a22 * b.a12;
        c.a22 += a.a21 * b.a21 + a.a22 * b.a22;
    }

    public static void multAddTransB(double alpha, DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += alpha * (a.a11 * b.a11 + a.a12 * b.a12);
        c.a12 += alpha * (a.a11 * b.a21 + a.a12 * b.a22);
        c.a21 += alpha * (a.a21 * b.a11 + a.a22 * b.a12);
        c.a22 += alpha * (a.a21 * b.a21 + a.a22 * b.a22);
    }

    public static void multAddOuter(double alpha, DMatrix2x2 A, double beta, DMatrix2 u, DMatrix2 v, DMatrix2x2 C) {
        C.a11 = alpha * A.a11 + beta * u.a1 * v.a1;
        C.a12 = alpha * A.a12 + beta * u.a1 * v.a2;
        C.a21 = alpha * A.a21 + beta * u.a2 * v.a1;
        C.a22 = alpha * A.a22 + beta * u.a2 * v.a2;
    }

    public static void mult(DMatrix2x2 a, DMatrix2 b, DMatrix2 c) {
        c.a1 = a.a11 * b.a1 + a.a12 * b.a2;
        c.a2 = a.a21 * b.a1 + a.a22 * b.a2;
    }

    public static void mult(DMatrix2 a, DMatrix2x2 b, DMatrix2 c) {
        c.a1 = a.a1 * b.a11 + a.a2 * b.a21;
        c.a2 = a.a1 * b.a12 + a.a2 * b.a22;
    }

    public static double dot(DMatrix2 a, DMatrix2 b) {
        return a.a1 * b.a1 + a.a2 * b.a2;
    }

    public static void setIdentity(DMatrix2x2 a) {
        a.a11 = 1.0;
        a.a21 = 0.0;
        a.a12 = 0.0;
        a.a22 = 1.0;
    }

    public static boolean invert(DMatrix2x2 a, DMatrix2x2 inv) {
        double a22;
        double scale = 1.0 / CommonOps_DDF2.elementMaxAbs(a);
        double a11 = a.a11 * scale;
        double a12 = a.a12 * scale;
        double a21 = a.a21 * scale;
        double m11 = a22 = a.a22 * scale;
        double m12 = -a21;
        double m21 = -a12;
        double m22 = a11;
        double det = (a11 * m11 + a12 * m12) / scale;
        inv.a11 = m11 / det;
        inv.a12 = m21 / det;
        inv.a21 = m12 / det;
        inv.a22 = m22 / det;
        return !Double.isNaN(det) && !Double.isInfinite(det);
    }

    public static double det(DMatrix2x2 mat) {
        return mat.a11 * mat.a22 - mat.a12 * mat.a21;
    }

    public static boolean cholL(DMatrix2x2 A) {
        A.a11 = Math.sqrt(A.a11);
        A.a12 = 0.0;
        A.a21 /= A.a11;
        A.a22 = Math.sqrt(A.a22 - A.a21 * A.a21);
        return !UtilEjml.isUncountable(A.a22);
    }

    public static boolean cholU(DMatrix2x2 A) {
        A.a11 = Math.sqrt(A.a11);
        A.a21 = 0.0;
        A.a12 /= A.a11;
        A.a22 = Math.sqrt(A.a22 - A.a12 * A.a12);
        return !UtilEjml.isUncountable(A.a22);
    }

    public static double trace(DMatrix2x2 a) {
        return a.a11 + a.a22;
    }

    public static void diag(DMatrix2x2 input, DMatrix2 out) {
        out.a1 = input.a11;
        out.a2 = input.a22;
    }

    public static double elementMax(DMatrix2x2 a) {
        double max = a.a11;
        if (a.a12 > max) {
            max = a.a12;
        }
        if (a.a21 > max) {
            max = a.a21;
        }
        if (a.a22 > max) {
            max = a.a22;
        }
        return max;
    }

    public static double elementMax(DMatrix2 a) {
        double max = a.a1;
        if (a.a2 > max) {
            max = a.a2;
        }
        return max;
    }

    public static double elementMaxAbs(DMatrix2x2 a) {
        double max = Math.abs(a.a11);
        double tmp = Math.abs(a.a12);
        if (tmp > max) {
            max = tmp;
        }
        if ((tmp = Math.abs(a.a21)) > max) {
            max = tmp;
        }
        if ((tmp = Math.abs(a.a22)) > max) {
            max = tmp;
        }
        return max;
    }

    public static double elementMaxAbs(DMatrix2 a) {
        double max = Math.abs(a.a1);
        double tmp = Math.abs(a.a2);
        if (tmp > max) {
            max = tmp;
        }
        if ((tmp = Math.abs(a.a2)) > max) {
            max = tmp;
        }
        return max;
    }

    public static double elementMin(DMatrix2x2 a) {
        double min = a.a11;
        if (a.a12 < min) {
            min = a.a12;
        }
        if (a.a21 < min) {
            min = a.a21;
        }
        if (a.a22 < min) {
            min = a.a22;
        }
        return min;
    }

    public static double elementMin(DMatrix2 a) {
        double min = a.a1;
        if (a.a2 < min) {
            min = a.a2;
        }
        return min;
    }

    public static double elementMinAbs(DMatrix2x2 a) {
        double min = Math.abs(a.a11);
        double tmp = Math.abs(a.a12);
        if (tmp < min) {
            min = tmp;
        }
        if ((tmp = Math.abs(a.a21)) < min) {
            min = tmp;
        }
        if ((tmp = Math.abs(a.a22)) < min) {
            min = tmp;
        }
        return min;
    }

    public static double elementMinAbs(DMatrix2 a) {
        double min = Math.abs(a.a1);
        double tmp = Math.abs(a.a1);
        if (tmp < min) {
            min = tmp;
        }
        if ((tmp = Math.abs(a.a2)) < min) {
            min = tmp;
        }
        return min;
    }

    public static void elementMult(DMatrix2x2 a, DMatrix2x2 b) {
        a.a11 *= b.a11;
        a.a12 *= b.a12;
        a.a21 *= b.a21;
        a.a22 *= b.a22;
    }

    public static void elementMult(DMatrix2 a, DMatrix2 b) {
        a.a1 *= b.a1;
        a.a2 *= b.a2;
    }

    public static void elementMult(DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        c.a11 = a.a11 * b.a11;
        c.a12 = a.a12 * b.a12;
        c.a21 = a.a21 * b.a21;
        c.a22 = a.a22 * b.a22;
    }

    public static void elementMult(DMatrix2 a, DMatrix2 b, DMatrix2 c) {
        c.a1 = a.a1 * b.a1;
        c.a2 = a.a2 * b.a2;
    }

    public static void elementDiv(DMatrix2x2 a, DMatrix2x2 b) {
        a.a11 /= b.a11;
        a.a12 /= b.a12;
        a.a21 /= b.a21;
        a.a22 /= b.a22;
    }

    public static void elementDiv(DMatrix2 a, DMatrix2 b) {
        a.a1 /= b.a1;
        a.a2 /= b.a2;
    }

    public static void elementDiv(DMatrix2x2 a, DMatrix2x2 b, DMatrix2x2 c) {
        c.a11 = a.a11 / b.a11;
        c.a12 = a.a12 / b.a12;
        c.a21 = a.a21 / b.a21;
        c.a22 = a.a22 / b.a22;
    }

    public static void elementDiv(DMatrix2 a, DMatrix2 b, DMatrix2 c) {
        c.a1 = a.a1 / b.a1;
        c.a2 = a.a2 / b.a2;
    }

    public static void scale(double alpha, DMatrix2x2 a) {
        a.a11 *= alpha;
        a.a12 *= alpha;
        a.a21 *= alpha;
        a.a22 *= alpha;
    }

    public static void scale(double alpha, DMatrix2 a) {
        a.a1 *= alpha;
        a.a2 *= alpha;
    }

    public static void scale(double alpha, DMatrix2x2 a, DMatrix2x2 b) {
        b.a11 = a.a11 * alpha;
        b.a12 = a.a12 * alpha;
        b.a21 = a.a21 * alpha;
        b.a22 = a.a22 * alpha;
    }

    public static void scale(double alpha, DMatrix2 a, DMatrix2 b) {
        b.a1 = a.a1 * alpha;
        b.a2 = a.a2 * alpha;
    }

    public static void divide(DMatrix2x2 a, double alpha) {
        a.a11 /= alpha;
        a.a12 /= alpha;
        a.a21 /= alpha;
        a.a22 /= alpha;
    }

    public static void divide(DMatrix2 a, double alpha) {
        a.a1 /= alpha;
        a.a2 /= alpha;
    }

    public static void divide(DMatrix2x2 a, double alpha, DMatrix2x2 b) {
        b.a11 = a.a11 / alpha;
        b.a12 = a.a12 / alpha;
        b.a21 = a.a21 / alpha;
        b.a22 = a.a22 / alpha;
    }

    public static void divide(DMatrix2 a, double alpha, DMatrix2 b) {
        b.a1 = a.a1 / alpha;
        b.a2 = a.a2 / alpha;
    }

    public static void changeSign(DMatrix2x2 a) {
        a.a11 = -a.a11;
        a.a12 = -a.a12;
        a.a21 = -a.a21;
        a.a22 = -a.a22;
    }

    public static void changeSign(DMatrix2 a) {
        a.a1 = -a.a1;
        a.a2 = -a.a2;
    }

    public static void fill(DMatrix2x2 a, double v) {
        a.a11 = v;
        a.a12 = v;
        a.a21 = v;
        a.a22 = v;
    }

    public static void fill(DMatrix2 a, double v) {
        a.a1 = v;
        a.a2 = v;
    }

    public static DMatrix2 extractRow(DMatrix2x2 a, int row, DMatrix2 out) {
        if (out == null) {
            out = new DMatrix2();
        }
        switch (row) {
            case 0: {
                out.a1 = a.a11;
                out.a2 = a.a12;
                break;
            }
            case 1: {
                out.a1 = a.a21;
                out.a2 = a.a22;
                break;
            }
            default: {
                throw new IllegalArgumentException("Out of bounds row. row = " + row);
            }
        }
        return out;
    }

    public static DMatrix2 extractColumn(DMatrix2x2 a, int column, DMatrix2 out) {
        if (out == null) {
            out = new DMatrix2();
        }
        switch (column) {
            case 0: {
                out.a1 = a.a11;
                out.a2 = a.a21;
                break;
            }
            case 1: {
                out.a1 = a.a12;
                out.a2 = a.a22;
                break;
            }
            default: {
                throw new IllegalArgumentException("Out of bounds column. column = " + column);
            }
        }
        return out;
    }
}

