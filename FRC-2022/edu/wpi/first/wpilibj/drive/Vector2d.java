/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.drive;

public class Vector2d {
    public double x;
    public double y;

    public Vector2d() {
    }

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void rotate(double angle) {
        double cosA = Math.cos(angle * (Math.PI / 180));
        double sinA = Math.sin(angle * (Math.PI / 180));
        double[] out = new double[]{this.x * cosA - this.y * sinA, this.x * sinA + this.y * cosA};
        this.x = out[0];
        this.y = out[1];
    }

    public double dot(Vector2d vec) {
        return this.x * vec.x + this.y * vec.y;
    }

    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double scalarProject(Vector2d vec) {
        return this.dot(vec) / vec.magnitude();
    }
}

