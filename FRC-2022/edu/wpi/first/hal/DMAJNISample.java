/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal;

import edu.wpi.first.hal.AccumulatorResult;
import edu.wpi.first.hal.DMAJNI;
import java.util.HashMap;
import java.util.Map;

public class DMAJNISample {
    private static final int kEnable_Accumulator0 = 8;
    private static final int kEnable_Accumulator1 = 9;
    private final int[] m_dataBuffer = new int[100];
    private final int[] m_storage = new int[100];
    private long m_timeStamp;
    private Map<Integer, BaseStore> m_propertyMap = new HashMap<Integer, BaseStore>();

    public int update(int dmaHandle, double timeoutSeconds) {
        this.m_timeStamp = DMAJNI.readDMA(dmaHandle, timeoutSeconds, this.m_dataBuffer, this.m_storage);
        return this.m_storage[25];
    }

    public int getCaptureSize() {
        return this.m_storage[22];
    }

    public int getTriggerChannels() {
        return this.m_storage[23];
    }

    public int getRemaining() {
        return this.m_storage[24];
    }

    public long getTime() {
        return this.m_timeStamp;
    }

    private BaseStore addSensorInternal(int handle) {
        BaseStore sensorData = DMAJNI.getSensorReadData(handle);
        this.m_propertyMap.put(handle, sensorData);
        return sensorData;
    }

    public void addSensor(int handle) {
        this.addSensorInternal(handle);
    }

    private int readValue(int valueType, int index) {
        int offset = this.m_storage[valueType];
        if (offset == -1) {
            throw new RuntimeException("Resource not found in DMA capture");
        }
        return this.m_dataBuffer[offset + index];
    }

    public int getEncoder(int encoderHandle) {
        BaseStore data = this.m_propertyMap.get(encoderHandle);
        if (data == null) {
            data = this.addSensorInternal(encoderHandle);
        }
        return this.readValue(data.m_valueType, data.m_index);
    }

    public int getEncoderPeriod(int encoderHandle) {
        BaseStore data = this.m_propertyMap.get(encoderHandle);
        if (data == null) {
            data = this.addSensorInternal(encoderHandle);
        }
        return this.readValue(data.m_valueType + 2, data.m_index);
    }

    public int getCounter(int counterHandle) {
        BaseStore data = this.m_propertyMap.get(counterHandle);
        if (data == null) {
            data = this.addSensorInternal(counterHandle);
        }
        return this.readValue(data.m_valueType, data.m_index);
    }

    public int getCounterPeriod(int counterHandle) {
        BaseStore data = this.m_propertyMap.get(counterHandle);
        if (data == null) {
            data = this.addSensorInternal(counterHandle);
        }
        return this.readValue(data.m_valueType + 2, data.m_index);
    }

    public boolean getDigitalSource(int digitalSourceHandle) {
        int value;
        BaseStore data = this.m_propertyMap.get(digitalSourceHandle);
        if (data == null) {
            data = this.addSensorInternal(digitalSourceHandle);
        }
        return ((value = this.readValue(data.m_valueType, 0)) >> data.m_index & 1) != 0;
    }

    public int getAnalogInput(int analogInputHandle) {
        BaseStore data = this.m_propertyMap.get(analogInputHandle);
        if (data == null) {
            data = this.addSensorInternal(analogInputHandle);
        }
        int value = this.readValue(data.m_valueType, data.m_index / 2);
        if (data.m_index % 2 != 0) {
            return value >>> 16 & 0xFFFF;
        }
        return value & 0xFFFF;
    }

    public int getAnalogInputAveraged(int analogInputHandle) {
        BaseStore data = this.m_propertyMap.get(analogInputHandle);
        if (data == null) {
            data = this.addSensorInternal(analogInputHandle);
        }
        int value = this.readValue(data.m_valueType + 2, data.m_index);
        return value;
    }

    public void getAnalogAccumulator(int analogInputHandle, AccumulatorResult result) {
        BaseStore data = this.m_propertyMap.get(analogInputHandle);
        if (data == null) {
            data = this.addSensorInternal(analogInputHandle);
        }
        if (data.m_index == 0) {
            int val0 = this.readValue(8, 0);
            int val1 = this.readValue(8, 1);
            int val2 = this.readValue(8, 2);
            result.count = val2;
            result.value = (long)val1 << 32 | (long)val0;
        } else if (data.m_index == 1) {
            int val0 = this.readValue(9, 0);
            int val1 = this.readValue(9, 1);
            int val2 = this.readValue(9, 2);
            result.count = val2;
            result.value = (long)val1 << 32 | (long)val0;
        } else {
            throw new RuntimeException("Resource not found in DMA capture");
        }
    }

    public int getDutyCycleOutput(int dutyCycleHandle) {
        BaseStore data = this.m_propertyMap.get(dutyCycleHandle);
        if (data == null) {
            data = this.addSensorInternal(dutyCycleHandle);
        }
        return this.readValue(data.m_valueType, data.m_index);
    }

    static class BaseStore {
        public final int m_valueType;
        public final int m_index;

        BaseStore(int valueType, int index) {
            this.m_valueType = valueType;
            this.m_index = index;
        }
    }
}

