/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

import org.opencv.video.BackgroundSubtractor;

public class BackgroundSubtractorKNN
extends BackgroundSubtractor {
    protected BackgroundSubtractorKNN(long addr) {
        super(addr);
    }

    public static BackgroundSubtractorKNN __fromPtr__(long addr) {
        return new BackgroundSubtractorKNN(addr);
    }

    public int getHistory() {
        return BackgroundSubtractorKNN.getHistory_0(this.nativeObj);
    }

    public void setHistory(int history) {
        BackgroundSubtractorKNN.setHistory_0(this.nativeObj, history);
    }

    public int getNSamples() {
        return BackgroundSubtractorKNN.getNSamples_0(this.nativeObj);
    }

    public void setNSamples(int _nN) {
        BackgroundSubtractorKNN.setNSamples_0(this.nativeObj, _nN);
    }

    public double getDist2Threshold() {
        return BackgroundSubtractorKNN.getDist2Threshold_0(this.nativeObj);
    }

    public void setDist2Threshold(double _dist2Threshold) {
        BackgroundSubtractorKNN.setDist2Threshold_0(this.nativeObj, _dist2Threshold);
    }

    public int getkNNSamples() {
        return BackgroundSubtractorKNN.getkNNSamples_0(this.nativeObj);
    }

    public void setkNNSamples(int _nkNN) {
        BackgroundSubtractorKNN.setkNNSamples_0(this.nativeObj, _nkNN);
    }

    public boolean getDetectShadows() {
        return BackgroundSubtractorKNN.getDetectShadows_0(this.nativeObj);
    }

    public void setDetectShadows(boolean detectShadows) {
        BackgroundSubtractorKNN.setDetectShadows_0(this.nativeObj, detectShadows);
    }

    public int getShadowValue() {
        return BackgroundSubtractorKNN.getShadowValue_0(this.nativeObj);
    }

    public void setShadowValue(int value) {
        BackgroundSubtractorKNN.setShadowValue_0(this.nativeObj, value);
    }

    public double getShadowThreshold() {
        return BackgroundSubtractorKNN.getShadowThreshold_0(this.nativeObj);
    }

    public void setShadowThreshold(double threshold) {
        BackgroundSubtractorKNN.setShadowThreshold_0(this.nativeObj, threshold);
    }

    @Override
    protected void finalize() throws Throwable {
        BackgroundSubtractorKNN.delete(this.nativeObj);
    }

    private static native int getHistory_0(long var0);

    private static native void setHistory_0(long var0, int var2);

    private static native int getNSamples_0(long var0);

    private static native void setNSamples_0(long var0, int var2);

    private static native double getDist2Threshold_0(long var0);

    private static native void setDist2Threshold_0(long var0, double var2);

    private static native int getkNNSamples_0(long var0);

    private static native void setkNNSamples_0(long var0, int var2);

    private static native boolean getDetectShadows_0(long var0);

    private static native void setDetectShadows_0(long var0, boolean var2);

    private static native int getShadowValue_0(long var0);

    private static native void setShadowValue_0(long var0, int var2);

    private static native double getShadowThreshold_0(long var0);

    private static native void setShadowThreshold_0(long var0, double var2);

    private static native void delete(long var0);
}

