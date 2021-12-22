/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import java.util.Arrays;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point3;
import org.opencv.core.Range;

public class MatOfPoint3f
extends Mat {
    private static final int _depth = 5;
    private static final int _channels = 3;

    public MatOfPoint3f() {
    }

    protected MatOfPoint3f(long addr) {
        super(addr);
        if (!this.empty() && this.checkVector(3, 5) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public static MatOfPoint3f fromNativeAddr(long addr) {
        return new MatOfPoint3f(addr);
    }

    public MatOfPoint3f(Mat m) {
        super(m, Range.all());
        if (!this.empty() && this.checkVector(3, 5) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public MatOfPoint3f(Point3 ... a) {
        this.fromArray(a);
    }

    public void alloc(int elemNumber) {
        if (elemNumber > 0) {
            super.create(elemNumber, 1, CvType.makeType(5, 3));
        }
    }

    public void fromArray(Point3 ... a) {
        if (a == null || a.length == 0) {
            return;
        }
        int num = a.length;
        this.alloc(num);
        float[] buff = new float[num * 3];
        for (int i = 0; i < num; ++i) {
            Point3 p = a[i];
            buff[3 * i + 0] = (float)p.x;
            buff[3 * i + 1] = (float)p.y;
            buff[3 * i + 2] = (float)p.z;
        }
        this.put(0, 0, buff);
    }

    public Point3[] toArray() {
        int num = (int)this.total();
        Point3[] ap = new Point3[num];
        if (num == 0) {
            return ap;
        }
        float[] buff = new float[num * 3];
        this.get(0, 0, buff);
        for (int i = 0; i < num; ++i) {
            ap[i] = new Point3(buff[i * 3], buff[i * 3 + 1], buff[i * 3 + 2]);
        }
        return ap;
    }

    public void fromList(List<Point3> lp) {
        Point3[] ap = lp.toArray(new Point3[0]);
        this.fromArray(ap);
    }

    public List<Point3> toList() {
        Point3[] ap = this.toArray();
        return Arrays.asList(ap);
    }
}

