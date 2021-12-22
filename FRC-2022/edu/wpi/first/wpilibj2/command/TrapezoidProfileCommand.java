/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.Consumer;

public class TrapezoidProfileCommand
extends CommandBase {
    private final TrapezoidProfile m_profile;
    private final Consumer<TrapezoidProfile.State> m_output;
    private final Timer m_timer = new Timer();

    public TrapezoidProfileCommand(TrapezoidProfile profile, Consumer<TrapezoidProfile.State> output, Subsystem ... requirements) {
        this.m_profile = ErrorMessages.requireNonNullParam(profile, "profile", "TrapezoidProfileCommand");
        this.m_output = ErrorMessages.requireNonNullParam(output, "output", "TrapezoidProfileCommand");
        this.addRequirements(requirements);
    }

    @Override
    public void initialize() {
        this.m_timer.reset();
        this.m_timer.start();
    }

    @Override
    public void execute() {
        this.m_output.accept(this.m_profile.calculate(this.m_timer.get()));
    }

    @Override
    public void end(boolean interrupted) {
        this.m_timer.stop();
    }

    @Override
    public boolean isFinished() {
        return this.m_timer.hasElapsed(this.m_profile.totalTime());
    }
}

