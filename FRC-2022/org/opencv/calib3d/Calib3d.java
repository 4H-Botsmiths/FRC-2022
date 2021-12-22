/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.calib3d;

import java.util.ArrayList;
import java.util.List;
import org.opencv.calib3d.UsacParams;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.utils.Converters;

public class Calib3d {
    public static final int CV_ITERATIVE = 0;
    public static final int CV_EPNP = 1;
    public static final int CV_P3P = 2;
    public static final int CV_DLS = 3;
    public static final int CvLevMarq_DONE = 0;
    public static final int CvLevMarq_STARTED = 1;
    public static final int CvLevMarq_CALC_J = 2;
    public static final int CvLevMarq_CHECK_ERR = 3;
    public static final int LMEDS = 4;
    public static final int RANSAC = 8;
    public static final int RHO = 16;
    public static final int USAC_DEFAULT = 32;
    public static final int USAC_PARALLEL = 33;
    public static final int USAC_FM_8PTS = 34;
    public static final int USAC_FAST = 35;
    public static final int USAC_ACCURATE = 36;
    public static final int USAC_PROSAC = 37;
    public static final int USAC_MAGSAC = 38;
    public static final int CALIB_CB_ADAPTIVE_THRESH = 1;
    public static final int CALIB_CB_NORMALIZE_IMAGE = 2;
    public static final int CALIB_CB_FILTER_QUADS = 4;
    public static final int CALIB_CB_FAST_CHECK = 8;
    public static final int CALIB_CB_EXHAUSTIVE = 16;
    public static final int CALIB_CB_ACCURACY = 32;
    public static final int CALIB_CB_LARGER = 64;
    public static final int CALIB_CB_MARKER = 128;
    public static final int CALIB_CB_SYMMETRIC_GRID = 1;
    public static final int CALIB_CB_ASYMMETRIC_GRID = 2;
    public static final int CALIB_CB_CLUSTERING = 4;
    public static final int CALIB_NINTRINSIC = 18;
    public static final int CALIB_USE_INTRINSIC_GUESS = 1;
    public static final int CALIB_FIX_ASPECT_RATIO = 2;
    public static final int CALIB_FIX_PRINCIPAL_POINT = 4;
    public static final int CALIB_ZERO_TANGENT_DIST = 8;
    public static final int CALIB_FIX_FOCAL_LENGTH = 16;
    public static final int CALIB_FIX_K1 = 32;
    public static final int CALIB_FIX_K2 = 64;
    public static final int CALIB_FIX_K3 = 128;
    public static final int CALIB_FIX_K4 = 2048;
    public static final int CALIB_FIX_K5 = 4096;
    public static final int CALIB_FIX_K6 = 8192;
    public static final int CALIB_RATIONAL_MODEL = 16384;
    public static final int CALIB_THIN_PRISM_MODEL = 32768;
    public static final int CALIB_FIX_S1_S2_S3_S4 = 65536;
    public static final int CALIB_TILTED_MODEL = 262144;
    public static final int CALIB_FIX_TAUX_TAUY = 524288;
    public static final int CALIB_USE_QR = 0x100000;
    public static final int CALIB_FIX_TANGENT_DIST = 0x200000;
    public static final int CALIB_FIX_INTRINSIC = 256;
    public static final int CALIB_SAME_FOCAL_LENGTH = 512;
    public static final int CALIB_ZERO_DISPARITY = 1024;
    public static final int CALIB_USE_LU = 131072;
    public static final int CALIB_USE_EXTRINSIC_GUESS = 0x400000;
    public static final int FM_7POINT = 1;
    public static final int FM_8POINT = 2;
    public static final int FM_LMEDS = 4;
    public static final int FM_RANSAC = 8;
    public static final int fisheye_CALIB_USE_INTRINSIC_GUESS = 1;
    public static final int fisheye_CALIB_RECOMPUTE_EXTRINSIC = 2;
    public static final int fisheye_CALIB_CHECK_COND = 4;
    public static final int fisheye_CALIB_FIX_SKEW = 8;
    public static final int fisheye_CALIB_FIX_K1 = 16;
    public static final int fisheye_CALIB_FIX_K2 = 32;
    public static final int fisheye_CALIB_FIX_K3 = 64;
    public static final int fisheye_CALIB_FIX_K4 = 128;
    public static final int fisheye_CALIB_FIX_INTRINSIC = 256;
    public static final int fisheye_CALIB_FIX_PRINCIPAL_POINT = 512;
    public static final int fisheye_CALIB_ZERO_DISPARITY = 1024;
    public static final int fisheye_CALIB_FIX_FOCAL_LENGTH = 2048;
    public static final int CirclesGridFinderParameters_SYMMETRIC_GRID = 0;
    public static final int CirclesGridFinderParameters_ASYMMETRIC_GRID = 1;
    public static final int CALIB_HAND_EYE_TSAI = 0;
    public static final int CALIB_HAND_EYE_PARK = 1;
    public static final int CALIB_HAND_EYE_HORAUD = 2;
    public static final int CALIB_HAND_EYE_ANDREFF = 3;
    public static final int CALIB_HAND_EYE_DANIILIDIS = 4;
    public static final int LOCAL_OPTIM_NULL = 0;
    public static final int LOCAL_OPTIM_INNER_LO = 1;
    public static final int LOCAL_OPTIM_INNER_AND_ITER_LO = 2;
    public static final int LOCAL_OPTIM_GC = 3;
    public static final int LOCAL_OPTIM_SIGMA = 4;
    public static final int NEIGH_FLANN_KNN = 0;
    public static final int NEIGH_GRID = 1;
    public static final int NEIGH_FLANN_RADIUS = 2;
    public static final int CALIB_ROBOT_WORLD_HAND_EYE_SHAH = 0;
    public static final int CALIB_ROBOT_WORLD_HAND_EYE_LI = 1;
    public static final int SAMPLING_UNIFORM = 0;
    public static final int SAMPLING_PROGRESSIVE_NAPSAC = 1;
    public static final int SAMPLING_NAPSAC = 2;
    public static final int SAMPLING_PROSAC = 3;
    public static final int SCORE_METHOD_RANSAC = 0;
    public static final int SCORE_METHOD_MSAC = 1;
    public static final int SCORE_METHOD_MAGSAC = 2;
    public static final int SCORE_METHOD_LMEDS = 3;
    public static final int SOLVEPNP_ITERATIVE = 0;
    public static final int SOLVEPNP_EPNP = 1;
    public static final int SOLVEPNP_P3P = 2;
    public static final int SOLVEPNP_DLS = 3;
    public static final int SOLVEPNP_UPNP = 4;
    public static final int SOLVEPNP_AP3P = 5;
    public static final int SOLVEPNP_IPPE = 6;
    public static final int SOLVEPNP_IPPE_SQUARE = 7;
    public static final int SOLVEPNP_SQPNP = 8;
    public static final int SOLVEPNP_MAX_COUNT = 9;
    public static final int PROJ_SPHERICAL_ORTHO = 0;
    public static final int PROJ_SPHERICAL_EQRECT = 1;

    public static void Rodrigues(Mat src, Mat dst, Mat jacobian) {
        Calib3d.Rodrigues_0(src.nativeObj, dst.nativeObj, jacobian.nativeObj);
    }

    public static void Rodrigues(Mat src, Mat dst) {
        Calib3d.Rodrigues_1(src.nativeObj, dst.nativeObj);
    }

    public static Mat findHomography(MatOfPoint2f srcPoints, MatOfPoint2f dstPoints, int method, double ransacReprojThreshold, Mat mask, int maxIters, double confidence) {
        MatOfPoint2f srcPoints_mat = srcPoints;
        MatOfPoint2f dstPoints_mat = dstPoints;
        return new Mat(Calib3d.findHomography_0(srcPoints_mat.nativeObj, dstPoints_mat.nativeObj, method, ransacReprojThreshold, mask.nativeObj, maxIters, confidence));
    }

    public static Mat findHomography(MatOfPoint2f srcPoints, MatOfPoint2f dstPoints, int method, double ransacReprojThreshold, Mat mask, int maxIters) {
        MatOfPoint2f srcPoints_mat = srcPoints;
        MatOfPoint2f dstPoints_mat = dstPoints;
        return new Mat(Calib3d.findHomography_1(srcPoints_mat.nativeObj, dstPoints_mat.nativeObj, method, ransacReprojThreshold, mask.nativeObj, maxIters));
    }

    public static Mat findHomography(MatOfPoint2f srcPoints, MatOfPoint2f dstPoints, int method, double ransacReprojThreshold, Mat mask) {
        MatOfPoint2f srcPoints_mat = srcPoints;
        MatOfPoint2f dstPoints_mat = dstPoints;
        return new Mat(Calib3d.findHomography_2(srcPoints_mat.nativeObj, dstPoints_mat.nativeObj, method, ransacReprojThreshold, mask.nativeObj));
    }

    public static Mat findHomography(MatOfPoint2f srcPoints, MatOfPoint2f dstPoints, int method, double ransacReprojThreshold) {
        MatOfPoint2f srcPoints_mat = srcPoints;
        MatOfPoint2f dstPoints_mat = dstPoints;
        return new Mat(Calib3d.findHomography_3(srcPoints_mat.nativeObj, dstPoints_mat.nativeObj, method, ransacReprojThreshold));
    }

    public static Mat findHomography(MatOfPoint2f srcPoints, MatOfPoint2f dstPoints, int method) {
        MatOfPoint2f srcPoints_mat = srcPoints;
        MatOfPoint2f dstPoints_mat = dstPoints;
        return new Mat(Calib3d.findHomography_4(srcPoints_mat.nativeObj, dstPoints_mat.nativeObj, method));
    }

    public static Mat findHomography(MatOfPoint2f srcPoints, MatOfPoint2f dstPoints) {
        MatOfPoint2f srcPoints_mat = srcPoints;
        MatOfPoint2f dstPoints_mat = dstPoints;
        return new Mat(Calib3d.findHomography_5(srcPoints_mat.nativeObj, dstPoints_mat.nativeObj));
    }

    public static Mat findHomography(MatOfPoint2f srcPoints, MatOfPoint2f dstPoints, Mat mask, UsacParams params) {
        MatOfPoint2f srcPoints_mat = srcPoints;
        MatOfPoint2f dstPoints_mat = dstPoints;
        return new Mat(Calib3d.findHomography_6(srcPoints_mat.nativeObj, dstPoints_mat.nativeObj, mask.nativeObj, params.nativeObj));
    }

    public static double[] RQDecomp3x3(Mat src, Mat mtxR, Mat mtxQ, Mat Qx, Mat Qy, Mat Qz) {
        return Calib3d.RQDecomp3x3_0(src.nativeObj, mtxR.nativeObj, mtxQ.nativeObj, Qx.nativeObj, Qy.nativeObj, Qz.nativeObj);
    }

    public static double[] RQDecomp3x3(Mat src, Mat mtxR, Mat mtxQ, Mat Qx, Mat Qy) {
        return Calib3d.RQDecomp3x3_1(src.nativeObj, mtxR.nativeObj, mtxQ.nativeObj, Qx.nativeObj, Qy.nativeObj);
    }

    public static double[] RQDecomp3x3(Mat src, Mat mtxR, Mat mtxQ, Mat Qx) {
        return Calib3d.RQDecomp3x3_2(src.nativeObj, mtxR.nativeObj, mtxQ.nativeObj, Qx.nativeObj);
    }

    public static double[] RQDecomp3x3(Mat src, Mat mtxR, Mat mtxQ) {
        return Calib3d.RQDecomp3x3_3(src.nativeObj, mtxR.nativeObj, mtxQ.nativeObj);
    }

    public static void decomposeProjectionMatrix(Mat projMatrix, Mat cameraMatrix, Mat rotMatrix, Mat transVect, Mat rotMatrixX, Mat rotMatrixY, Mat rotMatrixZ, Mat eulerAngles) {
        Calib3d.decomposeProjectionMatrix_0(projMatrix.nativeObj, cameraMatrix.nativeObj, rotMatrix.nativeObj, transVect.nativeObj, rotMatrixX.nativeObj, rotMatrixY.nativeObj, rotMatrixZ.nativeObj, eulerAngles.nativeObj);
    }

    public static void decomposeProjectionMatrix(Mat projMatrix, Mat cameraMatrix, Mat rotMatrix, Mat transVect, Mat rotMatrixX, Mat rotMatrixY, Mat rotMatrixZ) {
        Calib3d.decomposeProjectionMatrix_1(projMatrix.nativeObj, cameraMatrix.nativeObj, rotMatrix.nativeObj, transVect.nativeObj, rotMatrixX.nativeObj, rotMatrixY.nativeObj, rotMatrixZ.nativeObj);
    }

    public static void decomposeProjectionMatrix(Mat projMatrix, Mat cameraMatrix, Mat rotMatrix, Mat transVect, Mat rotMatrixX, Mat rotMatrixY) {
        Calib3d.decomposeProjectionMatrix_2(projMatrix.nativeObj, cameraMatrix.nativeObj, rotMatrix.nativeObj, transVect.nativeObj, rotMatrixX.nativeObj, rotMatrixY.nativeObj);
    }

    public static void decomposeProjectionMatrix(Mat projMatrix, Mat cameraMatrix, Mat rotMatrix, Mat transVect, Mat rotMatrixX) {
        Calib3d.decomposeProjectionMatrix_3(projMatrix.nativeObj, cameraMatrix.nativeObj, rotMatrix.nativeObj, transVect.nativeObj, rotMatrixX.nativeObj);
    }

    public static void decomposeProjectionMatrix(Mat projMatrix, Mat cameraMatrix, Mat rotMatrix, Mat transVect) {
        Calib3d.decomposeProjectionMatrix_4(projMatrix.nativeObj, cameraMatrix.nativeObj, rotMatrix.nativeObj, transVect.nativeObj);
    }

    public static void matMulDeriv(Mat A, Mat B, Mat dABdA, Mat dABdB) {
        Calib3d.matMulDeriv_0(A.nativeObj, B.nativeObj, dABdA.nativeObj, dABdB.nativeObj);
    }

    public static void composeRT(Mat rvec1, Mat tvec1, Mat rvec2, Mat tvec2, Mat rvec3, Mat tvec3, Mat dr3dr1, Mat dr3dt1, Mat dr3dr2, Mat dr3dt2, Mat dt3dr1, Mat dt3dt1, Mat dt3dr2, Mat dt3dt2) {
        Calib3d.composeRT_0(rvec1.nativeObj, tvec1.nativeObj, rvec2.nativeObj, tvec2.nativeObj, rvec3.nativeObj, tvec3.nativeObj, dr3dr1.nativeObj, dr3dt1.nativeObj, dr3dr2.nativeObj, dr3dt2.nativeObj, dt3dr1.nativeObj, dt3dt1.nativeObj, dt3dr2.nativeObj, dt3dt2.nativeObj);
    }

