/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.imgcodecs;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.utils.Converters;

public class Imgcodecs {
    public static final int IMREAD_UNCHANGED = -1;
    public static final int IMREAD_GRAYSCALE = 0;
    public static final int IMREAD_COLOR = 1;
    public static final int IMREAD_ANYDEPTH = 2;
    public static final int IMREAD_ANYCOLOR = 4;
    public static final int IMREAD_LOAD_GDAL = 8;
    public static final int IMREAD_REDUCED_GRAYSCALE_2 = 16;
    public static final int IMREAD_REDUCED_COLOR_2 = 17;
    public static final int IMREAD_REDUCED_GRAYSCALE_4 = 32;
    public static final int IMREAD_REDUCED_COLOR_4 = 33;
    public static final int IMREAD_REDUCED_GRAYSCALE_8 = 64;
    public static final int IMREAD_REDUCED_COLOR_8 = 65;
    public static final int IMREAD_IGNORE_ORIENTATION = 128;
    public static final int IMWRITE_EXR_COMPRESSION_NO = 0;
    public static final int IMWRITE_EXR_COMPRESSION_RLE = 1;
    public static final int IMWRITE_EXR_COMPRESSION_ZIPS = 2;
    public static final int IMWRITE_EXR_COMPRESSION_ZIP = 3;
    public static final int IMWRITE_EXR_COMPRESSION_PIZ = 4;
    public static final int IMWRITE_EXR_COMPRESSION_PXR24 = 5;
    public static final int IMWRITE_EXR_COMPRESSION_B44 = 6;
    public static final int IMWRITE_EXR_COMPRESSION_B44A = 7;
    public static final int IMWRITE_EXR_COMPRESSION_DWAA = 8;
    public static final int IMWRITE_EXR_COMPRESSION_DWAB = 9;
    public static final int IMWRITE_EXR_TYPE_HALF = 1;
    public static final int IMWRITE_EXR_TYPE_FLOAT = 2;
    public static final int IMWRITE_JPEG_QUALITY = 1;
    public static final int IMWRITE_JPEG_PROGRESSIVE = 2;
    public static final int IMWRITE_JPEG_OPTIMIZE = 3;
    public static final int IMWRITE_JPEG_RST_INTERVAL = 4;
    public static final int IMWRITE_JPEG_LUMA_QUALITY = 5;
    public static final int IMWRITE_JPEG_CHROMA_QUALITY = 6;
    public static final int IMWRITE_PNG_COMPRESSION = 16;
    public static final int IMWRITE_PNG_STRATEGY = 17;
    public static final int IMWRITE_PNG_BILEVEL = 18;
    public static final int IMWRITE_PXM_BINARY = 32;
    public static final int IMWRITE_EXR_TYPE = 48;
    public static final int IMWRITE_EXR_COMPRESSION = 49;
    public static final int IMWRITE_WEBP_QUALITY = 64;
    public static final int IMWRITE_PAM_TUPLETYPE = 128;
    public static final int IMWRITE_TIFF_RESUNIT = 256;
    public static final int IMWRITE_TIFF_XDPI = 257;
    public static final int IMWRITE_TIFF_YDPI = 258;
    public static final int IMWRITE_TIFF_COMPRESSION = 259;
    public static final int IMWRITE_JPEG2000_COMPRESSION_X1000 = 272;
    public static final int IMWRITE_PAM_FORMAT_NULL = 0;
    public static final int IMWRITE_PAM_FORMAT_BLACKANDWHITE = 1;
    public static final int IMWRITE_PAM_FORMAT_GRAYSCALE = 2;
    public static final int IMWRITE_PAM_FORMAT_GRAYSCALE_ALPHA = 3;
    public static final int IMWRITE_PAM_FORMAT_RGB = 4;
    public static final int IMWRITE_PAM_FORMAT_RGB_ALPHA = 5;
    public static final int IMWRITE_PNG_STRATEGY_DEFAULT = 0;
    public static final int IMWRITE_PNG_STRATEGY_FILTERED = 1;
    public static final int IMWRITE_PNG_STRATEGY_HUFFMAN_ONLY = 2;
    public static final int IMWRITE_PNG_STRATEGY_RLE = 3;
    public static final int IMWRITE_PNG_STRATEGY_FIXED = 4;

