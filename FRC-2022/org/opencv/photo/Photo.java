/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.photo;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Point;
import org.opencv.photo.AlignMTB;
import org.opencv.photo.CalibrateDebevec;
import org.opencv.photo.CalibrateRobertson;
import org.opencv.photo.MergeDebevec;
import org.opencv.photo.MergeMertens;
import org.opencv.photo.MergeRobertson;
import org.opencv.photo.Tonemap;
import org.opencv.photo.TonemapDrago;
import org.opencv.photo.TonemapMantiuk;
import org.opencv.photo.TonemapReinhard;
import org.opencv.utils.Converters;

public class Photo {
    public static final int INPAINT_NS = 0;
    public static final int INPAINT_TELEA = 1;
    public static final int LDR_SIZE = 256;
    public static final int NORMAL_CLONE = 1;
    public static final int MIXED_CLONE = 2;
    public static final int MONOCHROME_TRANSFER = 3;
    public static final int RECURS_FILTER = 1;
    public static final int NORMCONV_FILTER = 2;

    public static void inpaint(Mat src, Mat inpaintMask, Mat dst, double inpaintRadius, int flags) {
        Photo.inpaint_0(src.nativeObj, inpaintMask.nativeObj, dst.nativeObj, inpaintRadius, flags);
    }

    public static void fastNlMeansDenoising(Mat src, Mat dst, float h, int templateWindowSize, int searchWindowSize) {
        Photo.fastNlMeansDenoising_0(src.nativeObj, dst.nativeObj, h, templateWindowSize, searchWindowSize);
    }

    public static void fastNlMeansDenoising(Mat src, Mat dst, float h, int templateWindowSize) {
        Photo.fastNlMeansDenoising_1(src.nativeObj, dst.nativeObj, h, templateWindowSize);
    }

    public static void fastNlMeansDenoising(Mat src, Mat dst, float h) {
        Photo.fastNlMeansDenoising_2(src.nativeObj, dst.nativeObj, h);
    }

    public static void fastNlMeansDenoising(Mat src, Mat dst) {
        Photo.fastNlMeansDenoising_3(src.nativeObj, dst.nativeObj);
    }

    public static void fastNlMeansDenoising(Mat src, Mat dst, MatOfFloat h, int templateWindowSize, int searchWindowSize, int normType) {
        MatOfFloat h_mat = h;
        Photo.fastNlMeansDenoising_4(src.nativeObj, dst.nativeObj, h_mat.nativeObj, templateWindowSize, searchWindowSize, normType);
    }

    public static void fastNlMeansDenoising(Mat src, Mat dst, MatOfFloat h, int templateWindowSize, int searchWindowSize) {
        MatOfFloat h_mat = h;
        Photo.fastNlMeansDenoising_5(src.nativeObj, dst.nativeObj, h_mat.nativeObj, templateWindowSize, searchWindowSize);
    }

    public static void fastNlMeansDenoising(Mat src, Mat dst, MatOfFloat h, int templateWindowSize) {
        MatOfFloat h_mat = h;
        Photo.fastNlMeansDenoising_6(src.nativeObj, dst.nativeObj, h_mat.nativeObj, templateWindowSize);
    }

    public static void fastNlMeansDenoising(Mat src, Mat dst, MatOfFloat h) {
        MatOfFloat h_mat = h;
        Photo.fastNlMeansDenoising_7(src.nativeObj, dst.nativeObj, h_mat.nativeObj);
    }

    public static void fastNlMeansDenoisingColored(Mat src, Mat dst, float h, float hColor, int templateWindowSize, int searchWindowSize) {
        Photo.fastNlMeansDenoisingColored_0(src.nativeObj, dst.nativeObj, h, hColor, templateWindowSize, searchWindowSize);
    }

    public static void fastNlMeansDenoisingColored(Mat src, Mat dst, float h, float hColor, int templateWindowSize) {
        Photo.fastNlMeansDenoisingColored_1(src.nativeObj, dst.nativeObj, h, hColor, templateWindowSize);
    }

    public static void fastNlMeansDenoisingColored(Mat src, Mat dst, float h, float hColor) {
        Photo.fastNlMeansDenoisingColored_2(src.nativeObj, dst.nativeObj, h, hColor);
    }

    public static void fastNlMeansDenoisingColored(Mat src, Mat dst, float h) {
        Photo.fastNlMeansDenoisingColored_3(src.nativeObj, dst.nativeObj, h);
    }

