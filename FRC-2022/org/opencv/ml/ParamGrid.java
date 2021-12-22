/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.ml;

public class ParamGrid {
    protected final long nativeObj;

    protected ParamGrid(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static ParamGrid __fromPtr__(long addr) {
        return new ParamGrid(addr);
    }

    public static ParamGrid create(double minVal, double maxVal, double logstep) {
        return ParamGrid.__fromPtr__(ParamGrid.create_0(minVal, maxVal, logstep));
    }

    public static ParamGrid create(double minVal, double maxVal) {
        return ParamGrid.__fromPtr__(ParamGrid.create_1(minVal, maxVal));
    }

    public static ParamGrid create(double minVal) {
        return ParamGrid.__fromPtr__(ParamGrid.create_2(minVal));
    }

    public static ParamGrid create() {
        return ParamGrid.__fromPtr__(ParamGrid.create_3());
    }

    public double get_minVal() {
        return ParamGrid.get_minVal_0(this.nativeObj);
    }

    public void set_minVal(double minVal) {
        ParamGrid.set_minVal_0(this.nativeObj, minVal);
    }

    public double get_maxVal() {
        return ParamGrid.get_maxVal_0(this.nativeObj);
    }

    public void set_maxVal(double maxVal) {
        ParamGrid.set_maxVal_0(this.nativeObj, maxVal);
    }

    public double get_logStep() {
        return ParamGrid.get_logStep_0(this.nativeObj);
    }

    public void set_logStep(double logStep) {
        ParamGrid.set_logStep_0(this.nativeObj, logStep);
    }

    protected void finalize() throws Throwable {
        ParamGrid.delete(this.nativeObj);
    }

    private static native long create_0(double var0, double var2, double var4);

    private static native long create_1(double var0, double var2);

    private static native long create_2(double var0);

    private static native long create_3();

    private static native double get_minVal_0(long var0);

    private static native void set_minVal_0(long var0, double var2);

    private static native double get_maxVal_0(long var0);

    private static native void set_maxVal_0(long var0, double var2);

    private static native double get_logStep_0(long var0);

    private static native void set_logStep_0(long var0, double var2);

    private static native void delete(long var0);
}

