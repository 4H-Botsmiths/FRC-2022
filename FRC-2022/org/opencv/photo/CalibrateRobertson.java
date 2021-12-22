/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.photo;

import org.opencv.core.Mat;
import org.opencv.photo.CalibrateCRF;

public class CalibrateRobertson
extends CalibrateCRF {
    protected CalibrateRobertson(long addr) {
        super(addr);
    }

    public static CalibrateRobertson __fromPtr__(long addr) {
        return new CalibrateRobertson(addr);
    }

    public int getMaxIter() {
        return CalibrateRobertson.getMaxIter_0(this.nativeObj);
    }

    public void setMaxIter(int max_iter) {
        CalibrateRobertson.setMaxIter_0(this.nativeObj, max_iter);
    }

    public float getThreshold() {
        return CalibrateRobertson.getThreshold_0(this.nativeObj);
    }

    public void setThreshold(float threshold) {
        CalibrateRobertson.setThreshold_0(this.nativeObj, threshold);
    }

    public Mat getRadiance() {
        return new Mat(CalibrateRobertson.getRadiance_0(this.nativeObj));
    }

    @Override
    protected void finalize() throws Throwable {
        CalibrateRobertson.delete(this.nativeObj);
    }

    private static native int getMaxIter_0(long var0);

    private static native void setMaxIter_0(long var0, int var2);

    private static native float getThreshold_0(long var0);

    private static native void setThreshold_0(long var0, float var2);

    private static native long getRadiance_0(long var0);

    private static native void delete(long var0);
}

