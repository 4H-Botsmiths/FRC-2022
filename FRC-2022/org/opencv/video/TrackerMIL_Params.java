/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

public class TrackerMIL_Params {
    protected final long nativeObj;

    protected TrackerMIL_Params(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static TrackerMIL_Params __fromPtr__(long addr) {
        return new TrackerMIL_Params(addr);
    }

    public TrackerMIL_Params() {
        this.nativeObj = TrackerMIL_Params.TrackerMIL_Params_0();
    }

    public float get_samplerInitInRadius() {
        return TrackerMIL_Params.get_samplerInitInRadius_0(this.nativeObj);
    }

    public void set_samplerInitInRadius(float samplerInitInRadius) {
        TrackerMIL_Params.set_samplerInitInRadius_0(this.nativeObj, samplerInitInRadius);
    }

    public int get_samplerInitMaxNegNum() {
        return TrackerMIL_Params.get_samplerInitMaxNegNum_0(this.nativeObj);
    }

    public void set_samplerInitMaxNegNum(int samplerInitMaxNegNum) {
        TrackerMIL_Params.set_samplerInitMaxNegNum_0(this.nativeObj, samplerInitMaxNegNum);
    }

    public float get_samplerSearchWinSize() {
        return TrackerMIL_Params.get_samplerSearchWinSize_0(this.nativeObj);
    }

    public void set_samplerSearchWinSize(float samplerSearchWinSize) {
        TrackerMIL_Params.set_samplerSearchWinSize_0(this.nativeObj, samplerSearchWinSize);
    }

    public float get_samplerTrackInRadius() {
        return TrackerMIL_Params.get_samplerTrackInRadius_0(this.nativeObj);
    }

    public void set_samplerTrackInRadius(float samplerTrackInRadius) {
        TrackerMIL_Params.set_samplerTrackInRadius_0(this.nativeObj, samplerTrackInRadius);
    }

    public int get_samplerTrackMaxPosNum() {
        return TrackerMIL_Params.get_samplerTrackMaxPosNum_0(this.nativeObj);
    }

    public void set_samplerTrackMaxPosNum(int samplerTrackMaxPosNum) {
        TrackerMIL_Params.set_samplerTrackMaxPosNum_0(this.nativeObj, samplerTrackMaxPosNum);
    }

    public int get_samplerTrackMaxNegNum() {
        return TrackerMIL_Params.get_samplerTrackMaxNegNum_0(this.nativeObj);
    }

    public void set_samplerTrackMaxNegNum(int samplerTrackMaxNegNum) {
        TrackerMIL_Params.set_samplerTrackMaxNegNum_0(this.nativeObj, samplerTrackMaxNegNum);
    }

    public int get_featureSetNumFeatures() {
        return TrackerMIL_Params.get_featureSetNumFeatures_0(this.nativeObj);
    }

    public void set_featureSetNumFeatures(int featureSetNumFeatures) {
        TrackerMIL_Params.set_featureSetNumFeatures_0(this.nativeObj, featureSetNumFeatures);
    }

    protected void finalize() throws Throwable {
        TrackerMIL_Params.delete(this.nativeObj);
    }

    private static native long TrackerMIL_Params_0();

    private static native float get_samplerInitInRadius_0(long var0);

    private static native void set_samplerInitInRadius_0(long var0, float var2);

    private static native int get_samplerInitMaxNegNum_0(long var0);

    private static native void set_samplerInitMaxNegNum_0(long var0, int var2);

    private static native float get_samplerSearchWinSize_0(long var0);

    private static native void set_samplerSearchWinSize_0(long var0, float var2);

    private static native float get_samplerTrackInRadius_0(long var0);

    private static native void set_samplerTrackInRadius_0(long var0, float var2);

    private static native int get_samplerTrackMaxPosNum_0(long var0);

    private static native void set_samplerTrackMaxPosNum_0(long var0, int var2);

    private static native int get_samplerTrackMaxNegNum_0(long var0);

    private static native void set_samplerTrackMaxNegNum_0(long var0, int var2);

    private static native int get_featureSetNumFeatures_0(long var0);

    private static native void set_featureSetNumFeatures_0(long var0, int var2);

    private static native void delete(long var0);
}

