/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.kinematics;

import java.util.stream.DoubleStream;

public class MecanumDriveWheelSpeeds {
    public double frontLeftMetersPerSecond;
    public double frontRightMetersPerSecond;
    public double rearLeftMetersPerSecond;
    public double rearRightMetersPerSecond;

    public MecanumDriveWheelSpeeds() {
    }

    public MecanumDriveWheelSpeeds(double frontLeftMetersPerSecond, double frontRightMetersPerSecond, double rearLeftMetersPerSecond, double rearRightMetersPerSecond) {
        this.frontLeftMetersPerSecond = frontLeftMetersPerSecond;
        this.frontRightMetersPerSecond = frontRightMetersPerSecond;
        this.rearLeftMetersPerSecond = rearLeftMetersPerSecond;
        this.rearRightMetersPerSecond = rearRightMetersPerSecond;
    }

    public void normalize(double attainableMaxSpeedMetersPerSecond) {
        double[] arrd = new double[]{this.frontLeftMetersPerSecond, this.frontRightMetersPerSecond, this.rearLeftMetersPerSecond, this.rearRightMetersPerSecond};
        double realMaxSpeed = DoubleStream.of(arrd).max().getAsDouble();
        if (realMaxSpeed > attainableMaxSpeedMetersPerSecond) {
            this.frontLeftMetersPerSecond = this.frontLeftMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
            this.frontRightMetersPerSecond = this.frontRightMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
            this.rearLeftMetersPerSecond = this.rearLeftMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
            this.rearRightMetersPerSecond = this.rearRightMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
        }
    }

    public String toString() {
        return String.format("MecanumDriveWheelSpeeds(Front Left: %.2f m/s, Front Right: %.2f m/s, Rear Left: %.2f m/s, Rear Right: %.2f m/s)", this.frontLeftMetersPerSecond, this.frontRightMetersPerSecond, this.rearLeftMetersPerSecond, this.rearRightMetersPerSecond);
    }
}

