/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.utils.Converters;
import org.opencv.video.BackgroundSubtractorKNN;
import org.opencv.video.BackgroundSubtractorMOG2;

public class Video {
    private static final int CV_LKFLOW_INITIAL_GUESSES = 4;
    private static final int CV_LKFLOW_GET_MIN_EIGENVALS = 8;
    public static final int OPTFLOW_USE_INITIAL_FLOW = 4;
    public static final int OPTFLOW_LK_GET_MIN_EIGENVALS = 8;
    public static final int OPTFLOW_FARNEBACK_GAUSSIAN = 256;
    public static final int MOTION_TRANSLATION = 0;
    public static final int MOTION_EUCLIDEAN = 1;
    public static final int MOTION_AFFINE = 2;
    public static final int MOTION_HOMOGRAPHY = 3;
    public static final int TrackerSamplerCSC_MODE_INIT_POS = 1;
    public static final int TrackerSamplerCSC_MODE_INIT_NEG = 2;
    public static final int TrackerSamplerCSC_MODE_TRACK_POS = 3;
    public static final int TrackerSamplerCSC_MODE_TRACK_NEG = 4;
    public static final int TrackerSamplerCSC_MODE_DETECT = 5;

    public static BackgroundSubtractorMOG2 createBackgroundSubtractorMOG2(int history, double varThreshold, boolean detectShadows) {
        return BackgroundSubtractorMOG2.__fromPtr__(Video.createBackgroundSubtractorMOG2_0(history, varThreshold, detectShadows));
    }

    public static BackgroundSubtractorMOG2 createBackgroundSubtractorMOG2(int history, double varThreshold) {
        return BackgroundSubtractorMOG2.__fromPtr__(Video.createBackgroundSubtractorMOG2_1(history, varThreshold));
    }

    public static BackgroundSubtractorMOG2 createBackgroundSubtractorMOG2(int history) {
        return BackgroundSubtractorMOG2.__fromPtr__(Video.createBackgroundSubtractorMOG2_2(history));
    }

    public static BackgroundSubtractorMOG2 createBackgroundSubtractorMOG2() {
        return BackgroundSubtractorMOG2.__fromPtr__(Video.createBackgroundSubtractorMOG2_3());
    }

    public static BackgroundSubtractorKNN createBackgroundSubtractorKNN(int history, double dist2Threshold, boolean detectShadows) {
        return BackgroundSubtractorKNN.__fromPtr__(Video.createBackgroundSubtractorKNN_0(history, dist2Threshold, detectShadows));
    }

    public static BackgroundSubtractorKNN createBackgroundSubtractorKNN(int history, double dist2Threshold) {
        return BackgroundSubtractorKNN.__fromPtr__(Video.createBackgroundSubtractorKNN_1(history, dist2Threshold));
    }

    public static BackgroundSubtractorKNN createBackgroundSubtractorKNN(int history) {
        return BackgroundSubtractorKNN.__fromPtr__(Video.createBackgroundSubtractorKNN_2(history));
    }

    public static BackgroundSubtractorKNN createBackgroundSubtractorKNN() {
        return BackgroundSubtractorKNN.__fromPtr__(Video.createBackgroundSubtractorKNN_3());
    }

    public static RotatedRect CamShift(Mat probImage, Rect window, TermCriteria criteria) {
        double[] window_out = new double[4];
        RotatedRect retVal = new RotatedRect(Video.CamShift_0(probImage.nativeObj, window.x, window.y, window.width, window.height, window_out, criteria.type, criteria.maxCount, criteria.epsilon));
        if (window != null) {
            window.x = (int)window_out[0];
            window.y = (int)window_out[1];
            window.width = (int)window_out[2];
            window.height = (int)window_out[3];
        }
        return retVal;
    }

    public static int meanShift(Mat probImage, Rect window, TermCriteria criteria) {
        double[] window_out = new double[4];
        int retVal = Video.meanShift_0(probImage.nativeObj, window.x, window.y, window.width, window.height, window_out, criteria.type, criteria.maxCount, criteria.epsilon);
        if (window != null) {
            window.x = (int)window_out[0];
            window.y = (int)window_out[1];
            window.width = (int)window_out[2];
            window.height = (int)window_out[3];
        }
        return retVal;
    }

