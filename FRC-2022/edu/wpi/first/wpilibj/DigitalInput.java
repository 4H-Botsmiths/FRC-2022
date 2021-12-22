/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.DIOJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.SensorUtil;

public class DigitalInput
extends DigitalSource
implements Sendable {
    private final int m_channel;
    private int m_handle;

    public DigitalInput(int channel) {
        SensorUtil.checkDigitalChannel(channel);
        this.m_channel = channel;
        this.m_handle = DIOJNI.initializeDIOPort(HAL.getPort((byte)channel), true);
        HAL.report(13, channel + 1);
        SendableRegistry.addLW((Sendable)this, "DigitalInput", channel);
    }

    @Override
    public void close() {
        super.close();
        SendableRegistry.remove(this);
        DIOJNI.freeDIOPort(this.m_handle);
        this.m_handle = 0;
    }

    public boolean get() {
        return DIOJNI.getDIO(this.m_handle);
    }

    @Override
    public int getChannel() {
        return this.m_channel;
    }

    @Override
    public int getAnalogTriggerTypeForRouting() {
        return 0;
    }

    @Override
    public boolean isAnalogTrigger() {
        return false;
    }

    @Override
    public int getPortHandleForRouting() {
        return this.m_handle;
    }

    public void setSimDevice(SimDevice device) {
        DIOJNI.setDIOSimDevice(this.m_handle, device.getNativeHandle());
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Digital Input");
        builder.addBooleanProperty("Value", this::get, null);
    }
}

