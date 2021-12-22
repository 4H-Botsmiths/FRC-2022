/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.HALValue;
import edu.wpi.first.hal.JNIWrapper;

public class SimDeviceJNI
extends JNIWrapper {
    public static final int kInput = 0;
    public static final int kOutput = 1;
    public static final int kBidir = 2;

    public static native int createSimDevice(String var0);

    public static native void freeSimDevice(int var0);

    private static native int createSimValueNative(int var0, String var1, int var2, int var3, long var4, double var6);

    @Deprecated
    public static int createSimValue(int device, String name, boolean readonly, HALValue initialValue) {
        return SimDeviceJNI.createSimValueNative(device, name, readonly ? 1 : 0, initialValue.getType(), initialValue.getNativeLong(), initialValue.getNativeDouble());
    }

    public static int createSimValue(int device, String name, int direction, HALValue initialValue) {
        return SimDeviceJNI.createSimValueNative(device, name, direction, initialValue.getType(), initialValue.getNativeLong(), initialValue.getNativeDouble());
    }

    public static int createSimValueInt(int device, String name, int direction, int initialValue) {
        return SimDeviceJNI.createSimValueNative(device, name, direction, 8, initialValue, 0.0);
    }

    public static int createSimValueLong(int device, String name, int direction, long initialValue) {
        return SimDeviceJNI.createSimValueNative(device, name, direction, 16, initialValue, 0.0);
    }

    @Deprecated
    public static int createSimValueDouble(int device, String name, boolean readonly, double initialValue) {
        return SimDeviceJNI.createSimValueNative(device, name, readonly ? 1 : 0, 2, 0L, initialValue);
    }

    public static int createSimValueDouble(int device, String name, int direction, double initialValue) {
        return SimDeviceJNI.createSimValueNative(device, name, direction, 2, 0L, initialValue);
    }

    @Deprecated
    public static int createSimValueEnum(int device, String name, boolean readonly, String[] options, int initialValue) {
        return SimDeviceJNI.createSimValueEnum(device, name, readonly ? 1 : 0, options, initialValue);
    }

    public static native int createSimValueEnum(int var0, String var1, int var2, String[] var3, int var4);

    public static native int createSimValueEnumDouble(int var0, String var1, int var2, String[] var3, double[] var4, int var5);

    @Deprecated
    public static int createSimValueBoolean(int device, String name, boolean readonly, boolean initialValue) {
        return SimDeviceJNI.createSimValueNative(device, name, readonly ? 1 : 0, 1, initialValue ? 1L : 0L, 0.0);
    }

    public static int createSimValueBoolean(int device, String name, int direction, boolean initialValue) {
        return SimDeviceJNI.createSimValueNative(device, name, direction, 1, initialValue ? 1L : 0L, 0.0);
    }

    public static native HALValue getSimValue(int var0);

    public static native int getSimValueInt(int var0);

    public static native long getSimValueLong(int var0);

    public static native double getSimValueDouble(int var0);

    public static native int getSimValueEnum(int var0);

    public static native boolean getSimValueBoolean(int var0);

    private static native void setSimValueNative(int var0, int var1, long var2, double var4);

    public static void setSimValue(int handle, HALValue value) {
        SimDeviceJNI.setSimValueNative(handle, value.getType(), value.getNativeLong(), value.getNativeDouble());
    }

    public static void setSimValueInt(int handle, int value) {
        SimDeviceJNI.setSimValueNative(handle, 8, value, 0.0);
    }

    public static void setSimValueLong(int handle, long value) {
        SimDeviceJNI.setSimValueNative(handle, 16, value, 0.0);
    }

    public static void setSimValueDouble(int handle, double value) {
        SimDeviceJNI.setSimValueNative(handle, 2, 0L, value);
    }

    public static void setSimValueEnum(int handle, int value) {
        SimDeviceJNI.setSimValueNative(handle, 4, value, 0.0);
    }

    public static void setSimValueBoolean(int handle, boolean value) {
        SimDeviceJNI.setSimValueNative(handle, 1, value ? 1L : 0L, 0.0);
    }

    public static native void resetSimValue(int var0);
}

