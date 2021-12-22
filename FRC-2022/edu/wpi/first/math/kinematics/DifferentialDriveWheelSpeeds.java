/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.kinematics;

public class DifferentialDriveWheelSpeeds {
    public double leftMetersPerSecond;
    public double rightMetersPerSecond;

    public DifferentialDriveWheelSpeeds() {
    }

    public DifferentialDriveWheelSpeeds(double leftMetersPerSecond, double rightMetersPerSecond) {
        this.leftMetersPerSecond = leftMetersPerSecond;
        this.rightMetersPerSecond = rightMetersPerSecond;
    }

    public void normalize(double attainableMaxSpeedMetersPerSecond) {
        double realMaxSpeed = Math.max(Math.abs(this.leftMetersPerSecond), Math.abs(this.rightMetersPerSecond));
        if (realMaxSpeed > attainableMaxSpeedMetersPerSecond) {
            this.leftMetersPerSecond = this.leftMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
            this.rightMetersPerSecond = this.rightMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
        }
    }

    public String toString() {
        return String.format("DifferentialDriveWheelSpeeds(Left: %.2f m/s, Right: %.2f m/s)", this.leftMetersPerSecond, this.rightMetersPerSecond);
    }
}