    public static int buildOpticalFlowPyramid(Mat img, List<Mat> pyramid, Size winSize, int maxLevel, boolean withDerivatives, int pyrBorder, int derivBorder, boolean tryReuseInputImage) {
        Mat pyramid_mat = new Mat();
        int retVal = Video.buildOpticalFlowPyramid_0(img.nativeObj, pyramid_mat.nativeObj, winSize.width, winSize.height, maxLevel, withDerivatives, pyrBorder, derivBorder, tryReuseInputImage);
        Converters.Mat_to_vector_Mat(pyramid_mat, pyramid);
        pyramid_mat.release();
        return retVal;
    }

    public static int buildOpticalFlowPyramid(Mat img, List<Mat> pyramid, Size winSize, int maxLevel, boolean withDerivatives, int pyrBorder, int derivBorder) {
        Mat pyramid_mat = new Mat();
        int retVal = Video.buildOpticalFlowPyramid_1(img.nativeObj, pyramid_mat.nativeObj, winSize.width, winSize.height, maxLevel, withDerivatives, pyrBorder, derivBorder);
        Converters.Mat_to_vector_Mat(pyramid_mat, pyramid);
        pyramid_mat.release();
        return retVal;
    }

    public static int buildOpticalFlowPyramid(Mat img, List<Mat> pyramid, Size winSize, int maxLevel, boolean withDerivatives, int pyrBorder) {
        Mat pyramid_mat = new Mat();
        int retVal = Video.buildOpticalFlowPyramid_2(img.nativeObj, pyramid_mat.nativeObj, winSize.width, winSize.height, maxLevel, withDerivatives, pyrBorder);
        Converters.Mat_to_vector_Mat(pyramid_mat, pyramid);
        pyramid_mat.release();
        return retVal;
    }

    public static int buildOpticalFlowPyramid(Mat img, List<Mat> pyramid, Size winSize, int maxLevel, boolean withDerivatives) {
        Mat pyramid_mat = new Mat();
        int retVal = Video.buildOpticalFlowPyramid_3(img.nativeObj, pyramid_mat.nativeObj, winSize.width, winSize.height, maxLevel, withDerivatives);
        Converters.Mat_to_vector_Mat(pyramid_mat, pyramid);
        pyramid_mat.release();
        return retVal;
    }

    public static int buildOpticalFlowPyramid(Mat img, List<Mat> pyramid, Size winSize, int maxLevel) {
        Mat pyramid_mat = new Mat();
        int retVal = Video.buildOpticalFlowPyramid_4(img.nativeObj, pyramid_mat.nativeObj, winSize.width, winSize.height, maxLevel);
        Converters.Mat_to_vector_Mat(pyramid_mat, pyramid);
        pyramid_mat.release();
        return retVal;
    }

    public static void calcOpticalFlowPyrLK(Mat prevImg, Mat nextImg, MatOfPoint2f prevPts, MatOfPoint2f nextPts, MatOfByte status, MatOfFloat err, Size winSize, int maxLevel, TermCriteria criteria, int flags, double minEigThreshold) {
        MatOfPoint2f prevPts_mat = prevPts;
        MatOfPoint2f nextPts_mat = nextPts;
        MatOfByte status_mat = status;
        MatOfFloat err_mat = err;
        Video.calcOpticalFlowPyrLK_0(prevImg.nativeObj, nextImg.nativeObj, prevPts_mat.nativeObj, nextPts_mat.nativeObj, status_mat.nativeObj, err_mat.nativeObj, winSize.width, winSize.height, maxLevel, criteria.type, criteria.maxCount, criteria.epsilon, flags, minEigThreshold);
    }

    public static void calcOpticalFlowPyrLK(Mat prevImg, Mat nextImg, MatOfPoint2f prevPts, MatOfPoint2f nextPts, MatOfByte status, MatOfFloat err, Size winSize, int maxLevel, TermCriteria criteria, int flags) {
        MatOfPoint2f prevPts_mat = prevPts;
        MatOfPoint2f nextPts_mat = nextPts;
        MatOfByte status_mat = status;
        MatOfFloat err_mat = err;
        Video.calcOpticalFlowPyrLK_1(prevImg.nativeObj, nextImg.nativeObj, prevPts_mat.nativeObj, nextPts_mat.nativeObj, status_mat.nativeObj, err_mat.nativeObj, winSize.width, winSize.height, maxLevel, criteria.type, criteria.maxCount, criteria.epsilon, flags);
    }

