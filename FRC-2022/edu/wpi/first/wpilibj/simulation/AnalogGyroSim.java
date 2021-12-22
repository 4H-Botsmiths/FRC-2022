/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.AnalogGyroDataJNI;
import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public class AnalogGyroSim {
    private final int m_index;

    public AnalogGyroSim(AnalogGyro gyro) {
        this.m_index = gyro.getAnalogInput().getChannel();
    }

    public AnalogGyroSim(int channel) {
        this.m_index = channel;
    }

    public CallbackStore registerAngleCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogGyroDataJNI.registerAngleCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogGyroDataJNI::cancelAngleCallback);
    }

    public double getAngle() {
        return AnalogGyroDataJNI.getAngle(this.m_index);
    }

    public void setAngle(double angle) {
        AnalogGyroDataJNI.setAngle(this.m_index, angle);
    }

    public CallbackStore registerRateCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogGyroDataJNI.registerRateCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogGyroDataJNI::cancelRateCallback);
    }

    public double getRate() {
        return AnalogGyroDataJNI.getRate(this.m_index);
    }

    public void setRate(double rate) {
        AnalogGyroDataJNI.setRate(this.m_index, rate);
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AnalogGyroDataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AnalogGyroDataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return AnalogGyroDataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        AnalogGyroDataJNI.setInitialized(this.m_index, initialized);
    }

    public void resetData() {
        AnalogGyroDataJNI.resetData(this.m_index);
    }
}

