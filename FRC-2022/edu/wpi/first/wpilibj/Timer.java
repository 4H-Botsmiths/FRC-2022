/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;

public class Timer {
    private double m_startTime;
    private double m_accumulatedTime;
    private boolean m_running;

    public static double getFPGATimestamp() {
        return (double)RobotController.getFPGATime() / 1000000.0;
    }

    public static double getMatchTime() {
        return DriverStation.getMatchTime();
    }

    public static void delay(double seconds) {
        try {
            Thread.sleep((long)(seconds * 1000.0));
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public Timer() {
        this.reset();
    }

    private double getMsClock() {
        return (double)RobotController.getFPGATime() / 1000.0;
    }

    public double get() {
        if (this.m_running) {
            return this.m_accumulatedTime + (this.getMsClock() - this.m_startTime) / 1000.0;
        }
        return this.m_accumulatedTime;
    }

    public void reset() {
        this.m_accumulatedTime = 0.0;
        this.m_startTime = this.getMsClock();
    }

    public void start() {
        if (!this.m_running) {
            this.m_startTime = this.getMsClock();
            this.m_running = true;
        }
    }

    public void stop() {
        this.m_accumulatedTime = this.get();
        this.m_running = false;
    }

    public boolean hasElapsed(double seconds) {
        return this.get() >= seconds;
    }

    @Deprecated(since="2022", forRemoval=true)
    public boolean hasPeriodPassed(double period) {
        return this.advanceIfElapsed(period);
    }

    public boolean advanceIfElapsed(double seconds) {
        if (this.get() >= seconds) {
            this.m_startTime += seconds * 1000.0;
            return true;
        }
        return false;
    }
}

