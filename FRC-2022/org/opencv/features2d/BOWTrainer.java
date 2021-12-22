/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.utils.Converters;

public class BOWTrainer {
    protected final long nativeObj;

    protected BOWTrainer(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static BOWTrainer __fromPtr__(long addr) {
        return new BOWTrainer(addr);
    }

    public void add(Mat descriptors) {
        BOWTrainer.add_0(this.nativeObj, descriptors.nativeObj);
    }

    public List<Mat> getDescriptors() {
        ArrayList<Mat> retVal = new ArrayList<Mat>();
        Mat retValMat = new Mat(BOWTrainer.getDescriptors_0(this.nativeObj));
        Converters.Mat_to_vector_Mat(retValMat, retVal);
        return retVal;
    }

    public int descriptorsCount() {
        return BOWTrainer.descriptorsCount_0(this.nativeObj);
    }

    public void clear() {
        BOWTrainer.clear_0(this.nativeObj);
    }

    public Mat cluster() {
        return new Mat(BOWTrainer.cluster_0(this.nativeObj));
    }

    public Mat cluster(Mat descriptors) {
        return new Mat(BOWTrainer.cluster_1(this.nativeObj, descriptors.nativeObj));
    }

    protected void finalize() throws Throwable {
        BOWTrainer.delete(this.nativeObj);
    }

    private static native void add_0(long var0, long var2);

    private static native long getDescriptors_0(long var0);

    private static native int descriptorsCount_0(long var0);

    private static native void clear_0(long var0);

    private static native long cluster_0(long var0);

    private static native long cluster_1(long var0, long var2);

    private static native void delete(long var0);
}

