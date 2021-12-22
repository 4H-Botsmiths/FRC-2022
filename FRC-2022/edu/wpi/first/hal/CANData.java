/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

public class CANData {
    public final byte[] data = new byte[8];
    public int length;
    public long timestamp;

    public byte[] setData(int length, long timestamp) {
        this.length = length;
        this.timestamp = timestamp;
        return this.data;
    }
}

