/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.motorcontrol;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;

public class Spark
extends PWMMotorController {
    public Spark(int channel) {
        super("Spark", channel);
        this.m_pwm.setBounds(2.003, 1.55, 1.5, 1.46, 0.999);
        this.m_pwm.setPeriodMultiplier(PWM.PeriodMultiplier.k1X);
        this.m_pwm.setSpeed(0.0);
        this.m_pwm.setZeroLatch();
        HAL.report(55, this.getChannel() + 1);
    }
}

