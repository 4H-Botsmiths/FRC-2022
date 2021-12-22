/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;

public class EncoderJNI
extends JNIWrapper {
    public static native int initializeEncoder(int var0, int var1, int var2, int var3, boolean var4, int var5);

    public static native void freeEncoder(int var0);

    public static native void setEncoderSimDevice(int var0, int var1);

    public static native int getEncoder(int var0);

    public static native int getEncoderRaw(int var0);

    public static native int getEncodingScaleFactor(int var0);

    public static native void resetEncoder(int var0);

    public static native double getEncoderPeriod(int var0);

    public static native void setEncoderMaxPeriod(int var0, double var1);

    public static native boolean getEncoderStopped(int var0);

    public static native boolean getEncoderDirection(int var0);

    public static native double getEncoderDistance(int var0);

    public static native double getEncoderRate(int var0);

    public static native void setEncoderMinRate(int var0, double var1);

    public static native void setEncoderDistancePerPulse(int var0, double var1);

    public static native void setEncoderReverseDirection(int var0, boolean var1);

    public static native void setEncoderSamplesToAverage(int var0, int var1);

    public static native int getEncoderSamplesToAverage(int var0);

    public static native void setEncoderIndexSource(int var0, int var1, int var2, int var3);

    public static native int getEncoderFPGAIndex(int var0);

    public static native int getEncoderEncodingScale(int var0);

    public static native double getEncoderDecodingScaleFactor(int var0);

    public static native double getEncoderDistancePerPulse(int var0);

    public static native int getEncoderEncodingType(int var0);
}

