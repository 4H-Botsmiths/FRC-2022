/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.utils.Converters;

public class Features2d {
    public static final int DrawMatchesFlags_DEFAULT = 0;
    public static final int DrawMatchesFlags_DRAW_OVER_OUTIMG = 1;
    public static final int DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS = 2;
    public static final int DrawMatchesFlags_DRAW_RICH_KEYPOINTS = 4;

    public static void drawKeypoints(Mat image, MatOfKeyPoint keypoints, Mat outImage, Scalar color, int flags) {
        MatOfKeyPoint keypoints_mat = keypoints;
        Features2d.drawKeypoints_0(image.nativeObj, keypoints_mat.nativeObj, outImage.nativeObj, color.val[0], color.val[1], color.val[2], color.val[3], flags);
    }

    public static void drawKeypoints(Mat image, MatOfKeyPoint keypoints, Mat outImage, Scalar color) {
        MatOfKeyPoint keypoints_mat = keypoints;
        Features2d.drawKeypoints_1(image.nativeObj, keypoints_mat.nativeObj, outImage.nativeObj, color.val[0], color.val[1], color.val[2], color.val[3]);
    }

    public static void drawKeypoints(Mat image, MatOfKeyPoint keypoints, Mat outImage) {
        MatOfKeyPoint keypoints_mat = keypoints;
        Features2d.drawKeypoints_2(image.nativeObj, keypoints_mat.nativeObj, outImage.nativeObj);
    }

    public static void drawMatches(Mat img1, MatOfKeyPoint keypoints1, Mat img2, MatOfKeyPoint keypoints2, MatOfDMatch matches1to2, Mat outImg, Scalar matchColor, Scalar singlePointColor, MatOfByte matchesMask, int flags) {
        MatOfKeyPoint keypoints1_mat = keypoints1;
        MatOfKeyPoint keypoints2_mat = keypoints2;
        MatOfDMatch matches1to2_mat = matches1to2;
        MatOfByte matchesMask_mat = matchesMask;
        Features2d.drawMatches_0(img1.nativeObj, keypoints1_mat.nativeObj, img2.nativeObj, keypoints2_mat.nativeObj, matches1to2_mat.nativeObj, outImg.nativeObj, matchColor.val[0], matchColor.val[1], matchColor.val[2], matchColor.val[3], singlePointColor.val[0], singlePointColor.val[1], singlePointColor.val[2], singlePointColor.val[3], matchesMask_mat.nativeObj, flags);
    }

    public static void drawMatches(Mat img1, MatOfKeyPoint keypoints1, Mat img2, MatOfKeyPoint keypoints2, MatOfDMatch matches1to2, Mat outImg, Scalar matchColor, Scalar singlePointColor, MatOfByte matchesMask) {
        MatOfKeyPoint keypoints1_mat = keypoints1;
        MatOfKeyPoint keypoints2_mat = keypoints2;
        MatOfDMatch matches1to2_mat = matches1to2;
        MatOfByte matchesMask_mat = matchesMask;
        Features2d.drawMatches_1(img1.nativeObj, keypoints1_mat.nativeObj, img2.nativeObj, keypoints2_mat.nativeObj, matches1to2_mat.nativeObj, outImg.nativeObj, matchColor.val[0], matchColor.val[1], matchColor.val[2], matchColor.val[3], singlePointColor.val[0], singlePointColor.val[1], singlePointColor.val[2], singlePointColor.val[3], matchesMask_mat.nativeObj);
    }

    public static void drawMatches(Mat img1, MatOfKeyPoint keypoints1, Mat img2, MatOfKeyPoint keypoints2, MatOfDMatch matches1to2, Mat outImg, Scalar matchColor, Scalar singlePointColor) {
        MatOfKeyPoint keypoints1_mat = keypoints1;
        MatOfKeyPoint keypoints2_mat = keypoints2;
        MatOfDMatch matches1to2_mat = matches1to2;
        Features2d.drawMatches_2(img1.nativeObj, keypoints1_mat.nativeObj, img2.nativeObj, keypoints2_mat.nativeObj, matches1to2_mat.nativeObj, outImg.nativeObj, matchColor.val[0], matchColor.val[1], matchColor.val[2], matchColor.val[3], singlePointColor.val[0], singlePointColor.val[1], singlePointColor.val[2], singlePointColor.val[3]);
    }

