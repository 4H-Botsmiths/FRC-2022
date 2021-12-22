/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.EntryInfo;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.networktables.RpcAnswer;
import edu.wpi.first.networktables.RpcCall;
import java.nio.ByteBuffer;
import java.util.function.Consumer;

public final class NetworkTableEntry {
    public static final int kPersistent = 1;
    private final NetworkTableInstance m_inst;
    private final int m_handle;

    public NetworkTableEntry(NetworkTableInstance inst, int handle) {
        this.m_inst = inst;
        this.m_handle = handle;
    }

    public boolean isValid() {
        return this.m_handle != 0;
    }

    public int getHandle() {
        return this.m_handle;
    }

    public NetworkTableInstance getInstance() {
        return this.m_inst;
    }

    public boolean exists() {
        return NetworkTablesJNI.getType(this.m_handle) != 0;
    }

    public String getName() {
        return NetworkTablesJNI.getEntryName(this.m_handle);
    }

    public NetworkTableType getType() {
        return NetworkTableType.getFromInt(NetworkTablesJNI.getType(this.m_handle));
    }

    public int getFlags() {
        return NetworkTablesJNI.getEntryFlags(this.m_handle);
    }

    public long getLastChange() {
        return NetworkTablesJNI.getEntryLastChange(this.m_handle);
    }

    public EntryInfo getInfo() {
        return NetworkTablesJNI.getEntryInfoHandle(this.m_inst, this.m_handle);
    }

    public NetworkTableValue getValue() {
        return NetworkTablesJNI.getValue(this.m_handle);
    }

    public boolean getBoolean(boolean defaultValue) {
        return NetworkTablesJNI.getBoolean(this.m_handle, defaultValue);
    }

    public double getDouble(double defaultValue) {
        return NetworkTablesJNI.getDouble(this.m_handle, defaultValue);
    }

    public Number getNumber(Number defaultValue) {
        return NetworkTablesJNI.getDouble(this.m_handle, defaultValue.doubleValue());
    }

    public String getString(String defaultValue) {
        return NetworkTablesJNI.getString(this.m_handle, defaultValue);
    }

    public byte[] getRaw(byte[] defaultValue) {
        return NetworkTablesJNI.getRaw(this.m_handle, defaultValue);
    }

    public boolean[] getBooleanArray(boolean[] defaultValue) {
        return NetworkTablesJNI.getBooleanArray(this.m_handle, defaultValue);
    }

    public Boolean[] getBooleanArray(Boolean[] defaultValue) {
        return NetworkTableValue.fromNative(NetworkTablesJNI.getBooleanArray(this.m_handle, NetworkTableValue.toNative(defaultValue)));
    }

    public double[] getDoubleArray(double[] defaultValue) {
        return NetworkTablesJNI.getDoubleArray(this.m_handle, defaultValue);
    }

    public Double[] getDoubleArray(Double[] defaultValue) {
        return NetworkTableValue.fromNative(NetworkTablesJNI.getDoubleArray(this.m_handle, NetworkTableValue.toNative(defaultValue)));
    }

    public Number[] getNumberArray(Number[] defaultValue) {
        return NetworkTableValue.fromNative(NetworkTablesJNI.getDoubleArray(this.m_handle, NetworkTableValue.toNative(defaultValue)));
    }

    public String[] getStringArray(String[] defaultValue) {
        return NetworkTablesJNI.getStringArray(this.m_handle, defaultValue);
    }

    public static boolean isValidDataType(Object data) {
        return data instanceof Number || data instanceof Boolean || data instanceof String || data instanceof double[] || data instanceof Double[] || data instanceof Number[] || data instanceof boolean[] || data instanceof Boolean[] || data instanceof String[] || data instanceof byte[] || data instanceof Byte[];
    }

