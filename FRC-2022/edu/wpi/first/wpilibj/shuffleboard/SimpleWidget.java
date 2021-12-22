/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardWidget;

public final class SimpleWidget
extends ShuffleboardWidget<SimpleWidget> {
    private NetworkTableEntry m_entry;

    SimpleWidget(ShuffleboardContainer parent, String title) {
        super(parent, title);
    }

    public NetworkTableEntry getEntry() {
        if (this.m_entry == null) {
            this.forceGenerate();
        }
        return this.m_entry;
    }

    @Override
    public void buildInto(NetworkTable parentTable, NetworkTable metaTable) {
        this.buildMetadata(metaTable);
        if (this.m_entry == null) {
            this.m_entry = parentTable.getEntry(this.getTitle());
        }
    }

    private void forceGenerate() {
        ShuffleboardContainer parent = this.getParent();
        while (parent instanceof ShuffleboardLayout) {
            parent = ((ShuffleboardLayout)parent).getParent();
        }
        ShuffleboardTab tab = (ShuffleboardTab)parent;
        tab.getRoot().update();
    }
}

