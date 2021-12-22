/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.kinematics;

import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.MathUsageId;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Twist2d;

public class DifferentialDriveOdometry {
    private Pose2d m_poseMeters;
    private Rotation2d m_gyroOffset;
    private Rotation2d m_previousAngle;
    private double m_prevLeftDistance;
    private double m_prevRightDistance;

    public DifferentialDriveOdometry(Rotation2d gyroAngle, Pose2d initialPoseMeters) {
        this.m_poseMeters = initialPoseMeters;
        this.m_gyroOffset = this.m_poseMeters.getRotation().minus(gyroAngle);
        this.m_previousAngle = initialPoseMeters.getRotation();
        MathSharedStore.reportUsage(MathUsageId.kOdometry_DifferentialDrive, 1);
    }

    public DifferentialDriveOdometry(Rotation2d gyroAngle) {
        this(gyroAngle, new Pose2d());
    }

    public void resetPosition(Pose2d poseMeters, Rotation2d gyroAngle) {
        this.m_poseMeters = poseMeters;
        this.m_previousAngle = poseMeters.getRotation();
        this.m_gyroOffset = this.m_poseMeters.getRotation().minus(gyroAngle);
        this.m_prevLeftDistance = 0.0;
        this.m_prevRightDistance = 0.0;
    }

    public Pose2d getPoseMeters() {
        return this.m_poseMeters;
    }

    public Pose2d update(Rotation2d gyroAngle, double leftDistanceMeters, double rightDistanceMeters) {
        double deltaLeftDistance = leftDistanceMeters - this.m_prevLeftDistance;
        double deltaRightDistance = rightDistanceMeters - this.m_prevRightDistance;
        this.m_prevLeftDistance = leftDistanceMeters;
        this.m_prevRightDistance = rightDistanceMeters;
        double averageDeltaDistance = (deltaLeftDistance + deltaRightDistance) / 2.0;
        Rotation2d angle = gyroAngle.plus(this.m_gyroOffset);
        Pose2d newPose = this.m_poseMeters.exp(new Twist2d(averageDeltaDistance, 0.0, angle.minus(this.m_previousAngle).getRadians()));
        this.m_previousAngle = angle;
        this.m_poseMeters = new Pose2d(newPose.getTranslation(), angle);
        return this.m_poseMeters;
    }
}