    public boolean setDefaultValue(Object defaultValue) {
        if (defaultValue instanceof NetworkTableValue) {
            long time = ((NetworkTableValue)defaultValue).getTime();
            Object otherValue = ((NetworkTableValue)defaultValue).getValue();
            switch (((NetworkTableValue)defaultValue).getType()) {
                case kBoolean: {
                    return NetworkTablesJNI.setDefaultBoolean(this.m_handle, time, (Boolean)otherValue);
                }
                case kDouble: {
                    return NetworkTablesJNI.setDefaultDouble(this.m_handle, time, ((Number)otherValue).doubleValue());
                }
                case kString: {
                    return NetworkTablesJNI.setDefaultString(this.m_handle, time, (String)otherValue);
                }
                case kRaw: {
                    return NetworkTablesJNI.setDefaultRaw(this.m_handle, time, (byte[])otherValue);
                }
                case kBooleanArray: {
                    return NetworkTablesJNI.setDefaultBooleanArray(this.m_handle, time, (boolean[])otherValue);
                }
                case kDoubleArray: {
                    return NetworkTablesJNI.setDefaultDoubleArray(this.m_handle, time, (double[])otherValue);
                }
                case kStringArray: {
                    return NetworkTablesJNI.setDefaultStringArray(this.m_handle, time, (String[])otherValue);
                }
            }
            return true;
        }
        if (defaultValue instanceof Boolean) {
            return this.setDefaultBoolean((Boolean)defaultValue);
        }
        if (defaultValue instanceof Number) {
            return this.setDefaultNumber((Number)defaultValue);
        }
        if (defaultValue instanceof String) {
            return this.setDefaultString((String)defaultValue);
        }
        if (defaultValue instanceof byte[]) {
            return this.setDefaultRaw((byte[])defaultValue);
        }
        if (defaultValue instanceof boolean[]) {
            return this.setDefaultBooleanArray((boolean[])defaultValue);
        }
        if (defaultValue instanceof double[]) {
            return this.setDefaultDoubleArray((double[])defaultValue);
        }
        if (defaultValue instanceof Boolean[]) {
            return this.setDefaultBooleanArray((Boolean[])defaultValue);
        }
        if (defaultValue instanceof Number[]) {
            return this.setDefaultNumberArray((Number[])defaultValue);
        }
        if (defaultValue instanceof String[]) {
            return this.setDefaultStringArray((String[])defaultValue);
        }
        throw new IllegalArgumentException("Value of type " + defaultValue.getClass().getName() + " cannot be put into a table");
    }

    public boolean setDefaultBoolean(boolean defaultValue) {
        return NetworkTablesJNI.setDefaultBoolean(this.m_handle, 0L, defaultValue);
    }

    public boolean setDefaultDouble(double defaultValue) {
        return NetworkTablesJNI.setDefaultDouble(this.m_handle, 0L, defaultValue);
    }

    public boolean setDefaultNumber(Number defaultValue) {
        return NetworkTablesJNI.setDefaultDouble(this.m_handle, 0L, defaultValue.doubleValue());
    }

    public boolean setDefaultString(String defaultValue) {
        return NetworkTablesJNI.setDefaultString(this.m_handle, 0L, defaultValue);
    }

    public boolean setDefaultRaw(byte[] defaultValue) {
        return NetworkTablesJNI.setDefaultRaw(this.m_handle, 0L, defaultValue);
    }

    public boolean setDefaultBooleanArray(boolean[] defaultValue) {
        return NetworkTablesJNI.setDefaultBooleanArray(this.m_handle, 0L, defaultValue);
    }

    public boolean setDefaultBooleanArray(Boolean[] defaultValue) {
        return NetworkTablesJNI.setDefaultBooleanArray(this.m_handle, 0L, NetworkTableValue.toNative(defaultValue));
    }

    public boolean setDefaultDoubleArray(double[] defaultValue) {
        return NetworkTablesJNI.setDefaultDoubleArray(this.m_handle, 0L, defaultValue);
    }

    public boolean setDefaultNumberArray(Number[] defaultValue) {
        return NetworkTablesJNI.setDefaultDoubleArray(this.m_handle, 0L, NetworkTableValue.toNative(defaultValue));
    }

    public boolean setDefaultStringArray(String[] defaultValue) {
        return NetworkTablesJNI.setDefaultStringArray(this.m_handle, 0L, defaultValue);
    }

