/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.features2d.Feature2D;

public class BRISK
extends Feature2D {
    protected BRISK(long addr) {
        super(addr);
    }

    public static BRISK __fromPtr__(long addr) {
        return new BRISK(addr);
    }

    public static BRISK create(int thresh, int octaves, float patternScale) {
        return BRISK.__fromPtr__(BRISK.create_0(thresh, octaves, patternScale));
    }

    public static BRISK create(int thresh, int octaves) {
        return BRISK.__fromPtr__(BRISK.create_1(thresh, octaves));
    }

    public static BRISK create(int thresh) {
        return BRISK.__fromPtr__(BRISK.create_2(thresh));
    }

    public static BRISK create() {
        return BRISK.__fromPtr__(BRISK.create_3());
    }

    public static BRISK create(MatOfFloat radiusList, MatOfInt numberList, float dMax, float dMin, MatOfInt indexChange) {
        MatOfFloat radiusList_mat = radiusList;
        MatOfInt numberList_mat = numberList;
        MatOfInt indexChange_mat = indexChange;
        return BRISK.__fromPtr__(BRISK.create_4(radiusList_mat.nativeObj, numberList_mat.nativeObj, dMax, dMin, indexChange_mat.nativeObj));
    }

    public static BRISK create(MatOfFloat radiusList, MatOfInt numberList, float dMax, float dMin) {
        MatOfFloat radiusList_mat = radiusList;
        MatOfInt numberList_mat = numberList;
        return BRISK.__fromPtr__(BRISK.create_5(radiusList_mat.nativeObj, numberList_mat.nativeObj, dMax, dMin));
    }

    public static BRISK create(MatOfFloat radiusList, MatOfInt numberList, float dMax) {
        MatOfFloat radiusList_mat = radiusList;
        MatOfInt numberList_mat = numberList;
        return BRISK.__fromPtr__(BRISK.create_6(radiusList_mat.nativeObj, numberList_mat.nativeObj, dMax));
    }

    public static BRISK create(MatOfFloat radiusList, MatOfInt numberList) {
        MatOfFloat radiusList_mat = radiusList;
        MatOfInt numberList_mat = numberList;
        return BRISK.__fromPtr__(BRISK.create_7(radiusList_mat.nativeObj, numberList_mat.nativeObj));
    }

    public static BRISK create(int thresh, int octaves, MatOfFloat radiusList, MatOfInt numberList, float dMax, float dMin, MatOfInt indexChange) {
        MatOfFloat radiusList_mat = radiusList;
        MatOfInt numberList_mat = numberList;
        MatOfInt indexChange_mat = indexChange;
        return BRISK.__fromPtr__(BRISK.create_8(thresh, octaves, radiusList_mat.nativeObj, numberList_mat.nativeObj, dMax, dMin, indexChange_mat.nativeObj));
    }

    public static BRISK create(int thresh, int octaves, MatOfFloat radiusList, MatOfInt numberList, float dMax, float dMin) {
        MatOfFloat radiusList_mat = radiusList;
        MatOfInt numberList_mat = numberList;
        return BRISK.__fromPtr__(BRISK.create_9(thresh, octaves, radiusList_mat.nativeObj, numberList_mat.nativeObj, dMax, dMin));
    }

    public static BRISK create(int thresh, int octaves, MatOfFloat radiusList, MatOfInt numberList, float dMax) {
        MatOfFloat radiusList_mat = radiusList;
        MatOfInt numberList_mat = numberList;
        return BRISK.__fromPtr__(BRISK.create_10(thresh, octaves, radiusList_mat.nativeObj, numberList_mat.nativeObj, dMax));
    }

    public static BRISK create(int thresh, int octaves, MatOfFloat radiusList, MatOfInt numberList) {
        MatOfFloat radiusList_mat = radiusList;
        MatOfInt numberList_mat = numberList;
        return BRISK.__fromPtr__(BRISK.create_11(thresh, octaves, radiusList_mat.nativeObj, numberList_mat.nativeObj));
    }

    @Override
    public String getDefaultName() {
        return BRISK.getDefaultName_0(this.nativeObj);
    }

    public void setThreshold(int threshold) {
        BRISK.setThreshold_0(this.nativeObj, threshold);
    }

    public int getThreshold() {
        return BRISK.getThreshold_0(this.nativeObj);
    }

    public void setOctaves(int octaves) {
        BRISK.setOctaves_0(this.nativeObj, octaves);
    }

    public int getOctaves() {
        return BRISK.getOctaves_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        BRISK.delete(this.nativeObj);
    }

    private static native long create_0(int var0, int var1, float var2);

    private static native long create_1(int var0, int var1);

    private static native long create_2(int var0);

    private static native long create_3();

    private static native long create_4(long var0, long var2, float var4, float var5, long var6);

    private static native long create_5(long var0, long var2, float var4, float var5);

    private static native long create_6(long var0, long var2, float var4);

    private static native long create_7(long var0, long var2);

    private static native long create_8(int var0, int var1, long var2, long var4, float var6, float var7, long var8);

    private static native long create_9(int var0, int var1, long var2, long var4, float var6, float var7);

    private static native long create_10(int var0, int var1, long var2, long var4, float var6);

    private static native long create_11(int var0, int var1, long var2, long var4);

    private static native String getDefaultName_0(long var0);

    private static native void setThreshold_0(long var0, int var2);

    private static native int getThreshold_0(long var0);

    private static native void setOctaves_0(long var0, int var2);

    private static native int getOctaves_0(long var0);

    private static native void delete(long var0);
}

