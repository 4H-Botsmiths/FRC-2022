/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.AnalogJNI;
import edu.wpi.first.hal.DMAJNISample;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DMA;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.Encoder;

public class DMASample {
    private final DMAJNISample m_dmaSample = new DMAJNISample();

    public DMAReadStatus update(DMA dma, double timeoutSeconds) {
        return DMAReadStatus.getValue(this.m_dmaSample.update(dma.m_dmaHandle, timeoutSeconds));
    }

    public int getCaptureSize() {
        return this.m_dmaSample.getCaptureSize();
    }

    public int getTriggerChannels() {
        return this.m_dmaSample.getTriggerChannels();
    }

    public int getRemaining() {
        return this.m_dmaSample.getRemaining();
    }

    public long getTime() {
        return this.m_dmaSample.getTime();
    }

    public double getTimeStamp() {
        return (double)this.getTime() * 1.0E-6;
    }

    public int getEncoderRaw(Encoder encoder) {
        return this.m_dmaSample.getEncoder(encoder.m_encoder);
    }

    public double getEncoderDistance(Encoder encoder) {
        double val = this.getEncoderRaw(encoder);
        val *= encoder.getDecodingScaleFactor();
        return val *= encoder.getDistancePerPulse();
    }

    public int getEncoderPeriodRaw(Encoder encoder) {
        return this.m_dmaSample.getEncoderPeriod(encoder.m_encoder);
    }

    public int getCounter(Counter counter) {
        return this.m_dmaSample.getCounter(counter.m_counter);
    }

    public int getCounterPeriod(Counter counter) {
        return this.m_dmaSample.getCounterPeriod(counter.m_counter);
    }

    public boolean getDigitalSource(DigitalSource digitalSource) {
        return this.m_dmaSample.getDigitalSource(digitalSource.getPortHandleForRouting());
    }

    public int getAnalogInputRaw(AnalogInput analogInput) {
        return this.m_dmaSample.getAnalogInput(analogInput.m_port);
    }

    public double getAnalogInputVoltage(AnalogInput analogInput) {
        return AnalogJNI.getAnalogValueToVolts(analogInput.m_port, this.getAnalogInputRaw(analogInput));
    }

    public int getAveragedAnalogInputRaw(AnalogInput analogInput) {
        return this.m_dmaSample.getAnalogInputAveraged(analogInput.m_port);
    }

    public double getAveragedAnalogInputVoltage(AnalogInput analogInput) {
        return AnalogJNI.getAnalogValueToVolts(analogInput.m_port, this.getAveragedAnalogInputRaw(analogInput));
    }

    public int getDutyCycleOutputRaw(DutyCycle dutyCycle) {
        return this.m_dmaSample.getDutyCycleOutput(dutyCycle.m_handle);
    }

    public double getDutyCycleOutput(DutyCycle dutyCycle) {
        return (double)this.m_dmaSample.getDutyCycleOutput(dutyCycle.m_handle) / (double)dutyCycle.getOutputScaleFactor();
    }

    public static enum DMAReadStatus {
        kOk(1),
        kTimeout(2),
        kError(3);

        private final int value;

        private DMAReadStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static DMAReadStatus getValue(int value) {
            if (value == 1) {
                return kOk;
            }
            if (value == 2) {
                return kTimeout;
            }
            return kError;
        }
    }
}

