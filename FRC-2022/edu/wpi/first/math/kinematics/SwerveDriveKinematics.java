/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.kinematics;

import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.MathUsageId;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import java.util.Arrays;
import java.util.Collections;
import org.ejml.simple.SimpleMatrix;

public class SwerveDriveKinematics {
    private final SimpleMatrix m_inverseKinematics;
    private final SimpleMatrix m_forwardKinematics;
    private final int m_numModules;
    private final Translation2d[] m_modules;
    private Translation2d m_prevCoR = new Translation2d();

    public SwerveDriveKinematics(Translation2d ... wheelsMeters) {
        if (wheelsMeters.length < 2) {
            throw new IllegalArgumentException("A swerve drive requires at least two modules");
        }
        this.m_numModules = wheelsMeters.length;
        this.m_modules = Arrays.copyOf(wheelsMeters, this.m_numModules);
        this.m_inverseKinematics = new SimpleMatrix(this.m_numModules * 2, 3);
        for (int i = 0; i < this.m_numModules; ++i) {
            this.m_inverseKinematics.setRow(i * 2 + 0, 0, 1.0, 0.0, -this.m_modules[i].getY());
            this.m_inverseKinematics.setRow(i * 2 + 1, 0, 0.0, 1.0, this.m_modules[i].getX());
        }
        this.m_forwardKinematics = (SimpleMatrix)this.m_inverseKinematics.pseudoInverse();
        MathSharedStore.reportUsage(MathUsageId.kKinematics_SwerveDrive, 1);
    }

    public SwerveModuleState[] toSwerveModuleStates(ChassisSpeeds chassisSpeeds, Translation2d centerOfRotationMeters) {
        if (!centerOfRotationMeters.equals(this.m_prevCoR)) {
            for (int i = 0; i < this.m_numModules; ++i) {
                this.m_inverseKinematics.setRow(i * 2 + 0, 0, 1.0, 0.0, -this.m_modules[i].getY() + centerOfRotationMeters.getY());
                this.m_inverseKinematics.setRow(i * 2 + 1, 0, 0.0, 1.0, this.m_modules[i].getX() - centerOfRotationMeters.getX());
            }
            this.m_prevCoR = centerOfRotationMeters;
        }
        SimpleMatrix chassisSpeedsVector = new SimpleMatrix(3, 1);
        chassisSpeedsVector.setColumn(0, 0, chassisSpeeds.vxMetersPerSecond, chassisSpeeds.vyMetersPerSecond, chassisSpeeds.omegaRadiansPerSecond);
        SimpleMatrix moduleStatesMatrix = this.m_inverseKinematics.mult(chassisSpeedsVector);
        SwerveModuleState[] moduleStates = new SwerveModuleState[this.m_numModules];
        for (int i = 0; i < this.m_numModules; ++i) {
            double x = moduleStatesMatrix.get(i * 2, 0);
            double y = moduleStatesMatrix.get(i * 2 + 1, 0);
            double speed = Math.hypot(x, y);
            Rotation2d angle = new Rotation2d(x, y);
            moduleStates[i] = new SwerveModuleState(speed, angle);
        }
        return moduleStates;
    }

    public SwerveModuleState[] toSwerveModuleStates(ChassisSpeeds chassisSpeeds) {
        return this.toSwerveModuleStates(chassisSpeeds, new Translation2d());
    }

    public ChassisSpeeds toChassisSpeeds(SwerveModuleState ... wheelStates) {
        if (wheelStates.length != this.m_numModules) {
            throw new IllegalArgumentException("Number of modules is not consistent with number of wheel locations provided in constructor");
        }
        SimpleMatrix moduleStatesMatrix = new SimpleMatrix(this.m_numModules * 2, 1);
        for (int i = 0; i < this.m_numModules; ++i) {
            SwerveModuleState module = wheelStates[i];
            moduleStatesMatrix.set(i * 2, 0, module.speedMetersPerSecond * module.angle.getCos());
            moduleStatesMatrix.set(i * 2 + 1, module.speedMetersPerSecond * module.angle.getSin());
        }
        SimpleMatrix chassisSpeedsVector = this.m_forwardKinematics.mult(moduleStatesMatrix);
        return new ChassisSpeeds(chassisSpeedsVector.get(0, 0), chassisSpeedsVector.get(1, 0), chassisSpeedsVector.get(2, 0));
    }

    public static void normalizeWheelSpeeds(SwerveModuleState[] moduleStates, double attainableMaxSpeedMetersPerSecond) {
        double realMaxSpeed = Collections.max(Arrays.asList(moduleStates)).speedMetersPerSecond;
        if (realMaxSpeed > attainableMaxSpeedMetersPerSecond) {
            for (SwerveModuleState moduleState : moduleStates) {
                moduleState.speedMetersPerSecond = moduleState.speedMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
            }
        }
    }
}

