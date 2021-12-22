/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;

public class SparseOpticalFlow
extends Algorithm {
    protected SparseOpticalFlow(long addr) {
        super(addr);
    }

    public static SparseOpticalFlow __fromPtr__(long addr) {
        return new SparseOpticalFlow(addr);
    }

    public void calc(Mat prevImg, Mat nextImg, Mat prevPts, Mat nextPts, Mat status, Mat err) {
        SparseOpticalFlow.calc_0(this.nativeObj, prevImg.nativeObj, nextImg.nativeObj, prevPts.nativeObj, nextPts.nativeObj, status.nativeObj, err.nativeObj);
    }

    public void calc(Mat prevImg, Mat nextImg, Mat prevPts, Mat nextPts, Mat status) {
        SparseOpticalFlow.calc_1(this.nativeObj, prevImg.nativeObj, nextImg.nativeObj, prevPts.nativeObj, nextPts.nativeObj, status.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        SparseOpticalFlow.delete(this.nativeObj);
    }

    private static native void calc_0(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    private static native void calc_1(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native void delete(long var0);
}

