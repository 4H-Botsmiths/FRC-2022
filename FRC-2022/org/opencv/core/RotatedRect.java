/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;

public class RotatedRect {
    public Point center;
    public Size size;
    public double angle;

    public RotatedRect() {
        this.center = new Point();
        this.size = new Size();
        this.angle = 0.0;
    }

    public RotatedRect(Point c, Size s, double a) {
        this.center = c.clone();
        this.size = s.clone();
        this.angle = a;
    }

    public RotatedRect(double[] vals) {
        this();
        this.set(vals);
    }

    public void set(double[] vals) {
        if (vals != null) {
            this.center.x = vals.length > 0 ? vals[0] : 0.0;
            this.center.y = vals.length > 1 ? vals[1] : 0.0;
            this.size.width = vals.length > 2 ? vals[2] : 0.0;
            this.size.height = vals.length > 3 ? vals[3] : 0.0;
            this.angle = vals.length > 4 ? vals[4] : 0.0;
        } else {
            this.center.x = 0.0;
            this.center.y = 0.0;
            this.size.width = 0.0;
            this.size.height = 0.0;
            this.angle = 0.0;
        }
    }

    public void points(Point[] pt) {
        double _angle = this.angle * Math.PI / 180.0;
        double b = Math.cos(_angle) * 0.5;
        double a = Math.sin(_angle) * 0.5;
        pt[0] = new Point(this.center.x - a * this.size.height - b * this.size.width, this.center.y + b * this.size.height - a * this.size.width);
        pt[1] = new Point(this.center.x + a * this.size.height - b * this.size.width, this.center.y - b * this.size.height - a * this.size.width);
        pt[2] = new Point(2.0 * this.center.x - pt[0].x, 2.0 * this.center.y - pt[0].y);
        pt[3] = new Point(2.0 * this.center.x - pt[1].x, 2.0 * this.center.y - pt[1].y);
    }

    public Rect boundingRect() {
        Point[] pt = new Point[4];
        this.points(pt);
        Rect r = new Rect((int)Math.floor(Math.min(Math.min(Math.min(pt[0].x, pt[1].x), pt[2].x), pt[3].x)), (int)Math.floor(Math.min(Math.min(Math.min(pt[0].y, pt[1].y), pt[2].y), pt[3].y)), (int)Math.ceil(Math.max(Math.max(Math.max(pt[0].x, pt[1].x), pt[2].x), pt[3].x)), (int)Math.ceil(Math.max(Math.max(Math.max(pt[0].y, pt[1].y), pt[2].y), pt[3].y)));
        r.width -= r.x - 1;
        r.height -= r.y - 1;
        return r;
    }

    public RotatedRect clone() {
        return new RotatedRect(this.center, this.size, this.angle);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        long temp = Double.doubleToLongBits(this.center.x);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.center.y);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.size.width);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.size.height);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.angle);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RotatedRect)) {
            return false;
        }
        RotatedRect it = (RotatedRect)obj;
        return this.center.equals(it.center) && this.size.equals(it.size) && this.angle == it.angle;
    }

    public String toString() {
        return "{ " + this.center + " " + this.size + " * " + this.angle + " }";
    }
}

