/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import java.util.Arrays;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Range;

public class MatOfDouble
extends Mat {
    private static final int _depth = 6;
    private static final int _channels = 1;

    public MatOfDouble() {
    }

    protected MatOfDouble(long addr) {
        super(addr);
        if (!this.empty() && this.checkVector(1, 6) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public static MatOfDouble fromNativeAddr(long addr) {
        return new MatOfDouble(addr);
    }

    public MatOfDouble(Mat m) {
        super(m, Range.all());
        if (!this.empty() && this.checkVector(1, 6) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public MatOfDouble(double ... a) {
        this.fromArray(a);
    }

    public void alloc(int elemNumber) {
        if (elemNumber > 0) {
            super.create(elemNumber, 1, CvType.makeType(6, 1));
        }
    }

    public void fromArray(double ... a) {
        if (a == null || a.length == 0) {
            return;
        }
        int num = a.length / 1;
        this.alloc(num);
        this.put(0, 0, a);
    }

    public double[] toArray() {
        int num = this.checkVector(1, 6);
        if (num < 0) {
            throw new RuntimeException("Native Mat has unexpected type or size: " + this.toString());
        }
        double[] a = new double[num * 1];
        if (num == 0) {
            return a;
        }
        this.get(0, 0, a);
        return a;
    }

    public void fromList(List<Double> lb) {
        if (lb == null || lb.size() == 0) {
            return;
        }
        Double[] ab = lb.toArray(new Double[0]);
        double[] a = new double[ab.length];
        for (int i = 0; i < ab.length; ++i) {
            a[i] = ab[i];
        }
        this.fromArray(a);
    }

    public List<Double> toList() {
        double[] a = this.toArray();
        Double[] ab = new Double[a.length];
        for (int i = 0; i < a.length; ++i) {
            ab[i] = a[i];
        }
        return Arrays.asList(ab);
    }
}

