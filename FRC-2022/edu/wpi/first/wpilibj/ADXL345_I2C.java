/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.SimEnum;
import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.NTSendableBuilder;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ADXL345_I2C
implements Accelerometer,
NTSendable,
AutoCloseable {
    private static final byte kAddress = 29;
    private static final byte kPowerCtlRegister = 45;
    private static final byte kDataFormatRegister = 49;
    private static final byte kDataRegister = 50;
    private static final double kGsPerLSB = 0.00390625;
    private static final byte kPowerCtl_Link = 32;
    private static final byte kPowerCtl_AutoSleep = 16;
    private static final byte kPowerCtl_Measure = 8;
    private static final byte kPowerCtl_Sleep = 4;
    private static final byte kDataFormat_SelfTest = -128;
    private static final byte kDataFormat_SPI = 64;
    private static final byte kDataFormat_IntInvert = 32;
    private static final byte kDataFormat_FullRes = 8;
    private static final byte kDataFormat_Justify = 4;
    protected I2C m_i2c;
    protected SimDevice m_simDevice;
    protected SimEnum m_simRange;
    protected SimDouble m_simX;
    protected SimDouble m_simY;
    protected SimDouble m_simZ;

    public ADXL345_I2C(I2C.Port port, Accelerometer.Range range) {
        this(port, range, 29);
    }

    public ADXL345_I2C(I2C.Port port, Accelerometer.Range range, int deviceAddress) {
        this.m_i2c = new I2C(port, deviceAddress);
        this.m_simDevice = SimDevice.create("Accel:ADXL345_I2C", port.value, deviceAddress);
        if (this.m_simDevice != null) {
            this.m_simRange = this.m_simDevice.createEnumDouble("range", SimDevice.Direction.kOutput, new String[]{"2G", "4G", "8G", "16G"}, new double[]{2.0, 4.0, 8.0, 16.0}, 0);
            this.m_simX = this.m_simDevice.createDouble("x", SimDevice.Direction.kInput, 0.0);
            this.m_simY = this.m_simDevice.createDouble("y", SimDevice.Direction.kInput, 0.0);
            this.m_simZ = this.m_simDevice.createDouble("z", SimDevice.Direction.kInput, 0.0);
        }
        this.m_i2c.write(45, 8);
        this.setRange(range);
        HAL.report(5, 2);
        SendableRegistry.addLW((Sendable)this, "ADXL345_I2C", port.value);
    }

    public int getPort() {
        return this.m_i2c.getPort();
    }

    public int getDeviceAddress() {
        return this.m_i2c.getDeviceAddress();
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        if (this.m_i2c != null) {
            this.m_i2c.close();
            this.m_i2c = null;
        }
        if (this.m_simDevice != null) {
            this.m_simDevice.close();
            this.m_simDevice = null;
        }
    }

    @Override
    public void setRange(Accelerometer.Range range) {
        int value;
        switch (range) {
            case k2G: {
                value = 0;
                break;
            }
            case k4G: {
                value = 1;
                break;
            }
            case k8G: {
                value = 2;
                break;
            }
            case k16G: {
                value = 3;
                break;
            }
            default: {
                throw new IllegalArgumentException(range + " unsupported range type");
            }
        }
        this.m_i2c.write(49, 8 | value);
        if (this.m_simRange != null) {
            this.m_simRange.set(value);
        }
    }

    @Override
    public double getX() {
        return this.getAcceleration(Axes.kX);
    }

    @Override
    public double getY() {
        return this.getAcceleration(Axes.kY);
    }

    @Override
    public double getZ() {
        return this.getAcceleration(Axes.kZ);
    }

    public double getAcceleration(Axes axis) {
        if (axis == Axes.kX && this.m_simX != null) {
            return this.m_simX.get();
        }
        if (axis == Axes.kY && this.m_simY != null) {
            return this.m_simY.get();
        }
        if (axis == Axes.kZ && this.m_simZ != null) {
            return this.m_simZ.get();
        }
        ByteBuffer rawAccel = ByteBuffer.allocate(2);
        this.m_i2c.read(50 + axis.value, 2, rawAccel);
        rawAccel.order(ByteOrder.LITTLE_ENDIAN);
        return (double)rawAccel.getShort(0) * 0.00390625;
    }

    public AllAxes getAccelerations() {
        AllAxes data = new AllAxes();
        if (this.m_simX != null && this.m_simY != null && this.m_simZ != null) {
            data.XAxis = this.m_simX.get();
            data.YAxis = this.m_simY.get();
            data.ZAxis = this.m_simZ.get();
            return data;
        }
        ByteBuffer rawData = ByteBuffer.allocate(6);
        this.m_i2c.read(50, 6, rawData);
        rawData.order(ByteOrder.LITTLE_ENDIAN);
        data.XAxis = (double)rawData.getShort(0) * 0.00390625;
        data.YAxis = (double)rawData.getShort(2) * 0.00390625;
        data.ZAxis = (double)rawData.getShort(4) * 0.00390625;
        return data;
    }

    @Override
    public void initSendable(NTSendableBuilder builder) {
        builder.setSmartDashboardType("3AxisAccelerometer");
        NetworkTableEntry entryX = builder.getEntry("X");
        NetworkTableEntry entryY = builder.getEntry("Y");
        NetworkTableEntry entryZ = builder.getEntry("Z");
        builder.setUpdateTable(() -> {
            AllAxes data = this.getAccelerations();
            entryX.setDouble(data.XAxis);
            entryY.setDouble(data.YAxis);
            entryZ.setDouble(data.ZAxis);
        });
    }

    public static class AllAxes {
        public double XAxis;
        public double YAxis;
        public double ZAxis;
    }

    public static enum Axes {
        kX(0),
        kY(2),
        kZ(4);

        public final byte value;

        private Axes(byte value) {
            this.value = value;
        }
    }
}

