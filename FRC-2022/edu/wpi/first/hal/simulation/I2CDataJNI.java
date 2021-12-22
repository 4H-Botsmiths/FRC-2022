/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.BufferCallback;
import edu.wpi.first.hal.simulation.ConstBufferCallback;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class I2CDataJNI
extends JNIWrapper {
    public static native int registerInitializedCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelInitializedCallback(int var0, int var1);

    public static native boolean getInitialized(int var0);

    public static native void setInitialized(int var0, boolean var1);

    public static native int registerReadCallback(int var0, BufferCallback var1);

    public static native void cancelReadCallback(int var0, int var1);

    public static native int registerWriteCallback(int var0, ConstBufferCallback var1);

    public static native void cancelWriteCallback(int var0, int var1);

    public static native void resetData(int var0);
}

