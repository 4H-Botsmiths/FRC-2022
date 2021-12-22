/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;
import java.nio.IntBuffer;

public class CounterJNI
extends JNIWrapper {
    public static final int TWO_PULSE = 0;
    public static final int SEMI_PERIOD = 1;
    public static final int PULSE_LENGTH = 2;
    public static final int EXTERNAL_DIRECTION = 3;

    public static native int initializeCounter(int var0, IntBuffer var1);

    public static native void freeCounter(int var0);

    public static native void setCounterAverageSize(int var0, int var1);

    public static native void setCounterUpSource(int var0, int var1, int var2);

    public static native void setCounterUpSourceEdge(int var0, boolean var1, boolean var2);

    public static native void clearCounterUpSource(int var0);

    public static native void setCounterDownSource(int var0, int var1, int var2);

    public static native void setCounterDownSourceEdge(int var0, boolean var1, boolean var2);

    public static native void clearCounterDownSource(int var0);

    public static native void setCounterUpDownMode(int var0);

    public static native void setCounterExternalDirectionMode(int var0);

    public static native void setCounterSemiPeriodMode(int var0, boolean var1);

    public static native void setCounterPulseLengthMode(int var0, double var1);

    public static native int getCounterSamplesToAverage(int var0);

    public static native void setCounterSamplesToAverage(int var0, int var1);

    public static native void resetCounter(int var0);

    public static native int getCounter(int var0);

    public static native double getCounterPeriod(int var0);

    public static native void setCounterMaxPeriod(int var0, double var1);

    public static native void setCounterUpdateWhenEmpty(int var0, boolean var1);

    public static native boolean getCounterStopped(int var0);

    public static native boolean getCounterDirection(int var0);

    public static native void setCounterReverseDirection(int var0, boolean var1);
}

