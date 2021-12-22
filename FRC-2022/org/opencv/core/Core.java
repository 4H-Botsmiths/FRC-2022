/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.TermCriteria;
import org.opencv.utils.Converters;

public class Core {
    public static final String VERSION = Core.getVersion();
    public static final String NATIVE_LIBRARY_NAME = Core.getNativeLibraryName();
    public static final int VERSION_MAJOR = Core.getVersionMajorJ();
    public static final int VERSION_MINOR = Core.getVersionMinorJ();
    public static final int VERSION_REVISION = Core.getVersionRevisionJ();
    public static final String VERSION_STATUS = Core.getVersionStatusJ();
    private static final int CV_8U = 0;
    private static final int CV_8S = 1;
    private static final int CV_16U = 2;
    private static final int CV_16S = 3;
    private static final int CV_32S = 4;
    private static final int CV_32F = 5;
    private static final int CV_64F = 6;
    private static final int CV_USRTYPE1 = 7;
    public static final int SVD_MODIFY_A = 1;
    public static final int SVD_NO_UV = 2;
    public static final int SVD_FULL_UV = 4;
    public static final int FILLED = -1;
    public static final int REDUCE_SUM = 0;
    public static final int REDUCE_AVG = 1;
    public static final int REDUCE_MAX = 2;
    public static final int REDUCE_MIN = 3;
    public static final int RNG_UNIFORM = 0;
    public static final int RNG_NORMAL = 1;
    public static final int BORDER_CONSTANT = 0;
    public static final int BORDER_REPLICATE = 1;
    public static final int BORDER_REFLECT = 2;
    public static final int BORDER_WRAP = 3;
    public static final int BORDER_REFLECT_101 = 4;
    public static final int BORDER_TRANSPARENT = 5;
    public static final int BORDER_REFLECT101 = 4;
    public static final int BORDER_DEFAULT = 4;
    public static final int BORDER_ISOLATED = 16;
    public static final int CMP_EQ = 0;
    public static final int CMP_GT = 1;
    public static final int CMP_GE = 2;
    public static final int CMP_LT = 3;
    public static final int CMP_LE = 4;
    public static final int CMP_NE = 5;
    public static final int COVAR_SCRAMBLED = 0;
    public static final int COVAR_NORMAL = 1;
    public static final int COVAR_USE_AVG = 2;
    public static final int COVAR_SCALE = 4;
    public static final int COVAR_ROWS = 8;
    public static final int COVAR_COLS = 16;
    public static final int DECOMP_LU = 0;
    public static final int DECOMP_SVD = 1;
    public static final int DECOMP_EIG = 2;
    public static final int DECOMP_CHOLESKY = 3;
    public static final int DECOMP_QR = 4;
    public static final int DECOMP_NORMAL = 16;
    public static final int DFT_INVERSE = 1;
    public static final int DFT_SCALE = 2;
    public static final int DFT_ROWS = 4;
    public static final int DFT_COMPLEX_OUTPUT = 16;
    public static final int DFT_REAL_OUTPUT = 32;
    public static final int DFT_COMPLEX_INPUT = 64;
    public static final int DCT_INVERSE = 1;
    public static final int DCT_ROWS = 4;
    public static final int StsOk = 0;
    public static final int StsBackTrace = -1;
    public static final int StsError = -2;
    public static final int StsInternal = -3;
    public static final int StsNoMem = -4;
    public static final int StsBadArg = -5;
    public static final int StsBadFunc = -6;
    public static final int StsNoConv = -7;
    public static final int StsAutoTrace = -8;
    public static final int HeaderIsNull = -9;
    public static final int BadImageSize = -10;
    public static final int BadOffset = -11;
    public static final int BadDataPtr = -12;
    public static final int BadStep = -13;
    public static final int BadModelOrChSeq = -14;
    public static final int BadNumChannels = -15;
    public static final int BadNumChannel1U = -16;
    public static final int BadDepth = -17;
    public static final int BadAlphaChannel = -18;
    public static final int BadOrder = -19;
    public static final int BadOrigin = -20;
    public static final int BadAlign = -21;
    public static final int BadCallBack = -22;
    public static final int BadTileSize = -23;
    public static final int BadCOI = -24;
    public static final int BadROISize = -25;
    public static final int MaskIsTiled = -26;
    public static final int StsNullPtr = -27;
    public static final int StsVecLengthErr = -28;
    public static final int StsFilterStructContentErr = -29;
    public static final int StsKernelStructContentErr = -30;
    public static final int StsFilterOffsetErr = -31;
    public static final int StsBadSize = -201;
    public static final int StsDivByZero = -202;
    public static final int StsInplaceNotSupported = -203;
    public static final int StsObjectNotFound = -204;
    public static final int StsUnmatchedFormats = -205;
    public static final int StsBadFlag = -206;
    public static final int StsBadPoint = -207;
    public static final int StsBadMask = -208;
    public static final int StsUnmatchedSizes = -209;
    public static final int StsUnsupportedFormat = -210;
    public static final int StsOutOfRange = -211;
    public static final int StsParseError = -212;
    public static final int StsNotImplemented = -213;
    public static final int StsBadMemBlock = -214;
    public static final int StsAssert = -215;
    public static final int GpuNotSupported = -216;
    public static final int GpuApiCallError = -217;
    public static final int OpenGlNotSupported = -218;
    public static final int OpenGlApiCallError = -219;
    public static final int OpenCLApiCallError = -220;
    public static final int OpenCLDoubleNotSupported = -221;
    public static final int OpenCLInitError = -222;
    public static final int OpenCLNoAMDBlasFft = -223;
    public static final int Formatter_FMT_DEFAULT = 0;
    public static final int Formatter_FMT_MATLAB = 1;
    public static final int Formatter_FMT_CSV = 2;
    public static final int Formatter_FMT_PYTHON = 3;
    public static final int Formatter_FMT_NUMPY = 4;
    public static final int Formatter_FMT_C = 5;
    public static final int GEMM_1_T = 1;
    public static final int GEMM_2_T = 2;
    public static final int GEMM_3_T = 4;
    public static final int KMEANS_RANDOM_CENTERS = 0;
    public static final int KMEANS_PP_CENTERS = 2;
    public static final int KMEANS_USE_INITIAL_LABELS = 1;
    public static final int NORM_INF = 1;
    public static final int NORM_L1 = 2;
    public static final int NORM_L2 = 4;
    public static final int NORM_L2SQR = 5;
    public static final int NORM_HAMMING = 6;
    public static final int NORM_HAMMING2 = 7;
    public static final int NORM_TYPE_MASK = 7;
    public static final int NORM_RELATIVE = 8;
    public static final int NORM_MINMAX = 32;
    public static final int PCA_DATA_AS_ROW = 0;
    public static final int PCA_DATA_AS_COL = 1;
    public static final int PCA_USE_AVG = 2;
    public static final int Param_INT = 0;
    public static final int Param_BOOLEAN = 1;
    public static final int Param_REAL = 2;
    public static final int Param_STRING = 3;
    public static final int Param_MAT = 4;
    public static final int Param_MAT_VECTOR = 5;
    public static final int Param_ALGORITHM = 6;
    public static final int Param_FLOAT = 7;
    public static final int Param_UNSIGNED_INT = 8;
    public static final int Param_UINT64 = 9;
    public static final int Param_UCHAR = 11;
    public static final int Param_SCALAR = 12;
    public static final int ROTATE_90_CLOCKWISE = 0;
    public static final int ROTATE_180 = 1;
    public static final int ROTATE_90_COUNTERCLOCKWISE = 2;
    public static final int SORT_EVERY_ROW = 0;
    public static final int SORT_EVERY_COLUMN = 1;
    public static final int SORT_ASCENDING = 0;
    public static final int SORT_DESCENDING = 16;

