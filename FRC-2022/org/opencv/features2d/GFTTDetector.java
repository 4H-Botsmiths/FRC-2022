/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import org.opencv.features2d.Feature2D;

public class GFTTDetector
extends Feature2D {
    protected GFTTDetector(long addr) {
        super(addr);
    }

    public static GFTTDetector __fromPtr__(long addr) {
        return new GFTTDetector(addr);
    }

    public static GFTTDetector create(int maxCorners, double qualityLevel, double minDistance, int blockSize, boolean useHarrisDetector, double k) {
        return GFTTDetector.__fromPtr__(GFTTDetector.create_0(maxCorners, qualityLevel, minDistance, blockSize, useHarrisDetector, k));
    }

    public static GFTTDetector create(int maxCorners, double qualityLevel, double minDistance, int blockSize, boolean useHarrisDetector) {
        return GFTTDetector.__fromPtr__(GFTTDetector.create_1(maxCorners, qualityLevel, minDistance, blockSize, useHarrisDetector));
    }

    public static GFTTDetector create(int maxCorners, double qualityLevel, double minDistance, int blockSize) {
        return GFTTDetector.__fromPtr__(GFTTDetector.create_2(maxCorners, qualityLevel, minDistance, blockSize));
    }

    public static GFTTDetector create(int maxCorners, double qualityLevel, double minDistance) {
        return GFTTDetector.__fromPtr__(GFTTDetector.create_3(maxCorners, qualityLevel, minDistance));
    }

    public static GFTTDetector create(int maxCorners, double qualityLevel) {
        return GFTTDetector.__fromPtr__(GFTTDetector.create_4(maxCorners, qualityLevel));
    }

    public static GFTTDetector create(int maxCorners) {
        return GFTTDetector.__fromPtr__(GFTTDetector.create_5(maxCorners));
    }

    public static GFTTDetector create() {
        return GFTTDetector.__fromPtr__(GFTTDetector.create_6());
    }

    public static GFTTDetector create(int maxCorners, double qualityLevel, double minDistance, int blockSize, int gradiantSize, boolean useHarrisDetector, double k) {
        return GFTTDetector.__fromPtr__(GFTTDetector.create_7(maxCorners, qualityLevel, minDistance, blockSize, gradiantSize, useHarrisDetector, k));
    }

    public static GFTTDetector create(int maxCorners, double qualityLevel, double minDistance, int blockSize, int gradiantSize, boolean useHarrisDetector) {
        return GFTTDetector.__fromPtr__(GFTTDetector.create_8(maxCorners, qualityLevel, minDistance, blockSize, gradiantSize, useHarrisDetector));
    }

    public static GFTTDetector create(int maxCorners, double qualityLevel, double minDistance, int blockSize, int gradiantSize) {
        return GFTTDetector.__fromPtr__(GFTTDetector.create_9(maxCorners, qualityLevel, minDistance, blockSize, gradiantSize));
    }

    public void setMaxFeatures(int maxFeatures) {
        GFTTDetector.setMaxFeatures_0(this.nativeObj, maxFeatures);
    }

    public int getMaxFeatures() {
        return GFTTDetector.getMaxFeatures_0(this.nativeObj);
    }

    public void setQualityLevel(double qlevel) {
        GFTTDetector.setQualityLevel_0(this.nativeObj, qlevel);
    }

    public double getQualityLevel() {
        return GFTTDetector.getQualityLevel_0(this.nativeObj);
    }

    public void setMinDistance(double minDistance) {
        GFTTDetector.setMinDistance_0(this.nativeObj, minDistance);
    }

    public double getMinDistance() {
        return GFTTDetector.getMinDistance_0(this.nativeObj);
    }

    public void setBlockSize(int blockSize) {
        GFTTDetector.setBlockSize_0(this.nativeObj, blockSize);
    }

    public int getBlockSize() {
        return GFTTDetector.getBlockSize_0(this.nativeObj);
    }

    public void setHarrisDetector(boolean val) {
        GFTTDetector.setHarrisDetector_0(this.nativeObj, val);
    }

    public boolean getHarrisDetector() {
        return GFTTDetector.getHarrisDetector_0(this.nativeObj);
    }

    public void setK(double k) {
        GFTTDetector.setK_0(this.nativeObj, k);
    }

    public double getK() {
        return GFTTDetector.getK_0(this.nativeObj);
    }

    @Override
    public String getDefaultName() {
        return GFTTDetector.getDefaultName_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        GFTTDetector.delete(this.nativeObj);
    }

    private static native long create_0(int var0, double var1, double var3, int var5, boolean var6, double var7);

    private static native long create_1(int var0, double var1, double var3, int var5, boolean var6);

    private static native long create_2(int var0, double var1, double var3, int var5);

    private static native long create_3(int var0, double var1, double var3);

    private static native long create_4(int var0, double var1);

    private static native long create_5(int var0);

    private static native long create_6();

    private static native long create_7(int var0, double var1, double var3, int var5, int var6, boolean var7, double var8);

    private static native long create_8(int var0, double var1, double var3, int var5, int var6, boolean var7);

    private static native long create_9(int var0, double var1, double var3, int var5, int var6);

    private static native void setMaxFeatures_0(long var0, int var2);

    private static native int getMaxFeatures_0(long var0);

    private static native void setQualityLevel_0(long var0, double var2);

    private static native double getQualityLevel_0(long var0);

    private static native void setMinDistance_0(long var0, double var2);

    private static native double getMinDistance_0(long var0);

    private static native void setBlockSize_0(long var0, int var2);

    private static native int getBlockSize_0(long var0);

    private static native void setHarrisDetector_0(long var0, boolean var2);

    private static native boolean getHarrisDetector_0(long var0);

    private static native void setK_0(long var0, double var2);

    private static native double getK_0(long var0);

    private static native String getDefaultName_0(long var0);

    private static native void delete(long var0);
}

