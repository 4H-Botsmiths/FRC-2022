/*
 * Decompiled with CFR 0.150.
 */
package com.revrobotics;

import com.revrobotics.CANError;
import com.revrobotics.ControlType;
import com.revrobotics.jni.CANSparkMaxJNI;
import edu.wpi.first.wpilibj.SpeedController;
import java.nio.ByteBuffer;

public abstract class CANSparkMaxLowLevel
implements SpeedController {
    public static final int kAPIMajorVersion = CANSparkMaxJNI.c_SparkMax_GetAPIMajorRevision();
    public static final int kAPIMinorVersion = CANSparkMaxJNI.c_SparkMax_GetAPIMinorRevision();
    public static final int kAPIBuildVersion = CANSparkMaxJNI.c_SparkMax_GetAPIBuildRevision();
    public static final int kAPIVersion = CANSparkMaxJNI.c_SparkMax_GetAPIVersion();
    protected long m_sparkMax;
    private final int m_deviceID;
    private String m_firmwareString;
    protected final MotorType m_motorType;

    public CANSparkMaxLowLevel(int deviceID, MotorType type) {
        this.m_deviceID = deviceID;
        this.m_sparkMax = CANSparkMaxJNI.c_SparkMax_Create(deviceID, type.value);
        this.m_firmwareString = "";
        this.m_motorType = type;
    }

    public int getFirmwareVersion() {
        return CANSparkMaxJNI.c_SparkMax_GetFirmwareVersion(this.m_sparkMax);
    }

    public void setControlFramePeriodMs(int periodMs) {
        CANSparkMaxJNI.c_SparkMax_SetControlFramePeriod(this.m_sparkMax, periodMs);
    }

    public String getFirmwareString() {
        if (this.m_firmwareString == "") {
            int version = this.getFirmwareVersion();
            ByteBuffer b = ByteBuffer.allocate(4);
            b.putInt(version);
            byte[] verBytes = b.array();
            StringBuilder firmwareString = new StringBuilder();
            firmwareString.append("v").append(verBytes[0]).append(".").append(verBytes[1]).append(".").append(verBytes[2] << 8 | verBytes[3]);
            this.m_firmwareString = firmwareString.toString();
        }
        return this.m_firmwareString;
    }

    public byte[] getSerialNumber() {
        return new byte[0];
    }

    public int getDeviceId() {
        return this.m_deviceID;
    }

    public MotorType getInitialMotorType() {
        return this.m_motorType;
    }

    @Deprecated(forRemoval=true)
    public CANError setMotorType(MotorType type) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetMotorType(this.m_sparkMax, type.value));
    }

    public MotorType getMotorType() {
        return MotorType.fromId(CANSparkMaxJNI.c_SparkMax_GetMotorType(this.m_sparkMax));
    }

    public CANError setPeriodicFramePeriod(PeriodicFrame frameID, int periodMs) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetPeriodicFramePeriod(this.m_sparkMax, frameID.value, periodMs));
    }

    public static void enableExternalUSBControl(boolean enable) {
        CANSparkMaxJNI.c_SparkMax_EnableExternalControl(enable);
    }

    static void setEnable(boolean enable) {
        CANSparkMaxJNI.c_SparkMax_SetEnable(enable);
    }

    CANError setFollow(FollowConfig follower) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetFollow(this.m_sparkMax, follower.leaderArbId, follower.config.getRaw()));
    }

    CANError setpointCommand(double value) {
        return this.setpointCommand(value, ControlType.kDutyCycle);
    }

    CANError setpointCommand(double value, ControlType ctrl) {
        return this.setpointCommand(value, ctrl, 0);
    }

    CANError setpointCommand(double value, ControlType ctrl, int pidSlot) {
        return this.setpointCommand(value, ctrl, pidSlot, 0.0);
    }

    CANError setpointCommand(double value, ControlType ctrl, int pidSlot, double arbFeedforward) {
        return this.setpointCommand(value, ctrl, pidSlot, arbFeedforward, 0);
    }

    CANError setpointCommand(double value, ControlType ctrl, int pidSlot, double arbFeedforward, int arbFFUnits) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetpointCommand(this.m_sparkMax, (float)value, ctrl.value, pidSlot, (float)arbFeedforward, arbFFUnits));
    }

    public float getSafeFloat(float f) {
        if (Float.isNaN(f) || Float.isInfinite(f)) {
            return 0.0f;
        }
        return f;
    }

    protected CANError setEncPosition(double value) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetEncoderPosition(this.m_sparkMax, (float)value));
    }

    protected CANError setAltEncPosition(double value) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetAltEncoderPosition(this.m_sparkMax, (float)value));
    }

    protected CANError setIAccum(double value) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetIAccum(this.m_sparkMax, (float)value));
    }

    public CANError restoreFactoryDefaults() {
        return this.restoreFactoryDefaults(false);
    }

    public CANError restoreFactoryDefaults(boolean persist) {
        return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_RestoreFactoryDefaults(this.m_sparkMax, persist));
    }

    protected static class FollowConfig {
        int leaderArbId;
        Config config = new Config();

        FollowConfig() {
        }

        public static class Config {
            public int rsvd1;
            public int invert;
            public int rsvd2;
            public int predefined;

            public int getRaw() {
                return this.rsvd1 & 0x3FFFF | (this.invert & 1) << 18 | (this.rsvd2 & 0x1F) << 19 | (this.predefined & 0xFF) << 24;
            }
        }
    }

    public class PeriodicStatus2 {
        public double sensorPosition;
        public double iAccum;
    }

    public class PeriodicStatus1 {
        public double sensorVelocity;
        public byte motorTemperature;
        public double busVoltage;
        public double outputCurrent;
    }

    public class PeriodicStatus0 {
        public double appliedOutput;
        public short faults;
        public short stickyFaults;
        public byte lock;
        public MotorType motorType;
        public boolean isFollower;
        public boolean isInverted;
        public boolean roboRIO;
    }

    public static enum PeriodicFrame {
        kStatus0(0),
        kStatus1(1),
        kStatus2(2);

        public final int value;

        private PeriodicFrame(int value) {
            this.value = value;
        }

        public static PeriodicFrame fromId(int id) {
            for (PeriodicFrame type : PeriodicFrame.values()) {
                if (type.value != id) continue;
                return type;
            }
            return null;
        }
    }

    public static enum MotorType {
        kBrushed(0),
        kBrushless(1);

        public final int value;

        private MotorType(int value) {
            this.value = value;
        }

        public static MotorType fromId(int id) {
            for (MotorType type : MotorType.values()) {
                if (type.value != id) continue;
                return type;
            }
            return null;
        }
    }
}

