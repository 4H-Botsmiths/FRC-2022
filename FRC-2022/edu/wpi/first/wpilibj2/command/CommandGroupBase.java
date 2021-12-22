/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public abstract class CommandGroupBase
extends CommandBase {
    private static final Set<Command> m_groupedCommands = Collections.newSetFromMap(new WeakHashMap());

    static void registerGroupedCommands(Command ... commands) {
        m_groupedCommands.addAll(Set.of((Object[])commands));
    }

    public static void clearGroupedCommands() {
        m_groupedCommands.clear();
    }

    public static void clearGroupedCommand(Command command) {
        m_groupedCommands.remove(command);
    }

    public static void requireUngrouped(Command ... commands) {
        CommandGroupBase.requireUngrouped(Set.of((Object[])commands));
    }

    public static void requireUngrouped(Collection<Command> commands) {
        if (!Collections.disjoint(commands, CommandGroupBase.getGroupedCommands())) {
            throw new IllegalArgumentException("Commands cannot be added to more than one CommandGroup");
        }
    }

    static Set<Command> getGroupedCommands() {
        return m_groupedCommands;
    }

    public abstract void addCommands(Command ... var1);

    public static CommandGroupBase sequence(Command ... commands) {
        return new SequentialCommandGroup(commands);
    }

    public static CommandGroupBase parallel(Command ... commands) {
        return new ParallelCommandGroup(commands);
    }

    public static CommandGroupBase race(Command ... commands) {
        return new ParallelRaceGroup(commands);
    }

    public static CommandGroupBase deadline(Command deadline, Command ... commands) {
        return new ParallelDeadlineGroup(deadline, commands);
    }
}

