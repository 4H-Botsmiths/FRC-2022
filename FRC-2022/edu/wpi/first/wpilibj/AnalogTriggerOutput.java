/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.AnalogJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.util.ErrorMessages;

public class AnalogTriggerOutput
extends DigitalSource
implements Sendable {
    private final AnalogTrigger m_trigger;
    private final AnalogTriggerType m_outputType;

    public AnalogTriggerOutput(AnalogTrigger trigger, AnalogTriggerType outputType) {
        ErrorMessages.requireNonNullParam(trigger, "trigger", "AnalogTriggerOutput");
        ErrorMessages.requireNonNullParam(outputType, "outputType", "AnalogTriggerOutput");
        this.m_trigger = trigger;
        this.m_outputType = outputType;
        HAL.report(8, trigger.getIndex() + 1, outputType.value + 1);
    }

    public boolean get() {
        return AnalogJNI.getAnalogTriggerOutput(this.m_trigger.m_port, this.m_outputType.value);
    }

    @Override
    public int getPortHandleForRouting() {
        return this.m_trigger.m_port;
    }

    @Override
    public int getAnalogTriggerTypeForRouting() {
        return this.m_outputType.value;
    }

    @Override
    public int getChannel() {
        return this.m_trigger.getIndex();
    }

    @Override
    public boolean isAnalogTrigger() {
        return true;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
    }

    public static enum AnalogTriggerType {
        kInWindow(0),
        kState(1),
        kRisingPulse(2),
        kFallingPulse(3);

        private final int value;

        private AnalogTriggerType(int value) {
            this.value = value;
        }
    }

    public static class AnalogTriggerOutputException
    extends RuntimeException {
        public AnalogTriggerOutputException(String message) {
            super(message);
        }
    }
}

