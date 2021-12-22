/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.SimDeviceJNI;
import edu.wpi.first.hal.SimValue;

public class SimLong
extends SimValue {
    public SimLong(int handle) {
        super(handle);
    }

    public long get() {
        return SimDeviceJNI.getSimValueLong(this.m_handle);
    }

    public void set(long value) {
        SimDeviceJNI.setSimValueLong(this.m_handle, value);
    }

    public void reset() {
        SimDeviceJNI.resetSimValue(this.m_handle);
    }
}

