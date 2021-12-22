/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.ml;

import org.opencv.ml.DTrees;

public class Boost
extends DTrees {
    public static final int DISCRETE = 0;
    public static final int REAL = 1;
    public static final int LOGIT = 2;
    public static final int GENTLE = 3;

    protected Boost(long addr) {
        super(addr);
    }

    public static Boost __fromPtr__(long addr) {
        return new Boost(addr);
    }

    public int getBoostType() {
        return Boost.getBoostType_0(this.nativeObj);
    }

    public void setBoostType(int val) {
        Boost.setBoostType_0(this.nativeObj, val);
    }

    public int getWeakCount() {
        return Boost.getWeakCount_0(this.nativeObj);
    }

    public void setWeakCount(int val) {
        Boost.setWeakCount_0(this.nativeObj, val);
    }

    public double getWeightTrimRate() {
        return Boost.getWeightTrimRate_0(this.nativeObj);
    }

    public void setWeightTrimRate(double val) {
        Boost.setWeightTrimRate_0(this.nativeObj, val);
    }

    public static Boost create() {
        return Boost.__fromPtr__(Boost.create_0());
    }

    public static Boost load(String filepath, String nodeName) {
        return Boost.__fromPtr__(Boost.load_0(filepath, nodeName));
    }

    public static Boost load(String filepath) {
        return Boost.__fromPtr__(Boost.load_1(filepath));
    }

    @Override
    protected void finalize() throws Throwable {
        Boost.delete(this.nativeObj);
    }

    private static native int getBoostType_0(long var0);

    private static native void setBoostType_0(long var0, int var2);

    private static native int getWeakCount_0(long var0);

    private static native void setWeakCount_0(long var0, int var2);

    private static native double getWeightTrimRate_0(long var0);

    private static native void setWeightTrimRate_0(long var0, double var2);

    private static native long create_0();

    private static native long load_0(String var0, String var1);

    private static native long load_1(String var0);

    private static native void delete(long var0);
}

