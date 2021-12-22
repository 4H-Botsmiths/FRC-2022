/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.TimedRobot;

public class TimesliceRobot
extends TimedRobot {
    private double m_nextOffset;
    private final double m_controllerPeriod;

    public TimesliceRobot(double robotPeriodicAllocation, double controllerPeriod) {
        this.m_nextOffset = robotPeriodicAllocation;
        this.m_controllerPeriod = controllerPeriod;
    }

    public void schedule(Runnable func, double allocation) {
        if (this.m_nextOffset + allocation > this.m_controllerPeriod) {
            throw new IllegalStateException("Function scheduled at offset " + this.m_nextOffset + " with allocation " + allocation + " exceeded controller period of " + this.m_controllerPeriod + "\n");
        }
        this.addPeriodic(func, this.m_controllerPeriod, this.m_nextOffset);
        this.m_nextOffset += allocation;
    }
}

