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
import java.util.Objects;

public class DifferentialDrive
extends RobotDriveBase
implements Sendable,
AutoCloseable {
    private static int instances;
    private final SpeedController m_leftMotor;
    private final SpeedController m_rightMotor;
    private boolean m_reported;

    public DifferentialDrive(SpeedController leftMotor, SpeedController rightMotor) {
        Objects.requireNonNull(leftMotor, "Left motor cannot be null");
        Objects.requireNonNull(rightMotor, "Right motor cannot be null");
        this.m_leftMotor = leftMotor;
        this.m_rightMotor = rightMotor;
        SendableRegistry.addChild(this, this.m_leftMotor);
        SendableRegistry.addChild(this, this.m_rightMotor);
        SendableRegistry.addLW((Sendable)this, "DifferentialDrive", ++instances);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        this.arcadeDrive(xSpeed, zRotation, true);
    }

    public void arcadeDrive(double xSpeed, double zRotation, boolean squareInputs) {
        if (!this.m_reported) {
            HAL.report(31, 7, 2);
            this.m_reported = true;
        }
        xSpeed = MathUtil.applyDeadband(xSpeed, this.m_deadband);
        zRotation = MathUtil.applyDeadband(zRotation, this.m_deadband);
        WheelSpeeds speeds = DifferentialDrive.arcadeDriveIK(xSpeed, zRotation, squareInputs);
        this.m_leftMotor.set(speeds.left * this.m_maxOutput);
        this.m_rightMotor.set(speeds.right * this.m_maxOutput);
        this.feed();
    }

    public void curvatureDrive(double xSpeed, double zRotation, boolean allowTurnInPlace) {
        if (!this.m_reported) {
            HAL.report(31, 9, 2);
            this.m_reported = true;
        }
        xSpeed = MathUtil.applyDeadband(xSpeed, this.m_deadband);
        zRotation = MathUtil.applyDeadband(zRotation, this.m_deadband);
        WheelSpeeds speeds = DifferentialDrive.curvatureDriveIK(xSpeed, zRotation, allowTurnInPlace);
        this.m_leftMotor.set(speeds.left * this.m_maxOutput);
        this.m_rightMotor.set(speeds.right * this.m_maxOutput);
        this.feed();
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        this.tankDrive(leftSpeed, rightSpeed, true);
    }

    public void tankDrive(double leftSpeed, double rightSpeed, boolean squareInputs) {
        if (!this.m_reported) {
            HAL.report(31, 8, 2);
            this.m_reported = true;
        }
        leftSpeed = MathUtil.applyDeadband(leftSpeed, this.m_deadband);
        rightSpeed = MathUtil.applyDeadband(rightSpeed, this.m_deadband);
        WheelSpeeds speeds = DifferentialDrive.tankDriveIK(leftSpeed, rightSpeed, squareInputs);
        this.m_leftMotor.set(speeds.left * this.m_maxOutput);
        this.m_rightMotor.set(speeds.right * this.m_maxOutput);
        this.feed();
    }

    public static WheelSpeeds arcadeDriveIK(double xSpeed, double zRotation, boolean squareInputs) {
        double rightSpeed;
        double leftSpeed;
        xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);
        zRotation = MathUtil.clamp(zRotation, -1.0, 1.0);
        if (squareInputs) {
            xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
            zRotation = Math.copySign(zRotation * zRotation, zRotation);
        }
        double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);
        if (xSpeed >= 0.0) {
            if (zRotation >= 0.0) {
                leftSpeed = maxInput;
                rightSpeed = xSpeed - zRotation;
            } else {
                leftSpeed = xSpeed + zRotation;
                rightSpeed = maxInput;
            }
        } else if (zRotation >= 0.0) {
            leftSpeed = xSpeed + zRotation;
            rightSpeed = maxInput;
        } else {
            leftSpeed = maxInput;
            rightSpeed = xSpeed - zRotation;
        }
        double maxMagnitude = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
        if (maxMagnitude > 1.0) {
            leftSpeed /= maxMagnitude;
            rightSpeed /= maxMagnitude;
        }
        return new WheelSpeeds(leftSpeed, rightSpeed);
    }

    public static WheelSpeeds curvatureDriveIK(double xSpeed, double zRotation, boolean allowTurnInPlace) {
        double rightSpeed;
        double leftSpeed;
        xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);
        zRotation = MathUtil.clamp(zRotation, -1.0, 1.0);
        if (allowTurnInPlace) {
            leftSpeed = xSpeed + zRotation;
            rightSpeed = xSpeed - zRotation;
        } else {
            leftSpeed = xSpeed + Math.abs(xSpeed) * zRotation;
            rightSpeed = xSpeed - Math.abs(xSpeed) * zRotation;
        }
        double maxMagnitude = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
        if (maxMagnitude > 1.0) {
            leftSpeed /= maxMagnitude;
            rightSpeed /= maxMagnitude;
        }
        return new WheelSpeeds(leftSpeed, rightSpeed);
    }

    public static WheelSpeeds tankDriveIK(double leftSpeed, double rightSpeed, boolean squareInputs) {
        leftSpeed = MathUtil.clamp(leftSpeed, -1.0, 1.0);
        rightSpeed = MathUtil.clamp(rightSpeed, -1.0, 1.0);
        if (squareInputs) {
            leftSpeed = Math.copySign(leftSpeed * leftSpeed, leftSpeed);
            rightSpeed = Math.copySign(rightSpeed * rightSpeed, rightSpeed);
        }
        return new WheelSpeeds(leftSpeed, rightSpeed);
    }

    @Override
    public void stopMotor() {
        this.m_leftMotor.stopMotor();
        this.m_rightMotor.stopMotor();
        this.feed();
    }

    @Override
    public String getDescription() {
        return "DifferentialDrive";
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("DifferentialDrive");
        builder.setActuator(true);
        builder.setSafeState(this::stopMotor);
        builder.addDoubleProperty("Left Motor Speed", this.m_leftMotor::get, this.m_leftMotor::set);
        builder.addDoubleProperty("Right Motor Speed", () -> this.m_rightMotor.get(), x -> this.m_rightMotor.set(x));
    }

    public static class WheelSpeeds {
        public double left;
        public double right;

        public WheelSpeeds() {
        }

        public WheelSpeeds(double left, double right) {
            this.left = left;
            this.right = right;
        }
    }
}

