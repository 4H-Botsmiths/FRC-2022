/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import org.opencv.core.Point;

public class Point3 {
    public double x;
    public double y;
    public double z;

    public Point3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3() {
        this(0.0, 0.0, 0.0);
    }

    public Point3(Point p) {
        this.x = p.x;
        this.y = p.y;
        this.z = 0.0;
    }

    public Point3(double[] vals) {
        this();
        this.set(vals);
    }

    public void set(double[] vals) {
        if (vals != null) {
            this.x = vals.length > 0 ? vals[0] : 0.0;
            this.y = vals.length > 1 ? vals[1] : 0.0;
            this.z = vals.length > 2 ? vals[2] : 0.0;
        } else {
            this.x = 0.0;
            this.y = 0.0;
            this.z = 0.0;
        }
    }

    public Point3 clone() {
        return new Point3(this.x, this.y, this.z);
    }

    public double dot(Point3 p) {
        return this.x * p.x + this.y * p.y + this.z * p.z;
    }

    public Point3 cross(Point3 p) {
        return new Point3(this.y * p.z - this.z * p.y, this.z * p.x - this.x * p.z, this.x * p.y - this.y * p.x);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        long temp = Double.doubleToLongBits(this.x);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.y);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.z);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Point3)) {
            return false;
        }
        Point3 it = (Point3)obj;
        return this.x == it.x && this.y == it.y && this.z == it.z;
    }

    public String toString() {
        return "{" + this.x + ", " + this.y + ", " + this.z + "}";
    }
}

