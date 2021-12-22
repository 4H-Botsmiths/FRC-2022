/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.trajectory;

import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.MathUsageId;
import java.util.Objects;

public class TrapezoidProfile {
    private int m_direction;
    private Constraints m_constraints;
    private State m_initial;
    private State m_goal;
    private double m_endAccel;
    private double m_endFullSpeed;
    private double m_endDeccel;

    public TrapezoidProfile(Constraints constraints, State goal, State initial) {
        double accelerationTime;
        double cutoffEnd;
        double cutoffDistEnd;
        double cutoffBegin;
        double cutoffDistBegin;
        double fullTrapezoidDist;
        double fullSpeedDist;
        this.m_direction = TrapezoidProfile.shouldFlipAcceleration(initial, goal) ? -1 : 1;
        this.m_constraints = constraints;
        this.m_initial = this.direct(initial);
        this.m_goal = this.direct(goal);
        if (this.m_initial.velocity > this.m_constraints.maxVelocity) {
            this.m_initial.velocity = this.m_constraints.maxVelocity;
        }
        if ((fullSpeedDist = (fullTrapezoidDist = (cutoffDistBegin = (cutoffBegin = this.m_initial.velocity / this.m_constraints.maxAcceleration) * cutoffBegin * this.m_constraints.maxAcceleration / 2.0) + (this.m_goal.position - this.m_initial.position) + (cutoffDistEnd = (cutoffEnd = this.m_goal.velocity / this.m_constraints.maxAcceleration) * cutoffEnd * this.m_constraints.maxAcceleration / 2.0)) - (accelerationTime = this.m_constraints.maxVelocity / this.m_constraints.maxAcceleration) * accelerationTime * this.m_constraints.maxAcceleration) < 0.0) {
            accelerationTime = Math.sqrt(fullTrapezoidDist / this.m_constraints.maxAcceleration);
            fullSpeedDist = 0.0;
        }
        this.m_endAccel = accelerationTime - cutoffBegin;
        this.m_endFullSpeed = this.m_endAccel + fullSpeedDist / this.m_constraints.maxVelocity;
        this.m_endDeccel = this.m_endFullSpeed + accelerationTime - cutoffEnd;
    }

    public TrapezoidProfile(Constraints constraints, State goal) {
        this(constraints, goal, new State(0.0, 0.0));
    }

    public State calculate(double t) {
        State result = new State(this.m_initial.position, this.m_initial.velocity);
        if (t < this.m_endAccel) {
            result.velocity += t * this.m_constraints.maxAcceleration;
            result.position += (this.m_initial.velocity + t * this.m_constraints.maxAcceleration / 2.0) * t;
        } else if (t < this.m_endFullSpeed) {
            result.velocity = this.m_constraints.maxVelocity;
            result.position += (this.m_initial.velocity + this.m_endAccel * this.m_constraints.maxAcceleration / 2.0) * this.m_endAccel + this.m_constraints.maxVelocity * (t - this.m_endAccel);
        } else if (t <= this.m_endDeccel) {
            result.velocity = this.m_goal.velocity + (this.m_endDeccel - t) * this.m_constraints.maxAcceleration;
            double timeLeft = this.m_endDeccel - t;
            result.position = this.m_goal.position - (this.m_goal.velocity + timeLeft * this.m_constraints.maxAcceleration / 2.0) * timeLeft;
        } else {
            result = this.m_goal;
        }
        return this.direct(result);
    }

    public double timeLeftUntil(double target) {
        double deccelDist;
        double position = this.m_initial.position * (double)this.m_direction;
        double velocity = this.m_initial.velocity * (double)this.m_direction;
        double endAccel = this.m_endAccel * (double)this.m_direction;
        double endFullSpeed = this.m_endFullSpeed * (double)this.m_direction - endAccel;
        if (target < position) {
            endAccel = -endAccel;
            endFullSpeed = -endFullSpeed;
            velocity = -velocity;
        }
        endAccel = Math.max(endAccel, 0.0);
        endFullSpeed = Math.max(endFullSpeed, 0.0);
        double acceleration = this.m_constraints.maxAcceleration;
        double decceleration = -this.m_constraints.maxAcceleration;
        double distToTarget = Math.abs(target - position);
        if (distToTarget < 1.0E-6) {
            return 0.0;
        }
        double accelDist = velocity * endAccel + 0.5 * acceleration * endAccel * endAccel;
        double deccelVelocity = endAccel > 0.0 ? Math.sqrt(Math.abs(velocity * velocity + 2.0 * acceleration * accelDist)) : velocity;
        double fullSpeedDist = this.m_constraints.maxVelocity * endFullSpeed;
        if (accelDist > distToTarget) {
            accelDist = distToTarget;
            fullSpeedDist = 0.0;
            deccelDist = 0.0;
        } else if (accelDist + fullSpeedDist > distToTarget) {
            fullSpeedDist = distToTarget - accelDist;
            deccelDist = 0.0;
        } else {
            deccelDist = distToTarget - fullSpeedDist - accelDist;
        }
        double accelTime = (-velocity + Math.sqrt(Math.abs(velocity * velocity + 2.0 * acceleration * accelDist))) / acceleration;
        double deccelTime = (-deccelVelocity + Math.sqrt(Math.abs(deccelVelocity * deccelVelocity + 2.0 * decceleration * deccelDist))) / decceleration;
        double fullSpeedTime = fullSpeedDist / this.m_constraints.maxVelocity;
        return accelTime + fullSpeedTime + deccelTime;
    }

    public double totalTime() {
        return this.m_endDeccel;
    }

    public boolean isFinished(double t) {
        return t >= this.totalTime();
    }

    private static boolean shouldFlipAcceleration(State initial, State goal) {
        return initial.position > goal.position;
    }

    private State direct(State in) {
        State result = new State(in.position, in.velocity);
        result.position *= (double)this.m_direction;
        result.velocity *= (double)this.m_direction;
        return result;
    }

    public static class State {
        public double position;
        public double velocity;

        public State() {
        }

        public State(double position, double velocity) {
            this.position = position;
            this.velocity = velocity;
        }

        public boolean equals(Object other) {
            if (other instanceof State) {
                State rhs = (State)other;
                return this.position == rhs.position && this.velocity == rhs.velocity;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.position, this.velocity);
        }
    }

    public static class Constraints {
        public final double maxVelocity;
        public final double maxAcceleration;

        public Constraints(double maxVelocity, double maxAcceleration) {
            this.maxVelocity = maxVelocity;
            this.maxAcceleration = maxAcceleration;
            MathSharedStore.reportUsage(MathUsageId.kTrajectory_TrapezoidProfile, 1);
        }
    }
}

