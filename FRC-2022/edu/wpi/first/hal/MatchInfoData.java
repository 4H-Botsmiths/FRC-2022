/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

public class MatchInfoData {
    public String eventName = "";
    public String gameSpecificMessage = "";
    public int matchNumber;
    public int replayNumber;
    public int matchType;

    public void setData(String eventName, String gameSpecificMessage, int matchNumber, int replayNumber, int matchType) {
        this.eventName = eventName;
        this.gameSpecificMessage = gameSpecificMessage;
        this.matchNumber = matchNumber;
        this.replayNumber = replayNumber;
        this.matchType = matchType;
    }
}

