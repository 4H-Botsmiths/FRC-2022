/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.ADXL345_SPI;
import edu.wpi.first.wpilibj.simulation.SimDeviceSim;
import java.util.Objects;

public class ADXL345Sim {
    protected SimDouble m_simX;
    protected SimDouble m_simY;
    protected SimDouble m_simZ;

    public ADXL345Sim(ADXL345_SPI device) {
        SimDeviceSim simDevice = new SimDeviceSim("Accel:ADXL345_SPI[" + device.getPort() + "]");
        this.initSim(simDevice);
    }

    public ADXL345Sim(ADXL345_I2C device) {
        SimDeviceSim simDevice = new SimDeviceSim("Accel:ADXL345_I2C[" + device.getPort() + "," + device.getDeviceAddress() + "]");
        this.initSim(simDevice);
    }

    private void initSim(SimDeviceSim simDevice) {
        Objects.requireNonNull(simDevice);
        this.m_simX = simDevice.getDouble("x");
        this.m_simY = simDevice.getDouble("y");
        this.m_simZ = simDevice.getDouble("z");
        Objects.requireNonNull(this.m_simX);
        Objects.requireNonNull(this.m_simY);
        Objects.requireNonNull(this.m_simZ);
    }

    public void setX(double x) {
        this.m_simX.set(x);
    }

    public void setY(double y) {
        this.m_simY.set(y);
    }

    public void setZ(double z) {
        this.m_simZ.set(z);
    }
}

