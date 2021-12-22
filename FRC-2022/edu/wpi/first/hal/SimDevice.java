/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.HALValue;
import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDeviceJNI;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.SimEnum;
import edu.wpi.first.hal.SimInt;
import edu.wpi.first.hal.SimLong;
import edu.wpi.first.hal.SimValue;

public class SimDevice
implements AutoCloseable {
    private final int m_handle;

    public static SimDevice create(String name) {
        int handle = SimDeviceJNI.createSimDevice(name);
        if (handle <= 0) {
            return null;
        }
        return new SimDevice(handle);
    }

    public static SimDevice create(String name, int index) {
        return SimDevice.create(name + "[" + index + "]");
    }

    public static SimDevice create(String name, int index, int channel) {
        return SimDevice.create(name + "[" + index + "," + channel + "]");
    }

    public SimDevice(int handle) {
        this.m_handle = handle;
    }

    @Override
    public void close() {
        SimDeviceJNI.freeSimDevice(this.m_handle);
    }

    public int getNativeHandle() {
        return this.m_handle;
    }

    @Deprecated
    public SimValue createValue(String name, boolean readonly, HALValue initialValue) {
        return this.createValue(name, readonly ? Direction.kOutput : Direction.kInput, initialValue);
    }

    public SimValue createValue(String name, Direction direction, HALValue initialValue) {
        int handle = SimDeviceJNI.createSimValue(this.m_handle, name, direction.m_value, initialValue);
        if (handle <= 0) {
            return null;
        }
        return new SimValue(handle);
    }

    public SimInt createInt(String name, Direction direction, int initialValue) {
        int handle = SimDeviceJNI.createSimValueInt(this.m_handle, name, direction.m_value, initialValue);
        if (handle <= 0) {
            return null;
        }
        return new SimInt(handle);
    }

    public SimLong createLong(String name, Direction direction, long initialValue) {
        int handle = SimDeviceJNI.createSimValueLong(this.m_handle, name, direction.m_value, initialValue);
        if (handle <= 0) {
            return null;
        }
        return new SimLong(handle);
    }

    @Deprecated
    public SimDouble createDouble(String name, boolean readonly, double initialValue) {
        return this.createDouble(name, readonly ? Direction.kOutput : Direction.kInput, initialValue);
    }

    public SimDouble createDouble(String name, Direction direction, double initialValue) {
        int handle = SimDeviceJNI.createSimValueDouble(this.m_handle, name, direction.m_value, initialValue);
        if (handle <= 0) {
            return null;
        }
        return new SimDouble(handle);
    }

    @Deprecated
    public SimEnum createEnum(String name, boolean readonly, String[] options, int initialValue) {
        return this.createEnum(name, readonly ? Direction.kOutput : Direction.kInput, options, initialValue);
    }

    public SimEnum createEnum(String name, Direction direction, String[] options, int initialValue) {
        int handle = SimDeviceJNI.createSimValueEnum(this.m_handle, name, direction.m_value, options, initialValue);
        if (handle <= 0) {
            return null;
        }
        return new SimEnum(handle);
    }

    public SimEnum createEnumDouble(String name, Direction direction, String[] options, double[] optionValues, int initialValue) {
        int handle = SimDeviceJNI.createSimValueEnumDouble(this.m_handle, name, direction.m_value, options, optionValues, initialValue);
        if (handle <= 0) {
            return null;
        }
        return new SimEnum(handle);
    }

    @Deprecated
    public SimBoolean createBoolean(String name, boolean readonly, boolean initialValue) {
        return this.createBoolean(name, readonly ? Direction.kOutput : Direction.kInput, initialValue);
    }

    public SimBoolean createBoolean(String name, Direction direction, boolean initialValue) {
        int handle = SimDeviceJNI.createSimValueBoolean(this.m_handle, name, direction.m_value, initialValue);
        if (handle <= 0) {
            return null;
        }
        return new SimBoolean(handle);
    }

    public static enum Direction {
        kInput(0),
        kOutput(1),
        kBidir(2);

        public final int m_value;

        private Direction(int value) {
            this.m_value = value;
        }
    }
}

