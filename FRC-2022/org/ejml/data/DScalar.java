/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

public class DScalar {
    public double value;

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DScalar)) {
            return false;
        }
        DScalar other = (DScalar)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return Double.compare(this.getValue(), other.getValue()) == 0;
    }

    protected boolean canEqual(Object other) {
        return other instanceof DScalar;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $value = Double.doubleToLongBits(this.getValue());
        result = result * 59 + (int)($value >>> 32 ^ $value);
        return result;
    }

    public String toString() {
        return "DScalar(value=" + this.getValue() + ")";
    }
}

