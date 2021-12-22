/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.video.SparseOpticalFlow;

public class SparsePyrLKOpticalFlow
extends SparseOpticalFlow {
    protected SparsePyrLKOpticalFlow(long addr) {
        super(addr);
    }

    public static SparsePyrLKOpticalFlow __fromPtr__(long addr) {
        return new SparsePyrLKOpticalFlow(addr);
    }

    public Size getWinSize() {
        return new Size(SparsePyrLKOpticalFlow.getWinSize_0(this.nativeObj));
    }

    public void setWinSize(Size winSize) {
        SparsePyrLKOpticalFlow.setWinSize_0(this.nativeObj, winSize.width, winSize.height);
    }

    public int getMaxLevel() {
        return SparsePyrLKOpticalFlow.getMaxLevel_0(this.nativeObj);
    }

    public void setMaxLevel(int maxLevel) {
        SparsePyrLKOpticalFlow.setMaxLevel_0(this.nativeObj, maxLevel);
    }

    public TermCriteria getTermCriteria() {
        return new TermCriteria(SparsePyrLKOpticalFlow.getTermCriteria_0(this.nativeObj));
    }

    public void setTermCriteria(TermCriteria crit) {
        SparsePyrLKOpticalFlow.setTermCriteria_0(this.nativeObj, crit.type, crit.maxCount, crit.epsilon);
    }

    public int getFlags() {
        return SparsePyrLKOpticalFlow.getFlags_0(this.nativeObj);
    }

    public void setFlags(int flags) {
        SparsePyrLKOpticalFlow.setFlags_0(this.nativeObj, flags);
    }

    public double getMinEigThreshold() {
        return SparsePyrLKOpticalFlow.getMinEigThreshold_0(this.nativeObj);
    }

    public void setMinEigThreshold(double minEigThreshold) {
        SparsePyrLKOpticalFlow.setMinEigThreshold_0(this.nativeObj, minEigThreshold);
    }

    public static SparsePyrLKOpticalFlow create(Size winSize, int maxLevel, TermCriteria crit, int flags, double minEigThreshold) {
        return SparsePyrLKOpticalFlow.__fromPtr__(SparsePyrLKOpticalFlow.create_0(winSize.width, winSize.height, maxLevel, crit.type, crit.maxCount, crit.epsilon, flags, minEigThreshold));
    }

    public static SparsePyrLKOpticalFlow create(Size winSize, int maxLevel, TermCriteria crit, int flags) {
        return SparsePyrLKOpticalFlow.__fromPtr__(SparsePyrLKOpticalFlow.create_1(winSize.width, winSize.height, maxLevel, crit.type, crit.maxCount, crit.epsilon, flags));
    }

    public static SparsePyrLKOpticalFlow create(Size winSize, int maxLevel, TermCriteria crit) {
        return SparsePyrLKOpticalFlow.__fromPtr__(SparsePyrLKOpticalFlow.create_2(winSize.width, winSize.height, maxLevel, crit.type, crit.maxCount, crit.epsilon));
    }

    public static SparsePyrLKOpticalFlow create(Size winSize, int maxLevel) {
        return SparsePyrLKOpticalFlow.__fromPtr__(SparsePyrLKOpticalFlow.create_3(winSize.width, winSize.height, maxLevel));
    }

    public static SparsePyrLKOpticalFlow create(Size winSize) {
        return SparsePyrLKOpticalFlow.__fromPtr__(SparsePyrLKOpticalFlow.create_4(winSize.width, winSize.height));
    }

    public static SparsePyrLKOpticalFlow create() {
        return SparsePyrLKOpticalFlow.__fromPtr__(SparsePyrLKOpticalFlow.create_5());
    }

    @Override
    protected void finalize() throws Throwable {
        SparsePyrLKOpticalFlow.delete(this.nativeObj);
    }

    private static native double[] getWinSize_0(long var0);

    private static native void setWinSize_0(long var0, double var2, double var4);

    private static native int getMaxLevel_0(long var0);

    private static native void setMaxLevel_0(long var0, int var2);

    private static native double[] getTermCriteria_0(long var0);

    private static native void setTermCriteria_0(long var0, int var2, int var3, double var4);

    private static native int getFlags_0(long var0);

    private static native void setFlags_0(long var0, int var2);

    private static native double getMinEigThreshold_0(long var0);

    private static native void setMinEigThreshold_0(long var0, double var2);

    private static native long create_0(double var0, double var2, int var4, int var5, int var6, double var7, int var9, double var10);

    private static native long create_1(double var0, double var2, int var4, int var5, int var6, double var7, int var9);

    private static native long create_2(double var0, double var2, int var4, int var5, int var6, double var7);

    private static native long create_3(double var0, double var2, int var4);

    private static native long create_4(double var0, double var2);

    private static native long create_5();

    private static native void delete(long var0);
}

