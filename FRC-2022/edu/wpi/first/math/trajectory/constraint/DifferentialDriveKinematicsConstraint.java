/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.trajectory.constraint;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.constraint.TrajectoryConstraint;

public class DifferentialDriveKinematicsConstraint
implements TrajectoryConstraint {
    private final double m_maxSpeedMetersPerSecond;
    private final DifferentialDriveKinematics m_kinematics;

    public DifferentialDriveKinematicsConstraint(DifferentialDriveKinematics kinematics, double maxSpeedMetersPerSecond) {
        this.m_maxSpeedMetersPerSecond = maxSpeedMetersPerSecond;
        this.m_kinematics = kinematics;
    }

    @Override
    public double getMaxVelocityMetersPerSecond(Pose2d poseMeters, double curvatureRadPerMeter, double velocityMetersPerSecond) {
        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(velocityMetersPerSecond, 0.0, velocityMetersPerSecond * curvatureRadPerMeter);
        DifferentialDriveWheelSpeeds wheelSpeeds = this.m_kinematics.toWheelSpeeds(chassisSpeeds);
        wheelSpeeds.normalize(this.m_maxSpeedMetersPerSecond);
        return this.m_kinematics.toChassisSpeeds((DifferentialDriveWheelSpeeds)wheelSpeeds).vxMetersPerSecond;
    }

    @Override
    public TrajectoryConstraint.MinMax getMinMaxAccelerationMetersPerSecondSq(Pose2d poseMeters, double curvatureRadPerMeter, double velocityMetersPerSecond) {
        return new TrajectoryConstraint.MinMax();
    }
}

