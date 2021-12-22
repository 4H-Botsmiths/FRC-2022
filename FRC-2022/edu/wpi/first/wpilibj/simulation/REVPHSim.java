/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.hal.simulation.REVPHDataJNI;
import edu.wpi.first.wpilibj.PneumaticsBase;
import edu.wpi.first.wpilibj.SensorUtil;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public class REVPHSim {
    private final int m_index;

    public REVPHSim() {
        this.m_index = SensorUtil.getDefaultREVPHModule();
    }

    public REVPHSim(int module) {
        this.m_index = module;
    }

    public REVPHSim(PneumaticsBase module) {
        this.m_index = module.getModuleNumber();
    }

    public CallbackStore registerSolenoidOutputCallback(int channel, NotifyCallback callback, boolean initialNotify) {
        int uid = REVPHDataJNI.registerSolenoidOutputCallback(this.m_index, channel, callback, initialNotify);
        return new CallbackStore(this.m_index, channel, uid, REVPHDataJNI::cancelSolenoidOutputCallback);
    }

    public boolean getSolenoidOutput(int channel) {
        return REVPHDataJNI.getSolenoidOutput(this.m_index, channel);
    }

    public void setSolenoidOutput(int channel, boolean solenoidOutput) {
        REVPHDataJNI.setSolenoidOutput(this.m_index, channel, solenoidOutput);
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = REVPHDataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, REVPHDataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return REVPHDataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        REVPHDataJNI.setInitialized(this.m_index, initialized);
    }

    public CallbackStore registerCompressorOnCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = REVPHDataJNI.registerCompressorOnCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, REVPHDataJNI::cancelCompressorOnCallback);
    }

    public boolean getCompressorOn() {
        return REVPHDataJNI.getCompressorOn(this.m_index);
    }

    public void setCompressorOn(boolean compressorOn) {
        REVPHDataJNI.setCompressorOn(this.m_index, compressorOn);
    }

    public CallbackStore registerCompressorConfigTypeCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = REVPHDataJNI.registerCompressorConfigTypeCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, REVPHDataJNI::cancelCompressorConfigTypeCallback);
    }

    public int getCompressorConfigType() {
        return REVPHDataJNI.getCompressorConfigType(this.m_index);
    }

    public void setCompressorConfigType(int compressorConfigType) {
        REVPHDataJNI.setCompressorConfigType(this.m_index, compressorConfigType);
    }

    public CallbackStore registerPressureSwitchCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = REVPHDataJNI.registerPressureSwitchCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, REVPHDataJNI::cancelPressureSwitchCallback);
    }

    public boolean getPressureSwitch() {
        return REVPHDataJNI.getPressureSwitch(this.m_index);
    }

    public void setPressureSwitch(boolean pressureSwitch) {
        REVPHDataJNI.setPressureSwitch(this.m_index, pressureSwitch);
    }

    public CallbackStore registerCompressorCurrentCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = REVPHDataJNI.registerCompressorCurrentCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, REVPHDataJNI::cancelCompressorCurrentCallback);
    }

    public double getCompressorCurrent() {
        return REVPHDataJNI.getCompressorCurrent(this.m_index);
    }

    public void setCompressorCurrent(double compressorCurrent) {
        REVPHDataJNI.setCompressorCurrent(this.m_index, compressorCurrent);
    }

    public void resetData() {
        REVPHDataJNI.resetData(this.m_index);
    }
}

