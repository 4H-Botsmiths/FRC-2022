/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.simulation.GenericHIDSim;

public class JoystickSim
extends GenericHIDSim {
    private Joystick m_joystick;

    public JoystickSim(Joystick joystick) {
        super(joystick);
        this.m_joystick = joystick;
        this.setAxisCount(5);
        this.setButtonCount(12);
        this.setPOVCount(1);
    }

    public JoystickSim(int port) {
        super(port);
        this.setAxisCount(5);
        this.setButtonCount(12);
        this.setPOVCount(1);
    }

    public void setX(double value) {
        this.setRawAxis(this.m_joystick != null ? this.m_joystick.getXChannel() : 0, value);
    }

    public void setY(double value) {
        this.setRawAxis(this.m_joystick != null ? this.m_joystick.getYChannel() : 1, value);
    }

    public void setZ(double value) {
        this.setRawAxis(this.m_joystick != null ? this.m_joystick.getZChannel() : 2, value);
    }

    public void setTwist(double value) {
        this.setRawAxis(this.m_joystick != null ? this.m_joystick.getTwistChannel() : 2, value);
    }

    public void setThrottle(double value) {
        this.setRawAxis(this.m_joystick != null ? this.m_joystick.getThrottleChannel() : 3, value);
    }

    public void setTrigger(boolean state) {
        this.setRawButton(1, state);
    }

    public void setTop(boolean state) {
        this.setRawButton(2, state);
    }
}

