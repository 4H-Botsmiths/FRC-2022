/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.calib3d;

public class UsacParams {
    protected final long nativeObj;

    protected UsacParams(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static UsacParams __fromPtr__(long addr) {
        return new UsacParams(addr);
    }

    public UsacParams() {
        this.nativeObj = UsacParams.UsacParams_0();
    }

    public double get_confidence() {
        return UsacParams.get_confidence_0(this.nativeObj);
    }

    public void set_confidence(double confidence) {
        UsacParams.set_confidence_0(this.nativeObj, confidence);
    }

    public boolean get_isParallel() {
        return UsacParams.get_isParallel_0(this.nativeObj);
    }

    public void set_isParallel(boolean isParallel) {
        UsacParams.set_isParallel_0(this.nativeObj, isParallel);
    }

    public int get_loIterations() {
        return UsacParams.get_loIterations_0(this.nativeObj);
    }

    public void set_loIterations(int loIterations) {
        UsacParams.set_loIterations_0(this.nativeObj, loIterations);
    }

    public int get_loMethod() {
        return UsacParams.get_loMethod_0(this.nativeObj);
    }

    public void set_loMethod(int loMethod) {
        UsacParams.set_loMethod_0(this.nativeObj, loMethod);
    }

    public int get_loSampleSize() {
        return UsacParams.get_loSampleSize_0(this.nativeObj);
    }

    public void set_loSampleSize(int loSampleSize) {
        UsacParams.set_loSampleSize_0(this.nativeObj, loSampleSize);
    }

    public int get_maxIterations() {
        return UsacParams.get_maxIterations_0(this.nativeObj);
    }

    public void set_maxIterations(int maxIterations) {
        UsacParams.set_maxIterations_0(this.nativeObj, maxIterations);
    }

    public int get_neighborsSearch() {
        return UsacParams.get_neighborsSearch_0(this.nativeObj);
    }

    public void set_neighborsSearch(int neighborsSearch) {
        UsacParams.set_neighborsSearch_0(this.nativeObj, neighborsSearch);
    }

    public int get_randomGeneratorState() {
        return UsacParams.get_randomGeneratorState_0(this.nativeObj);
    }

    public void set_randomGeneratorState(int randomGeneratorState) {
        UsacParams.set_randomGeneratorState_0(this.nativeObj, randomGeneratorState);
    }

    public int get_sampler() {
        return UsacParams.get_sampler_0(this.nativeObj);
    }

    public void set_sampler(int sampler) {
        UsacParams.set_sampler_0(this.nativeObj, sampler);
    }

    public int get_score() {
        return UsacParams.get_score_0(this.nativeObj);
    }

    public void set_score(int score) {
        UsacParams.set_score_0(this.nativeObj, score);
    }

    public double get_threshold() {
        return UsacParams.get_threshold_0(this.nativeObj);
    }

    public void set_threshold(double threshold) {
        UsacParams.set_threshold_0(this.nativeObj, threshold);
    }

    protected void finalize() throws Throwable {
        UsacParams.delete(this.nativeObj);
    }

    private static native long UsacParams_0();

    private static native double get_confidence_0(long var0);

    private static native void set_confidence_0(long var0, double var2);

    private static native boolean get_isParallel_0(long var0);

    private static native void set_isParallel_0(long var0, boolean var2);

    private static native int get_loIterations_0(long var0);

    private static native void set_loIterations_0(long var0, int var2);

    private static native int get_loMethod_0(long var0);

    private static native void set_loMethod_0(long var0, int var2);

    private static native int get_loSampleSize_0(long var0);

    private static native void set_loSampleSize_0(long var0, int var2);

    private static native int get_maxIterations_0(long var0);

    private static native void set_maxIterations_0(long var0, int var2);

    private static native int get_neighborsSearch_0(long var0);

    private static native void set_neighborsSearch_0(long var0, int var2);

    private static native int get_randomGeneratorState_0(long var0);

    private static native void set_randomGeneratorState_0(long var0, int var2);

    private static native int get_sampler_0(long var0);

    private static native void set_sampler_0(long var0, int var2);

    private static native int get_score_0(long var0);

    private static native void set_score_0(long var0, int var2);

    private static native double get_threshold_0(long var0);

    private static native void set_threshold_0(long var0, double var2);

    private static native void delete(long var0);
}

