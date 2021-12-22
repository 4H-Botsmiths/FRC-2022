/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.trajectory.constraint;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.constraint.TrajectoryConstraint;

public class EllipticalRegionConstraint
implements TrajectoryConstraint {
    private final Translation2d m_center;
    private final Translation2d m_radii;
    private final TrajectoryConstraint m_constraint;

    public EllipticalRegionConstraint(Translation2d center, double xWidth, double yWidth, Rotation2d rotation, TrajectoryConstraint constraint) {
        this.m_center = center;
        this.m_radii = new Translation2d(xWidth / 2.0, yWidth / 2.0).rotateBy(rotation);
        this.m_constraint = constraint;
    }

    @Override
    public double getMaxVelocityMetersPerSecond(Pose2d poseMeters, double curvatureRadPerMeter, double velocityMetersPerSecond) {
        if (this.isPoseInRegion(poseMeters)) {
            return this.m_constraint.getMaxVelocityMetersPerSecond(poseMeters, curvatureRadPerMeter, velocityMetersPerSecond);
        }
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public TrajectoryConstraint.MinMax getMinMaxAccelerationMetersPerSecondSq(Pose2d poseMeters, double curvatureRadPerMeter, double velocityMetersPerSecond) {
        if (this.isPoseInRegion(poseMeters)) {
            return this.m_constraint.getMinMaxAccelerationMetersPerSecondSq(poseMeters, curvatureRadPerMeter, velocityMetersPerSecond);
        }
        return new TrajectoryConstraint.MinMax();
    }

    public boolean isPoseInRegion(Pose2d robotPose) {
        return Math.pow(robotPose.getX() - this.m_center.getX(), 2.0) * Math.pow(this.m_radii.getY(), 2.0) + Math.pow(robotPose.getY() - this.m_center.getY(), 2.0) * Math.pow(this.m_radii.getX(), 2.0) <= Math.pow(this.m_radii.getX(), 2.0) * Math.pow(this.m_radii.getY(), 2.0);
    }
}