    public boolean setValue(Object value) {
        if (value instanceof NetworkTableValue) {
            long time = ((NetworkTableValue)value).getTime();
            Object otherValue = ((NetworkTableValue)value).getValue();
            switch (((NetworkTableValue)value).getType()) {
                case kBoolean: {
                    return NetworkTablesJNI.setBoolean(this.m_handle, time, (Boolean)otherValue, false);
                }
                case kDouble: {
                    return NetworkTablesJNI.setDouble(this.m_handle, time, ((Number)otherValue).doubleValue(), false);
                }
                case kString: {
                    return NetworkTablesJNI.setString(this.m_handle, time, (String)otherValue, false);
                }
                case kRaw: {
                    return NetworkTablesJNI.setRaw(this.m_handle, time, (byte[])otherValue, false);
                }
                case kBooleanArray: {
                    return NetworkTablesJNI.setBooleanArray(this.m_handle, time, (boolean[])otherValue, false);
                }
                case kDoubleArray: {
                    return NetworkTablesJNI.setDoubleArray(this.m_handle, time, (double[])otherValue, false);
                }
                case kStringArray: {
                    return NetworkTablesJNI.setStringArray(this.m_handle, time, (String[])otherValue, false);
                }
            }
            return true;
        }
        if (value instanceof Boolean) {
            return this.setBoolean((Boolean)value);
        }
        if (value instanceof Number) {
            return this.setNumber((Number)value);
        }
        if (value instanceof String) {
            return this.setString((String)value);
        }
        if (value instanceof byte[]) {
            return this.setRaw((byte[])value);
        }
        if (value instanceof boolean[]) {
            return this.setBooleanArray((boolean[])value);
        }
        if (value instanceof double[]) {
            return this.setDoubleArray((double[])value);
        }
        if (value instanceof Boolean[]) {
            return this.setBooleanArray((Boolean[])value);
        }
        if (value instanceof Number[]) {
            return this.setNumberArray((Number[])value);
        }
        if (value instanceof String[]) {
            return this.setStringArray((String[])value);
        }
        throw new IllegalArgumentException("Value of type " + value.getClass().getName() + " cannot be put into a table");
    }

    public boolean setBoolean(boolean value) {
        return NetworkTablesJNI.setBoolean(this.m_handle, 0L, value, false);
    }

    public boolean setDouble(double value) {
        return NetworkTablesJNI.setDouble(this.m_handle, 0L, value, false);
    }

    public boolean setNumber(Number value) {
        return NetworkTablesJNI.setDouble(this.m_handle, 0L, value.doubleValue(), false);
    }

    public boolean setString(String value) {
        return NetworkTablesJNI.setString(this.m_handle, 0L, value, false);
    }

    public boolean setRaw(byte[] value) {
        return NetworkTablesJNI.setRaw(this.m_handle, 0L, value, false);
    }

    public boolean setRaw(ByteBuffer value, int len) {
        if (!value.isDirect()) {
            throw new IllegalArgumentException("must be a direct buffer");
        }
        if (value.capacity() < len) {
            throw new IllegalArgumentException("buffer is too small, must be at least " + len);
        }
        return NetworkTablesJNI.setRaw(this.m_handle, 0L, value, len, false);
    }

    public boolean setBooleanArray(boolean[] value) {
        return NetworkTablesJNI.setBooleanArray(this.m_handle, 0L, value, false);
    }

    public boolean setBooleanArray(Boolean[] value) {
        return NetworkTablesJNI.setBooleanArray(this.m_handle, 0L, NetworkTableValue.toNative(value), false);
    }

    public boolean setDoubleArray(double[] value) {
        return NetworkTablesJNI.setDoubleArray(this.m_handle, 0L, value, false);
    }

    public boolean setNumberArray(Number[] value) {
        return NetworkTablesJNI.setDoubleArray(this.m_handle, 0L, NetworkTableValue.toNative(value), false);
    }

    public boolean setStringArray(String[] value) {
        return NetworkTablesJNI.setStringArray(this.m_handle, 0L, value, false);
    }

