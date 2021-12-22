/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj.Timer;

class CommandState {
    private double m_startTime = -1.0;
    private final boolean m_interruptible;

    CommandState(boolean interruptible) {
        this.m_interruptible = interruptible;
        this.startTiming();
        this.startRunning();
    }

    private void startTiming() {
        this.m_startTime = Timer.getFPGATimestamp();
    }

    synchronized void startRunning() {
        this.m_startTime = -1.0;
    }

    boolean isInterruptible() {
        return this.m_interruptible;
    }

    double timeSinceInitialized() {
        return this.m_startTime != -1.0 ? Timer.getFPGATimestamp() - this.m_startTime : -1.0;
    }
}

