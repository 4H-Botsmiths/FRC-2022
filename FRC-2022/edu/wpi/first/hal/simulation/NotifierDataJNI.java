/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.JNIWrapper;

public class NotifierDataJNI
extends JNIWrapper {
    public static native long getNextTimeout();

    public static native int getNumNotifiers();
}

