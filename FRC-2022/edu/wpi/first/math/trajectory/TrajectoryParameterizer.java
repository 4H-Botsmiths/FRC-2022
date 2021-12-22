/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.trajectory;

import edu.wpi.first.math.spline.PoseWithCurvature;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.constraint.TrajectoryConstraint;
import java.util.ArrayList;
import java.util.List;

public final class TrajectoryParameterizer {
    private TrajectoryParameterizer() {
    }

    public static Trajectory timeParameterizeTrajectory(List<PoseWithCurvature> points, List<TrajectoryConstraint> constraints, double startVelocityMetersPerSecond, double endVelocityMetersPerSecond, double maxVelocityMetersPerSecond, double maxAccelerationMetersPerSecondSq, boolean reversed) {
        ArrayList<ConstrainedState> constrainedStates = new ArrayList<ConstrainedState>(points.size());
        ConstrainedState predecessor = new ConstrainedState(points.get(0), 0.0, startVelocityMetersPerSecond, -maxAccelerationMetersPerSecondSq, maxAccelerationMetersPerSecondSq);
        for (int i = 0; i < points.size(); ++i) {
            ConstrainedState constrainedState;
            block13: {
                double actualAcceleration;
                constrainedStates.add(new ConstrainedState());
                constrainedState = (ConstrainedState)constrainedStates.get(i);
                constrainedState.pose = points.get(i);
                double ds = constrainedState.pose.poseMeters.getTranslation().getDistance(predecessor.pose.poseMeters.getTranslation());
                constrainedState.distanceMeters = predecessor.distanceMeters + ds;
                while (true) {
                    constrainedState.maxVelocityMetersPerSecond = Math.min(maxVelocityMetersPerSecond, Math.sqrt(predecessor.maxVelocityMetersPerSecond * predecessor.maxVelocityMetersPerSecond + predecessor.maxAccelerationMetersPerSecondSq * ds * 2.0));
                    constrainedState.minAccelerationMetersPerSecondSq = -maxAccelerationMetersPerSecondSq;
                    constrainedState.maxAccelerationMetersPerSecondSq = maxAccelerationMetersPerSecondSq;
                    for (TrajectoryConstraint constraint : constraints) {
                        constrainedState.maxVelocityMetersPerSecond = Math.min(constrainedState.maxVelocityMetersPerSecond, constraint.getMaxVelocityMetersPerSecond(constrainedState.pose.poseMeters, constrainedState.pose.curvatureRadPerMeter, constrainedState.maxVelocityMetersPerSecond));
                    }
                    TrajectoryParameterizer.enforceAccelerationLimits(reversed, constraints, constrainedState);
                    if (ds < 1.0E-6) break block13;
                    actualAcceleration = (constrainedState.maxVelocityMetersPerSecond * constrainedState.maxVelocityMetersPerSecond - predecessor.maxVelocityMetersPerSecond * predecessor.maxVelocityMetersPerSecond) / (ds * 2.0);
                    if (!(constrainedState.maxAccelerationMetersPerSecondSq < actualAcceleration - 1.0E-6)) break;
                    predecessor.maxAccelerationMetersPerSecondSq = constrainedState.maxAccelerationMetersPerSecondSq;
                }
                if (actualAcceleration > predecessor.minAccelerationMetersPerSecondSq) {
                    predecessor.maxAccelerationMetersPerSecondSq = actualAcceleration;
                }
            }
            predecessor = constrainedState;
        }
        ConstrainedState successor = new ConstrainedState(points.get(points.size() - 1), ((ConstrainedState)constrainedStates.get((int)(constrainedStates.size() - 1))).distanceMeters, endVelocityMetersPerSecond, -maxAccelerationMetersPerSecondSq, maxAccelerationMetersPerSecondSq);
        for (int i = points.size() - 1; i >= 0; --i) {
            double newMaxVelocity;
            ConstrainedState constrainedState = (ConstrainedState)constrainedStates.get(i);
            double ds = constrainedState.distanceMeters - successor.distanceMeters;
            while (!((newMaxVelocity = Math.sqrt(successor.maxVelocityMetersPerSecond * successor.maxVelocityMetersPerSecond + successor.minAccelerationMetersPerSecondSq * ds * 2.0)) >= constrainedState.maxVelocityMetersPerSecond)) {
                constrainedState.maxVelocityMetersPerSecond = newMaxVelocity;
                TrajectoryParameterizer.enforceAccelerationLimits(reversed, constraints, constrainedState);
                if (ds > -1.0E-6) break;
                double actualAcceleration = (constrainedState.maxVelocityMetersPerSecond * constrainedState.maxVelocityMetersPerSecond - successor.maxVelocityMetersPerSecond * successor.maxVelocityMetersPerSecond) / (ds * 2.0);
                if (constrainedState.minAccelerationMetersPerSecondSq > actualAcceleration + 1.0E-6) {
                    successor.minAccelerationMetersPerSecondSq = constrainedState.minAccelerationMetersPerSecondSq;
                    continue;
                }
                successor.minAccelerationMetersPerSecondSq = actualAcceleration;
                break;
            }
            successor = constrainedState;
        }
        ArrayList<Trajectory.State> states = new ArrayList<Trajectory.State>(points.size());
        double timeSeconds = 0.0;
        double distanceMeters = 0.0;
        double velocityMetersPerSecond = 0.0;
        for (int i = 0; i < constrainedStates.size(); ++i) {
            ConstrainedState state = (ConstrainedState)constrainedStates.get(i);
            double ds = state.distanceMeters - distanceMeters;
            double accel = (state.maxVelocityMetersPerSecond * state.maxVelocityMetersPerSecond - velocityMetersPerSecond * velocityMetersPerSecond) / (ds * 2.0);
            double dt = 0.0;
            if (i > 0) {
                double d = states.get((int)(i - 1)).accelerationMetersPerSecondSq = reversed ? -accel : accel;
                if (Math.abs(accel) > 1.0E-6) {
                    dt = (state.maxVelocityMetersPerSecond - velocityMetersPerSecond) / accel;
                } else if (Math.abs(velocityMetersPerSecond) > 1.0E-6) {
                    dt = ds / velocityMetersPerSecond;
                } else {
                    throw new TrajectoryGenerationException("Something went wrong at iteration " + i + " of time parameterization.");
                }
            }
            velocityMetersPerSecond = state.maxVelocityMetersPerSecond;
            distanceMeters = state.distanceMeters;
            states.add(new Trajectory.State(timeSeconds += dt, reversed ? -velocityMetersPerSecond : velocityMetersPerSecond, reversed ? -accel : accel, state.pose.poseMeters, state.pose.curvatureRadPerMeter));
        }
        return new Trajectory(states);
    }

