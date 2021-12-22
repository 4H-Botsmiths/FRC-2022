/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command.button;

import edu.wpi.first.wpilibj2.command.button.Button;

public class InternalButton
extends Button {
    private boolean m_pressed;
    private boolean m_inverted;

    public InternalButton() {
        this(false);
    }

    public InternalButton(boolean inverted) {
        this.m_pressed = this.m_inverted = inverted;
    }

    public void setInverted(boolean inverted) {
        this.m_inverted = inverted;
    }

    public void setPressed(boolean pressed) {
        this.m_pressed = pressed;
    }

    @Override
    public boolean get() {
        return this.m_pressed ^ this.m_inverted;
    }
}

