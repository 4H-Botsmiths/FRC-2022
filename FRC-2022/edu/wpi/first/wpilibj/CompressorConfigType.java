/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

public enum CompressorConfigType {
    Disabled(0),
    Digital(1),
    Analog(2),
    Hybrid(3);

    public final int value;

    private CompressorConfigType(int value) {
        this.value = value;
    }

    public static CompressorConfigType fromValue(int value) {
        switch (value) {
            case 3: {
                return Hybrid;
            }
            case 2: {
                return Analog;
            }
            case 1: {
                return Digital;
            }
        }
        return Disabled;
    }

    public int getValue() {
        return this.value;
    }
}