    private static String getVersion() {
        return "4.5.2";
    }

    private static String getNativeLibraryName() {
        return "opencv_java452";
    }

    private static int getVersionMajorJ() {
        return 4;
    }

    private static int getVersionMinorJ() {
        return 5;
    }

    private static int getVersionRevisionJ() {
        return 2;
    }

    private static String getVersionStatusJ() {
        return "";
    }

    public static float cubeRoot(float val) {
        return Core.cubeRoot_0(val);
    }

    public static float fastAtan2(float y, float x) {
        return Core.fastAtan2_0(y, x);
    }

    public static boolean useIPP() {
        return Core.useIPP_0();
    }

    public static void setUseIPP(boolean flag) {
        Core.setUseIPP_0(flag);
    }

    public static String getIppVersion() {
        return Core.getIppVersion_0();
    }

    public static boolean useIPP_NotExact() {
        return Core.useIPP_NotExact_0();
    }

    public static void setUseIPP_NotExact(boolean flag) {
        Core.setUseIPP_NotExact_0(flag);
    }

    public static int borderInterpolate(int p, int len, int borderType) {
        return Core.borderInterpolate_0(p, len, borderType);
    }

    public static void copyMakeBorder(Mat src, Mat dst, int top, int bottom, int left, int right, int borderType, Scalar value) {
        Core.copyMakeBorder_0(src.nativeObj, dst.nativeObj, top, bottom, left, right, borderType, value.val[0], value.val[1], value.val[2], value.val[3]);
    }

    public static void copyMakeBorder(Mat src, Mat dst, int top, int bottom, int left, int right, int borderType) {
        Core.copyMakeBorder_1(src.nativeObj, dst.nativeObj, top, bottom, left, right, borderType);
    }

    public static void add(Mat src1, Mat src2, Mat dst, Mat mask, int dtype) {
        Core.add_0(src1.nativeObj, src2.nativeObj, dst.nativeObj, mask.nativeObj, dtype);
    }

    public static void add(Mat src1, Mat src2, Mat dst, Mat mask) {
        Core.add_1(src1.nativeObj, src2.nativeObj, dst.nativeObj, mask.nativeObj);
    }

    public static void add(Mat src1, Mat src2, Mat dst) {
        Core.add_2(src1.nativeObj, src2.nativeObj, dst.nativeObj);
    }

    public static void subtract(Mat src1, Mat src2, Mat dst, Mat mask, int dtype) {
        Core.subtract_0(src1.nativeObj, src2.nativeObj, dst.nativeObj, mask.nativeObj, dtype);
    }

    public static void subtract(Mat src1, Mat src2, Mat dst, Mat mask) {
        Core.subtract_1(src1.nativeObj, src2.nativeObj, dst.nativeObj, mask.nativeObj);
    }

    public static void subtract(Mat src1, Mat src2, Mat dst) {
        Core.subtract_2(src1.nativeObj, src2.nativeObj, dst.nativeObj);
    }

    public static void multiply(Mat src1, Mat src2, Mat dst, double scale, int dtype) {
        Core.multiply_0(src1.nativeObj, src2.nativeObj, dst.nativeObj, scale, dtype);
    }

    public static void multiply(Mat src1, Mat src2, Mat dst, double scale) {
        Core.multiply_1(src1.nativeObj, src2.nativeObj, dst.nativeObj, scale);
    }

    public static void multiply(Mat src1, Mat src2, Mat dst) {
        Core.multiply_2(src1.nativeObj, src2.nativeObj, dst.nativeObj);
    }

    public static void divide(Mat src1, Mat src2, Mat dst, double scale, int dtype) {
        Core.divide_0(src1.nativeObj, src2.nativeObj, dst.nativeObj, scale, dtype);
    }

    public static void divide(Mat src1, Mat src2, Mat dst, double scale) {
        Core.divide_1(src1.nativeObj, src2.nativeObj, dst.nativeObj, scale);
    }

    public static void divide(Mat src1, Mat src2, Mat dst) {
        Core.divide_2(src1.nativeObj, src2.nativeObj, dst.nativeObj);
    }

    public static void divide(double scale, Mat src2, Mat dst, int dtype) {
        Core.divide_3(scale, src2.nativeObj, dst.nativeObj, dtype);
    }

    public static void divide(double scale, Mat src2, Mat dst) {
        Core.divide_4(scale, src2.nativeObj, dst.nativeObj);
    }

    public static void scaleAdd(Mat src1, double alpha, Mat src2, Mat dst) {
        Core.scaleAdd_0(src1.nativeObj, alpha, src2.nativeObj, dst.nativeObj);
    }

    public static void addWeighted(Mat src1, double alpha, Mat src2, double beta, double gamma, Mat dst, int dtype) {
        Core.addWeighted_0(src1.nativeObj, alpha, src2.nativeObj, beta, gamma, dst.nativeObj, dtype);
    }

    public static void addWeighted(Mat src1, double alpha, Mat src2, double beta, double gamma, Mat dst) {
        Core.addWeighted_1(src1.nativeObj, alpha, src2.nativeObj, beta, gamma, dst.nativeObj);
    }

    public static void convertScaleAbs(Mat src, Mat dst, double alpha, double beta) {
        Core.convertScaleAbs_0(src.nativeObj, dst.nativeObj, alpha, beta);
    }

    public static void convertScaleAbs(Mat src, Mat dst, double alpha) {
        Core.convertScaleAbs_1(src.nativeObj, dst.nativeObj, alpha);
    }

    public static void convertScaleAbs(Mat src, Mat dst) {
        Core.convertScaleAbs_2(src.nativeObj, dst.nativeObj);
    }

    public static void convertFp16(Mat src, Mat dst) {
        Core.convertFp16_0(src.nativeObj, dst.nativeObj);
    }

    public static void LUT(Mat src, Mat lut, Mat dst) {
        Core.LUT_0(src.nativeObj, lut.nativeObj, dst.nativeObj);
    }

    public static Scalar sumElems(Mat src) {
        return new Scalar(Core.sumElems_0(src.nativeObj));
    }

    public static int countNonZero(Mat src) {
        return Core.countNonZero_0(src.nativeObj);
    }

    public static void findNonZero(Mat src, Mat idx) {
        Core.findNonZero_0(src.nativeObj, idx.nativeObj);
    }

    public static Scalar mean(Mat src, Mat mask) {
        return new Scalar(Core.mean_0(src.nativeObj, mask.nativeObj));
    }

    public static Scalar mean(Mat src) {
        return new Scalar(Core.mean_1(src.nativeObj));
    }

