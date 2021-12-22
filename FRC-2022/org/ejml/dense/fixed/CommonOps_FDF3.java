/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.dense.fixed;

import org.ejml.UtilEjml;
import org.ejml.data.FMatrix3;
import org.ejml.data.FMatrix3x3;

public class CommonOps_FDF3 {
    public static void add(FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        c.a11 = a.a11 + b.a11;
        c.a12 = a.a12 + b.a12;
        c.a13 = a.a13 + b.a13;
        c.a21 = a.a21 + b.a21;
        c.a22 = a.a22 + b.a22;
        c.a23 = a.a23 + b.a23;
        c.a31 = a.a31 + b.a31;
        c.a32 = a.a32 + b.a32;
        c.a33 = a.a33 + b.a33;
    }

    public static void add(FMatrix3 a, FMatrix3 b, FMatrix3 c) {
        c.a1 = a.a1 + b.a1;
        c.a2 = a.a2 + b.a2;
        c.a3 = a.a3 + b.a3;
    }

    public static void addEquals(FMatrix3x3 a, FMatrix3x3 b) {
        a.a11 += b.a11;
        a.a12 += b.a12;
        a.a13 += b.a13;
        a.a21 += b.a21;
        a.a22 += b.a22;
        a.a23 += b.a23;
        a.a31 += b.a31;
        a.a32 += b.a32;
        a.a33 += b.a33;
    }

    public static void addEquals(FMatrix3 a, FMatrix3 b) {
        a.a1 += b.a1;
        a.a2 += b.a2;
        a.a3 += b.a3;
    }

    public static void subtract(FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        c.a11 = a.a11 - b.a11;
        c.a12 = a.a12 - b.a12;
        c.a13 = a.a13 - b.a13;
        c.a21 = a.a21 - b.a21;
        c.a22 = a.a22 - b.a22;
        c.a23 = a.a23 - b.a23;
        c.a31 = a.a31 - b.a31;
        c.a32 = a.a32 - b.a32;
        c.a33 = a.a33 - b.a33;
    }

    public static void subtract(FMatrix3 a, FMatrix3 b, FMatrix3 c) {
        c.a1 = a.a1 - b.a1;
        c.a2 = a.a2 - b.a2;
        c.a3 = a.a3 - b.a3;
    }

    public static void subtractEquals(FMatrix3x3 a, FMatrix3x3 b) {
        a.a11 -= b.a11;
        a.a12 -= b.a12;
        a.a13 -= b.a13;
        a.a21 -= b.a21;
        a.a22 -= b.a22;
        a.a23 -= b.a23;
        a.a31 -= b.a31;
        a.a32 -= b.a32;
        a.a33 -= b.a33;
    }

    public static void subtractEquals(FMatrix3 a, FMatrix3 b) {
        a.a1 -= b.a1;
        a.a2 -= b.a2;
        a.a3 -= b.a3;
    }

    public static void transpose(FMatrix3x3 m) {
        float tmp = m.a12;
        m.a12 = m.a21;
        m.a21 = tmp;
        tmp = m.a13;
        m.a13 = m.a31;
        m.a31 = tmp;
        tmp = m.a23;
        m.a23 = m.a32;
        m.a32 = tmp;
    }

    public static FMatrix3x3 transpose(FMatrix3x3 input, FMatrix3x3 output) {
        if (input == null) {
            input = new FMatrix3x3();
        }
        UtilEjml.checkSameInstance(input, output);
        output.a11 = input.a11;
        output.a12 = input.a21;
        output.a13 = input.a31;
        output.a21 = input.a12;
        output.a22 = input.a22;
        output.a23 = input.a32;
        output.a31 = input.a13;
        output.a32 = input.a23;
        output.a33 = input.a33;
        return output;
    }

    public static void mult(FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = a.a11 * b.a11 + a.a12 * b.a21 + a.a13 * b.a31;
        c.a12 = a.a11 * b.a12 + a.a12 * b.a22 + a.a13 * b.a32;
        c.a13 = a.a11 * b.a13 + a.a12 * b.a23 + a.a13 * b.a33;
        c.a21 = a.a21 * b.a11 + a.a22 * b.a21 + a.a23 * b.a31;
        c.a22 = a.a21 * b.a12 + a.a22 * b.a22 + a.a23 * b.a32;
        c.a23 = a.a21 * b.a13 + a.a22 * b.a23 + a.a23 * b.a33;
        c.a31 = a.a31 * b.a11 + a.a32 * b.a21 + a.a33 * b.a31;
        c.a32 = a.a31 * b.a12 + a.a32 * b.a22 + a.a33 * b.a32;
        c.a33 = a.a31 * b.a13 + a.a32 * b.a23 + a.a33 * b.a33;
    }

