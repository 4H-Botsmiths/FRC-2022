/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.ops.ComplexMath_F64;

public class Complex_F64 {
    public double real;
    public double imaginary;

    public Complex_F64(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Complex_F64() {
    }

    public double getMagnitude() {
        return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
    }

    public double getMagnitude2() {
        return this.real * this.real + this.imaginary * this.imaginary;
    }

    public void setTo(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public void setTo(Complex_F64 src) {
        this.real = src.real;
        this.imaginary = src.imaginary;
    }

    public boolean isReal() {
        return this.imaginary == 0.0;
    }

    public Complex_F64 plus(Complex_F64 a) {
        Complex_F64 ret = new Complex_F64();
        ComplexMath_F64.plus(this, a, ret);
        return ret;
    }

    public Complex_F64 minus(Complex_F64 a) {
        Complex_F64 ret = new Complex_F64();
        ComplexMath_F64.minus(this, a, ret);
        return ret;
    }

    public Complex_F64 times(Complex_F64 a) {
        Complex_F64 ret = new Complex_F64();
        ComplexMath_F64.multiply(this, a, ret);
        return ret;
    }

    public Complex_F64 divide(Complex_F64 a) {
        Complex_F64 ret = new Complex_F64();
        ComplexMath_F64.divide(this, a, ret);
        return ret;
    }

    public String toString() {
        if (this.imaginary == 0.0) {
            return "" + this.real;
        }
        return this.real + " " + this.imaginary + "i";
    }

    public double getReal() {
        return this.real;
    }

    public double getImaginary() {
        return this.imaginary;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Complex_F64)) {
            return false;
        }
        Complex_F64 other = (Complex_F64)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (Double.compare(this.getReal(), other.getReal()) != 0) {
            return false;
        }
        return Double.compare(this.getImaginary(), other.getImaginary()) == 0;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Complex_F64;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $real = Double.doubleToLongBits(this.getReal());
        result = result * 59 + (int)($real >>> 32 ^ $real);
        long $imaginary = Double.doubleToLongBits(this.getImaginary());
        result = result * 59 + (int)($imaginary >>> 32 ^ $imaginary);
        return result;
    }
}

