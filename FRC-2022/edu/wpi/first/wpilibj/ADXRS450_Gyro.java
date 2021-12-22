/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ADXRS450_Gyro
implements Gyro,
Sendable {
    private static final double kSamplePeriod = 5.0E-4;
    private static final double kCalibrationSampleTime = 5.0;
    private static final double kDegreePerSecondPerLSB = 0.0125;
    private static final int kRateRegister = 0;
    private static final int kTemRegister = 2;
    private static final int kLoCSTRegister = 4;
    private static final int kHiCSTRegister = 6;
    private static final int kQuadRegister = 8;
    private static final int kFaultRegister = 10;
    private static final int kPIDRegister = 12;
    private static final int kSNHighRegister = 14;
    private static final int kSNLowRegister = 16;
    private SPI m_spi;
    private SimDevice m_simDevice;
    private SimBoolean m_simConnected;
    private SimDouble m_simAngle;
    private SimDouble m_simRate;

    public ADXRS450_Gyro() {
        this(SPI.Port.kOnboardCS0);
    }

    public ADXRS450_Gyro(SPI.Port port) {
        this.m_spi = new SPI(port);
        this.m_simDevice = SimDevice.create("Gyro:ADXRS450", port.value);
        if (this.m_simDevice != null) {
            this.m_simConnected = this.m_simDevice.createBoolean("connected", SimDevice.Direction.kInput, true);
            this.m_simAngle = this.m_simDevice.createDouble("angle_x", SimDevice.Direction.kInput, 0.0);
            this.m_simRate = this.m_simDevice.createDouble("rate_x", SimDevice.Direction.kInput, 0.0);
        }
        this.m_spi.setClockRate(3000000);
        this.m_spi.setMSBFirst();
        this.m_spi.setSampleDataOnLeadingEdge();
        this.m_spi.setClockActiveHigh();
        this.m_spi.setChipSelectActiveLow();
        if (this.m_simDevice == null) {
            if ((this.readRegister(12) & 0xFF00) != 20992) {
                this.m_spi.close();
                this.m_spi = null;
                DriverStation.reportError("could not find ADXRS450 gyro on SPI port " + port.value, false);
                return;
            }
            this.m_spi.initAccumulator(5.0E-4, 0x20000000, 4, 0xC00000E, 0x4000000, 10, 16, true, true);
            this.calibrate();
        }
        HAL.report(54, port.value + 1);
        SendableRegistry.addLW((Sendable)this, "ADXRS450_Gyro", port.value);
    }

    public boolean isConnected() {
        if (this.m_simConnected != null) {
            return this.m_simConnected.get();
        }
        return this.m_spi != null;
    }

    @Override
    public void calibrate() {
        if (this.m_spi == null) {
            return;
        }
        Timer.delay(0.1);
        this.m_spi.setAccumulatorIntegratedCenter(0.0);
        this.m_spi.resetAccumulator();
        Timer.delay(5.0);
        this.m_spi.setAccumulatorIntegratedCenter(this.m_spi.getAccumulatorIntegratedAverage());
        this.m_spi.resetAccumulator();
    }

    public int getPort() {
        return this.m_spi.getPort();
    }

    private boolean calcParity(int value) {
        boolean parity = false;
        while (value != 0) {
            parity = !parity;
            value &= value - 1;
        }
        return parity;
    }

    private int readRegister(int reg) {
        int cmdhi = 0x8000 | reg << 1;
        boolean parity = this.calcParity(cmdhi);
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.order(ByteOrder.BIG_ENDIAN);
        buf.put(0, (byte)(cmdhi >> 8));
        buf.put(1, (byte)(cmdhi & 0xFF));
        buf.put(2, (byte)0);
        buf.put(3, (byte)(!parity ? 1 : 0));
        this.m_spi.write(buf, 4);
        this.m_spi.read(false, buf, 4);
        if ((buf.get(0) & 0xE0) == 0) {
            return 0;
        }
        return buf.getInt(0) >> 5 & 0xFFFF;
    }

    @Override
    public void reset() {
        if (this.m_simAngle != null) {
            this.m_simAngle.reset();
        }
        if (this.m_spi != null) {
            this.m_spi.resetAccumulator();
        }
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
    public double getAngle() {
        if (this.m_simAngle != null) {
            return this.m_simAngle.get();
        }
        if (this.m_spi == null) {
            return 0.0;
        }
        return this.m_spi.getAccumulatorIntegratedValue() * 0.0125;
    }

    @Override
    public double getRate() {
        if (this.m_simRate != null) {
            return this.m_simRate.get();
        }
        if (this.m_spi == null) {
            return 0.0;
        }
        return (double)this.m_spi.getAccumulatorLastValue() * 0.0125;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Gyro");
        builder.addDoubleProperty("Value", this::getAngle, null);
    }
}

