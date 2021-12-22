/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.interfaces;

import edu.wpi.first.math.geometry.Rotation2d;

public interface Gyro
extends AutoCloseable {
    public void calibrate();

    public void reset();

    public double getAngle();

    public double getRate();

    default public Rotation2d getRotation2d() {
        return Rotation2d.fromDegrees(-this.getAngle());
    }
}

