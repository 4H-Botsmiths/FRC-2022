/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.can;

public class CANMessageNotAllowedException
extends RuntimeException {
    private static final long serialVersionUID = -638450112427013494L;

    public CANMessageNotAllowedException(String msg) {
        super(msg);
    }
}

