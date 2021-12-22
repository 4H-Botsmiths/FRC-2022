/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import org.opencv.core.Point;
import org.opencv.core.Size;

public class Rect {
    public int x;
    public int y;
    public int width;
    public int height;

    public Rect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rect() {
        this(0, 0, 0, 0);
    }

    public Rect(Point p1, Point p2) {
        this.x = (int)(p1.x < p2.x ? p1.x : p2.x);
        this.y = (int)(p1.y < p2.y ? p1.y : p2.y);
        this.width = (int)(p1.x > p2.x ? p1.x : p2.x) - this.x;
        this.height = (int)(p1.y > p2.y ? p1.y : p2.y) - this.y;
    }

    public Rect(Point p, Size s) {
        this((int)p.x, (int)p.y, (int)s.width, (int)s.height);
    }

    public Rect(double[] vals) {
        this.set(vals);
    }

    public void set(double[] vals) {
        if (vals != null) {
            this.x = vals.length > 0 ? (int)vals[0] : 0;
            this.y = vals.length > 1 ? (int)vals[1] : 0;
            this.width = vals.length > 2 ? (int)vals[2] : 0;
            this.height = vals.length > 3 ? (int)vals[3] : 0;
        } else {
            this.x = 0;
            this.y = 0;
            this.width = 0;
            this.height = 0;
        }
    }

    public Rect clone() {
        return new Rect(this.x, this.y, this.width, this.height);
    }

    public Point tl() {
        return new Point(this.x, this.y);
    }

    public Point br() {
        return new Point(this.x + this.width, this.y + this.height);
    }

    public Size size() {
        return new Size(this.width, this.height);
    }

    public double area() {
        return this.width * this.height;
    }

    public boolean empty() {
        return this.width <= 0 || this.height <= 0;
    }

    public boolean contains(Point p) {
        return (double)this.x <= p.x && p.x < (double)(this.x + this.width) && (double)this.y <= p.y && p.y < (double)(this.y + this.height);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        long temp = Double.doubleToLongBits(this.height);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.width);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.x);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.y);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Rect)) {
            return false;
        }
        Rect it = (Rect)obj;
        return this.x == it.x && this.y == it.y && this.width == it.width && this.height == it.height;
    }

    public String toString() {
        return "{" + this.x + ", " + this.y + ", " + this.width + "x" + this.height + "}";
    }
}

