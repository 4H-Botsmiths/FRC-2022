/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.ml;

import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.ParamGrid;
import org.opencv.ml.StatModel;

public class SVM
extends StatModel {
    public static final int CUSTOM = -1;
    public static final int LINEAR = 0;
    public static final int POLY = 1;
    public static final int RBF = 2;
    public static final int SIGMOID = 3;
    public static final int CHI2 = 4;
    public static final int INTER = 5;
    public static final int C = 0;
    public static final int GAMMA = 1;
    public static final int P = 2;
    public static final int NU = 3;
    public static final int COEF = 4;
    public static final int DEGREE = 5;
    public static final int C_SVC = 100;
    public static final int NU_SVC = 101;
    public static final int ONE_CLASS = 102;
    public static final int EPS_SVR = 103;
    public static final int NU_SVR = 104;

    protected SVM(long addr) {
        super(addr);
    }

    public static SVM __fromPtr__(long addr) {
        return new SVM(addr);
    }

    public int getType() {
        return SVM.getType_0(this.nativeObj);
    }

    public void setType(int val) {
        SVM.setType_0(this.nativeObj, val);
    }

    public double getGamma() {
        return SVM.getGamma_0(this.nativeObj);
    }

    public void setGamma(double val) {
        SVM.setGamma_0(this.nativeObj, val);
    }

    public double getCoef0() {
        return SVM.getCoef0_0(this.nativeObj);
    }

    public void setCoef0(double val) {
        SVM.setCoef0_0(this.nativeObj, val);
    }

    public double getDegree() {
        return SVM.getDegree_0(this.nativeObj);
    }

    public void setDegree(double val) {
        SVM.setDegree_0(this.nativeObj, val);
    }

    public double getC() {
        return SVM.getC_0(this.nativeObj);
    }

    public void setC(double val) {
        SVM.setC_0(this.nativeObj, val);
    }

    public double getNu() {
        return SVM.getNu_0(this.nativeObj);
    }

    public void setNu(double val) {
        SVM.setNu_0(this.nativeObj, val);
    }

    public double getP() {
        return SVM.getP_0(this.nativeObj);
    }

    public void setP(double val) {
        SVM.setP_0(this.nativeObj, val);
    }

    public Mat getClassWeights() {
        return new Mat(SVM.getClassWeights_0(this.nativeObj));
    }

    public void setClassWeights(Mat val) {
        SVM.setClassWeights_0(this.nativeObj, val.nativeObj);
    }

    public TermCriteria getTermCriteria() {
        return new TermCriteria(SVM.getTermCriteria_0(this.nativeObj));
    }

    public void setTermCriteria(TermCriteria val) {
        SVM.setTermCriteria_0(this.nativeObj, val.type, val.maxCount, val.epsilon);
    }

    public int getKernelType() {
        return SVM.getKernelType_0(this.nativeObj);
    }

    public void setKernel(int kernelType) {
        SVM.setKernel_0(this.nativeObj, kernelType);
    }

    public boolean trainAuto(Mat samples, int layout, Mat responses, int kFold, ParamGrid Cgrid, ParamGrid gammaGrid, ParamGrid pGrid, ParamGrid nuGrid, ParamGrid coeffGrid, ParamGrid degreeGrid, boolean balanced) {
        return SVM.trainAuto_0(this.nativeObj, samples.nativeObj, layout, responses.nativeObj, kFold, Cgrid.getNativeObjAddr(), gammaGrid.getNativeObjAddr(), pGrid.getNativeObjAddr(), nuGrid.getNativeObjAddr(), coeffGrid.getNativeObjAddr(), degreeGrid.getNativeObjAddr(), balanced);
    }

    public boolean trainAuto(Mat samples, int layout, Mat responses, int kFold, ParamGrid Cgrid, ParamGrid gammaGrid, ParamGrid pGrid, ParamGrid nuGrid, ParamGrid coeffGrid, ParamGrid degreeGrid) {
        return SVM.trainAuto_1(this.nativeObj, samples.nativeObj, layout, responses.nativeObj, kFold, Cgrid.getNativeObjAddr(), gammaGrid.getNativeObjAddr(), pGrid.getNativeObjAddr(), nuGrid.getNativeObjAddr(), coeffGrid.getNativeObjAddr(), degreeGrid.getNativeObjAddr());
    }

    public boolean trainAuto(Mat samples, int layout, Mat responses, int kFold, ParamGrid Cgrid, ParamGrid gammaGrid, ParamGrid pGrid, ParamGrid nuGrid, ParamGrid coeffGrid) {
        return SVM.trainAuto_2(this.nativeObj, samples.nativeObj, layout, responses.nativeObj, kFold, Cgrid.getNativeObjAddr(), gammaGrid.getNativeObjAddr(), pGrid.getNativeObjAddr(), nuGrid.getNativeObjAddr(), coeffGrid.getNativeObjAddr());
    }

    public boolean trainAuto(Mat samples, int layout, Mat responses, int kFold, ParamGrid Cgrid, ParamGrid gammaGrid, ParamGrid pGrid, ParamGrid nuGrid) {
        return SVM.trainAuto_3(this.nativeObj, samples.nativeObj, layout, responses.nativeObj, kFold, Cgrid.getNativeObjAddr(), gammaGrid.getNativeObjAddr(), pGrid.getNativeObjAddr(), nuGrid.getNativeObjAddr());
    }

    public boolean trainAuto(Mat samples, int layout, Mat responses, int kFold, ParamGrid Cgrid, ParamGrid gammaGrid, ParamGrid pGrid) {
        return SVM.trainAuto_4(this.nativeObj, samples.nativeObj, layout, responses.nativeObj, kFold, Cgrid.getNativeObjAddr(), gammaGrid.getNativeObjAddr(), pGrid.getNativeObjAddr());
    }

    public boolean trainAuto(Mat samples, int layout, Mat responses, int kFold, ParamGrid Cgrid, ParamGrid gammaGrid) {
        return SVM.trainAuto_5(this.nativeObj, samples.nativeObj, layout, responses.nativeObj, kFold, Cgrid.getNativeObjAddr(), gammaGrid.getNativeObjAddr());
    }

    public boolean trainAuto(Mat samples, int layout, Mat responses, int kFold, ParamGrid Cgrid) {
        return SVM.trainAuto_6(this.nativeObj, samples.nativeObj, layout, responses.nativeObj, kFold, Cgrid.getNativeObjAddr());
    }

    public boolean trainAuto(Mat samples, int layout, Mat responses, int kFold) {
        return SVM.trainAuto_7(this.nativeObj, samples.nativeObj, layout, responses.nativeObj, kFold);
    }

    public boolean trainAuto(Mat samples, int layout, Mat responses) {
        return SVM.trainAuto_8(this.nativeObj, samples.nativeObj, layout, responses.nativeObj);
    }

    public Mat getSupportVectors() {
        return new Mat(SVM.getSupportVectors_0(this.nativeObj));
    }

    public Mat getUncompressedSupportVectors() {
        return new Mat(SVM.getUncompressedSupportVectors_0(this.nativeObj));
    }

    public double getDecisionFunction(int i, Mat alpha, Mat svidx) {
        return SVM.getDecisionFunction_0(this.nativeObj, i, alpha.nativeObj, svidx.nativeObj);
    }

    public static ParamGrid getDefaultGridPtr(int param_id) {
        return ParamGrid.__fromPtr__(SVM.getDefaultGridPtr_0(param_id));
    }

    public static SVM create() {
        return SVM.__fromPtr__(SVM.create_0());
    }

    public static SVM load(String filepath) {
        return SVM.__fromPtr__(SVM.load_0(filepath));
    }

    @Override
    protected void finalize() throws Throwable {
        SVM.delete(this.nativeObj);
    }

    private static native int getType_0(long var0);

    private static native void setType_0(long var0, int var2);

    private static native double getGamma_0(long var0);

    private static native void setGamma_0(long var0, double var2);

    private static native double getCoef0_0(long var0);

    private static native void setCoef0_0(long var0, double var2);

    private static native double getDegree_0(long var0);

    private static native void setDegree_0(long var0, double var2);

    private static native double getC_0(long var0);

    private static native void setC_0(long var0, double var2);

    private static native double getNu_0(long var0);

    private static native void setNu_0(long var0, double var2);

    private static native double getP_0(long var0);

    private static native void setP_0(long var0, double var2);

    private static native long getClassWeights_0(long var0);

    private static native void setClassWeights_0(long var0, long var2);

    private static native double[] getTermCriteria_0(long var0);

    private static native void setTermCriteria_0(long var0, int var2, int var3, double var4);

    private static native int getKernelType_0(long var0);

    private static native void setKernel_0(long var0, int var2);

    private static native boolean trainAuto_0(long var0, long var2, int var4, long var5, int var7, long var8, long var10, long var12, long var14, long var16, long var18, boolean var20);

    private static native boolean trainAuto_1(long var0, long var2, int var4, long var5, int var7, long var8, long var10, long var12, long var14, long var16, long var18);

    private static native boolean trainAuto_2(long var0, long var2, int var4, long var5, int var7, long var8, long var10, long var12, long var14, long var16);

    private static native boolean trainAuto_3(long var0, long var2, int var4, long var5, int var7, long var8, long var10, long var12, long var14);

    private static native boolean trainAuto_4(long var0, long var2, int var4, long var5, int var7, long var8, long var10, long var12);

    private static native boolean trainAuto_5(long var0, long var2, int var4, long var5, int var7, long var8, long var10);

    private static native boolean trainAuto_6(long var0, long var2, int var4, long var5, int var7, long var8);

    private static native boolean trainAuto_7(long var0, long var2, int var4, long var5, int var7);

    private static native boolean trainAuto_8(long var0, long var2, int var4, long var5);

    private static native long getSupportVectors_0(long var0);

    private static native long getUncompressedSupportVectors_0(long var0);

    private static native double getDecisionFunction_0(long var0, int var2, long var3, long var5);

    private static native long getDefaultGridPtr_0(int var0);

    private static native long create_0();

    private static native long load_0(String var0);

    private static native void delete(long var0);
}

