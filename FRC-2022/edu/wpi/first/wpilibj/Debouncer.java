/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.Timer;

public class Debouncer {
    private final Timer m_timer = new Timer();
    private final double m_debounceTime;
    private final DebounceType m_debounceType;
    private boolean m_baseline;

    public Debouncer(double debounceTime, DebounceType type) {
        this.m_debounceTime = debounceTime;
        this.m_debounceType = type;
        this.m_timer.start();
        switch (this.m_debounceType) {
            case kBoth: 
            case kRising: {
                this.m_baseline = false;
                break;
            }
            case kFalling: {
                this.m_baseline = true;
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid debounce type!");
            }
        }
    }

    public Debouncer(double debounceTime) {
        this(debounceTime, DebounceType.kRising);
    }

    public boolean calculate(boolean input) {
        if (input == this.m_baseline) {
            this.m_timer.reset();
        }
        if (this.m_timer.hasElapsed(this.m_debounceTime)) {
            if (this.m_debounceType == DebounceType.kBoth) {
                this.m_baseline = input;
                this.m_timer.reset();
            }
            return input;
        }
        return this.m_baseline;
    }

    public static enum DebounceType {
        kRising,
        kFalling,
        kBoth;

    }
}

