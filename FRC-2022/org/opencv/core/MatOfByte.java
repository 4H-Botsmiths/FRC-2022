/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import java.util.Arrays;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Range;

public class MatOfByte
extends Mat {
    private static final int _depth = 0;
    private static final int _channels = 1;

    public MatOfByte() {
    }

    protected MatOfByte(long addr) {
        super(addr);
        if (!this.empty() && this.checkVector(1, 0) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public static MatOfByte fromNativeAddr(long addr) {
        return new MatOfByte(addr);
    }

    public MatOfByte(Mat m) {
        super(m, Range.all());
        if (!this.empty() && this.checkVector(1, 0) < 0) {
            throw new IllegalArgumentException("Incompatible Mat");
        }
    }

    public MatOfByte(byte ... a) {
        this.fromArray(a);
    }

    public MatOfByte(int offset, int length, byte ... a) {
        this.fromArray(offset, length, a);
    }

    public void alloc(int elemNumber) {
        if (elemNumber > 0) {
            super.create(elemNumber, 1, CvType.makeType(0, 1));
        }
    }

    public void fromArray(byte ... a) {
        if (a == null || a.length == 0) {
            return;
        }
        int num = a.length / 1;
        this.alloc(num);
        this.put(0, 0, a);
    }

    public void fromArray(int offset, int length, byte ... a) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset < 0");
        }
        if (a == null) {
            throw new NullPointerException();
        }
        if (length < 0 || length + offset > a.length) {
            throw new IllegalArgumentException("invalid 'length' parameter: " + Integer.toString(length));
        }
        if (a.length == 0) {
            return;
        }
        int num = length / 1;
        this.alloc(num);
        this.put(0, 0, a, offset, length);
    }

    public byte[] toArray() {
        int num = this.checkVector(1, 0);
        if (num < 0) {
            throw new RuntimeException("Native Mat has unexpected type or size: " + this.toString());
        }
        byte[] a = new byte[num * 1];
        if (num == 0) {
            return a;
        }
        this.get(0, 0, a);
        return a;
    }

    public void fromList(List<Byte> lb) {
        if (lb == null || lb.size() == 0) {
            return;
        }
        Byte[] ab = lb.toArray(new Byte[0]);
        byte[] a = new byte[ab.length];
        for (int i = 0; i < ab.length; ++i) {
            a[i] = ab[i];
        }
        this.fromArray(a);
    }

    public List<Byte> toList() {
        byte[] a = this.toArray();
        Byte[] ab = new Byte[a.length];
        for (int i = 0; i < a.length; ++i) {
            ab[i] = a[i];
        }
        return Arrays.asList(ab);
    }
}

