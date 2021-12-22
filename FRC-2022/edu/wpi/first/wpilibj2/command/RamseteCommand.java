/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class RamseteCommand
extends CommandBase {
    private final Timer m_timer = new Timer();
    private final boolean m_usePID;
    private final Trajectory m_trajectory;
    private final Supplier<Pose2d> m_pose;
    private final RamseteController m_follower;
    private final SimpleMotorFeedforward m_feedforward;
    private final DifferentialDriveKinematics m_kinematics;
    private final Supplier<DifferentialDriveWheelSpeeds> m_speeds;
    private final PIDController m_leftController;
    private final PIDController m_rightController;
    private final BiConsumer<Double, Double> m_output;
    private DifferentialDriveWheelSpeeds m_prevSpeeds;
    private double m_prevTime;

    public RamseteCommand(Trajectory trajectory, Supplier<Pose2d> pose, RamseteController controller, SimpleMotorFeedforward feedforward, DifferentialDriveKinematics kinematics, Supplier<DifferentialDriveWheelSpeeds> wheelSpeeds, PIDController leftController, PIDController rightController, BiConsumer<Double, Double> outputVolts, Subsystem ... requirements) {
        this.m_trajectory = ErrorMessages.requireNonNullParam(trajectory, "trajectory", "RamseteCommand");
        this.m_pose = ErrorMessages.requireNonNullParam(pose, "pose", "RamseteCommand");
        this.m_follower = ErrorMessages.requireNonNullParam(controller, "controller", "RamseteCommand");
        this.m_feedforward = feedforward;
        this.m_kinematics = ErrorMessages.requireNonNullParam(kinematics, "kinematics", "RamseteCommand");
        this.m_speeds = ErrorMessages.requireNonNullParam(wheelSpeeds, "wheelSpeeds", "RamseteCommand");
        this.m_leftController = ErrorMessages.requireNonNullParam(leftController, "leftController", "RamseteCommand");
        this.m_rightController = ErrorMessages.requireNonNullParam(rightController, "rightController", "RamseteCommand");
        this.m_output = ErrorMessages.requireNonNullParam(outputVolts, "outputVolts", "RamseteCommand");
        this.m_usePID = true;
        this.addRequirements(requirements);
    }

    public RamseteCommand(Trajectory trajectory, Supplier<Pose2d> pose, RamseteController follower, DifferentialDriveKinematics kinematics, BiConsumer<Double, Double> outputMetersPerSecond, Subsystem ... requirements) {
        this.m_trajectory = ErrorMessages.requireNonNullParam(trajectory, "trajectory", "RamseteCommand");
        this.m_pose = ErrorMessages.requireNonNullParam(pose, "pose", "RamseteCommand");
        this.m_follower = ErrorMessages.requireNonNullParam(follower, "follower", "RamseteCommand");
        this.m_kinematics = ErrorMessages.requireNonNullParam(kinematics, "kinematics", "RamseteCommand");
        this.m_output = ErrorMessages.requireNonNullParam(outputMetersPerSecond, "output", "RamseteCommand");
        this.m_feedforward = null;
        this.m_speeds = null;
        this.m_leftController = null;
        this.m_rightController = null;
        this.m_usePID = false;
        this.addRequirements(requirements);
    }

    @Override
    public void initialize() {
        this.m_prevTime = -1.0;
        Trajectory.State initialState = this.m_trajectory.sample(0.0);
        this.m_prevSpeeds = this.m_kinematics.toWheelSpeeds(new ChassisSpeeds(initialState.velocityMetersPerSecond, 0.0, initialState.curvatureRadPerMeter * initialState.velocityMetersPerSecond));
        this.m_timer.reset();
        this.m_timer.start();
        if (this.m_usePID) {
            this.m_leftController.reset();
            this.m_rightController.reset();
        }
    }

    @Override
    public void execute() {
        double rightOutput;
        double leftOutput;
        double curTime = this.m_timer.get();
        double dt = curTime - this.m_prevTime;
        if (this.m_prevTime < 0.0) {
            this.m_output.accept(0.0, 0.0);
            this.m_prevTime = curTime;
            return;
        }
        DifferentialDriveWheelSpeeds targetWheelSpeeds = this.m_kinematics.toWheelSpeeds(this.m_follower.calculate(this.m_pose.get(), this.m_trajectory.sample(curTime)));
        double leftSpeedSetpoint = targetWheelSpeeds.leftMetersPerSecond;
        double rightSpeedSetpoint = targetWheelSpeeds.rightMetersPerSecond;
        if (this.m_usePID) {
            double leftFeedforward = this.m_feedforward.calculate(leftSpeedSetpoint, (leftSpeedSetpoint - this.m_prevSpeeds.leftMetersPerSecond) / dt);
            double rightFeedforward = this.m_feedforward.calculate(rightSpeedSetpoint, (rightSpeedSetpoint - this.m_prevSpeeds.rightMetersPerSecond) / dt);
            leftOutput = leftFeedforward + this.m_leftController.calculate(this.m_speeds.get().leftMetersPerSecond, leftSpeedSetpoint);
            rightOutput = rightFeedforward + this.m_rightController.calculate(this.m_speeds.get().rightMetersPerSecond, rightSpeedSetpoint);
        } else {
            leftOutput = leftSpeedSetpoint;
            rightOutput = rightSpeedSetpoint;
        }
        this.m_output.accept(leftOutput, rightOutput);
        this.m_prevSpeeds = targetWheelSpeeds;
        this.m_prevTime = curTime;
    }

    @Override
    public void end(boolean interrupted) {
        this.m_timer.stop();
        if (interrupted) {
            this.m_output.accept(0.0, 0.0);
        }
    }

    @Override
    public boolean isFinished() {
        return this.m_timer.hasElapsed(this.m_trajectory.getTotalTimeSeconds());
    }
}

