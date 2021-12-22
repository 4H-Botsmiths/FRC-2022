/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ParallelCommandGroup
extends CommandGroupBase {
    private final Map<Command, Boolean> m_commands = new HashMap<Command, Boolean>();
    private boolean m_runWhenDisabled = true;

    public ParallelCommandGroup(Command ... commands) {
        this.addCommands(commands);
    }

    @Override
    public final void addCommands(Command ... commands) {
        ParallelCommandGroup.requireUngrouped(commands);
        if (this.m_commands.containsValue(true)) {
            throw new IllegalStateException("Commands cannot be added to a CommandGroup while the group is running");
        }
        ParallelCommandGroup.registerGroupedCommands(commands);
        for (Command command : commands) {
            if (!Collections.disjoint(command.getRequirements(), this.m_requirements)) {
                throw new IllegalArgumentException("Multiple commands in a parallel group cannotrequire the same subsystems");
            }
            this.m_commands.put(command, false);
            this.m_requirements.addAll(command.getRequirements());
            this.m_runWhenDisabled &= command.runsWhenDisabled();
        }
    }

    @Override
    public void initialize() {
        for (Map.Entry<Command, Boolean> commandRunning : this.m_commands.entrySet()) {
            commandRunning.getKey().initialize();
            commandRunning.setValue(true);
        }
    }

    @Override
    public void execute() {
        for (Map.Entry<Command, Boolean> commandRunning : this.m_commands.entrySet()) {
            if (!commandRunning.getValue().booleanValue()) continue;
            commandRunning.getKey().execute();
            if (!commandRunning.getKey().isFinished()) continue;
            commandRunning.getKey().end(false);
            commandRunning.setValue(false);
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            for (Map.Entry<Command, Boolean> commandRunning : this.m_commands.entrySet()) {
                if (!commandRunning.getValue().booleanValue()) continue;
                commandRunning.getKey().end(true);
            }
        }
    }

    @Override
    public boolean isFinished() {
        return !this.m_commands.containsValue(true);
    }

    @Override
    public boolean runsWhenDisabled() {
        return this.m_runWhenDisabled;
    }

    @Override
    public ParallelCommandGroup alongWith(Command ... parallel) {
        this.addCommands(parallel);
        return this;
    }
}

