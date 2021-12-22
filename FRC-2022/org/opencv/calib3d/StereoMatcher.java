/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.calib3d;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;

public class StereoMatcher
extends Algorithm {
    public static final int DISP_SHIFT = 4;
    public static final int DISP_SCALE = 16;

    protected StereoMatcher(long addr) {
        super(addr);
    }

    public static StereoMatcher __fromPtr__(long addr) {
        return new StereoMatcher(addr);
    }

    public void compute(Mat left, Mat right, Mat disparity) {
        StereoMatcher.compute_0(this.nativeObj, left.nativeObj, right.nativeObj, disparity.nativeObj);
    }

    public int getMinDisparity() {
        return StereoMatcher.getMinDisparity_0(this.nativeObj);
    }

    public void setMinDisparity(int minDisparity) {
        StereoMatcher.setMinDisparity_0(this.nativeObj, minDisparity);
    }

    public int getNumDisparities() {
        return StereoMatcher.getNumDisparities_0(this.nativeObj);
    }

    public void setNumDisparities(int numDisparities) {
        StereoMatcher.setNumDisparities_0(this.nativeObj, numDisparities);
    }

    public int getBlockSize() {
        return StereoMatcher.getBlockSize_0(this.nativeObj);
    }

    public void setBlockSize(int blockSize) {
        StereoMatcher.setBlockSize_0(this.nativeObj, blockSize);
    }

    public int getSpeckleWindowSize() {
        return StereoMatcher.getSpeckleWindowSize_0(this.nativeObj);
    }

    public void setSpeckleWindowSize(int speckleWindowSize) {
        StereoMatcher.setSpeckleWindowSize_0(this.nativeObj, speckleWindowSize);
    }

    public int getSpeckleRange() {
        return StereoMatcher.getSpeckleRange_0(this.nativeObj);
    }

    public void setSpeckleRange(int speckleRange) {
        StereoMatcher.setSpeckleRange_0(this.nativeObj, speckleRange);
    }

    public int getDisp12MaxDiff() {
        return StereoMatcher.getDisp12MaxDiff_0(this.nativeObj);
    }

    public void setDisp12MaxDiff(int disp12MaxDiff) {
        StereoMatcher.setDisp12MaxDiff_0(this.nativeObj, disp12MaxDiff);
    }

    @Override
    protected void finalize() throws Throwable {
        StereoMatcher.delete(this.nativeObj);
    }

    private static native void compute_0(long var0, long var2, long var4, long var6);

    private static native int getMinDisparity_0(long var0);

    private static native void setMinDisparity_0(long var0, int var2);

    private static native int getNumDisparities_0(long var0);

    private static native void setNumDisparities_0(long var0, int var2);

    private static native int getBlockSize_0(long var0);

    private static native void setBlockSize_0(long var0, int var2);

    private static native int getSpeckleWindowSize_0(long var0);

    private static native void setSpeckleWindowSize_0(long var0, int var2);

    private static native int getSpeckleRange_0(long var0);

    private static native void setSpeckleRange_0(long var0, int var2);

    private static native int getDisp12MaxDiff_0(long var0);

    private static native void setDisp12MaxDiff_0(long var0, int var2);

    private static native void delete(long var0);
}

