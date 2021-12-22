/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.PowerDistributionFaults;
import edu.wpi.first.hal.PowerDistributionJNI;
import edu.wpi.first.hal.PowerDistributionStickyFaults;
import edu.wpi.first.hal.PowerDistributionVersion;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;

public class PowerDistribution
implements Sendable,
AutoCloseable {
    private final int m_handle;
    private final int m_module;
    public static final int kDefaultModule = -1;

    public PowerDistribution(int module, ModuleType moduleType) {
        this.m_handle = PowerDistributionJNI.initialize(module, moduleType.value);
        this.m_module = PowerDistributionJNI.getModuleNumber(this.m_handle);
        HAL.report(59, this.m_module + 1);
        SendableRegistry.addLW((Sendable)this, "PowerDistribution", this.m_module);
    }

    public PowerDistribution() {
        this.m_handle = PowerDistributionJNI.initialize(-1, 0);
        this.m_module = PowerDistributionJNI.getModuleNumber(this.m_handle);
        HAL.report(59, this.m_module + 1);
        SendableRegistry.addLW((Sendable)this, "PowerDistribution", this.m_module);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
    }

    public int getNumChannels() {
        return PowerDistributionJNI.getNumChannels(this.m_handle);
    }

    public double getVoltage() {
        return PowerDistributionJNI.getVoltage(this.m_handle);
    }

    public double getTemperature() {
        return PowerDistributionJNI.getTemperature(this.m_handle);
    }

    public double getCurrent(int channel) {
        double current = PowerDistributionJNI.getChannelCurrent(this.m_handle, channel);
        return current;
    }

    public double getTotalCurrent() {
        return PowerDistributionJNI.getTotalCurrent(this.m_handle);
    }

    public double getTotalPower() {
        return PowerDistributionJNI.getTotalPower(this.m_handle);
    }

    public double getTotalEnergy() {
        return PowerDistributionJNI.getTotalEnergy(this.m_handle);
    }

    public void resetTotalEnergy() {
        PowerDistributionJNI.resetTotalEnergy(this.m_handle);
    }

    public void clearStickyFaults() {
        PowerDistributionJNI.clearStickyFaults(this.m_handle);
    }

    public int getModule() {
        return this.m_module;
    }

    public boolean getSwitchableChannel() {
        return PowerDistributionJNI.getSwitchableChannel(this.m_handle);
    }

    public void setSwitchableChannel(boolean enabled) {
        PowerDistributionJNI.setSwitchableChannel(this.m_handle, enabled);
    }

    PowerDistributionVersion getVersion() {
        return PowerDistributionJNI.getVersion(this.m_handle);
    }

    PowerDistributionFaults getFaults() {
        return PowerDistributionJNI.getFaults(this.m_handle);
    }

    PowerDistributionStickyFaults getStickyFaults() {
        return PowerDistributionJNI.getStickyFaults(this.m_handle);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("PowerDistribution");
        int numChannels = this.getNumChannels();
        for (int i = 0; i < numChannels; ++i) {
            int chan = i;
            builder.addDoubleProperty("Chan" + i, () -> PowerDistributionJNI.getChannelCurrentNoError(this.m_handle, chan), null);
        }
        builder.addDoubleProperty("Voltage", () -> PowerDistributionJNI.getVoltageNoError(this.m_handle), null);
        builder.addDoubleProperty("TotalCurrent", () -> PowerDistributionJNI.getTotalCurrent(this.m_handle), null);
        builder.addBooleanProperty("SwitchableChannel", () -> PowerDistributionJNI.getSwitchableChannelNoError(this.m_handle), value -> PowerDistributionJNI.setSwitchableChannel(this.m_handle, value));
    }

    public static enum ModuleType {
        kCTRE(1),
        kRev(2);

        public final int value;

        private ModuleType(int value) {
            this.value = value;
        }
    }
}

