/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.SimEnum;
import edu.wpi.first.hal.SimInt;
import edu.wpi.first.hal.SimLong;
import edu.wpi.first.hal.SimValue;
import edu.wpi.first.hal.simulation.SimDeviceCallback;
import edu.wpi.first.hal.simulation.SimDeviceDataJNI;
import edu.wpi.first.hal.simulation.SimValueCallback;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public class SimDeviceSim {
    private final int m_handle;

    public SimDeviceSim(String name) {
        this.m_handle = SimDeviceDataJNI.getSimDeviceHandle(name);
    }

    public SimDeviceSim(String name, int index) {
        this(name + "[" + index + "]");
    }

    public SimDeviceSim(String name, int index, int channel) {
        this(name + "[" + index + "," + channel + "]");
    }

    public SimValue getValue(String name) {
        int handle = SimDeviceDataJNI.getSimValueHandle(this.m_handle, name);
        if (handle <= 0) {
            return null;
        }
        return new SimValue(handle);
    }

    public SimInt getInt(String name) {
        int handle = SimDeviceDataJNI.getSimValueHandle(this.m_handle, name);
        if (handle <= 0) {
            return null;
        }
        return new SimInt(handle);
    }

    public SimLong getLong(String name) {
        int handle = SimDeviceDataJNI.getSimValueHandle(this.m_handle, name);
        if (handle <= 0) {
            return null;
        }
        return new SimLong(handle);
    }

    public SimDouble getDouble(String name) {
        int handle = SimDeviceDataJNI.getSimValueHandle(this.m_handle, name);
        if (handle <= 0) {
            return null;
        }
        return new SimDouble(handle);
    }

    public SimEnum getEnum(String name) {
        int handle = SimDeviceDataJNI.getSimValueHandle(this.m_handle, name);
        if (handle <= 0) {
            return null;
        }
        return new SimEnum(handle);
    }

    public SimBoolean getBoolean(String name) {
        int handle = SimDeviceDataJNI.getSimValueHandle(this.m_handle, name);
        if (handle <= 0) {
            return null;
        }
        return new SimBoolean(handle);
    }

    public static String[] getEnumOptions(SimEnum val) {
        return SimDeviceDataJNI.getSimValueEnumOptions(val.getNativeHandle());
    }

    public SimDeviceDataJNI.SimValueInfo[] enumerateValues() {
        return SimDeviceDataJNI.enumerateSimValues(this.m_handle);
    }

    public int getNativeHandle() {
        return this.m_handle;
    }

    public CallbackStore registerValueCreatedCallback(SimValueCallback callback, boolean initialNotify) {
        int uid = SimDeviceDataJNI.registerSimValueCreatedCallback(this.m_handle, callback, initialNotify);
        return new CallbackStore(uid, SimDeviceDataJNI::cancelSimValueCreatedCallback);
    }

    public CallbackStore registerValueChangedCallback(SimValue value, SimValueCallback callback, boolean initialNotify) {
        int uid = SimDeviceDataJNI.registerSimValueChangedCallback(value.getNativeHandle(), callback, initialNotify);
        return new CallbackStore(uid, SimDeviceDataJNI::cancelSimValueChangedCallback);
    }

    public CallbackStore registerValueResetCallback(SimValue value, SimValueCallback callback, boolean initialNotify) {
        int uid = SimDeviceDataJNI.registerSimValueResetCallback(value.getNativeHandle(), callback, initialNotify);
        return new CallbackStore(uid, SimDeviceDataJNI::cancelSimValueResetCallback);
    }

    public static SimDeviceDataJNI.SimDeviceInfo[] enumerateDevices(String prefix) {
        return SimDeviceDataJNI.enumerateSimDevices(prefix);
    }

    public static CallbackStore registerDeviceCreatedCallback(String prefix, SimDeviceCallback callback, boolean initialNotify) {
        int uid = SimDeviceDataJNI.registerSimDeviceCreatedCallback(prefix, callback, initialNotify);
        return new CallbackStore(uid, SimDeviceDataJNI::cancelSimDeviceCreatedCallback);
    }

    public static CallbackStore registerDeviceFreedCallback(String prefix, SimDeviceCallback callback, boolean initialNotify) {
        int uid = SimDeviceDataJNI.registerSimDeviceFreedCallback(prefix, callback, initialNotify);
        return new CallbackStore(uid, SimDeviceDataJNI::cancelSimDeviceFreedCallback);
    }

    public static void resetData() {
        SimDeviceDataJNI.resetSimDeviceData();
    }
}

