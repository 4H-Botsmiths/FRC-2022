/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableValue;

@FunctionalInterface
public interface TableEntryListener
extends EntryListenerFlags {
    public void valueChanged(NetworkTable var1, String var2, NetworkTableEntry var3, NetworkTableValue var4, int var5);
}

