/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.UtilEjml;
import org.ejml.data.ComplexPolar_F32;
import org.ejml.data.Complex_F32;

public class ComplexMath_F32 {
    public static void conj(Complex_F32 input, Complex_F32 conj) {
        conj.real = input.real;
        conj.imaginary = -input.imaginary;
    }

    public static void plus(Complex_F32 a, Complex_F32 b, Complex_F32 result) {
        result.real = a.real + b.real;
        result.imaginary = a.imaginary + b.imaginary;
    }

    public static void minus(Complex_F32 a, Complex_F32 b, Complex_F32 result) {
        result.real = a.real - b.real;
        result.imaginary = a.imaginary - b.imaginary;
    }

    public static void multiply(Complex_F32 a, Complex_F32 b, Complex_F32 result) {
        result.real = a.real * b.real - a.imaginary * b.imaginary;
        result.imaginary = a.real * b.imaginary + a.imaginary * b.real;
    }

    public static void divide(Complex_F32 a, Complex_F32 b, Complex_F32 result) {
        float norm = b.getMagnitude2();
        result.real = (a.real * b.real + a.imaginary * b.imaginary) / norm;
        result.imaginary = (a.imaginary * b.real - a.real * b.imaginary) / norm;
    }

    public static void convert(Complex_F32 input, ComplexPolar_F32 output) {
        output.r = input.getMagnitude();
        output.theta = (float)Math.atan2(input.imaginary, input.real);
    }

    public static void convert(ComplexPolar_F32 input, Complex_F32 output) {
        output.real = input.r * (float)Math.cos(input.theta);
        output.imaginary = input.r * (float)Math.sin(input.theta);
    }

    public static void multiply(ComplexPolar_F32 a, ComplexPolar_F32 b, ComplexPolar_F32 result) {
        result.r = a.r * b.r;
        result.theta = a.theta + b.theta;
    }

    public static void divide(ComplexPolar_F32 a, ComplexPolar_F32 b, ComplexPolar_F32 result) {
        result.r = a.r / b.r;
        result.theta = a.theta - b.theta;
    }

    public static void pow(ComplexPolar_F32 a, int N, ComplexPolar_F32 result) {
        result.r = (float)Math.pow(a.r, N);
        result.theta = (float)N * a.theta;
    }

    public static void root(ComplexPolar_F32 a, int N, int k, ComplexPolar_F32 result) {
        result.r = (float)Math.pow(a.r, 1.0f / (float)N);
        result.theta = (a.theta + 2.0f * (float)k * UtilEjml.F_PI) / (float)N;
    }

    public static void root(Complex_F32 a, int N, int k, Complex_F32 result) {
        float r = a.getMagnitude();
        float theta = (float)Math.atan2(a.imaginary, a.real);
        r = (float)Math.pow(r, 1.0f / (float)N);
        theta = (theta + 2.0f * (float)k * UtilEjml.F_PI) / (float)N;
        result.real = r * (float)Math.cos(theta);
        result.imaginary = r * (float)Math.sin(theta);
    }

    public static void sqrt(Complex_F32 input, Complex_F32 root) {
        float r = input.getMagnitude();
        float a = input.real;
        root.real = (float)Math.sqrt((r + a) / 2.0f);
        root.imaginary = (float)Math.sqrt((r - a) / 2.0f);
        if (input.imaginary < 0.0f) {
            root.imaginary = -root.imaginary;
        }
    }
}

