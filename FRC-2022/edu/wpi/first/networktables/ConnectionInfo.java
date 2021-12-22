/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

public final class ConnectionInfo {
    public final String remote_id;
    public final String remote_ip;
    public final int remote_port;
    public final long last_update;
    public final int protocol_version;

    public ConnectionInfo(String remoteId, String remoteIp, int remotePort, long lastUpdate, int protocolVersion) {
        this.remote_id = remoteId;
        this.remote_ip = remoteIp;
        this.remote_port = remotePort;
        this.last_update = lastUpdate;
        this.protocol_version = protocolVersion;
    }
}

