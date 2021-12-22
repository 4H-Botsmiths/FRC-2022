/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.ml;

import org.opencv.core.Mat;
import org.opencv.ml.StatModel;

public class KNearest
extends StatModel {
    public static final int BRUTE_FORCE = 1;
    public static final int KDTREE = 2;

    protected KNearest(long addr) {
        super(addr);
    }

    public static KNearest __fromPtr__(long addr) {
        return new KNearest(addr);
    }

    public int getDefaultK() {
        return KNearest.getDefaultK_0(this.nativeObj);
    }

    public void setDefaultK(int val) {
        KNearest.setDefaultK_0(this.nativeObj, val);
    }

    public boolean getIsClassifier() {
        return KNearest.getIsClassifier_0(this.nativeObj);
    }

    public void setIsClassifier(boolean val) {
        KNearest.setIsClassifier_0(this.nativeObj, val);
    }

    public int getEmax() {
        return KNearest.getEmax_0(this.nativeObj);
    }

    public void setEmax(int val) {
        KNearest.setEmax_0(this.nativeObj, val);
    }

    public int getAlgorithmType() {
        return KNearest.getAlgorithmType_0(this.nativeObj);
    }

    public void setAlgorithmType(int val) {
        KNearest.setAlgorithmType_0(this.nativeObj, val);
    }

    public float findNearest(Mat samples, int k, Mat results, Mat neighborResponses, Mat dist) {
        return KNearest.findNearest_0(this.nativeObj, samples.nativeObj, k, results.nativeObj, neighborResponses.nativeObj, dist.nativeObj);
    }

    public float findNearest(Mat samples, int k, Mat results, Mat neighborResponses) {
        return KNearest.findNearest_1(this.nativeObj, samples.nativeObj, k, results.nativeObj, neighborResponses.nativeObj);
    }

    public float findNearest(Mat samples, int k, Mat results) {
        return KNearest.findNearest_2(this.nativeObj, samples.nativeObj, k, results.nativeObj);
    }

    public static KNearest create() {
        return KNearest.__fromPtr__(KNearest.create_0());
    }

    public static KNearest load(String filepath) {
        return KNearest.__fromPtr__(KNearest.load_0(filepath));
    }

    @Override
    protected void finalize() throws Throwable {
        KNearest.delete(this.nativeObj);
    }

    private static native int getDefaultK_0(long var0);

    private static native void setDefaultK_0(long var0, int var2);

    private static native boolean getIsClassifier_0(long var0);

    private static native void setIsClassifier_0(long var0, boolean var2);

    private static native int getEmax_0(long var0);

    private static native void setEmax_0(long var0, int var2);

    private static native int getAlgorithmType_0(long var0);

    private static native void setAlgorithmType_0(long var0, int var2);

    private static native float findNearest_0(long var0, long var2, int var4, long var5, long var7, long var9);

    private static native float findNearest_1(long var0, long var2, int var4, long var5, long var7);

    private static native float findNearest_2(long var0, long var2, int var4, long var5);

    private static native long create_0();

    private static native long load_0(String var0);

    private static native void delete(long var0);
}

