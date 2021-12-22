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

public class SingleJointedArmSim
extends LinearSystemSim<N2, N1, N1> {
    private final DCMotor m_gearbox;
    private final double m_gearing;
    private final double m_r;
    private final double m_minAngle;
    private final double m_maxAngle;
    private final double m_armMass;
    private final boolean m_simulateGravity;

    public SingleJointedArmSim(LinearSystem<N2, N1, N1> plant, DCMotor gearbox, double gearing, double armLengthMeters, double minAngleRads, double maxAngleRads, double armMassKg, boolean simulateGravity) {
        this(plant, gearbox, gearing, armLengthMeters, minAngleRads, maxAngleRads, armMassKg, simulateGravity, null);
    }

    public SingleJointedArmSim(LinearSystem<N2, N1, N1> plant, DCMotor gearbox, double gearing, double armLengthMeters, double minAngleRads, double maxAngleRads, double armMassKg, boolean simulateGravity, Matrix<N1, N1> measurementStdDevs) {
        super(plant, measurementStdDevs);
        this.m_gearbox = gearbox;
        this.m_gearing = gearing;
        this.m_r = armLengthMeters;
        this.m_minAngle = minAngleRads;
        this.m_maxAngle = maxAngleRads;
        this.m_armMass = armMassKg;
        this.m_simulateGravity = simulateGravity;
    }

    public SingleJointedArmSim(DCMotor gearbox, double gearing, double jKgMetersSquared, double armLengthMeters, double minAngleRads, double maxAngleRads, double armMassKg, boolean simulateGravity) {
        this(gearbox, gearing, jKgMetersSquared, armLengthMeters, minAngleRads, maxAngleRads, armMassKg, simulateGravity, null);
    }

    public SingleJointedArmSim(DCMotor gearbox, double gearing, double jKgMetersSquared, double armLengthMeters, double minAngleRads, double maxAngleRads, double armMassKg, boolean simulateGravity, Matrix<N1, N1> measurementStdDevs) {
        super(LinearSystemId.createSingleJointedArmSystem(gearbox, jKgMetersSquared, gearing), measurementStdDevs);
        this.m_gearbox = gearbox;
        this.m_gearing = gearing;
        this.m_r = armLengthMeters;
        this.m_minAngle = minAngleRads;
        this.m_maxAngle = maxAngleRads;
        this.m_armMass = armMassKg;
        this.m_simulateGravity = simulateGravity;
    }

    public boolean wouldHitLowerLimit(double currentAngleRads) {
        return currentAngleRads < this.m_minAngle;
    }

    public boolean wouldHitUpperLimit(double currentAngleRads) {
        return currentAngleRads > this.m_maxAngle;
    }

    public boolean hasHitLowerLimit() {
        return this.wouldHitLowerLimit(this.getAngleRads());
    }

    public boolean hasHitUpperLimit() {
        return this.wouldHitUpperLimit(this.getAngleRads());
    }

    public double getAngleRads() {
        return this.m_y.get(0, 0);
    }

    public double getVelocityRadPerSec() {
        return this.m_x.get(1, 0);
    }

    @Override
    public double getCurrentDrawAmps() {
        double motorVelocity = this.m_x.get(1, 0) * this.m_gearing;
        return this.m_gearbox.getCurrent(motorVelocity, this.m_u.get(0, 0)) * Math.signum(this.m_u.get(0, 0));
    }

    public void setInputVoltage(double volts) {
        this.setInput(volts);
    }

    public static double estimateMOI(double lengthMeters, double massKg) {
        return 0.3333333333333333 * massKg * lengthMeters * lengthMeters;
    }

    @Override
    protected Matrix<N2, N1> updateX(Matrix<N2, N1> currentXhat, Matrix<N1, N1> u, double dtSeconds) {
        Matrix<N2, N1> updatedXhat = NumericalIntegration.rkdp((x, u_) -> {
            Matrix xdot = this.m_plant.getA().times(x).plus(this.m_plant.getB().times(u_));
            if (this.m_simulateGravity) {
                xdot = xdot.plus(VecBuilder.fill(0.0, this.m_armMass * this.m_r * -9.8 * 3.0 / (this.m_armMass * this.m_r * this.m_r) * Math.cos(x.get(0, 0))));
            }
            return xdot;
        }, currentXhat, u, dtSeconds);
        if (this.wouldHitLowerLimit(updatedXhat.get(0, 0))) {
            return VecBuilder.fill(this.m_minAngle, 0.0);
        }
        if (this.wouldHitUpperLimit(updatedXhat.get(0, 0))) {
            return VecBuilder.fill(this.m_maxAngle, 0.0);
        }
        return updatedXhat;
    }
}

