/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.ThreadsJNI;

public final class Threads {
    public static int getCurrentThreadPriority() {
        return ThreadsJNI.getCurrentThreadPriority();
    }

    public static boolean getCurrentThreadIsRealTime() {
        return ThreadsJNI.getCurrentThreadIsRealTime();
    }

    public static boolean setCurrentThreadPriority(boolean realTime, int priority) {
        return ThreadsJNI.setCurrentThreadPriority(realTime, priority);
    }

    private Threads() {
    }
}

