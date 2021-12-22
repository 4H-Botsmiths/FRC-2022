/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  edu.wpi.first.wpilibj.PWMSpeedController
 *  edu.wpi.first.wpilibj.Sendable
 *  edu.wpi.first.wpilibj.smartdashboard.SendableRegistry
 */
package com.revrobotics;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;

@Deprecated(forRemoval=true)
public class SparkMax
extends PWMSpeedController {
    SparkMax(int channel) {
        super(channel);
        this.setBounds(2.003, 1.55, 1.5, 1.46, 0.999);
        this.setPeriodMultiplier(PWM.PeriodMultiplier.k1X);
        this.setSpeed(0.0);
        this.setZeroLatch();
        HAL.report(82, this.getChannel());
        SendableRegistry.setName((Sendable)this, (String)"SparkMax", (int)channel);
    }
}

