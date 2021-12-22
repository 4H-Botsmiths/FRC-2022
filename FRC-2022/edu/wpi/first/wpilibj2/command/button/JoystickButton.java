/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command.button;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.button.Button;

public class JoystickButton
extends Button {
    private final GenericHID m_joystick;
    private final int m_buttonNumber;

    public JoystickButton(GenericHID joystick, int buttonNumber) {
        ErrorMessages.requireNonNullParam(joystick, "joystick", "JoystickButton");
        this.m_joystick = joystick;
        this.m_buttonNumber = buttonNumber;
    }

    @Override
    public boolean get() {
        return this.m_joystick.getRawButton(this.m_buttonNumber);
    }
}

