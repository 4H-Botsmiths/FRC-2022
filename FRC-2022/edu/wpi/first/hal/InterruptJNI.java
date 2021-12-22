/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;

public class InterruptJNI
extends JNIWrapper {
    public static final int HalInvalidHandle = 0;

    public static native int initializeInterrupts();

    public static native void cleanInterrupts(int var0);

    public static native int waitForInterrupt(int var0, double var1, boolean var3);

    public static native long readInterruptRisingTimestamp(int var0);

    public static native long readInterruptFallingTimestamp(int var0);

    public static native void requestInterrupts(int var0, int var1, int var2);

    public static native void setInterruptUpSourceEdge(int var0, boolean var1, boolean var2);

    public static native void releaseWaitingInterrupt(int var0);
}

