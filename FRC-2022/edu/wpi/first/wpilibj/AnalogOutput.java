/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.AnalogJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.SensorUtil;

public class AnalogOutput
implements Sendable,
AutoCloseable {
    private int m_port;
    private int m_channel;

    public AnalogOutput(int channel) {
        SensorUtil.checkAnalogOutputChannel(channel);
        this.m_channel = channel;
        int portHandle = HAL.getPort((byte)channel);
        this.m_port = AnalogJNI.initializeAnalogOutputPort(portHandle);
        HAL.report(49, channel + 1);
        SendableRegistry.addLW((Sendable)this, "AnalogOutput", channel);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        AnalogJNI.freeAnalogOutputPort(this.m_port);
        this.m_port = 0;
        this.m_channel = 0;
    }

    public int getChannel() {
        return this.m_channel;
    }

    public void setVoltage(double voltage) {
        AnalogJNI.setAnalogOutput(this.m_port, voltage);
    }

    public double getVoltage() {
        return AnalogJNI.getAnalogOutput(this.m_port);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Analog Output");
        builder.addDoubleProperty("Value", this::getVoltage, this::setVoltage);
    }
}