    public static void calcOpticalFlowPyrLK(Mat prevImg, Mat nextImg, MatOfPoint2f prevPts, MatOfPoint2f nextPts, MatOfByte status, MatOfFloat err, Size winSize, int maxLevel, TermCriteria criteria) {
        MatOfPoint2f prevPts_mat = prevPts;
        MatOfPoint2f nextPts_mat = nextPts;
        MatOfByte status_mat = status;
        MatOfFloat err_mat = err;
        Video.calcOpticalFlowPyrLK_2(prevImg.nativeObj, nextImg.nativeObj, prevPts_mat.nativeObj, nextPts_mat.nativeObj, status_mat.nativeObj, err_mat.nativeObj, winSize.width, winSize.height, maxLevel, criteria.type, criteria.maxCount, criteria.epsilon);
    }

    public static void calcOpticalFlowPyrLK(Mat prevImg, Mat nextImg, MatOfPoint2f prevPts, MatOfPoint2f nextPts, MatOfByte status, MatOfFloat err, Size winSize, int maxLevel) {
        MatOfPoint2f prevPts_mat = prevPts;
        MatOfPoint2f nextPts_mat = nextPts;
        MatOfByte status_mat = status;
        MatOfFloat err_mat = err;
        Video.calcOpticalFlowPyrLK_3(prevImg.nativeObj, nextImg.nativeObj, prevPts_mat.nativeObj, nextPts_mat.nativeObj, status_mat.nativeObj, err_mat.nativeObj, winSize.width, winSize.height, maxLevel);
    }

    public static void calcOpticalFlowPyrLK(Mat prevImg, Mat nextImg, MatOfPoint2f prevPts, MatOfPoint2f nextPts, MatOfByte status, MatOfFloat err, Size winSize) {
        MatOfPoint2f prevPts_mat = prevPts;
        MatOfPoint2f nextPts_mat = nextPts;
        MatOfByte status_mat = status;
        MatOfFloat err_mat = err;
        Video.calcOpticalFlowPyrLK_4(prevImg.nativeObj, nextImg.nativeObj, prevPts_mat.nativeObj, nextPts_mat.nativeObj, status_mat.nativeObj, err_mat.nativeObj, winSize.width, winSize.height);
    }

    public static void calcOpticalFlowPyrLK(Mat prevImg, Mat nextImg, MatOfPoint2f prevPts, MatOfPoint2f nextPts, MatOfByte status, MatOfFloat err) {
        MatOfPoint2f prevPts_mat = prevPts;
        MatOfPoint2f nextPts_mat = nextPts;
        MatOfByte status_mat = status;
        MatOfFloat err_mat = err;
        Video.calcOpticalFlowPyrLK_5(prevImg.nativeObj, nextImg.nativeObj, prevPts_mat.nativeObj, nextPts_mat.nativeObj, status_mat.nativeObj, err_mat.nativeObj);
    }

    public static void calcOpticalFlowFarneback(Mat prev, Mat next, Mat flow, double pyr_scale, int levels, int winsize, int iterations, int poly_n, double poly_sigma, int flags) {
        Video.calcOpticalFlowFarneback_0(prev.nativeObj, next.nativeObj, flow.nativeObj, pyr_scale, levels, winsize, iterations, poly_n, poly_sigma, flags);
    }

    public static double computeECC(Mat templateImage, Mat inputImage, Mat inputMask) {
        return Video.computeECC_0(templateImage.nativeObj, inputImage.nativeObj, inputMask.nativeObj);
    }

    public static double computeECC(Mat templateImage, Mat inputImage) {
        return Video.computeECC_1(templateImage.nativeObj, inputImage.nativeObj);
    }

    public static double findTransformECC(Mat templateImage, Mat inputImage, Mat warpMatrix, int motionType, TermCriteria criteria, Mat inputMask, int gaussFiltSize) {
        return Video.findTransformECC_0(templateImage.nativeObj, inputImage.nativeObj, warpMatrix.nativeObj, motionType, criteria.type, criteria.maxCount, criteria.epsilon, inputMask.nativeObj, gaussFiltSize);
    }

    public static double findTransformECC(Mat templateImage, Mat inputImage, Mat warpMatrix, int motionType, TermCriteria criteria, Mat inputMask) {
        return Video.findTransformECC_1(templateImage.nativeObj, inputImage.nativeObj, warpMatrix.nativeObj, motionType, criteria.type, criteria.maxCount, criteria.epsilon, inputMask.nativeObj);
    }

