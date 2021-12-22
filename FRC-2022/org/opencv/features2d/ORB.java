/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import org.opencv.features2d.Feature2D;

public class ORB
extends Feature2D {
    public static final int HARRIS_SCORE = 0;
    public static final int FAST_SCORE = 1;

    protected ORB(long addr) {
        super(addr);
    }

    public static ORB __fromPtr__(long addr) {
        return new ORB(addr);
    }

    public static ORB create(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel, int WTA_K, int scoreType, int patchSize, int fastThreshold) {
        return ORB.__fromPtr__(ORB.create_0(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel, WTA_K, scoreType, patchSize, fastThreshold));
    }

    public static ORB create(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel, int WTA_K, int scoreType, int patchSize) {
        return ORB.__fromPtr__(ORB.create_1(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel, WTA_K, scoreType, patchSize));
    }

    public static ORB create(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel, int WTA_K, int scoreType) {
        return ORB.__fromPtr__(ORB.create_2(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel, WTA_K, scoreType));
    }

    public static ORB create(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel, int WTA_K) {
        return ORB.__fromPtr__(ORB.create_3(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel, WTA_K));
    }

    public static ORB create(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold, int firstLevel) {
        return ORB.__fromPtr__(ORB.create_4(nfeatures, scaleFactor, nlevels, edgeThreshold, firstLevel));
    }

    public static ORB create(int nfeatures, float scaleFactor, int nlevels, int edgeThreshold) {
        return ORB.__fromPtr__(ORB.create_5(nfeatures, scaleFactor, nlevels, edgeThreshold));
    }

    public static ORB create(int nfeatures, float scaleFactor, int nlevels) {
        return ORB.__fromPtr__(ORB.create_6(nfeatures, scaleFactor, nlevels));
    }

    public static ORB create(int nfeatures, float scaleFactor) {
        return ORB.__fromPtr__(ORB.create_7(nfeatures, scaleFactor));
    }

    public static ORB create(int nfeatures) {
        return ORB.__fromPtr__(ORB.create_8(nfeatures));
    }

    public static ORB create() {
        return ORB.__fromPtr__(ORB.create_9());
    }

    public void setMaxFeatures(int maxFeatures) {
        ORB.setMaxFeatures_0(this.nativeObj, maxFeatures);
    }

    public int getMaxFeatures() {
        return ORB.getMaxFeatures_0(this.nativeObj);
    }

    public void setScaleFactor(double scaleFactor) {
        ORB.setScaleFactor_0(this.nativeObj, scaleFactor);
    }

    public double getScaleFactor() {
        return ORB.getScaleFactor_0(this.nativeObj);
    }

    public void setNLevels(int nlevels) {
        ORB.setNLevels_0(this.nativeObj, nlevels);
    }

    public int getNLevels() {
        return ORB.getNLevels_0(this.nativeObj);
    }

    public void setEdgeThreshold(int edgeThreshold) {
        ORB.setEdgeThreshold_0(this.nativeObj, edgeThreshold);
    }

    public int getEdgeThreshold() {
        return ORB.getEdgeThreshold_0(this.nativeObj);
    }

    public void setFirstLevel(int firstLevel) {
        ORB.setFirstLevel_0(this.nativeObj, firstLevel);
    }

    public int getFirstLevel() {
        return ORB.getFirstLevel_0(this.nativeObj);
    }

    public void setWTA_K(int wta_k) {
        ORB.setWTA_K_0(this.nativeObj, wta_k);
    }

    public int getWTA_K() {
        return ORB.getWTA_K_0(this.nativeObj);
    }

    public void setScoreType(int scoreType) {
        ORB.setScoreType_0(this.nativeObj, scoreType);
    }

    public int getScoreType() {
        return ORB.getScoreType_0(this.nativeObj);
    }

    public void setPatchSize(int patchSize) {
        ORB.setPatchSize_0(this.nativeObj, patchSize);
    }

    public int getPatchSize() {
        return ORB.getPatchSize_0(this.nativeObj);
    }

    public void setFastThreshold(int fastThreshold) {
        ORB.setFastThreshold_0(this.nativeObj, fastThreshold);
    }

    public int getFastThreshold() {
        return ORB.getFastThreshold_0(this.nativeObj);
    }

    @Override
    public String getDefaultName() {
        return ORB.getDefaultName_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        ORB.delete(this.nativeObj);
    }

    private static native long create_0(int var0, float var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8);

    private static native long create_1(int var0, float var1, int var2, int var3, int var4, int var5, int var6, int var7);

    private static native long create_2(int var0, float var1, int var2, int var3, int var4, int var5, int var6);

    private static native long create_3(int var0, float var1, int var2, int var3, int var4, int var5);

    private static native long create_4(int var0, float var1, int var2, int var3, int var4);

    private static native long create_5(int var0, float var1, int var2, int var3);

    private static native long create_6(int var0, float var1, int var2);

    private static native long create_7(int var0, float var1);

    private static native long create_8(int var0);

    private static native long create_9();

    private static native void setMaxFeatures_0(long var0, int var2);

    private static native int getMaxFeatures_0(long var0);

    private static native void setScaleFactor_0(long var0, double var2);

    private static native double getScaleFactor_0(long var0);

    private static native void setNLevels_0(long var0, int var2);

    private static native int getNLevels_0(long var0);

    private static native void setEdgeThreshold_0(long var0, int var2);

    private static native int getEdgeThreshold_0(long var0);

    private static native void setFirstLevel_0(long var0, int var2);

    private static native int getFirstLevel_0(long var0);

    private static native void setWTA_K_0(long var0, int var2);

    private static native int getWTA_K_0(long var0);

    private static native void setScoreType_0(long var0, int var2);

    private static native int getScoreType_0(long var0);

    private static native void setPatchSize_0(long var0, int var2);

    private static native int getPatchSize_0(long var0);

    private static native void setFastThreshold_0(long var0, int var2);

    private static native int getFastThreshold_0(long var0);

    private static native String getDefaultName_0(long var0);

    private static native void delete(long var0);
}

