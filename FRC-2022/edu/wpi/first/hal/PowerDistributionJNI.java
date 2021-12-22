/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.PowerDistributionFaults;
import edu.wpi.first.hal.PowerDistributionStickyFaults;
import edu.wpi.first.hal.PowerDistributionVersion;

public class PowerDistributionJNI
extends JNIWrapper {
    public static final int AUTOMATIC_TYPE = 0;
    public static final int CTRE_TYPE = 1;
    public static final int REV_TYPE = 2;
    public static final int DEFAULT_MODULE = -1;

    public static native int initialize(int var0, int var1);

    public static native void free(int var0);

    public static native int getModuleNumber(int var0);

    public static native boolean checkModule(int var0, int var1);

    public static native boolean checkChannel(int var0, int var1);

    public static native int getType(int var0);

    public static native int getNumChannels(int var0);

    public static native double getTemperature(int var0);

    public static native double getVoltage(int var0);

    public static native double getChannelCurrent(int var0, int var1);

    public static native void getAllCurrents(int var0, double[] var1);

    public static native double getTotalCurrent(int var0);

    public static native double getTotalPower(int var0);

    public static native double getTotalEnergy(int var0);

    public static native void resetTotalEnergy(int var0);

    public static native void clearStickyFaults(int var0);

    public static native boolean getSwitchableChannel(int var0);

    public static native void setSwitchableChannel(int var0, boolean var1);

    public static native double getVoltageNoError(int var0);

    public static native double getChannelCurrentNoError(int var0, int var1);

    public static native double getTotalCurrentNoError(int var0);

    public static native boolean getSwitchableChannelNoError(int var0);

    public static native void setSwitchableChannelNoError(int var0, boolean var1);

    public static native int getFaultsNative(int var0);

    public static PowerDistributionFaults getFaults(int handle) {
        return new PowerDistributionFaults(PowerDistributionJNI.getFaultsNative(handle));
    }

    public static native int getStickyFaultsNative(int var0);

    public static PowerDistributionStickyFaults getStickyFaults(int handle) {
        return new PowerDistributionStickyFaults(PowerDistributionJNI.getStickyFaultsNative(handle));
    }

    public static native PowerDistributionVersion getVersion(int var0);
}

