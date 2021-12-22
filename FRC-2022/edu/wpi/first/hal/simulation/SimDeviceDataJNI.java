/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.HALValue;
import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.SimDeviceCallback;
import edu.wpi.first.hal.simulation.SimValueCallback;

public class SimDeviceDataJNI
extends JNIWrapper {
    public static native void setSimDeviceEnabled(String var0, boolean var1);

    public static native boolean isSimDeviceEnabled(String var0);

    public static native int registerSimDeviceCreatedCallback(String var0, SimDeviceCallback var1, boolean var2);

    public static native void cancelSimDeviceCreatedCallback(int var0);

    public static native int registerSimDeviceFreedCallback(String var0, SimDeviceCallback var1, boolean var2);

    public static native void cancelSimDeviceFreedCallback(int var0);

    public static native int getSimDeviceHandle(String var0);

    public static native String getSimDeviceName(int var0);

    public static native int getSimValueDeviceHandle(int var0);

    public static native SimDeviceInfo[] enumerateSimDevices(String var0);

    public static native int registerSimValueCreatedCallback(int var0, SimValueCallback var1, boolean var2);

    public static native void cancelSimValueCreatedCallback(int var0);

    public static native int registerSimValueChangedCallback(int var0, SimValueCallback var1, boolean var2);

    public static native void cancelSimValueChangedCallback(int var0);

    public static native int registerSimValueResetCallback(int var0, SimValueCallback var1, boolean var2);

    public static native void cancelSimValueResetCallback(int var0);

    public static native int getSimValueHandle(int var0, String var1);

    public static native SimValueInfo[] enumerateSimValues(int var0);

    public static native String[] getSimValueEnumOptions(int var0);

    public static native double[] getSimValueEnumDoubleValues(int var0);

    public static native void resetSimDeviceData();

    public static class SimValueInfo {
        public String name;
        public int handle;
        @Deprecated
        public boolean readonly;
        public int direction;
        public HALValue value;

        public SimValueInfo(String name, int handle, int direction, int type, long value1, double value2) {
            this.name = name;
            this.handle = handle;
            this.readonly = direction == 1;
            this.direction = direction;
            this.value = HALValue.fromNative(type, value1, value2);
        }
    }

    public static class SimDeviceInfo {
        public String name;
        public int handle;

        public SimDeviceInfo(String name, int handle) {
            this.name = name;
            this.handle = handle;
        }
    }
}

