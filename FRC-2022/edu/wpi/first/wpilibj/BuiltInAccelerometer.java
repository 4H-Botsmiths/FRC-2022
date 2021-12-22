/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.AccelerometerJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class BuiltInAccelerometer
implements Accelerometer,
Sendable,
AutoCloseable {
    public BuiltInAccelerometer(Accelerometer.Range range) {
        this.setRange(range);
        HAL.report(4, 0, 0, "Built-in accelerometer");
        SendableRegistry.addLW((Sendable)this, "BuiltInAccel", 0);
    }

    public BuiltInAccelerometer() {
        this(Accelerometer.Range.k8G);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
    }

    @Override
    public void setRange(Accelerometer.Range range) {
        AccelerometerJNI.setAccelerometerActive(false);
        switch (range) {
            case k2G: {
                AccelerometerJNI.setAccelerometerRange(0);
                break;
            }
            case k4G: {
                AccelerometerJNI.setAccelerometerRange(1);
                break;
            }
            case k8G: {
                AccelerometerJNI.setAccelerometerRange(2);
                break;
            }
            default: {
                throw new IllegalArgumentException(range + " range not supported (use k2G, k4G, or k8G)");
            }
        }
        AccelerometerJNI.setAccelerometerActive(true);
    }

    @Override
    public double getX() {
        return AccelerometerJNI.getAccelerometerX();
    }

    @Override
    public double getY() {
        return AccelerometerJNI.getAccelerometerY();
    }

    @Override
    public double getZ() {
        return AccelerometerJNI.getAccelerometerZ();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("3AxisAccelerometer");
        builder.addDoubleProperty("X", this::getX, null);
        builder.addDoubleProperty("Y", this::getY, null);
        builder.addDoubleProperty("Z", this::getZ, null);
    }
}

