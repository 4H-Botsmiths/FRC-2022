/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import org.opencv.features2d.Feature2D;

public class FastFeatureDetector
extends Feature2D {
    public static final int THRESHOLD = 10000;
    public static final int NONMAX_SUPPRESSION = 10001;
    public static final int FAST_N = 10002;
    public static final int TYPE_5_8 = 0;
    public static final int TYPE_7_12 = 1;
    public static final int TYPE_9_16 = 2;

    protected FastFeatureDetector(long addr) {
        super(addr);
    }

    public static FastFeatureDetector __fromPtr__(long addr) {
        return new FastFeatureDetector(addr);
    }

    public static FastFeatureDetector create(int threshold, boolean nonmaxSuppression, int type) {
        return FastFeatureDetector.__fromPtr__(FastFeatureDetector.create_0(threshold, nonmaxSuppression, type));
    }

    public static FastFeatureDetector create(int threshold, boolean nonmaxSuppression) {
        return FastFeatureDetector.__fromPtr__(FastFeatureDetector.create_1(threshold, nonmaxSuppression));
    }

    public static FastFeatureDetector create(int threshold) {
        return FastFeatureDetector.__fromPtr__(FastFeatureDetector.create_2(threshold));
    }

    public static FastFeatureDetector create() {
        return FastFeatureDetector.__fromPtr__(FastFeatureDetector.create_3());
    }

    public void setThreshold(int threshold) {
        FastFeatureDetector.setThreshold_0(this.nativeObj, threshold);
    }

    public int getThreshold() {
        return FastFeatureDetector.getThreshold_0(this.nativeObj);
    }

    public void setNonmaxSuppression(boolean f) {
        FastFeatureDetector.setNonmaxSuppression_0(this.nativeObj, f);
    }

    public boolean getNonmaxSuppression() {
        return FastFeatureDetector.getNonmaxSuppression_0(this.nativeObj);
    }

    public void setType(int type) {
        FastFeatureDetector.setType_0(this.nativeObj, type);
    }

    public int getType() {
        return FastFeatureDetector.getType_0(this.nativeObj);
    }

    @Override
    public String getDefaultName() {
        return FastFeatureDetector.getDefaultName_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        FastFeatureDetector.delete(this.nativeObj);
    }

    private static native long create_0(int var0, boolean var1, int var2);

    private static native long create_1(int var0, boolean var1);

    private static native long create_2(int var0);

    private static native long create_3();

    private static native void setThreshold_0(long var0, int var2);

    private static native int getThreshold_0(long var0);

    private static native void setNonmaxSuppression_0(long var0, boolean var2);

    private static native boolean getNonmaxSuppression_0(long var0);

    private static native void setType_0(long var0, int var2);

    private static native int getType_0(long var0);

    private static native String getDefaultName_0(long var0);

    private static native void delete(long var0);
}

