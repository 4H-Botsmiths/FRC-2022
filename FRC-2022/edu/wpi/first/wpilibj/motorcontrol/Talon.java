/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.motorcontrol;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;

public class Talon
extends PWMMotorController {
    public Talon(int channel) {
        super("Talon", channel);
        this.m_pwm.setBounds(2.037, 1.539, 1.513, 1.487, 0.989);
        this.m_pwm.setPeriodMultiplier(PWM.PeriodMultiplier.k1X);
        this.m_pwm.setSpeed(0.0);
        this.m_pwm.setZeroLatch();
        HAL.report(44, this.getChannel() + 1);
    }
}

