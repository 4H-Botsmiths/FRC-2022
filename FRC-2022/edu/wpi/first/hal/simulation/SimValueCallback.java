/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.simulation;

import edu.wpi.first.hal.HALValue;

@FunctionalInterface
public interface SimValueCallback {
    public void callback(String var1, int var2, int var3, HALValue var4);

    default public void callbackNative(String name, int handle, int direction, int type, long value1, double value2) {
        this.callback(name, handle, direction, HALValue.fromNative(type, value1, value2));
    }
}

