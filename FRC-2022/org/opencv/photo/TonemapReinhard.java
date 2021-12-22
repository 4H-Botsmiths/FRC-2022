/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.photo;

import org.opencv.photo.Tonemap;

public class TonemapReinhard
extends Tonemap {
    protected TonemapReinhard(long addr) {
        super(addr);
    }

    public static TonemapReinhard __fromPtr__(long addr) {
        return new TonemapReinhard(addr);
    }

    public float getIntensity() {
        return TonemapReinhard.getIntensity_0(this.nativeObj);
    }

    public void setIntensity(float intensity) {
        TonemapReinhard.setIntensity_0(this.nativeObj, intensity);
    }

    public float getLightAdaptation() {
        return TonemapReinhard.getLightAdaptation_0(this.nativeObj);
    }

    public void setLightAdaptation(float light_adapt) {
        TonemapReinhard.setLightAdaptation_0(this.nativeObj, light_adapt);
    }

    public float getColorAdaptation() {
        return TonemapReinhard.getColorAdaptation_0(this.nativeObj);
    }

    public void setColorAdaptation(float color_adapt) {
        TonemapReinhard.setColorAdaptation_0(this.nativeObj, color_adapt);
    }

    @Override
    protected void finalize() throws Throwable {
        TonemapReinhard.delete(this.nativeObj);
    }

    private static native float getIntensity_0(long var0);

    private static native void setIntensity_0(long var0, float var2);

    private static native float getLightAdaptation_0(long var0);

    private static native void setLightAdaptation_0(long var0, float var2);

    private static native float getColorAdaptation_0(long var0);

    private static native void setColorAdaptation_0(long var0, float var2);

    private static native void delete(long var0);
}

