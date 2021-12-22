/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.kinematics;

import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.MathUsageId;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import org.ejml.simple.SimpleMatrix;

public class MecanumDriveKinematics {
    private final SimpleMatrix m_inverseKinematics;
    private final SimpleMatrix m_forwardKinematics;
    private final Translation2d m_frontLeftWheelMeters;
    private final Translation2d m_frontRightWheelMeters;
    private final Translation2d m_rearLeftWheelMeters;
    private final Translation2d m_rearRightWheelMeters;
    private Translation2d m_prevCoR = new Translation2d();

    public MecanumDriveKinematics(Translation2d frontLeftWheelMeters, Translation2d frontRightWheelMeters, Translation2d rearLeftWheelMeters, Translation2d rearRightWheelMeters) {
        this.m_frontLeftWheelMeters = frontLeftWheelMeters;
        this.m_frontRightWheelMeters = frontRightWheelMeters;
        this.m_rearLeftWheelMeters = rearLeftWheelMeters;
        this.m_rearRightWheelMeters = rearRightWheelMeters;
        this.m_inverseKinematics = new SimpleMatrix(4, 3);
        this.setInverseKinematics(frontLeftWheelMeters, frontRightWheelMeters, rearLeftWheelMeters, rearRightWheelMeters);
        this.m_forwardKinematics = (SimpleMatrix)this.m_inverseKinematics.pseudoInverse();
        MathSharedStore.reportUsage(MathUsageId.kKinematics_MecanumDrive, 1);
    }

    public MecanumDriveWheelSpeeds toWheelSpeeds(ChassisSpeeds chassisSpeeds, Translation2d centerOfRotationMeters) {
        if (!centerOfRotationMeters.equals(this.m_prevCoR)) {
            Translation2d fl = this.m_frontLeftWheelMeters.minus(centerOfRotationMeters);
            Translation2d fr = this.m_frontRightWheelMeters.minus(centerOfRotationMeters);
            Translation2d rl = this.m_rearLeftWheelMeters.minus(centerOfRotationMeters);
            Translation2d rr = this.m_rearRightWheelMeters.minus(centerOfRotationMeters);
            this.setInverseKinematics(fl, fr, rl, rr);
            this.m_prevCoR = centerOfRotationMeters;
        }
        SimpleMatrix chassisSpeedsVector = new SimpleMatrix(3, 1);
        chassisSpeedsVector.setColumn(0, 0, chassisSpeeds.vxMetersPerSecond, chassisSpeeds.vyMetersPerSecond, chassisSpeeds.omegaRadiansPerSecond);
        SimpleMatrix wheelsVector = this.m_inverseKinematics.mult(chassisSpeedsVector);
        return new MecanumDriveWheelSpeeds(wheelsVector.get(0, 0), wheelsVector.get(1, 0), wheelsVector.get(2, 0), wheelsVector.get(3, 0));
    }

    public MecanumDriveWheelSpeeds toWheelSpeeds(ChassisSpeeds chassisSpeeds) {
        return this.toWheelSpeeds(chassisSpeeds, new Translation2d());
    }

    public ChassisSpeeds toChassisSpeeds(MecanumDriveWheelSpeeds wheelSpeeds) {
        SimpleMatrix wheelSpeedsVector = new SimpleMatrix(4, 1);
        wheelSpeedsVector.setColumn(0, 0, wheelSpeeds.frontLeftMetersPerSecond, wheelSpeeds.frontRightMetersPerSecond, wheelSpeeds.rearLeftMetersPerSecond, wheelSpeeds.rearRightMetersPerSecond);
        SimpleMatrix chassisSpeedsVector = this.m_forwardKinematics.mult(wheelSpeedsVector);
        return new ChassisSpeeds(chassisSpeedsVector.get(0, 0), chassisSpeedsVector.get(1, 0), chassisSpeedsVector.get(2, 0));
    }

    private void setInverseKinematics(Translation2d fl, Translation2d fr, Translation2d rl, Translation2d rr) {
        this.m_inverseKinematics.setRow(0, 0, 1.0, -1.0, -(fl.getX() + fl.getY()));
        this.m_inverseKinematics.setRow(1, 0, 1.0, 1.0, fr.getX() - fr.getY());
        this.m_inverseKinematics.setRow(2, 0, 1.0, 1.0, rl.getX() - rl.getY());
        this.m_inverseKinematics.setRow(3, 0, 1.0, -1.0, -(rr.getX() + rr.getY()));
    }
}

