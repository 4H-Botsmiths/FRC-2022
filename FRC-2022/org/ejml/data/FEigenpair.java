/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.data;

import org.ejml.data.FMatrixRMaj;

public class FEigenpair {
    public float value;
    public FMatrixRMaj vector;

    public FEigenpair(float value, FMatrixRMaj vector) {
        this.value = value;
        this.vector = vector;
    }

    public float getValue() {
        return this.value;
    }

    public FMatrixRMaj getVector() {
        return this.vector;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setVector(FMatrixRMaj vector) {
        this.vector = vector;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FEigenpair)) {
            return false;
        }
        FEigenpair other = (FEigenpair)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (Float.compare(this.getValue(), other.getValue()) != 0) {
            return false;
        }
        FMatrixRMaj this$vector = this.getVector();
        FMatrixRMaj other$vector = other.getVector();
        return !(this$vector == null ? other$vector != null : !this$vector.equals(other$vector));
    }

    protected boolean canEqual(Object other) {
        return other instanceof FEigenpair;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + Float.floatToIntBits(this.getValue());
        FMatrixRMaj $vector = this.getVector();
        result = result * 59 + ($vector == null ? 43 : $vector.hashCode());
        return result;
    }

    public String toString() {
        return "FEigenpair(value=" + this.getValue() + ", vector=" + this.getVector() + ")";
    }
}

