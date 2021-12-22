/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class REVPHDataJNI
extends JNIWrapper {
    public static native int registerInitializedCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelInitializedCallback(int var0, int var1);

    public static native boolean getInitialized(int var0);

    public static native void setInitialized(int var0, boolean var1);

    public static native int registerSolenoidOutputCallback(int var0, int var1, NotifyCallback var2, boolean var3);

    public static native void cancelSolenoidOutputCallback(int var0, int var1, int var2);

    public static native boolean getSolenoidOutput(int var0, int var1);

    public static native void setSolenoidOutput(int var0, int var1, boolean var2);

    public static native int registerCompressorOnCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelCompressorOnCallback(int var0, int var1);

    public static native boolean getCompressorOn(int var0);

    public static native void setCompressorOn(int var0, boolean var1);

    public static native int registerCompressorConfigTypeCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelCompressorConfigTypeCallback(int var0, int var1);

    public static native int getCompressorConfigType(int var0);

    public static native void setCompressorConfigType(int var0, int var1);

    public static native int registerPressureSwitchCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelPressureSwitchCallback(int var0, int var1);

    public static native boolean getPressureSwitch(int var0);

    public static native void setPressureSwitch(int var0, boolean var1);

    public static native int registerCompressorCurrentCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelCompressorCurrentCallback(int var0, int var1);

    public static native double getCompressorCurrent(int var0);

    public static native void setCompressorCurrent(int var0, double var1);

    public static native void registerAllNonSolenoidCallbacks(int var0, NotifyCallback var1, boolean var2);

    public static native void registerAllSolenoidCallbacks(int var0, int var1, NotifyCallback var2, boolean var3);

    public static native void resetData(int var0);
}

