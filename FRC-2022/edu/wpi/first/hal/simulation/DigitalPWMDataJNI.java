/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class DigitalPWMDataJNI
extends JNIWrapper {
    public static native int registerInitializedCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelInitializedCallback(int var0, int var1);

    public static native boolean getInitialized(int var0);

    public static native void setInitialized(int var0, boolean var1);

    public static native int registerDutyCycleCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelDutyCycleCallback(int var0, int var1);

    public static native double getDutyCycle(int var0);

    public static native void setDutyCycle(int var0, double var1);

    public static native int registerPinCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelPinCallback(int var0, int var1);

    public static native int getPin(int var0);

    public static native void setPin(int var0, int var1);

    public static native void resetData(int var0);

    public static native int findForChannel(int var0);
}