    public static void fastNlMeansDenoisingColored(Mat src, Mat dst) {
        Photo.fastNlMeansDenoisingColored_4(src.nativeObj, dst.nativeObj);
    }

    public static void fastNlMeansDenoisingMulti(List<Mat> srcImgs, Mat dst, int imgToDenoiseIndex, int temporalWindowSize, float h, int templateWindowSize, int searchWindowSize) {
        Mat srcImgs_mat = Converters.vector_Mat_to_Mat(srcImgs);
        Photo.fastNlMeansDenoisingMulti_0(srcImgs_mat.nativeObj, dst.nativeObj, imgToDenoiseIndex, temporalWindowSize, h, templateWindowSize, searchWindowSize);
    }

    public static void fastNlMeansDenoisingMulti(List<Mat> srcImgs, Mat dst, int imgToDenoiseIndex, int temporalWindowSize, float h, int templateWindowSize) {
        Mat srcImgs_mat = Converters.vector_Mat_to_Mat(srcImgs);
        Photo.fastNlMeansDenoisingMulti_1(srcImgs_mat.nativeObj, dst.nativeObj, imgToDenoiseIndex, temporalWindowSize, h, templateWindowSize);
    }

    public static void fastNlMeansDenoisingMulti(List<Mat> srcImgs, Mat dst, int imgToDenoiseIndex, int temporalWindowSize, float h) {
        Mat srcImgs_mat = Converters.vector_Mat_to_Mat(srcImgs);
        Photo.fastNlMeansDenoisingMulti_2(srcImgs_mat.nativeObj, dst.nativeObj, imgToDenoiseIndex, temporalWindowSize, h);
    }

    public static void fastNlMeansDenoisingMulti(List<Mat> srcImgs, Mat dst, int imgToDenoiseIndex, int temporalWindowSize) {
        Mat srcImgs_mat = Converters.vector_Mat_to_Mat(srcImgs);
        Photo.fastNlMeansDenoisingMulti_3(srcImgs_mat.nativeObj, dst.nativeObj, imgToDenoiseIndex, temporalWindowSize);
    }

    public static void fastNlMeansDenoisingMulti(List<Mat> srcImgs, Mat dst, int imgToDenoiseIndex, int temporalWindowSize, MatOfFloat h, int templateWindowSize, int searchWindowSize, int normType) {
        Mat srcImgs_mat = Converters.vector_Mat_to_Mat(srcImgs);
        MatOfFloat h_mat = h;
        Photo.fastNlMeansDenoisingMulti_4(srcImgs_mat.nativeObj, dst.nativeObj, imgToDenoiseIndex, temporalWindowSize, h_mat.nativeObj, templateWindowSize, searchWindowSize, normType);
    }

    public static void fastNlMeansDenoisingMulti(List<Mat> srcImgs, Mat dst, int imgToDenoiseIndex, int temporalWindowSize, MatOfFloat h, int templateWindowSize, int searchWindowSize) {
        Mat srcImgs_mat = Converters.vector_Mat_to_Mat(srcImgs);
        MatOfFloat h_mat = h;
        Photo.fastNlMeansDenoisingMulti_5(srcImgs_mat.nativeObj, dst.nativeObj, imgToDenoiseIndex, temporalWindowSize, h_mat.nativeObj, templateWindowSize, searchWindowSize);
    }

    public static void fastNlMeansDenoisingMulti(List<Mat> srcImgs, Mat dst, int imgToDenoiseIndex, int temporalWindowSize, MatOfFloat h, int templateWindowSize) {
        Mat srcImgs_mat = Converters.vector_Mat_to_Mat(srcImgs);
        MatOfFloat h_mat = h;
        Photo.fastNlMeansDenoisingMulti_6(srcImgs_mat.nativeObj, dst.nativeObj, imgToDenoiseIndex, temporalWindowSize, h_mat.nativeObj, templateWindowSize);
    }

    public static void fastNlMeansDenoisingMulti(List<Mat> srcImgs, Mat dst, int imgToDenoiseIndex, int temporalWindowSize, MatOfFloat h) {
        Mat srcImgs_mat = Converters.vector_Mat_to_Mat(srcImgs);
        MatOfFloat h_mat = h;
        Photo.fastNlMeansDenoisingMulti_7(srcImgs_mat.nativeObj, dst.nativeObj, imgToDenoiseIndex, temporalWindowSize, h_mat.nativeObj);
    }

