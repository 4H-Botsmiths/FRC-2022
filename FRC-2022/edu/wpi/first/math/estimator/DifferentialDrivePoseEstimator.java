/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.estimator;

import edu.wpi.first.math.MatBuilder;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.StateSpaceUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.estimator.AngleStatistics;
import edu.wpi.first.math.estimator.KalmanFilterLatencyCompensator;
import edu.wpi.first.math.estimator.UnscentedKalmanFilter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.numbers.N5;
import edu.wpi.first.util.WPIUtilJNI;
import java.util.function.BiConsumer;
import org.ejml.simple.SimpleMatrix;

public class DifferentialDrivePoseEstimator {
    final UnscentedKalmanFilter<N5, N3, N3> m_observer;
    private final BiConsumer<Matrix<N3, N1>, Matrix<N3, N1>> m_visionCorrect;
    private final KalmanFilterLatencyCompensator<N5, N3, N3> m_latencyCompensator;
    private final double m_nominalDt;
    private double m_prevTimeSeconds = -1.0;
    private Rotation2d m_gyroOffset;
    private Rotation2d m_previousAngle;
    private Matrix<N3, N3> m_visionContR;

    public DifferentialDrivePoseEstimator(Rotation2d gyroAngle, Pose2d initialPoseMeters, Matrix<N5, N1> stateStdDevs, Matrix<N3, N1> localMeasurementStdDevs, Matrix<N3, N1> visionMeasurementStdDevs) {
        this(gyroAngle, initialPoseMeters, stateStdDevs, localMeasurementStdDevs, visionMeasurementStdDevs, 0.02);
    }

    public DifferentialDrivePoseEstimator(Rotation2d gyroAngle, Pose2d initialPoseMeters, Matrix<N5, N1> stateStdDevs, Matrix<N3, N1> localMeasurementStdDevs, Matrix<N3, N1> visionMeasurementStdDevs, double nominalDtSeconds) {
        this.m_nominalDt = nominalDtSeconds;
        this.m_observer = new UnscentedKalmanFilter(Nat.N5(), Nat.N3(), (arg_0, arg_1) -> this.f(arg_0, arg_1), (x, u) -> VecBuilder.fill(x.get(3, 0), x.get(4, 0), x.get(2, 0)), stateStdDevs, localMeasurementStdDevs, AngleStatistics.angleMean(2), AngleStatistics.angleMean(2), AngleStatistics.angleResidual(2), AngleStatistics.angleResidual(2), AngleStatistics.angleAdd(2), this.m_nominalDt);
        this.m_latencyCompensator = new KalmanFilterLatencyCompensator();
        this.setVisionMeasurementStdDevs(visionMeasurementStdDevs);
        this.m_visionCorrect = (u, y) -> this.m_observer.correct(Nat.N3(), (Matrix<N3, N1>)u, y, (x, u1) -> new Matrix((SimpleMatrix)x.getStorage().extractMatrix(0, 3, 0, 1)), this.m_visionContR, AngleStatistics.angleMean(2), AngleStatistics.angleResidual(2), AngleStatistics.angleResidual(2), AngleStatistics.angleAdd(2));
        this.m_gyroOffset = initialPoseMeters.getRotation().minus(gyroAngle);
        this.m_previousAngle = initialPoseMeters.getRotation();
        this.m_observer.setXhat(DifferentialDrivePoseEstimator.fillStateVector(initialPoseMeters, 0.0, 0.0));
    }

    public void setVisionMeasurementStdDevs(Matrix<N3, N1> visionMeasurementStdDevs) {
        this.m_visionContR = StateSpaceUtil.makeCovarianceMatrix(Nat.N3(), visionMeasurementStdDevs);
    }

    private Matrix<N5, N1> f(Matrix<N5, N1> x, Matrix<N3, N1> u) {
        double theta = x.get(2, 0);
        Matrix<N5, N5> toFieldRotation = new MatBuilder<N5, N5>(Nat.N5(), Nat.N5()).fill(Math.cos(theta), -Math.sin(theta), 0.0, 0.0, 0.0, Math.sin(theta), Math.cos(theta), 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0);
        return toFieldRotation.times(VecBuilder.fill(u.get(0, 0), u.get(1, 0), u.get(2, 0), u.get(0, 0), u.get(1, 0)));
    }

    public void resetPosition(Pose2d poseMeters, Rotation2d gyroAngle) {
        this.m_observer.reset();
        this.m_latencyCompensator.reset();
        this.m_observer.setXhat(DifferentialDrivePoseEstimator.fillStateVector(poseMeters, 0.0, 0.0));
        this.m_gyroOffset = this.getEstimatedPosition().getRotation().minus(gyroAngle);
        this.m_previousAngle = poseMeters.getRotation();
    }

    public Pose2d getEstimatedPosition() {
        return new Pose2d(this.m_observer.getXhat(0), this.m_observer.getXhat(1), new Rotation2d(this.m_observer.getXhat(2)));
    }

    public void addVisionMeasurement(Pose2d visionRobotPoseMeters, double timestampSeconds) {
        this.m_latencyCompensator.applyPastGlobalMeasurement(Nat.N3(), this.m_observer, this.m_nominalDt, StateSpaceUtil.poseTo3dVector(visionRobotPoseMeters), this.m_visionCorrect, timestampSeconds);
    }

    public void addVisionMeasurement(Pose2d visionRobotPoseMeters, double timestampSeconds, Matrix<N3, N1> visionMeasurementStdDevs) {
        this.setVisionMeasurementStdDevs(visionMeasurementStdDevs);
        this.addVisionMeasurement(visionRobotPoseMeters, timestampSeconds);
    }

    public Pose2d update(Rotation2d gyroAngle, DifferentialDriveWheelSpeeds wheelVelocitiesMetersPerSecond, double distanceLeftMeters, double distanceRightMeters) {
        return this.updateWithTime((double)WPIUtilJNI.now() * 1.0E-6, gyroAngle, wheelVelocitiesMetersPerSecond, distanceLeftMeters, distanceRightMeters);
    }

    public Pose2d updateWithTime(double currentTimeSeconds, Rotation2d gyroAngle, DifferentialDriveWheelSpeeds wheelVelocitiesMetersPerSecond, double distanceLeftMeters, double distanceRightMeters) {
        double dt = this.m_prevTimeSeconds >= 0.0 ? currentTimeSeconds - this.m_prevTimeSeconds : this.m_nominalDt;
        this.m_prevTimeSeconds = currentTimeSeconds;
        Rotation2d angle = gyroAngle.plus(this.m_gyroOffset);
        DifferentialDriveWheelSpeeds wheelVels = wheelVelocitiesMetersPerSecond;
        Vector<N3> u = VecBuilder.fill((wheelVels.leftMetersPerSecond + wheelVels.rightMetersPerSecond) / 2.0, 0.0, angle.minus(this.m_previousAngle).getRadians() / dt);
        this.m_previousAngle = angle;
        Vector<N3> localY = VecBuilder.fill(distanceLeftMeters, distanceRightMeters, angle.getRadians());
        this.m_latencyCompensator.addObserverState(this.m_observer, u, localY, currentTimeSeconds);
        this.m_observer.predict(u, dt);
        this.m_observer.correct(u, localY);
        return this.getEstimatedPosition();
    }

    private static Matrix<N5, N1> fillStateVector(Pose2d pose, double leftDist, double rightDist) {
        return VecBuilder.fill(pose.getTranslation().getX(), pose.getTranslation().getY(), pose.getRotation().getRadians(), leftDist, rightDist);
    }
}

