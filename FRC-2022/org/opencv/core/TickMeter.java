/*
 * Decompiled with CFR 0.150.
 */
package org.opencv.core;

public class TickMeter {
    protected final long nativeObj;

    protected TickMeter(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static TickMeter __fromPtr__(long addr) {
        return new TickMeter(addr);
    }

    public TickMeter() {
        this.nativeObj = TickMeter.TickMeter_0();
    }

    public void start() {
        TickMeter.start_0(this.nativeObj);
    }

    public void stop() {
        TickMeter.stop_0(this.nativeObj);
    }

    public long getTimeTicks() {
        return TickMeter.getTimeTicks_0(this.nativeObj);
    }

    public double getTimeMicro() {
        return TickMeter.getTimeMicro_0(this.nativeObj);
    }

    public double getTimeMilli() {
        return TickMeter.getTimeMilli_0(this.nativeObj);
    }

    public double getTimeSec() {
        return TickMeter.getTimeSec_0(this.nativeObj);
    }

    public long getCounter() {
        return TickMeter.getCounter_0(this.nativeObj);
    }

    public double getFPS() {
        return TickMeter.getFPS_0(this.nativeObj);
    }

    public double getAvgTimeSec() {
        return TickMeter.getAvgTimeSec_0(this.nativeObj);
    }

    public double getAvgTimeMilli() {
        return TickMeter.getAvgTimeMilli_0(this.nativeObj);
    }

    public void reset() {
        TickMeter.reset_0(this.nativeObj);
    }

    protected void finalize() throws Throwable {
        TickMeter.delete(this.nativeObj);
    }

    private static native long TickMeter_0();

    private static native void start_0(long var0);

    private static native void stop_0(long var0);

    private static native long getTimeTicks_0(long var0);

    private static native double getTimeMicro_0(long var0);

    private static native double getTimeMilli_0(long var0);

    private static native double getTimeSec_0(long var0);

    private static native long getCounter_0(long var0);

    private static native double getFPS_0(long var0);

    private static native double getAvgTimeSec_0(long var0);

    private static native double getAvgTimeMilli_0(long var0);

    private static native void reset_0(long var0);

    private static native void delete(long var0);
}