    public static void composeRT(Mat rvec1, Mat tvec1, Mat rvec2, Mat tvec2, Mat rvec3, Mat tvec3, Mat dr3dr1, Mat dr3dt1, Mat dr3dr2, Mat dr3dt2, Mat dt3dr1, Mat dt3dt1, Mat dt3dr2) {
        Calib3d.composeRT_1(rvec1.nativeObj, tvec1.nativeObj, rvec2.nativeObj, tvec2.nativeObj, rvec3.nativeObj, tvec3.nativeObj, dr3dr1.nativeObj, dr3dt1.nativeObj, dr3dr2.nativeObj, dr3dt2.nativeObj, dt3dr1.nativeObj, dt3dt1.nativeObj, dt3dr2.nativeObj);
    }

    public static void composeRT(Mat rvec1, Mat tvec1, Mat rvec2, Mat tvec2, Mat rvec3, Mat tvec3, Mat dr3dr1, Mat dr3dt1, Mat dr3dr2, Mat dr3dt2, Mat dt3dr1, Mat dt3dt1) {
        Calib3d.composeRT_2(rvec1.nativeObj, tvec1.nativeObj, rvec2.nativeObj, tvec2.nativeObj, rvec3.nativeObj, tvec3.nativeObj, dr3dr1.nativeObj, dr3dt1.nativeObj, dr3dr2.nativeObj, dr3dt2.nativeObj, dt3dr1.nativeObj, dt3dt1.nativeObj);
    }

    public static void composeRT(Mat rvec1, Mat tvec1, Mat rvec2, Mat tvec2, Mat rvec3, Mat tvec3, Mat dr3dr1, Mat dr3dt1, Mat dr3dr2, Mat dr3dt2, Mat dt3dr1) {
        Calib3d.composeRT_3(rvec1.nativeObj, tvec1.nativeObj, rvec2.nativeObj, tvec2.nativeObj, rvec3.nativeObj, tvec3.nativeObj, dr3dr1.nativeObj, dr3dt1.nativeObj, dr3dr2.nativeObj, dr3dt2.nativeObj, dt3dr1.nativeObj);
    }

    public static void composeRT(Mat rvec1, Mat tvec1, Mat rvec2, Mat tvec2, Mat rvec3, Mat tvec3, Mat dr3dr1, Mat dr3dt1, Mat dr3dr2, Mat dr3dt2) {
        Calib3d.composeRT_4(rvec1.nativeObj, tvec1.nativeObj, rvec2.nativeObj, tvec2.nativeObj, rvec3.nativeObj, tvec3.nativeObj, dr3dr1.nativeObj, dr3dt1.nativeObj, dr3dr2.nativeObj, dr3dt2.nativeObj);
    }

    public static void composeRT(Mat rvec1, Mat tvec1, Mat rvec2, Mat tvec2, Mat rvec3, Mat tvec3, Mat dr3dr1, Mat dr3dt1, Mat dr3dr2) {
        Calib3d.composeRT_5(rvec1.nativeObj, tvec1.nativeObj, rvec2.nativeObj, tvec2.nativeObj, rvec3.nativeObj, tvec3.nativeObj, dr3dr1.nativeObj, dr3dt1.nativeObj, dr3dr2.nativeObj);
    }

    public static void composeRT(Mat rvec1, Mat tvec1, Mat rvec2, Mat tvec2, Mat rvec3, Mat tvec3, Mat dr3dr1, Mat dr3dt1) {
        Calib3d.composeRT_6(rvec1.nativeObj, tvec1.nativeObj, rvec2.nativeObj, tvec2.nativeObj, rvec3.nativeObj, tvec3.nativeObj, dr3dr1.nativeObj, dr3dt1.nativeObj);
    }

    public static void composeRT(Mat rvec1, Mat tvec1, Mat rvec2, Mat tvec2, Mat rvec3, Mat tvec3, Mat dr3dr1) {
        Calib3d.composeRT_7(rvec1.nativeObj, tvec1.nativeObj, rvec2.nativeObj, tvec2.nativeObj, rvec3.nativeObj, tvec3.nativeObj, dr3dr1.nativeObj);
    }

    public static void composeRT(Mat rvec1, Mat tvec1, Mat rvec2, Mat tvec2, Mat rvec3, Mat tvec3) {
        Calib3d.composeRT_8(rvec1.nativeObj, tvec1.nativeObj, rvec2.nativeObj, tvec2.nativeObj, rvec3.nativeObj, tvec3.nativeObj);
    }

