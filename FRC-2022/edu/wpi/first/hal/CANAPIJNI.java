/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.CANData;
import edu.wpi.first.hal.JNIWrapper;

public class CANAPIJNI
extends JNIWrapper {
    public static native int initializeCAN(int var0, int var1, int var2);

    public static native void cleanCAN(int var0);

    public static native void writeCANPacket(int var0, byte[] var1, int var2);

    public static native void writeCANPacketRepeating(int var0, byte[] var1, int var2, int var3);

    public static native void writeCANRTRFrame(int var0, int var1, int var2);

    public static native int writeCANPacketNoThrow(int var0, byte[] var1, int var2);

    public static native int writeCANPacketRepeatingNoThrow(int var0, byte[] var1, int var2, int var3);

    public static native int writeCANRTRFrameNoThrow(int var0, int var1, int var2);

    public static native void stopCANPacketRepeating(int var0, int var1);

    public static native boolean readCANPacketNew(int var0, int var1, CANData var2);

    public static native boolean readCANPacketLatest(int var0, int var1, CANData var2);

    public static native boolean readCANPacketTimeout(int var0, int var1, int var2, CANData var3);
}

