/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.NotifierDataJNI;

public final class NotifierSim {
    private NotifierSim() {
    }

    public static long getNextTimeout() {
        return NotifierDataJNI.getNextTimeout();
    }

    public static int getNumNotifiers() {
        return NotifierDataJNI.getNumNotifiers();
    }
}

