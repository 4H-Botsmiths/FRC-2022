/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.wpilibj.DriverStation;

public class DSControlWord {
    private final ControlWord m_controlWord = new ControlWord();

    public DSControlWord() {
        this.update();
    }

    public void update() {
        DriverStation.updateControlWordFromCache(this.m_controlWord);
    }

    public boolean isEnabled() {
        return this.m_controlWord.getEnabled() && this.m_controlWord.getDSAttached();
    }

    public boolean isDisabled() {
        return !this.isEnabled();
    }

    public boolean isEStopped() {
        return this.m_controlWord.getEStop();
    }

    public boolean isAutonomous() {
        return this.m_controlWord.getAutonomous();
    }

    public boolean isAutonomousEnabled() {
        return this.m_controlWord.getAutonomous() && this.m_controlWord.getEnabled() && this.m_controlWord.getDSAttached();
    }

    public boolean isTeleop() {
        return !this.isAutonomous() && !this.isTest();
    }

    public boolean isTeleopEnabled() {
        return !this.m_controlWord.getAutonomous() && !this.m_controlWord.getTest() && this.m_controlWord.getEnabled() && this.m_controlWord.getDSAttached();
    }

    public boolean isTest() {
        return this.m_controlWord.getTest();
    }

    public boolean isDSAttached() {
        return this.m_controlWord.getDSAttached();
    }

    public boolean isFMSAttached() {
        return this.m_controlWord.getFMSAttached();
    }
}

