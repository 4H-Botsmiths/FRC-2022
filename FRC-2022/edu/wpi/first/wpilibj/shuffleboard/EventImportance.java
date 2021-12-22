/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

public enum EventImportance {
    kTrivial("TRIVIAL"),
    kLow("LOW"),
    kNormal("NORMAL"),
    kHigh("HIGH"),
    kCritical("CRITICAL");

    private final String m_simpleName;

    private EventImportance(String simpleName) {
        this.m_simpleName = simpleName;
    }

    public String getSimpleName() {
        return this.m_simpleName;
    }
}

