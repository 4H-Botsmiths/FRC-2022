/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.features2d.Feature2D;
import org.opencv.utils.Converters;

public class MSER
extends Feature2D {
    protected MSER(long addr) {
        super(addr);
    }

    public static MSER __fromPtr__(long addr) {
        return new MSER(addr);
    }

    public static MSER create(int _delta, int _min_area, int _max_area, double _max_variation, double _min_diversity, int _max_evolution, double _area_threshold, double _min_margin, int _edge_blur_size) {
        return MSER.__fromPtr__(MSER.create_0(_delta, _min_area, _max_area, _max_variation, _min_diversity, _max_evolution, _area_threshold, _min_margin, _edge_blur_size));
    }

    public static MSER create(int _delta, int _min_area, int _max_area, double _max_variation, double _min_diversity, int _max_evolution, double _area_threshold, double _min_margin) {
        return MSER.__fromPtr__(MSER.create_1(_delta, _min_area, _max_area, _max_variation, _min_diversity, _max_evolution, _area_threshold, _min_margin));
    }

    public static MSER create(int _delta, int _min_area, int _max_area, double _max_variation, double _min_diversity, int _max_evolution, double _area_threshold) {
        return MSER.__fromPtr__(MSER.create_2(_delta, _min_area, _max_area, _max_variation, _min_diversity, _max_evolution, _area_threshold));
    }

    public static MSER create(int _delta, int _min_area, int _max_area, double _max_variation, double _min_diversity, int _max_evolution) {
        return MSER.__fromPtr__(MSER.create_3(_delta, _min_area, _max_area, _max_variation, _min_diversity, _max_evolution));
    }

    public static MSER create(int _delta, int _min_area, int _max_area, double _max_variation, double _min_diversity) {
        return MSER.__fromPtr__(MSER.create_4(_delta, _min_area, _max_area, _max_variation, _min_diversity));
    }

    public static MSER create(int _delta, int _min_area, int _max_area, double _max_variation) {
        return MSER.__fromPtr__(MSER.create_5(_delta, _min_area, _max_area, _max_variation));
    }

    public static MSER create(int _delta, int _min_area, int _max_area) {
        return MSER.__fromPtr__(MSER.create_6(_delta, _min_area, _max_area));
    }

    public static MSER create(int _delta, int _min_area) {
        return MSER.__fromPtr__(MSER.create_7(_delta, _min_area));
    }

    public static MSER create(int _delta) {
        return MSER.__fromPtr__(MSER.create_8(_delta));
    }

    public static MSER create() {
        return MSER.__fromPtr__(MSER.create_9());
    }

    public void detectRegions(Mat image, List<MatOfPoint> msers, MatOfRect bboxes) {
        Mat msers_mat = new Mat();
        MatOfRect bboxes_mat = bboxes;
        MSER.detectRegions_0(this.nativeObj, image.nativeObj, msers_mat.nativeObj, bboxes_mat.nativeObj);
        Converters.Mat_to_vector_vector_Point(msers_mat, msers);
        msers_mat.release();
    }

    public void setDelta(int delta) {
        MSER.setDelta_0(this.nativeObj, delta);
    }

    public int getDelta() {
        return MSER.getDelta_0(this.nativeObj);
    }

    public void setMinArea(int minArea) {
        MSER.setMinArea_0(this.nativeObj, minArea);
    }

    public int getMinArea() {
        return MSER.getMinArea_0(this.nativeObj);
    }

    public void setMaxArea(int maxArea) {
        MSER.setMaxArea_0(this.nativeObj, maxArea);
    }

    public int getMaxArea() {
        return MSER.getMaxArea_0(this.nativeObj);
    }

    public void setPass2Only(boolean f) {
        MSER.setPass2Only_0(this.nativeObj, f);
    }

    public boolean getPass2Only() {
        return MSER.getPass2Only_0(this.nativeObj);
    }

    @Override
    public String getDefaultName() {
        return MSER.getDefaultName_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        MSER.delete(this.nativeObj);
    }

    private static native long create_0(int var0, int var1, int var2, double var3, double var5, int var7, double var8, double var10, int var12);

    private static native long create_1(int var0, int var1, int var2, double var3, double var5, int var7, double var8, double var10);

    private static native long create_2(int var0, int var1, int var2, double var3, double var5, int var7, double var8);

    private static native long create_3(int var0, int var1, int var2, double var3, double var5, int var7);

    private static native long create_4(int var0, int var1, int var2, double var3, double var5);

    private static native long create_5(int var0, int var1, int var2, double var3);

    private static native long create_6(int var0, int var1, int var2);

    private static native long create_7(int var0, int var1);

    private static native long create_8(int var0);

    private static native long create_9();

    private static native void detectRegions_0(long var0, long var2, long var4, long var6);

    private static native void setDelta_0(long var0, int var2);

    private static native int getDelta_0(long var0);

    private static native void setMinArea_0(long var0, int var2);

    private static native int getMinArea_0(long var0);

    private static native void setMaxArea_0(long var0, int var2);

    private static native int getMaxArea_0(long var0);

    private static native void setPass2Only_0(long var0, boolean var2);

    private static native boolean getPass2Only_0(long var0);

    private static native String getDefaultName_0(long var0);

    private static native void delete(long var0);
}

