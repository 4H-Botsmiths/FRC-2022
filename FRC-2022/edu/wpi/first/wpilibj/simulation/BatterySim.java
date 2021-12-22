/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

public final class BatterySim {
    private BatterySim() {
    }

    public static double calculateLoadedBatteryVoltage(double nominalVoltage, double resistanceOhms, double ... currents) {
        double retval = nominalVoltage;
        for (double current : currents) {
            retval -= current * resistanceOhms;
        }
        return retval;
    }

    public static double calculateDefaultBatteryLoadedVoltage(double ... currents) {
        return BatterySim.calculateLoadedBatteryVoltage(12.0, 0.02, currents);
    }
}

