/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.spline;

import edu.wpi.first.math.geometry.Pose2d;

public class PoseWithCurvature {
    public Pose2d poseMeters;
    public double curvatureRadPerMeter;

    public PoseWithCurvature(Pose2d poseMeters, double curvatureRadPerMeter) {
        this.poseMeters = poseMeters;
        this.curvatureRadPerMeter = curvatureRadPerMeter;
    }

    public PoseWithCurvature() {
        this.poseMeters = new Pose2d();
    }
}

