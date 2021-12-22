/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.AccumulatorResult;
import edu.wpi.first.hal.AnalogJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.util.AllocationException;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Timer;

public class AnalogInput
implements Sendable,
AutoCloseable {
    private static final int kAccumulatorSlot = 1;
    int m_port;
    private int m_channel;
    private static final int[] kAccumulatorChannels = new int[]{0, 1};
    private long m_accumulatorOffset;

    public AnalogInput(int channel) {
        AnalogJNI.checkAnalogInputChannel(channel);
        this.m_channel = channel;
        int portHandle = HAL.getPort((byte)channel);
        this.m_port = AnalogJNI.initializeAnalogInputPort(portHandle);
        HAL.report(6, channel + 1);
        SendableRegistry.addLW((Sendable)this, "AnalogInput", channel);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        AnalogJNI.freeAnalogInputPort(this.m_port);
        this.m_port = 0;
        this.m_channel = 0;
        this.m_accumulatorOffset = 0L;
    }

    public int getValue() {
        return AnalogJNI.getAnalogValue(this.m_port);
    }

    public int getAverageValue() {
        return AnalogJNI.getAnalogAverageValue(this.m_port);
    }

    public double getVoltage() {
        return AnalogJNI.getAnalogVoltage(this.m_port);
    }

    public double getAverageVoltage() {
        return AnalogJNI.getAnalogAverageVoltage(this.m_port);
    }

    public long getLSBWeight() {
        return AnalogJNI.getAnalogLSBWeight(this.m_port);
    }

    public int getOffset() {
        return AnalogJNI.getAnalogOffset(this.m_port);
    }

    public int getChannel() {
        return this.m_channel;
    }

    public void setAverageBits(int bits) {
        AnalogJNI.setAnalogAverageBits(this.m_port, bits);
    }

    public int getAverageBits() {
        return AnalogJNI.getAnalogAverageBits(this.m_port);
    }

    public void setOversampleBits(int bits) {
        AnalogJNI.setAnalogOversampleBits(this.m_port, bits);
    }

    public int getOversampleBits() {
        return AnalogJNI.getAnalogOversampleBits(this.m_port);
    }

    public void initAccumulator() {
        if (!this.isAccumulatorChannel()) {
            throw new AllocationException("Accumulators are only available on slot 1 on channels " + kAccumulatorChannels[0] + ", " + kAccumulatorChannels[1]);
        }
        this.m_accumulatorOffset = 0L;
        AnalogJNI.initAccumulator(this.m_port);
    }

    public void setAccumulatorInitialValue(long initialValue) {
        this.m_accumulatorOffset = initialValue;
    }

    public void resetAccumulator() {
        AnalogJNI.resetAccumulator(this.m_port);
        double sampleTime = 1.0 / AnalogInput.getGlobalSampleRate();
        double overSamples = 1 << this.getOversampleBits();
        double averageSamples = 1 << this.getAverageBits();
        Timer.delay(sampleTime * overSamples * averageSamples);
    }

    public void setAccumulatorCenter(int center) {
        AnalogJNI.setAccumulatorCenter(this.m_port, center);
    }

    public void setAccumulatorDeadband(int deadband) {
        AnalogJNI.setAccumulatorDeadband(this.m_port, deadband);
    }

    public long getAccumulatorValue() {
        return AnalogJNI.getAccumulatorValue(this.m_port) + this.m_accumulatorOffset;
    }

    public long getAccumulatorCount() {
        return AnalogJNI.getAccumulatorCount(this.m_port);
    }

    public void getAccumulatorOutput(AccumulatorResult result) {
        if (result == null) {
            throw new IllegalArgumentException("Null parameter `result'");
        }
        if (!this.isAccumulatorChannel()) {
            throw new IllegalArgumentException("Channel " + this.m_channel + " is not an accumulator channel.");
        }
        AnalogJNI.getAccumulatorOutput(this.m_port, result);
        result.value += this.m_accumulatorOffset;
    }

    public boolean isAccumulatorChannel() {
        for (int channel : kAccumulatorChannels) {
            if (this.m_channel != channel) continue;
            return true;
        }
        return false;
    }

    public static void setGlobalSampleRate(double samplesPerSecond) {
        AnalogJNI.setAnalogSampleRate(samplesPerSecond);
    }

    public static double getGlobalSampleRate() {
        return AnalogJNI.getAnalogSampleRate();
    }

    public void setSimDevice(SimDevice device) {
        AnalogJNI.setAnalogInputSimDevice(this.m_port, device.getNativeHandle());
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Analog Input");
        builder.addDoubleProperty("Value", this::getAverageVoltage, null);
    }
}

