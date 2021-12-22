/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.controller;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;

public class HolonomicDriveController {
    private Pose2d m_poseError = new Pose2d();
    private Rotation2d m_rotationError = new Rotation2d();
    private Pose2d m_poseTolerance = new Pose2d();
    private boolean m_enabled = true;
    private final PIDController m_xController;
    private final PIDController m_yController;
    private final ProfiledPIDController m_thetaController;
    private boolean m_firstRun = true;

    public HolonomicDriveController(PIDController xController, PIDController yController, ProfiledPIDController thetaController) {
        this.m_xController = xController;
        this.m_yController = yController;
        this.m_thetaController = thetaController;
    }

    public boolean atReference() {
        Translation2d eTranslate = this.m_poseError.getTranslation();
        Rotation2d eRotate = this.m_rotationError;
        Translation2d tolTranslate = this.m_poseTolerance.getTranslation();
        Rotation2d tolRotate = this.m_poseTolerance.getRotation();
        return Math.abs(eTranslate.getX()) < tolTranslate.getX() && Math.abs(eTranslate.getY()) < tolTranslate.getY() && Math.abs(eRotate.getRadians()) < tolRotate.getRadians();
    }

    public void setTolerance(Pose2d tolerance) {
        this.m_poseTolerance = tolerance;
    }

    public ChassisSpeeds calculate(Pose2d currentPose, Pose2d poseRef, double linearVelocityRefMeters, Rotation2d angleRef) {
        if (this.m_firstRun) {
            this.m_thetaController.reset(currentPose.getRotation().getRadians());
            this.m_firstRun = false;
        }
        double xFF = linearVelocityRefMeters * poseRef.getRotation().getCos();
        double yFF = linearVelocityRefMeters * poseRef.getRotation().getSin();
        double thetaFF = this.m_thetaController.calculate(currentPose.getRotation().getRadians(), angleRef.getRadians());
        this.m_poseError = poseRef.relativeTo(currentPose);
        this.m_rotationError = angleRef.minus(currentPose.getRotation());
        if (!this.m_enabled) {
            return ChassisSpeeds.fromFieldRelativeSpeeds(xFF, yFF, thetaFF, currentPose.getRotation());
        }
        double xFeedback = this.m_xController.calculate(currentPose.getX(), poseRef.getX());
        double yFeedback = this.m_yController.calculate(currentPose.getY(), poseRef.getY());
        return ChassisSpeeds.fromFieldRelativeSpeeds(xFF + xFeedback, yFF + yFeedback, thetaFF, currentPose.getRotation());
    }

    public ChassisSpeeds calculate(Pose2d currentPose, Trajectory.State desiredState, Rotation2d angleRef) {
        return this.calculate(currentPose, desiredState.poseMeters, desiredState.velocityMetersPerSecond, angleRef);
    }

    public void setEnabled(boolean enabled) {
        this.m_enabled = enabled;
    }
}

