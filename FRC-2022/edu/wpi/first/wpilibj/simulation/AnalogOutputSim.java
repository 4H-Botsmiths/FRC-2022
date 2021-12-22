/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.AnalogOutDataJNI;
import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public class AnalogOutputSim {
    private final int m_index;

    public AnalogOutputSim(AnalogOutput analogOutput) {
        this.m_index = analogOutput.getChannel();
    }

    public AnalogOutputSim(int channel) {
        this.m_index = channel;
    }

    public CallbackStore registerVoltageCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogOutDataJNI.registerVoltageCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogOutDataJNI::cancelVoltageCallback);
    }

    public double getVoltage() {
        return AnalogOutDataJNI.getVoltage(this.m_index);
    }

    public void setVoltage(double voltage) {
        AnalogOutDataJNI.setVoltage(this.m_index, voltage);
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogOutDataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogOutDataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return AnalogOutDataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        AnalogOutDataJNI.setInitialized(this.m_index, initialized);
    }

    public void resetData() {
        AnalogOutDataJNI.resetData(this.m_index);
    }
}

