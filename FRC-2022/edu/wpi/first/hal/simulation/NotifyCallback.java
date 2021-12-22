/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.HALValue;

public interface NotifyCallback {
    public void callback(String var1, HALValue var2);

    default public void callbackNative(String name, int type, long value1, double value2) {
        this.callback(name, HALValue.fromNative(type, value1, value2));
    }
}

