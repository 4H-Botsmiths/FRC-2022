/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

public class ControlWord {
    private boolean m_enabled;
    private boolean m_autonomous;
    private boolean m_test;
    private boolean m_emergencyStop;
    private boolean m_fmsAttached;
    private boolean m_dsAttached;

    void update(boolean enabled, boolean autonomous, boolean test, boolean emergencyStop, boolean fmsAttached, boolean dsAttached) {
        this.m_enabled = enabled;
        this.m_autonomous = autonomous;
        this.m_test = test;
        this.m_emergencyStop = emergencyStop;
        this.m_fmsAttached = fmsAttached;
        this.m_dsAttached = dsAttached;
    }

    public void update(ControlWord word) {
        this.m_enabled = word.m_enabled;
        this.m_autonomous = word.m_autonomous;
        this.m_test = word.m_test;
        this.m_emergencyStop = word.m_emergencyStop;
        this.m_fmsAttached = word.m_fmsAttached;
        this.m_dsAttached = word.m_dsAttached;
    }

    public boolean getEnabled() {
        return this.m_enabled;
    }

    public boolean getAutonomous() {
        return this.m_autonomous;
    }

    public boolean getTest() {
        return this.m_test;
    }

    public boolean getEStop() {
        return this.m_emergencyStop;
    }

    public boolean getFMSAttached() {
        return this.m_fmsAttached;
    }

    public boolean getDSAttached() {
        return this.m_dsAttached;
    }
}

