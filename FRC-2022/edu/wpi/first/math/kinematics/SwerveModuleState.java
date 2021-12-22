/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.kinematics;

import edu.wpi.first.math.geometry.Rotation2d;
import java.util.Objects;

public class SwerveModuleState
implements Comparable<SwerveModuleState> {
    public double speedMetersPerSecond;
    public Rotation2d angle = Rotation2d.fromDegrees(0.0);

    public SwerveModuleState() {
    }

    public SwerveModuleState(double speedMetersPerSecond, Rotation2d angle) {
        this.speedMetersPerSecond = speedMetersPerSecond;
        this.angle = angle;
    }

    public boolean equals(Object obj) {
        if (obj instanceof SwerveModuleState) {
            return Double.compare(this.speedMetersPerSecond, ((SwerveModuleState)obj).speedMetersPerSecond) == 0;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.speedMetersPerSecond);
    }

    @Override
    public int compareTo(SwerveModuleState other) {
        return Double.compare(this.speedMetersPerSecond, other.speedMetersPerSecond);
    }

    public String toString() {
        return String.format("SwerveModuleState(Speed: %.2f m/s, Angle: %s)", this.speedMetersPerSecond, this.angle);
    }

    public static SwerveModuleState optimize(SwerveModuleState desiredState, Rotation2d currentAngle) {
        Rotation2d delta = desiredState.angle.minus(currentAngle);
        if (Math.abs(delta.getDegrees()) > 90.0) {
            return new SwerveModuleState(-desiredState.speedMetersPerSecond, desiredState.angle.rotateBy(Rotation2d.fromDegrees(180.0)));
        }
        return new SwerveModuleState(desiredState.speedMetersPerSecond, desiredState.angle);
    }
}