    public static void projectPoints(MatOfPoint3f objectPoints, Mat rvec, Mat tvec, Mat cameraMatrix, MatOfDouble distCoeffs, MatOfPoint2f imagePoints, Mat jacobian, double aspectRatio) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        MatOfPoint2f imagePoints_mat = imagePoints;
        Calib3d.projectPoints_0(objectPoints_mat.nativeObj, rvec.nativeObj, tvec.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, imagePoints_mat.nativeObj, jacobian.nativeObj, aspectRatio);
    }

    public static void projectPoints(MatOfPoint3f objectPoints, Mat rvec, Mat tvec, Mat cameraMatrix, MatOfDouble distCoeffs, MatOfPoint2f imagePoints, Mat jacobian) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        MatOfPoint2f imagePoints_mat = imagePoints;
        Calib3d.projectPoints_1(objectPoints_mat.nativeObj, rvec.nativeObj, tvec.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, imagePoints_mat.nativeObj, jacobian.nativeObj);
    }

    public static void projectPoints(MatOfPoint3f objectPoints, Mat rvec, Mat tvec, Mat cameraMatrix, MatOfDouble distCoeffs, MatOfPoint2f imagePoints) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        MatOfPoint2f imagePoints_mat = imagePoints;
        Calib3d.projectPoints_2(objectPoints_mat.nativeObj, rvec.nativeObj, tvec.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, imagePoints_mat.nativeObj);
    }

    public static boolean solvePnP(MatOfPoint3f objectPoints, MatOfPoint2f imagePoints, Mat cameraMatrix, MatOfDouble distCoeffs, Mat rvec, Mat tvec, boolean useExtrinsicGuess, int flags) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfPoint2f imagePoints_mat = imagePoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        return Calib3d.solvePnP_0(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, rvec.nativeObj, tvec.nativeObj, useExtrinsicGuess, flags);
    }

    public static boolean solvePnP(MatOfPoint3f objectPoints, MatOfPoint2f imagePoints, Mat cameraMatrix, MatOfDouble distCoeffs, Mat rvec, Mat tvec, boolean useExtrinsicGuess) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfPoint2f imagePoints_mat = imagePoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        return Calib3d.solvePnP_1(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, rvec.nativeObj, tvec.nativeObj, useExtrinsicGuess);
    }

    public static boolean solvePnP(MatOfPoint3f objectPoints, MatOfPoint2f imagePoints, Mat cameraMatrix, MatOfDouble distCoeffs, Mat rvec, Mat tvec) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfPoint2f imagePoints_mat = imagePoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        return Calib3d.solvePnP_2(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, rvec.nativeObj, tvec.nativeObj);
    }

    public static boolean solvePnPRansac(MatOfPoint3f objectPoints, MatOfPoint2f imagePoints, Mat cameraMatrix, MatOfDouble distCoeffs, Mat rvec, Mat tvec, boolean useExtrinsicGuess, int iterationsCount, float reprojectionError, double confidence, Mat inliers, int flags) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfPoint2f imagePoints_mat = imagePoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        return Calib3d.solvePnPRansac_0(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, rvec.nativeObj, tvec.nativeObj, useExtrinsicGuess, iterationsCount, reprojectionError, confidence, inliers.nativeObj, flags);
    }

    public static boolean solvePnPRansac(MatOfPoint3f objectPoints, MatOfPoint2f imagePoints, Mat cameraMatrix, MatOfDouble distCoeffs, Mat rvec, Mat tvec, boolean useExtrinsicGuess, int iterationsCount, float reprojectionError, double confidence, Mat inliers) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfPoint2f imagePoints_mat = imagePoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        return Calib3d.solvePnPRansac_1(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, rvec.nativeObj, tvec.nativeObj, useExtrinsicGuess, iterationsCount, reprojectionError, confidence, inliers.nativeObj);
    }

    public static boolean solvePnPRansac(MatOfPoint3f objectPoints, MatOfPoint2f imagePoints, Mat cameraMatrix, MatOfDouble distCoeffs, Mat rvec, Mat tvec, boolean useExtrinsicGuess, int iterationsCount, float reprojectionError, double confidence) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfPoint2f imagePoints_mat = imagePoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        return Calib3d.solvePnPRansac_2(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, rvec.nativeObj, tvec.nativeObj, useExtrinsicGuess, iterationsCount, reprojectionError, confidence);
    }

    public static boolean solvePnPRansac(MatOfPoint3f objectPoints, MatOfPoint2f imagePoints, Mat cameraMatrix, MatOfDouble distCoeffs, Mat rvec, Mat tvec, boolean useExtrinsicGuess, int iterationsCount, float reprojectionError) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfPoint2f imagePoints_mat = imagePoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        return Calib3d.solvePnPRansac_3(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, rvec.nativeObj, tvec.nativeObj, useExtrinsicGuess, iterationsCount, reprojectionError);
    }

    public static boolean solvePnPRansac(MatOfPoint3f objectPoints, MatOfPoint2f imagePoints, Mat cameraMatrix, MatOfDouble distCoeffs, Mat rvec, Mat tvec, boolean useExtrinsicGuess, int iterationsCount) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfPoint2f imagePoints_mat = imagePoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        return Calib3d.solvePnPRansac_4(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, rvec.nativeObj, tvec.nativeObj, useExtrinsicGuess, iterationsCount);
    }

    public static boolean solvePnPRansac(MatOfPoint3f objectPoints, MatOfPoint2f imagePoints, Mat cameraMatrix, MatOfDouble distCoeffs, Mat rvec, Mat tvec, boolean useExtrinsicGuess) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfPoint2f imagePoints_mat = imagePoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        return Calib3d.solvePnPRansac_5(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, rvec.nativeObj, tvec.nativeObj, useExtrinsicGuess);
    }

    public static boolean solvePnPRansac(MatOfPoint3f objectPoints, MatOfPoint2f imagePoints, Mat cameraMatrix, MatOfDouble distCoeffs, Mat rvec, Mat tvec) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfPoint2f imagePoints_mat = imagePoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        return Calib3d.solvePnPRansac_6(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, rvec.nativeObj, tvec.nativeObj);
    }

    public static boolean solvePnPRansac(MatOfPoint3f objectPoints, MatOfPoint2f imagePoints, Mat cameraMatrix, MatOfDouble distCoeffs, Mat rvec, Mat tvec, Mat inliers, UsacParams params) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfPoint2f imagePoints_mat = imagePoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        return Calib3d.solvePnPRansac_7(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, rvec.nativeObj, tvec.nativeObj, inliers.nativeObj, params.nativeObj);
    }

    public static boolean solvePnPRansac(MatOfPoint3f objectPoints, MatOfPoint2f imagePoints, Mat cameraMatrix, MatOfDouble distCoeffs, Mat rvec, Mat tvec, Mat inliers) {
        MatOfPoint3f objectPoints_mat = objectPoints;
        MatOfPoint2f imagePoints_mat = imagePoints;
        MatOfDouble distCoeffs_mat = distCoeffs;
        return Calib3d.solvePnPRansac_8(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs_mat.nativeObj, rvec.nativeObj, tvec.nativeObj, inliers.nativeObj);
    }

    public static int solveP3P(Mat objectPoints, Mat imagePoints, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, int flags) {
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        int retVal = Calib3d.solveP3P_0(objectPoints.nativeObj, imagePoints.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, flags);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static void solvePnPRefineLM(Mat objectPoints, Mat imagePoints, Mat cameraMatrix, Mat distCoeffs, Mat rvec, Mat tvec, TermCriteria criteria) {
        Calib3d.solvePnPRefineLM_0(objectPoints.nativeObj, imagePoints.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvec.nativeObj, tvec.nativeObj, criteria.type, criteria.maxCount, criteria.epsilon);
    }

    public static void solvePnPRefineLM(Mat objectPoints, Mat imagePoints, Mat cameraMatrix, Mat distCoeffs, Mat rvec, Mat tvec) {
        Calib3d.solvePnPRefineLM_1(objectPoints.nativeObj, imagePoints.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvec.nativeObj, tvec.nativeObj);
    }

    public static void solvePnPRefineVVS(Mat objectPoints, Mat imagePoints, Mat cameraMatrix, Mat distCoeffs, Mat rvec, Mat tvec, TermCriteria criteria, double VVSlambda) {
        Calib3d.solvePnPRefineVVS_0(objectPoints.nativeObj, imagePoints.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvec.nativeObj, tvec.nativeObj, criteria.type, criteria.maxCount, criteria.epsilon, VVSlambda);
    }

    public static void solvePnPRefineVVS(Mat objectPoints, Mat imagePoints, Mat cameraMatrix, Mat distCoeffs, Mat rvec, Mat tvec, TermCriteria criteria) {
        Calib3d.solvePnPRefineVVS_1(objectPoints.nativeObj, imagePoints.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvec.nativeObj, tvec.nativeObj, criteria.type, criteria.maxCount, criteria.epsilon);
    }

    public static void solvePnPRefineVVS(Mat objectPoints, Mat imagePoints, Mat cameraMatrix, Mat distCoeffs, Mat rvec, Mat tvec) {
        Calib3d.solvePnPRefineVVS_2(objectPoints.nativeObj, imagePoints.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvec.nativeObj, tvec.nativeObj);
    }

    public static int solvePnPGeneric(Mat objectPoints, Mat imagePoints, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, boolean useExtrinsicGuess, int flags, Mat rvec, Mat tvec, Mat reprojectionError) {
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        int retVal = Calib3d.solvePnPGeneric_0(objectPoints.nativeObj, imagePoints.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, useExtrinsicGuess, flags, rvec.nativeObj, tvec.nativeObj, reprojectionError.nativeObj);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static int solvePnPGeneric(Mat objectPoints, Mat imagePoints, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, boolean useExtrinsicGuess, int flags, Mat rvec, Mat tvec) {
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        int retVal = Calib3d.solvePnPGeneric_1(objectPoints.nativeObj, imagePoints.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, useExtrinsicGuess, flags, rvec.nativeObj, tvec.nativeObj);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static int solvePnPGeneric(Mat objectPoints, Mat imagePoints, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, boolean useExtrinsicGuess, int flags, Mat rvec) {
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        int retVal = Calib3d.solvePnPGeneric_2(objectPoints.nativeObj, imagePoints.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, useExtrinsicGuess, flags, rvec.nativeObj);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static int solvePnPGeneric(Mat objectPoints, Mat imagePoints, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, boolean useExtrinsicGuess, int flags) {
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        int retVal = Calib3d.solvePnPGeneric_3(objectPoints.nativeObj, imagePoints.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, useExtrinsicGuess, flags);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static int solvePnPGeneric(Mat objectPoints, Mat imagePoints, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, boolean useExtrinsicGuess) {
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        int retVal = Calib3d.solvePnPGeneric_4(objectPoints.nativeObj, imagePoints.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, useExtrinsicGuess);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static int solvePnPGeneric(Mat objectPoints, Mat imagePoints, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs) {
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        int retVal = Calib3d.solvePnPGeneric_5(objectPoints.nativeObj, imagePoints.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static Mat initCameraMatrix2D(List<MatOfPoint3f> objectPoints, List<MatOfPoint2f> imagePoints, Size imageSize, double aspectRatio) {
        ArrayList<Mat> objectPoints_tmplm = new ArrayList<Mat>(objectPoints != null ? objectPoints.size() : 0);
        Mat objectPoints_mat = Converters.vector_vector_Point3f_to_Mat(objectPoints, objectPoints_tmplm);
        ArrayList<Mat> imagePoints_tmplm = new ArrayList<Mat>(imagePoints != null ? imagePoints.size() : 0);
        Mat imagePoints_mat = Converters.vector_vector_Point2f_to_Mat(imagePoints, imagePoints_tmplm);
        return new Mat(Calib3d.initCameraMatrix2D_0(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, imageSize.width, imageSize.height, aspectRatio));
    }

    public static Mat initCameraMatrix2D(List<MatOfPoint3f> objectPoints, List<MatOfPoint2f> imagePoints, Size imageSize) {
        ArrayList<Mat> objectPoints_tmplm = new ArrayList<Mat>(objectPoints != null ? objectPoints.size() : 0);
        Mat objectPoints_mat = Converters.vector_vector_Point3f_to_Mat(objectPoints, objectPoints_tmplm);
        ArrayList<Mat> imagePoints_tmplm = new ArrayList<Mat>(imagePoints != null ? imagePoints.size() : 0);
        Mat imagePoints_mat = Converters.vector_vector_Point2f_to_Mat(imagePoints, imagePoints_tmplm);
        return new Mat(Calib3d.initCameraMatrix2D_1(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, imageSize.width, imageSize.height));
    }

    public static boolean findChessboardCorners(Mat image, Size patternSize, MatOfPoint2f corners, int flags) {
        MatOfPoint2f corners_mat = corners;
        return Calib3d.findChessboardCorners_0(image.nativeObj, patternSize.width, patternSize.height, corners_mat.nativeObj, flags);
    }

    public static boolean findChessboardCorners(Mat image, Size patternSize, MatOfPoint2f corners) {
        MatOfPoint2f corners_mat = corners;
        return Calib3d.findChessboardCorners_1(image.nativeObj, patternSize.width, patternSize.height, corners_mat.nativeObj);
    }

    public static boolean checkChessboard(Mat img, Size size) {
        return Calib3d.checkChessboard_0(img.nativeObj, size.width, size.height);
    }

    public static boolean findChessboardCornersSBWithMeta(Mat image, Size patternSize, Mat corners, int flags, Mat meta) {
        return Calib3d.findChessboardCornersSBWithMeta_0(image.nativeObj, patternSize.width, patternSize.height, corners.nativeObj, flags, meta.nativeObj);
    }

    public static boolean findChessboardCornersSB(Mat image, Size patternSize, Mat corners, int flags) {
        return Calib3d.findChessboardCornersSB_0(image.nativeObj, patternSize.width, patternSize.height, corners.nativeObj, flags);
    }

    public static boolean findChessboardCornersSB(Mat image, Size patternSize, Mat corners) {
        return Calib3d.findChessboardCornersSB_1(image.nativeObj, patternSize.width, patternSize.height, corners.nativeObj);
    }

    public static Scalar estimateChessboardSharpness(Mat image, Size patternSize, Mat corners, float rise_distance, boolean vertical, Mat sharpness) {
        return new Scalar(Calib3d.estimateChessboardSharpness_0(image.nativeObj, patternSize.width, patternSize.height, corners.nativeObj, rise_distance, vertical, sharpness.nativeObj));
    }

    public static Scalar estimateChessboardSharpness(Mat image, Size patternSize, Mat corners, float rise_distance, boolean vertical) {
        return new Scalar(Calib3d.estimateChessboardSharpness_1(image.nativeObj, patternSize.width, patternSize.height, corners.nativeObj, rise_distance, vertical));
    }

    public static Scalar estimateChessboardSharpness(Mat image, Size patternSize, Mat corners, float rise_distance) {
        return new Scalar(Calib3d.estimateChessboardSharpness_2(image.nativeObj, patternSize.width, patternSize.height, corners.nativeObj, rise_distance));
    }

    public static Scalar estimateChessboardSharpness(Mat image, Size patternSize, Mat corners) {
        return new Scalar(Calib3d.estimateChessboardSharpness_3(image.nativeObj, patternSize.width, patternSize.height, corners.nativeObj));
    }

    public static boolean find4QuadCornerSubpix(Mat img, Mat corners, Size region_size) {
        return Calib3d.find4QuadCornerSubpix_0(img.nativeObj, corners.nativeObj, region_size.width, region_size.height);
    }

    public static void drawChessboardCorners(Mat image, Size patternSize, MatOfPoint2f corners, boolean patternWasFound) {
        MatOfPoint2f corners_mat = corners;
        Calib3d.drawChessboardCorners_0(image.nativeObj, patternSize.width, patternSize.height, corners_mat.nativeObj, patternWasFound);
    }

    public static void drawFrameAxes(Mat image, Mat cameraMatrix, Mat distCoeffs, Mat rvec, Mat tvec, float length, int thickness) {
        Calib3d.drawFrameAxes_0(image.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvec.nativeObj, tvec.nativeObj, length, thickness);
    }

    public static void drawFrameAxes(Mat image, Mat cameraMatrix, Mat distCoeffs, Mat rvec, Mat tvec, float length) {
        Calib3d.drawFrameAxes_1(image.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvec.nativeObj, tvec.nativeObj, length);
    }

    public static boolean findCirclesGrid(Mat image, Size patternSize, Mat centers, int flags) {
        return Calib3d.findCirclesGrid_0(image.nativeObj, patternSize.width, patternSize.height, centers.nativeObj, flags);
    }

    public static boolean findCirclesGrid(Mat image, Size patternSize, Mat centers) {
        return Calib3d.findCirclesGrid_2(image.nativeObj, patternSize.width, patternSize.height, centers.nativeObj);
    }

    public static double calibrateCameraExtended(List<Mat> objectPoints, List<Mat> imagePoints, Size imageSize, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, Mat stdDeviationsIntrinsics, Mat stdDeviationsExtrinsics, Mat perViewErrors, int flags, TermCriteria criteria) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.calibrateCameraExtended_0(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, imageSize.width, imageSize.height, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, stdDeviationsIntrinsics.nativeObj, stdDeviationsExtrinsics.nativeObj, perViewErrors.nativeObj, flags, criteria.type, criteria.maxCount, criteria.epsilon);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static double calibrateCameraExtended(List<Mat> objectPoints, List<Mat> imagePoints, Size imageSize, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, Mat stdDeviationsIntrinsics, Mat stdDeviationsExtrinsics, Mat perViewErrors, int flags) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.calibrateCameraExtended_1(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, imageSize.width, imageSize.height, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, stdDeviationsIntrinsics.nativeObj, stdDeviationsExtrinsics.nativeObj, perViewErrors.nativeObj, flags);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static double calibrateCameraExtended(List<Mat> objectPoints, List<Mat> imagePoints, Size imageSize, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, Mat stdDeviationsIntrinsics, Mat stdDeviationsExtrinsics, Mat perViewErrors) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.calibrateCameraExtended_2(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, imageSize.width, imageSize.height, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, stdDeviationsIntrinsics.nativeObj, stdDeviationsExtrinsics.nativeObj, perViewErrors.nativeObj);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static double calibrateCamera(List<Mat> objectPoints, List<Mat> imagePoints, Size imageSize, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, int flags, TermCriteria criteria) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.calibrateCamera_0(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, imageSize.width, imageSize.height, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, flags, criteria.type, criteria.maxCount, criteria.epsilon);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static double calibrateCamera(List<Mat> objectPoints, List<Mat> imagePoints, Size imageSize, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, int flags) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.calibrateCamera_1(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, imageSize.width, imageSize.height, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, flags);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static double calibrateCamera(List<Mat> objectPoints, List<Mat> imagePoints, Size imageSize, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.calibrateCamera_2(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, imageSize.width, imageSize.height, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static double calibrateCameraROExtended(List<Mat> objectPoints, List<Mat> imagePoints, Size imageSize, int iFixedPoint, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, Mat newObjPoints, Mat stdDeviationsIntrinsics, Mat stdDeviationsExtrinsics, Mat stdDeviationsObjPoints, Mat perViewErrors, int flags, TermCriteria criteria) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.calibrateCameraROExtended_0(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, imageSize.width, imageSize.height, iFixedPoint, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, newObjPoints.nativeObj, stdDeviationsIntrinsics.nativeObj, stdDeviationsExtrinsics.nativeObj, stdDeviationsObjPoints.nativeObj, perViewErrors.nativeObj, flags, criteria.type, criteria.maxCount, criteria.epsilon);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static double calibrateCameraROExtended(List<Mat> objectPoints, List<Mat> imagePoints, Size imageSize, int iFixedPoint, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, Mat newObjPoints, Mat stdDeviationsIntrinsics, Mat stdDeviationsExtrinsics, Mat stdDeviationsObjPoints, Mat perViewErrors, int flags) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.calibrateCameraROExtended_1(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, imageSize.width, imageSize.height, iFixedPoint, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, newObjPoints.nativeObj, stdDeviationsIntrinsics.nativeObj, stdDeviationsExtrinsics.nativeObj, stdDeviationsObjPoints.nativeObj, perViewErrors.nativeObj, flags);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static double calibrateCameraROExtended(List<Mat> objectPoints, List<Mat> imagePoints, Size imageSize, int iFixedPoint, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, Mat newObjPoints, Mat stdDeviationsIntrinsics, Mat stdDeviationsExtrinsics, Mat stdDeviationsObjPoints, Mat perViewErrors) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.calibrateCameraROExtended_2(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, imageSize.width, imageSize.height, iFixedPoint, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, newObjPoints.nativeObj, stdDeviationsIntrinsics.nativeObj, stdDeviationsExtrinsics.nativeObj, stdDeviationsObjPoints.nativeObj, perViewErrors.nativeObj);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static double calibrateCameraRO(List<Mat> objectPoints, List<Mat> imagePoints, Size imageSize, int iFixedPoint, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, Mat newObjPoints, int flags, TermCriteria criteria) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.calibrateCameraRO_0(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, imageSize.width, imageSize.height, iFixedPoint, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, newObjPoints.nativeObj, flags, criteria.type, criteria.maxCount, criteria.epsilon);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static double calibrateCameraRO(List<Mat> objectPoints, List<Mat> imagePoints, Size imageSize, int iFixedPoint, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, Mat newObjPoints, int flags) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.calibrateCameraRO_1(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, imageSize.width, imageSize.height, iFixedPoint, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, newObjPoints.nativeObj, flags);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static double calibrateCameraRO(List<Mat> objectPoints, List<Mat> imagePoints, Size imageSize, int iFixedPoint, Mat cameraMatrix, Mat distCoeffs, List<Mat> rvecs, List<Mat> tvecs, Mat newObjPoints) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.calibrateCameraRO_2(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, imageSize.width, imageSize.height, iFixedPoint, cameraMatrix.nativeObj, distCoeffs.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, newObjPoints.nativeObj);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static void calibrationMatrixValues(Mat cameraMatrix, Size imageSize, double apertureWidth, double apertureHeight, double[] fovx, double[] fovy, double[] focalLength, Point principalPoint, double[] aspectRatio) {
        double[] fovx_out = new double[1];
        double[] fovy_out = new double[1];
        double[] focalLength_out = new double[1];
        double[] principalPoint_out = new double[2];
        double[] aspectRatio_out = new double[1];
        Calib3d.calibrationMatrixValues_0(cameraMatrix.nativeObj, imageSize.width, imageSize.height, apertureWidth, apertureHeight, fovx_out, fovy_out, focalLength_out, principalPoint_out, aspectRatio_out);
        if (fovx != null) {
            fovx[0] = fovx_out[0];
        }
        if (fovy != null) {
            fovy[0] = fovy_out[0];
        }
        if (focalLength != null) {
            focalLength[0] = focalLength_out[0];
        }
        if (principalPoint != null) {
            principalPoint.x = principalPoint_out[0];
            principalPoint.y = principalPoint_out[1];
        }
        if (aspectRatio != null) {
            aspectRatio[0] = aspectRatio_out[0];
        }
    }

    public static double stereoCalibrateExtended(List<Mat> objectPoints, List<Mat> imagePoints1, List<Mat> imagePoints2, Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, Size imageSize, Mat R, Mat T, Mat E, Mat F, Mat perViewErrors, int flags, TermCriteria criteria) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints1_mat = Converters.vector_Mat_to_Mat(imagePoints1);
        Mat imagePoints2_mat = Converters.vector_Mat_to_Mat(imagePoints2);
        return Calib3d.stereoCalibrateExtended_0(objectPoints_mat.nativeObj, imagePoints1_mat.nativeObj, imagePoints2_mat.nativeObj, cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj, E.nativeObj, F.nativeObj, perViewErrors.nativeObj, flags, criteria.type, criteria.maxCount, criteria.epsilon);
    }

    public static double stereoCalibrateExtended(List<Mat> objectPoints, List<Mat> imagePoints1, List<Mat> imagePoints2, Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, Size imageSize, Mat R, Mat T, Mat E, Mat F, Mat perViewErrors, int flags) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints1_mat = Converters.vector_Mat_to_Mat(imagePoints1);
        Mat imagePoints2_mat = Converters.vector_Mat_to_Mat(imagePoints2);
        return Calib3d.stereoCalibrateExtended_1(objectPoints_mat.nativeObj, imagePoints1_mat.nativeObj, imagePoints2_mat.nativeObj, cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj, E.nativeObj, F.nativeObj, perViewErrors.nativeObj, flags);
    }

    public static double stereoCalibrateExtended(List<Mat> objectPoints, List<Mat> imagePoints1, List<Mat> imagePoints2, Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, Size imageSize, Mat R, Mat T, Mat E, Mat F, Mat perViewErrors) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints1_mat = Converters.vector_Mat_to_Mat(imagePoints1);
        Mat imagePoints2_mat = Converters.vector_Mat_to_Mat(imagePoints2);
        return Calib3d.stereoCalibrateExtended_2(objectPoints_mat.nativeObj, imagePoints1_mat.nativeObj, imagePoints2_mat.nativeObj, cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj, E.nativeObj, F.nativeObj, perViewErrors.nativeObj);
    }

    public static double stereoCalibrate(List<Mat> objectPoints, List<Mat> imagePoints1, List<Mat> imagePoints2, Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, Size imageSize, Mat R, Mat T, Mat E, Mat F, int flags, TermCriteria criteria) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints1_mat = Converters.vector_Mat_to_Mat(imagePoints1);
        Mat imagePoints2_mat = Converters.vector_Mat_to_Mat(imagePoints2);
        return Calib3d.stereoCalibrate_0(objectPoints_mat.nativeObj, imagePoints1_mat.nativeObj, imagePoints2_mat.nativeObj, cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj, E.nativeObj, F.nativeObj, flags, criteria.type, criteria.maxCount, criteria.epsilon);
    }

    public static double stereoCalibrate(List<Mat> objectPoints, List<Mat> imagePoints1, List<Mat> imagePoints2, Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, Size imageSize, Mat R, Mat T, Mat E, Mat F, int flags) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints1_mat = Converters.vector_Mat_to_Mat(imagePoints1);
        Mat imagePoints2_mat = Converters.vector_Mat_to_Mat(imagePoints2);
        return Calib3d.stereoCalibrate_1(objectPoints_mat.nativeObj, imagePoints1_mat.nativeObj, imagePoints2_mat.nativeObj, cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj, E.nativeObj, F.nativeObj, flags);
    }

    public static double stereoCalibrate(List<Mat> objectPoints, List<Mat> imagePoints1, List<Mat> imagePoints2, Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, Size imageSize, Mat R, Mat T, Mat E, Mat F) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints1_mat = Converters.vector_Mat_to_Mat(imagePoints1);
        Mat imagePoints2_mat = Converters.vector_Mat_to_Mat(imagePoints2);
        return Calib3d.stereoCalibrate_2(objectPoints_mat.nativeObj, imagePoints1_mat.nativeObj, imagePoints2_mat.nativeObj, cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj, E.nativeObj, F.nativeObj);
    }

    public static void stereoRectify(Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, Size imageSize, Mat R, Mat T, Mat R1, Mat R2, Mat P1, Mat P2, Mat Q, int flags, double alpha, Size newImageSize, Rect validPixROI1, Rect validPixROI2) {
        double[] validPixROI1_out = new double[4];
        double[] validPixROI2_out = new double[4];
        Calib3d.stereoRectify_0(cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj, R1.nativeObj, R2.nativeObj, P1.nativeObj, P2.nativeObj, Q.nativeObj, flags, alpha, newImageSize.width, newImageSize.height, validPixROI1_out, validPixROI2_out);
        if (validPixROI1 != null) {
            validPixROI1.x = (int)validPixROI1_out[0];
            validPixROI1.y = (int)validPixROI1_out[1];
            validPixROI1.width = (int)validPixROI1_out[2];
            validPixROI1.height = (int)validPixROI1_out[3];
        }
        if (validPixROI2 != null) {
            validPixROI2.x = (int)validPixROI2_out[0];
            validPixROI2.y = (int)validPixROI2_out[1];
            validPixROI2.width = (int)validPixROI2_out[2];
            validPixROI2.height = (int)validPixROI2_out[3];
        }
    }

    public static void stereoRectify(Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, Size imageSize, Mat R, Mat T, Mat R1, Mat R2, Mat P1, Mat P2, Mat Q, int flags, double alpha, Size newImageSize, Rect validPixROI1) {
        double[] validPixROI1_out = new double[4];
        Calib3d.stereoRectify_1(cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj, R1.nativeObj, R2.nativeObj, P1.nativeObj, P2.nativeObj, Q.nativeObj, flags, alpha, newImageSize.width, newImageSize.height, validPixROI1_out);
        if (validPixROI1 != null) {
            validPixROI1.x = (int)validPixROI1_out[0];
            validPixROI1.y = (int)validPixROI1_out[1];
            validPixROI1.width = (int)validPixROI1_out[2];
            validPixROI1.height = (int)validPixROI1_out[3];
        }
    }

    public static void stereoRectify(Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, Size imageSize, Mat R, Mat T, Mat R1, Mat R2, Mat P1, Mat P2, Mat Q, int flags, double alpha, Size newImageSize) {
        Calib3d.stereoRectify_2(cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj, R1.nativeObj, R2.nativeObj, P1.nativeObj, P2.nativeObj, Q.nativeObj, flags, alpha, newImageSize.width, newImageSize.height);
    }

    public static void stereoRectify(Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, Size imageSize, Mat R, Mat T, Mat R1, Mat R2, Mat P1, Mat P2, Mat Q, int flags, double alpha) {
        Calib3d.stereoRectify_3(cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj, R1.nativeObj, R2.nativeObj, P1.nativeObj, P2.nativeObj, Q.nativeObj, flags, alpha);
    }

    public static void stereoRectify(Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, Size imageSize, Mat R, Mat T, Mat R1, Mat R2, Mat P1, Mat P2, Mat Q, int flags) {
        Calib3d.stereoRectify_4(cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj, R1.nativeObj, R2.nativeObj, P1.nativeObj, P2.nativeObj, Q.nativeObj, flags);
    }

    public static void stereoRectify(Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, Size imageSize, Mat R, Mat T, Mat R1, Mat R2, Mat P1, Mat P2, Mat Q) {
        Calib3d.stereoRectify_5(cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj, R1.nativeObj, R2.nativeObj, P1.nativeObj, P2.nativeObj, Q.nativeObj);
    }

    public static boolean stereoRectifyUncalibrated(Mat points1, Mat points2, Mat F, Size imgSize, Mat H1, Mat H2, double threshold) {
        return Calib3d.stereoRectifyUncalibrated_0(points1.nativeObj, points2.nativeObj, F.nativeObj, imgSize.width, imgSize.height, H1.nativeObj, H2.nativeObj, threshold);
    }

    public static boolean stereoRectifyUncalibrated(Mat points1, Mat points2, Mat F, Size imgSize, Mat H1, Mat H2) {
        return Calib3d.stereoRectifyUncalibrated_1(points1.nativeObj, points2.nativeObj, F.nativeObj, imgSize.width, imgSize.height, H1.nativeObj, H2.nativeObj);
    }

    public static float rectify3Collinear(Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, Mat cameraMatrix3, Mat distCoeffs3, List<Mat> imgpt1, List<Mat> imgpt3, Size imageSize, Mat R12, Mat T12, Mat R13, Mat T13, Mat R1, Mat R2, Mat R3, Mat P1, Mat P2, Mat P3, Mat Q, double alpha, Size newImgSize, Rect roi1, Rect roi2, int flags) {
        Mat imgpt1_mat = Converters.vector_Mat_to_Mat(imgpt1);
        Mat imgpt3_mat = Converters.vector_Mat_to_Mat(imgpt3);
        double[] roi1_out = new double[4];
        double[] roi2_out = new double[4];
        float retVal = Calib3d.rectify3Collinear_0(cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, cameraMatrix3.nativeObj, distCoeffs3.nativeObj, imgpt1_mat.nativeObj, imgpt3_mat.nativeObj, imageSize.width, imageSize.height, R12.nativeObj, T12.nativeObj, R13.nativeObj, T13.nativeObj, R1.nativeObj, R2.nativeObj, R3.nativeObj, P1.nativeObj, P2.nativeObj, P3.nativeObj, Q.nativeObj, alpha, newImgSize.width, newImgSize.height, roi1_out, roi2_out, flags);
        if (roi1 != null) {
            roi1.x = (int)roi1_out[0];
            roi1.y = (int)roi1_out[1];
            roi1.width = (int)roi1_out[2];
            roi1.height = (int)roi1_out[3];
        }
        if (roi2 != null) {
            roi2.x = (int)roi2_out[0];
            roi2.y = (int)roi2_out[1];
            roi2.width = (int)roi2_out[2];
            roi2.height = (int)roi2_out[3];
        }
        return retVal;
    }

    public static Mat getOptimalNewCameraMatrix(Mat cameraMatrix, Mat distCoeffs, Size imageSize, double alpha, Size newImgSize, Rect validPixROI, boolean centerPrincipalPoint) {
        double[] validPixROI_out = new double[4];
        Mat retVal = new Mat(Calib3d.getOptimalNewCameraMatrix_0(cameraMatrix.nativeObj, distCoeffs.nativeObj, imageSize.width, imageSize.height, alpha, newImgSize.width, newImgSize.height, validPixROI_out, centerPrincipalPoint));
        if (validPixROI != null) {
            validPixROI.x = (int)validPixROI_out[0];
            validPixROI.y = (int)validPixROI_out[1];
            validPixROI.width = (int)validPixROI_out[2];
            validPixROI.height = (int)validPixROI_out[3];
        }
        return retVal;
    }

    public static Mat getOptimalNewCameraMatrix(Mat cameraMatrix, Mat distCoeffs, Size imageSize, double alpha, Size newImgSize, Rect validPixROI) {
        double[] validPixROI_out = new double[4];
        Mat retVal = new Mat(Calib3d.getOptimalNewCameraMatrix_1(cameraMatrix.nativeObj, distCoeffs.nativeObj, imageSize.width, imageSize.height, alpha, newImgSize.width, newImgSize.height, validPixROI_out));
        if (validPixROI != null) {
            validPixROI.x = (int)validPixROI_out[0];
            validPixROI.y = (int)validPixROI_out[1];
            validPixROI.width = (int)validPixROI_out[2];
            validPixROI.height = (int)validPixROI_out[3];
        }
        return retVal;
    }

    public static Mat getOptimalNewCameraMatrix(Mat cameraMatrix, Mat distCoeffs, Size imageSize, double alpha, Size newImgSize) {
        return new Mat(Calib3d.getOptimalNewCameraMatrix_2(cameraMatrix.nativeObj, distCoeffs.nativeObj, imageSize.width, imageSize.height, alpha, newImgSize.width, newImgSize.height));
    }

    public static Mat getOptimalNewCameraMatrix(Mat cameraMatrix, Mat distCoeffs, Size imageSize, double alpha) {
        return new Mat(Calib3d.getOptimalNewCameraMatrix_3(cameraMatrix.nativeObj, distCoeffs.nativeObj, imageSize.width, imageSize.height, alpha));
    }

    public static void calibrateHandEye(List<Mat> R_gripper2base, List<Mat> t_gripper2base, List<Mat> R_target2cam, List<Mat> t_target2cam, Mat R_cam2gripper, Mat t_cam2gripper, int method) {
        Mat R_gripper2base_mat = Converters.vector_Mat_to_Mat(R_gripper2base);
        Mat t_gripper2base_mat = Converters.vector_Mat_to_Mat(t_gripper2base);
        Mat R_target2cam_mat = Converters.vector_Mat_to_Mat(R_target2cam);
        Mat t_target2cam_mat = Converters.vector_Mat_to_Mat(t_target2cam);
        Calib3d.calibrateHandEye_0(R_gripper2base_mat.nativeObj, t_gripper2base_mat.nativeObj, R_target2cam_mat.nativeObj, t_target2cam_mat.nativeObj, R_cam2gripper.nativeObj, t_cam2gripper.nativeObj, method);
    }

    public static void calibrateHandEye(List<Mat> R_gripper2base, List<Mat> t_gripper2base, List<Mat> R_target2cam, List<Mat> t_target2cam, Mat R_cam2gripper, Mat t_cam2gripper) {
        Mat R_gripper2base_mat = Converters.vector_Mat_to_Mat(R_gripper2base);
        Mat t_gripper2base_mat = Converters.vector_Mat_to_Mat(t_gripper2base);
        Mat R_target2cam_mat = Converters.vector_Mat_to_Mat(R_target2cam);
        Mat t_target2cam_mat = Converters.vector_Mat_to_Mat(t_target2cam);
        Calib3d.calibrateHandEye_1(R_gripper2base_mat.nativeObj, t_gripper2base_mat.nativeObj, R_target2cam_mat.nativeObj, t_target2cam_mat.nativeObj, R_cam2gripper.nativeObj, t_cam2gripper.nativeObj);
    }

    public static void calibrateRobotWorldHandEye(List<Mat> R_world2cam, List<Mat> t_world2cam, List<Mat> R_base2gripper, List<Mat> t_base2gripper, Mat R_base2world, Mat t_base2world, Mat R_gripper2cam, Mat t_gripper2cam, int method) {
        Mat R_world2cam_mat = Converters.vector_Mat_to_Mat(R_world2cam);
        Mat t_world2cam_mat = Converters.vector_Mat_to_Mat(t_world2cam);
        Mat R_base2gripper_mat = Converters.vector_Mat_to_Mat(R_base2gripper);
        Mat t_base2gripper_mat = Converters.vector_Mat_to_Mat(t_base2gripper);
        Calib3d.calibrateRobotWorldHandEye_0(R_world2cam_mat.nativeObj, t_world2cam_mat.nativeObj, R_base2gripper_mat.nativeObj, t_base2gripper_mat.nativeObj, R_base2world.nativeObj, t_base2world.nativeObj, R_gripper2cam.nativeObj, t_gripper2cam.nativeObj, method);
    }

    public static void calibrateRobotWorldHandEye(List<Mat> R_world2cam, List<Mat> t_world2cam, List<Mat> R_base2gripper, List<Mat> t_base2gripper, Mat R_base2world, Mat t_base2world, Mat R_gripper2cam, Mat t_gripper2cam) {
        Mat R_world2cam_mat = Converters.vector_Mat_to_Mat(R_world2cam);
        Mat t_world2cam_mat = Converters.vector_Mat_to_Mat(t_world2cam);
        Mat R_base2gripper_mat = Converters.vector_Mat_to_Mat(R_base2gripper);
        Mat t_base2gripper_mat = Converters.vector_Mat_to_Mat(t_base2gripper);
        Calib3d.calibrateRobotWorldHandEye_1(R_world2cam_mat.nativeObj, t_world2cam_mat.nativeObj, R_base2gripper_mat.nativeObj, t_base2gripper_mat.nativeObj, R_base2world.nativeObj, t_base2world.nativeObj, R_gripper2cam.nativeObj, t_gripper2cam.nativeObj);
    }

    public static void convertPointsToHomogeneous(Mat src, Mat dst) {
        Calib3d.convertPointsToHomogeneous_0(src.nativeObj, dst.nativeObj);
    }

    public static void convertPointsFromHomogeneous(Mat src, Mat dst) {
        Calib3d.convertPointsFromHomogeneous_0(src.nativeObj, dst.nativeObj);
    }

    public static Mat findFundamentalMat(MatOfPoint2f points1, MatOfPoint2f points2, int method, double ransacReprojThreshold, double confidence, int maxIters, Mat mask) {
        MatOfPoint2f points1_mat = points1;
        MatOfPoint2f points2_mat = points2;
        return new Mat(Calib3d.findFundamentalMat_0(points1_mat.nativeObj, points2_mat.nativeObj, method, ransacReprojThreshold, confidence, maxIters, mask.nativeObj));
    }

    public static Mat findFundamentalMat(MatOfPoint2f points1, MatOfPoint2f points2, int method, double ransacReprojThreshold, double confidence, int maxIters) {
        MatOfPoint2f points1_mat = points1;
        MatOfPoint2f points2_mat = points2;
        return new Mat(Calib3d.findFundamentalMat_1(points1_mat.nativeObj, points2_mat.nativeObj, method, ransacReprojThreshold, confidence, maxIters));
    }

    public static Mat findFundamentalMat(MatOfPoint2f points1, MatOfPoint2f points2, int method, double ransacReprojThreshold, double confidence, Mat mask) {
        MatOfPoint2f points1_mat = points1;
        MatOfPoint2f points2_mat = points2;
        return new Mat(Calib3d.findFundamentalMat_2(points1_mat.nativeObj, points2_mat.nativeObj, method, ransacReprojThreshold, confidence, mask.nativeObj));
    }

    public static Mat findFundamentalMat(MatOfPoint2f points1, MatOfPoint2f points2, int method, double ransacReprojThreshold, double confidence) {
        MatOfPoint2f points1_mat = points1;
        MatOfPoint2f points2_mat = points2;
        return new Mat(Calib3d.findFundamentalMat_3(points1_mat.nativeObj, points2_mat.nativeObj, method, ransacReprojThreshold, confidence));
    }

    public static Mat findFundamentalMat(MatOfPoint2f points1, MatOfPoint2f points2, int method, double ransacReprojThreshold) {
        MatOfPoint2f points1_mat = points1;
        MatOfPoint2f points2_mat = points2;
        return new Mat(Calib3d.findFundamentalMat_4(points1_mat.nativeObj, points2_mat.nativeObj, method, ransacReprojThreshold));
    }

    public static Mat findFundamentalMat(MatOfPoint2f points1, MatOfPoint2f points2, int method) {
        MatOfPoint2f points1_mat = points1;
        MatOfPoint2f points2_mat = points2;
        return new Mat(Calib3d.findFundamentalMat_5(points1_mat.nativeObj, points2_mat.nativeObj, method));
    }

    public static Mat findFundamentalMat(MatOfPoint2f points1, MatOfPoint2f points2) {
        MatOfPoint2f points1_mat = points1;
        MatOfPoint2f points2_mat = points2;
        return new Mat(Calib3d.findFundamentalMat_6(points1_mat.nativeObj, points2_mat.nativeObj));
    }

    public static Mat findFundamentalMat(MatOfPoint2f points1, MatOfPoint2f points2, Mat mask, UsacParams params) {
        MatOfPoint2f points1_mat = points1;
        MatOfPoint2f points2_mat = points2;
        return new Mat(Calib3d.findFundamentalMat_7(points1_mat.nativeObj, points2_mat.nativeObj, mask.nativeObj, params.nativeObj));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, Mat cameraMatrix, int method, double prob, double threshold, Mat mask) {
        return new Mat(Calib3d.findEssentialMat_0(points1.nativeObj, points2.nativeObj, cameraMatrix.nativeObj, method, prob, threshold, mask.nativeObj));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, Mat cameraMatrix, int method, double prob, double threshold) {
        return new Mat(Calib3d.findEssentialMat_1(points1.nativeObj, points2.nativeObj, cameraMatrix.nativeObj, method, prob, threshold));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, Mat cameraMatrix, int method, double prob) {
        return new Mat(Calib3d.findEssentialMat_2(points1.nativeObj, points2.nativeObj, cameraMatrix.nativeObj, method, prob));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, Mat cameraMatrix, int method) {
        return new Mat(Calib3d.findEssentialMat_3(points1.nativeObj, points2.nativeObj, cameraMatrix.nativeObj, method));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, Mat cameraMatrix) {
        return new Mat(Calib3d.findEssentialMat_4(points1.nativeObj, points2.nativeObj, cameraMatrix.nativeObj));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, double focal, Point pp, int method, double prob, double threshold, Mat mask) {
        return new Mat(Calib3d.findEssentialMat_5(points1.nativeObj, points2.nativeObj, focal, pp.x, pp.y, method, prob, threshold, mask.nativeObj));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, double focal, Point pp, int method, double prob, double threshold) {
        return new Mat(Calib3d.findEssentialMat_6(points1.nativeObj, points2.nativeObj, focal, pp.x, pp.y, method, prob, threshold));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, double focal, Point pp, int method, double prob) {
        return new Mat(Calib3d.findEssentialMat_7(points1.nativeObj, points2.nativeObj, focal, pp.x, pp.y, method, prob));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, double focal, Point pp, int method) {
        return new Mat(Calib3d.findEssentialMat_8(points1.nativeObj, points2.nativeObj, focal, pp.x, pp.y, method));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, double focal, Point pp) {
        return new Mat(Calib3d.findEssentialMat_9(points1.nativeObj, points2.nativeObj, focal, pp.x, pp.y));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, double focal) {
        return new Mat(Calib3d.findEssentialMat_10(points1.nativeObj, points2.nativeObj, focal));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2) {
        return new Mat(Calib3d.findEssentialMat_11(points1.nativeObj, points2.nativeObj));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, int method, double prob, double threshold, Mat mask) {
        return new Mat(Calib3d.findEssentialMat_12(points1.nativeObj, points2.nativeObj, cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, method, prob, threshold, mask.nativeObj));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, int method, double prob, double threshold) {
        return new Mat(Calib3d.findEssentialMat_13(points1.nativeObj, points2.nativeObj, cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, method, prob, threshold));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, int method, double prob) {
        return new Mat(Calib3d.findEssentialMat_14(points1.nativeObj, points2.nativeObj, cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, method, prob));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2, int method) {
        return new Mat(Calib3d.findEssentialMat_15(points1.nativeObj, points2.nativeObj, cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj, method));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, Mat cameraMatrix1, Mat distCoeffs1, Mat cameraMatrix2, Mat distCoeffs2) {
        return new Mat(Calib3d.findEssentialMat_16(points1.nativeObj, points2.nativeObj, cameraMatrix1.nativeObj, distCoeffs1.nativeObj, cameraMatrix2.nativeObj, distCoeffs2.nativeObj));
    }

    public static Mat findEssentialMat(Mat points1, Mat points2, Mat cameraMatrix1, Mat cameraMatrix2, Mat dist_coeff1, Mat dist_coeff2, Mat mask, UsacParams params) {
        return new Mat(Calib3d.findEssentialMat_17(points1.nativeObj, points2.nativeObj, cameraMatrix1.nativeObj, cameraMatrix2.nativeObj, dist_coeff1.nativeObj, dist_coeff2.nativeObj, mask.nativeObj, params.nativeObj));
    }

    public static void decomposeEssentialMat(Mat E, Mat R1, Mat R2, Mat t) {
        Calib3d.decomposeEssentialMat_0(E.nativeObj, R1.nativeObj, R2.nativeObj, t.nativeObj);
    }

    public static int recoverPose(Mat E, Mat points1, Mat points2, Mat cameraMatrix, Mat R, Mat t, Mat mask) {
        return Calib3d.recoverPose_0(E.nativeObj, points1.nativeObj, points2.nativeObj, cameraMatrix.nativeObj, R.nativeObj, t.nativeObj, mask.nativeObj);
    }

    public static int recoverPose(Mat E, Mat points1, Mat points2, Mat cameraMatrix, Mat R, Mat t) {
        return Calib3d.recoverPose_1(E.nativeObj, points1.nativeObj, points2.nativeObj, cameraMatrix.nativeObj, R.nativeObj, t.nativeObj);
    }

    public static int recoverPose(Mat E, Mat points1, Mat points2, Mat R, Mat t, double focal, Point pp, Mat mask) {
        return Calib3d.recoverPose_2(E.nativeObj, points1.nativeObj, points2.nativeObj, R.nativeObj, t.nativeObj, focal, pp.x, pp.y, mask.nativeObj);
    }

    public static int recoverPose(Mat E, Mat points1, Mat points2, Mat R, Mat t, double focal, Point pp) {
        return Calib3d.recoverPose_3(E.nativeObj, points1.nativeObj, points2.nativeObj, R.nativeObj, t.nativeObj, focal, pp.x, pp.y);
    }

    public static int recoverPose(Mat E, Mat points1, Mat points2, Mat R, Mat t, double focal) {
        return Calib3d.recoverPose_4(E.nativeObj, points1.nativeObj, points2.nativeObj, R.nativeObj, t.nativeObj, focal);
    }

    public static int recoverPose(Mat E, Mat points1, Mat points2, Mat R, Mat t) {
        return Calib3d.recoverPose_5(E.nativeObj, points1.nativeObj, points2.nativeObj, R.nativeObj, t.nativeObj);
    }

    public static int recoverPose(Mat E, Mat points1, Mat points2, Mat cameraMatrix, Mat R, Mat t, double distanceThresh, Mat mask, Mat triangulatedPoints) {
        return Calib3d.recoverPose_6(E.nativeObj, points1.nativeObj, points2.nativeObj, cameraMatrix.nativeObj, R.nativeObj, t.nativeObj, distanceThresh, mask.nativeObj, triangulatedPoints.nativeObj);
    }

    public static int recoverPose(Mat E, Mat points1, Mat points2, Mat cameraMatrix, Mat R, Mat t, double distanceThresh, Mat mask) {
        return Calib3d.recoverPose_7(E.nativeObj, points1.nativeObj, points2.nativeObj, cameraMatrix.nativeObj, R.nativeObj, t.nativeObj, distanceThresh, mask.nativeObj);
    }

    public static int recoverPose(Mat E, Mat points1, Mat points2, Mat cameraMatrix, Mat R, Mat t, double distanceThresh) {
        return Calib3d.recoverPose_8(E.nativeObj, points1.nativeObj, points2.nativeObj, cameraMatrix.nativeObj, R.nativeObj, t.nativeObj, distanceThresh);
    }

    public static void computeCorrespondEpilines(Mat points, int whichImage, Mat F, Mat lines) {
        Calib3d.computeCorrespondEpilines_0(points.nativeObj, whichImage, F.nativeObj, lines.nativeObj);
    }

    public static void triangulatePoints(Mat projMatr1, Mat projMatr2, Mat projPoints1, Mat projPoints2, Mat points4D) {
        Calib3d.triangulatePoints_0(projMatr1.nativeObj, projMatr2.nativeObj, projPoints1.nativeObj, projPoints2.nativeObj, points4D.nativeObj);
    }

    public static void correctMatches(Mat F, Mat points1, Mat points2, Mat newPoints1, Mat newPoints2) {
        Calib3d.correctMatches_0(F.nativeObj, points1.nativeObj, points2.nativeObj, newPoints1.nativeObj, newPoints2.nativeObj);
    }

    public static void filterSpeckles(Mat img, double newVal, int maxSpeckleSize, double maxDiff, Mat buf) {
        Calib3d.filterSpeckles_0(img.nativeObj, newVal, maxSpeckleSize, maxDiff, buf.nativeObj);
    }

    public static void filterSpeckles(Mat img, double newVal, int maxSpeckleSize, double maxDiff) {
        Calib3d.filterSpeckles_1(img.nativeObj, newVal, maxSpeckleSize, maxDiff);
    }

    public static Rect getValidDisparityROI(Rect roi1, Rect roi2, int minDisparity, int numberOfDisparities, int blockSize) {
        return new Rect(Calib3d.getValidDisparityROI_0(roi1.x, roi1.y, roi1.width, roi1.height, roi2.x, roi2.y, roi2.width, roi2.height, minDisparity, numberOfDisparities, blockSize));
    }

    public static void validateDisparity(Mat disparity, Mat cost, int minDisparity, int numberOfDisparities, int disp12MaxDisp) {
        Calib3d.validateDisparity_0(disparity.nativeObj, cost.nativeObj, minDisparity, numberOfDisparities, disp12MaxDisp);
    }

    public static void validateDisparity(Mat disparity, Mat cost, int minDisparity, int numberOfDisparities) {
        Calib3d.validateDisparity_1(disparity.nativeObj, cost.nativeObj, minDisparity, numberOfDisparities);
    }

    public static void reprojectImageTo3D(Mat disparity, Mat _3dImage, Mat Q, boolean handleMissingValues, int ddepth) {
        Calib3d.reprojectImageTo3D_0(disparity.nativeObj, _3dImage.nativeObj, Q.nativeObj, handleMissingValues, ddepth);
    }

    public static void reprojectImageTo3D(Mat disparity, Mat _3dImage, Mat Q, boolean handleMissingValues) {
        Calib3d.reprojectImageTo3D_1(disparity.nativeObj, _3dImage.nativeObj, Q.nativeObj, handleMissingValues);
    }

    public static void reprojectImageTo3D(Mat disparity, Mat _3dImage, Mat Q) {
        Calib3d.reprojectImageTo3D_2(disparity.nativeObj, _3dImage.nativeObj, Q.nativeObj);
    }

    public static double sampsonDistance(Mat pt1, Mat pt2, Mat F) {
        return Calib3d.sampsonDistance_0(pt1.nativeObj, pt2.nativeObj, F.nativeObj);
    }

    public static int estimateAffine3D(Mat src, Mat dst, Mat out, Mat inliers, double ransacThreshold, double confidence) {
        return Calib3d.estimateAffine3D_0(src.nativeObj, dst.nativeObj, out.nativeObj, inliers.nativeObj, ransacThreshold, confidence);
    }

    public static int estimateAffine3D(Mat src, Mat dst, Mat out, Mat inliers, double ransacThreshold) {
        return Calib3d.estimateAffine3D_1(src.nativeObj, dst.nativeObj, out.nativeObj, inliers.nativeObj, ransacThreshold);
    }

    public static int estimateAffine3D(Mat src, Mat dst, Mat out, Mat inliers) {
        return Calib3d.estimateAffine3D_2(src.nativeObj, dst.nativeObj, out.nativeObj, inliers.nativeObj);
    }

    public static int estimateTranslation3D(Mat src, Mat dst, Mat out, Mat inliers, double ransacThreshold, double confidence) {
        return Calib3d.estimateTranslation3D_0(src.nativeObj, dst.nativeObj, out.nativeObj, inliers.nativeObj, ransacThreshold, confidence);
    }

    public static int estimateTranslation3D(Mat src, Mat dst, Mat out, Mat inliers, double ransacThreshold) {
        return Calib3d.estimateTranslation3D_1(src.nativeObj, dst.nativeObj, out.nativeObj, inliers.nativeObj, ransacThreshold);
    }

    public static int estimateTranslation3D(Mat src, Mat dst, Mat out, Mat inliers) {
        return Calib3d.estimateTranslation3D_2(src.nativeObj, dst.nativeObj, out.nativeObj, inliers.nativeObj);
    }

    public static Mat estimateAffine2D(Mat from, Mat to, Mat inliers, int method, double ransacReprojThreshold, long maxIters, double confidence, long refineIters) {
        return new Mat(Calib3d.estimateAffine2D_0(from.nativeObj, to.nativeObj, inliers.nativeObj, method, ransacReprojThreshold, maxIters, confidence, refineIters));
    }

    public static Mat estimateAffine2D(Mat from, Mat to, Mat inliers, int method, double ransacReprojThreshold, long maxIters, double confidence) {
        return new Mat(Calib3d.estimateAffine2D_1(from.nativeObj, to.nativeObj, inliers.nativeObj, method, ransacReprojThreshold, maxIters, confidence));
    }

    public static Mat estimateAffine2D(Mat from, Mat to, Mat inliers, int method, double ransacReprojThreshold, long maxIters) {
        return new Mat(Calib3d.estimateAffine2D_2(from.nativeObj, to.nativeObj, inliers.nativeObj, method, ransacReprojThreshold, maxIters));
    }

    public static Mat estimateAffine2D(Mat from, Mat to, Mat inliers, int method, double ransacReprojThreshold) {
        return new Mat(Calib3d.estimateAffine2D_3(from.nativeObj, to.nativeObj, inliers.nativeObj, method, ransacReprojThreshold));
    }

    public static Mat estimateAffine2D(Mat from, Mat to, Mat inliers, int method) {
        return new Mat(Calib3d.estimateAffine2D_4(from.nativeObj, to.nativeObj, inliers.nativeObj, method));
    }

    public static Mat estimateAffine2D(Mat from, Mat to, Mat inliers) {
        return new Mat(Calib3d.estimateAffine2D_5(from.nativeObj, to.nativeObj, inliers.nativeObj));
    }

    public static Mat estimateAffine2D(Mat from, Mat to) {
        return new Mat(Calib3d.estimateAffine2D_6(from.nativeObj, to.nativeObj));
    }

    public static Mat estimateAffine2D(Mat pts1, Mat pts2, Mat inliers, UsacParams params) {
        return new Mat(Calib3d.estimateAffine2D_7(pts1.nativeObj, pts2.nativeObj, inliers.nativeObj, params.nativeObj));
    }

    public static Mat estimateAffinePartial2D(Mat from, Mat to, Mat inliers, int method, double ransacReprojThreshold, long maxIters, double confidence, long refineIters) {
        return new Mat(Calib3d.estimateAffinePartial2D_0(from.nativeObj, to.nativeObj, inliers.nativeObj, method, ransacReprojThreshold, maxIters, confidence, refineIters));
    }

    public static Mat estimateAffinePartial2D(Mat from, Mat to, Mat inliers, int method, double ransacReprojThreshold, long maxIters, double confidence) {
        return new Mat(Calib3d.estimateAffinePartial2D_1(from.nativeObj, to.nativeObj, inliers.nativeObj, method, ransacReprojThreshold, maxIters, confidence));
    }

    public static Mat estimateAffinePartial2D(Mat from, Mat to, Mat inliers, int method, double ransacReprojThreshold, long maxIters) {
        return new Mat(Calib3d.estimateAffinePartial2D_2(from.nativeObj, to.nativeObj, inliers.nativeObj, method, ransacReprojThreshold, maxIters));
    }

    public static Mat estimateAffinePartial2D(Mat from, Mat to, Mat inliers, int method, double ransacReprojThreshold) {
        return new Mat(Calib3d.estimateAffinePartial2D_3(from.nativeObj, to.nativeObj, inliers.nativeObj, method, ransacReprojThreshold));
    }

    public static Mat estimateAffinePartial2D(Mat from, Mat to, Mat inliers, int method) {
        return new Mat(Calib3d.estimateAffinePartial2D_4(from.nativeObj, to.nativeObj, inliers.nativeObj, method));
    }

    public static Mat estimateAffinePartial2D(Mat from, Mat to, Mat inliers) {
        return new Mat(Calib3d.estimateAffinePartial2D_5(from.nativeObj, to.nativeObj, inliers.nativeObj));
    }

    public static Mat estimateAffinePartial2D(Mat from, Mat to) {
        return new Mat(Calib3d.estimateAffinePartial2D_6(from.nativeObj, to.nativeObj));
    }

    public static int decomposeHomographyMat(Mat H, Mat K, List<Mat> rotations, List<Mat> translations, List<Mat> normals) {
        Mat rotations_mat = new Mat();
        Mat translations_mat = new Mat();
        Mat normals_mat = new Mat();
        int retVal = Calib3d.decomposeHomographyMat_0(H.nativeObj, K.nativeObj, rotations_mat.nativeObj, translations_mat.nativeObj, normals_mat.nativeObj);
        Converters.Mat_to_vector_Mat(rotations_mat, rotations);
        rotations_mat.release();
        Converters.Mat_to_vector_Mat(translations_mat, translations);
        translations_mat.release();
        Converters.Mat_to_vector_Mat(normals_mat, normals);
        normals_mat.release();
        return retVal;
    }

    public static void filterHomographyDecompByVisibleRefpoints(List<Mat> rotations, List<Mat> normals, Mat beforePoints, Mat afterPoints, Mat possibleSolutions, Mat pointsMask) {
        Mat rotations_mat = Converters.vector_Mat_to_Mat(rotations);
        Mat normals_mat = Converters.vector_Mat_to_Mat(normals);
        Calib3d.filterHomographyDecompByVisibleRefpoints_0(rotations_mat.nativeObj, normals_mat.nativeObj, beforePoints.nativeObj, afterPoints.nativeObj, possibleSolutions.nativeObj, pointsMask.nativeObj);
    }

    public static void filterHomographyDecompByVisibleRefpoints(List<Mat> rotations, List<Mat> normals, Mat beforePoints, Mat afterPoints, Mat possibleSolutions) {
        Mat rotations_mat = Converters.vector_Mat_to_Mat(rotations);
        Mat normals_mat = Converters.vector_Mat_to_Mat(normals);
        Calib3d.filterHomographyDecompByVisibleRefpoints_1(rotations_mat.nativeObj, normals_mat.nativeObj, beforePoints.nativeObj, afterPoints.nativeObj, possibleSolutions.nativeObj);
    }

    public static void undistort(Mat src, Mat dst, Mat cameraMatrix, Mat distCoeffs, Mat newCameraMatrix) {
        Calib3d.undistort_0(src.nativeObj, dst.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, newCameraMatrix.nativeObj);
    }

    public static void undistort(Mat src, Mat dst, Mat cameraMatrix, Mat distCoeffs) {
        Calib3d.undistort_1(src.nativeObj, dst.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj);
    }

    public static void initUndistortRectifyMap(Mat cameraMatrix, Mat distCoeffs, Mat R, Mat newCameraMatrix, Size size, int m1type, Mat map1, Mat map2) {
        Calib3d.initUndistortRectifyMap_0(cameraMatrix.nativeObj, distCoeffs.nativeObj, R.nativeObj, newCameraMatrix.nativeObj, size.width, size.height, m1type, map1.nativeObj, map2.nativeObj);
    }

    public static Mat getDefaultNewCameraMatrix(Mat cameraMatrix, Size imgsize, boolean centerPrincipalPoint) {
        return new Mat(Calib3d.getDefaultNewCameraMatrix_0(cameraMatrix.nativeObj, imgsize.width, imgsize.height, centerPrincipalPoint));
    }

    public static Mat getDefaultNewCameraMatrix(Mat cameraMatrix, Size imgsize) {
        return new Mat(Calib3d.getDefaultNewCameraMatrix_1(cameraMatrix.nativeObj, imgsize.width, imgsize.height));
    }

    public static Mat getDefaultNewCameraMatrix(Mat cameraMatrix) {
        return new Mat(Calib3d.getDefaultNewCameraMatrix_2(cameraMatrix.nativeObj));
    }

    public static void undistortPoints(MatOfPoint2f src, MatOfPoint2f dst, Mat cameraMatrix, Mat distCoeffs, Mat R, Mat P) {
        MatOfPoint2f src_mat = src;
        MatOfPoint2f dst_mat = dst;
        Calib3d.undistortPoints_0(src_mat.nativeObj, dst_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, R.nativeObj, P.nativeObj);
    }

    public static void undistortPoints(MatOfPoint2f src, MatOfPoint2f dst, Mat cameraMatrix, Mat distCoeffs, Mat R) {
        MatOfPoint2f src_mat = src;
        MatOfPoint2f dst_mat = dst;
        Calib3d.undistortPoints_1(src_mat.nativeObj, dst_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, R.nativeObj);
    }

    public static void undistortPoints(MatOfPoint2f src, MatOfPoint2f dst, Mat cameraMatrix, Mat distCoeffs) {
        MatOfPoint2f src_mat = src;
        MatOfPoint2f dst_mat = dst;
        Calib3d.undistortPoints_2(src_mat.nativeObj, dst_mat.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj);
    }

    public static void undistortPointsIter(Mat src, Mat dst, Mat cameraMatrix, Mat distCoeffs, Mat R, Mat P, TermCriteria criteria) {
        Calib3d.undistortPointsIter_0(src.nativeObj, dst.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, R.nativeObj, P.nativeObj, criteria.type, criteria.maxCount, criteria.epsilon);
    }

    public static void fisheye_projectPoints(Mat objectPoints, Mat imagePoints, Mat rvec, Mat tvec, Mat K, Mat D, double alpha, Mat jacobian) {
        Calib3d.fisheye_projectPoints_0(objectPoints.nativeObj, imagePoints.nativeObj, rvec.nativeObj, tvec.nativeObj, K.nativeObj, D.nativeObj, alpha, jacobian.nativeObj);
    }

    public static void fisheye_projectPoints(Mat objectPoints, Mat imagePoints, Mat rvec, Mat tvec, Mat K, Mat D, double alpha) {
        Calib3d.fisheye_projectPoints_1(objectPoints.nativeObj, imagePoints.nativeObj, rvec.nativeObj, tvec.nativeObj, K.nativeObj, D.nativeObj, alpha);
    }

    public static void fisheye_projectPoints(Mat objectPoints, Mat imagePoints, Mat rvec, Mat tvec, Mat K, Mat D) {
        Calib3d.fisheye_projectPoints_2(objectPoints.nativeObj, imagePoints.nativeObj, rvec.nativeObj, tvec.nativeObj, K.nativeObj, D.nativeObj);
    }

    public static void fisheye_distortPoints(Mat undistorted, Mat distorted, Mat K, Mat D, double alpha) {
        Calib3d.fisheye_distortPoints_0(undistorted.nativeObj, distorted.nativeObj, K.nativeObj, D.nativeObj, alpha);
    }

    public static void fisheye_distortPoints(Mat undistorted, Mat distorted, Mat K, Mat D) {
        Calib3d.fisheye_distortPoints_1(undistorted.nativeObj, distorted.nativeObj, K.nativeObj, D.nativeObj);
    }

    public static void fisheye_undistortPoints(Mat distorted, Mat undistorted, Mat K, Mat D, Mat R, Mat P) {
        Calib3d.fisheye_undistortPoints_0(distorted.nativeObj, undistorted.nativeObj, K.nativeObj, D.nativeObj, R.nativeObj, P.nativeObj);
    }

    public static void fisheye_undistortPoints(Mat distorted, Mat undistorted, Mat K, Mat D, Mat R) {
        Calib3d.fisheye_undistortPoints_1(distorted.nativeObj, undistorted.nativeObj, K.nativeObj, D.nativeObj, R.nativeObj);
    }

    public static void fisheye_undistortPoints(Mat distorted, Mat undistorted, Mat K, Mat D) {
        Calib3d.fisheye_undistortPoints_2(distorted.nativeObj, undistorted.nativeObj, K.nativeObj, D.nativeObj);
    }

    public static void fisheye_initUndistortRectifyMap(Mat K, Mat D, Mat R, Mat P, Size size, int m1type, Mat map1, Mat map2) {
        Calib3d.fisheye_initUndistortRectifyMap_0(K.nativeObj, D.nativeObj, R.nativeObj, P.nativeObj, size.width, size.height, m1type, map1.nativeObj, map2.nativeObj);
    }

    public static void fisheye_undistortImage(Mat distorted, Mat undistorted, Mat K, Mat D, Mat Knew, Size new_size) {
        Calib3d.fisheye_undistortImage_0(distorted.nativeObj, undistorted.nativeObj, K.nativeObj, D.nativeObj, Knew.nativeObj, new_size.width, new_size.height);
    }

    public static void fisheye_undistortImage(Mat distorted, Mat undistorted, Mat K, Mat D, Mat Knew) {
        Calib3d.fisheye_undistortImage_1(distorted.nativeObj, undistorted.nativeObj, K.nativeObj, D.nativeObj, Knew.nativeObj);
    }

    public static void fisheye_undistortImage(Mat distorted, Mat undistorted, Mat K, Mat D) {
        Calib3d.fisheye_undistortImage_2(distorted.nativeObj, undistorted.nativeObj, K.nativeObj, D.nativeObj);
    }

    public static void fisheye_estimateNewCameraMatrixForUndistortRectify(Mat K, Mat D, Size image_size, Mat R, Mat P, double balance, Size new_size, double fov_scale) {
        Calib3d.fisheye_estimateNewCameraMatrixForUndistortRectify_0(K.nativeObj, D.nativeObj, image_size.width, image_size.height, R.nativeObj, P.nativeObj, balance, new_size.width, new_size.height, fov_scale);
    }

    public static void fisheye_estimateNewCameraMatrixForUndistortRectify(Mat K, Mat D, Size image_size, Mat R, Mat P, double balance, Size new_size) {
        Calib3d.fisheye_estimateNewCameraMatrixForUndistortRectify_1(K.nativeObj, D.nativeObj, image_size.width, image_size.height, R.nativeObj, P.nativeObj, balance, new_size.width, new_size.height);
    }

    public static void fisheye_estimateNewCameraMatrixForUndistortRectify(Mat K, Mat D, Size image_size, Mat R, Mat P, double balance) {
        Calib3d.fisheye_estimateNewCameraMatrixForUndistortRectify_2(K.nativeObj, D.nativeObj, image_size.width, image_size.height, R.nativeObj, P.nativeObj, balance);
    }

    public static void fisheye_estimateNewCameraMatrixForUndistortRectify(Mat K, Mat D, Size image_size, Mat R, Mat P) {
        Calib3d.fisheye_estimateNewCameraMatrixForUndistortRectify_3(K.nativeObj, D.nativeObj, image_size.width, image_size.height, R.nativeObj, P.nativeObj);
    }

    public static double fisheye_calibrate(List<Mat> objectPoints, List<Mat> imagePoints, Size image_size, Mat K, Mat D, List<Mat> rvecs, List<Mat> tvecs, int flags, TermCriteria criteria) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.fisheye_calibrate_0(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, image_size.width, image_size.height, K.nativeObj, D.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, flags, criteria.type, criteria.maxCount, criteria.epsilon);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static double fisheye_calibrate(List<Mat> objectPoints, List<Mat> imagePoints, Size image_size, Mat K, Mat D, List<Mat> rvecs, List<Mat> tvecs, int flags) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.fisheye_calibrate_1(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, image_size.width, image_size.height, K.nativeObj, D.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj, flags);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static double fisheye_calibrate(List<Mat> objectPoints, List<Mat> imagePoints, Size image_size, Mat K, Mat D, List<Mat> rvecs, List<Mat> tvecs) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints_mat = Converters.vector_Mat_to_Mat(imagePoints);
        Mat rvecs_mat = new Mat();
        Mat tvecs_mat = new Mat();
        double retVal = Calib3d.fisheye_calibrate_2(objectPoints_mat.nativeObj, imagePoints_mat.nativeObj, image_size.width, image_size.height, K.nativeObj, D.nativeObj, rvecs_mat.nativeObj, tvecs_mat.nativeObj);
        Converters.Mat_to_vector_Mat(rvecs_mat, rvecs);
        rvecs_mat.release();
        Converters.Mat_to_vector_Mat(tvecs_mat, tvecs);
        tvecs_mat.release();
        return retVal;
    }

    public static void fisheye_stereoRectify(Mat K1, Mat D1, Mat K2, Mat D2, Size imageSize, Mat R, Mat tvec, Mat R1, Mat R2, Mat P1, Mat P2, Mat Q, int flags, Size newImageSize, double balance, double fov_scale) {
        Calib3d.fisheye_stereoRectify_0(K1.nativeObj, D1.nativeObj, K2.nativeObj, D2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, tvec.nativeObj, R1.nativeObj, R2.nativeObj, P1.nativeObj, P2.nativeObj, Q.nativeObj, flags, newImageSize.width, newImageSize.height, balance, fov_scale);
    }

    public static void fisheye_stereoRectify(Mat K1, Mat D1, Mat K2, Mat D2, Size imageSize, Mat R, Mat tvec, Mat R1, Mat R2, Mat P1, Mat P2, Mat Q, int flags, Size newImageSize, double balance) {
        Calib3d.fisheye_stereoRectify_1(K1.nativeObj, D1.nativeObj, K2.nativeObj, D2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, tvec.nativeObj, R1.nativeObj, R2.nativeObj, P1.nativeObj, P2.nativeObj, Q.nativeObj, flags, newImageSize.width, newImageSize.height, balance);
    }

    public static void fisheye_stereoRectify(Mat K1, Mat D1, Mat K2, Mat D2, Size imageSize, Mat R, Mat tvec, Mat R1, Mat R2, Mat P1, Mat P2, Mat Q, int flags, Size newImageSize) {
        Calib3d.fisheye_stereoRectify_2(K1.nativeObj, D1.nativeObj, K2.nativeObj, D2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, tvec.nativeObj, R1.nativeObj, R2.nativeObj, P1.nativeObj, P2.nativeObj, Q.nativeObj, flags, newImageSize.width, newImageSize.height);
    }

    public static void fisheye_stereoRectify(Mat K1, Mat D1, Mat K2, Mat D2, Size imageSize, Mat R, Mat tvec, Mat R1, Mat R2, Mat P1, Mat P2, Mat Q, int flags) {
        Calib3d.fisheye_stereoRectify_3(K1.nativeObj, D1.nativeObj, K2.nativeObj, D2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, tvec.nativeObj, R1.nativeObj, R2.nativeObj, P1.nativeObj, P2.nativeObj, Q.nativeObj, flags);
    }

    public static double fisheye_stereoCalibrate(List<Mat> objectPoints, List<Mat> imagePoints1, List<Mat> imagePoints2, Mat K1, Mat D1, Mat K2, Mat D2, Size imageSize, Mat R, Mat T, int flags, TermCriteria criteria) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints1_mat = Converters.vector_Mat_to_Mat(imagePoints1);
        Mat imagePoints2_mat = Converters.vector_Mat_to_Mat(imagePoints2);
        return Calib3d.fisheye_stereoCalibrate_0(objectPoints_mat.nativeObj, imagePoints1_mat.nativeObj, imagePoints2_mat.nativeObj, K1.nativeObj, D1.nativeObj, K2.nativeObj, D2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj, flags, criteria.type, criteria.maxCount, criteria.epsilon);
    }

    public static double fisheye_stereoCalibrate(List<Mat> objectPoints, List<Mat> imagePoints1, List<Mat> imagePoints2, Mat K1, Mat D1, Mat K2, Mat D2, Size imageSize, Mat R, Mat T, int flags) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints1_mat = Converters.vector_Mat_to_Mat(imagePoints1);
        Mat imagePoints2_mat = Converters.vector_Mat_to_Mat(imagePoints2);
        return Calib3d.fisheye_stereoCalibrate_1(objectPoints_mat.nativeObj, imagePoints1_mat.nativeObj, imagePoints2_mat.nativeObj, K1.nativeObj, D1.nativeObj, K2.nativeObj, D2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj, flags);
    }

    public static double fisheye_stereoCalibrate(List<Mat> objectPoints, List<Mat> imagePoints1, List<Mat> imagePoints2, Mat K1, Mat D1, Mat K2, Mat D2, Size imageSize, Mat R, Mat T) {
        Mat objectPoints_mat = Converters.vector_Mat_to_Mat(objectPoints);
        Mat imagePoints1_mat = Converters.vector_Mat_to_Mat(imagePoints1);
        Mat imagePoints2_mat = Converters.vector_Mat_to_Mat(imagePoints2);
        return Calib3d.fisheye_stereoCalibrate_2(objectPoints_mat.nativeObj, imagePoints1_mat.nativeObj, imagePoints2_mat.nativeObj, K1.nativeObj, D1.nativeObj, K2.nativeObj, D2.nativeObj, imageSize.width, imageSize.height, R.nativeObj, T.nativeObj);
    }

    private static native void Rodrigues_0(long var0, long var2, long var4);

    private static native void Rodrigues_1(long var0, long var2);

    private static native long findHomography_0(long var0, long var2, int var4, double var5, long var7, int var9, double var10);

    private static native long findHomography_1(long var0, long var2, int var4, double var5, long var7, int var9);

    private static native long findHomography_2(long var0, long var2, int var4, double var5, long var7);

    private static native long findHomography_3(long var0, long var2, int var4, double var5);

    private static native long findHomography_4(long var0, long var2, int var4);

    private static native long findHomography_5(long var0, long var2);

    private static native long findHomography_6(long var0, long var2, long var4, long var6);

    private static native double[] RQDecomp3x3_0(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native double[] RQDecomp3x3_1(long var0, long var2, long var4, long var6, long var8);

    private static native double[] RQDecomp3x3_2(long var0, long var2, long var4, long var6);

    private static native double[] RQDecomp3x3_3(long var0, long var2, long var4);

    private static native void decomposeProjectionMatrix_0(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14);

    private static native void decomposeProjectionMatrix_1(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    private static native void decomposeProjectionMatrix_2(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native void decomposeProjectionMatrix_3(long var0, long var2, long var4, long var6, long var8);

    private static native void decomposeProjectionMatrix_4(long var0, long var2, long var4, long var6);

    private static native void matMulDeriv_0(long var0, long var2, long var4, long var6);

    private static native void composeRT_0(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, long var16, long var18, long var20, long var22, long var24, long var26);

    private static native void composeRT_1(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, long var16, long var18, long var20, long var22, long var24);

    private static native void composeRT_2(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, long var16, long var18, long var20, long var22);

    private static native void composeRT_3(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, long var16, long var18, long var20);

    private static native void composeRT_4(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, long var16, long var18);

    private static native void composeRT_5(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, long var16);

    private static native void composeRT_6(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14);

    private static native void composeRT_7(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    private static native void composeRT_8(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native void projectPoints_0(long var0, long var2, long var4, long var6, long var8, long var10, long var12, double var14);

    private static native void projectPoints_1(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    private static native void projectPoints_2(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native boolean solvePnP_0(long var0, long var2, long var4, long var6, long var8, long var10, boolean var12, int var13);

    private static native boolean solvePnP_1(long var0, long var2, long var4, long var6, long var8, long var10, boolean var12);

    private static native boolean solvePnP_2(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native boolean solvePnPRansac_0(long var0, long var2, long var4, long var6, long var8, long var10, boolean var12, int var13, float var14, double var15, long var17, int var19);

    private static native boolean solvePnPRansac_1(long var0, long var2, long var4, long var6, long var8, long var10, boolean var12, int var13, float var14, double var15, long var17);

    private static native boolean solvePnPRansac_2(long var0, long var2, long var4, long var6, long var8, long var10, boolean var12, int var13, float var14, double var15);

    private static native boolean solvePnPRansac_3(long var0, long var2, long var4, long var6, long var8, long var10, boolean var12, int var13, float var14);

    private static native boolean solvePnPRansac_4(long var0, long var2, long var4, long var6, long var8, long var10, boolean var12, int var13);

    private static native boolean solvePnPRansac_5(long var0, long var2, long var4, long var6, long var8, long var10, boolean var12);

    private static native boolean solvePnPRansac_6(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native boolean solvePnPRansac_7(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14);

    private static native boolean solvePnPRansac_8(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    private static native int solveP3P_0(long var0, long var2, long var4, long var6, long var8, long var10, int var12);

    private static native void solvePnPRefineLM_0(long var0, long var2, long var4, long var6, long var8, long var10, int var12, int var13, double var14);

    private static native void solvePnPRefineLM_1(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native void solvePnPRefineVVS_0(long var0, long var2, long var4, long var6, long var8, long var10, int var12, int var13, double var14, double var16);

    private static native void solvePnPRefineVVS_1(long var0, long var2, long var4, long var6, long var8, long var10, int var12, int var13, double var14);

    private static native void solvePnPRefineVVS_2(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native int solvePnPGeneric_0(long var0, long var2, long var4, long var6, long var8, long var10, boolean var12, int var13, long var14, long var16, long var18);

    private static native int solvePnPGeneric_1(long var0, long var2, long var4, long var6, long var8, long var10, boolean var12, int var13, long var14, long var16);

    private static native int solvePnPGeneric_2(long var0, long var2, long var4, long var6, long var8, long var10, boolean var12, int var13, long var14);

    private static native int solvePnPGeneric_3(long var0, long var2, long var4, long var6, long var8, long var10, boolean var12, int var13);

    private static native int solvePnPGeneric_4(long var0, long var2, long var4, long var6, long var8, long var10, boolean var12);

    private static native int solvePnPGeneric_5(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native long initCameraMatrix2D_0(long var0, long var2, double var4, double var6, double var8);

    private static native long initCameraMatrix2D_1(long var0, long var2, double var4, double var6);

    private static native boolean findChessboardCorners_0(long var0, double var2, double var4, long var6, int var8);

    private static native boolean findChessboardCorners_1(long var0, double var2, double var4, long var6);

    private static native boolean checkChessboard_0(long var0, double var2, double var4);

    private static native boolean findChessboardCornersSBWithMeta_0(long var0, double var2, double var4, long var6, int var8, long var9);

    private static native boolean findChessboardCornersSB_0(long var0, double var2, double var4, long var6, int var8);

    private static native boolean findChessboardCornersSB_1(long var0, double var2, double var4, long var6);

    private static native double[] estimateChessboardSharpness_0(long var0, double var2, double var4, long var6, float var8, boolean var9, long var10);

    private static native double[] estimateChessboardSharpness_1(long var0, double var2, double var4, long var6, float var8, boolean var9);

    private static native double[] estimateChessboardSharpness_2(long var0, double var2, double var4, long var6, float var8);

    private static native double[] estimateChessboardSharpness_3(long var0, double var2, double var4, long var6);

    private static native boolean find4QuadCornerSubpix_0(long var0, long var2, double var4, double var6);

    private static native void drawChessboardCorners_0(long var0, double var2, double var4, long var6, boolean var8);

    private static native void drawFrameAxes_0(long var0, long var2, long var4, long var6, long var8, float var10, int var11);

    private static native void drawFrameAxes_1(long var0, long var2, long var4, long var6, long var8, float var10);

    private static native boolean findCirclesGrid_0(long var0, double var2, double var4, long var6, int var8);

    private static native boolean findCirclesGrid_2(long var0, double var2, double var4, long var6);

    private static native double calibrateCameraExtended_0(long var0, long var2, double var4, double var6, long var8, long var10, long var12, long var14, long var16, long var18, long var20, int var22, int var23, int var24, double var25);

    private static native double calibrateCameraExtended_1(long var0, long var2, double var4, double var6, long var8, long var10, long var12, long var14, long var16, long var18, long var20, int var22);

    private static native double calibrateCameraExtended_2(long var0, long var2, double var4, double var6, long var8, long var10, long var12, long var14, long var16, long var18, long var20);

    private static native double calibrateCamera_0(long var0, long var2, double var4, double var6, long var8, long var10, long var12, long var14, int var16, int var17, int var18, double var19);

    private static native double calibrateCamera_1(long var0, long var2, double var4, double var6, long var8, long var10, long var12, long var14, int var16);

    private static native double calibrateCamera_2(long var0, long var2, double var4, double var6, long var8, long var10, long var12, long var14);

    private static native double calibrateCameraROExtended_0(long var0, long var2, double var4, double var6, int var8, long var9, long var11, long var13, long var15, long var17, long var19, long var21, long var23, long var25, int var27, int var28, int var29, double var30);

    private static native double calibrateCameraROExtended_1(long var0, long var2, double var4, double var6, int var8, long var9, long var11, long var13, long var15, long var17, long var19, long var21, long var23, long var25, int var27);

    private static native double calibrateCameraROExtended_2(long var0, long var2, double var4, double var6, int var8, long var9, long var11, long var13, long var15, long var17, long var19, long var21, long var23, long var25);

    private static native double calibrateCameraRO_0(long var0, long var2, double var4, double var6, int var8, long var9, long var11, long var13, long var15, long var17, int var19, int var20, int var21, double var22);

    private static native double calibrateCameraRO_1(long var0, long var2, double var4, double var6, int var8, long var9, long var11, long var13, long var15, long var17, int var19);

    private static native double calibrateCameraRO_2(long var0, long var2, double var4, double var6, int var8, long var9, long var11, long var13, long var15, long var17);

    private static native void calibrationMatrixValues_0(long var0, double var2, double var4, double var6, double var8, double[] var10, double[] var11, double[] var12, double[] var13, double[] var14);

    private static native double stereoCalibrateExtended_0(long var0, long var2, long var4, long var6, long var8, long var10, long var12, double var14, double var16, long var18, long var20, long var22, long var24, long var26, int var28, int var29, int var30, double var31);

    private static native double stereoCalibrateExtended_1(long var0, long var2, long var4, long var6, long var8, long var10, long var12, double var14, double var16, long var18, long var20, long var22, long var24, long var26, int var28);

    private static native double stereoCalibrateExtended_2(long var0, long var2, long var4, long var6, long var8, long var10, long var12, double var14, double var16, long var18, long var20, long var22, long var24, long var26);

    private static native double stereoCalibrate_0(long var0, long var2, long var4, long var6, long var8, long var10, long var12, double var14, double var16, long var18, long var20, long var22, long var24, int var26, int var27, int var28, double var29);

    private static native double stereoCalibrate_1(long var0, long var2, long var4, long var6, long var8, long var10, long var12, double var14, double var16, long var18, long var20, long var22, long var24, int var26);

    private static native double stereoCalibrate_2(long var0, long var2, long var4, long var6, long var8, long var10, long var12, double var14, double var16, long var18, long var20, long var22, long var24);

    private static native void stereoRectify_0(long var0, long var2, long var4, long var6, double var8, double var10, long var12, long var14, long var16, long var18, long var20, long var22, long var24, int var26, double var27, double var29, double var31, double[] var33, double[] var34);

    private static native void stereoRectify_1(long var0, long var2, long var4, long var6, double var8, double var10, long var12, long var14, long var16, long var18, long var20, long var22, long var24, int var26, double var27, double var29, double var31, double[] var33);

    private static native void stereoRectify_2(long var0, long var2, long var4, long var6, double var8, double var10, long var12, long var14, long var16, long var18, long var20, long var22, long var24, int var26, double var27, double var29, double var31);

    private static native void stereoRectify_3(long var0, long var2, long var4, long var6, double var8, double var10, long var12, long var14, long var16, long var18, long var20, long var22, long var24, int var26, double var27);

    private static native void stereoRectify_4(long var0, long var2, long var4, long var6, double var8, double var10, long var12, long var14, long var16, long var18, long var20, long var22, long var24, int var26);

    private static native void stereoRectify_5(long var0, long var2, long var4, long var6, double var8, double var10, long var12, long var14, long var16, long var18, long var20, long var22, long var24);

    private static native boolean stereoRectifyUncalibrated_0(long var0, long var2, long var4, double var6, double var8, long var10, long var12, double var14);

    private static native boolean stereoRectifyUncalibrated_1(long var0, long var2, long var4, double var6, double var8, long var10, long var12);

    private static native float rectify3Collinear_0(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, double var16, double var18, long var20, long var22, long var24, long var26, long var28, long var30, long var32, long var34, long var36, long var38, long var40, double var42, double var44, double var46, double[] var48, double[] var49, int var50);

    private static native long getOptimalNewCameraMatrix_0(long var0, long var2, double var4, double var6, double var8, double var10, double var12, double[] var14, boolean var15);

    private static native long getOptimalNewCameraMatrix_1(long var0, long var2, double var4, double var6, double var8, double var10, double var12, double[] var14);

    private static native long getOptimalNewCameraMatrix_2(long var0, long var2, double var4, double var6, double var8, double var10, double var12);

    private static native long getOptimalNewCameraMatrix_3(long var0, long var2, double var4, double var6, double var8);

    private static native void calibrateHandEye_0(long var0, long var2, long var4, long var6, long var8, long var10, int var12);

    private static native void calibrateHandEye_1(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native void calibrateRobotWorldHandEye_0(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, int var16);

    private static native void calibrateRobotWorldHandEye_1(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14);

    private static native void convertPointsToHomogeneous_0(long var0, long var2);

    private static native void convertPointsFromHomogeneous_0(long var0, long var2);

    private static native long findFundamentalMat_0(long var0, long var2, int var4, double var5, double var7, int var9, long var10);

    private static native long findFundamentalMat_1(long var0, long var2, int var4, double var5, double var7, int var9);

    private static native long findFundamentalMat_2(long var0, long var2, int var4, double var5, double var7, long var9);

    private static native long findFundamentalMat_3(long var0, long var2, int var4, double var5, double var7);

    private static native long findFundamentalMat_4(long var0, long var2, int var4, double var5);

    private static native long findFundamentalMat_5(long var0, long var2, int var4);

    private static native long findFundamentalMat_6(long var0, long var2);

    private static native long findFundamentalMat_7(long var0, long var2, long var4, long var6);

    private static native long findEssentialMat_0(long var0, long var2, long var4, int var6, double var7, double var9, long var11);

    private static native long findEssentialMat_1(long var0, long var2, long var4, int var6, double var7, double var9);

    private static native long findEssentialMat_2(long var0, long var2, long var4, int var6, double var7);

    private static native long findEssentialMat_3(long var0, long var2, long var4, int var6);

    private static native long findEssentialMat_4(long var0, long var2, long var4);

    private static native long findEssentialMat_5(long var0, long var2, double var4, double var6, double var8, int var10, double var11, double var13, long var15);

    private static native long findEssentialMat_6(long var0, long var2, double var4, double var6, double var8, int var10, double var11, double var13);

    private static native long findEssentialMat_7(long var0, long var2, double var4, double var6, double var8, int var10, double var11);

    private static native long findEssentialMat_8(long var0, long var2, double var4, double var6, double var8, int var10);

    private static native long findEssentialMat_9(long var0, long var2, double var4, double var6, double var8);

    private static native long findEssentialMat_10(long var0, long var2, double var4);

    private static native long findEssentialMat_11(long var0, long var2);

    private static native long findEssentialMat_12(long var0, long var2, long var4, long var6, long var8, long var10, int var12, double var13, double var15, long var17);

    private static native long findEssentialMat_13(long var0, long var2, long var4, long var6, long var8, long var10, int var12, double var13, double var15);

    private static native long findEssentialMat_14(long var0, long var2, long var4, long var6, long var8, long var10, int var12, double var13);

    private static native long findEssentialMat_15(long var0, long var2, long var4, long var6, long var8, long var10, int var12);

    private static native long findEssentialMat_16(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native long findEssentialMat_17(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14);

    private static native void decomposeEssentialMat_0(long var0, long var2, long var4, long var6);

    private static native int recoverPose_0(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    private static native int recoverPose_1(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native int recoverPose_2(long var0, long var2, long var4, long var6, long var8, double var10, double var12, double var14, long var16);

    private static native int recoverPose_3(long var0, long var2, long var4, long var6, long var8, double var10, double var12, double var14);

    private static native int recoverPose_4(long var0, long var2, long var4, long var6, long var8, double var10);

    private static native int recoverPose_5(long var0, long var2, long var4, long var6, long var8);

    private static native int recoverPose_6(long var0, long var2, long var4, long var6, long var8, long var10, double var12, long var14, long var16);

    private static native int recoverPose_7(long var0, long var2, long var4, long var6, long var8, long var10, double var12, long var14);

    private static native int recoverPose_8(long var0, long var2, long var4, long var6, long var8, long var10, double var12);

    private static native void computeCorrespondEpilines_0(long var0, int var2, long var3, long var5);

    private static native void triangulatePoints_0(long var0, long var2, long var4, long var6, long var8);

    private static native void correctMatches_0(long var0, long var2, long var4, long var6, long var8);

    private static native void filterSpeckles_0(long var0, double var2, int var4, double var5, long var7);

    private static native void filterSpeckles_1(long var0, double var2, int var4, double var5);

    private static native double[] getValidDisparityROI_0(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10);

    private static native void validateDisparity_0(long var0, long var2, int var4, int var5, int var6);

    private static native void validateDisparity_1(long var0, long var2, int var4, int var5);

    private static native void reprojectImageTo3D_0(long var0, long var2, long var4, boolean var6, int var7);

    private static native void reprojectImageTo3D_1(long var0, long var2, long var4, boolean var6);

    private static native void reprojectImageTo3D_2(long var0, long var2, long var4);

    private static native double sampsonDistance_0(long var0, long var2, long var4);

    private static native int estimateAffine3D_0(long var0, long var2, long var4, long var6, double var8, double var10);

    private static native int estimateAffine3D_1(long var0, long var2, long var4, long var6, double var8);

    private static native int estimateAffine3D_2(long var0, long var2, long var4, long var6);

    private static native int estimateTranslation3D_0(long var0, long var2, long var4, long var6, double var8, double var10);

    private static native int estimateTranslation3D_1(long var0, long var2, long var4, long var6, double var8);

    private static native int estimateTranslation3D_2(long var0, long var2, long var4, long var6);

    private static native long estimateAffine2D_0(long var0, long var2, long var4, int var6, double var7, long var9, double var11, long var13);

    private static native long estimateAffine2D_1(long var0, long var2, long var4, int var6, double var7, long var9, double var11);

    private static native long estimateAffine2D_2(long var0, long var2, long var4, int var6, double var7, long var9);

    private static native long estimateAffine2D_3(long var0, long var2, long var4, int var6, double var7);

    private static native long estimateAffine2D_4(long var0, long var2, long var4, int var6);

    private static native long estimateAffine2D_5(long var0, long var2, long var4);

    private static native long estimateAffine2D_6(long var0, long var2);

    private static native long estimateAffine2D_7(long var0, long var2, long var4, long var6);

    private static native long estimateAffinePartial2D_0(long var0, long var2, long var4, int var6, double var7, long var9, double var11, long var13);

    private static native long estimateAffinePartial2D_1(long var0, long var2, long var4, int var6, double var7, long var9, double var11);

    private static native long estimateAffinePartial2D_2(long var0, long var2, long var4, int var6, double var7, long var9);

    private static native long estimateAffinePartial2D_3(long var0, long var2, long var4, int var6, double var7);

    private static native long estimateAffinePartial2D_4(long var0, long var2, long var4, int var6);

    private static native long estimateAffinePartial2D_5(long var0, long var2, long var4);

    private static native long estimateAffinePartial2D_6(long var0, long var2);

    private static native int decomposeHomographyMat_0(long var0, long var2, long var4, long var6, long var8);

    private static native void filterHomographyDecompByVisibleRefpoints_0(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native void filterHomographyDecompByVisibleRefpoints_1(long var0, long var2, long var4, long var6, long var8);

    private static native void undistort_0(long var0, long var2, long var4, long var6, long var8);

    private static native void undistort_1(long var0, long var2, long var4, long var6);

    private static native void initUndistortRectifyMap_0(long var0, long var2, long var4, long var6, double var8, double var10, int var12, long var13, long var15);

    private static native long getDefaultNewCameraMatrix_0(long var0, double var2, double var4, boolean var6);

    private static native long getDefaultNewCameraMatrix_1(long var0, double var2, double var4);

    private static native long getDefaultNewCameraMatrix_2(long var0);

    private static native void undistortPoints_0(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native void undistortPoints_1(long var0, long var2, long var4, long var6, long var8);

    private static native void undistortPoints_2(long var0, long var2, long var4, long var6);

    private static native void undistortPointsIter_0(long var0, long var2, long var4, long var6, long var8, long var10, int var12, int var13, double var14);

    private static native void fisheye_projectPoints_0(long var0, long var2, long var4, long var6, long var8, long var10, double var12, long var14);

    private static native void fisheye_projectPoints_1(long var0, long var2, long var4, long var6, long var8, long var10, double var12);

    private static native void fisheye_projectPoints_2(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native void fisheye_distortPoints_0(long var0, long var2, long var4, long var6, double var8);

    private static native void fisheye_distortPoints_1(long var0, long var2, long var4, long var6);

    private static native void fisheye_undistortPoints_0(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native void fisheye_undistortPoints_1(long var0, long var2, long var4, long var6, long var8);

    private static native void fisheye_undistortPoints_2(long var0, long var2, long var4, long var6);

    private static native void fisheye_initUndistortRectifyMap_0(long var0, long var2, long var4, long var6, double var8, double var10, int var12, long var13, long var15);

    private static native void fisheye_undistortImage_0(long var0, long var2, long var4, long var6, long var8, double var10, double var12);

    private static native void fisheye_undistortImage_1(long var0, long var2, long var4, long var6, long var8);

    private static native void fisheye_undistortImage_2(long var0, long var2, long var4, long var6);

    private static native void fisheye_estimateNewCameraMatrixForUndistortRectify_0(long var0, long var2, double var4, double var6, long var8, long var10, double var12, double var14, double var16, double var18);

    private static native void fisheye_estimateNewCameraMatrixForUndistortRectify_1(long var0, long var2, double var4, double var6, long var8, long var10, double var12, double var14, double var16);

    private static native void fisheye_estimateNewCameraMatrixForUndistortRectify_2(long var0, long var2, double var4, double var6, long var8, long var10, double var12);

    private static native void fisheye_estimateNewCameraMatrixForUndistortRectify_3(long var0, long var2, double var4, double var6, long var8, long var10);

    private static native double fisheye_calibrate_0(long var0, long var2, double var4, double var6, long var8, long var10, long var12, long var14, int var16, int var17, int var18, double var19);

    private static native double fisheye_calibrate_1(long var0, long var2, double var4, double var6, long var8, long var10, long var12, long var14, int var16);

    private static native double fisheye_calibrate_2(long var0, long var2, double var4, double var6, long var8, long var10, long var12, long var14);

    private static native void fisheye_stereoRectify_0(long var0, long var2, long var4, long var6, double var8, double var10, long var12, long var14, long var16, long var18, long var20, long var22, long var24, int var26, double var27, double var29, double var31, double var33);

    private static native void fisheye_stereoRectify_1(long var0, long var2, long var4, long var6, double var8, double var10, long var12, long var14, long var16, long var18, long var20, long var22, long var24, int var26, double var27, double var29, double var31);

    private static native void fisheye_stereoRectify_2(long var0, long var2, long var4, long var6, double var8, double var10, long var12, long var14, long var16, long var18, long var20, long var22, long var24, int var26, double var27, double var29);

    private static native void fisheye_stereoRectify_3(long var0, long var2, long var4, long var6, double var8, double var10, long var12, long var14, long var16, long var18, long var20, long var22, long var24, int var26);

    private static native double fisheye_stereoCalibrate_0(long var0, long var2, long var4, long var6, long var8, long var10, long var12, double var14, double var16, long var18, long var20, int var22, int var23, int var24, double var25);

    private static native double fisheye_stereoCalibrate_1(long var0, long var2, long var4, long var6, long var8, long var10, long var12, double var14, double var16, long var18, long var20, int var22);

    private static native double fisheye_stereoCalibrate_2(long var0, long var2, long var4, long var6, long var8, long var10, long var12, double var14, double var16, long var18, long var20);
}

