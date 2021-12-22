/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;

public class AnalogGyroJNI
extends JNIWrapper {
    public static native int initializeAnalogGyro(int var0);

    public static native void setupAnalogGyro(int var0);

    public static native void freeAnalogGyro(int var0);

    public static native void setAnalogGyroParameters(int var0, double var1, double var3, int var5);

    public static native void setAnalogGyroVoltsPerDegreePerSecond(int var0, double var1);

    public static native void resetAnalogGyro(int var0);

    public static native void calibrateAnalogGyro(int var0);

    public static native void setAnalogGyroDeadband(int var0, double var1);

    public static native double getAnalogGyroAngle(int var0);

    public static native double getAnalogGyroRate(int var0);

    public static native double getAnalogGyroOffset(int var0);

    public static native int getAnalogGyroCenter(int var0);
}

