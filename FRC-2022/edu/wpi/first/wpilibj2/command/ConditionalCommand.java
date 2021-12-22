/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import java.util.function.BooleanSupplier;

public class ConditionalCommand
extends CommandBase {
    private final Command m_onTrue;
    private final Command m_onFalse;
    private final BooleanSupplier m_condition;
    private Command m_selectedCommand;

    public ConditionalCommand(Command onTrue, Command onFalse, BooleanSupplier condition) {
        CommandGroupBase.requireUngrouped(onTrue, onFalse);
        CommandGroupBase.registerGroupedCommands(onTrue, onFalse);
        this.m_onTrue = onTrue;
        this.m_onFalse = onFalse;
        this.m_condition = ErrorMessages.requireNonNullParam(condition, "condition", "ConditionalCommand");
        this.m_requirements.addAll(this.m_onTrue.getRequirements());
        this.m_requirements.addAll(this.m_onFalse.getRequirements());
    }

    @Override
    public void initialize() {
        this.m_selectedCommand = this.m_condition.getAsBoolean() ? this.m_onTrue : this.m_onFalse;
        this.m_selectedCommand.initialize();
    }

    @Override
    public void execute() {
        this.m_selectedCommand.execute();
    }

    @Override
    public void end(boolean interrupted) {
        this.m_selectedCommand.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return this.m_selectedCommand.isFinished();
    }

    @Override
    public boolean runsWhenDisabled() {
        return this.m_onTrue.runsWhenDisabled() && this.m_onFalse.runsWhenDisabled();
    }
}

