/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

import org.opencv.video.DenseOpticalFlow;

public class DISOpticalFlow
extends DenseOpticalFlow {
    public static final int PRESET_ULTRAFAST = 0;
    public static final int PRESET_FAST = 1;
    public static final int PRESET_MEDIUM = 2;

    protected DISOpticalFlow(long addr) {
        super(addr);
    }

    public static DISOpticalFlow __fromPtr__(long addr) {
        return new DISOpticalFlow(addr);
    }

    public int getFinestScale() {
        return DISOpticalFlow.getFinestScale_0(this.nativeObj);
    }

    public void setFinestScale(int val) {
        DISOpticalFlow.setFinestScale_0(this.nativeObj, val);
    }

    public int getPatchSize() {
        return DISOpticalFlow.getPatchSize_0(this.nativeObj);
    }

    public void setPatchSize(int val) {
        DISOpticalFlow.setPatchSize_0(this.nativeObj, val);
    }

    public int getPatchStride() {
        return DISOpticalFlow.getPatchStride_0(this.nativeObj);
    }

    public void setPatchStride(int val) {
        DISOpticalFlow.setPatchStride_0(this.nativeObj, val);
    }

    public int getGradientDescentIterations() {
        return DISOpticalFlow.getGradientDescentIterations_0(this.nativeObj);
    }

    public void setGradientDescentIterations(int val) {
        DISOpticalFlow.setGradientDescentIterations_0(this.nativeObj, val);
    }

    public int getVariationalRefinementIterations() {
        return DISOpticalFlow.getVariationalRefinementIterations_0(this.nativeObj);
    }

    public void setVariationalRefinementIterations(int val) {
        DISOpticalFlow.setVariationalRefinementIterations_0(this.nativeObj, val);
    }

    public float getVariationalRefinementAlpha() {
        return DISOpticalFlow.getVariationalRefinementAlpha_0(this.nativeObj);
    }

    public void setVariationalRefinementAlpha(float val) {
        DISOpticalFlow.setVariationalRefinementAlpha_0(this.nativeObj, val);
    }

    public float getVariationalRefinementDelta() {
        return DISOpticalFlow.getVariationalRefinementDelta_0(this.nativeObj);
    }

    public void setVariationalRefinementDelta(float val) {
        DISOpticalFlow.setVariationalRefinementDelta_0(this.nativeObj, val);
    }

    public float getVariationalRefinementGamma() {
        return DISOpticalFlow.getVariationalRefinementGamma_0(this.nativeObj);
    }

    public void setVariationalRefinementGamma(float val) {
        DISOpticalFlow.setVariationalRefinementGamma_0(this.nativeObj, val);
    }

    public boolean getUseMeanNormalization() {
        return DISOpticalFlow.getUseMeanNormalization_0(this.nativeObj);
    }

    public void setUseMeanNormalization(boolean val) {
        DISOpticalFlow.setUseMeanNormalization_0(this.nativeObj, val);
    }

    public boolean getUseSpatialPropagation() {
        return DISOpticalFlow.getUseSpatialPropagation_0(this.nativeObj);
    }

    public void setUseSpatialPropagation(boolean val) {
        DISOpticalFlow.setUseSpatialPropagation_0(this.nativeObj, val);
    }

    public static DISOpticalFlow create(int preset) {
        return DISOpticalFlow.__fromPtr__(DISOpticalFlow.create_0(preset));
    }

    public static DISOpticalFlow create() {
        return DISOpticalFlow.__fromPtr__(DISOpticalFlow.create_1());
    }

    @Override
    protected void finalize() throws Throwable {
        DISOpticalFlow.delete(this.nativeObj);
    }

    private static native int getFinestScale_0(long var0);

    private static native void setFinestScale_0(long var0, int var2);

    private static native int getPatchSize_0(long var0);

    private static native void setPatchSize_0(long var0, int var2);

    private static native int getPatchStride_0(long var0);

    private static native void setPatchStride_0(long var0, int var2);

    private static native int getGradientDescentIterations_0(long var0);

    private static native void setGradientDescentIterations_0(long var0, int var2);

    private static native int getVariationalRefinementIterations_0(long var0);

    private static native void setVariationalRefinementIterations_0(long var0, int var2);

    private static native float getVariationalRefinementAlpha_0(long var0);

    private static native void setVariationalRefinementAlpha_0(long var0, float var2);

    private static native float getVariationalRefinementDelta_0(long var0);

    private static native void setVariationalRefinementDelta_0(long var0, float var2);

    private static native float getVariationalRefinementGamma_0(long var0);

    private static native void setVariationalRefinementGamma_0(long var0, float var2);

    private static native boolean getUseMeanNormalization_0(long var0);

    private static native void setUseMeanNormalization_0(long var0, boolean var2);

    private static native boolean getUseSpatialPropagation_0(long var0);

    private static native void setUseSpatialPropagation_0(long var0, boolean var2);

    private static native long create_0(int var0);

    private static native long create_1();

    private static native void delete(long var0);
}

