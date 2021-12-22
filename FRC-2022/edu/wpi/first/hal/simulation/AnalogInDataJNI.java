/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class AnalogInDataJNI
extends JNIWrapper {
    public static native int registerInitializedCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelInitializedCallback(int var0, int var1);

    public static native boolean getInitialized(int var0);

    public static native void setInitialized(int var0, boolean var1);

    public static native int registerAverageBitsCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelAverageBitsCallback(int var0, int var1);

    public static native int getAverageBits(int var0);

    public static native void setAverageBits(int var0, int var1);

    public static native int registerOversampleBitsCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelOversampleBitsCallback(int var0, int var1);

    public static native int getOversampleBits(int var0);

    public static native void setOversampleBits(int var0, int var1);

    public static native int registerVoltageCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelVoltageCallback(int var0, int var1);

    public static native double getVoltage(int var0);

    public static native void setVoltage(int var0, double var1);

    public static native int registerAccumulatorInitializedCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelAccumulatorInitializedCallback(int var0, int var1);

    public static native boolean getAccumulatorInitialized(int var0);

    public static native void setAccumulatorInitialized(int var0, boolean var1);

    public static native int registerAccumulatorValueCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelAccumulatorValueCallback(int var0, int var1);

    public static native long getAccumulatorValue(int var0);

    public static native void setAccumulatorValue(int var0, long var1);

    public static native int registerAccumulatorCountCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelAccumulatorCountCallback(int var0, int var1);

    public static native long getAccumulatorCount(int var0);

    public static native void setAccumulatorCount(int var0, long var1);

    public static native int registerAccumulatorCenterCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelAccumulatorCenterCallback(int var0, int var1);

    public static native int getAccumulatorCenter(int var0);

    public static native void setAccumulatorCenter(int var0, int var1);

    public static native int registerAccumulatorDeadbandCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelAccumulatorDeadbandCallback(int var0, int var1);

    public static native int getAccumulatorDeadband(int var0);

    public static native void setAccumulatorDeadband(int var0, int var1);

    public static native void resetData(int var0);
}

