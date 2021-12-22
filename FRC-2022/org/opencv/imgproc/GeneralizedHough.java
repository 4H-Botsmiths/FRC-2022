/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.imgproc;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;
import org.opencv.core.Point;

public class GeneralizedHough
extends Algorithm {
    protected GeneralizedHough(long addr) {
        super(addr);
    }

    public static GeneralizedHough __fromPtr__(long addr) {
        return new GeneralizedHough(addr);
    }

    public void setTemplate(Mat templ, Point templCenter) {
        GeneralizedHough.setTemplate_0(this.nativeObj, templ.nativeObj, templCenter.x, templCenter.y);
    }

    public void setTemplate(Mat templ) {
        GeneralizedHough.setTemplate_1(this.nativeObj, templ.nativeObj);
    }

    public void setTemplate(Mat edges, Mat dx, Mat dy, Point templCenter) {
        GeneralizedHough.setTemplate_2(this.nativeObj, edges.nativeObj, dx.nativeObj, dy.nativeObj, templCenter.x, templCenter.y);
    }

    public void setTemplate(Mat edges, Mat dx, Mat dy) {
        GeneralizedHough.setTemplate_3(this.nativeObj, edges.nativeObj, dx.nativeObj, dy.nativeObj);
    }

    public void detect(Mat image, Mat positions, Mat votes) {
        GeneralizedHough.detect_0(this.nativeObj, image.nativeObj, positions.nativeObj, votes.nativeObj);
    }

    public void detect(Mat image, Mat positions) {
        GeneralizedHough.detect_1(this.nativeObj, image.nativeObj, positions.nativeObj);
    }

    public void detect(Mat edges, Mat dx, Mat dy, Mat positions, Mat votes) {
        GeneralizedHough.detect_2(this.nativeObj, edges.nativeObj, dx.nativeObj, dy.nativeObj, positions.nativeObj, votes.nativeObj);
    }

    public void detect(Mat edges, Mat dx, Mat dy, Mat positions) {
        GeneralizedHough.detect_3(this.nativeObj, edges.nativeObj, dx.nativeObj, dy.nativeObj, positions.nativeObj);
    }

    public void setCannyLowThresh(int cannyLowThresh) {
        GeneralizedHough.setCannyLowThresh_0(this.nativeObj, cannyLowThresh);
    }

    public int getCannyLowThresh() {
        return GeneralizedHough.getCannyLowThresh_0(this.nativeObj);
    }

    public void setCannyHighThresh(int cannyHighThresh) {
        GeneralizedHough.setCannyHighThresh_0(this.nativeObj, cannyHighThresh);
    }

    public int getCannyHighThresh() {
        return GeneralizedHough.getCannyHighThresh_0(this.nativeObj);
    }

    public void setMinDist(double minDist) {
        GeneralizedHough.setMinDist_0(this.nativeObj, minDist);
    }

    public double getMinDist() {
        return GeneralizedHough.getMinDist_0(this.nativeObj);
    }

    public void setDp(double dp) {
        GeneralizedHough.setDp_0(this.nativeObj, dp);
    }

    public double getDp() {
        return GeneralizedHough.getDp_0(this.nativeObj);
    }

    public void setMaxBufferSize(int maxBufferSize) {
        GeneralizedHough.setMaxBufferSize_0(this.nativeObj, maxBufferSize);
    }

    public int getMaxBufferSize() {
        return GeneralizedHough.getMaxBufferSize_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        GeneralizedHough.delete(this.nativeObj);
    }

    private static native void setTemplate_0(long var0, long var2, double var4, double var6);

    private static native void setTemplate_1(long var0, long var2);

    private static native void setTemplate_2(long var0, long var2, long var4, long var6, double var8, double var10);

    private static native void setTemplate_3(long var0, long var2, long var4, long var6);

    private static native void detect_0(long var0, long var2, long var4, long var6);

    private static native void detect_1(long var0, long var2, long var4);

    private static native void detect_2(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native void detect_3(long var0, long var2, long var4, long var6, long var8);

    private static native void setCannyLowThresh_0(long var0, int var2);

    private static native int getCannyLowThresh_0(long var0);

    private static native void setCannyHighThresh_0(long var0, int var2);

    private static native int getCannyHighThresh_0(long var0);

    private static native void setMinDist_0(long var0, double var2);

    private static native double getMinDist_0(long var0);

    private static native void setDp_0(long var0, double var2);

    private static native double getDp_0(long var0);

    private static native void setMaxBufferSize_0(long var0, int var2);

    private static native int getMaxBufferSize_0(long var0);

    private static native void delete(long var0);
}

