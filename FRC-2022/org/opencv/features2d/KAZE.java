/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import org.opencv.features2d.Feature2D;

public class KAZE
extends Feature2D {
    public static final int DIFF_PM_G1 = 0;
    public static final int DIFF_PM_G2 = 1;
    public static final int DIFF_WEICKERT = 2;
    public static final int DIFF_CHARBONNIER = 3;

    protected KAZE(long addr) {
        super(addr);
    }

    public static KAZE __fromPtr__(long addr) {
        return new KAZE(addr);
    }

    public static KAZE create(boolean extended, boolean upright, float threshold, int nOctaves, int nOctaveLayers, int diffusivity) {
        return KAZE.__fromPtr__(KAZE.create_0(extended, upright, threshold, nOctaves, nOctaveLayers, diffusivity));
    }

    public static KAZE create(boolean extended, boolean upright, float threshold, int nOctaves, int nOctaveLayers) {
        return KAZE.__fromPtr__(KAZE.create_1(extended, upright, threshold, nOctaves, nOctaveLayers));
    }

    public static KAZE create(boolean extended, boolean upright, float threshold, int nOctaves) {
        return KAZE.__fromPtr__(KAZE.create_2(extended, upright, threshold, nOctaves));
    }

    public static KAZE create(boolean extended, boolean upright, float threshold) {
        return KAZE.__fromPtr__(KAZE.create_3(extended, upright, threshold));
    }

    public static KAZE create(boolean extended, boolean upright) {
        return KAZE.__fromPtr__(KAZE.create_4(extended, upright));
    }

    public static KAZE create(boolean extended) {
        return KAZE.__fromPtr__(KAZE.create_5(extended));
    }

    public static KAZE create() {
        return KAZE.__fromPtr__(KAZE.create_6());
    }

    public void setExtended(boolean extended) {
        KAZE.setExtended_0(this.nativeObj, extended);
    }

    public boolean getExtended() {
        return KAZE.getExtended_0(this.nativeObj);
    }

    public void setUpright(boolean upright) {
        KAZE.setUpright_0(this.nativeObj, upright);
    }

    public boolean getUpright() {
        return KAZE.getUpright_0(this.nativeObj);
    }

    public void setThreshold(double threshold) {
        KAZE.setThreshold_0(this.nativeObj, threshold);
    }

    public double getThreshold() {
        return KAZE.getThreshold_0(this.nativeObj);
    }

    public void setNOctaves(int octaves) {
        KAZE.setNOctaves_0(this.nativeObj, octaves);
    }

    public int getNOctaves() {
        return KAZE.getNOctaves_0(this.nativeObj);
    }

    public void setNOctaveLayers(int octaveLayers) {
        KAZE.setNOctaveLayers_0(this.nativeObj, octaveLayers);
    }

    public int getNOctaveLayers() {
        return KAZE.getNOctaveLayers_0(this.nativeObj);
    }

    public void setDiffusivity(int diff) {
        KAZE.setDiffusivity_0(this.nativeObj, diff);
    }

    public int getDiffusivity() {
        return KAZE.getDiffusivity_0(this.nativeObj);
    }

    @Override
    public String getDefaultName() {
        return KAZE.getDefaultName_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        KAZE.delete(this.nativeObj);
    }

    private static native long create_0(boolean var0, boolean var1, float var2, int var3, int var4, int var5);

    private static native long create_1(boolean var0, boolean var1, float var2, int var3, int var4);

    private static native long create_2(boolean var0, boolean var1, float var2, int var3);

    private static native long create_3(boolean var0, boolean var1, float var2);

    private static native long create_4(boolean var0, boolean var1);

    private static native long create_5(boolean var0);

    private static native long create_6();

    private static native void setExtended_0(long var0, boolean var2);

    private static native boolean getExtended_0(long var0);

    private static native void setUpright_0(long var0, boolean var2);

    private static native boolean getUpright_0(long var0);

    private static native void setThreshold_0(long var0, double var2);

    private static native double getThreshold_0(long var0);

    private static native void setNOctaves_0(long var0, int var2);

    private static native int getNOctaves_0(long var0);

    private static native void setNOctaveLayers_0(long var0, int var2);

    private static native int getNOctaveLayers_0(long var0);

    private static native void setDiffusivity_0(long var0, int var2);

    private static native int getDiffusivity_0(long var0);

    private static native String getDefaultName_0(long var0);

    private static native void delete(long var0);
}

