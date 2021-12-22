/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.ops.ComplexMath_F32;

public class Complex_F32 {
    public float real;
    public float imaginary;

    public Complex_F32(float real, float imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Complex_F32() {
    }

    public float getMagnitude() {
        return (float)Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
    }

    public float getMagnitude2() {
        return this.real * this.real + this.imaginary * this.imaginary;
    }

    public void setTo(float real, float imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public void setTo(Complex_F32 src) {
        this.real = src.real;
        this.imaginary = src.imaginary;
    }

    public boolean isReal() {
        return this.imaginary == 0.0f;
    }

    public Complex_F32 plus(Complex_F32 a) {
        Complex_F32 ret = new Complex_F32();
        ComplexMath_F32.plus(this, a, ret);
        return ret;
    }

    public Complex_F32 minus(Complex_F32 a) {
        Complex_F32 ret = new Complex_F32();
        ComplexMath_F32.minus(this, a, ret);
        return ret;
    }

    public Complex_F32 times(Complex_F32 a) {
        Complex_F32 ret = new Complex_F32();
        ComplexMath_F32.multiply(this, a, ret);
        return ret;
    }

    public Complex_F32 divide(Complex_F32 a) {
        Complex_F32 ret = new Complex_F32();
        ComplexMath_F32.divide(this, a, ret);
        return ret;
    }

    public String toString() {
        if (this.imaginary == 0.0f) {
            return "" + this.real;
        }
        return this.real + " " + this.imaginary + "i";
    }

    public float getReal() {
        return this.real;
    }

    public float getImaginary() {
        return this.imaginary;
    }

    public void setReal(float real) {
        this.real = real;
    }

    public void setImaginary(float imaginary) {
        this.imaginary = imaginary;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Complex_F32)) {
            return false;
        }
        Complex_F32 other = (Complex_F32)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (Float.compare(this.getReal(), other.getReal()) != 0) {
            return false;
        }
        return Float.compare(this.getImaginary(), other.getImaginary()) == 0;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Complex_F32;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + Float.floatToIntBits(this.getReal());
        result = result * 59 + Float.floatToIntBits(this.getImaginary());
        return result;
    }
}

