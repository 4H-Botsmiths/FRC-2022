/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj2.command.button;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.button.Button;

public class NetworkButton
extends Button {
    private final NetworkTableEntry m_entry;

    public NetworkButton(NetworkTableEntry entry) {
        this.m_entry = entry;
    }

    public NetworkButton(NetworkTable table, String field) {
        this(table.getEntry(field));
    }

    public NetworkButton(String table, String field) {
        this(NetworkTableInstance.getDefault().getTable(table), field);
    }

    @Override
    public boolean get() {
        return this.m_entry.getInstance().isConnected() && this.m_entry.getBoolean(false);
    }
}