    public void forceSetValue(Object value) {
        if (value instanceof NetworkTableValue) {
            long time = ((NetworkTableValue)value).getTime();
            Object otherValue = ((NetworkTableValue)value).getValue();
            switch (((NetworkTableValue)value).getType()) {
                case kBoolean: {
                    NetworkTablesJNI.setBoolean(this.m_handle, time, (Boolean)otherValue, true);
                    return;
                }
                case kDouble: {
                    NetworkTablesJNI.setDouble(this.m_handle, time, ((Number)otherValue).doubleValue(), true);
                    return;
                }
                case kString: {
                    NetworkTablesJNI.setString(this.m_handle, time, (String)otherValue, true);
                    return;
                }
                case kRaw: {
                    NetworkTablesJNI.setRaw(this.m_handle, time, (byte[])otherValue, true);
                    return;
                }
                case kBooleanArray: {
                    NetworkTablesJNI.setBooleanArray(this.m_handle, time, (boolean[])otherValue, true);
                    return;
                }
                case kDoubleArray: {
                    NetworkTablesJNI.setDoubleArray(this.m_handle, time, (double[])otherValue, true);
                    return;
                }
                case kStringArray: {
                    NetworkTablesJNI.setStringArray(this.m_handle, time, (String[])otherValue, true);
                    return;
                }
            }
            return;
        }
        if (value instanceof Boolean) {
            this.forceSetBoolean((Boolean)value);
        } else if (value instanceof Number) {
            this.forceSetNumber((Number)value);
        } else if (value instanceof String) {
            this.forceSetString((String)value);
        } else if (value instanceof byte[]) {
            this.forceSetRaw((byte[])value);
        } else if (value instanceof boolean[]) {
            this.forceSetBooleanArray((boolean[])value);
        } else if (value instanceof double[]) {
            this.forceSetDoubleArray((double[])value);
        } else if (value instanceof Boolean[]) {
            this.forceSetBooleanArray((Boolean[])value);
        } else if (value instanceof Number[]) {
            this.forceSetNumberArray((Number[])value);
        } else if (value instanceof String[]) {
            this.forceSetStringArray((String[])value);
        } else {
            throw new IllegalArgumentException("Value of type " + value.getClass().getName() + " cannot be put into a table");
        }
    }

    public void forceSetBoolean(boolean value) {
        NetworkTablesJNI.setBoolean(this.m_handle, 0L, value, true);
    }

    public void forceSetDouble(double value) {
        NetworkTablesJNI.setDouble(this.m_handle, 0L, value, true);
    }

    public void forceSetNumber(Number value) {
        NetworkTablesJNI.setDouble(this.m_handle, 0L, value.doubleValue(), true);
    }

    public void forceSetString(String value) {
        NetworkTablesJNI.setString(this.m_handle, 0L, value, true);
    }

    public void forceSetRaw(byte[] value) {
        NetworkTablesJNI.setRaw(this.m_handle, 0L, value, true);
    }

    public void forceSetBooleanArray(boolean[] value) {
        NetworkTablesJNI.setBooleanArray(this.m_handle, 0L, value, true);
    }

    public void forceSetBooleanArray(Boolean[] value) {
        NetworkTablesJNI.setBooleanArray(this.m_handle, 0L, NetworkTableValue.toNative(value), true);
    }

    public void forceSetDoubleArray(double[] value) {
        NetworkTablesJNI.setDoubleArray(this.m_handle, 0L, value, true);
    }

    public void forceSetNumberArray(Number[] value) {
        NetworkTablesJNI.setDoubleArray(this.m_handle, 0L, NetworkTableValue.toNative(value), true);
    }

    public void forceSetStringArray(String[] value) {
        NetworkTablesJNI.setStringArray(this.m_handle, 0L, value, true);
    }

    public void setFlags(int flags) {
        NetworkTablesJNI.setEntryFlags(this.m_handle, this.getFlags() | flags);
    }

    public void clearFlags(int flags) {
        NetworkTablesJNI.setEntryFlags(this.m_handle, this.getFlags() & ~flags);
    }

    public void setPersistent() {
        this.setFlags(1);
    }

    public void clearPersistent() {
        this.clearFlags(1);
    }

    public boolean isPersistent() {
        return (this.getFlags() & 1) != 0;
    }

    public void delete() {
        NetworkTablesJNI.deleteEntry(this.m_handle);
    }

    public void createRpc(Consumer<RpcAnswer> callback) {
        this.m_inst.createRpc(this, callback);
    }

    public RpcCall callRpc(byte[] params) {
        return new RpcCall(this, NetworkTablesJNI.callRpc(this.m_handle, params));
    }

    public int addListener(Consumer<EntryNotification> listener, int flags) {
        return this.m_inst.addEntryListener(this, listener, flags);
    }

    public void removeListener(int listener) {
        this.m_inst.removeEntryListener(listener);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof NetworkTableEntry)) {
            return false;
        }
        return this.m_handle == ((NetworkTableEntry)other).m_handle;
    }

    public int hashCode() {
        return this.m_handle;
    }
}