    public static Mat imread(String filename, int flags) {
        return new Mat(Imgcodecs.imread_0(filename, flags));
    }

    public static Mat imread(String filename) {
        return new Mat(Imgcodecs.imread_1(filename));
    }

    public static boolean imreadmulti(String filename, List<Mat> mats, int flags) {
        Mat mats_mat = new Mat();
        boolean retVal = Imgcodecs.imreadmulti_0(filename, mats_mat.nativeObj, flags);
        Converters.Mat_to_vector_Mat(mats_mat, mats);
        mats_mat.release();
        return retVal;
    }

    public static boolean imreadmulti(String filename, List<Mat> mats) {
        Mat mats_mat = new Mat();
        boolean retVal = Imgcodecs.imreadmulti_1(filename, mats_mat.nativeObj);
        Converters.Mat_to_vector_Mat(mats_mat, mats);
        mats_mat.release();
        return retVal;
    }

    public static boolean imwrite(String filename, Mat img, MatOfInt params) {
        MatOfInt params_mat = params;
        return Imgcodecs.imwrite_0(filename, img.nativeObj, params_mat.nativeObj);
    }

    public static boolean imwrite(String filename, Mat img) {
        return Imgcodecs.imwrite_1(filename, img.nativeObj);
    }

    public static boolean imwritemulti(String filename, List<Mat> img, MatOfInt params) {
        Mat img_mat = Converters.vector_Mat_to_Mat(img);
        MatOfInt params_mat = params;
        return Imgcodecs.imwritemulti_0(filename, img_mat.nativeObj, params_mat.nativeObj);
    }

    public static boolean imwritemulti(String filename, List<Mat> img) {
        Mat img_mat = Converters.vector_Mat_to_Mat(img);
        return Imgcodecs.imwritemulti_1(filename, img_mat.nativeObj);
    }

    public static Mat imdecode(Mat buf, int flags) {
        return new Mat(Imgcodecs.imdecode_0(buf.nativeObj, flags));
    }

    public static boolean imencode(String ext, Mat img, MatOfByte buf, MatOfInt params) {
        MatOfByte buf_mat = buf;
        MatOfInt params_mat = params;
        return Imgcodecs.imencode_0(ext, img.nativeObj, buf_mat.nativeObj, params_mat.nativeObj);
    }

    public static boolean imencode(String ext, Mat img, MatOfByte buf) {
        MatOfByte buf_mat = buf;
        return Imgcodecs.imencode_1(ext, img.nativeObj, buf_mat.nativeObj);
    }

    public static boolean haveImageReader(String filename) {
        return Imgcodecs.haveImageReader_0(filename);
    }

    public static boolean haveImageWriter(String filename) {
        return Imgcodecs.haveImageWriter_0(filename);
    }

    private static native long imread_0(String var0, int var1);

    private static native long imread_1(String var0);

    private static native boolean imreadmulti_0(String var0, long var1, int var3);

    private static native boolean imreadmulti_1(String var0, long var1);

    private static native boolean imwrite_0(String var0, long var1, long var3);

    private static native boolean imwrite_1(String var0, long var1);

    private static native boolean imwritemulti_0(String var0, long var1, long var3);

    private static native boolean imwritemulti_1(String var0, long var1);

    private static native long imdecode_0(long var0, int var2);

    private static native boolean imencode_0(String var0, long var1, long var3, long var5);

    private static native boolean imencode_1(String var0, long var1, long var3);

    private static native boolean haveImageReader_0(String var0);

    private static native boolean haveImageWriter_0(String var0);
}

