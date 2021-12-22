/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.ml;

import org.opencv.core.Mat;
import org.opencv.ml.StatModel;

public class NormalBayesClassifier
extends StatModel {
    protected NormalBayesClassifier(long addr) {
        super(addr);
    }

    public static NormalBayesClassifier __fromPtr__(long addr) {
        return new NormalBayesClassifier(addr);
    }

    public float predictProb(Mat inputs, Mat outputs, Mat outputProbs, int flags) {
        return NormalBayesClassifier.predictProb_0(this.nativeObj, inputs.nativeObj, outputs.nativeObj, outputProbs.nativeObj, flags);
    }

    public float predictProb(Mat inputs, Mat outputs, Mat outputProbs) {
        return NormalBayesClassifier.predictProb_1(this.nativeObj, inputs.nativeObj, outputs.nativeObj, outputProbs.nativeObj);
    }

    public static NormalBayesClassifier create() {
        return NormalBayesClassifier.__fromPtr__(NormalBayesClassifier.create_0());
    }

    public static NormalBayesClassifier load(String filepath, String nodeName) {
        return NormalBayesClassifier.__fromPtr__(NormalBayesClassifier.load_0(filepath, nodeName));
    }

    public static NormalBayesClassifier load(String filepath) {
        return NormalBayesClassifier.__fromPtr__(NormalBayesClassifier.load_1(filepath));
    }

    @Override
    protected void finalize() throws Throwable {
        NormalBayesClassifier.delete(this.nativeObj);
    }

    private static native float predictProb_0(long var0, long var2, long var4, long var6, int var8);

    private static native float predictProb_1(long var0, long var2, long var4, long var6);

    private static native long create_0();

    private static native long load_0(String var0, String var1);

    private static native long load_1(String var0);

    private static native void delete(long var0);
}