    public static void mult(float alpha, FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = alpha * (a.a11 * b.a11 + a.a12 * b.a21 + a.a13 * b.a31);
        c.a12 = alpha * (a.a11 * b.a12 + a.a12 * b.a22 + a.a13 * b.a32);
        c.a13 = alpha * (a.a11 * b.a13 + a.a12 * b.a23 + a.a13 * b.a33);
        c.a21 = alpha * (a.a21 * b.a11 + a.a22 * b.a21 + a.a23 * b.a31);
        c.a22 = alpha * (a.a21 * b.a12 + a.a22 * b.a22 + a.a23 * b.a32);
        c.a23 = alpha * (a.a21 * b.a13 + a.a22 * b.a23 + a.a23 * b.a33);
        c.a31 = alpha * (a.a31 * b.a11 + a.a32 * b.a21 + a.a33 * b.a31);
        c.a32 = alpha * (a.a31 * b.a12 + a.a32 * b.a22 + a.a33 * b.a32);
        c.a33 = alpha * (a.a31 * b.a13 + a.a32 * b.a23 + a.a33 * b.a33);
    }

    public static void multTransA(FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = a.a11 * b.a11 + a.a21 * b.a21 + a.a31 * b.a31;
        c.a12 = a.a11 * b.a12 + a.a21 * b.a22 + a.a31 * b.a32;
        c.a13 = a.a11 * b.a13 + a.a21 * b.a23 + a.a31 * b.a33;
        c.a21 = a.a12 * b.a11 + a.a22 * b.a21 + a.a32 * b.a31;
        c.a22 = a.a12 * b.a12 + a.a22 * b.a22 + a.a32 * b.a32;
        c.a23 = a.a12 * b.a13 + a.a22 * b.a23 + a.a32 * b.a33;
        c.a31 = a.a13 * b.a11 + a.a23 * b.a21 + a.a33 * b.a31;
        c.a32 = a.a13 * b.a12 + a.a23 * b.a22 + a.a33 * b.a32;
        c.a33 = a.a13 * b.a13 + a.a23 * b.a23 + a.a33 * b.a33;
    }

    public static void multTransA(float alpha, FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = alpha * (a.a11 * b.a11 + a.a21 * b.a21 + a.a31 * b.a31);
        c.a12 = alpha * (a.a11 * b.a12 + a.a21 * b.a22 + a.a31 * b.a32);
        c.a13 = alpha * (a.a11 * b.a13 + a.a21 * b.a23 + a.a31 * b.a33);
        c.a21 = alpha * (a.a12 * b.a11 + a.a22 * b.a21 + a.a32 * b.a31);
        c.a22 = alpha * (a.a12 * b.a12 + a.a22 * b.a22 + a.a32 * b.a32);
        c.a23 = alpha * (a.a12 * b.a13 + a.a22 * b.a23 + a.a32 * b.a33);
        c.a31 = alpha * (a.a13 * b.a11 + a.a23 * b.a21 + a.a33 * b.a31);
        c.a32 = alpha * (a.a13 * b.a12 + a.a23 * b.a22 + a.a33 * b.a32);
        c.a33 = alpha * (a.a13 * b.a13 + a.a23 * b.a23 + a.a33 * b.a33);
    }

    public static void multTransAB(FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = a.a11 * b.a11 + a.a21 * b.a12 + a.a31 * b.a13;
        c.a12 = a.a11 * b.a21 + a.a21 * b.a22 + a.a31 * b.a23;
        c.a13 = a.a11 * b.a31 + a.a21 * b.a32 + a.a31 * b.a33;
        c.a21 = a.a12 * b.a11 + a.a22 * b.a12 + a.a32 * b.a13;
        c.a22 = a.a12 * b.a21 + a.a22 * b.a22 + a.a32 * b.a23;
        c.a23 = a.a12 * b.a31 + a.a22 * b.a32 + a.a32 * b.a33;
        c.a31 = a.a13 * b.a11 + a.a23 * b.a12 + a.a33 * b.a13;
        c.a32 = a.a13 * b.a21 + a.a23 * b.a22 + a.a33 * b.a23;
        c.a33 = a.a13 * b.a31 + a.a23 * b.a32 + a.a33 * b.a33;
    }

    public static void multTransAB(float alpha, FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = alpha * (a.a11 * b.a11 + a.a21 * b.a12 + a.a31 * b.a13);
        c.a12 = alpha * (a.a11 * b.a21 + a.a21 * b.a22 + a.a31 * b.a23);
        c.a13 = alpha * (a.a11 * b.a31 + a.a21 * b.a32 + a.a31 * b.a33);
        c.a21 = alpha * (a.a12 * b.a11 + a.a22 * b.a12 + a.a32 * b.a13);
        c.a22 = alpha * (a.a12 * b.a21 + a.a22 * b.a22 + a.a32 * b.a23);
        c.a23 = alpha * (a.a12 * b.a31 + a.a22 * b.a32 + a.a32 * b.a33);
        c.a31 = alpha * (a.a13 * b.a11 + a.a23 * b.a12 + a.a33 * b.a13);
        c.a32 = alpha * (a.a13 * b.a21 + a.a23 * b.a22 + a.a33 * b.a23);
        c.a33 = alpha * (a.a13 * b.a31 + a.a23 * b.a32 + a.a33 * b.a33);
    }

