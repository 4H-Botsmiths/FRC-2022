/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.trajectory.constraint;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.constraint.TrajectoryConstraint;

public class MaxVelocityConstraint
implements TrajectoryConstraint {
    private final double m_maxVelocity;

    public MaxVelocityConstraint(double maxVelocityMetersPerSecond) {
        this.m_maxVelocity = maxVelocityMetersPerSecond;
    }

    @Override
    public double getMaxVelocityMetersPerSecond(Pose2d poseMeters, double curvatureRadPerMeter, double velocityMetersPerSecond) {
        return this.m_maxVelocity;
    }

    @Override
    public TrajectoryConstraint.MinMax getMinMaxAccelerationMetersPerSecondSq(Pose2d poseMeters, double curvatureRadPerMeter, double velocityMetersPerSecond) {
        return new TrajectoryConstraint.MinMax();
    }
}

