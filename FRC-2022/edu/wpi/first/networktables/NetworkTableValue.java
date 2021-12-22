/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.networktables.NetworkTablesJNI;
import java.util.Objects;

public final class NetworkTableValue {
    private NetworkTableType m_type;
    private Object m_value;
    private long m_time;

    NetworkTableValue(NetworkTableType type, Object value, long time) {
        this.m_type = type;
        this.m_value = value;
        this.m_time = time;
    }

    NetworkTableValue(NetworkTableType type, Object value) {
        this(type, value, NetworkTablesJNI.now());
    }

    NetworkTableValue(int type, Object value, long time) {
        this(NetworkTableType.getFromInt(type), value, time);
    }

    public NetworkTableType getType() {
        return this.m_type;
    }

    public Object getValue() {
        return this.m_value;
    }

    public long getTime() {
        return this.m_time;
    }

    public boolean isValid() {
        return this.m_type != NetworkTableType.kUnassigned;
    }

    public boolean isBoolean() {
        return this.m_type == NetworkTableType.kBoolean;
    }

    public boolean isDouble() {
        return this.m_type == NetworkTableType.kDouble;
    }

    public boolean isString() {
        return this.m_type == NetworkTableType.kString;
    }

    public boolean isRaw() {
        return this.m_type == NetworkTableType.kRaw;
    }

    public boolean isRpc() {
        return this.m_type == NetworkTableType.kRpc;
    }

    public boolean isBooleanArray() {
        return this.m_type == NetworkTableType.kBooleanArray;
    }

    public boolean isDoubleArray() {
        return this.m_type == NetworkTableType.kDoubleArray;
    }

    public boolean isStringArray() {
        return this.m_type == NetworkTableType.kStringArray;
    }

    public boolean getBoolean() {
        if (this.m_type != NetworkTableType.kBoolean) {
            throw new ClassCastException("cannot convert " + this.m_type + " to boolean");
        }
        return (Boolean)this.m_value;
    }

    public double getDouble() {
        if (this.m_type != NetworkTableType.kDouble) {
            throw new ClassCastException("cannot convert " + this.m_type + " to double");
        }
        return ((Number)this.m_value).doubleValue();
    }

    public String getString() {
        if (this.m_type != NetworkTableType.kString) {
            throw new ClassCastException("cannot convert " + this.m_type + " to string");
        }
        return (String)this.m_value;
    }

    public byte[] getRaw() {
        if (this.m_type != NetworkTableType.kRaw) {
            throw new ClassCastException("cannot convert " + this.m_type + " to raw");
        }
        return (byte[])this.m_value;
    }

    public byte[] getRpc() {
        if (this.m_type != NetworkTableType.kRpc) {
            throw new ClassCastException("cannot convert " + this.m_type + " to rpc");
        }
        return (byte[])this.m_value;
    }

    public boolean[] getBooleanArray() {
        if (this.m_type != NetworkTableType.kBooleanArray) {
            throw new ClassCastException("cannot convert " + this.m_type + " to boolean array");
        }
        return (boolean[])this.m_value;
    }

    public double[] getDoubleArray() {
        if (this.m_type != NetworkTableType.kDoubleArray) {
            throw new ClassCastException("cannot convert " + this.m_type + " to double array");
        }
        return (double[])this.m_value;
    }

    public String[] getStringArray() {
        if (this.m_type != NetworkTableType.kStringArray) {
            throw new ClassCastException("cannot convert " + this.m_type + " to string array");
        }
        return (String[])this.m_value;
    }

    public static NetworkTableValue makeBoolean(boolean value) {
        return new NetworkTableValue(NetworkTableType.kBoolean, value);
    }

    public static NetworkTableValue makeBoolean(boolean value, long time) {
        return new NetworkTableValue(NetworkTableType.kBoolean, (Object)value, time);
    }

    public static NetworkTableValue makeDouble(double value) {
        return new NetworkTableValue(NetworkTableType.kDouble, value);
    }

    public static NetworkTableValue makeDouble(double value, long time) {
        return new NetworkTableValue(NetworkTableType.kDouble, (Object)value, time);
    }

    public static NetworkTableValue makeString(String value) {
        return new NetworkTableValue(NetworkTableType.kString, value);
    }

    public static NetworkTableValue makeString(String value, long time) {
        return new NetworkTableValue(NetworkTableType.kString, (Object)value, time);
    }

    public static NetworkTableValue makeRaw(byte[] value) {
        return new NetworkTableValue(NetworkTableType.kRaw, value);
    }

    public static NetworkTableValue makeRaw(byte[] value, long time) {
        return new NetworkTableValue(NetworkTableType.kRaw, (Object)value, time);
    }

    public static NetworkTableValue makeRpc(byte[] value) {
        return new NetworkTableValue(NetworkTableType.kRpc, value);
    }

    public static NetworkTableValue makeRpc(byte[] value, long time) {
        return new NetworkTableValue(NetworkTableType.kRpc, (Object)value, time);
    }

    public static NetworkTableValue makeBooleanArray(boolean[] value) {
        return new NetworkTableValue(NetworkTableType.kBooleanArray, value);
    }

    public static NetworkTableValue makeBooleanArray(boolean[] value, long time) {
        return new NetworkTableValue(NetworkTableType.kBooleanArray, (Object)value, time);
    }

    public static NetworkTableValue makeBooleanArray(Boolean[] value) {
        return new NetworkTableValue(NetworkTableType.kBooleanArray, NetworkTableValue.toNative(value));
    }

    public static NetworkTableValue makeBooleanArray(Boolean[] value, long time) {
        return new NetworkTableValue(NetworkTableType.kBooleanArray, (Object)NetworkTableValue.toNative(value), time);
    }

    public static NetworkTableValue makeDoubleArray(double[] value) {
        return new NetworkTableValue(NetworkTableType.kDoubleArray, value);
    }

    public static NetworkTableValue makeDoubleArray(double[] value, long time) {
        return new NetworkTableValue(NetworkTableType.kDoubleArray, (Object)value, time);
    }

    public static NetworkTableValue makeDoubleArray(Number[] value) {
        return new NetworkTableValue(NetworkTableType.kDoubleArray, NetworkTableValue.toNative(value));
    }

    public static NetworkTableValue makeDoubleArray(Number[] value, long time) {
        return new NetworkTableValue(NetworkTableType.kDoubleArray, (Object)NetworkTableValue.toNative(value), time);
    }

    public static NetworkTableValue makeStringArray(String[] value) {
        return new NetworkTableValue(NetworkTableType.kStringArray, value);
    }

    public static NetworkTableValue makeStringArray(String[] value, long time) {
        return new NetworkTableValue(NetworkTableType.kStringArray, (Object)value, time);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof NetworkTableValue)) {
            return false;
        }
        NetworkTableValue ntOther = (NetworkTableValue)other;
        return this.m_type == ntOther.m_type && this.m_value.equals(ntOther.m_value);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.m_type, this.m_value});
    }

    static boolean[] toNative(Boolean[] arr) {
        boolean[] out = new boolean[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            out[i] = arr[i];
        }
        return out;
    }

    static double[] toNative(Number[] arr) {
        double[] out = new double[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            out[i] = arr[i].doubleValue();
        }
        return out;
    }

    static Boolean[] fromNative(boolean[] arr) {
        Boolean[] out = new Boolean[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            out[i] = arr[i];
        }
        return out;
    }

    static Double[] fromNative(double[] arr) {
        Double[] out = new Double[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            out[i] = arr[i];
        }
        return out;
    }
}

