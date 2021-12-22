/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import java.util.Arrays;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Range;

public class MatOfInt
extends Mat {
    private static final int _depth = 4;
    private static final int _channels = 1;

    public MatOfInt() {
    }

    protected MatOfInt(long addr) {
        super(addr);
        if (!this.empty() && this.checkVector(1, 4) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public static MatOfInt fromNativeAddr(long addr) {
        return new MatOfInt(addr);
    }

    public MatOfInt(Mat m) {
        super(m, Range.all());
        if (!this.empty() && this.checkVector(1, 4) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public MatOfInt(int ... a) {
        this.fromArray(a);
    }

    public void alloc(int elemNumber) {
        if (elemNumber > 0) {
            super.create(elemNumber, 1, CvType.makeType(4, 1));
        }
    }

    public void fromArray(int ... a) {
        if (a == null || a.length == 0) {
            return;
        }
        int num = a.length / 1;
        this.alloc(num);
        this.put(0, 0, a);
    }

    public int[] toArray() {
        int num = this.checkVector(1, 4);
        if (num < 0) {
            throw new RuntimeException("Native Mat has unexpected type or size: " + this.toString());
        }
        int[] a = new int[num * 1];
        if (num == 0) {
            return a;
        }
        this.get(0, 0, a);
        return a;
    }

    public void fromList(List<Integer> lb) {
        if (lb == null || lb.size() == 0) {
            return;
        }
        Integer[] ab = lb.toArray(new Integer[0]);
        int[] a = new int[ab.length];
        for (int i = 0; i < ab.length; ++i) {
            a[i] = ab[i];
        }
        this.fromArray(a);
    }

    public List<Integer> toList() {
        int[] a = this.toArray();
        Integer[] ab = new Integer[a.length];
        for (int i = 0; i < a.length; ++i) {
            ab[i] = a[i];
        }
        return Arrays.asList(ab);
    }
}

