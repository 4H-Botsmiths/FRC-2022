/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;

public class SimulatorJNI
extends JNIWrapper {
    public static native void setRuntimeType(int var0);

    public static native void waitForProgramStart();

    public static native void setProgramStarted();

    public static native boolean getProgramStarted();

    public static native void restartTiming();

    public static native void pauseTiming();

    public static native void resumeTiming();

    public static native boolean isTimingPaused();

    public static native void stepTiming(long var0);

    public static native void stepTimingAsync(long var0);

    public static native void resetHandles();
}

