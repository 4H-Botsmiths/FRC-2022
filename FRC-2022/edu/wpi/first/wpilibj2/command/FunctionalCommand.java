/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class FunctionalCommand
extends CommandBase {
    protected final Runnable m_onInit;
    protected final Runnable m_onExecute;
    protected final Consumer<Boolean> m_onEnd;
    protected final BooleanSupplier m_isFinished;

    public FunctionalCommand(Runnable onInit, Runnable onExecute, Consumer<Boolean> onEnd, BooleanSupplier isFinished, Subsystem ... requirements) {
        this.m_onInit = ErrorMessages.requireNonNullParam(onInit, "onInit", "FunctionalCommand");
        this.m_onExecute = ErrorMessages.requireNonNullParam(onExecute, "onExecute", "FunctionalCommand");
        this.m_onEnd = ErrorMessages.requireNonNullParam(onEnd, "onEnd", "FunctionalCommand");
        this.m_isFinished = ErrorMessages.requireNonNullParam(isFinished, "isFinished", "FunctionalCommand");
        this.addRequirements(requirements);
    }

    @Override
    public void initialize() {
        this.m_onInit.run();
    }

    @Override
    public void execute() {
        this.m_onExecute.run();
    }

    @Override
    public void end(boolean interrupted) {
        this.m_onEnd.accept(interrupted);
    }

    @Override
    public boolean isFinished() {
        return this.m_isFinished.getAsBoolean();
    }
}

