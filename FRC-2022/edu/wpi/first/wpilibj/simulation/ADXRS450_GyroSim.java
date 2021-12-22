/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.simulation.SimDeviceSim;

public class ADXRS450_GyroSim {
    private final SimDouble m_simAngle;
    private final SimDouble m_simRate;

    public ADXRS450_GyroSim(ADXRS450_Gyro gyro) {
        SimDeviceSim wrappedSimDevice = new SimDeviceSim("Gyro:ADXRS450[" + gyro.getPort() + "]");
        this.m_simAngle = wrappedSimDevice.getDouble("angle_x");
        this.m_simRate = wrappedSimDevice.getDouble("rate_x");
    }

    public void setAngle(double angleDegrees) {
        this.m_simAngle.set(angleDegrees);
    }

    public void setRate(double rateDegreesPerSecond) {
        this.m_simRate.set(rateDegreesPerSecond);
    }
}

