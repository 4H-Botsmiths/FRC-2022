/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.AccumulatorResult;
import edu.wpi.first.hal.JNIWrapper;

public class AnalogJNI
extends JNIWrapper {
    public static native int initializeAnalogInputPort(int var0);

    public static native void freeAnalogInputPort(int var0);

    public static native int initializeAnalogOutputPort(int var0);

    public static native void freeAnalogOutputPort(int var0);

    public static native boolean checkAnalogModule(byte var0);

    public static native boolean checkAnalogInputChannel(int var0);

    public static native boolean checkAnalogOutputChannel(int var0);

    public static native void setAnalogInputSimDevice(int var0, int var1);

    public static native void setAnalogOutput(int var0, double var1);

    public static native double getAnalogOutput(int var0);

    public static native void setAnalogSampleRate(double var0);

    public static native double getAnalogSampleRate();

    public static native void setAnalogAverageBits(int var0, int var1);

    public static native int getAnalogAverageBits(int var0);

    public static native void setAnalogOversampleBits(int var0, int var1);

    public static native int getAnalogOversampleBits(int var0);

    public static native short getAnalogValue(int var0);

    public static native int getAnalogAverageValue(int var0);

    public static native int getAnalogVoltsToValue(int var0, double var1);

    public static native double getAnalogValueToVolts(int var0, int var1);

    public static native double getAnalogVoltage(int var0);

    public static native double getAnalogAverageVoltage(int var0);

    public static native int getAnalogLSBWeight(int var0);

    public static native int getAnalogOffset(int var0);

    public static native boolean isAccumulatorChannel(int var0);

    public static native void initAccumulator(int var0);

    public static native void resetAccumulator(int var0);

    public static native void setAccumulatorCenter(int var0, int var1);

    public static native void setAccumulatorDeadband(int var0, int var1);

    public static native long getAccumulatorValue(int var0);

    public static native int getAccumulatorCount(int var0);

    public static native void getAccumulatorOutput(int var0, AccumulatorResult var1);

    public static native int initializeAnalogTrigger(int var0);

    public static native int initializeAnalogTriggerDutyCycle(int var0);

    public static native void cleanAnalogTrigger(int var0);

    public static native void setAnalogTriggerLimitsRaw(int var0, int var1, int var2);

    public static native void setAnalogTriggerLimitsDutyCycle(int var0, double var1, double var3);

    public static native void setAnalogTriggerLimitsVoltage(int var0, double var1, double var3);

    public static native void setAnalogTriggerAveraged(int var0, boolean var1);

    public static native void setAnalogTriggerFiltered(int var0, boolean var1);

    public static native boolean getAnalogTriggerInWindow(int var0);

    public static native boolean getAnalogTriggerTriggerState(int var0);

    public static native boolean getAnalogTriggerOutput(int var0, int var1);

    public static native int getAnalogTriggerFPGAIndex(int var0);

    public static interface AnalogTriggerType {
        public static final int kInWindow = 0;
        public static final int kState = 1;
        public static final int kRisingPulse = 2;
        public static final int kFallingPulse = 3;
    }
}

