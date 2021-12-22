/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.CTREPCMDataJNI;
import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.wpilibj.PneumaticsBase;
import edu.wpi.first.wpilibj.SensorUtil;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public class CTREPCMSim {
    private final int m_index;

    public CTREPCMSim() {
        this.m_index = SensorUtil.getDefaultCTREPCMModule();
    }

    public CTREPCMSim(int module) {
        this.m_index = module;
    }

    public CTREPCMSim(PneumaticsBase module) {
        this.m_index = module.getModuleNumber();
    }

    public CallbackStore registerSolenoidOutputCallback(int channel, NotifyCallback callback, boolean initialNotify) {
        int uid = CTREPCMDataJNI.registerSolenoidOutputCallback(this.m_index, channel, callback, initialNotify);
        return new CallbackStore(this.m_index, channel, uid, CTREPCMDataJNI::cancelSolenoidOutputCallback);
    }

    public boolean getSolenoidOutput(int channel) {
        return CTREPCMDataJNI.getSolenoidOutput(this.m_index, channel);
    }

    public void setSolenoidOutput(int channel, boolean solenoidOutput) {
        CTREPCMDataJNI.setSolenoidOutput(this.m_index, channel, solenoidOutput);
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = CTREPCMDataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, CTREPCMDataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return CTREPCMDataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        CTREPCMDataJNI.setInitialized(this.m_index, initialized);
    }

    public CallbackStore registerCompressorOnCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = CTREPCMDataJNI.registerCompressorOnCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, CTREPCMDataJNI::cancelCompressorOnCallback);
    }

    public boolean getCompressorOn() {
        return CTREPCMDataJNI.getCompressorOn(this.m_index);
    }

    public void setCompressorOn(boolean compressorOn) {
        CTREPCMDataJNI.setCompressorOn(this.m_index, compressorOn);
    }

    public CallbackStore registerClosedLoopEnabledCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = CTREPCMDataJNI.registerClosedLoopEnabledCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, CTREPCMDataJNI::cancelClosedLoopEnabledCallback);
    }

    public boolean getClosedLoopEnabled() {
        return CTREPCMDataJNI.getClosedLoopEnabled(this.m_index);
    }

    public void setClosedLoopEnabled(boolean closedLoopEnabled) {
        CTREPCMDataJNI.setClosedLoopEnabled(this.m_index, closedLoopEnabled);
    }

    public CallbackStore registerPressureSwitchCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = CTREPCMDataJNI.registerPressureSwitchCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, CTREPCMDataJNI::cancelPressureSwitchCallback);
    }

    public boolean getPressureSwitch() {
        return CTREPCMDataJNI.getPressureSwitch(this.m_index);
    }

    public void setPressureSwitch(boolean pressureSwitch) {
        CTREPCMDataJNI.setPressureSwitch(this.m_index, pressureSwitch);
    }

    public CallbackStore registerCompressorCurrentCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = CTREPCMDataJNI.registerCompressorCurrentCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, CTREPCMDataJNI::cancelCompressorCurrentCallback);
    }

    public double getCompressorCurrent() {
        return CTREPCMDataJNI.getCompressorCurrent(this.m_index);
    }

    public void setCompressorCurrent(double compressorCurrent) {
        CTREPCMDataJNI.setCompressorCurrent(this.m_index, compressorCurrent);
    }

    public void resetData() {
        CTREPCMDataJNI.resetData(this.m_index);
    }
}