    public static void meanStdDev(Mat src, MatOfDouble mean, MatOfDouble stddev, Mat mask) {
        MatOfDouble mean_mat = mean;
        MatOfDouble stddev_mat = stddev;
        Core.meanStdDev_0(src.nativeObj, mean_mat.nativeObj, stddev_mat.nativeObj, mask.nativeObj);
    }

    public static void meanStdDev(Mat src, MatOfDouble mean, MatOfDouble stddev) {
        MatOfDouble mean_mat = mean;
        MatOfDouble stddev_mat = stddev;
        Core.meanStdDev_1(src.nativeObj, mean_mat.nativeObj, stddev_mat.nativeObj);
    }

    public static double norm(Mat src1, int normType, Mat mask) {
        return Core.norm_0(src1.nativeObj, normType, mask.nativeObj);
    }

    public static double norm(Mat src1, int normType) {
        return Core.norm_1(src1.nativeObj, normType);
    }

    public static double norm(Mat src1) {
        return Core.norm_2(src1.nativeObj);
    }

    public static double norm(Mat src1, Mat src2, int normType, Mat mask) {
        return Core.norm_3(src1.nativeObj, src2.nativeObj, normType, mask.nativeObj);
    }

    public static double norm(Mat src1, Mat src2, int normType) {
        return Core.norm_4(src1.nativeObj, src2.nativeObj, normType);
    }

    public static double norm(Mat src1, Mat src2) {
        return Core.norm_5(src1.nativeObj, src2.nativeObj);
    }

    public static double PSNR(Mat src1, Mat src2, double R) {
        return Core.PSNR_0(src1.nativeObj, src2.nativeObj, R);
    }

    public static double PSNR(Mat src1, Mat src2) {
        return Core.PSNR_1(src1.nativeObj, src2.nativeObj);
    }

    public static void batchDistance(Mat src1, Mat src2, Mat dist, int dtype, Mat nidx, int normType, int K, Mat mask, int update, boolean crosscheck) {
        Core.batchDistance_0(src1.nativeObj, src2.nativeObj, dist.nativeObj, dtype, nidx.nativeObj, normType, K, mask.nativeObj, update, crosscheck);
    }

    public static void batchDistance(Mat src1, Mat src2, Mat dist, int dtype, Mat nidx, int normType, int K, Mat mask, int update) {
        Core.batchDistance_1(src1.nativeObj, src2.nativeObj, dist.nativeObj, dtype, nidx.nativeObj, normType, K, mask.nativeObj, update);
    }

    public static void batchDistance(Mat src1, Mat src2, Mat dist, int dtype, Mat nidx, int normType, int K, Mat mask) {
        Core.batchDistance_2(src1.nativeObj, src2.nativeObj, dist.nativeObj, dtype, nidx.nativeObj, normType, K, mask.nativeObj);
    }

    public static void batchDistance(Mat src1, Mat src2, Mat dist, int dtype, Mat nidx, int normType, int K) {
        Core.batchDistance_3(src1.nativeObj, src2.nativeObj, dist.nativeObj, dtype, nidx.nativeObj, normType, K);
    }

    public static void batchDistance(Mat src1, Mat src2, Mat dist, int dtype, Mat nidx, int normType) {
        Core.batchDistance_4(src1.nativeObj, src2.nativeObj, dist.nativeObj, dtype, nidx.nativeObj, normType);
    }

    public static void batchDistance(Mat src1, Mat src2, Mat dist, int dtype, Mat nidx) {
        Core.batchDistance_5(src1.nativeObj, src2.nativeObj, dist.nativeObj, dtype, nidx.nativeObj);
    }

    public static void normalize(Mat src, Mat dst, double alpha, double beta, int norm_type, int dtype, Mat mask) {
        Core.normalize_0(src.nativeObj, dst.nativeObj, alpha, beta, norm_type, dtype, mask.nativeObj);
    }

    public static void normalize(Mat src, Mat dst, double alpha, double beta, int norm_type, int dtype) {
        Core.normalize_1(src.nativeObj, dst.nativeObj, alpha, beta, norm_type, dtype);
    }

    public static void normalize(Mat src, Mat dst, double alpha, double beta, int norm_type) {
        Core.normalize_2(src.nativeObj, dst.nativeObj, alpha, beta, norm_type);
    }

    public static void normalize(Mat src, Mat dst, double alpha, double beta) {
        Core.normalize_3(src.nativeObj, dst.nativeObj, alpha, beta);
    }

    public static void normalize(Mat src, Mat dst, double alpha) {
        Core.normalize_4(src.nativeObj, dst.nativeObj, alpha);
    }

    public static void normalize(Mat src, Mat dst) {
        Core.normalize_5(src.nativeObj, dst.nativeObj);
    }

    public static void reduce(Mat src, Mat dst, int dim, int rtype, int dtype) {
        Core.reduce_0(src.nativeObj, dst.nativeObj, dim, rtype, dtype);
    }

    public static void reduce(Mat src, Mat dst, int dim, int rtype) {
        Core.reduce_1(src.nativeObj, dst.nativeObj, dim, rtype);
    }

    public static void merge(List<Mat> mv, Mat dst) {
        Mat mv_mat = Converters.vector_Mat_to_Mat(mv);
        Core.merge_0(mv_mat.nativeObj, dst.nativeObj);
    }

    public static void split(Mat m, List<Mat> mv) {
        Mat mv_mat = new Mat();
        Core.split_0(m.nativeObj, mv_mat.nativeObj);
        Converters.Mat_to_vector_Mat(mv_mat, mv);
        mv_mat.release();
    }

    public static void mixChannels(List<Mat> src, List<Mat> dst, MatOfInt fromTo) {
        Mat src_mat = Converters.vector_Mat_to_Mat(src);
        Mat dst_mat = Converters.vector_Mat_to_Mat(dst);
        MatOfInt fromTo_mat = fromTo;
        Core.mixChannels_0(src_mat.nativeObj, dst_mat.nativeObj, fromTo_mat.nativeObj);
    }

    public static void extractChannel(Mat src, Mat dst, int coi) {
        Core.extractChannel_0(src.nativeObj, dst.nativeObj, coi);
    }

    public static void insertChannel(Mat src, Mat dst, int coi) {
        Core.insertChannel_0(src.nativeObj, dst.nativeObj, coi);
    }

    public static void flip(Mat src, Mat dst, int flipCode) {
        Core.flip_0(src.nativeObj, dst.nativeObj, flipCode);
    }

    public static void rotate(Mat src, Mat dst, int rotateCode) {
        Core.rotate_0(src.nativeObj, dst.nativeObj, rotateCode);
    }

    public static void repeat(Mat src, int ny, int nx, Mat dst) {
        Core.repeat_0(src.nativeObj, ny, nx, dst.nativeObj);
    }

    public static void hconcat(List<Mat> src, Mat dst) {
        Mat src_mat = Converters.vector_Mat_to_Mat(src);
        Core.hconcat_0(src_mat.nativeObj, dst.nativeObj);
    }

    public static void vconcat(List<Mat> src, Mat dst) {
        Mat src_mat = Converters.vector_Mat_to_Mat(src);
        Core.vconcat_0(src_mat.nativeObj, dst.nativeObj);
    }

