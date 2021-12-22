/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;

public class ThreadsJNI
extends JNIWrapper {
    public static native int getCurrentThreadPriority();

    public static native boolean getCurrentThreadIsRealTime();

    public static native boolean setCurrentThreadPriority(boolean var0, int var1);
}

