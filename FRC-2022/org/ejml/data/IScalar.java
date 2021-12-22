/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

public class IScalar {
    public int value;

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IScalar)) {
            return false;
        }
        IScalar other = (IScalar)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return this.getValue() == other.getValue();
    }

    protected boolean canEqual(Object other) {
        return other instanceof IScalar;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getValue();
        return result;
    }

    public String toString() {
        return "IScalar(value=" + this.getValue() + ")";
    }
}

