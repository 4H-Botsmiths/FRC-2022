/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.geometry;

import java.util.Objects;

public class Twist2d {
    public double dx;
    public double dy;
    public double dtheta;

    public Twist2d() {
    }

    public Twist2d(double dx, double dy, double dtheta) {
        this.dx = dx;
        this.dy = dy;
        this.dtheta = dtheta;
    }

    public String toString() {
        return String.format("Twist2d(dX: %.2f, dY: %.2f, dTheta: %.2f)", this.dx, this.dy, this.dtheta);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Twist2d) {
            return Math.abs(((Twist2d)obj).dx - this.dx) < 1.0E-9 && Math.abs(((Twist2d)obj).dy - this.dy) < 1.0E-9 && Math.abs(((Twist2d)obj).dtheta - this.dtheta) < 1.0E-9;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.dx, this.dy, this.dtheta);
    }
}

