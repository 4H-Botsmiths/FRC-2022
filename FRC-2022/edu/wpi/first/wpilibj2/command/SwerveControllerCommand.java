/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SwerveControllerCommand
extends CommandBase {
    private final Timer m_timer = new Timer();
    private final Trajectory m_trajectory;
    private final Supplier<Pose2d> m_pose;
    private final SwerveDriveKinematics m_kinematics;
    private final HolonomicDriveController m_controller;
    private final Consumer<SwerveModuleState[]> m_outputModuleStates;
    private final Supplier<Rotation2d> m_desiredRotation;

    public SwerveControllerCommand(Trajectory trajectory, Supplier<Pose2d> pose, SwerveDriveKinematics kinematics, PIDController xController, PIDController yController, ProfiledPIDController thetaController, Supplier<Rotation2d> desiredRotation, Consumer<SwerveModuleState[]> outputModuleStates, Subsystem ... requirements) {
        this.m_trajectory = ErrorMessages.requireNonNullParam(trajectory, "trajectory", "SwerveControllerCommand");
        this.m_pose = ErrorMessages.requireNonNullParam(pose, "pose", "SwerveControllerCommand");
        this.m_kinematics = ErrorMessages.requireNonNullParam(kinematics, "kinematics", "SwerveControllerCommand");
        this.m_controller = new HolonomicDriveController(ErrorMessages.requireNonNullParam(xController, "xController", "SwerveControllerCommand"), ErrorMessages.requireNonNullParam(yController, "xController", "SwerveControllerCommand"), ErrorMessages.requireNonNullParam(thetaController, "thetaController", "SwerveControllerCommand"));
        this.m_outputModuleStates = ErrorMessages.requireNonNullParam(outputModuleStates, "frontLeftOutput", "SwerveControllerCommand");
        this.m_desiredRotation = ErrorMessages.requireNonNullParam(desiredRotation, "desiredRotation", "SwerveControllerCommand");
        this.addRequirements(requirements);
    }

    public SwerveControllerCommand(Trajectory trajectory, Supplier<Pose2d> pose, SwerveDriveKinematics kinematics, PIDController xController, PIDController yController, ProfiledPIDController thetaController, Consumer<SwerveModuleState[]> outputModuleStates, Subsystem ... requirements) {
        this(trajectory, pose, kinematics, xController, yController, thetaController, () -> trajectory.getStates().get((int)(trajectory.getStates().size() - 1)).poseMeters.getRotation(), outputModuleStates, requirements);
    }

    @Override
    public void initialize() {
        this.m_timer.reset();
        this.m_timer.start();
    }

    @Override
    public void execute() {
        double curTime = this.m_timer.get();
        Trajectory.State desiredState = this.m_trajectory.sample(curTime);
        ChassisSpeeds targetChassisSpeeds = this.m_controller.calculate(this.m_pose.get(), desiredState, this.m_desiredRotation.get());
        SwerveModuleState[] targetModuleStates = this.m_kinematics.toSwerveModuleStates(targetChassisSpeeds);
        this.m_outputModuleStates.accept(targetModuleStates);
    }

    @Override
    public void end(boolean interrupted) {
        this.m_timer.stop();
    }

    @Override
    public boolean isFinished() {
        return this.m_timer.hasElapsed(this.m_trajectory.getTotalTimeSeconds());
    }
}

