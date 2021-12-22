/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.wpilibj.ADXL362;
import edu.wpi.first.wpilibj.simulation.SimDeviceSim;
import java.util.Objects;

public class ADXL362Sim {
    protected SimDouble m_simX;
    protected SimDouble m_simY;
    protected SimDouble m_simZ;

    public ADXL362Sim(ADXL362 device) {
        SimDeviceSim wrappedSimDevice = new SimDeviceSim("Accel:ADXL362[" + device.getPort() + "]");
        this.initSim(wrappedSimDevice);
    }

    private void initSim(SimDeviceSim wrappedSimDevice) {
        this.m_simX = wrappedSimDevice.getDouble("x");
        this.m_simY = wrappedSimDevice.getDouble("y");
        this.m_simZ = wrappedSimDevice.getDouble("z");
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

