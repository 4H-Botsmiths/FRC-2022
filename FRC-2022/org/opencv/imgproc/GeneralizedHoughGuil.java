/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.imgproc;

import org.opencv.imgproc.GeneralizedHough;

public class GeneralizedHoughGuil
extends GeneralizedHough {
    protected GeneralizedHoughGuil(long addr) {
        super(addr);
    }

    public static GeneralizedHoughGuil __fromPtr__(long addr) {
        return new GeneralizedHoughGuil(addr);
    }

    public void setXi(double xi) {
        GeneralizedHoughGuil.setXi_0(this.nativeObj, xi);
    }

    public double getXi() {
        return GeneralizedHoughGuil.getXi_0(this.nativeObj);
    }

    public void setLevels(int levels) {
        GeneralizedHoughGuil.setLevels_0(this.nativeObj, levels);
    }

    public int getLevels() {
        return GeneralizedHoughGuil.getLevels_0(this.nativeObj);
    }

    public void setAngleEpsilon(double angleEpsilon) {
        GeneralizedHoughGuil.setAngleEpsilon_0(this.nativeObj, angleEpsilon);
    }

    public double getAngleEpsilon() {
        return GeneralizedHoughGuil.getAngleEpsilon_0(this.nativeObj);
    }

    public void setMinAngle(double minAngle) {
        GeneralizedHoughGuil.setMinAngle_0(this.nativeObj, minAngle);
    }

    public double getMinAngle() {
        return GeneralizedHoughGuil.getMinAngle_0(this.nativeObj);
    }

    public void setMaxAngle(double maxAngle) {
        GeneralizedHoughGuil.setMaxAngle_0(this.nativeObj, maxAngle);
    }

    public double getMaxAngle() {
        return GeneralizedHoughGuil.getMaxAngle_0(this.nativeObj);
    }

    public void setAngleStep(double angleStep) {
        GeneralizedHoughGuil.setAngleStep_0(this.nativeObj, angleStep);
    }

    public double getAngleStep() {
        return GeneralizedHoughGuil.getAngleStep_0(this.nativeObj);
    }

    public void setAngleThresh(int angleThresh) {
        GeneralizedHoughGuil.setAngleThresh_0(this.nativeObj, angleThresh);
    }

    public int getAngleThresh() {
        return GeneralizedHoughGuil.getAngleThresh_0(this.nativeObj);
    }

    public void setMinScale(double minScale) {
        GeneralizedHoughGuil.setMinScale_0(this.nativeObj, minScale);
    }

    public double getMinScale() {
        return GeneralizedHoughGuil.getMinScale_0(this.nativeObj);
    }

    public void setMaxScale(double maxScale) {
        GeneralizedHoughGuil.setMaxScale_0(this.nativeObj, maxScale);
    }

    public double getMaxScale() {
        return GeneralizedHoughGuil.getMaxScale_0(this.nativeObj);
    }

    public void setScaleStep(double scaleStep) {
        GeneralizedHoughGuil.setScaleStep_0(this.nativeObj, scaleStep);
    }

    public double getScaleStep() {
        return GeneralizedHoughGuil.getScaleStep_0(this.nativeObj);
    }

    public void setScaleThresh(int scaleThresh) {
        GeneralizedHoughGuil.setScaleThresh_0(this.nativeObj, scaleThresh);
    }

    public int getScaleThresh() {
        return GeneralizedHoughGuil.getScaleThresh_0(this.nativeObj);
    }

    public void setPosThresh(int posThresh) {
        GeneralizedHoughGuil.setPosThresh_0(this.nativeObj, posThresh);
    }

    public int getPosThresh() {
        return GeneralizedHoughGuil.getPosThresh_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        GeneralizedHoughGuil.delete(this.nativeObj);
    }

    private static native void setXi_0(long var0, double var2);

    private static native double getXi_0(long var0);

    private static native void setLevels_0(long var0, int var2);

    private static native int getLevels_0(long var0);

    private static native void setAngleEpsilon_0(long var0, double var2);

    private static native double getAngleEpsilon_0(long var0);

    private static native void setMinAngle_0(long var0, double var2);

    private static native double getMinAngle_0(long var0);

    private static native void setMaxAngle_0(long var0, double var2);

    private static native double getMaxAngle_0(long var0);

    private static native void setAngleStep_0(long var0, double var2);

    private static native double getAngleStep_0(long var0);

    private static native void setAngleThresh_0(long var0, int var2);

    private static native int getAngleThresh_0(long var0);

    private static native void setMinScale_0(long var0, double var2);

    private static native double getMinScale_0(long var0);

    private static native void setMaxScale_0(long var0, double var2);

    private static native double getMaxScale_0(long var0);

    private static native void setScaleStep_0(long var0, double var2);

    private static native double getScaleStep_0(long var0);

    private static native void setScaleThresh_0(long var0, int var2);

    private static native int getScaleThresh_0(long var0);

    private static native void setPosThresh_0(long var0, int var2);

    private static native int getPosThresh_0(long var0);

    private static native void delete(long var0);
}

