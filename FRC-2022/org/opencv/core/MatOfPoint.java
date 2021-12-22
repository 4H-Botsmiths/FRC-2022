/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import java.util.Arrays;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Range;

public class MatOfPoint
extends Mat {
    private static final int _depth = 4;
    private static final int _channels = 2;

    public MatOfPoint() {
    }

    protected MatOfPoint(long addr) {
        super(addr);
        if (!this.empty() && this.checkVector(2, 4) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public static MatOfPoint fromNativeAddr(long addr) {
        return new MatOfPoint(addr);
    }

    public MatOfPoint(Mat m) {
        super(m, Range.all());
        if (!this.empty() && this.checkVector(2, 4) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public MatOfPoint(Point ... a) {
        this.fromArray(a);
    }

    public void alloc(int elemNumber) {
        if (elemNumber > 0) {
            super.create(elemNumber, 1, CvType.makeType(4, 2));
        }
    }

    public void fromArray(Point ... a) {
        if (a == null || a.length == 0) {
            return;
        }
        int num = a.length;
        this.alloc(num);
        int[] buff = new int[num * 2];
        for (int i = 0; i < num; ++i) {
            Point p = a[i];
            buff[2 * i + 0] = (int)p.x;
            buff[2 * i + 1] = (int)p.y;
        }
        this.put(0, 0, buff);
    }

    public Point[] toArray() {
        int num = (int)this.total();
        Point[] ap = new Point[num];
        if (num == 0) {
            return ap;
        }
        int[] buff = new int[num * 2];
        this.get(0, 0, buff);
        for (int i = 0; i < num; ++i) {
            ap[i] = new Point(buff[i * 2], buff[i * 2 + 1]);
        }
        return ap;
    }

    public void fromList(List<Point> lp) {
        Point[] ap = lp.toArray(new Point[0]);
        this.fromArray(ap);
    }

    public List<Point> toList() {
        Point[] ap = this.toArray();
        return Arrays.asList(ap);
    }
}

