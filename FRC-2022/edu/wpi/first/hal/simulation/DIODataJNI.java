/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class DIODataJNI
extends JNIWrapper {
    public static native int registerInitializedCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelInitializedCallback(int var0, int var1);

    public static native boolean getInitialized(int var0);

    public static native void setInitialized(int var0, boolean var1);

    public static native int registerValueCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelValueCallback(int var0, int var1);

    public static native boolean getValue(int var0);

    public static native void setValue(int var0, boolean var1);

    public static native int registerPulseLengthCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelPulseLengthCallback(int var0, int var1);

    public static native double getPulseLength(int var0);

    public static native void setPulseLength(int var0, double var1);

    public static native int registerIsInputCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelIsInputCallback(int var0, int var1);

    public static native boolean getIsInput(int var0);

    public static native void setIsInput(int var0, boolean var1);

    public static native int registerFilterIndexCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelFilterIndexCallback(int var0, int var1);

    public static native int getFilterIndex(int var0);

    public static native void setFilterIndex(int var0, int var1);

    public static native void resetData(int var0);
}

