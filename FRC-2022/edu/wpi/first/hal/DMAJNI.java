/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.DMAJNISample;
import edu.wpi.first.hal.JNIWrapper;

public class DMAJNI
extends JNIWrapper {
    public static native int initialize();

    public static native void free(int var0);

    public static native void setPause(int var0, boolean var1);

    public static native void setTimedTrigger(int var0, double var1);

    public static native void setTimedTriggerCycles(int var0, int var1);

    public static native void addEncoder(int var0, int var1);

    public static native void addEncoderPeriod(int var0, int var1);

    public static native void addCounter(int var0, int var1);

    public static native void addCounterPeriod(int var0, int var1);

    public static native void addDigitalSource(int var0, int var1);

    public static native void addDutyCycle(int var0, int var1);

    public static native void addAnalogInput(int var0, int var1);

    public static native void addAveragedAnalogInput(int var0, int var1);

    public static native void addAnalogAccumulator(int var0, int var1);

    public static native int setExternalTrigger(int var0, int var1, int var2, boolean var3, boolean var4);

    public static native void clearSensors(int var0);

    public static native void clearExternalTriggers(int var0);

    public static native void startDMA(int var0, int var1);

    public static native void stopDMA(int var0);

    public static native long readDMA(int var0, double var1, int[] var3, int[] var4);

    public static native DMAJNISample.BaseStore getSensorReadData(int var0);
}

