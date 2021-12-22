/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.ml;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;
import org.opencv.ml.TrainData;

public class StatModel
extends Algorithm {
    public static final int UPDATE_MODEL = 1;
    public static final int RAW_OUTPUT = 1;
    public static final int COMPRESSED_INPUT = 2;
    public static final int PREPROCESSED_INPUT = 4;

    protected StatModel(long addr) {
        super(addr);
    }

    public static StatModel __fromPtr__(long addr) {
        return new StatModel(addr);
    }

    public int getVarCount() {
        return StatModel.getVarCount_0(this.nativeObj);
    }

    @Override
    public boolean empty() {
        return StatModel.empty_0(this.nativeObj);
    }

    public boolean isTrained() {
        return StatModel.isTrained_0(this.nativeObj);
    }

    public boolean isClassifier() {
        return StatModel.isClassifier_0(this.nativeObj);
    }

    public boolean train(TrainData trainData, int flags) {
        return StatModel.train_0(this.nativeObj, trainData.getNativeObjAddr(), flags);
    }

    public boolean train(TrainData trainData) {
        return StatModel.train_1(this.nativeObj, trainData.getNativeObjAddr());
    }

    public boolean train(Mat samples, int layout, Mat responses) {
        return StatModel.train_2(this.nativeObj, samples.nativeObj, layout, responses.nativeObj);
    }

    public float calcError(TrainData data, boolean test, Mat resp) {
        return StatModel.calcError_0(this.nativeObj, data.getNativeObjAddr(), test, resp.nativeObj);
    }

    public float predict(Mat samples, Mat results, int flags) {
        return StatModel.predict_0(this.nativeObj, samples.nativeObj, results.nativeObj, flags);
    }

    public float predict(Mat samples, Mat results) {
        return StatModel.predict_1(this.nativeObj, samples.nativeObj, results.nativeObj);
    }

    public float predict(Mat samples) {
        return StatModel.predict_2(this.nativeObj, samples.nativeObj);
    }

    @Override
    protected void finalize() throws Throwable {
        StatModel.delete(this.nativeObj);
    }

    private static native int getVarCount_0(long var0);

    private static native boolean empty_0(long var0);

    private static native boolean isTrained_0(long var0);

    private static native boolean isClassifier_0(long var0);

    private static native boolean train_0(long var0, long var2, int var4);

    private static native boolean train_1(long var0, long var2);

    private static native boolean train_2(long var0, long var2, int var4, long var5);

    private static native float calcError_0(long var0, long var2, boolean var4, long var5);

    private static native float predict_0(long var0, long var2, long var4, int var6);

    private static native float predict_1(long var0, long var2, long var4);

    private static native float predict_2(long var0, long var2);

    private static native void delete(long var0);
}

