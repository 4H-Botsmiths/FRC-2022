/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class ProfiledPIDSubsystem
extends SubsystemBase {
    protected final ProfiledPIDController m_controller;
    protected boolean m_enabled;
    private TrapezoidProfile.State m_goal;

    public ProfiledPIDSubsystem(ProfiledPIDController controller, double initialPosition) {
        this.m_controller = ErrorMessages.requireNonNullParam(controller, "controller", "ProfiledPIDSubsystem");
        this.setGoal(initialPosition);
    }

    public ProfiledPIDSubsystem(ProfiledPIDController controller) {
        this(controller, 0.0);
    }

    @Override
    public void periodic() {
        if (this.m_enabled) {
            this.useOutput(this.m_controller.calculate(this.getMeasurement(), this.m_goal), this.m_controller.getSetpoint());
        }
    }

    public ProfiledPIDController getController() {
        return this.m_controller;
    }

    public void setGoal(TrapezoidProfile.State goal) {
        this.m_goal = goal;
    }

    public void setGoal(double goal) {
        this.setGoal(new TrapezoidProfile.State(goal, 0.0));
    }

    protected abstract void useOutput(double var1, TrapezoidProfile.State var3);

    protected abstract double getMeasurement();

    public void enable() {
        this.m_enabled = true;
        this.m_controller.reset(this.getMeasurement());
    }

    public void disable() {
        this.m_enabled = false;
        this.useOutput(0.0, new TrapezoidProfile.State());
    }

    public boolean isEnabled() {
        return this.m_enabled;
    }
}

