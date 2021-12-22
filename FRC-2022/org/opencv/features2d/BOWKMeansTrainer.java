/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.features2d;

import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.features2d.BOWTrainer;

public class BOWKMeansTrainer
extends BOWTrainer {
    protected BOWKMeansTrainer(long addr) {
        super(addr);
    }

    public static BOWKMeansTrainer __fromPtr__(long addr) {
        return new BOWKMeansTrainer(addr);
    }

    public BOWKMeansTrainer(int clusterCount, TermCriteria termcrit, int attempts, int flags) {
        super(BOWKMeansTrainer.BOWKMeansTrainer_0(clusterCount, termcrit.type, termcrit.maxCount, termcrit.epsilon, attempts, flags));
    }

    public BOWKMeansTrainer(int clusterCount, TermCriteria termcrit, int attempts) {
        super(BOWKMeansTrainer.BOWKMeansTrainer_1(clusterCount, termcrit.type, termcrit.maxCount, termcrit.epsilon, attempts));
    }

    public BOWKMeansTrainer(int clusterCount, TermCriteria termcrit) {
        super(BOWKMeansTrainer.BOWKMeansTrainer_2(clusterCount, termcrit.type, termcrit.maxCount, termcrit.epsilon));
    }

    public BOWKMeansTrainer(int clusterCount) {
        super(BOWKMeansTrainer.BOWKMeansTrainer_3(clusterCount));
    }

    @Override
    public Mat cluster() {
        return new Mat(BOWKMeansTrainer.cluster_0(this.nativeObj));
    }

    @Override
    public Mat cluster(Mat descriptors) {
        return new Mat(BOWKMeansTrainer.cluster_1(this.nativeObj, descriptors.nativeObj));
    }

    @Override
    protected void finalize() throws Throwable {
        BOWKMeansTrainer.delete(this.nativeObj);
    }

    private static native long BOWKMeansTrainer_0(int var0, int var1, int var2, double var3, int var5, int var6);

    private static native long BOWKMeansTrainer_1(int var0, int var1, int var2, double var3, int var5);

    private static native long BOWKMeansTrainer_2(int var0, int var1, int var2, double var3);

    private static native long BOWKMeansTrainer_3(int var0);

    private static native long cluster_0(long var0);

    private static native long cluster_1(long var0, long var2);

    private static native void delete(long var0);
}

