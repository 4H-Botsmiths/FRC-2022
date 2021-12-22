/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.DriverStation;
import java.util.HashMap;
import java.util.Map;

public class GenericHID {
    private final int m_port;
    private int m_outputs;
    private short m_leftRumble;
    private short m_rightRumble;

    public GenericHID(int port) {
        this.m_port = port;
    }

    public boolean getRawButton(int button) {
        return DriverStation.getStickButton(this.m_port, (byte)button);
    }

    public boolean getRawButtonPressed(int button) {
        return DriverStation.getStickButtonPressed(this.m_port, (byte)button);
    }

    public boolean getRawButtonReleased(int button) {
        return DriverStation.getStickButtonReleased(this.m_port, button);
    }

    public double getRawAxis(int axis) {
        return DriverStation.getStickAxis(this.m_port, axis);
    }

    public int getPOV(int pov) {
        return DriverStation.getStickPOV(this.m_port, pov);
    }

    public int getPOV() {
        return this.getPOV(0);
    }

    public int getAxisCount() {
        return DriverStation.getStickAxisCount(this.m_port);
    }

    public int getPOVCount() {
        return DriverStation.getStickPOVCount(this.m_port);
    }

    public int getButtonCount() {
        return DriverStation.getStickButtonCount(this.m_port);
    }

    public boolean isConnected() {
        return DriverStation.isJoystickConnected(this.m_port);
    }

    public HIDType getType() {
        return HIDType.of(DriverStation.getJoystickType(this.m_port));
    }

    public String getName() {
        return DriverStation.getJoystickName(this.m_port);
    }

    public int getAxisType(int axis) {
        return DriverStation.getJoystickAxisType(this.m_port, axis);
    }

    public int getPort() {
        return this.m_port;
    }

    public void setOutput(int outputNumber, boolean value) {
        this.m_outputs = this.m_outputs & ~(1 << outputNumber - 1) | (value ? 1 : 0) << outputNumber - 1;
        HAL.setJoystickOutputs((byte)this.m_port, this.m_outputs, this.m_leftRumble, this.m_rightRumble);
    }

    public void setOutputs(int value) {
        this.m_outputs = value;
        HAL.setJoystickOutputs((byte)this.m_port, this.m_outputs, this.m_leftRumble, this.m_rightRumble);
    }

    public void setRumble(RumbleType type, double value) {
        if (value < 0.0) {
            value = 0.0;
        } else if (value > 1.0) {
            value = 1.0;
        }
        if (type == RumbleType.kLeftRumble) {
            this.m_leftRumble = (short)(value * 65535.0);
        } else {
            this.m_rightRumble = (short)(value * 65535.0);
        }
        HAL.setJoystickOutputs((byte)this.m_port, this.m_outputs, this.m_leftRumble, this.m_rightRumble);
    }

    public static enum HIDType {
        kUnknown(-1),
        kXInputUnknown(0),
        kXInputGamepad(1),
        kXInputWheel(2),
        kXInputArcadeStick(3),
        kXInputFlightStick(4),
        kXInputDancePad(5),
        kXInputGuitar(6),
        kXInputGuitar2(7),
        kXInputDrumKit(8),
        kXInputGuitar3(11),
        kXInputArcadePad(19),
        kHIDJoystick(20),
        kHIDGamepad(21),
        kHIDDriving(22),
        kHIDFlight(23),
        kHID1stPerson(24);

        public final int value;
        private static final Map<Integer, HIDType> map;

        private HIDType(int value) {
            this.value = value;
        }

        public static HIDType of(int value) {
            return map.get(value);
        }

        static {
            map = new HashMap<Integer, HIDType>();
            for (HIDType hidType : HIDType.values()) {
                map.put(hidType.value, hidType);
            }
        }
    }

    public static enum RumbleType {
        kLeftRumble,
        kRightRumble;

    }
}

