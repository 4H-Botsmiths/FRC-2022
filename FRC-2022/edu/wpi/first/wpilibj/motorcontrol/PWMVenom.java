/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.motorcontrol;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;

public class PWMVenom
extends PWMMotorController {
    public PWMVenom(int channel) {
        super("PWMVenom", channel);
        this.m_pwm.setBounds(2.004, 1.52, 1.5, 1.48, 0.997);
        this.m_pwm.setPeriodMultiplier(PWM.PeriodMultiplier.k1X);
        this.m_pwm.setSpeed(0.0);
        this.m_pwm.setZeroLatch();
        HAL.report(93, this.getChannel() + 1);
    }
}

