/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.NumericalIntegration;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.LinearSystemSim;

public class ElevatorSim
extends LinearSystemSim<N2, N1, N1> {
    private final DCMotor m_gearbox;
    private final double m_gearing;
    private final double m_drumRadius;
    private final double m_minHeight;
    private final double m_maxHeight;

    public ElevatorSim(LinearSystem<N2, N1, N1> plant, DCMotor gearbox, double gearing, double drumRadiusMeters, double minHeightMeters, double maxHeightMeters) {
        this(plant, gearbox, gearing, drumRadiusMeters, minHeightMeters, maxHeightMeters, null);
    }

    public ElevatorSim(LinearSystem<N2, N1, N1> plant, DCMotor gearbox, double gearing, double drumRadiusMeters, double minHeightMeters, double maxHeightMeters, Matrix<N1, N1> measurementStdDevs) {
        super(plant, measurementStdDevs);
        this.m_gearbox = gearbox;
        this.m_gearing = gearing;
        this.m_drumRadius = drumRadiusMeters;
        this.m_minHeight = minHeightMeters;
        this.m_maxHeight = maxHeightMeters;
    }

    public ElevatorSim(DCMotor gearbox, double gearing, double carriageMassKg, double drumRadiusMeters, double minHeightMeters, double maxHeightMeters) {
        this(gearbox, gearing, carriageMassKg, drumRadiusMeters, minHeightMeters, maxHeightMeters, null);
    }

    public ElevatorSim(DCMotor gearbox, double gearing, double carriageMassKg, double drumRadiusMeters, double minHeightMeters, double maxHeightMeters, Matrix<N1, N1> measurementStdDevs) {
        super(LinearSystemId.createElevatorSystem(gearbox, carriageMassKg, drumRadiusMeters, gearing), measurementStdDevs);
        this.m_gearbox = gearbox;
        this.m_gearing = gearing;
        this.m_drumRadius = drumRadiusMeters;
        this.m_minHeight = minHeightMeters;
        this.m_maxHeight = maxHeightMeters;
    }

    public boolean wouldHitLowerLimit(double elevatorHeightMeters) {
        return elevatorHeightMeters < this.m_minHeight;
    }

    public boolean wouldHitUpperLimit(double elevatorHeightMeters) {
        return elevatorHeightMeters > this.m_maxHeight;
    }

    public boolean hasHitLowerLimit() {
        return this.wouldHitLowerLimit(this.getPositionMeters());
    }

    public boolean hasHitUpperLimit() {
        return this.wouldHitUpperLimit(this.getPositionMeters());
    }

    public double getPositionMeters() {
        return this.getOutput(0);
    }

    public double getVelocityMetersPerSecond() {
        return this.m_x.get(1, 0);
    }

    @Override
    public double getCurrentDrawAmps() {
        double motorVelocityRadPerSec = this.getVelocityMetersPerSecond() / this.m_drumRadius * this.m_gearing;
        double appliedVoltage = this.m_u.get(0, 0);
        return this.m_gearbox.getCurrent(motorVelocityRadPerSec, appliedVoltage) * Math.signum(appliedVoltage);
    }

    public void setInputVoltage(double volts) {
        this.setInput(volts);
    }

    @Override
    protected Matrix<N2, N1> updateX(Matrix<N2, N1> currentXhat, Matrix<N1, N1> u, double dtSeconds) {
        Matrix<N2, N1> updatedXhat = NumericalIntegration.rkdp((x, u_) -> this.m_plant.getA().times(x).plus(this.m_plant.getB().times(u_)).plus(VecBuilder.fill(0.0, -9.8)), currentXhat, u, dtSeconds);
        if (this.wouldHitLowerLimit(updatedXhat.get(0, 0))) {
            return VecBuilder.fill(this.m_minHeight, 0.0);
        }
        if (this.wouldHitUpperLimit(updatedXhat.get(0, 0))) {
            return VecBuilder.fill(this.m_maxHeight, 0.0);
        }
        return updatedXhat;
    }
}

