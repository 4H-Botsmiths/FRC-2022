/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.photo;

import java.util.List;
import org.opencv.core.Algorithm;
import org.opencv.core.Mat;
import org.opencv.utils.Converters;

public class MergeExposures
extends Algorithm {
    protected MergeExposures(long addr) {
        super(addr);
    }

    public static MergeExposures __fromPtr__(long addr) {
        return new MergeExposures(addr);
    }

    public void process(List<Mat> src, Mat dst, Mat times, Mat response) {
        Mat src_mat = Converters.vector_Mat_to_Mat(src);
        MergeExposures.process_0(this.nativeObj, src_mat.nativeObj, dst.nativeObj, times.nativeObj, response.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        MergeExposures.delete(this.nativeObj);
    }

    private static native void process_0(long var0, long var2, long var4, long var6, long var8);

    private static native void delete(long var0);
}

