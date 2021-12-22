/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.drive;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.drive.Vector2d;
import java.util.Objects;

public class KilloughDrive
extends RobotDriveBase
implements Sendable,
AutoCloseable {
    public static final double kDefaultLeftMotorAngle = 60.0;
    public static final double kDefaultRightMotorAngle = 120.0;
    public static final double kDefaultBackMotorAngle = 270.0;
    private static int instances;
    private SpeedController m_leftMotor;
    private SpeedController m_rightMotor;
    private SpeedController m_backMotor;
    private Vector2d m_leftVec;
    private Vector2d m_rightVec;
    private Vector2d m_backVec;
    private boolean m_reported;

    public KilloughDrive(SpeedController leftMotor, SpeedController rightMotor, SpeedController backMotor) {
        this(leftMotor, rightMotor, backMotor, 60.0, 120.0, 270.0);
    }

    public KilloughDrive(SpeedController leftMotor, SpeedController rightMotor, SpeedController backMotor, double leftMotorAngle, double rightMotorAngle, double backMotorAngle) {
        Objects.requireNonNull(leftMotor, "Left motor cannot be null");
        Objects.requireNonNull(rightMotor, "Right motor cannot be null");
        Objects.requireNonNull(backMotor, "Back motor cannot be null");
        this.m_leftMotor = leftMotor;
        this.m_rightMotor = rightMotor;
        this.m_backMotor = backMotor;
        this.m_leftVec = new Vector2d(Math.cos(leftMotorAngle * (Math.PI / 180)), Math.sin(leftMotorAngle * (Math.PI / 180)));
        this.m_rightVec = new Vector2d(Math.cos(rightMotorAngle * (Math.PI / 180)), Math.sin(rightMotorAngle * (Math.PI / 180)));
        this.m_backVec = new Vector2d(Math.cos(backMotorAngle * (Math.PI / 180)), Math.sin(backMotorAngle * (Math.PI / 180)));
        SendableRegistry.addChild(this, this.m_leftMotor);
        SendableRegistry.addChild(this, this.m_rightMotor);
        SendableRegistry.addChild(this, this.m_backMotor);
        SendableRegistry.addLW((Sendable)this, "KilloughDrive", ++instances);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
    }

    public void driveCartesian(double ySpeed, double xSpeed, double zRotation) {
        this.driveCartesian(ySpeed, xSpeed, zRotation, 0.0);
    }

    public void driveCartesian(double ySpeed, double xSpeed, double zRotation, double gyroAngle) {
        if (!this.m_reported) {
            HAL.report(31, 12, 3);
            this.m_reported = true;
        }
        ySpeed = MathUtil.applyDeadband(ySpeed, this.m_deadband);
        xSpeed = MathUtil.applyDeadband(xSpeed, this.m_deadband);
        WheelSpeeds speeds = this.driveCartesianIK(ySpeed, xSpeed, zRotation, gyroAngle);
        this.m_leftMotor.set(speeds.left * this.m_maxOutput);
        this.m_rightMotor.set(speeds.right * this.m_maxOutput);
        this.m_backMotor.set(speeds.back * this.m_maxOutput);
        this.feed();
    }

    public void drivePolar(double magnitude, double angle, double zRotation) {
        if (!this.m_reported) {
            HAL.report(31, 13, 3);
            this.m_reported = true;
        }
        this.driveCartesian(magnitude * Math.sin(angle * (Math.PI / 180)), magnitude * Math.cos(angle * (Math.PI / 180)), zRotation, 0.0);
    }

    public WheelSpeeds driveCartesianIK(double ySpeed, double xSpeed, double zRotation, double gyroAngle) {
        ySpeed = MathUtil.clamp(ySpeed, -1.0, 1.0);
        xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);
        Vector2d input = new Vector2d(ySpeed, xSpeed);
        input.rotate(-gyroAngle);
        double[] wheelSpeeds = new double[3];
        wheelSpeeds[RobotDriveBase.MotorType.kLeft.value] = input.scalarProject(this.m_leftVec) + zRotation;
        wheelSpeeds[RobotDriveBase.MotorType.kRight.value] = input.scalarProject(this.m_rightVec) + zRotation;
        wheelSpeeds[RobotDriveBase.MotorType.kBack.value] = input.scalarProject(this.m_backVec) + zRotation;
        KilloughDrive.normalize(wheelSpeeds);
        return new WheelSpeeds(wheelSpeeds[RobotDriveBase.MotorType.kLeft.value], wheelSpeeds[RobotDriveBase.MotorType.kRight.value], wheelSpeeds[RobotDriveBase.MotorType.kBack.value]);
    }

    @Override
    public void stopMotor() {
        this.m_leftMotor.stopMotor();
        this.m_rightMotor.stopMotor();
        this.m_backMotor.stopMotor();
        this.feed();
    }

    @Override
    public String getDescription() {
        return "KilloughDrive";
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("KilloughDrive");
        builder.setActuator(true);
        builder.setSafeState(this::stopMotor);
        builder.addDoubleProperty("Left Motor Speed", this.m_leftMotor::get, this.m_leftMotor::set);
        builder.addDoubleProperty("Right Motor Speed", this.m_rightMotor::get, this.m_rightMotor::set);
        builder.addDoubleProperty("Back Motor Speed", this.m_backMotor::get, this.m_backMotor::set);
    }

    public static class WheelSpeeds {
        public double left;
        public double right;
        public double back;

        public WheelSpeeds() {
        }

        public WheelSpeeds(double left, double right, double back) {
            this.left = left;
            this.right = right;
            this.back = back;
        }
    }
}

