/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.HALValue;
import edu.wpi.first.hal.SimDeviceJNI;

public class SimValue {
    protected final int m_handle;

    public SimValue(int handle) {
        this.m_handle = handle;
    }

    public int getNativeHandle() {
        return this.m_handle;
    }

    public HALValue getValue() {
        return SimDeviceJNI.getSimValue(this.m_handle);
    }

    public void setValue(HALValue value) {
        SimDeviceJNI.setSimValue(this.m_handle, value);
    }
}

