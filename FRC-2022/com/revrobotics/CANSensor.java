/*
 * Decompiled with CFR 0.150.
 */
package com.revrobotics;

import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;

public abstract class CANSensor {
    protected final CANSparkMax m_device;

    @Deprecated(forRemoval=true)
    public CANSensor(CANSparkMax device) {
        this.m_device = device;
    }

    protected abstract int getID();

    public abstract CANError setInverted(boolean var1);

    public abstract boolean getInverted();

    protected static enum FeedbackSensorType {
        kNoSensor(0),
        kHallSensor(1),
        kQuadrature(2),
        kSensorless(3),
        kAnalog(4),
        kAltQuadrature(5);

        public final int value;

        private FeedbackSensorType(int value) {
            this.value = value;
        }

        public static FeedbackSensorType fromId(int id) {
            switch (id) {
                case 1: {
                    return kHallSensor;
                }
                case 2: {
                    return kQuadrature;
                }
                case 3: {
                    return kSensorless;
                }
                case 4: {
                    return kAnalog;
                }
                case 5: {
                    return kAltQuadrature;
                }
            }
            return kNoSensor;
        }
    }
}

