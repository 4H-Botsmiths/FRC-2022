/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.photo;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.photo.MergeExposures;
import org.opencv.utils.Converters;

public class MergeRobertson
extends MergeExposures {
    protected MergeRobertson(long addr) {
        super(addr);
    }

    public static MergeRobertson __fromPtr__(long addr) {
        return new MergeRobertson(addr);
    }

    @Override
    public void process(List<Mat> src, Mat dst, Mat times, Mat response) {
        Mat src_mat = Converters.vector_Mat_to_Mat(src);
        MergeRobertson.process_0(this.nativeObj, src_mat.nativeObj, dst.nativeObj, times.nativeObj, response.nativeObj);
    }

    public void process(List<Mat> src, Mat dst, Mat times) {
        Mat src_mat = Converters.vector_Mat_to_Mat(src);
        MergeRobertson.process_1(this.nativeObj, src_mat.nativeObj, dst.nativeObj, times.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        MergeRobertson.delete(this.nativeObj);
    }

    private static native void process_0(long var0, long var2, long var4, long var6, long var8);

    private static native void process_1(long var0, long var2, long var4, long var6);

    private static native void delete(long var0);
}

