/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.util.ErrorMessages;

public class AnalogAccelerometer
implements Sendable,
AutoCloseable {
    private AnalogInput m_analogChannel;
    private double m_voltsPerG = 1.0;
    private double m_zeroGVoltage = 2.5;
    private final boolean m_allocatedChannel;

    private void initAccelerometer() {
        HAL.report(4, this.m_analogChannel.getChannel() + 1);
        SendableRegistry.addLW((Sendable)this, "Accelerometer", this.m_analogChannel.getChannel());
    }

    public AnalogAccelerometer(int channel) {
        this(new AnalogInput(channel), true);
        SendableRegistry.addChild(this, this.m_analogChannel);
    }

    public AnalogAccelerometer(AnalogInput channel) {
        this(channel, false);
    }

    private AnalogAccelerometer(AnalogInput channel, boolean allocatedChannel) {
        ErrorMessages.requireNonNullParam(channel, "channel", "AnalogAccelerometer");
        this.m_allocatedChannel = allocatedChannel;
        this.m_analogChannel = channel;
        this.initAccelerometer();
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        if (this.m_analogChannel != null && this.m_allocatedChannel) {
            this.m_analogChannel.close();
        }
        this.m_analogChannel = null;
    }

    public double getAcceleration() {
        if (this.m_analogChannel == null) {
            return 0.0;
        }
        return (this.m_analogChannel.getAverageVoltage() - this.m_zeroGVoltage) / this.m_voltsPerG;
    }

    public void setSensitivity(double sensitivity) {
        this.m_voltsPerG = sensitivity;
    }

    public void setZero(double zero) {
        this.m_zeroGVoltage = zero;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Accelerometer");
        builder.addDoubleProperty("Value", this::getAcceleration, null);
    }
}

