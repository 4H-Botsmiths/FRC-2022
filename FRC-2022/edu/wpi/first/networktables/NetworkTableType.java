/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

public enum NetworkTableType {
    kUnassigned(0),
    kBoolean(1),
    kDouble(2),
    kString(4),
    kRaw(8),
    kBooleanArray(16),
    kDoubleArray(32),
    kStringArray(64),
    kRpc(128);

    private final int value;

    private NetworkTableType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static NetworkTableType getFromInt(int value) {
        switch (value) {
            case 1: {
                return kBoolean;
            }
            case 2: {
                return kDouble;
            }
            case 4: {
                return kString;
            }
            case 8: {
                return kRaw;
            }
            case 16: {
                return kBooleanArray;
            }
            case 32: {
                return kDoubleArray;
            }
            case 64: {
                return kStringArray;
            }
            case 128: {
                return kRpc;
            }
        }
        return kUnassigned;
    }
}

