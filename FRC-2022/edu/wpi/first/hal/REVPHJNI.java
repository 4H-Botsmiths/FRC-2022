/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.REVPHFaults;
import edu.wpi.first.hal.REVPHStickyFaults;
import edu.wpi.first.hal.REVPHVersion;

public class REVPHJNI
extends JNIWrapper {
    public static final int COMPRESSOR_CONFIG_TYPE_DISABLED = 0;
    public static final int COMPRESSOR_CONFIG_TYPE_DIGITAL = 1;
    public static final int COMPRESSOR_CONFIG_TYPE_ANALOG = 2;
    public static final int COMPRESSOR_CONFIG_TYPE_HYBRID = 3;

    public static native int initialize(int var0);

    public static native void free(int var0);

    public static native boolean checkSolenoidChannel(int var0);

    public static native boolean getCompressor(int var0);

    public static native void setCompressorConfig(int var0, double var1, double var3, boolean var5, boolean var6);

    public static native void setClosedLoopControlDisabled(int var0);

    public static native void setClosedLoopControlDigital(int var0);

    public static native void setClosedLoopControlAnalog(int var0, double var1, double var3);

    public static native void setClosedLoopControlHybrid(int var0, double var1, double var3);

    public static native int getCompressorConfig(int var0);

    public static native boolean getPressureSwitch(int var0);

    public static native double getAnalogVoltage(int var0, int var1);

    public static native double getCompressorCurrent(int var0);

    public static native int getSolenoids(int var0);

    public static native void setSolenoids(int var0, int var1, int var2);

    public static native void fireOneShot(int var0, int var1, int var2);

    public static native void clearStickyFaults(int var0);

    public static native double getInputVoltage(int var0);

    public static native double get5VVoltage(int var0);

    public static native double getSolenoidCurrent(int var0);

    public static native double getSolenoidVoltage(int var0);

    public static native int getStickyFaultsNative(int var0);

    public static REVPHStickyFaults getStickyFaults(int handle) {
        return new REVPHStickyFaults(REVPHJNI.getStickyFaultsNative(handle));
    }

    public static native int getFaultsNative(int var0);

    public static REVPHFaults getFaults(int handle) {
        return new REVPHFaults(REVPHJNI.getFaultsNative(handle));
    }

    public static native REVPHVersion getVersion(int var0);
}

