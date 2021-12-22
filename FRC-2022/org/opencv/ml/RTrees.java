/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.ml;

import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.DTrees;

public class RTrees
extends DTrees {
    protected RTrees(long addr) {
        super(addr);
    }

    public static RTrees __fromPtr__(long addr) {
        return new RTrees(addr);
    }

    public boolean getCalculateVarImportance() {
        return RTrees.getCalculateVarImportance_0(this.nativeObj);
    }

    public void setCalculateVarImportance(boolean val) {
        RTrees.setCalculateVarImportance_0(this.nativeObj, val);
    }

    public int getActiveVarCount() {
        return RTrees.getActiveVarCount_0(this.nativeObj);
    }

    public void setActiveVarCount(int val) {
        RTrees.setActiveVarCount_0(this.nativeObj, val);
    }

    public TermCriteria getTermCriteria() {
        return new TermCriteria(RTrees.getTermCriteria_0(this.nativeObj));
    }

    public void setTermCriteria(TermCriteria val) {
        RTrees.setTermCriteria_0(this.nativeObj, val.type, val.maxCount, val.epsilon);
    }

    public Mat getVarImportance() {
        return new Mat(RTrees.getVarImportance_0(this.nativeObj));
    }

    public void getVotes(Mat samples, Mat results, int flags) {
        RTrees.getVotes_0(this.nativeObj, samples.nativeObj, results.nativeObj, flags);
    }

    public double getOOBError() {
        return RTrees.getOOBError_0(this.nativeObj);
    }

    public static RTrees create() {
        return RTrees.__fromPtr__(RTrees.create_0());
    }

    public static RTrees load(String filepath, String nodeName) {
        return RTrees.__fromPtr__(RTrees.load_0(filepath, nodeName));
    }

    public static RTrees load(String filepath) {
        return RTrees.__fromPtr__(RTrees.load_1(filepath));
    }

    @Override
    protected void finalize() throws Throwable {
        RTrees.delete(this.nativeObj);
    }

    private static native boolean getCalculateVarImportance_0(long var0);

    private static native void setCalculateVarImportance_0(long var0, boolean var2);

    private static native int getActiveVarCount_0(long var0);

    private static native void setActiveVarCount_0(long var0, int var2);

    private static native double[] getTermCriteria_0(long var0);

    private static native void setTermCriteria_0(long var0, int var2, int var3, double var4);

    private static native long getVarImportance_0(long var0);

    private static native void getVotes_0(long var0, long var2, long var4, int var6);

    private static native double getOOBError_0(long var0);

    private static native long create_0();

    private static native long load_0(String var0, String var1);

    private static native long load_1(String var0);

    private static native void delete(long var0);
}

