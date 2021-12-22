/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.DIOJNI;

public class RelayJNI
extends DIOJNI {
    public static native int initializeRelayPort(int var0, boolean var1);

    public static native void freeRelayPort(int var0);

    public static native boolean checkRelayChannel(int var0);

    public static native void setRelay(int var0, boolean var1);

    public static native boolean getRelay(int var0);
}