    public static void bitwise_and(Mat src1, Mat src2, Mat dst, Mat mask) {
        Core.bitwise_and_0(src1.nativeObj, src2.nativeObj, dst.nativeObj, mask.nativeObj);
    }

    public static void bitwise_and(Mat src1, Mat src2, Mat dst) {
        Core.bitwise_and_1(src1.nativeObj, src2.nativeObj, dst.nativeObj);
    }

    public static void bitwise_or(Mat src1, Mat src2, Mat dst, Mat mask) {
        Core.bitwise_or_0(src1.nativeObj, src2.nativeObj, dst.nativeObj, mask.nativeObj);
    }

    public static void bitwise_or(Mat src1, Mat src2, Mat dst) {
        Core.bitwise_or_1(src1.nativeObj, src2.nativeObj, dst.nativeObj);
    }

    public static void bitwise_xor(Mat src1, Mat src2, Mat dst, Mat mask) {
        Core.bitwise_xor_0(src1.nativeObj, src2.nativeObj, dst.nativeObj, mask.nativeObj);
    }

    public static void bitwise_xor(Mat src1, Mat src2, Mat dst) {
        Core.bitwise_xor_1(src1.nativeObj, src2.nativeObj, dst.nativeObj);
    }

    public static void bitwise_not(Mat src, Mat dst, Mat mask) {
        Core.bitwise_not_0(src.nativeObj, dst.nativeObj, mask.nativeObj);
    }

    public static void bitwise_not(Mat src, Mat dst) {
        Core.bitwise_not_1(src.nativeObj, dst.nativeObj);
    }

    public static void absdiff(Mat src1, Mat src2, Mat dst) {
        Core.absdiff_0(src1.nativeObj, src2.nativeObj, dst.nativeObj);
    }

    public static void copyTo(Mat src, Mat dst, Mat mask) {
        Core.copyTo_0(src.nativeObj, dst.nativeObj, mask.nativeObj);
    }

    public static void inRange(Mat src, Scalar lowerb, Scalar upperb, Mat dst) {
        Core.inRange_0(src.nativeObj, lowerb.val[0], lowerb.val[1], lowerb.val[2], lowerb.val[3], upperb.val[0], upperb.val[1], upperb.val[2], upperb.val[3], dst.nativeObj);
    }

    public static void compare(Mat src1, Mat src2, Mat dst, int cmpop) {
        Core.compare_0(src1.nativeObj, src2.nativeObj, dst.nativeObj, cmpop);
    }

    public static void min(Mat src1, Mat src2, Mat dst) {
        Core.min_0(src1.nativeObj, src2.nativeObj, dst.nativeObj);
    }

    public static void max(Mat src1, Mat src2, Mat dst) {
        Core.max_0(src1.nativeObj, src2.nativeObj, dst.nativeObj);
    }

    public static void sqrt(Mat src, Mat dst) {
        Core.sqrt_0(src.nativeObj, dst.nativeObj);
    }

    public static void pow(Mat src, double power, Mat dst) {
        Core.pow_0(src.nativeObj, power, dst.nativeObj);
    }

    public static void exp(Mat src, Mat dst) {
        Core.exp_0(src.nativeObj, dst.nativeObj);
    }

    public static void log(Mat src, Mat dst) {
        Core.log_0(src.nativeObj, dst.nativeObj);
    }

    public static void polarToCart(Mat magnitude, Mat angle, Mat x, Mat y, boolean angleInDegrees) {
        Core.polarToCart_0(magnitude.nativeObj, angle.nativeObj, x.nativeObj, y.nativeObj, angleInDegrees);
    }

    public static void polarToCart(Mat magnitude, Mat angle, Mat x, Mat y) {
        Core.polarToCart_1(magnitude.nativeObj, angle.nativeObj, x.nativeObj, y.nativeObj);
    }

    public static void cartToPolar(Mat x, Mat y, Mat magnitude, Mat angle, boolean angleInDegrees) {
        Core.cartToPolar_0(x.nativeObj, y.nativeObj, magnitude.nativeObj, angle.nativeObj, angleInDegrees);
    }

    public static void cartToPolar(Mat x, Mat y, Mat magnitude, Mat angle) {
        Core.cartToPolar_1(x.nativeObj, y.nativeObj, magnitude.nativeObj, angle.nativeObj);
    }

    public static void phase(Mat x, Mat y, Mat angle, boolean angleInDegrees) {
        Core.phase_0(x.nativeObj, y.nativeObj, angle.nativeObj, angleInDegrees);
    }

    public static void phase(Mat x, Mat y, Mat angle) {
        Core.phase_1(x.nativeObj, y.nativeObj, angle.nativeObj);
    }

    public static void magnitude(Mat x, Mat y, Mat magnitude) {
        Core.magnitude_0(x.nativeObj, y.nativeObj, magnitude.nativeObj);
    }

    public static boolean checkRange(Mat a, boolean quiet, double minVal, double maxVal) {
        return Core.checkRange_0(a.nativeObj, quiet, minVal, maxVal);
    }

    public static boolean checkRange(Mat a, boolean quiet, double minVal) {
        return Core.checkRange_1(a.nativeObj, quiet, minVal);
    }

    public static boolean checkRange(Mat a, boolean quiet) {
        return Core.checkRange_2(a.nativeObj, quiet);
    }

    public static boolean checkRange(Mat a) {
        return Core.checkRange_4(a.nativeObj);
    }

    public static void patchNaNs(Mat a, double val) {
        Core.patchNaNs_0(a.nativeObj, val);
    }

    public static void patchNaNs(Mat a) {
        Core.patchNaNs_1(a.nativeObj);
    }

    public static void gemm(Mat src1, Mat src2, double alpha, Mat src3, double beta, Mat dst, int flags) {
        Core.gemm_0(src1.nativeObj, src2.nativeObj, alpha, src3.nativeObj, beta, dst.nativeObj, flags);
    }

    public static void gemm(Mat src1, Mat src2, double alpha, Mat src3, double beta, Mat dst) {
        Core.gemm_1(src1.nativeObj, src2.nativeObj, alpha, src3.nativeObj, beta, dst.nativeObj);
    }

    public static void mulTransposed(Mat src, Mat dst, boolean aTa, Mat delta, double scale, int dtype) {
        Core.mulTransposed_0(src.nativeObj, dst.nativeObj, aTa, delta.nativeObj, scale, dtype);
    }

    public static void mulTransposed(Mat src, Mat dst, boolean aTa, Mat delta, double scale) {
        Core.mulTransposed_1(src.nativeObj, dst.nativeObj, aTa, delta.nativeObj, scale);
    }

    public static void mulTransposed(Mat src, Mat dst, boolean aTa, Mat delta) {
        Core.mulTransposed_2(src.nativeObj, dst.nativeObj, aTa, delta.nativeObj);
    }

    public static void mulTransposed(Mat src, Mat dst, boolean aTa) {
        Core.mulTransposed_3(src.nativeObj, dst.nativeObj, aTa);
    }

    public static void transpose(Mat src, Mat dst) {
        Core.transpose_0(src.nativeObj, dst.nativeObj);
    }

