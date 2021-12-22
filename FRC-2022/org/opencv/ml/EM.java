/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.ml;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.StatModel;
import org.opencv.utils.Converters;

public class EM
extends StatModel {
    public static final int DEFAULT_NCLUSTERS = 5;
    public static final int DEFAULT_MAX_ITERS = 100;
    public static final int START_E_STEP = 1;
    public static final int START_M_STEP = 2;
    public static final int START_AUTO_STEP = 0;
    public static final int COV_MAT_SPHERICAL = 0;
    public static final int COV_MAT_DIAGONAL = 1;
    public static final int COV_MAT_GENERIC = 2;
    public static final int COV_MAT_DEFAULT = 1;

    protected EM(long addr) {
        super(addr);
    }

    public static EM __fromPtr__(long addr) {
        return new EM(addr);
    }

    public int getClustersNumber() {
        return EM.getClustersNumber_0(this.nativeObj);
    }

    public void setClustersNumber(int val) {
        EM.setClustersNumber_0(this.nativeObj, val);
    }

    public int getCovarianceMatrixType() {
        return EM.getCovarianceMatrixType_0(this.nativeObj);
    }

    public void setCovarianceMatrixType(int val) {
        EM.setCovarianceMatrixType_0(this.nativeObj, val);
    }

    public TermCriteria getTermCriteria() {
        return new TermCriteria(EM.getTermCriteria_0(this.nativeObj));
    }

    public void setTermCriteria(TermCriteria val) {
        EM.setTermCriteria_0(this.nativeObj, val.type, val.maxCount, val.epsilon);
    }

    public Mat getWeights() {
        return new Mat(EM.getWeights_0(this.nativeObj));
    }

    public Mat getMeans() {
        return new Mat(EM.getMeans_0(this.nativeObj));
    }

    public void getCovs(List<Mat> covs) {
        Mat covs_mat = new Mat();
        EM.getCovs_0(this.nativeObj, covs_mat.nativeObj);
        Converters.Mat_to_vector_Mat(covs_mat, covs);
        covs_mat.release();
    }

    @Override
    public float predict(Mat samples, Mat results, int flags) {
        return EM.predict_0(this.nativeObj, samples.nativeObj, results.nativeObj, flags);
    }

    @Override
    public float predict(Mat samples, Mat results) {
        return EM.predict_1(this.nativeObj, samples.nativeObj, results.nativeObj);
    }

    @Override
    public float predict(Mat samples) {
        return EM.predict_2(this.nativeObj, samples.nativeObj);
    }

    public double[] predict2(Mat sample, Mat probs) {
        return EM.predict2_0(this.nativeObj, sample.nativeObj, probs.nativeObj);
    }

    public boolean trainEM(Mat samples, Mat logLikelihoods, Mat labels, Mat probs) {
        return EM.trainEM_0(this.nativeObj, samples.nativeObj, logLikelihoods.nativeObj, labels.nativeObj, probs.nativeObj);
    }

    public boolean trainEM(Mat samples, Mat logLikelihoods, Mat labels) {
        return EM.trainEM_1(this.nativeObj, samples.nativeObj, logLikelihoods.nativeObj, labels.nativeObj);
    }

    public boolean trainEM(Mat samples, Mat logLikelihoods) {
        return EM.trainEM_2(this.nativeObj, samples.nativeObj, logLikelihoods.nativeObj);
    }

    public boolean trainEM(Mat samples) {
        return EM.trainEM_3(this.nativeObj, samples.nativeObj);
    }

    public boolean trainE(Mat samples, Mat means0, Mat covs0, Mat weights0, Mat logLikelihoods, Mat labels, Mat probs) {
        return EM.trainE_0(this.nativeObj, samples.nativeObj, means0.nativeObj, covs0.nativeObj, weights0.nativeObj, logLikelihoods.nativeObj, labels.nativeObj, probs.nativeObj);
    }

    public boolean trainE(Mat samples, Mat means0, Mat covs0, Mat weights0, Mat logLikelihoods, Mat labels) {
        return EM.trainE_1(this.nativeObj, samples.nativeObj, means0.nativeObj, covs0.nativeObj, weights0.nativeObj, logLikelihoods.nativeObj, labels.nativeObj);
    }

    public boolean trainE(Mat samples, Mat means0, Mat covs0, Mat weights0, Mat logLikelihoods) {
        return EM.trainE_2(this.nativeObj, samples.nativeObj, means0.nativeObj, covs0.nativeObj, weights0.nativeObj, logLikelihoods.nativeObj);
    }

    public boolean trainE(Mat samples, Mat means0, Mat covs0, Mat weights0) {
        return EM.trainE_3(this.nativeObj, samples.nativeObj, means0.nativeObj, covs0.nativeObj, weights0.nativeObj);
    }

    public boolean trainE(Mat samples, Mat means0, Mat covs0) {
        return EM.trainE_4(this.nativeObj, samples.nativeObj, means0.nativeObj, covs0.nativeObj);
    }

    public boolean trainE(Mat samples, Mat means0) {
        return EM.trainE_5(this.nativeObj, samples.nativeObj, means0.nativeObj);
    }

    public boolean trainM(Mat samples, Mat probs0, Mat logLikelihoods, Mat labels, Mat probs) {
        return EM.trainM_0(this.nativeObj, samples.nativeObj, probs0.nativeObj, logLikelihoods.nativeObj, labels.nativeObj, probs.nativeObj);
    }

    public boolean trainM(Mat samples, Mat probs0, Mat logLikelihoods, Mat labels) {
        return EM.trainM_1(this.nativeObj, samples.nativeObj, probs0.nativeObj, logLikelihoods.nativeObj, labels.nativeObj);
    }

    public boolean trainM(Mat samples, Mat probs0, Mat logLikelihoods) {
        return EM.trainM_2(this.nativeObj, samples.nativeObj, probs0.nativeObj, logLikelihoods.nativeObj);
    }

    public boolean trainM(Mat samples, Mat probs0) {
        return EM.trainM_3(this.nativeObj, samples.nativeObj, probs0.nativeObj);
    }

    public static EM create() {
        return EM.__fromPtr__(EM.create_0());
    }

    public static EM load(String filepath, String nodeName) {
        return EM.__fromPtr__(EM.load_0(filepath, nodeName));
    }

    public static EM load(String filepath) {
        return EM.__fromPtr__(EM.load_1(filepath));
    }

    @Override
    protected void finalize() throws Throwable {
        EM.delete(this.nativeObj);
    }

    private static native int getClustersNumber_0(long var0);

    private static native void setClustersNumber_0(long var0, int var2);

    private static native int getCovarianceMatrixType_0(long var0);

    private static native void setCovarianceMatrixType_0(long var0, int var2);

    private static native double[] getTermCriteria_0(long var0);

    private static native void setTermCriteria_0(long var0, int var2, int var3, double var4);

    private static native long getWeights_0(long var0);

    private static native long getMeans_0(long var0);

    private static native void getCovs_0(long var0, long var2);

    private static native float predict_0(long var0, long var2, long var4, int var6);

    private static native float predict_1(long var0, long var2, long var4);

    private static native float predict_2(long var0, long var2);

    private static native double[] predict2_0(long var0, long var2, long var4);

    private static native boolean trainEM_0(long var0, long var2, long var4, long var6, long var8);

    private static native boolean trainEM_1(long var0, long var2, long var4, long var6);

    private static native boolean trainEM_2(long var0, long var2, long var4);

    private static native boolean trainEM_3(long var0, long var2);

    private static native boolean trainE_0(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14);

    private static native boolean trainE_1(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

    private static native boolean trainE_2(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native boolean trainE_3(long var0, long var2, long var4, long var6, long var8);

    private static native boolean trainE_4(long var0, long var2, long var4, long var6);

    private static native boolean trainE_5(long var0, long var2, long var4);

    private static native boolean trainM_0(long var0, long var2, long var4, long var6, long var8, long var10);

    private static native boolean trainM_1(long var0, long var2, long var4, long var6, long var8);

    private static native boolean trainM_2(long var0, long var2, long var4, long var6);

    private static native boolean trainM_3(long var0, long var2, long var4);

    private static native long create_0();

    private static native long load_0(String var0, String var1);

    private static native long load_1(String var0);

    private static native void delete(long var0);
}

