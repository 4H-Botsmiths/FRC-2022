/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.hal.simulation.SPIAccelerometerDataJNI;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public class SPIAccelerometerSim {
    private final int m_index;

    public SPIAccelerometerSim(int index) {
        this.m_index = index;
    }

    public CallbackStore registerActiveCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = SPIAccelerometerDataJNI.registerActiveCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, SPIAccelerometerDataJNI::cancelActiveCallback);
    }

    public boolean getActive() {
        return SPIAccelerometerDataJNI.getActive(this.m_index);
    }

    public void setActive(boolean active) {
        SPIAccelerometerDataJNI.setActive(this.m_index, active);
    }

    public CallbackStore registerRangeCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = SPIAccelerometerDataJNI.registerRangeCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, SPIAccelerometerDataJNI::cancelRangeCallback);
    }

    public int getRange() {
        return SPIAccelerometerDataJNI.getRange(this.m_index);
    }

    public void setRange(int range) {
        SPIAccelerometerDataJNI.setRange(this.m_index, range);
    }

    public CallbackStore registerXCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = SPIAccelerometerDataJNI.registerXCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, SPIAccelerometerDataJNI::cancelXCallback);
    }

    public double getX() {
        return SPIAccelerometerDataJNI.getX(this.m_index);
    }

    public void setX(double x) {
        SPIAccelerometerDataJNI.setX(this.m_index, x);
    }

    public CallbackStore registerYCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = SPIAccelerometerDataJNI.registerYCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, SPIAccelerometerDataJNI::cancelYCallback);
    }

    public double getY() {
        return SPIAccelerometerDataJNI.getY(this.m_index);
    }

    public void setY(double y) {
        SPIAccelerometerDataJNI.setY(this.m_index, y);
    }

    public CallbackStore registerZCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = SPIAccelerometerDataJNI.registerZCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, SPIAccelerometerDataJNI::cancelZCallback);
    }

    public double getZ() {
        return SPIAccelerometerDataJNI.getZ(this.m_index);
    }

    public void setZ(double z) {
        SPIAccelerometerDataJNI.setZ(this.m_index, z);
    }

    public void resetData() {
        SPIAccelerometerDataJNI.resetData(this.m_index);
    }
}

