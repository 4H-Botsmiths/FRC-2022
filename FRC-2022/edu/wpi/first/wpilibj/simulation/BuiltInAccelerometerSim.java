/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.AccelerometerDataJNI;
import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public class BuiltInAccelerometerSim {
    private final int m_index;

    public BuiltInAccelerometerSim() {
        this.m_index = 0;
    }

    public BuiltInAccelerometerSim(BuiltInAccelerometer accel) {
        this.m_index = 0;
    }

    public CallbackStore registerActiveCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AccelerometerDataJNI.registerActiveCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AccelerometerDataJNI::cancelActiveCallback);
    }

    public boolean getActive() {
        return AccelerometerDataJNI.getActive(this.m_index);
    }

    public void setActive(boolean active) {
        AccelerometerDataJNI.setActive(this.m_index, active);
    }

    public CallbackStore registerRangeCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AccelerometerDataJNI.registerRangeCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AccelerometerDataJNI::cancelRangeCallback);
    }

    public int getRange() {
        return AccelerometerDataJNI.getRange(this.m_index);
    }

    public void setRange(int range) {
        AccelerometerDataJNI.setRange(this.m_index, range);
    }

    public CallbackStore registerXCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AccelerometerDataJNI.registerXCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AccelerometerDataJNI::cancelXCallback);
    }

    public double getX() {
        return AccelerometerDataJNI.getX(this.m_index);
    }

    public void setX(double x) {
        AccelerometerDataJNI.setX(this.m_index, x);
    }

    public CallbackStore registerYCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AccelerometerDataJNI.registerYCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AccelerometerDataJNI::cancelYCallback);
    }

    public double getY() {
        return AccelerometerDataJNI.getY(this.m_index);
    }

    public void setY(double y) {
        AccelerometerDataJNI.setY(this.m_index, y);
    }

    public CallbackStore registerZCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = AccelerometerDataJNI.registerZCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, AccelerometerDataJNI::cancelZCallback);
    }

    public double getZ() {
        return AccelerometerDataJNI.getZ(this.m_index);
    }

    public void setZ(double z) {
        AccelerometerDataJNI.setZ(this.m_index, z);
    }

    public void resetData() {
        AccelerometerDataJNI.resetData(this.m_index);
    }
}

