/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class RelayDataJNI
extends JNIWrapper {
    public static native int registerInitializedForwardCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelInitializedForwardCallback(int var0, int var1);

    public static native boolean getInitializedForward(int var0);

    public static native void setInitializedForward(int var0, boolean var1);

    public static native int registerInitializedReverseCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelInitializedReverseCallback(int var0, int var1);

    public static native boolean getInitializedReverse(int var0);

    public static native void setInitializedReverse(int var0, boolean var1);

    public static native int registerForwardCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelForwardCallback(int var0, int var1);

    public static native boolean getForward(int var0);

    public static native void setForward(int var0, boolean var1);

    public static native int registerReverseCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelReverseCallback(int var0, int var1);

    public static native boolean getReverse(int var0);

    public static native void setReverse(int var0, boolean var1);

    public static native void resetData(int var0);
}

