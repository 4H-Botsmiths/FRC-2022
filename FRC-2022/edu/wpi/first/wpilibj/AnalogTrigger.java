/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.AnalogJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.util.BoundaryException;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogTriggerOutput;
import edu.wpi.first.wpilibj.DutyCycle;

public class AnalogTrigger
implements Sendable,
AutoCloseable {
    protected int m_port;
    protected AnalogInput m_analogInput;
    protected DutyCycle m_dutyCycle;
    protected boolean m_ownsAnalog;

    public AnalogTrigger(int channel) {
        this(new AnalogInput(channel));
        this.m_ownsAnalog = true;
        SendableRegistry.addChild(this, this.m_analogInput);
    }

    public AnalogTrigger(AnalogInput channel) {
        this.m_analogInput = channel;
        this.m_port = AnalogJNI.initializeAnalogTrigger(channel.m_port);
        int index = this.getIndex();
        HAL.report(7, index + 1);
        SendableRegistry.addLW((Sendable)this, "AnalogTrigger", index);
    }

    public AnalogTrigger(DutyCycle input) {
        this.m_dutyCycle = input;
        this.m_port = AnalogJNI.initializeAnalogTriggerDutyCycle(input.m_handle);
        int index = this.getIndex();
        HAL.report(7, index + 1);
        SendableRegistry.addLW((Sendable)this, "AnalogTrigger", index);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        AnalogJNI.cleanAnalogTrigger(this.m_port);
        this.m_port = 0;
        if (this.m_ownsAnalog && this.m_analogInput != null) {
            this.m_analogInput.close();
        }
    }

    public void setLimitsRaw(int lower, int upper) {
        if (lower > upper) {
            throw new BoundaryException("Lower bound is greater than upper");
        }
        AnalogJNI.setAnalogTriggerLimitsRaw(this.m_port, lower, upper);
    }

    public void setLimitsDutyCycle(double lower, double upper) {
        if (lower > upper) {
            throw new BoundaryException("Lower bound is greater than upper bound");
        }
        AnalogJNI.setAnalogTriggerLimitsDutyCycle(this.m_port, lower, upper);
    }

    public void setLimitsVoltage(double lower, double upper) {
        if (lower > upper) {
            throw new BoundaryException("Lower bound is greater than upper bound");
        }
        AnalogJNI.setAnalogTriggerLimitsVoltage(this.m_port, lower, upper);
    }

    public void setAveraged(boolean useAveragedValue) {
        AnalogJNI.setAnalogTriggerAveraged(this.m_port, useAveragedValue);
    }

    public void setFiltered(boolean useFilteredValue) {
        AnalogJNI.setAnalogTriggerFiltered(this.m_port, useFilteredValue);
    }

    public final int getIndex() {
        return AnalogJNI.getAnalogTriggerFPGAIndex(this.m_port);
    }

    public boolean getInWindow() {
        return AnalogJNI.getAnalogTriggerInWindow(this.m_port);
    }

    public boolean getTriggerState() {
        return AnalogJNI.getAnalogTriggerTriggerState(this.m_port);
    }

    public AnalogTriggerOutput createOutput(AnalogTriggerOutput.AnalogTriggerType type) {
        return new AnalogTriggerOutput(this, type);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        if (this.m_ownsAnalog) {
            this.m_analogInput.initSendable(builder);
        }
    }

    public static class AnalogTriggerException
    extends RuntimeException {
        public AnalogTriggerException(String message) {
            super(message);
        }
    }
}

