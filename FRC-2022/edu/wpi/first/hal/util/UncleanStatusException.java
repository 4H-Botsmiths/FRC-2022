/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.util;

public final class UncleanStatusException
extends IllegalStateException {
    private final int m_statusCode;

    public UncleanStatusException(int status, String message) {
        super(message);
        this.m_statusCode = status;
    }

    public UncleanStatusException(int status) {
        this(status, "Status code was non-zero");
    }

    public UncleanStatusException(String message) {
        this(-1, message);
    }

    public UncleanStatusException() {
        this(-1, "Status code was non-zero");
    }

    public int getStatus() {
        return this.m_statusCode;
    }
}

