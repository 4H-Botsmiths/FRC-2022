/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.CANAPIJNI;
import edu.wpi.first.hal.CANData;
import edu.wpi.first.hal.HAL;
import java.io.Closeable;

public class CAN
implements Closeable {
    public static final int kTeamManufacturer = 8;
    public static final int kTeamDeviceType = 10;
    private final int m_handle;

    public CAN(int deviceId) {
        this.m_handle = CANAPIJNI.initializeCAN(8, deviceId, 10);
        HAL.report(79, deviceId + 1);
    }

    public CAN(int deviceId, int deviceManufacturer, int deviceType) {
        this.m_handle = CANAPIJNI.initializeCAN(deviceManufacturer, deviceId, deviceType);
        HAL.report(79, deviceId + 1);
    }

    @Override
    public void close() {
        if (this.m_handle != 0) {
            CANAPIJNI.cleanCAN(this.m_handle);
        }
    }

    public void writePacket(byte[] data, int apiId) {
        CANAPIJNI.writeCANPacket(this.m_handle, data, apiId);
    }

    public void writePacketRepeating(byte[] data, int apiId, int repeatMs) {
        CANAPIJNI.writeCANPacketRepeating(this.m_handle, data, apiId, repeatMs);
    }

    public void writeRTRFrame(int length, int apiId) {
        CANAPIJNI.writeCANRTRFrame(this.m_handle, length, apiId);
    }

    public int writePacketNoThrow(byte[] data, int apiId) {
        return CANAPIJNI.writeCANPacketNoThrow(this.m_handle, data, apiId);
    }

    public int writePacketRepeatingNoThrow(byte[] data, int apiId, int repeatMs) {
        return CANAPIJNI.writeCANPacketRepeatingNoThrow(this.m_handle, data, apiId, repeatMs);
    }

    public int writeRTRFrameNoThrow(int length, int apiId) {
        return CANAPIJNI.writeCANRTRFrameNoThrow(this.m_handle, length, apiId);
    }

    public void stopPacketRepeating(int apiId) {
        CANAPIJNI.stopCANPacketRepeating(this.m_handle, apiId);
    }

    public boolean readPacketNew(int apiId, CANData data) {
        return CANAPIJNI.readCANPacketNew(this.m_handle, apiId, data);
    }

    public boolean readPacketLatest(int apiId, CANData data) {
        return CANAPIJNI.readCANPacketLatest(this.m_handle, apiId, data);
    }

    public boolean readPacketTimeout(int apiId, int timeoutMs, CANData data) {
        return CANAPIJNI.readCANPacketTimeout(this.m_handle, apiId, timeoutMs, data);
    }
}

