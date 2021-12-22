/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

import org.opencv.video.DenseOpticalFlow;

public class FarnebackOpticalFlow
extends DenseOpticalFlow {
    protected FarnebackOpticalFlow(long addr) {
        super(addr);
    }

    public static FarnebackOpticalFlow __fromPtr__(long addr) {
        return new FarnebackOpticalFlow(addr);
    }

    public int getNumLevels() {
        return FarnebackOpticalFlow.getNumLevels_0(this.nativeObj);
    }

    public void setNumLevels(int numLevels) {
        FarnebackOpticalFlow.setNumLevels_0(this.nativeObj, numLevels);
    }

    public double getPyrScale() {
        return FarnebackOpticalFlow.getPyrScale_0(this.nativeObj);
    }

    public void setPyrScale(double pyrScale) {
        FarnebackOpticalFlow.setPyrScale_0(this.nativeObj, pyrScale);
    }

    public boolean getFastPyramids() {
        return FarnebackOpticalFlow.getFastPyramids_0(this.nativeObj);
    }

    public void setFastPyramids(boolean fastPyramids) {
        FarnebackOpticalFlow.setFastPyramids_0(this.nativeObj, fastPyramids);
    }

    public int getWinSize() {
        return FarnebackOpticalFlow.getWinSize_0(this.nativeObj);
    }

    public void setWinSize(int winSize) {
        FarnebackOpticalFlow.setWinSize_0(this.nativeObj, winSize);
    }

    public int getNumIters() {
        return FarnebackOpticalFlow.getNumIters_0(this.nativeObj);
    }

    public void setNumIters(int numIters) {
        FarnebackOpticalFlow.setNumIters_0(this.nativeObj, numIters);
    }

    public int getPolyN() {
        return FarnebackOpticalFlow.getPolyN_0(this.nativeObj);
    }

    public void setPolyN(int polyN) {
        FarnebackOpticalFlow.setPolyN_0(this.nativeObj, polyN);
    }

    public double getPolySigma() {
        return FarnebackOpticalFlow.getPolySigma_0(this.nativeObj);
    }

    public void setPolySigma(double polySigma) {
        FarnebackOpticalFlow.setPolySigma_0(this.nativeObj, polySigma);
    }

    public int getFlags() {
        return FarnebackOpticalFlow.getFlags_0(this.nativeObj);
    }

    public void setFlags(int flags) {
        FarnebackOpticalFlow.setFlags_0(this.nativeObj, flags);
    }

    public static FarnebackOpticalFlow create(int numLevels, double pyrScale, boolean fastPyramids, int winSize, int numIters, int polyN, double polySigma, int flags) {
        return FarnebackOpticalFlow.__fromPtr__(FarnebackOpticalFlow.create_0(numLevels, pyrScale, fastPyramids, winSize, numIters, polyN, polySigma, flags));
    }

    public static FarnebackOpticalFlow create(int numLevels, double pyrScale, boolean fastPyramids, int winSize, int numIters, int polyN, double polySigma) {
        return FarnebackOpticalFlow.__fromPtr__(FarnebackOpticalFlow.create_1(numLevels, pyrScale, fastPyramids, winSize, numIters, polyN, polySigma));
    }

    public static FarnebackOpticalFlow create(int numLevels, double pyrScale, boolean fastPyramids, int winSize, int numIters, int polyN) {
        return FarnebackOpticalFlow.__fromPtr__(FarnebackOpticalFlow.create_2(numLevels, pyrScale, fastPyramids, winSize, numIters, polyN));
    }

    public static FarnebackOpticalFlow create(int numLevels, double pyrScale, boolean fastPyramids, int winSize, int numIters) {
        return FarnebackOpticalFlow.__fromPtr__(FarnebackOpticalFlow.create_3(numLevels, pyrScale, fastPyramids, winSize, numIters));
    }

    public static FarnebackOpticalFlow create(int numLevels, double pyrScale, boolean fastPyramids, int winSize) {
        return FarnebackOpticalFlow.__fromPtr__(FarnebackOpticalFlow.create_4(numLevels, pyrScale, fastPyramids, winSize));
    }

    public static FarnebackOpticalFlow create(int numLevels, double pyrScale, boolean fastPyramids) {
        return FarnebackOpticalFlow.__fromPtr__(FarnebackOpticalFlow.create_5(numLevels, pyrScale, fastPyramids));
    }

    public static FarnebackOpticalFlow create(int numLevels, double pyrScale) {
        return FarnebackOpticalFlow.__fromPtr__(FarnebackOpticalFlow.create_6(numLevels, pyrScale));
    }

    public static FarnebackOpticalFlow create(int numLevels) {
        return FarnebackOpticalFlow.__fromPtr__(FarnebackOpticalFlow.create_7(numLevels));
    }

    public static FarnebackOpticalFlow create() {
        return FarnebackOpticalFlow.__fromPtr__(FarnebackOpticalFlow.create_8());
    }

    @Override
    protected void finalize() throws Throwable {
        FarnebackOpticalFlow.delete(this.nativeObj);
    }

    private static native int getNumLevels_0(long var0);

    private static native void setNumLevels_0(long var0, int var2);

    private static native double getPyrScale_0(long var0);

    private static native void setPyrScale_0(long var0, double var2);

    private static native boolean getFastPyramids_0(long var0);

    private static native void setFastPyramids_0(long var0, boolean var2);

    private static native int getWinSize_0(long var0);

    private static native void setWinSize_0(long var0, int var2);

    private static native int getNumIters_0(long var0);

    private static native void setNumIters_0(long var0, int var2);

    private static native int getPolyN_0(long var0);

    private static native void setPolyN_0(long var0, int var2);

    private static native double getPolySigma_0(long var0);

    private static native void setPolySigma_0(long var0, double var2);

    private static native int getFlags_0(long var0);

    private static native void setFlags_0(long var0, int var2);

    private static native long create_0(int var0, double var1, boolean var3, int var4, int var5, int var6, double var7, int var9);

    private static native long create_1(int var0, double var1, boolean var3, int var4, int var5, int var6, double var7);

    private static native long create_2(int var0, double var1, boolean var3, int var4, int var5, int var6);

    private static native long create_3(int var0, double var1, boolean var3, int var4, int var5);

    private static native long create_4(int var0, double var1, boolean var3, int var4);

    private static native long create_5(int var0, double var1, boolean var3);

    private static native long create_6(int var0, double var1);

    private static native long create_7(int var0);

    private static native long create_8();

    private static native void delete(long var0);
}

