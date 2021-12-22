/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.photo;

import java.util.List;
import org.opencv.core.Algorithm;
import org.opencv.core.Mat;
import org.opencv.utils.Converters;

public class CalibrateCRF
extends Algorithm {
    protected CalibrateCRF(long addr) {
        super(addr);
    }

    public static CalibrateCRF __fromPtr__(long addr) {
        return new CalibrateCRF(addr);
    }

    public void process(List<Mat> src, Mat dst, Mat times) {
        Mat src_mat = Converters.vector_Mat_to_Mat(src);
        CalibrateCRF.process_0(this.nativeObj, src_mat.nativeObj, dst.nativeObj, times.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        CalibrateCRF.delete(this.nativeObj);
    }

    private static native void process_0(long var0, long var2, long var4, long var6);

    private static native void delete(long var0);
}