    public static void transform(Mat src, Mat dst, Mat m) {
        Core.transform_0(src.nativeObj, dst.nativeObj, m.nativeObj);
    }

    public static void perspectiveTransform(Mat src, Mat dst, Mat m) {
        Core.perspectiveTransform_0(src.nativeObj, dst.nativeObj, m.nativeObj);
    }

    public static void completeSymm(Mat m, boolean lowerToUpper) {
        Core.completeSymm_0(m.nativeObj, lowerToUpper);
    }

    public static void completeSymm(Mat m) {
        Core.completeSymm_1(m.nativeObj);
    }

    public static void setIdentity(Mat mtx, Scalar s) {
        Core.setIdentity_0(mtx.nativeObj, s.val[0], s.val[1], s.val[2], s.val[3]);
    }

    public static void setIdentity(Mat mtx) {
        Core.setIdentity_1(mtx.nativeObj);
    }

    public static double determinant(Mat mtx) {
        return Core.determinant_0(mtx.nativeObj);
    }

    public static Scalar trace(Mat mtx) {
        return new Scalar(Core.trace_0(mtx.nativeObj));
    }

    public static double invert(Mat src, Mat dst, int flags) {
        return Core.invert_0(src.nativeObj, dst.nativeObj, flags);
    }

    public static double invert(Mat src, Mat dst) {
        return Core.invert_1(src.nativeObj, dst.nativeObj);
    }

    public static boolean solve(Mat src1, Mat src2, Mat dst, int flags) {
        return Core.solve_0(src1.nativeObj, src2.nativeObj, dst.nativeObj, flags);
    }

    public static boolean solve(Mat src1, Mat src2, Mat dst) {
        return Core.solve_1(src1.nativeObj, src2.nativeObj, dst.nativeObj);
    }

    public static void sort(Mat src, Mat dst, int flags) {
        Core.sort_0(src.nativeObj, dst.nativeObj, flags);
    }

    public static void sortIdx(Mat src, Mat dst, int flags) {
        Core.sortIdx_0(src.nativeObj, dst.nativeObj, flags);
    }

    public static int solveCubic(Mat coeffs, Mat roots) {
        return Core.solveCubic_0(coeffs.nativeObj, roots.nativeObj);
    }

    public static double solvePoly(Mat coeffs, Mat roots, int maxIters) {
        return Core.solvePoly_0(coeffs.nativeObj, roots.nativeObj, maxIters);
    }

    public static double solvePoly(Mat coeffs, Mat roots) {
        return Core.solvePoly_1(coeffs.nativeObj, roots.nativeObj);
    }

    public static boolean eigen(Mat src, Mat eigenvalues, Mat eigenvectors) {
        return Core.eigen_0(src.nativeObj, eigenvalues.nativeObj, eigenvectors.nativeObj);
    }

    public static boolean eigen(Mat src, Mat eigenvalues) {
        return Core.eigen_1(src.nativeObj, eigenvalues.nativeObj);
    }

    public static void eigenNonSymmetric(Mat src, Mat eigenvalues, Mat eigenvectors) {
        Core.eigenNonSymmetric_0(src.nativeObj, eigenvalues.nativeObj, eigenvectors.nativeObj);
    }

    public static void calcCovarMatrix(Mat samples, Mat covar, Mat mean, int flags, int ctype) {
        Core.calcCovarMatrix_0(samples.nativeObj, covar.nativeObj, mean.nativeObj, flags, ctype);
    }

    public static void calcCovarMatrix(Mat samples, Mat covar, Mat mean, int flags) {
        Core.calcCovarMatrix_1(samples.nativeObj, covar.nativeObj, mean.nativeObj, flags);
    }

    public static void PCACompute(Mat data, Mat mean, Mat eigenvectors, int maxComponents) {
        Core.PCACompute_0(data.nativeObj, mean.nativeObj, eigenvectors.nativeObj, maxComponents);
    }

    public static void PCACompute(Mat data, Mat mean, Mat eigenvectors) {
        Core.PCACompute_1(data.nativeObj, mean.nativeObj, eigenvectors.nativeObj);
    }

    public static void PCACompute2(Mat data, Mat mean, Mat eigenvectors, Mat eigenvalues, int maxComponents) {
        Core.PCACompute2_0(data.nativeObj, mean.nativeObj, eigenvectors.nativeObj, eigenvalues.nativeObj, maxComponents);
    }

    public static void PCACompute2(Mat data, Mat mean, Mat eigenvectors, Mat eigenvalues) {
        Core.PCACompute2_1(data.nativeObj, mean.nativeObj, eigenvectors.nativeObj, eigenvalues.nativeObj);
    }

    public static void PCACompute(Mat data, Mat mean, Mat eigenvectors, double retainedVariance) {
        Core.PCACompute_2(data.nativeObj, mean.nativeObj, eigenvectors.nativeObj, retainedVariance);
    }

    public static void PCACompute2(Mat data, Mat mean, Mat eigenvectors, Mat eigenvalues, double retainedVariance) {
        Core.PCACompute2_2(data.nativeObj, mean.nativeObj, eigenvectors.nativeObj, eigenvalues.nativeObj, retainedVariance);
    }

    public static void PCAProject(Mat data, Mat mean, Mat eigenvectors, Mat result) {
        Core.PCAProject_0(data.nativeObj, mean.nativeObj, eigenvectors.nativeObj, result.nativeObj);
    }

    public static void PCABackProject(Mat data, Mat mean, Mat eigenvectors, Mat result) {
        Core.PCABackProject_0(data.nativeObj, mean.nativeObj, eigenvectors.nativeObj, result.nativeObj);
    }

    public static void SVDecomp(Mat src, Mat w, Mat u, Mat vt, int flags) {
        Core.SVDecomp_0(src.nativeObj, w.nativeObj, u.nativeObj, vt.nativeObj, flags);
    }

    public static void SVDecomp(Mat src, Mat w, Mat u, Mat vt) {
        Core.SVDecomp_1(src.nativeObj, w.nativeObj, u.nativeObj, vt.nativeObj);
    }

    public static void SVBackSubst(Mat w, Mat u, Mat vt, Mat rhs, Mat dst) {
        Core.SVBackSubst_0(w.nativeObj, u.nativeObj, vt.nativeObj, rhs.nativeObj, dst.nativeObj);
    }

    public static double Mahalanobis(Mat v1, Mat v2, Mat icovar) {
        return Core.Mahalanobis_0(v1.nativeObj, v2.nativeObj, icovar.nativeObj);
    }

    public static void dft(Mat src, Mat dst, int flags, int nonzeroRows) {
        Core.dft_0(src.nativeObj, dst.nativeObj, flags, nonzeroRows);
    }

    public static void dft(Mat src, Mat dst, int flags) {
        Core.dft_1(src.nativeObj, dst.nativeObj, flags);
    }

    public static void dft(Mat src, Mat dst) {
        Core.dft_2(src.nativeObj, dst.nativeObj);
    }

    public static void idft(Mat src, Mat dst, int flags, int nonzeroRows) {
        Core.idft_0(src.nativeObj, dst.nativeObj, flags, nonzeroRows);
    }

    public static void idft(Mat src, Mat dst, int flags) {
        Core.idft_1(src.nativeObj, dst.nativeObj, flags);
    }

