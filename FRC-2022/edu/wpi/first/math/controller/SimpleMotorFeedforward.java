/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.controller;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.controller.LinearPlantInversionFeedforward;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.LinearSystemId;

public class SimpleMotorFeedforward {
    public final double ks;
    public final double kv;
    public final double ka;

    public SimpleMotorFeedforward(double ks, double kv, double ka) {
        this.ks = ks;
        this.kv = kv;
        this.ka = ka;
    }

    public SimpleMotorFeedforward(double ks, double kv) {
        this(ks, kv, 0.0);
    }

    public double calculate(double velocity, double acceleration) {
        return this.ks * Math.signum(velocity) + this.kv * velocity + this.ka * acceleration;
    }

    public double calculate(double currentVelocity, double nextVelocity, double dtSeconds) {
        LinearSystem<N1, N1, N1> plant = LinearSystemId.identifyVelocitySystem(this.kv, this.ka);
        LinearPlantInversionFeedforward<N1, N1, N1> feedforward = new LinearPlantInversionFeedforward<N1, N1, N1>(plant, dtSeconds);
        Matrix<N1, N1> r = Matrix.mat(Nat.N1(), Nat.N1()).fill(currentVelocity);
        Matrix<N1, N1> nextR = Matrix.mat(Nat.N1(), Nat.N1()).fill(nextVelocity);
        return this.ks * Math.signum(currentVelocity) + feedforward.calculate(r, nextR).get(0, 0);
    }

    public double calculate(double velocity) {
        return this.calculate(velocity, 0.0);
    }

    public double maxAchievableVelocity(double maxVoltage, double acceleration) {
        return (maxVoltage - this.ks - acceleration * this.ka) / this.kv;
    }

    public double minAchievableVelocity(double maxVoltage, double acceleration) {
        return (-maxVoltage + this.ks - acceleration * this.ka) / this.kv;
    }

    public double maxAchievableAcceleration(double maxVoltage, double velocity) {
        return (maxVoltage - this.ks * Math.signum(velocity) - velocity * this.kv) / this.ka;
    }

    public double minAchievableAcceleration(double maxVoltage, double velocity) {
        return this.maxAchievableAcceleration(-maxVoltage, velocity);
    }
}

