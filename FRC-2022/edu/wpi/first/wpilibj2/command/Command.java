/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.PerpetualCommand;
import edu.wpi.first.wpilibj2.command.ProxyScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import java.util.Set;
import java.util.function.BooleanSupplier;

public interface Command {
    default public void initialize() {
    }

    default public void execute() {
    }

    default public void end(boolean interrupted) {
    }

    default public boolean isFinished() {
        return false;
    }

    public Set<Subsystem> getRequirements();

    default public ParallelRaceGroup withTimeout(double seconds) {
        return this.raceWith(new WaitCommand(seconds));
    }

    default public ParallelRaceGroup withInterrupt(BooleanSupplier condition) {
        return this.raceWith(new WaitUntilCommand(condition));
    }

    default public SequentialCommandGroup beforeStarting(Runnable toRun, Subsystem ... requirements) {
        return this.beforeStarting(new InstantCommand(toRun, requirements));
    }

    default public SequentialCommandGroup beforeStarting(Command before) {
        return new SequentialCommandGroup(before, this);
    }

    default public SequentialCommandGroup andThen(Runnable toRun, Subsystem ... requirements) {
        return this.andThen(new InstantCommand(toRun, requirements));
    }

    default public SequentialCommandGroup andThen(Command ... next) {
        SequentialCommandGroup group = new SequentialCommandGroup(this);
        group.addCommands(next);
        return group;
    }

    default public ParallelDeadlineGroup deadlineWith(Command ... parallel) {
        return new ParallelDeadlineGroup(this, parallel);
    }

    default public ParallelCommandGroup alongWith(Command ... parallel) {
        ParallelCommandGroup group = new ParallelCommandGroup(this);
        group.addCommands(parallel);
        return group;
    }

    default public ParallelRaceGroup raceWith(Command ... parallel) {
        ParallelRaceGroup group = new ParallelRaceGroup(this);
        group.addCommands(parallel);
        return group;
    }

    default public PerpetualCommand perpetually() {
        return new PerpetualCommand(this);
    }

    default public ProxyScheduleCommand asProxy() {
        return new ProxyScheduleCommand(this);
    }

    default public void schedule(boolean interruptible) {
        CommandScheduler.getInstance().schedule(interruptible, new Command[]{this});
    }

    default public void schedule() {
        this.schedule(true);
    }

    default public void cancel() {
        CommandScheduler.getInstance().cancel(this);
    }

    default public boolean isScheduled() {
        return CommandScheduler.getInstance().isScheduled(this);
    }

    default public boolean hasRequirement(Subsystem requirement) {
        return this.getRequirements().contains(requirement);
    }

    default public boolean runsWhenDisabled() {
        return false;
    }

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

