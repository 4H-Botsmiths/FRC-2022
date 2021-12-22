/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.AnalogInDataJNI;
import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public class AnalogInputSim {
    private final int m_index;

    public AnalogInputSim(AnalogInput analogInput) {
        this.m_index = analogInput.getChannel();
    }

    public AnalogInputSim(int channel) {
        this.m_index = channel;
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogInDataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogInDataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return AnalogInDataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        AnalogInDataJNI.setInitialized(this.m_index, initialized);
    }

    public CallbackStore registerAverageBitsCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogInDataJNI.registerAverageBitsCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogInDataJNI::cancelAverageBitsCallback);
    }

    public int getAverageBits() {
        return AnalogInDataJNI.getAverageBits(this.m_index);
    }

    public void setAverageBits(int averageBits) {
        AnalogInDataJNI.setAverageBits(this.m_index, averageBits);
    }

    public CallbackStore registerOversampleBitsCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogInDataJNI.registerOversampleBitsCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogInDataJNI::cancelOversampleBitsCallback);
    }

    public int getOversampleBits() {
        return AnalogInDataJNI.getOversampleBits(this.m_index);
    }

    public void setOversampleBits(int oversampleBits) {
        AnalogInDataJNI.setOversampleBits(this.m_index, oversampleBits);
    }

    public CallbackStore registerVoltageCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogInDataJNI.registerVoltageCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogInDataJNI::cancelVoltageCallback);
    }

    public double getVoltage() {
        return AnalogInDataJNI.getVoltage(this.m_index);
    }

    public void setVoltage(double voltage) {
        AnalogInDataJNI.setVoltage(this.m_index, voltage);
    }

    public CallbackStore registerAccumulatorInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogInDataJNI.registerAccumulatorInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogInDataJNI::cancelAccumulatorInitializedCallback);
    }

    public boolean getAccumulatorInitialized() {
        return AnalogInDataJNI.getAccumulatorInitialized(this.m_index);
    }

    public void setAccumulatorInitialized(boolean accumulatorInitialized) {
        AnalogInDataJNI.setAccumulatorInitialized(this.m_index, accumulatorInitialized);
    }

    public CallbackStore registerAccumulatorValueCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogInDataJNI.registerAccumulatorValueCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogInDataJNI::cancelAccumulatorValueCallback);
    }

    public long getAccumulatorValue() {
        return AnalogInDataJNI.getAccumulatorValue(this.m_index);
    }

    public void setAccumulatorValue(long accumulatorValue) {
        AnalogInDataJNI.setAccumulatorValue(this.m_index, accumulatorValue);
    }

    public CallbackStore registerAccumulatorCountCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogInDataJNI.registerAccumulatorCountCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogInDataJNI::cancelAccumulatorCountCallback);
    }

    public long getAccumulatorCount() {
        return AnalogInDataJNI.getAccumulatorCount(this.m_index);
    }

    public void setAccumulatorCount(long accumulatorCount) {
        AnalogInDataJNI.setAccumulatorCount(this.m_index, accumulatorCount);
    }

    public CallbackStore registerAccumulatorCenterCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogInDataJNI.registerAccumulatorCenterCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogInDataJNI::cancelAccumulatorCenterCallback);
    }

    public int getAccumulatorCenter() {
        return AnalogInDataJNI.getAccumulatorCenter(this.m_index);
    }

    public void setAccumulatorCenter(int accumulatorCenter) {
        AnalogInDataJNI.setAccumulatorCenter(this.m_index, accumulatorCenter);
    }

    public CallbackStore registerAccumulatorDeadbandCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogInDataJNI.registerAccumulatorDeadbandCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogInDataJNI::cancelAccumulatorDeadbandCallback);
    }

    public int getAccumulatorDeadband() {
        return AnalogInDataJNI.getAccumulatorDeadband(this.m_index);
    }

    public void setAccumulatorDeadband(int accumulatorDeadband) {
        AnalogInDataJNI.setAccumulatorDeadband(this.m_index, accumulatorDeadband);
    }

    public void resetData() {
        AnalogInDataJNI.resetData(this.m_index);
    }
}

