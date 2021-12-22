/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;

public class AccelerometerJNI
extends JNIWrapper {
    public static native void setAccelerometerActive(boolean var0);

    public static native void setAccelerometerRange(int var0);

    public static native double getAccelerometerX();

    public static native double getAccelerometerY();

    public static native double getAccelerometerZ();
}

