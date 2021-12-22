/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;

public class AddressableLEDJNI
extends JNIWrapper {
    public static native int initialize(int var0);

    public static native void free(int var0);

    public static native void setLength(int var0, int var1);

    public static native void setData(int var0, byte[] var1);

    public static native void setBitTiming(int var0, int var1, int var2, int var3, int var4);

    public static native void setSyncTime(int var0, int var1);

    public static native void start(int var0);

    public static native void stop(int var0);
}

