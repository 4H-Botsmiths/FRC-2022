/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

public class Algorithm {
    protected final long nativeObj;

    protected Algorithm(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static Algorithm __fromPtr__(long addr) {
        return new Algorithm(addr);
    }

    public void clear() {
        Algorithm.clear_0(this.nativeObj);
    }

    public boolean empty() {
        return Algorithm.empty_0(this.nativeObj);
    }

    public void save(String filename) {
        Algorithm.save_0(this.nativeObj, filename);
    }

    public String getDefaultName() {
        return Algorithm.getDefaultName_0(this.nativeObj);
    }

    protected void finalize() throws Throwable {
        Algorithm.delete(this.nativeObj);
    }

    private static native void clear_0(long var0);

    private static native boolean empty_0(long var0);

    private static native void save_0(long var0, String var2);

    private static native String getDefaultName_0(long var0);

    private static native void delete(long var0);
}

