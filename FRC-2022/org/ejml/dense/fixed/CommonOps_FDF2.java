/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.fixed;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrix2;
import org.ejml.data.FMatrix2x2;

public class CommonOps_FDF2 {
    public static void add(FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        c.a11 = a.a11 + b.a11;
        c.a12 = a.a12 + b.a12;
        c.a21 = a.a21 + b.a21;
        c.a22 = a.a22 + b.a22;
    }

    public static void add(FMatrix2 a, FMatrix2 b, FMatrix2 c) {
        c.a1 = a.a1 + b.a1;
        c.a2 = a.a2 + b.a2;
    }

    public static void addEquals(FMatrix2x2 a, FMatrix2x2 b) {
        a.a11 += b.a11;
        a.a12 += b.a12;
        a.a21 += b.a21;
        a.a22 += b.a22;
    }

    public static void addEquals(FMatrix2 a, FMatrix2 b) {
        a.a1 += b.a1;
        a.a2 += b.a2;
    }

    public static void subtract(FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        c.a11 = a.a11 - b.a11;
        c.a12 = a.a12 - b.a12;
        c.a21 = a.a21 - b.a21;
        c.a22 = a.a22 - b.a22;
    }

    public static void subtract(FMatrix2 a, FMatrix2 b, FMatrix2 c) {
        c.a1 = a.a1 - b.a1;
        c.a2 = a.a2 - b.a2;
    }

    public static void subtractEquals(FMatrix2x2 a, FMatrix2x2 b) {
        a.a11 -= b.a11;
        a.a12 -= b.a12;
        a.a21 -= b.a21;
        a.a22 -= b.a22;
    }

    public static void subtractEquals(FMatrix2 a, FMatrix2 b) {
        a.a1 -= b.a1;
        a.a2 -= b.a2;
    }

    public static void transpose(FMatrix2x2 m) {
        float tmp = m.a12;
        m.a12 = m.a21;
        m.a21 = tmp;
    }

    public static FMatrix2x2 transpose(FMatrix2x2 input, FMatrix2x2 output) {
        if (input == null) {
            input = new FMatrix2x2();
        }
        UtilEjml.checkSameInstance(input, output);
        output.a11 = input.a11;
        output.a12 = input.a21;
        output.a21 = input.a12;
        output.a22 = input.a22;
        return output;
    }

    public static void mult(FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = a.a11 * b.a11 + a.a12 * b.a21;
        c.a12 = a.a11 * b.a12 + a.a12 * b.a22;
        c.a21 = a.a21 * b.a11 + a.a22 * b.a21;
        c.a22 = a.a21 * b.a12 + a.a22 * b.a22;
    }

    public static void mult(float alpha, FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = alpha * (a.a11 * b.a11 + a.a12 * b.a21);
        c.a12 = alpha * (a.a11 * b.a12 + a.a12 * b.a22);
        c.a21 = alpha * (a.a21 * b.a11 + a.a22 * b.a21);
        c.a22 = alpha * (a.a21 * b.a12 + a.a22 * b.a22);
    }

    public static void multTransA(FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = a.a11 * b.a11 + a.a21 * b.a21;
        c.a12 = a.a11 * b.a12 + a.a21 * b.a22;
        c.a21 = a.a12 * b.a11 + a.a22 * b.a21;
        c.a22 = a.a12 * b.a12 + a.a22 * b.a22;
    }

    public static void multTransA(float alpha, FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = alpha * (a.a11 * b.a11 + a.a21 * b.a21);
        c.a12 = alpha * (a.a11 * b.a12 + a.a21 * b.a22);
        c.a21 = alpha * (a.a12 * b.a11 + a.a22 * b.a21);
        c.a22 = alpha * (a.a12 * b.a12 + a.a22 * b.a22);
    }

    public static void multTransAB(FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = a.a11 * b.a11 + a.a21 * b.a12;
        c.a12 = a.a11 * b.a21 + a.a21 * b.a22;
        c.a21 = a.a12 * b.a11 + a.a22 * b.a12;
        c.a22 = a.a12 * b.a21 + a.a22 * b.a22;
    }

