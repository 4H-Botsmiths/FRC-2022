/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import java.util.Arrays;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Range;

public class MatOfFloat6
extends Mat {
    private static final int _depth = 5;
    private static final int _channels = 6;

    public MatOfFloat6() {
    }

    protected MatOfFloat6(long addr) {
        super(addr);
        if (!this.empty() && this.checkVector(6, 5) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public static MatOfFloat6 fromNativeAddr(long addr) {
        return new MatOfFloat6(addr);
    }

    public MatOfFloat6(Mat m) {
        super(m, Range.all());
        if (!this.empty() && this.checkVector(6, 5) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public MatOfFloat6(float ... a) {
        this.fromArray(a);
    }

    public void alloc(int elemNumber) {
        if (elemNumber > 0) {
            super.create(elemNumber, 1, CvType.makeType(5, 6));
        }
    }

    public void fromArray(float ... a) {
        if (a == null || a.length == 0) {
            return;
        }
        int num = a.length / 6;
        this.alloc(num);
        this.put(0, 0, a);
    }

    public float[] toArray() {
        int num = this.checkVector(6, 5);
        if (num < 0) {
            throw new RuntimeException("Native Mat has unexpected type or size: " + this.toString());
        }
        float[] a = new float[num * 6];
        if (num == 0) {
            return a;
        }
        this.get(0, 0, a);
        return a;
    }

    public void fromList(List<Float> lb) {
        if (lb == null || lb.size() == 0) {
            return;
        }
        Float[] ab = lb.toArray(new Float[0]);
        float[] a = new float[ab.length];
        for (int i = 0; i < ab.length; ++i) {
            a[i] = ab[i].floatValue();
        }
        this.fromArray(a);
    }

    public List<Float> toList() {
        float[] a = this.toArray();
        Float[] ab = new Float[a.length];
        for (int i = 0; i < a.length; ++i) {
            ab[i] = Float.valueOf(a[i]);
        }
        return Arrays.asList(ab);
    }
}

