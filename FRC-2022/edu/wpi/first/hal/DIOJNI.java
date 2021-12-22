/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;

public class DIOJNI
extends JNIWrapper {
    public static native int initializeDIOPort(int var0, boolean var1);

    public static native boolean checkDIOChannel(int var0);

    public static native void freeDIOPort(int var0);

    public static native void setDIOSimDevice(int var0, int var1);

    public static native void setDIO(int var0, boolean var1);

    public static native void setDIODirection(int var0, boolean var1);

    public static native boolean getDIO(int var0);

    public static native boolean getDIODirection(int var0);

    public static native void pulse(int var0, double var1);

    public static native boolean isPulsing(int var0);

    public static native boolean isAnyPulsing();

    public static native short getLoopTiming();

    public static native int allocateDigitalPWM();

    public static native void freeDigitalPWM(int var0);

    public static native void setDigitalPWMRate(double var0);

    public static native void setDigitalPWMDutyCycle(int var0, double var1);

    public static native void setDigitalPWMOutputChannel(int var0, int var1);
}

