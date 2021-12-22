/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

public class TrackerGOTURN_Params {
    protected final long nativeObj;

    protected TrackerGOTURN_Params(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static TrackerGOTURN_Params __fromPtr__(long addr) {
        return new TrackerGOTURN_Params(addr);
    }

    public TrackerGOTURN_Params() {
        this.nativeObj = TrackerGOTURN_Params.TrackerGOTURN_Params_0();
    }

    public String get_modelTxt() {
        return TrackerGOTURN_Params.get_modelTxt_0(this.nativeObj);
    }

    public void set_modelTxt(String modelTxt) {
        TrackerGOTURN_Params.set_modelTxt_0(this.nativeObj, modelTxt);
    }

    public String get_modelBin() {
        return TrackerGOTURN_Params.get_modelBin_0(this.nativeObj);
    }

    public void set_modelBin(String modelBin) {
        TrackerGOTURN_Params.set_modelBin_0(this.nativeObj, modelBin);
    }

    protected void finalize() throws Throwable {
        TrackerGOTURN_Params.delete(this.nativeObj);
    }

    private static native long TrackerGOTURN_Params_0();

    private static native String get_modelTxt_0(long var0);

    private static native void set_modelTxt_0(long var0, String var2);

    private static native String get_modelBin_0(long var0);

    private static native void set_modelBin_0(long var0, String var2);

    private static native void delete(long var0);
}

