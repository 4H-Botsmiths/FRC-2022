/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;

public class CTREPCMJNI
extends JNIWrapper {
    public static native int initialize(int var0);

    public static native void free(int var0);

    public static native boolean checkSolenoidChannel(int var0);

    public static native boolean getCompressor(int var0);

    public static native void setClosedLoopControl(int var0, boolean var1);

    public static native boolean getClosedLoopControl(int var0);

    public static native boolean getPressureSwitch(int var0);

    public static native double getCompressorCurrent(int var0);

    public static native boolean getCompressorCurrentTooHighFault(int var0);

    public static native boolean getCompressorCurrentTooHighStickyFault(int var0);

    public static native boolean getCompressorShortedFault(int var0);

    public static native boolean getCompressorShortedStickyFault(int var0);

    public static native boolean getCompressorNotConnectedFault(int var0);

    public static native boolean getCompressorNotConnectedStickyFault(int var0);

    public static native int getSolenoids(int var0);

    public static native void setSolenoids(int var0, int var1, int var2);

    public static native int getSolenoidDisabledList(int var0);

    public static native boolean getSolenoidVoltageFault(int var0);

    public static native boolean getSolenoidVoltageStickyFault(int var0);

    public static native void clearAllStickyFaults(int var0);

    public static native void fireOneShot(int var0, int var1);

    public static native void setOneShotDuration(int var0, int var1, int var2);
}

