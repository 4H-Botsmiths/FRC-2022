/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import java.util.Arrays;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.Range;

public class MatOfKeyPoint
extends Mat {
    private static final int _depth = 5;
    private static final int _channels = 7;

    public MatOfKeyPoint() {
    }

    protected MatOfKeyPoint(long addr) {
        super(addr);
        if (!this.empty() && this.checkVector(7, 5) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public static MatOfKeyPoint fromNativeAddr(long addr) {
        return new MatOfKeyPoint(addr);
    }

    public MatOfKeyPoint(Mat m) {
        super(m, Range.all());
        if (!this.empty() && this.checkVector(7, 5) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public MatOfKeyPoint(KeyPoint ... a) {
        this.fromArray(a);
    }

    public void alloc(int elemNumber) {
        if (elemNumber > 0) {
            super.create(elemNumber, 1, CvType.makeType(5, 7));
        }
    }

    public void fromArray(KeyPoint ... a) {
        if (a == null || a.length == 0) {
            return;
        }
        int num = a.length;
        this.alloc(num);
        float[] buff = new float[num * 7];
        for (int i = 0; i < num; ++i) {
            KeyPoint kp = a[i];
            buff[7 * i + 0] = (float)kp.pt.x;
            buff[7 * i + 1] = (float)kp.pt.y;
            buff[7 * i + 2] = kp.size;
            buff[7 * i + 3] = kp.angle;
            buff[7 * i + 4] = kp.response;
            buff[7 * i + 5] = kp.octave;
            buff[7 * i + 6] = kp.class_id;
        }
        this.put(0, 0, buff);
    }

    public KeyPoint[] toArray() {
        int num = (int)this.total();
        KeyPoint[] a = new KeyPoint[num];
        if (num == 0) {
            return a;
        }
        float[] buff = new float[num * 7];
        this.get(0, 0, buff);
        for (int i = 0; i < num; ++i) {
            a[i] = new KeyPoint(buff[7 * i + 0], buff[7 * i + 1], buff[7 * i + 2], buff[7 * i + 3], buff[7 * i + 4], (int)buff[7 * i + 5], (int)buff[7 * i + 6]);
        }
        return a;
    }

    public void fromList(List<KeyPoint> lkp) {
        KeyPoint[] akp = lkp.toArray(new KeyPoint[0]);
        this.fromArray(akp);
    }

    public List<KeyPoint> toList() {
        KeyPoint[] akp = this.toArray();
        return Arrays.asList(akp);
    }
}