    public static void drawMatches(Mat img1, MatOfKeyPoint keypoints1, Mat img2, MatOfKeyPoint keypoints2, MatOfDMatch matches1to2, Mat outImg, Scalar matchColor) {
        MatOfKeyPoint keypoints1_mat = keypoints1;
        MatOfKeyPoint keypoints2_mat = keypoints2;
        MatOfDMatch matches1to2_mat = matches1to2;
        Features2d.drawMatches_3(img1.nativeObj, keypoints1_mat.nativeObj, img2.nativeObj, keypoints2_mat.nativeObj, matches1to2_mat.nativeObj, outImg.nativeObj, matchColor.val[0], matchColor.val[1], matchColor.val[2], matchColor.val[3]);
    }

    public static void drawMatches(Mat img1, MatOfKeyPoint keypoints1, Mat img2, MatOfKeyPoint keypoints2, MatOfDMatch matches1to2, Mat outImg) {
        MatOfKeyPoint keypoints1_mat = keypoints1;
        MatOfKeyPoint keypoints2_mat = keypoints2;
        MatOfDMatch matches1to2_mat = matches1to2;
        Features2d.drawMatches_4(img1.nativeObj, keypoints1_mat.nativeObj, img2.nativeObj, keypoints2_mat.nativeObj, matches1to2_mat.nativeObj, outImg.nativeObj);
    }

    public static void drawMatchesKnn(Mat img1, MatOfKeyPoint keypoints1, Mat img2, MatOfKeyPoint keypoints2, List<MatOfDMatch> matches1to2, Mat outImg, Scalar matchColor, Scalar singlePointColor, List<MatOfByte> matchesMask, int flags) {
        MatOfKeyPoint keypoints1_mat = keypoints1;
        MatOfKeyPoint keypoints2_mat = keypoints2;
        ArrayList<Mat> matches1to2_tmplm = new ArrayList<Mat>(matches1to2 != null ? matches1to2.size() : 0);
        Mat matches1to2_mat = Converters.vector_vector_DMatch_to_Mat(matches1to2, matches1to2_tmplm);
        ArrayList<Mat> matchesMask_tmplm = new ArrayList<Mat>(matchesMask != null ? matchesMask.size() : 0);
        Mat matchesMask_mat = Converters.vector_vector_char_to_Mat(matchesMask, matchesMask_tmplm);
        Features2d.drawMatchesKnn_0(img1.nativeObj, keypoints1_mat.nativeObj, img2.nativeObj, keypoints2_mat.nativeObj, matches1to2_mat.nativeObj, outImg.nativeObj, matchColor.val[0], matchColor.val[1], matchColor.val[2], matchColor.val[3], singlePointColor.val[0], singlePointColor.val[1], singlePointColor.val[2], singlePointColor.val[3], matchesMask_mat.nativeObj, flags);
    }

    public static void drawMatchesKnn(Mat img1, MatOfKeyPoint keypoints1, Mat img2, MatOfKeyPoint keypoints2, List<MatOfDMatch> matches1to2, Mat outImg, Scalar matchColor, Scalar singlePointColor, List<MatOfByte> matchesMask) {
        MatOfKeyPoint keypoints1_mat = keypoints1;
        MatOfKeyPoint keypoints2_mat = keypoints2;
        ArrayList<Mat> matches1to2_tmplm = new ArrayList<Mat>(matches1to2 != null ? matches1to2.size() : 0);
        Mat matches1to2_mat = Converters.vector_vector_DMatch_to_Mat(matches1to2, matches1to2_tmplm);
        ArrayList<Mat> matchesMask_tmplm = new ArrayList<Mat>(matchesMask != null ? matchesMask.size() : 0);
        Mat matchesMask_mat = Converters.vector_vector_char_to_Mat(matchesMask, matchesMask_tmplm);
        Features2d.drawMatchesKnn_1(img1.nativeObj, keypoints1_mat.nativeObj, img2.nativeObj, keypoints2_mat.nativeObj, matches1to2_mat.nativeObj, outImg.nativeObj, matchColor.val[0], matchColor.val[1], matchColor.val[2], matchColor.val[3], singlePointColor.val[0], singlePointColor.val[1], singlePointColor.val[2], singlePointColor.val[3], matchesMask_mat.nativeObj);
    }