    public static void multTransB(FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = a.a11 * b.a11 + a.a12 * b.a12 + a.a13 * b.a13;
        c.a12 = a.a11 * b.a21 + a.a12 * b.a22 + a.a13 * b.a23;
        c.a13 = a.a11 * b.a31 + a.a12 * b.a32 + a.a13 * b.a33;
        c.a21 = a.a21 * b.a11 + a.a22 * b.a12 + a.a23 * b.a13;
        c.a22 = a.a21 * b.a21 + a.a22 * b.a22 + a.a23 * b.a23;
        c.a23 = a.a21 * b.a31 + a.a22 * b.a32 + a.a23 * b.a33;
        c.a31 = a.a31 * b.a11 + a.a32 * b.a12 + a.a33 * b.a13;
        c.a32 = a.a31 * b.a21 + a.a32 * b.a22 + a.a33 * b.a23;
        c.a33 = a.a31 * b.a31 + a.a32 * b.a32 + a.a33 * b.a33;
    }

    public static void multTransB(float alpha, FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 = alpha * (a.a11 * b.a11 + a.a12 * b.a12 + a.a13 * b.a13);
        c.a12 = alpha * (a.a11 * b.a21 + a.a12 * b.a22 + a.a13 * b.a23);
        c.a13 = alpha * (a.a11 * b.a31 + a.a12 * b.a32 + a.a13 * b.a33);
        c.a21 = alpha * (a.a21 * b.a11 + a.a22 * b.a12 + a.a23 * b.a13);
        c.a22 = alpha * (a.a21 * b.a21 + a.a22 * b.a22 + a.a23 * b.a23);
        c.a23 = alpha * (a.a21 * b.a31 + a.a22 * b.a32 + a.a23 * b.a33);
        c.a31 = alpha * (a.a31 * b.a11 + a.a32 * b.a12 + a.a33 * b.a13);
        c.a32 = alpha * (a.a31 * b.a21 + a.a32 * b.a22 + a.a33 * b.a23);
        c.a33 = alpha * (a.a31 * b.a31 + a.a32 * b.a32 + a.a33 * b.a33);
    }

    public static void multAdd(FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += a.a11 * b.a11 + a.a12 * b.a21 + a.a13 * b.a31;
        c.a12 += a.a11 * b.a12 + a.a12 * b.a22 + a.a13 * b.a32;
        c.a13 += a.a11 * b.a13 + a.a12 * b.a23 + a.a13 * b.a33;
        c.a21 += a.a21 * b.a11 + a.a22 * b.a21 + a.a23 * b.a31;
        c.a22 += a.a21 * b.a12 + a.a22 * b.a22 + a.a23 * b.a32;
        c.a23 += a.a21 * b.a13 + a.a22 * b.a23 + a.a23 * b.a33;
        c.a31 += a.a31 * b.a11 + a.a32 * b.a21 + a.a33 * b.a31;
        c.a32 += a.a31 * b.a12 + a.a32 * b.a22 + a.a33 * b.a32;
        c.a33 += a.a31 * b.a13 + a.a32 * b.a23 + a.a33 * b.a33;
    }

    public static void multAdd(float alpha, FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += alpha * (a.a11 * b.a11 + a.a12 * b.a21 + a.a13 * b.a31);
        c.a12 += alpha * (a.a11 * b.a12 + a.a12 * b.a22 + a.a13 * b.a32);
        c.a13 += alpha * (a.a11 * b.a13 + a.a12 * b.a23 + a.a13 * b.a33);
        c.a21 += alpha * (a.a21 * b.a11 + a.a22 * b.a21 + a.a23 * b.a31);
        c.a22 += alpha * (a.a21 * b.a12 + a.a22 * b.a22 + a.a23 * b.a32);
        c.a23 += alpha * (a.a21 * b.a13 + a.a22 * b.a23 + a.a23 * b.a33);
        c.a31 += alpha * (a.a31 * b.a11 + a.a32 * b.a21 + a.a33 * b.a31);
        c.a32 += alpha * (a.a31 * b.a12 + a.a32 * b.a22 + a.a33 * b.a32);
        c.a33 += alpha * (a.a31 * b.a13 + a.a32 * b.a23 + a.a33 * b.a33);
    }

