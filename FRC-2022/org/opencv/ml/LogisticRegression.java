/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.ml;

import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.StatModel;

public class LogisticRegression
extends StatModel {
    public static final int BATCH = 0;
    public static final int MINI_BATCH = 1;
    public static final int REG_DISABLE = -1;
    public static final int REG_L1 = 0;
    public static final int REG_L2 = 1;

    protected LogisticRegression(long addr) {
        super(addr);
    }

    public static LogisticRegression __fromPtr__(long addr) {
        return new LogisticRegression(addr);
    }

    public double getLearningRate() {
        return LogisticRegression.getLearningRate_0(this.nativeObj);
    }

    public void setLearningRate(double val) {
        LogisticRegression.setLearningRate_0(this.nativeObj, val);
    }

    public int getIterations() {
        return LogisticRegression.getIterations_0(this.nativeObj);
    }

    public void setIterations(int val) {
        LogisticRegression.setIterations_0(this.nativeObj, val);
    }

    public int getRegularization() {
        return LogisticRegression.getRegularization_0(this.nativeObj);
    }

    public void setRegularization(int val) {
        LogisticRegression.setRegularization_0(this.nativeObj, val);
    }

    public int getTrainMethod() {
        return LogisticRegression.getTrainMethod_0(this.nativeObj);
    }

    public void setTrainMethod(int val) {
        LogisticRegression.setTrainMethod_0(this.nativeObj, val);
    }

    public int getMiniBatchSize() {
        return LogisticRegression.getMiniBatchSize_0(this.nativeObj);
    }

    public void setMiniBatchSize(int val) {
        LogisticRegression.setMiniBatchSize_0(this.nativeObj, val);
    }

    public TermCriteria getTermCriteria() {
        return new TermCriteria(LogisticRegression.getTermCriteria_0(this.nativeObj));
    }

    public void setTermCriteria(TermCriteria val) {
        LogisticRegression.setTermCriteria_0(this.nativeObj, val.type, val.maxCount, val.epsilon);
    }

    @Override
    public float predict(Mat samples, Mat results, int flags) {
        return LogisticRegression.predict_0(this.nativeObj, samples.nativeObj, results.nativeObj, flags);
    }

    @Override
    public float predict(Mat samples, Mat results) {
        return LogisticRegression.predict_1(this.nativeObj, samples.nativeObj, results.nativeObj);
    }

    @Override
    public float predict(Mat samples) {
        return LogisticRegression.predict_2(this.nativeObj, samples.nativeObj);
    }

    public Mat get_learnt_thetas() {
        return new Mat(LogisticRegression.get_learnt_thetas_0(this.nativeObj));
    }

    public static LogisticRegression create() {
        return LogisticRegression.__fromPtr__(LogisticRegression.create_0());
    }

    public static LogisticRegression load(String filepath, String nodeName) {
        return LogisticRegression.__fromPtr__(LogisticRegression.load_0(filepath, nodeName));
    }

    public static LogisticRegression load(String filepath) {
        return LogisticRegression.__fromPtr__(LogisticRegression.load_1(filepath));
    }

    @Override
    protected void finalize() throws Throwable {
        LogisticRegression.delete(this.nativeObj);
    }

    private static native double getLearningRate_0(long var0);

    private static native void setLearningRate_0(long var0, double var2);

    private static native int getIterations_0(long var0);

    private static native void setIterations_0(long var0, int var2);

    private static native int getRegularization_0(long var0);

    private static native void setRegularization_0(long var0, int var2);

    private static native int getTrainMethod_0(long var0);

    private static native void setTrainMethod_0(long var0, int var2);

    private static native int getMiniBatchSize_0(long var0);

    private static native void setMiniBatchSize_0(long var0, int var2);

    private static native double[] getTermCriteria_0(long var0);

    private static native void setTermCriteria_0(long var0, int var2, int var3, double var4);

    private static native float predict_0(long var0, long var2, long var4, int var6);

    private static native float predict_1(long var0, long var2, long var4);

    private static native float predict_2(long var0, long var2);

    private static native long get_learnt_thetas_0(long var0);

    private static native long create_0();

    private static native long load_0(String var0, String var1);

    private static native long load_1(String var0);

    private static native void delete(long var0);
}

