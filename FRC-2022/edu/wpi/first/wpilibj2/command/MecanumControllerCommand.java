/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveMotorVoltages;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MecanumControllerCommand
extends CommandBase {
    private final Timer m_timer = new Timer();
    private final boolean m_usePID;
    private final Trajectory m_trajectory;
    private final Supplier<Pose2d> m_pose;
    private final SimpleMotorFeedforward m_feedforward;
    private final MecanumDriveKinematics m_kinematics;
    private final HolonomicDriveController m_controller;
    private final Supplier<Rotation2d> m_desiredRotation;
    private final double m_maxWheelVelocityMetersPerSecond;
    private final PIDController m_frontLeftController;
    private final PIDController m_rearLeftController;
    private final PIDController m_frontRightController;
    private final PIDController m_rearRightController;
    private final Supplier<MecanumDriveWheelSpeeds> m_currentWheelSpeeds;
    private final Consumer<MecanumDriveMotorVoltages> m_outputDriveVoltages;
    private final Consumer<MecanumDriveWheelSpeeds> m_outputWheelSpeeds;
    private MecanumDriveWheelSpeeds m_prevSpeeds;
    private double m_prevTime;

    public MecanumControllerCommand(Trajectory trajectory, Supplier<Pose2d> pose, SimpleMotorFeedforward feedforward, MecanumDriveKinematics kinematics, PIDController xController, PIDController yController, ProfiledPIDController thetaController, Supplier<Rotation2d> desiredRotation, double maxWheelVelocityMetersPerSecond, PIDController frontLeftController, PIDController rearLeftController, PIDController frontRightController, PIDController rearRightController, Supplier<MecanumDriveWheelSpeeds> currentWheelSpeeds, Consumer<MecanumDriveMotorVoltages> outputDriveVoltages, Subsystem ... requirements) {
        this.m_trajectory = ErrorMessages.requireNonNullParam(trajectory, "trajectory", "MecanumControllerCommand");
        this.m_pose = ErrorMessages.requireNonNullParam(pose, "pose", "MecanumControllerCommand");
        this.m_feedforward = ErrorMessages.requireNonNullParam(feedforward, "feedforward", "MecanumControllerCommand");
        this.m_kinematics = ErrorMessages.requireNonNullParam(kinematics, "kinematics", "MecanumControllerCommand");
        this.m_controller = new HolonomicDriveController(ErrorMessages.requireNonNullParam(xController, "xController", "SwerveControllerCommand"), ErrorMessages.requireNonNullParam(yController, "xController", "SwerveControllerCommand"), ErrorMessages.requireNonNullParam(thetaController, "thetaController", "SwerveControllerCommand"));
        this.m_desiredRotation = ErrorMessages.requireNonNullParam(desiredRotation, "desiredRotation", "MecanumControllerCommand");
        this.m_maxWheelVelocityMetersPerSecond = ErrorMessages.requireNonNullParam(maxWheelVelocityMetersPerSecond, "maxWheelVelocityMetersPerSecond", "MecanumControllerCommand");
        this.m_frontLeftController = ErrorMessages.requireNonNullParam(frontLeftController, "frontLeftController", "MecanumControllerCommand");
        this.m_rearLeftController = ErrorMessages.requireNonNullParam(rearLeftController, "rearLeftController", "MecanumControllerCommand");
        this.m_frontRightController = ErrorMessages.requireNonNullParam(frontRightController, "frontRightController", "MecanumControllerCommand");
        this.m_rearRightController = ErrorMessages.requireNonNullParam(rearRightController, "rearRightController", "MecanumControllerCommand");
        this.m_currentWheelSpeeds = ErrorMessages.requireNonNullParam(currentWheelSpeeds, "currentWheelSpeeds", "MecanumControllerCommand");
        this.m_outputDriveVoltages = ErrorMessages.requireNonNullParam(outputDriveVoltages, "outputDriveVoltages", "MecanumControllerCommand");
        this.m_outputWheelSpeeds = null;
        this.m_usePID = true;
        this.addRequirements(requirements);
    }

    public MecanumControllerCommand(Trajectory trajectory, Supplier<Pose2d> pose, SimpleMotorFeedforward feedforward, MecanumDriveKinematics kinematics, PIDController xController, PIDController yController, ProfiledPIDController thetaController, double maxWheelVelocityMetersPerSecond, PIDController frontLeftController, PIDController rearLeftController, PIDController frontRightController, PIDController rearRightController, Supplier<MecanumDriveWheelSpeeds> currentWheelSpeeds, Consumer<MecanumDriveMotorVoltages> outputDriveVoltages, Subsystem ... requirements) {
        this(trajectory, pose, feedforward, kinematics, xController, yController, thetaController, () -> trajectory.getStates().get((int)(trajectory.getStates().size() - 1)).poseMeters.getRotation(), maxWheelVelocityMetersPerSecond, frontLeftController, rearLeftController, frontRightController, rearRightController, currentWheelSpeeds, outputDriveVoltages, requirements);
    }

    public MecanumControllerCommand(Trajectory trajectory, Supplier<Pose2d> pose, MecanumDriveKinematics kinematics, PIDController xController, PIDController yController, ProfiledPIDController thetaController, Supplier<Rotation2d> desiredRotation, double maxWheelVelocityMetersPerSecond, Consumer<MecanumDriveWheelSpeeds> outputWheelSpeeds, Subsystem ... requirements) {
        this.m_trajectory = ErrorMessages.requireNonNullParam(trajectory, "trajectory", "MecanumControllerCommand");
        this.m_pose = ErrorMessages.requireNonNullParam(pose, "pose", "MecanumControllerCommand");
        this.m_feedforward = new SimpleMotorFeedforward(0.0, 0.0, 0.0);
        this.m_kinematics = ErrorMessages.requireNonNullParam(kinematics, "kinematics", "MecanumControllerCommand");
        this.m_controller = new HolonomicDriveController(ErrorMessages.requireNonNullParam(xController, "xController", "SwerveControllerCommand"), ErrorMessages.requireNonNullParam(yController, "xController", "SwerveControllerCommand"), ErrorMessages.requireNonNullParam(thetaController, "thetaController", "SwerveControllerCommand"));
        this.m_desiredRotation = ErrorMessages.requireNonNullParam(desiredRotation, "desiredRotation", "MecanumControllerCommand");
        this.m_maxWheelVelocityMetersPerSecond = ErrorMessages.requireNonNullParam(maxWheelVelocityMetersPerSecond, "maxWheelVelocityMetersPerSecond", "MecanumControllerCommand");
        this.m_frontLeftController = null;
        this.m_rearLeftController = null;
        this.m_frontRightController = null;
        this.m_rearRightController = null;
        this.m_currentWheelSpeeds = null;
        this.m_outputWheelSpeeds = ErrorMessages.requireNonNullParam(outputWheelSpeeds, "outputWheelSpeeds", "MecanumControllerCommand");
        this.m_outputDriveVoltages = null;
        this.m_usePID = false;
        this.addRequirements(requirements);
    }

    public MecanumControllerCommand(Trajectory trajectory, Supplier<Pose2d> pose, MecanumDriveKinematics kinematics, PIDController xController, PIDController yController, ProfiledPIDController thetaController, double maxWheelVelocityMetersPerSecond, Consumer<MecanumDriveWheelSpeeds> outputWheelSpeeds, Subsystem ... requirements) {
        this(trajectory, pose, kinematics, xController, yController, thetaController, () -> trajectory.getStates().get((int)(trajectory.getStates().size() - 1)).poseMeters.getRotation(), maxWheelVelocityMetersPerSecond, outputWheelSpeeds, requirements);
    }

    @Override
    public void initialize() {
        Trajectory.State initialState = this.m_trajectory.sample(0.0);
        double initialXVelocity = initialState.velocityMetersPerSecond * initialState.poseMeters.getRotation().getCos();
        double initialYVelocity = initialState.velocityMetersPerSecond * initialState.poseMeters.getRotation().getSin();
        this.m_prevSpeeds = this.m_kinematics.toWheelSpeeds(new ChassisSpeeds(initialXVelocity, initialYVelocity, 0.0));
        this.m_timer.reset();
        this.m_timer.start();
    }

    @Override
    public void execute() {
        double curTime = this.m_timer.get();
        double dt = curTime - this.m_prevTime;
        Trajectory.State desiredState = this.m_trajectory.sample(curTime);
        ChassisSpeeds targetChassisSpeeds = this.m_controller.calculate(this.m_pose.get(), desiredState, this.m_desiredRotation.get());
        MecanumDriveWheelSpeeds targetWheelSpeeds = this.m_kinematics.toWheelSpeeds(targetChassisSpeeds);
        targetWheelSpeeds.normalize(this.m_maxWheelVelocityMetersPerSecond);
        double frontLeftSpeedSetpoint = targetWheelSpeeds.frontLeftMetersPerSecond;
        double rearLeftSpeedSetpoint = targetWheelSpeeds.rearLeftMetersPerSecond;
        double frontRightSpeedSetpoint = targetWheelSpeeds.frontRightMetersPerSecond;
        double rearRightSpeedSetpoint = targetWheelSpeeds.rearRightMetersPerSecond;
        if (this.m_usePID) {
            double frontLeftFeedforward = this.m_feedforward.calculate(frontLeftSpeedSetpoint, (frontLeftSpeedSetpoint - this.m_prevSpeeds.frontLeftMetersPerSecond) / dt);
            double rearLeftFeedforward = this.m_feedforward.calculate(rearLeftSpeedSetpoint, (rearLeftSpeedSetpoint - this.m_prevSpeeds.rearLeftMetersPerSecond) / dt);
            double frontRightFeedforward = this.m_feedforward.calculate(frontRightSpeedSetpoint, (frontRightSpeedSetpoint - this.m_prevSpeeds.frontRightMetersPerSecond) / dt);
            double rearRightFeedforward = this.m_feedforward.calculate(rearRightSpeedSetpoint, (rearRightSpeedSetpoint - this.m_prevSpeeds.rearRightMetersPerSecond) / dt);
            double frontLeftOutput = frontLeftFeedforward + this.m_frontLeftController.calculate(this.m_currentWheelSpeeds.get().frontLeftMetersPerSecond, frontLeftSpeedSetpoint);
            double rearLeftOutput = rearLeftFeedforward + this.m_rearLeftController.calculate(this.m_currentWheelSpeeds.get().rearLeftMetersPerSecond, rearLeftSpeedSetpoint);
            double frontRightOutput = frontRightFeedforward + this.m_frontRightController.calculate(this.m_currentWheelSpeeds.get().frontRightMetersPerSecond, frontRightSpeedSetpoint);
            double rearRightOutput = rearRightFeedforward + this.m_rearRightController.calculate(this.m_currentWheelSpeeds.get().rearRightMetersPerSecond, rearRightSpeedSetpoint);
            this.m_outputDriveVoltages.accept(new MecanumDriveMotorVoltages(frontLeftOutput, frontRightOutput, rearLeftOutput, rearRightOutput));
        } else {
            this.m_outputWheelSpeeds.accept(new MecanumDriveWheelSpeeds(frontLeftSpeedSetpoint, frontRightSpeedSetpoint, rearLeftSpeedSetpoint, rearRightSpeedSetpoint));
        }
        this.m_prevTime = curTime;
        this.m_prevSpeeds = targetWheelSpeeds;
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

