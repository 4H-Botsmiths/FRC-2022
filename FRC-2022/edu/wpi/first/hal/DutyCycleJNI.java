/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;

public class DutyCycleJNI
extends JNIWrapper {
    public static native int initialize(int var0, int var1);

    public static native void free(int var0);

    public static native int getFrequency(int var0);

    public static native double getOutput(int var0);

    public static native int getOutputRaw(int var0);

    public static native int getOutputScaleFactor(int var0);

    public static native int getFPGAIndex(int var0);
}

