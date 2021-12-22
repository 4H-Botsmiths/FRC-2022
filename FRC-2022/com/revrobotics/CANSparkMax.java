/*
 * Decompiled with CFR 0.150.
 */
package com.revrobotics;

import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANAnalog;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.jni.CANSparkMaxJNI;

public class CANSparkMax
extends CANSparkMaxLowLevel
implements AutoCloseable {
    boolean m_altEncInitialized = false;
    boolean m_limitSwitchInitialized = false;

    public CANSparkMax(int deviceID, CANSparkMaxLowLevel.MotorType type) {
        super(deviceID, type);
    }

    @Override
    public void close() {
    }

    @Override
    public void set(double speed) {
        this.setpointCommand(speed, ControlType.kDutyCycle);
    }

    @Override
    public void setVoltage(double outputVolts) {
        this.setpointCommand(outputVolts, ControlType.kVoltage);
    }

    @Override
    public double get() {
        return this.getAppliedOutput();
    }

    @Override
    public void setInverted(boolean isInverted) {
        CANSparkMaxJNI.c_SparkMax_SetInverted(this.m_sparkMax, isInverted);
    }

    @Override
    public boolean getInverted() {
        return CANSparkMaxJNI.c_SparkMax_GetInverted(this.m_sparkMax);
    }

    @Override
    public void disable() {
        this.set(0.0);
    }

    @Override
    public void stopMotor() {
        this.set(0.0);
    }

    public void pidWrite(double output) {
        this.set(output);
    }

    public CANEncoder getEncoder() {
        return this.getEncoder(EncoderType.kHallSensor, 0);
    }

    public CANEncoder getEncoder(EncoderType encoderType, int countsPerRev) {
        return new CANEncoder(this, encoderType, countsPerRev);
    }

    @Deprecated(forRemoval=true)
    public CANEncoder getAlternateEncoder() {
        return new CANEncoder(this, AlternateEncoderType.kQuadrature, 0);
    }

    public CANEncoder getAlternateEncoder(int countsPerRev) {
        return this.getAlternateEncoder(AlternateEncoderType.kQuadrature, countsPerRev);
    }

    public CANEncoder getAlternateEncoder(AlternateEncoderType encoderType, int countsPerRev) {
        return new CANEncoder(this, encoderType, countsPerRev);
    }

    public CANAnalog getAnalog(CANAnalog.AnalogMode mode) {
        return new CANAnalog(this, mode);
    }

    public CANPIDController getPIDController() {
        return new CANPIDController(this);
    }

    public CANDigitalInput getForwardLimitSwitch(CANDigitalInput.LimitSwitchPolarity polarity) {
        return new CANDigitalInput(this, CANDigitalInput.LimitSwitch.kForward, polarity);
    }

    public CANDigitalInput getReverseLimitSwitch(CANDigitalInput.LimitSwitchPolarity polarity) {
        return new CANDigitalInput(this, CANDigitalInput.LimitSwitch.kReverse, polarity);
    }

    public CANError setSmartCurrentLimit(int limit) {
        return this.setSmartCurrentLimit(limit, 0, 20000);
    }

    public CANError setSmartCurrentLimit(int stallLimit, int freeLimit) {
        return this.setSmartCurrentLimit(stallLimit, freeLimit, 20000);
    }

    public CANError setSmartCurrentLimit(int stallLimit, int freeLimit, int limitRPM) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetSmartCurrentLimit(this.m_sparkMax, stallLimit, freeLimit, limitRPM));
    }

    public CANError setSecondaryCurrentLimit(double limit) {
        return this.setSecondaryCurrentLimit(limit, 0);
    }

    public CANError setSecondaryCurrentLimit(double limit, int chopCycles) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetSecondaryCurrentLimit(this.m_sparkMax, (float)limit, chopCycles));
    }

    public CANError setIdleMode(IdleMode mode) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetIdleMode(this.m_sparkMax, mode.value));
    }

    public IdleMode getIdleMode() {
        return IdleMode.fromId(CANSparkMaxJNI.c_SparkMax_GetIdleMode(this.m_sparkMax));
    }

    public CANError enableVoltageCompensation(double nominalVoltage) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_EnableVoltageCompensation(this.m_sparkMax, (float)nominalVoltage));
    }

    public CANError disableVoltageCompensation() {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_DisableVoltageCompensation(this.m_sparkMax));
    }

    public double getVoltageCompensationNominalVoltage() {
        return CANSparkMaxJNI.c_SparkMax_GetVoltageCompensationNominalVoltage(this.m_sparkMax);
    }

    public CANError setOpenLoopRampRate(double rate) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetOpenLoopRampRate(this.m_sparkMax, (float)rate));
    }

    public CANError setClosedLoopRampRate(double rate) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetClosedLoopRampRate(this.m_sparkMax, (float)rate));
    }

    public double getOpenLoopRampRate() {
        return CANSparkMaxJNI.c_SparkMax_GetOpenLoopRampRate(this.m_sparkMax);
    }

    public double getClosedLoopRampRate() {
        return CANSparkMaxJNI.c_SparkMax_GetClosedLoopRampRate(this.m_sparkMax);
    }

    public CANError follow(CANSparkMax leader) {
        return this.follow(leader, false);
    }

    public CANError follow(CANSparkMax leader, boolean invert) {
        return this.follow(ExternalFollower.kFollowerSparkMax, leader.getDeviceId(), invert);
    }

    public CANError follow(ExternalFollower leader, int deviceID) {
        boolean inverted = CANSparkMaxJNI.c_SparkMax_GetInverted(this.m_sparkMax);
        return this.follow(leader, deviceID, inverted);
    }

    public CANError follow(ExternalFollower leader, int deviceID, boolean invert) {
        CANSparkMaxLowLevel.FollowConfig maxFollower = new CANSparkMaxLowLevel.FollowConfig();
        maxFollower.leaderArbId = leader.arbId == 0 ? 0 : leader.arbId | deviceID;
        maxFollower.config.predefined = leader.configId;
        maxFollower.config.invert = invert ? 1 : 0;
        return this.setFollow(maxFollower);
    }

    public boolean isFollower() {
        return CANSparkMaxJNI.c_SparkMax_IsFollower(this.m_sparkMax);
    }

    public short getFaults() {
        return (short)CANSparkMaxJNI.c_SparkMax_GetFaults(this.m_sparkMax);
    }

    public short getStickyFaults() {
        return (short)CANSparkMaxJNI.c_SparkMax_GetStickyFaults(this.m_sparkMax);
    }

    public boolean getFault(FaultID faultID) {
        return CANSparkMaxJNI.c_SparkMax_GetFault(this.m_sparkMax, faultID.value);
    }

    public boolean getStickyFault(FaultID faultID) {
        return CANSparkMaxJNI.c_SparkMax_GetStickyFault(this.m_sparkMax, faultID.value);
    }

    public double getBusVoltage() {
        return CANSparkMaxJNI.c_SparkMax_GetBusVoltage(this.m_sparkMax);
    }

    public double getAppliedOutput() {
        return CANSparkMaxJNI.c_SparkMax_GetAppliedOutput(this.m_sparkMax);
    }

    public double getOutputCurrent() {
        return CANSparkMaxJNI.c_SparkMax_GetOutputCurrent(this.m_sparkMax);
    }

    public double getMotorTemperature() {
        return CANSparkMaxJNI.c_SparkMax_GetMotorTemperature(this.m_sparkMax);
    }

    public CANError clearFaults() {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_ClearFaults(this.m_sparkMax));
    }

    public CANError burnFlash() {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_BurnFlash(this.m_sparkMax));
    }

    public CANError setCANTimeout(int milliseconds) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetCANTimeout(this.m_sparkMax, milliseconds));
    }

    public CANError enableSoftLimit(SoftLimitDirection direction, boolean enable) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_EnableSoftLimit(this.m_sparkMax, direction.value, enable));
    }

    public CANError setSoftLimit(SoftLimitDirection direction, float limit) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetSoftLimit(this.m_sparkMax, direction.value, limit));
    }

    public double getSoftLimit(SoftLimitDirection direction) {
        return CANSparkMaxJNI.c_SparkMax_GetSoftLimit(this.m_sparkMax, direction.value);
    }

    public boolean isSoftLimitEnabled(SoftLimitDirection direction) {
        return CANSparkMaxJNI.c_SparkMax_IsSoftLimitEnabled(this.m_sparkMax, direction.value);
    }

    protected int getFeedbackDeviceID() {
        return CANSparkMaxJNI.c_SparkMax_GetFeedbackDeviceID(this.m_sparkMax);
    }

    public CANError getLastError() {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_GetLastError(this.m_sparkMax));
    }

    public static class ExternalFollower {
        private final int arbId;
        private final int configId;
        public static final ExternalFollower kFollowerDisabled = new ExternalFollower(0, 0);
        public static final ExternalFollower kFollowerSparkMax = new ExternalFollower(33888256, 26);
        public static final ExternalFollower kFollowerPhoenix = new ExternalFollower(33816704, 27);

        public ExternalFollower(int arbId, int configId) {
            this.arbId = arbId;
            this.configId = configId;
        }
    }

    public static enum FaultID {
        kBrownout(0),
        kOvercurrent(1),
        kIWDTReset(2),
        kMotorFault(3),
        kSensorFault(4),
        kStall(5),
        kEEPROMCRC(6),
        kCANTX(7),
        kCANRX(8),
        kHasReset(9),
        kDRVFault(10),
        kOtherFault(11),
        kSoftLimitFwd(12),
        kSoftLimitRev(13),
        kHardLimitFwd(14),
        kHardLimitRev(15);

        public final int value;

        private FaultID(int value) {
            this.value = value;
        }

        public static FaultID fromId(int id) {
            for (FaultID type : FaultID.values()) {
                if (type.value != id) continue;
                return type;
            }
            return null;
        }
    }

    public static enum SoftLimitDirection {
        kForward(0),
        kReverse(1);

        public final int value;

        private SoftLimitDirection(int value) {
            this.value = value;
        }

        public static SoftLimitDirection fromID(int id) {
            if (id == 1) {
                return kReverse;
            }
            return kForward;
        }
    }

    public static enum InputMode {
        kPWM(0),
        kCAN(1);

        public final int value;

        private InputMode(int value) {
            this.value = value;
        }

        public static InputMode fromId(int id) {
            if (id == 1) {
                return kCAN;
            }
            return kPWM;
        }
    }

    public static enum IdleMode {
        kCoast(0),
        kBrake(1);

        public final int value;

        private IdleMode(int value) {
            this.value = value;
        }

        public static IdleMode fromId(int id) {
            if (id == 1) {
                return kBrake;
            }
            return kCoast;
        }
    }
}

