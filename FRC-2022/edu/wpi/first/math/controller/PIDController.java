/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.controller;

import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.MathUsageId;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;

public class PIDController
implements Sendable,
AutoCloseable {
    private static int instances;
    private double m_kp;
    private double m_ki;
    private double m_kd;
    private final double m_period;
    private double m_maximumIntegral = 1.0;
    private double m_minimumIntegral = -1.0;
    private double m_maximumInput;
    private double m_minimumInput;
    private boolean m_continuous;
    private double m_positionError;
    private double m_velocityError;
    private double m_prevError;
    private double m_totalError;
    private double m_positionTolerance = 0.05;
    private double m_velocityTolerance = Double.POSITIVE_INFINITY;
    private double m_setpoint;
    private double m_measurement;

    public PIDController(double kp, double ki, double kd) {
        this(kp, ki, kd, 0.02);
    }

    public PIDController(double kp, double ki, double kd, double period) {
        this.m_kp = kp;
        this.m_ki = ki;
        this.m_kd = kd;
        if (period <= 0.0) {
            throw new IllegalArgumentException("Controller period must be a non-zero positive number!");
        }
        this.m_period = period;
        SendableRegistry.addLW((Sendable)this, "PIDController", ++instances);
        MathSharedStore.reportUsage(MathUsageId.kController_PIDController2, instances);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
    }

    public void setPID(double kp, double ki, double kd) {
        this.m_kp = kp;
        this.m_ki = ki;
        this.m_kd = kd;
    }

    public void setP(double kp) {
        this.m_kp = kp;
    }

    public void setI(double ki) {
        this.m_ki = ki;
    }

    public void setD(double kd) {
        this.m_kd = kd;
    }

    public double getP() {
        return this.m_kp;
    }

    public double getI() {
        return this.m_ki;
    }

    public double getD() {
        return this.m_kd;
    }

    public double getPeriod() {
        return this.m_period;
    }

    public void setSetpoint(double setpoint) {
        this.m_setpoint = setpoint;
    }

    public double getSetpoint() {
        return this.m_setpoint;
    }

    public boolean atSetpoint() {
        double positionError;
        if (this.m_continuous) {
            double errorBound = (this.m_maximumInput - this.m_minimumInput) / 2.0;
            positionError = MathUtil.inputModulus(this.m_setpoint - this.m_measurement, -errorBound, errorBound);
        } else {
            positionError = this.m_setpoint - this.m_measurement;
        }
        double velocityError = (positionError - this.m_prevError) / this.m_period;
        return Math.abs(positionError) < this.m_positionTolerance && Math.abs(velocityError) < this.m_velocityTolerance;
    }

    public void enableContinuousInput(double minimumInput, double maximumInput) {
        this.m_continuous = true;
        this.m_minimumInput = minimumInput;
        this.m_maximumInput = maximumInput;
    }

    public void disableContinuousInput() {
        this.m_continuous = false;
    }

    public boolean isContinuousInputEnabled() {
        return this.m_continuous;
    }

    public void setIntegratorRange(double minimumIntegral, double maximumIntegral) {
        this.m_minimumIntegral = minimumIntegral;
        this.m_maximumIntegral = maximumIntegral;
    }

    public void setTolerance(double positionTolerance) {
        this.setTolerance(positionTolerance, Double.POSITIVE_INFINITY);
    }

    public void setTolerance(double positionTolerance, double velocityTolerance) {
        this.m_positionTolerance = positionTolerance;
        this.m_velocityTolerance = velocityTolerance;
    }

    public double getPositionError() {
        return this.m_positionError;
    }

    public double getVelocityError() {
        return this.m_velocityError;
    }

    public double calculate(double measurement, double setpoint) {
        this.setSetpoint(setpoint);
        return this.calculate(measurement);
    }

    public double calculate(double measurement) {
        this.m_measurement = measurement;
        this.m_prevError = this.m_positionError;
        if (this.m_continuous) {
            double errorBound = (this.m_maximumInput - this.m_minimumInput) / 2.0;
            this.m_positionError = MathUtil.inputModulus(this.m_setpoint - this.m_measurement, -errorBound, errorBound);
        } else {
            this.m_positionError = this.m_setpoint - measurement;
        }
        this.m_velocityError = (this.m_positionError - this.m_prevError) / this.m_period;
        if (this.m_ki != 0.0) {
            this.m_totalError = MathUtil.clamp(this.m_totalError + this.m_positionError * this.m_period, this.m_minimumIntegral / this.m_ki, this.m_maximumIntegral / this.m_ki);
        }
        return this.m_kp * this.m_positionError + this.m_ki * this.m_totalError + this.m_kd * this.m_velocityError;
    }

    public void reset() {
        this.m_prevError = 0.0;
        this.m_totalError = 0.0;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("PIDController");
        builder.addDoubleProperty("p", this::getP, this::setP);
        builder.addDoubleProperty("i", this::getI, this::setI);
        builder.addDoubleProperty("d", this::getD, this::setD);
        builder.addDoubleProperty("setpoint", this::getSetpoint, this::setSetpoint);
    }
}

