/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;

public abstract class SubsystemBase
implements Subsystem,
Sendable {
    public SubsystemBase() {
        String name = this.getClass().getSimpleName();
        name = name.substring(name.lastIndexOf(46) + 1);
        SendableRegistry.addLW((Sendable)this, name, name);
        CommandScheduler.getInstance().registerSubsystem(this);
    }

    public String getName() {
        return SendableRegistry.getName(this);
    }

    public void setName(String name) {
        SendableRegistry.setName(this, name);
    }

    public String getSubsystem() {
        return SendableRegistry.getSubsystem(this);
    }

    public void setSubsystem(String subsystem) {
        SendableRegistry.setSubsystem(this, subsystem);
    }

    public void addChild(String name, Sendable child) {
        SendableRegistry.addLW(child, this.getSubsystem(), name);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Subsystem");
        builder.addBooleanProperty(".hasDefault", () -> this.getDefaultCommand() != null, null);
        builder.addStringProperty(".default", () -> this.getDefaultCommand() != null ? this.getDefaultCommand().getName() : "none", null);
        builder.addBooleanProperty(".hasCommand", () -> this.getCurrentCommand() != null, null);
        builder.addStringProperty(".command", () -> this.getCurrentCommand() != null ? this.getCurrentCommand().getName() : "none", null);
    }
}

