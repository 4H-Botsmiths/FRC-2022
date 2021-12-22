/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.NetworkTable;

@FunctionalInterface
public interface TableListener {
    public void tableCreated(NetworkTable var1, String var2, NetworkTable var3);
}

