/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.kinematics;

import edu.wpi.first.math.geometry.Rotation2d;

public class ChassisSpeeds {
    public double vxMetersPerSecond;
    public double vyMetersPerSecond;
    public double omegaRadiansPerSecond;

    public ChassisSpeeds() {
    }

    public ChassisSpeeds(double vxMetersPerSecond, double vyMetersPerSecond, double omegaRadiansPerSecond) {
        this.vxMetersPerSecond = vxMetersPerSecond;
        this.vyMetersPerSecond = vyMetersPerSecond;
        this.omegaRadiansPerSecond = omegaRadiansPerSecond;
    }

    public static ChassisSpeeds fromFieldRelativeSpeeds(double vxMetersPerSecond, double vyMetersPerSecond, double omegaRadiansPerSecond, Rotation2d robotAngle) {
        return new ChassisSpeeds(vxMetersPerSecond * robotAngle.getCos() + vyMetersPerSecond * robotAngle.getSin(), -vxMetersPerSecond * robotAngle.getSin() + vyMetersPerSecond * robotAngle.getCos(), omegaRadiansPerSecond);
    }

    public String toString() {
        return String.format("ChassisSpeeds(Vx: %.2f m/s, Vy: %.2f m/s, Omega: %.2f rad/s)", this.vxMetersPerSecond, this.vyMetersPerSecond, this.omegaRadiansPerSecond);
    }
}

