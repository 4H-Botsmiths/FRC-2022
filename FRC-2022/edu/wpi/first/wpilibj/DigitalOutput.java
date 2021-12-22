/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.DIOJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.SensorUtil;

public class DigitalOutput
extends DigitalSource
implements Sendable {
    private static final int invalidPwmGenerator = 0;
    private int m_pwmGenerator = 0;
    private final int m_channel;
    private int m_handle;

    public DigitalOutput(int channel) {
        SensorUtil.checkDigitalChannel(channel);
        this.m_channel = channel;
        this.m_handle = DIOJNI.initializeDIOPort(HAL.getPort((byte)channel), false);
        HAL.report(14, channel + 1);
        SendableRegistry.addLW((Sendable)this, "DigitalOutput", channel);
    }

    @Override
    public void close() {
        super.close();
        SendableRegistry.remove(this);
        if (this.m_pwmGenerator != 0) {
            this.disablePWM();
        }
        DIOJNI.freeDIOPort(this.m_handle);
        this.m_handle = 0;
    }

    public void set(boolean value) {
        DIOJNI.setDIO(this.m_handle, value);
    }

    public boolean get() {
        return DIOJNI.getDIO(this.m_handle);
    }

    @Override
    public int getChannel() {
        return this.m_channel;
    }

    public void pulse(double pulseLength) {
        DIOJNI.pulse(this.m_handle, pulseLength);
    }

    public boolean isPulsing() {
        return DIOJNI.isPulsing(this.m_handle);
    }

    public void setPWMRate(double rate) {
        DIOJNI.setDigitalPWMRate(rate);
    }

    public void enablePWM(double initialDutyCycle) {
        if (this.m_pwmGenerator != 0) {
            return;
        }
        this.m_pwmGenerator = DIOJNI.allocateDigitalPWM();
        DIOJNI.setDigitalPWMDutyCycle(this.m_pwmGenerator, initialDutyCycle);
        DIOJNI.setDigitalPWMOutputChannel(this.m_pwmGenerator, this.m_channel);
    }

    public void disablePWM() {
        if (this.m_pwmGenerator == 0) {
            return;
        }
        DIOJNI.setDigitalPWMOutputChannel(this.m_pwmGenerator, SensorUtil.kDigitalChannels);
        DIOJNI.freeDigitalPWM(this.m_pwmGenerator);
        this.m_pwmGenerator = 0;
    }

    public void updateDutyCycle(double dutyCycle) {
        if (this.m_pwmGenerator == 0) {
            return;
        }
        DIOJNI.setDigitalPWMDutyCycle(this.m_pwmGenerator, dutyCycle);
    }

    public void setSimDevice(SimDevice device) {
        DIOJNI.setDIOSimDevice(this.m_handle, device.getNativeHandle());
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Digital Output");
        builder.addBooleanProperty("Value", this::get, this::set);
    }

    @Override
    public boolean isAnalogTrigger() {
        return false;
    }

    @Override
    public int getAnalogTriggerTypeForRouting() {
        return 0;
    }

    @Override
    public int getPortHandleForRouting() {
        return this.m_handle;
    }
}

