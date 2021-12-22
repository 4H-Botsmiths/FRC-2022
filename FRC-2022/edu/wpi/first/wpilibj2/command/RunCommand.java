/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class RunCommand
extends CommandBase {
    protected final Runnable m_toRun;

    public RunCommand(Runnable toRun, Subsystem ... requirements) {
        this.m_toRun = ErrorMessages.requireNonNullParam(toRun, "toRun", "RunCommand");
        this.addRequirements(requirements);
    }

    @Override
    public void execute() {
        this.m_toRun.run();
    }
}

