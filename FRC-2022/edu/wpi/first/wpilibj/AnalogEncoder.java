/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.AnalogTriggerOutput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DriverStation;

public class AnalogEncoder
implements Sendable,
AutoCloseable {
    private final AnalogInput m_analogInput;
    private AnalogTrigger m_analogTrigger;
    private Counter m_counter;
    private double m_positionOffset;
    private double m_distancePerRotation = 1.0;
    private double m_lastPosition;
    protected SimDevice m_simDevice;
    protected SimDouble m_simPosition;

    public AnalogEncoder(int channel) {
        this(new AnalogInput(channel));
    }

    public AnalogEncoder(AnalogInput analogInput) {
        this.m_analogInput = analogInput;
        this.init();
    }

    private void init() {
        this.m_analogTrigger = new AnalogTrigger(this.m_analogInput);
        this.m_counter = new Counter();
        this.m_simDevice = SimDevice.create("AnalogEncoder", this.m_analogInput.getChannel());
        if (this.m_simDevice != null) {
            this.m_simPosition = this.m_simDevice.createDouble("Position", SimDevice.Direction.kInput, 0.0);
        }
        this.m_analogTrigger.setLimitsVoltage(1.25, 3.75);
        this.m_counter.setUpSource(this.m_analogTrigger, AnalogTriggerOutput.AnalogTriggerType.kRisingPulse);
        this.m_counter.setDownSource(this.m_analogTrigger, AnalogTriggerOutput.AnalogTriggerType.kFallingPulse);
        SendableRegistry.addLW((Sendable)this, "Analog Encoder", this.m_analogInput.getChannel());
    }

    public double get() {
        if (this.m_simPosition != null) {
            return this.m_simPosition.get();
        }
        for (int i = 0; i < 10; ++i) {
            double position;
            double counter = this.m_counter.get();
            double pos = this.m_analogInput.getVoltage();
            double counter2 = this.m_counter.get();
            double pos2 = this.m_analogInput.getVoltage();
            if (counter != counter2 || pos != pos2) continue;
            this.m_lastPosition = position = counter + pos - this.m_positionOffset;
            return position;
        }
        DriverStation.reportWarning("Failed to read Analog Encoder. Potential Speed Overrun. Returning last value", false);
        return this.m_lastPosition;
    }

    public double getPositionOffset() {
        return this.m_positionOffset;
    }

    public void setDistancePerRotation(double distancePerRotation) {
        this.m_distancePerRotation = distancePerRotation;
    }

    public double getDistancePerRotation() {
        return this.m_distancePerRotation;
    }

    public double getDistance() {
        return this.get() * this.getDistancePerRotation();
    }

    public int getChannel() {
        return this.m_analogInput.getChannel();
    }

    public void reset() {
        this.m_counter.reset();
        this.m_positionOffset = this.m_analogInput.getVoltage();
    }

    @Override
    public void close() {
        this.m_counter.close();
        this.m_analogTrigger.close();
        if (this.m_simDevice != null) {
            this.m_simDevice.close();
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("AbsoluteEncoder");
        builder.addDoubleProperty("Distance", this::getDistance, null);
        builder.addDoubleProperty("Distance Per Rotation", this::getDistancePerRotation, null);
    }
}

