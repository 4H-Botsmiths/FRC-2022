/*
 * Decompiled with CFR 0.150.
 */
package com.revrobotics;

import com.revrobotics.CANError;
import com.revrobotics.CANSensor;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.jni.CANSparkMaxJNI;

public class CANPIDController {
    private final CANSparkMax m_device;

    @Deprecated(forRemoval=true)
    public CANPIDController(CANSparkMax device) {
        this.m_device = device;
    }

    public CANError setReference(double value, ControlType ctrl) {
        return this.setReference(value, ctrl, 0);
    }

    public CANError setReference(double value, ControlType ctrl, int pidSlot) {
        return this.setReference(value, ctrl, pidSlot, 0.0);
    }

    public CANError setReference(double value, ControlType ctrl, int pidSlot, double arbFeedforward) {
        return this.m_device.setpointCommand(value, ctrl, pidSlot, arbFeedforward);
    }

    public CANError setReference(double value, ControlType ctrl, int pidSlot, double arbFeedforward, ArbFFUnits arbFFUnits) {
        return this.m_device.setpointCommand(value, ctrl, pidSlot, arbFeedforward, arbFFUnits.value);
    }

    public CANError setP(double gain) {
        return this.setP(gain, 0);
    }

    public CANError setP(double gain, int slotID) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetP(this.m_device.m_sparkMax, slotID, (float)gain));
    }

    public CANError setI(double gain) {
        return this.setI(gain, 0);
    }

    public CANError setI(double gain, int slotID) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetI(this.m_device.m_sparkMax, slotID, (float)gain));
    }

    public CANError setD(double gain) {
        return this.setD(gain, 0);
    }

    public CANError setD(double gain, int slotID) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetD(this.m_device.m_sparkMax, slotID, (float)gain));
    }

    public CANError setDFilter(double gain) {
        return this.setDFilter(gain, 0);
    }

    public CANError setDFilter(double gain, int slotID) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetDFilter(this.m_device.m_sparkMax, slotID, (float)gain));
    }

    public CANError setFF(double gain) {
        return this.setFF(gain, 0);
    }

    public CANError setFF(double gain, int slotID) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetFF(this.m_device.m_sparkMax, slotID, (float)gain));
    }

    public CANError setIZone(double IZone) {
        return this.setIZone(IZone, 0);
    }

    public CANError setIZone(double IZone, int slotID) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetIZone(this.m_device.m_sparkMax, slotID, (float)IZone));
    }

    public CANError setOutputRange(double min, double max) {
        return this.setOutputRange(min, max, 0);
    }

    public CANError setOutputRange(double min, double max, int slotID) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetOutputRange(this.m_device.m_sparkMax, slotID, (float)min, (float)max));
    }

    public double getP() {
        return this.getP(0);
    }

    public double getP(int slotID) {
        return CANSparkMaxJNI.c_SparkMax_GetP(this.m_device.m_sparkMax, slotID);
    }

    public double getI() {
        return this.getI(0);
    }

    public double getI(int slotID) {
        return CANSparkMaxJNI.c_SparkMax_GetI(this.m_device.m_sparkMax, slotID);
    }

    public double getD() {
        return this.getD(0);
    }

    public double getD(int slotID) {
        return CANSparkMaxJNI.c_SparkMax_GetD(this.m_device.m_sparkMax, slotID);
    }

    public double getDFilter(int slotID) {
        return CANSparkMaxJNI.c_SparkMax_GetDFilter(this.m_device.m_sparkMax, slotID);
    }

    public double getFF() {
        return this.getFF(0);
    }

    public double getFF(int slotID) {
        return CANSparkMaxJNI.c_SparkMax_GetFF(this.m_device.m_sparkMax, slotID);
    }

    public double getIZone() {
        return this.getIZone(0);
    }

    public double getIZone(int slotID) {
        return CANSparkMaxJNI.c_SparkMax_GetIZone(this.m_device.m_sparkMax, slotID);
    }

    public double getOutputMin() {
        return this.getOutputMin(0);
    }

    public double getOutputMin(int slotID) {
        return CANSparkMaxJNI.c_SparkMax_GetOutputMin(this.m_device.m_sparkMax, slotID);
    }

    public double getOutputMax() {
        return this.getOutputMax(0);
    }

    public double getOutputMax(int slotID) {
        return CANSparkMaxJNI.c_SparkMax_GetOutputMax(this.m_device.m_sparkMax, slotID);
    }

    public CANError setSmartMotionMaxVelocity(double maxVel, int slotID) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetSmartMotionMaxVelocity(this.m_device.m_sparkMax, slotID, (float)maxVel));
    }

    public CANError setSmartMotionMaxAccel(double maxAccel, int slotID) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetSmartMotionMaxAccel(this.m_device.m_sparkMax, slotID, (float)maxAccel));
    }

    public CANError setSmartMotionMinOutputVelocity(double minVel, int slotID) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetSmartMotionMinOutputVelocity(this.m_device.m_sparkMax, slotID, (float)minVel));
    }

    public CANError setSmartMotionAllowedClosedLoopError(double allowedErr, int slotID) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetSmartMotionAllowedClosedLoopError(this.m_device.m_sparkMax, slotID, (float)allowedErr));
    }

    public CANError setSmartMotionAccelStrategy(AccelStrategy accelStrategy, int slotID) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetSmartMotionAccelStrategy(this.m_device.m_sparkMax, slotID, accelStrategy.value));
    }

    public double getSmartMotionMaxVelocity(int slotID) {
        return CANSparkMaxJNI.c_SparkMax_GetSmartMotionMaxVelocity(this.m_device.m_sparkMax, slotID);
    }

    public double getSmartMotionMaxAccel(int slotID) {
        return CANSparkMaxJNI.c_SparkMax_GetSmartMotionMaxAccel(this.m_device.m_sparkMax, slotID);
    }

    public double getSmartMotionMinOutputVelocity(int slotID) {
        return CANSparkMaxJNI.c_SparkMax_GetSmartMotionMinOutputVelocity(this.m_device.m_sparkMax, slotID);
    }

    public double getSmartMotionAllowedClosedLoopError(int slotID) {
        return CANSparkMaxJNI.c_SparkMax_GetSmartMotionAllowedClosedLoopError(this.m_device.m_sparkMax, slotID);
    }

    public AccelStrategy getSmartMotionAccelStrategy(int slotID) {
        return AccelStrategy.fromInt(CANSparkMaxJNI.c_SparkMax_GetSmartMotionAccelStrategy(this.m_device.m_sparkMax, slotID));
    }

    public CANError setIMaxAccum(double iMaxAccum, int slotID) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetIMaxAccum(this.m_device.m_sparkMax, slotID, (float)iMaxAccum));
    }

    public double getIMaxAccum(int slotID) {
        return CANSparkMaxJNI.c_SparkMax_GetIMaxAccum(this.m_device.m_sparkMax, slotID);
    }

    public CANError setIAccum(double iAccum) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetIAccum(this.m_device.m_sparkMax, (float)iAccum));
    }

    public double getIAccum() {
        return CANSparkMaxJNI.c_SparkMax_GetIAccum(this.m_device.m_sparkMax);
    }

    public CANError setFeedbackDevice(CANSensor sensor) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetFeedbackDevice(this.m_device.m_sparkMax, sensor.getID()));
    }

    public static enum ArbFFUnits {
        kVoltage(0),
        kPercentOut(1);

        public final int value;

        private ArbFFUnits(int value) {
            this.value = value;
        }
    }

    public static enum AccelStrategy {
        kTrapezoidal(0),
        kSCurve(1);

        public final int value;

        private AccelStrategy(int value) {
            this.value = value;
        }

        public static AccelStrategy fromInt(int value) {
            switch (value) {
                case 0: {
                    return kTrapezoidal;
                }
                case 1: {
                    return kSCurve;
                }
            }
            return kTrapezoidal;
        }
    }
}

