/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.BufferCallback;
import edu.wpi.first.hal.simulation.ConstBufferCallback;
import edu.wpi.first.hal.simulation.I2CDataJNI;
import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public class I2CSim {
    private final int m_index;

    public I2CSim(int index) {
        this.m_index = index;
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = I2CDataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, I2CDataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return I2CDataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        I2CDataJNI.setInitialized(this.m_index, initialized);
    }

    public CallbackStore registerReadCallback(BufferCallback callback) {
        int uid = I2CDataJNI.registerReadCallback(this.m_index, callback);
        return new CallbackStore(this.m_index, uid, I2CDataJNI::cancelReadCallback);
    }

    public CallbackStore registerWriteCallback(ConstBufferCallback callback) {
        int uid = I2CDataJNI.registerWriteCallback(this.m_index, callback);
        return new CallbackStore(this.m_index, uid, I2CDataJNI::cancelWriteCallback);
    }

    public void resetData() {
        I2CDataJNI.resetData(this.m_index);
    }
}

