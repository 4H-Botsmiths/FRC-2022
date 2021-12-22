/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.LinearSystemSim;

public class FlywheelSim
extends LinearSystemSim<N1, N1, N1> {
    private final DCMotor m_gearbox;
    private final double m_gearing;

    public FlywheelSim(LinearSystem<N1, N1, N1> plant, DCMotor gearbox, double gearing) {
        super(plant);
        this.m_gearbox = gearbox;
        this.m_gearing = gearing;
    }

    public FlywheelSim(LinearSystem<N1, N1, N1> plant, DCMotor gearbox, double gearing, Matrix<N1, N1> measurementStdDevs) {
        super(plant, measurementStdDevs);
        this.m_gearbox = gearbox;
        this.m_gearing = gearing;
    }

    public FlywheelSim(DCMotor gearbox, double gearing, double jKgMetersSquared) {
        super(LinearSystemId.createFlywheelSystem(gearbox, jKgMetersSquared, gearing));
        this.m_gearbox = gearbox;
        this.m_gearing = gearing;
    }

    public FlywheelSim(DCMotor gearbox, double gearing, double jKgMetersSquared, Matrix<N1, N1> measurementStdDevs) {
        super(LinearSystemId.createFlywheelSystem(gearbox, jKgMetersSquared, gearing), measurementStdDevs);
        this.m_gearbox = gearbox;
        this.m_gearing = gearing;
    }

    public double getAngularVelocityRadPerSec() {
        return this.getOutput(0);
    }

    public double getAngularVelocityRPM() {
        return Units.radiansPerSecondToRotationsPerMinute(this.getOutput(0));
    }

    @Override
    public double getCurrentDrawAmps() {
        return this.m_gearbox.getCurrent(this.getAngularVelocityRadPerSec() * this.m_gearing, this.m_u.get(0, 0)) * Math.signum(this.m_u.get(0, 0));
    }

    public void setInputVoltage(double volts) {
        this.setInput(volts);
    }
}

