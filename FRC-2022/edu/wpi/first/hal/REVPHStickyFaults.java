/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

public class REVPHStickyFaults {
    public final boolean CompressorOverCurrent;
    public final boolean CompressorOpen;
    public final boolean SolenoidOverCurrent;
    public final boolean Brownout;
    public final boolean CanWarning;
    public final boolean CanBusOff;
    public final boolean HasReset;

    public REVPHStickyFaults(int faults) {
        this.CompressorOverCurrent = (faults & 1) != 0;
        this.CompressorOpen = (faults & 2) != 0;
        this.SolenoidOverCurrent = (faults & 4) != 0;
        this.Brownout = (faults & 8) != 0;
        this.CanWarning = (faults & 0x10) != 0;
        this.CanBusOff = (faults & 0x20) != 0;
        this.HasReset = (faults & 0x40) != 0;
    }
}

