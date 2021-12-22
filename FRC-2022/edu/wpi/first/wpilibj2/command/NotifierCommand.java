/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class NotifierCommand
extends CommandBase {
    protected final Notifier m_notifier;
    protected final double m_period;

    public NotifierCommand(Runnable toRun, double period, Subsystem ... requirements) {
        this.m_notifier = new Notifier(toRun);
        this.m_period = period;
        this.addRequirements(requirements);
    }

    @Override
    public void initialize() {
        this.m_notifier.startPeriodic(this.m_period);
    }

    @Override
    public void end(boolean interrupted) {
        this.m_notifier.stop();
    }
}

