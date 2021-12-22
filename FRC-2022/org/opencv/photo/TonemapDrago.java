/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.photo;

import org.opencv.photo.Tonemap;

public class TonemapDrago
extends Tonemap {
    protected TonemapDrago(long addr) {
        super(addr);
    }

    public static TonemapDrago __fromPtr__(long addr) {
        return new TonemapDrago(addr);
    }

    public float getSaturation() {
        return TonemapDrago.getSaturation_0(this.nativeObj);
    }

    public void setSaturation(float saturation) {
        TonemapDrago.setSaturation_0(this.nativeObj, saturation);
    }

    public float getBias() {
        return TonemapDrago.getBias_0(this.nativeObj);
    }

    public void setBias(float bias) {
        TonemapDrago.setBias_0(this.nativeObj, bias);
    }

    @Override
    protected void finalize() throws Throwable {
        TonemapDrago.delete(this.nativeObj);
    }

    private static native float getSaturation_0(long var0);

    private static native void setSaturation_0(long var0, float var2);

    private static native float getBias_0(long var0);

    private static native void setBias_0(long var0, float var2);

    private static native void delete(long var0);
}

