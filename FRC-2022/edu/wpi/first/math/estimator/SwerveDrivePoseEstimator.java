/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.estimator;

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
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.util.WPIUtilJNI;
import java.util.function.BiConsumer;

public class SwerveDrivePoseEstimator {
    private final UnscentedKalmanFilter<N3, N3, N1> m_observer;
    private final SwerveDriveKinematics m_kinematics;
    private final BiConsumer<Matrix<N3, N1>, Matrix<N3, N1>> m_visionCorrect;
    private final KalmanFilterLatencyCompensator<N3, N3, N1> m_latencyCompensator;
    private final double m_nominalDt;
    private double m_prevTimeSeconds = -1.0;
    private Rotation2d m_gyroOffset;
    private Rotation2d m_previousAngle;
    private Matrix<N3, N3> m_visionContR;

    public SwerveDrivePoseEstimator(Rotation2d gyroAngle, Pose2d initialPoseMeters, SwerveDriveKinematics kinematics, Matrix<N3, N1> stateStdDevs, Matrix<N1, N1> localMeasurementStdDevs, Matrix<N3, N1> visionMeasurementStdDevs) {
        this(gyroAngle, initialPoseMeters, kinematics, stateStdDevs, localMeasurementStdDevs, visionMeasurementStdDevs, 0.02);
    }

    public SwerveDrivePoseEstimator(Rotation2d gyroAngle, Pose2d initialPoseMeters, SwerveDriveKinematics kinematics, Matrix<N3, N1> stateStdDevs, Matrix<N1, N1> localMeasurementStdDevs, Matrix<N3, N1> visionMeasurementStdDevs, double nominalDtSeconds) {
        this.m_nominalDt = nominalDtSeconds;
        this.m_observer = new UnscentedKalmanFilter(Nat.N3(), Nat.N1(), (x, u) -> u, (x, u) -> x.extractRowVector(2), stateStdDevs, localMeasurementStdDevs, AngleStatistics.angleMean(2), AngleStatistics.angleMean(0), AngleStatistics.angleResidual(2), AngleStatistics.angleResidual(0), AngleStatistics.angleAdd(2), this.m_nominalDt);
        this.m_kinematics = kinematics;
        this.m_latencyCompensator = new KalmanFilterLatencyCompensator();
        this.setVisionMeasurementStdDevs(visionMeasurementStdDevs);
        this.m_visionCorrect = (u, y) -> this.m_observer.correct(Nat.N3(), (Matrix<N3, N1>)u, y, (x, u1) -> x, this.m_visionContR, AngleStatistics.angleMean(2), AngleStatistics.angleResidual(2), AngleStatistics.angleResidual(2), AngleStatistics.angleAdd(2));
        this.m_gyroOffset = initialPoseMeters.getRotation().minus(gyroAngle);
        this.m_previousAngle = initialPoseMeters.getRotation();
        this.m_observer.setXhat(StateSpaceUtil.poseTo3dVector(initialPoseMeters));
    }

    public void setVisionMeasurementStdDevs(Matrix<N3, N1> visionMeasurementStdDevs) {
        this.m_visionContR = StateSpaceUtil.makeCovarianceMatrix(Nat.N3(), visionMeasurementStdDevs);
    }

    public void resetPosition(Pose2d poseMeters, Rotation2d gyroAngle) {
        this.m_observer.reset();
        this.m_latencyCompensator.reset();
        this.m_observer.setXhat(StateSpaceUtil.poseTo3dVector(poseMeters));
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

    public Pose2d update(Rotation2d gyroAngle, SwerveModuleState ... moduleStates) {
        return this.updateWithTime((double)WPIUtilJNI.now() * 1.0E-6, gyroAngle, moduleStates);
    }

    public Pose2d updateWithTime(double currentTimeSeconds, Rotation2d gyroAngle, SwerveModuleState ... moduleStates) {
        double dt = this.m_prevTimeSeconds >= 0.0 ? currentTimeSeconds - this.m_prevTimeSeconds : this.m_nominalDt;
        this.m_prevTimeSeconds = currentTimeSeconds;
        Rotation2d angle = gyroAngle.plus(this.m_gyroOffset);
        double omega = angle.minus(this.m_previousAngle).getRadians() / dt;
        ChassisSpeeds chassisSpeeds = this.m_kinematics.toChassisSpeeds(moduleStates);
        Translation2d fieldRelativeVelocities = new Translation2d(chassisSpeeds.vxMetersPerSecond, chassisSpeeds.vyMetersPerSecond).rotateBy(angle);
        Vector<N3> u = VecBuilder.fill(fieldRelativeVelocities.getX(), fieldRelativeVelocities.getY(), omega);
        this.m_previousAngle = angle;
        Vector<N1> localY = VecBuilder.fill(angle.getRadians());
        this.m_latencyCompensator.addObserverState(this.m_observer, u, localY, currentTimeSeconds);
        this.m_observer.predict(u, dt);
        this.m_observer.correct(u, localY);
        return this.getEstimatedPosition();
    }
}

