/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.PWMConfigDataResult;
import edu.wpi.first.hal.PWMJNI;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.SensorUtil;

public class PWM
implements Sendable,
AutoCloseable {
    private final int m_channel;
    private int m_handle;

    public PWM(int channel) {
        this(channel, true);
    }

    public PWM(int channel, boolean registerSendable) {
        SensorUtil.checkPWMChannel(channel);
        this.m_channel = channel;
        this.m_handle = PWMJNI.initializePWMPort(HAL.getPort((byte)channel));
        this.setDisabled();
        PWMJNI.setPWMEliminateDeadband(this.m_handle, false);
        HAL.report(29, channel + 1);
        if (registerSendable) {
            SendableRegistry.addLW((Sendable)this, "PWM", channel);
        }
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        if (this.m_handle == 0) {
            return;
        }
        this.setDisabled();
        PWMJNI.freePWMPort(this.m_handle);
        this.m_handle = 0;
    }

    public void enableDeadbandElimination(boolean eliminateDeadband) {
        PWMJNI.setPWMEliminateDeadband(this.m_handle, eliminateDeadband);
    }

    public void setBounds(double max, double deadbandMax, double center, double deadbandMin, double min) {
        PWMJNI.setPWMConfig(this.m_handle, max, deadbandMax, center, deadbandMin, min);
    }

    public PWMConfigDataResult getRawBounds() {
        return PWMJNI.getPWMConfigRaw(this.m_handle);
    }

    public int getChannel() {
        return this.m_channel;
    }

    public void setPosition(double pos) {
        PWMJNI.setPWMPosition(this.m_handle, pos);
    }

    public double getPosition() {
        return PWMJNI.getPWMPosition(this.m_handle);
    }

    public void setSpeed(double speed) {
        PWMJNI.setPWMSpeed(this.m_handle, speed);
    }

    public double getSpeed() {
        return PWMJNI.getPWMSpeed(this.m_handle);
    }

    public void setRaw(int value) {
        PWMJNI.setPWMRaw(this.m_handle, (short)value);
    }

    public int getRaw() {
        return PWMJNI.getPWMRaw(this.m_handle);
    }

    public void setDisabled() {
        PWMJNI.setPWMDisabled(this.m_handle);
    }

    public void setPeriodMultiplier(PeriodMultiplier mult) {
        switch (mult) {
            case k4X: {
                PWMJNI.setPWMPeriodScale(this.m_handle, 3);
                break;
            }
            case k2X: {
                PWMJNI.setPWMPeriodScale(this.m_handle, 1);
                break;
            }
            case k1X: {
                PWMJNI.setPWMPeriodScale(this.m_handle, 0);
                break;
            }
        }
    }

    public void setZeroLatch() {
        PWMJNI.latchPWMZero(this.m_handle);
    }

    public int getHandle() {
        return this.m_handle;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("PWM");
        builder.setActuator(true);
        builder.setSafeState(this::setDisabled);
        builder.addDoubleProperty("Value", this::getRaw, value -> this.setRaw((int)value));
    }

    public static enum PeriodMultiplier {
        k1X,
        k2X,
        k4X;

    }
}

