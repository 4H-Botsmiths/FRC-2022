/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import org.opencv.features2d.Feature2D;

public class AgastFeatureDetector
extends Feature2D {
    public static final int THRESHOLD = 10000;
    public static final int NONMAX_SUPPRESSION = 10001;
    public static final int AGAST_5_8 = 0;
    public static final int AGAST_7_12d = 1;
    public static final int AGAST_7_12s = 2;
    public static final int OAST_9_16 = 3;

    protected AgastFeatureDetector(long addr) {
        super(addr);
    }

    public static AgastFeatureDetector __fromPtr__(long addr) {
        return new AgastFeatureDetector(addr);
    }

    public static AgastFeatureDetector create(int threshold, boolean nonmaxSuppression, int type) {
        return AgastFeatureDetector.__fromPtr__(AgastFeatureDetector.create_0(threshold, nonmaxSuppression, type));
    }

    public static AgastFeatureDetector create(int threshold, boolean nonmaxSuppression) {
        return AgastFeatureDetector.__fromPtr__(AgastFeatureDetector.create_1(threshold, nonmaxSuppression));
    }

    public static AgastFeatureDetector create(int threshold) {
        return AgastFeatureDetector.__fromPtr__(AgastFeatureDetector.create_2(threshold));
    }

    public static AgastFeatureDetector create() {
        return AgastFeatureDetector.__fromPtr__(AgastFeatureDetector.create_3());
    }

    public void setThreshold(int threshold) {
        AgastFeatureDetector.setThreshold_0(this.nativeObj, threshold);
    }

    public int getThreshold() {
        return AgastFeatureDetector.getThreshold_0(this.nativeObj);
    }

    public void setNonmaxSuppression(boolean f) {
        AgastFeatureDetector.setNonmaxSuppression_0(this.nativeObj, f);
    }

    public boolean getNonmaxSuppression() {
        return AgastFeatureDetector.getNonmaxSuppression_0(this.nativeObj);
    }

    public void setType(int type) {
        AgastFeatureDetector.setType_0(this.nativeObj, type);
    }

    public int getType() {
        return AgastFeatureDetector.getType_0(this.nativeObj);
    }

    @Override
    public String getDefaultName() {
        return AgastFeatureDetector.getDefaultName_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        AgastFeatureDetector.delete(this.nativeObj);
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

