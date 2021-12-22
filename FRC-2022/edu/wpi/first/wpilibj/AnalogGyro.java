/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.AnalogGyroJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.util.ErrorMessages;

public class AnalogGyro
implements Gyro,
Sendable {
    private static final double kDefaultVoltsPerDegreePerSecond = 0.007;
    protected AnalogInput m_analog;
    private boolean m_channelAllocated;
    private int m_gyroHandle;

    public void initGyro() {
        if (this.m_gyroHandle == 0) {
            this.m_gyroHandle = AnalogGyroJNI.initializeAnalogGyro(this.m_analog.m_port);
        }
        AnalogGyroJNI.setupAnalogGyro(this.m_gyroHandle);
        HAL.report(20, this.m_analog.getChannel() + 1);
        SendableRegistry.addLW((Sendable)this, "AnalogGyro", this.m_analog.getChannel());
    }

    @Override
    public void calibrate() {
        AnalogGyroJNI.calibrateAnalogGyro(this.m_gyroHandle);
    }

    public AnalogGyro(int channel) {
        this(new AnalogInput(channel));
        this.m_channelAllocated = true;
        SendableRegistry.addChild(this, this.m_analog);
    }

    public AnalogGyro(AnalogInput channel) {
        ErrorMessages.requireNonNullParam(channel, "channel", "AnalogGyro");
        this.m_analog = channel;
        this.initGyro();
        this.calibrate();
    }

    public AnalogGyro(int channel, int center, double offset) {
        this(new AnalogInput(channel), center, offset);
        this.m_channelAllocated = true;
        SendableRegistry.addChild(this, this.m_analog);
    }

    public AnalogGyro(AnalogInput channel, int center, double offset) {
        ErrorMessages.requireNonNullParam(channel, "channel", "AnalogGyro");
        this.m_analog = channel;
        this.initGyro();
        AnalogGyroJNI.setAnalogGyroParameters(this.m_gyroHandle, 0.007, offset, center);
        this.reset();
    }

    @Override
    public void reset() {
        AnalogGyroJNI.resetAnalogGyro(this.m_gyroHandle);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        if (this.m_analog != null && this.m_channelAllocated) {
            this.m_analog.close();
        }
        this.m_analog = null;
        AnalogGyroJNI.freeAnalogGyro(this.m_gyroHandle);
    }

    @Override
    public double getAngle() {
        if (this.m_analog == null) {
            return 0.0;
        }
        return AnalogGyroJNI.getAnalogGyroAngle(this.m_gyroHandle);
    }

    @Override
    public double getRate() {
        if (this.m_analog == null) {
            return 0.0;
        }
        return AnalogGyroJNI.getAnalogGyroRate(this.m_gyroHandle);
    }

    public double getOffset() {
        return AnalogGyroJNI.getAnalogGyroOffset(this.m_gyroHandle);
    }

    public int getCenter() {
        return AnalogGyroJNI.getAnalogGyroCenter(this.m_gyroHandle);
    }

    public void setSensitivity(double voltsPerDegreePerSecond) {
        AnalogGyroJNI.setAnalogGyroVoltsPerDegreePerSecond(this.m_gyroHandle, voltsPerDegreePerSecond);
    }

    public void setDeadband(double volts) {
        AnalogGyroJNI.setAnalogGyroDeadband(this.m_gyroHandle, volts);
    }

    public AnalogInput getAnalogInput() {
        return this.m_analog;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Gyro");
        builder.addDoubleProperty("Value", this::getAngle, null);
    }
}

