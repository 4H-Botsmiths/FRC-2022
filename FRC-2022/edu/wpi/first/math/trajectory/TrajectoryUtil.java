/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.trajectory;

import edu.wpi.first.math.WPIMathJNI;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public final class TrajectoryUtil {
    private TrajectoryUtil() {
        throw new UnsupportedOperationException("This is a utility class!");
    }

    private static Trajectory createTrajectoryFromElements(double[] elements) {
        if (elements.length % 7 != 0) {
            throw new TrajectorySerializationException("An error occurred when converting trajectory elements into a trajectory.");
        }
        ArrayList<Trajectory.State> states = new ArrayList<Trajectory.State>();
        for (int i = 0; i < elements.length; i += 7) {
            states.add(new Trajectory.State(elements[i], elements[i + 1], elements[i + 2], new Pose2d(elements[i + 3], elements[i + 4], new Rotation2d(elements[i + 5])), elements[i + 6]));
        }
        return new Trajectory(states);
    }

    private static double[] getElementsFromTrajectory(Trajectory trajectory) {
        double[] elements = new double[trajectory.getStates().size() * 7];
        for (int i = 0; i < trajectory.getStates().size() * 7; i += 7) {
            Trajectory.State state = trajectory.getStates().get(i / 7);
            elements[i] = state.timeSeconds;
            elements[i + 1] = state.velocityMetersPerSecond;
            elements[i + 2] = state.accelerationMetersPerSecondSq;
            elements[i + 3] = state.poseMeters.getX();
            elements[i + 4] = state.poseMeters.getY();
            elements[i + 5] = state.poseMeters.getRotation().getRadians();
            elements[i + 6] = state.curvatureRadPerMeter;
        }
        return elements;
    }

    public static Trajectory fromPathweaverJson(Path path) throws IOException {
        return TrajectoryUtil.createTrajectoryFromElements(WPIMathJNI.fromPathweaverJson(path.toString()));
    }

    public static void toPathweaverJson(Trajectory trajectory, Path path) throws IOException {
        WPIMathJNI.toPathweaverJson(TrajectoryUtil.getElementsFromTrajectory(trajectory), path.toString());
    }

    public static Trajectory deserializeTrajectory(String json) {
        return TrajectoryUtil.createTrajectoryFromElements(WPIMathJNI.deserializeTrajectory(json));
    }

    public static String serializeTrajectory(Trajectory trajectory) {
        return WPIMathJNI.serializeTrajectory(TrajectoryUtil.getElementsFromTrajectory(trajectory));
    }

    public static class TrajectorySerializationException
    extends RuntimeException {
        public TrajectorySerializationException(String message) {
            super(message);
        }
    }
}

