/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import java.util.Arrays;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Range;

public class MatOfInt4
extends Mat {
    private static final int _depth = 4;
    private static final int _channels = 4;

    public MatOfInt4() {
    }

    protected MatOfInt4(long addr) {
        super(addr);
        if (!this.empty() && this.checkVector(4, 4) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public static MatOfInt4 fromNativeAddr(long addr) {
        return new MatOfInt4(addr);
    }

    public MatOfInt4(Mat m) {
        super(m, Range.all());
        if (!this.empty() && this.checkVector(4, 4) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public MatOfInt4(int ... a) {
        this.fromArray(a);
    }

    public void alloc(int elemNumber) {
        if (elemNumber > 0) {
            super.create(elemNumber, 1, CvType.makeType(4, 4));
        }
    }

    public void fromArray(int ... a) {
        if (a == null || a.length == 0) {
            return;
        }
        int num = a.length / 4;
        this.alloc(num);
        this.put(0, 0, a);
    }

    public int[] toArray() {
        int num = this.checkVector(4, 4);
        if (num < 0) {
            throw new RuntimeException("Native Mat has unexpected type or size: " + this.toString());
        }
        int[] a = new int[num * 4];
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
