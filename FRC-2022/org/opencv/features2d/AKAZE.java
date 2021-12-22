/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import org.opencv.features2d.Feature2D;

public class AKAZE
extends Feature2D {
    public static final int DESCRIPTOR_KAZE_UPRIGHT = 2;
    public static final int DESCRIPTOR_KAZE = 3;
    public static final int DESCRIPTOR_MLDB_UPRIGHT = 4;
    public static final int DESCRIPTOR_MLDB = 5;

    protected AKAZE(long addr) {
        super(addr);
    }

    public static AKAZE __fromPtr__(long addr) {
        return new AKAZE(addr);
    }

    public static AKAZE create(int descriptor_type, int descriptor_size, int descriptor_channels, float threshold, int nOctaves, int nOctaveLayers, int diffusivity) {
        return AKAZE.__fromPtr__(AKAZE.create_0(descriptor_type, descriptor_size, descriptor_channels, threshold, nOctaves, nOctaveLayers, diffusivity));
    }

    public static AKAZE create(int descriptor_type, int descriptor_size, int descriptor_channels, float threshold, int nOctaves, int nOctaveLayers) {
        return AKAZE.__fromPtr__(AKAZE.create_1(descriptor_type, descriptor_size, descriptor_channels, threshold, nOctaves, nOctaveLayers));
    }

    public static AKAZE create(int descriptor_type, int descriptor_size, int descriptor_channels, float threshold, int nOctaves) {
        return AKAZE.__fromPtr__(AKAZE.create_2(descriptor_type, descriptor_size, descriptor_channels, threshold, nOctaves));
    }

    public static AKAZE create(int descriptor_type, int descriptor_size, int descriptor_channels, float threshold) {
        return AKAZE.__fromPtr__(AKAZE.create_3(descriptor_type, descriptor_size, descriptor_channels, threshold));
    }

    public static AKAZE create(int descriptor_type, int descriptor_size, int descriptor_channels) {
        return AKAZE.__fromPtr__(AKAZE.create_4(descriptor_type, descriptor_size, descriptor_channels));
    }

    public static AKAZE create(int descriptor_type, int descriptor_size) {
        return AKAZE.__fromPtr__(AKAZE.create_5(descriptor_type, descriptor_size));
    }

    public static AKAZE create(int descriptor_type) {
        return AKAZE.__fromPtr__(AKAZE.create_6(descriptor_type));
    }

    public static AKAZE create() {
        return AKAZE.__fromPtr__(AKAZE.create_7());
    }

    public void setDescriptorType(int dtype) {
        AKAZE.setDescriptorType_0(this.nativeObj, dtype);
    }

    public int getDescriptorType() {
        return AKAZE.getDescriptorType_0(this.nativeObj);
    }

    public void setDescriptorSize(int dsize) {
        AKAZE.setDescriptorSize_0(this.nativeObj, dsize);
    }

    public int getDescriptorSize() {
        return AKAZE.getDescriptorSize_0(this.nativeObj);
    }

    public void setDescriptorChannels(int dch) {
        AKAZE.setDescriptorChannels_0(this.nativeObj, dch);
    }

    public int getDescriptorChannels() {
        return AKAZE.getDescriptorChannels_0(this.nativeObj);
    }

    public void setThreshold(double threshold) {
        AKAZE.setThreshold_0(this.nativeObj, threshold);
    }

    public double getThreshold() {
        return AKAZE.getThreshold_0(this.nativeObj);
    }

    public void setNOctaves(int octaves) {
        AKAZE.setNOctaves_0(this.nativeObj, octaves);
    }

    public int getNOctaves() {
        return AKAZE.getNOctaves_0(this.nativeObj);
    }

    public void setNOctaveLayers(int octaveLayers) {
        AKAZE.setNOctaveLayers_0(this.nativeObj, octaveLayers);
    }

    public int getNOctaveLayers() {
        return AKAZE.getNOctaveLayers_0(this.nativeObj);
    }

    public void setDiffusivity(int diff) {
        AKAZE.setDiffusivity_0(this.nativeObj, diff);
    }

    public int getDiffusivity() {
        return AKAZE.getDiffusivity_0(this.nativeObj);
    }

    @Override
    public String getDefaultName() {
        return AKAZE.getDefaultName_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        AKAZE.delete(this.nativeObj);
    }

    private static native long create_0(int var0, int var1, int var2, float var3, int var4, int var5, int var6);

    private static native long create_1(int var0, int var1, int var2, float var3, int var4, int var5);

    private static native long create_2(int var0, int var1, int var2, float var3, int var4);

    private static native long create_3(int var0, int var1, int var2, float var3);

    private static native long create_4(int var0, int var1, int var2);

    private static native long create_5(int var0, int var1);

    private static native long create_6(int var0);

    private static native long create_7();

    private static native void setDescriptorType_0(long var0, int var2);

    private static native int getDescriptorType_0(long var0);

    private static native void setDescriptorSize_0(long var0, int var2);

    private static native int getDescriptorSize_0(long var0);

    private static native void setDescriptorChannels_0(long var0, int var2);

    private static native int getDescriptorChannels_0(long var0);

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

