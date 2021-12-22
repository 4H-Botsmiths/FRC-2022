/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.util.AllocationException;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.PneumaticsBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Solenoid
implements Sendable,
AutoCloseable {
    private final int m_mask;
    private final int m_channel;
    private PneumaticsBase m_module;

    public Solenoid(PneumaticsModuleType moduleType, int channel) {
        this(PneumaticsBase.getDefaultForType(moduleType), moduleType, channel);
    }

    public Solenoid(int module, PneumaticsModuleType moduleType, int channel) {
        this.m_module = PneumaticsBase.getForType(module, moduleType);
        this.m_mask = 1 << channel;
        this.m_channel = channel;
        if (!this.m_module.checkSolenoidChannel(channel)) {
            this.m_module.close();
            throw new IllegalArgumentException("Channel " + channel + " out of range");
        }
        if (this.m_module.checkAndReserveSolenoids(this.m_mask) != 0) {
            this.m_module.close();
            throw new AllocationException("Solenoid already allocated");
        }
        HAL.report(34, channel + 1, this.m_module.getModuleNumber() + 1);
        SendableRegistry.addLW(this, "Solenoid", this.m_module.getModuleNumber(), channel);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        this.m_module.unreserveSolenoids(this.m_mask);
        this.m_module.close();
        this.m_module = null;
    }

    public void set(boolean on) {
        int value = on ? 0xFFFF & this.m_mask : 0;
        this.m_module.setSolenoids(this.m_mask, value);
    }

    public boolean get() {
        int currentAll = this.m_module.getSolenoids();
        return (currentAll & this.m_mask) != 0;
    }

    public void toggle() {
        this.set(!this.get());
    }

    public int getChannel() {
        return this.m_channel;
    }

    public boolean isDisabled() {
        return (this.m_module.getSolenoidDisabledList() & this.m_mask) != 0;
    }

    public void setPulseDuration(double durationSeconds) {
        long durationMS = (long)(durationSeconds * 1000.0);
        this.m_module.setOneShotDuration(this.m_channel, (int)durationMS);
    }

    public void startPulse() {
        this.m_module.fireOneShot(this.m_channel);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Solenoid");
        builder.setActuator(true);
        builder.setSafeState(() -> this.set(false));
        builder.addBooleanProperty("Value", this::get, this::set);
    }
}

