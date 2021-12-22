/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

public class FScalar {
    public float value;

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FScalar)) {
            return false;
        }
        FScalar other = (FScalar)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return Float.compare(this.getValue(), other.getValue()) == 0;
    }

    protected boolean canEqual(Object other) {
        return other instanceof FScalar;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + Float.floatToIntBits(this.getValue());
        return result;
    }

    public String toString() {
        return "FScalar(value=" + this.getValue() + ")";
    }
}

