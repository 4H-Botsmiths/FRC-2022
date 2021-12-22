/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.can;

public class CANStatus {
    public double percentBusUtilization;
    public int busOffCount;
    public int txFullCount;
    public int receiveErrorCount;
    public int transmitErrorCount;

    public void setStatus(double percentBusUtilization, int busOffCount, int txFullCount, int receiveErrorCount, int transmitErrorCount) {
        this.percentBusUtilization = percentBusUtilization;
        this.busOffCount = busOffCount;
        this.txFullCount = txFullCount;
        this.receiveErrorCount = receiveErrorCount;
        this.transmitErrorCount = transmitErrorCount;
    }
}

