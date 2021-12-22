/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.ml;

import org.opencv.core.Mat;
import org.opencv.ml.StatModel;

public class DTrees
extends StatModel {
    public static final int PREDICT_AUTO = 0;
    public static final int PREDICT_SUM = 256;
    public static final int PREDICT_MAX_VOTE = 512;
    public static final int PREDICT_MASK = 768;

    protected DTrees(long addr) {
        super(addr);
    }

    public static DTrees __fromPtr__(long addr) {
        return new DTrees(addr);
    }

    public int getMaxCategories() {
        return DTrees.getMaxCategories_0(this.nativeObj);
    }

    public void setMaxCategories(int val) {
        DTrees.setMaxCategories_0(this.nativeObj, val);
    }

    public int getMaxDepth() {
        return DTrees.getMaxDepth_0(this.nativeObj);
    }

    public void setMaxDepth(int val) {
        DTrees.setMaxDepth_0(this.nativeObj, val);
    }

    public int getMinSampleCount() {
        return DTrees.getMinSampleCount_0(this.nativeObj);
    }

    public void setMinSampleCount(int val) {
        DTrees.setMinSampleCount_0(this.nativeObj, val);
    }

    public int getCVFolds() {
        return DTrees.getCVFolds_0(this.nativeObj);
    }

    public void setCVFolds(int val) {
        DTrees.setCVFolds_0(this.nativeObj, val);
    }

    public boolean getUseSurrogates() {
        return DTrees.getUseSurrogates_0(this.nativeObj);
    }

    public void setUseSurrogates(boolean val) {
        DTrees.setUseSurrogates_0(this.nativeObj, val);
    }

    public boolean getUse1SERule() {
        return DTrees.getUse1SERule_0(this.nativeObj);
    }

    public void setUse1SERule(boolean val) {
        DTrees.setUse1SERule_0(this.nativeObj, val);
    }

    public boolean getTruncatePrunedTree() {
        return DTrees.getTruncatePrunedTree_0(this.nativeObj);
    }

    public void setTruncatePrunedTree(boolean val) {
        DTrees.setTruncatePrunedTree_0(this.nativeObj, val);
    }

    public float getRegressionAccuracy() {
        return DTrees.getRegressionAccuracy_0(this.nativeObj);
    }

    public void setRegressionAccuracy(float val) {
        DTrees.setRegressionAccuracy_0(this.nativeObj, val);
    }

    public Mat getPriors() {
        return new Mat(DTrees.getPriors_0(this.nativeObj));
    }

    public void setPriors(Mat val) {
        DTrees.setPriors_0(this.nativeObj, val.nativeObj);
    }

    public static DTrees create() {
        return DTrees.__fromPtr__(DTrees.create_0());
    }

    public static DTrees load(String filepath, String nodeName) {
        return DTrees.__fromPtr__(DTrees.load_0(filepath, nodeName));
    }

    public static DTrees load(String filepath) {
        return DTrees.__fromPtr__(DTrees.load_1(filepath));
    }

    @Override
    protected void finalize() throws Throwable {
        DTrees.delete(this.nativeObj);
    }

    private static native int getMaxCategories_0(long var0);

    private static native void setMaxCategories_0(long var0, int var2);

    private static native int getMaxDepth_0(long var0);

    private static native void setMaxDepth_0(long var0, int var2);

    private static native int getMinSampleCount_0(long var0);

    private static native void setMinSampleCount_0(long var0, int var2);

    private static native int getCVFolds_0(long var0);

    private static native void setCVFolds_0(long var0, int var2);

    private static native boolean getUseSurrogates_0(long var0);

    private static native void setUseSurrogates_0(long var0, boolean var2);

    private static native boolean getUse1SERule_0(long var0);

    private static native void setUse1SERule_0(long var0, boolean var2);

    private static native boolean getTruncatePrunedTree_0(long var0);

    private static native void setTruncatePrunedTree_0(long var0, boolean var2);

    private static native float getRegressionAccuracy_0(long var0);

    private static native void setRegressionAccuracy_0(long var0, float var2);

    private static native long getPriors_0(long var0);

    private static native void setPriors_0(long var0, long var2);

    private static native long create_0();

    private static native long load_0(String var0, String var1);

    private static native long load_1(String var0);

    private static native void delete(long var0);
}

