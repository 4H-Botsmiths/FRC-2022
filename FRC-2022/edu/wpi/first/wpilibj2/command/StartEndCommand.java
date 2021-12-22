/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class StartEndCommand
extends CommandBase {
    protected final Runnable m_onInit;
    protected final Runnable m_onEnd;

    public StartEndCommand(Runnable onInit, Runnable onEnd, Subsystem ... requirements) {
        this.m_onInit = ErrorMessages.requireNonNullParam(onInit, "onInit", "StartEndCommand");
        this.m_onEnd = ErrorMessages.requireNonNullParam(onEnd, "onEnd", "StartEndCommand");
        this.addRequirements(requirements);
    }

    @Override
    public void initialize() {
        this.m_onInit.run();
    }

    @Override
    public void end(boolean interrupted) {
        this.m_onEnd.run();
    }
}

