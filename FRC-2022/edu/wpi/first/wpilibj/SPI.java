/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.AccumulatorResult;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.SPIJNI;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Notifier;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class SPI
implements AutoCloseable {
    private int m_port;
    private int m_msbFirst;
    private int m_clockIdleHigh;
    private int m_sampleOnTrailing;
    private static final int kAccumulateDepth = 2048;
    private Accumulator m_accum;

    public SPI(Port port) {
        this.m_port = (byte)port.value;
        SPIJNI.spiInitialize(this.m_port);
        HAL.report(35, port.value + 1);
    }

    public int getPort() {
        return this.m_port;
    }

    @Override
    public void close() {
        if (this.m_accum != null) {
            this.m_accum.close();
            this.m_accum = null;
        }
        SPIJNI.spiClose(this.m_port);
    }

    public final void setClockRate(int hz) {
        SPIJNI.spiSetSpeed(this.m_port, hz);
    }

    public final void setMSBFirst() {
        this.m_msbFirst = 1;
        SPIJNI.spiSetOpts(this.m_port, this.m_msbFirst, this.m_sampleOnTrailing, this.m_clockIdleHigh);
    }

    public final void setLSBFirst() {
        this.m_msbFirst = 0;
        SPIJNI.spiSetOpts(this.m_port, this.m_msbFirst, this.m_sampleOnTrailing, this.m_clockIdleHigh);
    }

    public final void setClockActiveLow() {
        this.m_clockIdleHigh = 1;
        SPIJNI.spiSetOpts(this.m_port, this.m_msbFirst, this.m_sampleOnTrailing, this.m_clockIdleHigh);
    }

    public final void setClockActiveHigh() {
        this.m_clockIdleHigh = 0;
        SPIJNI.spiSetOpts(this.m_port, this.m_msbFirst, this.m_sampleOnTrailing, this.m_clockIdleHigh);
    }

    public final void setSampleDataOnLeadingEdge() {
        this.m_sampleOnTrailing = 0;
        SPIJNI.spiSetOpts(this.m_port, this.m_msbFirst, this.m_sampleOnTrailing, this.m_clockIdleHigh);
    }

    public final void setSampleDataOnTrailingEdge() {
        this.m_sampleOnTrailing = 1;
        SPIJNI.spiSetOpts(this.m_port, this.m_msbFirst, this.m_sampleOnTrailing, this.m_clockIdleHigh);
    }

    @Deprecated
    public final void setSampleDataOnFalling() {
        this.m_sampleOnTrailing = 1;
        SPIJNI.spiSetOpts(this.m_port, this.m_msbFirst, this.m_sampleOnTrailing, this.m_clockIdleHigh);
    }

    @Deprecated
    public final void setSampleDataOnRising() {
        this.m_sampleOnTrailing = 0;
        SPIJNI.spiSetOpts(this.m_port, this.m_msbFirst, this.m_sampleOnTrailing, this.m_clockIdleHigh);
    }

    public final void setChipSelectActiveHigh() {
        SPIJNI.spiSetChipSelectActiveHigh(this.m_port);
    }

    public final void setChipSelectActiveLow() {
        SPIJNI.spiSetChipSelectActiveLow(this.m_port);
    }

    public int write(byte[] dataToSend, int size) {
        if (dataToSend.length < size) {
            throw new IllegalArgumentException("buffer is too small, must be at least " + size);
        }
        return SPIJNI.spiWriteB(this.m_port, dataToSend, (byte)size);
    }

    public int write(ByteBuffer dataToSend, int size) {
        if (dataToSend.hasArray()) {
            return this.write(dataToSend.array(), size);
        }
        if (!dataToSend.isDirect()) {
            throw new IllegalArgumentException("must be a direct buffer");
        }
        if (dataToSend.capacity() < size) {
            throw new IllegalArgumentException("buffer is too small, must be at least " + size);
        }
        return SPIJNI.spiWrite(this.m_port, dataToSend, (byte)size);
    }

    public int read(boolean initiate, byte[] dataReceived, int size) {
        if (dataReceived.length < size) {
            throw new IllegalArgumentException("buffer is too small, must be at least " + size);
        }
        return SPIJNI.spiReadB(this.m_port, initiate, dataReceived, (byte)size);
    }

    public int read(boolean initiate, ByteBuffer dataReceived, int size) {
        if (dataReceived.hasArray()) {
            return this.read(initiate, dataReceived.array(), size);
        }
        if (!dataReceived.isDirect()) {
            throw new IllegalArgumentException("must be a direct buffer");
        }
        if (dataReceived.capacity() < size) {
            throw new IllegalArgumentException("buffer is too small, must be at least " + size);
        }
        return SPIJNI.spiRead(this.m_port, initiate, dataReceived, (byte)size);
    }

    public int transaction(byte[] dataToSend, byte[] dataReceived, int size) {
        if (dataToSend.length < size) {
            throw new IllegalArgumentException("dataToSend is too small, must be at least " + size);
        }
        if (dataReceived.length < size) {
            throw new IllegalArgumentException("dataReceived is too small, must be at least " + size);
        }
        return SPIJNI.spiTransactionB(this.m_port, dataToSend, dataReceived, (byte)size);
    }

    public int transaction(ByteBuffer dataToSend, ByteBuffer dataReceived, int size) {
        if (dataToSend.hasArray() && dataReceived.hasArray()) {
            return this.transaction(dataToSend.array(), dataReceived.array(), size);
        }
        if (!dataToSend.isDirect()) {
            throw new IllegalArgumentException("dataToSend must be a direct buffer");
        }
        if (dataToSend.capacity() < size) {
            throw new IllegalArgumentException("dataToSend is too small, must be at least " + size);
        }
        if (!dataReceived.isDirect()) {
            throw new IllegalArgumentException("dataReceived must be a direct buffer");
        }
        if (dataReceived.capacity() < size) {
            throw new IllegalArgumentException("dataReceived is too small, must be at least " + size);
        }
        return SPIJNI.spiTransaction(this.m_port, dataToSend, dataReceived, (byte)size);
    }

    public void initAuto(int bufferSize) {
        SPIJNI.spiInitAuto(this.m_port, bufferSize);
    }

    public void freeAuto() {
        SPIJNI.spiFreeAuto(this.m_port);
    }

    public void setAutoTransmitData(byte[] dataToSend, int zeroSize) {
        SPIJNI.spiSetAutoTransmitData(this.m_port, dataToSend, zeroSize);
    }

    public void startAutoRate(double period) {
        SPIJNI.spiStartAutoRate(this.m_port, period);
    }

    public void startAutoTrigger(DigitalSource source, boolean rising, boolean falling) {
        SPIJNI.spiStartAutoTrigger(this.m_port, source.getPortHandleForRouting(), source.getAnalogTriggerTypeForRouting(), rising, falling);
    }

    public void stopAuto() {
        SPIJNI.spiStopAuto(this.m_port);
    }

    public void forceAutoRead() {
        SPIJNI.spiForceAutoRead(this.m_port);
    }

    public int readAutoReceivedData(ByteBuffer buffer, int numToRead, double timeout) {
        if (!buffer.isDirect()) {
            throw new IllegalArgumentException("must be a direct buffer");
        }
        if (buffer.capacity() < numToRead * 4) {
            throw new IllegalArgumentException("buffer is too small, must be at least " + numToRead * 4);
        }
        return SPIJNI.spiReadAutoReceivedData(this.m_port, buffer, numToRead, timeout);
    }

    public int readAutoReceivedData(int[] buffer, int numToRead, double timeout) {
        if (buffer.length < numToRead) {
            throw new IllegalArgumentException("buffer is too small, must be at least " + numToRead);
        }
        return SPIJNI.spiReadAutoReceivedData(this.m_port, buffer, numToRead, timeout);
    }

    public int getAutoDroppedCount() {
        return SPIJNI.spiGetAutoDroppedCount(this.m_port);
    }

    public void configureAutoStall(int csToSclkTicks, int stallTicks, int pow2BytesPerRead) {
        SPIJNI.spiConfigureAutoStall(this.m_port, csToSclkTicks, stallTicks, pow2BytesPerRead);
    }

    public void initAccumulator(double period, int cmd, int xferSize, int validMask, int validValue, int dataShift, int dataSize, boolean isSigned, boolean bigEndian) {
        this.initAuto(xferSize * 2048);
        byte[] cmdBytes = new byte[]{0, 0, 0, 0};
        if (bigEndian) {
            for (int i = xferSize - 1; i >= 0; --i) {
                cmdBytes[i] = (byte)(cmd & 0xFF);
                cmd >>= 8;
            }
        } else {
            cmdBytes[0] = (byte)(cmd & 0xFF);
            cmdBytes[1] = (byte)((cmd >>= 8) & 0xFF);
            cmdBytes[2] = (byte)((cmd >>= 8) & 0xFF);
            cmdBytes[3] = (byte)((cmd >>= 8) & 0xFF);
        }
        this.setAutoTransmitData(cmdBytes, xferSize - 4);
        this.startAutoRate(period);
        this.m_accum = new Accumulator(this.m_port, xferSize, validMask, validValue, dataShift, dataSize, isSigned, bigEndian);
        this.m_accum.m_notifier.startPeriodic(period * 1024.0);
    }

    public void freeAccumulator() {
        if (this.m_accum != null) {
            this.m_accum.close();
            this.m_accum = null;
        }
        this.freeAuto();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resetAccumulator() {
        if (this.m_accum == null) {
            return;
        }
        Object object = this.m_accum.m_mutex;
        synchronized (object) {
            this.m_accum.m_value = 0L;
            this.m_accum.m_count = 0;
            this.m_accum.m_lastValue = 0;
            this.m_accum.m_lastTimestamp = 0L;
            this.m_accum.m_integratedValue = 0.0;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setAccumulatorCenter(int center) {
        if (this.m_accum == null) {
            return;
        }
        Object object = this.m_accum.m_mutex;
        synchronized (object) {
            this.m_accum.m_center = center;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setAccumulatorDeadband(int deadband) {
        if (this.m_accum == null) {
            return;
        }
        Object object = this.m_accum.m_mutex;
        synchronized (object) {
            this.m_accum.m_deadband = deadband;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getAccumulatorLastValue() {
        if (this.m_accum == null) {
            return 0;
        }
        Object object = this.m_accum.m_mutex;
        synchronized (object) {
            this.m_accum.update();
            return this.m_accum.m_lastValue;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long getAccumulatorValue() {
        if (this.m_accum == null) {
            return 0L;
        }
        Object object = this.m_accum.m_mutex;
        synchronized (object) {
            this.m_accum.update();
            return this.m_accum.m_value;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getAccumulatorCount() {
        if (this.m_accum == null) {
            return 0;
        }
        Object object = this.m_accum.m_mutex;
        synchronized (object) {
            this.m_accum.update();
            return this.m_accum.m_count;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public double getAccumulatorAverage() {
        if (this.m_accum == null) {
            return 0.0;
        }
        Object object = this.m_accum.m_mutex;
        synchronized (object) {
            this.m_accum.update();
            if (this.m_accum.m_count == 0) {
                return 0.0;
            }
            return (double)this.m_accum.m_value / (double)this.m_accum.m_count;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void getAccumulatorOutput(AccumulatorResult result) {
        if (result == null) {
            throw new IllegalArgumentException("Null parameter `result'");
        }
        if (this.m_accum == null) {
            result.value = 0L;
            result.count = 0L;
            return;
        }
        Object object = this.m_accum.m_mutex;
        synchronized (object) {
            this.m_accum.update();
            result.value = this.m_accum.m_value;
            result.count = this.m_accum.m_count;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setAccumulatorIntegratedCenter(double center) {
        if (this.m_accum == null) {
            return;
        }
        Object object = this.m_accum.m_mutex;
        synchronized (object) {
            this.m_accum.m_integratedCenter = center;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public double getAccumulatorIntegratedValue() {
        if (this.m_accum == null) {
            return 0.0;
        }
        Object object = this.m_accum.m_mutex;
        synchronized (object) {
            this.m_accum.update();
            return this.m_accum.m_integratedValue;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public double getAccumulatorIntegratedAverage() {
        if (this.m_accum == null) {
            return 0.0;
        }
        Object object = this.m_accum.m_mutex;
        synchronized (object) {
            this.m_accum.update();
            if (this.m_accum.m_count <= 1) {
                return 0.0;
            }
            return this.m_accum.m_integratedValue / (double)(this.m_accum.m_count - 1);
        }
    }

    private static class Accumulator
    implements AutoCloseable {
        final Notifier m_notifier;
        final ByteBuffer m_buf;
        final IntBuffer m_intBuf;
        final Object m_mutex = new Object();
        long m_value;
        int m_count;
        int m_lastValue;
        long m_lastTimestamp;
        double m_integratedValue;
        int m_center;
        int m_deadband;
        double m_integratedCenter;
        final int m_validMask;
        final int m_validValue;
        final int m_dataMax;
        final int m_dataMsbMask;
        final int m_dataShift;
        final int m_xferSize;
        final boolean m_isSigned;
        final boolean m_bigEndian;
        final int m_port;

        Accumulator(int port, int xferSize, int validMask, int validValue, int dataShift, int dataSize, boolean isSigned, boolean bigEndian) {
            this.m_notifier = new Notifier(this::update);
            this.m_buf = ByteBuffer.allocateDirect((xferSize + 1) * 2048 * 4).order(ByteOrder.nativeOrder());
            this.m_intBuf = this.m_buf.asIntBuffer();
            this.m_xferSize = xferSize + 1;
            this.m_validMask = validMask;
            this.m_validValue = validValue;
            this.m_dataShift = dataShift;
            this.m_dataMax = 1 << dataSize;
            this.m_dataMsbMask = 1 << dataSize - 1;
            this.m_isSigned = isSigned;
            this.m_bigEndian = bigEndian;
            this.m_port = port;
        }

        @Override
        public void close() {
            this.m_notifier.close();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void update() {
            Object object = this.m_mutex;
            synchronized (object) {
                boolean done = false;
                while (!done) {
                    done = true;
                    int numToRead = SPIJNI.spiReadAutoReceivedData(this.m_port, this.m_buf, 0, 0.0);
                    if ((numToRead -= numToRead % this.m_xferSize) > this.m_xferSize * 2048) {
                        numToRead = this.m_xferSize * 2048;
                        done = false;
                    }
                    if (numToRead == 0) {
                        return;
                    }
                    SPIJNI.spiReadAutoReceivedData(this.m_port, this.m_buf, numToRead, 0.0);
                    for (int off = 0; off < numToRead; off += this.m_xferSize) {
                        int i;
                        long timestamp = (long)this.m_intBuf.get(off) & 0xFFFFFFFFL;
                        int resp = 0;
                        if (this.m_bigEndian) {
                            for (i = 1; i < this.m_xferSize; ++i) {
                                resp <<= 8;
                                resp |= this.m_intBuf.get(off + i) & 0xFF;
                            }
                        } else {
                            for (i = this.m_xferSize - 1; i >= 1; --i) {
                                resp <<= 8;
                                resp |= this.m_intBuf.get(off + i) & 0xFF;
                            }
                        }
                        if ((resp & this.m_validMask) == this.m_validValue) {
                            int data = resp >> this.m_dataShift;
                            if (this.m_isSigned && ((data &= this.m_dataMax - 1) & this.m_dataMsbMask) != 0) {
                                data -= this.m_dataMax;
                            }
                            int dataNoCenter = data;
                            if ((data -= this.m_center) < -this.m_deadband || data > this.m_deadband) {
                                this.m_value += (long)data;
                                if (this.m_count != 0) {
                                    this.m_integratedValue = timestamp >= this.m_lastTimestamp ? (this.m_integratedValue += (double)((long)dataNoCenter * (timestamp - this.m_lastTimestamp)) * 1.0E-6 - this.m_integratedCenter) : (this.m_integratedValue += (double)((long)dataNoCenter * (0x100000000L - this.m_lastTimestamp + timestamp)) * 1.0E-6 - this.m_integratedCenter);
                                }
                            }
                            ++this.m_count;
                            this.m_lastValue = data;
                        } else {
                            this.m_lastValue = 0;
                        }
                        this.m_lastTimestamp = timestamp;
                    }
                }
            }
        }
    }

    public static enum Port {
        kOnboardCS0(0),
        kOnboardCS1(1),
        kOnboardCS2(2),
        kOnboardCS3(3),
        kMXP(4);

        public final int value;

        private Port(int value) {
            this.value = value;
        }
    }
}