    public static void fastNlMeansDenoisingColoredMulti(List<Mat> srcImgs, Mat dst, int imgToDenoiseIndex, int temporalWindowSize, float h, float hColor, int templateWindowSize, int searchWindowSize) {
        Mat srcImgs_mat = Converters.vector_Mat_to_Mat(srcImgs);
        Photo.fastNlMeansDenoisingColoredMulti_0(srcImgs_mat.nativeObj, dst.nativeObj, imgToDenoiseIndex, temporalWindowSize, h, hColor, templateWindowSize, searchWindowSize);
    }

    public static void fastNlMeansDenoisingColoredMulti(List<Mat> srcImgs, Mat dst, int imgToDenoiseIndex, int temporalWindowSize, float h, float hColor, int templateWindowSize) {
        Mat srcImgs_mat = Converters.vector_Mat_to_Mat(srcImgs);
        Photo.fastNlMeansDenoisingColoredMulti_1(srcImgs_mat.nativeObj, dst.nativeObj, imgToDenoiseIndex, temporalWindowSize, h, hColor, templateWindowSize);
    }

    public static void fastNlMeansDenoisingColoredMulti(List<Mat> srcImgs, Mat dst, int imgToDenoiseIndex, int temporalWindowSize, float h, float hColor) {
        Mat srcImgs_mat = Converters.vector_Mat_to_Mat(srcImgs);
        Photo.fastNlMeansDenoisingColoredMulti_2(srcImgs_mat.nativeObj, dst.nativeObj, imgToDenoiseIndex, temporalWindowSize, h, hColor);
    }

    public static void fastNlMeansDenoisingColoredMulti(List<Mat> srcImgs, Mat dst, int imgToDenoiseIndex, int temporalWindowSize, float h) {
        Mat srcImgs_mat = Converters.vector_Mat_to_Mat(srcImgs);
        Photo.fastNlMeansDenoisingColoredMulti_3(srcImgs_mat.nativeObj, dst.nativeObj, imgToDenoiseIndex, temporalWindowSize, h);
    }

    public static void fastNlMeansDenoisingColoredMulti(List<Mat> srcImgs, Mat dst, int imgToDenoiseIndex, int temporalWindowSize) {
        Mat srcImgs_mat = Converters.vector_Mat_to_Mat(srcImgs);
        Photo.fastNlMeansDenoisingColoredMulti_4(srcImgs_mat.nativeObj, dst.nativeObj, imgToDenoiseIndex, temporalWindowSize);
    }

    public static void denoise_TVL1(List<Mat> observations, Mat result, double lambda, int niters) {
        Mat observations_mat = Converters.vector_Mat_to_Mat(observations);
        Photo.denoise_TVL1_0(observations_mat.nativeObj, result.nativeObj, lambda, niters);
    }

    public static void denoise_TVL1(List<Mat> observations, Mat result, double lambda) {
        Mat observations_mat = Converters.vector_Mat_to_Mat(observations);
        Photo.denoise_TVL1_1(observations_mat.nativeObj, result.nativeObj, lambda);
    }

    public static void denoise_TVL1(List<Mat> observations, Mat result) {
        Mat observations_mat = Converters.vector_Mat_to_Mat(observations);
        Photo.denoise_TVL1_2(observations_mat.nativeObj, result.nativeObj);
    }

    public static Tonemap createTonemap(float gamma) {
        return Tonemap.__fromPtr__(Photo.createTonemap_0(gamma));
    }

    public static Tonemap createTonemap() {
        return Tonemap.__fromPtr__(Photo.createTonemap_1());
    }

    public static TonemapDrago createTonemapDrago(float gamma, float saturation, float bias) {
        return TonemapDrago.__fromPtr__(Photo.createTonemapDrago_0(gamma, saturation, bias));
    }

    public static TonemapDrago createTonemapDrago(float gamma, float saturation) {
        return TonemapDrago.__fromPtr__(Photo.createTonemapDrago_1(gamma, saturation));
    }

    public static TonemapDrago createTonemapDrago(float gamma) {
        return TonemapDrago.__fromPtr__(Photo.createTonemapDrago_2(gamma));
    }

    public static TonemapDrago createTonemapDrago() {
        return TonemapDrago.__fromPtr__(Photo.createTonemapDrago_3());
    }

