/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.simulation.SimDeviceSim;

public class AnalogEncoderSim {
    private final SimDouble m_simPosition;

    public AnalogEncoderSim(AnalogEncoder encoder) {
        SimDeviceSim wrappedSimDevice = new SimDeviceSim("AnalogEncoder[" + encoder.getChannel() + "]");
        this.m_simPosition = wrappedSimDevice.getDouble("Position");
    }

    public void setPosition(Rotation2d angle) {
        this.setTurns(angle.getDegrees() / 360.0);
    }

    public void setTurns(double turns) {
        this.m_simPosition.set(turns);
    }

    public double getTurns() {
        return this.m_simPosition.get();
    }

    public Rotation2d getPosition() {
        return Rotation2d.fromDegrees(this.getTurns() * 360.0);
    }
}

