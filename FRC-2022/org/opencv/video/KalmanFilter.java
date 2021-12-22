/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.video;

import org.opencv.core.Mat;

public class KalmanFilter {
    protected final long nativeObj;

    protected KalmanFilter(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static KalmanFilter __fromPtr__(long addr) {
        return new KalmanFilter(addr);
    }

    public KalmanFilter() {
        this.nativeObj = KalmanFilter.KalmanFilter_0();
    }

    public KalmanFilter(int dynamParams, int measureParams, int controlParams, int type) {
        this.nativeObj = KalmanFilter.KalmanFilter_1(dynamParams, measureParams, controlParams, type);
    }

    public KalmanFilter(int dynamParams, int measureParams, int controlParams) {
        this.nativeObj = KalmanFilter.KalmanFilter_2(dynamParams, measureParams, controlParams);
    }

    public KalmanFilter(int dynamParams, int measureParams) {
        this.nativeObj = KalmanFilter.KalmanFilter_3(dynamParams, measureParams);
    }

    public Mat predict(Mat control) {
        return new Mat(KalmanFilter.predict_0(this.nativeObj, control.nativeObj));
    }

    public Mat predict() {
        return new Mat(KalmanFilter.predict_1(this.nativeObj));
    }

    public Mat correct(Mat measurement) {
        return new Mat(KalmanFilter.correct_0(this.nativeObj, measurement.nativeObj));
    }

    public Mat get_statePre() {
        return new Mat(KalmanFilter.get_statePre_0(this.nativeObj));
    }

    public void set_statePre(Mat statePre) {
        KalmanFilter.set_statePre_0(this.nativeObj, statePre.nativeObj);
    }

    public Mat get_statePost() {
        return new Mat(KalmanFilter.get_statePost_0(this.nativeObj));
    }

    public void set_statePost(Mat statePost) {
        KalmanFilter.set_statePost_0(this.nativeObj, statePost.nativeObj);
    }

    public Mat get_transitionMatrix() {
        return new Mat(KalmanFilter.get_transitionMatrix_0(this.nativeObj));
    }

    public void set_transitionMatrix(Mat transitionMatrix) {
        KalmanFilter.set_transitionMatrix_0(this.nativeObj, transitionMatrix.nativeObj);
    }

    public Mat get_controlMatrix() {
        return new Mat(KalmanFilter.get_controlMatrix_0(this.nativeObj));
    }

    public void set_controlMatrix(Mat controlMatrix) {
        KalmanFilter.set_controlMatrix_0(this.nativeObj, controlMatrix.nativeObj);
    }

    public Mat get_measurementMatrix() {
        return new Mat(KalmanFilter.get_measurementMatrix_0(this.nativeObj));
    }

    public void set_measurementMatrix(Mat measurementMatrix) {
        KalmanFilter.set_measurementMatrix_0(this.nativeObj, measurementMatrix.nativeObj);
    }

    public Mat get_processNoiseCov() {
        return new Mat(KalmanFilter.get_processNoiseCov_0(this.nativeObj));
    }

    public void set_processNoiseCov(Mat processNoiseCov) {
        KalmanFilter.set_processNoiseCov_0(this.nativeObj, processNoiseCov.nativeObj);
    }

    public Mat get_measurementNoiseCov() {
        return new Mat(KalmanFilter.get_measurementNoiseCov_0(this.nativeObj));
    }

    public void set_measurementNoiseCov(Mat measurementNoiseCov) {
        KalmanFilter.set_measurementNoiseCov_0(this.nativeObj, measurementNoiseCov.nativeObj);
    }

    public Mat get_errorCovPre() {
        return new Mat(KalmanFilter.get_errorCovPre_0(this.nativeObj));
    }

    public void set_errorCovPre(Mat errorCovPre) {
        KalmanFilter.set_errorCovPre_0(this.nativeObj, errorCovPre.nativeObj);
    }

    public Mat get_gain() {
        return new Mat(KalmanFilter.get_gain_0(this.nativeObj));
    }

    public void set_gain(Mat gain) {
        KalmanFilter.set_gain_0(this.nativeObj, gain.nativeObj);
    }

    public Mat get_errorCovPost() {
        return new Mat(KalmanFilter.get_errorCovPost_0(this.nativeObj));
    }

    public void set_errorCovPost(Mat errorCovPost) {
        KalmanFilter.set_errorCovPost_0(this.nativeObj, errorCovPost.nativeObj);
    }

    protected void finalize() throws Throwable {
        KalmanFilter.delete(this.nativeObj);
    }

    private static native long KalmanFilter_0();

    private static native long KalmanFilter_1(int var0, int var1, int var2, int var3);

    private static native long KalmanFilter_2(int var0, int var1, int var2);

    private static native long KalmanFilter_3(int var0, int var1);

    private static native long predict_0(long var0, long var2);

    private static native long predict_1(long var0);

    private static native long correct_0(long var0, long var2);

    private static native long get_statePre_0(long var0);

    private static native void set_statePre_0(long var0, long var2);

    private static native long get_statePost_0(long var0);

    private static native void set_statePost_0(long var0, long var2);

    private static native long get_transitionMatrix_0(long var0);

    private static native void set_transitionMatrix_0(long var0, long var2);

    private static native long get_controlMatrix_0(long var0);

    private static native void set_controlMatrix_0(long var0, long var2);

    private static native long get_measurementMatrix_0(long var0);

    private static native void set_measurementMatrix_0(long var0, long var2);

    private static native long get_processNoiseCov_0(long var0);

    private static native void set_processNoiseCov_0(long var0, long var2);

    private static native long get_measurementNoiseCov_0(long var0);

    private static native void set_measurementNoiseCov_0(long var0, long var2);

    private static native long get_errorCovPre_0(long var0);

    private static native void set_errorCovPre_0(long var0, long var2);

    private static native long get_gain_0(long var0);

    private static native void set_gain_0(long var0, long var2);

    private static native long get_errorCovPost_0(long var0);

    private static native void set_errorCovPost_0(long var0, long var2);

    private static native void delete(long var0);
}

