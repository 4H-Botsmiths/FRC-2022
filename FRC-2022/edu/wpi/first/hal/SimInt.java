/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.SimDeviceJNI;
import edu.wpi.first.hal.SimValue;

public class SimInt
extends SimValue {
    public SimInt(int handle) {
        super(handle);
    }

    public int get() {
        return SimDeviceJNI.getSimValueInt(this.m_handle);
    }

    public void set(int value) {
        SimDeviceJNI.setSimValueInt(this.m_handle, value);
    }

    public void reset() {
        SimDeviceJNI.resetSimValue(this.m_handle);
    }
}

