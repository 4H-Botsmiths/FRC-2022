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
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ADXL345_SPI
implements Accelerometer,
NTSendable,
AutoCloseable {
    private static final int kPowerCtlRegister = 45;
    private static final int kDataFormatRegister = 49;
    private static final int kDataRegister = 50;
    private static final double kGsPerLSB = 0.00390625;
    private static final int kAddress_Read = 128;
    private static final int kAddress_MultiByte = 64;
    private static final int kPowerCtl_Link = 32;
    private static final int kPowerCtl_AutoSleep = 16;
    private static final int kPowerCtl_Measure = 8;
    private static final int kPowerCtl_Sleep = 4;
    private static final int kDataFormat_SelfTest = 128;
    private static final int kDataFormat_SPI = 64;
    private static final int kDataFormat_IntInvert = 32;
    private static final int kDataFormat_FullRes = 8;
    private static final int kDataFormat_Justify = 4;
    protected SPI m_spi;
    protected SimDevice m_simDevice;
    protected SimEnum m_simRange;
    protected SimDouble m_simX;
    protected SimDouble m_simY;
    protected SimDouble m_simZ;

    public ADXL345_SPI(SPI.Port port, Accelerometer.Range range) {
        this.m_spi = new SPI(port);
        this.m_simDevice = SimDevice.create("Accel:ADXL345_SPI", port.value);
        if (this.m_simDevice != null) {
            this.m_simRange = this.m_simDevice.createEnumDouble("range", SimDevice.Direction.kOutput, new String[]{"2G", "4G", "8G", "16G"}, new double[]{2.0, 4.0, 8.0, 16.0}, 0);
            this.m_simX = this.m_simDevice.createDouble("x", SimDevice.Direction.kInput, 0.0);
            this.m_simY = this.m_simDevice.createDouble("y", SimDevice.Direction.kInput, 0.0);
            this.m_simZ = this.m_simDevice.createDouble("z", SimDevice.Direction.kInput, 0.0);
        }
        this.init(range);
        SendableRegistry.addLW((Sendable)this, "ADXL345_SPI", port.value);
    }

    public int getPort() {
        return this.m_spi.getPort();
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
        if (this.m_spi != null) {
            this.m_spi.close();
            this.m_spi = null;
        }
        if (this.m_simDevice != null) {
            this.m_simDevice.close();
            this.m_simDevice = null;
        }
    }

    private void init(Accelerometer.Range range) {
        this.m_spi.setClockRate(500000);
        this.m_spi.setMSBFirst();
        this.m_spi.setSampleDataOnTrailingEdge();
        this.m_spi.setClockActiveLow();
        this.m_spi.setChipSelectActiveHigh();
        byte[] commands = new byte[]{45, 8};
        this.m_spi.write(commands, 2);
        this.setRange(range);
        HAL.report(5, 1);
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
                throw new IllegalArgumentException(range + " unsupported");
            }
        }
        byte[] commands = new byte[]{49, (byte)(8 | value)};
        this.m_spi.write(commands, commands.length);
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
        ByteBuffer transferBuffer = ByteBuffer.allocate(3);
        transferBuffer.put(0, (byte)(242 + axis.value));
        this.m_spi.transaction(transferBuffer, transferBuffer, 3);
        transferBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return (double)transferBuffer.getShort(1) * 0.00390625;
    }

    public AllAxes getAccelerations() {
        AllAxes data = new AllAxes();
        if (this.m_simX != null && this.m_simY != null && this.m_simZ != null) {
            data.XAxis = this.m_simX.get();
            data.YAxis = this.m_simY.get();
            data.ZAxis = this.m_simZ.get();
            return data;
        }
        if (this.m_spi != null) {
            ByteBuffer dataBuffer = ByteBuffer.allocate(7);
            dataBuffer.put(0, (byte)-14);
            this.m_spi.transaction(dataBuffer, dataBuffer, 7);
            dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
            data.XAxis = (double)dataBuffer.getShort(1) * 0.00390625;
            data.YAxis = (double)dataBuffer.getShort(3) * 0.00390625;
            data.ZAxis = (double)dataBuffer.getShort(5) * 0.00390625;
        }
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