    public static void multAddTransA(FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += a.a11 * b.a11 + a.a21 * b.a21 + a.a31 * b.a31;
        c.a12 += a.a11 * b.a12 + a.a21 * b.a22 + a.a31 * b.a32;
        c.a13 += a.a11 * b.a13 + a.a21 * b.a23 + a.a31 * b.a33;
        c.a21 += a.a12 * b.a11 + a.a22 * b.a21 + a.a32 * b.a31;
        c.a22 += a.a12 * b.a12 + a.a22 * b.a22 + a.a32 * b.a32;
        c.a23 += a.a12 * b.a13 + a.a22 * b.a23 + a.a32 * b.a33;
        c.a31 += a.a13 * b.a11 + a.a23 * b.a21 + a.a33 * b.a31;
        c.a32 += a.a13 * b.a12 + a.a23 * b.a22 + a.a33 * b.a32;
        c.a33 += a.a13 * b.a13 + a.a23 * b.a23 + a.a33 * b.a33;
    }

    public static void multAddTransA(float alpha, FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += alpha * (a.a11 * b.a11 + a.a21 * b.a21 + a.a31 * b.a31);
        c.a12 += alpha * (a.a11 * b.a12 + a.a21 * b.a22 + a.a31 * b.a32);
        c.a13 += alpha * (a.a11 * b.a13 + a.a21 * b.a23 + a.a31 * b.a33);
        c.a21 += alpha * (a.a12 * b.a11 + a.a22 * b.a21 + a.a32 * b.a31);
        c.a22 += alpha * (a.a12 * b.a12 + a.a22 * b.a22 + a.a32 * b.a32);
        c.a23 += alpha * (a.a12 * b.a13 + a.a22 * b.a23 + a.a32 * b.a33);
        c.a31 += alpha * (a.a13 * b.a11 + a.a23 * b.a21 + a.a33 * b.a31);
        c.a32 += alpha * (a.a13 * b.a12 + a.a23 * b.a22 + a.a33 * b.a32);
        c.a33 += alpha * (a.a13 * b.a13 + a.a23 * b.a23 + a.a33 * b.a33);
    }

    public static void multAddTransAB(FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += a.a11 * b.a11 + a.a21 * b.a12 + a.a31 * b.a13;
        c.a12 += a.a11 * b.a21 + a.a21 * b.a22 + a.a31 * b.a23;
        c.a13 += a.a11 * b.a31 + a.a21 * b.a32 + a.a31 * b.a33;
        c.a21 += a.a12 * b.a11 + a.a22 * b.a12 + a.a32 * b.a13;
        c.a22 += a.a12 * b.a21 + a.a22 * b.a22 + a.a32 * b.a23;
        c.a23 += a.a12 * b.a31 + a.a22 * b.a32 + a.a32 * b.a33;
        c.a31 += a.a13 * b.a11 + a.a23 * b.a12 + a.a33 * b.a13;
        c.a32 += a.a13 * b.a21 + a.a23 * b.a22 + a.a33 * b.a23;
        c.a33 += a.a13 * b.a31 + a.a23 * b.a32 + a.a33 * b.a33;
    }

    public static void multAddTransAB(float alpha, FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += alpha * (a.a11 * b.a11 + a.a21 * b.a12 + a.a31 * b.a13);
        c.a12 += alpha * (a.a11 * b.a21 + a.a21 * b.a22 + a.a31 * b.a23);
        c.a13 += alpha * (a.a11 * b.a31 + a.a21 * b.a32 + a.a31 * b.a33);
        c.a21 += alpha * (a.a12 * b.a11 + a.a22 * b.a12 + a.a32 * b.a13);
        c.a22 += alpha * (a.a12 * b.a21 + a.a22 * b.a22 + a.a32 * b.a23);
        c.a23 += alpha * (a.a12 * b.a31 + a.a22 * b.a32 + a.a32 * b.a33);
        c.a31 += alpha * (a.a13 * b.a11 + a.a23 * b.a12 + a.a33 * b.a13);
        c.a32 += alpha * (a.a13 * b.a21 + a.a23 * b.a22 + a.a33 * b.a23);
        c.a33 += alpha * (a.a13 * b.a31 + a.a23 * b.a32 + a.a33 * b.a33);
    }

    public static void multAddTransB(FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += a.a11 * b.a11 + a.a12 * b.a12 + a.a13 * b.a13;
        c.a12 += a.a11 * b.a21 + a.a12 * b.a22 + a.a13 * b.a23;
        c.a13 += a.a11 * b.a31 + a.a12 * b.a32 + a.a13 * b.a33;
        c.a21 += a.a21 * b.a11 + a.a22 * b.a12 + a.a23 * b.a13;
        c.a22 += a.a21 * b.a21 + a.a22 * b.a22 + a.a23 * b.a23;
        c.a23 += a.a21 * b.a31 + a.a22 * b.a32 + a.a23 * b.a33;
        c.a31 += a.a31 * b.a11 + a.a32 * b.a12 + a.a33 * b.a13;
        c.a32 += a.a31 * b.a21 + a.a32 * b.a22 + a.a33 * b.a23;
        c.a33 += a.a31 * b.a31 + a.a32 * b.a32 + a.a33 * b.a33;
    }

