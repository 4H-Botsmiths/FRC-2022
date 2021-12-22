/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.photo;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;

public class Tonemap
extends Algorithm {
    protected Tonemap(long addr) {
        super(addr);
    }

    public static Tonemap __fromPtr__(long addr) {
        return new Tonemap(addr);
    }

    public void process(Mat src, Mat dst) {
        Tonemap.process_0(this.nativeObj, src.nativeObj, dst.nativeObj);
    }

    public float getGamma() {
        return Tonemap.getGamma_0(this.nativeObj);
    }

    public void setGamma(float gamma) {
        Tonemap.setGamma_0(this.nativeObj, gamma);
    }

    @Override
    protected void finalize() throws Throwable {
        Tonemap.delete(this.nativeObj);
    }

    private static native void process_0(long var0, long var2, long var4);

    private static native float getGamma_0(long var0);

    private static native void setGamma_0(long var0, float var2);

    private static native void delete(long var0);
}

