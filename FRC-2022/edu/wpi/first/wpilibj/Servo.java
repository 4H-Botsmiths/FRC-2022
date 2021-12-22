/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.PWM;

public class Servo
extends PWM {
    private static final double kMaxServoAngle = 180.0;
    private static final double kMinServoAngle = 0.0;
    protected static final double kDefaultMaxServoPWM = 2.4;
    protected static final double kDefaultMinServoPWM = 0.6;

    public Servo(int channel) {
        super(channel);
        this.setBounds(2.4, 0.0, 0.0, 0.0, 0.6);
        this.setPeriodMultiplier(PWM.PeriodMultiplier.k4X);
        HAL.report(33, this.getChannel() + 1);
        SendableRegistry.setName((Sendable)this, "Servo", this.getChannel());
    }

    public void set(double value) {
        this.setPosition(value);
    }

    public double get() {
        return this.getPosition();
    }

    public void setAngle(double degrees) {
        if (degrees < 0.0) {
            degrees = 0.0;
        } else if (degrees > 180.0) {
            degrees = 180.0;
        }
        this.setPosition((degrees - 0.0) / this.getServoAngleRange());
    }

    public double getAngle() {
        return this.getPosition() * this.getServoAngleRange() + 0.0;
    }

    private double getServoAngleRange() {
        return 180.0;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Servo");
        builder.addDoubleProperty("Value", this::get, this::set);
    }
}

