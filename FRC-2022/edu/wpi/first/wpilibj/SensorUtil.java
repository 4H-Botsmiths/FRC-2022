/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.AnalogJNI;
import edu.wpi.first.hal.ConstantsJNI;
import edu.wpi.first.hal.DIOJNI;
import edu.wpi.first.hal.PWMJNI;
import edu.wpi.first.hal.PortsJNI;
import edu.wpi.first.hal.RelayJNI;

public final class SensorUtil {
    public static final int kSystemClockTicksPerMicrosecond = ConstantsJNI.getSystemClockTicksPerMicrosecond();
    public static final int kDigitalChannels = PortsJNI.getNumDigitalChannels();
    public static final int kAnalogInputChannels = PortsJNI.getNumAnalogInputs();
    public static final int kAnalogOutputChannels = PortsJNI.getNumAnalogOutputs();
    public static final int kCTRESolenoidChannels = PortsJNI.getNumCTRESolenoidChannels();
    public static final int kPwmChannels = PortsJNI.getNumPWMChannels();
    public static final int kRelayChannels = PortsJNI.getNumRelayHeaders();
    public static final int kCTREPDPChannels = PortsJNI.getNumCTREPDPChannels();
    public static final int kCTREPDPModules = PortsJNI.getNumCTREPDPModules();
    public static final int kCTREPCMModules = PortsJNI.getNumCTREPCMModules();
    public static final int kREVPHChannels = PortsJNI.getNumREVPHChannels();
    public static final int kREVPHModules = PortsJNI.getNumREVPHModules();

    public static void checkDigitalChannel(int channel) {
        if (!DIOJNI.checkDIOChannel(channel)) {
            StringBuilder buf = new StringBuilder();
            buf.append("Requested DIO channel is out of range. Minimum: 0, Maximum: ").append(kDigitalChannels).append(", Requested: ").append(channel);
            throw new IllegalArgumentException(buf.toString());
        }
    }

    public static void checkRelayChannel(int channel) {
        if (!RelayJNI.checkRelayChannel(channel)) {
            StringBuilder buf = new StringBuilder();
            buf.append("Requested relay channel is out of range. Minimum: 0, Maximum: ").append(kRelayChannels).append(", Requested: ").append(channel);
            throw new IllegalArgumentException(buf.toString());
        }
    }

    public static void checkPWMChannel(int channel) {
        if (!PWMJNI.checkPWMChannel(channel)) {
            StringBuilder buf = new StringBuilder();
            buf.append("Requested PWM channel is out of range. Minimum: 0, Maximum: ").append(kPwmChannels).append(", Requested: ").append(channel);
            throw new IllegalArgumentException(buf.toString());
        }
    }

    public static void checkAnalogInputChannel(int channel) {
        if (!AnalogJNI.checkAnalogInputChannel(channel)) {
            StringBuilder buf = new StringBuilder();
            buf.append("Requested analog input channel is out of range. Minimum: 0, Maximum: ").append(kAnalogInputChannels).append(", Requested: ").append(channel);
            throw new IllegalArgumentException(buf.toString());
        }
    }

    public static void checkAnalogOutputChannel(int channel) {
        if (!AnalogJNI.checkAnalogOutputChannel(channel)) {
            StringBuilder buf = new StringBuilder();
            buf.append("Requested analog output channel is out of range. Minimum: 0, Maximum: ").append(kAnalogOutputChannels).append(", Requested: ").append(channel);
            throw new IllegalArgumentException(buf.toString());
        }
    }

    public static int getDefaultCTREPCMModule() {
        return 0;
    }

    public static int getDefaultREVPHModule() {
        return 1;
    }

    private SensorUtil() {
    }
}

