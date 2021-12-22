/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.util.AllocationException;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.CompressorConfigType;
import edu.wpi.first.wpilibj.PneumaticsBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Compressor
implements Sendable,
AutoCloseable {
    private PneumaticsBase m_module;

    public Compressor(int module, PneumaticsModuleType moduleType) {
        this.m_module = PneumaticsBase.getForType(module, moduleType);
        if (!this.m_module.reserveCompressor()) {
            this.m_module.close();
            throw new AllocationException("Compressor already allocated");
        }
        this.m_module.enableCompressorDigital();
        HAL.report(10, module + 1);
        SendableRegistry.addLW((Sendable)this, "Compressor", module);
    }

    public Compressor(PneumaticsModuleType moduleType) {
        this(PneumaticsBase.getDefaultForType(moduleType), moduleType);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        this.m_module.unreserveCompressor();
        this.m_module.close();
        this.m_module = null;
    }

    @Deprecated(since="2022", forRemoval=true)
    public void start() {
        this.enableDigital();
    }

    @Deprecated(since="2022", forRemoval=true)
    public void stop() {
        this.disable();
    }

    public boolean enabled() {
        return this.m_module.getCompressor();
    }

    public boolean getPressureSwitchValue() {
        return this.m_module.getPressureSwitch();
    }

    public double getCurrent() {
        return this.m_module.getCompressorCurrent();
    }

    public double getAnalogVoltage() {
        return this.m_module.getAnalogVoltage(0);
    }

    public void disable() {
        this.m_module.disableCompressor();
    }

    public void enableDigital() {
        this.m_module.enableCompressorDigital();
    }

    public void enableAnalog(double minAnalogVoltage, double maxAnalogVoltage) {
        this.m_module.enableCompressorAnalog(minAnalogVoltage, maxAnalogVoltage);
    }

    public void enableHybrid(double minAnalogVoltage, double maxAnalogVoltage) {
        this.m_module.enableCompressorHybrid(minAnalogVoltage, maxAnalogVoltage);
    }

    public CompressorConfigType getConfigType() {
        return this.m_module.getCompressorConfigType();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Compressor");
        builder.addBooleanProperty("Enabled", this::enabled, null);
        builder.addBooleanProperty("Pressure switch", this::getPressureSwitchValue, null);
    }
}

