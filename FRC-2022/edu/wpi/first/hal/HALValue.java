/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

public final class HALValue {
    public static final int kUnassigned = 0;
    public static final int kBoolean = 1;
    public static final int kDouble = 2;
    public static final int kEnum = 4;
    public static final int kInt = 8;
    public static final int kLong = 16;
    private int m_type;
    private long m_long;
    private double m_double;

    private HALValue(double value, int type) {
        this.m_type = type;
        this.m_double = value;
    }

    private HALValue(long value, int type) {
        this.m_type = type;
        this.m_long = value;
    }

    private HALValue() {
    }

    public int getType() {
        return this.m_type;
    }

    public boolean getBoolean() {
        return this.m_long != 0L;
    }

    public long getLong() {
        return this.m_long;
    }

    public double getDouble() {
        return this.m_double;
    }

    public long getNativeLong() {
        return this.m_long;
    }

    public double getNativeDouble() {
        return this.m_double;
    }

    public static HALValue makeBoolean(boolean value) {
        return new HALValue(value ? 1L : 0L, 1);
    }

    public static HALValue makeEnum(int value) {
        return new HALValue(value, 4);
    }

    public static HALValue makeInt(int value) {
        return new HALValue(value, 8);
    }

    public static HALValue makeLong(long value) {
        return new HALValue(value, 16);
    }

    public static HALValue makeDouble(double value) {
        return new HALValue(value, 2);
    }

    public static HALValue makeUnassigned() {
        return new HALValue();
    }

    public static HALValue fromNative(int type, long value1, double value2) {
        switch (type) {
            case 1: {
                return HALValue.makeBoolean(value1 != 0L);
            }
            case 2: {
                return HALValue.makeDouble(value2);
            }
            case 4: {
                return HALValue.makeEnum((int)value1);
            }
            case 8: {
                return HALValue.makeInt((int)value1);
            }
            case 16: {
                return HALValue.makeLong(value1);
            }
        }
        return HALValue.makeUnassigned();
    }
}