    public static void multTransAB(float alpha, FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = alpha * (a.a11 * b.a11 + a.a21 * b.a12);
        c.a12 = alpha * (a.a11 * b.a21 + a.a21 * b.a22);
        c.a21 = alpha * (a.a12 * b.a11 + a.a22 * b.a12);
        c.a22 = alpha * (a.a12 * b.a21 + a.a22 * b.a22);
    }

    public static void multTransB(FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = a.a11 * b.a11 + a.a12 * b.a12;
        c.a12 = a.a11 * b.a21 + a.a12 * b.a22;
        c.a21 = a.a21 * b.a11 + a.a22 * b.a12;
        c.a22 = a.a21 * b.a21 + a.a22 * b.a22;
    }

    public static void multTransB(float alpha, FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = alpha * (a.a11 * b.a11 + a.a12 * b.a12);
        c.a12 = alpha * (a.a11 * b.a21 + a.a12 * b.a22);
        c.a21 = alpha * (a.a21 * b.a11 + a.a22 * b.a12);
        c.a22 = alpha * (a.a21 * b.a21 + a.a22 * b.a22);
    }

    public static void multAdd(FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += a.a11 * b.a11 + a.a12 * b.a21;
        c.a12 += a.a11 * b.a12 + a.a12 * b.a22;
        c.a21 += a.a21 * b.a11 + a.a22 * b.a21;
        c.a22 += a.a21 * b.a12 + a.a22 * b.a22;
    }

    public static void multAdd(float alpha, FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += alpha * (a.a11 * b.a11 + a.a12 * b.a21);
        c.a12 += alpha * (a.a11 * b.a12 + a.a12 * b.a22);
        c.a21 += alpha * (a.a21 * b.a11 + a.a22 * b.a21);
        c.a22 += alpha * (a.a21 * b.a12 + a.a22 * b.a22);
    }

    public static void multAddTransA(FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += a.a11 * b.a11 + a.a21 * b.a21;
        c.a12 += a.a11 * b.a12 + a.a21 * b.a22;
        c.a21 += a.a12 * b.a11 + a.a22 * b.a21;
        c.a22 += a.a12 * b.a12 + a.a22 * b.a22;
    }

    public static void multAddTransA(float alpha, FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += alpha * (a.a11 * b.a11 + a.a21 * b.a21);
        c.a12 += alpha * (a.a11 * b.a12 + a.a21 * b.a22);
        c.a21 += alpha * (a.a12 * b.a11 + a.a22 * b.a21);
        c.a22 += alpha * (a.a12 * b.a12 + a.a22 * b.a22);
    }

    public static void multAddTransAB(FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += a.a11 * b.a11 + a.a21 * b.a12;
        c.a12 += a.a11 * b.a21 + a.a21 * b.a22;
        c.a21 += a.a12 * b.a11 + a.a22 * b.a12;
        c.a22 += a.a12 * b.a21 + a.a22 * b.a22;
    }

    public static void multAddTransAB(float alpha, FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += alpha * (a.a11 * b.a11 + a.a21 * b.a12);
        c.a12 += alpha * (a.a11 * b.a21 + a.a21 * b.a22);
        c.a21 += alpha * (a.a12 * b.a11 + a.a22 * b.a12);
        c.a22 += alpha * (a.a12 * b.a21 + a.a22 * b.a22);
    }

    public static void multAddTransB(FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += a.a11 * b.a11 + a.a12 * b.a12;
        c.a12 += a.a11 * b.a21 + a.a12 * b.a22;
        c.a21 += a.a21 * b.a11 + a.a22 * b.a12;
        c.a22 += a.a21 * b.a21 + a.a22 * b.a22;
    }

    public static void multAddTransB(float alpha, FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += alpha * (a.a11 * b.a11 + a.a12 * b.a12);
        c.a12 += alpha * (a.a11 * b.a21 + a.a12 * b.a22);
        c.a21 += alpha * (a.a21 * b.a11 + a.a22 * b.a12);
        c.a22 += alpha * (a.a21 * b.a21 + a.a22 * b.a22);
    }

    public static void multAddOuter(float alpha, FMatrix2x2 A, float beta, FMatrix2 u, FMatrix2 v, FMatrix2x2 C) {
        C.a11 = alpha * A.a11 + beta * u.a1 * v.a1;
        C.a12 = alpha * A.a12 + beta * u.a1 * v.a2;
        C.a21 = alpha * A.a21 + beta * u.a2 * v.a1;
        C.a22 = alpha * A.a22 + beta * u.a2 * v.a2;
    }

