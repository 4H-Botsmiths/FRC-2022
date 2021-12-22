/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.kinematics;

import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.MathUsageId;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;

public class DifferentialDriveKinematics {
    public final double trackWidthMeters;

    public DifferentialDriveKinematics(double trackWidthMeters) {
        this.trackWidthMeters = trackWidthMeters;
        MathSharedStore.reportUsage(MathUsageId.kKinematics_DifferentialDrive, 1);
    }

    public ChassisSpeeds toChassisSpeeds(DifferentialDriveWheelSpeeds wheelSpeeds) {
        return new ChassisSpeeds((wheelSpeeds.leftMetersPerSecond + wheelSpeeds.rightMetersPerSecond) / 2.0, 0.0, (wheelSpeeds.rightMetersPerSecond - wheelSpeeds.leftMetersPerSecond) / this.trackWidthMeters);
    }

    public DifferentialDriveWheelSpeeds toWheelSpeeds(ChassisSpeeds chassisSpeeds) {
        return new DifferentialDriveWheelSpeeds(chassisSpeeds.vxMetersPerSecond - this.trackWidthMeters / 2.0 * chassisSpeeds.omegaRadiansPerSecond, chassisSpeeds.vxMetersPerSecond + this.trackWidthMeters / 2.0 * chassisSpeeds.omegaRadiansPerSecond);
    }
}

