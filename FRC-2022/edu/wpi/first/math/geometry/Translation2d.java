/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.geometry;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.wpi.first.math.geometry.Rotation2d;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.NONE)
public class Translation2d {
    private final double m_x;
    private final double m_y;

    public Translation2d() {
        this(0.0, 0.0);
    }

    @JsonCreator
    public Translation2d(@JsonProperty(required=true, value="x") double x, @JsonProperty(required=true, value="y") double y) {
        this.m_x = x;
        this.m_y = y;
    }

    public Translation2d(double distance, Rotation2d angle) {
        this.m_x = distance * angle.getCos();
        this.m_y = distance * angle.getSin();
    }

    public double getDistance(Translation2d other) {
        return Math.hypot(other.m_x - this.m_x, other.m_y - this.m_y);
    }

    @JsonProperty
    public double getX() {
        return this.m_x;
    }

    @JsonProperty
    public double getY() {
        return this.m_y;
    }

    public double getNorm() {
        return Math.hypot(this.m_x, this.m_y);
    }

    public Translation2d rotateBy(Rotation2d other) {
        return new Translation2d(this.m_x * other.getCos() - this.m_y * other.getSin(), this.m_x * other.getSin() + this.m_y * other.getCos());
    }

    public Translation2d plus(Translation2d other) {
        return new Translation2d(this.m_x + other.m_x, this.m_y + other.m_y);
    }

    public Translation2d minus(Translation2d other) {
        return new Translation2d(this.m_x - other.m_x, this.m_y - other.m_y);
    }

    public Translation2d unaryMinus() {
        return new Translation2d(-this.m_x, -this.m_y);
    }

    public Translation2d times(double scalar) {
        return new Translation2d(this.m_x * scalar, this.m_y * scalar);
    }

    public Translation2d div(double scalar) {
        return new Translation2d(this.m_x / scalar, this.m_y / scalar);
    }

    public String toString() {
        return String.format("Translation2d(X: %.2f, Y: %.2f)", this.m_x, this.m_y);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Translation2d) {
            return Math.abs(((Translation2d)obj).m_x - this.m_x) < 1.0E-9 && Math.abs(((Translation2d)obj).m_y - this.m_y) < 1.0E-9;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.m_x, this.m_y);
    }
}

