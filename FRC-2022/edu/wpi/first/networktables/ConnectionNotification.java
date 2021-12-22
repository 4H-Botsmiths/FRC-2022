/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.ConnectionInfo;
import edu.wpi.first.networktables.NetworkTableInstance;

public final class ConnectionNotification {
    public final int listener;
    public final boolean connected;
    public final ConnectionInfo conn;
    private final NetworkTableInstance m_inst;

    public ConnectionNotification(NetworkTableInstance inst, int listener, boolean connected, ConnectionInfo conn) {
        this.m_inst = inst;
        this.listener = listener;
        this.connected = connected;
        this.conn = conn;
    }

    public NetworkTableInstance getInstance() {
        return this.m_inst;
    }
}

