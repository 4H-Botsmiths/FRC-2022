/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;

public final class EntryNotification {
    public final int listener;
    public final int entry;
    public final String name;
    public final NetworkTableValue value;
    public final int flags;
    private final NetworkTableInstance m_inst;
    NetworkTableEntry m_entryObject;

    public EntryNotification(NetworkTableInstance inst, int listener, int entry, String name, NetworkTableValue value, int flags) {
        this.m_inst = inst;
        this.listener = listener;
        this.entry = entry;
        this.name = name;
        this.value = value;
        this.flags = flags;
    }

    public NetworkTableEntry getEntry() {
        if (this.m_entryObject == null) {
            this.m_entryObject = new NetworkTableEntry(this.m_inst, this.entry);
        }
        return this.m_entryObject;
    }
}

