/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.motorcontrol;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;

public class SD540
extends PWMMotorController {
    public SD540(int channel) {
        super("SD540", channel);
        this.m_pwm.setBounds(2.05, 1.55, 1.5, 1.44, 0.94);
        this.m_pwm.setPeriodMultiplier(PWM.PeriodMultiplier.k1X);
        this.m_pwm.setSpeed(0.0);
        this.m_pwm.setZeroLatch();
        HAL.report(56, this.getChannel() + 1);
    }
}

