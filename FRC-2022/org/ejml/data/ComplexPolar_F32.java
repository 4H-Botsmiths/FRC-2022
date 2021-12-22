/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.Complex_F32;
import org.ejml.ops.ComplexMath_F32;

public class ComplexPolar_F32 {
    public float r;
    public float theta;

    public ComplexPolar_F32(float r, float theta) {
        this.r = r;
        this.theta = theta;
    }

    public ComplexPolar_F32(Complex_F32 n) {
        ComplexMath_F32.convert(n, this);
    }

    public ComplexPolar_F32() {
    }

    public Complex_F32 toStandard() {
        Complex_F32 ret = new Complex_F32();
        ComplexMath_F32.convert(this, ret);
        return ret;
    }

    public void setTo(float r, float theta) {
        this.r = r;
        this.theta = theta;
    }

    public void setTo(ComplexPolar_F32 src) {
        this.r = src.r;
        this.theta = src.theta;
    }

    public String toString() {
        return "( r = " + this.r + " theta = " + this.theta + " )";
    }

    public float getR() {
        return this.r;
    }

    public float getTheta() {
        return this.theta;
    }

    public void setR(float r) {
        this.r = r;
    }

    public void setTheta(float theta) {
        this.theta = theta;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ComplexPolar_F32)) {
            return false;
        }
        ComplexPolar_F32 other = (ComplexPolar_F32)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (Float.compare(this.getR(), other.getR()) != 0) {
            return false;
        }
        return Float.compare(this.getTheta(), other.getTheta()) == 0;
    }

    protected boolean canEqual(Object other) {
        return other instanceof ComplexPolar_F32;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + Float.floatToIntBits(this.getR());
        result = result * 59 + Float.floatToIntBits(this.getTheta());
        return result;
    }
}

