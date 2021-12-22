/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

public class PowerDistributionFaults {
    public final boolean Channel0BreakerFault;
    public final boolean Channel1BreakerFault;
    public final boolean Channel2BreakerFault;
    public final boolean Channel3BreakerFault;
    public final boolean Channel4BreakerFault;
    public final boolean Channel5BreakerFault;
    public final boolean Channel6BreakerFault;
    public final boolean Channel7BreakerFault;
    public final boolean Channel8BreakerFault;
    public final boolean Channel9BreakerFault;
    public final boolean Channel10BreakerFault;
    public final boolean Channel11BreakerFault;
    public final boolean Channel12BreakerFault;
    public final boolean Channel13BreakerFault;
    public final boolean Channel14BreakerFault;
    public final boolean Channel15BreakerFault;
    public final boolean Channel16BreakerFault;
    public final boolean Channel17BreakerFault;
    public final boolean Channel18BreakerFault;
    public final boolean Channel19BreakerFault;
    public final boolean Channel20BreakerFault;
    public final boolean Channel21BreakerFault;
    public final boolean Channel22BreakerFault;
    public final boolean Channel23BreakerFault;
    public final boolean Brownout;
    public final boolean CanWarning;
    public final boolean HardwareFault;

    public PowerDistributionFaults(int faults) {
        this.Channel0BreakerFault = (faults & 1) != 0;
        this.Channel1BreakerFault = (faults & 2) != 0;
        this.Channel2BreakerFault = (faults & 4) != 0;
        this.Channel3BreakerFault = (faults & 8) != 0;
        this.Channel4BreakerFault = (faults & 0x10) != 0;
        this.Channel5BreakerFault = (faults & 0x20) != 0;
        this.Channel6BreakerFault = (faults & 0x40) != 0;
        this.Channel7BreakerFault = (faults & 0x80) != 0;
        this.Channel8BreakerFault = (faults & 0x100) != 0;
        this.Channel9BreakerFault = (faults & 0x200) != 0;
        this.Channel10BreakerFault = (faults & 0x400) != 0;
        this.Channel11BreakerFault = (faults & 0x800) != 0;
        this.Channel12BreakerFault = (faults & 0x1000) != 0;
        this.Channel13BreakerFault = (faults & 0x2000) != 0;
        this.Channel14BreakerFault = (faults & 0x4000) != 0;
        this.Channel15BreakerFault = (faults & 0x8000) != 0;
        this.Channel16BreakerFault = (faults & 0x10000) != 0;
        this.Channel17BreakerFault = (faults & 0x20000) != 0;
        this.Channel18BreakerFault = (faults & 0x40000) != 0;
        this.Channel19BreakerFault = (faults & 0x80000) != 0;
        this.Channel20BreakerFault = (faults & 0x100000) != 0;
        this.Channel21BreakerFault = (faults & 0x200000) != 0;
        this.Channel22BreakerFault = (faults & 0x400000) != 0;
        this.Channel23BreakerFault = (faults & 0x800000) != 0;
        this.Brownout = (faults & 0x1000000) != 0;
        this.CanWarning = (faults & 0x2000000) != 0;
        this.HardwareFault = (faults & 0x4000000) != 0;
    }
}

