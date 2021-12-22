/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import java.util.Arrays;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Range;
import org.opencv.core.Rect;

public class MatOfRect
extends Mat {
    private static final int _depth = 4;
    private static final int _channels = 4;

    public MatOfRect() {
    }

    protected MatOfRect(long addr) {
        super(addr);
        if (!this.empty() && this.checkVector(4, 4) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public static MatOfRect fromNativeAddr(long addr) {
        return new MatOfRect(addr);
    }

    public MatOfRect(Mat m) {
        super(m, Range.all());
        if (!this.empty() && this.checkVector(4, 4) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public MatOfRect(Rect ... a) {
        this.fromArray(a);
    }

    public void alloc(int elemNumber) {
        if (elemNumber > 0) {
            super.create(elemNumber, 1, CvType.makeType(4, 4));
        }
    }

    public void fromArray(Rect ... a) {
        if (a == null || a.length == 0) {
            return;
        }
        int num = a.length;
        this.alloc(num);
        int[] buff = new int[num * 4];
        for (int i = 0; i < num; ++i) {
            Rect r = a[i];
            buff[4 * i + 0] = r.x;
            buff[4 * i + 1] = r.y;
            buff[4 * i + 2] = r.width;
            buff[4 * i + 3] = r.height;
        }
        this.put(0, 0, buff);
    }

    public Rect[] toArray() {
        int num = (int)this.total();
        Rect[] a = new Rect[num];
        if (num == 0) {
            return a;
        }
        int[] buff = new int[num * 4];
        this.get(0, 0, buff);
        for (int i = 0; i < num; ++i) {
            a[i] = new Rect(buff[i * 4], buff[i * 4 + 1], buff[i * 4 + 2], buff[i * 4 + 3]);
        }
        return a;
    }

    public void fromList(List<Rect> lr) {
        Rect[] ap = lr.toArray(new Rect[0]);
        this.fromArray(ap);
    }

    public List<Rect> toList() {
        Rect[] ar = this.toArray();
        return Arrays.asList(ar);
    }
}

