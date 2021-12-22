/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.SimDeviceJNI;
import edu.wpi.first.hal.SimValue;

public class SimBoolean
extends SimValue {
    public SimBoolean(int handle) {
        super(handle);
    }

    public boolean get() {
        return SimDeviceJNI.getSimValueBoolean(this.m_handle);
    }

    public void set(boolean value) {
        SimDeviceJNI.setSimValueBoolean(this.m_handle, value);
    }
}

