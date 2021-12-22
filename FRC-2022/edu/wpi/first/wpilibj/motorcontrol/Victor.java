/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.motorcontrol;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;

public class Victor
extends PWMMotorController {
    public Victor(int channel) {
        super("Victor", channel);
        this.m_pwm.setBounds(2.027, 1.525, 1.507, 1.49, 1.026);
        this.m_pwm.setPeriodMultiplier(PWM.PeriodMultiplier.k2X);
        this.m_pwm.setSpeed(0.0);
        this.m_pwm.setZeroLatch();
        HAL.report(38, this.getChannel() + 1);
    }
}

