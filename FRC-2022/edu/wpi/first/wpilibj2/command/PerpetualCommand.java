/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;

public class PerpetualCommand
extends CommandBase {
    protected final Command m_command;

    public PerpetualCommand(Command command) {
        CommandGroupBase.requireUngrouped(command);
        CommandGroupBase.registerGroupedCommands(command);
        this.m_command = command;
        this.m_requirements.addAll(command.getRequirements());
    }

    @Override
    public void initialize() {
        this.m_command.initialize();
    }

    @Override
    public void execute() {
        this.m_command.execute();
    }

    @Override
    public void end(boolean interrupted) {
        this.m_command.end(interrupted);
    }

    @Override
    public boolean runsWhenDisabled() {
        return this.m_command.runsWhenDisabled();
    }

    @Override
    public PerpetualCommand perpetually() {
        return this;
    }
}