    private static void enforceAccelerationLimits(boolean reverse, List<TrajectoryConstraint> constraints, ConstrainedState state) {
        for (TrajectoryConstraint constraint : constraints) {
            double factor = reverse ? -1.0 : 1.0;
            TrajectoryConstraint.MinMax minMaxAccel = constraint.getMinMaxAccelerationMetersPerSecondSq(state.pose.poseMeters, state.pose.curvatureRadPerMeter, state.maxVelocityMetersPerSecond * factor);
            if (minMaxAccel.minAccelerationMetersPerSecondSq > minMaxAccel.maxAccelerationMetersPerSecondSq) {
                throw new TrajectoryGenerationException("The constraint's min acceleration was greater than its max acceleration.\n Offending Constraint: " + constraint.getClass().getName() + "\n If the offending constraint was packaged with WPILib, please file a bug report.");
            }
            state.minAccelerationMetersPerSecondSq = Math.max(state.minAccelerationMetersPerSecondSq, reverse ? -minMaxAccel.maxAccelerationMetersPerSecondSq : minMaxAccel.minAccelerationMetersPerSecondSq);
            state.maxAccelerationMetersPerSecondSq = Math.min(state.maxAccelerationMetersPerSecondSq, reverse ? -minMaxAccel.minAccelerationMetersPerSecondSq : minMaxAccel.maxAccelerationMetersPerSecondSq);
        }
    }

    public static class TrajectoryGenerationException
    extends RuntimeException {
        public TrajectoryGenerationException(String message) {
            super(message);
        }
    }

    private static class ConstrainedState {
        PoseWithCurvature pose;
        double distanceMeters;
        double maxVelocityMetersPerSecond;
        double minAccelerationMetersPerSecondSq;
        double maxAccelerationMetersPerSecondSq;

        ConstrainedState(PoseWithCurvature pose, double distanceMeters, double maxVelocityMetersPerSecond, double minAccelerationMetersPerSecondSq, double maxAccelerationMetersPerSecondSq) {
            this.pose = pose;
            this.distanceMeters = distanceMeters;
            this.maxVelocityMetersPerSecond = maxVelocityMetersPerSecond;
            this.minAccelerationMetersPerSecondSq = minAccelerationMetersPerSecondSq;
            this.maxAccelerationMetersPerSecondSq = maxAccelerationMetersPerSecondSq;
        }

        ConstrainedState() {
            this.pose = new PoseWithCurvature();
        }
    }
}

