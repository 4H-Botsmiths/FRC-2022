/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;
import java.nio.ByteBuffer;

public class SPIJNI
extends JNIWrapper {
    public static native void spiInitialize(int var0);

    public static native int spiTransaction(int var0, ByteBuffer var1, ByteBuffer var2, byte var3);

    public static native int spiTransactionB(int var0, byte[] var1, byte[] var2, byte var3);

    public static native int spiWrite(int var0, ByteBuffer var1, byte var2);

    public static native int spiWriteB(int var0, byte[] var1, byte var2);

    public static native int spiRead(int var0, boolean var1, ByteBuffer var2, byte var3);

    public static native int spiReadB(int var0, boolean var1, byte[] var2, byte var3);

    public static native void spiClose(int var0);

    public static native void spiSetSpeed(int var0, int var1);

    public static native void spiSetOpts(int var0, int var1, int var2, int var3);

    public static native void spiSetChipSelectActiveHigh(int var0);

    public static native void spiSetChipSelectActiveLow(int var0);

    public static native void spiInitAuto(int var0, int var1);

    public static native void spiFreeAuto(int var0);

    public static native void spiStartAutoRate(int var0, double var1);

    public static native void spiStartAutoTrigger(int var0, int var1, int var2, boolean var3, boolean var4);

    public static native void spiStopAuto(int var0);

    public static native void spiSetAutoTransmitData(int var0, byte[] var1, int var2);

    public static native void spiForceAutoRead(int var0);

    public static native int spiReadAutoReceivedData(int var0, ByteBuffer var1, int var2, double var3);

    public static native int spiReadAutoReceivedData(int var0, int[] var1, int var2, double var3);

    public static native int spiGetAutoDroppedCount(int var0);

    public static native void spiConfigureAutoStall(int var0, int var1, int var2, int var3);
}

