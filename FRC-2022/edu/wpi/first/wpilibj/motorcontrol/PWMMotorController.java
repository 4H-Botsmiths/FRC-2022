/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.motorcontrol;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public abstract class PWMMotorController
extends MotorSafety
implements MotorController,
Sendable,
AutoCloseable {
    private boolean m_isInverted;
    protected PWM m_pwm;

    protected PWMMotorController(String name, int channel) {
        this.m_pwm = new PWM(channel, false);
        SendableRegistry.addLW((Sendable)this, name, channel);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        this.m_pwm.close();
    }

    @Override
    public void set(double speed) {
        this.m_pwm.setSpeed(this.m_isInverted ? -speed : speed);
        this.feed();
    }

    @Override
    public double get() {
        return this.m_pwm.getSpeed() * (this.m_isInverted ? -1.0 : 1.0);
    }

    @Override
    public void setInverted(boolean isInverted) {
        this.m_isInverted = isInverted;
    }

    @Override
    public boolean getInverted() {
        return this.m_isInverted;
    }

    @Override
    public void disable() {
        this.m_pwm.setDisabled();
    }

    @Override
    public void stopMotor() {
        this.disable();
    }

    @Override
    public String getDescription() {
        return "PWM " + this.getChannel();
    }

    public int getPwmHandle() {
        return this.m_pwm.getHandle();
    }

    public int getChannel() {
        return this.m_pwm.getChannel();
    }

    public void enableDeadbandElimination(boolean eliminateDeadband) {
        this.m_pwm.enableDeadbandElimination(eliminateDeadband);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Motor Controller");
        builder.setActuator(true);
        builder.setSafeState(this::disable);
        builder.addDoubleProperty("Value", this::get, this::set);
    }
}

