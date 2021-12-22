/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class ProfiledPIDCommand
extends CommandBase {
    protected final ProfiledPIDController m_controller;
    protected DoubleSupplier m_measurement;
    protected Supplier<TrapezoidProfile.State> m_goal;
    protected BiConsumer<Double, TrapezoidProfile.State> m_useOutput;

    public ProfiledPIDCommand(ProfiledPIDController controller, DoubleSupplier measurementSource, Supplier<TrapezoidProfile.State> goalSource, BiConsumer<Double, TrapezoidProfile.State> useOutput, Subsystem ... requirements) {
        ErrorMessages.requireNonNullParam(controller, "controller", "SynchronousPIDCommand");
        ErrorMessages.requireNonNullParam(measurementSource, "measurementSource", "SynchronousPIDCommand");
        ErrorMessages.requireNonNullParam(goalSource, "goalSource", "SynchronousPIDCommand");
        ErrorMessages.requireNonNullParam(useOutput, "useOutput", "SynchronousPIDCommand");
        this.m_controller = controller;
        this.m_useOutput = useOutput;
        this.m_measurement = measurementSource;
        this.m_goal = goalSource;
        this.m_requirements.addAll(Set.of((Object[])requirements));
    }

    public ProfiledPIDCommand(ProfiledPIDController controller, DoubleSupplier measurementSource, DoubleSupplier goalSource, BiConsumer<Double, TrapezoidProfile.State> useOutput, Subsystem ... requirements) {
        ErrorMessages.requireNonNullParam(controller, "controller", "SynchronousPIDCommand");
        ErrorMessages.requireNonNullParam(measurementSource, "measurementSource", "SynchronousPIDCommand");
        ErrorMessages.requireNonNullParam(goalSource, "goalSource", "SynchronousPIDCommand");
        ErrorMessages.requireNonNullParam(useOutput, "useOutput", "SynchronousPIDCommand");
        this.m_controller = controller;
        this.m_useOutput = useOutput;
        this.m_measurement = measurementSource;
        this.m_goal = () -> new TrapezoidProfile.State(goalSource.getAsDouble(), 0.0);
        this.m_requirements.addAll(Set.of((Object[])requirements));
    }

    public ProfiledPIDCommand(ProfiledPIDController controller, DoubleSupplier measurementSource, TrapezoidProfile.State goal, BiConsumer<Double, TrapezoidProfile.State> useOutput, Subsystem ... requirements) {
        this(controller, measurementSource, () -> goal, useOutput, requirements);
    }

    public ProfiledPIDCommand(ProfiledPIDController controller, DoubleSupplier measurementSource, double goal, BiConsumer<Double, TrapezoidProfile.State> useOutput, Subsystem ... requirements) {
        this(controller, measurementSource, () -> goal, useOutput, requirements);
    }

    @Override
    public void initialize() {
        this.m_controller.reset(this.m_measurement.getAsDouble());
    }

    @Override
    public void execute() {
        this.m_useOutput.accept(this.m_controller.calculate(this.m_measurement.getAsDouble(), this.m_goal.get()), this.m_controller.getSetpoint());
    }

    @Override
    public void end(boolean interrupted) {
        this.m_useOutput.accept(0.0, new TrapezoidProfile.State());
    }

    public ProfiledPIDController getController() {
        return this.m_controller;
    }
}

