/*
 * Decompiled with CFR 0.150.
 */
package com.revrobotics;

public enum EncoderType {
    kNoSensor(0),
    kHallSensor(1),
    kQuadrature(2),
    kSensorless(3);

    public final int value;

    private EncoderType(int value) {
        this.value = value;
    }

    public static EncoderType fromId(int id) {
        switch (id) {
            case 1: {
                return kHallSensor;
            }
            case 2: {
                return kQuadrature;
            }
            case 3: {
                return kSensorless;
            }
        }
        return kNoSensor;
    }
}

