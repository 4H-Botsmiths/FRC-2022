/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;

public class AnalogPotentiometer
implements Sendable,
AutoCloseable {
    private AnalogInput m_analogInput;
    private boolean m_initAnalogInput;
    private double m_fullRange;
    private double m_offset;

    public AnalogPotentiometer(int channel, double fullRange, double offset) {
        this(new AnalogInput(channel), fullRange, offset);
        this.m_initAnalogInput = true;
        SendableRegistry.addChild(this, this.m_analogInput);
    }

    public AnalogPotentiometer(AnalogInput input, double fullRange, double offset) {
        SendableRegistry.addLW((Sendable)this, "AnalogPotentiometer", input.getChannel());
        this.m_analogInput = input;
        this.m_initAnalogInput = false;
        this.m_fullRange = fullRange;
        this.m_offset = offset;
    }

    public AnalogPotentiometer(int channel, double scale) {
        this(channel, scale, 0.0);
    }

    public AnalogPotentiometer(AnalogInput input, double scale) {
        this(input, scale, 0.0);
    }

    public AnalogPotentiometer(int channel) {
        this(channel, 1.0, 0.0);
    }

    public AnalogPotentiometer(AnalogInput input) {
        this(input, 1.0, 0.0);
    }

    public double get() {
        if (this.m_analogInput == null) {
            return this.m_offset;
        }
        return this.m_analogInput.getAverageVoltage() / RobotController.getVoltage5V() * this.m_fullRange + this.m_offset;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        if (this.m_analogInput != null) {
            builder.setSmartDashboardType("Analog Input");
            builder.addDoubleProperty("Value", this::get, null);
        }
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        if (this.m_initAnalogInput) {
            this.m_analogInput.close();
            this.m_analogInput = null;
            this.m_initAnalogInput = false;
        }
    }
}

