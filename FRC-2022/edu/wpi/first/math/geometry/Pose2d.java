/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.geometry;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Twist2d;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.NONE)
public class Pose2d {
    private final Translation2d m_translation;
    private final Rotation2d m_rotation;

    public Pose2d() {
        this.m_translation = new Translation2d();
        this.m_rotation = new Rotation2d();
    }

    @JsonCreator
    public Pose2d(@JsonProperty(required=true, value="translation") Translation2d translation, @JsonProperty(required=true, value="rotation") Rotation2d rotation) {
        this.m_translation = translation;
        this.m_rotation = rotation;
    }

    public Pose2d(double x, double y, Rotation2d rotation) {
        this.m_translation = new Translation2d(x, y);
        this.m_rotation = rotation;
    }

    public Pose2d plus(Transform2d other) {
        return this.transformBy(other);
    }

    public Transform2d minus(Pose2d other) {
        Pose2d pose = this.relativeTo(other);
        return new Transform2d(pose.getTranslation(), pose.getRotation());
    }

    @JsonProperty
    public Translation2d getTranslation() {
        return this.m_translation;
    }

    public double getX() {
        return this.m_translation.getX();
    }

    public double getY() {
        return this.m_translation.getY();
    }

    @JsonProperty
    public Rotation2d getRotation() {
        return this.m_rotation;
    }

    public Pose2d transformBy(Transform2d other) {
        return new Pose2d(this.m_translation.plus(other.getTranslation().rotateBy(this.m_rotation)), this.m_rotation.plus(other.getRotation()));
    }

    public Pose2d relativeTo(Pose2d other) {
        Transform2d transform = new Transform2d(other, this);
        return new Pose2d(transform.getTranslation(), transform.getRotation());
    }

    public Pose2d exp(Twist2d twist) {
        double c;
        double s;
        double dx = twist.dx;
        double dy = twist.dy;
        double dtheta = twist.dtheta;
        double sinTheta = Math.sin(dtheta);
        double cosTheta = Math.cos(dtheta);
        if (Math.abs(dtheta) < 1.0E-9) {
            s = 1.0 - 0.16666666666666666 * dtheta * dtheta;
            c = 0.5 * dtheta;
        } else {
            s = sinTheta / dtheta;
            c = (1.0 - cosTheta) / dtheta;
        }
        Transform2d transform = new Transform2d(new Translation2d(dx * s - dy * c, dx * c + dy * s), new Rotation2d(cosTheta, sinTheta));
        return this.plus(transform);
    }

    public Twist2d log(Pose2d end) {
        Pose2d transform = end.relativeTo(this);
        double dtheta = transform.getRotation().getRadians();
        double halfDtheta = dtheta / 2.0;
        double cosMinusOne = transform.getRotation().getCos() - 1.0;
        double halfThetaByTanOfHalfDtheta = Math.abs(cosMinusOne) < 1.0E-9 ? 1.0 - 0.08333333333333333 * dtheta * dtheta : -(halfDtheta * transform.getRotation().getSin()) / cosMinusOne;
        Translation2d translationPart = transform.getTranslation().rotateBy(new Rotation2d(halfThetaByTanOfHalfDtheta, -halfDtheta)).times(Math.hypot(halfThetaByTanOfHalfDtheta, halfDtheta));
        return new Twist2d(translationPart.getX(), translationPart.getY(), dtheta);
    }

    public String toString() {
        return String.format("Pose2d(%s, %s)", this.m_translation, this.m_rotation);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Pose2d) {
            return ((Pose2d)obj).m_translation.equals(this.m_translation) && ((Pose2d)obj).m_rotation.equals(this.m_rotation);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.m_translation, this.m_rotation);
    }
}

