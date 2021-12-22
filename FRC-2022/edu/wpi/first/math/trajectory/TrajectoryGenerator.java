/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.trajectory;

import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.spline.PoseWithCurvature;
import edu.wpi.first.math.spline.Spline;
import edu.wpi.first.math.spline.SplineHelper;
import edu.wpi.first.math.spline.SplineParameterizer;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryParameterizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

public final class TrajectoryGenerator {
    private static final Trajectory kDoNothingTrajectory = new Trajectory(Arrays.asList(new Trajectory.State()));
    private static BiConsumer<String, StackTraceElement[]> errorFunc;

    private TrajectoryGenerator() {
    }

    private static void reportError(String error, StackTraceElement[] stackTrace) {
        if (errorFunc != null) {
            errorFunc.accept(error, stackTrace);
        } else {
            MathSharedStore.reportError(error, stackTrace);
        }
    }

    public static void setErrorHandler(BiConsumer<String, StackTraceElement[]> func) {
        errorFunc = func;
    }

    public static Trajectory generateTrajectory(Spline.ControlVector initial, List<Translation2d> interiorWaypoints, Spline.ControlVector end, TrajectoryConfig config) {
        List<PoseWithCurvature> points;
        Transform2d flip = new Transform2d(new Translation2d(), Rotation2d.fromDegrees(180.0));
        Spline.ControlVector newInitial = new Spline.ControlVector(initial.x, initial.y);
        Spline.ControlVector newEnd = new Spline.ControlVector(end.x, end.y);
        if (config.isReversed()) {
            newInitial.x[1] = newInitial.x[1] * -1.0;
            newInitial.y[1] = newInitial.y[1] * -1.0;
            newEnd.x[1] = newEnd.x[1] * -1.0;
            newEnd.y[1] = newEnd.y[1] * -1.0;
        }
        try {
            points = TrajectoryGenerator.splinePointsFromSplines(SplineHelper.getCubicSplinesFromControlVectors(newInitial, interiorWaypoints.toArray(new Translation2d[0]), newEnd));
        }
        catch (SplineParameterizer.MalformedSplineException ex) {
            TrajectoryGenerator.reportError(ex.getMessage(), ex.getStackTrace());
            return kDoNothingTrajectory;
        }
        if (config.isReversed()) {
            for (PoseWithCurvature point : points) {
                point.poseMeters = point.poseMeters.plus(flip);
                point.curvatureRadPerMeter *= -1.0;
            }
        }
        return TrajectoryParameterizer.timeParameterizeTrajectory(points, config.getConstraints(), config.getStartVelocity(), config.getEndVelocity(), config.getMaxVelocity(), config.getMaxAcceleration(), config.isReversed());
    }

    public static Trajectory generateTrajectory(Pose2d start, List<Translation2d> interiorWaypoints, Pose2d end, TrajectoryConfig config) {
        Spline.ControlVector[] controlVectors = SplineHelper.getCubicControlVectorsFromWaypoints(start, interiorWaypoints.toArray(new Translation2d[0]), end);
        return TrajectoryGenerator.generateTrajectory(controlVectors[0], interiorWaypoints, controlVectors[1], config);
    }

    public static Trajectory generateTrajectory(ControlVectorList controlVectors, TrajectoryConfig config) {
        List<PoseWithCurvature> points;
        Transform2d flip = new Transform2d(new Translation2d(), Rotation2d.fromDegrees(180.0));
        ArrayList<Spline.ControlVector> newControlVectors = new ArrayList<Spline.ControlVector>(controlVectors.size());
        for (Spline.ControlVector vector : controlVectors) {
            Spline.ControlVector newVector = new Spline.ControlVector(vector.x, vector.y);
            if (config.isReversed()) {
                newVector.x[1] = newVector.x[1] * -1.0;
                newVector.y[1] = newVector.y[1] * -1.0;
            }
            newControlVectors.add(newVector);
        }
        try {
            points = TrajectoryGenerator.splinePointsFromSplines(SplineHelper.getQuinticSplinesFromControlVectors(newControlVectors.toArray(new Spline.ControlVector[0])));
        }
        catch (SplineParameterizer.MalformedSplineException ex) {
            TrajectoryGenerator.reportError(ex.getMessage(), ex.getStackTrace());
            return kDoNothingTrajectory;
        }
        if (config.isReversed()) {
            for (PoseWithCurvature point : points) {
                point.poseMeters = point.poseMeters.plus(flip);
                point.curvatureRadPerMeter *= -1.0;
            }
        }
        return TrajectoryParameterizer.timeParameterizeTrajectory(points, config.getConstraints(), config.getStartVelocity(), config.getEndVelocity(), config.getMaxVelocity(), config.getMaxAcceleration(), config.isReversed());
    }

    public static Trajectory generateTrajectory(List<Pose2d> waypoints, TrajectoryConfig config) {
        List<PoseWithCurvature> points;
        Transform2d flip = new Transform2d(new Translation2d(), Rotation2d.fromDegrees(180.0));
        ArrayList<Pose2d> newWaypoints = new ArrayList<Pose2d>();
        if (config.isReversed()) {
            for (Pose2d originalWaypoint : waypoints) {
                newWaypoints.add(originalWaypoint.plus(flip));
            }
        } else {
            newWaypoints.addAll(waypoints);
        }
        try {
            points = TrajectoryGenerator.splinePointsFromSplines(SplineHelper.getQuinticSplinesFromWaypoints(newWaypoints));
        }
        catch (SplineParameterizer.MalformedSplineException ex) {
            TrajectoryGenerator.reportError(ex.getMessage(), ex.getStackTrace());
            return kDoNothingTrajectory;
        }
        if (config.isReversed()) {
            for (PoseWithCurvature point : points) {
                point.poseMeters = point.poseMeters.plus(flip);
                point.curvatureRadPerMeter *= -1.0;
            }
        }
        return TrajectoryParameterizer.timeParameterizeTrajectory(points, config.getConstraints(), config.getStartVelocity(), config.getEndVelocity(), config.getMaxVelocity(), config.getMaxAcceleration(), config.isReversed());
    }

    public static List<PoseWithCurvature> splinePointsFromSplines(Spline[] splines) {
        ArrayList<PoseWithCurvature> splinePoints = new ArrayList<PoseWithCurvature>();
        splinePoints.add(splines[0].getPoint(0.0));
        for (Spline spline : splines) {
            List<PoseWithCurvature> points = SplineParameterizer.parameterize(spline);
            splinePoints.addAll(points.subList(1, points.size()));
        }
        return splinePoints;
    }

    public static class ControlVectorList
    extends ArrayList<Spline.ControlVector> {
        public ControlVectorList(int initialCapacity) {
            super(initialCapacity);
        }

        public ControlVectorList() {
        }

        public ControlVectorList(Collection<? extends Spline.ControlVector> collection) {
            super(collection);
        }
    }
}

