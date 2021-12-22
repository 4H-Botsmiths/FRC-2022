/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.InterruptJNI;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.util.ErrorMessages;

public class SynchronousInterrupt
implements AutoCloseable {
    private final DigitalSource m_source;
    private final int m_handle;

    public SynchronousInterrupt(DigitalSource source) {
        this.m_source = ErrorMessages.requireNonNullParam(source, "source", "SynchronousInterrupt");
        this.m_handle = InterruptJNI.initializeInterrupts();
        InterruptJNI.requestInterrupts(this.m_handle, this.m_source.getPortHandleForRouting(), this.m_source.getAnalogTriggerTypeForRouting());
        InterruptJNI.setInterruptUpSourceEdge(this.m_handle, true, false);
    }

    @Override
    public void close() {
        InterruptJNI.cleanInterrupts(this.m_handle);
    }

    int waitForInterruptRaw(double timeoutSeconds, boolean ignorePrevious) {
        return InterruptJNI.waitForInterrupt(this.m_handle, timeoutSeconds, ignorePrevious);
    }

    public WaitResult waitForInterrupt(double timeoutSeconds, boolean ignorePrevious) {
        int result = InterruptJNI.waitForInterrupt(this.m_handle, timeoutSeconds, ignorePrevious);
        boolean rising = (result & 0xFF) != 0;
        boolean falling = (result & 0xFF00) != 0;
        return WaitResult.getValue(rising, falling);
    }

    public WaitResult waitForInterrupt(double timeoutSeconds) {
        return this.waitForInterrupt(timeoutSeconds, true);
    }

    public void setInterruptEdges(boolean risingEdge, boolean fallingEdge) {
        InterruptJNI.setInterruptUpSourceEdge(this.m_handle, risingEdge, fallingEdge);
    }

    public double getRisingTimestamp() {
        return (double)InterruptJNI.readInterruptRisingTimestamp(this.m_handle) * 1.0E-6;
    }

    public double getFallingTimestamp() {
        return (double)InterruptJNI.readInterruptFallingTimestamp(this.m_handle) * 1.0E-6;
    }

    public void wakeupWaitingInterrupt() {
        InterruptJNI.releaseWaitingInterrupt(this.m_handle);
    }

    public static enum WaitResult {
        kTimeout(0),
        kRisingEdge(1),
        kFallingEdge(256),
        kBoth(257);

        public final int value;

        private WaitResult(int value) {
            this.value = value;
        }

        public static WaitResult getValue(boolean rising, boolean falling) {
            if (rising && falling) {
                return kBoth;
            }
            if (rising) {
                return kRisingEdge;
            }
            if (falling) {
                return kFallingEdge;
            }
            return kTimeout;
        }
    }
}

