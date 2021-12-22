/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.cscore.VideoMode;
import edu.wpi.first.cscore.VideoProperty;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource;

public class VideoEvent {
    public Kind kind;
    public int sourceHandle;
    public int sinkHandle;
    public String name;
    public VideoMode mode;
    public int propertyHandle;
    public VideoProperty.Kind propertyKind;
    public int value;
    public String valueStr;
    public int listener;

    public static Kind getKindFromInt(int kind) {
        switch (kind) {
            case 1: {
                return Kind.kSourceCreated;
            }
            case 2: {
                return Kind.kSourceDestroyed;
            }
            case 4: {
                return Kind.kSourceConnected;
            }
            case 8: {
                return Kind.kSourceDisconnected;
            }
            case 16: {
                return Kind.kSourceVideoModesUpdated;
            }
            case 32: {
                return Kind.kSourceVideoModeChanged;
            }
            case 64: {
                return Kind.kSourcePropertyCreated;
            }
            case 128: {
                return Kind.kSourcePropertyValueUpdated;
            }
            case 256: {
                return Kind.kSourcePropertyChoicesUpdated;
            }
            case 512: {
                return Kind.kSinkSourceChanged;
            }
            case 1024: {
                return Kind.kSinkCreated;
            }
            case 2048: {
                return Kind.kSinkDestroyed;
            }
            case 4096: {
                return Kind.kSinkEnabled;
            }
            case 8192: {
                return Kind.kSinkDisabled;
            }
            case 16384: {
                return Kind.kNetworkInterfacesChanged;
            }
            case 65536: {
                return Kind.kSinkPropertyCreated;
            }
            case 131072: {
                return Kind.kSinkPropertyValueUpdated;
            }
            case 262144: {
                return Kind.kSinkPropertyChoicesUpdated;
            }
            case 524288: {
                return Kind.kUsbCamerasChanged;
            }
        }
        return Kind.kUnknown;
    }

    VideoEvent(int kind, int source, int sink, String name, int pixelFormat, int width, int height, int fps, int property, int propertyKind, int value, String valueStr, int listener) {
        this.kind = VideoEvent.getKindFromInt(kind);
        this.sourceHandle = source;
        this.sinkHandle = sink;
        this.name = name;
        this.mode = new VideoMode(pixelFormat, width, height, fps);
        this.propertyHandle = property;
        this.propertyKind = VideoProperty.getKindFromInt(propertyKind);
        this.value = value;
        this.valueStr = valueStr;
        this.listener = listener;
    }

    public VideoSource getSource() {
        return new VideoSource(CameraServerJNI.copySource(this.sourceHandle));
    }

    public VideoSink getSink() {
        return new VideoSink(CameraServerJNI.copySink(this.sinkHandle));
    }

    public VideoProperty getProperty() {
        return new VideoProperty(this.propertyHandle, this.propertyKind);
    }

    public static enum Kind {
        kUnknown(0),
        kSourceCreated(1),
        kSourceDestroyed(2),
        kSourceConnected(4),
        kSourceDisconnected(8),
        kSourceVideoModesUpdated(16),
        kSourceVideoModeChanged(32),
        kSourcePropertyCreated(64),
        kSourcePropertyValueUpdated(128),
        kSourcePropertyChoicesUpdated(256),
        kSinkSourceChanged(512),
        kSinkCreated(1024),
        kSinkDestroyed(2048),
        kSinkEnabled(4096),
        kSinkDisabled(8192),
        kNetworkInterfacesChanged(16384),
        kTelemetryUpdated(32768),
        kSinkPropertyCreated(65536),
        kSinkPropertyValueUpdated(131072),
        kSinkPropertyChoicesUpdated(262144),
        kUsbCamerasChanged(524288);

        private final int value;

        private Kind(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}

