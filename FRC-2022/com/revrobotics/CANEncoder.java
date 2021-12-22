/*
 * Decompiled with CFR 0.150.
 */
package com.revrobotics;

import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANError;
import com.revrobotics.CANSensor;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.EncoderType;
import com.revrobotics.jni.CANSparkMaxJNI;
import java.util.concurrent.atomic.AtomicBoolean;

public class CANEncoder
extends CANSensor {
    private int m_counts_per_rev = 4096;
    private AtomicBoolean m_encInitialized = new AtomicBoolean(false);
    private AtomicBoolean m_altEncInitialized = new AtomicBoolean(false);

    @Deprecated(forRemoval=true)
    public CANEncoder(CANSparkMax device, EncoderType encoderType, int countsPerRev) {
        super(device);
        if (!this.m_encInitialized.get() || this.m_counts_per_rev != countsPerRev) {
            this.m_encInitialized.set(true);
            this.m_altEncInitialized.set(false);
            this.m_counts_per_rev = countsPerRev;
            CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetSensorType(this.m_device.m_sparkMax, encoderType.value));
            if (encoderType != EncoderType.kHallSensor && this.m_counts_per_rev != 0) {
                CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetCountsPerRevolution(this.m_device.m_sparkMax, countsPerRev));
            }
        }
    }

    @Deprecated(forRemoval=true)
    public CANEncoder(CANSparkMax device, AlternateEncoderType encoderType, int countsPerRev) {
        super(device);
        if (this.m_device.m_limitSwitchInitialized) {
            throw new IllegalArgumentException("Cannot instantiate an alternative encoder while limit switches are enabled");
        }
        if (!this.m_altEncInitialized.get()) {
            this.m_altEncInitialized.set(true);
            this.m_encInitialized.set(false);
            CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetDataPortConfig(this.m_device.m_sparkMax, 1));
        }
        if (this.m_counts_per_rev != countsPerRev) {
            this.m_counts_per_rev = countsPerRev;
            CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetAltEncoderCountsPerRevolution(this.m_device.m_sparkMax, countsPerRev));
        }
    }

    @Deprecated(forRemoval=true)
    public CANEncoder(CANSparkMax device) {
        this(device, EncoderType.kHallSensor, 42);
    }

    public double getPosition() {
        if (this.m_encInitialized.get()) {
            return CANSparkMaxJNI.c_SparkMax_GetEncoderPosition(this.m_device.m_sparkMax);
        }
        return CANSparkMaxJNI.c_SparkMax_GetAltEncoderPosition(this.m_device.m_sparkMax);
    }

    public double getVelocity() {
        if (this.m_encInitialized.get()) {
            return CANSparkMaxJNI.c_SparkMax_GetEncoderVelocity(this.m_device.m_sparkMax);
        }
        return CANSparkMaxJNI.c_SparkMax_GetAltEncoderVelocity(this.m_device.m_sparkMax);
    }

    public CANError setPosition(double position) {
        if (this.m_encInitialized.get()) {
            return this.m_device.setEncPosition(position);
        }
        return this.m_device.setAltEncPosition(position);
    }

    public CANError setPositionConversionFactor(double factor) {
        if (this.m_encInitialized.get()) {
            return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetPositionConversionFactor(this.m_device.m_sparkMax, (float)factor));
        }
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetAltEncoderPositionFactor(this.m_device.m_sparkMax, (float)factor));
    }

    public CANError setVelocityConversionFactor(double factor) {
        if (this.m_encInitialized.get()) {
            return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetVelocityConversionFactor(this.m_device.m_sparkMax, (float)factor));
        }
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetAltEncoderVelocityFactor(this.m_device.m_sparkMax, (float)factor));
    }

    public double getPositionConversionFactor() {
        if (this.m_encInitialized.get()) {
            return CANSparkMaxJNI.c_SparkMax_GetPositionConversionFactor(this.m_device.m_sparkMax);
        }
        return CANSparkMaxJNI.c_SparkMax_GetAltEncoderPositionFactor(this.m_device.m_sparkMax);
    }

    public double getVelocityConversionFactor() {
        if (this.m_encInitialized.get()) {
            return CANSparkMaxJNI.c_SparkMax_GetVelocityConversionFactor(this.m_device.m_sparkMax);
        }
        return CANSparkMaxJNI.c_SparkMax_GetAltEncoderVelocityFactor(this.m_device.m_sparkMax);
    }

    public CANError setAverageDepth(int depth) {
        if (this.m_encInitialized.get()) {
            return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetAverageDepth(this.m_device.m_sparkMax, depth));
        }
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetAltEncoderAverageDepth(this.m_device.m_sparkMax, depth));
    }

    public int getAverageDepth() {
        if (this.m_encInitialized.get()) {
            return CANSparkMaxJNI.c_SparkMax_GetAverageDepth(this.m_device.m_sparkMax);
        }
        return CANSparkMaxJNI.c_SparkMax_GetAltEncoderAverageDepth(this.m_device.m_sparkMax);
    }

    public CANError setMeasurementPeriod(int period_us) {
        if (this.m_encInitialized.get()) {
            return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetMeasurementPeriod(this.m_device.m_sparkMax, period_us));
        }
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetAltEncoderMeasurementPeriod(this.m_device.m_sparkMax, period_us));
    }

    public int getMeasurementPeriod() {
        if (this.m_encInitialized.get()) {
            return CANSparkMaxJNI.c_SparkMax_GetMeasurementPeriod(this.m_device.m_sparkMax);
        }
        return CANSparkMaxJNI.c_SparkMax_GetAltEncoderMeasurementPeriod(this.m_device.m_sparkMax);
    }

    @Deprecated(since="1.5.0", forRemoval=true)
    public int getCPR() {
        return this.getCountsPerRevolution();
    }

    public int getCountsPerRevolution() {
        if (this.m_encInitialized.get()) {
            return CANSparkMaxJNI.c_SparkMax_GetCountsPerRevolution(this.m_device.m_sparkMax);
        }
        return CANSparkMaxJNI.c_SparkMax_GetAltEncoderCountsPerRevolution(this.m_device.m_sparkMax);
    }

    @Override
    protected int getID() {
        if (this.m_encInitialized.get()) {
            return this.m_device.getInitialMotorType() == CANSparkMaxLowLevel.MotorType.kBrushless ? CANSensor.FeedbackSensorType.kHallSensor.value : CANSensor.FeedbackSensorType.kQuadrature.value;
        }
        return CANSensor.FeedbackSensorType.kAltQuadrature.value;
    }

    @Override
    public CANError setInverted(boolean inverted) {
        if (this.m_encInitialized.get()) {
            if (this.m_device.getInitialMotorType() == CANSparkMaxLowLevel.MotorType.kBrushless) {
                throw new IllegalArgumentException("Not available in Brushless Mode");
            }
            return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetEncoderInverted(this.m_device.m_sparkMax, inverted));
        }
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetAltEncoderInverted(this.m_device.m_sparkMax, inverted));
    }

    @Override
    public boolean getInverted() {
        if (this.m_encInitialized.get()) {
            return CANSparkMaxJNI.c_SparkMax_GetEncoderInverted(this.m_device.m_sparkMax);
        }
        return CANSparkMaxJNI.c_SparkMax_GetAltEncoderInverted(this.m_device.m_sparkMax);
    }
}

