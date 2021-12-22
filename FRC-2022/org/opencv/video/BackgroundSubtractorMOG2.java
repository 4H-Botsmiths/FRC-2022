/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

import org.opencv.core.Mat;
import org.opencv.video.BackgroundSubtractor;

public class BackgroundSubtractorMOG2
extends BackgroundSubtractor {
    protected BackgroundSubtractorMOG2(long addr) {
        super(addr);
    }

    public static BackgroundSubtractorMOG2 __fromPtr__(long addr) {
        return new BackgroundSubtractorMOG2(addr);
    }

    public int getHistory() {
        return BackgroundSubtractorMOG2.getHistory_0(this.nativeObj);
    }

    public void setHistory(int history) {
        BackgroundSubtractorMOG2.setHistory_0(this.nativeObj, history);
    }

    public int getNMixtures() {
        return BackgroundSubtractorMOG2.getNMixtures_0(this.nativeObj);
    }

    public void setNMixtures(int nmixtures) {
        BackgroundSubtractorMOG2.setNMixtures_0(this.nativeObj, nmixtures);
    }

    public double getBackgroundRatio() {
        return BackgroundSubtractorMOG2.getBackgroundRatio_0(this.nativeObj);
    }

    public void setBackgroundRatio(double ratio) {
        BackgroundSubtractorMOG2.setBackgroundRatio_0(this.nativeObj, ratio);
    }

    public double getVarThreshold() {
        return BackgroundSubtractorMOG2.getVarThreshold_0(this.nativeObj);
    }

    public void setVarThreshold(double varThreshold) {
        BackgroundSubtractorMOG2.setVarThreshold_0(this.nativeObj, varThreshold);
    }

    public double getVarThresholdGen() {
        return BackgroundSubtractorMOG2.getVarThresholdGen_0(this.nativeObj);
    }

    public void setVarThresholdGen(double varThresholdGen) {
        BackgroundSubtractorMOG2.setVarThresholdGen_0(this.nativeObj, varThresholdGen);
    }

    public double getVarInit() {
        return BackgroundSubtractorMOG2.getVarInit_0(this.nativeObj);
    }

    public void setVarInit(double varInit) {
        BackgroundSubtractorMOG2.setVarInit_0(this.nativeObj, varInit);
    }

    public double getVarMin() {
        return BackgroundSubtractorMOG2.getVarMin_0(this.nativeObj);
    }

    public void setVarMin(double varMin) {
        BackgroundSubtractorMOG2.setVarMin_0(this.nativeObj, varMin);
    }

    public double getVarMax() {
        return BackgroundSubtractorMOG2.getVarMax_0(this.nativeObj);
    }

    public void setVarMax(double varMax) {
        BackgroundSubtractorMOG2.setVarMax_0(this.nativeObj, varMax);
    }

    public double getComplexityReductionThreshold() {
        return BackgroundSubtractorMOG2.getComplexityReductionThreshold_0(this.nativeObj);
    }

    public void setComplexityReductionThreshold(double ct) {
        BackgroundSubtractorMOG2.setComplexityReductionThreshold_0(this.nativeObj, ct);
    }

    public boolean getDetectShadows() {
        return BackgroundSubtractorMOG2.getDetectShadows_0(this.nativeObj);
    }

    public void setDetectShadows(boolean detectShadows) {
        BackgroundSubtractorMOG2.setDetectShadows_0(this.nativeObj, detectShadows);
    }

    public int getShadowValue() {
        return BackgroundSubtractorMOG2.getShadowValue_0(this.nativeObj);
    }

    public void setShadowValue(int value) {
        BackgroundSubtractorMOG2.setShadowValue_0(this.nativeObj, value);
    }

    public double getShadowThreshold() {
        return BackgroundSubtractorMOG2.getShadowThreshold_0(this.nativeObj);
    }

    public void setShadowThreshold(double threshold) {
        BackgroundSubtractorMOG2.setShadowThreshold_0(this.nativeObj, threshold);
    }

    @Override
    public void apply(Mat image, Mat fgmask, double learningRate) {
        BackgroundSubtractorMOG2.apply_0(this.nativeObj, image.nativeObj, fgmask.nativeObj, learningRate);
    }

    @Override
    public void apply(Mat image, Mat fgmask) {
        BackgroundSubtractorMOG2.apply_1(this.nativeObj, image.nativeObj, fgmask.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        BackgroundSubtractorMOG2.delete(this.nativeObj);
    }

    private static native int getHistory_0(long var0);

    private static native void setHistory_0(long var0, int var2);

    private static native int getNMixtures_0(long var0);

    private static native void setNMixtures_0(long var0, int var2);

    private static native double getBackgroundRatio_0(long var0);

    private static native void setBackgroundRatio_0(long var0, double var2);

    private static native double getVarThreshold_0(long var0);

    private static native void setVarThreshold_0(long var0, double var2);

    private static native double getVarThresholdGen_0(long var0);

    private static native void setVarThresholdGen_0(long var0, double var2);

    private static native double getVarInit_0(long var0);

    private static native void setVarInit_0(long var0, double var2);

    private static native double getVarMin_0(long var0);

    private static native void setVarMin_0(long var0, double var2);

    private static native double getVarMax_0(long var0);

    private static native void setVarMax_0(long var0, double var2);

    private static native double getComplexityReductionThreshold_0(long var0);

    private static native void setComplexityReductionThreshold_0(long var0, double var2);

    private static native boolean getDetectShadows_0(long var0);

    private static native void setDetectShadows_0(long var0, boolean var2);

    private static native int getShadowValue_0(long var0);

    private static native void setShadowValue_0(long var0, int var2);

    private static native double getShadowThreshold_0(long var0);

    private static native void setShadowThreshold_0(long var0, double var2);

    private static native void apply_0(long var0, long var2, long var4, double var6);

    private static native void apply_1(long var0, long var2, long var4);

    private static native void delete(long var0);
}

