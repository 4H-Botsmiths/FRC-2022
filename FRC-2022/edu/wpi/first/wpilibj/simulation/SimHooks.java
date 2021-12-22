/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.SimulatorJNI;

public final class SimHooks {
    private SimHooks() {
    }

    public static void setHALRuntimeType(int type) {
        SimulatorJNI.setRuntimeType(type);
    }

    public static void waitForProgramStart() {
        SimulatorJNI.waitForProgramStart();
    }

    public static void setProgramStarted() {
        SimulatorJNI.setProgramStarted();
    }

    public static boolean getProgramStarted() {
        return SimulatorJNI.getProgramStarted();
    }

    public static void restartTiming() {
        SimulatorJNI.restartTiming();
    }

    public static void pauseTiming() {
        SimulatorJNI.pauseTiming();
    }

    public static void resumeTiming() {
        SimulatorJNI.resumeTiming();
    }

    public static boolean isTimingPaused() {
        return SimulatorJNI.isTimingPaused();
    }

    public static void stepTiming(double deltaSeconds) {
        SimulatorJNI.stepTiming((long)(deltaSeconds * 1000000.0));
    }

    public static void stepTimingAsync(double deltaSeconds) {
        SimulatorJNI.stepTimingAsync((long)(deltaSeconds * 1000000.0));
    }
}

