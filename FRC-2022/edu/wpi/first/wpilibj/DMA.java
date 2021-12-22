/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.DMAJNI;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;

public class DMA
implements AutoCloseable {
    final int m_dmaHandle = DMAJNI.initialize();

    @Override
    public void close() {
        DMAJNI.free(this.m_dmaHandle);
    }

    public void setPause(boolean pause) {
        DMAJNI.setPause(this.m_dmaHandle, pause);
    }

    public void setTimedTrigger(double periodSeconds) {
        DMAJNI.setTimedTrigger(this.m_dmaHandle, periodSeconds);
    }

    public void setTimedTriggerCycles(int cycles) {
        DMAJNI.setTimedTriggerCycles(this.m_dmaHandle, cycles);
    }

    public void addEncoder(Encoder encoder) {
        DMAJNI.addEncoder(this.m_dmaHandle, encoder.m_encoder);
    }

    public void addEncoderPeriod(Encoder encoder) {
        DMAJNI.addEncoderPeriod(this.m_dmaHandle, encoder.m_encoder);
    }

    public void addCounter(Counter counter) {
        DMAJNI.addCounter(this.m_dmaHandle, counter.m_counter);
    }

    public void addCounterPeriod(Counter counter) {
        DMAJNI.addCounterPeriod(this.m_dmaHandle, counter.m_counter);
    }

    public void addDigitalSource(DigitalSource digitalSource) {
        DMAJNI.addDigitalSource(this.m_dmaHandle, digitalSource.getPortHandleForRouting());
    }

    public void addDutyCycle(DutyCycle dutyCycle) {
        DMAJNI.addDutyCycle(this.m_dmaHandle, dutyCycle.m_handle);
    }

    public void addAnalogInput(AnalogInput analogInput) {
        DMAJNI.addAnalogInput(this.m_dmaHandle, analogInput.m_port);
    }

    public void addAveragedAnalogInput(AnalogInput analogInput) {
        DMAJNI.addAveragedAnalogInput(this.m_dmaHandle, analogInput.m_port);
    }

    public void addAnalogAccumulator(AnalogInput analogInput) {
        DMAJNI.addAnalogAccumulator(this.m_dmaHandle, analogInput.m_port);
    }

    public int setExternalTrigger(DigitalSource source, boolean rising, boolean falling) {
        return DMAJNI.setExternalTrigger(this.m_dmaHandle, source.getPortHandleForRouting(), source.getAnalogTriggerTypeForRouting(), rising, falling);
    }

    public int setPwmEdgeTrigger(PWMMotorController pwm, boolean rising, boolean falling) {
        return DMAJNI.setExternalTrigger(this.m_dmaHandle, pwm.getPwmHandle(), 0, rising, falling);
    }

    public int setPwmEdgeTrigger(PWM pwm, boolean rising, boolean falling) {
        return DMAJNI.setExternalTrigger(this.m_dmaHandle, pwm.getHandle(), 0, rising, falling);
    }

    public void clearSensors() {
        DMAJNI.clearSensors(this.m_dmaHandle);
    }

    public void clearExternalTriggers() {
        DMAJNI.clearExternalTriggers(this.m_dmaHandle);
    }

    public void start(int queueDepth) {
        DMAJNI.startDMA(this.m_dmaHandle, queueDepth);
    }

    public void stop() {
        DMAJNI.stopDMA(this.m_dmaHandle);
    }
}

