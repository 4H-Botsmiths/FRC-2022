/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.objdetect;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Size;

public class CascadeClassifier {
    protected final long nativeObj;

    protected CascadeClassifier(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static CascadeClassifier __fromPtr__(long addr) {
        return new CascadeClassifier(addr);
    }

    public CascadeClassifier() {
        this.nativeObj = CascadeClassifier.CascadeClassifier_0();
    }

    public CascadeClassifier(String filename) {
        this.nativeObj = CascadeClassifier.CascadeClassifier_1(filename);
    }

    public boolean empty() {
        return CascadeClassifier.empty_0(this.nativeObj);
    }

    public boolean load(String filename) {
        return CascadeClassifier.load_0(this.nativeObj, filename);
    }

    public void detectMultiScale(Mat image, MatOfRect objects, double scaleFactor, int minNeighbors, int flags, Size minSize, Size maxSize) {
        MatOfRect objects_mat = objects;
        CascadeClassifier.detectMultiScale_0(this.nativeObj, image.nativeObj, objects_mat.nativeObj, scaleFactor, minNeighbors, flags, minSize.width, minSize.height, maxSize.width, maxSize.height);
    }

    public void detectMultiScale(Mat image, MatOfRect objects, double scaleFactor, int minNeighbors, int flags, Size minSize) {
        MatOfRect objects_mat = objects;
        CascadeClassifier.detectMultiScale_1(this.nativeObj, image.nativeObj, objects_mat.nativeObj, scaleFactor, minNeighbors, flags, minSize.width, minSize.height);
    }

    public void detectMultiScale(Mat image, MatOfRect objects, double scaleFactor, int minNeighbors, int flags) {
        MatOfRect objects_mat = objects;
        CascadeClassifier.detectMultiScale_2(this.nativeObj, image.nativeObj, objects_mat.nativeObj, scaleFactor, minNeighbors, flags);
    }

    public void detectMultiScale(Mat image, MatOfRect objects, double scaleFactor, int minNeighbors) {
        MatOfRect objects_mat = objects;
        CascadeClassifier.detectMultiScale_3(this.nativeObj, image.nativeObj, objects_mat.nativeObj, scaleFactor, minNeighbors);
    }

    public void detectMultiScale(Mat image, MatOfRect objects, double scaleFactor) {
        MatOfRect objects_mat = objects;
        CascadeClassifier.detectMultiScale_4(this.nativeObj, image.nativeObj, objects_mat.nativeObj, scaleFactor);
    }

    public void detectMultiScale(Mat image, MatOfRect objects) {
        MatOfRect objects_mat = objects;
        CascadeClassifier.detectMultiScale_5(this.nativeObj, image.nativeObj, objects_mat.nativeObj);
    }

    public void detectMultiScale2(Mat image, MatOfRect objects, MatOfInt numDetections, double scaleFactor, int minNeighbors, int flags, Size minSize, Size maxSize) {
        MatOfRect objects_mat = objects;
        MatOfInt numDetections_mat = numDetections;
        CascadeClassifier.detectMultiScale2_0(this.nativeObj, image.nativeObj, objects_mat.nativeObj, numDetections_mat.nativeObj, scaleFactor, minNeighbors, flags, minSize.width, minSize.height, maxSize.width, maxSize.height);
    }

    public void detectMultiScale2(Mat image, MatOfRect objects, MatOfInt numDetections, double scaleFactor, int minNeighbors, int flags, Size minSize) {
        MatOfRect objects_mat = objects;
        MatOfInt numDetections_mat = numDetections;
        CascadeClassifier.detectMultiScale2_1(this.nativeObj, image.nativeObj, objects_mat.nativeObj, numDetections_mat.nativeObj, scaleFactor, minNeighbors, flags, minSize.width, minSize.height);
    }

    public void detectMultiScale2(Mat image, MatOfRect objects, MatOfInt numDetections, double scaleFactor, int minNeighbors, int flags) {
        MatOfRect objects_mat = objects;
        MatOfInt numDetections_mat = numDetections;
        CascadeClassifier.detectMultiScale2_2(this.nativeObj, image.nativeObj, objects_mat.nativeObj, numDetections_mat.nativeObj, scaleFactor, minNeighbors, flags);
    }

    public void detectMultiScale2(Mat image, MatOfRect objects, MatOfInt numDetections, double scaleFactor, int minNeighbors) {
        MatOfRect objects_mat = objects;
        MatOfInt numDetections_mat = numDetections;
        CascadeClassifier.detectMultiScale2_3(this.nativeObj, image.nativeObj, objects_mat.nativeObj, numDetections_mat.nativeObj, scaleFactor, minNeighbors);
    }

    public void detectMultiScale2(Mat image, MatOfRect objects, MatOfInt numDetections, double scaleFactor) {
        MatOfRect objects_mat = objects;
        MatOfInt numDetections_mat = numDetections;
        CascadeClassifier.detectMultiScale2_4(this.nativeObj, image.nativeObj, objects_mat.nativeObj, numDetections_mat.nativeObj, scaleFactor);
    }

    public void detectMultiScale2(Mat image, MatOfRect objects, MatOfInt numDetections) {
        MatOfRect objects_mat = objects;
        MatOfInt numDetections_mat = numDetections;
        CascadeClassifier.detectMultiScale2_5(this.nativeObj, image.nativeObj, objects_mat.nativeObj, numDetections_mat.nativeObj);
    }

    public void detectMultiScale3(Mat image, MatOfRect objects, MatOfInt rejectLevels, MatOfDouble levelWeights, double scaleFactor, int minNeighbors, int flags, Size minSize, Size maxSize, boolean outputRejectLevels) {
        MatOfRect objects_mat = objects;
        MatOfInt rejectLevels_mat = rejectLevels;
        MatOfDouble levelWeights_mat = levelWeights;
        CascadeClassifier.detectMultiScale3_0(this.nativeObj, image.nativeObj, objects_mat.nativeObj, rejectLevels_mat.nativeObj, levelWeights_mat.nativeObj, scaleFactor, minNeighbors, flags, minSize.width, minSize.height, maxSize.width, maxSize.height, outputRejectLevels);
    }

    public void detectMultiScale3(Mat image, MatOfRect objects, MatOfInt rejectLevels, MatOfDouble levelWeights, double scaleFactor, int minNeighbors, int flags, Size minSize, Size maxSize) {
        MatOfRect objects_mat = objects;
        MatOfInt rejectLevels_mat = rejectLevels;
        MatOfDouble levelWeights_mat = levelWeights;
        CascadeClassifier.detectMultiScale3_1(this.nativeObj, image.nativeObj, objects_mat.nativeObj, rejectLevels_mat.nativeObj, levelWeights_mat.nativeObj, scaleFactor, minNeighbors, flags, minSize.width, minSize.height, maxSize.width, maxSize.height);
    }

    public void detectMultiScale3(Mat image, MatOfRect objects, MatOfInt rejectLevels, MatOfDouble levelWeights, double scaleFactor, int minNeighbors, int flags, Size minSize) {
        MatOfRect objects_mat = objects;
        MatOfInt rejectLevels_mat = rejectLevels;
        MatOfDouble levelWeights_mat = levelWeights;
        CascadeClassifier.detectMultiScale3_2(this.nativeObj, image.nativeObj, objects_mat.nativeObj, rejectLevels_mat.nativeObj, levelWeights_mat.nativeObj, scaleFactor, minNeighbors, flags, minSize.width, minSize.height);
    }

    public void detectMultiScale3(Mat image, MatOfRect objects, MatOfInt rejectLevels, MatOfDouble levelWeights, double scaleFactor, int minNeighbors, int flags) {
        MatOfRect objects_mat = objects;
        MatOfInt rejectLevels_mat = rejectLevels;
        MatOfDouble levelWeights_mat = levelWeights;
        CascadeClassifier.detectMultiScale3_3(this.nativeObj, image.nativeObj, objects_mat.nativeObj, rejectLevels_mat.nativeObj, levelWeights_mat.nativeObj, scaleFactor, minNeighbors, flags);
    }

    public void detectMultiScale3(Mat image, MatOfRect objects, MatOfInt rejectLevels, MatOfDouble levelWeights, double scaleFactor, int minNeighbors) {
        MatOfRect objects_mat = objects;
        MatOfInt rejectLevels_mat = rejectLevels;
        MatOfDouble levelWeights_mat = levelWeights;
        CascadeClassifier.detectMultiScale3_4(this.nativeObj, image.nativeObj, objects_mat.nativeObj, rejectLevels_mat.nativeObj, levelWeights_mat.nativeObj, scaleFactor, minNeighbors);
    }

    public void detectMultiScale3(Mat image, MatOfRect objects, MatOfInt rejectLevels, MatOfDouble levelWeights, double scaleFactor) {
        MatOfRect objects_mat = objects;
        MatOfInt rejectLevels_mat = rejectLevels;
        MatOfDouble levelWeights_mat = levelWeights;
        CascadeClassifier.detectMultiScale3_5(this.nativeObj, image.nativeObj, objects_mat.nativeObj, rejectLevels_mat.nativeObj, levelWeights_mat.nativeObj, scaleFactor);
    }

    public void detectMultiScale3(Mat image, MatOfRect objects, MatOfInt rejectLevels, MatOfDouble levelWeights) {
        MatOfRect objects_mat = objects;
        MatOfInt rejectLevels_mat = rejectLevels;
        MatOfDouble levelWeights_mat = levelWeights;
        CascadeClassifier.detectMultiScale3_6(this.nativeObj, image.nativeObj, objects_mat.nativeObj, rejectLevels_mat.nativeObj, levelWeights_mat.nativeObj);
    }

    public boolean isOldFormatCascade() {
        return CascadeClassifier.isOldFormatCascade_0(this.nativeObj);
    }

    public Size getOriginalWindowSize() {
        return new Size(CascadeClassifier.getOriginalWindowSize_0(this.nativeObj));
    }

    public int getFeatureType() {
        return CascadeClassifier.getFeatureType_0(this.nativeObj);
    }

    public static boolean convert(String oldcascade, String newcascade) {
        return CascadeClassifier.convert_0(oldcascade, newcascade);
    }

    protected void finalize() throws Throwable {
        CascadeClassifier.delete(this.nativeObj);
    }

    private static native long CascadeClassifier_0();

    private static native long CascadeClassifier_1(String var0);

    private static native boolean empty_0(long var0);

    private static native boolean load_0(long var0, String var2);

    private static native void detectMultiScale_0(long var0, long var2, long var4, double var6, int var8, int var9, double var10, double var12, double var14, double var16);

    private static native void detectMultiScale_1(long var0, long var2, long var4, double var6, int var8, int var9, double var10, double var12);

    private static native void detectMultiScale_2(long var0, long var2, long var4, double var6, int var8, int var9);

    private static native void detectMultiScale_3(long var0, long var2, long var4, double var6, int var8);

    private static native void detectMultiScale_4(long var0, long var2, long var4, double var6);

    private static native void detectMultiScale_5(long var0, long var2, long var4);

    private static native void detectMultiScale2_0(long var0, long var2, long var4, long var6, double var8, int var10, int var11, double var12, double var14, double var16, double var18);

    private static native void detectMultiScale2_1(long var0, long var2, long var4, long var6, double var8, int var10, int var11, double var12, double var14);

    private static native void detectMultiScale2_2(long var0, long var2, long var4, long var6, double var8, int var10, int var11);

    private static native void detectMultiScale2_3(long var0, long var2, long var4, long var6, double var8, int var10);

    private static native void detectMultiScale2_4(long var0, long var2, long var4, long var6, double var8);

    private static native void detectMultiScale2_5(long var0, long var2, long var4, long var6);

    private static native void detectMultiScale3_0(long var0, long var2, long var4, long var6, long var8, double var10, int var12, int var13, double var14, double var16, double var18, double var20, boolean var22);

    private static native void detectMultiScale3_1(long var0, long var2, long var4, long var6, long var8, double var10, int var12, int var13, double var14, double var16, double var18, double var20);

    private static native void detectMultiScale3_2(long var0, long var2, long var4, long var6, long var8, double var10, int var12, int var13, double var14, double var16);

    private static native void detectMultiScale3_3(long var0, long var2, long var4, long var6, long var8, double var10, int var12, int var13);

    private static native void detectMultiScale3_4(long var0, long var2, long var4, long var6, long var8, double var10, int var12);

    private static native void detectMultiScale3_5(long var0, long var2, long var4, long var6, long var8, double var10);

    private static native void detectMultiScale3_6(long var0, long var2, long var4, long var6, long var8);

    private static native boolean isOldFormatCascade_0(long var0);

    private static native double[] getOriginalWindowSize_0(long var0);

    private static native int getFeatureType_0(long var0);

    private static native boolean convert_0(String var0, String var1);

    private static native void delete(long var0);
}