    public static TonemapReinhard createTonemapReinhard(float gamma, float intensity, float light_adapt, float color_adapt) {
        return TonemapReinhard.__fromPtr__(Photo.createTonemapReinhard_0(gamma, intensity, light_adapt, color_adapt));
    }

    public static TonemapReinhard createTonemapReinhard(float gamma, float intensity, float light_adapt) {
        return TonemapReinhard.__fromPtr__(Photo.createTonemapReinhard_1(gamma, intensity, light_adapt));
    }

    public static TonemapReinhard createTonemapReinhard(float gamma, float intensity) {
        return TonemapReinhard.__fromPtr__(Photo.createTonemapReinhard_2(gamma, intensity));
    }

    public static TonemapReinhard createTonemapReinhard(float gamma) {
        return TonemapReinhard.__fromPtr__(Photo.createTonemapReinhard_3(gamma));
    }

    public static TonemapReinhard createTonemapReinhard() {
        return TonemapReinhard.__fromPtr__(Photo.createTonemapReinhard_4());
    }

    public static TonemapMantiuk createTonemapMantiuk(float gamma, float scale, float saturation) {
        return TonemapMantiuk.__fromPtr__(Photo.createTonemapMantiuk_0(gamma, scale, saturation));
    }

    public static TonemapMantiuk createTonemapMantiuk(float gamma, float scale) {
        return TonemapMantiuk.__fromPtr__(Photo.createTonemapMantiuk_1(gamma, scale));
    }

    public static TonemapMantiuk createTonemapMantiuk(float gamma) {
        return TonemapMantiuk.__fromPtr__(Photo.createTonemapMantiuk_2(gamma));
    }

    public static TonemapMantiuk createTonemapMantiuk() {
        return TonemapMantiuk.__fromPtr__(Photo.createTonemapMantiuk_3());
    }

    public static AlignMTB createAlignMTB(int max_bits, int exclude_range, boolean cut) {
        return AlignMTB.__fromPtr__(Photo.createAlignMTB_0(max_bits, exclude_range, cut));
    }

    public static AlignMTB createAlignMTB(int max_bits, int exclude_range) {
        return AlignMTB.__fromPtr__(Photo.createAlignMTB_1(max_bits, exclude_range));
    }

    public static AlignMTB createAlignMTB(int max_bits) {
        return AlignMTB.__fromPtr__(Photo.createAlignMTB_2(max_bits));
    }

    public static AlignMTB createAlignMTB() {
        return AlignMTB.__fromPtr__(Photo.createAlignMTB_3());
    }

    public static CalibrateDebevec createCalibrateDebevec(int samples, float lambda, boolean random) {
        return CalibrateDebevec.__fromPtr__(Photo.createCalibrateDebevec_0(samples, lambda, random));
    }

    public static CalibrateDebevec createCalibrateDebevec(int samples, float lambda) {
        return CalibrateDebevec.__fromPtr__(Photo.createCalibrateDebevec_1(samples, lambda));
    }

    public static CalibrateDebevec createCalibrateDebevec(int samples) {
        return CalibrateDebevec.__fromPtr__(Photo.createCalibrateDebevec_2(samples));
    }

    public static CalibrateDebevec createCalibrateDebevec() {
        return CalibrateDebevec.__fromPtr__(Photo.createCalibrateDebevec_3());
    }

    public static CalibrateRobertson createCalibrateRobertson(int max_iter, float threshold) {
        return CalibrateRobertson.__fromPtr__(Photo.createCalibrateRobertson_0(max_iter, threshold));
    }

    public static CalibrateRobertson createCalibrateRobertson(int max_iter) {
        return CalibrateRobertson.__fromPtr__(Photo.createCalibrateRobertson_1(max_iter));
    }

    public static CalibrateRobertson createCalibrateRobertson() {
        return CalibrateRobertson.__fromPtr__(Photo.createCalibrateRobertson_2());
    }

    public static MergeDebevec createMergeDebevec() {
        return MergeDebevec.__fromPtr__(Photo.createMergeDebevec_0());
    }

    public static MergeMertens createMergeMertens(float contrast_weight, float saturation_weight, float exposure_weight) {
        return MergeMertens.__fromPtr__(Photo.createMergeMertens_0(contrast_weight, saturation_weight, exposure_weight));
    }

    public static MergeMertens createMergeMertens(float contrast_weight, float saturation_weight) {
        return MergeMertens.__fromPtr__(Photo.createMergeMertens_1(contrast_weight, saturation_weight));
    }

