/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableType;

public final class EntryInfo {
    public final int entry;
    public final String name;
    public final NetworkTableType type;
    public final int flags;
    public final long last_change;
    private final NetworkTableInstance m_inst;
    private NetworkTableEntry m_entryObject;

    public EntryInfo(NetworkTableInstance inst, int entry, String name, int type, int flags, long lastChange) {
        this.m_inst = inst;
        this.entry = entry;
        this.name = name;
        this.type = NetworkTableType.getFromInt(type);
        this.flags = flags;
        this.last_change = lastChange;
    }

    NetworkTableEntry getEntry() {
        if (this.m_entryObject == null) {
            this.m_entryObject = new NetworkTableEntry(this.m_inst, this.entry);
        }
        return this.m_entryObject;
    }
}

