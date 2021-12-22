/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.SimDeviceJNI;
import edu.wpi.first.hal.SimValue;

public class SimDouble
extends SimValue {
    public SimDouble(int handle) {
        super(handle);
    }

    public double get() {
        return SimDeviceJNI.getSimValueDouble(this.m_handle);
    }

    public void set(double value) {
        SimDeviceJNI.setSimValueDouble(this.m_handle, value);
    }

    public void reset() {
        SimDeviceJNI.resetSimValue(this.m_handle);
    }
}

