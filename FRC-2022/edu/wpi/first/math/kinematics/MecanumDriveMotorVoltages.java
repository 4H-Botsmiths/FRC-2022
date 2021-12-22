/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.kinematics;

public class MecanumDriveMotorVoltages {
    public double frontLeftVoltage;
    public double frontRightVoltage;
    public double rearLeftVoltage;
    public double rearRightVoltage;

    public MecanumDriveMotorVoltages() {
    }

    public MecanumDriveMotorVoltages(double frontLeftVoltage, double frontRightVoltage, double rearLeftVoltage, double rearRightVoltage) {
        this.frontLeftVoltage = frontLeftVoltage;
        this.frontRightVoltage = frontRightVoltage;
        this.rearLeftVoltage = rearLeftVoltage;
        this.rearRightVoltage = rearRightVoltage;
    }

    public String toString() {
        return String.format("MecanumDriveMotorVoltages(Front Left: %.2f V, Front Right: %.2f V, Rear Left: %.2f V, Rear Right: %.2f V)", this.frontLeftVoltage, this.frontRightVoltage, this.rearLeftVoltage, this.rearRightVoltage);
    }
}

