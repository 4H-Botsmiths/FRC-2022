/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;

public class SerialPortJNI
extends JNIWrapper {
    public static native int serialInitializePort(byte var0);

    public static native int serialInitializePortDirect(byte var0, String var1);

    public static native void serialSetBaudRate(int var0, int var1);

    public static native void serialSetDataBits(int var0, byte var1);

    public static native void serialSetParity(int var0, byte var1);

    public static native void serialSetStopBits(int var0, byte var1);

    public static native void serialSetWriteMode(int var0, byte var1);

    public static native void serialSetFlowControl(int var0, byte var1);

    public static native void serialSetTimeout(int var0, double var1);

    public static native void serialEnableTermination(int var0, char var1);

    public static native void serialDisableTermination(int var0);

    public static native void serialSetReadBufferSize(int var0, int var1);

    public static native void serialSetWriteBufferSize(int var0, int var1);

    public static native int serialGetBytesReceived(int var0);

    public static native int serialRead(int var0, byte[] var1, int var2);

    public static native int serialWrite(int var0, byte[] var1, int var2);

    public static native void serialFlush(int var0);

    public static native void serialClear(int var0);

    public static native void serialClose(int var0);
}

