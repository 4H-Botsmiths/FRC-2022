/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.ConnectionInfo;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTablesJNI;

public final class RpcAnswer {
    public final int entry;
    public int call;
    public final String name;
    public final byte[] params;
    public final ConnectionInfo conn;
    static final byte[] emptyResponse = new byte[0];
    private final NetworkTableInstance m_inst;
    NetworkTableEntry m_entryObject;

    public RpcAnswer(NetworkTableInstance inst, int entry, int call, String name, byte[] params, ConnectionInfo conn) {
        this.m_inst = inst;
        this.entry = entry;
        this.call = call;
        this.name = name;
        this.params = params;
        this.conn = conn;
    }

    void finish() {
        if (this.call != 0) {
            NetworkTablesJNI.postRpcResponse(this.entry, this.call, emptyResponse);
            this.call = 0;
        }
    }

    public boolean isValid() {
        return this.call != 0;
    }

    public boolean postResponse(byte[] result) {
        boolean ret = NetworkTablesJNI.postRpcResponse(this.entry, this.call, result);
        this.call = 0;
        return ret;
    }

    NetworkTableEntry getEntry() {
        if (this.m_entryObject == null) {
            this.m_entryObject = new NetworkTableEntry(this.m_inst, this.entry);
        }
        return this.m_entryObject;
    }
}

