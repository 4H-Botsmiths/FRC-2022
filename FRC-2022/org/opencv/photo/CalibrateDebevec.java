/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.photo;

import org.opencv.photo.CalibrateCRF;

public class CalibrateDebevec
extends CalibrateCRF {
    protected CalibrateDebevec(long addr) {
        super(addr);
    }

    public static CalibrateDebevec __fromPtr__(long addr) {
        return new CalibrateDebevec(addr);
    }

    public float getLambda() {
        return CalibrateDebevec.getLambda_0(this.nativeObj);
    }

    public void setLambda(float lambda) {
        CalibrateDebevec.setLambda_0(this.nativeObj, lambda);
    }

    public int getSamples() {
        return CalibrateDebevec.getSamples_0(this.nativeObj);
    }

    public void setSamples(int samples) {
        CalibrateDebevec.setSamples_0(this.nativeObj, samples);
    }

    public boolean getRandom() {
        return CalibrateDebevec.getRandom_0(this.nativeObj);
    }

    public void setRandom(boolean random) {
        CalibrateDebevec.setRandom_0(this.nativeObj, random);
    }

    @Override
    protected void finalize() throws Throwable {
        CalibrateDebevec.delete(this.nativeObj);
    }

    private static native float getLambda_0(long var0);

    private static native void setLambda_0(long var0, float var2);

    private static native int getSamples_0(long var0);

    private static native void setSamples_0(long var0, int var2);

    private static native boolean getRandom_0(long var0);

    private static native void setRandom_0(long var0, boolean var2);

    private static native void delete(long var0);
}