    public static void multAddTransB(float alpha, FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        UtilEjml.checkSameInstance(a, c);
        UtilEjml.checkSameInstance(b, c);
        c.a11 += alpha * (a.a11 * b.a11 + a.a12 * b.a12 + a.a13 * b.a13);
        c.a12 += alpha * (a.a11 * b.a21 + a.a12 * b.a22 + a.a13 * b.a23);
        c.a13 += alpha * (a.a11 * b.a31 + a.a12 * b.a32 + a.a13 * b.a33);
        c.a21 += alpha * (a.a21 * b.a11 + a.a22 * b.a12 + a.a23 * b.a13);
        c.a22 += alpha * (a.a21 * b.a21 + a.a22 * b.a22 + a.a23 * b.a23);
        c.a23 += alpha * (a.a21 * b.a31 + a.a22 * b.a32 + a.a23 * b.a33);
        c.a31 += alpha * (a.a31 * b.a11 + a.a32 * b.a12 + a.a33 * b.a13);
        c.a32 += alpha * (a.a31 * b.a21 + a.a32 * b.a22 + a.a33 * b.a23);
        c.a33 += alpha * (a.a31 * b.a31 + a.a32 * b.a32 + a.a33 * b.a33);
    }

    public static void multAddOuter(float alpha, FMatrix3x3 A, float beta, FMatrix3 u, FMatrix3 v, FMatrix3x3 C) {
        C.a11 = alpha * A.a11 + beta * u.a1 * v.a1;
        C.a12 = alpha * A.a12 + beta * u.a1 * v.a2;
        C.a13 = alpha * A.a13 + beta * u.a1 * v.a3;
        C.a21 = alpha * A.a21 + beta * u.a2 * v.a1;
        C.a22 = alpha * A.a22 + beta * u.a2 * v.a2;
        C.a23 = alpha * A.a23 + beta * u.a2 * v.a3;
        C.a31 = alpha * A.a31 + beta * u.a3 * v.a1;
        C.a32 = alpha * A.a32 + beta * u.a3 * v.a2;
        C.a33 = alpha * A.a33 + beta * u.a3 * v.a3;
    }

    public static void mult(FMatrix3x3 a, FMatrix3 b, FMatrix3 c) {
        c.a1 = a.a11 * b.a1 + a.a12 * b.a2 + a.a13 * b.a3;
        c.a2 = a.a21 * b.a1 + a.a22 * b.a2 + a.a23 * b.a3;
        c.a3 = a.a31 * b.a1 + a.a32 * b.a2 + a.a33 * b.a3;
    }

    public static void mult(FMatrix3 a, FMatrix3x3 b, FMatrix3 c) {
        c.a1 = a.a1 * b.a11 + a.a2 * b.a21 + a.a3 * b.a31;
        c.a2 = a.a1 * b.a12 + a.a2 * b.a22 + a.a3 * b.a32;
        c.a3 = a.a1 * b.a13 + a.a2 * b.a23 + a.a3 * b.a33;
    }

    public static float dot(FMatrix3 a, FMatrix3 b) {
        return a.a1 * b.a1 + a.a2 * b.a2 + a.a3 * b.a3;
    }

    public static void setIdentity(FMatrix3x3 a) {
        a.a11 = 1.0f;
        a.a21 = 0.0f;
        a.a31 = 0.0f;
        a.a12 = 0.0f;
        a.a22 = 1.0f;
        a.a32 = 0.0f;
        a.a13 = 0.0f;
        a.a23 = 0.0f;
        a.a33 = 1.0f;
    }

    public static boolean invert(FMatrix3x3 a, FMatrix3x3 inv) {
        float scale = 1.0f / CommonOps_FDF3.elementMaxAbs(a);
        float a11 = a.a11 * scale;
        float a12 = a.a12 * scale;
        float a13 = a.a13 * scale;
        float a21 = a.a21 * scale;
        float a22 = a.a22 * scale;
        float a23 = a.a23 * scale;
        float a31 = a.a31 * scale;
        float a32 = a.a32 * scale;
        float a33 = a.a33 * scale;
        float m11 = a22 * a33 - a23 * a32;
        float m12 = -(a21 * a33 - a23 * a31);
        float m13 = a21 * a32 - a22 * a31;
        float m21 = -(a12 * a33 - a13 * a32);
        float m22 = a11 * a33 - a13 * a31;
        float m23 = -(a11 * a32 - a12 * a31);
        float m31 = a12 * a23 - a13 * a22;
        float m32 = -(a11 * a23 - a13 * a21);
        float m33 = a11 * a22 - a12 * a21;
        float det = (a11 * m11 + a12 * m12 + a13 * m13) / scale;
        inv.a11 = m11 / det;
        inv.a12 = m21 / det;
        inv.a13 = m31 / det;
        inv.a21 = m12 / det;
        inv.a22 = m22 / det;
        inv.a23 = m32 / det;
        inv.a31 = m13 / det;
        inv.a32 = m23 / det;
        inv.a33 = m33 / det;
        return !Float.isNaN(det) && !Float.isInfinite(det);
    }

