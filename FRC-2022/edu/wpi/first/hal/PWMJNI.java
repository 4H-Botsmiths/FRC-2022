/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.DIOJNI;
import edu.wpi.first.hal.PWMConfigDataResult;

public class PWMJNI
extends DIOJNI {
    public static native int initializePWMPort(int var0);

    public static native boolean checkPWMChannel(int var0);

    public static native void freePWMPort(int var0);

    public static native void setPWMConfigRaw(int var0, int var1, int var2, int var3, int var4, int var5);

    public static native void setPWMConfig(int var0, double var1, double var3, double var5, double var7, double var9);

    public static native PWMConfigDataResult getPWMConfigRaw(int var0);

    public static native void setPWMEliminateDeadband(int var0, boolean var1);

    public static native boolean getPWMEliminateDeadband(int var0);

    public static native void setPWMRaw(int var0, short var1);

    public static native void setPWMSpeed(int var0, double var1);

    public static native void setPWMPosition(int var0, double var1);

    public static native short getPWMRaw(int var0);

    public static native double getPWMSpeed(int var0);

    public static native double getPWMPosition(int var0);

    public static native void setPWMDisabled(int var0);

    public static native void latchPWMZero(int var0);

    public static native void setPWMPeriodScale(int var0, int var1);
}

