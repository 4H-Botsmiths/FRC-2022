/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.geometry;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.NONE)
public class Rotation2d {
    private final double m_value;
    private final double m_cos;
    private final double m_sin;

    public Rotation2d() {
        this.m_value = 0.0;
        this.m_cos = 1.0;
        this.m_sin = 0.0;
    }

    @JsonCreator
    public Rotation2d(@JsonProperty(required=true, value="radians") double value) {
        this.m_value = value;
        this.m_cos = Math.cos(value);
        this.m_sin = Math.sin(value);
    }

    public Rotation2d(double x, double y) {
        double magnitude = Math.hypot(x, y);
        if (magnitude > 1.0E-6) {
            this.m_sin = y / magnitude;
            this.m_cos = x / magnitude;
        } else {
            this.m_sin = 0.0;
            this.m_cos = 1.0;
        }
        this.m_value = Math.atan2(this.m_sin, this.m_cos);
    }

    public static Rotation2d fromDegrees(double degrees) {
        return new Rotation2d(Math.toRadians(degrees));
    }

    public Rotation2d plus(Rotation2d other) {
        return this.rotateBy(other);
    }

    public Rotation2d minus(Rotation2d other) {
        return this.rotateBy(other.unaryMinus());
    }

    public Rotation2d unaryMinus() {
        return new Rotation2d(-this.m_value);
    }

    public Rotation2d times(double scalar) {
        return new Rotation2d(this.m_value * scalar);
    }

    public Rotation2d rotateBy(Rotation2d other) {
        return new Rotation2d(this.m_cos * other.m_cos - this.m_sin * other.m_sin, this.m_cos * other.m_sin + this.m_sin * other.m_cos);
    }

    @JsonProperty
    public double getRadians() {
        return this.m_value;
    }

    public double getDegrees() {
        return Math.toDegrees(this.m_value);
    }

    public double getCos() {
        return this.m_cos;
    }

    public double getSin() {
        return this.m_sin;
    }

    public double getTan() {
        return this.m_sin / this.m_cos;
    }

    public String toString() {
        return String.format("Rotation2d(Rads: %.2f, Deg: %.2f)", this.m_value, Math.toDegrees(this.m_value));
    }

    public boolean equals(Object obj) {
        if (obj instanceof Rotation2d) {
            Rotation2d other = (Rotation2d)obj;
            return Math.hypot(this.m_cos - other.m_cos, this.m_sin - other.m_sin) < 1.0E-9;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.m_value);
    }
}

