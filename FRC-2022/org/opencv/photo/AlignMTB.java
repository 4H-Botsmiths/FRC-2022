/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.photo;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.photo.AlignExposures;
import org.opencv.utils.Converters;

public class AlignMTB
extends AlignExposures {
    protected AlignMTB(long addr) {
        super(addr);
    }

    public static AlignMTB __fromPtr__(long addr) {
        return new AlignMTB(addr);
    }

    @Override
    public void process(List<Mat> src, List<Mat> dst, Mat times, Mat response) {
        Mat src_mat = Converters.vector_Mat_to_Mat(src);
        Mat dst_mat = Converters.vector_Mat_to_Mat(dst);
        AlignMTB.process_0(this.nativeObj, src_mat.nativeObj, dst_mat.nativeObj, times.nativeObj, response.nativeObj);
    }

    public void process(List<Mat> src, List<Mat> dst) {
        Mat src_mat = Converters.vector_Mat_to_Mat(src);
        Mat dst_mat = Converters.vector_Mat_to_Mat(dst);
        AlignMTB.process_1(this.nativeObj, src_mat.nativeObj, dst_mat.nativeObj);
    }

    public Point calculateShift(Mat img0, Mat img1) {
        return new Point(AlignMTB.calculateShift_0(this.nativeObj, img0.nativeObj, img1.nativeObj));
    }

    public void shiftMat(Mat src, Mat dst, Point shift) {
        AlignMTB.shiftMat_0(this.nativeObj, src.nativeObj, dst.nativeObj, shift.x, shift.y);
    }

    public void computeBitmaps(Mat img, Mat tb, Mat eb) {
        AlignMTB.computeBitmaps_0(this.nativeObj, img.nativeObj, tb.nativeObj, eb.nativeObj);
    }

    public int getMaxBits() {
        return AlignMTB.getMaxBits_0(this.nativeObj);
    }

    public void setMaxBits(int max_bits) {
        AlignMTB.setMaxBits_0(this.nativeObj, max_bits);
    }

    public int getExcludeRange() {
        return AlignMTB.getExcludeRange_0(this.nativeObj);
    }

    public void setExcludeRange(int exclude_range) {
        AlignMTB.setExcludeRange_0(this.nativeObj, exclude_range);
    }

    public boolean getCut() {
        return AlignMTB.getCut_0(this.nativeObj);
    }

    public void setCut(boolean value) {
        AlignMTB.setCut_0(this.nativeObj, value);
    }

    @Override
    protected void finalize() throws Throwable {
        AlignMTB.delete(this.nativeObj);
    }

    private static native void process_0(long var0, long var2, long var4, long var6, long var8);

    private static native void process_1(long var0, long var2, long var4);

    private static native double[] calculateShift_0(long var0, long var2, long var4);

    private static native void shiftMat_0(long var0, long var2, long var4, double var6, double var8);

    private static native void computeBitmaps_0(long var0, long var2, long var4, long var6);

    private static native int getMaxBits_0(long var0);

    private static native void setMaxBits_0(long var0, int var2);

    private static native int getExcludeRange_0(long var0);

    private static native void setExcludeRange_0(long var0, int var2);

    private static native boolean getCut_0(long var0);

    private static native void setCut_0(long var0, boolean var2);

    private static native void delete(long var0);
}

