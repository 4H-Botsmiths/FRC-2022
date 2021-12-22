/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class AnalogGyroDataJNI
extends JNIWrapper {
    public static native int registerAngleCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelAngleCallback(int var0, int var1);

    public static native double getAngle(int var0);

    public static native void setAngle(int var0, double var1);

    public static native int registerRateCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelRateCallback(int var0, int var1);

    public static native double getRate(int var0);

    public static native void setRate(int var0, double var1);

    public static native int registerInitializedCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelInitializedCallback(int var0, int var1);

    public static native boolean getInitialized(int var0);

    public static native void setInitialized(int var0, boolean var1);

    public static native void resetData(int var0);
}

