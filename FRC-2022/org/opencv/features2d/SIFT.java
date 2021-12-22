/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import org.opencv.features2d.Feature2D;

public class SIFT
extends Feature2D {
    protected SIFT(long addr) {
        super(addr);
    }

    public static SIFT __fromPtr__(long addr) {
        return new SIFT(addr);
    }

    public static SIFT create(int nfeatures, int nOctaveLayers, double contrastThreshold, double edgeThreshold, double sigma) {
        return SIFT.__fromPtr__(SIFT.create_0(nfeatures, nOctaveLayers, contrastThreshold, edgeThreshold, sigma));
    }

    public static SIFT create(int nfeatures, int nOctaveLayers, double contrastThreshold, double edgeThreshold) {
        return SIFT.__fromPtr__(SIFT.create_1(nfeatures, nOctaveLayers, contrastThreshold, edgeThreshold));
    }

    public static SIFT create(int nfeatures, int nOctaveLayers, double contrastThreshold) {
        return SIFT.__fromPtr__(SIFT.create_2(nfeatures, nOctaveLayers, contrastThreshold));
    }

    public static SIFT create(int nfeatures, int nOctaveLayers) {
        return SIFT.__fromPtr__(SIFT.create_3(nfeatures, nOctaveLayers));
    }

    public static SIFT create(int nfeatures) {
        return SIFT.__fromPtr__(SIFT.create_4(nfeatures));
    }

    public static SIFT create() {
        return SIFT.__fromPtr__(SIFT.create_5());
    }

    public static SIFT create(int nfeatures, int nOctaveLayers, double contrastThreshold, double edgeThreshold, double sigma, int descriptorType) {
        return SIFT.__fromPtr__(SIFT.create_6(nfeatures, nOctaveLayers, contrastThreshold, edgeThreshold, sigma, descriptorType));
    }

    @Override
    public String getDefaultName() {
        return SIFT.getDefaultName_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        SIFT.delete(this.nativeObj);
    }

    private static native long create_0(int var0, int var1, double var2, double var4, double var6);

    private static native long create_1(int var0, int var1, double var2, double var4);

    private static native long create_2(int var0, int var1, double var2);

    private static native long create_3(int var0, int var1);

    private static native long create_4(int var0);

    private static native long create_5();

    private static native long create_6(int var0, int var1, double var2, double var4, double var6, int var8);

    private static native String getDefaultName_0(long var0);

    private static native void delete(long var0);
}

