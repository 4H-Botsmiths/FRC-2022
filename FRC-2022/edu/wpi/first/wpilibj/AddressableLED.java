/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.AddressableLEDJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.PWMJNI;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class AddressableLED
implements AutoCloseable {
    private final int m_pwmHandle;
    private final int m_handle;

    public AddressableLED(int port) {
        this.m_pwmHandle = PWMJNI.initializePWMPort(HAL.getPort((byte)port));
        this.m_handle = AddressableLEDJNI.initialize(this.m_pwmHandle);
        HAL.report(92, port + 1);
    }

    @Override
    public void close() {
        if (this.m_handle != 0) {
            AddressableLEDJNI.free(this.m_handle);
        }
        if (this.m_pwmHandle != 0) {
            PWMJNI.freePWMPort(this.m_pwmHandle);
        }
    }

    public void setLength(int length) {
        AddressableLEDJNI.setLength(this.m_handle, length);
    }

    public void setData(AddressableLEDBuffer buffer) {
        AddressableLEDJNI.setData(this.m_handle, buffer.m_buffer);
    }

    public void setBitTiming(int lowTime0NanoSeconds, int highTime0NanoSeconds, int lowTime1NanoSeconds, int highTime1NanoSeconds) {
        AddressableLEDJNI.setBitTiming(this.m_handle, lowTime0NanoSeconds, highTime0NanoSeconds, lowTime1NanoSeconds, highTime1NanoSeconds);
    }

    public void setSyncTime(int syncTimeMicroSeconds) {
        AddressableLEDJNI.setSyncTime(this.m_handle, syncTimeMicroSeconds);
    }

    public void start() {
        AddressableLEDJNI.start(this.m_handle);
    }

    public void stop() {
        AddressableLEDJNI.stop(this.m_handle);
    }
}

