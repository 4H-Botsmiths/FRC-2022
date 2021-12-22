/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.interfaces;

public interface Accelerometer {
    public void setRange(Range var1);

    public double getX();

    public double getY();

    public double getZ();

    public static enum Range {
        k2G,
        k4G,
        k8G,
        k16G;

    }
}

