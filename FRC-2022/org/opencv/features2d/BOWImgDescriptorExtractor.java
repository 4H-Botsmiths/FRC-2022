/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;

public class BOWImgDescriptorExtractor {
    protected final long nativeObj;

    protected BOWImgDescriptorExtractor(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static BOWImgDescriptorExtractor __fromPtr__(long addr) {
        return new BOWImgDescriptorExtractor(addr);
    }

    public void setVocabulary(Mat vocabulary) {
        BOWImgDescriptorExtractor.setVocabulary_0(this.nativeObj, vocabulary.nativeObj);
    }

    public Mat getVocabulary() {
        return new Mat(BOWImgDescriptorExtractor.getVocabulary_0(this.nativeObj));
    }

    public void compute(Mat image, MatOfKeyPoint keypoints, Mat imgDescriptor) {
        MatOfKeyPoint keypoints_mat = keypoints;
        BOWImgDescriptorExtractor.compute_0(this.nativeObj, image.nativeObj, keypoints_mat.nativeObj, imgDescriptor.nativeObj);
    }

    public int descriptorSize() {
        return BOWImgDescriptorExtractor.descriptorSize_0(this.nativeObj);
    }

    public int descriptorType() {
        return BOWImgDescriptorExtractor.descriptorType_0(this.nativeObj);
    }

    protected void finalize() throws Throwable {
        BOWImgDescriptorExtractor.delete(this.nativeObj);
    }

    private static native void setVocabulary_0(long var0, long var2);

    private static native long getVocabulary_0(long var0);

    private static native void compute_0(long var0, long var2, long var4, long var6);

    private static native int descriptorSize_0(long var0);

    private static native int descriptorType_0(long var0);

    private static native void delete(long var0);
}

