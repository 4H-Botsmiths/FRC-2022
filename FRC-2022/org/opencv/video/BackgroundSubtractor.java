/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;

public class BackgroundSubtractor
extends Algorithm {
    protected BackgroundSubtractor(long addr) {
        super(addr);
    }

    public static BackgroundSubtractor __fromPtr__(long addr) {
        return new BackgroundSubtractor(addr);
    }

    public void apply(Mat image, Mat fgmask, double learningRate) {
        BackgroundSubtractor.apply_0(this.nativeObj, image.nativeObj, fgmask.nativeObj, learningRate);
    }

    public void apply(Mat image, Mat fgmask) {
        BackgroundSubtractor.apply_1(this.nativeObj, image.nativeObj, fgmask.nativeObj);
    }

    public void getBackgroundImage(Mat backgroundImage) {
        BackgroundSubtractor.getBackgroundImage_0(this.nativeObj, backgroundImage.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        BackgroundSubtractor.delete(this.nativeObj);
    }

    private static native void apply_0(long var0, long var2, long var4, double var6);

    private static native void apply_1(long var0, long var2, long var4);

    private static native void getBackgroundImage_0(long var0, long var2);

    private static native void delete(long var0);
}

