/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.ml;

import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.StatModel;

public class ANN_MLP
extends StatModel {
    public static final int IDENTITY = 0;
    public static final int SIGMOID_SYM = 1;
    public static final int GAUSSIAN = 2;
    public static final int RELU = 3;
    public static final int LEAKYRELU = 4;
    public static final int UPDATE_WEIGHTS = 1;
    public static final int NO_INPUT_SCALE = 2;
    public static final int NO_OUTPUT_SCALE = 4;
    public static final int BACKPROP = 0;
    public static final int RPROP = 1;
    public static final int ANNEAL = 2;

    protected ANN_MLP(long addr) {
        super(addr);
    }

    public static ANN_MLP __fromPtr__(long addr) {
        return new ANN_MLP(addr);
    }

    public void setTrainMethod(int method, double param1, double param2) {
        ANN_MLP.setTrainMethod_0(this.nativeObj, method, param1, param2);
    }

    public void setTrainMethod(int method, double param1) {
        ANN_MLP.setTrainMethod_1(this.nativeObj, method, param1);
    }

    public void setTrainMethod(int method) {
        ANN_MLP.setTrainMethod_2(this.nativeObj, method);
    }

    public int getTrainMethod() {
        return ANN_MLP.getTrainMethod_0(this.nativeObj);
    }

    public void setActivationFunction(int type, double param1, double param2) {
        ANN_MLP.setActivationFunction_0(this.nativeObj, type, param1, param2);
    }

    public void setActivationFunction(int type, double param1) {
        ANN_MLP.setActivationFunction_1(this.nativeObj, type, param1);
    }

    public void setActivationFunction(int type) {
        ANN_MLP.setActivationFunction_2(this.nativeObj, type);
    }

    public void setLayerSizes(Mat _layer_sizes) {
        ANN_MLP.setLayerSizes_0(this.nativeObj, _layer_sizes.nativeObj);
    }

    public Mat getLayerSizes() {
        return new Mat(ANN_MLP.getLayerSizes_0(this.nativeObj));
    }

    public TermCriteria getTermCriteria() {
        return new TermCriteria(ANN_MLP.getTermCriteria_0(this.nativeObj));
    }

    public void setTermCriteria(TermCriteria val) {
        ANN_MLP.setTermCriteria_0(this.nativeObj, val.type, val.maxCount, val.epsilon);
    }

    public double getBackpropWeightScale() {
        return ANN_MLP.getBackpropWeightScale_0(this.nativeObj);
    }

    public void setBackpropWeightScale(double val) {
        ANN_MLP.setBackpropWeightScale_0(this.nativeObj, val);
    }

    public double getBackpropMomentumScale() {
        return ANN_MLP.getBackpropMomentumScale_0(this.nativeObj);
    }

    public void setBackpropMomentumScale(double val) {
        ANN_MLP.setBackpropMomentumScale_0(this.nativeObj, val);
    }

    public double getRpropDW0() {
        return ANN_MLP.getRpropDW0_0(this.nativeObj);
    }

    public void setRpropDW0(double val) {
        ANN_MLP.setRpropDW0_0(this.nativeObj, val);
    }

    public double getRpropDWPlus() {
        return ANN_MLP.getRpropDWPlus_0(this.nativeObj);
    }

    public void setRpropDWPlus(double val) {
        ANN_MLP.setRpropDWPlus_0(this.nativeObj, val);
    }

    public double getRpropDWMinus() {
        return ANN_MLP.getRpropDWMinus_0(this.nativeObj);
    }

    public void setRpropDWMinus(double val) {
        ANN_MLP.setRpropDWMinus_0(this.nativeObj, val);
    }

    public double getRpropDWMin() {
        return ANN_MLP.getRpropDWMin_0(this.nativeObj);
    }

    public void setRpropDWMin(double val) {
        ANN_MLP.setRpropDWMin_0(this.nativeObj, val);
    }

    public double getRpropDWMax() {
        return ANN_MLP.getRpropDWMax_0(this.nativeObj);
    }

    public void setRpropDWMax(double val) {
        ANN_MLP.setRpropDWMax_0(this.nativeObj, val);
    }

    public double getAnnealInitialT() {
        return ANN_MLP.getAnnealInitialT_0(this.nativeObj);
    }

    public void setAnnealInitialT(double val) {
        ANN_MLP.setAnnealInitialT_0(this.nativeObj, val);
    }

    public double getAnnealFinalT() {
        return ANN_MLP.getAnnealFinalT_0(this.nativeObj);
    }

    public void setAnnealFinalT(double val) {
        ANN_MLP.setAnnealFinalT_0(this.nativeObj, val);
    }

    public double getAnnealCoolingRatio() {
        return ANN_MLP.getAnnealCoolingRatio_0(this.nativeObj);
    }

    public void setAnnealCoolingRatio(double val) {
        ANN_MLP.setAnnealCoolingRatio_0(this.nativeObj, val);
    }

    public int getAnnealItePerStep() {
        return ANN_MLP.getAnnealItePerStep_0(this.nativeObj);
    }

    public void setAnnealItePerStep(int val) {
        ANN_MLP.setAnnealItePerStep_0(this.nativeObj, val);
    }

    public Mat getWeights(int layerIdx) {
        return new Mat(ANN_MLP.getWeights_0(this.nativeObj, layerIdx));
    }

    public static ANN_MLP create() {
        return ANN_MLP.__fromPtr__(ANN_MLP.create_0());
    }

    public static ANN_MLP load(String filepath) {
        return ANN_MLP.__fromPtr__(ANN_MLP.load_0(filepath));
    }

    @Override
    protected void finalize() throws Throwable {
        ANN_MLP.delete(this.nativeObj);
    }

    private static native void setTrainMethod_0(long var0, int var2, double var3, double var5);

    private static native void setTrainMethod_1(long var0, int var2, double var3);

    private static native void setTrainMethod_2(long var0, int var2);

    private static native int getTrainMethod_0(long var0);

    private static native void setActivationFunction_0(long var0, int var2, double var3, double var5);

    private static native void setActivationFunction_1(long var0, int var2, double var3);

    private static native void setActivationFunction_2(long var0, int var2);

    private static native void setLayerSizes_0(long var0, long var2);

    private static native long getLayerSizes_0(long var0);

    private static native double[] getTermCriteria_0(long var0);

    private static native void setTermCriteria_0(long var0, int var2, int var3, double var4);

    private static native double getBackpropWeightScale_0(long var0);

    private static native void setBackpropWeightScale_0(long var0, double var2);

    private static native double getBackpropMomentumScale_0(long var0);

    private static native void setBackpropMomentumScale_0(long var0, double var2);

    private static native double getRpropDW0_0(long var0);

    private static native void setRpropDW0_0(long var0, double var2);

    private static native double getRpropDWPlus_0(long var0);

    private static native void setRpropDWPlus_0(long var0, double var2);

    private static native double getRpropDWMinus_0(long var0);

    private static native void setRpropDWMinus_0(long var0, double var2);

    private static native double getRpropDWMin_0(long var0);

    private static native void setRpropDWMin_0(long var0, double var2);

    private static native double getRpropDWMax_0(long var0);

    private static native void setRpropDWMax_0(long var0, double var2);

    private static native double getAnnealInitialT_0(long var0);

    private static native void setAnnealInitialT_0(long var0, double var2);

    private static native double getAnnealFinalT_0(long var0);

    private static native void setAnnealFinalT_0(long var0, double var2);

    private static native double getAnnealCoolingRatio_0(long var0);

    private static native void setAnnealCoolingRatio_0(long var0, double var2);

    private static native int getAnnealItePerStep_0(long var0);

    private static native void setAnnealItePerStep_0(long var0, int var2);

    private static native long getWeights_0(long var0, int var2);

    private static native long create_0();

    private static native long load_0(String var0);

    private static native void delete(long var0);
}

