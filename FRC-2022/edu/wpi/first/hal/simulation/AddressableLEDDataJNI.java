/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.ConstBufferCallback;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class AddressableLEDDataJNI
extends JNIWrapper {
    public static native int registerInitializedCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelInitializedCallback(int var0, int var1);

    public static native boolean getInitialized(int var0);

    public static native void setInitialized(int var0, boolean var1);

    public static native int registerOutputPortCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelOutputPortCallback(int var0, int var1);

    public static native int getOutputPort(int var0);

    public static native void setOutputPort(int var0, int var1);

    public static native int registerLengthCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelLengthCallback(int var0, int var1);

    public static native int getLength(int var0);

    public static native void setLength(int var0, int var1);

    public static native int registerRunningCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelRunningCallback(int var0, int var1);

    public static native boolean getRunning(int var0);

    public static native void setRunning(int var0, boolean var1);

    public static native int registerDataCallback(int var0, ConstBufferCallback var1);

    public static native void cancelDataCallback(int var0, int var1);

    public static native byte[] getData(int var0);

    public static native void setData(int var0, byte[] var1);

    public static native void resetData(int var0);

    public static native int findForChannel(int var0);
}

