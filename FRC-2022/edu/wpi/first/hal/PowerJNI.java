/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;

public class PowerJNI
extends JNIWrapper {
    public static native double getVinVoltage();

    public static native double getVinCurrent();

    public static native double getUserVoltage6V();

    public static native double getUserCurrent6V();

    public static native boolean getUserActive6V();

    public static native int getUserCurrentFaults6V();

    public static native double getUserVoltage5V();

    public static native double getUserCurrent5V();

    public static native boolean getUserActive5V();

    public static native int getUserCurrentFaults5V();

    public static native double getUserVoltage3V3();

    public static native double getUserCurrent3V3();

    public static native boolean getUserActive3V3();

    public static native int getUserCurrentFaults3V3();

    public static native void setBrownoutVoltage(double var0);

    public static native double getBrownoutVoltage();
}

