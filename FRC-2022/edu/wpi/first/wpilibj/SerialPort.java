/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.SerialPortJNI;
import java.nio.charset.StandardCharsets;

public class SerialPort
implements AutoCloseable {
    private int m_portHandle;

    @Deprecated
    public SerialPort(int baudRate, String portName, Port port, int dataBits, Parity parity, StopBits stopBits) {
        this.m_portHandle = SerialPortJNI.serialInitializePortDirect((byte)port.value, portName);
        SerialPortJNI.serialSetBaudRate(this.m_portHandle, baudRate);
        SerialPortJNI.serialSetDataBits(this.m_portHandle, (byte)dataBits);
        SerialPortJNI.serialSetParity(this.m_portHandle, (byte)parity.value);
        SerialPortJNI.serialSetStopBits(this.m_portHandle, (byte)stopBits.value);
        this.setReadBufferSize(1);
        this.setTimeout(5.0);
        this.setWriteBufferMode(WriteBufferMode.kFlushOnAccess);
        this.disableTermination();
        HAL.report(32, port.value + 1);
    }

    public SerialPort(int baudRate, Port port, int dataBits, Parity parity, StopBits stopBits) {
        this.m_portHandle = SerialPortJNI.serialInitializePort((byte)port.value);
        SerialPortJNI.serialSetBaudRate(this.m_portHandle, baudRate);
        SerialPortJNI.serialSetDataBits(this.m_portHandle, (byte)dataBits);
        SerialPortJNI.serialSetParity(this.m_portHandle, (byte)parity.value);
        SerialPortJNI.serialSetStopBits(this.m_portHandle, (byte)stopBits.value);
        this.setReadBufferSize(1);
        this.setTimeout(5.0);
        this.setWriteBufferMode(WriteBufferMode.kFlushOnAccess);
        this.disableTermination();
        HAL.report(32, port.value + 1);
    }

    public SerialPort(int baudRate, Port port, int dataBits, Parity parity) {
        this(baudRate, port, dataBits, parity, StopBits.kOne);
    }

    public SerialPort(int baudRate, Port port, int dataBits) {
        this(baudRate, port, dataBits, Parity.kNone, StopBits.kOne);
    }

    public SerialPort(int baudRate, Port port) {
        this(baudRate, port, 8, Parity.kNone, StopBits.kOne);
    }

    @Override
    public void close() {
        SerialPortJNI.serialClose(this.m_portHandle);
    }

    public void setFlowControl(FlowControl flowControl) {
        SerialPortJNI.serialSetFlowControl(this.m_portHandle, (byte)flowControl.value);
    }

    public void enableTermination(char terminator) {
        SerialPortJNI.serialEnableTermination(this.m_portHandle, terminator);
    }

    public void enableTermination() {
        this.enableTermination('\n');
    }

    public void disableTermination() {
        SerialPortJNI.serialDisableTermination(this.m_portHandle);
    }

    public int getBytesReceived() {
        return SerialPortJNI.serialGetBytesReceived(this.m_portHandle);
    }

    public String readString() {
        return this.readString(this.getBytesReceived());
    }

    public String readString(int count) {
        byte[] out = this.read(count);
        return new String(out, 0, out.length, StandardCharsets.US_ASCII);
    }

    public byte[] read(int count) {
        byte[] dataReceivedBuffer = new byte[count];
        int gotten = SerialPortJNI.serialRead(this.m_portHandle, dataReceivedBuffer, count);
        if (gotten == count) {
            return dataReceivedBuffer;
        }
        byte[] retVal = new byte[gotten];
        System.arraycopy(dataReceivedBuffer, 0, retVal, 0, gotten);
        return retVal;
    }

    public int write(byte[] buffer, int count) {
        if (buffer.length < count) {
            throw new IllegalArgumentException("buffer is too small, must be at least " + count);
        }
        return SerialPortJNI.serialWrite(this.m_portHandle, buffer, count);
    }

    public int writeString(String data) {
        return this.write(data.getBytes(StandardCharsets.UTF_8), data.length());
    }

    public void setTimeout(double timeout) {
        SerialPortJNI.serialSetTimeout(this.m_portHandle, timeout);
    }

    public void setReadBufferSize(int size) {
        SerialPortJNI.serialSetReadBufferSize(this.m_portHandle, size);
    }

    public void setWriteBufferSize(int size) {
        SerialPortJNI.serialSetWriteBufferSize(this.m_portHandle, size);
    }

    public void setWriteBufferMode(WriteBufferMode mode) {
        SerialPortJNI.serialSetWriteMode(this.m_portHandle, (byte)mode.value);
    }

    public void flush() {
        SerialPortJNI.serialFlush(this.m_portHandle);
    }

    public void reset() {
        SerialPortJNI.serialClear(this.m_portHandle);
    }

    public static enum WriteBufferMode {
        kFlushOnAccess(1),
        kFlushWhenFull(2);

        public final int value;

        private WriteBufferMode(int value) {
            this.value = value;
        }
    }

    public static enum FlowControl {
        kNone(0),
        kXonXoff(1),
        kRtsCts(2),
        kDtsDsr(4);

        public final int value;

        private FlowControl(int value) {
            this.value = value;
        }
    }

    public static enum StopBits {
        kOne(10),
        kOnePointFive(15),
        kTwo(20);

        public final int value;

        private StopBits(int value) {
            this.value = value;
        }
    }

    public static enum Parity {
        kNone(0),
        kOdd(1),
        kEven(2),
        kMark(3),
        kSpace(4);

        public final int value;

        private Parity(int value) {
            this.value = value;
        }
    }

    public static enum Port {
        kOnboard(0),
        kMXP(1),
        kUSB(2),
        kUSB1(2),
        kUSB2(3);

        public final int value;

        private Port(int value) {
            this.value = value;
        }
    }
}

