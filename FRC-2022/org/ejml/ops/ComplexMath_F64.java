/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import org.ejml.UtilEjml;
import org.ejml.data.ComplexPolar_F64;
import org.ejml.data.Complex_F64;

public class ComplexMath_F64 {
    public static void conj(Complex_F64 input, Complex_F64 conj) {
        conj.real = input.real;
        conj.imaginary = -input.imaginary;
    }

    public static void plus(Complex_F64 a, Complex_F64 b, Complex_F64 result) {
        result.real = a.real + b.real;
        result.imaginary = a.imaginary + b.imaginary;
    }

    public static void minus(Complex_F64 a, Complex_F64 b, Complex_F64 result) {
        result.real = a.real - b.real;
        result.imaginary = a.imaginary - b.imaginary;
    }

    public static void multiply(Complex_F64 a, Complex_F64 b, Complex_F64 result) {
        result.real = a.real * b.real - a.imaginary * b.imaginary;
        result.imaginary = a.real * b.imaginary + a.imaginary * b.real;
    }

    public static void divide(Complex_F64 a, Complex_F64 b, Complex_F64 result) {
        double norm = b.getMagnitude2();
        result.real = (a.real * b.real + a.imaginary * b.imaginary) / norm;
        result.imaginary = (a.imaginary * b.real - a.real * b.imaginary) / norm;
    }

    public static void convert(Complex_F64 input, ComplexPolar_F64 output) {
        output.r = input.getMagnitude();
        output.theta = Math.atan2(input.imaginary, input.real);
    }

    public static void convert(ComplexPolar_F64 input, Complex_F64 output) {
        output.real = input.r * Math.cos(input.theta);
        output.imaginary = input.r * Math.sin(input.theta);
    }

    public static void multiply(ComplexPolar_F64 a, ComplexPolar_F64 b, ComplexPolar_F64 result) {
        result.r = a.r * b.r;
        result.theta = a.theta + b.theta;
    }

    public static void divide(ComplexPolar_F64 a, ComplexPolar_F64 b, ComplexPolar_F64 result) {
        result.r = a.r / b.r;
        result.theta = a.theta - b.theta;
    }

    public static void pow(ComplexPolar_F64 a, int N, ComplexPolar_F64 result) {
        result.r = Math.pow(a.r, N);
        result.theta = (double)N * a.theta;
    }

    public static void root(ComplexPolar_F64 a, int N, int k, ComplexPolar_F64 result) {
        result.r = Math.pow(a.r, 1.0 / (double)N);
        result.theta = (a.theta + 2.0 * (double)k * UtilEjml.PI) / (double)N;
    }

    public static void root(Complex_F64 a, int N, int k, Complex_F64 result) {
        double r = a.getMagnitude();
        double theta = Math.atan2(a.imaginary, a.real);
        r = Math.pow(r, 1.0 / (double)N);
        theta = (theta + 2.0 * (double)k * UtilEjml.PI) / (double)N;
        result.real = r * Math.cos(theta);
        result.imaginary = r * Math.sin(theta);
    }

    public static void sqrt(Complex_F64 input, Complex_F64 root) {
        double r = input.getMagnitude();
        double a = input.real;
        root.real = Math.sqrt((r + a) / 2.0);
        root.imaginary = Math.sqrt((r - a) / 2.0);
        if (input.imaginary < 0.0) {
            root.imaginary = -root.imaginary;
        }
    }
}

