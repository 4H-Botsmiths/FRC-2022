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

public class MecanumDrive
extends RobotDriveBase
implements Sendable,
AutoCloseable {
    private static int instances;
    private final SpeedController m_frontLeftMotor;
    private final SpeedController m_rearLeftMotor;
    private final SpeedController m_frontRightMotor;
    private final SpeedController m_rearRightMotor;
    private boolean m_reported;

    public MecanumDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor, SpeedController rearRightMotor) {
        Objects.requireNonNull(frontLeftMotor, "Front-left motor cannot be null");
        Objects.requireNonNull(rearLeftMotor, "Rear-left motor cannot be null");
        Objects.requireNonNull(frontRightMotor, "Front-right motor cannot be null");
        Objects.requireNonNull(rearRightMotor, "Rear-right motor cannot be null");
        this.m_frontLeftMotor = frontLeftMotor;
        this.m_rearLeftMotor = rearLeftMotor;
        this.m_frontRightMotor = frontRightMotor;
        this.m_rearRightMotor = rearRightMotor;
        SendableRegistry.addChild(this, this.m_frontLeftMotor);
        SendableRegistry.addChild(this, this.m_rearLeftMotor);
        SendableRegistry.addChild(this, this.m_frontRightMotor);
        SendableRegistry.addChild(this, this.m_rearRightMotor);
        SendableRegistry.addLW((Sendable)this, "MecanumDrive", ++instances);
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
            HAL.report(31, 10, 4);
            this.m_reported = true;
        }
        ySpeed = MathUtil.applyDeadband(ySpeed, this.m_deadband);
        xSpeed = MathUtil.applyDeadband(xSpeed, this.m_deadband);
        WheelSpeeds speeds = MecanumDrive.driveCartesianIK(ySpeed, xSpeed, zRotation, gyroAngle);
        this.m_frontLeftMotor.set(speeds.frontLeft * this.m_maxOutput);
        this.m_frontRightMotor.set(speeds.frontRight * this.m_maxOutput);
        this.m_rearLeftMotor.set(speeds.rearLeft * this.m_maxOutput);
        this.m_rearRightMotor.set(speeds.rearRight * this.m_maxOutput);
        this.feed();
    }

    public void drivePolar(double magnitude, double angle, double zRotation) {
        if (!this.m_reported) {
            HAL.report(31, 11, 4);
            this.m_reported = true;
        }
        this.driveCartesian(magnitude * Math.cos(angle * (Math.PI / 180)), magnitude * Math.sin(angle * (Math.PI / 180)), zRotation, 0.0);
    }

    public static WheelSpeeds driveCartesianIK(double ySpeed, double xSpeed, double zRotation, double gyroAngle) {
        ySpeed = MathUtil.clamp(ySpeed, -1.0, 1.0);
        xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);
        Vector2d input = new Vector2d(ySpeed, xSpeed);
        input.rotate(-gyroAngle);
        double[] wheelSpeeds = new double[4];
        wheelSpeeds[RobotDriveBase.MotorType.kFrontLeft.value] = input.x + input.y + zRotation;
        wheelSpeeds[RobotDriveBase.MotorType.kFrontRight.value] = input.x - input.y - zRotation;
        wheelSpeeds[RobotDriveBase.MotorType.kRearLeft.value] = input.x - input.y + zRotation;
        wheelSpeeds[RobotDriveBase.MotorType.kRearRight.value] = input.x + input.y - zRotation;
        MecanumDrive.normalize(wheelSpeeds);
        return new WheelSpeeds(wheelSpeeds[RobotDriveBase.MotorType.kFrontLeft.value], wheelSpeeds[RobotDriveBase.MotorType.kFrontRight.value], wheelSpeeds[RobotDriveBase.MotorType.kRearLeft.value], wheelSpeeds[RobotDriveBase.MotorType.kRearRight.value]);
    }

    @Override
    public void stopMotor() {
        this.m_frontLeftMotor.stopMotor();
        this.m_frontRightMotor.stopMotor();
        this.m_rearLeftMotor.stopMotor();
        this.m_rearRightMotor.stopMotor();
        this.feed();
    }

    @Override
    public String getDescription() {
        return "MecanumDrive";
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("MecanumDrive");
        builder.setActuator(true);
        builder.setSafeState(this::stopMotor);
        builder.addDoubleProperty("Front Left Motor Speed", this.m_frontLeftMotor::get, this.m_frontLeftMotor::set);
        builder.addDoubleProperty("Front Right Motor Speed", () -> this.m_frontRightMotor.get(), value -> this.m_frontRightMotor.set(value));
        builder.addDoubleProperty("Rear Left Motor Speed", this.m_rearLeftMotor::get, this.m_rearLeftMotor::set);
        builder.addDoubleProperty("Rear Right Motor Speed", () -> this.m_rearRightMotor.get(), value -> this.m_rearRightMotor.set(value));
    }

    public static class WheelSpeeds {
        public double frontLeft;
        public double frontRight;
        public double rearLeft;
        public double rearRight;

        public WheelSpeeds() {
        }

        public WheelSpeeds(double frontLeft, double frontRight, double rearLeft, double rearRight) {
            this.frontLeft = frontLeft;
            this.frontRight = frontRight;
            this.rearLeft = rearLeft;
            this.rearRight = rearRight;
        }
    }
}

