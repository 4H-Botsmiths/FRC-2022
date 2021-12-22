/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.geometry;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import java.util.Objects;

public class Transform2d {
    private final Translation2d m_translation;
    private final Rotation2d m_rotation;

    public Transform2d(Pose2d initial, Pose2d last) {
        this.m_translation = last.getTranslation().minus(initial.getTranslation()).rotateBy(initial.getRotation().unaryMinus());
        this.m_rotation = last.getRotation().minus(initial.getRotation());
    }

    public Transform2d(Translation2d translation, Rotation2d rotation) {
        this.m_translation = translation;
        this.m_rotation = rotation;
    }

    public Transform2d() {
        this.m_translation = new Translation2d();
        this.m_rotation = new Rotation2d();
    }

    public Transform2d times(double scalar) {
        return new Transform2d(this.m_translation.times(scalar), this.m_rotation.times(scalar));
    }

    public Transform2d plus(Transform2d other) {
        return new Transform2d(new Pose2d(), new Pose2d().transformBy(this).transformBy(other));
    }

    public Translation2d getTranslation() {
        return this.m_translation;
    }

    public double getX() {
        return this.m_translation.getX();
    }

    public double getY() {
        return this.m_translation.getY();
    }

    public Rotation2d getRotation() {
        return this.m_rotation;
    }

    public Transform2d inverse() {
        return new Transform2d(this.getTranslation().unaryMinus().rotateBy(this.getRotation().unaryMinus()), this.getRotation().unaryMinus());
    }

    public String toString() {
        return String.format("Transform2d(%s, %s)", this.m_translation, this.m_rotation);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Transform2d) {
            return ((Transform2d)obj).m_translation.equals(this.m_translation) && ((Transform2d)obj).m_rotation.equals(this.m_rotation);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.m_translation, this.m_rotation);
    }
}

