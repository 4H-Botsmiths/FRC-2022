/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;

public class DigitalGlitchFilterJNI
extends JNIWrapper {
    public static native void setFilterSelect(int var0, int var1);

    public static native int getFilterSelect(int var0);

    public static native void setFilterPeriod(int var0, int var1);

    public static native int getFilterPeriod(int var0);
}

