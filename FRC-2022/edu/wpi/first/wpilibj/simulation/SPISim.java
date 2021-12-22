/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.simulation.BufferCallback;
import edu.wpi.first.hal.simulation.ConstBufferCallback;
import edu.wpi.first.hal.simulation.NotifyCallback;
import edu.wpi.first.hal.simulation.SPIDataJNI;
import edu.wpi.first.hal.simulation.SpiReadAutoReceiveBufferCallback;
import edu.wpi.first.wpilibj.simulation.CallbackStore;

public class SPISim {
    private final int m_index;

    public SPISim() {
        this.m_index = 0;
    }

    public CallbackStore registerInitializedCallback(NotifyCallback callback, boolean initialNotify) {
        int uid = SPIDataJNI.registerInitializedCallback(this.m_index, callback, initialNotify);
        return new CallbackStore(this.m_index, uid, SPIDataJNI::cancelInitializedCallback);
    }

    public boolean getInitialized() {
        return SPIDataJNI.getInitialized(this.m_index);
    }

    public void setInitialized(boolean initialized) {
        SPIDataJNI.setInitialized(this.m_index, initialized);
    }

    public CallbackStore registerReadCallback(BufferCallback callback) {
        int uid = SPIDataJNI.registerReadCallback(this.m_index, callback);
        return new CallbackStore(this.m_index, uid, SPIDataJNI::cancelReadCallback);
    }

    public CallbackStore registerWriteCallback(ConstBufferCallback callback) {
        int uid = SPIDataJNI.registerWriteCallback(this.m_index, callback);
        return new CallbackStore(this.m_index, uid, SPIDataJNI::cancelWriteCallback);
    }

    public CallbackStore registerReadAutoReceiveBufferCallback(SpiReadAutoReceiveBufferCallback callback) {
        int uid = SPIDataJNI.registerReadAutoReceiveBufferCallback(this.m_index, callback);
        return new CallbackStore(this.m_index, uid, SPIDataJNI::cancelReadAutoReceiveBufferCallback);
    }

    public void resetData() {
        SPIDataJNI.resetData(this.m_index);
    }
}

