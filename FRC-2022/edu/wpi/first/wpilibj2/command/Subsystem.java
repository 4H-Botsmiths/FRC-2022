/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public interface Subsystem {
    default public void periodic() {
    }

    default public void simulationPeriodic() {
    }

    default public void setDefaultCommand(Command defaultCommand) {
        CommandScheduler.getInstance().setDefaultCommand(this, defaultCommand);
    }

    default public Command getDefaultCommand() {
        return CommandScheduler.getInstance().getDefaultCommand(this);
    }

    default public Command getCurrentCommand() {
        return CommandScheduler.getInstance().requiring(this);
    }

    default public void register() {
        CommandScheduler.getInstance().registerSubsystem(this);
    }
}

