/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.Complex_F64;
import org.ejml.ops.ComplexMath_F64;

public class ComplexPolar_F64 {
    public double r;
    public double theta;

    public ComplexPolar_F64(double r, double theta) {
        this.r = r;
        this.theta = theta;
    }

    public ComplexPolar_F64(Complex_F64 n) {
        ComplexMath_F64.convert(n, this);
    }

    public ComplexPolar_F64() {
    }

    public Complex_F64 toStandard() {
        Complex_F64 ret = new Complex_F64();
        ComplexMath_F64.convert(this, ret);
        return ret;
    }

    public void setTo(double r, double theta) {
        this.r = r;
        this.theta = theta;
    }

    public void setTo(ComplexPolar_F64 src) {
        this.r = src.r;
        this.theta = src.theta;
    }

    public String toString() {
        return "( r = " + this.r + " theta = " + this.theta + " )";
    }

    public double getR() {
        return this.r;
    }

    public double getTheta() {
        return this.theta;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ComplexPolar_F64)) {
            return false;
        }
        ComplexPolar_F64 other = (ComplexPolar_F64)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (Double.compare(this.getR(), other.getR()) != 0) {
            return false;
        }
        return Double.compare(this.getTheta(), other.getTheta()) == 0;
    }

    protected boolean canEqual(Object other) {
        return other instanceof ComplexPolar_F64;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $r = Double.doubleToLongBits(this.getR());
        result = result * 59 + (int)($r >>> 32 ^ $r);
        long $theta = Double.doubleToLongBits(this.getTheta());
        result = result * 59 + (int)($theta >>> 32 ^ $theta);
        return result;
    }
}

