/*
 * Decompiled with CFR 0.150.
 */
package com.revrobotics;

public enum AlternateEncoderType {
    kQuadrature(0);

    public final int value;

    private AlternateEncoderType(int value) {
        this.value = value;
    }

    public static AlternateEncoderType fromId(int id) {
        return kQuadrature;
    }
}

