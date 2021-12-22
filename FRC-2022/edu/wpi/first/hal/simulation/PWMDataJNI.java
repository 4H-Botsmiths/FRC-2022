/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class PWMDataJNI
extends JNIWrapper {
    public static native int registerInitializedCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelInitializedCallback(int var0, int var1);

    public static native boolean getInitialized(int var0);

    public static native void setInitialized(int var0, boolean var1);

    public static native int registerRawValueCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelRawValueCallback(int var0, int var1);

    public static native int getRawValue(int var0);

    public static native void setRawValue(int var0, int var1);

    public static native int registerSpeedCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelSpeedCallback(int var0, int var1);

    public static native double getSpeed(int var0);

    public static native void setSpeed(int var0, double var1);

    public static native int registerPositionCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelPositionCallback(int var0, int var1);

    public static native double getPosition(int var0);

    public static native void setPosition(int var0, double var1);

    public static native int registerPeriodScaleCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelPeriodScaleCallback(int var0, int var1);

    public static native int getPeriodScale(int var0);

    public static native void setPeriodScale(int var0, int var1);

    public static native int registerZeroLatchCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelZeroLatchCallback(int var0, int var1);

    public static native boolean getZeroLatch(int var0);

    public static native void setZeroLatch(int var0, boolean var1);

    public static native void resetData(int var0);
}

