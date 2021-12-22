/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.GenericHID;

public class Joystick
extends GenericHID {
    public static final byte kDefaultXChannel = 0;
    public static final byte kDefaultYChannel = 1;
    public static final byte kDefaultZChannel = 2;
    public static final byte kDefaultTwistChannel = 2;
    public static final byte kDefaultThrottleChannel = 3;
    private final byte[] m_axes = new byte[AxisType.values().length];

    public Joystick(int port) {
        super(port);
        this.m_axes[AxisType.kX.value] = 0;
        this.m_axes[AxisType.kY.value] = 1;
        this.m_axes[AxisType.kZ.value] = 2;
        this.m_axes[AxisType.kTwist.value] = 2;
        this.m_axes[AxisType.kThrottle.value] = 3;
        HAL.report(24, port + 1);
    }

    public void setXChannel(int channel) {
        this.m_axes[AxisType.kX.value] = (byte)channel;
    }

    public void setYChannel(int channel) {
        this.m_axes[AxisType.kY.value] = (byte)channel;
    }

    public void setZChannel(int channel) {
        this.m_axes[AxisType.kZ.value] = (byte)channel;
    }

    public void setThrottleChannel(int channel) {
        this.m_axes[AxisType.kThrottle.value] = (byte)channel;
    }

    public void setTwistChannel(int channel) {
        this.m_axes[AxisType.kTwist.value] = (byte)channel;
    }

    public int getXChannel() {
        return this.m_axes[AxisType.kX.value];
    }

    public int getYChannel() {
        return this.m_axes[AxisType.kY.value];
    }

    public int getZChannel() {
        return this.m_axes[AxisType.kZ.value];
    }

    public int getTwistChannel() {
        return this.m_axes[AxisType.kTwist.value];
    }

    public int getThrottleChannel() {
        return this.m_axes[AxisType.kThrottle.value];
    }

    public final double getX() {
        return this.getRawAxis(this.m_axes[AxisType.kX.value]);
    }

    public final double getY() {
        return this.getRawAxis(this.m_axes[AxisType.kY.value]);
    }

    public double getZ() {
        return this.getRawAxis(this.m_axes[AxisType.kZ.value]);
    }

    public double getTwist() {
        return this.getRawAxis(this.m_axes[AxisType.kTwist.value]);
    }

    public double getThrottle() {
        return this.getRawAxis(this.m_axes[AxisType.kThrottle.value]);
    }

    public boolean getTrigger() {
        return this.getRawButton(ButtonType.kTrigger.value);
    }

    public boolean getTriggerPressed() {
        return this.getRawButtonPressed(ButtonType.kTrigger.value);
    }

    public boolean getTriggerReleased() {
        return this.getRawButtonReleased(ButtonType.kTrigger.value);
    }

    public boolean getTop() {
        return this.getRawButton(ButtonType.kTop.value);
    }

    public boolean getTopPressed() {
        return this.getRawButtonPressed(ButtonType.kTop.value);
    }

    public boolean getTopReleased() {
        return this.getRawButtonReleased(ButtonType.kTop.value);
    }

    public double getMagnitude() {
        return Math.sqrt(Math.pow(this.getX(), 2.0) + Math.pow(this.getY(), 2.0));
    }

    public double getDirectionRadians() {
        return Math.atan2(this.getX(), -this.getY());
    }

    public double getDirectionDegrees() {
        return Math.toDegrees(this.getDirectionRadians());
    }

    public static enum ButtonType {
        kTrigger(1),
        kTop(2);

        public final int value;

        private ButtonType(int value) {
            this.value = value;
        }
    }

    public static enum AxisType {
        kX(0),
        kY(1),
        kZ(2),
        kTwist(3),
        kThrottle(4);

        public final int value;

        private AxisType(int value) {
            this.value = value;
        }
    }
}

