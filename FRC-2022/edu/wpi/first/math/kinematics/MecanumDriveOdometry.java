/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.kinematics;

import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.MathUsageId;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.util.WPIUtilJNI;

public class MecanumDriveOdometry {
    private final MecanumDriveKinematics m_kinematics;
    private Pose2d m_poseMeters;
    private double m_prevTimeSeconds = -1.0;
    private Rotation2d m_gyroOffset;
    private Rotation2d m_previousAngle;

    public MecanumDriveOdometry(MecanumDriveKinematics kinematics, Rotation2d gyroAngle, Pose2d initialPoseMeters) {
        this.m_kinematics = kinematics;
        this.m_poseMeters = initialPoseMeters;
        this.m_gyroOffset = this.m_poseMeters.getRotation().minus(gyroAngle);
        this.m_previousAngle = initialPoseMeters.getRotation();
        MathSharedStore.reportUsage(MathUsageId.kOdometry_MecanumDrive, 1);
    }

    public MecanumDriveOdometry(MecanumDriveKinematics kinematics, Rotation2d gyroAngle) {
        this(kinematics, gyroAngle, new Pose2d());
    }

    public void resetPosition(Pose2d poseMeters, Rotation2d gyroAngle) {
        this.m_poseMeters = poseMeters;
        this.m_previousAngle = poseMeters.getRotation();
        this.m_gyroOffset = this.m_poseMeters.getRotation().minus(gyroAngle);
    }

    public Pose2d getPoseMeters() {
        return this.m_poseMeters;
    }

    public Pose2d updateWithTime(double currentTimeSeconds, Rotation2d gyroAngle, MecanumDriveWheelSpeeds wheelSpeeds) {
        double period = this.m_prevTimeSeconds >= 0.0 ? currentTimeSeconds - this.m_prevTimeSeconds : 0.0;
        this.m_prevTimeSeconds = currentTimeSeconds;
        Rotation2d angle = gyroAngle.plus(this.m_gyroOffset);
        ChassisSpeeds chassisState = this.m_kinematics.toChassisSpeeds(wheelSpeeds);
        Pose2d newPose = this.m_poseMeters.exp(new Twist2d(chassisState.vxMetersPerSecond * period, chassisState.vyMetersPerSecond * period, angle.minus(this.m_previousAngle).getRadians()));
        this.m_previousAngle = angle;
        this.m_poseMeters = new Pose2d(newPose.getTranslation(), angle);
        return this.m_poseMeters;
    }

    public Pose2d update(Rotation2d gyroAngle, MecanumDriveWheelSpeeds wheelSpeeds) {
        return this.updateWithTime((double)WPIUtilJNI.now() * 1.0E-6, gyroAngle, wheelSpeeds);
    }
}

