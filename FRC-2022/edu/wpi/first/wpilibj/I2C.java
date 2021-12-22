/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.I2CJNI;
import edu.wpi.first.hal.util.BoundaryException;
import edu.wpi.first.wpilibj.util.ErrorMessages;
import java.nio.ByteBuffer;

public class I2C
implements AutoCloseable {
    private final int m_port;
    private final int m_deviceAddress;
    private ByteBuffer m_readDataToSendBuffer;

    public I2C(Port port, int deviceAddress) {
        this.m_port = port.value;
        this.m_deviceAddress = deviceAddress;
        I2CJNI.i2CInitialize((byte)port.value);
        HAL.report(21, deviceAddress);
    }

    public int getPort() {
        return this.m_port;
    }

    public int getDeviceAddress() {
        return this.m_deviceAddress;
    }

    @Override
    public void close() {
        I2CJNI.i2CClose(this.m_port);
    }

    public synchronized boolean transaction(byte[] dataToSend, int sendSize, byte[] dataReceived, int receiveSize) {
        if (dataToSend.length < sendSize) {
            throw new IllegalArgumentException("dataToSend is too small, must be at least " + sendSize);
        }
        if (dataReceived.length < receiveSize) {
            throw new IllegalArgumentException("dataReceived is too small, must be at least " + receiveSize);
        }
        return I2CJNI.i2CTransactionB(this.m_port, (byte)this.m_deviceAddress, dataToSend, (byte)sendSize, dataReceived, (byte)receiveSize) < 0;
    }

    public synchronized boolean transaction(ByteBuffer dataToSend, int sendSize, ByteBuffer dataReceived, int receiveSize) {
        if (dataToSend.hasArray() && dataReceived.hasArray()) {
            return this.transaction(dataToSend.array(), sendSize, dataReceived.array(), receiveSize);
        }
        if (!dataToSend.isDirect()) {
            throw new IllegalArgumentException("dataToSend must be a direct buffer");
        }
        if (dataToSend.capacity() < sendSize) {
            throw new IllegalArgumentException("dataToSend is too small, must be at least " + sendSize);
        }
        if (!dataReceived.isDirect()) {
            throw new IllegalArgumentException("dataReceived must be a direct buffer");
        }
        if (dataReceived.capacity() < receiveSize) {
            throw new IllegalArgumentException("dataReceived is too small, must be at least " + receiveSize);
        }
        return I2CJNI.i2CTransaction(this.m_port, (byte)this.m_deviceAddress, dataToSend, (byte)sendSize, dataReceived, (byte)receiveSize) < 0;
    }

    public boolean addressOnly() {
        return this.transaction(new byte[0], 0, new byte[0], 0);
    }

    public synchronized boolean write(int registerAddress, int data) {
        byte[] buffer = new byte[]{(byte)registerAddress, (byte)data};
        return I2CJNI.i2CWriteB(this.m_port, (byte)this.m_deviceAddress, buffer, (byte)buffer.length) < 0;
    }

    public synchronized boolean writeBulk(byte[] data) {
        return this.writeBulk(data, data.length);
    }

    public synchronized boolean writeBulk(byte[] data, int size) {
        if (data.length < size) {
            throw new IllegalArgumentException("buffer is too small, must be at least " + size);
        }
        return I2CJNI.i2CWriteB(this.m_port, (byte)this.m_deviceAddress, data, (byte)size) < 0;
    }

    public synchronized boolean writeBulk(ByteBuffer data, int size) {
        if (data.hasArray()) {
            return this.writeBulk(data.array(), size);
        }
        if (!data.isDirect()) {
            throw new IllegalArgumentException("must be a direct buffer");
        }
        if (data.capacity() < size) {
            throw new IllegalArgumentException("buffer is too small, must be at least " + size);
        }
        return I2CJNI.i2CWrite(this.m_port, (byte)this.m_deviceAddress, data, (byte)size) < 0;
    }

    public boolean read(int registerAddress, int count, byte[] buffer) {
        ErrorMessages.requireNonNullParam(buffer, "buffer", "read");
        if (count < 1) {
            throw new BoundaryException("Value must be at least 1, " + count + " given");
        }
        if (buffer.length < count) {
            throw new IllegalArgumentException("buffer is too small, must be at least " + count);
        }
        byte[] registerAddressArray = new byte[]{(byte)registerAddress};
        return this.transaction(registerAddressArray, registerAddressArray.length, buffer, count);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean read(int registerAddress, int count, ByteBuffer buffer) {
        if (count < 1) {
            throw new BoundaryException("Value must be at least 1, " + count + " given");
        }
        if (buffer.hasArray()) {
            return this.read(registerAddress, count, buffer.array());
        }
        if (!buffer.isDirect()) {
            throw new IllegalArgumentException("must be a direct buffer");
        }
        if (buffer.capacity() < count) {
            throw new IllegalArgumentException("buffer is too small, must be at least " + count);
        }
        I2C i2C = this;
        synchronized (i2C) {
            if (this.m_readDataToSendBuffer == null) {
                this.m_readDataToSendBuffer = ByteBuffer.allocateDirect(1);
            }
            this.m_readDataToSendBuffer.put(0, (byte)registerAddress);
            return this.transaction(this.m_readDataToSendBuffer, 1, buffer, count);
        }
    }

    public boolean readOnly(byte[] buffer, int count) {
        ErrorMessages.requireNonNullParam(buffer, "buffer", "readOnly");
        if (count < 1) {
            throw new BoundaryException("Value must be at least 1, " + count + " given");
        }
        if (buffer.length < count) {
            throw new IllegalArgumentException("buffer is too small, must be at least " + count);
        }
        return I2CJNI.i2CReadB(this.m_port, (byte)this.m_deviceAddress, buffer, (byte)count) < 0;
    }

    public boolean readOnly(ByteBuffer buffer, int count) {
        if (count < 1) {
            throw new BoundaryException("Value must be at least 1, " + count + " given");
        }
        if (buffer.hasArray()) {
            return this.readOnly(buffer.array(), count);
        }
        if (!buffer.isDirect()) {
            throw new IllegalArgumentException("must be a direct buffer");
        }
        if (buffer.capacity() < count) {
            throw new IllegalArgumentException("buffer is too small, must be at least " + count);
        }
        return I2CJNI.i2CRead(this.m_port, (byte)this.m_deviceAddress, buffer, (byte)count) < 0;
    }

    public boolean verifySensor(int registerAddress, int count, byte[] expected) {
        byte[] dataToSend = new byte[1];
        byte[] deviceData = new byte[4];
        for (int i = 0; i < count; i += 4) {
            int toRead = count - i < 4 ? count - i : 4;
            dataToSend[0] = (byte)(registerAddress + i);
            if (this.transaction(dataToSend, 1, deviceData, toRead)) {
                return false;
            }
            for (int j = 0; j < toRead; j = (int)((byte)(j + 1))) {
                if (deviceData[j] == expected[i + j]) continue;
                return false;
            }
        }
        return true;
    }

    public static enum Port {
        kOnboard(0),
        kMXP(1);

        public final int value;

        private Port(int value) {
            this.value = value;
        }
    }
}

