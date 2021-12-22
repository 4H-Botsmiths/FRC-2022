/*
 * Decompiled with CFR 0.150.
 */
package com.revrobotics;

import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.jni.CANSparkMaxJNI;

public class CANDigitalInput {
    private final CANSparkMax m_device;
    private LimitSwitch m_limitSwitch = LimitSwitch.kForward;

    @Deprecated(forRemoval=true)
    public CANDigitalInput(CANSparkMax device, LimitSwitch limitSwitch, LimitSwitchPolarity polarity) {
        this.m_device = device;
        this.m_limitSwitch = limitSwitch;
        if (this.m_device.m_altEncInitialized) {
            throw new IllegalArgumentException("Cannot instantiate a limit switch while using an alternative encoder");
        }
        CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetDataPortConfig(this.m_device.m_sparkMax, 0));
        CANSparkMaxJNI.c_SparkMax_SetLimitPolarity(this.m_device.m_sparkMax, limitSwitch.value, polarity.value);
    }

    public boolean get() {
        if (this.m_limitSwitch == LimitSwitch.kForward) {
            return this.m_device.getFault(CANSparkMax.FaultID.kHardLimitFwd);
        }
        return this.m_device.getFault(CANSparkMax.FaultID.kHardLimitRev);
    }

    public CANError enableLimitSwitch(boolean enable) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_EnableLimitSwitch(this.m_device.m_sparkMax, this.m_limitSwitch.value, enable));
    }

    public boolean isLimitSwitchEnabled() {
        return CANSparkMaxJNI.c_SparkMax_IsLimitEnabled(this.m_device.m_sparkMax, this.m_limitSwitch.value);
    }

    public static enum LimitSwitchPolarity {
        kNormallyOpen(0),
        kNormallyClosed(1);

        public final int value;

        private LimitSwitchPolarity(int value) {
            this.value = value;
        }
    }

    @Deprecated(forRemoval=true)
    public static enum LimitSwitch {
        kForward(0),
        kReverse(1);

        public final int value;

        private LimitSwitch(int value) {
            this.value = value;
        }
    }
}

