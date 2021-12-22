/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import java.util.Arrays;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.Range;

public class MatOfDMatch
extends Mat {
    private static final int _depth = 5;
    private static final int _channels = 4;

    public MatOfDMatch() {
    }

    protected MatOfDMatch(long addr) {
        super(addr);
        if (!this.empty() && this.checkVector(4, 5) < 0) {
            throw new IllegalArgumentException("Incompatible Mat: " + this.toString());
        }
    }

    public static MatOfDMatch fromNativeAddr(long addr) {
        return new MatOfDMatch(addr);
    }

    public MatOfDMatch(Mat m) {
        super(m, Range.all());
        if (!this.empty() && this.checkVector(4, 5) < 0) {
            throw new IllegalArgumentException("Incompatible Mat: " + this.toString());
        }
    }

    public MatOfDMatch(DMatch ... ap) {
        this.fromArray(ap);
    }

    public void alloc(int elemNumber) {
        if (elemNumber > 0) {
            super.create(elemNumber, 1, CvType.makeType(5, 4));
        }
    }

    public void fromArray(DMatch ... a) {
        if (a == null || a.length == 0) {
            return;
        }
        int num = a.length;
        this.alloc(num);
        float[] buff = new float[num * 4];
        for (int i = 0; i < num; ++i) {
            DMatch m = a[i];
            buff[4 * i + 0] = m.queryIdx;
            buff[4 * i + 1] = m.trainIdx;
            buff[4 * i + 2] = m.imgIdx;
            buff[4 * i + 3] = m.distance;
        }
        this.put(0, 0, buff);
    }

    public DMatch[] toArray() {
        int num = (int)this.total();
        DMatch[] a = new DMatch[num];
        if (num == 0) {
            return a;
        }
        float[] buff = new float[num * 4];
        this.get(0, 0, buff);
        for (int i = 0; i < num; ++i) {
            a[i] = new DMatch((int)buff[4 * i + 0], (int)buff[4 * i + 1], (int)buff[4 * i + 2], buff[4 * i + 3]);
        }
        return a;
    }

    public void fromList(List<DMatch> ldm) {
        DMatch[] adm = ldm.toArray(new DMatch[0]);
        this.fromArray(adm);
    }

    public List<DMatch> toList() {
        DMatch[] adm = this.toArray();
        return Arrays.asList(adm);
    }
}

