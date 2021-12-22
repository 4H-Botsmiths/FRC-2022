/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.simulation.SimDeviceSim;

public class DutyCycleEncoderSim {
    private final SimDouble m_simPosition;
    private final SimDouble m_simDistancePerRotation;

    public DutyCycleEncoderSim(DutyCycleEncoder encoder) {
        SimDeviceSim wrappedSimDevice = new SimDeviceSim("DutyCycle:DutyCycleEncoder[" + encoder.getSourceChannel() + "]");
        this.m_simPosition = wrappedSimDevice.getDouble("position");
        this.m_simDistancePerRotation = wrappedSimDevice.getDouble("distance_per_rot");
    }

    public void set(double turns) {
        this.m_simPosition.set(turns);
    }

    public void setDistance(double distance) {
        this.m_simPosition.set(distance / this.m_simDistancePerRotation.get());
    }
}

