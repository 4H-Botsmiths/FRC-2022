/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.controller;

import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.MathUsageId;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;

public class BangBangController
implements Sendable {
    private static int instances;
    private double m_tolerance;
    private double m_setpoint;
    private double m_measurement;

    public BangBangController(double tolerance) {
        this.setTolerance(tolerance);
        SendableRegistry.addLW((Sendable)this, "BangBangController", ++instances);
        MathSharedStore.reportUsage(MathUsageId.kController_PIDController2, instances);
    }

    public BangBangController() {
        this(Double.POSITIVE_INFINITY);
    }

    public void setSetpoint(double setpoint) {
        this.m_setpoint = setpoint;
    }

    public double getSetpoint() {
        return this.m_setpoint;
    }

    public boolean atSetpoint() {
        return Math.abs(this.m_setpoint - this.m_measurement) < this.m_tolerance;
    }

    public void setTolerance(double tolerance) {
        this.m_tolerance = tolerance;
    }

    public double getTolerance() {
        return this.m_tolerance;
    }

    public double getMeasurement() {
        return this.m_measurement;
    }

    public double getError() {
        return this.m_setpoint - this.m_measurement;
    }

    public double calculate(double measurement, double setpoint) {
        this.m_measurement = measurement;
        this.m_setpoint = setpoint;
        return measurement < setpoint ? 1.0 : 0.0;
    }

    public double calculate(double measurement) {
        return this.calculate(measurement, this.m_setpoint);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("BangBangController");
        builder.addDoubleProperty("tolerance", this::getTolerance, this::setTolerance);
        builder.addDoubleProperty("setpoint", this::getSetpoint, this::setSetpoint);
        builder.addDoubleProperty("measurement", this::getMeasurement, null);
        builder.addDoubleProperty("error", this::getError, null);
        builder.addBooleanProperty("atSetpoint", this::atSetpoint, null);
    }
}

