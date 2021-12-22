/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import org.opencv.core.MatOfFloat;
import org.opencv.features2d.Feature2D;

public class AffineFeature
extends Feature2D {
    protected AffineFeature(long addr) {
        super(addr);
    }

    public static AffineFeature __fromPtr__(long addr) {
        return new AffineFeature(addr);
    }

    public static AffineFeature create(Feature2D backend, int maxTilt, int minTilt, float tiltStep, float rotateStepBase) {
        return AffineFeature.__fromPtr__(AffineFeature.create_0(backend.getNativeObjAddr(), maxTilt, minTilt, tiltStep, rotateStepBase));
    }

    public static AffineFeature create(Feature2D backend, int maxTilt, int minTilt, float tiltStep) {
        return AffineFeature.__fromPtr__(AffineFeature.create_1(backend.getNativeObjAddr(), maxTilt, minTilt, tiltStep));
    }

    public static AffineFeature create(Feature2D backend, int maxTilt, int minTilt) {
        return AffineFeature.__fromPtr__(AffineFeature.create_2(backend.getNativeObjAddr(), maxTilt, minTilt));
    }

    public static AffineFeature create(Feature2D backend, int maxTilt) {
        return AffineFeature.__fromPtr__(AffineFeature.create_3(backend.getNativeObjAddr(), maxTilt));
    }

    public static AffineFeature create(Feature2D backend) {
        return AffineFeature.__fromPtr__(AffineFeature.create_4(backend.getNativeObjAddr()));
    }

    public void setViewParams(MatOfFloat tilts, MatOfFloat rolls) {
        MatOfFloat tilts_mat = tilts;
        MatOfFloat rolls_mat = rolls;
        AffineFeature.setViewParams_0(this.nativeObj, tilts_mat.nativeObj, rolls_mat.nativeObj);
    }

    public void getViewParams(MatOfFloat tilts, MatOfFloat rolls) {
        MatOfFloat tilts_mat = tilts;
        MatOfFloat rolls_mat = rolls;
        AffineFeature.getViewParams_0(this.nativeObj, tilts_mat.nativeObj, rolls_mat.nativeObj);
    }

    @Override
    public String getDefaultName() {
        return AffineFeature.getDefaultName_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        AffineFeature.delete(this.nativeObj);
    }

    private static native long create_0(long var0, int var2, int var3, float var4, float var5);

    private static native long create_1(long var0, int var2, int var3, float var4);

    private static native long create_2(long var0, int var2, int var3);

    private static native long create_3(long var0, int var2);

    private static native long create_4(long var0);

    private static native void setViewParams_0(long var0, long var2, long var4);

    private static native void getViewParams_0(long var0, long var2, long var4);

    private static native String getDefaultName_0(long var0);

    private static native void delete(long var0);
}

