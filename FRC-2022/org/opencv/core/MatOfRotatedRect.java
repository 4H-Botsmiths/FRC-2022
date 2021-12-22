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
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;

public class MatOfRotatedRect
extends Mat {
    private static final int _depth = 5;
    private static final int _channels = 5;

    public MatOfRotatedRect() {
    }

    protected MatOfRotatedRect(long addr) {
        super(addr);
        if (!this.empty() && this.checkVector(5, 5) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public static MatOfRotatedRect fromNativeAddr(long addr) {
        return new MatOfRotatedRect(addr);
    }

    public MatOfRotatedRect(Mat m) {
        super(m, Range.all());
        if (!this.empty() && this.checkVector(5, 5) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public MatOfRotatedRect(RotatedRect ... a) {
        this.fromArray(a);
    }

    public void alloc(int elemNumber) {
        if (elemNumber > 0) {
            super.create(elemNumber, 1, CvType.makeType(5, 5));
        }
    }

    public void fromArray(RotatedRect ... a) {
        if (a == null || a.length == 0) {
            return;
        }
        int num = a.length;
        this.alloc(num);
        float[] buff = new float[num * 5];
        for (int i = 0; i < num; ++i) {
            RotatedRect r = a[i];
            buff[5 * i + 0] = (float)r.center.x;
            buff[5 * i + 1] = (float)r.center.y;
            buff[5 * i + 2] = (float)r.size.width;
            buff[5 * i + 3] = (float)r.size.height;
            buff[5 * i + 4] = (float)r.angle;
        }
        this.put(0, 0, buff);
    }

    public RotatedRect[] toArray() {
        int num = (int)this.total();
        RotatedRect[] a = new RotatedRect[num];
        if (num == 0) {
            return a;
        }
        float[] buff = new float[5];
        for (int i = 0; i < num; ++i) {
            this.get(i, 0, buff);
            a[i] = new RotatedRect(new Point(buff[0], buff[1]), new Size(buff[2], buff[3]), buff[4]);
        }
        return a;
    }

    public void fromList(List<RotatedRect> lr) {
        RotatedRect[] ap = lr.toArray(new RotatedRect[0]);
        this.fromArray(ap);
    }

    public List<RotatedRect> toList() {
        RotatedRect[] ar = this.toArray();
        return Arrays.asList(ar);
    }
}

