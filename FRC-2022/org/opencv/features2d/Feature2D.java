/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Algorithm;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.utils.Converters;

public class Feature2D
extends Algorithm {
    protected Feature2D(long addr) {
        super(addr);
    }

    public static Feature2D __fromPtr__(long addr) {
        return new Feature2D(addr);
    }

    public void detect(Mat image, MatOfKeyPoint keypoints, Mat mask) {
        MatOfKeyPoint keypoints_mat = keypoints;
        Feature2D.detect_0(this.nativeObj, image.nativeObj, keypoints_mat.nativeObj, mask.nativeObj);
    }

    public void detect(Mat image, MatOfKeyPoint keypoints) {
        MatOfKeyPoint keypoints_mat = keypoints;
        Feature2D.detect_1(this.nativeObj, image.nativeObj, keypoints_mat.nativeObj);
    }

    public void detect(List<Mat> images, List<MatOfKeyPoint> keypoints, List<Mat> masks) {
        Mat images_mat = Converters.vector_Mat_to_Mat(images);
        Mat keypoints_mat = new Mat();
        Mat masks_mat = Converters.vector_Mat_to_Mat(masks);
        Feature2D.detect_2(this.nativeObj, images_mat.nativeObj, keypoints_mat.nativeObj, masks_mat.nativeObj);
        Converters.Mat_to_vector_vector_KeyPoint(keypoints_mat, keypoints);
        keypoints_mat.release();
    }

    public void detect(List<Mat> images, List<MatOfKeyPoint> keypoints) {
        Mat images_mat = Converters.vector_Mat_to_Mat(images);
        Mat keypoints_mat = new Mat();
        Feature2D.detect_3(this.nativeObj, images_mat.nativeObj, keypoints_mat.nativeObj);
        Converters.Mat_to_vector_vector_KeyPoint(keypoints_mat, keypoints);
        keypoints_mat.release();
    }

    public void compute(Mat image, MatOfKeyPoint keypoints, Mat descriptors) {
        MatOfKeyPoint keypoints_mat = keypoints;
        Feature2D.compute_0(this.nativeObj, image.nativeObj, keypoints_mat.nativeObj, descriptors.nativeObj);
    }

    public void compute(List<Mat> images, List<MatOfKeyPoint> keypoints, List<Mat> descriptors) {
        Mat images_mat = Converters.vector_Mat_to_Mat(images);
        ArrayList<Mat> keypoints_tmplm = new ArrayList<Mat>(keypoints != null ? keypoints.size() : 0);
        Mat keypoints_mat = Converters.vector_vector_KeyPoint_to_Mat(keypoints, keypoints_tmplm);
        Mat descriptors_mat = new Mat();
        Feature2D.compute_1(this.nativeObj, images_mat.nativeObj, keypoints_mat.nativeObj, descriptors_mat.nativeObj);
        Converters.Mat_to_vector_vector_KeyPoint(keypoints_mat, keypoints);
        keypoints_mat.release();
        Converters.Mat_to_vector_Mat(descriptors_mat, descriptors);
        descriptors_mat.release();
    }

    public void detectAndCompute(Mat image, Mat mask, MatOfKeyPoint keypoints, Mat descriptors, boolean useProvidedKeypoints) {
        MatOfKeyPoint keypoints_mat = keypoints;
        Feature2D.detectAndCompute_0(this.nativeObj, image.nativeObj, mask.nativeObj, keypoints_mat.nativeObj, descriptors.nativeObj, useProvidedKeypoints);
    }

    public void detectAndCompute(Mat image, Mat mask, MatOfKeyPoint keypoints, Mat descriptors) {
        MatOfKeyPoint keypoints_mat = keypoints;
        Feature2D.detectAndCompute_1(this.nativeObj, image.nativeObj, mask.nativeObj, keypoints_mat.nativeObj, descriptors.nativeObj);
    }

    public int descriptorSize() {
        return Feature2D.descriptorSize_0(this.nativeObj);
    }

    public int descriptorType() {
        return Feature2D.descriptorType_0(this.nativeObj);
    }

    public int defaultNorm() {
        return Feature2D.defaultNorm_0(this.nativeObj);
    }

    public void write(String fileName) {
        Feature2D.write_0(this.nativeObj, fileName);
    }

    public void read(String fileName) {
        Feature2D.read_0(this.nativeObj, fileName);
    }

    @Override
    public boolean empty() {
        return Feature2D.empty_0(this.nativeObj);
    }

    @Override
    public String getDefaultName() {
        return Feature2D.getDefaultName_0(this.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        Feature2D.delete(this.nativeObj);
    }

    private static native void detect_0(long var0, long var2, long var4, long var6);

    private static native void detect_1(long var0, long var2, long var4);

    private static native void detect_2(long var0, long var2, long var4, long var6);

    private static native void detect_3(long var0, long var2, long var4);

    private static native void compute_0(long var0, long var2, long var4, long var6);

    private static native void compute_1(long var0, long var2, long var4, long var6);

    private static native void detectAndCompute_0(long var0, long var2, long var4, long var6, long var8, boolean var10);

    private static native void detectAndCompute_1(long var0, long var2, long var4, long var6, long var8);

    private static native int descriptorSize_0(long var0);

    private static native int descriptorType_0(long var0);

    private static native int defaultNorm_0(long var0);

    private static native void write_0(long var0, String var2);

    private static native void read_0(long var0, String var2);

    private static native boolean empty_0(long var0);

    private static native String getDefaultName_0(long var0);

    private static native void delete(long var0);
}

