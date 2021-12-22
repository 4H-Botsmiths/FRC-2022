/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.trajectory.constraint;

import edu.wpi.first.math.geometry.Pose2d;

public interface TrajectoryConstraint {
    public double getMaxVelocityMetersPerSecond(Pose2d var1, double var2, double var4);

    public MinMax getMinMaxAccelerationMetersPerSecondSq(Pose2d var1, double var2, double var4);

    public static class MinMax {
        public double minAccelerationMetersPerSecondSq = -1.7976931348623157E308;
        public double maxAccelerationMetersPerSecondSq = Double.MAX_VALUE;

        public MinMax(double minAccelerationMetersPerSecondSq, double maxAccelerationMetersPerSecondSq) {
            this.minAccelerationMetersPerSecondSq = minAccelerationMetersPerSecondSq;
            this.maxAccelerationMetersPerSecondSq = maxAccelerationMetersPerSecondSq;
        }

        public MinMax() {
        }
    }
}

