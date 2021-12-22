/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.calib3d;

import org.opencv.calib3d.StereoMatcher;
import org.opencv.core.Rect;

public class StereoBM
extends StereoMatcher {
    public static final int PREFILTER_NORMALIZED_RESPONSE = 0;
    public static final int PREFILTER_XSOBEL = 1;

    protected StereoBM(long addr) {
        super(addr);
    }

    public static StereoBM __fromPtr__(long addr) {
        return new StereoBM(addr);
    }

    public int getPreFilterType() {
        return StereoBM.getPreFilterType_0(this.nativeObj);
    }

    public void setPreFilterType(int preFilterType) {
        StereoBM.setPreFilterType_0(this.nativeObj, preFilterType);
    }

    public int getPreFilterSize() {
        return StereoBM.getPreFilterSize_0(this.nativeObj);
    }

    public void setPreFilterSize(int preFilterSize) {
        StereoBM.setPreFilterSize_0(this.nativeObj, preFilterSize);
    }

    public int getPreFilterCap() {
        return StereoBM.getPreFilterCap_0(this.nativeObj);
    }

    public void setPreFilterCap(int preFilterCap) {
        StereoBM.setPreFilterCap_0(this.nativeObj, preFilterCap);
    }

    public int getTextureThreshold() {
        return StereoBM.getTextureThreshold_0(this.nativeObj);
    }

    public void setTextureThreshold(int textureThreshold) {
        StereoBM.setTextureThreshold_0(this.nativeObj, textureThreshold);
    }

    public int getUniquenessRatio() {
        return StereoBM.getUniquenessRatio_0(this.nativeObj);
    }

    public void setUniquenessRatio(int uniquenessRatio) {
        StereoBM.setUniquenessRatio_0(this.nativeObj, uniquenessRatio);
    }

    public int getSmallerBlockSize() {
        return StereoBM.getSmallerBlockSize_0(this.nativeObj);
    }

    public void setSmallerBlockSize(int blockSize) {
        StereoBM.setSmallerBlockSize_0(this.nativeObj, blockSize);
    }

    public Rect getROI1() {
        return new Rect(StereoBM.getROI1_0(this.nativeObj));
    }

    public void setROI1(Rect roi1) {
        StereoBM.setROI1_0(this.nativeObj, roi1.x, roi1.y, roi1.width, roi1.height);
    }

    public Rect getROI2() {
        return new Rect(StereoBM.getROI2_0(this.nativeObj));
    }

    public void setROI2(Rect roi2) {
        StereoBM.setROI2_0(this.nativeObj, roi2.x, roi2.y, roi2.width, roi2.height);
    }

    public static StereoBM create(int numDisparities, int blockSize) {
        return StereoBM.__fromPtr__(StereoBM.create_0(numDisparities, blockSize));
    }

    public static StereoBM create(int numDisparities) {
        return StereoBM.__fromPtr__(StereoBM.create_1(numDisparities));
    }

    public static StereoBM create() {
        return StereoBM.__fromPtr__(StereoBM.create_2());
    }

    @Override
    protected void finalize() throws Throwable {
        StereoBM.delete(this.nativeObj);
    }

    private static native int getPreFilterType_0(long var0);

    private static native void setPreFilterType_0(long var0, int var2);

    private static native int getPreFilterSize_0(long var0);

    private static native void setPreFilterSize_0(long var0, int var2);

    private static native int getPreFilterCap_0(long var0);

    private static native void setPreFilterCap_0(long var0, int var2);

    private static native int getTextureThreshold_0(long var0);

    private static native void setTextureThreshold_0(long var0, int var2);

    private static native int getUniquenessRatio_0(long var0);

    private static native void setUniquenessRatio_0(long var0, int var2);

    private static native int getSmallerBlockSize_0(long var0);

    private static native void setSmallerBlockSize_0(long var0, int var2);

    private static native double[] getROI1_0(long var0);

    private static native void setROI1_0(long var0, int var2, int var3, int var4, int var5);

    private static native double[] getROI2_0(long var0);

    private static native void setROI2_0(long var0, int var2, int var3, int var4, int var5);

    private static native long create_0(int var0, int var1);

    private static native long create_1(int var0);

    private static native long create_2();

    private static native void delete(long var0);
}

