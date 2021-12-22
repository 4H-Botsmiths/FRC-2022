/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.controller;

import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.MathUsageId;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

public class ProfiledPIDController
implements Sendable {
    private static int instances;
    private PIDController m_controller;
    private double m_minimumInput;
    private double m_maximumInput;
    private TrapezoidProfile.State m_goal = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_setpoint = new TrapezoidProfile.State();
    private TrapezoidProfile.Constraints m_constraints;

    public ProfiledPIDController(double Kp, double Ki, double Kd, TrapezoidProfile.Constraints constraints) {
        this(Kp, Ki, Kd, constraints, 0.02);
    }

    public ProfiledPIDController(double Kp, double Ki, double Kd, TrapezoidProfile.Constraints constraints, double period) {
        this.m_controller = new PIDController(Kp, Ki, Kd, period);
        this.m_constraints = constraints;
        MathSharedStore.reportUsage(MathUsageId.kController_ProfiledPIDController, ++instances);
    }

    public void setPID(double Kp, double Ki, double Kd) {
        this.m_controller.setPID(Kp, Ki, Kd);
    }

    public void setP(double Kp) {
        this.m_controller.setP(Kp);
    }

    public void setI(double Ki) {
        this.m_controller.setI(Ki);
    }

    public void setD(double Kd) {
        this.m_controller.setD(Kd);
    }

    public double getP() {
        return this.m_controller.getP();
    }

    public double getI() {
        return this.m_controller.getI();
    }

    public double getD() {
        return this.m_controller.getD();
    }

    public double getPeriod() {
        return this.m_controller.getPeriod();
    }

    public void setGoal(TrapezoidProfile.State goal) {
        this.m_goal = goal;
    }

    public void setGoal(double goal) {
        this.m_goal = new TrapezoidProfile.State(goal, 0.0);
    }

    public TrapezoidProfile.State getGoal() {
        return this.m_goal;
    }

    public boolean atGoal() {
        return this.atSetpoint() && this.m_goal.equals(this.m_setpoint);
    }

    public void setConstraints(TrapezoidProfile.Constraints constraints) {
        this.m_constraints = constraints;
    }

    public TrapezoidProfile.State getSetpoint() {
        return this.m_setpoint;
    }

    public boolean atSetpoint() {
        return this.m_controller.atSetpoint();
    }

    public void enableContinuousInput(double minimumInput, double maximumInput) {
        this.m_controller.enableContinuousInput(minimumInput, maximumInput);
        this.m_minimumInput = minimumInput;
        this.m_maximumInput = maximumInput;
    }

    public void disableContinuousInput() {
        this.m_controller.disableContinuousInput();
    }

    public void setIntegratorRange(double minimumIntegral, double maximumIntegral) {
        this.m_controller.setIntegratorRange(minimumIntegral, maximumIntegral);
    }

    public void setTolerance(double positionTolerance) {
        this.setTolerance(positionTolerance, Double.POSITIVE_INFINITY);
    }

    public void setTolerance(double positionTolerance, double velocityTolerance) {
        this.m_controller.setTolerance(positionTolerance, velocityTolerance);
    }

    public double getPositionError() {
        return this.m_controller.getPositionError();
    }

    public double getVelocityError() {
        return this.m_controller.getVelocityError();
    }

    public double calculate(double measurement) {
        if (this.m_controller.isContinuousInputEnabled()) {
            double errorBound = (this.m_maximumInput - this.m_minimumInput) / 2.0;
            double goalMinDistance = MathUtil.inputModulus(this.m_goal.position - measurement, -errorBound, errorBound);
            double setpointMinDistance = MathUtil.inputModulus(this.m_setpoint.position - measurement, -errorBound, errorBound);
            this.m_goal.position = goalMinDistance + measurement;
            this.m_setpoint.position = setpointMinDistance + measurement;
        }
        TrapezoidProfile profile = new TrapezoidProfile(this.m_constraints, this.m_goal, this.m_setpoint);
        this.m_setpoint = profile.calculate(this.getPeriod());
        return this.m_controller.calculate(measurement, this.m_setpoint.position);
    }

    public double calculate(double measurement, TrapezoidProfile.State goal) {
        this.setGoal(goal);
        return this.calculate(measurement);
    }

    public double calculate(double measurement, double goal) {
        this.setGoal(goal);
        return this.calculate(measurement);
    }

    public double calculate(double measurement, TrapezoidProfile.State goal, TrapezoidProfile.Constraints constraints) {
        this.setConstraints(constraints);
        return this.calculate(measurement, goal);
    }

    public void reset(TrapezoidProfile.State measurement) {
        this.m_controller.reset();
        this.m_setpoint = measurement;
    }

    public void reset(double measuredPosition, double measuredVelocity) {
        this.reset(new TrapezoidProfile.State(measuredPosition, measuredVelocity));
    }

    public void reset(double measuredPosition) {
        this.reset(measuredPosition, 0.0);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("ProfiledPIDController");
        builder.addDoubleProperty("p", this::getP, this::setP);
        builder.addDoubleProperty("i", this::getI, this::setI);
        builder.addDoubleProperty("d", this::getD, this::setD);
        builder.addDoubleProperty("goal", () -> this.getGoal().position, this::setGoal);
    }
}

