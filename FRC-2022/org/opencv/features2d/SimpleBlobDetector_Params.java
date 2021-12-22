/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

public class SimpleBlobDetector_Params {
    protected final long nativeObj;

    protected SimpleBlobDetector_Params(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static SimpleBlobDetector_Params __fromPtr__(long addr) {
        return new SimpleBlobDetector_Params(addr);
    }

    public SimpleBlobDetector_Params() {
        this.nativeObj = SimpleBlobDetector_Params.SimpleBlobDetector_Params_0();
    }

    public float get_thresholdStep() {
        return SimpleBlobDetector_Params.get_thresholdStep_0(this.nativeObj);
    }

    public void set_thresholdStep(float thresholdStep) {
        SimpleBlobDetector_Params.set_thresholdStep_0(this.nativeObj, thresholdStep);
    }

    public float get_minThreshold() {
        return SimpleBlobDetector_Params.get_minThreshold_0(this.nativeObj);
    }

    public void set_minThreshold(float minThreshold) {
        SimpleBlobDetector_Params.set_minThreshold_0(this.nativeObj, minThreshold);
    }

    public float get_maxThreshold() {
        return SimpleBlobDetector_Params.get_maxThreshold_0(this.nativeObj);
    }

    public void set_maxThreshold(float maxThreshold) {
        SimpleBlobDetector_Params.set_maxThreshold_0(this.nativeObj, maxThreshold);
    }

    public long get_minRepeatability() {
        return SimpleBlobDetector_Params.get_minRepeatability_0(this.nativeObj);
    }

    public void set_minRepeatability(long minRepeatability) {
        SimpleBlobDetector_Params.set_minRepeatability_0(this.nativeObj, minRepeatability);
    }

    public float get_minDistBetweenBlobs() {
        return SimpleBlobDetector_Params.get_minDistBetweenBlobs_0(this.nativeObj);
    }

    public void set_minDistBetweenBlobs(float minDistBetweenBlobs) {
        SimpleBlobDetector_Params.set_minDistBetweenBlobs_0(this.nativeObj, minDistBetweenBlobs);
    }

    public boolean get_filterByColor() {
        return SimpleBlobDetector_Params.get_filterByColor_0(this.nativeObj);
    }

    public void set_filterByColor(boolean filterByColor) {
        SimpleBlobDetector_Params.set_filterByColor_0(this.nativeObj, filterByColor);
    }

    public boolean get_filterByArea() {
        return SimpleBlobDetector_Params.get_filterByArea_0(this.nativeObj);
    }

    public void set_filterByArea(boolean filterByArea) {
        SimpleBlobDetector_Params.set_filterByArea_0(this.nativeObj, filterByArea);
    }

    public float get_minArea() {
        return SimpleBlobDetector_Params.get_minArea_0(this.nativeObj);
    }

    public void set_minArea(float minArea) {
        SimpleBlobDetector_Params.set_minArea_0(this.nativeObj, minArea);
    }

    public float get_maxArea() {
        return SimpleBlobDetector_Params.get_maxArea_0(this.nativeObj);
    }

    public void set_maxArea(float maxArea) {
        SimpleBlobDetector_Params.set_maxArea_0(this.nativeObj, maxArea);
    }

    public boolean get_filterByCircularity() {
        return SimpleBlobDetector_Params.get_filterByCircularity_0(this.nativeObj);
    }

    public void set_filterByCircularity(boolean filterByCircularity) {
        SimpleBlobDetector_Params.set_filterByCircularity_0(this.nativeObj, filterByCircularity);
    }

    public float get_minCircularity() {
        return SimpleBlobDetector_Params.get_minCircularity_0(this.nativeObj);
    }

    public void set_minCircularity(float minCircularity) {
        SimpleBlobDetector_Params.set_minCircularity_0(this.nativeObj, minCircularity);
    }

    public float get_maxCircularity() {
        return SimpleBlobDetector_Params.get_maxCircularity_0(this.nativeObj);
    }

    public void set_maxCircularity(float maxCircularity) {
        SimpleBlobDetector_Params.set_maxCircularity_0(this.nativeObj, maxCircularity);
    }

    public boolean get_filterByInertia() {
        return SimpleBlobDetector_Params.get_filterByInertia_0(this.nativeObj);
    }

    public void set_filterByInertia(boolean filterByInertia) {
        SimpleBlobDetector_Params.set_filterByInertia_0(this.nativeObj, filterByInertia);
    }

    public float get_minInertiaRatio() {
        return SimpleBlobDetector_Params.get_minInertiaRatio_0(this.nativeObj);
    }

    public void set_minInertiaRatio(float minInertiaRatio) {
        SimpleBlobDetector_Params.set_minInertiaRatio_0(this.nativeObj, minInertiaRatio);
    }

    public float get_maxInertiaRatio() {
        return SimpleBlobDetector_Params.get_maxInertiaRatio_0(this.nativeObj);
    }

    public void set_maxInertiaRatio(float maxInertiaRatio) {
        SimpleBlobDetector_Params.set_maxInertiaRatio_0(this.nativeObj, maxInertiaRatio);
    }

    public boolean get_filterByConvexity() {
        return SimpleBlobDetector_Params.get_filterByConvexity_0(this.nativeObj);
    }

    public void set_filterByConvexity(boolean filterByConvexity) {
        SimpleBlobDetector_Params.set_filterByConvexity_0(this.nativeObj, filterByConvexity);
    }

    public float get_minConvexity() {
        return SimpleBlobDetector_Params.get_minConvexity_0(this.nativeObj);
    }

    public void set_minConvexity(float minConvexity) {
        SimpleBlobDetector_Params.set_minConvexity_0(this.nativeObj, minConvexity);
    }

    public float get_maxConvexity() {
        return SimpleBlobDetector_Params.get_maxConvexity_0(this.nativeObj);
    }

    public void set_maxConvexity(float maxConvexity) {
        SimpleBlobDetector_Params.set_maxConvexity_0(this.nativeObj, maxConvexity);
    }

    protected void finalize() throws Throwable {
        SimpleBlobDetector_Params.delete(this.nativeObj);
    }

    private static native long SimpleBlobDetector_Params_0();

    private static native float get_thresholdStep_0(long var0);

    private static native void set_thresholdStep_0(long var0, float var2);

    private static native float get_minThreshold_0(long var0);

    private static native void set_minThreshold_0(long var0, float var2);

    private static native float get_maxThreshold_0(long var0);

    private static native void set_maxThreshold_0(long var0, float var2);

    private static native long get_minRepeatability_0(long var0);

    private static native void set_minRepeatability_0(long var0, long var2);

    private static native float get_minDistBetweenBlobs_0(long var0);

    private static native void set_minDistBetweenBlobs_0(long var0, float var2);

    private static native boolean get_filterByColor_0(long var0);

    private static native void set_filterByColor_0(long var0, boolean var2);

    private static native boolean get_filterByArea_0(long var0);

    private static native void set_filterByArea_0(long var0, boolean var2);

    private static native float get_minArea_0(long var0);

    private static native void set_minArea_0(long var0, float var2);

    private static native float get_maxArea_0(long var0);

    private static native void set_maxArea_0(long var0, float var2);

    private static native boolean get_filterByCircularity_0(long var0);

    private static native void set_filterByCircularity_0(long var0, boolean var2);

    private static native float get_minCircularity_0(long var0);

    private static native void set_minCircularity_0(long var0, float var2);

    private static native float get_maxCircularity_0(long var0);

    private static native void set_maxCircularity_0(long var0, float var2);

    private static native boolean get_filterByInertia_0(long var0);

    private static native void set_filterByInertia_0(long var0, boolean var2);

    private static native float get_minInertiaRatio_0(long var0);

    private static native void set_minInertiaRatio_0(long var0, float var2);

    private static native float get_maxInertiaRatio_0(long var0);

    private static native void set_maxInertiaRatio_0(long var0, float var2);

    private static native boolean get_filterByConvexity_0(long var0);

    private static native void set_filterByConvexity_0(long var0, boolean var2);

    private static native float get_minConvexity_0(long var0);

    private static native void set_minConvexity_0(long var0, float var2);

    private static native float get_maxConvexity_0(long var0);

    private static native void set_maxConvexity_0(long var0, float var2);

    private static native void delete(long var0);
}

