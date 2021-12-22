/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.CounterJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.AnalogTriggerOutput;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

public class Counter
implements CounterBase,
Sendable,
AutoCloseable {
    protected DigitalSource m_upSource;
    protected DigitalSource m_downSource;
    private boolean m_allocatedUpSource;
    private boolean m_allocatedDownSource;
    int m_counter;
    private int m_index;
    private double m_distancePerPulse;

    public Counter(Mode mode) {
        ByteBuffer index = ByteBuffer.allocateDirect(4);
        index.order(ByteOrder.LITTLE_ENDIAN);
        this.m_counter = CounterJNI.initializeCounter(mode.value, index.asIntBuffer());
        this.m_index = index.asIntBuffer().get(0);
        this.m_allocatedUpSource = false;
        this.m_allocatedDownSource = false;
        this.m_upSource = null;
        this.m_downSource = null;
        this.setMaxPeriod(0.5);
        HAL.report(11, this.m_index + 1, mode.value + 1);
        SendableRegistry.addLW((Sendable)this, "Counter", this.m_index);
    }

    public Counter() {
        this(Mode.kTwoPulse);
    }

    public Counter(DigitalSource source) {
        this();
        ErrorMessages.requireNonNullParam(source, "source", "Counter");
        this.setUpSource(source);
    }

    public Counter(int channel) {
        this();
        this.setUpSource(channel);
    }

    public Counter(CounterBase.EncodingType encodingType, DigitalSource upSource, DigitalSource downSource, boolean inverted) {
        this(Mode.kExternalDirection);
        ErrorMessages.requireNonNullParam(encodingType, "encodingType", "Counter");
        ErrorMessages.requireNonNullParam(upSource, "upSource", "Counter");
        ErrorMessages.requireNonNullParam(downSource, "downSource", "Counter");
        if (encodingType != CounterBase.EncodingType.k1X && encodingType != CounterBase.EncodingType.k2X) {
            throw new IllegalArgumentException("Counters only support 1X and 2X quadrature decoding!");
        }
        this.setUpSource(upSource);
        this.setDownSource(downSource);
        if (encodingType == CounterBase.EncodingType.k1X) {
            this.setUpSourceEdge(true, false);
            CounterJNI.setCounterAverageSize(this.m_counter, 1);
        } else {
            this.setUpSourceEdge(true, true);
            CounterJNI.setCounterAverageSize(this.m_counter, 2);
        }
        this.setDownSourceEdge(inverted, true);
    }

    public Counter(AnalogTrigger trigger) {
        this();
        ErrorMessages.requireNonNullParam(trigger, "trigger", "Counter");
        this.setUpSource(trigger.createOutput(AnalogTriggerOutput.AnalogTriggerType.kState));
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        this.setUpdateWhenEmpty(true);
        this.clearUpSource();
        this.clearDownSource();
        CounterJNI.freeCounter(this.m_counter);
        this.m_upSource = null;
        this.m_downSource = null;
        this.m_counter = 0;
    }

    public int getFPGAIndex() {
        return this.m_index;
    }

    public void setUpSource(int channel) {
        this.setUpSource(new DigitalInput(channel));
        this.m_allocatedUpSource = true;
        SendableRegistry.addChild(this, this.m_upSource);
    }

    public void setUpSource(DigitalSource source) {
        if (this.m_upSource != null && this.m_allocatedUpSource) {
            this.m_upSource.close();
            this.m_allocatedUpSource = false;
        }
        this.m_upSource = source;
        CounterJNI.setCounterUpSource(this.m_counter, source.getPortHandleForRouting(), source.getAnalogTriggerTypeForRouting());
    }

    public void setUpSource(AnalogTrigger analogTrigger, AnalogTriggerOutput.AnalogTriggerType triggerType) {
        ErrorMessages.requireNonNullParam(analogTrigger, "analogTrigger", "setUpSource");
        ErrorMessages.requireNonNullParam(triggerType, "triggerType", "setUpSource");
        this.setUpSource(analogTrigger.createOutput(triggerType));
        this.m_allocatedUpSource = true;
    }

    public void setUpSourceEdge(boolean risingEdge, boolean fallingEdge) {
        if (this.m_upSource == null) {
            throw new IllegalStateException("Up Source must be set before setting the edge!");
        }
        CounterJNI.setCounterUpSourceEdge(this.m_counter, risingEdge, fallingEdge);
    }

    public void clearUpSource() {
        if (this.m_upSource != null && this.m_allocatedUpSource) {
            this.m_upSource.close();
            this.m_allocatedUpSource = false;
        }
        this.m_upSource = null;
        CounterJNI.clearCounterUpSource(this.m_counter);
    }

    public void setDownSource(int channel) {
        this.setDownSource(new DigitalInput(channel));
        this.m_allocatedDownSource = true;
        SendableRegistry.addChild(this, this.m_downSource);
    }

    public void setDownSource(DigitalSource source) {
        Objects.requireNonNull(source, "The Digital Source given was null");
        if (this.m_downSource != null && this.m_allocatedDownSource) {
            this.m_downSource.close();
            this.m_allocatedDownSource = false;
        }
        CounterJNI.setCounterDownSource(this.m_counter, source.getPortHandleForRouting(), source.getAnalogTriggerTypeForRouting());
        this.m_downSource = source;
    }

    public void setDownSource(AnalogTrigger analogTrigger, AnalogTriggerOutput.AnalogTriggerType triggerType) {
        ErrorMessages.requireNonNullParam(analogTrigger, "analogTrigger", "setDownSource");
        ErrorMessages.requireNonNullParam(triggerType, "analogTrigger", "setDownSource");
        this.setDownSource(analogTrigger.createOutput(triggerType));
        this.m_allocatedDownSource = true;
    }

    public void setDownSourceEdge(boolean risingEdge, boolean fallingEdge) {
        Objects.requireNonNull(this.m_downSource, "Down Source must be set before setting the edge!");
        CounterJNI.setCounterDownSourceEdge(this.m_counter, risingEdge, fallingEdge);
    }

    public void clearDownSource() {
        if (this.m_downSource != null && this.m_allocatedDownSource) {
            this.m_downSource.close();
            this.m_allocatedDownSource = false;
        }
        this.m_downSource = null;
        CounterJNI.clearCounterDownSource(this.m_counter);
    }

    public void setUpDownCounterMode() {
        CounterJNI.setCounterUpDownMode(this.m_counter);
    }

    public void setExternalDirectionMode() {
        CounterJNI.setCounterExternalDirectionMode(this.m_counter);
    }

    public void setSemiPeriodMode(boolean highSemiPeriod) {
        CounterJNI.setCounterSemiPeriodMode(this.m_counter, highSemiPeriod);
    }

    public void setPulseLengthMode(double threshold) {
        CounterJNI.setCounterPulseLengthMode(this.m_counter, threshold);
    }

    @Override
    public int get() {
        return CounterJNI.getCounter(this.m_counter);
    }

    public double getDistance() {
        return (double)this.get() * this.m_distancePerPulse;
    }

    @Override
    public void reset() {
        CounterJNI.resetCounter(this.m_counter);
    }

    @Override
    public void setMaxPeriod(double maxPeriod) {
        CounterJNI.setCounterMaxPeriod(this.m_counter, maxPeriod);
    }

    public void setUpdateWhenEmpty(boolean enabled) {
        CounterJNI.setCounterUpdateWhenEmpty(this.m_counter, enabled);
    }

    @Override
    public boolean getStopped() {
        return CounterJNI.getCounterStopped(this.m_counter);
    }

    @Override
    public boolean getDirection() {
        return CounterJNI.getCounterDirection(this.m_counter);
    }

    public void setReverseDirection(boolean reverseDirection) {
        CounterJNI.setCounterReverseDirection(this.m_counter, reverseDirection);
    }

    @Override
    public double getPeriod() {
        return CounterJNI.getCounterPeriod(this.m_counter);
    }

    public double getRate() {
        return this.m_distancePerPulse / this.getPeriod();
    }

    public void setSamplesToAverage(int samplesToAverage) {
        CounterJNI.setCounterSamplesToAverage(this.m_counter, samplesToAverage);
    }

    public int getSamplesToAverage() {
        return CounterJNI.getCounterSamplesToAverage(this.m_counter);
    }

    public void setDistancePerPulse(double distancePerPulse) {
        this.m_distancePerPulse = distancePerPulse;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Counter");
        builder.addDoubleProperty("Value", this::get, null);
    }

    public static enum Mode {
        kTwoPulse(0),
        kSemiperiod(1),
        kPulseLength(2),
        kExternalDirection(3);

        public final int value;

        private Mode(int value) {
            this.value = value;
        }
    }
}

