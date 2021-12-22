/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class DutyCycleDataJNI
extends JNIWrapper {
    public static native int registerInitializedCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelInitializedCallback(int var0, int var1);

    public static native boolean getInitialized(int var0);

    public static native void setInitialized(int var0, boolean var1);

    public static native int registerFrequencyCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelFrequencyCallback(int var0, int var1);

    public static native int getFrequency(int var0);

    public static native void setFrequency(int var0, int var1);

    public static native int registerOutputCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelOutputCallback(int var0, int var1);

    public static native double getOutput(int var0);

    public static native void setOutput(int var0, double var1);

    public static native void resetData(int var0);

    public static native int findForChannel(int var0);
}

