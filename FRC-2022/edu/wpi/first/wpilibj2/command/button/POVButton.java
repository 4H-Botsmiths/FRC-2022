/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command.button;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import edu.wpi.first.wpilibj2.command.button.Button;

public class POVButton
extends Button {
    private final GenericHID m_joystick;
    private final int m_angle;
    private final int m_povNumber;

    public POVButton(GenericHID joystick, int angle, int povNumber) {
        ErrorMessages.requireNonNullParam(joystick, "joystick", "POVButton");
        this.m_joystick = joystick;
        this.m_angle = angle;
        this.m_povNumber = povNumber;
    }

    public POVButton(GenericHID joystick, int angle) {
        this(joystick, angle, 0);
    }

    @Override
    public boolean get() {
        return this.m_joystick.getPOV(this.m_povNumber) == this.m_angle;
    }
}

