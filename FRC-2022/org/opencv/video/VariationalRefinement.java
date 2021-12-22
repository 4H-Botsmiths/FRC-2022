/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

import org.opencv.core.Mat;
import org.opencv.video.DenseOpticalFlow;

public class VariationalRefinement
extends DenseOpticalFlow {
    protected VariationalRefinement(long addr) {
        super(addr);
    }

    public static VariationalRefinement __fromPtr__(long addr) {
        return new VariationalRefinement(addr);
    }

    public void calcUV(Mat I0, Mat I1, Mat flow_u, Mat flow_v) {
        VariationalRefinement.calcUV_0(this.nativeObj, I0.nativeObj, I1.nativeObj, flow_u.nativeObj, flow_v.nativeObj);
    }

    public int getFixedPointIterations() {
        return VariationalRefinement.getFixedPointIterations_0(this.nativeObj);
    }

    public void setFixedPointIterations(int val) {
        VariationalRefinement.setFixedPointIterations_0(this.nativeObj, val);
    }

    public int getSorIterations() {
        return VariationalRefinement.getSorIterations_0(this.nativeObj);
    }

    public void setSorIterations(int val) {
        VariationalRefinement.setSorIterations_0(this.nativeObj, val);
    }

    public float getOmega() {
        return VariationalRefinement.getOmega_0(this.nativeObj);
    }

    public void setOmega(float val) {
        VariationalRefinement.setOmega_0(this.nativeObj, val);
    }

    public float getAlpha() {
        return VariationalRefinement.getAlpha_0(this.nativeObj);
    }

    public void setAlpha(float val) {
        VariationalRefinement.setAlpha_0(this.nativeObj, val);
    }

    public float getDelta() {
        return VariationalRefinement.getDelta_0(this.nativeObj);
    }

    public void setDelta(float val) {
        VariationalRefinement.setDelta_0(this.nativeObj, val);
    }

    public float getGamma() {
        return VariationalRefinement.getGamma_0(this.nativeObj);
    }

    public void setGamma(float val) {
        VariationalRefinement.setGamma_0(this.nativeObj, val);
    }

    public static VariationalRefinement create() {
        return VariationalRefinement.__fromPtr__(VariationalRefinement.create_0());
    }

    @Override
    protected void finalize() throws Throwable {
        VariationalRefinement.delete(this.nativeObj);
    }

    private static native void calcUV_0(long var0, long var2, long var4, long var6, long var8);

    private static native int getFixedPointIterations_0(long var0);

    private static native void setFixedPointIterations_0(long var0, int var2);

    private static native int getSorIterations_0(long var0);

    private static native void setSorIterations_0(long var0, int var2);

    private static native float getOmega_0(long var0);

    private static native void setOmega_0(long var0, float var2);

    private static native float getAlpha_0(long var0);

    private static native void setAlpha_0(long var0, float var2);

    private static native float getDelta_0(long var0);

    private static native void setDelta_0(long var0, float var2);

    private static native float getGamma_0(long var0);

    private static native void setGamma_0(long var0, float var2);

    private static native long create_0();

    private static native void delete(long var0);
}

