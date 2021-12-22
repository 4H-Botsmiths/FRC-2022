/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.cscore.VideoMode;
import edu.wpi.first.cscore.VideoProperty;
import edu.wpi.first.cscore.VideoSink;

public class VideoSource
implements AutoCloseable {
    protected int m_handle;

    public static Kind getKindFromInt(int kind) {
        switch (kind) {
            case 1: {
                return Kind.kUsb;
            }
            case 2: {
                return Kind.kHttp;
            }
            case 4: {
                return Kind.kCv;
            }
        }
        return Kind.kUnknown;
    }

    protected VideoSource(int handle) {
        this.m_handle = handle;
    }

    @Override
    public synchronized void close() {
        if (this.m_handle != 0) {
            CameraServerJNI.releaseSource(this.m_handle);
        }
        this.m_handle = 0;
    }

    public boolean isValid() {
        return this.m_handle != 0;
    }

    public int getHandle() {
        return this.m_handle;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }
        VideoSource source = (VideoSource)other;
        return this.m_handle == source.m_handle;
    }

    public int hashCode() {
        return this.m_handle;
    }

    public Kind getKind() {
        return VideoSource.getKindFromInt(CameraServerJNI.getSourceKind(this.m_handle));
    }

    public String getName() {
        return CameraServerJNI.getSourceName(this.m_handle);
    }

    public String getDescription() {
        return CameraServerJNI.getSourceDescription(this.m_handle);
    }

    public long getLastFrameTime() {
        return CameraServerJNI.getSourceLastFrameTime(this.m_handle);
    }

    public void setConnectionStrategy(ConnectionStrategy strategy) {
        CameraServerJNI.setSourceConnectionStrategy(this.m_handle, strategy.getValue());
    }

    public boolean isConnected() {
        return CameraServerJNI.isSourceConnected(this.m_handle);
    }

    public boolean isEnabled() {
        return CameraServerJNI.isSourceEnabled(this.m_handle);
    }

    public VideoProperty getProperty(String name) {
        return new VideoProperty(CameraServerJNI.getSourceProperty(this.m_handle, name));
    }

    public VideoProperty[] enumerateProperties() {
        int[] handles = CameraServerJNI.enumerateSourceProperties(this.m_handle);
        VideoProperty[] rv = new VideoProperty[handles.length];
        for (int i = 0; i < handles.length; ++i) {
            rv[i] = new VideoProperty(handles[i]);
        }
        return rv;
    }

    public VideoMode getVideoMode() {
        return CameraServerJNI.getSourceVideoMode(this.m_handle);
    }

    public boolean setVideoMode(VideoMode mode) {
        return CameraServerJNI.setSourceVideoMode(this.m_handle, mode.pixelFormat.getValue(), mode.width, mode.height, mode.fps);
    }

    public boolean setVideoMode(VideoMode.PixelFormat pixelFormat, int width, int height, int fps) {
        return CameraServerJNI.setSourceVideoMode(this.m_handle, pixelFormat.getValue(), width, height, fps);
    }

    public boolean setPixelFormat(VideoMode.PixelFormat pixelFormat) {
        return CameraServerJNI.setSourcePixelFormat(this.m_handle, pixelFormat.getValue());
    }

    public boolean setResolution(int width, int height) {
        return CameraServerJNI.setSourceResolution(this.m_handle, width, height);
    }

    public boolean setFPS(int fps) {
        return CameraServerJNI.setSourceFPS(this.m_handle, fps);
    }

    public boolean setConfigJson(String config) {
        return CameraServerJNI.setSourceConfigJson(this.m_handle, config);
    }

    public String getConfigJson() {
        return CameraServerJNI.getSourceConfigJson(this.m_handle);
    }

    public double getActualFPS() {
        return CameraServerJNI.getTelemetryAverageValue(this.m_handle, CameraServerJNI.TelemetryKind.kSourceFramesReceived);
    }

    public double getActualDataRate() {
        return CameraServerJNI.getTelemetryAverageValue(this.m_handle, CameraServerJNI.TelemetryKind.kSourceBytesReceived);
    }

    public VideoMode[] enumerateVideoModes() {
        return CameraServerJNI.enumerateSourceVideoModes(this.m_handle);
    }

    public VideoSink[] enumerateSinks() {
        int[] handles = CameraServerJNI.enumerateSourceSinks(this.m_handle);
        VideoSink[] rv = new VideoSink[handles.length];
        for (int i = 0; i < handles.length; ++i) {
            rv[i] = new VideoSink(handles[i]);
        }
        return rv;
    }

    public static VideoSource[] enumerateSources() {
        int[] handles = CameraServerJNI.enumerateSources();
        VideoSource[] rv = new VideoSource[handles.length];
        for (int i = 0; i < handles.length; ++i) {
            rv[i] = new VideoSource(handles[i]);
        }
        return rv;
    }

    public static enum ConnectionStrategy {
        kAutoManage(0),
        kKeepOpen(1),
        kForceClose(2);

        private final int value;

        private ConnectionStrategy(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum Kind {
        kUnknown(0),
        kUsb(1),
        kHttp(2),
        kCv(4),
        kRaw(8);

        private final int value;

        private Kind(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}

