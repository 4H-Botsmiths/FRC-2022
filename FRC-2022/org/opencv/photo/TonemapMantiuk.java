/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.photo;

import org.opencv.photo.Tonemap;

public class TonemapMantiuk
extends Tonemap {
    protected TonemapMantiuk(long addr) {
        super(addr);
    }

    public static TonemapMantiuk __fromPtr__(long addr) {
        return new TonemapMantiuk(addr);
    }

    public float getScale() {
        return TonemapMantiuk.getScale_0(this.nativeObj);
    }

    public void setScale(float scale) {
        TonemapMantiuk.setScale_0(this.nativeObj, scale);
    }

    public float getSaturation() {
        return TonemapMantiuk.getSaturation_0(this.nativeObj);
    }

    public void setSaturation(float saturation) {
        TonemapMantiuk.setSaturation_0(this.nativeObj, saturation);
    }

    @Override
    protected void finalize() throws Throwable {
        TonemapMantiuk.delete(this.nativeObj);
    }

    private static native float getScale_0(long var0);

    private static native void setScale_0(long var0, float var2);

    private static native float getSaturation_0(long var0);

    private static native void setSaturation_0(long var0, float var2);

    private static native void delete(long var0);
}

