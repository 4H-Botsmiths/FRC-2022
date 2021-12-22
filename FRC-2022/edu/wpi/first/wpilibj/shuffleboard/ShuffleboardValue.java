/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.shuffleboard;

import edu.wpi.first.networktables.NetworkTable;

interface ShuffleboardValue {
    public String getTitle();

    public void buildInto(NetworkTable var1, NetworkTable var2);
}

