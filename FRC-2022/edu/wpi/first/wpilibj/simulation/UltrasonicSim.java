/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.simulation.SimDeviceSim;

public class UltrasonicSim {
    private final SimBoolean m_simRangeValid;
    private final SimDouble m_simRange;

    public UltrasonicSim(Ultrasonic ultrasonic) {
        SimDeviceSim simDevice = new SimDeviceSim("Ultrasonic", ultrasonic.getEchoChannel());
        this.m_simRangeValid = simDevice.getBoolean("Range Valid");
        this.m_simRange = simDevice.getDouble("Range (in)");
    }

    public void setRangeValid(boolean valid) {
        this.m_simRangeValid.set(valid);
    }

    public void setRangeInches(double inches) {
        this.m_simRange.set(inches);
    }

    public void setRangeMeters(double meters) {
        this.m_simRange.set(Units.metersToInches(meters));
    }
}

