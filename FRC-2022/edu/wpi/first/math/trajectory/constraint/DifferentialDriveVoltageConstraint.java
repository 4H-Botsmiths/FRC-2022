/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.trajectory.constraint;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.constraint.TrajectoryConstraint;
import edu.wpi.first.util.ErrorMessages;

public class DifferentialDriveVoltageConstraint
implements TrajectoryConstraint {
    private final SimpleMotorFeedforward m_feedforward;
    private final DifferentialDriveKinematics m_kinematics;
    private final double m_maxVoltage;

    public DifferentialDriveVoltageConstraint(SimpleMotorFeedforward feedforward, DifferentialDriveKinematics kinematics, double maxVoltage) {
        this.m_feedforward = ErrorMessages.requireNonNullParam(feedforward, "feedforward", "DifferentialDriveVoltageConstraint");
        this.m_kinematics = ErrorMessages.requireNonNullParam(kinematics, "kinematics", "DifferentialDriveVoltageConstraint");
        this.m_maxVoltage = maxVoltage;
    }

    @Override
    public double getMaxVelocityMetersPerSecond(Pose2d poseMeters, double curvatureRadPerMeter, double velocityMetersPerSecond) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public TrajectoryConstraint.MinMax getMinMaxAccelerationMetersPerSecondSq(Pose2d poseMeters, double curvatureRadPerMeter, double velocityMetersPerSecond) {
        double minChassisAcceleration;
        double maxChassisAcceleration;
        DifferentialDriveWheelSpeeds wheelSpeeds = this.m_kinematics.toWheelSpeeds(new ChassisSpeeds(velocityMetersPerSecond, 0.0, velocityMetersPerSecond * curvatureRadPerMeter));
        double maxWheelSpeed = Math.max(wheelSpeeds.leftMetersPerSecond, wheelSpeeds.rightMetersPerSecond);
        double minWheelSpeed = Math.min(wheelSpeeds.leftMetersPerSecond, wheelSpeeds.rightMetersPerSecond);
        double maxWheelAcceleration = this.m_feedforward.maxAchievableAcceleration(this.m_maxVoltage, maxWheelSpeed);
        double minWheelAcceleration = this.m_feedforward.minAchievableAcceleration(this.m_maxVoltage, minWheelSpeed);
        if (velocityMetersPerSecond == 0.0) {
            maxChassisAcceleration = maxWheelAcceleration / (1.0 + this.m_kinematics.trackWidthMeters * Math.abs(curvatureRadPerMeter) / 2.0);
            minChassisAcceleration = minWheelAcceleration / (1.0 + this.m_kinematics.trackWidthMeters * Math.abs(curvatureRadPerMeter) / 2.0);
        } else {
            maxChassisAcceleration = maxWheelAcceleration / (1.0 + this.m_kinematics.trackWidthMeters * Math.abs(curvatureRadPerMeter) * Math.signum(velocityMetersPerSecond) / 2.0);
            minChassisAcceleration = minWheelAcceleration / (1.0 - this.m_kinematics.trackWidthMeters * Math.abs(curvatureRadPerMeter) * Math.signum(velocityMetersPerSecond) / 2.0);
        }
        if (this.m_kinematics.trackWidthMeters / 2.0 > 1.0 / Math.abs(curvatureRadPerMeter)) {
            if (velocityMetersPerSecond > 0.0) {
                minChassisAcceleration = -minChassisAcceleration;
            } else if (velocityMetersPerSecond < 0.0) {
                maxChassisAcceleration = -maxChassisAcceleration;
            }
        }
        return new TrajectoryConstraint.MinMax(minChassisAcceleration, maxChassisAcceleration);
    }
}

