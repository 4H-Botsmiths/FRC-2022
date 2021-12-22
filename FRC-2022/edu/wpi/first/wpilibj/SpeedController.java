/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.RobotController;

@Deprecated(since="2022", forRemoval=true)
public interface SpeedController {
    public void set(double var1);

    default public void setVoltage(double outputVolts) {
        this.set(outputVolts / RobotController.getBatteryVoltage());
    }

    public double get();

    public void setInverted(boolean var1);

    public boolean getInverted();

    public void disable();

    public void stopMotor();
}

