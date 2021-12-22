/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import java.util.ArrayList;
import java.util.List;

public class SequentialCommandGroup
extends CommandGroupBase {
    private final List<Command> m_commands = new ArrayList<Command>();
    private int m_currentCommandIndex = -1;
    private boolean m_runWhenDisabled = true;

    public SequentialCommandGroup(Command ... commands) {
        this.addCommands(commands);
    }

    @Override
    public final void addCommands(Command ... commands) {
        SequentialCommandGroup.requireUngrouped(commands);
        if (this.m_currentCommandIndex != -1) {
            throw new IllegalStateException("Commands cannot be added to a CommandGroup while the group is running");
        }
        SequentialCommandGroup.registerGroupedCommands(commands);
        for (Command command : commands) {
            this.m_commands.add(command);
            this.m_requirements.addAll(command.getRequirements());
            this.m_runWhenDisabled &= command.runsWhenDisabled();
        }
    }

    @Override
    public void initialize() {
        this.m_currentCommandIndex = 0;
        if (!this.m_commands.isEmpty()) {
            this.m_commands.get(0).initialize();
        }
    }

    @Override
    public void execute() {
        if (this.m_commands.isEmpty()) {
            return;
        }
        Command currentCommand = this.m_commands.get(this.m_currentCommandIndex);
        currentCommand.execute();
        if (currentCommand.isFinished()) {
            currentCommand.end(false);
            ++this.m_currentCommandIndex;
            if (this.m_currentCommandIndex < this.m_commands.size()) {
                this.m_commands.get(this.m_currentCommandIndex).initialize();
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted && !this.m_commands.isEmpty() && this.m_currentCommandIndex > -1 && this.m_currentCommandIndex < this.m_commands.size()) {
            this.m_commands.get(this.m_currentCommandIndex).end(true);
        }
        this.m_currentCommandIndex = -1;
    }

    @Override
    public boolean isFinished() {
        return this.m_currentCommandIndex == this.m_commands.size();
    }

    @Override
    public boolean runsWhenDisabled() {
        return this.m_runWhenDisabled;
    }

    @Override
    public SequentialCommandGroup beforeStarting(Command before) {
        ArrayList commands = new ArrayList();
        commands.add(before);
        commands.addAll(this.m_commands);
        commands.forEach(CommandGroupBase::clearGroupedCommand);
        this.m_commands.clear();
        this.m_requirements.clear();
        this.m_runWhenDisabled = true;
        this.addCommands((Command[])commands.toArray(Command[]::new));
        return this;
    }

    @Override
    public SequentialCommandGroup andThen(Command ... next) {
        this.addCommands(next);
        return this;
    }
}