    public static float det(FMatrix3x3 mat) {
        float a = mat.a11 * (mat.a22 * mat.a33 - mat.a23 * mat.a32);
        float b = mat.a12 * (mat.a21 * mat.a33 - mat.a23 * mat.a31);
        float c = mat.a13 * (mat.a21 * mat.a32 - mat.a31 * mat.a22);
        return a - b + c;
    }

    public static boolean cholL(FMatrix3x3 A) {
        A.a11 = (float)Math.sqrt(A.a11);
        A.a12 = 0.0f;
        A.a13 = 0.0f;
        A.a21 /= A.a11;
        A.a22 = (float)Math.sqrt(A.a22 - A.a21 * A.a21);
        A.a23 = 0.0f;
        A.a31 /= A.a11;
        A.a32 = (A.a32 - A.a31 * A.a21) / A.a22;
        A.a33 = (float)Math.sqrt(A.a33 - A.a31 * A.a31 - A.a32 * A.a32);
        return !UtilEjml.isUncountable(A.a33);
    }

    public static boolean cholU(FMatrix3x3 A) {
        A.a11 = (float)Math.sqrt(A.a11);
        A.a21 = 0.0f;
        A.a31 = 0.0f;
        A.a12 /= A.a11;
        A.a22 = (float)Math.sqrt(A.a22 - A.a12 * A.a12);
        A.a32 = 0.0f;
        A.a13 /= A.a11;
        A.a23 = (A.a23 - A.a12 * A.a13) / A.a22;
        A.a33 = (float)Math.sqrt(A.a33 - A.a13 * A.a13 - A.a23 * A.a23);
        return !UtilEjml.isUncountable(A.a33);
    }

    public static float trace(FMatrix3x3 a) {
        return a.a11 + a.a22 + a.a33;
    }

    public static void diag(FMatrix3x3 input, FMatrix3 out) {
        out.a1 = input.a11;
        out.a2 = input.a22;
        out.a3 = input.a33;
    }

    public static float elementMax(FMatrix3x3 a) {
        float max = a.a11;
        if (a.a12 > max) {
            max = a.a12;
        }
        if (a.a13 > max) {
            max = a.a13;
        }
        if (a.a21 > max) {
            max = a.a21;
        }
        if (a.a22 > max) {
            max = a.a22;
        }
        if (a.a23 > max) {
            max = a.a23;
        }
        if (a.a31 > max) {
            max = a.a31;
        }
        if (a.a32 > max) {
            max = a.a32;
        }
        if (a.a33 > max) {
            max = a.a33;
        }
        return max;
    }

    public static float elementMax(FMatrix3 a) {
        float max = a.a1;
        if (a.a2 > max) {
            max = a.a2;
        }
        if (a.a3 > max) {
            max = a.a3;
        }
        return max;
    }

    public static float elementMaxAbs(FMatrix3x3 a) {
        float max = Math.abs(a.a11);
        float tmp = Math.abs(a.a12);
        if (tmp > max) {
            max = tmp;
        }
        if ((tmp = Math.abs(a.a13)) > max) {
            max = tmp;
        }
        if ((tmp = Math.abs(a.a21)) > max) {
            max = tmp;
        }
        if ((tmp = Math.abs(a.a22)) > max) {
            max = tmp;
        }
        if ((tmp = Math.abs(a.a23)) > max) {
            max = tmp;
        }
        if ((tmp = Math.abs(a.a31)) > max) {
            max = tmp;
        }
        if ((tmp = Math.abs(a.a32)) > max) {
            max = tmp;
        }
        if ((tmp = Math.abs(a.a33)) > max) {
            max = tmp;
        }
        return max;
    }

    public static float elementMaxAbs(FMatrix3 a) {
        float max = Math.abs(a.a1);
        float tmp = Math.abs(a.a2);
        if (tmp > max) {
            max = tmp;
        }
        if ((tmp = Math.abs(a.a2)) > max) {
            max = tmp;
        }
        if ((tmp = Math.abs(a.a3)) > max) {
            max = tmp;
        }
        return max;
    }

