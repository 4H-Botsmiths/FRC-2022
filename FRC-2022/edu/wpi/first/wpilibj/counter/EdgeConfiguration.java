/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.counter;

public enum EdgeConfiguration {
    kNone(false, false),
    kRisingEdge(true, false),
    kFallingEdge(false, true),
    kBoth(true, true);

    public final boolean rising;
    public final boolean falling;

    private EdgeConfiguration(boolean rising, boolean falling) {
        this.rising = rising;
        this.falling = falling;
    }
}

