/*
 * Decompiled with CFR 0.150.
 */
package com.revrobotics;

import com.revrobotics.CANError;
import com.revrobotics.CANSensor;
import com.revrobotics.CANSparkMax;
import com.revrobotics.jni.CANSparkMaxJNI;

public class CANAnalog
extends CANSensor {
    @Deprecated(forRemoval=true)
    public CANAnalog(CANSparkMax device, AnalogMode mode) {
        super(device);
        CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetAnalogMode(this.m_device.m_sparkMax, mode.value));
    }

    public double getVoltage() {
        return CANSparkMaxJNI.c_SparkMax_GetAnalogVoltage(this.m_device.m_sparkMax);
    }

    public double getPosition() {
        return CANSparkMaxJNI.c_SparkMax_GetAnalogPosition(this.m_device.m_sparkMax);
    }

    public double getVelocity() {
        return CANSparkMaxJNI.c_SparkMax_GetAnalogVelocity(this.m_device.m_sparkMax);
    }

    public CANError setPositionConversionFactor(double factor) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetAnalogPositionConversionFactor(this.m_device.m_sparkMax, (float)factor));
    }

    public CANError setVelocityConversionFactor(double factor) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetAnalogVelocityConversionFactor(this.m_device.m_sparkMax, (float)factor));
    }

    public double getPositionConversionFactor() {
        return CANSparkMaxJNI.c_SparkMax_GetAnalogPositionConversionFactor(this.m_device.m_sparkMax);
    }

    public double getVelocityConversionFactor() {
        return CANSparkMaxJNI.c_SparkMax_GetAnalogVelocityConversionFactor(this.m_device.m_sparkMax);
    }

    @Override
    protected int getID() {
        return CANSensor.FeedbackSensorType.kAnalog.value;
    }

    @Override
    public CANError setInverted(boolean inverted) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetAnalogInverted(this.m_device.m_sparkMax, inverted));
    }

    @Override
    public boolean getInverted() {
        return CANSparkMaxJNI.c_SparkMax_GetAnalogInverted(this.m_device.m_sparkMax);
    }

    public static enum AnalogMode {
        kAbsolute(0),
        kRelative(1);

        public final int value;

        private AnalogMode(int value) {
            this.value = value;
        }

        public static AnalogMode fromId(int id) {
            if (id == 1) {
                return kRelative;
            }
            return kAbsolute;
        }
    }
}

