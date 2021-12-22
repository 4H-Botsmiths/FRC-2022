/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.simulation.NotifyCallback;

public class EncoderDataJNI
extends JNIWrapper {
    public static native int registerInitializedCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelInitializedCallback(int var0, int var1);

    public static native boolean getInitialized(int var0);

    public static native void setInitialized(int var0, boolean var1);

    public static native int registerCountCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelCountCallback(int var0, int var1);

    public static native int getCount(int var0);

    public static native void setCount(int var0, int var1);

    public static native int registerPeriodCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelPeriodCallback(int var0, int var1);

    public static native double getPeriod(int var0);

    public static native void setPeriod(int var0, double var1);

    public static native int registerResetCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelResetCallback(int var0, int var1);

    public static native boolean getReset(int var0);

    public static native void setReset(int var0, boolean var1);

    public static native int registerMaxPeriodCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelMaxPeriodCallback(int var0, int var1);

    public static native double getMaxPeriod(int var0);

    public static native void setMaxPeriod(int var0, double var1);

    public static native int registerDirectionCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelDirectionCallback(int var0, int var1);

    public static native boolean getDirection(int var0);

    public static native void setDirection(int var0, boolean var1);

    public static native int registerReverseDirectionCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelReverseDirectionCallback(int var0, int var1);

    public static native boolean getReverseDirection(int var0);

    public static native void setReverseDirection(int var0, boolean var1);

    public static native int registerSamplesToAverageCallback(int var0, NotifyCallback var1, boolean var2);

    public static native void cancelSamplesToAverageCallback(int var0, int var1);

    public static native int getSamplesToAverage(int var0);

    public static native void setSamplesToAverage(int var0, int var1);

    public static native void setDistance(int var0, double var1);

    public static native double getDistance(int var0);

    public static native void setRate(int var0, double var1);

    public static native double getRate(int var0);

    public static native void resetData(int var0);

    public static native int findForChannel(int var0);
}

