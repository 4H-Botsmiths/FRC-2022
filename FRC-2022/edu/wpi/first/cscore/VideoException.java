/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

public class VideoException
extends RuntimeException {
    private static final long serialVersionUID = -9155939328084105145L;

    public VideoException(String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        return "VideoException [" + super.toString() + "]";
    }
}

