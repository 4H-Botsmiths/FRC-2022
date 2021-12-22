/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

public enum RuntimeType {
    kRoboRIO(0),
    kRoboRIO2(1),
    kSimulation(2);

    public final int value;

    private RuntimeType(int value) {
        this.value = value;
    }

    public static RuntimeType getValue(int type) {
        if (type == 0) {
            return kRoboRIO;
        }
        if (type == 1) {
            return kRoboRIO2;
        }
        return kSimulation;
    }
}

