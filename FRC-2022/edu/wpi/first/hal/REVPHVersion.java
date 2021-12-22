/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

public class REVPHVersion {
    public final int firmwareMajor;
    public final int firmwareMinor;
    public final int firmwareFix;
    public final int hardwareMinor;
    public final int hardwareMajor;
    public final int uniqueId;

    public REVPHVersion(int firmwareMajor, int firmwareMinor, int firmwareFix, int hardwareMinor, int hardwareMajor, int uniqueId) {
        this.firmwareMajor = firmwareMajor;
        this.firmwareMinor = firmwareMinor;
        this.firmwareFix = firmwareFix;
        this.hardwareMinor = hardwareMinor;
        this.hardwareMajor = hardwareMajor;
        this.uniqueId = uniqueId;
    }
}

