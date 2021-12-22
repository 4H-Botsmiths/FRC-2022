/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.util.sendable.SendableBuilder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface NTSendableBuilder
extends SendableBuilder {
    public void setUpdateTable(Runnable var1);

    public NetworkTableEntry getEntry(String var1);

    public void addValueProperty(String var1, Supplier<NetworkTableValue> var2, Consumer<NetworkTableValue> var3);

    public NetworkTable getTable();

    @Override
    default public SendableBuilder.BackendKind getBackendKind() {
        return SendableBuilder.BackendKind.kNetworkTables;
    }
}

