/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.system.plant;

import edu.wpi.first.math.util.Units;

public class DCMotor {
    public final double nominalVoltageVolts;
    public final double stallTorqueNewtonMeters;
    public final double stallCurrentAmps;
    public final double freeCurrentAmps;
    public final double freeSpeedRadPerSec;
    public final double rOhms;
    public final double KvRadPerSecPerVolt;
    public final double KtNMPerAmp;

    public DCMotor(double nominalVoltageVolts, double stallTorqueNewtonMeters, double stallCurrentAmps, double freeCurrentAmps, double freeSpeedRadPerSec, int numMotors) {
        this.nominalVoltageVolts = nominalVoltageVolts;
        this.stallTorqueNewtonMeters = stallTorqueNewtonMeters * (double)numMotors;
        this.stallCurrentAmps = stallCurrentAmps * (double)numMotors;
        this.freeCurrentAmps = freeCurrentAmps * (double)numMotors;
        this.freeSpeedRadPerSec = freeSpeedRadPerSec;
        this.rOhms = nominalVoltageVolts / this.stallCurrentAmps;
        this.KvRadPerSecPerVolt = freeSpeedRadPerSec / (nominalVoltageVolts - this.rOhms * this.freeCurrentAmps);
        this.KtNMPerAmp = this.stallTorqueNewtonMeters / this.stallCurrentAmps;
    }

    public double getCurrent(double speedRadiansPerSec, double voltageInputVolts) {
        return -1.0 / this.KvRadPerSecPerVolt / this.rOhms * speedRadiansPerSec + 1.0 / this.rOhms * voltageInputVolts;
    }

    public static DCMotor getCIM(int numMotors) {
        return new DCMotor(12.0, 2.42, 133.0, 2.7, Units.rotationsPerMinuteToRadiansPerSecond(5310.0), numMotors);
    }

    public static DCMotor getVex775Pro(int numMotors) {
        return new DCMotor(12.0, 0.71, 134.0, 0.7, Units.rotationsPerMinuteToRadiansPerSecond(18730.0), numMotors);
    }

    public static DCMotor getNEO(int numMotors) {
        return new DCMotor(12.0, 2.6, 105.0, 1.8, Units.rotationsPerMinuteToRadiansPerSecond(5676.0), numMotors);
    }

    public static DCMotor getMiniCIM(int numMotors) {
        return new DCMotor(12.0, 1.41, 89.0, 3.0, Units.rotationsPerMinuteToRadiansPerSecond(5840.0), numMotors);
    }

    public static DCMotor getBag(int numMotors) {
        return new DCMotor(12.0, 0.43, 53.0, 1.8, Units.rotationsPerMinuteToRadiansPerSecond(13180.0), numMotors);
    }

    public static DCMotor getAndymarkRs775_125(int numMotors) {
        return new DCMotor(12.0, 0.28, 18.0, 1.6, Units.rotationsPerMinuteToRadiansPerSecond(5800.0), numMotors);
    }

    public static DCMotor getBanebotsRs775(int numMotors) {
        return new DCMotor(12.0, 0.72, 97.0, 2.7, Units.rotationsPerMinuteToRadiansPerSecond(13050.0), numMotors);
    }

    public static DCMotor getAndymark9015(int numMotors) {
        return new DCMotor(12.0, 0.36, 71.0, 3.7, Units.rotationsPerMinuteToRadiansPerSecond(14270.0), numMotors);
    }

    public static DCMotor getBanebotsRs550(int numMotors) {
        return new DCMotor(12.0, 0.38, 84.0, 0.4, Units.rotationsPerMinuteToRadiansPerSecond(19000.0), numMotors);
    }

    public static DCMotor getNeo550(int numMotors) {
        return new DCMotor(12.0, 0.97, 100.0, 1.4, Units.rotationsPerMinuteToRadiansPerSecond(11000.0), numMotors);
    }

    public static DCMotor getFalcon500(int numMotors) {
        return new DCMotor(12.0, 4.69, 257.0, 1.5, Units.rotationsPerMinuteToRadiansPerSecond(6380.0), numMotors);
    }

    public static DCMotor getRomiBuiltIn(int numMotors) {
        return new DCMotor(4.5, 0.1765, 1.25, 0.13, Units.rotationsPerMinuteToRadiansPerSecond(150.0), numMotors);
    }
}

