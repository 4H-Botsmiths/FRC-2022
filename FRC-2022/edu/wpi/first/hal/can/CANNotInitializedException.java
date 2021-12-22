/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.can;

public class CANNotInitializedException
extends RuntimeException {
    private static final long serialVersionUID = -5982895147092686594L;

    public CANNotInitializedException() {
    }

    public CANNotInitializedException(String msg) {
        super(msg);
    }
}

