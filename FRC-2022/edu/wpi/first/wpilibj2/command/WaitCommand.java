/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class WaitCommand
extends CommandBase {
    protected Timer m_timer = new Timer();
    private final double m_duration;

    public WaitCommand(double seconds) {
        this.m_duration = seconds;
        SendableRegistry.setName(this, this.getName() + ": " + seconds + " seconds");
    }

    @Override
    public void initialize() {
        this.m_timer.reset();
        this.m_timer.start();
    }

    @Override
    public void end(boolean interrupted) {
        this.m_timer.stop();
    }

    @Override
    public boolean isFinished() {
        return this.m_timer.hasElapsed(this.m_duration);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}

