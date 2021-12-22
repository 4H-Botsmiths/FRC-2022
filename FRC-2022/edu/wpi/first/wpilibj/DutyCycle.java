/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.DutyCycleJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DigitalSource;

public class DutyCycle
implements Sendable,
AutoCloseable {
    final int m_handle;
    private final DigitalSource m_source;

    public DutyCycle(DigitalSource digitalSource) {
        this.m_handle = DutyCycleJNI.initialize(digitalSource.getPortHandleForRouting(), digitalSource.getAnalogTriggerTypeForRouting());
        this.m_source = digitalSource;
        int index = this.getFPGAIndex();
        HAL.report(91, index + 1);
        SendableRegistry.addLW((Sendable)this, "Duty Cycle", index);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        DutyCycleJNI.free(this.m_handle);
    }

    public int getFrequency() {
        return DutyCycleJNI.getFrequency(this.m_handle);
    }

    public double getOutput() {
        return DutyCycleJNI.getOutput(this.m_handle);
    }

    public int getOutputRaw() {
        return DutyCycleJNI.getOutputRaw(this.m_handle);
    }

    public int getOutputScaleFactor() {
        return DutyCycleJNI.getOutputScaleFactor(this.m_handle);
    }

    public final int getFPGAIndex() {
        return DutyCycleJNI.getFPGAIndex(this.m_handle);
    }

    public int getSourceChannel() {
        return this.m_source.getChannel();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Duty Cycle");
        builder.addDoubleProperty("Frequency", this::getFrequency, null);
        builder.addDoubleProperty("Output", this::getOutput, null);
    }
}