    public static MergeMertens createMergeMertens(float contrast_weight) {
        return MergeMertens.__fromPtr__(Photo.createMergeMertens_2(contrast_weight));
    }

    public static MergeMertens createMergeMertens() {
        return MergeMertens.__fromPtr__(Photo.createMergeMertens_3());
    }

    public static MergeRobertson createMergeRobertson() {
        return MergeRobertson.__fromPtr__(Photo.createMergeRobertson_0());
    }

    public static void decolor(Mat src, Mat grayscale, Mat color_boost) {
        Photo.decolor_0(src.nativeObj, grayscale.nativeObj, color_boost.nativeObj);
    }

    public static void seamlessClone(Mat src, Mat dst, Mat mask, Point p, Mat blend, int flags) {
        Photo.seamlessClone_0(src.nativeObj, dst.nativeObj, mask.nativeObj, p.x, p.y, blend.nativeObj, flags);
    }

    public static void colorChange(Mat src, Mat mask, Mat dst, float red_mul, float green_mul, float blue_mul) {
        Photo.colorChange_0(src.nativeObj, mask.nativeObj, dst.nativeObj, red_mul, green_mul, blue_mul);
    }

    public static void colorChange(Mat src, Mat mask, Mat dst, float red_mul, float green_mul) {
        Photo.colorChange_1(src.nativeObj, mask.nativeObj, dst.nativeObj, red_mul, green_mul);
    }

    public static void colorChange(Mat src, Mat mask, Mat dst, float red_mul) {
        Photo.colorChange_2(src.nativeObj, mask.nativeObj, dst.nativeObj, red_mul);
    }

    public static void colorChange(Mat src, Mat mask, Mat dst) {
        Photo.colorChange_3(src.nativeObj, mask.nativeObj, dst.nativeObj);
    }

    public static void illuminationChange(Mat src, Mat mask, Mat dst, float alpha, float beta) {
        Photo.illuminationChange_0(src.nativeObj, mask.nativeObj, dst.nativeObj, alpha, beta);
    }

    public static void illuminationChange(Mat src, Mat mask, Mat dst, float alpha) {
        Photo.illuminationChange_1(src.nativeObj, mask.nativeObj, dst.nativeObj, alpha);
    }

    public static void illuminationChange(Mat src, Mat mask, Mat dst) {
        Photo.illuminationChange_2(src.nativeObj, mask.nativeObj, dst.nativeObj);
    }

    public static void textureFlattening(Mat src, Mat mask, Mat dst, float low_threshold, float high_threshold, int kernel_size) {
        Photo.textureFlattening_0(src.nativeObj, mask.nativeObj, dst.nativeObj, low_threshold, high_threshold, kernel_size);
    }

    public static void textureFlattening(Mat src, Mat mask, Mat dst, float low_threshold, float high_threshold) {
        Photo.textureFlattening_1(src.nativeObj, mask.nativeObj, dst.nativeObj, low_threshold, high_threshold);
    }

    public static void textureFlattening(Mat src, Mat mask, Mat dst, float low_threshold) {
        Photo.textureFlattening_2(src.nativeObj, mask.nativeObj, dst.nativeObj, low_threshold);
    }

    public static void textureFlattening(Mat src, Mat mask, Mat dst) {
        Photo.textureFlattening_3(src.nativeObj, mask.nativeObj, dst.nativeObj);
    }

    public static void edgePreservingFilter(Mat src, Mat dst, int flags, float sigma_s, float sigma_r) {
        Photo.edgePreservingFilter_0(src.nativeObj, dst.nativeObj, flags, sigma_s, sigma_r);
    }

    public static void edgePreservingFilter(Mat src, Mat dst, int flags, float sigma_s) {
        Photo.edgePreservingFilter_1(src.nativeObj, dst.nativeObj, flags, sigma_s);
    }

    public static void edgePreservingFilter(Mat src, Mat dst, int flags) {
        Photo.edgePreservingFilter_2(src.nativeObj, dst.nativeObj, flags);
    }

    public static void edgePreservingFilter(Mat src, Mat dst) {
        Photo.edgePreservingFilter_3(src.nativeObj, dst.nativeObj);
    }

    public static void detailEnhance(Mat src, Mat dst, float sigma_s, float sigma_r) {
        Photo.detailEnhance_0(src.nativeObj, dst.nativeObj, sigma_s, sigma_r);
    }

