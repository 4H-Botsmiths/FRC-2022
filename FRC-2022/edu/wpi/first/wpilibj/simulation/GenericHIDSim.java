/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;

public class GenericHIDSim {
    protected final int m_port;

    public GenericHIDSim(GenericHID joystick) {
        this.m_port = joystick.getPort();
    }

    public GenericHIDSim(int port) {
        this.m_port = port;
    }

    public void notifyNewData() {
        DriverStationSim.notifyNewData();
    }

    public void setRawButton(int button, boolean value) {
        DriverStationSim.setJoystickButton(this.m_port, button, value);
    }

    public void setRawAxis(int axis, double value) {
        DriverStationSim.setJoystickAxis(this.m_port, axis, value);
    }

    public void setPOV(int pov, int value) {
        DriverStationSim.setJoystickPOV(this.m_port, pov, value);
    }

    public void setPOV(int value) {
        this.setPOV(0, value);
    }

    public void setAxisCount(int count) {
        DriverStationSim.setJoystickAxisCount(this.m_port, count);
    }

    public void setPOVCount(int count) {
        DriverStationSim.setJoystickPOVCount(this.m_port, count);
    }

    public void setButtonCount(int count) {
        DriverStationSim.setJoystickButtonCount(this.m_port, count);
    }

    public void setType(GenericHID.HIDType type) {
        DriverStationSim.setJoystickType(this.m_port, type.value);
    }

    public void setName(String name) {
        DriverStationSim.setJoystickName(this.m_port, name);
    }

    public void setAxisType(int axis, int type) {
        DriverStationSim.setJoystickAxisType(this.m_port, axis, type);
    }

    public boolean getOutput(int outputNumber) {
        long outputs = this.getOutputs();
        return (outputs & (long)(1 << outputNumber - 1)) != 0L;
    }

    public long getOutputs() {
        return DriverStationSim.getJoystickOutputs(this.m_port);
    }

    public double getRumble(GenericHID.RumbleType type) {
        int value = DriverStationSim.getJoystickRumble(this.m_port, type == GenericHID.RumbleType.kLeftRumble ? 0 : 1);
        return (double)value / 65535.0;
    }
}

