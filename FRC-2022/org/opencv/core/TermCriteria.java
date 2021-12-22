/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

public class TermCriteria {
    public static final int COUNT = 1;
    public static final int MAX_ITER = 1;
    public static final int EPS = 2;
    public int type;
    public int maxCount;
    public double epsilon;

    public TermCriteria(int type, int maxCount, double epsilon) {
        this.type = type;
        this.maxCount = maxCount;
        this.epsilon = epsilon;
    }

    public TermCriteria() {
        this(0, 0, 0.0);
    }

    public TermCriteria(double[] vals) {
        this.set(vals);
    }

    public void set(double[] vals) {
        if (vals != null) {
            this.type = vals.length > 0 ? (int)vals[0] : 0;
            this.maxCount = vals.length > 1 ? (int)vals[1] : 0;
            this.epsilon = vals.length > 2 ? vals[2] : 0.0;
        } else {
            this.type = 0;
            this.maxCount = 0;
            this.epsilon = 0.0;
        }
    }

    public TermCriteria clone() {
        return new TermCriteria(this.type, this.maxCount, this.epsilon);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        long temp = Double.doubleToLongBits(this.type);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.maxCount);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.epsilon);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TermCriteria)) {
            return false;
        }
        TermCriteria it = (TermCriteria)obj;
        return this.type == it.type && this.maxCount == it.maxCount && this.epsilon == it.epsilon;
    }

    public String toString() {
        return "{ type: " + this.type + ", maxCount: " + this.maxCount + ", epsilon: " + this.epsilon + "}";
    }
}

