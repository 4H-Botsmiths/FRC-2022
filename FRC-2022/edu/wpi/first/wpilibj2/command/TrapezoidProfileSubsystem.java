/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class TrapezoidProfileSubsystem
extends SubsystemBase {
    private final double m_period;
    private final TrapezoidProfile.Constraints m_constraints;
    private TrapezoidProfile.State m_state;
    private TrapezoidProfile.State m_goal;
    private boolean m_enabled = true;

    public TrapezoidProfileSubsystem(TrapezoidProfile.Constraints constraints, double initialPosition, double period) {
        this.m_constraints = ErrorMessages.requireNonNullParam(constraints, "constraints", "TrapezoidProfileSubsystem");
        this.m_state = new TrapezoidProfile.State(initialPosition, 0.0);
        this.setGoal(initialPosition);
        this.m_period = period;
    }

    public TrapezoidProfileSubsystem(TrapezoidProfile.Constraints constraints, double initialPosition) {
        this(constraints, initialPosition, 0.02);
    }

    public TrapezoidProfileSubsystem(TrapezoidProfile.Constraints constraints) {
        this(constraints, 0.0, 0.02);
    }

    @Override
    public void periodic() {
        TrapezoidProfile profile = new TrapezoidProfile(this.m_constraints, this.m_goal, this.m_state);
        this.m_state = profile.calculate(this.m_period);
        if (this.m_enabled) {
            this.useState(this.m_state);
        }
    }

    public void setGoal(TrapezoidProfile.State goal) {
        this.m_goal = goal;
    }

    public void setGoal(double goal) {
        this.setGoal(new TrapezoidProfile.State(goal, 0.0));
    }

    public void enable() {
        this.m_enabled = true;
    }

    public void disable() {
        this.m_enabled = false;
    }

    protected abstract void useState(TrapezoidProfile.State var1);
}

