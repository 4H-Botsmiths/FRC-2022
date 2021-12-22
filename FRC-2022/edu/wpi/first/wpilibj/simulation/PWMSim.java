/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.hal.simulation.PWMDataJNI;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public class PWMSim {
    private final int m_index;

    public PWMSim(PWM pwm) {
        this.m_index = pwm.getChannel();
    }

    public PWMSim(int channel) {
        this.m_index = channel;
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = PWMDataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, PWMDataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return PWMDataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        PWMDataJNI.setInitialized(this.m_index, initialized);
    }

    public CallbackStore registerRawValueCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = PWMDataJNI.registerRawValueCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, PWMDataJNI::cancelRawValueCallback);
    }

    public int getRawValue() {
        return PWMDataJNI.getRawValue(this.m_index);
    }

    public void setRawValue(int rawValue) {
        PWMDataJNI.setRawValue(this.m_index, rawValue);
    }

    public CallbackStore registerSpeedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = PWMDataJNI.registerSpeedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, PWMDataJNI::cancelSpeedCallback);
    }

    public double getSpeed() {
        return PWMDataJNI.getSpeed(this.m_index);
    }

    public void setSpeed(double speed) {
        PWMDataJNI.setSpeed(this.m_index, speed);
    }

    public CallbackStore registerPositionCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = PWMDataJNI.registerPositionCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, PWMDataJNI::cancelPositionCallback);
    }

    public double getPosition() {
        return PWMDataJNI.getPosition(this.m_index);
    }

    public void setPosition(double position) {
        PWMDataJNI.setPosition(this.m_index, position);
    }

    public CallbackStore registerPeriodScaleCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = PWMDataJNI.registerPeriodScaleCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, PWMDataJNI::cancelPeriodScaleCallback);
    }

    public int getPeriodScale() {
        return PWMDataJNI.getPeriodScale(this.m_index);
    }

    public void setPeriodScale(int periodScale) {
        PWMDataJNI.setPeriodScale(this.m_index, periodScale);
    }

    public CallbackStore registerZeroLatchCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = PWMDataJNI.registerZeroLatchCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, PWMDataJNI::cancelZeroLatchCallback);
    }

    public boolean getZeroLatch() {
        return PWMDataJNI.getZeroLatch(this.m_index);
    }

    public void setZeroLatch(boolean zeroLatch) {
        PWMDataJNI.setZeroLatch(this.m_index, zeroLatch);
    }

    public void resetData() {
        PWMDataJNI.resetData(this.m_index);
    }
}

