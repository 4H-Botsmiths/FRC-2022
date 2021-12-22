/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.ml;

import java.util.List;
import org.opencv.core.Mat;

public class TrainData {
    protected final long nativeObj;

    protected TrainData(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static TrainData __fromPtr__(long addr) {
        return new TrainData(addr);
    }

    public int getLayout() {
        return TrainData.getLayout_0(this.nativeObj);
    }

    public int getNTrainSamples() {
        return TrainData.getNTrainSamples_0(this.nativeObj);
    }

    public int getNTestSamples() {
        return TrainData.getNTestSamples_0(this.nativeObj);
    }

    public int getNSamples() {
        return TrainData.getNSamples_0(this.nativeObj);
    }

    public int getNVars() {
        return TrainData.getNVars_0(this.nativeObj);
    }

    public int getNAllVars() {
        return TrainData.getNAllVars_0(this.nativeObj);
    }

    public void getSample(Mat varIdx, int sidx, float buf) {
        TrainData.getSample_0(this.nativeObj, varIdx.nativeObj, sidx, buf);
    }

    public Mat getSamples() {
        return new Mat(TrainData.getSamples_0(this.nativeObj));
    }

    public Mat getMissing() {
        return new Mat(TrainData.getMissing_0(this.nativeObj));
    }

    public Mat getTrainSamples(int layout, boolean compressSamples, boolean compressVars) {
        return new Mat(TrainData.getTrainSamples_0(this.nativeObj, layout, compressSamples, compressVars));
    }

    public Mat getTrainSamples(int layout, boolean compressSamples) {
        return new Mat(TrainData.getTrainSamples_1(this.nativeObj, layout, compressSamples));
    }

    public Mat getTrainSamples(int layout) {
        return new Mat(TrainData.getTrainSamples_2(this.nativeObj, layout));
    }

    public Mat getTrainSamples() {
        return new Mat(TrainData.getTrainSamples_3(this.nativeObj));
    }

    public Mat getTrainResponses() {
        return new Mat(TrainData.getTrainResponses_0(this.nativeObj));
    }

    public Mat getTrainNormCatResponses() {
        return new Mat(TrainData.getTrainNormCatResponses_0(this.nativeObj));
    }

    public Mat getTestResponses() {
        return new Mat(TrainData.getTestResponses_0(this.nativeObj));
    }

    public Mat getTestNormCatResponses() {
        return new Mat(TrainData.getTestNormCatResponses_0(this.nativeObj));
    }

    public Mat getResponses() {
        return new Mat(TrainData.getResponses_0(this.nativeObj));
    }

    public Mat getNormCatResponses() {
        return new Mat(TrainData.getNormCatResponses_0(this.nativeObj));
    }

    public Mat getSampleWeights() {
        return new Mat(TrainData.getSampleWeights_0(this.nativeObj));
    }

    public Mat getTrainSampleWeights() {
        return new Mat(TrainData.getTrainSampleWeights_0(this.nativeObj));
    }

    public Mat getTestSampleWeights() {
        return new Mat(TrainData.getTestSampleWeights_0(this.nativeObj));
    }

    public Mat getVarIdx() {
        return new Mat(TrainData.getVarIdx_0(this.nativeObj));
    }

    public Mat getVarType() {
        return new Mat(TrainData.getVarType_0(this.nativeObj));
    }

    public Mat getVarSymbolFlags() {
        return new Mat(TrainData.getVarSymbolFlags_0(this.nativeObj));
    }

    public int getResponseType() {
        return TrainData.getResponseType_0(this.nativeObj);
    }

    public Mat getTrainSampleIdx() {
        return new Mat(TrainData.getTrainSampleIdx_0(this.nativeObj));
    }

    public Mat getTestSampleIdx() {
        return new Mat(TrainData.getTestSampleIdx_0(this.nativeObj));
    }

    public void getValues(int vi, Mat sidx, float values) {
        TrainData.getValues_0(this.nativeObj, vi, sidx.nativeObj, values);
    }

    public Mat getDefaultSubstValues() {
        return new Mat(TrainData.getDefaultSubstValues_0(this.nativeObj));
    }

    public int getCatCount(int vi) {
        return TrainData.getCatCount_0(this.nativeObj, vi);
    }

    public Mat getClassLabels() {
        return new Mat(TrainData.getClassLabels_0(this.nativeObj));
    }

    public Mat getCatOfs() {
        return new Mat(TrainData.getCatOfs_0(this.nativeObj));
    }

    public Mat getCatMap() {
        return new Mat(TrainData.getCatMap_0(this.nativeObj));
    }

    public void setTrainTestSplit(int count, boolean shuffle) {
        TrainData.setTrainTestSplit_0(this.nativeObj, count, shuffle);
    }

    public void setTrainTestSplit(int count) {
        TrainData.setTrainTestSplit_1(this.nativeObj, count);
    }

    public void setTrainTestSplitRatio(double ratio, boolean shuffle) {
        TrainData.setTrainTestSplitRatio_0(this.nativeObj, ratio, shuffle);
    }

    public void setTrainTestSplitRatio(double ratio) {
        TrainData.setTrainTestSplitRatio_1(this.nativeObj, ratio);
    }

    public void shuffleTrainTest() {
        TrainData.shuffleTrainTest_0(this.nativeObj);
    }

    public Mat getTestSamples() {
        return new Mat(TrainData.getTestSamples_0(this.nativeObj));
    }

    public void getNames(List<String> names) {
        TrainData.getNames_0(this.nativeObj, names);
    }

    public static Mat getSubVector(Mat vec, Mat idx) {
        return new Mat(TrainData.getSubVector_0(vec.nativeObj, idx.nativeObj));
    }

    public static Mat getSubMatrix(Mat matrix, Mat idx, int layout) {
        return new Mat(TrainData.getSubMatrix_0(matrix.nativeObj, idx.nativeObj, layout));
    }

    public static TrainData create(Mat samples, int layout, Mat responses, Mat varIdx, Mat sampleIdx, Mat sampleWeights, Mat varType) {
        return TrainData.__fromPtr__(TrainData.create_0(samples.nativeObj, layout, responses.nativeObj, varIdx.nativeObj, sampleIdx.nativeObj, sampleWeights.nativeObj, varType.nativeObj));
    }

    public static TrainData create(Mat samples, int layout, Mat responses, Mat varIdx, Mat sampleIdx, Mat sampleWeights) {
        return TrainData.__fromPtr__(TrainData.create_1(samples.nativeObj, layout, responses.nativeObj, varIdx.nativeObj, sampleIdx.nativeObj, sampleWeights.nativeObj));
    }

    public static TrainData create(Mat samples, int layout, Mat responses, Mat varIdx, Mat sampleIdx) {
        return TrainData.__fromPtr__(TrainData.create_2(samples.nativeObj, layout, responses.nativeObj, varIdx.nativeObj, sampleIdx.nativeObj));
    }

    public static TrainData create(Mat samples, int layout, Mat responses, Mat varIdx) {
        return TrainData.__fromPtr__(TrainData.create_3(samples.nativeObj, layout, responses.nativeObj, varIdx.nativeObj));
    }

    public static TrainData create(Mat samples, int layout, Mat responses) {
        return TrainData.__fromPtr__(TrainData.create_4(samples.nativeObj, layout, responses.nativeObj));
    }

    protected void finalize() throws Throwable {
        TrainData.delete(this.nativeObj);
    }

    private static native int getLayout_0(long var0);

    private static native int getNTrainSamples_0(long var0);

    private static native int getNTestSamples_0(long var0);

    private static native int getNSamples_0(long var0);

    private static native int getNVars_0(long var0);

    private static native int getNAllVars_0(long var0);

    private static native void getSample_0(long var0, long var2, int var4, float var5);

    private static native long getSamples_0(long var0);

    private static native long getMissing_0(long var0);

    private static native long getTrainSamples_0(long var0, int var2, boolean var3, boolean var4);

    private static native long getTrainSamples_1(long var0, int var2, boolean var3);

    private static native long getTrainSamples_2(long var0, int var2);

    private static native long getTrainSamples_3(long var0);

    private static native long getTrainResponses_0(long var0);

    private static native long getTrainNormCatResponses_0(long var0);

    private static native long getTestResponses_0(long var0);

    private static native long getTestNormCatResponses_0(long var0);

    private static native long getResponses_0(long var0);

    private static native long getNormCatResponses_0(long var0);

    private static native long getSampleWeights_0(long var0);

    private static native long getTrainSampleWeights_0(long var0);

    private static native long getTestSampleWeights_0(long var0);

    private static native long getVarIdx_0(long var0);

    private static native long getVarType_0(long var0);

    private static native long getVarSymbolFlags_0(long var0);

    private static native int getResponseType_0(long var0);

    private static native long getTrainSampleIdx_0(long var0);

    private static native long getTestSampleIdx_0(long var0);

    private static native void getValues_0(long var0, int var2, long var3, float var5);

    private static native long getDefaultSubstValues_0(long var0);

    private static native int getCatCount_0(long var0, int var2);

    private static native long getClassLabels_0(long var0);

    private static native long getCatOfs_0(long var0);

    private static native long getCatMap_0(long var0);

    private static native void setTrainTestSplit_0(long var0, int var2, boolean var3);

    private static native void setTrainTestSplit_1(long var0, int var2);

    private static native void setTrainTestSplitRatio_0(long var0, double var2, boolean var4);

    private static native void setTrainTestSplitRatio_1(long var0, double var2);

    private static native void shuffleTrainTest_0(long var0);

    private static native long getTestSamples_0(long var0);

    private static native void getNames_0(long var0, List<String> var2);

    private static native long getSubVector_0(long var0, long var2);

    private static native long getSubMatrix_0(long var0, long var2, int var4);

    private static native long create_0(long var0, int var2, long var3, long var5, long var7, long var9, long var11);

    private static native long create_1(long var0, int var2, long var3, long var5, long var7, long var9);

    private static native long create_2(long var0, int var2, long var3, long var5, long var7);

    private static native long create_3(long var0, int var2, long var3, long var5);

    private static native long create_4(long var0, int var2, long var3);

    private static native void delete(long var0);
}

