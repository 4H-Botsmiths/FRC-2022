/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.motorcontrol;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SpeedController;

public interface MotorController
extends SpeedController {
    @Override
    public void set(double var1);

    @Override
    default public void setVoltage(double outputVolts) {
        this.set(outputVolts / RobotController.getBatteryVoltage());
    }

    @Override
    public double get();

    @Override
    public void setInverted(boolean var1);

    @Override
    public boolean getInverted();

    @Override
    public void disable();

    @Override
    public void stopMotor();
}

