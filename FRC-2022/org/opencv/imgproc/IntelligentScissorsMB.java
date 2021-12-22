/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.imgproc;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public class IntelligentScissorsMB {
    protected final long nativeObj;

    protected IntelligentScissorsMB(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static IntelligentScissorsMB __fromPtr__(long addr) {
        return new IntelligentScissorsMB(addr);
    }

    public IntelligentScissorsMB() {
        this.nativeObj = IntelligentScissorsMB.IntelligentScissorsMB_0();
    }

    public IntelligentScissorsMB setWeights(float weight_non_edge, float weight_gradient_direction, float weight_gradient_magnitude) {
        return new IntelligentScissorsMB(IntelligentScissorsMB.setWeights_0(this.nativeObj, weight_non_edge, weight_gradient_direction, weight_gradient_magnitude));
    }

    public IntelligentScissorsMB setGradientMagnitudeMaxLimit(float gradient_magnitude_threshold_max) {
        return new IntelligentScissorsMB(IntelligentScissorsMB.setGradientMagnitudeMaxLimit_0(this.nativeObj, gradient_magnitude_threshold_max));
    }

    public IntelligentScissorsMB setGradientMagnitudeMaxLimit() {
        return new IntelligentScissorsMB(IntelligentScissorsMB.setGradientMagnitudeMaxLimit_1(this.nativeObj));
    }

    public IntelligentScissorsMB setEdgeFeatureZeroCrossingParameters(float gradient_magnitude_min_value) {
        return new IntelligentScissorsMB(IntelligentScissorsMB.setEdgeFeatureZeroCrossingParameters_0(this.nativeObj, gradient_magnitude_min_value));
    }

    public IntelligentScissorsMB setEdgeFeatureZeroCrossingParameters() {
        return new IntelligentScissorsMB(IntelligentScissorsMB.setEdgeFeatureZeroCrossingParameters_1(this.nativeObj));
    }

    public IntelligentScissorsMB setEdgeFeatureCannyParameters(double threshold1, double threshold2, int apertureSize, boolean L2gradient) {
        return new IntelligentScissorsMB(IntelligentScissorsMB.setEdgeFeatureCannyParameters_0(this.nativeObj, threshold1, threshold2, apertureSize, L2gradient));
    }

    public IntelligentScissorsMB setEdgeFeatureCannyParameters(double threshold1, double threshold2, int apertureSize) {
        return new IntelligentScissorsMB(IntelligentScissorsMB.setEdgeFeatureCannyParameters_1(this.nativeObj, threshold1, threshold2, apertureSize));
    }

    public IntelligentScissorsMB setEdgeFeatureCannyParameters(double threshold1, double threshold2) {
        return new IntelligentScissorsMB(IntelligentScissorsMB.setEdgeFeatureCannyParameters_2(this.nativeObj, threshold1, threshold2));
    }

    public IntelligentScissorsMB applyImage(Mat image) {
        return new IntelligentScissorsMB(IntelligentScissorsMB.applyImage_0(this.nativeObj, image.nativeObj));
    }

    public IntelligentScissorsMB applyImageFeatures(Mat non_edge, Mat gradient_direction, Mat gradient_magnitude, Mat image) {
        return new IntelligentScissorsMB(IntelligentScissorsMB.applyImageFeatures_0(this.nativeObj, non_edge.nativeObj, gradient_direction.nativeObj, gradient_magnitude.nativeObj, image.nativeObj));
    }

    public IntelligentScissorsMB applyImageFeatures(Mat non_edge, Mat gradient_direction, Mat gradient_magnitude) {
        return new IntelligentScissorsMB(IntelligentScissorsMB.applyImageFeatures_1(this.nativeObj, non_edge.nativeObj, gradient_direction.nativeObj, gradient_magnitude.nativeObj));
    }

    public void buildMap(Point sourcePt) {
        IntelligentScissorsMB.buildMap_0(this.nativeObj, sourcePt.x, sourcePt.y);
    }

    public void getContour(Point targetPt, Mat contour, boolean backward) {
        IntelligentScissorsMB.getContour_0(this.nativeObj, targetPt.x, targetPt.y, contour.nativeObj, backward);
    }

    public void getContour(Point targetPt, Mat contour) {
        IntelligentScissorsMB.getContour_1(this.nativeObj, targetPt.x, targetPt.y, contour.nativeObj);
    }

    protected void finalize() throws Throwable {
        IntelligentScissorsMB.delete(this.nativeObj);
    }

    private static native long IntelligentScissorsMB_0();

    private static native long setWeights_0(long var0, float var2, float var3, float var4);

    private static native long setGradientMagnitudeMaxLimit_0(long var0, float var2);

    private static native long setGradientMagnitudeMaxLimit_1(long var0);

    private static native long setEdgeFeatureZeroCrossingParameters_0(long var0, float var2);

    private static native long setEdgeFeatureZeroCrossingParameters_1(long var0);

    private static native long setEdgeFeatureCannyParameters_0(long var0, double var2, double var4, int var6, boolean var7);

    private static native long setEdgeFeatureCannyParameters_1(long var0, double var2, double var4, int var6);

    private static native long setEdgeFeatureCannyParameters_2(long var0, double var2, double var4);

    private static native long applyImage_0(long var0, long var2);

    private static native long applyImageFeatures_0(long var0, long var2, long var4, long var6, long var8);

    private static native long applyImageFeatures_1(long var0, long var2, long var4, long var6);

    private static native void buildMap_0(long var0, double var2, double var4);

    private static native void getContour_0(long var0, double var2, double var4, long var6, boolean var8);

    private static native void getContour_1(long var0, double var2, double var4, long var6);

    private static native void delete(long var0);
}

