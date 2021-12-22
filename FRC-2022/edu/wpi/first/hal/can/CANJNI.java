/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.can;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.can.CANStatus;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class CANJNI
extends JNIWrapper {
    public static final int CAN_SEND_PERIOD_NO_REPEAT = 0;
    public static final int CAN_SEND_PERIOD_STOP_REPEATING = -1;
    public static final int CAN_IS_FRAME_REMOTE = Integer.MIN_VALUE;
    public static final int CAN_IS_FRAME_11BIT = 0x40000000;

    public static native void FRCNetCommCANSessionMuxSendMessage(int var0, byte[] var1, int var2);

    public static native byte[] FRCNetCommCANSessionMuxReceiveMessage(IntBuffer var0, int var1, ByteBuffer var2);

    public static native void getCANStatus(CANStatus var0);
}

