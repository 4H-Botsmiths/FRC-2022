/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class PIDSubsystem
extends SubsystemBase {
    protected final PIDController m_controller;
    protected boolean m_enabled;
    private double m_setpoint;

    public PIDSubsystem(PIDController controller, double initialPosition) {
        this.setSetpoint(initialPosition);
        this.m_controller = ErrorMessages.requireNonNullParam(controller, "controller", "PIDSubsystem");
        this.addChild("PID Controller", this.m_controller);
    }

    public PIDSubsystem(PIDController controller) {
        this(controller, 0.0);
    }

    @Override
    public void periodic() {
        if (this.m_enabled) {
            this.useOutput(this.m_controller.calculate(this.getMeasurement(), this.m_setpoint), this.m_setpoint);
        }
    }

    public PIDController getController() {
        return this.m_controller;
    }

    public void setSetpoint(double setpoint) {
        this.m_setpoint = setpoint;
    }

    public double getSetpoint() {
        return this.m_setpoint;
    }

    protected abstract void useOutput(double var1, double var3);

    protected abstract double getMeasurement();

    public void enable() {
        this.m_enabled = true;
        this.m_controller.reset();
    }

    public void disable() {
        this.m_enabled = false;
        this.useOutput(0.0, 0.0);
    }

    public boolean isEnabled() {
        return this.m_enabled;
    }
}

