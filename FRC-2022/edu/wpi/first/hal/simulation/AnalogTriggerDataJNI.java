/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class AnalogTriggerDataJNI
extends JNIWrapper {
    public static native int registerInitializedCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelInitializedCallback(int var0, int var1);

    public static native boolean getInitialized(int var0);

    public static native void setInitialized(int var0, boolean var1);

    public static native int registerTriggerLowerBoundCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelTriggerLowerBoundCallback(int var0, int var1);

    public static native double getTriggerLowerBound(int var0);

    public static native void setTriggerLowerBound(int var0, double var1);

    public static native int registerTriggerUpperBoundCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelTriggerUpperBoundCallback(int var0, int var1);

    public static native double getTriggerUpperBound(int var0);

    public static native void setTriggerUpperBound(int var0, double var1);

    public static native void resetData(int var0);

    public static native int findForChannel(int var0);
}

