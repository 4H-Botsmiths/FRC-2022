/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.motorcontrol;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;

public class Jaguar
extends PWMMotorController {
    public Jaguar(int channel) {
        super("Jaguar", channel);
        this.m_pwm.setBounds(2.31, 1.55, 1.507, 1.454, 0.697);
        this.m_pwm.setPeriodMultiplier(PWM.PeriodMultiplier.k1X);
        this.m_pwm.setSpeed(0.0);
        this.m_pwm.setZeroLatch();
        HAL.report(23, this.getChannel() + 1);
    }
}

