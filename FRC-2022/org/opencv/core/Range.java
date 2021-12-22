/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

public class Range {
    public int start;
    public int end;

    public Range(int s, int e) {
        this.start = s;
        this.end = e;
    }

    public Range() {
        this(0, 0);
    }

    public Range(double[] vals) {
        this.set(vals);
    }

    public void set(double[] vals) {
        if (vals != null) {
            this.start = vals.length > 0 ? (int)vals[0] : 0;
            this.end = vals.length > 1 ? (int)vals[1] : 0;
        } else {
            this.start = 0;
            this.end = 0;
        }
    }

    public int size() {
        return this.empty() ? 0 : this.end - this.start;
    }

    public boolean empty() {
        return this.end <= this.start;
    }

    public static Range all() {
        return new Range(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public Range intersection(Range r1) {
        Range r = new Range(Math.max(r1.start, this.start), Math.min(r1.end, this.end));
        r.end = Math.max(r.end, r.start);
        return r;
    }

    public Range shift(int delta) {
        return new Range(this.start + delta, this.end + delta);
    }

    public Range clone() {
        return new Range(this.start, this.end);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        long temp = Double.doubleToLongBits(this.start);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.end);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Range)) {
            return false;
        }
        Range it = (Range)obj;
        return this.start == it.start && this.end == it.end;
    }

    public String toString() {
        return "[" + this.start + ", " + this.end + ")";
    }
}

