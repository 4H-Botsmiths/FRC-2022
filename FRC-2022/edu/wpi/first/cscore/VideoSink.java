/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.cscore;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.cscore.VideoProperty;
import edu.wpi.first.cscore.VideoSource;

public class VideoSink
implements AutoCloseable {
    protected int m_handle;

    public static Kind getKindFromInt(int kind) {
        switch (kind) {
            case 2: {
                return Kind.kMjpeg;
            }
            case 4: {
                return Kind.kCv;
            }
        }
        return Kind.kUnknown;
    }

    protected VideoSink(int handle) {
        this.m_handle = handle;
    }

    @Override
    public synchronized void close() {
        if (this.m_handle != 0) {
            CameraServerJNI.releaseSink(this.m_handle);
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
        VideoSink sink = (VideoSink)other;
        return this.m_handle == sink.m_handle;
    }

    public int hashCode() {
        return this.m_handle;
    }

    public Kind getKind() {
        return VideoSink.getKindFromInt(CameraServerJNI.getSinkKind(this.m_handle));
    }

    public String getName() {
        return CameraServerJNI.getSinkName(this.m_handle);
    }

    public String getDescription() {
        return CameraServerJNI.getSinkDescription(this.m_handle);
    }

    public VideoProperty getProperty(String name) {
        return new VideoProperty(CameraServerJNI.getSinkProperty(this.m_handle, name));
    }

    public VideoProperty[] enumerateProperties() {
        int[] handles = CameraServerJNI.enumerateSinkProperties(this.m_handle);
        VideoProperty[] rv = new VideoProperty[handles.length];
        for (int i = 0; i < handles.length; ++i) {
            rv[i] = new VideoProperty(handles[i]);
        }
        return rv;
    }

    public boolean setConfigJson(String config) {
        return CameraServerJNI.setSinkConfigJson(this.m_handle, config);
    }

    public String getConfigJson() {
        return CameraServerJNI.getSinkConfigJson(this.m_handle);
    }

    public void setSource(VideoSource source) {
        if (source == null) {
            CameraServerJNI.setSinkSource(this.m_handle, 0);
        } else {
            CameraServerJNI.setSinkSource(this.m_handle, source.m_handle);
        }
    }

    public VideoSource getSource() {
        return new VideoSource(CameraServerJNI.getSinkSource(this.m_handle));
    }

    public VideoProperty getSourceProperty(String name) {
        return new VideoProperty(CameraServerJNI.getSinkSourceProperty(this.m_handle, name));
    }

    public static VideoSink[] enumerateSinks() {
        int[] handles = CameraServerJNI.enumerateSinks();
        VideoSink[] rv = new VideoSink[handles.length];
        for (int i = 0; i < handles.length; ++i) {
            rv[i] = new VideoSink(handles[i]);
        }
        return rv;
    }

    public static enum Kind {
        kUnknown(0),
        kMjpeg(2),
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

