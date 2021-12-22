/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.can;

public class CANMessageNotFoundException
extends RuntimeException {
    private static final long serialVersionUID = 8249780881928189975L;

    public CANMessageNotFoundException() {
    }

    public CANMessageNotFoundException(String msg) {
        super(msg);
    }
}

