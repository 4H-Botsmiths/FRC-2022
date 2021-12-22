/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;
import java.nio.ByteBuffer;

public class I2CJNI
extends JNIWrapper {
    public static native void i2CInitialize(int var0);

    public static native int i2CTransaction(int var0, byte var1, ByteBuffer var2, byte var3, ByteBuffer var4, byte var5);

    public static native int i2CTransactionB(int var0, byte var1, byte[] var2, byte var3, byte[] var4, byte var5);

    public static native int i2CWrite(int var0, byte var1, ByteBuffer var2, byte var3);

    public static native int i2CWriteB(int var0, byte var1, byte[] var2, byte var3);

    public static native int i2CRead(int var0, byte var1, ByteBuffer var2, byte var3);

    public static native int i2CReadB(int var0, byte var1, byte[] var2, byte var3);

    public static native void i2CClose(int var0);
}

