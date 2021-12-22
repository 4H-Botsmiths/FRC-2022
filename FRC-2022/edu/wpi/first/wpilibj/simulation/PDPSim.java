/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.hal.simulation.PowerDistributionDataJNI;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public class PDPSim {
    private final int m_index;

    public PDPSim() {
        this.m_index = 0;
    }

    public PDPSim(int module) {
        this.m_index = module;
    }

    public PDPSim(PowerDistribution pdp) {
        this.m_index = pdp.getModule();
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = PowerDistributionDataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, PowerDistributionDataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return PowerDistributionDataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        PowerDistributionDataJNI.setInitialized(this.m_index, initialized);
    }

    public CallbackStore registerTemperatureCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = PowerDistributionDataJNI.registerTemperatureCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, PowerDistributionDataJNI::cancelTemperatureCallback);
    }

    public double getTemperature() {
        return PowerDistributionDataJNI.getTemperature(this.m_index);
    }

    public void setTemperature(double temperature) {
        PowerDistributionDataJNI.setTemperature(this.m_index, temperature);
    }

    public CallbackStore registerVoltageCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = PowerDistributionDataJNI.registerVoltageCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, PowerDistributionDataJNI::cancelVoltageCallback);
    }

    public double getVoltage() {
        return PowerDistributionDataJNI.getVoltage(this.m_index);
    }

    public void setVoltage(double voltage) {
        PowerDistributionDataJNI.setVoltage(this.m_index, voltage);
    }

    public CallbackStore registerCurrentCallback(int channel, NotifyCallback callback, boolean initialNotify) {
        int uid = PowerDistributionDataJNI.registerCurrentCallback(this.m_index, channel, callback, initialNotify);
        return new CallbackStore(this.m_index, channel, uid, PowerDistributionDataJNI::cancelCurrentCallback);
    }

    public double getCurrent(int channel) {
        return PowerDistributionDataJNI.getCurrent(this.m_index, channel);
    }

    public void setCurrent(int channel, double current) {
        PowerDistributionDataJNI.setCurrent(this.m_index, channel, current);
    }

    public void resetData() {
        PowerDistributionDataJNI.resetData(this.m_index);
    }
}

