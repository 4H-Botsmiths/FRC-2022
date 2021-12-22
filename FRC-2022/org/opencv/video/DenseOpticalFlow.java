/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;

public class DenseOpticalFlow
extends Algorithm {
    protected DenseOpticalFlow(long addr) {
        super(addr);
    }

    public static DenseOpticalFlow __fromPtr__(long addr) {
        return new DenseOpticalFlow(addr);
    }

    public void calc(Mat I0, Mat I1, Mat flow) {
        DenseOpticalFlow.calc_0(this.nativeObj, I0.nativeObj, I1.nativeObj, flow.nativeObj);
    }

    public void collectGarbage() {
        DenseOpticalFlow.collectGarbage_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        DenseOpticalFlow.delete(this.nativeObj);
    }

    private static native void calc_0(long var0, long var2, long var4, long var6);

    private static native void collectGarbage_0(long var0);

    private static native void delete(long var0);
}

