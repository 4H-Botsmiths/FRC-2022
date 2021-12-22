/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.util.concurrent;

import edu.wpi.first.util.WPIUtilJNI;

public final class Event
implements AutoCloseable {
    private int m_handle;

    public Event(boolean manualReset, boolean initialState) {
        this.m_handle = WPIUtilJNI.createEvent(manualReset, initialState);
    }

    public Event(boolean manualReset) {
        this(manualReset, false);
    }

    public Event() {
        this(false, false);
    }

    @Override
    public void close() {
        if (this.m_handle != 0) {
            WPIUtilJNI.destroyEvent(this.m_handle);
            this.m_handle = 0;
        }
    }

    public int getHandle() {
        return this.m_handle;
    }

    public void set() {
        WPIUtilJNI.setEvent(this.m_handle);
    }

    public void reset() {
        WPIUtilJNI.resetEvent(this.m_handle);
    }
}

