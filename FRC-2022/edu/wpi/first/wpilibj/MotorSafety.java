/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class MotorSafety {
    private static final double kDefaultSafetyExpiration = 0.1;
    private double m_expiration = 0.1;
    private boolean m_enabled;
    private double m_stopTime = Timer.getFPGATimestamp();
    private final Object m_thisMutex = new Object();
    private static final Set<MotorSafety> m_instanceList = new LinkedHashSet<MotorSafety>();
    private static final Object m_listMutex = new Object();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public MotorSafety() {
        Object object = m_listMutex;
        synchronized (object) {
            m_instanceList.add(this);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void feed() {
        Object object = this.m_thisMutex;
        synchronized (object) {
            this.m_stopTime = Timer.getFPGATimestamp() + this.m_expiration;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setExpiration(double expirationTime) {
        Object object = this.m_thisMutex;
        synchronized (object) {
            this.m_expiration = expirationTime;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public double getExpiration() {
        Object object = this.m_thisMutex;
        synchronized (object) {
            return this.m_expiration;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isAlive() {
        Object object = this.m_thisMutex;
        synchronized (object) {
            return !this.m_enabled || this.m_stopTime > Timer.getFPGATimestamp();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void check() {
        double stopTime;
        boolean enabled;
        Object object = this.m_thisMutex;
        synchronized (object) {
            enabled = this.m_enabled;
            stopTime = this.m_stopTime;
        }
        if (!enabled || RobotState.isDisabled() || RobotState.isTest()) {
            return;
        }
        if (stopTime < Timer.getFPGATimestamp()) {
            DriverStation.reportError(this.getDescription() + "... Output not updated often enough.", false);
            this.stopMotor();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setSafetyEnabled(boolean enabled) {
        Object object = this.m_thisMutex;
        synchronized (object) {
            this.m_enabled = enabled;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isSafetyEnabled() {
        Object object = this.m_thisMutex;
        synchronized (object) {
            return this.m_enabled;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void checkMotors() {
        Object object = m_listMutex;
        synchronized (object) {
            for (MotorSafety elem : m_instanceList) {
                elem.check();
            }
        }
    }

    public abstract void stopMotor();

    public abstract String getDescription();
}

