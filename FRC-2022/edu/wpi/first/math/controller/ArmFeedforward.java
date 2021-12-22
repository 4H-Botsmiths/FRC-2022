/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.controller;

public class ArmFeedforward {
    public final double ks;
    public final double kcos;
    public final double kv;
    public final double ka;

    public ArmFeedforward(double ks, double kcos, double kv, double ka) {
        this.ks = ks;
        this.kcos = kcos;
        this.kv = kv;
        this.ka = ka;
    }

    public ArmFeedforward(double ks, double kcos, double kv) {
        this(ks, kcos, kv, 0.0);
    }

    public double calculate(double positionRadians, double velocityRadPerSec, double accelRadPerSecSquared) {
        return this.ks * Math.signum(velocityRadPerSec) + this.kcos * Math.cos(positionRadians) + this.kv * velocityRadPerSec + this.ka * accelRadPerSecSquared;
    }

    public double calculate(double positionRadians, double velocity) {
        return this.calculate(positionRadians, velocity, 0.0);
    }

    public double maxAchievableVelocity(double maxVoltage, double angle, double acceleration) {
        return (maxVoltage - this.ks - Math.cos(angle) * this.kcos - acceleration * this.ka) / this.kv;
    }

    public double minAchievableVelocity(double maxVoltage, double angle, double acceleration) {
        return (-maxVoltage + this.ks - Math.cos(angle) * this.kcos - acceleration * this.ka) / this.kv;
    }

    public double maxAchievableAcceleration(double maxVoltage, double angle, double velocity) {
        return (maxVoltage - this.ks * Math.signum(velocity) - Math.cos(angle) * this.kcos - velocity * this.kv) / this.ka;
    }

    public double minAchievableAcceleration(double maxVoltage, double angle, double velocity) {
        return this.maxAchievableAcceleration(-maxVoltage, angle, velocity);
    }
}

