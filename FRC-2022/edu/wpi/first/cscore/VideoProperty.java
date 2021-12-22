/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.CameraServerJNI;

public class VideoProperty {
    int m_handle;
    private Kind m_kind;

    public static Kind getKindFromInt(int kind) {
        switch (kind) {
            case 1: {
                return Kind.kBoolean;
            }
            case 2: {
                return Kind.kInteger;
            }
            case 4: {
                return Kind.kString;
            }
            case 8: {
                return Kind.kEnum;
            }
        }
        return Kind.kNone;
    }

    public String getName() {
        return CameraServerJNI.getPropertyName(this.m_handle);
    }

    public Kind getKind() {
        return this.m_kind;
    }

    public boolean isValid() {
        return this.m_kind != Kind.kNone;
    }

    public boolean isBoolean() {
        return this.m_kind == Kind.kBoolean;
    }

    public boolean isInteger() {
        return this.m_kind == Kind.kInteger;
    }

    public boolean isString() {
        return this.m_kind == Kind.kString;
    }

    public boolean isEnum() {
        return this.m_kind == Kind.kEnum;
    }

    public int get() {
        return CameraServerJNI.getProperty(this.m_handle);
    }

    public void set(int value) {
        CameraServerJNI.setProperty(this.m_handle, value);
    }

    public int getMin() {
        return CameraServerJNI.getPropertyMin(this.m_handle);
    }

    public int getMax() {
        return CameraServerJNI.getPropertyMax(this.m_handle);
    }

    public int getStep() {
        return CameraServerJNI.getPropertyStep(this.m_handle);
    }

    public int getDefault() {
        return CameraServerJNI.getPropertyDefault(this.m_handle);
    }

    public String getString() {
        return CameraServerJNI.getStringProperty(this.m_handle);
    }

    public void setString(String value) {
        CameraServerJNI.setStringProperty(this.m_handle, value);
    }

    public String[] getChoices() {
        return CameraServerJNI.getEnumPropertyChoices(this.m_handle);
    }

    VideoProperty(int handle) {
        this.m_handle = handle;
        this.m_kind = VideoProperty.getKindFromInt(CameraServerJNI.getPropertyKind(handle));
    }

    VideoProperty(int handle, Kind kind) {
        this.m_handle = handle;
        this.m_kind = kind;
    }

    public static enum Kind {
        kNone(0),
        kBoolean(1),
        kInteger(2),
        kString(4),
        kEnum(8);

        private final int value;

        private Kind(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}

