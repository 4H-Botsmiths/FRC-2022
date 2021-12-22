/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.Set;

public class ProxyScheduleCommand
extends CommandBase {
    private final Set<Command> m_toSchedule;
    private boolean m_finished;

    public ProxyScheduleCommand(Command ... toSchedule) {
        this.m_toSchedule = Set.of((Object[])toSchedule);
    }

    @Override
    public void initialize() {
        for (Command command : this.m_toSchedule) {
            command.schedule();
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            for (Command command : this.m_toSchedule) {
                command.cancel();
            }
        }
    }

    @Override
    public void execute() {
        this.m_finished = true;
        for (Command command : this.m_toSchedule) {
            this.m_finished &= !command.isScheduled();
        }
    }

    @Override
    public boolean isFinished() {
        return this.m_finished;
    }
}

