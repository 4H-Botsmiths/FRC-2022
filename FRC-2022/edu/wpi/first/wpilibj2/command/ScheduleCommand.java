/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.Set;

public class ScheduleCommand
extends CommandBase {
    private final Set<Command> m_toSchedule;

    public ScheduleCommand(Command ... toSchedule) {
        this.m_toSchedule = Set.of((Object[])toSchedule);
    }

    @Override
    public void initialize() {
        for (Command command : this.m_toSchedule) {
            command.schedule();
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}

