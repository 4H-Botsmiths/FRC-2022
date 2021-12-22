/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.calib3d;

import org.opencv.calib3d.StereoMatcher;

public class StereoSGBM
extends StereoMatcher {
    public static final int MODE_SGBM = 0;
    public static final int MODE_HH = 1;
    public static final int MODE_SGBM_3WAY = 2;
    public static final int MODE_HH4 = 3;

    protected StereoSGBM(long addr) {
        super(addr);
    }

    public static StereoSGBM __fromPtr__(long addr) {
        return new StereoSGBM(addr);
    }

    public int getPreFilterCap() {
        return StereoSGBM.getPreFilterCap_0(this.nativeObj);
    }

    public void setPreFilterCap(int preFilterCap) {
        StereoSGBM.setPreFilterCap_0(this.nativeObj, preFilterCap);
    }

    public int getUniquenessRatio() {
        return StereoSGBM.getUniquenessRatio_0(this.nativeObj);
    }

    public void setUniquenessRatio(int uniquenessRatio) {
        StereoSGBM.setUniquenessRatio_0(this.nativeObj, uniquenessRatio);
    }

    public int getP1() {
        return StereoSGBM.getP1_0(this.nativeObj);
    }

    public void setP1(int P1) {
        StereoSGBM.setP1_0(this.nativeObj, P1);
    }

    public int getP2() {
        return StereoSGBM.getP2_0(this.nativeObj);
    }

    public void setP2(int P2) {
        StereoSGBM.setP2_0(this.nativeObj, P2);
    }

    public int getMode() {
        return StereoSGBM.getMode_0(this.nativeObj);
    }

    public void setMode(int mode) {
        StereoSGBM.setMode_0(this.nativeObj, mode);
    }

    public static StereoSGBM create(int minDisparity, int numDisparities, int blockSize, int P1, int P2, int disp12MaxDiff, int preFilterCap, int uniquenessRatio, int speckleWindowSize, int speckleRange, int mode) {
        return StereoSGBM.__fromPtr__(StereoSGBM.create_0(minDisparity, numDisparities, blockSize, P1, P2, disp12MaxDiff, preFilterCap, uniquenessRatio, speckleWindowSize, speckleRange, mode));
    }

    public static StereoSGBM create(int minDisparity, int numDisparities, int blockSize, int P1, int P2, int disp12MaxDiff, int preFilterCap, int uniquenessRatio, int speckleWindowSize, int speckleRange) {
        return StereoSGBM.__fromPtr__(StereoSGBM.create_1(minDisparity, numDisparities, blockSize, P1, P2, disp12MaxDiff, preFilterCap, uniquenessRatio, speckleWindowSize, speckleRange));
    }

    public static StereoSGBM create(int minDisparity, int numDisparities, int blockSize, int P1, int P2, int disp12MaxDiff, int preFilterCap, int uniquenessRatio, int speckleWindowSize) {
        return StereoSGBM.__fromPtr__(StereoSGBM.create_2(minDisparity, numDisparities, blockSize, P1, P2, disp12MaxDiff, preFilterCap, uniquenessRatio, speckleWindowSize));
    }

    public static StereoSGBM create(int minDisparity, int numDisparities, int blockSize, int P1, int P2, int disp12MaxDiff, int preFilterCap, int uniquenessRatio) {
        return StereoSGBM.__fromPtr__(StereoSGBM.create_3(minDisparity, numDisparities, blockSize, P1, P2, disp12MaxDiff, preFilterCap, uniquenessRatio));
    }

    public static StereoSGBM create(int minDisparity, int numDisparities, int blockSize, int P1, int P2, int disp12MaxDiff, int preFilterCap) {
        return StereoSGBM.__fromPtr__(StereoSGBM.create_4(minDisparity, numDisparities, blockSize, P1, P2, disp12MaxDiff, preFilterCap));
    }

    public static StereoSGBM create(int minDisparity, int numDisparities, int blockSize, int P1, int P2, int disp12MaxDiff) {
        return StereoSGBM.__fromPtr__(StereoSGBM.create_5(minDisparity, numDisparities, blockSize, P1, P2, disp12MaxDiff));
    }

    public static StereoSGBM create(int minDisparity, int numDisparities, int blockSize, int P1, int P2) {
        return StereoSGBM.__fromPtr__(StereoSGBM.create_6(minDisparity, numDisparities, blockSize, P1, P2));
    }

    public static StereoSGBM create(int minDisparity, int numDisparities, int blockSize, int P1) {
        return StereoSGBM.__fromPtr__(StereoSGBM.create_7(minDisparity, numDisparities, blockSize, P1));
    }

    public static StereoSGBM create(int minDisparity, int numDisparities, int blockSize) {
        return StereoSGBM.__fromPtr__(StereoSGBM.create_8(minDisparity, numDisparities, blockSize));
    }

    public static StereoSGBM create(int minDisparity, int numDisparities) {
        return StereoSGBM.__fromPtr__(StereoSGBM.create_9(minDisparity, numDisparities));
    }

    public static StereoSGBM create(int minDisparity) {
        return StereoSGBM.__fromPtr__(StereoSGBM.create_10(minDisparity));
    }

    public static StereoSGBM create() {
        return StereoSGBM.__fromPtr__(StereoSGBM.create_11());
    }

    @Override
    protected void finalize() throws Throwable {
        StereoSGBM.delete(this.nativeObj);
    }

    private static native int getPreFilterCap_0(long var0);

    private static native void setPreFilterCap_0(long var0, int var2);

    private static native int getUniquenessRatio_0(long var0);

    private static native void setUniquenessRatio_0(long var0, int var2);

    private static native int getP1_0(long var0);

    private static native void setP1_0(long var0, int var2);

    private static native int getP2_0(long var0);

    private static native void setP2_0(long var0, int var2);

    private static native int getMode_0(long var0);

    private static native void setMode_0(long var0, int var2);

    private static native long create_0(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10);

    private static native long create_1(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9);

    private static native long create_2(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8);

    private static native long create_3(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7);

    private static native long create_4(int var0, int var1, int var2, int var3, int var4, int var5, int var6);

    private static native long create_5(int var0, int var1, int var2, int var3, int var4, int var5);

    private static native long create_6(int var0, int var1, int var2, int var3, int var4);

    private static native long create_7(int var0, int var1, int var2, int var3);

    private static native long create_8(int var0, int var1, int var2);

    private static native long create_9(int var0, int var1);

    private static native long create_10(int var0);

    private static native long create_11();

    private static native void delete(long var0);
}