    public static void mult(FMatrix2x2 a, FMatrix2 b, FMatrix2 c) {
        c.a1 = a.a11 * b.a1 + a.a12 * b.a2;
        c.a2 = a.a21 * b.a1 + a.a22 * b.a2;
    }

    public static void mult(FMatrix2 a, FMatrix2x2 b, FMatrix2 c) {
        c.a1 = a.a1 * b.a11 + a.a2 * b.a21;
        c.a2 = a.a1 * b.a12 + a.a2 * b.a22;
    }

    public static float dot(FMatrix2 a, FMatrix2 b) {
        return a.a1 * b.a1 + a.a2 * b.a2;
    }

    public static void setIdentity(FMatrix2x2 a) {
        a.a11 = 1.0f;
        a.a21 = 0.0f;
        a.a12 = 0.0f;
        a.a22 = 1.0f;
    }

    public static boolean invert(FMatrix2x2 a, FMatrix2x2 inv) {
        float a22;
        float scale = 1.0f / CommonOps_FDF2.elementMaxAbs(a);
        float a11 = a.a11 * scale;
        float a12 = a.a12 * scale;
        float a21 = a.a21 * scale;
        float m11 = a22 = a.a22 * scale;
        float m12 = -a21;
        float m21 = -a12;
        float m22 = a11;
        float det = (a11 * m11 + a12 * m12) / scale;
        inv.a11 = m11 / det;
        inv.a12 = m21 / det;
        inv.a21 = m12 / det;
        inv.a22 = m22 / det;
        return !Float.isNaN(det) && !Float.isInfinite(det);
    }

    public static float det(FMatrix2x2 mat) {
        return mat.a11 * mat.a22 - mat.a12 * mat.a21;
    }

    public static boolean cholL(FMatrix2x2 A) {
        A.a11 = (float)Math.sqrt(A.a11);
        A.a12 = 0.0f;
        A.a21 /= A.a11;
        A.a22 = (float)Math.sqrt(A.a22 - A.a21 * A.a21);
        return !UtilEjml.isUncountable(A.a22);
    }

    public static boolean cholU(FMatrix2x2 A) {
        A.a11 = (float)Math.sqrt(A.a11);
        A.a21 = 0.0f;
        A.a12 /= A.a11;
        A.a22 = (float)Math.sqrt(A.a22 - A.a12 * A.a12);
        return !UtilEjml.isUncountable(A.a22);
    }

    public static float trace(FMatrix2x2 a) {
        return a.a11 + a.a22;
    }

    public static void diag(FMatrix2x2 input, FMatrix2 out) {
        out.a1 = input.a11;
        out.a2 = input.a22;
    }

