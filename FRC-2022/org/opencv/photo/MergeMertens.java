/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.photo;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.photo.MergeExposures;
import org.opencv.utils.Converters;

public class MergeMertens
extends MergeExposures {
    protected MergeMertens(long addr) {
        super(addr);
    }

    public static MergeMertens __fromPtr__(long addr) {
        return new MergeMertens(addr);
    }

    @Override
    public void process(List<Mat> src, Mat dst, Mat times, Mat response) {
        Mat src_mat = Converters.vector_Mat_to_Mat(src);
        MergeMertens.process_0(this.nativeObj, src_mat.nativeObj, dst.nativeObj, times.nativeObj, response.nativeObj);
    }

    public void process(List<Mat> src, Mat dst) {
        Mat src_mat = Converters.vector_Mat_to_Mat(src);
        MergeMertens.process_1(this.nativeObj, src_mat.nativeObj, dst.nativeObj);
    }

    public float getContrastWeight() {
        return MergeMertens.getContrastWeight_0(this.nativeObj);
    }

    public void setContrastWeight(float contrast_weiht) {
        MergeMertens.setContrastWeight_0(this.nativeObj, contrast_weiht);
    }

    public float getSaturationWeight() {
        return MergeMertens.getSaturationWeight_0(this.nativeObj);
    }

    public void setSaturationWeight(float saturation_weight) {
        MergeMertens.setSaturationWeight_0(this.nativeObj, saturation_weight);
    }

    public float getExposureWeight() {
        return MergeMertens.getExposureWeight_0(this.nativeObj);
    }

    public void setExposureWeight(float exposure_weight) {
        MergeMertens.setExposureWeight_0(this.nativeObj, exposure_weight);
    }

    @Override
    protected void finalize() throws Throwable {
        MergeMertens.delete(this.nativeObj);
    }

    private static native void process_0(long var0, long var2, long var4, long var6, long var8);

    private static native void process_1(long var0, long var2, long var4);

    private static native float getContrastWeight_0(long var0);

    private static native void setContrastWeight_0(long var0, float var2);

    private static native float getSaturationWeight_0(long var0);

    private static native void setSaturationWeight_0(long var0, float var2);

    private static native float getExposureWeight_0(long var0);

    private static native void setExposureWeight_0(long var0, float var2);

    private static native void delete(long var0);
}

