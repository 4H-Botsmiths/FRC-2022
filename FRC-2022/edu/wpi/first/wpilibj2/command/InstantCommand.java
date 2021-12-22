/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class InstantCommand
extends CommandBase {
    private final Runnable m_toRun;

    public InstantCommand(Runnable toRun, Subsystem ... requirements) {
        this.m_toRun = ErrorMessages.requireNonNullParam(toRun, "toRun", "InstantCommand");
        this.addRequirements(requirements);
    }

    public InstantCommand() {
        this.m_toRun = () -> {};
    }

    @Override
    public void initialize() {
        this.m_toRun.run();
    }

    @Override
    public final boolean isFinished() {
        return true;
    }
}