    public static void idft(Mat src, Mat dst) {
        Core.idft_2(src.nativeObj, dst.nativeObj);
    }

    public static void dct(Mat src, Mat dst, int flags) {
        Core.dct_0(src.nativeObj, dst.nativeObj, flags);
    }

    public static void dct(Mat src, Mat dst) {
        Core.dct_1(src.nativeObj, dst.nativeObj);
    }

    public static void idct(Mat src, Mat dst, int flags) {
        Core.idct_0(src.nativeObj, dst.nativeObj, flags);
    }

    public static void idct(Mat src, Mat dst) {
        Core.idct_1(src.nativeObj, dst.nativeObj);
    }

    public static void mulSpectrums(Mat a, Mat b, Mat c, int flags, boolean conjB) {
        Core.mulSpectrums_0(a.nativeObj, b.nativeObj, c.nativeObj, flags, conjB);
    }

    public static void mulSpectrums(Mat a, Mat b, Mat c, int flags) {
        Core.mulSpectrums_1(a.nativeObj, b.nativeObj, c.nativeObj, flags);
    }

    public static int getOptimalDFTSize(int vecsize) {
        return Core.getOptimalDFTSize_0(vecsize);
    }

    public static void setRNGSeed(int seed) {
        Core.setRNGSeed_0(seed);
    }

    public static void randu(Mat dst, double low, double high) {
        Core.randu_0(dst.nativeObj, low, high);
    }

    public static void randn(Mat dst, double mean, double stddev) {
        Core.randn_0(dst.nativeObj, mean, stddev);
    }

    public static void randShuffle(Mat dst, double iterFactor) {
        Core.randShuffle_0(dst.nativeObj, iterFactor);
    }

    public static void randShuffle(Mat dst) {
        Core.randShuffle_2(dst.nativeObj);
    }

    public static double kmeans(Mat data, int K, Mat bestLabels, TermCriteria criteria, int attempts, int flags, Mat centers) {
        return Core.kmeans_0(data.nativeObj, K, bestLabels.nativeObj, criteria.type, criteria.maxCount, criteria.epsilon, attempts, flags, centers.nativeObj);
    }

    public static double kmeans(Mat data, int K, Mat bestLabels, TermCriteria criteria, int attempts, int flags) {
        return Core.kmeans_1(data.nativeObj, K, bestLabels.nativeObj, criteria.type, criteria.maxCount, criteria.epsilon, attempts, flags);
    }

    public static void setNumThreads(int nthreads) {
        Core.setNumThreads_0(nthreads);
    }

    public static int getNumThreads() {
        return Core.getNumThreads_0();
    }

    @Deprecated
    public static int getThreadNum() {
        return Core.getThreadNum_0();
    }

    public static String getBuildInformation() {
        return Core.getBuildInformation_0();
    }

    public static String getVersionString() {
        return Core.getVersionString_0();
    }

    public static int getVersionMajor() {
        return Core.getVersionMajor_0();
    }

    public static int getVersionMinor() {
        return Core.getVersionMinor_0();
    }

    public static int getVersionRevision() {
        return Core.getVersionRevision_0();
    }

    public static long getTickCount() {
        return Core.getTickCount_0();
    }

    public static double getTickFrequency() {
        return Core.getTickFrequency_0();
    }

    public static long getCPUTickCount() {
        return Core.getCPUTickCount_0();
    }

    public static String getHardwareFeatureName(int feature) {
        return Core.getHardwareFeatureName_0(feature);
    }

    public static String getCPUFeaturesLine() {
        return Core.getCPUFeaturesLine_0();
    }

    public static int getNumberOfCPUs() {
        return Core.getNumberOfCPUs_0();
    }

    public static String findFile(String relative_path, boolean required, boolean silentMode) {
        return Core.findFile_0(relative_path, required, silentMode);
    }

    public static String findFile(String relative_path, boolean required) {
        return Core.findFile_1(relative_path, required);
    }

    public static String findFile(String relative_path) {
        return Core.findFile_2(relative_path);
    }

    public static String findFileOrKeep(String relative_path, boolean silentMode) {
        return Core.findFileOrKeep_0(relative_path, silentMode);
    }

    public static String findFileOrKeep(String relative_path) {
        return Core.findFileOrKeep_1(relative_path);
    }

    public static void addSamplesDataSearchPath(String path) {
        Core.addSamplesDataSearchPath_0(path);
    }

    public static void addSamplesDataSearchSubDirectory(String subdir) {
        Core.addSamplesDataSearchSubDirectory_0(subdir);
    }

    public static void setErrorVerbosity(boolean verbose) {
        Core.setErrorVerbosity_0(verbose);
    }

    public static void add(Mat src1, Scalar src2, Mat dst, Mat mask, int dtype) {
        Core.add_3(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj, mask.nativeObj, dtype);
    }

    public static void add(Mat src1, Scalar src2, Mat dst, Mat mask) {
        Core.add_4(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj, mask.nativeObj);
    }

    public static void add(Mat src1, Scalar src2, Mat dst) {
        Core.add_5(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj);
    }

    public static void subtract(Mat src1, Scalar src2, Mat dst, Mat mask, int dtype) {
        Core.subtract_3(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj, mask.nativeObj, dtype);
    }

    public static void subtract(Mat src1, Scalar src2, Mat dst, Mat mask) {
        Core.subtract_4(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj, mask.nativeObj);
    }

    public static void subtract(Mat src1, Scalar src2, Mat dst) {
        Core.subtract_5(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj);
    }

    public static void multiply(Mat src1, Scalar src2, Mat dst, double scale, int dtype) {
        Core.multiply_3(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj, scale, dtype);
    }

    public static void multiply(Mat src1, Scalar src2, Mat dst, double scale) {
        Core.multiply_4(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj, scale);
    }

    public static void multiply(Mat src1, Scalar src2, Mat dst) {
        Core.multiply_5(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj);
    }

    public static void divide(Mat src1, Scalar src2, Mat dst, double scale, int dtype) {
        Core.divide_5(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj, scale, dtype);
    }

    public static void divide(Mat src1, Scalar src2, Mat dst, double scale) {
        Core.divide_6(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj, scale);
    }

    public static void divide(Mat src1, Scalar src2, Mat dst) {
        Core.divide_7(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj);
    }

    public static void absdiff(Mat src1, Scalar src2, Mat dst) {
        Core.absdiff_1(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj);
    }

    public static void compare(Mat src1, Scalar src2, Mat dst, int cmpop) {
        Core.compare_1(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj, cmpop);
    }

    public static void min(Mat src1, Scalar src2, Mat dst) {
        Core.min_1(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj);
    }

    public static void max(Mat src1, Scalar src2, Mat dst) {
        Core.max_1(src1.nativeObj, src2.val[0], src2.val[1], src2.val[2], src2.val[3], dst.nativeObj);
    }

