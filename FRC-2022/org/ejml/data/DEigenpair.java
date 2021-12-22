/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.DMatrixRMaj;

public class DEigenpair {
    public double value;
    public DMatrixRMaj vector;

    public DEigenpair(double value, DMatrixRMaj vector) {
        this.value = value;
        this.vector = vector;
    }

    public double getValue() {
        return this.value;
    }

    public DMatrixRMaj getVector() {
        return this.vector;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setVector(DMatrixRMaj vector) {
        this.vector = vector;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DEigenpair)) {
            return false;
        }
        DEigenpair other = (DEigenpair)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (Double.compare(this.getValue(), other.getValue()) != 0) {
            return false;
        }
        DMatrixRMaj this$vector = this.getVector();
        DMatrixRMaj other$vector = other.getVector();
        return !(this$vector == null ? other$vector != null : !this$vector.equals(other$vector));
    }

    protected boolean canEqual(Object other) {
        return other instanceof DEigenpair;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $value = Double.doubleToLongBits(this.getValue());
        result = result * 59 + (int)($value >>> 32 ^ $value);
        DMatrixRMaj $vector = this.getVector();
        result = result * 59 + ($vector == null ? 43 : $vector.hashCode());
        return result;
    }

    public String toString() {
        return "DEigenpair(value=" + this.getValue() + ", vector=" + this.getVector() + ")";
    }
}

