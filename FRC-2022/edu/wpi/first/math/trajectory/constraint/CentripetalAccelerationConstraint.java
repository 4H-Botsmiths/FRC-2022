/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.trajectory.constraint;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.constraint.TrajectoryConstraint;

public class CentripetalAccelerationConstraint
implements TrajectoryConstraint {
    private final double m_maxCentripetalAccelerationMetersPerSecondSq;

    public CentripetalAccelerationConstraint(double maxCentripetalAccelerationMetersPerSecondSq) {
        this.m_maxCentripetalAccelerationMetersPerSecondSq = maxCentripetalAccelerationMetersPerSecondSq;
    }

    @Override
    public double getMaxVelocityMetersPerSecond(Pose2d poseMeters, double curvatureRadPerMeter, double velocityMetersPerSecond) {
        return Math.sqrt(this.m_maxCentripetalAccelerationMetersPerSecondSq / Math.abs(curvatureRadPerMeter));
    }

    @Override
    public TrajectoryConstraint.MinMax getMinMaxAccelerationMetersPerSecondSq(Pose2d poseMeters, double curvatureRadPerMeter, double velocityMetersPerSecond) {
        return new TrajectoryConstraint.MinMax();
    }
}

