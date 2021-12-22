/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

public abstract class DigitalSource
implements AutoCloseable {
    public abstract boolean isAnalogTrigger();

    public abstract int getChannel();

    public abstract int getAnalogTriggerTypeForRouting();

    public abstract int getPortHandleForRouting();

    @Override
    public void close() {
    }
}