    public static MinMaxLocResult minMaxLoc(Mat src, Mat mask) {
        MinMaxLocResult res = new MinMaxLocResult();
        long maskNativeObj = 0L;
        if (mask != null) {
            maskNativeObj = mask.nativeObj;
        }
        double[] resarr = Core.n_minMaxLocManual(src.nativeObj, maskNativeObj);
        res.minVal = resarr[0];
        res.maxVal = resarr[1];
        res.minLoc.x = resarr[2];
        res.minLoc.y = resarr[3];
        res.maxLoc.x = resarr[4];
        res.maxLoc.y = resarr[5];
        return res;
    }

    public static MinMaxLocResult minMaxLoc(Mat src) {
        return Core.minMaxLoc(src, null);
    }

    private static native float cubeRoot_0(float var0);

    private static native float fastAtan2_0(float var0, float var1);

    private static native boolean useIPP_0();

    private static native void setUseIPP_0(boolean var0);

    private static native String getIppVersion_0();

    private static native boolean useIPP_NotExact_0();

    private static native void setUseIPP_NotExact_0(boolean var0);

    private static native int borderInterpolate_0(int var0, int var1, int var2);

    private static native void copyMakeBorder_0(long var0, long var2, int var4, int var5, int var6, int var7, int var8, double var9, double var11, double var13, double var15);

    private static native void copyMakeBorder_1(long var0, long var2, int var4, int var5, int var6, int var7, int var8);

    private static native void add_0(long var0, long var2, long var4, long var6, int var8);

    private static native void add_1(long var0, long var2, long var4, long var6);

    private static native void add_2(long var0, long var2, long var4);

    private static native void subtract_0(long var0, long var2, long var4, long var6, int var8);

    private static native void subtract_1(long var0, long var2, long var4, long var6);

    private static native void subtract_2(long var0, long var2, long var4);

    private static native void multiply_0(long var0, long var2, long var4, double var6, int var8);

    private static native void multiply_1(long var0, long var2, long var4, double var6);

    private static native void multiply_2(long var0, long var2, long var4);

    private static native void divide_0(long var0, long var2, long var4, double var6, int var8);

    private static native void divide_1(long var0, long var2, long var4, double var6);

    private static native void divide_2(long var0, long var2, long var4);

    private static native void divide_3(double var0, long var2, long var4, int var6);

    private static native void divide_4(double var0, long var2, long var4);

    private static native void scaleAdd_0(long var0, double var2, long var4, long var6);

    private static native void addWeighted_0(long var0, double var2, long var4, double var6, double var8, long var10, int var12);

    private static native void addWeighted_1(long var0, double var2, long var4, double var6, double var8, long var10);

    private static native void convertScaleAbs_0(long var0, long var2, double var4, double var6);

    private static native void convertScaleAbs_1(long var0, long var2, double var4);

    private static native void convertScaleAbs_2(long var0, long var2);

    private static native void convertFp16_0(long var0, long var2);

    private static native void LUT_0(long var0, long var2, long var4);

    private static native double[] sumElems_0(long var0);

    private static native int countNonZero_0(long var0);

    private static native void findNonZero_0(long var0, long var2);

    private static native double[] mean_0(long var0, long var2);

    private static native double[] mean_1(long var0);

    private static native void meanStdDev_0(long var0, long var2, long var4, long var6);

    private static native void meanStdDev_1(long var0, long var2, long var4);

    private static native double norm_0(long var0, int var2, long var3);

    private static native double norm_1(long var0, int var2);

    private static native double norm_2(long var0);

    private static native double norm_3(long var0, long var2, int var4, long var5);

    private static native double norm_4(long var0, long var2, int var4);

    private static native double norm_5(long var0, long var2);

    private static native double PSNR_0(long var0, long var2, double var4);

    private static native double PSNR_1(long var0, long var2);

    private static native void batchDistance_0(long var0, long var2, long var4, int var6, long var7, int var9, int var10, long var11, int var13, boolean var14);

    private static native void batchDistance_1(long var0, long var2, long var4, int var6, long var7, int var9, int var10, long var11, int var13);

    private static native void batchDistance_2(long var0, long var2, long var4, int var6, long var7, int var9, int var10, long var11);

    private static native void batchDistance_3(long var0, long var2, long var4, int var6, long var7, int var9, int var10);

    private static native void batchDistance_4(long var0, long var2, long var4, int var6, long var7, int var9);

    private static native void batchDistance_5(long var0, long var2, long var4, int var6, long var7);

    private static native void normalize_0(long var0, long var2, double var4, double var6, int var8, int var9, long var10);

    private static native void normalize_1(long var0, long var2, double var4, double var6, int var8, int var9);

    private static native void normalize_2(long var0, long var2, double var4, double var6, int var8);

    private static native void normalize_3(long var0, long var2, double var4, double var6);

    private static native void normalize_4(long var0, long var2, double var4);

    private static native void normalize_5(long var0, long var2);

    private static native void reduce_0(long var0, long var2, int var4, int var5, int var6);

    private static native void reduce_1(long var0, long var2, int var4, int var5);

    private static native void merge_0(long var0, long var2);

    private static native void split_0(long var0, long var2);

    private static native void mixChannels_0(long var0, long var2, long var4);

    private static native void extractChannel_0(long var0, long var2, int var4);

    private static native void insertChannel_0(long var0, long var2, int var4);

    private static native void flip_0(long var0, long var2, int var4);

    private static native void rotate_0(long var0, long var2, int var4);

    private static native void repeat_0(long var0, int var2, int var3, long var4);

    private static native void hconcat_0(long var0, long var2);

    private static native void vconcat_0(long var0, long var2);

    private static native void bitwise_and_0(long var0, long var2, long var4, long var6);

    private static native void bitwise_and_1(long var0, long var2, long var4);

    private static native void bitwise_or_0(long var0, long var2, long var4, long var6);

    private static native void bitwise_or_1(long var0, long var2, long var4);

    private static native void bitwise_xor_0(long var0, long var2, long var4, long var6);

    private static native void bitwise_xor_1(long var0, long var2, long var4);

    private static native void bitwise_not_0(long var0, long var2, long var4);

    private static native void bitwise_not_1(long var0, long var2);

    private static native void absdiff_0(long var0, long var2, long var4);

    private static native void copyTo_0(long var0, long var2, long var4);

    private static native void inRange_0(long var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, double var16, long var18);

    private static native void compare_0(long var0, long var2, long var4, int var6);

    private static native void min_0(long var0, long var2, long var4);

    private static native void max_0(long var0, long var2, long var4);

    private static native void sqrt_0(long var0, long var2);

    private static native void pow_0(long var0, double var2, long var4);

    private static native void exp_0(long var0, long var2);

    private static native void log_0(long var0, long var2);

    private static native void polarToCart_0(long var0, long var2, long var4, long var6, boolean var8);

    private static native void polarToCart_1(long var0, long var2, long var4, long var6);

    private static native void cartToPolar_0(long var0, long var2, long var4, long var6, boolean var8);

    private static native void cartToPolar_1(long var0, long var2, long var4, long var6);

    private static native void phase_0(long var0, long var2, long var4, boolean var6);

    private static native void phase_1(long var0, long var2, long var4);

    private static native void magnitude_0(long var0, long var2, long var4);

    private static native boolean checkRange_0(long var0, boolean var2, double var3, double var5);

    private static native boolean checkRange_1(long var0, boolean var2, double var3);

    private static native boolean checkRange_2(long var0, boolean var2);

