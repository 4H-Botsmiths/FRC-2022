/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Algorithm;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.utils.Converters;

public class DescriptorMatcher
extends Algorithm {
    public static final int FLANNBASED = 1;
    public static final int BRUTEFORCE = 2;
    public static final int BRUTEFORCE_L1 = 3;
    public static final int BRUTEFORCE_HAMMING = 4;
    public static final int BRUTEFORCE_HAMMINGLUT = 5;
    public static final int BRUTEFORCE_SL2 = 6;

    protected DescriptorMatcher(long addr) {
        super(addr);
    }

    public static DescriptorMatcher __fromPtr__(long addr) {
        return new DescriptorMatcher(addr);
    }

    public void add(List<Mat> descriptors) {
        Mat descriptors_mat = Converters.vector_Mat_to_Mat(descriptors);
        DescriptorMatcher.add_0(this.nativeObj, descriptors_mat.nativeObj);
    }

    public List<Mat> getTrainDescriptors() {
        ArrayList<Mat> retVal = new ArrayList<Mat>();
        Mat retValMat = new Mat(DescriptorMatcher.getTrainDescriptors_0(this.nativeObj));
        Converters.Mat_to_vector_Mat(retValMat, retVal);
        return retVal;
    }

    @Override
    public void clear() {
        DescriptorMatcher.clear_0(this.nativeObj);
    }

    @Override
    public boolean empty() {
        return DescriptorMatcher.empty_0(this.nativeObj);
    }

    public boolean isMaskSupported() {
        return DescriptorMatcher.isMaskSupported_0(this.nativeObj);
    }

    public void train() {
        DescriptorMatcher.train_0(this.nativeObj);
    }

    public void match(Mat queryDescriptors, Mat trainDescriptors, MatOfDMatch matches, Mat mask) {
        MatOfDMatch matches_mat = matches;
        DescriptorMatcher.match_0(this.nativeObj, queryDescriptors.nativeObj, trainDescriptors.nativeObj, matches_mat.nativeObj, mask.nativeObj);
    }

    public void match(Mat queryDescriptors, Mat trainDescriptors, MatOfDMatch matches) {
        MatOfDMatch matches_mat = matches;
        DescriptorMatcher.match_1(this.nativeObj, queryDescriptors.nativeObj, trainDescriptors.nativeObj, matches_mat.nativeObj);
    }

    public void knnMatch(Mat queryDescriptors, Mat trainDescriptors, List<MatOfDMatch> matches, int k, Mat mask, boolean compactResult) {
        Mat matches_mat = new Mat();
        DescriptorMatcher.knnMatch_0(this.nativeObj, queryDescriptors.nativeObj, trainDescriptors.nativeObj, matches_mat.nativeObj, k, mask.nativeObj, compactResult);
        Converters.Mat_to_vector_vector_DMatch(matches_mat, matches);
        matches_mat.release();
    }

    public void knnMatch(Mat queryDescriptors, Mat trainDescriptors, List<MatOfDMatch> matches, int k, Mat mask) {
        Mat matches_mat = new Mat();
        DescriptorMatcher.knnMatch_1(this.nativeObj, queryDescriptors.nativeObj, trainDescriptors.nativeObj, matches_mat.nativeObj, k, mask.nativeObj);
        Converters.Mat_to_vector_vector_DMatch(matches_mat, matches);
        matches_mat.release();
    }

    public void knnMatch(Mat queryDescriptors, Mat trainDescriptors, List<MatOfDMatch> matches, int k) {
        Mat matches_mat = new Mat();
        DescriptorMatcher.knnMatch_2(this.nativeObj, queryDescriptors.nativeObj, trainDescriptors.nativeObj, matches_mat.nativeObj, k);
        Converters.Mat_to_vector_vector_DMatch(matches_mat, matches);
        matches_mat.release();
    }

    public void radiusMatch(Mat queryDescriptors, Mat trainDescriptors, List<MatOfDMatch> matches, float maxDistance, Mat mask, boolean compactResult) {
        Mat matches_mat = new Mat();
        DescriptorMatcher.radiusMatch_0(this.nativeObj, queryDescriptors.nativeObj, trainDescriptors.nativeObj, matches_mat.nativeObj, maxDistance, mask.nativeObj, compactResult);
        Converters.Mat_to_vector_vector_DMatch(matches_mat, matches);
        matches_mat.release();
    }

    public void radiusMatch(Mat queryDescriptors, Mat trainDescriptors, List<MatOfDMatch> matches, float maxDistance, Mat mask) {
        Mat matches_mat = new Mat();
        DescriptorMatcher.radiusMatch_1(this.nativeObj, queryDescriptors.nativeObj, trainDescriptors.nativeObj, matches_mat.nativeObj, maxDistance, mask.nativeObj);
        Converters.Mat_to_vector_vector_DMatch(matches_mat, matches);
        matches_mat.release();
    }

    public void radiusMatch(Mat queryDescriptors, Mat trainDescriptors, List<MatOfDMatch> matches, float maxDistance) {
        Mat matches_mat = new Mat();
        DescriptorMatcher.radiusMatch_2(this.nativeObj, queryDescriptors.nativeObj, trainDescriptors.nativeObj, matches_mat.nativeObj, maxDistance);
        Converters.Mat_to_vector_vector_DMatch(matches_mat, matches);
        matches_mat.release();
    }

    public void match(Mat queryDescriptors, MatOfDMatch matches, List<Mat> masks) {
        MatOfDMatch matches_mat = matches;
        Mat masks_mat = Converters.vector_Mat_to_Mat(masks);
        DescriptorMatcher.match_2(this.nativeObj, queryDescriptors.nativeObj, matches_mat.nativeObj, masks_mat.nativeObj);
    }

    public void match(Mat queryDescriptors, MatOfDMatch matches) {
        MatOfDMatch matches_mat = matches;
        DescriptorMatcher.match_3(this.nativeObj, queryDescriptors.nativeObj, matches_mat.nativeObj);
    }

    public void knnMatch(Mat queryDescriptors, List<MatOfDMatch> matches, int k, List<Mat> masks, boolean compactResult) {
        Mat matches_mat = new Mat();
        Mat masks_mat = Converters.vector_Mat_to_Mat(masks);
        DescriptorMatcher.knnMatch_3(this.nativeObj, queryDescriptors.nativeObj, matches_mat.nativeObj, k, masks_mat.nativeObj, compactResult);
        Converters.Mat_to_vector_vector_DMatch(matches_mat, matches);
        matches_mat.release();
    }

    public void knnMatch(Mat queryDescriptors, List<MatOfDMatch> matches, int k, List<Mat> masks) {
        Mat matches_mat = new Mat();
        Mat masks_mat = Converters.vector_Mat_to_Mat(masks);
        DescriptorMatcher.knnMatch_4(this.nativeObj, queryDescriptors.nativeObj, matches_mat.nativeObj, k, masks_mat.nativeObj);
        Converters.Mat_to_vector_vector_DMatch(matches_mat, matches);
        matches_mat.release();
    }

    public void knnMatch(Mat queryDescriptors, List<MatOfDMatch> matches, int k) {
        Mat matches_mat = new Mat();
        DescriptorMatcher.knnMatch_5(this.nativeObj, queryDescriptors.nativeObj, matches_mat.nativeObj, k);
        Converters.Mat_to_vector_vector_DMatch(matches_mat, matches);
        matches_mat.release();
    }

    public void radiusMatch(Mat queryDescriptors, List<MatOfDMatch> matches, float maxDistance, List<Mat> masks, boolean compactResult) {
        Mat matches_mat = new Mat();
        Mat masks_mat = Converters.vector_Mat_to_Mat(masks);
        DescriptorMatcher.radiusMatch_3(this.nativeObj, queryDescriptors.nativeObj, matches_mat.nativeObj, maxDistance, masks_mat.nativeObj, compactResult);
        Converters.Mat_to_vector_vector_DMatch(matches_mat, matches);
        matches_mat.release();
    }

    public void radiusMatch(Mat queryDescriptors, List<MatOfDMatch> matches, float maxDistance, List<Mat> masks) {
        Mat matches_mat = new Mat();
        Mat masks_mat = Converters.vector_Mat_to_Mat(masks);
        DescriptorMatcher.radiusMatch_4(this.nativeObj, queryDescriptors.nativeObj, matches_mat.nativeObj, maxDistance, masks_mat.nativeObj);
        Converters.Mat_to_vector_vector_DMatch(matches_mat, matches);
        matches_mat.release();
    }

    public void radiusMatch(Mat queryDescriptors, List<MatOfDMatch> matches, float maxDistance) {
        Mat matches_mat = new Mat();
        DescriptorMatcher.radiusMatch_5(this.nativeObj, queryDescriptors.nativeObj, matches_mat.nativeObj, maxDistance);
        Converters.Mat_to_vector_vector_DMatch(matches_mat, matches);
        matches_mat.release();
    }

    public void write(String fileName) {
        DescriptorMatcher.write_0(this.nativeObj, fileName);
    }

    public void read(String fileName) {
        DescriptorMatcher.read_0(this.nativeObj, fileName);
    }

    public DescriptorMatcher clone(boolean emptyTrainData) {
        return DescriptorMatcher.__fromPtr__(DescriptorMatcher.clone_0(this.nativeObj, emptyTrainData));
    }

    public DescriptorMatcher clone() {
        return DescriptorMatcher.__fromPtr__(DescriptorMatcher.clone_1(this.nativeObj));
    }

    public static DescriptorMatcher create(String descriptorMatcherType) {
        return DescriptorMatcher.__fromPtr__(DescriptorMatcher.create_0(descriptorMatcherType));
    }

    public static DescriptorMatcher create(int matcherType) {
        return DescriptorMatcher.__fromPtr__(DescriptorMatcher.create_1(matcherType));
    }

    @Override
    protected void finalize() throws Throwable {
        DescriptorMatcher.delete(this.nativeObj);
    }

    private static native void add_0(long var0, long var2);

    private static native long getTrainDescriptors_0(long var0);

    private static native void clear_0(long var0);

    private static native boolean empty_0(long var0);

    private static native boolean isMaskSupported_0(long var0);

    private static native void train_0(long var0);

    private static native void match_0(long var0, long var2, long var4, long var6, long var8);

    private static native void match_1(long var0, long var2, long var4, long var6);

    private static native void knnMatch_0(long var0, long var2, long var4, long var6, int var8, long var9, boolean var11);

    private static native void knnMatch_1(long var0, long var2, long var4, long var6, int var8, long var9);

    private static native void knnMatch_2(long var0, long var2, long var4, long var6, int var8);

    private static native void radiusMatch_0(long var0, long var2, long var4, long var6, float var8, long var9, boolean var11);

    private static native void radiusMatch_1(long var0, long var2, long var4, long var6, float var8, long var9);

    private static native void radiusMatch_2(long var0, long var2, long var4, long var6, float var8);

    private static native void match_2(long var0, long var2, long var4, long var6);

    private static native void match_3(long var0, long var2, long var4);

    private static native void knnMatch_3(long var0, long var2, long var4, int var6, long var7, boolean var9);

    private static native void knnMatch_4(long var0, long var2, long var4, int var6, long var7);

    private static native void knnMatch_5(long var0, long var2, long var4, int var6);

    private static native void radiusMatch_3(long var0, long var2, long var4, float var6, long var7, boolean var9);

    private static native void radiusMatch_4(long var0, long var2, long var4, float var6, long var7);

    private static native void radiusMatch_5(long var0, long var2, long var4, float var6);

    private static native void write_0(long var0, String var2);

    private static native void read_0(long var0, String var2);

    private static native long clone_0(long var0, boolean var2);

    private static native long clone_1(long var0);

    private static native long create_0(String var0);

    private static native long create_1(int var0);

    private static native void delete(long var0);
}

