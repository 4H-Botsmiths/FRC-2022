/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.networktables;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTablesJNI;

public final class RpcCall
implements AutoCloseable {
    private final NetworkTableEntry m_entry;
    private int m_call;

    public RpcCall(NetworkTableEntry entry, int call) {
        this.m_entry = entry;
        this.m_call = call;
    }

    @Override
    public synchronized void close() {
        if (this.m_call != 0) {
            this.cancelResult();
        }
    }

    public boolean isValid() {
        return this.m_call != 0;
    }

    public NetworkTableEntry getEntry() {
        return this.m_entry;
    }

    public int getCall() {
        return this.m_call;
    }

    public byte[] getResult() {
        byte[] result = NetworkTablesJNI.getRpcResult(this.m_entry.getHandle(), this.m_call);
        if (result.length != 0) {
            this.m_call = 0;
        }
        return result;
    }

    public byte[] getResult(double timeout) {
        byte[] result = NetworkTablesJNI.getRpcResult(this.m_entry.getHandle(), this.m_call, timeout);
        if (result.length != 0) {
            this.m_call = 0;
        }
        return result;
    }

    public void cancelResult() {
        NetworkTablesJNI.cancelRpcResult(this.m_entry.getHandle(), this.m_call);
    }
}

