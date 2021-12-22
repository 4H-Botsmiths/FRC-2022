/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.HashSet;
import java.util.Set;

public abstract class CommandBase
implements Sendable,
Command {
    protected Set<Subsystem> m_requirements = new HashSet<Subsystem>();

    protected CommandBase() {
        String name = this.getClass().getName();
        SendableRegistry.add(this, name.substring(name.lastIndexOf(46) + 1));
    }

    public final void addRequirements(Subsystem ... requirements) {
        this.m_requirements.addAll(Set.of((Object[])requirements));
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return this.m_requirements;
    }

    @Override
    public String getName() {
        return SendableRegistry.getName(this);
    }

    public void setName(String name) {
        SendableRegistry.setName(this, name);
    }

    public CommandBase withName(String name) {
        this.setName(name);
        return this;
    }

    public String getSubsystem() {
        return SendableRegistry.getSubsystem(this);
    }

    public void setSubsystem(String subsystem) {
        SendableRegistry.setSubsystem(this, subsystem);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Command");
        builder.addStringProperty(".name", this::getName, null);
        builder.addBooleanProperty("running", this::isScheduled, value -> {
            if (value) {
                if (!this.isScheduled()) {
                    this.schedule();
                }
            } else if (this.isScheduled()) {
                this.cancel();
            }
        });
        builder.addBooleanProperty(".isParented", () -> CommandGroupBase.getGroupedCommands().contains(this), null);
    }
}

