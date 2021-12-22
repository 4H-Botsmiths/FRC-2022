/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import org.opencv.core.Rect;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this(0.0, 0.0);
    }

    public Point(double[] vals) {
        this();
        this.set(vals);
    }

    public void set(double[] vals) {
        if (vals != null) {
            this.x = vals.length > 0 ? vals[0] : 0.0;
            this.y = vals.length > 1 ? vals[1] : 0.0;
        } else {
            this.x = 0.0;
            this.y = 0.0;
        }
    }

    public Point clone() {
        return new Point(this.x, this.y);
    }

    public double dot(Point p) {
        return this.x * p.x + this.y * p.y;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        long temp = Double.doubleToLongBits(this.x);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.y);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point it = (Point)obj;
        return this.x == it.x && this.y == it.y;
    }

    public boolean inside(Rect r) {
        return r.contains(this);
    }

    public String toString() {
        return "{" + this.x + ", " + this.y + "}";
    }
}