    private static native boolean checkRange_4(long var0);

    private static native void patchNaNs_0(long var0, double var2);

    private static native void patchNaNs_1(long var0);

    private static native void gemm_0(long var0, long var2, double var4, long var6, double var8, long var10, int var12);

    private static native void gemm_1(long var0, long var2, double var4, long var6, double var8, long var10);

    private static native void mulTransposed_0(long var0, long var2, boolean var4, long var5, double var7, int var9);

    private static native void mulTransposed_1(long var0, long var2, boolean var4, long var5, double var7);

    private static native void mulTransposed_2(long var0, long var2, boolean var4, long var5);

    private static native void mulTransposed_3(long var0, long var2, boolean var4);

    private static native void transpose_0(long var0, long var2);

    private static native void transform_0(long var0, long var2, long var4);

    private static native void perspectiveTransform_0(long var0, long var2, long var4);

    private static native void completeSymm_0(long var0, boolean var2);

    private static native void completeSymm_1(long var0);

    private static native void setIdentity_0(long var0, double var2, double var4, double var6, double var8);

    private static native void setIdentity_1(long var0);

    private static native double determinant_0(long var0);

    private static native double[] trace_0(long var0);

    private static native double invert_0(long var0, long var2, int var4);

    private static native double invert_1(long var0, long var2);

    private static native boolean solve_0(long var0, long var2, long var4, int var6);

    private static native boolean solve_1(long var0, long var2, long var4);

    private static native void sort_0(long var0, long var2, int var4);

    private static native void sortIdx_0(long var0, long var2, int var4);

    private static native int solveCubic_0(long var0, long var2);

    private static native double solvePoly_0(long var0, long var2, int var4);

    private static native double solvePoly_1(long var0, long var2);

    private static native boolean eigen_0(long var0, long var2, long var4);

    private static native boolean eigen_1(long var0, long var2);

    private static native void eigenNonSymmetric_0(long var0, long var2, long var4);

    private static native void calcCovarMatrix_0(long var0, long var2, long var4, int var6, int var7);

    private static native void calcCovarMatrix_1(long var0, long var2, long var4, int var6);

    private static native void PCACompute_0(long var0, long var2, long var4, int var6);

    private static native void PCACompute_1(long var0, long var2, long var4);

    private static native void PCACompute2_0(long var0, long var2, long var4, long var6, int var8);

    private static native void PCACompute2_1(long var0, long var2, long var4, long var6);

    private static native void PCACompute_2(long var0, long var2, long var4, double var6);

    private static native void PCACompute2_2(long var0, long var2, long var4, long var6, double var8);

    private static native void PCAProject_0(long var0, long var2, long var4, long var6);

    private static native void PCABackProject_0(long var0, long var2, long var4, long var6);

    private static native void SVDecomp_0(long var0, long var2, long var4, long var6, int var8);

    private static native void SVDecomp_1(long var0, long var2, long var4, long var6);

    private static native void SVBackSubst_0(long var0, long var2, long var4, long var6, long var8);

    private static native double Mahalanobis_0(long var0, long var2, long var4);

    private static native void dft_0(long var0, long var2, int var4, int var5);

    private static native void dft_1(long var0, long var2, int var4);

    private static native void dft_2(long var0, long var2);

    private static native void idft_0(long var0, long var2, int var4, int var5);

    private static native void idft_1(long var0, long var2, int var4);

    private static native void idft_2(long var0, long var2);

    private static native void dct_0(long var0, long var2, int var4);

    private static native void dct_1(long var0, long var2);

    private static native void idct_0(long var0, long var2, int var4);

    private static native void idct_1(long var0, long var2);

    private static native void mulSpectrums_0(long var0, long var2, long var4, int var6, boolean var7);

    private static native void mulSpectrums_1(long var0, long var2, long var4, int var6);

    private static native int getOptimalDFTSize_0(int var0);

    private static native void setRNGSeed_0(int var0);

    private static native void randu_0(long var0, double var2, double var4);

    private static native void randn_0(long var0, double var2, double var4);

    private static native void randShuffle_0(long var0, double var2);

    private static native void randShuffle_2(long var0);

    private static native double kmeans_0(long var0, int var2, long var3, int var5, int var6, double var7, int var9, int var10, long var11);

    private static native double kmeans_1(long var0, int var2, long var3, int var5, int var6, double var7, int var9, int var10);

    private static native void setNumThreads_0(int var0);

    private static native int getNumThreads_0();

    private static native int getThreadNum_0();

    private static native String getBuildInformation_0();

    private static native String getVersionString_0();

    private static native int getVersionMajor_0();

    private static native int getVersionMinor_0();

    private static native int getVersionRevision_0();

    private static native long getTickCount_0();

    private static native double getTickFrequency_0();

    private static native long getCPUTickCount_0();

    private static native String getHardwareFeatureName_0(int var0);

    private static native String getCPUFeaturesLine_0();

    private static native int getNumberOfCPUs_0();

    private static native String findFile_0(String var0, boolean var1, boolean var2);

    private static native String findFile_1(String var0, boolean var1);

    private static native String findFile_2(String var0);

    private static native String findFileOrKeep_0(String var0, boolean var1);

    private static native String findFileOrKeep_1(String var0);

    private static native void addSamplesDataSearchPath_0(String var0);

    private static native void addSamplesDataSearchSubDirectory_0(String var0);

    private static native void setErrorVerbosity_0(boolean var0);

    private static native void add_3(long var0, double var2, double var4, double var6, double var8, long var10, long var12, int var14);

    private static native void add_4(long var0, double var2, double var4, double var6, double var8, long var10, long var12);

    private static native void add_5(long var0, double var2, double var4, double var6, double var8, long var10);

    private static native void subtract_3(long var0, double var2, double var4, double var6, double var8, long var10, long var12, int var14);

    private static native void subtract_4(long var0, double var2, double var4, double var6, double var8, long var10, long var12);

    private static native void subtract_5(long var0, double var2, double var4, double var6, double var8, long var10);

    private static native void multiply_3(long var0, double var2, double var4, double var6, double var8, long var10, double var12, int var14);

    private static native void multiply_4(long var0, double var2, double var4, double var6, double var8, long var10, double var12);

    private static native void multiply_5(long var0, double var2, double var4, double var6, double var8, long var10);

    private static native void divide_5(long var0, double var2, double var4, double var6, double var8, long var10, double var12, int var14);

    private static native void divide_6(long var0, double var2, double var4, double var6, double var8, long var10, double var12);

    private static native void divide_7(long var0, double var2, double var4, double var6, double var8, long var10);

    private static native void absdiff_1(long var0, double var2, double var4, double var6, double var8, long var10);

    private static native void compare_1(long var0, double var2, double var4, double var6, double var8, long var10, int var12);

    private static native void min_1(long var0, double var2, double var4, double var6, double var8, long var10);

    private static native void max_1(long var0, double var2, double var4, double var6, double var8, long var10);

    private static native double[] n_minMaxLocManual(long var0, long var2);

    public static class MinMaxLocResult {
        public double minVal = 0.0;
        public double maxVal = 0.0;
        public Point minLoc = new Point();
        public Point maxLoc = new Point();
    }
}

