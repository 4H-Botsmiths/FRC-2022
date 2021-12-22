/*
 * Decompiled with CFR 0.150.
 */
package com.revrobotics;

public enum ControlType {
    kDutyCycle(0),
    kVelocity(1),
    kVoltage(2),
    kPosition(3),
    kSmartMotion(4),
    kCurrent(5),
    kSmartVelocity(6);

    public final int value;

    private ControlType(int value) {
        this.value = value;
    }
}

