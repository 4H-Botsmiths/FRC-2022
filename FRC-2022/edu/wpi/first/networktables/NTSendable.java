/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.NTSendableBuilder;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

public interface NTSendable
extends Sendable {
    public void initSendable(NTSendableBuilder var1);

    @Override
    default public void initSendable(SendableBuilder builder) {
        if (builder.getBackendKind() == SendableBuilder.BackendKind.kNetworkTables) {
            this.initSendable((NTSendableBuilder)builder);
        }
    }
}

