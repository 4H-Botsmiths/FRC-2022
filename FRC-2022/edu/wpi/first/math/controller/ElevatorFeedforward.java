/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.controller;

public class ElevatorFeedforward {
    public final double ks;
    public final double kg;
    public final double kv;
    public final double ka;

    public ElevatorFeedforward(double ks, double kg, double kv, double ka) {
        this.ks = ks;
        this.kg = kg;
        this.kv = kv;
        this.ka = ka;
    }

    public ElevatorFeedforward(double ks, double kg, double kv) {
        this(ks, kg, kv, 0.0);
    }

    public double calculate(double velocity, double acceleration) {
        return this.ks * Math.signum(velocity) + this.kg + this.kv * velocity + this.ka * acceleration;
    }

    public double calculate(double velocity) {
        return this.calculate(velocity, 0.0);
    }

    public double maxAchievableVelocity(double maxVoltage, double acceleration) {
        return (maxVoltage - this.ks - this.kg - acceleration * this.ka) / this.kv;
    }

    public double minAchievableVelocity(double maxVoltage, double acceleration) {
        return (-maxVoltage + this.ks - this.kg - acceleration * this.ka) / this.kv;
    }

    public double maxAchievableAcceleration(double maxVoltage, double velocity) {
        return (maxVoltage - this.ks * Math.signum(velocity) - this.kg - velocity * this.kv) / this.ka;
    }

    public double minAchievableAcceleration(double maxVoltage, double velocity) {
        return this.maxAchievableAcceleration(-maxVoltage, velocity);
    }
}

