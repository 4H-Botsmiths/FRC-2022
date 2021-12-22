/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class PowerDistributionDataJNI
extends JNIWrapper {
    public static native int registerInitializedCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelInitializedCallback(int var0, int var1);

    public static native boolean getInitialized(int var0);

    public static native void setInitialized(int var0, boolean var1);

    public static native int registerTemperatureCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelTemperatureCallback(int var0, int var1);

    public static native double getTemperature(int var0);

    public static native void setTemperature(int var0, double var1);

    public static native int registerVoltageCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelVoltageCallback(int var0, int var1);

    public static native double getVoltage(int var0);

    public static native void setVoltage(int var0, double var1);

    public static native int registerCurrentCallback(int var0, int var1, NotifyCallback var2, boolean var3);

    public static native void cancelCurrentCallback(int var0, int var1, int var2);

    public static native double getCurrent(int var0, int var1);

    public static native void setCurrent(int var0, int var1, double var2);

    public static native void resetData(int var0);
}
