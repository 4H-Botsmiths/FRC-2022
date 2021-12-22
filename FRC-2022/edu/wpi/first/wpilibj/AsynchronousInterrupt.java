/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.SynchronousInterrupt;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class AsynchronousInterrupt
implements AutoCloseable {
    private final BiConsumer<Boolean, Boolean> m_callback;
    private final SynchronousInterrupt m_interrupt;
    private final AtomicBoolean m_keepRunning = new AtomicBoolean(false);
    private Thread m_thread;

    public AsynchronousInterrupt(DigitalSource source, BiConsumer<Boolean, Boolean> callback) {
        this.m_callback = ErrorMessages.requireNonNullParam(callback, "callback", "AsynchronousInterrupt");
        this.m_interrupt = new SynchronousInterrupt(source);
    }

    @Override
    public void close() {
        this.disable();
        this.m_interrupt.close();
    }

    public void enable() {
        if (this.m_keepRunning.get()) {
            return;
        }
        this.m_keepRunning.set(true);
        this.m_thread = new Thread(this::threadMain);
        this.m_thread.start();
    }

    public void disable() {
        this.m_keepRunning.set(false);
        this.m_interrupt.wakeupWaitingInterrupt();
        if (this.m_thread != null) {
            if (this.m_thread.isAlive()) {
                try {
                    this.m_thread.interrupt();
                    this.m_thread.join();
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            this.m_thread = null;
        }
    }

    public void setInterruptEdges(boolean risingEdge, boolean fallingEdge) {
        this.m_interrupt.setInterruptEdges(risingEdge, fallingEdge);
    }

    public double getRisingTimestamp() {
        return this.m_interrupt.getRisingTimestamp();
    }

    public double getFallingTimestamp() {
        return this.m_interrupt.getFallingTimestamp();
    }

    private void threadMain() {
        while (this.m_keepRunning.get()) {
            int result = this.m_interrupt.waitForInterruptRaw(10.0, false);
            if (!this.m_keepRunning.get()) break;
            if (result == 0) continue;
            this.m_callback.accept((result & 1) != 0, (result & 0x100) != 0);
        }
    }
}

