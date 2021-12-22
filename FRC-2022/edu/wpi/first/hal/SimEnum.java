/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.SimDeviceJNI;
import edu.wpi.first.hal.SimValue;

public class SimEnum
extends SimValue {
    public SimEnum(int handle) {
        super(handle);
    }

    public int get() {
        return SimDeviceJNI.getSimValueEnum(this.m_handle);
    }

    public void set(int value) {
        SimDeviceJNI.setSimValueEnum(this.m_handle, value);
    }
}

