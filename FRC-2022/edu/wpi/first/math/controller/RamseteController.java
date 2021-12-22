/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.controller;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;

public class RamseteController {
    private final double m_b;
    private final double m_zeta;
    private Pose2d m_poseError = new Pose2d();
    private Pose2d m_poseTolerance = new Pose2d();
    private boolean m_enabled = true;

    public RamseteController(double b, double zeta) {
        this.m_b = b;
        this.m_zeta = zeta;
    }

    public RamseteController() {
        this(2.0, 0.7);
    }

    public boolean atReference() {
        Translation2d eTranslate = this.m_poseError.getTranslation();
        Rotation2d eRotate = this.m_poseError.getRotation();
        Translation2d tolTranslate = this.m_poseTolerance.getTranslation();
        Rotation2d tolRotate = this.m_poseTolerance.getRotation();
        return Math.abs(eTranslate.getX()) < tolTranslate.getX() && Math.abs(eTranslate.getY()) < tolTranslate.getY() && Math.abs(eRotate.getRadians()) < tolRotate.getRadians();
    }

    public void setTolerance(Pose2d poseTolerance) {
        this.m_poseTolerance = poseTolerance;
    }

    public ChassisSpeeds calculate(Pose2d currentPose, Pose2d poseRef, double linearVelocityRefMeters, double angularVelocityRefRadiansPerSecond) {
        if (!this.m_enabled) {
            return new ChassisSpeeds(linearVelocityRefMeters, 0.0, angularVelocityRefRadiansPerSecond);
        }
        this.m_poseError = poseRef.relativeTo(currentPose);
        double eX = this.m_poseError.getX();
        double eY = this.m_poseError.getY();
        double eTheta = this.m_poseError.getRotation().getRadians();
        double vRef = linearVelocityRefMeters;
        double omegaRef = angularVelocityRefRadiansPerSecond;
        double k = 2.0 * this.m_zeta * Math.sqrt(Math.pow(omegaRef, 2.0) + this.m_b * Math.pow(vRef, 2.0));
        return new ChassisSpeeds(vRef * this.m_poseError.getRotation().getCos() + k * eX, 0.0, omegaRef + k * eTheta + this.m_b * vRef * RamseteController.sinc(eTheta) * eY);
    }

    public ChassisSpeeds calculate(Pose2d currentPose, Trajectory.State desiredState) {
        return this.calculate(currentPose, desiredState.poseMeters, desiredState.velocityMetersPerSecond, desiredState.velocityMetersPerSecond * desiredState.curvatureRadPerMeter);
    }

    public void setEnabled(boolean enabled) {
        this.m_enabled = enabled;
    }

    private static double sinc(double x) {
        if (Math.abs(x) < 1.0E-9) {
            return 1.0 - 0.16666666666666666 * x * x;
        }
        return Math.sin(x) / x;
    }
}

