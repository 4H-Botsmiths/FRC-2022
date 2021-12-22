/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.trajectory;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Trajectory {
    private final double m_totalTimeSeconds;
    private final List<State> m_states;

    public Trajectory() {
        this.m_states = new ArrayList<State>();
        this.m_totalTimeSeconds = 0.0;
    }

    public Trajectory(List<State> states) {
        this.m_states = states;
        this.m_totalTimeSeconds = this.m_states.get((int)(this.m_states.size() - 1)).timeSeconds;
    }

    private static double lerp(double startValue, double endValue, double t) {
        return startValue + (endValue - startValue) * t;
    }

    private static Pose2d lerp(Pose2d startValue, Pose2d endValue, double t) {
        return startValue.plus(endValue.minus(startValue).times(t));
    }

    public Pose2d getInitialPose() {
        return this.sample((double)0.0).poseMeters;
    }

    public double getTotalTimeSeconds() {
        return this.m_totalTimeSeconds;
    }

    public List<State> getStates() {
        return this.m_states;
    }

    public State sample(double timeSeconds) {
        if (timeSeconds <= this.m_states.get((int)0).timeSeconds) {
            return this.m_states.get(0);
        }
        if (timeSeconds >= this.m_totalTimeSeconds) {
            return this.m_states.get(this.m_states.size() - 1);
        }
        int low = 1;
        int high = this.m_states.size() - 1;
        while (low != high) {
            int mid = (low + high) / 2;
            if (this.m_states.get((int)mid).timeSeconds < timeSeconds) {
                low = mid + 1;
                continue;
            }
            high = mid;
        }
        State sample = this.m_states.get(low);
        State prevSample = this.m_states.get(low - 1);
        if (Math.abs(sample.timeSeconds - prevSample.timeSeconds) < 1.0E-9) {
            return sample;
        }
        return prevSample.interpolate(sample, (timeSeconds - prevSample.timeSeconds) / (sample.timeSeconds - prevSample.timeSeconds));
    }

    public Trajectory transformBy(Transform2d transform) {
        State firstState = this.m_states.get(0);
        Pose2d firstPose = firstState.poseMeters;
        Pose2d newFirstPose = firstPose.plus(transform);
        ArrayList<State> newStates = new ArrayList<State>();
        newStates.add(new State(firstState.timeSeconds, firstState.velocityMetersPerSecond, firstState.accelerationMetersPerSecondSq, newFirstPose, firstState.curvatureRadPerMeter));
        for (int i = 1; i < this.m_states.size(); ++i) {
            State state = this.m_states.get(i);
            newStates.add(new State(state.timeSeconds, state.velocityMetersPerSecond, state.accelerationMetersPerSecondSq, newFirstPose.plus(state.poseMeters.minus(firstPose)), state.curvatureRadPerMeter));
        }
        return new Trajectory(newStates);
    }

    public Trajectory relativeTo(Pose2d pose) {
        return new Trajectory(this.m_states.stream().map(state -> new State(state.timeSeconds, state.velocityMetersPerSecond, state.accelerationMetersPerSecondSq, state.poseMeters.relativeTo(pose), state.curvatureRadPerMeter)).collect(Collectors.toList()));
    }

    public Trajectory concatenate(Trajectory other) {
        if (this.m_states.isEmpty()) {
            return other;
        }
        List<State> states = this.m_states.stream().map(state -> new State(state.timeSeconds, state.velocityMetersPerSecond, state.accelerationMetersPerSecondSq, state.poseMeters, state.curvatureRadPerMeter)).collect(Collectors.toList());
        for (int i = 1; i < other.getStates().size(); ++i) {
            State s = other.getStates().get(i);
            states.add(new State(s.timeSeconds + this.m_totalTimeSeconds, s.velocityMetersPerSecond, s.accelerationMetersPerSecondSq, s.poseMeters, s.curvatureRadPerMeter));
        }
        return new Trajectory(states);
    }

    public String toString() {
        String stateList = this.m_states.stream().map(State::toString).collect(Collectors.joining(", \n"));
        return String.format("Trajectory - Seconds: %.2f, States:\n%s", this.m_totalTimeSeconds, stateList);
    }

    public int hashCode() {
        return this.m_states.hashCode();
    }

    public boolean equals(Object obj) {
        return obj instanceof Trajectory && this.m_states.equals(((Trajectory)obj).getStates());
    }

    public static class State {
        @JsonProperty(value="time")
        public double timeSeconds;
        @JsonProperty(value="velocity")
        public double velocityMetersPerSecond;
        @JsonProperty(value="acceleration")
        public double accelerationMetersPerSecondSq;
        @JsonProperty(value="pose")
        public Pose2d poseMeters;
        @JsonProperty(value="curvature")
        public double curvatureRadPerMeter;

        public State() {
            this.poseMeters = new Pose2d();
        }

        public State(double timeSeconds, double velocityMetersPerSecond, double accelerationMetersPerSecondSq, Pose2d poseMeters, double curvatureRadPerMeter) {
            this.timeSeconds = timeSeconds;
            this.velocityMetersPerSecond = velocityMetersPerSecond;
            this.accelerationMetersPerSecondSq = accelerationMetersPerSecondSq;
            this.poseMeters = poseMeters;
            this.curvatureRadPerMeter = curvatureRadPerMeter;
        }

        State interpolate(State endValue, double i) {
            double newT = Trajectory.lerp(this.timeSeconds, endValue.timeSeconds, i);
            double deltaT = newT - this.timeSeconds;
            if (deltaT < 0.0) {
                return endValue.interpolate(this, 1.0 - i);
            }
            boolean reversing = this.velocityMetersPerSecond < 0.0 || Math.abs(this.velocityMetersPerSecond) < 1.0E-9 && this.accelerationMetersPerSecondSq < 0.0;
            double newV = this.velocityMetersPerSecond + this.accelerationMetersPerSecondSq * deltaT;
            double newS = (this.velocityMetersPerSecond * deltaT + 0.5 * this.accelerationMetersPerSecondSq * Math.pow(deltaT, 2.0)) * (reversing ? -1.0 : 1.0);
            double interpolationFrac = newS / endValue.poseMeters.getTranslation().getDistance(this.poseMeters.getTranslation());
            return new State(newT, newV, this.accelerationMetersPerSecondSq, Trajectory.lerp(this.poseMeters, endValue.poseMeters, interpolationFrac), Trajectory.lerp(this.curvatureRadPerMeter, endValue.curvatureRadPerMeter, interpolationFrac));
        }

        public String toString() {
            return String.format("State(Sec: %.2f, Vel m/s: %.2f, Accel m/s/s: %.2f, Pose: %s, Curvature: %.2f)", this.timeSeconds, this.velocityMetersPerSecond, this.accelerationMetersPerSecondSq, this.poseMeters, this.curvatureRadPerMeter);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof State)) {
                return false;
            }
            State state = (State)obj;
            return Double.compare(state.timeSeconds, this.timeSeconds) == 0 && Double.compare(state.velocityMetersPerSecond, this.velocityMetersPerSecond) == 0 && Double.compare(state.accelerationMetersPerSecondSq, this.accelerationMetersPerSecondSq) == 0 && Double.compare(state.curvatureRadPerMeter, this.curvatureRadPerMeter) == 0 && Objects.equals(this.poseMeters, state.poseMeters);
        }

        public int hashCode() {
            return Objects.hash(this.timeSeconds, this.velocityMetersPerSecond, this.accelerationMetersPerSecondSq, this.poseMeters, this.curvatureRadPerMeter);
        }
    }
}