    public static double findTransformECC(Mat templateImage, Mat inputImage, Mat warpMatrix, int motionType, TermCriteria criteria) {
        return Video.findTransformECC_2(templateImage.nativeObj, inputImage.nativeObj, warpMatrix.nativeObj, motionType, criteria.type, criteria.maxCount, criteria.epsilon);
    }

    public static double findTransformECC(Mat templateImage, Mat inputImage, Mat warpMatrix, int motionType) {
        return Video.findTransformECC_3(templateImage.nativeObj, inputImage.nativeObj, warpMatrix.nativeObj, motionType);
    }

    public static double findTransformECC(Mat templateImage, Mat inputImage, Mat warpMatrix) {
        return Video.findTransformECC_4(templateImage.nativeObj, inputImage.nativeObj, warpMatrix.nativeObj);
    }

    public static Mat readOpticalFlow(String path) {
        return new Mat(Video.readOpticalFlow_0(path));
    }

    public static boolean writeOpticalFlow(String path, Mat flow) {
        return Video.writeOpticalFlow_0(path, flow.nativeObj);
    }

    private static native long createBackgroundSubtractorMOG2_0(int var0, double var1, boolean var3);

    private static native long createBackgroundSubtractorMOG2_1(int var0, double var1);

    private static native long createBackgroundSubtractorMOG2_2(int var0);

    private static native long createBackgroundSubtractorMOG2_3();

    private static native long createBackgroundSubtractorKNN_0(int var0, double var1, boolean var3);

    private static native long createBackgroundSubtractorKNN_1(int var0, double var1);

    private static native long createBackgroundSubtractorKNN_2(int var0);

    private static native long createBackgroundSubtractorKNN_3();

    private static native double[] CamShift_0(long var0, int var2, int var3, int var4, int var5, double[] var6, int var7, int var8, double var9);

    private static native int meanShift_0(long var0, int var2, int var3, int var4, int var5, double[] var6, int var7, int var8, double var9);

    private static native int buildOpticalFlowPyramid_0(long var0, long var2, double var4, double var6, int var8, boolean var9, int var10, int var11, boolean var12);

    private static native int buildOpticalFlowPyramid_1(long var0, long var2, double var4, double var6, int var8, boolean var9, int var10, int var11);

    private static native int buildOpticalFlowPyramid_2(long var0, long var2, double var4, double var6, int var8, boolean var9, int var10);

    private static native int buildOpticalFlowPyramid_3(long var0, long var2, double var4, double var6, int var8, boolean var9);

    private static native int buildOpticalFlowPyramid_4(long var0, long var2, double var4, double var6, int var8);

    private static native void calcOpticalFlowPyrLK_0(long var0, long var2, long var4, long var6, long var8, long var10, double var12, double var14, int var16, int var17, int var18, double var19, int var21, double var22);

    private static native void calcOpticalFlowPyrLK_1(long var0, long var2, long var4, long var6, long var8, long var10, double var12, double var14, int var16, int var17, int var18, double var19, int var21);

    private static native void calcOpticalFlowPyrLK_2(long var0, long var2, long var4, long var6, long var8, long var10, double var12, double var14, int var16, int var17, int var18, double var19);

    private static native void calcOpticalFlowPyrLK_3(long var0, long var2, long var4, long var6, long var8, long var10, double var12, double var14, int var16);

    private static native void calcOpticalFlowPyrLK_4(long var0, long var2, long var4, long var6, long var8, long var10, double var12, double var14);

    private static native void calcOpticalFlowPyrLK_5(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native void calcOpticalFlowFarneback_0(long var0, long var2, long var4, double var6, int var8, int var9, int var10, int var11, double var12, int var14);

    private static native double computeECC_0(long var0, long var2, long var4);

    private static native double computeECC_1(long var0, long var2);

    private static native double findTransformECC_0(long var0, long var2, long var4, int var6, int var7, int var8, double var9, long var11, int var13);

    private static native double findTransformECC_1(long var0, long var2, long var4, int var6, int var7, int var8, double var9, long var11);

    private static native double findTransformECC_2(long var0, long var2, long var4, int var6, int var7, int var8, double var9);

    private static native double findTransformECC_3(long var0, long var2, long var4, int var6);

    private static native double findTransformECC_4(long var0, long var2, long var4);

    private static native long readOpticalFlow_0(String var0);

    private static native boolean writeOpticalFlow_0(String var0, long var1);
}

