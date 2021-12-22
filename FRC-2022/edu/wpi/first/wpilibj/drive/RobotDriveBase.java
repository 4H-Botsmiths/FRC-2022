/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.MotorSafety;

public abstract class RobotDriveBase
extends MotorSafety {
    public static final double kDefaultDeadband = 0.02;
    public static final double kDefaultMaxOutput = 1.0;
    protected double m_deadband = 0.02;
    protected double m_maxOutput = 1.0;

    public RobotDriveBase() {
        this.setSafetyEnabled(true);
    }

    public void setDeadband(double deadband) {
        this.m_deadband = deadband;
    }

    public void setMaxOutput(double maxOutput) {
        this.m_maxOutput = maxOutput;
    }

    public void feedWatchdog() {
        this.feed();
    }

    @Override
    public abstract void stopMotor();

    @Override
    public abstract String getDescription();

    @Deprecated(since="2021", forRemoval=true)
    protected static double applyDeadband(double value, double deadband) {
        return MathUtil.applyDeadband(value, deadband);
    }

    protected static void normalize(double[] wheelSpeeds) {
        int i;
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        for (i = 1; i < wheelSpeeds.length; ++i) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (!(maxMagnitude < temp)) continue;
            maxMagnitude = temp;
        }
        if (maxMagnitude > 1.0) {
            for (i = 0; i < wheelSpeeds.length; ++i) {
                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
            }
        }
    }

    public static enum MotorType {
        kFrontLeft(0),
        kFrontRight(1),
        kRearLeft(2),
        kRearRight(3),
        kLeft(0),
        kRight(1),
        kBack(2);

        public final int value;

        private MotorType(int value) {
            this.value = value;
        }
    }
}

