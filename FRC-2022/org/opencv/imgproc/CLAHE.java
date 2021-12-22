/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.imgproc;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;
import org.opencv.core.Size;

public class CLAHE
extends Algorithm {
    protected CLAHE(long addr) {
        super(addr);
    }

    public static CLAHE __fromPtr__(long addr) {
        return new CLAHE(addr);
    }

    public void apply(Mat src, Mat dst) {
        CLAHE.apply_0(this.nativeObj, src.nativeObj, dst.nativeObj);
    }

    public void setClipLimit(double clipLimit) {
        CLAHE.setClipLimit_0(this.nativeObj, clipLimit);
    }

    public double getClipLimit() {
        return CLAHE.getClipLimit_0(this.nativeObj);
    }

    public void setTilesGridSize(Size tileGridSize) {
        CLAHE.setTilesGridSize_0(this.nativeObj, tileGridSize.width, tileGridSize.height);
    }

    public Size getTilesGridSize() {
        return new Size(CLAHE.getTilesGridSize_0(this.nativeObj));
    }

    public void collectGarbage() {
        CLAHE.collectGarbage_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        CLAHE.delete(this.nativeObj);
    }

    private static native void apply_0(long var0, long var2, long var4);

    private static native void setClipLimit_0(long var0, double var2);

    private static native double getClipLimit_0(long var0);

    private static native void setTilesGridSize_0(long var0, double var2, double var4);

    private static native double[] getTilesGridSize_0(long var0);

    private static native void collectGarbage_0(long var0);

    private static native void delete(long var0);
}