    public static void detailEnhance(Mat src, Mat dst, float sigma_s) {
        Photo.detailEnhance_1(src.nativeObj, dst.nativeObj, sigma_s);
    }

    public static void detailEnhance(Mat src, Mat dst) {
        Photo.detailEnhance_2(src.nativeObj, dst.nativeObj);
    }

    public static void pencilSketch(Mat src, Mat dst1, Mat dst2, float sigma_s, float sigma_r, float shade_factor) {
        Photo.pencilSketch_0(src.nativeObj, dst1.nativeObj, dst2.nativeObj, sigma_s, sigma_r, shade_factor);
    }

    public static void pencilSketch(Mat src, Mat dst1, Mat dst2, float sigma_s, float sigma_r) {
        Photo.pencilSketch_1(src.nativeObj, dst1.nativeObj, dst2.nativeObj, sigma_s, sigma_r);
    }

    public static void pencilSketch(Mat src, Mat dst1, Mat dst2, float sigma_s) {
        Photo.pencilSketch_2(src.nativeObj, dst1.nativeObj, dst2.nativeObj, sigma_s);
    }

    public static void pencilSketch(Mat src, Mat dst1, Mat dst2) {
        Photo.pencilSketch_3(src.nativeObj, dst1.nativeObj, dst2.nativeObj);
    }

    public static void stylization(Mat src, Mat dst, float sigma_s, float sigma_r) {
        Photo.stylization_0(src.nativeObj, dst.nativeObj, sigma_s, sigma_r);
    }

    public static void stylization(Mat src, Mat dst, float sigma_s) {
        Photo.stylization_1(src.nativeObj, dst.nativeObj, sigma_s);
    }

    public static void stylization(Mat src, Mat dst) {
        Photo.stylization_2(src.nativeObj, dst.nativeObj);
    }

    private static native void inpaint_0(long var0, long var2, long var4, double var6, int var8);

    private static native void fastNlMeansDenoising_0(long var0, long var2, float var4, int var5, int var6);

    private static native void fastNlMeansDenoising_1(long var0, long var2, float var4, int var5);

    private static native void fastNlMeansDenoising_2(long var0, long var2, float var4);

    private static native void fastNlMeansDenoising_3(long var0, long var2);

    private static native void fastNlMeansDenoising_4(long var0, long var2, long var4, int var6, int var7, int var8);

    private static native void fastNlMeansDenoising_5(long var0, long var2, long var4, int var6, int var7);

    private static native void fastNlMeansDenoising_6(long var0, long var2, long var4, int var6);

    private static native void fastNlMeansDenoising_7(long var0, long var2, long var4);

    private static native void fastNlMeansDenoisingColored_0(long var0, long var2, float var4, float var5, int var6, int var7);

    private static native void fastNlMeansDenoisingColored_1(long var0, long var2, float var4, float var5, int var6);

    private static native void fastNlMeansDenoisingColored_2(long var0, long var2, float var4, float var5);

    private static native void fastNlMeansDenoisingColored_3(long var0, long var2, float var4);

    private static native void fastNlMeansDenoisingColored_4(long var0, long var2);

    private static native void fastNlMeansDenoisingMulti_0(long var0, long var2, int var4, int var5, float var6, int var7, int var8);

    private static native void fastNlMeansDenoisingMulti_1(long var0, long var2, int var4, int var5, float var6, int var7);

    private static native void fastNlMeansDenoisingMulti_2(long var0, long var2, int var4, int var5, float var6);

    private static native void fastNlMeansDenoisingMulti_3(long var0, long var2, int var4, int var5);

    private static native void fastNlMeansDenoisingMulti_4(long var0, long var2, int var4, int var5, long var6, int var8, int var9, int var10);

    private static native void fastNlMeansDenoisingMulti_5(long var0, long var2, int var4, int var5, long var6, int var8, int var9);

    private static native void fastNlMeansDenoisingMulti_6(long var0, long var2, int var4, int var5, long var6, int var8);

    private static native void fastNlMeansDenoisingMulti_7(long var0, long var2, int var4, int var5, long var6);

    private static native void fastNlMeansDenoisingColoredMulti_0(long var0, long var2, int var4, int var5, float var6, float var7, int var8, int var9);

    private static native void fastNlMeansDenoisingColoredMulti_1(long var0, long var2, int var4, int var5, float var6, float var7, int var8);

    private static native void fastNlMeansDenoisingColoredMulti_2(long var0, long var2, int var4, int var5, float var6, float var7);

