/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.trajectory.constraint;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.constraint.TrajectoryConstraint;

public class RectangularRegionConstraint
implements TrajectoryConstraint {
    private final Translation2d m_bottomLeftPoint;
    private final Translation2d m_topRightPoint;
    private final TrajectoryConstraint m_constraint;

    public RectangularRegionConstraint(Translation2d bottomLeftPoint, Translation2d topRightPoint, TrajectoryConstraint constraint) {
        this.m_bottomLeftPoint = bottomLeftPoint;
        this.m_topRightPoint = topRightPoint;
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
        return robotPose.getX() >= this.m_bottomLeftPoint.getX() && robotPose.getX() <= this.m_topRightPoint.getX() && robotPose.getY() >= this.m_bottomLeftPoint.getY() && robotPose.getY() <= this.m_topRightPoint.getY();
    }
}

