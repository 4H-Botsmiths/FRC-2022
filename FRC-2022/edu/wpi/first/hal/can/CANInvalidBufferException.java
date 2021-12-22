/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.can;

public class CANInvalidBufferException
extends RuntimeException {
    private static final long serialVersionUID = -7993785672956997939L;

    public CANInvalidBufferException() {
    }

    public CANInvalidBufferException(String msg) {
        super(msg);
    }
}