    private static native void fastNlMeansDenoisingColoredMulti_3(long var0, long var2, int var4, int var5, float var6);

    private static native void fastNlMeansDenoisingColoredMulti_4(long var0, long var2, int var4, int var5);

    private static native void denoise_TVL1_0(long var0, long var2, double var4, int var6);

    private static native void denoise_TVL1_1(long var0, long var2, double var4);

    private static native void denoise_TVL1_2(long var0, long var2);

    private static native long createTonemap_0(float var0);

    private static native long createTonemap_1();

    private static native long createTonemapDrago_0(float var0, float var1, float var2);

    private static native long createTonemapDrago_1(float var0, float var1);

    private static native long createTonemapDrago_2(float var0);

    private static native long createTonemapDrago_3();

    private static native long createTonemapReinhard_0(float var0, float var1, float var2, float var3);

    private static native long createTonemapReinhard_1(float var0, float var1, float var2);

    private static native long createTonemapReinhard_2(float var0, float var1);

    private static native long createTonemapReinhard_3(float var0);

    private static native long createTonemapReinhard_4();

    private static native long createTonemapMantiuk_0(float var0, float var1, float var2);

    private static native long createTonemapMantiuk_1(float var0, float var1);

    private static native long createTonemapMantiuk_2(float var0);

    private static native long createTonemapMantiuk_3();

    private static native long createAlignMTB_0(int var0, int var1, boolean var2);

    private static native long createAlignMTB_1(int var0, int var1);

    private static native long createAlignMTB_2(int var0);

    private static native long createAlignMTB_3();

    private static native long createCalibrateDebevec_0(int var0, float var1, boolean var2);

    private static native long createCalibrateDebevec_1(int var0, float var1);

    private static native long createCalibrateDebevec_2(int var0);

    private static native long createCalibrateDebevec_3();

    private static native long createCalibrateRobertson_0(int var0, float var1);

    private static native long createCalibrateRobertson_1(int var0);

    private static native long createCalibrateRobertson_2();

    private static native long createMergeDebevec_0();

    private static native long createMergeMertens_0(float var0, float var1, float var2);

    private static native long createMergeMertens_1(float var0, float var1);

    private static native long createMergeMertens_2(float var0);

    private static native long createMergeMertens_3();

    private static native long createMergeRobertson_0();

    private static native void decolor_0(long var0, long var2, long var4);

    private static native void seamlessClone_0(long var0, long var2, long var4, double var6, double var8, long var10, int var12);

    private static native void colorChange_0(long var0, long var2, long var4, float var6, float var7, float var8);

    private static native void colorChange_1(long var0, long var2, long var4, float var6, float var7);

    private static native void colorChange_2(long var0, long var2, long var4, float var6);

    private static native void colorChange_3(long var0, long var2, long var4);

    private static native void illuminationChange_0(long var0, long var2, long var4, float var6, float var7);

    private static native void illuminationChange_1(long var0, long var2, long var4, float var6);

    private static native void illuminationChange_2(long var0, long var2, long var4);

    private static native void textureFlattening_0(long var0, long var2, long var4, float var6, float var7, int var8);

    private static native void textureFlattening_1(long var0, long var2, long var4, float var6, float var7);

    private static native void textureFlattening_2(long var0, long var2, long var4, float var6);

    private static native void textureFlattening_3(long var0, long var2, long var4);

    private static native void edgePreservingFilter_0(long var0, long var2, int var4, float var5, float var6);

    private static native void edgePreservingFilter_1(long var0, long var2, int var4, float var5);

    private static native void edgePreservingFilter_2(long var0, long var2, int var4);

    private static native void edgePreservingFilter_3(long var0, long var2);

    private static native void detailEnhance_0(long var0, long var2, float var4, float var5);

    private static native void detailEnhance_1(long var0, long var2, float var4);

    private static native void detailEnhance_2(long var0, long var2);

    private static native void pencilSketch_0(long var0, long var2, long var4, float var6, float var7, float var8);

    private static native void pencilSketch_1(long var0, long var2, long var4, float var6, float var7);

    private static native void pencilSketch_2(long var0, long var2, long var4, float var6);

    private static native void pencilSketch_3(long var0, long var2, long var4);

    private static native void stylization_0(long var0, long var2, float var4, float var5);

    private static native void stylization_1(long var0, long var2, float var4);

    private static native void stylization_2(long var0, long var2);
}

