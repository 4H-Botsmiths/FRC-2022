/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.LayoutType;

public enum BuiltInLayouts implements LayoutType
{
    kList("List Layout"),
    kGrid("Grid Layout");

    private final String m_layoutName;

    private BuiltInLayouts(String layoutName) {
        this.m_layoutName = layoutName;
    }

    @Override
    public String getLayoutName() {
        return this.m_layoutName;
    }
}