    public static float elementMin(FMatrix3x3 a) {
        float min = a.a11;
        if (a.a12 < min) {
            min = a.a12;
        }
        if (a.a13 < min) {
            min = a.a13;
        }
        if (a.a21 < min) {
            min = a.a21;
        }
        if (a.a22 < min) {
            min = a.a22;
        }
        if (a.a23 < min) {
            min = a.a23;
        }
        if (a.a31 < min) {
            min = a.a31;
        }
        if (a.a32 < min) {
            min = a.a32;
        }
        if (a.a33 < min) {
            min = a.a33;
        }
        return min;
    }

    public static float elementMin(FMatrix3 a) {
        float min = a.a1;
        if (a.a2 < min) {
            min = a.a2;
        }
        if (a.a3 < min) {
            min = a.a3;
        }
        return min;
    }

    public static float elementMinAbs(FMatrix3x3 a) {
        float min = Math.abs(a.a11);
        float tmp = Math.abs(a.a12);
        if (tmp < min) {
            min = tmp;
        }
        if ((tmp = Math.abs(a.a13)) < min) {
            min = tmp;
        }
        if ((tmp = Math.abs(a.a21)) < min) {
            min = tmp;
        }
        if ((tmp = Math.abs(a.a22)) < min) {
            min = tmp;
        }
        if ((tmp = Math.abs(a.a23)) < min) {
            min = tmp;
        }
        if ((tmp = Math.abs(a.a31)) < min) {
            min = tmp;
        }
        if ((tmp = Math.abs(a.a32)) < min) {
            min = tmp;
        }
        if ((tmp = Math.abs(a.a33)) < min) {
            min = tmp;
        }
        return min;
    }

    public static float elementMinAbs(FMatrix3 a) {
        float min = Math.abs(a.a1);
        float tmp = Math.abs(a.a1);
        if (tmp < min) {
            min = tmp;
        }
        if ((tmp = Math.abs(a.a2)) < min) {
            min = tmp;
        }
        if ((tmp = Math.abs(a.a3)) < min) {
            min = tmp;
        }
        return min;
    }

    public static void elementMult(FMatrix3x3 a, FMatrix3x3 b) {
        a.a11 *= b.a11;
        a.a12 *= b.a12;
        a.a13 *= b.a13;
        a.a21 *= b.a21;
        a.a22 *= b.a22;
        a.a23 *= b.a23;
        a.a31 *= b.a31;
        a.a32 *= b.a32;
        a.a33 *= b.a33;
    }

    public static void elementMult(FMatrix3 a, FMatrix3 b) {
        a.a1 *= b.a1;
        a.a2 *= b.a2;
        a.a3 *= b.a3;
    }

    public static void elementMult(FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        c.a11 = a.a11 * b.a11;
        c.a12 = a.a12 * b.a12;
        c.a13 = a.a13 * b.a13;
        c.a21 = a.a21 * b.a21;
        c.a22 = a.a22 * b.a22;
        c.a23 = a.a23 * b.a23;
        c.a31 = a.a31 * b.a31;
        c.a32 = a.a32 * b.a32;
        c.a33 = a.a33 * b.a33;
    }

    public static void elementMult(FMatrix3 a, FMatrix3 b, FMatrix3 c) {
        c.a1 = a.a1 * b.a1;
        c.a2 = a.a2 * b.a2;
        c.a3 = a.a3 * b.a3;
    }

    public static void elementDiv(FMatrix3x3 a, FMatrix3x3 b) {
        a.a11 /= b.a11;
        a.a12 /= b.a12;
        a.a13 /= b.a13;
        a.a21 /= b.a21;
        a.a22 /= b.a22;
        a.a23 /= b.a23;
        a.a31 /= b.a31;
        a.a32 /= b.a32;
        a.a33 /= b.a33;
    }

    public static void elementDiv(FMatrix3 a, FMatrix3 b) {
        a.a1 /= b.a1;
        a.a2 /= b.a2;
        a.a3 /= b.a3;
    }

    public static void elementDiv(FMatrix3x3 a, FMatrix3x3 b, FMatrix3x3 c) {
        c.a11 = a.a11 / b.a11;
        c.a12 = a.a12 / b.a12;
        c.a13 = a.a13 / b.a13;
        c.a21 = a.a21 / b.a21;
        c.a22 = a.a22 / b.a22;
        c.a23 = a.a23 / b.a23;
        c.a31 = a.a31 / b.a31;
        c.a32 = a.a32 / b.a32;
        c.a33 = a.a33 / b.a33;
    }

    public static void elementDiv(FMatrix3 a, FMatrix3 b, FMatrix3 c) {
        c.a1 = a.a1 / b.a1;
        c.a2 = a.a2 / b.a2;
        c.a3 = a.a3 / b.a3;
    }

