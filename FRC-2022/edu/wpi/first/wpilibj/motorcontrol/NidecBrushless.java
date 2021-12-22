/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.motorcontrol;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class NidecBrushless
extends MotorSafety
implements MotorController,
Sendable,
AutoCloseable {
    private boolean m_isInverted;
    private final DigitalOutput m_dio;
    private final PWM m_pwm;
    private volatile double m_speed;
    private volatile boolean m_disabled;

    public NidecBrushless(int pwmChannel, int dioChannel) {
        this.setSafetyEnabled(false);
        this.m_dio = new DigitalOutput(dioChannel);
        SendableRegistry.addChild(this, this.m_dio);
        this.m_dio.setPWMRate(15625.0);
        this.m_dio.enablePWM(0.5);
        this.m_pwm = new PWM(pwmChannel);
        SendableRegistry.addChild(this, this.m_pwm);
        HAL.report(62, pwmChannel + 1);
        SendableRegistry.addLW((Sendable)this, "Nidec Brushless", pwmChannel);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        this.m_dio.close();
        this.m_pwm.close();
    }

    @Override
    public void set(double speed) {
        if (!this.m_disabled) {
            this.m_speed = speed;
            this.m_dio.updateDutyCycle(0.5 + 0.5 * (this.m_isInverted ? -speed : speed));
            this.m_pwm.setRaw(65535);
        }
        this.feed();
    }

    @Override
    public double get() {
        return this.m_speed;
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
    public void stopMotor() {
        this.m_dio.updateDutyCycle(0.5);
        this.m_pwm.setDisabled();
    }

    @Override
    public String getDescription() {
        return "Nidec " + this.getChannel();
    }

    @Override
    public void disable() {
        this.m_disabled = true;
        this.m_dio.updateDutyCycle(0.5);
        this.m_pwm.setDisabled();
    }

    public void enable() {
        this.m_disabled = false;
    }

    public int getChannel() {
        return this.m_pwm.getChannel();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Nidec Brushless");
        builder.setActuator(true);
        builder.setSafeState(this::stopMotor);
        builder.addDoubleProperty("Value", this::get, this::set);
    }
}

