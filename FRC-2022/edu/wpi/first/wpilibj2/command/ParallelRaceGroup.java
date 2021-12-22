/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ParallelRaceGroup
extends CommandGroupBase {
    private final Set<Command> m_commands = new HashSet<Command>();
    private boolean m_runWhenDisabled = true;
    private boolean m_finished = true;

    public ParallelRaceGroup(Command ... commands) {
        this.addCommands(commands);
    }

    @Override
    public final void addCommands(Command ... commands) {
        ParallelRaceGroup.requireUngrouped(commands);
        if (!this.m_finished) {
            throw new IllegalStateException("Commands cannot be added to a CommandGroup while the group is running");
        }
        ParallelRaceGroup.registerGroupedCommands(commands);
        for (Command command : commands) {
            if (!Collections.disjoint(command.getRequirements(), this.m_requirements)) {
                throw new IllegalArgumentException("Multiple commands in a parallel group cannot require the same subsystems");
            }
            this.m_commands.add(command);
            this.m_requirements.addAll(command.getRequirements());
            this.m_runWhenDisabled &= command.runsWhenDisabled();
        }
    }

    @Override
    public void initialize() {
        this.m_finished = false;
        for (Command command : this.m_commands) {
            command.initialize();
        }
    }

    @Override
    public void execute() {
        for (Command command : this.m_commands) {
            command.execute();
            if (!command.isFinished()) continue;
            this.m_finished = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        for (Command command : this.m_commands) {
            command.end(!command.isFinished());
        }
    }

    @Override
    public boolean isFinished() {
        return this.m_finished;
    }

    @Override
    public boolean runsWhenDisabled() {
        return this.m_runWhenDisabled;
    }

    @Override
    public ParallelRaceGroup raceWith(Command ... parallel) {
        this.addCommands(parallel);
        return this;
    }
}

