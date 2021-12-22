/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.DIODataJNI;
import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public class DIOSim {
    private final int m_index;

    public DIOSim(DigitalInput input) {
        this.m_index = input.getChannel();
    }

    public DIOSim(DigitalOutput output) {
        this.m_index = output.getChannel();
    }

    public DIOSim(int channel) {
        this.m_index = channel;
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DIODataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, DIODataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return DIODataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        DIODataJNI.setInitialized(this.m_index, initialized);
    }

    public CallbackStore registerValueCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DIODataJNI.registerValueCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, DIODataJNI::cancelValueCallback);
    }

    public boolean getValue() {
        return DIODataJNI.getValue(this.m_index);
    }

    public void setValue(boolean value) {
        DIODataJNI.setValue(this.m_index, value);
    }

    public CallbackStore registerPulseLengthCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DIODataJNI.registerPulseLengthCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, DIODataJNI::cancelPulseLengthCallback);
    }

    public double getPulseLength() {
        return DIODataJNI.getPulseLength(this.m_index);
    }

    public void setPulseLength(double pulseLength) {
        DIODataJNI.setPulseLength(this.m_index, pulseLength);
    }

    public CallbackStore registerIsInputCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DIODataJNI.registerIsInputCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, DIODataJNI::cancelIsInputCallback);
    }

    public boolean getIsInput() {
        return DIODataJNI.getIsInput(this.m_index);
    }

    public void setIsInput(boolean isInput) {
        DIODataJNI.setIsInput(this.m_index, isInput);
    }

    public CallbackStore registerFilterIndexCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = DIODataJNI.registerFilterIndexCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, DIODataJNI::cancelFilterIndexCallback);
    }

    public int getFilterIndex() {
        return DIODataJNI.getFilterIndex(this.m_index);
    }

    public void setFilterIndex(int filterIndex) {
        DIODataJNI.setFilterIndex(this.m_index, filterIndex);
    }

    public void resetData() {
        DIODataJNI.resetData(this.m_index);
    }
}

