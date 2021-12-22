/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.trajectory;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveKinematicsConstraint;
import edu.wpi.first.math.trajectory.constraint.MecanumDriveKinematicsConstraint;
import edu.wpi.first.math.trajectory.constraint.SwerveDriveKinematicsConstraint;
import edu.wpi.first.math.trajectory.constraint.TrajectoryConstraint;
import java.util.ArrayList;
import java.util.List;

public class TrajectoryConfig {
    private final double m_maxVelocity;
    private final double m_maxAcceleration;
    private final List<TrajectoryConstraint> m_constraints;
    private double m_startVelocity;
    private double m_endVelocity;
    private boolean m_reversed;

    public TrajectoryConfig(double maxVelocityMetersPerSecond, double maxAccelerationMetersPerSecondSq) {
        this.m_maxVelocity = maxVelocityMetersPerSecond;
        this.m_maxAcceleration = maxAccelerationMetersPerSecondSq;
        this.m_constraints = new ArrayList<TrajectoryConstraint>();
    }

    public TrajectoryConfig addConstraint(TrajectoryConstraint constraint) {
        this.m_constraints.add(constraint);
        return this;
    }

    public TrajectoryConfig addConstraints(List<? extends TrajectoryConstraint> constraints) {
        this.m_constraints.addAll(constraints);
        return this;
    }

    public TrajectoryConfig setKinematics(DifferentialDriveKinematics kinematics) {
        this.addConstraint(new DifferentialDriveKinematicsConstraint(kinematics, this.m_maxVelocity));
        return this;
    }

    public TrajectoryConfig setKinematics(MecanumDriveKinematics kinematics) {
        this.addConstraint(new MecanumDriveKinematicsConstraint(kinematics, this.m_maxVelocity));
        return this;
    }

    public TrajectoryConfig setKinematics(SwerveDriveKinematics kinematics) {
        this.addConstraint(new SwerveDriveKinematicsConstraint(kinematics, this.m_maxVelocity));
        return this;
    }

    public double getStartVelocity() {
        return this.m_startVelocity;
    }

    public TrajectoryConfig setStartVelocity(double startVelocityMetersPerSecond) {
        this.m_startVelocity = startVelocityMetersPerSecond;
        return this;
    }

    public double getEndVelocity() {
        return this.m_endVelocity;
    }

    public TrajectoryConfig setEndVelocity(double endVelocityMetersPerSecond) {
        this.m_endVelocity = endVelocityMetersPerSecond;
        return this;
    }

    public double getMaxVelocity() {
        return this.m_maxVelocity;
    }

    public double getMaxAcceleration() {
        return this.m_maxAcceleration;
    }

    public List<TrajectoryConstraint> getConstraints() {
        return this.m_constraints;
    }

    public boolean isReversed() {
        return this.m_reversed;
    }

    public TrajectoryConfig setReversed(boolean reversed) {
        this.m_reversed = reversed;
        return this;
    }
}