    public static float elementMax(FMatrix2x2 a) {
        float max = a.a11;
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

    public static float elementMax(FMatrix2 a) {
        float max = a.a1;
        if (a.a2 > max) {
            max = a.a2;
        }
        return max;
    }

    public static float elementMaxAbs(FMatrix2x2 a) {
        float max = Math.abs(a.a11);
        float tmp = Math.abs(a.a12);
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

    public static float elementMaxAbs(FMatrix2 a) {
        float max = Math.abs(a.a1);
        float tmp = Math.abs(a.a2);
        if (tmp > max) {
            max = tmp;
        }
        if ((tmp = Math.abs(a.a2)) > max) {
            max = tmp;
        }
        return max;
    }

    public static float elementMin(FMatrix2x2 a) {
        float min = a.a11;
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

    public static float elementMin(FMatrix2 a) {
        float min = a.a1;
        if (a.a2 < min) {
            min = a.a2;
        }
        return min;
    }

    public static float elementMinAbs(FMatrix2x2 a) {
        float min = Math.abs(a.a11);
        float tmp = Math.abs(a.a12);
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

    public static float elementMinAbs(FMatrix2 a) {
        float min = Math.abs(a.a1);
        float tmp = Math.abs(a.a1);
        if (tmp < min) {
            min = tmp;
        }
        if ((tmp = Math.abs(a.a2)) < min) {
            min = tmp;
        }
        return min;
    }

    public static void elementMult(FMatrix2x2 a, FMatrix2x2 b) {
        a.a11 *= b.a11;
        a.a12 *= b.a12;
        a.a21 *= b.a21;
        a.a22 *= b.a22;
    }

    public static void elementMult(FMatrix2 a, FMatrix2 b) {
        a.a1 *= b.a1;
        a.a2 *= b.a2;
    }

    public static void elementMult(FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        c.a11 = a.a11 * b.a11;
        c.a12 = a.a12 * b.a12;
        c.a21 = a.a21 * b.a21;
        c.a22 = a.a22 * b.a22;
    }

    public static void elementMult(FMatrix2 a, FMatrix2 b, FMatrix2 c) {
        c.a1 = a.a1 * b.a1;
        c.a2 = a.a2 * b.a2;
    }

    public static void elementDiv(FMatrix2x2 a, FMatrix2x2 b) {
        a.a11 /= b.a11;
        a.a12 /= b.a12;
        a.a21 /= b.a21;
        a.a22 /= b.a22;
    }

    public static void elementDiv(FMatrix2 a, FMatrix2 b) {
        a.a1 /= b.a1;
        a.a2 /= b.a2;
    }

    public static void elementDiv(FMatrix2x2 a, FMatrix2x2 b, FMatrix2x2 c) {
        c.a11 = a.a11 / b.a11;
        c.a12 = a.a12 / b.a12;
        c.a21 = a.a21 / b.a21;
        c.a22 = a.a22 / b.a22;
    }

    public static void elementDiv(FMatrix2 a, FMatrix2 b, FMatrix2 c) {
        c.a1 = a.a1 / b.a1;
        c.a2 = a.a2 / b.a2;
    }

    public static void scale(float alpha, FMatrix2x2 a) {
        a.a11 *= alpha;
        a.a12 *= alpha;
        a.a21 *= alpha;
        a.a22 *= alpha;
    }

    public static void scale(float alpha, FMatrix2 a) {
        a.a1 *= alpha;
        a.a2 *= alpha;
    }

    public static void scale(float alpha, FMatrix2x2 a, FMatrix2x2 b) {
        b.a11 = a.a11 * alpha;
        b.a12 = a.a12 * alpha;
        b.a21 = a.a21 * alpha;
        b.a22 = a.a22 * alpha;
    }

    public static void scale(float alpha, FMatrix2 a, FMatrix2 b) {
        b.a1 = a.a1 * alpha;
        b.a2 = a.a2 * alpha;
    }

    public static void divide(FMatrix2x2 a, float alpha) {
        a.a11 /= alpha;
        a.a12 /= alpha;
        a.a21 /= alpha;
        a.a22 /= alpha;
    }

    public static void divide(FMatrix2 a, float alpha) {
        a.a1 /= alpha;
        a.a2 /= alpha;
    }

    public static void divide(FMatrix2x2 a, float alpha, FMatrix2x2 b) {
        b.a11 = a.a11 / alpha;
        b.a12 = a.a12 / alpha;
        b.a21 = a.a21 / alpha;
        b.a22 = a.a22 / alpha;
    }

    public static void divide(FMatrix2 a, float alpha, FMatrix2 b) {
        b.a1 = a.a1 / alpha;
        b.a2 = a.a2 / alpha;
    }

    public static void changeSign(FMatrix2x2 a) {
        a.a11 = -a.a11;
        a.a12 = -a.a12;
        a.a21 = -a.a21;
        a.a22 = -a.a22;
    }

    public static void changeSign(FMatrix2 a) {
        a.a1 = -a.a1;
        a.a2 = -a.a2;
    }

    public static void fill(FMatrix2x2 a, float v) {
        a.a11 = v;
        a.a12 = v;
        a.a21 = v;
        a.a22 = v;
    }

    public static void fill(FMatrix2 a, float v) {
        a.a1 = v;
        a.a2 = v;
    }

    public static FMatrix2 extractRow(FMatrix2x2 a, int row, FMatrix2 out) {
        if (out == null) {
            out = new FMatrix2();
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

    public static FMatrix2 extractColumn(FMatrix2x2 a, int column, FMatrix2 out) {
        if (out == null) {
            out = new FMatrix2();
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

