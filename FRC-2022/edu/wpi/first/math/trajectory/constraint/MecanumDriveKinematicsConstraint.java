/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.trajectory.constraint;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.constraint.TrajectoryConstraint;

public class MecanumDriveKinematicsConstraint
implements TrajectoryConstraint {
    private final double m_maxSpeedMetersPerSecond;
    private final MecanumDriveKinematics m_kinematics;

    public MecanumDriveKinematicsConstraint(MecanumDriveKinematics kinematics, double maxSpeedMetersPerSecond) {
        this.m_maxSpeedMetersPerSecond = maxSpeedMetersPerSecond;
        this.m_kinematics = kinematics;
    }

    @Override
    public double getMaxVelocityMetersPerSecond(Pose2d poseMeters, double curvatureRadPerMeter, double velocityMetersPerSecond) {
        double xdVelocity = velocityMetersPerSecond * poseMeters.getRotation().getCos();
        double ydVelocity = velocityMetersPerSecond * poseMeters.getRotation().getSin();
        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(xdVelocity, ydVelocity, velocityMetersPerSecond * curvatureRadPerMeter);
        MecanumDriveWheelSpeeds wheelSpeeds = this.m_kinematics.toWheelSpeeds(chassisSpeeds);
        wheelSpeeds.normalize(this.m_maxSpeedMetersPerSecond);
        ChassisSpeeds normSpeeds = this.m_kinematics.toChassisSpeeds(wheelSpeeds);
        return Math.hypot(normSpeeds.vxMetersPerSecond, normSpeeds.vyMetersPerSecond);
    }

    @Override
    public TrajectoryConstraint.MinMax getMinMaxAccelerationMetersPerSecondSq(Pose2d poseMeters, double curvatureRadPerMeter, double velocityMetersPerSecond) {
        return new TrajectoryConstraint.MinMax();
    }
}

