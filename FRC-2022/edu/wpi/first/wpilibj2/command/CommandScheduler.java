/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.NTSendableBuilder;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.CommandState;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public final class CommandScheduler
implements NTSendable,
AutoCloseable {
    private static CommandScheduler instance;
    private final Map<Command, CommandState> m_scheduledCommands = new LinkedHashMap<Command, CommandState>();
    private final Map<Subsystem, Command> m_requirements = new LinkedHashMap<Subsystem, Command>();
    private final Map<Subsystem, Command> m_subsystems = new LinkedHashMap<Subsystem, Command>();
    private final Collection<Runnable> m_buttons = new LinkedHashSet<Runnable>();
    private boolean m_disabled;
    private final List<Consumer<Command>> m_initActions = new ArrayList<Consumer<Command>>();
    private final List<Consumer<Command>> m_executeActions = new ArrayList<Consumer<Command>>();
    private final List<Consumer<Command>> m_interruptActions = new ArrayList<Consumer<Command>>();
    private final List<Consumer<Command>> m_finishActions = new ArrayList<Consumer<Command>>();
    private boolean m_inRunLoop;
    private final Map<Command, Boolean> m_toSchedule = new LinkedHashMap<Command, Boolean>();
    private final List<Command> m_toCancel = new ArrayList<Command>();
    private final Watchdog m_watchdog = new Watchdog(0.02, () -> {});

    public static synchronized CommandScheduler getInstance() {
        if (instance == null) {
            instance = new CommandScheduler();
        }
        return instance;
    }

    CommandScheduler() {
        HAL.report(40, 2);
        SendableRegistry.addLW(this, "Scheduler");
        LiveWindow.setEnabledListener(() -> {
            this.disable();
            this.cancelAll();
        });
        LiveWindow.setDisabledListener(() -> this.enable());
    }

    public void setPeriod(double period) {
        this.m_watchdog.setTimeout(period);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        LiveWindow.setEnabledListener(null);
        LiveWindow.setDisabledListener(null);
    }

    public void addButton(Runnable button) {
        this.m_buttons.add(button);
    }

    public void clearButtons() {
        this.m_buttons.clear();
    }

    private void initCommand(Command command, boolean interruptible, Set<Subsystem> requirements) {
        CommandState scheduledCommand = new CommandState(interruptible);
        this.m_scheduledCommands.put(command, scheduledCommand);
        command.initialize();
        for (Subsystem subsystem : requirements) {
            this.m_requirements.put(subsystem, command);
        }
        for (Consumer consumer : this.m_initActions) {
            consumer.accept(command);
        }
        this.m_watchdog.addEpoch(command.getName() + ".initialize()");
    }

    private void schedule(boolean interruptible, Command command) {
        if (this.m_inRunLoop) {
            this.m_toSchedule.put(command, interruptible);
            return;
        }
        if (CommandGroupBase.getGroupedCommands().contains(command)) {
            throw new IllegalArgumentException("A command that is part of a command group cannot be independently scheduled");
        }
        if (this.m_disabled || RobotState.isDisabled() && !command.runsWhenDisabled() || this.m_scheduledCommands.containsKey(command)) {
            return;
        }
        Set<Subsystem> requirements = command.getRequirements();
        if (Collections.disjoint(this.m_requirements.keySet(), requirements)) {
            this.initCommand(command, interruptible, requirements);
        } else {
            for (Subsystem requirement : requirements) {
                if (!this.m_requirements.containsKey(requirement) || this.m_scheduledCommands.get(this.m_requirements.get(requirement)).isInterruptible()) continue;
                return;
            }
            for (Subsystem requirement : requirements) {
                if (!this.m_requirements.containsKey(requirement)) continue;
                this.cancel(this.m_requirements.get(requirement));
            }
            this.initCommand(command, interruptible, requirements);
        }
    }

    public void schedule(boolean interruptible, Command ... commands) {
        for (Command command : commands) {
            this.schedule(interruptible, command);
        }
    }

    public void schedule(Command ... commands) {
        this.schedule(true, commands);
    }

    public void run() {
        if (this.m_disabled) {
            return;
        }
        this.m_watchdog.reset();
        for (Subsystem subsystem : this.m_subsystems.keySet()) {
            subsystem.periodic();
            if (RobotBase.isSimulation()) {
                subsystem.simulationPeriodic();
            }
            this.m_watchdog.addEpoch(subsystem.getClass().getSimpleName() + ".periodic()");
        }
        for (Runnable runnable : this.m_buttons) {
            runnable.run();
        }
        this.m_watchdog.addEpoch("buttons.run()");
        this.m_inRunLoop = true;
        Iterator<Command> iterator = this.m_scheduledCommands.keySet().iterator();
        while (iterator.hasNext()) {
            Command command = iterator.next();
            if (!command.runsWhenDisabled() && RobotState.isDisabled()) {
                command.end(true);
                for (Consumer<Command> action : this.m_interruptActions) {
                    action.accept(command);
                }
                this.m_requirements.keySet().removeAll(command.getRequirements());
                iterator.remove();
                this.m_watchdog.addEpoch(command.getName() + ".end(true)");
                continue;
            }
            command.execute();
            for (Consumer<Command> action : this.m_executeActions) {
                action.accept(command);
            }
            this.m_watchdog.addEpoch(command.getName() + ".execute()");
            if (!command.isFinished()) continue;
            command.end(false);
            for (Consumer<Command> action : this.m_finishActions) {
                action.accept(command);
            }
            iterator.remove();
            this.m_requirements.keySet().removeAll(command.getRequirements());
            this.m_watchdog.addEpoch(command.getName() + ".end(false)");
        }
        this.m_inRunLoop = false;
        for (Map.Entry entry : this.m_toSchedule.entrySet()) {
            this.schedule((boolean)((Boolean)entry.getValue()), (Command)entry.getKey());
        }
        for (Command command : this.m_toCancel) {
            this.cancel(command);
        }
        this.m_toSchedule.clear();
        this.m_toCancel.clear();
        for (Map.Entry entry : this.m_subsystems.entrySet()) {
            if (this.m_requirements.containsKey(entry.getKey()) || entry.getValue() == null) continue;
            this.schedule((Command)entry.getValue());
        }
        this.m_watchdog.disable();
        if (this.m_watchdog.isExpired()) {
            System.out.println("CommandScheduler loop overrun");
            this.m_watchdog.printEpochs();
        }
    }

    public void registerSubsystem(Subsystem ... subsystems) {
        for (Subsystem subsystem : subsystems) {
            this.m_subsystems.put(subsystem, null);
        }
    }

    public void unregisterSubsystem(Subsystem ... subsystems) {
        this.m_subsystems.keySet().removeAll(Set.of((Object[])subsystems));
    }

    public void setDefaultCommand(Subsystem subsystem, Command defaultCommand) {
        if (!defaultCommand.getRequirements().contains(subsystem)) {
            throw new IllegalArgumentException("Default commands must require their subsystem!");
        }
        if (defaultCommand.isFinished()) {
            throw new IllegalArgumentException("Default commands should not end!");
        }
        this.m_subsystems.put(subsystem, defaultCommand);
    }

    public Command getDefaultCommand(Subsystem subsystem) {
        return this.m_subsystems.get(subsystem);
    }

    public void cancel(Command ... commands) {
        if (this.m_inRunLoop) {
            this.m_toCancel.addAll(List.of((Object[])commands));
            return;
        }
        for (Command command : commands) {
            if (!this.m_scheduledCommands.containsKey(command)) continue;
            command.end(true);
            for (Consumer<Command> action : this.m_interruptActions) {
                action.accept(command);
            }
            this.m_scheduledCommands.remove(command);
            this.m_requirements.keySet().removeAll(command.getRequirements());
            this.m_watchdog.addEpoch(command.getName() + ".end(true)");
        }
    }

    public void cancelAll() {
        for (Command command : this.m_scheduledCommands.keySet().toArray(new Command[0])) {
            this.cancel(command);
        }
    }

    public double timeSinceScheduled(Command command) {
        CommandState commandState = this.m_scheduledCommands.get(command);
        if (commandState != null) {
            return commandState.timeSinceInitialized();
        }
        return -1.0;
    }

    public boolean isScheduled(Command ... commands) {
        return this.m_scheduledCommands.keySet().containsAll(Set.of((Object[])commands));
    }

    public Command requiring(Subsystem subsystem) {
        return this.m_requirements.get(subsystem);
    }

    public void disable() {
        this.m_disabled = true;
    }

    public void enable() {
        this.m_disabled = false;
    }

    public void onCommandInitialize(Consumer<Command> action) {
        this.m_initActions.add(action);
    }

    public void onCommandExecute(Consumer<Command> action) {
        this.m_executeActions.add(action);
    }

    public void onCommandInterrupt(Consumer<Command> action) {
        this.m_interruptActions.add(action);
    }

    public void onCommandFinish(Consumer<Command> action) {
        this.m_finishActions.add(action);
    }

    @Override
    public void initSendable(NTSendableBuilder builder) {
        builder.setSmartDashboardType("Scheduler");
        NetworkTableEntry namesEntry = builder.getEntry("Names");
        NetworkTableEntry idsEntry = builder.getEntry("Ids");
        NetworkTableEntry cancelEntry = builder.getEntry("Cancel");
        builder.setUpdateTable(() -> {
            if (namesEntry == null || idsEntry == null || cancelEntry == null) {
                return;
            }
            LinkedHashMap<Double, Command> ids = new LinkedHashMap<Double, Command>();
            for (Command command2 : this.m_scheduledCommands.keySet()) {
                ids.put(Double.valueOf(command2.hashCode()), command2);
            }
            double[] toCancel = cancelEntry.getDoubleArray(new double[0]);
            if (toCancel.length > 0) {
                for (double hash : toCancel) {
                    this.cancel((Command)ids.get(hash));
                    ids.remove(hash);
                }
                cancelEntry.setDoubleArray(new double[0]);
            }
            ArrayList arrayList = new ArrayList();
            ids.values().forEach(command -> names.add(command.getName()));
            namesEntry.setStringArray(arrayList.toArray(new String[0]));
            idsEntry.setNumberArray(ids.keySet().toArray(new Double[0]));
        });
    }
}

