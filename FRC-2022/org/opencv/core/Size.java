/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import org.opencv.core.Point;

public class Size {
    public double width;
    public double height;

    public Size(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public Size() {
        this(0.0, 0.0);
    }

    public Size(Point p) {
        this.width = p.x;
        this.height = p.y;
    }

    public Size(double[] vals) {
        this.set(vals);
    }

    public void set(double[] vals) {
        if (vals != null) {
            this.width = vals.length > 0 ? vals[0] : 0.0;
            this.height = vals.length > 1 ? vals[1] : 0.0;
        } else {
            this.width = 0.0;
            this.height = 0.0;
        }
    }

    public double area() {
        return this.width * this.height;
    }

    public boolean empty() {
        return this.width <= 0.0 || this.height <= 0.0;
    }

    public Size clone() {
        return new Size(this.width, this.height);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        long temp = Double.doubleToLongBits(this.height);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.width);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Size)) {
            return false;
        }
        Size it = (Size)obj;
        return this.width == it.width && this.height == it.height;
    }

    public String toString() {
        return (int)this.width + "x" + (int)this.height;
    }
}

