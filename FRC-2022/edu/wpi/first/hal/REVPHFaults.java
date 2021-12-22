/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

public class REVPHFaults {
    public final boolean Channel0Fault;
    public final boolean Channel1Fault;
    public final boolean Channel2Fault;
    public final boolean Channel3Fault;
    public final boolean Channel4Fault;
    public final boolean Channel5Fault;
    public final boolean Channel6Fault;
    public final boolean Channel7Fault;
    public final boolean Channel8Fault;
    public final boolean Channel9Fault;
    public final boolean Channel10Fault;
    public final boolean Channel11Fault;
    public final boolean Channel12Fault;
    public final boolean Channel13Fault;
    public final boolean Channel14Fault;
    public final boolean Channel15Fault;
    public final boolean CompressorOverCurrent;
    public final boolean CompressorOpen;
    public final boolean SolenoidOverCurrent;
    public final boolean Brownout;
    public final boolean CanWarning;
    public final boolean HardwareFault;

    public REVPHFaults(int faults) {
        this.Channel0Fault = (faults & 1) != 0;
        this.Channel1Fault = (faults & 2) != 0;
        this.Channel2Fault = (faults & 4) != 0;
        this.Channel3Fault = (faults & 8) != 0;
        this.Channel4Fault = (faults & 0x10) != 0;
        this.Channel5Fault = (faults & 0x20) != 0;
        this.Channel6Fault = (faults & 0x40) != 0;
        this.Channel7Fault = (faults & 0x80) != 0;
        this.Channel8Fault = (faults & 0x100) != 0;
        this.Channel9Fault = (faults & 0x200) != 0;
        this.Channel10Fault = (faults & 0x400) != 0;
        this.Channel11Fault = (faults & 0x800) != 0;
        this.Channel12Fault = (faults & 0x1000) != 0;
        this.Channel13Fault = (faults & 0x2000) != 0;
        this.Channel14Fault = (faults & 0x4000) != 0;
        this.Channel15Fault = (faults & 0x8000) != 0;
        this.CompressorOverCurrent = (faults & 0x8000) != 0;
        this.CompressorOpen = (faults & 0x10000) != 0;
        this.SolenoidOverCurrent = (faults & 0x20000) != 0;
        this.Brownout = (faults & 0x40000) != 0;
        this.CanWarning = (faults & 0x80000) != 0;
        this.HardwareFault = (faults & 0x100000) != 0;
    }
}

