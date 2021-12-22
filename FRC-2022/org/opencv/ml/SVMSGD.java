/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.ml;

import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.StatModel;

public class SVMSGD
extends StatModel {
    public static final int SOFT_MARGIN = 0;
    public static final int HARD_MARGIN = 1;
    public static final int SGD = 0;
    public static final int ASGD = 1;

    protected SVMSGD(long addr) {
        super(addr);
    }

    public static SVMSGD __fromPtr__(long addr) {
        return new SVMSGD(addr);
    }

    public Mat getWeights() {
        return new Mat(SVMSGD.getWeights_0(this.nativeObj));
    }

    public float getShift() {
        return SVMSGD.getShift_0(this.nativeObj);
    }

    public static SVMSGD create() {
        return SVMSGD.__fromPtr__(SVMSGD.create_0());
    }

    public static SVMSGD load(String filepath, String nodeName) {
        return SVMSGD.__fromPtr__(SVMSGD.load_0(filepath, nodeName));
    }

    public static SVMSGD load(String filepath) {
        return SVMSGD.__fromPtr__(SVMSGD.load_1(filepath));
    }

    public void setOptimalParameters(int svmsgdType, int marginType) {
        SVMSGD.setOptimalParameters_0(this.nativeObj, svmsgdType, marginType);
    }

    public void setOptimalParameters(int svmsgdType) {
        SVMSGD.setOptimalParameters_1(this.nativeObj, svmsgdType);
    }

    public void setOptimalParameters() {
        SVMSGD.setOptimalParameters_2(this.nativeObj);
    }

    public int getSvmsgdType() {
        return SVMSGD.getSvmsgdType_0(this.nativeObj);
    }

    public void setSvmsgdType(int svmsgdType) {
        SVMSGD.setSvmsgdType_0(this.nativeObj, svmsgdType);
    }

    public int getMarginType() {
        return SVMSGD.getMarginType_0(this.nativeObj);
    }

    public void setMarginType(int marginType) {
        SVMSGD.setMarginType_0(this.nativeObj, marginType);
    }

    public float getMarginRegularization() {
        return SVMSGD.getMarginRegularization_0(this.nativeObj);
    }

    public void setMarginRegularization(float marginRegularization) {
        SVMSGD.setMarginRegularization_0(this.nativeObj, marginRegularization);
    }

    public float getInitialStepSize() {
        return SVMSGD.getInitialStepSize_0(this.nativeObj);
    }

    public void setInitialStepSize(float InitialStepSize) {
        SVMSGD.setInitialStepSize_0(this.nativeObj, InitialStepSize);
    }

    public float getStepDecreasingPower() {
        return SVMSGD.getStepDecreasingPower_0(this.nativeObj);
    }

    public void setStepDecreasingPower(float stepDecreasingPower) {
        SVMSGD.setStepDecreasingPower_0(this.nativeObj, stepDecreasingPower);
    }

    public TermCriteria getTermCriteria() {
        return new TermCriteria(SVMSGD.getTermCriteria_0(this.nativeObj));
    }

    public void setTermCriteria(TermCriteria val) {
        SVMSGD.setTermCriteria_0(this.nativeObj, val.type, val.maxCount, val.epsilon);
    }

    @Override
    protected void finalize() throws Throwable {
        SVMSGD.delete(this.nativeObj);
    }

    private static native long getWeights_0(long var0);

    private static native float getShift_0(long var0);

    private static native long create_0();

    private static native long load_0(String var0, String var1);

    private static native long load_1(String var0);

    private static native void setOptimalParameters_0(long var0, int var2, int var3);

    private static native void setOptimalParameters_1(long var0, int var2);

    private static native void setOptimalParameters_2(long var0);

    private static native int getSvmsgdType_0(long var0);

    private static native void setSvmsgdType_0(long var0, int var2);

    private static native int getMarginType_0(long var0);

    private static native void setMarginType_0(long var0, int var2);

    private static native float getMarginRegularization_0(long var0);

    private static native void setMarginRegularization_0(long var0, float var2);

    private static native float getInitialStepSize_0(long var0);

    private static native void setInitialStepSize_0(long var0, float var2);

    private static native float getStepDecreasingPower_0(long var0);

    private static native void setStepDecreasingPower_0(long var0, float var2);

    private static native double[] getTermCriteria_0(long var0);

    private static native void setTermCriteria_0(long var0, int var2, int var3, double var4);

    private static native void delete(long var0);
}

