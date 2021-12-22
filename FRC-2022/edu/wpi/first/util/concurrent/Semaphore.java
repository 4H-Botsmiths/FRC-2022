/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.util.concurrent;

import edu.wpi.first.util.WPIUtilJNI;

public final class Semaphore
implements AutoCloseable {
    private int m_handle;

    public Semaphore(int initialCount, int maximumCount) {
        this.m_handle = WPIUtilJNI.createSemaphore(initialCount, maximumCount);
    }

    public Semaphore(int initialCount) {
        this(initialCount, Integer.MAX_VALUE);
    }

    public Semaphore() {
        this(0, Integer.MAX_VALUE);
    }

    @Override
    public void close() {
        if (this.m_handle != 0) {
            WPIUtilJNI.destroySemaphore(this.m_handle);
            this.m_handle = 0;
        }
    }

    public int getHandle() {
        return this.m_handle;
    }

    public boolean release(int releaseCount) {
        return WPIUtilJNI.releaseSemaphore(this.m_handle, releaseCount);
    }

    public boolean release() {
        return this.release(1);
    }
}