    public static void drawMatchesKnn(Mat img1, MatOfKeyPoint keypoints1, Mat img2, MatOfKeyPoint keypoints2, List<MatOfDMatch> matches1to2, Mat outImg, Scalar matchColor, Scalar singlePointColor) {
        MatOfKeyPoint keypoints1_mat = keypoints1;
        MatOfKeyPoint keypoints2_mat = keypoints2;
        ArrayList<Mat> matches1to2_tmplm = new ArrayList<Mat>(matches1to2 != null ? matches1to2.size() : 0);
        Mat matches1to2_mat = Converters.vector_vector_DMatch_to_Mat(matches1to2, matches1to2_tmplm);
        Features2d.drawMatchesKnn_2(img1.nativeObj, keypoints1_mat.nativeObj, img2.nativeObj, keypoints2_mat.nativeObj, matches1to2_mat.nativeObj, outImg.nativeObj, matchColor.val[0], matchColor.val[1], matchColor.val[2], matchColor.val[3], singlePointColor.val[0], singlePointColor.val[1], singlePointColor.val[2], singlePointColor.val[3]);
    }

    public static void drawMatchesKnn(Mat img1, MatOfKeyPoint keypoints1, Mat img2, MatOfKeyPoint keypoints2, List<MatOfDMatch> matches1to2, Mat outImg, Scalar matchColor) {
        MatOfKeyPoint keypoints1_mat = keypoints1;
        MatOfKeyPoint keypoints2_mat = keypoints2;
        ArrayList<Mat> matches1to2_tmplm = new ArrayList<Mat>(matches1to2 != null ? matches1to2.size() : 0);
        Mat matches1to2_mat = Converters.vector_vector_DMatch_to_Mat(matches1to2, matches1to2_tmplm);
        Features2d.drawMatchesKnn_3(img1.nativeObj, keypoints1_mat.nativeObj, img2.nativeObj, keypoints2_mat.nativeObj, matches1to2_mat.nativeObj, outImg.nativeObj, matchColor.val[0], matchColor.val[1], matchColor.val[2], matchColor.val[3]);
    }

    public static void drawMatchesKnn(Mat img1, MatOfKeyPoint keypoints1, Mat img2, MatOfKeyPoint keypoints2, List<MatOfDMatch> matches1to2, Mat outImg) {
        MatOfKeyPoint keypoints1_mat = keypoints1;
        MatOfKeyPoint keypoints2_mat = keypoints2;
        ArrayList<Mat> matches1to2_tmplm = new ArrayList<Mat>(matches1to2 != null ? matches1to2.size() : 0);
        Mat matches1to2_mat = Converters.vector_vector_DMatch_to_Mat(matches1to2, matches1to2_tmplm);
        Features2d.drawMatchesKnn_4(img1.nativeObj, keypoints1_mat.nativeObj, img2.nativeObj, keypoints2_mat.nativeObj, matches1to2_mat.nativeObj, outImg.nativeObj);
    }

    private static native void drawKeypoints_0(long var0, long var2, long var4, double var6, double var8, double var10, double var12, int var14);

    private static native void drawKeypoints_1(long var0, long var2, long var4, double var6, double var8, double var10, double var12);

    private static native void drawKeypoints_2(long var0, long var2, long var4);

    private static native void drawMatches_0(long var0, long var2, long var4, long var6, long var8, long var10, double var12, double var14, double var16, double var18, double var20, double var22, double var24, double var26, long var28, int var30);

    private static native void drawMatches_1(long var0, long var2, long var4, long var6, long var8, long var10, double var12, double var14, double var16, double var18, double var20, double var22, double var24, double var26, long var28);

    private static native void drawMatches_2(long var0, long var2, long var4, long var6, long var8, long var10, double var12, double var14, double var16, double var18, double var20, double var22, double var24, double var26);

    private static native void drawMatches_3(long var0, long var2, long var4, long var6, long var8, long var10, double var12, double var14, double var16, double var18);

    private static native void drawMatches_4(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native void drawMatchesKnn_0(long var0, long var2, long var4, long var6, long var8, long var10, double var12, double var14, double var16, double var18, double var20, double var22, double var24, double var26, long var28, int var30);

    private static native void drawMatchesKnn_1(long var0, long var2, long var4, long var6, long var8, long var10, double var12, double var14, double var16, double var18, double var20, double var22, double var24, double var26, long var28);

    private static native void drawMatchesKnn_2(long var0, long var2, long var4, long var6, long var8, long var10, double var12, double var14, double var16, double var18, double var20, double var22, double var24, double var26);

    private static native void drawMatchesKnn_3(long var0, long var2, long var4, long var6, long var8, long var10, double var12, double var14, double var16, double var18);

    private static native void drawMatchesKnn_4(long var0, long var2, long var4, long var6, long var8, long var10);
}

