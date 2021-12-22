/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class SPIAccelerometerDataJNI
extends JNIWrapper {
    public static native int registerActiveCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelActiveCallback(int var0, int var1);

    public static native boolean getActive(int var0);

    public static native void setActive(int var0, boolean var1);

    public static native int registerRangeCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelRangeCallback(int var0, int var1);

    public static native int getRange(int var0);

    public static native void setRange(int var0, int var1);

    public static native int registerXCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelXCallback(int var0, int var1);

    public static native double getX(int var0);

    public static native void setX(int var0, double var1);

    public static native int registerYCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelYCallback(int var0, int var1);

    public static native double getY(int var0);

    public static native void setY(int var0, double var1);

    public static native int registerZCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelZCallback(int var0, int var1);

    public static native double getZ(int var0);

    public static native void setZ(int var0, double var1);

    public static native void resetData(int var0);
}

