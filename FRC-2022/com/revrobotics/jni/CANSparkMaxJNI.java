/*
 * Decompiled with CFR 0.150.
 */
package com.revrobotics.jni;

import com.revrobotics.jni.RevJNIWrapper;

public class CANSparkMaxJNI
extends RevJNIWrapper {
    public static native long c_SparkMax_Create(int var0, int var1);

    public static native void c_SparkMax_Destroy(long var0);

    public static native int c_SparkMax_GetFirmwareVersion(long var0);

    public static native int c_SparkMax_GetDeviceId(long var0);

    public static native int c_SparkMax_SetMotorType(long var0, int var2);

    public static native int c_SparkMax_GetMotorType(long var0);

    public static native int c_SparkMax_SetPeriodicFramePeriod(long var0, int var2, int var3);

    public static native void c_SparkMax_SetControlFramePeriod(long var0, int var2);

    public static native int c_SparkMax_GetControlFramePeriod(long var0);

    public static native int c_SparkMax_SetEncoderPosition(long var0, float var2);

    public static native int c_SparkMax_RestoreFactoryDefaults(long var0, boolean var2);

    public static native int c_SparkMax_SetFollow(long var0, int var2, int var3);

    public static native float c_SparkMax_SafeFloat(float var0);

    public static native void c_SparkMax_EnableExternalControl(boolean var0);

    public static native void c_SparkMax_SetEnable(boolean var0);

    public static native int c_SparkMax_SetpointCommand(long var0, float var2, int var3, int var4, float var5, int var6);

    public static native int c_SparkMax_SetInverted(long var0, boolean var2);

    public static native boolean c_SparkMax_GetInverted(long var0);

    public static native int c_SparkMax_SetSmartCurrentLimit(long var0, int var2, int var3, int var4);

    public static native int c_SparkMax_GetSmartCurrentStallLimit(long var0);

    public static native int c_SparkMax_GetSmartCurrentFreeLimit(long var0);

    public static native int c_SparkMax_GetSmartCurrentLimitRPM(long var0);

    public static native int c_SparkMax_SetSecondaryCurrentLimit(long var0, float var2, int var3);

    public static native float c_SparkMax_GetSecondaryCurrentLimit(long var0);

    public static native int c_SparkMax_GetSecondaryCurrentLimitCycles(long var0);

    public static native int c_SparkMax_SetIdleMode(long var0, int var2);

    public static native int c_SparkMax_GetIdleMode(long var0);

    public static native int c_SparkMax_EnableVoltageCompensation(long var0, float var2);

    public static native float c_SparkMax_GetVoltageCompensationNominalVoltage(long var0);

    public static native int c_SparkMax_DisableVoltageCompensation(long var0);

    public static native int c_SparkMax_SetOpenLoopRampRate(long var0, float var2);

    public static native float c_SparkMax_GetOpenLoopRampRate(long var0);

    public static native int c_SparkMax_SetClosedLoopRampRate(long var0, float var2);

    public static native float c_SparkMax_GetClosedLoopRampRate(long var0);

    public static native boolean c_SparkMax_IsFollower(long var0);

    public static native int c_SparkMax_GetFaults(long var0);

    public static native int c_SparkMax_GetStickyFaults(long var0);

    public static native boolean c_SparkMax_GetFault(long var0, int var2);

    public static native boolean c_SparkMax_GetStickyFault(long var0, int var2);

    public static native float c_SparkMax_GetBusVoltage(long var0);

    public static native float c_SparkMax_GetAppliedOutput(long var0);

    public static native float c_SparkMax_GetOutputCurrent(long var0);

    public static native float c_SparkMax_GetMotorTemperature(long var0);

    public static native int c_SparkMax_ClearFaults(long var0);

    public static native int c_SparkMax_BurnFlash(long var0);

    public static native int c_SparkMax_SetCANTimeout(long var0, int var2);

    public static native int c_SparkMax_EnableSoftLimit(long var0, int var2, boolean var3);

    public static native boolean c_SparkMax_IsSoftLimitEnabled(long var0, int var2);

    public static native int c_SparkMax_SetSoftLimit(long var0, int var2, float var3);

    public static native float c_SparkMax_GetSoftLimit(long var0, int var2);

    public static native int c_SparkMax_SetSensorType(long var0, int var2);

    public static native int c_SparkMax_SetLimitPolarity(long var0, int var2, int var3);

    public static native int c_SparkMax_GetLimitPolarity(long var0, int var2);

    public static native boolean c_SparkMax_GetLimitSwitch(long var0, int var2);

    public static native int c_SparkMax_EnableLimitSwitch(long var0, int var2, boolean var3);

    public static native boolean c_SparkMax_IsLimitEnabled(long var0, int var2);

    public static native float c_SparkMax_GetAnalogPosition(long var0);

    public static native float c_SparkMax_GetAnalogVelocity(long var0);

    public static native float c_SparkMax_GetAnalogVoltage(long var0);

    public static native int c_SparkMax_SetAnalogPositionConversionFactor(long var0, float var2);

    public static native int c_SparkMax_SetAnalogVelocityConversionFactor(long var0, float var2);

    public static native float c_SparkMax_GetAnalogPositionConversionFactor(long var0);

    public static native float c_SparkMax_GetAnalogVelocityConversionFactor(long var0);

    public static native int c_SparkMax_SetAnalogInverted(long var0, boolean var2);

    public static native boolean c_SparkMax_GetAnalogInverted(long var0);

    public static native int c_SparkMax_SetAnalogAverageDepth(long var0, int var2);

    public static native int c_SparkMax_GetAnalogAverageDepth(long var0);

    public static native int c_SparkMax_SetAnalogMeasurementPeriod(long var0, int var2);

    public static native int c_SparkMax_GetAnalogMeasurementPeriod(long var0);

    public static native int c_SparkMax_SetAnalogMode(long var0, int var2);

    public static native int c_SparkMax_GetAnalogMode(long var0);

    public static native float c_SparkMax_GetEncoderPosition(long var0);

    public static native float c_SparkMax_GetEncoderVelocity(long var0);

    public static native int c_SparkMax_SetPositionConversionFactor(long var0, float var2);

    public static native int c_SparkMax_SetVelocityConversionFactor(long var0, float var2);

    public static native float c_SparkMax_GetPositionConversionFactor(long var0);

    public static native float c_SparkMax_GetVelocityConversionFactor(long var0);

    public static native int c_SparkMax_SetAverageDepth(long var0, int var2);

    public static native int c_SparkMax_GetAverageDepth(long var0);

    public static native int c_SparkMax_SetMeasurementPeriod(long var0, int var2);

    public static native int c_SparkMax_GetMeasurementPeriod(long var0);

    public static native int c_SparkMax_SetCountsPerRevolution(long var0, int var2);

    public static native int c_SparkMax_GetCountsPerRevolution(long var0);

    public static native int c_SparkMax_SetEncoderInverted(long var0, boolean var2);

    public static native boolean c_SparkMax_GetEncoderInverted(long var0);

    public static native int c_SparkMax_SetAltEncoderPosition(long var0, float var2);

    public static native float c_SparkMax_GetAltEncoderPosition(long var0);

    public static native float c_SparkMax_GetAltEncoderVelocity(long var0);

    public static native int c_SparkMax_SetAltEncoderPositionFactor(long var0, float var2);

    public static native int c_SparkMax_SetAltEncoderVelocityFactor(long var0, float var2);

    public static native float c_SparkMax_GetAltEncoderPositionFactor(long var0);

    public static native float c_SparkMax_GetAltEncoderVelocityFactor(long var0);

    public static native int c_SparkMax_SetAltEncoderAverageDepth(long var0, int var2);

    public static native int c_SparkMax_GetAltEncoderAverageDepth(long var0);

    public static native int c_SparkMax_SetAltEncoderMeasurementPeriod(long var0, int var2);

    public static native int c_SparkMax_GetAltEncoderMeasurementPeriod(long var0);

    public static native int c_SparkMax_SetAltEncoderCountsPerRevolution(long var0, int var2);

    public static native int c_SparkMax_GetAltEncoderCountsPerRevolution(long var0);

    public static native int c_SparkMax_SetAltEncoderInverted(long var0, boolean var2);

    public static native boolean c_SparkMax_GetAltEncoderInverted(long var0);

    public static native int c_SparkMax_SetDataPortConfig(long var0, int var2);

    public static native int c_SparkMax_SetP(long var0, int var2, float var3);

    public static native int c_SparkMax_SetI(long var0, int var2, float var3);

    public static native int c_SparkMax_SetD(long var0, int var2, float var3);

    public static native int c_SparkMax_SetDFilter(long var0, int var2, float var3);

    public static native int c_SparkMax_SetFF(long var0, int var2, float var3);

    public static native int c_SparkMax_SetIZone(long var0, int var2, float var3);

    public static native int c_SparkMax_SetOutputRange(long var0, int var2, float var3, float var4);

    public static native float c_SparkMax_GetP(long var0, int var2);

    public static native float c_SparkMax_GetI(long var0, int var2);

    public static native float c_SparkMax_GetD(long var0, int var2);

    public static native float c_SparkMax_GetDFilter(long var0, int var2);

    public static native float c_SparkMax_GetFF(long var0, int var2);

    public static native float c_SparkMax_GetIZone(long var0, int var2);

    public static native float c_SparkMax_GetOutputMin(long var0, int var2);

    public static native float c_SparkMax_GetOutputMax(long var0, int var2);

    public static native int c_SparkMax_SetSmartMotionMaxVelocity(long var0, int var2, float var3);

    public static native int c_SparkMax_SetSmartMotionMaxAccel(long var0, int var2, float var3);

    public static native int c_SparkMax_SetSmartMotionMinOutputVelocity(long var0, int var2, float var3);

    public static native int c_SparkMax_SetSmartMotionAccelStrategy(long var0, int var2, int var3);

    public static native int c_SparkMax_SetSmartMotionAllowedClosedLoopError(long var0, int var2, float var3);

    public static native float c_SparkMax_GetSmartMotionMaxVelocity(long var0, int var2);

    public static native float c_SparkMax_GetSmartMotionMaxAccel(long var0, int var2);

    public static native float c_SparkMax_GetSmartMotionMinOutputVelocity(long var0, int var2);

    public static native int c_SparkMax_GetSmartMotionAccelStrategy(long var0, int var2);

    public static native float c_SparkMax_GetSmartMotionAllowedClosedLoopError(long var0, int var2);

    public static native int c_SparkMax_SetIMaxAccum(long var0, int var2, float var3);

    public static native float c_SparkMax_GetIMaxAccum(long var0, int var2);

    public static native int c_SparkMax_SetIAccum(long var0, float var2);

    public static native float c_SparkMax_GetIAccum(long var0);

    public static native int c_SparkMax_SetFeedbackDevice(long var0, int var2);

    public static native int c_SparkMax_SetFeedbackDeviceRange(long var0, float var2, float var3);

    public static native int c_SparkMax_GetFeedbackDeviceID(long var0);

    public static native int c_SparkMax_GetAPIMajorRevision();

    public static native int c_SparkMax_GetAPIMinorRevision();

    public static native int c_SparkMax_GetAPIBuildRevision();

    public static native int c_SparkMax_GetAPIVersion();

    public static native int c_SparkMax_GetLastError(long var0);
}

