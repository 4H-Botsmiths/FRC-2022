/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.JNIWrapper;

public class PortsJNI
extends JNIWrapper {
    public static native int getNumAccumulators();

    public static native int getNumAnalogTriggers();

    public static native int getNumAnalogInputs();

    public static native int getNumAnalogOutputs();

    public static native int getNumCounters();

    public static native int getNumDigitalHeaders();

    public static native int getNumPWMHeaders();

    public static native int getNumDigitalChannels();

    public static native int getNumPWMChannels();

    public static native int getNumDigitalPWMOutputs();

    public static native int getNumEncoders();

    public static native int getNumInterrupts();

    public static native int getNumRelayChannels();

    public static native int getNumRelayHeaders();

    public static native int getNumCTREPCMModules();

    public static native int getNumCTRESolenoidChannels();

    public static native int getNumCTREPDPModules();

    public static native int getNumCTREPDPChannels();

    public static native int getNumREVPDHModules();

    public static native int getNumREVPDHChannels();

    public static native int getNumREVPHModules();

    public static native int getNumREVPHChannels();
}

