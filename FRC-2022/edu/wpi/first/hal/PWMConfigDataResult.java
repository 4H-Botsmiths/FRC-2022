/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

public class PWMConfigDataResult {
    public int max;
    public int deadbandMax;
    public int center;
    public int deadbandMin;
    public int min;

    PWMConfigDataResult(int max, int deadbandMax, int center, int deadbandMin, int min) {
        this.max = max;
        this.deadbandMax = deadbandMax;
        this.center = center;
        this.deadbandMin = deadbandMin;
        this.min = min;
    }
}

