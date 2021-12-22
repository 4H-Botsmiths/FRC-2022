/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import java.util.Map;
import java.util.function.Supplier;

public class SelectCommand
extends CommandBase {
    private final Map<Object, Command> m_commands;
    private final Supplier<Object> m_selector;
    private final Supplier<Command> m_toRun;
    private Command m_selectedCommand;

    public SelectCommand(Map<Object, Command> commands, Supplier<Object> selector) {
        CommandGroupBase.requireUngrouped(commands.values());
        CommandGroupBase.registerGroupedCommands(commands.values().toArray(new Command[0]));
        this.m_commands = ErrorMessages.requireNonNullParam(commands, "commands", "SelectCommand");
        this.m_selector = ErrorMessages.requireNonNullParam(selector, "selector", "SelectCommand");
        this.m_toRun = null;
        for (Command command : this.m_commands.values()) {
            this.m_requirements.addAll(command.getRequirements());
        }
    }

    public SelectCommand(Supplier<Command> toRun) {
        this.m_commands = null;
        this.m_selector = null;
        this.m_toRun = ErrorMessages.requireNonNullParam(toRun, "toRun", "SelectCommand");
    }

    @Override
    public void initialize() {
        if (this.m_selector != null) {
            if (!this.m_commands.keySet().contains(this.m_selector.get())) {
                this.m_selectedCommand = new PrintCommand("SelectCommand selector value does not correspond to any command!");
                return;
            }
            this.m_selectedCommand = this.m_commands.get(this.m_selector.get());
        } else {
            this.m_selectedCommand = this.m_toRun.get();
        }
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
        if (this.m_commands != null) {
            boolean runsWhenDisabled = true;
            for (Command command : this.m_commands.values()) {
                runsWhenDisabled &= command.runsWhenDisabled();
            }
            return runsWhenDisabled;
        }
        return this.m_toRun.get().runsWhenDisabled();
    }
}

