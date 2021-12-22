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
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ADXL362
implements Accelerometer,
NTSendable,
AutoCloseable {
    private static final byte kRegWrite = 10;
    private static final byte kRegRead = 11;
    private static final byte kPartIdRegister = 2;
    private static final byte kDataRegister = 14;
    private static final byte kFilterCtlRegister = 44;
    private static final byte kPowerCtlRegister = 45;
    private static final byte kFilterCtl_Range2G = 0;
    private static final byte kFilterCtl_Range4G = 64;
    private static final byte kFilterCtl_Range8G = -128;
    private static final byte kFilterCtl_ODR_100Hz = 3;
    private static final byte kPowerCtl_UltraLowNoise = 32;
    private static final byte kPowerCtl_AutoSleep = 4;
    private static final byte kPowerCtl_Measure = 2;
    private SPI m_spi;
    private SimDevice m_simDevice;
    private SimEnum m_simRange;
    private SimDouble m_simX;
    private SimDouble m_simY;
    private SimDouble m_simZ;
    private double m_gsPerLSB;

    public ADXL362(Accelerometer.Range range) {
        this(SPI.Port.kOnboardCS1, range);
    }

    public ADXL362(SPI.Port port, Accelerometer.Range range) {
        this.m_spi = new SPI(port);
        this.m_simDevice = SimDevice.create("Accel:ADXL362", port.value);
        if (this.m_simDevice != null) {
            this.m_simRange = this.m_simDevice.createEnumDouble("range", SimDevice.Direction.kOutput, new String[]{"2G", "4G", "8G", "16G"}, new double[]{2.0, 4.0, 8.0, 16.0}, 0);
            this.m_simX = this.m_simDevice.createDouble("x", SimDevice.Direction.kInput, 0.0);
            this.m_simY = this.m_simDevice.createDouble("y", SimDevice.Direction.kInput, 0.0);
            this.m_simZ = this.m_simDevice.createDouble("z", SimDevice.Direction.kInput, 0.0);
        }
        this.m_spi.setClockRate(3000000);
        this.m_spi.setMSBFirst();
        this.m_spi.setSampleDataOnTrailingEdge();
        this.m_spi.setClockActiveLow();
        this.m_spi.setChipSelectActiveLow();
        ByteBuffer transferBuffer = ByteBuffer.allocate(3);
        if (this.m_simDevice == null) {
            transferBuffer.put(0, (byte)11);
            transferBuffer.put(1, (byte)2);
            this.m_spi.transaction(transferBuffer, transferBuffer, 3);
            if (transferBuffer.get(2) != -14) {
                this.m_spi.close();
                this.m_spi = null;
                DriverStation.reportError("could not find ADXL362 on SPI port " + port.value, false);
                return;
            }
        }
        this.setRange(range);
        transferBuffer.put(0, (byte)10);
        transferBuffer.put(1, (byte)45);
        transferBuffer.put(2, (byte)34);
        this.m_spi.write(transferBuffer, 3);
        HAL.report(53, port.value + 1);
        SendableRegistry.addLW((Sendable)this, "ADXL362", port.value);
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

    @Override
    public void setRange(Accelerometer.Range range) {
        int value;
        if (this.m_spi == null) {
            return;
        }
        switch (range) {
            case k2G: {
                value = 0;
                this.m_gsPerLSB = 0.001;
                break;
            }
            case k4G: {
                value = 64;
                this.m_gsPerLSB = 0.002;
                break;
            }
            case k8G: 
            case k16G: {
                value = -128;
                this.m_gsPerLSB = 0.004;
                break;
            }
            default: {
                throw new IllegalArgumentException(range + " unsupported");
            }
        }
        byte[] commands = new byte[]{10, 44, (byte)(3 | value)};
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
        if (this.m_spi == null) {
            return 0.0;
        }
        ByteBuffer transferBuffer = ByteBuffer.allocate(4);
        transferBuffer.put(0, (byte)11);
        transferBuffer.put(1, (byte)(14 + axis.value));
        this.m_spi.transaction(transferBuffer, transferBuffer, 4);
        transferBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return (double)transferBuffer.getShort(2) * this.m_gsPerLSB;
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
            ByteBuffer dataBuffer = ByteBuffer.allocate(8);
            dataBuffer.put(0, (byte)11);
            dataBuffer.put(1, (byte)14);
            this.m_spi.transaction(dataBuffer, dataBuffer, 8);
            dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
            data.XAxis = (double)dataBuffer.getShort(2) * this.m_gsPerLSB;
            data.YAxis = (double)dataBuffer.getShort(4) * this.m_gsPerLSB;
            data.ZAxis = (double)dataBuffer.getShort(6) * this.m_gsPerLSB;
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

