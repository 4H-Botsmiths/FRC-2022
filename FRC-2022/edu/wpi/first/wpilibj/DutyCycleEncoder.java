/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.AnalogTriggerOutput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DutyCycle;

public class DutyCycleEncoder
implements Sendable,
AutoCloseable {
    private final DutyCycle m_dutyCycle;
    private boolean m_ownsDutyCycle;
    private DigitalInput m_digitalInput;
    private AnalogTrigger m_analogTrigger;
    private Counter m_counter;
    private int m_frequencyThreshold = 100;
    private double m_positionOffset;
    private double m_distancePerRotation = 1.0;
    private double m_lastPosition;
    private double m_sensorMin;
    private double m_sensorMax = 1.0;
    protected SimDevice m_simDevice;
    protected SimDouble m_simPosition;
    protected SimDouble m_simDistancePerRotation;
    protected SimBoolean m_simIsConnected;

    public DutyCycleEncoder(int channel) {
        this.m_digitalInput = new DigitalInput(channel);
        this.m_ownsDutyCycle = true;
        this.m_dutyCycle = new DutyCycle(this.m_digitalInput);
        this.init();
    }

    public DutyCycleEncoder(DutyCycle dutyCycle) {
        this.m_dutyCycle = dutyCycle;
        this.init();
    }

    public DutyCycleEncoder(DigitalSource source) {
        this.m_ownsDutyCycle = true;
        this.m_dutyCycle = new DutyCycle(source);
        this.init();
    }

    private void init() {
        this.m_simDevice = SimDevice.create("DutyCycle:DutyCycleEncoder", this.m_dutyCycle.getSourceChannel());
        if (this.m_simDevice != null) {
            this.m_simPosition = this.m_simDevice.createDouble("position", SimDevice.Direction.kInput, 0.0);
            this.m_simDistancePerRotation = this.m_simDevice.createDouble("distance_per_rot", SimDevice.Direction.kOutput, 1.0);
            this.m_simIsConnected = this.m_simDevice.createBoolean("connected", SimDevice.Direction.kInput, true);
        } else {
            this.m_counter = new Counter();
            this.m_analogTrigger = new AnalogTrigger(this.m_dutyCycle);
            this.m_analogTrigger.setLimitsDutyCycle(0.25, 0.75);
            this.m_counter.setUpSource(this.m_analogTrigger, AnalogTriggerOutput.AnalogTriggerType.kRisingPulse);
            this.m_counter.setDownSource(this.m_analogTrigger, AnalogTriggerOutput.AnalogTriggerType.kFallingPulse);
        }
        SendableRegistry.addLW((Sendable)this, "DutyCycle Encoder", this.m_dutyCycle.getSourceChannel());
    }

    public double get() {
        if (this.m_simPosition != null) {
            return this.m_simPosition.get();
        }
        for (int i = 0; i < 10; ++i) {
            double position;
            double counter = this.m_counter.get();
            double pos = this.m_dutyCycle.getOutput();
            double counter2 = this.m_counter.get();
            double pos2 = this.m_dutyCycle.getOutput();
            if (counter != counter2 || pos != pos2) continue;
            if (pos < this.m_sensorMin) {
                pos = this.m_sensorMin;
            }
            if (pos > this.m_sensorMax) {
                pos = this.m_sensorMax;
            }
            pos = (pos - this.m_sensorMin) / (this.m_sensorMax - this.m_sensorMin);
            this.m_lastPosition = position = counter + pos - this.m_positionOffset;
            return position;
        }
        DriverStation.reportWarning("Failed to read Analog Encoder. Potential Speed Overrun. Returning last value", false);
        return this.m_lastPosition;
    }

    public double getPositionOffset() {
        return this.m_positionOffset;
    }

    public void setDutyCycleRange(double min, double max) {
        this.m_sensorMin = MathUtil.clamp(min, 0.0, 1.0);
        this.m_sensorMax = MathUtil.clamp(max, 0.0, 1.0);
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

    public int getFrequency() {
        return this.m_dutyCycle.getFrequency();
    }

    public void reset() {
        if (this.m_counter != null) {
            this.m_counter.reset();
        }
        this.m_positionOffset = this.m_dutyCycle.getOutput();
    }

    public boolean isConnected() {
        if (this.m_simIsConnected != null) {
            return this.m_simIsConnected.get();
        }
        return this.getFrequency() > this.m_frequencyThreshold;
    }

    public void setConnectedFrequencyThreshold(int frequency) {
        if (frequency < 0) {
            frequency = 0;
        }
        this.m_frequencyThreshold = frequency;
    }

    @Override
    public void close() {
        if (this.m_counter != null) {
            this.m_counter.close();
        }
        if (this.m_analogTrigger != null) {
            this.m_analogTrigger.close();
        }
        if (this.m_ownsDutyCycle) {
            this.m_dutyCycle.close();
        }
        if (this.m_digitalInput != null) {
            this.m_digitalInput.close();
        }
        if (this.m_simDevice != null) {
            this.m_simDevice.close();
        }
    }

    public int getFPGAIndex() {
        return this.m_dutyCycle.getFPGAIndex();
    }

    public int getSourceChannel() {
        return this.m_dutyCycle.getSourceChannel();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("AbsoluteEncoder");
        builder.addDoubleProperty("Distance", this::getDistance, null);
        builder.addDoubleProperty("Distance Per Rotation", this::getDistancePerRotation, null);
        builder.addBooleanProperty("Is Connected", this::isConnected, null);
    }
}