    public static void scale(float alpha, FMatrix3x3 a) {
        a.a11 *= alpha;
        a.a12 *= alpha;
        a.a13 *= alpha;
        a.a21 *= alpha;
        a.a22 *= alpha;
        a.a23 *= alpha;
        a.a31 *= alpha;
        a.a32 *= alpha;
        a.a33 *= alpha;
    }

    public static void scale(float alpha, FMatrix3 a) {
        a.a1 *= alpha;
        a.a2 *= alpha;
        a.a3 *= alpha;
    }

    public static void scale(float alpha, FMatrix3x3 a, FMatrix3x3 b) {
        b.a11 = a.a11 * alpha;
        b.a12 = a.a12 * alpha;
        b.a13 = a.a13 * alpha;
        b.a21 = a.a21 * alpha;
        b.a22 = a.a22 * alpha;
        b.a23 = a.a23 * alpha;
        b.a31 = a.a31 * alpha;
        b.a32 = a.a32 * alpha;
        b.a33 = a.a33 * alpha;
    }

    public static void scale(float alpha, FMatrix3 a, FMatrix3 b) {
        b.a1 = a.a1 * alpha;
        b.a2 = a.a2 * alpha;
        b.a3 = a.a3 * alpha;
    }

    public static void divide(FMatrix3x3 a, float alpha) {
        a.a11 /= alpha;
        a.a12 /= alpha;
        a.a13 /= alpha;
        a.a21 /= alpha;
        a.a22 /= alpha;
        a.a23 /= alpha;
        a.a31 /= alpha;
        a.a32 /= alpha;
        a.a33 /= alpha;
    }

    public static void divide(FMatrix3 a, float alpha) {
        a.a1 /= alpha;
        a.a2 /= alpha;
        a.a3 /= alpha;
    }

    public static void divide(FMatrix3x3 a, float alpha, FMatrix3x3 b) {
        b.a11 = a.a11 / alpha;
        b.a12 = a.a12 / alpha;
        b.a13 = a.a13 / alpha;
        b.a21 = a.a21 / alpha;
        b.a22 = a.a22 / alpha;
        b.a23 = a.a23 / alpha;
        b.a31 = a.a31 / alpha;
        b.a32 = a.a32 / alpha;
        b.a33 = a.a33 / alpha;
    }

    public static void divide(FMatrix3 a, float alpha, FMatrix3 b) {
        b.a1 = a.a1 / alpha;
        b.a2 = a.a2 / alpha;
        b.a3 = a.a3 / alpha;
    }

    public static void changeSign(FMatrix3x3 a) {
        a.a11 = -a.a11;
        a.a12 = -a.a12;
        a.a13 = -a.a13;
        a.a21 = -a.a21;
        a.a22 = -a.a22;
        a.a23 = -a.a23;
        a.a31 = -a.a31;
        a.a32 = -a.a32;
        a.a33 = -a.a33;
    }

    public static void changeSign(FMatrix3 a) {
        a.a1 = -a.a1;
        a.a2 = -a.a2;
        a.a3 = -a.a3;
    }

    public static void fill(FMatrix3x3 a, float v) {
        a.a11 = v;
        a.a12 = v;
        a.a13 = v;
        a.a21 = v;
        a.a22 = v;
        a.a23 = v;
        a.a31 = v;
        a.a32 = v;
        a.a33 = v;
    }

    public static void fill(FMatrix3 a, float v) {
        a.a1 = v;
        a.a2 = v;
        a.a3 = v;
    }

    public static FMatrix3 extractRow(FMatrix3x3 a, int row, FMatrix3 out) {
        if (out == null) {
            out = new FMatrix3();
        }
        switch (row) {
            case 0: {
                out.a1 = a.a11;
                out.a2 = a.a12;
                out.a3 = a.a13;
                break;
            }
            case 1: {
                out.a1 = a.a21;
                out.a2 = a.a22;
                out.a3 = a.a23;
                break;
            }
            case 2: {
                out.a1 = a.a31;
                out.a2 = a.a32;
                out.a3 = a.a33;
                break;
            }
            default: {
                throw new IllegalArgumentException("Out of bounds row. row = " + row);
            }
        }
        return out;
    }

    public static FMatrix3 extractColumn(FMatrix3x3 a, int column, FMatrix3 out) {
        if (out == null) {
            out = new FMatrix3();
        }
        switch (column) {
            case 0: {
                out.a1 = a.a11;
                out.a2 = a.a21;
                out.a3 = a.a31;
                break;
            }
            case 1: {
                out.a1 = a.a12;
                out.a2 = a.a22;
                out.a3 = a.a32;
                break;
            }
            case 2: {
                out.a1 = a.a13;
                out.a2 = a.a23;
                out.a3 = a.a33;
                break;
            }
            default: {
                throw new IllegalArgumentException("Out of bounds column. column = " + column);
            }
        }
        return out;
    }
}

