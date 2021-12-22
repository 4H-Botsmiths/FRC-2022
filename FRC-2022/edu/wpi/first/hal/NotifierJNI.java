/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;

public class NotifierJNI
extends JNIWrapper {
    public static native int initializeNotifier();

    public static native boolean setHALThreadPriority(boolean var0, int var1);

    public static native void setNotifierName(int var0, String var1);

    public static native void stopNotifier(int var0);

    public static native void cleanNotifier(int var0);

    public static native void updateNotifierAlarm(int var0, long var1);

    public static native void cancelNotifierAlarm(int var0);

    public static native long waitForNotifierAlarm(int var0);
}

