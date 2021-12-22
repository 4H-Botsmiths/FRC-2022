/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.Set;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

public class PIDCommand
extends CommandBase {
    protected final PIDController m_controller;
    protected DoubleSupplier m_measurement;
    protected DoubleSupplier m_setpoint;
    protected DoubleConsumer m_useOutput;

    public PIDCommand(PIDController controller, DoubleSupplier measurementSource, DoubleSupplier setpointSource, DoubleConsumer useOutput, Subsystem ... requirements) {
        ErrorMessages.requireNonNullParam(controller, "controller", "SynchronousPIDCommand");
        ErrorMessages.requireNonNullParam(measurementSource, "measurementSource", "SynchronousPIDCommand");
        ErrorMessages.requireNonNullParam(setpointSource, "setpointSource", "SynchronousPIDCommand");
        ErrorMessages.requireNonNullParam(useOutput, "useOutput", "SynchronousPIDCommand");
        this.m_controller = controller;
        this.m_useOutput = useOutput;
        this.m_measurement = measurementSource;
        this.m_setpoint = setpointSource;
        this.m_requirements.addAll(Set.of((Object[])requirements));
    }

    public PIDCommand(PIDController controller, DoubleSupplier measurementSource, double setpoint, DoubleConsumer useOutput, Subsystem ... requirements) {
        this(controller, measurementSource, () -> setpoint, useOutput, requirements);
    }

    @Override
    public void initialize() {
        this.m_controller.reset();
    }

    @Override
    public void execute() {
        this.m_useOutput.accept(this.m_controller.calculate(this.m_measurement.getAsDouble(), this.m_setpoint.getAsDouble()));
    }

    @Override
    public void end(boolean interrupted) {
        this.m_useOutput.accept(0.0);
    }

    public PIDController getController() {
        return this.m_controller;
    }
}

